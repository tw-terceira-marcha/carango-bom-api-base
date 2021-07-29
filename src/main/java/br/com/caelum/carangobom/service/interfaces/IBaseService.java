package br.com.caelum.carangobom.service.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IBaseService<Entity, Id, Repository extends JpaRepository<Entity, Id>, DTO, CreateForm, UpdateForm> {

    public Repository getRepository();

    public default Optional<DTO> getById(Id id) {
        return this.getRepository()
                .findById(id)
                .map(this::entityToDTO);
    }

    public default DTO create(CreateForm form) {
        // TODO: should this check for duplicates?
        return this.entityToDTO(
                this.getRepository().save(this.formToEntity(form)));
    }

    public default Optional<DTO> update(Id id, UpdateForm form) {
        return this.getRepository()
                .findById(id)
                .map(entity -> {
                    this.updateEntity(entity, form);
                    return this.entityToDTO(entity);
                });
    }

    public default Optional<DTO> delete(Id id) {
        var repository = this.getRepository();
        return repository
                .findById(id)
                .map(entity -> {
                    repository.delete(entity);
                    return this.entityToDTO(entity);
                });
    }

    public DTO entityToDTO(Entity entity);

    public Entity formToEntity(CreateForm form);

    public void updateEntity(Entity entity, UpdateForm form);

}
