package com.example.service;

import com.example.builders.CitizenBuilder;
import com.example.database.dto.Citizen;
import com.example.database.repository.CitizensRepo;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class CitizenService {
    @Autowired
    CitizensRepo citizensRepo;
    public CitizenService(){}

    public void addCitizenInRepository(String first_name, String last_name, String middle_name, String birth_date,
                                              String phone, String extra_phone, String dul_serie, String dul_number){
        CitizenBuilder builder = new CitizenBuilder();
        builder.addFirstName(first_name).addLastName(last_name)
                .addPhone(phone).addDulSerie(dul_serie)
                .addDulNumber(dul_number);

        if(middle_name != null && !middle_name.trim().isEmpty())builder.addMiddleName(middle_name);
        if(birth_date != null && !birth_date.trim().isEmpty())builder.addBirthDate(birth_date);
        if(extra_phone != null && !extra_phone.trim().isEmpty())builder.addExtraPhone(extra_phone);

        citizensRepo.save(builder.build());
    }
    public List<Citizen> findAllCitizensByParams(String lastName, String firstName, String middleName, String birthDate){
        return (List<Citizen>) citizensRepo.findAll();
    }
    public Citizen findCitizenById(Long id){
        return citizensRepo.findById(id).get();
    }
}
