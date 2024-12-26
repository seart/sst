package org.example.latestspringsecurity.controller;

import org.example.latestspringsecurity.handler.SecurityAuthenticationFailureHandler;
import org.example.latestspringsecurity.handler.SecurityAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

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
//                .logout(logout -> logout.logoutSuccessHandler(new SecurityLogoutSuccessHandler()))
//                .exceptionHandling(exception ->
                        // 用spring security 自带的登录页，要屏蔽下面配置
                       // 如果开启配置，需要前端做跳转处理，跳转到登录页才行，否则会一直返回 SecurityAuthenticationEntryPoint json 结构体数据
//                      exception.authenticationEntryPoint(new SecurityAuthenticationEntryPoint())
//                {})
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        PasswordEncoder encoder = passwordEncoder();
        String encoderPassword = encoder.encode("123");
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin").password(encoderPassword).roles("ADMIN", "USER").build());
        manager.createUser(User.withUsername("user").password(encoderPassword).roles("USER").build());
        return manager;
    }

    /**
     * 加密之后的密码进行匹配
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * 明文密码进行匹配，如果用这个，password可以直接写123
     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }

}