package com.nnk.springboot.services;

import com.nnk.springboot.domain.UserCustom;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService {
    private UserRepository userRepository;
    public UserService(UserRepository userRepository) {this.userRepository = userRepository;}

    public void saveUser(UserCustom user){userRepository.save(user);}
    public List<UserCustom> findAll(){return userRepository.findAll();}
    public Optional<UserCustom> findById(Integer id){
        return userRepository.findById(id);
    };
    public UserCustom findUserById(Integer id){
        Optional<UserCustom> user = userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        }
        return null;
    }
    public void deleteUser(UserCustom user){userRepository.delete(user);}
    public void deleteAllUser(){userRepository.deleteAll();}
}
