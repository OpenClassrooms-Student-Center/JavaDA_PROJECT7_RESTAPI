package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.RatingService;
import com.nnk.springboot.util.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(RatingServiceImpl.class)
class RatingServiceImplTest {

    private static final Rating rating = new Rating();
    @MockBean
    private RatingRepository ratingRepository;
    @Autowired
    private RatingService ratingService;

    @BeforeEach
    private void init() {
        rating.setId(1);
        rating.setFitchRating("AA");
        rating.setSandPRating("BB");
        rating.setMoodysRating("A");
    }


    @Test
    final void testFindById() {
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));
        Rating foundRating = ratingService.findById(1);

        assertThat(foundRating).isEqualTo(rating);

    }

    @Test
    final void testFindByIdNotFound() throws NotFoundException {
        assertThrows(NotFoundException.class, () -> ratingService.findById(0));
    }

    @Test
    final void testDeleteById() {
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));
        ratingService.delete(1);

        verify(ratingRepository, times(1)).delete(any(Rating.class));
    }

    @Test
    final void testFindAll() {
        List<Rating> findAll = new ArrayList<>();
        findAll.add(rating);

        when(ratingRepository.findAll()).thenReturn(findAll);
        List<Rating> foundRating = ratingService.findAll();

        assertThat(foundRating).isEqualTo(findAll);
    }

    @Test
    final void testCreateRating() {
        RatingDto ratingDto = new RatingDto();
        ratingDto.setFitchRating("AA");
        ratingDto.setSandPRating("BB");
        ratingDto.setMoodysRating("A");

        when(ratingRepository.save(any(Rating.class))).thenReturn(new Rating());
        Rating result = ratingService.create(ratingDto);

        verify(ratingRepository, times(1)).save(any(Rating.class));
        assertNotNull(result);
    }


    @Test
    final void testUpdateRating() {
        RatingDto ratingDto = new RatingDto();
        ratingDto.setFitchRating("A");
        ratingDto.setSandPRating("BBC");
        ratingDto.setMoodysRating("B");
        Rating Rating = new Rating(ratingDto);

        when(ratingRepository.findById(1)).thenReturn(Optional.of(RatingServiceImplTest.rating));
        when(ratingRepository.getById(1)).thenReturn(RatingServiceImplTest.rating);
        when(ratingRepository.save(Rating)).thenReturn(Rating);
        ratingService.update(1, ratingDto);

        assertThat(RatingServiceImplTest.rating.getFitchRating()).isEqualTo("A");
        assertThat(RatingServiceImplTest.rating.getMoodysRating()).isEqualTo("B");
        assertThat(RatingServiceImplTest.rating.getSandPRating()).isEqualTo("BBC");
    }

    @Test
    final void testUpdateRatingThrowEntityNotFoundException() {
        when(ratingRepository.findById(1)).thenReturn(Optional.empty());
        RatingDto ratingDto = new RatingDto();
        ratingDto.setFitchRating("A");
        ratingDto.setSandPRating("BBC");
        ratingDto.setMoodysRating("B");

        assertThrows(NotFoundException.class, () -> ratingService.update(1, ratingDto));
    }
}
