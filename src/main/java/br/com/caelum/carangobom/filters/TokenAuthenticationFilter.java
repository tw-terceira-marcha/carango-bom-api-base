package br.com.caelum.carangobom.filters;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.caelum.carangobom.repository.interfaces.UserRepository;
import br.com.caelum.carangobom.service.interfaces.token.ITokenClaims;
import br.com.caelum.carangobom.service.interfaces.token.ITokenService;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private ITokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        this.parseToken(request)
                .flatMap(tokenService::parseClaims)
                .ifPresent(this::authenticate);

        filterChain.doFilter(request, response);
    }

    private void authenticate(ITokenClaims claims) {
        Long userId = claims.getUserId();
        userRepository
            .findById(userId)
            .ifPresent(
                user -> SecurityContextHolder
                    .getContext()
                    .setAuthentication(
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())
                    )
            );
    }

    private Optional<String> parseToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders("Authorization");

        while (headers.hasMoreElements()) { // Most times there will be only one.
            String value = headers.nextElement();

            if (value.startsWith(BEARER_PREFIX)) {
                return Optional.of(
                        value.substring(BEARER_PREFIX.length()).trim());
            }
        }

        return Optional.empty();
    }

}
