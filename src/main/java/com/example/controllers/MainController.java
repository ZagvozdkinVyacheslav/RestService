package com.example.controllers;

import com.example.builders.CitizenBuilder;
import com.example.database.dto.Citizen;
import com.example.database.repository.CitizensRepo;
import com.example.service.CitizenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
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
        return modelAndView;
    }
    @GetMapping(path = "/citizens")
    public ModelAndView getCitizensByParams(@RequestParam(required = false,defaultValue = "off") String lastNameBtn,
                                            @RequestParam(required = false,defaultValue = "off") String firstNameBtn,
                                            @RequestParam(required = false,defaultValue = "off") String middleNameBtn,
                                            @RequestParam(required = false,defaultValue = "off") String birthDateBtn) {
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
    public void addNewCitizen(@RequestParam String first_name,@RequestParam String last_name,
                              @RequestParam String middle_name,@RequestParam String birth_date,
                              @RequestParam String phone,@RequestParam String extra_phone,
                              @RequestParam String dul_serie,@RequestParam String dul_number) {
        citizenService.addCitizenInRepository(first_name,last_name, middle_name, birth_date,
                phone, extra_phone, dul_serie,dul_number);
    }
    @PutMapping(path = "/citizens/{id}")
    public void modificationDataCitizen() {

    }
    @DeleteMapping (path = "/citizens/{id}")
    public void deleteCitizen() {

    }
}
