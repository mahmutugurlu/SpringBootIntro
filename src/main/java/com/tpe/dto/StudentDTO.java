package com.tpe.dto;

import com.tpe.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

    private String name;

    private String lastname;

    private Integer grade;

    //Student->DTO
    public StudentDTO(Student student) {
        this.name = student.getName();
        this.lastname = student.getLastname();
        this.grade = student.getGrade();
    }




}
