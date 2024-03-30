package com.learn.springboot.securityeazybank.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.learn.springboot.securityeazybank.filters.AuthoritiesLoggingFilter;
import com.learn.springboot.securityeazybank.filters.JwtTokenFilter;

@Configuration
public class ProjectSecurityConfig {

    @Autowired
    JwtTokenFilter jwtTokenFilter;

    @Autowired
    AuthoritiesLoggingFilter authoritiesLoggingFilter;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(
        		request -> request
        		.requestMatchers("/contact","/notices", "/register", "/login", "/swagger-ui/index.html")
        		.permitAll()
        		.anyRequest()
        		.authenticated()
        );
        httpSecurity.cors(AbstractHttpConfigurer::disable)
                        .csrf(AbstractHttpConfigurer::disable)
                        .sessionManagement(Customizer -> Customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterAfter(authoritiesLoggingFilter, BasicAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
