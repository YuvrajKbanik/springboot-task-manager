package com.codewithraj.store.config;

import com.codewithraj.store.service.CustomUserDetailsService;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService){

        this.customUserDetailsService=customUserDetailsService;

    }

    @Bean
    PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();

    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception{

        return configuration.getAuthenticationManager();

    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception{

        http

                .csrf(csrf->csrf.disable())

                .authorizeHttpRequests(auth->auth

                        .requestMatchers(
                                "/login",
                                "/register",
                                "/css/**"
                        ).permitAll()

                        .anyRequest()

                        .authenticated())

                .formLogin(form->form

                        .loginPage("/login")

                        .defaultSuccessUrl("/tasks",true)

                        .permitAll())

                .logout(logout->logout

                        .logoutSuccessUrl("/login?logout")

                        .permitAll());

        return http.build();

    }

}