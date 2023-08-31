package com.example.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Range;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "citizens")
public class Citizen {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotBlank(message = "Citizen must have lastname")
    @Column(name = "last_name")
    private String last_name;
    @NotBlank(message = "Citizen must have firstname")
    @Column(name = "first_name")
    private String first_name;
    @Column(name = "middle_name")
    private String middle_name;
    @Pattern(regexp = "[0-9]{4}.[0-9]{2}.[0-9]{2}",message = "Invalid date format")
    @NotBlank(message = "Citizen must have birth date")
    @Column(name = "birth_date")
    private String birth_date;
    @NotBlank(message = "Citizen must have phone")
    @Pattern(regexp = "^((\\+7)[\\-]?)(\\(\\d{3}\\)[\\-]?)[\\d\\-]{9}$",message = "Invalid mobile number")
    @Column(name = "phone")
    private String phone;
    @Pattern(regexp = "^((\\+7)[\\-]?)(\\(\\d{3}\\)[\\-]?)[\\d\\-]{9}$",message = "Invalid mobile number")
    @Column(name = "extra_phone")
    private String extra_phone;
    @Min(value = 1000, message = "dul serie must have 4 digits")
    @Max(value = 9999, message = "dul serie must have 4 digits")
    @Column(name = "dul_serie")
    private Integer dul_serie;
    @Min(value = 100000, message = "dul number must have 6 digits")
    @Max(value = 999999, message = "dul number must have 6 digits")
    @Column(name = "dul_number")
    private Integer dul_number;

    @Override
    public String toString() {
        return "Citizen{" +
                "id=" + id +
                ", last_name='" + last_name + '\'' +
                ", first_name='" + first_name + '\'' +
                ", middle_name='" + middle_name + '\'' +
                ", birth_date='" + birth_date + '\'' +
                ", phone='" + phone + '\'' +
                ", extra_phone='" + extra_phone + '\'' +
                ", dul_serie=" + dul_serie +
                ", dul_number=" + dul_number +
                '}';
    }
}
