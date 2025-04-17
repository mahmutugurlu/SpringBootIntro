package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.dto.UpdateStudentDTO;
import com.tpe.exceptions.ConflictException;
import com.tpe.exceptions.ResourceNotFoundException;
import com.tpe.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController //requestler bu classtaki metodlarla eşleştirilecek ve responselar hazırlanacak
//@ResponseBody :metodun dönüş değerini JSON formatında cevap olarak hazırlar
//@RequestBody  :requestin içindeki(bodysinde) JSON formatında olan datayı uygulama içindeki objeye dönüştürür
// obje <-> JSON dönüsümü : Jackson kütüphanesi
@RequestMapping("/students") //@RequestMapping, gelen HTTP isteklerini (GET, POST, PUT, DELETE vs.) bir metoda veya controller sınıfına yönlendirmek için kullanılır.
                            //Hem sınıf düzeyinde hem de metot düzeyinde tanımlanabilir.

@RequiredArgsConstructor //@RequiredArgsConstructor, sınıf içinde final olarak tanımlanmış tüm alanlar (veya @NonNull anotasyonu ile işaretlenmiş alanlar) için bir constructor (yapıcı metot) otomatik olarak oluşturur.
                //Yani bu anotasyon sayesinde, gerekli bağımlılıkları enjekte etmek için elle constructor yazmaya gerek kalmaz.

public class StudentController {

    Logger logger= LoggerFactory.getLogger(StudentController.class);

    private final StudentService service;


    //SpringBOOT'u selamlama:)
    //http://localhost:8080/students/greet + GET
    @GetMapping("/greet") //Bu anotasyonla işaretlenmiş metodlar, sadece GET tipindeki HTTP isteklerini işler.
    //@ResponseBody
    public String greet(){

        return "Hello, Spring BOOT is perfect :)";
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

    @PostMapping //@PostMapping, Spring Framework’te HTTP POST isteklerini bir metoda yönlendirmek için kullanılan bir kısa yol (shortcut) anotasyondur.
    public ResponseEntity<String> createStudent(@Valid @RequestBody Student student){
        try {

            service.saveStudent(student);
            logger.info("yeni öğrenci eklendi, id : "+student.getId());
            return new ResponseEntity<>("Student is created successfully..",HttpStatus.CREATED);//201

        }catch (ConflictException e){
            logger.warn(e.getMessage());
            return new ResponseEntity<>("Student creation is failed!!!",HttpStatus.BAD_REQUEST);//400
        }

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

        /*

        Spring Boot'ta @RequestParam, bir HTTP isteği içindeki query parametrelerini (URL’deki ? sonrası kısımlar) Java metot parametrelerine bağlamak için kullanılır.
        Kısaca: URL'deki parametreleri metoda kolayca almanı sağlar.

                Ne zaman @RequestParam kullanılır?
        ✅ URL query parametreleriyle çalışırken
        ✅ Arama, filtreleme, sıralama gibi işlemlerde
        ✅ Formdan gelen verilerde (GET ile gönderiliyorsa)


         */

    }

    //ÖDEV(Alternatif)-path param ile idsi verilen öğrenciyi getirme
    //request : http://localhost:8080/students/1 + GET
    //response:student + 200

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentPath(@PathVariable("id") Long identity){

        Student student=service.findStudentById(identity);


        //return new ResponseEntity<>(student,HttpStatus.OK);
        return ResponseEntity.ok(student);//200

        /*

        @PathVariable, bir HTTP isteği URL’sinin path (yol) kısmındaki verileri Java metoduna aktarmak için kullanılır.
        Yani URL’nin içine gömülü veriyi almanı sağlar.

                 Anotasyonun Özellikleri

        Özellik	        Açıklama
        @PathVariable	URL içindeki {}  süslü parantezli path verilerini çeker.
        value	        URL’deki değişken ismi. Genelde yazmaya bile gerek yoktur.
        required	     Varsayılan true – yoksa hata verir.


        🔸 @PathVariable vs @RequestParam

Özellik	                @PathVariable	                                   @RequestParam
Nereden veri alır?	    URL yolundan (/.../{id})	                  URL query'sinden (?id=...)
Kullanım amacı	        Kaynak kimliği, yönlendirme	                  Filtreleme, sıralama, arama
URL Örneği	            /students/5	                                  /students?id=5



         */

    }






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

    //12-idsi verilen öğrencinin name,lastname ve emailini değiştirme(güncelleme)
    //request : http://localhost:8080/students/1 + PUT(yerine koyma)/PATCH(kısmi) + BODY(JSON)
    //response: yeni bilgilerle güncelleme, başarılı mesaj + 201

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable("id") Long id,
                                                @Valid @RequestBody UpdateStudentDTO studentDTO){

        service.updateStudentById(id,studentDTO);

        return new ResponseEntity<>("Student is updated successfully..",HttpStatus.CREATED);


    }

    /*

        @PatchMapping, Spring Boot’ta HTTP PATCH isteklerini işlemek için kullanılır.

    PATCH, bir kaynağın tamamını değil, sadece belirli alanlarını güncellemek için kullanılır.

    🔧 Ne zaman kullanılır?
    Bir nesnenin sadece bazı alanlarını değiştirmek istediğinde (örneğin: sadece ad, sadece e-posta).

    PUT gibi tüm nesneyi değil, kısmi değişiklik yapmak istediğinde


     */





    //NOT:response : updated student + başarılı mesajı + 201 birlikte göndermek istersek
    //1.yol: ResponseEntity: HashMap.put(student,mesaj) + 201
    //2.yol: CustomResponse:body,message,status


    //14 grade ile öğrencileri filtreleyelim

    @GetMapping("/grade/{grade}")
    public ResponseEntity<List<Student>> getStudentsByGrade(@PathVariable("grade") Integer grade){

        List<Student> students=service.findStudentsByGrade(grade);

        return ResponseEntity.ok(students);
    }


    //Practice1:isim veya soyisme göre filtreleme
    //request:http://localhost:8080/students/search?word=Deniz + GET

    @GetMapping("/search")
    public ResponseEntity<List<Student>> getStudentsByNameOrLastname(@RequestParam("word") String word){

        List<Student> students=service.getStudentsByNameOrLastname(word);

        return ResponseEntity.ok(students);//200

    }




    //Practice2:grade x ile y arasında olan öğrencileri filtreleyelim
    //request : http://localhost:8080/students/point?min=15&max=50 + GET
    //response:grade:15 ile 50 olan öğrencileri listeleyelim

    @GetMapping("/point")
    public ResponseEntity<List<Student>>getStudentsByPoint(@RequestParam("min") Long min, @RequestParam("max") Long max){

        List<Student> student2 = service.getStudentsByPoint(min, max);
        return ResponseEntity.ok(student2);
    }


    //1-repository:metod isimlerini türeterek
    //2-JPQL/SQL ile custom sorgu yazarak

    //Meraklısına ÖDEVVV:)name içinde ".." hecesi geçen öğrencileri filtreleme
    //request:http://localhost:8080/students/filter?word=al + GET ex:halil, lale




    //16-id'si verilen öğrencinin name,lastname ve grade getirme
    //request:http://localhost:8080/students/info/2 + GET
    //response:idsi verilen öğrencinin sadece 3 datasını DTO olarak gösterme + 200
    @GetMapping("/info/{id}")
    public ResponseEntity<StudentDTO> getStudentInfo(@PathVariable("id") Long id){

        // StudentDTO studentDTO=service.getStudentInfoById(id);
        StudentDTO studentDTO=service.getStudentDto(id);

        return ResponseEntity.ok(studentDTO);

    }


        /*

    Loglama, bir yazılım veya sistemin çalışırken yaptığı önemli olayları,
     işlemleri ve hataları kaydetmesi anlamına gelir. Loglar,

     bilgisayar programlarının bir çeşit "günlüğü" veya "karne defteri" gibi düşünülebilir.
     Sistem, yaptığı işleri ve karşılaştığı problemleri
     buraya yazar ve bu bilgiler, geliştiriciler veya sistem yöneticileri için çok değerlidir.

         */



    //19-loglama örneği
    //request: http://localhost:8080/students/welcome + GET
    @GetMapping("/welcome")
    public String getMessage(HttpServletRequest request){
        logger.info("Welcome isteği metoda eşleştirildi.");
        logger.warn("welcome isteğinin metodu : "+request.getMethod());
        logger.warn("welcome isteğinin pathi : "+request.getServletPath());

        return "Welcome Spring Boot:)";

    }

    //20-exception handling
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleException(ResourceNotFoundException e){

        Map<String,String> response=new HashMap<>();
        response.put("error",e.getMessage());//body
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);//404

    }







    /*
        API'ler ya da HTTP istekleriyle uğraşırken, "status" genellikle bir işlemin sonucunu anlatan durum kodudur.
    200 OK → İşlem başarılı
    404 Not Found → Kaynak bulunamadı
    500 Internal Server Error → Sunucu hatası
    300 -> Sunucu, isteğe karşılık olarak birden fazla seçenek olduğunu belirtir. İstemcinin bu seçenekler arasından
    bir seçim yapması bekleni

     */

          /*
    Spring Boot Actuator, bir uygulamanın sağlık durumunu ve çalışma metriklerini izlemek
    için kullanılan bir Spring Boot kütüphanesidir. Actuator, bir uygulamanın
    arka planda nasıl çalıştığını görmenizi sağlar ve uygulamanın izlenebilirliğini artırır.
    Uygulama Sağlık Durumu:

    Uygulamanın çalışır durumda olup olmadığını kontrol eder.
    Örneğin: "Veritabanına bağlanabiliyor mu? Sunucu çalışıyor mu?"
    Metrik Takibi:

    Uygulamanın performansı hakkında bilgiler sağlar.
    Örneğin: "Kaç kullanıcı sisteme bağlandı? Bellek kullanımı ne durumda?"
    Günlük İşleyişin İzlenmesi:

    Loglama, yapılandırmalar, güvenlik bilgileri gibi iç detayları görmenizi sağlar.
    Sorun Giderme:

    Hata durumunda, sistemin hangi noktada sorun yaşadığını anlamanıza yardımcı olur
    /actuator/health    Uygulamanın sağlık durumunu gösterir.
    /actuator/metrics   Uygulamanın performansıyla ilgili metrikleri listeler.
    /actuator/env       Uygulamanın çevre değişkenlerini listeler.
    /actuator/loggers   Log seviyelerini ve log yapılandırmalarını kontrol eder.
    /actuator/info      Uygulama hakkında bilgi verir (ör. sürüm bilgisi).

    */




}
