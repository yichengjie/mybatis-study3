1. 添加配置
    ```properties
    mybatis-plus.configuration.cache-enabled=true
    ```
2. 在mapper.xml文件中添加cache标签
   ```xml
   <cache size="10240" eviction="LRU"/>
   ```
3. 同一个事务中二级缓存不生效，会使用一级缓存，因为事务未提交。
#### 执行流程部分
1. Configuration创建Executor对象
    ```java
    public class Configuration{
        public Executor newExecutor(Transaction transaction, ExecutorType executorType) {
            executorType = executorType == null ? defaultExecutorType : executorType;
            Executor executor = new SimpleExecutor(this, transaction);
            // 根据settings中配置的启用cache创建CachingExecutor包装类
            if (cacheEnabled) {
                executor = new CachingExecutor(executor);
            }
            return (Executor) interceptorChain.pluginAll(executor);
        }
    }
    ```
2. CachingExecutor#query查询逻辑
   ```java
   public class CachingExecutor implements Executor {
      public <E> List<E> query(
              MappedStatement ms, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler,
              CacheKey key, BoundSql boundSql) throws SQLException {
         //1. 从 MappedStatement中获取二级缓存对象，如果存在，则进入缓存查询逻辑
         Cache cache = ms.getCache();
         if (cache != null) {
            flushCacheIfRequired(ms);
            if (ms.isUseCache() && resultHandler == null) {
               ensureNoOutParams(ms, boundSql);
               List<E> list = (List<E>) tcm.getObject(cache, key);
               if (list == null) {
                  list = delegate.query(ms, parameterObject, rowBounds, resultHandler, key, boundSql);
                  //2. 将数据保存到缓存中，注意这里先放入TransactionalCache对象中，等待事务提交时回调CachingExecutor#commit时，
                  // 调用TransactionalCache#commit -> 调用flushPendingEntries将缓存数据写入到cache中,
                  // 所以同一个事务中多次查询无法使用二级缓存中的数据
                  tcm.putObject(cache, key, list); // issue #578 and #116
               }
               return list;
            }
         }
         // 3. 非二级缓存逻辑
         return delegate.query(ms, parameterObject, rowBounds, resultHandler, key, boundSql);
      }    
   }
   ```
3. TransactionalCache#commit事务提交时回调逻辑
   ```java
   public class TransactionalCache{
      // 事务体骄傲时回调函数 
      public void commit() {
         if (clearOnCommit) {
            delegate.clear();
         }
         flushPendingEntries();
         reset();
      }
      private void flushPendingEntries() {
         for (Map.Entry<Object, Object> entry : entriesToAddOnCommit.entrySet()) {
            // 将缓存数据写入到cache对象中 
            delegate.putObject(entry.getKey(), entry.getValue());
         }
         for (Object entry : entriesMissedInCache) {
            if (!entriesToAddOnCommit.containsKey(entry)) {
               delegate.putObject(entry, null);
            }
         }
      }
   }
   ```
### 解析注册部分
#### XMLMapperBuilder解析MapperStatement并放入到Configuration中
1. XMLMapperBuilder#configurationElement业务逻辑处理
   ```java
   public class XMLMapperBuilder{
      private void configurationElement(XNode context) {
         try {
            String namespace = context.getStringAttribute("namespace");
            if (namespace == null || namespace.isEmpty()) {
               throw new BuilderException("Mapper's namespace cannot be empty");
            }
            builderAssistant.setCurrentNamespace(namespace);
            cacheRefElement(context.evalNode("cache-ref"));
            // 1. 解析XXXMapper.xml文件中的cache标签，并调用builderAssistant.useNewCache构建Cache实例
            cacheElement(context.evalNode("cache"));
            parameterMapElement(context.evalNodes("/mapper/parameterMap"));
            resultMapElements(context.evalNodes("/mapper/resultMap"));
            sqlElement(context.evalNodes("/mapper/sql"));
            // 2. 构建Statement对象
            buildStatementFromContext(context.evalNodes("select|insert|update|delete"));
         } catch (Exception e) {
            throw new BuilderException("Error parsing Mapper XML. The XML location is '" + resource + "'. Cause: " + e, e);
         }
      }    
      private void buildStatementFromContext(List<XNode> list, String requiredDatabaseId) {
         for (XNode context : list) {
            final XMLStatementBuilder statementParser = new XMLStatementBuilder(configuration, 
                builderAssistant, context, requiredDatabaseId);
             // 构建Statement对象
             statementParser.parseStatementNode();
        }
     }
   }
   ```
#### XMLMapperBuilder#cacheElement 展开细节
1. MapperBuilderAssistant#useNewCache构建Cache实例并添加到Configuration中缓存
   ```java
   public class MapperBuilderAssistant{
      public Cache useNewCache(
              Class<? extends Cache> typeClass, Class<? extends Cache> evictionClass, Long flushInterval,
              Integer size, boolean readWrite, boolean blocking, Properties props) {
         Cache cache = new CacheBuilder(currentNamespace).implementation(valueOrDefault(typeClass, PerpetualCache.class))
                 .addDecorator(valueOrDefault(evictionClass, LruCache.class)).clearInterval(flushInterval).size(size)
                 .readWrite(readWrite).blocking(blocking).properties(props).build();
         configuration.addCache(cache);
         // 将cache实例赋值给MapperBuilderAssistant对象的 currentCache 属性
         currentCache = cache;
         return cache;
      }    
   }
   ```
####  XMLStatementBuilder#parseStatementNode 展开细节
1. XMLStatementBuilder#parseStatementNode调用builderAssistant.addMappedStatement实例化MapperStatement对象
   ```java
   public class XMLStatementBuilder extends BaseBuilder {
      public void parseStatementNode() {
         // 省略部分代码 
         builderAssistant.addMappedStatement(
              id, sqlSource, statementType, sqlCommandType, fetchSize, timeout, parameterMap,
              parameterTypeClass, resultMap, resultTypeClass, resultSetTypeEnum, flushCache, useCache, resultOrdered,
              keyGenerator, keyProperty, keyColumn, databaseId, langDriver, resultSets, dirtySelect);
      }
   }
   ```
2. MapperBuilderAssistant#addMappedStatement
   ```java
   public class MapperBuilderAssistant extends BaseBuilder {
      public MappedStatement addMappedStatement(
           String id, SqlSource sqlSource, StatementType statementType,
           SqlCommandType sqlCommandType, Integer fetchSize, Integer timeout, String parameterMap, Class<?> parameterType,
           String resultMap, Class<?> resultType, ResultSetType resultSetType, boolean flushCache, boolean useCache,
           boolean resultOrdered, KeyGenerator keyGenerator, String keyProperty, String keyColumn, String databaseId,
           LanguageDriver lang, String resultSets, boolean dirtySelect) {
         id = applyCurrentNamespace(id, false);
         MappedStatement.Builder statementBuilder = new MappedStatement.Builder(configuration, id, sqlSource, sqlCommandType)
                 .resource(resource).fetchSize(fetchSize).timeout(timeout).statementType(statementType)
                 .keyGenerator(keyGenerator).keyProperty(keyProperty).keyColumn(keyColumn).databaseId(databaseId).lang(lang)
                 .resultOrdered(resultOrdered).resultSets(resultSets)
                 .resultMaps(getStatementResultMaps(resultMap, resultType, id)).resultSetType(resultSetType)
                 .flushCacheRequired(flushCache).useCache(useCache)
                 //1. 将MapperBuilderAssistant对象的 currentCache 属性放入到cache字段中
                 .cache(currentCache)
                .dirtySelect(dirtySelect);
         ParameterMap statementParameterMap = getStatementParameterMap(parameterMap, parameterType, id);
         if (statementParameterMap != null) {
            statementBuilder.parameterMap(statementParameterMap);
         }
         MappedStatement statement = statementBuilder.build();
         configuration.addMappedStatement(statement);
         return statement;
      }    
   }
   ```