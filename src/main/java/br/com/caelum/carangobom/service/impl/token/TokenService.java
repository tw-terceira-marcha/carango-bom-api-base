package br.com.caelum.carangobom.service.impl.token;

import br.com.caelum.carangobom.models.User;
import br.com.caelum.carangobom.service.interfaces.token.ITokenClaims;
import br.com.caelum.carangobom.service.interfaces.token.ITokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@Service
public class TokenService implements ITokenService {

    private SecretKey key;

    private String expiration;

    public TokenService(
            @Value("${carangobom.jwt.secret}") String key,
            @Value("${carangobom.jwt.expiration}") String expiration) {
        this.key = Keys.hmacShaKeyFor(key.getBytes());
        this.expiration = expiration;
    }

    @Override
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

    @Override
    public Optional<ITokenClaims> parseClaims(String token) {
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
