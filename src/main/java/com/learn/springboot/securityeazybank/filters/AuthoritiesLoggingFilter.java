package com.learn.springboot.securityeazybank.filters;

import jakarta.servlet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthoritiesLoggingFilter implements Filter {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            LOGGER.info("User {} is successfully authenticated and has authorities {}",
                    authentication.getName() , authentication.getAuthorities().toString());
        }
        chain.doFilter(request, response);
    }
}
