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
public class BidListApiRestController {

    /**
     * SLF4J Logger instance.
     */
    private static final Logger logger = LogManager.getLogger("BidListApiRest");


    private IBidListService bidListService;

    public BidListApiRestController(IBidListService bidListService) {
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
        Optional<BidList> bid = bidListService.findById(id);
        if (bid.isEmpty()) {
            throw new DataNotFoundException("Id not present");

        }
        return new ResponseEntity<>(bidListService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/bidList/api")
    public String addRestBid(@RequestBody BidList bidList) {
        logger.info("@PostMapping(\"/bidList/api\")");
        bidListService.save(bidList);
        //TODO: cette metode return BidLIst Object
        return "add " + bidList.getBidListId() + " success";
    }

    @PutMapping("/bidList/api/{bidListId}")
    public BidList uploadRestBid(@RequestBody BidList bidList, @PathVariable int bidListId) {
        logger.info("@PutMapping(\"/bidList/api/{}\")  Id " + bidListId + " as modified", bidListId);

        return bidListService.update(bidList);
    }

    @DeleteMapping("/bidList/api/{bidListId}")
    public String deleteRestBid(@PathVariable int bidListId) throws DataNotFoundException {
        logger.info("@DeleteMapping(\"/bidList/api/{bidListId}\")");

        bidListService.delete(bidListId);
        return "delete bid id: " + bidListId + " success";
    }

}
