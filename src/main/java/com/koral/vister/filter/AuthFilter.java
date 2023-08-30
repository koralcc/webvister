package com.koral.vister.filter;

import com.koral.vister.tenant.TenantContext;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.koral.vister.constants.CommonHeaders.*;


@Component
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        try {
            TenantContext.setTenantCode(request.getHeader(HEADER_NAME_TENANT_CODE));
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            TenantContext.removeTenantCode();
        }
    }
}
