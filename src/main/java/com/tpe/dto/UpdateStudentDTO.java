package com.tpe.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter

public class UpdateStudentDTO {

    @NotBlank(message = "Please provide valid name!")
    @Size(min = 2,max = 50,message = "name must be between 2 and 50")
    private String name;

    @NotBlank(message = "Please provide valid lastname!")
    @Size(min = 2,max = 50,message = "lastname must be between 2 and 50")
    private String lastname;

    @Email()//aaa@bbb.ccc email formatında olmasını doğrulama

    private String email;



}
