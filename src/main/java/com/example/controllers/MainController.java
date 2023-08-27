package com.example.controllers;

import com.example.builders.CitizenBuilder;
import com.example.database.dto.Citizen;
import com.example.database.repository.CitizensRepo;
import com.example.service.CitizenService;
import jakarta.persistence.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.exceptions.TemplateInputException;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/")
public class MainController {

    @Autowired
    CitizenService citizenService;
    @GetMapping()
    public ModelAndView startPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.setStatus(HttpStatusCode.valueOf(200));
        return modelAndView;
    }
    @GetMapping(path = "/citizens")
    public ModelAndView getCitizensByParams(@RequestParam String lastNameBtn,
                                            @RequestParam String firstNameBtn,
                                            @RequestParam String middleNameBtn,
                                            @RequestParam String birthDateBtn) {
        if(lastNameBtn.equals(""))lastNameBtn = null;
        if(firstNameBtn.equals(""))firstNameBtn = null;
        if(middleNameBtn.equals(""))middleNameBtn = null;
        if(birthDateBtn.equals(""))birthDateBtn = null;
        ModelAndView modelAndView = new ModelAndView();
        try {
            List<Citizen> citizenList = citizenService.findListOfCitizensByOptionalParams(lastNameBtn,firstNameBtn,middleNameBtn,birthDateBtn);
            modelAndView.setViewName("all_citizens");
            modelAndView.setStatus(HttpStatusCode.valueOf(200));
            modelAndView.addObject("citizenList", citizenList);
        }catch (NoSuchElementException e){
            modelAndView.setViewName("some_exception");
            modelAndView.addObject("someMessage", e.getMessage());
            modelAndView.setStatus(HttpStatusCode.valueOf(404));
        }
        catch (Exception e){
            modelAndView.setViewName("some_exception");
            modelAndView.addObject("someMessage", "Произошла ошибка вызванная работой сервера");
            modelAndView.setStatus(HttpStatusCode.valueOf(500));
        }

        return modelAndView;
    }
    @GetMapping(path = "/citizens/{id}")//нет работы через html
    public ModelAndView getCitizen(@PathVariable(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Citizen citizen = citizenService.findCitizenById(id);
            modelAndView.setViewName("one_citizen");
            List<Citizen> citizenList = new ArrayList<>();
            citizenList.add(citizen);
            modelAndView.addObject("citizenList", citizenList);
            modelAndView.setStatus(HttpStatusCode.valueOf(200));
        }catch (NoSuchElementException e){
            modelAndView.setViewName("some_exception");
            modelAndView.addObject("someMessage", "Такой элемент не найден!");
            modelAndView.setStatus(HttpStatusCode.valueOf(404));
        }
        catch (Exception e){
            modelAndView.setViewName("some_exception");
            modelAndView.addObject("someMessage", "Произошла ошибка вызванная работой сервера");
            modelAndView.setStatus(HttpStatusCode.valueOf(500));
        }
        return modelAndView;
    }
    @PostMapping(path = "/citizens")
    public ModelAndView addNewCitizen(@RequestParam(required = false,defaultValue = "") String last_name,
                              @RequestParam(required = false,defaultValue = "") String first_name,
                              @RequestParam(required = false,defaultValue = "") String middle_name,
                              @RequestParam(required = false,defaultValue = "") String birth_date,
                              @RequestParam(required = false,defaultValue = "") String phone,
                              @RequestParam(required = false,defaultValue = "") String extra_phone,
                              @RequestParam(required = false,defaultValue = "1") Integer dul_serie,
                              @RequestParam(required = false,defaultValue = "1") Integer dul_number) {
        ModelAndView modelAndView = new ModelAndView();
        try{
            citizenService.addCitizenInRepository(last_name,first_name, middle_name, birth_date,
                    phone, extra_phone, dul_serie,dul_number);
            Long id = citizenService.findAllCitizensByParams(last_name,first_name,middle_name,birth_date).get(0).getId();
            modelAndView.setViewName("success_add_citizen");
            modelAndView.addObject("citizen_id", "id гражданина = " + id.toString());
            modelAndView.setStatus(HttpStatusCode.valueOf(201));
        }catch (NullPointerException e){
            modelAndView.setViewName("some_exception");
            modelAndView.addObject("someMessage", e.getMessage());
            modelAndView.setStatus(HttpStatusCode.valueOf(400));
        }catch (NumberFormatException e){
            modelAndView.setViewName("some_exception");
            modelAndView.addObject("someMessage", e.getMessage());
            modelAndView.setStatus(HttpStatusCode.valueOf(400));
        }
        catch (NonUniqueResultException e){
            modelAndView.setViewName("some_exception");
            modelAndView.addObject("someMessage", e.getMessage());
            modelAndView.setStatus(HttpStatusCode.valueOf(400));
        }
        catch (DateTimeException e){
            modelAndView.setViewName("some_exception");
            modelAndView.addObject("someMessage", e.getMessage());
            modelAndView.setStatus(HttpStatusCode.valueOf(400));
        }

        return modelAndView;
    }
    @PutMapping(path = "/citizens/{id}")
    public void modificationDataCitizen(@PathVariable(value = "id") Long id,
            @RequestParam(required = false,defaultValue = "") String last_name,
                                        @RequestParam(required = false,defaultValue = "") String first_name,
                                        @RequestParam(required = false,defaultValue = "") String middle_name,
                                        @RequestParam(required = false,defaultValue = "") String birth_date,
                                        @RequestParam(required = false,defaultValue = "") String phone,
                                        @RequestParam(required = false,defaultValue = "") String extra_phone,
                                        @RequestParam(required = false,defaultValue = "1") Integer dul_serie,
                                        @RequestParam(required = false,defaultValue = "1") Integer dul_number) {
        ModelAndView modelAndView= new ModelAndView();


    }
    @DeleteMapping (path = "/citizens/{id}")
    public ModelAndView deleteCitizen(@PathVariable(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            citizenService.deleteCitizenById(id);
            modelAndView.setViewName("index");
            modelAndView.setStatus(HttpStatusCode.valueOf(200));
        }catch (TemplateInputException e){//если такого нет
            modelAndView.setViewName("some_exception");
            modelAndView.addObject("someMessage", "В базе данных нет гражданина с таким id");
            modelAndView.setStatus(HttpStatusCode.valueOf(404));
        }catch (Exception e){
            modelAndView.setViewName("some_exception");
            modelAndView.addObject("someMessage", "Произошла ошибка вызванная работой сервера");
            modelAndView.setStatus(HttpStatusCode.valueOf(500));
        }

        return modelAndView;

    }
}
