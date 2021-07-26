package br.com.caelum.carangobom.service;

import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import br.com.caelum.carangobom.models.User;
import br.com.caelum.carangobom.service.token.TokenClaims;

@Service
public class TokenService {

    private SecretKey key;

    private String expiration;

    public TokenService(
            @Value("${carangobom.jwt.secret}") String key,
            @Value("${carangobom.jwt.expiration}") String expiration) {
        this.key = Keys.hmacShaKeyFor(key.getBytes());
        this.expiration = expiration;
    }

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + Long.parseLong(this.expiration));

        return Jwts
                .builder()
                .setIssuer("API do Carangobom")
                .setSubject(user.getId().toString())
                .setIssuedAt(today)
                .setExpiration(expirationDate)
                .signWith(this.key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Optional<TokenClaims> parseClaims(String token) {
        try {
            Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(this.key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return Optional.of(new TokenClaims(claims));
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
                | SignatureException | IllegalArgumentException e) {
            return Optional.empty();
        }

    }
}
