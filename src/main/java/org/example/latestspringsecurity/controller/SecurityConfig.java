package org.example.latestspringsecurity.controller;

import org.example.latestspringsecurity.handler.SecurityAuthenticationEntryPoint;
import org.example.latestspringsecurity.handler.SecurityAuthenticationFailureHandler;
import org.example.latestspringsecurity.handler.SecurityAuthenticationSuccessHandler;
import org.example.latestspringsecurity.handler.SecurityLogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/js/**", "/css/**", "/images/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.successHandler(new SecurityAuthenticationSuccessHandler())
                        .failureHandler(new SecurityAuthenticationFailureHandler())
                )
                .logout(logout -> logout.logoutSuccessHandler(new SecurityLogoutSuccessHandler()))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(new SecurityAuthenticationEntryPoint()))
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}