package com.itxh.xhsecurity.config;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * 虽然与 AuthenticationSuccessHandler
 * 和 AuthenticationFailureHandler 相似，
 * 但它们的好处是可以独立于servlet API来使用。
 */
@Component
public class AuthenticationEvents {
    @EventListener//监听事件
    public void onSuccess(AuthenticationSuccessEvent success) {
        // ...
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failures) {
        // ...
    }
}
