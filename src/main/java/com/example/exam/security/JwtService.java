package com.example.exam.security;

import com.example.exam.service.enums.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
@Service
public class JwtService {
    private final SecretKey signingKey;
    private final int accessTokenMinutes;
    private final int refreshTokenDays;


    public JwtService(
            @Value("${app.jwt.secret}") String base64Secret,
            @Value("${app.jwt.access-token-expiration-minutes}") int accessTokenMinutes,
            @Value("${app.jwt.refresh-token-expiration-days}") int refreshTokenDays
    ) {
        this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret));
        this.accessTokenMinutes = accessTokenMinutes;
        this.refreshTokenDays = refreshTokenDays;
    }
    public String generateToken(String userName, List<String> roles, TokenType type){
        Instant now = Instant.now();
        Instant exp = (type == TokenType.ACCESS)
                ? now.plus(accessTokenMinutes, ChronoUnit.MINUTES)
                : now.plus(refreshTokenDays, ChronoUnit.DAYS);
        Map<String,Object> claims=new HashMap<>();
        claims.put("typ",type.name());
        if (type ==TokenType.ACCESS){
            claims.put("roles",roles);
        }
        return Jwts.builder()
                .subject(userName)
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .claims(claims)
                .signWith(signingKey)
                .compact();

    }
    public Jws<Claims> parse(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token);
    }
    public String extractUsername(String token){
        return parse(token).getPayload().getSubject();
    }
    public boolean isExpired(String token){
        Date expiration = parse(token).getPayload().getExpiration();
        return expiration.before(new Date());
    }
    public TokenType extractTokenType(String token){
        String typ = Objects.toString(parse(token).getPayload().get("typ"), "");
        return TokenType.valueOf(typ);
    }

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token){
        Object roles = parse(token).getPayload().get("roles");
        if (roles instanceof List<?> list){
            List<String> out =new ArrayList<>();
            for (Object o :list){
                out.add(String.valueOf(o));
            }
            return out;
        }
        return List.of();
    }
}
