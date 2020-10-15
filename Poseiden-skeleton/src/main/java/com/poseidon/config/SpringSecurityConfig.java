package com.poseidon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
    protected void configure(final AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(myUserDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
    }

    /**
     * For push http requests throw security filters (views).
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/bidList/**", "/curvePoint/**", "/rating/**",
                        "/ruleName/**", "/trade/**", "/api/bidList",
                        "/api/curvePoint", "/api/rating", "/api/ruleName",
                        "/api/trade")
                .authenticated().antMatchers("/user/**", "/api/user")
                .hasAnyAuthority("ADMIN").and().csrf().disable().httpBasic()
                .and().formLogin().defaultSuccessUrl("/bidList/list").and()
                .logout().logoutUrl("/app-logout").logoutSuccessUrl("/");
    }

}
