package com.example.controllers;

import com.example.database.dto.Citizen;
import com.example.database.repository.CitizensRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "requests")
public class MainController {

    @Autowired
    CitizensRepo citizensRepo;

    @GetMapping(path = "/citizens")
    public void getAllCitizens() {

    }
    @GetMapping(path = "/citizens/{id}")//with param
    public void getCitizen() {

    }
    @PostMapping(path = "/citizens")
    public void addNewCitizen() {

    }
    @PutMapping(path = "/citizens/{id}")
    public void modificationDataCitizen() {

    }
    @DeleteMapping (path = "/citizens/{id}")
    public void deleteCitizen() {

    }
}
