package br.com.caelum.carangobom.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import javax.validation.Valid;

@RestController
public class AuthenticationController {

    private AuthenticationManager authManager;

    private TokenService tokenService;

    @Autowired
    public AuthenticationController(AuthenticationManager authManager, TokenService tokenService) {
        this.authManager = authManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/auth")
    public ResponseEntity<TokenDto> authenticate(@Valid @RequestBody AuthenticationForm form) {
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(form.getEmail(),
                form.getPassword());
        try {
            Authentication authentication = authManager.authenticate(credentials);
            String token = tokenService.generateToken(authentication);
            return ResponseEntity.ok(new TokenDto(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}