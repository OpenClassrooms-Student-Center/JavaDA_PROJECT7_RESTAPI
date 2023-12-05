package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CurveController.class)
public class CurveControllerTests extends TestVariables {

    @Autowired
    CurveController curveController;

    @MockBean
    private CurvePointService curvePointService;

    @MockBean
    private BindingResult bindingResult;

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        curvePoint.setId(1);

        when(curvePointService.findAll()).thenReturn(curvePointList);
        when(curvePointService.findById(any(Integer.class))).thenReturn(curvePointOptional);
        when(curvePointService.findById(any(Integer.class))).thenReturn(curvePointOptional);
        when(bindingResult.hasErrors()).thenReturn(false);
    }

    @Test
    public void ContextLoads() {}

    @Nested
    public class HomeTests
    {
        @Test
        public void homeTest () {
            assertEquals("curvePoint/list", curveController.home(model));
            verify(curvePointService, Mockito.times(1)).findAll();
        }
    }

    @Nested
    public class AddTests
    {
        @Test
        public void addCurvePointFormTest () {
            assertEquals("curvePoint/add", curveController.addCurvePointForm(curvePoint));
        }
        @Test
        public void addCurvePointFormTestIfEmpty () {
            assertEquals("curvePoint/add", curveController.addCurvePointForm(new CurvePoint()));
        }
        @Test
        public void addCurvePointFormTestIfNull () {
            assertEquals("curvePoint/add", curveController.addCurvePointForm(null));
        }
    }

    @Nested
    public class ValidateTests
    {
        @Test
        public void validateTest () {
            assertEquals("redirect:/curvePoint/list", curveController.validate(curvePoint, bindingResult, model));
            verify(curvePointService, Mockito.times(1)).save(any(CurvePoint.class));
        }
        @Test
        public void validateTestIfInvalidCurvePoint () {
            when(bindingResult.hasErrors()).thenReturn(true);
            assertEquals("curvePoint/add", curveController.validate(curvePoint, bindingResult, model));
            verify(curvePointService, Mockito.times(0)).save(any(CurvePoint.class));
        }
    }

    @Nested
    public class ShowUpdateTests
    {
        @Test
        public void showUpdateFormTest () {
            assertEquals("curvePoint/update", curveController.showUpdateForm(curvePoint.getId(), model));
            verify(curvePointService, Mockito.times(1)).findById(any(Integer.class));
        }
        @Test
        public void showUpdateFormTestIfCurvePointNotFound () {
            when(curvePointService.findById(any(Integer.class))).thenReturn(Optional.empty());
            assertThrows(IllegalArgumentException.class, () -> curveController.showUpdateForm(curvePoint.getId(), model));
            verify(curvePointService, Mockito.times(1)).findById(any(Integer.class));
        }
    }

    @Nested
    public class UpdateCurvePointTests
    {
        @Test
        public void updateCurvePointTest () {
            assertEquals("redirect:/curvePoint/list", curveController.updateCurvePoint(curvePoint.getId(), curvePoint, bindingResult, model));
            verify(curvePointService, Mockito.times(1)).findById(any(Integer.class));
            verify(curvePointService, Mockito.times(1)).save(any(CurvePoint.class));
        }
        @Test
        public void updateCurvePointTestIfInvalidCurvePoint () {
            when(bindingResult.hasErrors()).thenReturn(true);
            assertEquals("redirect:/curvePoint/list", curveController.updateCurvePoint(curvePoint.getId(), curvePoint, bindingResult, model));
            verify(curvePointService, Mockito.times(0)).findById(any(Integer.class));
            verify(curvePointService, Mockito.times(0)).save(any(CurvePoint.class));
        }
        @Test
        public void updateCurvePointTestIfCurvePointNotInDB () {
            when(curvePointService.findById(any(Integer.class))).thenReturn(Optional.empty());
            assertThrows(IllegalArgumentException.class, () -> curveController.updateCurvePoint(curvePoint.getId(), curvePoint, bindingResult, model));
            verify(curvePointService, Mockito.times(1)).findById(any(Integer.class));
            verify(curvePointService, Mockito.times(0)).save(any(CurvePoint.class));
        }
    }

    @Nested
    public class DeleteCurvePointTests
    {
        @Test
        public void deleteCurvePointTest () {
            assertEquals("redirect:/curvePoint/list", curveController.deleteCurvePoint(curvePoint.getId(), model));
            verify(curvePointService, Mockito.times(1)).findById(any(Integer.class));
            verify(curvePointService, Mockito.times(1)).deleteById(any(Integer.class));
        }
        @Test
        public void deleteCurvePointTestIfCurvePointNotInDB () {
            when(curvePointService.findById(any(Integer.class))).thenReturn(Optional.empty());
            assertThrows(IllegalArgumentException.class, () -> curveController.deleteCurvePoint(curvePoint.getId(), model));
            verify(curvePointService, Mockito.times(1)).findById(any(Integer.class));
            verify(curvePointService, Mockito.times(0)).deleteById(any(Integer.class));
        }
    }
}
