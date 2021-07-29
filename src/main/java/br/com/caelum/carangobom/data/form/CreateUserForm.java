package br.com.caelum.carangobom.data.form;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateUserForm extends UpdateUserForm {

    @NotBlank
    @NotNull
    private String password;

    public CreateUserForm(String name, String email, String password) {
        super(name, email);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(password);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        CreateUserForm other = (CreateUserForm) obj;
        return super.equals(other)
                && Objects.equals(password, other.password);
    }

}
