package com.nnk.springboot;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exceptions.RequestedObjectNotFoundException;
import com.nnk.springboot.services.BidListService;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;



@SpringBootTest
public class BidTests {

	@Autowired
	private BidListService bidListService;

	@Test
	@Transactional
	public void bidListSaveTest() throws Exception {
		BidList bid = new BidList("Account Test", "Type Test", 10d);

		// Save
		bid = bidListService.addBidList(bid);
		
		assertNotNull(bid.getBidlistid());
		assertEquals(bid.getBidQuantity(), 10d);

		// Update
		bid.setBidQuantity(20d);
		bid = bidListService.addBidList(bid);
		assertEquals(bid.getBidQuantity(), 20d, 20d);

		// Find
		List<BidList> listResult = bidListService.findAll();
		assertEquals(true,listResult.size() > 0);

		// Delete
		Integer id = bid.getBidlistid();
		bidListService.deleteBidList(bid.getBidlistid());
		BidList bidList;
		try {
			bidList = bidListService.findById(id);
		} catch (RequestedObjectNotFoundException e) {
			bidList=null;
		}
		assertNull(bidList);
	}
}
