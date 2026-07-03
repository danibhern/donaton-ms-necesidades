package com.example.ms_necesidades.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // Deshabilitamos CSRF porque es una API REST que no maneja cookies/sesiones
                .csrf(csrf -> csrf.disable())
                
                // Deshabilitamos el formulario de login HTML que te está apareciendo
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                
                // Forzamos a que el microservicio no guarde estados de sesión
                .sessionManagement(session -> 
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                
                // Configuración de los accesos
                .authorizeHttpRequests(auth -> auth
                        // 1. Dejar públicos TODOS los endpoints de necesidades para que el BFF los consuma
                        .requestMatchers("/api/necesidades/**").permitAll()
                        
                        // Cualesquiera otros endpoints de este micro requerirán estar autenticados
                        .anyRequest().authenticated()
                )
                .build();
    }
}
