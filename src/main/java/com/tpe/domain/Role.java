package com.tpe.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;

    @Enumerated(EnumType.STRING)//enum sabitlerinin STRING olarak DB de saklanmasını sağlar
    @Column(nullable = false)
    private RoleType type;//ROLE_ADMIN,ROLE_STUDENT,ROLE_INSTRUCTOR






}
