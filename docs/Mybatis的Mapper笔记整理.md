#### Mapper执行流程(以update操作为例)
1. MybatisMapperProxy.invoke
2. MybatisMapperMethod#execute(sqlSession, args)
3. SqlSessionTemplate#update(command.getName(), param)
4. SqlSessionTemplate.invoke
#### SqlSessionTemplate#invoke 详情
1. SqlSessionUtils#getSqlSession获取DefaultSqlSession对象
2. DefaultSqlSession#update(statement, parameter)
3. Executor.update(MappedStatement ms, Object parameter)
#### 其他(只更新有值的字段)
1. DynamicSqlSource#getBoundSql(parameterObject)
2. MixedSqlNode#apply
3. SetSqlNode#apply -> TrimSqlNode.apply
4. FilteredDynamicContext#applyAll