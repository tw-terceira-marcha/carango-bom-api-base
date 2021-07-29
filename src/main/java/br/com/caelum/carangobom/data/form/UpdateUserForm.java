package br.com.caelum.carangobom.data.form;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateUserForm {
    @NotBlank
    @NotNull
    @Size(min = 2, message = "Must have {min} or more characters.")
    private String name;

    @NotBlank
    @NotNull
    @Size(min = 6, message = "Must have {min} or more characters.")
    private String email;

    public UpdateUserForm(String name, String email) {
        this.name = name;
        this.email = email;
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
    public int hashCode() {
        return Objects.hash(email, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UpdateUserForm other = (UpdateUserForm) obj;
        return Objects.equals(email, other.email)
                && Objects.equals(name, other.name);
    }
}
