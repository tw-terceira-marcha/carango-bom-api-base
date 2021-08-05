package br.com.caelum.carangobom.controllers.interfaces;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.caelum.carangobom.service.interfaces.IBaseService;

public interface IBaseController<Id, Entity, Repository extends JpaRepository<Entity, Id>,
        DTO, CreateForm, UpdateForm,
        Service extends IBaseService<Entity, Id, Repository, DTO, CreateForm, UpdateForm>> {

    Service getService();

    URI getCreatedURI(DTO dto);

    @GetMapping()
    default List<DTO> list() {
        return this.getService().getList();
    }

    @GetMapping("/{id}")
    default ResponseEntity<DTO> details(@PathVariable Id id) {
        return this.getService()
                .getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    default ResponseEntity<DTO> create(
            @Valid @RequestBody CreateForm Form,
            UriComponentsBuilder uriBuilder) {
        return this.getService()
            .create(Form)
            .map(dto -> ResponseEntity
                 .created(this.getCreatedURI(dto))
                 .body(dto)
            )
            .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PutMapping("/{id}")
    default ResponseEntity<DTO> update(
            @PathVariable Id id,
            @Valid @RequestBody UpdateForm Form) {
        return this.getService()
                .update(id, Form)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    default ResponseEntity<DTO> delete(@PathVariable Id id) {
        return this.getService()
                .delete(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
