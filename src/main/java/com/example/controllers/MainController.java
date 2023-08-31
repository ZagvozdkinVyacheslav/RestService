package com.example.controllers;

import com.example.database.entity.Citizen;
import com.example.service.CitizenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Citizen", description = "The Citizen API")
@RestController
@RequestMapping(value = "/")
public class MainController {
    @Autowired
    CitizenService citizenService;
    //@Operation(summary = "Start page", tags = "citizen")
    @GetMapping()
    public void startPage() {

    }
    /*@Operation(summary = "Gets all citizens", tags = "citizen")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the users by Optional params",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Citizen.class)))
                    }),
            @ApiResponse(responseCode = "404",description = "The citizen was not found according to the specified parameters"),
            @ApiResponse(responseCode = "500",description = "An error occurred caused by the server operation")
    })*/
    /*@GetMapping(path = "/citizens")
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

            modelAndView.setStatus(HttpStatusCode.valueOf(200));
            modelAndView.addObject("citizenList", citizenList);
        }catch (NoSuchElementException e){

            modelAndView.addObject("someMessage", e.getMessage());
            modelAndView.setStatus(HttpStatusCode.valueOf(404));
        }
        catch (Exception e){

            modelAndView.addObject("someMessage", "Произошла ошибка вызванная работой сервера");
            modelAndView.setStatus(HttpStatusCode.valueOf(500));
        }
        return modelAndView;
    }*/
    /*@Operation(summary = "Get one citizen", tags = "citizen")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found user by id(@PathVariable)",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Citizen.class)))
                    }),
            @ApiResponse(responseCode = "404",description = "The citizen was not found according to the id"),
            @ApiResponse(responseCode = "500",description = "An error occurred caused by the server operation"),
    })*/
    /*@GetMapping(path = "/citizens/{id}")//нет работы через html
    public ModelAndView getCitizen(@PathVariable(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Citizen citizen = citizenService.findCitizenById(id);
            List<Citizen> citizenList = new ArrayList<>();
            citizenList.add(citizen);
            modelAndView.addObject("citizenList", citizenList);
            modelAndView.setStatus(HttpStatusCode.valueOf(200));
        }catch (NoSuchElementException e){

            modelAndView.addObject("someMessage", "Такой элемент не найден!");
            modelAndView.setStatus(HttpStatusCode.valueOf(404));
        }
        catch (Exception e){

            modelAndView.addObject("someMessage", "Произошла ошибка вызванная работой сервера");
            modelAndView.setStatus(HttpStatusCode.valueOf(500));
        }
        return modelAndView;
    }*/
    /*@Operation(summary = "Create new citizen", tags = "citizen")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Create new citizen by params",
                    content = {
                            @Content(
                                    mediaType = "application/json")
                    }),
            @ApiResponse(responseCode = "400",description = "Required fields are not filled in + fields output"),
            @ApiResponse(responseCode = "400",description = "Such a user was created earlier")
    })*/
    @PostMapping(path = "/citizens")
    public ResponseEntity<String> addNewCitizen(@RequestBody @Valid Citizen citizen) {
        citizenService.saveCitizen(citizen);
        return new ResponseEntity<>("Успех", HttpStatus.CREATED);

    }
    /*@Operation(summary = "Update one citizen", tags = "citizen")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Update citizen by id and some params",
                    content = {
                            @Content(
                                    mediaType = "application/json")
                    }),
            @ApiResponse(responseCode = "400",description = "Data entry format error + fields output"),
            @ApiResponse(responseCode = "404",description = "There is no such citizen"),
            @ApiResponse(responseCode = "500",description = "After changing the parameters, 2 identical citizens appear in the database"),
    })*/
    /*@PutMapping(path = "/citizens/{id}")
    public ModelAndView modificationDataCitizen(@PathVariable(value = "id") Long id,
            @RequestParam(required = false,defaultValue = "") String last_name,
                                        @RequestParam(required = false,defaultValue = "") String first_name,
                                        @RequestParam(required = false,defaultValue = "") String middle_name,
                                        @RequestParam(required = false,defaultValue = "") String birth_date,
                                        @RequestParam(required = false,defaultValue = "") String phone,
                                        @RequestParam(required = false,defaultValue = "") String extra_phone,
                                        @RequestParam(required = false,defaultValue = "1") Integer dul_serie,
                                        @RequestParam(required = false,defaultValue = "1") Integer dul_number) {
        ModelAndView modelAndView = new ModelAndView();
        try{
            citizenService.modificateFields(id,last_name,first_name,middle_name,birth_date,phone,extra_phone,dul_serie,dul_number);
            modelAndView.addObject("citizen_id", "id гражданина = " + id.toString());
            modelAndView.setStatus(HttpStatusCode.valueOf(200));
        }catch (NoSuchElementException e){

            modelAndView.addObject("someMessage", e.getMessage());
            modelAndView.setStatus(HttpStatusCode.valueOf(404));
        }catch (DateTimeException e){

            modelAndView.addObject("someMessage", e.getMessage());
            modelAndView.setStatus(HttpStatusCode.valueOf(400));
        }catch (NonUniqueResultException e){

            modelAndView.addObject("someMessage", e.getMessage());
            modelAndView.setStatus(HttpStatusCode.valueOf(500));
        }catch (NumberFormatException e){

            modelAndView.addObject("someMessage", e.getMessage());
            modelAndView.setStatus(HttpStatusCode.valueOf(400));
        }catch (NoSuchFieldException e){

            modelAndView.addObject("someMessage", e.getMessage());
            modelAndView.setStatus(HttpStatusCode.valueOf(400));
        }
        return modelAndView;
    }*/
    /*@Operation(summary = "Delete citizen by id", tags = "citizen")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Delete citizen by id",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                                    )
                    })
    })*/
   /* @DeleteMapping (path = "/citizens/{id}")
    public ModelAndView deleteCitizen(@PathVariable(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            citizenService.deleteCitizenById(id);

            modelAndView.setStatus(HttpStatusCode.valueOf(204));
        }catch (TemplateInputException e){//если такого нет

            modelAndView.addObject("someMessage", "В базе данных нет гражданина с таким id");
            modelAndView.setStatus(HttpStatusCode.valueOf(404));
        }catch (Exception e){

            modelAndView.addObject("someMessage", "Произошла ошибка вызванная работой сервера");
            modelAndView.setStatus(HttpStatusCode.valueOf(500));
        }
        return modelAndView;
    }*/
}
