package com.tpe.repository;

import com.tpe.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository//
public interface StudentRepository extends JpaRepository<Student, Long> {


    //JpaRepositorydeki metodlar Spring tarafından otomatik olarak implemente edilir, burada data türünü baz alarak implemente ediyor
    //yani book adli classi implement etmek icin Student data türünü book olarak degistiririz.

    //
    boolean existsByEmail(String email); //türetilmis metodlar Spring tarafindan otomatik implente edilir,
}                               //burada existsById metodundan existsByEmail metodunu türettik

