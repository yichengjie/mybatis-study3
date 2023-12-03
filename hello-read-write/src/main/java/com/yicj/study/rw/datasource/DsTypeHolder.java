package com.yicj.study.rw.datasource;

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