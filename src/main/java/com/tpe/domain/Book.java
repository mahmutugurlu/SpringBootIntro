package com.tpe.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Book { //many

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JsonProperty("bookName")  //sadece JSON formatında bu fieldın isminin belirtilen şekilde gösterilmesini sağlar
    @Column(name = "book_name") //SQL de book_name, JPQL:name, JAva:name , JSON:bookName

    private String name;

    @ManyToOne
    @JsonIgnore//book objesini JSON formatına dönüştürürken student fieldını ignore etmeyi sağlar
    private Student student;


}
