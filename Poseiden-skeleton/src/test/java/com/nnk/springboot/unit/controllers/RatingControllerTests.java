package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RatingControllerTests extends TestVariables {

    RatingController ratingController;

    @Mock
    private RatingService ratingService;

    @Mock
    private BindingResult bindingResult;

    private Model model;

    @Before
    public void setUpPerTest() {
        initializeVariables();
        ratingController = new RatingController(ratingService);
        model = new Model() {
            @Override
            public Model addAttribute(String attributeName, Object attributeValue) {
                return null;
            }

            @Override
            public Model addAttribute(Object attributeValue) {
                return null;
            }

            @Override
            public Model addAllAttributes(Collection<?> attributeValues) {
                return null;
            }

            @Override
            public Model addAllAttributes(Map<String, ?> attributes) {
                return null;
            }

            @Override
            public Model mergeAttributes(Map<String, ?> attributes) {
                return null;
            }

            @Override
            public boolean containsAttribute(String attributeName) {
                return false;
            }

            @Override
            public Object getAttribute(String attributeName) {
                return null;
            }

            @Override
            public Map<String, Object> asMap() {
                return null;
            }
        };

        when(ratingService.findAll()).thenReturn(ratingList);
        when(ratingService.findById(any(Integer.class))).thenReturn(ratingOptional);
        when(bindingResult.hasErrors()).thenReturn(false);
    }

    @Test
    public void ContextLoads() {}

    @Test
    public void homeTest() {
        assertEquals("rating/list", ratingController.home(model));
        verify(ratingService, Mockito.times(1)).findAll();
    }

    @Test
    public void addRatingFormTest() {
        assertEquals("rating/add", ratingController.addRatingForm(rating));
    }
    @Test
    public void addRatingFormTestIfEmpty() {
        assertEquals("rating/add", ratingController.addRatingForm(new Rating()));
    }
    @Test
    public void addRatingFormTestIfNull() {
        assertEquals("rating/add", ratingController.addRatingForm(null));
    }

    @Test
    public void validateTest() {
        assertEquals("rating/add", ratingController.validate(rating, bindingResult, model));
        verify(ratingService, Mockito.times(1)).save(any(Rating.class));
    }
    @Test
    public void validateTestIfInvalidRating() {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertEquals("rating/add", ratingController.validate(rating, bindingResult, model));
        verify(ratingService, Mockito.times(0)).save(any(Rating.class));
    }

    @Test
    public void showUpdateFormTest() {
        assertEquals("rating/update", ratingController.showUpdateForm(rating.getId(), model));
        verify(ratingService, Mockito.times(1)).findById(any(Integer.class));
    }

    @Test
    public void updateRatingTest() {
        assertEquals("redirect:/rating/list", ratingController.updateRating(rating.getId(), rating, bindingResult, model));
        verify(ratingService, Mockito.times(1)).findById(any(Integer.class));
        verify(ratingService, Mockito.times(1)).save(any(Rating.class));
    }
    @Test
    public void updateRatingTestIfInvalidRating() {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertEquals("redirect:/rating/list", ratingController.updateRating(rating.getId(), rating, bindingResult, model));
        verify(ratingService, Mockito.times(0)).findById(any(Integer.class));
        verify(ratingService, Mockito.times(0)).save(any(Rating.class));
    }
    @Test
    public void updateRatingTestIfRatingNotInDB() {
        when(ratingService.findById(any(Integer.class))).thenReturn(Optional.empty());
        assertEquals("redirect:/rating/list", ratingController.updateRating(rating.getId(), rating, bindingResult, model));
        verify(ratingService, Mockito.times(1)).findById(any(Integer.class));
        verify(ratingService, Mockito.times(0)).save(any(Rating.class));
    }

    @Test
    public void deleteRatingTest() {
        assertEquals("redirect:/rating/list", ratingController.deleteRating(rating.getId(), model));
        verify(ratingService, Mockito.times(1)).findById(any(Integer.class));
        verify(ratingService, Mockito.times(1)).deleteById(any(Integer.class));
    }
    @Test
    public void deleteRatingTestIfRatingNotInDB() {
        when(ratingService.findById(any(Integer.class))).thenReturn(Optional.empty());
        assertEquals("redirect:/rating/list", ratingController.deleteRating(rating.getId(), model));
        verify(ratingService, Mockito.times(1)).findById(any(Integer.class));
        verify(ratingService, Mockito.times(0)).deleteById(any(Integer.class));
    }
}
