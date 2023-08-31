package com.example.service;

import com.example.database.entity.Citizen;
import com.example.database.repository.CitizensRepo;

import com.example.exception.UniqueException;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@NoArgsConstructor
public class CitizenService {
    @Autowired
    CitizensRepo citizensRepo;



    @SneakyThrows
    public Citizen saveCitizen(Citizen citizenIncome){

        Citizen citizen = Citizen.builder()
                .last_name(citizenIncome.getLast_name())
                .first_name(citizenIncome.getFirst_name())
                .middle_name(citizenIncome.getMiddle_name())
                .birth_date(citizenIncome.getBirth_date())
                .phone(citizenIncome.getPhone())
                .extra_phone(citizenIncome.getExtra_phone())
                .dul_serie(citizenIncome.getDul_serie())
                .dul_number(citizenIncome.getDul_number())
                .build();
        if(citizensRepo.findListOfCitizensByOptionalParams(citizen.getLast_name(),citizen.getFirst_name(),
                citizen.getMiddle_name(), citizen.getBirth_date()).size() >= 1){
            throw new UniqueException(ZonedDateTime.now());
        }
        return citizensRepo.save(citizen);
    }

}
