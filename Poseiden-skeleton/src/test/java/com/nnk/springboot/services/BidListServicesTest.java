package com.nnk.springboot.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.security.UserDetailsServiceImpl;

import Exceptions.AlreadyExistException;

@WebMvcTest(BidListService.class)
public class BidListServicesTest {
	
	@MockBean
	private BidListRepository bidListRepository;
	
	@MockBean
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private BidListService bidListService;
	
	private static BidList bidList = new BidList();
	
	@BeforeEach
	private void init() {
		bidList.setBidListId(1);
		bidList.setAccount("Test");
		bidList.setType("Test");
        bidList.setBidQuantity(10.0);
	}
	

	@Test
	final void testFindById() {
		when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));
		BidList foundBidList = bidListService.findById(1);
		assertThat(foundBidList).isEqualTo(bidList);
		
	}
	
	@Test
	final void testFindByIdNotFound() throws EntityNotFoundException {
		assertThrows(EntityNotFoundException.class, () -> bidListService.findById(0));
	}
	
	@Test
	final void testDeleteById() {
		when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));
		bidListService.deleteById(1);
		verify(bidListRepository).deleteById(1);
	}
	
	@Test
	final void testFindAll() {
		List<BidList> findAll = new ArrayList<>();
		findAll.add(bidList);
		when(bidListRepository.findAll()).thenReturn(findAll);
		List<BidList> foundBidList = bidListService.findAll();
		assertThat(foundBidList).isEqualTo(findAll);
	}
	
	@Test
	final void testCreateBidList() throws AlreadyExistException {
		BidList createBidList = new BidList();
		createBidList.setBidListId(2);
		createBidList.setAccount("Account");
		createBidList.setType("Type");
		createBidList.setBidQuantity(10.0);
		when(bidListRepository.findByAccount(Mockito.anyString())).thenReturn(null);
		when(bidListRepository.save(createBidList)).thenReturn(createBidList);
		bidListService.createBidList(createBidList);
		verify(bidListRepository).save(createBidList);
	}
	
	@Test
	final void testCreateBidListThrowAlreadyExistException() {
		when(bidListRepository.findByAccount(Mockito.anyString())).thenReturn(bidList);
		assertThrows(AlreadyExistException.class, () -> bidListService.createBidList(bidList));
	}
	
	@Test
	final void testUpdateBidList() {
		BidList toUpdateBidList = new BidList();
		toUpdateBidList.setAccount("Account");
		toUpdateBidList.setType("Type");
		toUpdateBidList.setBidQuantity(5.0);
		when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));
		when(bidListRepository.getById(1)).thenReturn(bidList);
		when(bidListRepository.save(toUpdateBidList)).thenReturn(toUpdateBidList);
		bidListService.updateBidList(toUpdateBidList, 1);
		assertThat(bidList.getAccount()).isEqualTo("Account");
		assertThat(bidList.getType()).isEqualTo("Type");
		assertThat(bidList.getBidQuantity()).isEqualTo(5.0);
	}
	
	@Test
	final void testUpdateBidListThrowEntityNotFoundException() {
		when(bidListRepository.findById(1)).thenReturn(null);
		assertThrows(EntityNotFoundException.class, () -> bidListService.updateBidList(bidList, 1));
	}

}
