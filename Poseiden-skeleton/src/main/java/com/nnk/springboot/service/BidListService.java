package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class BidListService implements IBidListService {

    private final BidListRepository bidListRepository;

    @Override
    public BidList findById(Integer bidlistId) {
        Optional<BidList> optionalBidList = bidListRepository.findById(bidlistId);
        if(optionalBidList.isPresent()) {
            return optionalBidList.get();
        }
        return null;
    }

    @Override
    public BidList saveBidlist(BidList bidList) {
        return bidListRepository.save(bidList);
    }

    @Override
    public BidList updateBidList(BidList bidList) {
        return null;
    }

    @Override
    public boolean deleteBidList(BidList bidList) {
        bidListRepository.delete(bidList);
        return findById(bidList.getBidListId()) == null;
    }
}
