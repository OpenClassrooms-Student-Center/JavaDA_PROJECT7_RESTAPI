package com.nnk.springboot.controllersTest;

import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import com.nnk.springboot.services.CurvePointService;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("/application-test.properties")

public class CurvePointControllerTest {
    @Mock
    CurvePointService curvePointService;

    private CurveController curveController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        curveController = new CurveController(curvePointService);
    }

    @Test
    public void homeDisplayCurvePointsPageTest(){
        //arrange
        Model model = new ConcurrentModel();
        List<CurvePoint> listOfCurvePoints = new ArrayList<>();
        when(curvePointService.findAll()).thenReturn(listOfCurvePoints);
        //act
        String view = curveController.homeDisplayCurvePointsPage(model);

        //assert
        assertEquals("curvePoint/list",view );

    }
    @Test
    public void displayAddBidFormTest(){
        CurvePoint curvePoint = new CurvePoint();
        //act
        String page =  curveController.displayAddCurvePointForm(curvePoint);
        assertEquals("curvePoint/add",page );


    }
    @Test
    public void validateCurvePointTest() throws Exception {
        //arrange
        CurvePoint curvePoint = new CurvePoint(2, 2.5, 3.5);
        BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        //act
        String page = curveController.validateCurvePoint(curvePoint, result, model);
        //
        assertEquals("redirect:/curvePoint/list", page);
    }

    @Test
    public void validateCurvePointWithErrorsOnCurvePointTest() throws Exception {
        //arrange
        CurvePoint curvePoint = new CurvePoint(1, 1.0, 1.0);
        BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        when(curvePointService.validateNewCurvePoint(curvePoint)).thenThrow(new Exception());
        //act
        String page = curveController.validateCurvePoint(curvePoint, result, model);
        //
        assertEquals("curvePoint/add", page);
        verify(curvePointService, times(1)).validateNewCurvePoint(curvePoint);
    }
    @Test
    public void validateCurvePointWithBindingErrorsTest(){
        CurvePoint curvePoint = new CurvePoint(1, 1.0, 1.0);

        BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        when(result.hasErrors()).thenReturn(true);
        String page = curveController.validateCurvePoint(curvePoint, result, model);
        //
        assertEquals("curvePoint/add", page);
    }

    @Test
    public void displayUpdateFormTest() throws Exception {
        Model model = new ConcurrentModel();
        Integer id = 2;

        when(curvePointService.getCurvePointById(id)).thenReturn(new CurvePoint(2, 2.5, 3.5));

        //act
        String page = curveController.displayUpdateForm(id, model);
        //assert
        assertEquals("curvePoint/update", page);


    }
    @Test
    public void displayUpdateFormWithErrorsTest() throws Exception {
        Model model = new ConcurrentModel();
        Integer id = 2;

        when(curvePointService.getCurvePointById(id)).thenThrow(new Exception());
        //act
        String page = curveController.displayUpdateForm(id, model);
        //assert
        assertEquals("curvePoint/list", page);


    }
    @Test
    public void updateCurvePointTest() throws Exception {
        //arrange
        CurvePoint curvePoint = new CurvePoint(2, 2.5, 3.5);

        CurvePoint updatedCurvePoint = new CurvePoint(5, 10.5, 15.5);

        //BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);
        when(curvePointService.updateCurvePoint(1, updatedCurvePoint)).thenReturn(curvePoint);
        //when(curvePointService.updateCurvePoint(1, updatedCurvePoint)).thenReturn(curvePoint);
        //act

        String page = curveController.updateCurvePoint(1, updatedCurvePoint, result, model);
        //
        assertEquals("redirect:/curvePoint/list", page);

    }
    @Test
    public void updateBidWithBindingResultErrorsTest() throws Exception {
        //arrange
        CurvePoint curvePoint = new CurvePoint(2, 2.5, 3.5);

        CurvePoint updatedCurvePoint = new CurvePoint(5, 10.5, 15.5);
        //BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);
        when(curvePointService.updateCurvePoint(1, updatedCurvePoint)).thenReturn(curvePoint);
        //when(curvePointService.updateCurvePoint(1, updatedCurvePoint)).thenReturn(curvePoint);
        //act

        String page = curveController.updateCurvePoint(1, updatedCurvePoint, result, model);
        //
        assertEquals("redirect:/curvePoint/update/1", page);

    }
    @Test
    public void updateCurvePointWithErrorsTest() throws Exception {
        CurvePoint curvePoint = new CurvePoint(2, 2.5, 3.5);

        CurvePoint updatedCurvePoint = new CurvePoint(5, 10.5, 15.5);
        BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        when(curvePointService.updateCurvePoint(1, updatedCurvePoint)).thenThrow(new Exception());
        //act
        String page = curveController.updateCurvePoint(1, updatedCurvePoint, result, model);
        //
        assertEquals("redirect:/curvePoint/update/1", page);

    }
    @Test
    public void deleteBidTest() throws Exception {
        Model model = new ConcurrentModel();
        doNothing().when(curvePointService).deleteCurvePoint(1);
        //act
        String page = curveController.deleteCurvePoint(1, model);
        //assert
        verify(curvePointService, times(1)).deleteCurvePoint(1);
        assertEquals("redirect:/curvePoint/list", page);
    }
    @Test
    public void deleteCurvePointWithErrorsTest() throws Exception {

        Model model = new ConcurrentModel();
        doThrow(new Exception()).when(curvePointService).deleteCurvePoint(1);

        //act
        String page = curveController.deleteCurvePoint(1, model);
        //assert
        verify(curvePointService, times(1)).deleteCurvePoint(1);
        assertEquals("curvePoint/list", page);
    }


}
