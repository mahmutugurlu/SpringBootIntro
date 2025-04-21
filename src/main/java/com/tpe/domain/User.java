package com.tpe.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false,length = 50)
    private String firstName;

    @Column(nullable = false,length = 50)
    private String lastName;

    @Column(nullable = false,length = 50,unique = true)
    private String userName;

    @Column(nullable = false,length = 255)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    //SSecurity nin Auth Providerı userı doğruladıktan sonra
    //contexte bırakır, işlem yetki kontrolü için rolllerini
    //görmemiz gerekir, bu sebeple fetch type EAGER olmalı
    private Set<Role> roles=new HashSet<>();





}
