package com.itxh.xhsecurity.config;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 持久化实现
 */
public class DelegatingSecurityContextRepository implements SecurityContextRepository {
    public DelegatingSecurityContextRepository(RequestAttributeSecurityContextRepository requestAttributeSecurityContextRepository, HttpSessionSecurityContextRepository httpSessionSecurityContextRepository) {

    }

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        return null;
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        return false;
    }
}
