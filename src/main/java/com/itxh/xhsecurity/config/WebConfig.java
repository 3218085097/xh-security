package com.itxh.xhsecurity.config;

import com.itxh.xhsecurity.handle.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebConfig {

    //    @Bean
//    public UserDetailsService userDetailsService() throws Exception {
//        // ensure the passwords are encoded properly
//        User.UserBuilder users = User.withDefaultPasswordEncoder();
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(users.username("user").password("password").roles("USER").build());
//        manager.createUser(users.username("admin").password("password").roles("USER", "ADMIN").build());
//        return manager;
//    }
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build());
        return manager;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .mvcMatcher("/api/**")
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().hasRole("ADMIN")
                );
//                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    //官网写法
    public SecurityFilterChain formLoginFilterChain(HttpSecurity http) throws Exception {
        //登录
        http
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                );
        //授权开启
        http
                .authorizeHttpRequests(authorize -> authorize
                        .mvcMatchers("/endpoint").hasRole("USER")
                        .anyRequest().authenticated()
                )
//                .formLogin(withDefaults())
                //认证成功时的处理
                .formLogin().successHandler(new AuthenticationSuccessHandlerImpl())
                .failureHandler(new AuthenticationFailureHandlerImpl());

        //注销
        http
                .logout(logout -> {
                    logout.logoutSuccessHandler(new LogoutSuccessHandlerImpl()); //注销成功时的处理
                });
        //错误处理
        http
                .exceptionHandling(exception -> {
                    exception.authenticationEntryPoint(new AuthenticationEntryPointImpl());//请求未认证的接口
                });
        //跨域
        http
                .cors(withDefaults());
        //会话管理
        http
                .sessionManagement(session -> {
                    session
                            .maximumSessions(1)//单用户登录
                            .expiredSessionStrategy(new SessionInformationExpiredStrategyImpl());
                });
        //关闭csrf攻击防御  默认开启
        http
                .csrf((csrf) -> {
                    csrf.disable();
                });

        http
                // ...
                .headers(headers -> headers.disable());

        http
                // ...
                .headers(headers -> headers
                        .cacheControl(cache -> cache.disable())
                );
        //持久化
        http
                // ...
                .securityContext((securityContext) -> securityContext
                        .securityContextRepository(new DelegatingSecurityContextRepository(
                                new RequestAttributeSecurityContextRepository(),
                                new HttpSessionSecurityContextRepository()
                        ))
                );

        return http.build();
    }
    //记住我模式实现
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, RememberMeServices rememberMeServices) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )
                .rememberMe((remember) -> remember
                        .rememberMeServices(rememberMeServices)
                );
        return http.build();
    }

    @Bean
    RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
//        RememberMeTokenAlgorithm encodingAlgorithm = RememberMeTokenAlgorithm.SHA256;
//        TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices(myKey, userDetailsService, encodingAlgorithm);
//        rememberMe.setMatchingAlgorithm(RememberMeTokenAlgorithm.MD5);
//        return rememberMe;
        return null;
    }
}

