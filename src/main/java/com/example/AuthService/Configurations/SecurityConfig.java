package com.example.AuthService.Configurations;

import com.example.AuthService.Filters.JwtAuthFilter;
import com.example.AuthService.Service.JWTService;
import org.apache.catalina.webresources.JarWarResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    JwtAuthFilter jwtAuthFilter;
    SecurityConfig(JwtAuthFilter jwtAuthFilter){
       this.jwtAuthFilter=jwtAuthFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth->auth.requestMatchers("/api/v1/passenger/signup",
                        "/api/v1/passenger/signin").
                        permitAll().anyRequest().authenticated())
                .addFilterBefore(this.jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();

    }
}
