package br.com.caelum.carangobom.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.caelum.carangobom.data.DTO.UserDTO;
import br.com.caelum.carangobom.data.form.CreateUserForm;
import br.com.caelum.carangobom.data.form.UpdateUserForm;
import br.com.caelum.carangobom.models.User;
import br.com.caelum.carangobom.repository.interfaces.UserRepository;
import br.com.caelum.carangobom.service.interfaces.IUserService;

@Service
public class UserService implements UserDetailsService, IUserService {

    private UserRepository repository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.repository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

    @Override
    public List<UserDTO> getList() {
        return this.repository
                .findAllByOrderByEmail()
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO entityToDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }

    @Override
    public User formToEntity(CreateUserForm userForm) {
        userForm.setPassword(this.passwordEncoder.encode(userForm.getPassword()));
        return new User(userForm.getName(), userForm.getEmail(), userForm.getPassword());
    }

    @Override
    public void updateEntity(User user, UpdateUserForm form) {
        user.setEmail(form.getEmail());
        user.setName(form.getName());
    }

    @Override
    public UserRepository getRepository() {
        return this.repository;
    }

    @Override
    public boolean entityExists(CreateUserForm form) {
        return this.repository.existsByEmail(form.getEmail());
    }

}
