package com.tpe.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Getter//tüm fieldlar için getter metodunun tanımlanmasını sağlar
@Setter//tüm fieldlar için setter metodunun tanımlanmasını sağlar
@AllArgsConstructor//tüm fieldların parametrede verildiği const. metodunu tanımlar
@NoArgsConstructor//default const metodunu tanımlar
//@RequiredArgsConstructor:sadece final(zorunlu) fieldları parametre olarak alan const. metodunu tanımlar
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE) //bu anotasyon ile bu field icin setter olusturulmayacak
    private Long id;

    @NotBlank(message = "Please provide valid name!")
    @Size(min =2, max = 50, message = "name must be between 2 and 50") //girilen karakter sayisini sinirlama ve kisitlama yapar
    @Column(nullable = false)
    private String name;


    @NotBlank(message = "Please provide valid lastname!")
    @Column(nullable = false)
    private String lastname;

    @NotNull(message = "Please provide valid grade!")
    @Column(nullable = false)
    private Integer grade;

    @Email() // email formatinda deger girilmesini dogrular
   // @Pattern() :regex ile format belirlenebilir
    @Column(nullable = false)
    private String email;

    @Setter(AccessLevel.NONE)
    private LocalDateTime createDate;

    @PrePersist
    public void prePersist(){
        this.createDate=LocalDateTime.now();
    }

    //getter-setter

}