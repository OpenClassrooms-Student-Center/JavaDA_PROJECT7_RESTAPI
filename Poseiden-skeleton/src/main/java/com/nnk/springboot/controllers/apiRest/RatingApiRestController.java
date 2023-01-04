package com.nnk.springboot.controllers.apiRest;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.service.IRatingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RatingApiRestController {

    /**
     * SLF4J Logger instance.
     */
    private static final Logger logger = LogManager.getLogger("RatingApiRestController");


    private IRatingService ratingService;

    public RatingApiRestController(IRatingService ratingService) {
        this.ratingService = ratingService;
    }


    /**
     * @return
     */
    @GetMapping("/ratings/api")
    public ResponseEntity<List<Rating>> showRestRating() {
        logger.info("@RequestMapping(\"/ratings/api\")");
        return new ResponseEntity<>(ratingService.findAll(), HttpStatus.OK);
    }

    /**
     * @param id
     * @return rating httpStatus.Ok
     * @throws DataNotFoundException
     */
    @GetMapping("/rating/api/{id}")
    public ResponseEntity<Optional<Rating>> showRestRatingById(@PathVariable int id) throws DataNotFoundException {
        logger.info("@RequestMapping(\"/rating/api/{id}\")");
        Optional<Rating> rating = ratingService.findById(id);

        return new ResponseEntity<>(ratingService.findById(id), HttpStatus.OK);
    }

    /**
     * @param rating
     * @return add bidList
     */
    @PostMapping("/rating/api")
    public Rating addRestRating(@RequestBody Rating rating) {
        logger.info("@PostMapping(\"/rating/api\")");
        ratingService.save(rating);
        return rating;
    }


    /**
     * @param rating
     * @return
     */
    @PutMapping("/rating/api")
    public Rating uploadRestRating(@RequestBody Rating rating) {
        logger.info("@PutMapping(\"/rating/api/{}\")  Id " + rating.getId() + " as modified", rating.getId());

        return ratingService.update(rating);
    }


    /**
     * @param ratingId
     * @return
     * @throws DataNotFoundException
     */
    @DeleteMapping("/rating/api/{ratingId}")
    public String deleteRestRating(@PathVariable int ratingId) throws DataNotFoundException {
        logger.info("@DeleteMapping(\"/rating/api/{ratingId}\")");

        ratingService.delete(ratingId);
        return "delete rating whit id: " + ratingId + " success";
    }


}
