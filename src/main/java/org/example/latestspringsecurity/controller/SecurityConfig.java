package org.example.latestspringsecurity.controller;

import org.example.latestspringsecurity.handler.SecurityAuthenticationFailureHandler;
import org.example.latestspringsecurity.handler.SecurityAuthenticationSuccessHandler;
import org.example.latestspringsecurity.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private MyUserDetailsService userDetailsService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/js/**", "/css/**", "/images/**").permitAll()
                        .requestMatchers("/visitor/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("USER","ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.successHandler(new SecurityAuthenticationSuccessHandler())
                        .failureHandler(new SecurityAuthenticationFailureHandler())
                )
                .exceptionHandling(exception ->
                        // 用spring security 自带的登录页，要屏蔽下面配置
                       // 如果开启配置，需要前端做跳转处理，跳转到登录页才行，否则会一直返回 SecurityAuthenticationEntryPoint json 结构体数据
//                      exception.authenticationEntryPoint(new SecurityAuthenticationEntryPoint())
                {})
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        // 这里为了方便样式，不使用任何密码加密器
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * 加密之后的密码进行匹配
     * @return
     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }

    /**
     * 明文密码进行匹配，测试临时用一下可以，生产线上绝对不行
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}