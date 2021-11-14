package com.nnk.springboot.unit.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.impl.CurvePointServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class CurvePointServiceImplTest {

    CurvePointRepository curvePointRepository ;
    CurvePointServiceImpl service;

    @BeforeEach
    void init(){
        curvePointRepository = mock(CurvePointRepository.class);
        service = new CurvePointServiceImpl(curvePointRepository);
    }
    @Test
    public void shouldDelete() {
        Integer id = 1;

        service.delete(id);

        verify(curvePointRepository,times(1)).deleteById(id);
        verify(curvePointRepository,times(1)).findAll();
    }

    @Test
    public void shouldSaveOrUpdate() {
        CurvePoint curvePoint1 = mock(CurvePoint.class);

        service.saveOrUpdate(curvePoint1);

        verify(curvePointRepository,times(1)).save(curvePoint1);
        verify(curvePointRepository,times(1)).findAll();
    }

    @Test
    public void shouldFindById() {
        Integer id = 1;

        when(curvePointRepository.findById(id)).thenReturn(Optional.of(new CurvePoint(1,2d, 3d)));
        service.findById(id);

        verify(curvePointRepository,times(1)).findById(id);
    }

    @Test()
    public void shouldThrowIllegalArgumentExecptionIfFindByInvalidId() {

        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {

            @Override
            public void execute() throws Throwable {
                Integer id = 1;
                service.findById(id);
                verify(curvePointRepository,times(1)).findById(id);

            }
        });
    }
    @Test
    public void shouldFindAll() {
        service.findAll();
        verify(curvePointRepository,times(1)).findAll();
    }
}
