package com.tpe.controller;

import com.tpe.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //requestler bu classtaki metodlarla eşleştirilecek ve responselar hazırlanacak
//@ResponseBody :metodun dönüş değerini JSON formatında cevap olarak hazırlar
// obje <-> JSON dönüsümü : Jackson kütüphanesi
@RequestMapping("/students")
@RequiredArgsConstructor

public class StudentController {

    private final StudentService service;


    //SpringBOOT'u selamlama:)
    //http://localhost:8080/students/greet + GET
    @GetMapping("/greet")
    //@ResponseBody
    public String greet(){
        return "Hello Spring BOOT:)";
    }



}
