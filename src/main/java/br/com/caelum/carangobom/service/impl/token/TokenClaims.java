package br.com.caelum.carangobom.service.impl.token;

import br.com.caelum.carangobom.service.interfaces.token.ITokenClaims;
import io.jsonwebtoken.Claims;

public class TokenClaims implements ITokenClaims {

    private Claims claims;

	public TokenClaims(Claims claims) {
        this.claims = claims;
    }

    @Override
    public Long getUserId() {
		return Long.parseLong(this.claims.getSubject());
    }

}
