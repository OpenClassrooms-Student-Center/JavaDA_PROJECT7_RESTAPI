package com.nnk.springboot.service.impl;

import com.nnk.springboot.NotFoundException;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.BidListService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListServiceImpl implements BidListService {

    private final BidListRepository bidListRepository;

    public BidListServiceImpl(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    @Override
    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

    @Override
    public BidList findById(Integer id) {
        Optional<BidList> bidList = bidListRepository.findById(id);
        return bidList.orElseThrow(() -> new NotFoundException("BidList not found with id " + id));
    }

    @Override
    public BidList create(BidListDto bidListDto) {
        BidList bidList = new BidList(bidListDto);
        return bidListRepository.save(bidList);
    }

    @Override
    public BidList update(Integer id, BidListDto bidListDto) {
        BidList bidList = findById(id);
        bidList.setAccount(bidListDto.getAccount());
        bidList.setType(bidListDto.getType());
        bidList.setBidQuantity(bidListDto.getBidQuantity());
        return bidListRepository.save(bidList);
    }

    @Override
    public void delete(Integer id) {
        BidList bidList = findById(id);
        bidListRepository.delete(bidList);
    }

}

