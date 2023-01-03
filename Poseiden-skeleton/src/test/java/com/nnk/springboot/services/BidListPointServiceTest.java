package com.nnk.springboot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@SpringBootTest
public class BidListPointServiceTest {

    private IBidListService bidListService;

    private static BidList bidListToAdd = new BidList();

    @Mock
    private BidListRepository bidListRepository;

    @BeforeEach
    public void setUp() {
	MockitoAnnotations.openMocks(this);

	bidListService = new BidListPointServiceImpl(bidListRepository);
	bidListToAdd.setBidListId(0);
    }

    @Test
    public void shouldGetAllBidLists() {
	List<BidList> bidListList = new ArrayList<>();
	bidListList.add(bidListToAdd);

	when(bidListRepository.findAll()).thenReturn(bidListList);

	List<BidList> bidLists = (List<BidList>) bidListService.getBidLists();
	assertEquals(bidLists.get(0).getBidListId(), bidListToAdd.getBidListId());
    }

    @Test
    public void shouldGetOneBidList() {
	when(bidListRepository.findById(bidListToAdd.getBidListId())).thenReturn(Optional.of(bidListToAdd));

	Optional<BidList> bidList = bidListService.getBidListById(bidListToAdd.getBidListId());

	assertEquals(bidList.get().getBidListId(), bidListToAdd.getBidListId());
    }

    @Test
    public void shouldSaveBidList() {
	when(bidListRepository.save(any(BidList.class))).thenReturn(bidListToAdd);

	BidList bidListUser = bidListService.saveBidList(bidListToAdd);

	assertEquals(bidListUser.getBidListId(), bidListToAdd.getBidListId());
    }

    @Test
    public void shouldDeleteBidList() {
	bidListService.deleteBidListById(bidListToAdd.getBidListId());

	verify(bidListRepository, times(1)).deleteById(bidListToAdd.getBidListId());
    }

}
