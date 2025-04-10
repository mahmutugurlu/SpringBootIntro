package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController //requestler bu classtaki metodlarla eşleştirilecek ve responselar hazırlanacak
//@ResponseBody :metodun dönüş değerini JSON formatında cevap olarak hazırlar
//@RequestBody  :requestin içindeki(bodysinde) JSON formatında olan datayı uygulama içindeki objeye dönüştürür
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

    //1-tüm öğrencileri listeleyelim : READ
    //Request : http://localhost:8080/students + GET
    //Response : tüm ögrencilerin listesi + 200 : ok (HttpStatus Code)

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents(){
        //taboladan tüm kayıtları getirelim
        List<Student>  allStudents=service.findAllStudents();
        return new ResponseEntity<>(allStudents, HttpStatus.OK);//200
    }

    //ResponseEntity : cevabın body + status code

    // ResponseEntity classi cevabi body ve status code ile beraber verilmesini saglar

    //3-ögrenciyi ekleme : CREATE
    //Request : http://localhost:8080/students + POST + body (JSON)

     /*
    {
    "name":"Ali",
    "lastname":"Can",
    "email":"can@mail.com",
    "grade":99
    }
    JSON--->obje (student)-->@RequestBody

     */

    //Response : öğrenci tabloya eklenir, başarılı mesajı + 201(Created)

    @PostMapping
    public ResponseEntity<String> createStudent(@Valid @RequestBody Student student){
        service.saveStudent(student);

        return new ResponseEntity<>("Student is created successfully..",HttpStatus.CREATED);//201
    }

    //RequestBody : istekte bulunan body i alır,Studenttaki eşleşen fieldlere yerleştirir.
    //@Valid : create edilirken kuralları kontrol eder.


    //6- query param ile idsi verilen öğrenciyi getirme
    //request : http://localhost:8080/students/query?id=1 + GET
    //response:student + 200

    @GetMapping("/query")
    public ResponseEntity<Student> getStudent(@RequestParam("id") Long identity){

        Student student=service.findStudentById(identity);


        //return new ResponseEntity<>(student,HttpStatus.OK);
        return ResponseEntity.ok(student);//200

    }

    //ÖDEV(Alternatif)-path param ile idsi verilen öğrenciyi getirme
    //request : http://localhost:8080/students/1 + GET
    //response:student + 200


    //8-path param ile idsi verilen öğrenciyi silme
    //request : http://localhost:8080/students/1 + DELETE
    //response:tablodan kayıt silinir, başarılı mesaj + 200
    @DeleteMapping("/{deletedId}")
    public ResponseEntity<String> deleteStudent(@PathVariable("deletedId") Long id){

        service.deleteStudentById(id);

        return ResponseEntity.ok("Student is deleted successfully...");//200
    }

    //10-tüm öğrencileri sayfa sayfa(page page) listeleme : READ
    //pagination(sayfalandırma) : hız/performans
    // 1|2|3 ...
    //http://localhost:8080/students/page?
    //                               page=3&
    //                               size=20&
    //                               sort=name&
    //                               direction=DESC(ASC) + GET

    //pagination :
    // db den data çekmek uygulamayı yavaslatır ,
    // dataları toplu halde getirmek yerine sayfaladırma işlemi yapılır,buna -pagination(sayfalandırma) denir
    //hız ve performans olarak tercih edilir.

    @GetMapping("/page")
    public ResponseEntity<Page<Student>> getAllStudentsByPage(

            @RequestParam("page") int pageNo,//kaçıncı sayfa
            @RequestParam("size") int size,//her sayfada kaç tane kayıt
            @RequestParam("sort") String property, //hangi özelliğe göre sıralama
            @RequestParam("direction") Sort.Direction direction//sıralamanın yönünü




    ){

        Pageable pageable = PageRequest.of(pageNo,size,Sort.by(direction,property));

        Page<Student> studentPage=service.getAllStudentsByPage(pageable);

        return ResponseEntity.ok(studentPage);

    }

    //postman ile 10 tane ögrenci ekleyelim










    /*
        API'ler ya da HTTP istekleriyle uğraşırken, "status" genellikle bir işlemin sonucunu anlatan durum kodudur.
    200 OK → İşlem başarılı
    404 Not Found → Kaynak bulunamadı
    500 Internal Server Error → Sunucu hatası
    300 -> Sunucu, isteğe karşılık olarak birden fazla seçenek olduğunu belirtir. İstemcinin bu seçenekler arasından
    bir seçim yapması bekleni

     */





}
