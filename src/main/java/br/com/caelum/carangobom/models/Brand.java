package br.com.caelum.carangobom.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Brand {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, message = "Must have {min} or more characters.")
    private String name;

    public Brand() {
        // Required by @Entity.
    }

    public Brand(String name) {
        this(null, name);
    }

    public Brand(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
