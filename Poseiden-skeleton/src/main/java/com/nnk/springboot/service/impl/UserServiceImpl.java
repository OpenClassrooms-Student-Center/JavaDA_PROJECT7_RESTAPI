package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.IUserService;
import com.nnk.springboot.web.dto.UserRegistrationDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public boolean userExist(String username) {
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
    public Optional<User> findById(Integer id) throws DataNotFoundException {
        logger.debug("fetching user by id:{}", id);
        return Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> {
            logger.debug("Invalid user Id: {} ", id);
            return new DataNotFoundException("No User with id " + id + "  found ");
        }));
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public User save(User user) {
        logger.debug("saving user{}", user.getFullname());
        userRepository.save(user);

        return user;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public User update(User user) throws UsernameNotFoundException {
        logger.debug("update user:{}", user.getFullname());
        Optional<User> isAlreadyUser = userRepository.findById(user.getId());
        if (isAlreadyUser.isPresent()) {
            userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("No User " + user + " present in dataBase ");
        }
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(int userId) throws DataNotFoundException {
        logger.debug("deleting user:{}", userId);
        User deleteUser = userRepository.findById(userId).orElseThrow(() -> {
            throw new DataNotFoundException("Id " + userId + " Not Present in Data Base");
        });

        userRepository.deleteById(deleteUser.getId());
    }


}
