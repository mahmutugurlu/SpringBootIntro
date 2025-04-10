package com.tpe.service;

import com.tpe.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor //final olan fieldların set edilmesi için parametreli constructor oluşturur
//    @Autowired
//    public StudentService(StudentRepository repository) {
//        this.repository = repository;
//    }
public class StudentService {

    private final StudentRepository repository;



}
