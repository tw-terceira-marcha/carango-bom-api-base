package br.com.caelum.carangobom.data.DTO;

import java.util.Objects;

public class UserDTO {
    private Long id;

    private String name;

    private String email;

    public UserDTO(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id)
                && name.equals(userDTO.name)
                && email.equals(userDTO.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
