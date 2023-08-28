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
                                 String phone, String extra_phone, Integer dul_serie, Integer dul_number) throws NoSuchFieldException {
        if(last_name.equals("")&&first_name.equals("")&&middle_name.equals("")&&birth_date.equals("")&&phone.equals("")
                &&extra_phone.equals("")&&dul_serie.equals(1)&&dul_number.equals(1))
            throw new NoSuchFieldException("Отправлен запрос без параметров для изменения");
        //если из основных 4 полей хотя бы одно не пустое
        try {
        if (!(last_name.equals("") &&first_name.equals("") &&middle_name.equals("") &&birth_date.equals("")))
        {

            Citizen citizen =  citizensRepo.findById(id).get();//throw no suchElemExc
            var mainFieldsList = filtersService.mainFieldsFilterList(last_name,first_name,middle_name,birth_date);
            if(mainFieldsList.get(0).equals(""))mainFieldsList.set(0,citizen.getLast_name());
            if(mainFieldsList.get(1).equals(""))mainFieldsList.set(1,citizen.getFirst_name());
            if(mainFieldsList.get(2).equals(""))mainFieldsList.set(2,citizen.getMiddle_name());
            if(mainFieldsList.get(3).equals(""))mainFieldsList.set(3,citizen.getBirth_date());
            if(citizensRepo.findListOfCitizensByOptionalParams(mainFieldsList.get(0),mainFieldsList.get(1),
                    mainFieldsList.get(2),mainFieldsList.get(3)).size() > 1)throw new NonUniqueResultException("Такой гражданин уже существует.");
            if(!mainFieldsList.get(0).equals(""))citizensRepo.updateCitizenLastNameById(id,last_name);
            if(!mainFieldsList.get(1).equals(""))citizensRepo.updateCitizenFirstNameById(id,first_name);
            if(!mainFieldsList.get(2).equals("")) citizensRepo.updateCitizenMiddleNameById(id,middle_name);
            if(!mainFieldsList.get(3).equals(""))citizensRepo.updateCitizenBirthDateById(id,birth_date);

        }

        if(!phone.equals("")){//check phone
            filtersService.checkValidPhone(phone);//throw NumberFormatExc
            citizensRepo.updateCitizenPhoneById(id,phone);
        }
        if(!extra_phone.equals("")){//check extra_phone
            filtersService.checkValidPhone(extra_phone);//throw NumberFormatExc
            citizensRepo.updateCitizenExtraPhoneById(id,extra_phone);
        }
        if(!dul_serie.equals(1) && dul_serie.toString().length() == 4)//check dulSerie
            citizensRepo.updateCitizenDulSerieById(id,dul_serie);
        else if(!dul_serie.equals(1)) throw new NumberFormatException("Поле dul_serie должно быть из 4 цифр");

        if(!dul_number.equals(1) && dul_number.toString().length() == 6)//check dulNumber
            citizensRepo.updateCitizenDulNumberById(id,dul_number);
        else if(!dul_number.equals(1))throw new NumberFormatException("Поле dul_number должно быть из 6 цифр");

        }catch (NoSuchElementException e){
            throw new NoSuchElementException("Гражданина с таким id не существует");
        }catch (DateTimeException e){
            throw new DateTimeException("Нельзя обновлять возраст гражданина так чтобы ему было меньше 18 лет");
        }catch (NonUniqueResultException e){
            throw new NonUniqueResultException(e.getMessage());
        }catch (NumberFormatException e){
            throw new NumberFormatException(e.getMessage());
        }

    }

}
