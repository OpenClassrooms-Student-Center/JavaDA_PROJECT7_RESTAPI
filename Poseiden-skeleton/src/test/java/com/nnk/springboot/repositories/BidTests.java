package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BidTests {

	@Autowired
	private BidListRepository bidListRepository;

	@Test
	public void bidListTest() {
		BidList bid = new BidList();
		bid.setAccount("Account Test");
		bid.setType("Type Test");
		bid.setBidQuantity(10d);

		// Save
		bid = bidListRepository.save(bid);
		Integer generatedId = bid.getBidListId();

		Assert.assertNotNull(generatedId);
		Assert.assertTrue(generatedId > 0);
		Assert.assertEquals(10d, bid.getBidQuantity(), 0.001);

		// Update
		bid.setBidQuantity(20d);
		BidList updatedBid = bidListRepository.save(bid);
		Assert.assertEquals(20d, updatedBid.getBidQuantity(), 0.001);

		// Find
		List<BidList> listResult = bidListRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// FindById
		Optional<BidList> foundBid = bidListRepository.findById(generatedId);
		Assert.assertTrue(foundBid.isPresent());

		// Delete
		bidListRepository.delete(bid);
		Optional<BidList> deletedBid = bidListRepository.findById(generatedId);
		Assert.assertFalse(deletedBid.isPresent());
	}
}