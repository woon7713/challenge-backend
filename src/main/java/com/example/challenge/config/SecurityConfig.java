package com.example.challenge.config;

import com.example.challenge.entity.Member;
import com.example.challenge.repository.MemberRepository;
import com.example.challenge.security.JwtAuthenticationFilter;
import com.example.challenge.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    public SecurityConfig(JwtUtil jwtUtil, MemberRepository memberRepository) {
        this.jwtUtil = jwtUtil;
        this.memberRepository = memberRepository;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .cors(cors -> cors.disable()) // CORS 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/members/login", "/api/members/register").permitAll()
                        .anyRequest().authenticated()  // 모든 요청 허용
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, memberRepository),
                        UsernamePasswordAuthenticationFilter.class);

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

    // BCryptPasswordEncoder Bean 추가
    @Bean
    public PasswordEncoder psswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
