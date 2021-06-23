package com.nnk.springboot;

import com.nnk.springboot.exceptions.NumberFormatException;
import com.nnk.springboot.forms.AddBidListForm;
import com.nnk.springboot.interfaces.BidListService;
import com.nnk.springboot.model.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class BidTests {

	@Autowired
	private BidListRepository bidListRepository;

	@Autowired
	private BidListService bidListService;

	@Test
	public void bidListTest() {
		BidList bid = new BidList("Account", "Type Test", 10d);

		// Save
		bid = bidListRepository.save(bid);
		Assert.assertNotNull(bid.getBidListId());
		Assert.assertEquals(bid.getBidQuantity(), 10d, 10d);

		// Update
		bid.setBidQuantity(20d);
		bid = bidListRepository.save(bid);
		Assert.assertEquals(bid.getBidQuantity(), 20d, 20d);

		// Find
		List<BidList> listResult = bidListRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = bid.getBidListId();
		bidListRepository.delete(bid);
		Optional<BidList> bidList = bidListRepository.findById(id);
		Assert.assertFalse(bidList.isPresent());
	}

	@Test
	public void deleteBidListTest() {
		BidList bid = new BidList("Account", "Type Test", 10);
		bid = bidListRepository.save(bid);
		// Delete
		Integer id = bid.getBidListId();
		bidListService.deleteBid(id);
		Optional<BidList> bidList = bidListRepository.findById(id);

		Assert.assertFalse(bidList.isPresent());


	}

	@Test
	public void updateBidListTest() {
		//given
		BidList bid = new BidList("Account", "Type Test", 10);
		bid = bidListRepository.save(bid);
		Integer id = bid.getBidListId();

		BidList bidList = new BidList("Account", "Type Test", 20);

		//when
		bidListService.updateBid(id, bidList);

		//then
		Assert.assertEquals(bid.getBidQuantity(), 20d, 20d);
	}

	@Test
	public void validateBidListTest() {
		//given
		BidList addBidList = new BidList("Account Test", "Type Test", 20);

		//when
		bidListService.validate(addBidList);

		//then
		BidList bid = bidListRepository.findBidListIdByAccount("Account Test");
		Optional<BidList> bidList = bidListRepository.findById(bid.getBidListId());
		Assert.assertTrue(bidList.isPresent());
	}
}
