package br.com.caelum.carangobom.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.caelum.carangobom.service.interfaces.IBaseService;

class BaseServiceTest {

    final class Entity {
    }

    interface Repository extends JpaRepository<Entity, Long> {
    }

    final class DTO {
    }

    final class Form {
    }

    @Mock
    private Repository repository;

    @BeforeEach
    public void setupMocks() {
        openMocks(this);
    }

    @Test
    void getEntity() {
        var entity = new Entity();
        var dto = new DTO();
        var service = new IBaseService<Entity, Long, Repository, DTO, Form, Form>() {
            @Override
            public Entity formToEntity(Form o) {
                fail("Should not have been called");
                return null;
            }

            @Override
            public void updateEntity(Entity o, Form o2) {
                fail("Should not have been called");
            }

            @Override
            public DTO entityToDTO(Entity e) {
                assertEquals(e, entity);
                return dto;
            }

            @Override
            public Repository getRepository() {
                return repository;
            }
        };

        when(repository.findById(1L))
                .thenReturn(Optional.of(entity));

        Optional<DTO> returnedDTO = service.getById(1L);

        assertEquals(returnedDTO.get(), dto);
    }

    @Test
    void getInexistentEntity() {
        var service = new IBaseService<Entity, Long, Repository, DTO, Form, Form>() {
            @Override
            public Entity formToEntity(Form o) {
                fail("Should not have been called");
                return null;
            }

            @Override
            public void updateEntity(Entity o, Form o2) {
                fail("Should not have been called");
            }

            @Override
            public DTO entityToDTO(Entity e) {
                fail("Should not have been called");
                return null;
            }

            @Override
            public Repository getRepository() {
                return repository;
            }
        };

        when(repository.findById(anyLong()))
                .thenReturn(Optional.empty());

        Optional<DTO> dto = service.getById(1L);
        assertFalse(dto.isPresent());
    }

    @Test
    void createEntity() {
        var entity = new Entity();
        var dto = new DTO();
        var form = new Form();
        var service = new IBaseService<Entity, Long, Repository, DTO, Form, Form>() {
            @Override
            public Entity formToEntity(Form f) {
                assertEquals(f, form);
                return entity;
            }

            @Override
            public void updateEntity(Entity o, Form o2) {
                fail("Should not have been called");
            }

            @Override
            public DTO entityToDTO(Entity e) {
                assertEquals(e, entity);
                return dto;
            }

            @Override
            public Repository getRepository() {
                return repository;
            }
        };

        when(repository.save(entity))
                .then(invocation -> {
                    Entity savedEntity = invocation.getArgument(0, Entity.class);
                    return savedEntity;
                });

        DTO returnedDTO = service.create(form);
        assertEquals(returnedDTO, dto);
    }

    @Test
    void updateEntity() {
        Long id = 1l;
        var entity = new Entity();
        var dto = new DTO();
        var form = new Form();
        var service = new IBaseService<Entity, Long, Repository, DTO, Form, Form>() {
            @Override
            public Entity formToEntity(Form f) {
                fail("Should not have been called");
                return null;
            }

            @Override
            public void updateEntity(Entity e, Form f) {
                assertEquals(e, entity);
                assertEquals(f, form);
            }

            @Override
            public DTO entityToDTO(Entity e) {
                assertEquals(e, entity);
                return dto;
            }

            @Override
            public Repository getRepository() {
                return repository;
            }
        };

        when(repository.findById(id))
                .thenReturn(Optional.of(entity));

        Optional<DTO> returnedDTO = service.update(id, form);
        assertEquals(returnedDTO.get(), dto);
    }

    @Test
    void updateInexistentEntity() {
        Long id = 1l;
        var form = new Form();
        var service = new IBaseService<Entity, Long, Repository, DTO, Form, Form>() {
            @Override
            public Entity formToEntity(Form f) {
                fail("Should not have been called");
                return null;
            }

            @Override
            public void updateEntity(Entity e, Form f) {
                fail("Should not have been called");
            }

            @Override
            public DTO entityToDTO(Entity e) {
                fail("Should not have been called");
                return null;
            }

            @Override
            public Repository getRepository() {
                return repository;
            }
        };

        when(repository.findById(anyLong()))
                .thenReturn(Optional.empty());

        Optional<DTO> returnedDTO = service.update(id, form);
        assertFalse(returnedDTO.isPresent());
    }

    @Test
    void deleteEntity() {
        Long id = 1l;
        var entity = new Entity();
        var dto = new DTO();
        var service = new IBaseService<Entity, Long, Repository, DTO, Form, Form>() {
            @Override
            public Entity formToEntity(Form f) {
                fail("Should not have been called");
                return null;
            }

            @Override
            public void updateEntity(Entity e, Form f) {
                fail("Should not have been called");
            }

            @Override
            public DTO entityToDTO(Entity e) {
                assertEquals(e, entity);
                return dto;
            }

            @Override
            public Repository getRepository() {
                return repository;
            }
        };

        when(repository.findById(id))
                .thenReturn(Optional.of(entity));

        Optional<DTO> returnedDTO = service.delete(id);
        assertTrue(returnedDTO.isPresent());
        verify(repository).delete(entity);
    }

    @Test
    void deleteInexistentEntity() {
        Long id = 1l;
        var service = new IBaseService<Entity, Long, Repository, DTO, Form, Form>() {
            @Override
            public Entity formToEntity(Form f) {
                fail("Should not have been called");
                return null;
            }

            @Override
            public void updateEntity(Entity e, Form f) {
                fail("Should not have been called");
            }

            @Override
            public DTO entityToDTO(Entity e) {
                fail("Should not have been called");
                return null;
            }

            @Override
            public Repository getRepository() {
                return repository;
            }
        };

        when(repository.findById(anyLong()))
                .thenReturn(Optional.empty());

        Optional<DTO> returnedDTO = service.delete(id);
        assertFalse(returnedDTO.isPresent());

        verify(repository, never()).delete(any());
    }
}
