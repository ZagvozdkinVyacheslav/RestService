package com.example.service;

import com.example.builders.CitizenBuilder;
import com.example.database.dto.Citizen;
import com.example.database.repository.CitizensRepo;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class CitizenService {
    @Autowired
    CitizensRepo citizensRepo;
    @Autowired
    FiltersService filtersService;
    public CitizenService(){}

    //TODO раскидать по метадам чтоб не в одном все валялось
    public void addCitizenInRepository(String last_name, String first_name, String middle_name, String birth_date,
                                              String phone, String extra_phone, Integer dul_serie, Integer dul_number){

        //checking empty field
        if(first_name.equals("")||last_name.equals("")|birth_date.equals("")||phone.equals("")||dul_serie.equals(1)||dul_number.equals(1)){
            StringBuilder sb = new StringBuilder();
            sb.append(filtersService.lastNameFilter(last_name));
            sb.append(filtersService.firstNameFilter(first_name));
            sb.append(filtersService.birthDateFilter(birth_date));
            sb.append(filtersService.phoneFilter(phone));
            sb.append(filtersService.dulSerieFilter(dul_serie));
            sb.append(filtersService.dulNumberFilter(dul_number));
            sb.deleteCharAt(sb.length() - 1);
            sb.append(".");
            throw new NullPointerException("Не заполнены обязательные поля:" + sb.toString());
        }

        //checking valid dul_serie and dul_number
        if(dul_serie.toString().length() != 4 || dul_number.toString().length() != 6){
            StringBuilder sb = new StringBuilder();
            filtersService.checkAlgDulSerie(dul_serie);
            filtersService.checkAlgDulNumber(dul_number);
            sb.deleteCharAt(sb.length() - 1);
            sb.append(".");
            throw new NumberFormatException("Неправильно заполнены поля:" + sb.toString());
        }
        //checking valid phone
        filtersService.checkValidPhone(phone);
        //check ident user
        if(citizensRepo.listCitizensByParam(last_name,first_name,middle_name,birth_date).size() >= 1)
            throw new NonUniqueResultException("Такой пользователь уже зарегестрирован");
        //checking date exceptions
        filtersService.check18Years(birth_date);



        //builder
        CitizenBuilder builder = new CitizenBuilder();
        builder.addLastName(last_name).addFirstName(first_name).addBirthDate(birth_date)
                        .addPhone(phone).addDulSerie(dul_serie).addDulNumber(dul_number);
        //checking valid extra_phone
        if(!extra_phone.equals("") && extra_phone.matches("^((\\+7)[\\-]?)(\\(\\d{3}\\)[\\-]?)[\\d\\-]{9}$")){
            builder.addExtraPhone(extra_phone);
        } else if (!extra_phone.equals("") && !extra_phone.matches("^((\\+7)[\\-]?)(\\(\\d{3}\\)[\\-]?)[\\d\\-]{9}$")) {
            throw new NumberFormatException("Неправильный формат extra_phone поля");
        }
        if(!middle_name.equals(""))builder.addMiddleName(middle_name);
        //builder
        citizensRepo.save(builder.build());

    }
    public List<Citizen> findAllCitizensByParams(String lastName, String firstName, String middleName, String birthDate){
        return citizensRepo.listCitizensByParam(lastName,firstName,middleName,birthDate);
    }
    public Citizen findCitizenById(Long id){
        return citizensRepo.findById(id).get();
    }
    public List<Citizen>findListOfCitizensByOptionalParams(String lastName, String firstName, String middleName, String birthDate){
        List<Citizen> lst = citizensRepo.findListOfCitizensByOptionalParams(lastName, firstName, middleName, birthDate);
        if(lst.size() == 0) throw new NoSuchElementException("Граждан по данному запросу не обнаружено");
        return lst;
    }
    public void deleteCitizenById(Long id){
        citizensRepo.deleteById(id);
    }
    public void modificateFields(Long id,String last_name, String first_name, String middle_name, String birth_date,
                                 String phone, String extra_phone, Integer dul_serie, Integer dul_number){
        citizensRepo.updateCitizenLastNameById(653l,"slava");

    }

}
