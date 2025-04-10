package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.exceptions.ConflictException;
import com.tpe.exceptions.ResourceNotFoundException;
import com.tpe.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor //final olan fieldların set edilmesi için parametreli constructor oluşturur
//    @Autowired
//    public StudentService(StudentRepository repository) {
//        this.repository = repository;
//    }
public class StudentService {

    private final StudentRepository repository;

    //2- tablodan tüm kayitlari getirme islemini yapacagiz
    public List<Student> findAllStudents() {
        return repository.findAll(); //findAll metodu JpaRepository den geliyor, studentRepository interfaceni JpaRepositorye
                                    //extends etmistik
                                    //JpaRepositorydeki metodlar Spring tarafından otomatik olarak implemente edilir
    }


    //4-yeni öğrenciyi tabloya ekleme

    public void saveStudent(Student student) {
        //email tabloda daha önce eklenmiş ?
        boolean existStudent = repository.existsByEmail(student.getEmail());
        if (existStudent){
            //bu email ile tabloda öğrenci varsa-->hata fırlatalım
            throw new ConflictException("Email already exists!");
        }

        repository.save(student);//insert into student ....//save metodu da JpaRepository classindan geliyor

    }

    //7-

    public Student findStudentById(Long identity) {

        Student student=repository.findById(identity).
                orElseThrow(()->new ResourceNotFoundException("Student is not found by id: "+identity));

        return student;
    }

    //9-
    public void deleteStudentById(Long id) {
        //idsi verilen student tabloda yoksa, kontrol edelim
        Student student=findStudentById(id);
        repository.delete(student);


        //2. yol
        //        findStudentById(id);
        //        repository.deleteById(id);



    }


    //11-tablodan kayıtları sayfalara ayırıp sadece istenen
    //sayfayı getirme
    public Page<Student> getAllStudentsByPage(Pageable pageable) {

        Page<Student> studentPage=repository.findAll(pageable);
        //Pageable:pageNo,size,sort(özellik,yön)

        return studentPage;
    }


}
