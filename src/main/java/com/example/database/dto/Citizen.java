package com.example.database.dto;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;


@Getter
@Setter
@ToString
@Component
@Entity
@Table(name = "citizens")
public class Citizen {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "last_name")
    private String last_name;
    @Column(name = "first_name")
    private String first_name;
    @Column(name = "middle_name")
    private String middle_name;
    @Column(name = "birth_date")
    private String birth_date;
    @Column(name = "phone")
    private String phone;
    @Column(name = "extra_phone")
    private String extra_phone;
    @Column(name = "dul_serie")
    private Integer dul_serie;
    @Column(name = "dul_number")
    private Integer dul_number;

    public Citizen() {
    }
}
