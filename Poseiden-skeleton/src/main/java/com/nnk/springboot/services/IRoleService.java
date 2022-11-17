package com.nnk.springboot.services;

import java.util.Optional;

import com.nnk.springboot.domain.Role;

public interface IRoleService {
    public Role getRoleById(Integer id);
}
