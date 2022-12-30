package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RatingServiceImplTest {


    @InjectMocks
    private RatingServiceImpl ratingService;

    @Mock
    private RatingRepository ratingRepository;

    private Rating rating1;

    private Rating rating2;



    @Test
    void findAllRatingTest() {
        //Given
        List<Rating> allRatings = List.of(
                new Rating("Good","qqq"," ",3),
                new Rating("Bad","qqq"," ",2)
        );
        when(ratingRepository.findAll()).thenReturn(allRatings);

        //When
        List<Rating> result = ratingService.findAll();

        //Then
        Assert.assertEquals(result.get(1), allRatings.get(1));


    }

    @Test
    void findByIdRatingTest() {

        //Given
        List<Rating> allRatings = List.of(
                new Rating("Good","qqq"," ",3),
                new Rating("Bad","qqq"," ",2)
        );
        when(ratingRepository.findById(1)).thenReturn(Optional.of(allRatings.get(1)));

        //When
        Optional<Rating> result = ratingService.findById(1);

        //Then
        Assert.assertEquals(result.get(), allRatings.get(1));

    }

    @Test
    public void findByIdRatingTest_ShouldThrowException() throws DataNotFoundException {
        //Given
        when(ratingRepository.findById(3)).thenReturn(Optional.empty());
        // When //Then
        assertThrows(DataNotFoundException.class, () -> ratingService.findById(3));
    }

    @Test
    void saveRatingTest() {
        //Given
        when(ratingRepository.save(rating1)).thenReturn(rating1);

        //When
        Rating result = ratingService.save(rating1);

        //Then
        Assert.assertEquals(result, rating1);

    }

    @Test
    void updateRatingTest() {
        //Given
        Rating rating = new Rating("Good","qqq"," ",3);

        when(ratingRepository.save(rating)).thenReturn(rating);

        //When
        Rating result = ratingService.update(rating);

        //Then
        Assert.assertEquals(result, rating);

    }

    @Test
    void deleteRatingTest() {

        //Given
        Rating rating = new Rating("Good","qqq"," ",3);

        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        //When
        ratingService.delete(1);

        //Then
        verify(ratingRepository).findById(1);
    }

    @Test
    public void deleteRatingTest_ShouldThrowException() throws DataNotFoundException {
        //Given // When //Then
        assertThrows(DataNotFoundException.class, () -> ratingService.delete(3));
    }
}