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

import br.com.caelum.carangobom.models.User;
import br.com.caelum.carangobom.repository.UserRepository;
import br.com.caelum.carangobom.service.TokenService;
import br.com.caelum.carangobom.service.token.TokenClaims;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        Optional<String> token = this.parseToken(request);
        Optional<TokenClaims> claims = token.flatMap(tokenService::parseClaims);
        if (claims.isPresent()) {
            this.authenticate(claims.get());
        }

        filterChain.doFilter(request, response);
    }

    private void authenticate(TokenClaims claims) throws ServletException {
        Long userId = claims.getUserId();
        Optional<User> userResult = userRepository.findById(userId);

        if (userResult.isPresent()) {
            User user = userResult.get();

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
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
