package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {
    @Autowired
    UserService userService;

    @Test
    public void userTest() {

        User user = new User();
        user.setFullname("Alexandre Dumas");
        user.setUsername("Alex");
        user.setRole("user");

        //Save
        user = userService.save(user);
        Assert.assertNotNull(user.getId());
        Assert.assertTrue(user.getUsername()=="Alex");

        //Update
        user.setUsername("Al");
        user=userService.save(user);
        Assert.assertTrue(user.getUsername()=="Al");

        //Find
        List<User> userList = userService.findAll();
        Assert.assertTrue(userList.size()>0);
    }


}
