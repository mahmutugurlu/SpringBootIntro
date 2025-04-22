package com.tpe.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity//Security için yapılandırma ayarlarını yapılacağını belirtir
//SecurityFilterChain, HttpSecurity bileşenlerinin aktif edilmesini sağlar

@EnableGlobalMethodSecurity(prePostEnabled = true) //metod bazlı yetkilendirmeyi(authorization) aktif eder.



public class WebSecurityConfig {

    //1-SecurityFilterChain ile filtre için kuralları belirledik,
    // bunun için HttpSecurity bileşeni kullanıldı
    //2-AuthenticationProvider'ın gerekli bileşenleri ile bean olarak üretilmesini sağladık
    //3-Hem AuthenticationProvider hem de kendimiz passwordleri hashlemek için
    //BCrypt hashleme algoritmasıyla Password Encoder beani ürettik

    @Autowired
    private UserDetailsService userDetailsService;

    // SecurityFilterChain: gelen HTTP requestleri hangi kurallara göre filtrelenecek
    //bu obje ile ayarlanır
    //HttpSecurity objesini kullanarak;
    // hangi endpointler filtrelenecek, hangi yöntemle filtrelenecek
    //CSRF saldırılarına karşı koruma

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //SS de CSRF koruması default olarak açık gelir
        http.csrf().disable() //REST API'de session bazlı kimlik doğrulama olmadığı için
        //CSRF(Cross-Site Request Forgery) korumasına gerek yoktur.

                .authorizeRequests()
                .antMatchers("/register","/login","/home").permitAll() //bu pathlerle eslesen endpointlere izin ver
                .anyRequest()//diğer requestlerin
                .authenticated()//authanticate et, filtrele
                .and()
                .httpBasic();//basic authentication yöntemini kullan
                    //BasicAuthenticationFilter'ı filtre zincirine dahil et

        return http.build();

    }

    //passwordü DBye hashlyerek kaydetmemiz için
    // ve DaoAuthenticationProvider'ın ise karşılaştırma için
    // password encoder'a ihtiyacı var
    //passwordü DBye hashlyerek kaydetmemiz için
    // ve DaoAuthenticationProvider'ın ise karşılaştırma için
    // password encoder'a ihtiyacı var
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);//4-34:default :10
    }

    //authentication işleminin yapılabilmesi AuthenticationProvider'a ihtiyaç var
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }








}
