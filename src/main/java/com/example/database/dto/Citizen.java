package com.example.database.dto;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


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
    private String dul_serie;
    @Column(name = "dul_number")
    private String dul_number;

    public Citizen() {
    }
}
