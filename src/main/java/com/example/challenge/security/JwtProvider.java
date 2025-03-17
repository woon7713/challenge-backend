package com.example.challenge.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {
    // HS512 알고리즘에 적합한 강력한 키 생성 (512비트 이상)

    //private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512); // 비밀키는 실제 서비스에서는 환경변수나 별도 설정 파일로 안전하게 관리 필요

    private SecretKey key;

    // application.properties에 저장된 Base64 인코딩 비밀키
    @Value("${jwt.secret}")
    private String Secret;

    private final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 토큰 만료 시간(밀리초 단위)

    @PostConstruct
    public void init(){
        byte[] decodedKey = Base64.getDecoder().decode(Secret);
        this.key = Keys.hmacShaKeyFor(decodedKey);
    }

    // JWT 토큰 생성: username을 주체로 payload에 담기
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key) // 강력한 키 사용
                .compact();
    }
    // 토큰에서 Claims 추출
    private Claims getClaims(String token) {
        return Jwts.parserBuilder() // parserBuilder() 사용 (jjwt 0.11.x 버전 기준)
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰에서 subject(username)추출
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }


    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Claims claims = getClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch(Exception e) {
            return false;
        }
    }
}
