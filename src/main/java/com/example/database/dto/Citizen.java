package com.example.database.dto;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;



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

    public Long getId() {
        return id;
    }


    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExtra_phone() {
        return extra_phone;
    }

    public void setExtra_phone(String extra_phone) {
        this.extra_phone = extra_phone;
    }

    public Integer getDul_serie() {
        return dul_serie;
    }

    public void setDul_serie(Integer dul_serie) {
        this.dul_serie = dul_serie;
    }

    public Integer getDul_number() {
        return dul_number;
    }

    public void setDul_number(Integer dul_number) {
        this.dul_number = dul_number;
    }
}
