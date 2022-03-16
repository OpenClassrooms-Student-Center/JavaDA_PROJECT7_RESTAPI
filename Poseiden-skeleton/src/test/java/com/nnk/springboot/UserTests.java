package com.nnk.springboot;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;

import org.assertj.core.util.Lists;
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
	private UserService userService;

	@Test
	public void userTest() {
		User user = new User("UserName", "Passw0rd!", "UserTestName", "USER");

		// Save
		user = userService.saveUser(user);
		Assert.assertNotNull(user.getId());
		Assert.assertTrue(user.getUsername().equals("UserName"));

		// Update
		user.setUsername("UserNameUpdate");;
		user = userService.saveUser(user);
		Assert.assertTrue(user.getUsername().equals("UserNameUpdate"));

		// Find
		List<User> listResult = Lists.newArrayList(userService.findAllUsers());
		Assert.assertTrue(listResult.size() > 2); // 2 users already saved in initial database

		// Delete
		Integer id = user.getId();
		userService.deleteUser(user);
		Optional<User> userList = userService.findUserById(id);
		Assert.assertFalse(userList.isPresent());
	}
}
