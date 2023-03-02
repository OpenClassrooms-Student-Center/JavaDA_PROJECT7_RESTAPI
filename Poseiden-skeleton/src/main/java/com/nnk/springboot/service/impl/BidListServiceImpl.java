package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.BidListService;
import com.nnk.springboot.util.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class that handles all BidList related business logic.
 */
@Slf4j
@Service
public class BidListServiceImpl implements BidListService {

    private final BidListRepository bidListRepository;

    public BidListServiceImpl(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }


    @Override
    public List<BidList> findAll() {
        log.info("Retrieving all BidLists");
        return bidListRepository.findAll();
    }

    @Override
    public BidList findById(Integer id) {
        log.info("Retrieving BidList with id {}", id);
        Optional<BidList> bidList = bidListRepository.findById(id);
        return bidList.orElseThrow(() -> new NotFoundException("BidList not found with id " + id));
    }

    @Override
    public BidList create(BidListDto bidListDto) {
        log.info("Creating new BidList");
        BidList bidList = new BidList(bidListDto);
        return bidListRepository.save(bidList);
    }

    @Override
    public BidList update(Integer id, BidListDto bidListDto) {
        log.info("Updating BidList with id {}", id);
        BidList bidList = findById(id);
        bidList.setAccount(bidListDto.getAccount());
        bidList.setType(bidListDto.getType());
        bidList.setBidQuantity(bidListDto.getBidQuantity());
        return bidListRepository.save(bidList);
    }

    @Override
    public void delete(Integer id) {
        log.info("Deleting BidList with id {}", id);
        BidList bidList = findById(id);
        bidListRepository.delete(bidList);
    }
}