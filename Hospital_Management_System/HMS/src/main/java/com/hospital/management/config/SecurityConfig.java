package com.hospital.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> {
                auth
                    .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/api/auth/**")).permitAll()

                    // Admin-only endpoints
                    .requestMatchers(new AntPathRequestMatcher("/api/departments/**")).hasRole("ADMIN")
                    .requestMatchers(new AntPathRequestMatcher("/api/staff/**")).hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/doctors/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/doctors/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/doctors/**").hasRole("ADMIN")

                    // User endpoints
                    .requestMatchers(HttpMethod.GET, "/api/doctors/**").hasAnyRole("ADMIN", "USER")
                    .requestMatchers(HttpMethod.GET, "/api/patients/**").hasAnyRole("ADMIN", "USER")
                    .requestMatchers(new AntPathRequestMatcher("/api/appointments/**")).hasAnyRole("ADMIN", "USER")

                    .anyRequest().authenticated();
            })
            .headers(headers ->
                headers.frameOptions(frameOptions ->
                    frameOptions.sameOrigin()
                )
            )
            .formLogin(form -> {
                form
                    .loginProcessingUrl("/api/auth/login")
                    .successHandler((request, response, authentication) -> {
                        response.setContentType("application/json");
                        response.getWriter().write("{\"success\":true,\"message\":\"Login successful\"}");
                        response.setStatus(200);
                    })
                    .failureHandler((request, response, exception) -> {
                        response.setContentType("application/json");
                        response.getWriter().write("{\"success\":false,\"message\":\"Login failed\"}");
                        response.setStatus(401);
                    })
                    .permitAll();
            })
            .httpBasic(Customizer.withDefaults())
            .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        UserDetails admin = User.builder()
            .username("admin")
            .password(bCryptPasswordEncoder.encode("admin123"))
            .roles("ADMIN")
            .build();

        UserDetails user = User.builder()
            .username("user")
            .password(bCryptPasswordEncoder.encode("user123"))
            .roles("USER")
            .build();

        manager.createUser(admin);
        manager.createUser(user);

        return manager;
    }
}