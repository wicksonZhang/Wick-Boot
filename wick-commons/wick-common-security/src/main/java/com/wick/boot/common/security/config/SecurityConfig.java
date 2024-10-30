package com.wick.boot.common.security.config;

import com.wick.boot.common.security.filter.TokenAuthenticationFilter;
import com.wick.boot.common.security.handler.MyAccessDeniedHandler;
import com.wick.boot.common.security.handler.MyAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * Security 全局配置
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    @Resource
    private MyAccessDeniedHandler myAccessDeniedHandler;

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // 禁用跨站请求伪造保护
                .formLogin().disable() // 禁用表单登录，适用于前后端分离

                // 配置拦截规则
                .authorizeRequests()
                .antMatchers(WHITE_URL_LIST)
                .permitAll() // 白名单允许所有访问
                .anyRequest()
                .authenticated() // 除白名单外的所有请求都需要认证

                // 禁用session管理: 采用无状态的Token认证方式
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 处理异常情况：认证失败和权限不足
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(myAuthenticationEntryPoint)
                .accessDeniedHandler(myAccessDeniedHandler)

                // Token 认证过滤器，在 UsernamePasswordAuthenticationFilter 之前添加自定义的 Token 认证过滤器
                .and()
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 白名单URL
     */
    private static final String[] WHITE_URL_LIST = {
            "/auth/captcha",
            "/auth/login",
            "/webjars/**",
            "/doc.html",
            "/swagger-resources/**",
            "/v2/api-docs/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/ws/**"
    };

    /**
     * Token 校验过滤器
     *
     * @return TokenAuthenticationFilter
     */
    @Bean
    public TokenAuthenticationFilter authenticationTokenFilterBean() {
        return new TokenAuthenticationFilter();
    }

    /**
     * 密码加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 由于 Spring Security 创建 AuthenticationManager 对象时，没声明 @Bean 注解，导致无法被注入
     * 通过覆写父类的该方法，添加 @Bean 注解，解决该问题
     */
    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
