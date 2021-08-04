package br.com.caelum.carangobom.controllers.impl;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.caelum.carangobom.controllers.interfaces.IBaseController;
import br.com.caelum.carangobom.controllers.interfaces.IFormValidation;
import br.com.caelum.carangobom.data.DTO.UserDTO;
import br.com.caelum.carangobom.data.form.CreateUserForm;
import br.com.caelum.carangobom.data.form.UpdateUserForm;
import br.com.caelum.carangobom.models.User;
import br.com.caelum.carangobom.repository.interfaces.UserRepository;
import br.com.caelum.carangobom.service.interfaces.IUserService;

@RequestMapping("/users")
@RestController
public class UserController
    implements IBaseController<Long, User, UserRepository, UserDTO, CreateUserForm, UpdateUserForm, IUserService>,
    IFormValidation {

    @Autowired
    public IUserService userService;

    @Override
    public IUserService getService() {
        return this.userService;
    }

    @Override
    public URI getCreatedURI(UserDTO dto) {
        return UriComponentsBuilder
            .newInstance()
            .path("/users/{id}")
            .buildAndExpand(dto.getId())
            .toUri();
    }

}
