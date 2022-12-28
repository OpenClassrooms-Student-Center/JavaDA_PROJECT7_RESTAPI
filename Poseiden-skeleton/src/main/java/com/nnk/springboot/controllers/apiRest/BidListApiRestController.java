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
    private static final Logger logger = LogManager.getLogger("BidListApiRestController");


    private IBidListService bidListService;

    public BidListApiRestController(IBidListService bidListService) {
        this.bidListService = bidListService;
    }

    /**
     * @return
     */
    @GetMapping("/bidList/api")
    public ResponseEntity<List<BidList>> showRestBid() {
        logger.info("@RequestMapping(\"/bidList/api\")");
        return new ResponseEntity<>(bidListService.findAll(), HttpStatus.OK);
    }

    /**
     * @param id
     * @return bid httpStatus.Ok
     * @throws DataNotFoundException
     */
    @GetMapping("/bidList/api/{id}")
    public ResponseEntity<Optional<BidList>> showRestBidById(@PathVariable int id) throws DataNotFoundException {
        logger.info("@RequestMapping(\"/bidList/api/{id}\")");
        Optional<BidList> bid = bidListService.findById(id);
        if (bid.isEmpty()) {
            throw new DataNotFoundException("Id not present");

        }
        return new ResponseEntity<>(bidListService.findById(id), HttpStatus.OK);
    }

    /**
     * @param bidList
     * @return add bidList
     */
    @PostMapping("/bidList/api")
    public BidList addRestBid(@RequestBody BidList bidList) {
        logger.info("@PostMapping(\"/bidList/api\")");
        bidListService.save(bidList);
        //TODO: cette metode return BidLIst Object
        return bidList;
    }


    @PutMapping("/bidList/api")
    public BidList uploadRestBid(@RequestBody BidList bidList) {
        logger.info("@PutMapping(\"/bidList/api/{}\")  Id " + bidList + " as modified", bidList.getBidListId());

        return bidListService.update(bidList);
    }

    /**
     * @param bidListId
     * @return
     * @throws DataNotFoundException
     */
    @DeleteMapping("/bidList/api/{bidListId}")
    public String deleteRestBid(@PathVariable int bidListId) throws DataNotFoundException {
        logger.info("@DeleteMapping(\"/bidList/api/{bidListId}\")");

        bidListService.delete(bidListId);
        return "delete bid by id: " + bidListId + " success";
    }

}
