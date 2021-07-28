package br.com.caelum.carangobom.service.interfaces;

import br.com.caelum.carangobom.data.DTO.TokenDTO;
import br.com.caelum.carangobom.data.form.AuthenticationForm;

import java.util.Optional;

public interface IAuthService {
    public Optional<TokenDTO> authenticate(AuthenticationForm form);
}
