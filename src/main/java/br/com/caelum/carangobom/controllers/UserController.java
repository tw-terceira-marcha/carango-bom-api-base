package br.com.caelum.carangobom.controllers;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.caelum.carangobom.data.DTO.UserDTO;
import br.com.caelum.carangobom.data.form.CreateUserForm;
import br.com.caelum.carangobom.data.form.UpdateUserForm;
import br.com.caelum.carangobom.service.interfaces.IUserService;

@RequestMapping("/users")
@RestController
public class UserController implements IFormValidation {

    @Autowired
    public IUserService userService;

    @GetMapping()
    public List<UserDTO> list() {
        return this.userService.getList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> id(@PathVariable Long id) {
        return this.userService
                .getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<UserDTO> create(
            @Valid @RequestBody CreateUserForm form,
            UriComponentsBuilder uriBuilder) {
        UserDTO user = this.userService.create(form);
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<UserDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserForm form) {
        return this.userService
                .update(id, form)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> delete(@PathVariable Long id) {
        return this.userService
                .delete(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
