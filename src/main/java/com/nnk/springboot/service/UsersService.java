package com.nnk.springboot.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Users;
import com.nnk.springboot.repositories.UsersRepository;

@Service
@Component
public class UsersService {

    // Récupération de notre logger.
    private static final Logger LOGGER = LogManager.getLogger(UsersService.class);

    @Autowired
    private LoggerApi loggerApi;

    @Autowired
    private UsersRepository userRepository;

    public UsersService() {
    }

    public UsersService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * @return List<Users>
     */
    public List<Users> findAll() {

        if (LOGGER.isDebugEnabled()) {
            String messageLoggerDebug = loggerApi.loggerDebug("List of users is : " + userRepository.findAll());
            LOGGER.debug(messageLoggerDebug);
        }

        return userRepository.findAll();
    }

    /**
     * @param id
     * @return Users
     */
    public Users findById(Integer id) {

        if (LOGGER.isDebugEnabled()) {
            String messageLoggerDebug = loggerApi
                    .loggerDebug("User with ID " + id + " is : " + userRepository.findById(id).orElse(null));
            LOGGER.debug(messageLoggerDebug);
        }

        return userRepository.findById(id).orElse(null);
    }

    /**
     * @param userName
     * @return Users
     */
    public Users getUser(String userName) {
        List<Users> usersList = findAll();
        for (Users users : usersList) {
            if (users.getUsername().equals(userName)) {

                if (LOGGER.isDebugEnabled()) {
                    String messageLoggerDebug = loggerApi
                            .loggerDebug("User name is : " + users.getUsername() + " " + users.getFullname());
                    LOGGER.debug(messageLoggerDebug);
                }

                return users;
            }
        }
        return null;
    }

    /**
     * @param users
     * @return Users
     */
    public Users save(Users users) {

        if (LOGGER.isDebugEnabled()) {
            String messageLoggerDebug = loggerApi
                    .loggerDebug("User saved is : " + users.getUsername() + " " + users.getFullname());
            LOGGER.debug(messageLoggerDebug);
        }

        return userRepository.save(users);
    }

    public void delete(Integer id) {

        if (LOGGER.isDebugEnabled()) {
            String messageLoggerDebug = loggerApi
                    .loggerDebug("ID of user deleted is : " + id);
            LOGGER.debug(messageLoggerDebug);
        }

        userRepository.deleteById(id);
    }

    public Users update(Users users) {

        if (LOGGER.isDebugEnabled()) {
            String messageLoggerDebug = loggerApi
                    .loggerDebug("User updated is : " + users.getUsername() + " " + users.getFullname());
            LOGGER.debug(messageLoggerDebug);
        }

        return userRepository.save(users);
    }

}