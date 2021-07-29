package br.com.caelum.carangobom.service.interfaces;

import java.util.List;

import br.com.caelum.carangobom.data.DTO.UserDTO;
import br.com.caelum.carangobom.data.form.CreateUserForm;
import br.com.caelum.carangobom.data.form.UpdateUserForm;
import br.com.caelum.carangobom.models.User;
import br.com.caelum.carangobom.repository.interfaces.UserRepository;

public interface IUserService
    extends IBaseService<User, Long, UserRepository, UserDTO, CreateUserForm, UpdateUserForm> {

    public List<UserDTO> getList();

}
