package com.example.service;

import com.example.database.entity.Citizen;
import com.example.database.repository.CitizensRepo;

import com.example.exception.NotFindException;
import com.example.exception.UniqueException;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

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
            throw new UniqueException("Пользователь с введенными вами данными уже существует");
        }
        return citizensRepo.save(citizen);
    }
    @SneakyThrows
    public List getAllCitizensByParams(Citizen citizen){
        var citizenList = citizensRepo.findListOfCitizensByOptionalParams(citizen.getLast_name(), citizen.getFirst_name(),
                citizen.getMiddle_name(), citizen.getBirth_date());
        if(citizenList.size() == 0)throw new NotFindException("По данным параметрам гражданин не найден.");
        return citizenList;
    }
    @SneakyThrows
    public Citizen getOneCitizen(Long id){
        try {
            Citizen citizen = citizensRepo.findById(id).get();
            return citizen;
        }catch (NoSuchElementException e){
            throw new NotFindException("По данному id гражданин не найден.");
        }
    }
    @SneakyThrows
    public void updateCitizenByIdAndParams(Long id, Citizen citizen){
        try {
            Citizen citizenForUpdate = citizensRepo.findById(id).get();


        Field[] fields = citizen.getClass().getDeclaredFields();
        ArrayList<Field> utilList = new ArrayList<Field>(Arrays.asList(fields));
        List<StringBuilder> sbFields = new ArrayList<>();
        for (int i = 1; i < utilList.size();i++) {
            sbFields.add(new StringBuilder(String.valueOf(utilList.get(i).getName())));
            Character firstChar = sbFields.get(i - 1).charAt(0);
            firstChar = (char) (firstChar & 0x5f);
            sbFields.get(i - 1).setCharAt(0,firstChar);
        }
        for (int i = 0; i < sbFields.size(); i++) {
            Method metGet = citizen.getClass().getMethod("get"+sbFields.get(i));
            metGet.setAccessible(true);
            if(metGet.invoke(citizen) != null){
                Method metSet;
                if(i <= 5)//после 5 метода наш методы получают integer
                {
                    metSet = citizen.getClass().getMethod("set"+sbFields.get(i), String.class);
                }
                else {
                    metSet = citizen.getClass().getMethod("set"+sbFields.get(i), Integer.class);
                }
                metSet.setAccessible(true);
                metSet.invoke(citizenForUpdate,metGet.invoke(citizen));
            }
        }
        if(citizensRepo.findListOfCitizensByOptionalParams(citizenForUpdate.getLast_name(), citizenForUpdate.getFirst_name(),
                citizenForUpdate.getMiddle_name(),citizenForUpdate.getBirth_date()).size() > 1)
            throw new UniqueException("По введенным данным уже существует гражданин");
        else{
            citizensRepo.save(citizenForUpdate);
        }
        }catch (NoSuchElementException e){
            throw new NotFindException("По данным параметрам гражданин не найден.");
        }
    }
}
