package ru.dimangan.bankingservice.services;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.dimangan.bankingservice.domain.models.User;

import javax.crypto.SecretKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {
    @Value("${token.signing.key}")
    private String jwtSigningKey;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> payload = new HashMap<>();
        if (userDetails instanceof User customUserDetails) {
            payload.put("id", customUserDetails.getId());
            payload.put("login", customUserDetails.getUsername());
            payload.put("password", customUserDetails.getPassword());
            payload.put("name", customUserDetails.getName());
            payload.put("birthday", customUserDetails.getBirthday());
        }
        log.info("Generating token for user {}", userDetails.getUsername());
        return generateToken(payload, userDetails);
    }

    private String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        log.info("Generated token for user {}", userDetails.getUsername());
        return Jwts.builder().claims(claims).subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(null)//new Date(System.currentTimeMillis() + 100000 * 60 * 24))
                .signWith(getSigningKey(), Jwts.SIG.HS256).compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        log.info("Generating signing key: {}", jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        log.info("Extract all claims from token {}", token);
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        log.info("Extracted claims: {}", claims);
        return claimsResolvers.apply(claims);
    }

    public String extractUsername(String token) {
        log.info("Extract username from token {}", token);
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        log.info("Extract expiration date from token {}", token);
        return extractClaim(token, Claims::getExpiration);
    }

//    private boolean isTokenExpired(String token) {
//        log.info("Checking is token expired fo token {}", token);
//        return extractExpiration(token).before(new Date());
//    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        log.info("Checking is token valid for user {}", userName);
        return (userName.equals(userDetails.getUsername()));// && !isTokenExpired(token);
    }



}
