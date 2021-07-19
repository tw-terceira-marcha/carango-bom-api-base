package br.com.caelum.carangobom.validation;

public class FieldErrorDTO {

    private String field;
    private String message;

    public String getField() {
        return field;
    }

    public void setField(String parameter) {
        this.field = parameter;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
