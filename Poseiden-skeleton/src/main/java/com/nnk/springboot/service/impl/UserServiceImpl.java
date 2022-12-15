package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.security.UserPrincipal;
import com.nnk.springboot.service.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * contain all business service methods for UserService
 */
@Service
public class UserServiceImpl implements IUserService {

    /**
     * SLF4J/LOG4J LOGGER instance.
     */
    private static final Logger logger = LogManager.getLogger("UserServiceImpl");

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        UserPrincipal userPrincipal = new UserPrincipal(user);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        logger.debug("getting userPrincipal");
        return userPrincipal;

    }


    @Override
    public List<User> findAll() {
        logger.debug("getting all users");
        return userRepository.findAll();
    }

    @Override
    public User findById(Integer id) throws DataNotFoundException {
        logger.debug("fetching user by id:{}", id);
        return userRepository.findById(id).orElseThrow(() -> {
            logger.debug("Invalid user Id: {} ", id);
            return new DataNotFoundException("No User with id " + id + "  found ");
        });
    }

    @Override
    public void save(User user) {
        logger.debug("saving user{}", user.getFullname());
        userRepository.save(user);

    }

    @Override
    public void delete(User user) {
        logger.debug("deleting user:{}", user.getFullname());
        userRepository.delete(user);
    }


}
