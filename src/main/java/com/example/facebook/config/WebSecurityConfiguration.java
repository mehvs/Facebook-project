package com.example.facebook.config;

import com.example.facebook.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserServiceImpl userService;

    @Autowired
    public WebSecurityConfiguration(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override

    protected void configure(HttpSecurity http) throws Exception {
        http
                    .authorizeRequests()
                    .antMatchers("/", "/register", "/upload","/css/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin().loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/profile", true).permitAll()
                    .usernameParameter("email")
                    .passwordParameter("password")
                .and()
                    .logout().logoutSuccessUrl("/login").permitAll()
                .and()
                    .exceptionHandling().accessDeniedPage("/unauthorized")
                .and()
                    .csrf().disable()
                    .rememberMe()
                    .rememberMeParameter("remember")
                    .key("f6d042be-bdfb-11ea-b3de-0242ac130004")
                    .userDetailsService(userService)
                    .rememberMeCookieName("rememberMe")
                    .tokenValiditySeconds(86400);
    }
}
