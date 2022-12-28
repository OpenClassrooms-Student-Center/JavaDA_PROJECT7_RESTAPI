package com.nnk.springboot.controllers.apiRest;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.service.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserApiRestController {

    /**
     * SLF4J Logger instance.
     */
    private static final Logger logger = LogManager.getLogger("UserApiRestController");


    /**
     * Instance of IUserService
     */
    private IUserService userService;

    /**
     * @param userService
     */
    public UserApiRestController(IUserService userService) {
        this.userService = userService;
    }


    /**
     * @return
     */
    @GetMapping("/users/api")
    public ResponseEntity<List<User>> showRestUser() {
        logger.info("@RequestMapping(\"/users/api\")");
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    /**
     * @param id
     * @return user httpStatus.Ok
     * @throws DataNotFoundException
     */
    @GetMapping("/user/api/{id}")
    public ResponseEntity<Optional<User>> showRestUserById(@PathVariable int id) throws DataNotFoundException {
        logger.info("@RequestMapping(\"/user/api/{id}\")");
        Optional<User> users = userService.findById(id);
        if (users.isEmpty()) {
            throw new DataNotFoundException("Id not present");

        }
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    /**
     * @param user
     * @return add bidList
     */
    @PostMapping("/user/api")
    public User addRestUser(@RequestBody User user) {
        logger.info("@PostMapping(\"/user/api\")");
        userService.save(user);
        return user;
    }


    @PutMapping("/user/api")
    public User uploadRestUser(@RequestBody User user) {
        logger.info("@PutMapping(\"/user/api/{}\")  Id " + user + " as modified", user.getId());

        return userService.update(user);
    }

    /**
     * @param userId
     * @return
     * @throws DataNotFoundException
     */
    @DeleteMapping("/user/api/{userId}")
    public String deleteRestUser(@PathVariable int userId) throws DataNotFoundException {
        logger.info("@DeleteMapping(\"/user/api/{userId}\")");

        userService.delete(userId);
        return "delete user by id: " + userId + " success";
    }



}
