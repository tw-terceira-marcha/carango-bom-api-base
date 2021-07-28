package br.com.caelum.carangobom.data.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class BrandForm {
    @NotBlank
    @Size(min = 2, message = "Must have {min} or more characters.")
    public String name;

    public BrandForm(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
