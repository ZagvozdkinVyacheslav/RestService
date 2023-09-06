package com.example.database.entity;

import com.example.interfaces.Marker;
import com.example.validation.Over18YO;
import com.example.validation.Over18YONotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(description = "Citizen entity")
@Entity
@Table(name = "citizens")
public class Citizen {
    @Schema(description = "Primary key in data base",accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    //Попытка в оптимизацию бд для маленького кол во запросов
    //SEQUENCE выбран из за возмож пакетной вставки и тк только один клиент работает с бд
    @SequenceGenerator(name = "CitizenGen",
             initialValue = 1, allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "CitizenGen")
    private Long id;
    @Schema(description = "Citizen surname(obligatory)", example = "Ivanov")
    @NotNull(message = "Citizen must have surname", groups = Marker.onCreate.class)
    @NotEmpty(message = "Citizen must have surname", groups = Marker.onCreate.class)
    @Column(name = "last_name")
    private String lastName;
    @Schema(description = "Citizen name(obligatory)", example = "Ivan")
    @NotNull(message = "Citizen must have name", groups = Marker.onCreate.class)
    @NotEmpty(message = "Citizen must have name", groups = Marker.onCreate.class)
    @Column(name = "first_name")
    private String firstName;
    @Schema(description = "Citizen patronymic", example = "Ivanovitch")
    @Column(name = "middle_name")
    private String middleName;
    @Schema(description = "Citizen birth date(obligatory)", example = "1990.01.01",pattern = "[0-9]{4}.[0-9]{2}.[0-9]{2}")
    @NotEmpty(message = "Citizen must have birth date", groups = Marker.onCreate.class)
    @Pattern(regexp = "(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[1,2])\\.(19|20)\\d{2}",message = "Invalid date format")
    @Column(name = "birth_date")
    @Over18YONotNull(groups = {Marker.onCreate.class})
    @Over18YO(groups = {Marker.onUpdate.class})
    private String birthDate;
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
    private String extraPhone;
    @Schema(description = "Citizen passport series(obligatory)",
            example = "1111",minimum = "1000",maximum = "9999")
    @NotNull(message = "Citizen must have dul serie", groups = Marker.onCreate.class)
    @Pattern(regexp = "(\\d{4})", message = "Invalid passport number")
    @Column(name = "dul_series")
    private String dulSeries;
    @Schema(description = "Citizen passport number(obligatory)",
            example = "111111",minimum = "100000",maximum = "999999")
    @NotNull(message = "Citizen must have dul series", groups = Marker.onCreate.class)
    @Pattern(regexp = "(\\d{6})", message = "Invalid passport number")
    @Column(name = "dul_number")
    private String dulNumber;


}
