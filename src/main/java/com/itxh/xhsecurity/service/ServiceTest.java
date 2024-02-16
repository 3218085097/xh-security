package com.itxh.xhsecurity.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

/**
 * 当方法安全处于激活状态时，你可以使用 @PostAuthorize 注解方法
 */
@Component
public class ServiceTest {
    @PreAuthorize("hasRole('ADMIN')")
    public void readAccount(Long id) {
        // ... is only invoked if the `Authentication` has the `ROLE_ADMIN` authority
    }
}
