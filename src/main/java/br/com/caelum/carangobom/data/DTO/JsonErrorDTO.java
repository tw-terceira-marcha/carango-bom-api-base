package br.com.caelum.carangobom.data.DTO;

public class JsonErrorDTO {

    private String error;

    private String message;

    public JsonErrorDTO(String message) {
        this.setError("Invalid JSON");
        this.message = message;
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
