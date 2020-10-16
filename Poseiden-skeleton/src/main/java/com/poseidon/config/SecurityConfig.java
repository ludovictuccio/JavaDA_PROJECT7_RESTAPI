package com.poseidon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.poseidon.services.MyUserDetailsService;

/**
 * Basic Authentication is used with Spring Security.
 *
 * @author Ludovic Tuccio
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {

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
    protected void configureGlobal(final AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(myUserDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
    }

    /**
     * API REST security configuration. Used to limit user access for Admins
     * only.
     *
     * @author Ludovic Tuccio
     */
    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter
            extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/v1/user").authorizeRequests().anyRequest()
                    .hasAnyAuthority("ADMIN").and().csrf().disable().httpBasic()
                    .and().formLogin().and().sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }

    /**
     * API REST global security configuration.
     *
     * @author Ludovic Tuccio
     */
    @Configuration
    @Order(2)
    public static class ApiWebSecurityConfigurationAdapter2
            extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/v1/**").authorizeRequests().anyRequest()
                    .hasAnyAuthority("ADMIN", "USER").and().csrf().disable()
                    .httpBasic().and().formLogin().and().sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }

    /**
     * Web app security configuration.
     *
     * @author Ludovic Tuccio
     */
    @Configuration
    public static class FormLoginWebSecurityConfigurerAdapter
            extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/login", "/bidList/**", "/curvePoint/**",
                            "/rating/**", "/ruleName/**", "/trade/**")
                    .authenticated().antMatchers("/user/**")
                    .hasAnyAuthority("ADMIN").and().csrf().disable().httpBasic()
                    .and().formLogin().defaultSuccessUrl("/bidList/list").and()
                    .logout().logoutUrl("/app-logout").logoutSuccessUrl("/");
        }
    }

}
