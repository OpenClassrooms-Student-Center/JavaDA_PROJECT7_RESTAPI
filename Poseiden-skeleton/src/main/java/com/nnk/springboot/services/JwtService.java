package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;

public interface JwtService {
    public String authenticate(User user) throws Exception;
}
