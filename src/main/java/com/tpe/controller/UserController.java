package com.tpe.controller;

import com.tpe.dto.UserDTO;
import com.tpe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //userı kaydetme:register
    //request:http://localhost:8080/register + POST + Body
    /*
    { "firstName":"Ali",
      "lastName":"Can",
      "username":"alican",
      "password":"12345678"
    }
     */

    //response:save + 201 + mesaj
    @RequestMapping("/register")
    @PostMapping
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDTO userDTO) {

        userService.saveUser(userDTO);

        return new ResponseEntity<>("User is registered successfully...", HttpStatus.CREATED);

    }

}
