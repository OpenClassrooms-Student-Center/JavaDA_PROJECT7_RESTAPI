package com.nnk.springboot.controllers.apiRest;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.service.IBidListService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BidListApiRest {

    /**
     * SLF4J Logger instance.
     */
    private static final Logger logger = LogManager.getLogger("BidListApiRest");


    private IBidListService bidListService;

    public BidListApiRest(IBidListService bidListService) {
        this.bidListService = bidListService;
    }

    @GetMapping("/bidList/api")
    public ResponseEntity<List<BidList>> showRestBid() {
        logger.info("@RequestMapping(\"/bidList/api\")");
        return new ResponseEntity<>(bidListService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/bidList/api/{id}")
    public ResponseEntity<Optional<BidList>> showRestBidById(@PathVariable int id) throws DataNotFoundException {
        logger.info("@RequestMapping(\"/bidList/api/{id}\")");
        return new ResponseEntity<>(bidListService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/bidList/api")
    public String addRestBid(@RequestBody BidList bidList) {
        logger.info("@PostMapping(\"/bidList/api\")");
        bidListService.save(bidList);
        return "add " + bidList.getBidListId() + " success";
    }

    @PutMapping("/bidList/api/{id}")
    public String uploadRestBid(@RequestBody BidList bidList, @PathVariable int id) throws DataNotFoundException {
        logger.info("@PutMapping(\"/bidList/api/{id}\")");
        Optional<BidList> uploadBid = bidListService.findById(id);
        bidListService.update(uploadBid.get());
        return "Id " + id + " as modified";
    }

    @DeleteMapping("/bidList/api/{id}")
    public String deleteRestBid(@RequestBody BidList bidList, @PathVariable int id) throws DataNotFoundException {
        logger.info("@DeleteMapping(\"/bidList/api/{id}\")");
        Optional<BidList> deleteBid = bidListService.findById(id);
        bidListService.delete(deleteBid.get());
        return "delete bid id: " + id + " success";
    }

}
