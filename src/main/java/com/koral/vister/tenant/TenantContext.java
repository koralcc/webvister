package com.koral.vister.tenant;

import com.koral.vister.constants.LogConstants;
import org.slf4j.MDC;

public class TenantContext {
    private static final ThreadLocal<String> tenantThreadLocal = new ThreadLocal<>();

    public static final String getTenantCode(){
        return tenantThreadLocal.get();
    }

    public static final void setTenantCode(String tenantCode){
        MDC.put(LogConstants.KEY_TENANT_ID,tenantCode);
        tenantThreadLocal.set(tenantCode);
    }

    public static final void removeTenantCode(){
        MDC.remove(LogConstants.KEY_TENANT_ID);
        tenantThreadLocal.remove();
    }

}
