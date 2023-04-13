package com.nnk.springboot.ServicesTest;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.services.RatingService;
import org.junit.Assert;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("/application-test.properties")
@ExtendWith(SpringExtension.class)



public class RatingServiceTests {

	@Mock
	private RatingRepository ratingRepository;

	RatingService ratingService;

	Rating rating = new Rating();

	@BeforeEach
	public void setUp(){
		MockitoAnnotations.openMocks(this);
		rating.setRating_id(1);
		rating.setFitch_rating("fitch");
		rating.setMoodys_rating("moody");
		rating.setOrder_number(2);
		rating.setSandprating("sandprating");

		ratingService = new RatingService(ratingRepository);

	}
	@Test
	public void findByIdTest() throws Exception {
		when(ratingRepository.findById(anyInt())).thenReturn(Optional.of(rating));

		assertEquals(rating,ratingService.getRatingById(1));
	}

	@Test
	public void findAllTest(){
		//ARRANGE
		List<Rating> listOfRatings = new ArrayList<>();
		when(ratingRepository.findAll()).thenReturn(listOfRatings);
		//ACT and ASSERT
		assertEquals(listOfRatings, ratingService.findAll());
	}
	@Test
	public void validateNewBidListTest(){
		//ARRANGE
		when(ratingRepository.save(any(Rating.class))).thenReturn(rating);
		//ACT
		Rating registeredRating = ratingService.validateNewRating(rating);
		//ASSERT
		assertNotNull(registeredRating);
		assertEquals("moody", registeredRating.getMoodys_rating());
		assertEquals("fitch", registeredRating.getFitch_rating());
		assertEquals(2, registeredRating.getOrder_number());
		assertEquals("sandprating", registeredRating.getSandprating());

		verify(ratingRepository, times(1)).save(registeredRating);
	}
	@Test
	public void updateBidListTest() throws Exception {
		//ARRANGE

		Rating updatedRatingEntity = new Rating("updatedmoody", "updatedsand", "updatedfitch", 22);
		when(ratingRepository.findById(rating.getRating_id())).thenReturn(Optional.of(rating));
		when(ratingRepository.save(rating)).thenReturn(rating);
		//ACT
		Rating result = ratingService.updateRating(rating.getRating_id(), updatedRatingEntity);
		//ASSERT
		assertEquals(rating.getMoodys_rating(), result.getMoodys_rating() );
		assertEquals(rating.getSandprating(), result.getSandprating());
		assertEquals(rating.getFitch_rating(), result.getFitch_rating());
		assertEquals(rating.getOrder_number(), result.getOrder_number());



		assertEquals(1, result.getRating_id());

	}
	@Test
	public void deleteBidListTest() throws Exception {
		//ARRANGE
		when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));
		doNothing().when(ratingRepository).delete(rating);
		//ACT
		ratingService.deleteRating(1);
		//ASSERT
		verify(ratingRepository, times(1)).delete(rating);
	}

	/*@Test
	public void ratingTest() {
		Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

		// Save
		rating = ratingRepository.save(rating);
		Assert.assertNotNull(rating.getRating_id());
		Assert.assertTrue(rating.getOrder_number() == 10);

		// Update
		rating.setOrder_number(20);
		rating = ratingRepository.save(rating);
		Assert.assertTrue(rating.getOrder_number() == 20);

		// Find
		List<Rating> listResult = ratingRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = rating.getRating_id();
		ratingRepository.delete(rating);
		Optional<Rating> ratingList = ratingRepository.findById(id);
		Assert.assertFalse(ratingList.isPresent());
	}*/
}
