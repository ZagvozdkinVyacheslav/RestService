package com.example.database.entity;

import com.example.interfaces.Marker;
import com.example.validation.Over18YO;
import com.example.validation.Over18YONotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Citizen entity")
@Entity
@Table(name = "citizens")
public class Citizen {
    @Schema(description = "Primary key in data base",accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Schema(description = "Citizen lastname(obligatory)", example = "Ivanov")
    @NotNull(message = "Citizen must have lastname", groups = Marker.onCreate.class)
    @NotEmpty(message = "Citizen must have lastname", groups = Marker.onCreate.class)
    @Column(name = "last_name")
    private String last_name;
    @Schema(description = "Citizen firstname(obligatory)", example = "Ivan")
    @NotNull(message = "Citizen must have firstname", groups = Marker.onCreate.class)
    @NotEmpty(message = "Citizen must have firstname", groups = Marker.onCreate.class)
    @Column(name = "first_name")
    private String first_name;
    @Schema(description = "Citizen patronymic", example = "Ivanovitch")
    @Column(name = "middle_name")
    private String middle_name;
    @Schema(description = "Citizen birth date(obligatory)", example = "1990.01.01",pattern = "[0-9]{4}.[0-9]{2}.[0-9]{2}")
    @NotEmpty(message = "Citizen must have birth date", groups = Marker.onCreate.class)
    @Pattern(regexp = "[0-9]{4}.[0-9]{2}.[0-9]{2}",message = "Invalid date format")
    @Column(name = "birth_date")
    @Over18YONotNull(groups = {Marker.onCreate.class})
    @Over18YO(groups = {Marker.onUpdate.class})
    private String birth_date;
    @Schema(description = "Citizen phone number(obligatory)",
            example = "+7(111)111-11-11",pattern = "^((\\+7)[\\-]?)(\\(\\d{3}\\)[\\-]?)[\\d\\-]{9}$")
    @NotNull(message = "Citizen must have phone", groups = Marker.onCreate.class)
    @NotEmpty(message = "Citizen must have phone", groups = Marker.onCreate.class)
    @Pattern(regexp = "^((\\+7)[\\-]?)(\\(\\d{3}\\)[\\-]?)[\\d\\-]{9}$",message = "Invalid mobile number")
    @Column(name = "phone")
    private String phone;
    @Schema(description = "Citizen extra phone number(optional)",
            example = "+7(111)111-11-11",pattern = "^((\\+7)[\\-]?)(\\(\\d{3}\\)[\\-]?)[\\d\\-]{9}$")
    @Pattern(regexp = "^((\\+7)[\\-]?)(\\(\\d{3}\\)[\\-]?)[\\d\\-]{9}$",message = "Invalid mobile number")
    @Column(name = "extra_phone")
    private String extra_phone;
    @Schema(description = "Citizen passport series(obligatory)",
            example = "1111",minimum = "1000",maximum = "9999")
    @NotNull(message = "Citizen must have dul serie", groups = Marker.onCreate.class)
    @Min(value = 1000, message = "dul serie must have 4 digits")
    @Max(value = 9999, message = "dul serie must have 4 digits")
    @Column(name = "dul_serie")
    private Integer dul_serie;
    @Schema(description = "Citizen passport number(obligatory)",
            example = "111111",minimum = "100000",maximum = "999999")
    @NotNull(message = "Citizen must have dul serie", groups = Marker.onCreate.class)
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
