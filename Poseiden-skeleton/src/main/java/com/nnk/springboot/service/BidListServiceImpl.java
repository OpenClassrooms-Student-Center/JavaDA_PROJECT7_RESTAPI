package com.nnk.springboot.service;

import com.nnk.springboot.NotFoundException;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;
import com.nnk.springboot.repositories.BidListRepository;
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
    public List<BidList> findAllBids() {
        return bidListRepository.findAll();
    }

    @Override
    public BidList findBidById(Integer id) {
        Optional<BidList> bidList = bidListRepository.findById(id);
        return bidList.orElseThrow(() -> new NotFoundException("BidList not found with id " + id));
    }

    @Override
    public BidList addBid(BidListDto bidListDto) {
        BidList bidList = new BidList(bidListDto);
        return bidListRepository.save(bidList);
    }

    @Override
    public BidList updateBid(Integer id, BidListDto bidListDto) {
        BidList bidList = findBidById(id);
        bidList.setAccount(bidListDto.getAccount());
        bidList.setType(bidListDto.getType());
        bidList.setBidQuantity(bidListDto.getBidQuantity());
        return bidListRepository.save(bidList);
    }

    @Override
    public void deleteBid(Integer id) {
        BidList bidList = findBidById(id);
        bidListRepository.delete(bidList);
    }

}

