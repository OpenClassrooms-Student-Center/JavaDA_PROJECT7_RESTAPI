package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
//@ActiveProfiles("localtest")
class BidListServiceImplTest {


    @InjectMocks
    private BidListServiceImpl bidListService;

    @Mock
    private BidListRepository bidListRepository;


//    @Captor
//    private ArgumentCaptor<User> userCaptor;

    private BidList bidList1;
    private BidList bidList2;

//
//    private static Logger logCaptor;


    @BeforeEach
    void setUp() {
        bidList1 = new BidList("NewAccount1", "Type1", 1D);
        bidList2 = new BidList("NewAccount2", "Type2", 2D);
        bidList1.setBidListId(1);
        bidList2.setBidListId(2);

    }


    @Test
    void findAll() {
        //Given
        List<BidList> allBids = List.of(
                new BidList("NewAccount1", "Type1", 1D),
                new BidList("NewAccount2", "Type2", 2D)
        );

        when(bidListRepository.findAll()).thenReturn(allBids);


        //When
        List<BidList> bidResult = bidListService.findAll();

        //Then
        Assertions.assertEquals(bidResult.size(), 2);

    }

    @Test
    void findByIdBidListTest() {
        //Given
        BidList bid = new BidList();
        bid.setBidListId(1);
        bid.setAccount("NewAccount1");
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));

        //When
        Optional<BidList> bidResult = bidListService.findById(1);

        //Then
        Assertions.assertEquals(bidResult.get().getAccount(), "NewAccount1");
    }

    @Test
    public void findByIdBidListTest_ShouldThrowException() throws DataNotFoundException {
        //Given
        when(bidListRepository.findById(3)).thenReturn(Optional.empty());
        // When //Then
        assertThrows(DataNotFoundException.class, () -> bidListService.findById(3));
    }

    @Test
    void saveBidListTest() {
        //Given
        BidList bid = new BidList();
        bid.setBidListId(1);
        bid.setAccount("NewAccount1");
        when(bidListRepository.save(any(BidList.class))).thenReturn((bid));

        //When
        BidList bidResult = bidListService.save(bid);

        //Then
        Assertions.assertEquals(bidResult.getAccount(), "NewAccount1");

    }

    @Test
    void updateBidListTest() {

        //Given
        BidList bid = new BidList();
        bid.setBidListId(1);
        bid.setAccount("NewAccount1");
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));

        when(bidListRepository.save(any(BidList.class))).thenReturn((bid));

        //When
        BidList bidResult = bidListService.update(bid);

        //Then
        Assertions.assertEquals(bidResult.getAccount(), "NewAccount1");


    }

    @Test
    public void updateBidListTest_ShouldThrowException() throws DataNotFoundException {

        //Given  // When //Then
        assertThrows(DataNotFoundException.class, () -> bidListService.update(bidList1));
    }

    @Test
    void deleteBidListTest() {

        //Given
        BidList bid = new BidList();
        bid.setBidListId(1);
        bid.setAccount("NewAccount1");
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));
        // ACT
        bidListService.delete(1);
        //ASSERT
        verify(bidListRepository).findById(1);
    }

    @Test
    public void deleteBidListTest_ShouldThrowException() throws DataNotFoundException {

        //Given // When //Then
        assertThrows(DataNotFoundException.class, () -> bidListService.delete(bidList1.getBidListId()));
    }

}