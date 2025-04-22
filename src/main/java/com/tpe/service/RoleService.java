package com.tpe.service;

import com.tpe.domain.Role;
import com.tpe.domain.RoleType;
import com.tpe.exceptions.ResourceNotFoundException;
import com.tpe.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;


    public Role getRoleByType(RoleType type) {
        return repository.findByType(type).
                orElseThrow(()->new ResourceNotFoundException("Role not found : "+type));
    }
}
