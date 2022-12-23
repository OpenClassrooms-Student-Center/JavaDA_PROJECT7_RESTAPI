package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.IUserService;
import com.nnk.springboot.web.dto.UserRegistrationDto;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * contain all business service methods for UserService
 */
@Service
@Transactional
public class UserServiceImpl implements IUserService {

    /**
     * SLF4J LOGGER instance.
     */
    private static final Logger logger = LogManager.getLogger("UserServiceImpl");

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public User saveUser(UserRegistrationDto registrationDto, String password) throws UsernameNotFoundException {
        if (userExist(registrationDto.getUsername())) {
            throw new UsernameNotFoundException(
                    "This full name : " + registrationDto.getFullname()+ " is present in database");
        } else {

            User user = new User(registrationDto.getUsername(),
                    registrationDto.getFullname(),
                    password,registrationDto.getRole());

//            User user = new User(registrationDto.getUsername(),
//                    registrationDto.getFullname(),
//                    password);

            return userRepository.save(user);
        }
    }

    private boolean userExist(String username) {
        return userRepository.existsByUsername(username);

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findAll() {
        logger.debug("getting all users");
        return userRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findById(Integer id) throws DataNotFoundException {
        logger.debug("fetching user by id:{}", id);
        return userRepository.findById(id).orElseThrow(() -> {
            logger.debug("Invalid user Id: {} ", id);
            return new DataNotFoundException("No User with id " + id + "  found ");
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(User user) {
        logger.debug("saving user{}", user.getFullname());
        userRepository.save(user);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(User user) throws UsernameNotFoundException {
        logger.debug("update user:{}", user.getFullname());
        Optional<User> isAlreadyUser = userRepository.findById(user.getId());
        if (isAlreadyUser.isPresent()) {
            userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("No User " + user + " present in dataBase ");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(User user) {
        logger.debug("deleting user:{}", user.getFullname());
        userRepository.delete(user);
    }


}
