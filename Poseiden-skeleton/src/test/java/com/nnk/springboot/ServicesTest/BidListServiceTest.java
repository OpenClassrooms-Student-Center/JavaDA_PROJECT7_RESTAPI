package com.nnk.springboot.ServicesTest;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("/application-test.properties")
public class BidListServiceTest {
	BidListService bidListService;
	@Mock BidListRepository bidListRepository;
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		bidListService = new BidListService(bidListRepository);
	}
	@Test
	public void findAllTest(){
		//ARRANGE

		//ACT
		//ASSERT


	}
	@Test
	public void validateNewBidListTest(){

	}
	@Test
	public void updateBidListTest(){

	}

	/*@Autowired
	private BidListRepository bidListRepository;

	@Test
	public void bidListTest() {
		BidList bid = new BidList("Account Test", "Type Test", 10d);

		// Save
		bid = bidListRepository.save(bid);
		Assert.assertNotNull(bid.getBid_list_id());
		Assert.assertEquals(bid.getBid_quantity(), 10d, 10d);

		// Update
		bid.setBid_quantity(20d);
		bid = bidListRepository.save(bid);
		Assert.assertEquals(bid.getBid_quantity(), 20d, 20d);

		// Find
		List<BidList> listResult = bidListRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = bid.getBid_list_id();
		bidListRepository.delete(bid);
		Optional<BidList> bidList = bidListRepository.findById(id);
		Assert.assertFalse(bidList.isPresent());
	}*/
}
