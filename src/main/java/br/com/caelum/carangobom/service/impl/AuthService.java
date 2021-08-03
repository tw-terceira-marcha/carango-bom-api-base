package br.com.caelum.carangobom.service.impl;

import br.com.caelum.carangobom.data.DTO.TokenDTO;
import br.com.caelum.carangobom.data.form.AuthenticationForm;
import br.com.caelum.carangobom.service.interfaces.IAuthService;
import br.com.caelum.carangobom.service.interfaces.token.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    private ITokenService tokenService;

    private AuthenticationManager authManager;

    @Autowired
    public AuthService(ITokenService tokenService, AuthenticationManager authManager) {
        this.tokenService = tokenService;
        this.authManager = authManager;
    }

    @Override
    public Optional<TokenDTO> authenticate(AuthenticationForm form) {
        UsernamePasswordAuthenticationToken credentials =
                new UsernamePasswordAuthenticationToken(form.getEmail(),
                form.getPassword());

        try {
            Authentication authentication = this.authManager.authenticate(credentials);
            String token = this.tokenService.generateToken(authentication);
            return Optional.of(new TokenDTO(token));
        } catch (AuthenticationException e) {
            return Optional.empty();
        }
    }
}
