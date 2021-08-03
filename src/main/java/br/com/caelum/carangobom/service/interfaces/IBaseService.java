package br.com.caelum.carangobom.service.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IBaseService<Entity, Id, Repository extends JpaRepository<Entity, Id>, DTO, CreateForm, UpdateForm> {

    Repository getRepository();

    default Optional<DTO> getById(Id id) {
        return this.getRepository()
                .findById(id)
                .map(this::entityToDTO);
    }

    default DTO create(CreateForm form) {
        // TODO: should this check for duplicates?
        return this.entityToDTO(
                this.getRepository().save(this.formToEntity(form)));
    }

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

}
