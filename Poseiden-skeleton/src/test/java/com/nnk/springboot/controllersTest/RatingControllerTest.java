package com.nnk.springboot.controllersTest;

import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.CurvePointService;
import com.nnk.springboot.services.RatingService;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("/application-test.properties")

public class RatingControllerTest {
    @Mock
    RatingService ratingService;

    private RatingController ratingController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        ratingController = new RatingController(ratingService);
    }

    @Test
    public void homeDisplayRatingsPageTest(){
        //arrange
        Model model = new ConcurrentModel();
        List<Rating> listOfRatings = new ArrayList<>();
        when(ratingService.findAll()).thenReturn(listOfRatings);
        //act
        String view = ratingController.homeDisplayRatingList(model);

        //assert
        assertEquals("rating/list",view );

    }
    @Test
    public void displayAddRatingFormTest(){
        Rating rating = new Rating();
        //act
        String page =  ratingController.displayAddRatingForm(rating);
        assertEquals("rating/add",page );


    }
    @Test
    public void validateRatingTest() throws Exception {
        //arrange
        Rating rating = new Rating("moody", "sand", "fitch", 1);
        BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        //act
        String page = ratingController.validateRating(rating, result, model);
        //
        assertEquals("redirect:/rating/list", page);
    }

    @Test
    public void validateRatingWithErrorsOnRatingTest() throws Exception {
        //arrange
        Rating rating = new Rating("", "", "fitch", 1);
        //BindingResult result = mock(BindingResult.class);
        BindingResult result = new BeanPropertyBindingResult(rating, "rating");

        Model model = new ConcurrentModel();
        //when (!result.hasErrors()).thenReturn(false);
        when(ratingService.validateNewRating(rating)).thenThrow(new Exception());
        //act
        String page = ratingController.validateRating(rating, result, model);
        //
        assertEquals("rating/add", page);
        //verify(ratingService, times(0)).validateNewRating(rating);
    }
    @Test
    public void validateRatingWithErrorsOnBindingTest(){
        Rating rating = new Rating("", "", "fitch", 1);

        BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        when(result.hasErrors()).thenReturn(true);
        String page = ratingController.validateRating(rating, result, model);
        //
        assertEquals("rating/add", page);
    }

    @Test
    public void displayUpdateFormTest() throws Exception {
        Model model = new ConcurrentModel();
        Integer id = 2;

        when(ratingService.getRatingById(id)).thenReturn(new Rating("moody", "sand", "fitch", 1));
        //act
        String page = ratingController.displayUpdateRatingForm(id, model);
        //assert
        assertEquals("rating/update", page);


    }
    @Test
    public void displayUpdateFormWithErrorsTest() throws Exception {
        Model model = new ConcurrentModel();
        Integer id = 2;

        when(ratingService.getRatingById(id)).thenThrow(new Exception());
        //act
        String page = ratingController.displayUpdateRatingForm(id, model);
        //assert
        assertEquals("rating/list", page);


    }
    @Test
    public void updateBidTest() throws Exception {
        //arrange
        Rating rating = new Rating("moody", "sand", "fitch", 1);
        Rating updatedRating = new Rating("moodyU", "sandU", "fitchU", 1);
        //BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);
        when(ratingService.updateRating(1, updatedRating)).thenReturn(rating);
        //when(ratingService.updateRating(1, updatedRating)).thenReturn(rating);
        //act

        String page = ratingController.updateRating(1, rating, result, model);
        //
        assertEquals("redirect:/rating/list", page);

    }
    @Test
    public void updateBidWithBindingResultErrorsTest() throws Exception {
        //arrange
        Rating rating = new Rating("moody", "sand", "fitch", 1);
        Rating updatedRating = new Rating("", "", "", 0);
        //BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);
        //when(ratingService.updateRating(1, updatedRating)).thenReturn(rating);
        //when(ratingService.updateRating(1, updatedRating)).thenReturn(rating);
        //act

        String page = ratingController.updateRating(1, updatedRating, result, model);
        //
        assertEquals("redirect:/rating/update/1", page);

    }
    @Test
    public void updateRatingWithErrorsTest() throws Exception {
        Rating rating = new Rating("moody", "sand", "fitch", 1);
        Rating updatedRating = new Rating("moodyU", "sandU", "fitchU", 1);
        BindingResult result = mock(BindingResult.class);
        Model model = new ConcurrentModel();
        when(ratingService.updateRating(1, updatedRating)).thenThrow(new Exception());
        //act
        String page = ratingController.updateRating(1, updatedRating, result, model);
        //
        assertEquals("redirect:/rating/update/1", page);

    }
    @Test
    public void deleteBidTest() throws Exception {
        Model model = new ConcurrentModel();
        doNothing().when(ratingService).deleteRating(1);
        //act
        String page = ratingController.deleteRating(1, model);
        //assert
        verify(ratingService, times(1)).deleteRating(1);
        assertEquals("redirect:/rating/list", page);
    }
    @Test
    public void deleteCurvePointWithErrorsTest() throws Exception {

        Model model = new ConcurrentModel();
        doThrow(new Exception()).when(ratingService).deleteRating(1);

        //act
        String page = ratingController.deleteRating(1, model);
        //assert
        verify(ratingService, times(1)).deleteRating(1);
        assertEquals("rating/list", page);
    }

}
