package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.BidListService;
import com.nnk.springboot.util.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(BidListServiceImpl.class)
class BidListServiceImplTest {

    private static final BidList bidList = new BidList();
    @MockBean
    private BidListRepository bidListRepository;
    @Autowired
    private BidListService bidListService;

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
    final void testFindByIdNotFound() throws NotFoundException {
        assertThrows(NotFoundException.class, () -> bidListService.findById(0));
    }

    @Test
    final void testDeleteById() {
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));
        bidListService.delete(1);

        verify(bidListRepository, times(1)).delete(any(BidList.class));
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
    final void testCreateBidList() {
        BidListDto bidListDto = new BidListDto();
        bidListDto.setAccount("Account");
        bidListDto.setType("Type");
        bidListDto.setBidQuantity(10.0);

        when(bidListRepository.save(any(BidList.class))).thenReturn(new BidList());
        BidList result = bidListService.create(bidListDto);

        verify(bidListRepository, times(1)).save(any(BidList.class));
        assertNotNull(result);
    }


    @Test
    final void testUpdateBidList() {
        BidListDto bidListDto = new BidListDto();
        bidListDto.setAccount("Account");
        bidListDto.setType("Type");
        bidListDto.setBidQuantity(5.0);
        BidList bidList = new BidList(bidListDto);

        when(bidListRepository.findById(1)).thenReturn(Optional.of(BidListServiceImplTest.bidList));
        when(bidListRepository.getById(1)).thenReturn(BidListServiceImplTest.bidList);
        when(bidListRepository.save(bidList)).thenReturn(bidList);
        bidListService.update(1, bidListDto);

        assertThat(BidListServiceImplTest.bidList.getAccount()).isEqualTo("Account");
        assertThat(BidListServiceImplTest.bidList.getType()).isEqualTo("Type");
        assertThat(BidListServiceImplTest.bidList.getBidQuantity()).isEqualTo(5.0);
    }

    @Test
    final void testUpdateBidListThrowEntityNotFoundException() {
        when(bidListRepository.findById(1)).thenReturn(Optional.empty());
        BidListDto bidListDto = new BidListDto();
        bidListDto.setAccount("Account");
        bidListDto.setType("Type");
        bidListDto.setBidQuantity(10.0);

        assertThrows(NotFoundException.class, () -> bidListService.update(1, bidListDto));
    }
}