package com.example.service;

import com.example.database.entity.Citizen;
import com.example.database.repository.CitizensRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class CitizenService {
    @Autowired
    CitizensRepo citizensRepo;

    public CitizenService(){}

    //TODO раскидать по метадам чтоб не в одном все валялось
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
        return citizensRepo.save(citizen);
    }

}
