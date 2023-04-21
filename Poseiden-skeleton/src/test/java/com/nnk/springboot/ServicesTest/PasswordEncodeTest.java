package com.nnk.springboot.ServicesTest;

import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("/application-test.properties")

public class PasswordEncodeTest {
    @Test
    public void testPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pw = encoder.encode("123456");
        System.out.println("[ "+ pw + " ]");
    }
}
