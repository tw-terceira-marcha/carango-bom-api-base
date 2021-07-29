package br.com.caelum.carangobom.service.interfaces.token;

import java.util.Optional;

import org.springframework.security.core.Authentication;

public interface ITokenService {

    public String generateToken(Authentication authentication);

    public Optional<ITokenClaims> parseClaims(String token);

}
