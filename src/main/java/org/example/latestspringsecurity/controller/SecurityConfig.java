package org.example.latestspringsecurity.controller;

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
                .formLogin(form -> form.loginPage("/login.html").permitAll()
                        //指登录成功后，是否始终跳转到登录成功url。它默认为false
                        .defaultSuccessUrl("/index.html", true)
                        //post登录接口，登录验证由系统实现
                        .loginProcessingUrl("/login")
                        .failureUrl("/error.html")
                        .usernameParameter("username")
                        .passwordParameter("password")
                )
                // logout 暂时没用上
//                .logout(logout -> logout.logoutUrl("/logout.html")
//                        .permitAll()
//                )
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}