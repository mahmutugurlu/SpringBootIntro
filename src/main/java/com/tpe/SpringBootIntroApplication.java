package com.tpe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootIntroApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringBootIntroApplication.class, args);
	}

}

	/*
1-@Componenet -> Spring bu sınıfı otomatik olarak belleğe alır.Yani bean olarak kaydeder.Özelleşmiş halleri :
   --@Controller --> bu sınıf clientle görüşen sınıftır
   --@Service --> bu sınıf iş mantıgı içeren bir sınıftır
   --@Repository -->  bu sınıf veri tabanlarını yöneten sınıftır

2-@SpringBootAplication -> Projenin başlangıç noktasıdır. ==> main methodunun bulundugu üst sınıfta kullanılır
   --ComponentScan --> Bean tarayıp otomatik olarak yükler
   --EnableAutoConfiguration --> Springin otomatik yapılarını devreye alır
   --SpringBootConfiguration --> Bulundugu sınıf için ,bu sınıf bir configuration sınıfıdır der.

3-@RestController --> sınıfı Rest API olarak tanımlar.
     ---> Controller + ResponseBody bileşimidir
     ---> Methodlardan dönen verileri otomatik olarak JSON formatına dönüştürür --> Jackson kütüphanesi ile
     ---> HTTP isteklerini karşılayan sınıflarda kullanıyoruz.

4-@RequestMapping --> Methoda ya da Classa gelen HTTP isteklerini eşler
     ---> Method seviyesinde ya da class seviyesinde kullanılır
     ---> End point te @RequestMappig("/students") dediğimizde ,url de gidilecek özelleşmiş adresi belirtir.(sınıf veya method için)
  *** Alternnatifler vardır***
     ---> @GetMapping
     ---> @PutMapping/@PatchMapping
     ---> @PostMapping
     ---> @DeleteMapping

5-@Entity --> Sınıfı ,veritabanında (db de) bir tabloya karşılık geldiğini belirtir,Yani bu sınıfın bir tablosu olusturulacak der.

 */
