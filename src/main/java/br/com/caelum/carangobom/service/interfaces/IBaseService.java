package br.com.caelum.carangobom.service.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface IBaseService<Entity, Id, Repository extends JpaRepository<Entity, Id>, DTO, CreateForm, UpdateForm> {

    Repository getRepository();

    List<DTO> getList();

    default Optional<DTO> getById(Id id) {
        return this.getRepository()
                .findById(id)
                .map(this::entityToDTO);
    }

    @Transactional
    default Optional<DTO> create(CreateForm form) {
        if (this.entityExists(form)) {
            return Optional.empty();
        }

        var entity = this.getRepository().save(this.formToEntity(form));
        var dto = this.entityToDTO(entity);
        return Optional.of(dto);
    }

    @Transactional
    default Optional<DTO> update(Id id, UpdateForm form) {
        return this.getRepository()
                .findById(id)
                .map(entity -> {
                    this.updateEntity(entity, form);
                    return this.entityToDTO(entity);
                });
    }

    default Optional<DTO> delete(Id id) {
        Repository repository = this.getRepository();
        return repository
                .findById(id)
                .map(entity -> {
                    repository.delete(entity);
                    return this.entityToDTO(entity);
                });
    }

    DTO entityToDTO(Entity entity);

    Entity formToEntity(CreateForm form);

    void updateEntity(Entity entity, UpdateForm form);

    boolean entityExists(CreateForm form);
}
