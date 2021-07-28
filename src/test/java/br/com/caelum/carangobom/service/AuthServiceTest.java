package br.com.caelum.carangobom.service;

import br.com.caelum.carangobom.data.DTO.TokenDTO;
import br.com.caelum.carangobom.data.form.AuthenticationForm;
import br.com.caelum.carangobom.repository.interfaces.UserRepository;
import br.com.caelum.carangobom.service.impl.AuthService;
import br.com.caelum.carangobom.service.interfaces.token.ITokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class AuthServiceTest {
    private AuthService authService;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private ITokenService tokenService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setupMocks() {
        openMocks(this);
        authService = new AuthService(tokenService, authManager, userRepository);
    }

    @Test
    void authenticationWithValidCredentials() {
        AuthenticationForm form = new AuthenticationForm("Luiz", "123456");
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(form.getEmail(),
                form.getPassword());
        Authentication authentication = new TestingAuthenticationToken(form.getEmail(), form.getPassword());
        when(authManager.authenticate(any())).then(args -> {
            assertEquals(credentials, args.getArguments()[0]);
            //Asserting credentials because for some unknown reasons java doesn't compare correctly on the parameter
            return authentication;
        });

        Optional<TokenDTO> tokenDTO = authService.authenticate(form);
        assertTrue(tokenDTO.isPresent());
    }

    @Test
    void authenticationWithInvalidCredentials() {
        AuthenticationForm form = new AuthenticationForm("Luiz", "123456");
        when(authManager.authenticate(any())).thenThrow(new BadCredentialsException("Authentication Failed"));

        Optional<TokenDTO> tokenDTO = authService.authenticate(form);
        assertFalse(tokenDTO.isPresent());
    }

    @Test
    void loadUserByUsernameWithValidUsername() {
        String userName = "Luiz";
        when(userRepository.findByEmail(userName)).then(args -> {
            return Optional.of(new User(userName, "marxRules", List.of()));
        });
        UserDetails user = authService.loadUserByUsername(userName);
        assertEquals(userName, user.getUsername());
    }

    @Test
    void authenticationFailureWithInvalidUsername() {
        String userName = "Luiz";
        when(userRepository.findByEmail(any())).then(args -> Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> authService.loadUserByUsername(userName)
        );
    }
}
