package br.com.caelum.carangobom.service.token;

import io.jsonwebtoken.Claims;

public class TokenClaims {

    private Claims claims;

	public TokenClaims(Claims claims) {
        this.claims = claims;
    }

    public Long getUserId() {
		return Long.parseLong(this.claims.getSubject());
    }

}
