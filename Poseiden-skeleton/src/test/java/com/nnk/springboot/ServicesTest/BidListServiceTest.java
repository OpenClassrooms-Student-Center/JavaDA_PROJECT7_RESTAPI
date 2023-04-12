package com.nnk.springboot.ServicesTest;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import org.junit.Assert;
//import org.junit.Test; CET IMPORT FAIT ECHOUER LES TESTS!!!!
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("/application-test.properties")
@ExtendWith(SpringExtension.class)

public class BidListServiceTest {
	@Mock
	private BidListRepository bidListRepository;
	BidListService bidListService;

	BidList bidList = new BidList();


	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		bidList.setAccount("account");
		bidList.setBid_quantity(12.5);
		bidList.setType("type");

		bidListService = new BidListService(bidListRepository);
	}
	@Test
	public void findAllTest(){
		//ARRANGE
		List<BidList> listOfBidLists = new ArrayList<>();
		when(bidListRepository.findAll()).thenReturn(listOfBidLists);
		//ACT and ASSERT
		assertEquals(listOfBidLists, bidListService.findAll());
	}
	@Test
	public void validateNewBidListTest(){
		//ARRANGE
		when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);
		//ACT
		BidList registeredBidList = bidListService.validateNewBidList(bidList);
		//ASSERT
		assertNotNull(registeredBidList);
		assertEquals("account", registeredBidList.getAccount());
		assertEquals(12.5, registeredBidList.getBid_quantity());
		assertEquals("type", registeredBidList.getType());

		verify(bidListRepository, times(1)).save(registeredBidList);
	}
	@Test
	public void updateBidListTest() throws Exception {
		//ARRANGE
		/*Integer id = 1;
		BidList updatedBidListEntity = new BidList("updatedAccount", "updatedType", bidList.getBid_quantity());
		when(bidListRepository.findById(any(Integer)).
		//ACT
		BidList updatedBidListEntityWithService = bidListService.updateBidList(id, updatedBidListEntity);
		//ASSERT
		assertEquals(updatedBidListEntity, updatedBidListEntityWithService);*/

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
