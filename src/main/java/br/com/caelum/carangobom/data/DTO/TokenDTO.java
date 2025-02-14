package br.com.caelum.carangobom.data.DTO;

public class TokenDTO {

    private String token;

    private String type;

    public TokenDTO(String token) {
        this.token = token;
        this.type = "Bearer";
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
