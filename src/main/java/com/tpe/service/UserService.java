package com.tpe.service;

import com.tpe.domain.Role;
import com.tpe.domain.RoleType;
import com.tpe.domain.User;
import com.tpe.dto.UserDTO;
import com.tpe.exceptions.ConflictException;
import com.tpe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;


    public void saveUser(UserDTO userDTO) {

        //username tabloda var mı
        boolean exists=userRepository.existsByUserName(userDTO.getUserName());
        if (exists){//tabloda var ise
            throw new ConflictException("Username already exists!!!");
        }

        //tabloda yoksa
        User user=new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUserName(userDTO.getUserName());
        String encodedpassword=passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encodedpassword);

        //userın rolünün verilmesi
        Set<Role> roleSet=new HashSet<>();
        //genellikle default olarak en düşük yetkideki rol verilir
        //ROLE_STUDENT'ı tablodan bulmamız gerekiyor
        Role role=roleService.getRoleByType(RoleType.ROLE_STUDENT);
        roleSet.add(role);
        user.setRoles(roleSet);

        userRepository.save(user);








    }



}

