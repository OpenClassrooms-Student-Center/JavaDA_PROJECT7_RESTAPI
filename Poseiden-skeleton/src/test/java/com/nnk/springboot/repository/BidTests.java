package com.nnk.springboot.repository;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
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
//		BidList bid =new BidList("accounted", "Type Test", 10d); pourquoi il ne fonctionne pas avec le constructeur

		BidList bid = new BidList();
		bid.setAccount("accountTest");
		bid.setType("typetest");
		bid.setBidQuantity(10d);


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
}
