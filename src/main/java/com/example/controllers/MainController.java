package com.example.controllers;

import com.example.database.entity.Citizen;
import com.example.exception.NotFindException;
import com.example.exception.UniqueException;
import com.example.interfaces.Marker;
import com.example.service.CitizenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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


import java.util.List;

@Tag(name = "Citizen controller", description = "The Citizen API")
@Validated
@RestController
@RequestMapping(value = "/")
public class MainController {
    @Autowired
    CitizenService citizenService;
    @Autowired
    Validator validator;

    @Operation(
            summary = "get list of citizens",
            description = "Makes it possible to get a list of citizens by optional parameters"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the users by Optional params",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Citizen.class)))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Citizen wasn't find by this params",
                    content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "Citizen wasn't find by this params"))
            }),
            @ApiResponse(responseCode = "500",
                    description = "An error occurred caused by the server operation",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(example = "An error occurred caused by the server operation"))
                    }
            )
    })
    @GetMapping(path = "/citizens")
    public ResponseEntity<List> getCitizensByParams(@Parameter(description = "pojo object of Citizen with params")
                                                        @RequestBody @Validated(value = {Marker.onGetting.class})
                                                        @Valid Citizen citizen) throws NotFindException {
        var listOfCitizens = citizenService.getAllCitizensByParams(citizen);
        return new ResponseEntity<>(listOfCitizens, HttpStatusCode.valueOf(200));
    }
    @Operation(
            summary = "get citizen by id",
            description = "Makes it possible to get citizen by id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the users by id",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Citizen.class)
                            ),

                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Citizen wasn't find by this id",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(example = "Citizen wasn't find by this id"))
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "An error occurred caused by the server operation",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(example = "An error occurred caused by the server operation"))
                    }
            )
    })
    @GetMapping(path = "/citizens/{id}")
    public ResponseEntity<Citizen> getCitizen(@Parameter(description = "path variable id of Citizen")@PathVariable(value = "id") Long id) throws NotFindException {
        return new ResponseEntity<>(citizenService.getOneCitizen(id),HttpStatusCode.valueOf(200));
    }
    @Operation(summary = "Create new citizen",
            description = "Makes it possible to create citizen by citizens params")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Create new citizen by params",
                    content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "id")
                    ),

                    }
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Error reading the request body",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(example = "Error reading the request body"))
                    }
            ),

            @ApiResponse(
                    responseCode = "400",
                    description = "Error with validation params",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(example = "Error with validation params"))
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "An error occurred caused by the server operation",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(example = "An error occurred caused by the server operation"))
                    }
            )
    })
    @PostMapping(path = "/citizens")
    public ResponseEntity<Object> addNewCitizen(@Parameter(description = "pojo object of Citizen with params")@RequestBody @Validated(value = {Marker.onCreate.class}) @Valid Citizen citizen) throws UniqueException {
        return new ResponseEntity<>(citizenService.saveCitizen(citizen).getId(), HttpStatus.CREATED);
    }
    @Operation(summary = "Update one citizen",
            description = "Makes it possible to update citizen by citizens params")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Update citizen by id and some params",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(example = "id")
                            )
                    }),
            @ApiResponse(
                    responseCode = "422",
                    description = "Error reading the request body",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(example = "Error reading the request body"))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error with validation params",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(example = "Error with validation params"))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The error of the absence of a mutable Citizen",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(example = "The error of the absence of a mutable Citizen")
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "An error occurred caused by the server operation",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(example = "An error occurred caused by the server operation")
                            )
                    }
            )
    })
    @PutMapping(path = "/citizens/{id}")
    public ResponseEntity<Object> modificationDataCitizen(@Parameter(description = "path variable id of Citizen")
                                                              @PathVariable(value = "id") Long id,
                                                          @Parameter(description = "pojo object of Citizen with params")
                                                          @RequestBody @Validated({Marker.onUpdate.class}) @Valid Citizen citizen) throws UniqueException, NotFindException {
        citizenService.updateCitizenByIdAndParams(id,citizen);
        return new ResponseEntity<>(id,HttpStatus.valueOf(200));
    }
    @Operation(summary = "Delete citizen by id",
            description = "Makes it possible to delete citizen by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Delete citizen by id",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(example = "")
                                    )
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Citizen wasn't find by this id",
                    content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(example = "Citizen wasn't find by this id")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "An error occurred caused by the server operation",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "An error occurred caused by the server operation")
                    )
            )
    })
   @DeleteMapping (path = "/citizens/{id}")
    public ResponseEntity<String> deleteCitizen(
                            @Parameter(description = "path variable id of Citizen")
                            @PathVariable(value = "id") Long id
    ) throws NotFindException {
        citizenService.deleteCitizenById(id);
       return new ResponseEntity<>("",HttpStatus.valueOf(204));
    }
}
