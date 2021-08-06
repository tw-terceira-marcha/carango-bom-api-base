package br.com.caelum.carangobom.data.DTO;

public class ConstraintErrorDTO {

    private String error;

    private String message;

    public ConstraintErrorDTO() {
        this.error = "Constraint violation";
        this.message = "Execution of this operation would violate a data constraint";
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
