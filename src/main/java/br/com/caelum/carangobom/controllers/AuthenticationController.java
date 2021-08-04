package br.com.caelum.carangobom.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.caelum.carangobom.data.DTO.TokenDTO;
import br.com.caelum.carangobom.data.form.AuthenticationForm;
import br.com.caelum.carangobom.service.interfaces.IAuthService;

@RequestMapping("/auth")
@RestController
public class AuthenticationController implements IFormValidation {

    @Autowired
    private IAuthService authService;

    @PostMapping()
    public ResponseEntity<TokenDTO> authenticate(@Valid @RequestBody AuthenticationForm form) {
        return this.authService
                .authenticate(form)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}
