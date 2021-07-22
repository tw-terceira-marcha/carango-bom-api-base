package br.com.caelum.carangobom.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import br.com.caelum.carangobom.controllers.data.AuthenticationForm;
import br.com.caelum.carangobom.controllers.data.TokenDTO;
import br.com.caelum.carangobom.service.TokenService;

import org.springframework.security.authentication.TestingAuthenticationToken;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class AuthenticationControllerTest {
    private AuthenticationController authenticationController;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private TokenService tokenService;

    @BeforeEach
    public void setupMocks() {
        openMocks(this);
        authenticationController = new AuthenticationController(authManager, tokenService);
    }

    @Test
    void authenticationSucess() {
        AuthenticationForm form = new AuthenticationForm("Luiz", "123456");
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(form.getEmail(),
                form.getPassword());
        Authentication authentication = new TestingAuthenticationToken(form.getEmail(), form.getPassword());
        when(authManager.authenticate(any())).then(args -> {
            assertEquals(credentials, args.getArguments()[0]);
            return authentication;
        });

        ResponseEntity<TokenDTO> response = authenticationController.authenticate(form);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void authenticationFailure() {
        AuthenticationForm form = new AuthenticationForm("Luiz", "123456");
        when(authManager.authenticate(any())).thenThrow(new BadCredentialsException("Authentication Failed"));

        ResponseEntity<TokenDTO> response = authenticationController.authenticate(form);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
