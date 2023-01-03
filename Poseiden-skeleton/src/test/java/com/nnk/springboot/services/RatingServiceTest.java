package com.nnk.springboot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

@SpringBootTest
public class RatingServiceTest {

    private IRatingService ratingService;

    private static Rating ratingToAdd = new Rating();

    @Mock
    private RatingRepository ratingRepository;

    @BeforeEach
    public void setUp() {
	MockitoAnnotations.openMocks(this);

	ratingService = new RatingServiceImpl(ratingRepository);
	ratingToAdd.setRatingId(0);
    }

    @Test
    public void shouldGetAllRatings() {
	List<Rating> ratingList = new ArrayList<>();
	ratingList.add(ratingToAdd);

	when(ratingRepository.findAll()).thenReturn(ratingList);

	List<Rating> ratings = (List<Rating>) ratingService.getRatings();
	assertEquals(ratings.get(0).getRatingId(), ratingToAdd.getRatingId());
    }

    @Test
    public void shouldGetOneRating() {
	when(ratingRepository.findById(ratingToAdd.getRatingId())).thenReturn(Optional.of(ratingToAdd));

	Optional<Rating> rating = ratingService.getRatingById(ratingToAdd.getRatingId());

	assertEquals(rating.get().getRatingId(), ratingToAdd.getRatingId());
    }

    @Test
    public void shouldSaveRating() {
	when(ratingRepository.save(any(Rating.class))).thenReturn(ratingToAdd);

	Rating ratingUser = ratingService.saveRating(ratingToAdd);

	assertEquals(ratingUser.getRatingId(), ratingToAdd.getRatingId());
    }

    @Test
    public void shouldDeleteRating() {
	ratingService.deleteRatingById(ratingToAdd.getRatingId());

	verify(ratingRepository, times(1)).deleteById(ratingToAdd.getRatingId());
    }

}
