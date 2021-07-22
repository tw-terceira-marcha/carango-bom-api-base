package br.com.caelum.carangobom.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import static javax.persistence.GenerationType.IDENTITY;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    public Role() {
        // Required by @Entity.
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

    @Override
    public String getAuthority() {
        return this.name;
    }
}
