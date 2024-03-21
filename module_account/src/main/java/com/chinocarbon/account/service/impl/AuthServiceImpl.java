package com.chinocarbon.account.service.impl;

import com.chinocarbon.account.service.AuthService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Service;


// AuthService.java
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final String ISSUER = "your-issuer";
    private static final String SUBJECT = "your-subject";
    private static final long EXPIRATION_TIME = 2000000; // 10 days in milliseconds

    public String createToken(String userId) {
        return Jwts.builder()
                .setSubject(SUBJECT)
                .setIssuer(ISSUER)
                .claim("userId", userId)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public String getUserIdFromToken(String token) throws ExpiredJwtException, SignatureException {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("userId", String.class);
    }
}
