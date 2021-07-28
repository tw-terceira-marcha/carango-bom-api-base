package br.com.caelum.carangobom.service;

import br.com.caelum.carangobom.models.User;
import br.com.caelum.carangobom.service.impl.token.TokenService;
import br.com.caelum.carangobom.service.interfaces.token.ITokenClaims;
import br.com.caelum.carangobom.service.interfaces.token.ITokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


class TokenServiceTest {
    private ITokenService tokenService;

    private SecretKey key;

    @BeforeEach
    public void setupMocks() {
        String key = "mlumVMqRjQZqCGQvhtC2Y8Pp/YjX5BSBDQWMCg/gN7M=";
        this.key = Keys.hmacShaKeyFor(key.getBytes());
        this.tokenService = new TokenService(key, "86400000");
    }


    @Test
    void generateTokenVerifyingIfTokenIsValid() {
        Long userId = 1578L;

        User user = new User();
        user.setId(userId);
        user.setEmail("Luiz");
        Authentication authentication = new TestingAuthenticationToken(user, "123456");

        String token = tokenService.generateToken(authentication);
        Long tokenUserId = Long.parseLong(
            Jwts
            .parserBuilder()
            .setSigningKey(this.key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject()
        );

        assertEquals(userId, tokenUserId);
    }


    @Test
    void parseClaimsVerifyingUserIdMatch() {
        Long userId = 1578L;

		Date today = new Date();
		Date expirationDate = new Date(today.getTime() + 86400000L);

		String token = Jwts
            .builder()
            .setIssuer("API do Carangobom")
            .setSubject(userId.toString())
            .setIssuedAt(today)
            .setExpiration(expirationDate)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

        ITokenClaims claims = tokenService.parseClaims(token).get();

        assertEquals(userId, claims.getUserId());
    }


    @Test
    void generateTokenWithInvalidKey() {
        Long userId = 1578L;

        User user = new User();
        user.setId(userId);
        user.setEmail("Luiz");
        Authentication authentication = new TestingAuthenticationToken(user, "123456");

        String token = tokenService.generateToken(authentication);
        assertThrows(
            SignatureException.class,
            () -> Long.parseLong(
                Jwts
                .parserBuilder()
                .setSigningKey("hBZ4hrf5HaOExbsTsfGw2QGGID1T3ogV/ywO3atIWyM=")
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject()
            )
        );
    }


    @Test
    void parseClaimsWithInvalidKey() {
        Long userId = 1578L;
        SecretKey key = Keys.hmacShaKeyFor(
            "hBZ4hrf5HaOExbsTsfGw2QGGID1T3ogV/ywO3atIWyM=".getBytes()
        );

		Date today = new Date();
		Date expirationDate = new Date(today.getTime() + 86400000L);

		String token = Jwts
            .builder()
            .setIssuer("API do Carangobom")
            .setSubject(userId.toString())
            .setIssuedAt(today)
            .setExpiration(expirationDate)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

        assertFalse(tokenService.parseClaims(token).isPresent());
    }
}
