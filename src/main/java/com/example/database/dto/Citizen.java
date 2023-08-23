package com.example.database.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor

@Getter
@Setter

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

    public Citizen(String last_name, String first_name, String middle_name, String birth_date) {
        this.last_name = last_name;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.birth_date = birth_date;
    }
}
