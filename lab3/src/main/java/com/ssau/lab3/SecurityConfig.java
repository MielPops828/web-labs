package com.ssau.lab3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.ssau.lab3.service.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/projects/*/tasks/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/projects").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/projects/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/projects/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/projects", "/projects/**").authenticated()

                        .requestMatchers("/projects/*/tasks/**").authenticated()

                        .anyRequest().denyAll()
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/projects")
                        .failureHandler(authenticationFailureHandler())
                        .permitAll())
                .csrf(csrf -> csrf.disable())
                .logout(logout -> logout
                        .permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            String errorMessage;
            
            if (exception instanceof BadCredentialsException) {
                errorMessage = "Неверное имя пользователя или пароль";
            } else if (exception instanceof UsernameNotFoundException) {
                errorMessage = "Пользователь не найден";
            } else {
                errorMessage = "Ошибка аутентификации";
            }
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write(errorMessage);
        };
    }
}