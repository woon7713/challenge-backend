package com.example.challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF 비활성화
                .cors(cors -> cors.disable())  // CORS 비활성화
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll());  // 모든 요청 허용

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:3000");  // 프론트엔드 URL 허용
        config.addAllowedMethod("*");  // 모든 HTTP 메서드 허용
        config.addAllowedHeader("*");  // 모든 요청 헤더 허용
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
