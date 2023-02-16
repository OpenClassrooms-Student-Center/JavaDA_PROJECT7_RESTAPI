package com.nnk.springboot.service.impl;

import com.nnk.springboot.NotFoundException;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new NotFoundException("User not found with id " + id));
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(Integer id, User user) {
        User userToUpdate = userRepository.findById(id).get();
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setFullname(user.getFullname());
        userToUpdate.setRole(user.getRole());
        return userRepository.save(user);
    }

    @Override
    public void delete(Integer id) {
        User user = findById(id);
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException(userName));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
    }
}
