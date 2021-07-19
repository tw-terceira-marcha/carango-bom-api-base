package br.com.caelum.carangobom.validacao;

import java.util.List;

public class FieldErrorsDTO {

    private List<FieldErrorDTO> errors;

    public int length() {
        return errors.size();
    }

    public List<FieldErrorDTO> getErrors() {
        return errors;
    }

    public void setErrors(List<FieldErrorDTO> errors) {
        this.errors = errors;
    }
}
