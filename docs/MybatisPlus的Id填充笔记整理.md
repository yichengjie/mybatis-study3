#### 启动流程
1. MybatisPlusAutoConfiguration#sqlSessionFactory
2. 从Spring容器获取 IdentifierGenerator 对象放入到 GlobalConfig对象中
3. 设置 GlobalConfig 到 MybatisSqlSessionFactoryBean
#### 自动填充Id执行流程
1. SimpleExecutor#doUpdate -> configuration.newStatementHandler(this, ms, parameter, RowBounds.DEFAULT, null, null);
2. new RoutingStatementHandler(executor, mappedStatement, parameterObject, rowBounds, resultHandler, boundSql)
3. new PreparedStatementHandler(executor, ms, parameter, rowBounds, resultHandler, boundSql)
4. configuration.newParameterHandler(mappedStatement, parameterObject, boundSql)
5. MybatisXMLLanguageDriver#createParameterHandler
6. new MybatisParameterHandler(mappedStatement, parameterObject, boundSql) -> processParameter(parameter)
7. MybatisParameterHandler#process(parameter) -> populateKeys(tableInfo, metaObject, entity)
#### populateKeys业务逻辑详解（以ASSIGN_ID为例）
1. 根据实体信息获取IdType类型枚举值
2. 获取GlobalConfig对象: GlobalConfigUtils.getGlobalConfig(this.configuration)
3. 从GlobalConfig中获取IdentifierGenerator#getIdentifierGenerator
4. 从输入Entity对象获取id值，如果为空，且IdType枚举值是否为 ASSIGN_ID 或 ASSIGN_UUID
5. 调用identifierGenerator.nextId(entity)获取id
6. 将上一步获取的id值赋值给待insert的实体对象