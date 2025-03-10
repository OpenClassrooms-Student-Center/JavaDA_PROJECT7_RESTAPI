package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BidListServiceTests {

    @Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListService bidListService;

    private BidList bid;

    @BeforeEach
    void setUp() {
        bid = new BidList();
        bid.setBidListId(1);
        bid.setAccount("Account Test");
        bid.setType("Type Test");
        bid.setBidQuantity(10d);
    }

    @Test
    void testGetAllBidList() {
        when(bidListRepository.findAll()).thenReturn(Arrays.asList(bid));

        List<BidList> result = bidListService.getAllBidList();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Account Test", result.get(0).getAccount());
    }

    @Test
    void testGetBidListById() {
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));

        Optional<BidList> result = bidListService.getBidListById(1);
        assertTrue(result.isPresent());
        assertEquals("Account Test", result.get().getAccount());
    }

    @Test
    void testSaveBidList() {
        when(bidListRepository.save(bid)).thenReturn(bid);

        BidList savedBid = bidListService.saveBidList(bid);
        assertNotNull(savedBid);
        assertEquals(10d, savedBid.getBidQuantity());
    }

    @Test
    void testDeleteBidListById() {
        doNothing().when(bidListRepository).deleteById(1);
        bidListService.deleteBidListById(1);
        verify(bidListRepository, times(1)).deleteById(1);
    }
}
