package br.com.caelum.carangobom.controllers;

import br.com.caelum.carangobom.data.DTO.TokenDTO;
import br.com.caelum.carangobom.data.form.AuthenticationForm;
import br.com.caelum.carangobom.service.interfaces.IAuthService;
import br.com.caelum.carangobom.service.interfaces.token.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthenticationController {


    private IAuthService authService;

    private ITokenService tokenService;

    @Autowired
    public AuthenticationController(ITokenService tokenService, IAuthService authService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @PostMapping("/auth")
    public ResponseEntity<TokenDTO> authenticate(@Valid @RequestBody AuthenticationForm form) {
        return this.authService
                .authenticate(form)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
