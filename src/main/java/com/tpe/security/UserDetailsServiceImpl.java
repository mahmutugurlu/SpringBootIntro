package com.tpe.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    //bu metod ile DB deki kendi userımızı bulup security'e UserDetails olarak vereceğiz
    //kendi user-->role
    //userdetails-->granted authority
    // kendi nesnelerimizi Spring security frameworkune tanitiyoruz
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        return null;
    }
}
