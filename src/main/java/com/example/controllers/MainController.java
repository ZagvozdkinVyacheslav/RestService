package com.example.controllers;

import com.example.database.entity.Citizen;
import com.example.interfaces.Marker;
import com.example.service.CitizenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Tag(name = "Citizen", description = "The Citizen API")
@Validated
@RestController
@RequestMapping(value = "/")
public class MainController {
    @Autowired
    CitizenService citizenService;
    @Autowired
    Validator validator;

    @Operation(summary = "Gets all citizens", tags = "citizen")
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
    })
    @GetMapping(path = "/citizens")
    public ResponseEntity<List> getCitizensByParams(@RequestBody @Validated(value = {Marker.onGetting.class}) @Valid Citizen citizen) {
        var listOfCitizens = citizenService.getAllCitizensByParams(citizen);
        return new ResponseEntity<>(listOfCitizens, HttpStatusCode.valueOf(200));
    }
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
    @GetMapping(path = "/citizens/{id}")//нет работы через html
    public ResponseEntity<Citizen> getCitizen(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(citizenService.getOneCitizen(id),HttpStatusCode.valueOf(200));
    }
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
    public ResponseEntity<Object> addNewCitizen(@RequestBody @Validated(value = {Marker.onCreate.class}) @Valid Citizen citizen) {

        return new ResponseEntity<>(citizenService.saveCitizen(citizen).getId(), HttpStatus.CREATED);

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
    @PutMapping(path = "/citizens/{id}")
    public ResponseEntity<Object> modificationDataCitizen(@PathVariable(value = "id") Long id,
                                                           @RequestBody @Validated({Marker.onUpdate.class}) @Valid Citizen citizen) {
        citizenService.updateCitizenByIdAndParams(id,citizen);
        return new ResponseEntity<>(id,HttpStatus.valueOf(200));
    }
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
   @DeleteMapping (path = "/citizens/{id}")
    public ResponseEntity<String> deleteCitizen(@PathVariable(value = "id") Long id) {
        citizenService.deleteCitizenById(id);
       return new ResponseEntity<>("",HttpStatus.valueOf(204));
    }
}
