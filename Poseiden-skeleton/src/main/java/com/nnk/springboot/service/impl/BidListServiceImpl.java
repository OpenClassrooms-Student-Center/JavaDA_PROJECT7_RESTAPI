package com.nnk.springboot.service.impl;

import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidListServiceImpl {

    @Autowired
    private BidListRepository bidListRepository;


}
