package com.poseidon.api.service;

import com.poseidon.api.model.User;
import com.poseidon.api.model.dto.UserDto;
import com.poseidon.api.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    ModelMapper modelMapper;

    /**
     * Spring Security implemented method
     * Allows user connection by using the username provided
     *
     * @param username							The username that tries to connect to the application
     * @throws UsernameNotFoundException		If the username doesn't exist in database
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return new User(user.get());
        }
        throw new UsernameNotFoundException("User not found : " + username);
    }

    /**
     * Get a list of every user registered on the application
     *
     * @return									List<User> : All existing users
     */
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Find a user by its ID
     *
     * @param userId
     * @return									 User if it exists in database
     */
    public User findUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (userId != null && user.isPresent()) {
            return user.get();
        }
        throw new UsernameNotFoundException("Could not find user with id : " + userId);
    }

    /**
     * Create a user and persist it into the database
     *
     * @param userEntity						The user entity we want to create
     * @return									True if the creation was successful
     */
    public boolean createUser(User userEntity) {
        if (userEntity != null && !userRepository.findByUsername(userEntity.getUsername()).isPresent()) {
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userRepository.save(userEntity);

            log.info("[SERVICE] New user with username : " + userEntity.getUsername() + " and role " + userEntity.getRole() + " has been created");
            return true;
        }
        throw new UsernameNotFoundException("Username is already taken");
    }

    /**
     * Updates an existing user and persist it into the database
     *
     * @param userId
     * @param userEntityUpdated
     * @return									True if the update was successful
     */
    public boolean updateUser(Long userId, User userEntityUpdated) {
        Optional<User> user = userRepository.findById(userId);
        if (userId != null && user.isPresent()) {

            userEntityUpdated.setId(userId);
            userEntityUpdated.setPassword(passwordEncoder.encode(userEntityUpdated.getPassword()));
            userRepository.save(userEntityUpdated);

            log.info("[SERVICE] Updated user with username : " + userEntityUpdated.getUsername());
            return true;
        }
        throw new UsernameNotFoundException("Could not find user with id : " + userId);
    }

    /**
     * Deletes an user by its ID
     *
     * @param userId							The user's ID we want to delete
     * @return									True if the deletion was successful
     */
    public boolean deleteUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (userId != null && user.isPresent()) {
            userRepository.delete(user.get());
            log.info("[SERVICE] Deleted user with username : " + user.get().getUsername());
            return true;
        }
        throw new UsernameNotFoundException("Could not find user with id : " + userId);
    }

    public User convertDtoToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public UserDto convertEntityToDto(User userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }
}