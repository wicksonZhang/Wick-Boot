package cn.wickson.security.system.security.config;

import cn.wickson.security.system.filter.TokenAuthenticationFilter;
import cn.wickson.security.system.security.handler.EntryPointUnauthorizedHandler;
import cn.wickson.security.system.security.handler.MyAccessDeniedHandler;
import cn.wickson.security.system.security.service.SecurityUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Resource
    private EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;

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
                .authenticationEntryPoint(entryPointUnauthorizedHandler)
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
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
    };

    /**
     * 自定义 Token 校验过滤器
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
     * 配置认证管理器
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
