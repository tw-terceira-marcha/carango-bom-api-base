package br.com.caelum.carangobom.data.form;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BrandForm {
    @NotBlank
    @NotNull
    @Size(min = 2, message = "Must have {min} or more characters.")
    private String name;

    public BrandForm(String name) {
        this.name = name;
    }

    public BrandForm() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BrandForm other = (BrandForm) obj;
        return Objects.equals(name, other.name);
    }
}
