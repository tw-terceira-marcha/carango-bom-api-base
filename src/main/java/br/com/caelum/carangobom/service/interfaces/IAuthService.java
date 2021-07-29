package br.com.caelum.carangobom.service.interfaces;

import java.util.Optional;

import br.com.caelum.carangobom.data.DTO.TokenDTO;
import br.com.caelum.carangobom.data.form.AuthenticationForm;

public interface IAuthService {

    public Optional<TokenDTO> authenticate(AuthenticationForm form);

}
