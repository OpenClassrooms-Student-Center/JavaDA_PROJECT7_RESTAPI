package com.nnk.springboot.repositories;


import com.nnk.springboot.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserTest() {
        User user = new User();
        user.setFullname("Super Admin");
        user.setRole("ROLE_ADMIN");
        user.setUsername("SuperAdmin");
        user.setPassword("SuperAdmin@2025");

        // Save
        user = userRepository.save(user);
        Integer generatedId = user.getId();

        Assert.assertNotNull(generatedId);
        Assert.assertTrue(generatedId > 0);
        Assert.assertEquals("SuperAdmin", user.getUsername());

        // Update
        user.setUsername("Admin");
        User updatedUser= userRepository.save(user);
        Assert.assertEquals("Admin", updatedUser.getUsername());

        // Find
        List<User> listUsers = userRepository.findAll();
        Assert.assertTrue(listUsers.size() > 0);

        // FindById
        Optional<User> foundUser = userRepository.findById(generatedId);
        Assert.assertTrue(foundUser.isPresent());

        // Delete
        userRepository.delete(user);
        Optional<User> deletedUser = userRepository.findById(generatedId);
        Assert.assertFalse(deletedUser.isPresent());
    }
}
