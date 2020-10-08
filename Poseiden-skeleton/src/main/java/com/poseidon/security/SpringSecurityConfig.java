package com.poseidon.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.poseidon.services.MyUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * For manage authentication rules.
     */
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(myUserDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
    }

    /**
     * For disable security and authentication using API REST.
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/bidList/**", "/api/curvePoint/**",
                "/api/rating/**", "/api/ruleName/**", "/api/trade/**",
                "/api/user**");
    }

    /**
     * For push http requests throw security filters (views).
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/bidList/**", "/curvePoint/**", "/rating/**",
                        "/ruleName/**", "/trade/**")
                .hasAnyAuthority("ADMIN", "USER").antMatchers("/user/**")
                .authenticated().and().csrf().disable().httpBasic().and()
                .formLogin().defaultSuccessUrl("/bidList/list").and().logout()
                .logoutUrl("/app-logout").logoutSuccessUrl("/").and()
                .exceptionHandling().accessDeniedPage("/access-denied");
    }

}
