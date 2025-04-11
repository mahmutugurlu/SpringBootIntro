package com.tpe.repository;

import com.tpe.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository//
public interface StudentRepository extends JpaRepository<Student, Long> {


    //JpaRepositorydeki metodlar Spring tarafından otomatik olarak implemente edilir, burada data türünü baz alarak implemente ediyor
    //yani book adli classi implement etmek icin Student data türünü book olarak degistiririz.

    //
    boolean existsByEmail(String email); //türetilmis metodlar Spring tarafindan otomatik implente edilir,
                                //burada existsById metodundan existsByEmail metodunu türettik

    //15-a
    List<Student> findAllByGrade(Integer grade);//türetilmiş metodlar Spring tarafından implemente edilir

    //15-b

    //JPQL:javaca
    @Query("FROM Student WHERE grade=:pGrade")
    List<Student> filterAllByGrade(@Param("pGrade") Integer grade);//spring verilen querye göre metodu implemente eder
    //@Param methodun parametresinde verilen degeri pGrade icerisine alir
    // ve bu degiskeni sorgu icerisinde kullanabiliriz


    //15-c
    //SQL
    @Query(value = "SELECT * FROM student WHERE grade=:pGrade",nativeQuery = true)
    List<Student> filterAllByGradeSql(@Param("pGrade") Integer grade);






}


