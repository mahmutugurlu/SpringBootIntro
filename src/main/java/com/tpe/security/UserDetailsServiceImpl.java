package com.tpe.security;

import com.tpe.domain.Role;
import com.tpe.domain.User;
import com.tpe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    //1-UserDetailsService->implemente ederek yaptık
    //2-UserDetails->loadUserByUsername
    //3-GrantedAuthority->

    //bu metod ile DB deki kendi userımızı bulup security'e UserDetails olarak vereceğiz
    //kendi user-->role
    //userdetails-->granted authority
    // kendi nesnelerimizi Spring security frameworkune tanitiyoruz

    //bu classta kendi objelerimizle SS objelerini olusturduk....
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //öncelikle username ile tablodan userı bulmamız gerekiyor
        User user =userRepository.findByUserName(username).
                orElseThrow(()->new UsernameNotFoundException("User not found by username : "+username));


        return new org.springframework.security.core.userdetails.
                User(user.getUserName(),user.getPassword(),buildGrantedAuthorities(user.getRoles()));
        //bu SS nin UserDetailsini implemente eden User
        //kendi user -> SS nin user
    }

    //userın(kendi) rollerinden --> grantedauthority elde etmeliyiz

    private List<SimpleGrantedAuthority> buildGrantedAuthorities(Set<Role>roles){

        List<SimpleGrantedAuthority> grantedAuthorities=new ArrayList<>();

        //SimpleGrantedAuthority nin yapıcı metoduna parametre olarak
        //rolümüzün ismini String olarak verdiğimizde GrantedAuthority
        //referansı ile bir obje elde ederiz ve listeye ekleriz
        //bu metod ile kendi userımızın rollerini SS nin GrantedAuthoritylerine
        //dönüştürebiliriz

        for (Role role:roles){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getType().name()));
            //GrantedAuthority authority=new SimpleGrantedAuthority("ROLE_ADMIN")

        }

        return grantedAuthorities;



    }



}
