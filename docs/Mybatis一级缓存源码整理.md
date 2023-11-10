1. 使用一级缓存
    ```java
    @Service
    public class UserService extends ServiceImpl<UserMapper, UserEntity>{
        @Autowired
        private UserMapper userMapper ;
    
        //1. 这里添加@Transactional 注解
        @Transactional(rollbackFor = Exception.class)
        public List<UserEntity> listUser(UserEntity entity){
            LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>(entity) ;
            //2. 首次查询
            List<UserEntity> list = baseMapper.selectList(wrapper);
            //3. 再次查询， 这一会一级缓存
            list = baseMapper.selectList(wrapper);
            return list ;
        }
    }
    ```
2. 关闭一级缓存
    ```properties
    mybatis-plus.configuration.local-cache-scope=statement
    ```
3. 一级缓存生效两个条件, 1. 添加事务注解，2. 事务方法重再次同样的查询
#### 缓存原理
1. 添加@Transactional注解主要是为了保证两次查询使用同一个SqlSession对象
    ```java
    private class SqlSessionInterceptor implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            SqlSession sqlSession = getSqlSession(SqlSessionTemplate.this.sqlSessionFactory,
                    SqlSessionTemplate.this.executorType, SqlSessionTemplate.this.exceptionTranslator);
            try {
                Object result = method.invoke(sqlSession, args);
                if (!isSqlSessionTransactional(sqlSession, SqlSessionTemplate.this.sqlSessionFactory)) {
                    sqlSession.commit(true);
                }
                return result;
            } finally {
                if (sqlSession != null) {
                    closeSqlSession(sqlSession, SqlSessionTemplate.this.sqlSessionFactory);
                }
            }
        }
    }
    ```
2. DefaultSqlSessionFactory#openSessionFromDataSource中新创建Executor: SimpleExecutor 继承 BaseExecutor
   ```java
   public class DefaultSqlSessionFactory{
      private SqlSession openSessionFromDataSource(
              ExecutorType execType, TransactionIsolationLevel level, boolean autoCommit) {
          final Environment environment = configuration.getEnvironment();
          final TransactionFactory transactionFactory = getTransactionFactoryFromEnvironment(environment);
          Transaction tx = transactionFactory.newTransaction(environment.getDataSource(), level, autoCommit);
          final Executor executor = configuration.newExecutor(tx, execType);
          return new DefaultSqlSession(configuration, executor, autoCommit);
      }
   }
   ```
3. BaseExecutor构造函数初始化一级缓存对象 localCache：PerpetualCache
4. BaseExecutor#query 方法中业务逻辑
   ```java
   public class BaseExecutor{
      public <E> List<E> query(
              MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler,
              CacheKey key, BoundSql boundSql) throws SQLException {
         // 1. 从localCache缓存中获取数据
         List<E> list = resultHandler == null ? (List<E>) localCache.getObject(key) : null;
         if (list == null) {
            // 2. 如果缓存数据不存在则从数据库查询数据 
            list = queryFromDatabase(ms, parameter, rowBounds, resultHandler, key, boundSql);
         } 
         // 3. 当local-cache-scope配置为statement时，清空localCache中缓存
         if (configuration.getLocalCacheScope() == LocalCacheScope.STATEMENT) {
            // issue #482
            clearLocalCache();
         }
         return list;
      }
   }
   ```
5. BaseExecutor#queryFromDatabase 方法中业务逻辑
   ```java
   public class BaseExecutor{
       private <E> List<E> queryFromDatabase(MappedStatement ms, Object parameter, RowBounds rowBounds,
            ResultHandler resultHandler, CacheKey key, BoundSql boundSql) throws SQLException {
          // 1. 从数据库中查询数据
          List<E> list = doQuery(ms, parameter, rowBounds, resultHandler, boundSql);
          // 2. 将查询到的数据放入localCache缓存中
          localCache.putObject(key, list);
          return list;
        }
   }
   ```