package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListService {

    private final BidListRepository bidListRepository;

    @Autowired
    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

    public Optional<BidList> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        return bidListRepository.findById(id);
    }

    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        findById(id).orElseThrow((() -> new IllegalArgumentException("Invalid bidList Id:" + id)));
        bidListRepository.deleteById(id);
    }

    public void save(BidList bidList) {
        bidListRepository.save(bidList);
    }
}
