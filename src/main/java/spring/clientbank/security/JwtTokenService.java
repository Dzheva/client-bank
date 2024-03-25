package spring.clientbank.security;

import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
@Log4j2
@Service
@PropertySource("classpath:jwt.properties")
public class JwtTokenService {
    @Value("${jwt.secret}")
    private String secret;

    private static Long day = 60 * 60 * 24 * 1000L;
    private static Long week = 7 * day;

    public String generateToken(Integer userId, boolean rememberMe) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + (rememberMe ? week : day));
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private Optional<Jws<Claims>> parseTokenToClaims(String token) {
        try {
            return Optional.of(Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
            );
        } catch (JwtException ex) {
            log.error("Token is wrong: {}", ex.getMessage());
            return Optional.empty();
        }
    }

    private Optional<Integer> toIntSafe(String raw) {
        try {
            return Optional.of(Integer.parseInt(raw));
        } catch (NumberFormatException ex) {
            log.error("User Id parse Error: {}", ex.getMessage());
            return Optional.empty();
        }
    }

    public Optional<Integer> parseToken(String token) {
        return parseTokenToClaims(token)
                .map(Jwt::getBody)
                .map(Claims::getSubject)
                .flatMap(this::toIntSafe);
    }
}
