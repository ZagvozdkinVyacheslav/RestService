package com.example.builders;

import com.example.database.dto.Citizen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CitizenBuilder {
//должны быть через autowired но он приходит как null, как лечить не знаю
    private Citizen citizen;
    @Autowired
    public CitizenBuilder(){
        this.citizen = new Citizen();
    }

    public CitizenBuilder addLastName(String lastName){
        citizen.setLast_name(lastName);
        return this;
    }
    public CitizenBuilder addFirstName(String firstName){
        citizen.setFirst_name(firstName);
        return this;
    }
    public CitizenBuilder addMiddleName(String middleName){
        citizen.setMiddle_name(middleName);
        return this;
    }
    public CitizenBuilder addBirthDate(String birthDate){
        citizen.setBirth_date(birthDate);
        return this;
    }
    public CitizenBuilder addPhone(String phone){
        citizen.setPhone(phone);
        return this;
    }
    public CitizenBuilder addExtraPhone(String extraPhone){
        citizen.setExtra_phone(extraPhone);
        return this;
    }
    public CitizenBuilder addDulSerie(String dulSerie){
        citizen.setDul_serie(dulSerie);
        return this;
    }
    public CitizenBuilder addDulNumber(String dulNumber){
        citizen.setDul_number(dulNumber);
        return this;
    }
    public Citizen build(){
        return citizen;
    }

}
