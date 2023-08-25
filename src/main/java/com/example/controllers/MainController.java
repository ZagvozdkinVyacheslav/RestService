package com.example.controllers;

import com.example.builders.CitizenBuilder;
import com.example.database.dto.Citizen;
import com.example.database.repository.CitizensRepo;
import com.example.service.CitizenService;
import jakarta.persistence.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView getCitizensByParams(@RequestParam(required = false,defaultValue = "off") String lastNameBtn,
                                            @RequestParam(required = false,defaultValue = "off") String firstNameBtn,
                                            @RequestParam(required = false,defaultValue = "off") String middleNameBtn,
                                            @RequestParam(required = false,defaultValue = "off") Date birthDateBtn) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("all_citizens");
        List<Citizen> citizenList = citizenService.findAllCitizensByParams(lastNameBtn,firstNameBtn,middleNameBtn,birthDateBtn);
        modelAndView.addObject("citizenList", citizenList);
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
            modelAndView.setViewName("success_add_citizen");
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




        return modelAndView;
    }
    @PutMapping(path = "/citizens/{id}")
    public void modificationDataCitizen() {

    }
    @DeleteMapping (path = "/citizens/{id}")
    public void deleteCitizen() {

    }
}
