package com.yicj.study.rw.datasource;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;

/**
 * @author yicj
 * @Since 2023/12/3 15:05
 */
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
