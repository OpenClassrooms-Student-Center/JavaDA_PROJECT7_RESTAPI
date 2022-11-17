package com.nnk.springboot.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.Role;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.RoleRepository;
import com.nnk.springboot.repositories.UserRepository;

/*
 * Service for handling User related operations
 */
@Service
public class RoleServiceImpl implements IRoleService {
    
	private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
    	this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public Role getRoleById(Integer id) {
    	return roleRepository.findById(id).get();
    }
}
