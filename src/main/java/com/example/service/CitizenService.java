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
    public CitizenService(){}

    //TODO раскидать по метадам чтоб не в одном все валялось
    public void addCitizenInRepository(String last_name, String first_name, String middle_name, String birth_date,
                                              String phone, String extra_phone, Integer dul_serie, Integer dul_number){
        //checking many exceptions
        //checking empty field
        if(first_name.equals("")||last_name.equals("")|birth_date.equals("")||phone.equals("")||dul_serie.equals(1)||dul_number.equals(1)){
            StringBuilder sb = new StringBuilder();
            if(last_name.equals("")) sb.append(" last_name,");
            if(first_name.equals("")) sb.append(" first_name,");
            if(birth_date.equals("")) sb.append(" birth_date,");
            if(phone.equals("")) sb.append(" phone,");
            if(dul_serie.equals(1)) sb.append(" dul_serie,");
            if(dul_number.equals(1)) sb.append(" dul_number,");
            sb.deleteCharAt(sb.length() - 1);
            sb.append(".");
            throw new NullPointerException("Не заполнены обязательные поля:" + sb.toString());
        }
        //checking valid dul_serie and dul_number
        if(dul_serie.toString().length() != 4 || dul_number.toString().length() != 6){
            StringBuilder sb = new StringBuilder();
            if(dul_serie.toString().length() != 4) sb.append(" dul_serie, должно быть 4 цифры,");
            if(dul_number.toString().length() != 6) sb.append(" dul_number, должно быть 6 цифр,");
            sb.deleteCharAt(sb.length() - 1);
            sb.append(".");
            throw new NumberFormatException("Неправильно заполнены поля:" + sb.toString());
        }
        //checking valid phone
        if(!phone.matches("^((\\+7)[\\-]?)(\\(\\d{3}\\)[\\-]?)[\\d\\-]{9}$")){//+7(123)123-12-12
            throw new NumberFormatException("Неправильный формат phone поля");
        }
        if(citizensRepo.listCitizensByParam(last_name,first_name,middle_name,birth_date).size() >= 1)
            throw new NonUniqueResultException("Такой пользователь уже зарегестрирован");
        //checking date exceptions
        Date birthDateFormatDate = new Date(Integer.parseInt(birth_date.substring(0,4)),
                Integer.parseInt(birth_date.substring(5,7)), Integer.parseInt(birth_date.substring(8,10)));

        String curentDateString = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

        Date currentDate = new Date(Integer.parseInt(curentDateString.substring(0,4)),
                Integer.parseInt(curentDateString.substring(4,6)),Integer.parseInt(curentDateString.substring(6,8)));
        long diffInMillies = Math.abs(currentDate.getTime() - birthDateFormatDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        if(diff / 365 < 18) throw new DateTimeException("Нельзя сохранить жителя, которому меньше 18 лет");
        //checking many exceptions


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
}
