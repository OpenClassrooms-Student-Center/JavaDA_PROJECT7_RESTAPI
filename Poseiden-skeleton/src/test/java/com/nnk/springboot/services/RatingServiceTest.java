package com.nnk.springboot.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.security.UserDetailsServiceImpl;

@WebMvcTest(RatingService.class)
class RatingServiceTest {
	
	@MockBean
	private RatingRepository ratingRepository;
	
	@MockBean
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private RatingService ratingService;
	
	private static Rating rating = new Rating();
	
	@BeforeEach
	private void init() {
		rating.setId(1);
		rating.setMoodysRating("Mood");
		rating.setSandPRating("Sand");
		rating.setFitchRating("Fitch");
		rating.setOrderNumber(1);
	}

	@Test
	final void testFindAll() {
		List<Rating> findAll = new ArrayList<>();
		findAll.add(rating);
		when(ratingRepository.findAll()).thenReturn(findAll);
		List<Rating> foundList = ratingService.findAll();
		assertThat(foundList).isEqualTo(findAll);
	}

	@Test
	final void testCreateRating() {
		Rating rating2 = new Rating();
		rating2.setId(1);
		rating2.setMoodysRating("Mood");
		rating2.setSandPRating("Sand");
		rating2.setFitchRating("Fitch");
		rating2.setOrderNumber(1);
		when(ratingRepository.save(rating2)).thenReturn(rating2);
		ratingService.createRating(rating2);
		verify(ratingRepository).save(rating2);
	}

	@Test
	final void testUpdateRating() {
		Rating toUpdateRating = new Rating();
		toUpdateRating.setMoodysRating("Moody");
		toUpdateRating.setSandPRating("Sandy");
		toUpdateRating.setFitchRating("Fitchy");
		toUpdateRating.setOrderNumber(2);
		when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));
		when(ratingRepository.getById(1)).thenReturn(rating);
		when(ratingRepository.save(toUpdateRating)).thenReturn(toUpdateRating);
		ratingService.updateRating(toUpdateRating, 1);
		assertThat(rating.getMoodysRating()).isEqualTo("Moody");
		assertThat(rating.getSandPRating()).isEqualTo("Sandy");
		assertThat(rating.getFitchRating()).isEqualTo("Fitchy");
		assertThat(rating.getOrderNumber()).isEqualTo(2);
	}
	
	@Test
	final void testUpdateRatingThrowEntityNotFoundException() {
		when(ratingRepository.findById(1)).thenReturn(null);
		assertThrows(EntityNotFoundException.class, () -> ratingService.updateRating(rating, 1));
	}

	@Test
	final void testFindById() {
		when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));
		Rating foundRating = ratingService.findById(1);
		assertThat(foundRating).isEqualTo(rating);
	}
	
	@Test
	final void testFindByIdNotFound() throws EntityNotFoundException {
		assertThrows(EntityNotFoundException.class, () -> ratingService.findById(0));
	}

	@Test
	final void testDeleteById() {
		ratingService.deleteById(1);
		verify(ratingRepository).deleteById(1);
	}

}
