package com.joaoalexandre.spring_security_jwt.security;

import jakarta.servlet.DispatcherType;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    private static final String[] SWAGGER_WHITELIST = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

    protected void configure(HttpSecurity http) throws Exception {
        HeadersConfigurer<HttpSecurity> securityHeadersConfigurer;
        securityHeadersConfigurer = http.headers().frameOptions().disable();
        http.cors().and().csrf().disable()
                .addFilterAfter(new JWTFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .dispatcherTypeMatchers().permitAll()
                .dispatcherTypeMatchers(HttpMethod.valueOf("/h2-console/**")).permitAll()
                .dispatcherTypeMatchers(HttpMethod.POST, DispatcherType.valueOf("/login")).permitAll()
                .dispatcherTypeMatchers(HttpMethod.POST, DispatcherType.valueOf("/users")).permitAll()
                .dispatcherTypeMatchers(HttpMethod.GET, DispatcherType.valueOf("/users")).hasAnyRole("USERS","MANAGERS")
                .dispatcherTypeMatchers(HttpMethod.valueOf("/managers")).hasAnyRole("MANAGERS")
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
    @Bean //HABILITANDO ACESSAR O H2-DATABSE NA WEB
    public ServletRegistrationBean h2servletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.addUrlMappings("/h2-console/*");
        return registrationBean;
    }
}