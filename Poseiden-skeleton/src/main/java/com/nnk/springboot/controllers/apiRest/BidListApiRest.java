package com.nnk.springboot.controllers.apiRest;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.IBidListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class BidListApiRest {


    private IBidListService bidListService;

    public BidListApiRest(IBidListService bidListService) {
        this
                .bidListService = bidListService;
    }

    @GetMapping("/bidList/showAll")
    public ResponseEntity<List<BidList>> showRestBid() {
        return new ResponseEntity<>(bidListService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/bidList/addRest")
    public String addRestBid(@RequestBody BidList bidList) {
        bidListService.save(bidList);
        return "success";
    }

}
