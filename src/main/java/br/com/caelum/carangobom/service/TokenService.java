package br.com.caelum.carangobom.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import br.com.caelum.carangobom.models.User;

@Service
public class TokenService {

	@Value("${carangobom.jwt.expiration}")
	private String expiration;

	public String generateToken(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		Date today = new Date();
		Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));
		SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

		return Jwts
            .builder()
            .setIssuer("API do Fórum da Alura")
            .setSubject(user.getId().toString())
            .setIssuedAt(today)
            .setExpiration(expirationDate)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
	}

}
