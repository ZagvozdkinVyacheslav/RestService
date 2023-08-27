package com.example.service;

import com.example.builders.CitizenBuilder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class FiltersService {
    public String lastNameFilter(String lastName){
        if(lastName.equals("")) return " last_name,";
        return "";
    }
    public String firstNameFilter(String firstName){
        if(firstName.equals("")) return " first_name,";
        return "";
    }
    public String birthDateFilter(String birthDate){
        if(birthDate.equals("")) return " birth_date,";
        return "";
    }
    public String phoneFilter(String phone){
        if(phone.equals("")) return " phone,";
        return "";
    }
    public String dulSerieFilter(Integer dulSerie){
        if(dulSerie.equals(1)) return " dul_serie,";
        return "";
    }
    public String dulNumberFilter(Integer dulNumber){
        if(dulNumber.equals(1)) return " dul_number,";
        return "";
    }
    public String checkAlgDulSerie(Integer dulSerie){
        if(dulSerie.toString().length() != 4) return " dul_serie, должно быть 4 цифры,";
        return "";

    }
    public String checkAlgDulNumber(Integer dulNumber) {
        if (dulNumber.toString().length() != 6) return (" dul_number, должно быть 6 цифр,");
        return "";
    }
    public void checkValidPhone(String phone) throws NumberFormatException{
        if(!phone.matches("^((\\+7)[\\-]?)(\\(\\d{3}\\)[\\-]?)[\\d\\-]{9}$")){//+7(123)123-12-12
            throw new NumberFormatException("Неправильный формат phone поля");
        }
    }
    public void check18Years(String birthDay) throws DateTimeException{
        Date birthDateFormatDate = new Date(Integer.parseInt(birthDay.substring(0,4)),
                Integer.parseInt(birthDay.substring(5,7)), Integer.parseInt(birthDay.substring(8,10)));

        String curentDateString = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

        Date currentDate = new Date(Integer.parseInt(curentDateString.substring(0,4)),
                Integer.parseInt(curentDateString.substring(4,6)),Integer.parseInt(curentDateString.substring(6,8)));
        long diffInMillies = Math.abs(currentDate.getTime() - birthDateFormatDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        if(diff / 365 < 18) throw new DateTimeException("Нельзя сохранить жителя, которому меньше 18 лет");
    }


}
