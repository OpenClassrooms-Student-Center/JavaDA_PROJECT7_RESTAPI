package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListService {

    @Autowired
    BidListRepository ratingRepository;

    public List<BidList> findAll() {
        return ratingRepository.findAll();
    }

    public Optional<BidList> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        return ratingRepository.findById(id);
    }

    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        ratingRepository.deleteById(id);
    }

    public void save(BidList rating) {
        ratingRepository.save(rating);
    }
}
