package com.example.entyties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Citizen {
    private String last_name;
    private String first_name;
    private String middle_name;
    private String birth_date;
}
