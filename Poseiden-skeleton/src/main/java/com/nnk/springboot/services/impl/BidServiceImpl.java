package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.Bid;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.BidRepository;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.BidService;
import com.nnk.springboot.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * The type Trade service.
 */
@Service
@AllArgsConstructor
public class BidServiceImpl extends AbstractCrudService<Bid> implements BidService {
    /**
     * The Repository.
     */
    BidRepository repository;

    @Override
    protected JpaRepository<Bid, Integer> getRepository() {
        return repository;
    }
}
