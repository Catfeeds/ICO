package com.tongwii.ico.configurer;

import com.tongwii.ico.security.JWTAuthenticationFilter;
import com.tongwii.ico.security.JwtAuthenticationEntryPoint;
import com.tongwii.ico.util.CurrentConfigEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

/**
 * @author : Zeral
 * @date : 2017/8/3
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.profiles.active}")
    private String env;//当前激活的配置文件

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureAuthentication ( AuthenticationManagerBuilder authenticationManagerBuilder ) throws Exception {
        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider impl = new DaoAuthenticationProvider();
        impl.setUserDetailsService(this.userDetailsService);
        impl.setHideUserNotFoundExceptions(false);
        impl.setPasswordEncoder(passwordEncoder());
        return impl ;
    }

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTAuthenticationFilter authenticationTokenFilterBean () throws AuthenticationException {
        return new JWTAuthenticationFilter();
    }

    @Override
    protected void configure ( HttpSecurity httpSecurity ) throws Exception {
        // 开发者模式，放行所有
        // token将拿不到用户信息
        if (env.equals(CurrentConfigEnum.dev.toString())) {
            httpSecurity
                    .csrf()
                    .disable()
                    .cors()
                .and()
                    .sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS ).and()
                    .authorizeRequests()
                    .anyRequest().permitAll();
        } else {
            httpSecurity
                    // 基于定制JWT安全过滤器
                    .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
                    .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                    .csrf()
                    .disable()
                    .cors()
                .and()
                    // jwt不需要session , 所以不创建会话
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    // 允许授权请求
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/user/register").permitAll()
                    .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/admin/**").hasRole("ADMIN");

        }
        // 禁用页面缓存
        httpSecurity.headers().cacheControl();
    }

}