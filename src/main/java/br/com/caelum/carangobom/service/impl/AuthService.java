package br.com.caelum.carangobom.service.impl;

import br.com.caelum.carangobom.data.DTO.TokenDTO;
import br.com.caelum.carangobom.data.form.AuthenticationForm;
import br.com.caelum.carangobom.models.User;
import br.com.caelum.carangobom.repository.interfaces.UserRepository;
import br.com.caelum.carangobom.service.interfaces.IAuthService;
import br.com.caelum.carangobom.service.interfaces.token.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService, IAuthService {

	private ITokenService tokenService;

	private AuthenticationManager authManager;

	private UserRepository repository;

	@Autowired
	public AuthService(ITokenService tokenService, AuthenticationManager authManager, UserRepository repository) {
		this.tokenService = tokenService;
		this.authManager = authManager;
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = repository.findByEmail(username);
		if (user.isPresent()) {
			return user.get();
		}

		throw new UsernameNotFoundException("User not found");
	}

	@Override
	public Optional<TokenDTO> authenticate(AuthenticationForm form) {
		UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(form.getEmail(),
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
