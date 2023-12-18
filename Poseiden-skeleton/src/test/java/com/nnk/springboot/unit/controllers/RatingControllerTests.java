package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = RatingController.class)
public class RatingControllerTests extends TestVariables {

    @Autowired
    RatingController ratingController;

    @MockBean
    private RatingService ratingService;

    @MockBean
    private BindingResult bindingResult;

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        rating.setId(1);

        when(ratingService.findAll()).thenReturn(ratingList);
        when(ratingService.findById(any(Integer.class))).thenReturn(ratingOptional);
        when(ratingService.findById(any(Integer.class))).thenReturn(ratingOptional);
        when(bindingResult.hasErrors()).thenReturn(false);
    }

    @Test
    public void ContextLoads() {}

    @Nested
    public class HomeTests {
        @Test
        public void homeTest () {
            assertEquals("rating/list", ratingController.home(model));
            verify(ratingService, Mockito.times(1)).findAll();
        }
    }

    @Nested
    public class AddRatingFormTests {
        @Test
        public void addRatingFormTest () {
            assertEquals("rating/add", ratingController.addRatingForm(rating));
        }
        @Test
        public void addRatingFormTestIfEmpty () {
            assertEquals("rating/add", ratingController.addRatingForm(new Rating()));
        }
        @Test
        public void addRatingFormTestIfNull () {
            assertEquals("rating/add", ratingController.addRatingForm(null));
        }
    }

    @Nested
    public class ValidateTests {
        @Test
        public void validateTest () {
            assertEquals("redirect:/rating/list", ratingController.validate(rating, bindingResult, model));
            verify(ratingService, Mockito.times(1)).save(any(Rating.class));
        }
        @Test
        public void validateTestIfInvalidRating () {
            when(bindingResult.hasErrors()).thenReturn(true);
            assertEquals("rating/add", ratingController.validate(rating, bindingResult, model));
            verify(ratingService, Mockito.times(0)).save(any(Rating.class));
        }
    }

    @Nested
    public class ShowUpdateFormTests {
        @Test
        public void showUpdateFormTest () {
            assertEquals("rating/update", ratingController.showUpdateForm(rating.getId(), model));
            verify(ratingService, Mockito.times(1)).findById(any(Integer.class));
        }
        @Test
        public void showUpdateFormTestIfRatingNotFound () {
            when(ratingService.findById(any(Integer.class))).thenReturn(Optional.empty());
            assertThrows(IllegalArgumentException.class, () -> ratingController.showUpdateForm(rating.getId(), model));
            verify(ratingService, Mockito.times(1)).findById(any(Integer.class));
        }
    }

    @Nested
    public class UpdateRatingTests {
        @Test
        public void updateRatingTest () {
            assertEquals("redirect:/rating/list", ratingController.updateRating(rating.getId(), rating, bindingResult, model));
            verify(ratingService, Mockito.times(1)).findById(any(Integer.class));
            verify(ratingService, Mockito.times(1)).save(any(Rating.class));
        }
        @Test
        public void updateRatingTestIfInvalidRating () {
            when(bindingResult.hasErrors()).thenReturn(true);
            assertEquals("redirect:/rating/update/{id}", ratingController.updateRating(rating.getId(), rating, bindingResult, model));
            verify(ratingService, Mockito.times(0)).findById(any(Integer.class));
            verify(ratingService, Mockito.times(0)).save(any(Rating.class));
        }
        @Test
        public void updateRatingTestIfRatingNotInDB () {
            when(ratingService.findById(any(Integer.class))).thenReturn(Optional.empty());
            assertThrows(IllegalArgumentException.class, () -> ratingController.updateRating(rating.getId(), rating, bindingResult, model));
            verify(ratingService, Mockito.times(1)).findById(any(Integer.class));
            verify(ratingService, Mockito.times(0)).save(any(Rating.class));
        }
    }

    @Nested
    public class DeleteRatingTests {
        @Test
        public void deleteRatingTest () {
            assertEquals("redirect:/rating/list", ratingController.deleteRating(rating.getId(), model));
            verify(ratingService, Mockito.times(1)).deleteById(any(Integer.class));
        }
    }
}
