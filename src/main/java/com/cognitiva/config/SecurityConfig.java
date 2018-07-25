package com.cognitiva.config;

import com.cognitiva.service.MyUserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/clients/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/campaign/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/login/**").permitAll()
                .and()
                .formLogin().loginPage("/login/loginPage")
                .defaultSuccessUrl("/")
                .failureUrl("/login/loginPage?error")
                .usernameParameter("username").passwordParameter("password")
                .and()
                .logout().logoutSuccessUrl("/login/loginPage?logout");
        http.csrf().disable();

    }

    @Override
    public void configure(AuthenticationManagerBuilder builder)
            throws Exception {
        builder.userDetailsService(new MyUserDetailsService());
    }

}
