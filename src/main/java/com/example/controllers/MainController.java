package com.example.controllers;

import com.example.builders.CitizenBuilder;
import com.example.database.dto.Citizen;
import com.example.database.repository.CitizensRepo;
import com.example.service.CitizenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping(value = "/")
public class MainController {

    @Autowired
    CitizenService citizenService;

    @GetMapping(path = "/citizens")
    public ModelAndView getAllCitizens() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("all_citizens");
        List<Citizen> citizenList = citizenService.findAllCitizens();
        modelAndView.addObject("citizenList", citizenList);
        return modelAndView;
    }
    /*@GetMapping(path = "/citizens/{id}")//with param
    public void getCitizen() {

    }*/
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
