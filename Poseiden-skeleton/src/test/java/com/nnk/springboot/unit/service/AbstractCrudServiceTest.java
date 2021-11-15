package com.nnk.springboot.unit.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.impl.AbstractCrudService;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class AbstractCrudServiceTest {
    @Data
    class MockEntity {
        private Integer id;
    }

    interface MockJpaRepository extends JpaRepository<MockEntity, Integer> {};

    class MockCrudService extends AbstractCrudService {

        @Override
        public JpaRepository getRepository() {
            return null;
        }
    }
    MockCrudService service;
    JpaRepository repository;

    @BeforeEach
    void init(){
        service = mock(MockCrudService.class, Mockito.CALLS_REAL_METHODS);
        repository = mock(MockJpaRepository.class);
        when(service.getRepository()).thenReturn(
                repository
        );
    }
    @Test
    public void shouldDelete() {
        Integer id = 1;

        service.delete(id);

        verify(repository,times(1)).deleteById(id);
        verify(repository,times(1)).findAll();
    }

    @Test
    public void shouldSaveOrUpdate() {
        MockEntity mockEntity = mock(MockEntity.class);

        service.saveOrUpdate(mockEntity);

        verify(repository,times(1)).save(mockEntity);
        verify(repository,times(1)).findAll();
    }

    @Test
    public void shouldFindById() {
        Integer id = 1;

        when(repository.findById(id)).thenReturn(Optional.of(new CurvePoint(1,2d, 3d)));
        service.findById(id);

        verify(repository,times(1)).findById(id);
    }

    @Test()
    public void shouldThrowIllegalArgumentExecptionIfFindByInvalidId() {

        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {

            @Override
            public void execute() throws Throwable {
                Integer id = 1;
                service.findById(id);
                verify(repository,times(1)).findById(id);

            }
        });
    }
    @Test
    public void shouldFindAll() {
        service.findAll();
        verify(repository,times(1)).findAll();
    }
}
