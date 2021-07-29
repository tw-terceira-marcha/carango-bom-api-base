package br.com.caelum.carangobom.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.caelum.carangobom.data.DTO.FieldErrorDTO;
import br.com.caelum.carangobom.data.DTO.TokenDTO;
import br.com.caelum.carangobom.data.form.AuthenticationForm;
import br.com.caelum.carangobom.service.interfaces.IAuthService;

@RestController
public class AuthenticationController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<TokenDTO> authenticate(@Valid @RequestBody AuthenticationForm form) {
        return this.authService
                .authenticate(form)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<FieldErrorDTO> validate(MethodArgumentNotValidException exception) {
        return exception
            .getBindingResult()
            .getFieldErrors()
            .stream()
            .map(ex -> new FieldErrorDTO(ex.getField(), ex.getDefaultMessage()))
            .collect(Collectors.toList());
    }

}
