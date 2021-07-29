package br.com.caelum.carangobom.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import br.com.caelum.carangobom.data.DTO.TokenDTO;
import br.com.caelum.carangobom.data.form.AuthenticationForm;
import br.com.caelum.carangobom.service.impl.AuthService;
import br.com.caelum.carangobom.service.interfaces.token.ITokenService;

class AuthServiceTest {
    private AuthService authService;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private ITokenService tokenService;

    @BeforeEach
    public void setupMocks() {
        openMocks(this);
        authService = new AuthService(tokenService, authManager);
    }

    @Test
    void authenticationWithValidCredentials() {
        AuthenticationForm form = new AuthenticationForm("Luiz", "123456");
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                form.getEmail(),
                form.getPassword());
        Authentication authentication = new TestingAuthenticationToken(form.getEmail(),
                form.getPassword());
        when(authManager.authenticate(any()))
                .then(invocation -> {
                    // Asserting credentials because for some unknown reasons java doesn't compare
                    // correctly on the parameter.
                    assertEquals(credentials, invocation.getArgument(0));
                    return authentication;
                });

        Optional<TokenDTO> tokenDTO = authService.authenticate(form);
        assertTrue(tokenDTO.isPresent());
    }

    @Test
    void authenticationWithInvalidCredentials() {
        AuthenticationForm form = new AuthenticationForm("Luiz", "123456");
        when(authManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Authentication Failed"));

        Optional<TokenDTO> tokenDTO = authService.authenticate(form);
        assertFalse(tokenDTO.isPresent());
    }
}
