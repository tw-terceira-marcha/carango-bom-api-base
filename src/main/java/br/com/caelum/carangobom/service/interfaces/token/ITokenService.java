package br.com.caelum.carangobom.service.interfaces.token;

import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface ITokenService {

    public String generateToken(Authentication authentication);

    public Optional<ITokenClaims> parseClaims(String token);

}
