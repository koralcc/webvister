package com.koral.vister.test;

public class TenantContext {
    private static ThreadLocal<String> tenantCodeHolder = new ThreadLocal<>();

    public static ThreadLocal<String> getTenantCodeHolder() {
        return tenantCodeHolder;
    }

    public static void setTenantCodeHolder(ThreadLocal<String> tenantCodeHolder) {
        TenantContext.tenantCodeHolder = tenantCodeHolder;
    }
}