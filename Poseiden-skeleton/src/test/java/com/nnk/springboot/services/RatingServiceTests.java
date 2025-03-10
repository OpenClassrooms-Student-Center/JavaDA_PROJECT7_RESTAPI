package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTests {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingService ratingService;

    private Rating rating;

    @BeforeEach
    void setUp() {
        rating = new Rating();
        rating.setId(1);
        rating.setOrderNumber(10);
        rating.setMoodysRating("Moodys Rating");
        rating.setFitchRating("Fitch Rating");
        rating.setSandPRating("Sand PRating");
    }

    @Test
    void testGetAllRating() {
        when(ratingRepository.findAll()).thenReturn(Arrays.asList(rating));

        List<Rating> result = ratingService.getAllRating();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Moodys Rating", result.get(0).getMoodysRating());
    }

    @Test
    void testGetRatingById() {
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        Optional<Rating> result = ratingService.getRatingById(1);
        assertTrue(result.isPresent());
        assertEquals(10, result.get().getOrderNumber());
    }

    @Test
    void testSaveRating() {
        when(ratingRepository.save(rating)).thenReturn(rating);

        Rating savedRating = ratingService.saveRating(rating);
        assertNotNull(savedRating);
        assertEquals("Fitch Rating", savedRating.getFitchRating());
    }

    @Test
    void testDeleteRatingById() {
        doNothing().when(ratingRepository).deleteById(1);
        ratingService.deleteRatingById(1);
        verify(ratingRepository, times(1)).deleteById(1);
    }
}
