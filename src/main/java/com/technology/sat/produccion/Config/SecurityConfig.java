package com.technology.sat.produccion.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        http.headers(headersConfigurer -> headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/incidencia", "/register", "/ia", "/signin" ,"/css/**", "/images/**").permitAll()
                .requestMatchers("/soporte/**").hasRole("SOPORTE")
                .requestMatchers("/cliente/**").hasRole("USUARIO")
                .requestMatchers("/tickets/**").hasAnyRole("ADMINISTRADOR", "SOPORTE", "USUARIO")
                .requestMatchers("/admin/**").hasRole("ADMINISTRADOR")
                .anyRequest().authenticated())
            .formLogin(formLogin -> formLogin
                .loginPage("/signin")
                .loginProcessingUrl("/login")
                .successHandler(customAuthenticationSuccessHandler()) 
                .failureUrl("/signin?error")
                .permitAll())
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/signin")
                .permitAll());
        http.exceptionHandling(exceptions -> exceptions.accessDeniedPage("/accessError"));
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            var authorities = authentication.getAuthorities();
            String redirectURL = request.getContextPath();

            if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMINISTRADOR"))) {
                redirectURL = "/admin/usuarios";  
            } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_SOPORTE"))) {
                redirectURL = "/soporte/tickets";
            } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_USUARIO"))) {
                redirectURL = "/cliente/tickets/listar";
            }

            response.sendRedirect(redirectURL);
        };
    }
}
