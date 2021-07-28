package br.com.caelum.carangobom.controllers;

import br.com.caelum.carangobom.service.interfaces.token.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import br.com.caelum.carangobom.controllers.data.AuthenticationForm;
import br.com.caelum.carangobom.controllers.data.TokenDTO;
import br.com.caelum.carangobom.service.impl.token.TokenService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import javax.validation.Valid;

@RestController
public class AuthenticationController {

    private AuthenticationManager authManager;

    private ITokenService tokenService;

    @Autowired
    public AuthenticationController(AuthenticationManager authManager, ITokenService tokenService) {
        this.authManager = authManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/auth")
    public ResponseEntity<TokenDTO> authenticate(@Valid @RequestBody AuthenticationForm form) {
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(form.getEmail(),
                form.getPassword());
        try {
            Authentication authentication = authManager.authenticate(credentials);
            String token = tokenService.generateToken(authentication);
            return ResponseEntity.ok(new TokenDTO(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
