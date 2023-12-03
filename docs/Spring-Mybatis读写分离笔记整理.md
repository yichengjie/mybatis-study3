1. 编写Spring动态数据源实现类
    ```java
    public class ReadWriteDataSource extends AbstractRoutingDataSource {
        @Nullable
        @Override
        protected Object determineCurrentLookupKey() {
            return DsTypeHolder.get().getCode();
        }
    }
    ```
2. 编写Mybatis拦截器切换数据源
    ```java
    @Intercepts({
       @Signature(type = Executor.class, method = "update",
               args = {MappedStatement.class, Object.class}),
       @Signature(type = Executor.class, method = "query",
               args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
       @Signature(type = Executor.class, method = "query",
               args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
    })
    public class DynamicDataSourceInterceptor implements Interceptor {
        private static final String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*" ;
        @Override
        public Object intercept(Invocation invocation) throws Throwable {
            boolean transactionActive = TransactionSynchronizationManager.isActualTransactionActive();
            DsType dsType = DsType.MASTER;
            if (!transactionActive){
                Object[] args = invocation.getArgs();
                MappedStatement ms = (MappedStatement) args[0] ;
                if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)){
                    // 如果selectKey为自增id查询主键，使用主库
                    if (ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)){
                        dsType = DsType.MASTER;
                    }else {
                        BoundSql boundSql = ms.getSqlSource().getBoundSql(args[1]);
                        String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]]", "");
                        // 如果是更新操作则切换到主库
                        if (sql.matches(REGEX)){
                            dsType = DsType.MASTER;
                        }else {
                            // 非更新操作则使用从库
                            dsType = DsType.SLAVE;
                        }
                    }
                }
            }else {
                dsType = DsType.MASTER;
            }
            DsTypeHolder.set(dsType);
            return invocation.proceed();
        }
        @Override
        public Object plugin(Object target) {
            if (target instanceof Executor){
                return Plugin.wrap(target, this);
            }
            return target ;
        }
    }
    ```
3. 数据源切换工具类
    ```java
    @Getter
    public enum DsType {
        MASTER("master"),
        SLAVE("slave");
    
        private final String code ;
    
        DsType(String code){
            this.code = code ;
        }
    }
    public class DsTypeHolder {
        private static ThreadLocal<DsType> dsTypeThreadLocal = new ThreadLocal<>();
    
        public static void set(DsType dsType) {
            dsTypeThreadLocal.set(dsType);
        }
    
        public static DsType get() {
            if (dsTypeThreadLocal.get() == null){
                return DsType.MASTER ;
            }
            return dsTypeThreadLocal.get();
        }
    
        public static void clear() {
            dsTypeThreadLocal.remove();
        }
    }
    ```
4. 编写数据源配置
    ```yaml
    spring:
      application:
        name: hello-read-write-app
      jackson:
        time-zone: GMT+8
      datasource:
        master:
          driver-class-name: com.mysql.cj.jdbc.Driver
          jdbc-url: jdbc:mysql://127.0.0.1:3306/test
          username: root
          password: root
        slave:
          driver-class-name: com.mysql.cj.jdbc.Driver
          jdbc-url: jdbc:mysql://127.0.0.1:3306/test
          username: root
          password: root
    logging:
      level:
        root: info
        com.yicj.study.rw.repository.mapper: debug
    mybatis-plus:
      mapper-locations: classpath:/mapper/*Mapper.xml
    ```
5. 将数据源注入到spring容器中
    ```java
    @Configuration
    public class DataSourceConfig {
        @Bean
        @ConfigurationProperties("spring.datasource.master")
        public DataSource masterDataSource() {
            return DataSourceBuilder.create().build();
        }
    
        @Bean
        @ConfigurationProperties("spring.datasource.slave")
        public DataSource slaveDataSource() {
            return DataSourceBuilder.create().build();
        }
    
        @Bean
        public ReadWriteDataSource readWriteDataSource(
                @Qualifier("masterDataSource") DataSource masterDataSource,
                @Qualifier("slaveDataSource") DataSource slaveDataSource) {
            Map<Object, Object> targetDataSources = new HashMap<>();
            targetDataSources.put(DsType.MASTER.getCode(), masterDataSource);
            targetDataSources.put(DsType.SLAVE.getCode(), slaveDataSource);
            //
            ReadWriteDataSource readWriteDataSource = new ReadWriteDataSource();
            readWriteDataSource.setDefaultTargetDataSource(masterDataSource);
            readWriteDataSource.setTargetDataSources(targetDataSources);
            return readWriteDataSource;
        }
    }
    ```
6. Mybatis-plus配置
    ```java
    @Configuration
    public class MyBatisConfig {
    
        @Autowired
        private ReadWriteDataSource readWriteDataSource ;
    
        @Bean
        public SqlSessionFactory sqlSessionFactory() throws Exception {
            MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
            sqlSessionFactory.setDataSource(readWriteDataSource);
            MybatisConfiguration configuration = new MybatisConfiguration();
            configuration.setJdbcTypeForNull(JdbcType.NULL);
            configuration.setMapUnderscoreToCamelCase(true);
            configuration.setCacheEnabled(false);
            // 动态数据源拦截器配置
            configuration.addInterceptor(new DynamicDataSourceInterceptor());
            //如果配置多个插件,切记分页最后添加
            MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
            configuration.addInterceptor(interceptor);
            sqlSessionFactory.setConfiguration(configuration);
            return sqlSessionFactory.getObject();
        }
    
        @Bean
        public PlatformTransactionManager platformTransactionManager() {
            return new DataSourceTransactionManager(readWriteDataSource);
        }
    }
    ```