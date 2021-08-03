package br.com.caelum.carangobom.service;

import br.com.caelum.carangobom.data.DTO.UserDTO;
import br.com.caelum.carangobom.data.form.CreateUserForm;
import br.com.caelum.carangobom.data.form.UpdateUserForm;
import br.com.caelum.carangobom.models.User;
import br.com.caelum.carangobom.repository.interfaces.UserRepository;
import br.com.caelum.carangobom.service.impl.UserService;
import br.com.caelum.carangobom.service.interfaces.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class UserServiceTest {

    private IUserService userService;

    private UserDetailsService userDetailsService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setupMocks() {
        openMocks(this);

        UserService userService = new UserService(userRepository, passwordEncoder);
        this.userService = userService;
        this.userDetailsService = userService;
    }

    @Test
    void entityToDTO() {
        User user = new User("Karl", "marx@gnu.org", "msilatipacyortsed");

        UserDTO dto = userService.entityToDTO(user);

        assertEquals(dto.getName(), user.getName());
    }

    @Test
    void formToEntity() {
        String password = "msilatipacyortsed";
        CreateUserForm form = new CreateUserForm("Karl", "marx@gnu.org", "msilatipacyortsed");

        User user = userService.formToEntity(form);

        assertNull(user.getId());
        assertNotEquals(user.getPassword(), password); // Password must have been encrypted.
        assertEquals(user.getPassword(), form.getPassword());
        assertEquals(user.getEmail(), form.getEmail());
        assertEquals(user.getName(), form.getName());
    }

    @Test
    void updateEntity() {
        String password = "msilatipacyortsed";
        User user = new User("Luiz", "luiz@gnu.org", password);
        UpdateUserForm form = new UpdateUserForm("Luiz Inácio", "lula@gnu.org");

        userService.updateEntity(user, form);

        assertNull(user.getId());
        assertEquals(user.getPassword(), password);
        assertEquals(user.getEmail(), form.getEmail());
        assertEquals(user.getName(), form.getName());
    }

    @Test
    void getUsers() {
        List<User> users = List.of(
                new User(1L, "Luiz", "luiz@email.com", "marxRules"),
                new User(2L, "Luizinho", "luizinho@email.com", "marxRules"),
                new User(3L, "Luizao", "luizao@email.com", "marxRules"));

        when(userRepository.findAllByOrderByEmail())
                .thenReturn(users);

        List<UserDTO> list = userService.getList();
        assertEquals(
                users
                        .stream()
                        .map(userService::entityToDTO)
                        .collect(Collectors.toList()),
                list);
    }

    @Test
    void loadUserByUsernameWithValidUsername() {
        String name = "Luiz";
        String email = "luiz@gmail.com";
        String password = "msilatipacyortsed";
        when(userRepository.findByEmail(email))
                .then(args -> Optional.of(new User(name, email, password)));
        UserDetails user = userDetailsService.loadUserByUsername(email);
        assertEquals(email, user.getUsername());
    }

    @Test
    void authenticationFailureWithInvalidUsername() {
        String userName = "Luiz";
        when(userRepository.findByEmail(any())).then(args -> Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(userName));
    }
}
