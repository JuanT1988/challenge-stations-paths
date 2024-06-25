package org.example.challengestationspaths.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.challengestationspaths.dto.request.StationDTO;
import org.example.challengestationspaths.dto.response.ResponseDTO;
import org.example.challengestationspaths.exception.CustomHttpException;
import org.example.challengestationspaths.service.StationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Station Controller", description = "Controller to add station")
@RestController
@RequestMapping("/stations")
public class StationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StationController.class);

    @Autowired
    private StationService service;

    @Operation(
            summary = "Add station",
            description = "Add station with id in endpoint with path variable and name into request body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Add is ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request, the same name do not insert")
    })
    @RequestMapping(value = "/{station_id}", produces = "application/json", consumes = "application/json", method = RequestMethod.PUT)
    public ResponseEntity<ResponseDTO> updateStation(@PathVariable(value = "station_id") Long stationId,
                                        @RequestBody StationDTO request) {
        LOGGER.info("Received request to update station with id {} and name {}", stationId, request.getName());
        try {
            service.addStation(stationId, request.getName());
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.name()));
        } catch (CustomHttpException e) {
            return new ResponseEntity<>(new ResponseDTO(e.getStatus().name() + " - " + e.getMessage()), e.getStatus());
        } catch (Exception e) {
            logException(e);
            return new ResponseEntity<>(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Get all Station's",
            description = "Search all Station's in memory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok, search return data"),
            @ApiResponse(responseCode = "404", description = "Not Found, the search return empty data")
    })
    @RequestMapping(produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<?> getAllStations() {
        LOGGER.info("Search all Station's");
        try {
            return ResponseEntity.ok(service.getAllStations());
        } catch (CustomHttpException e) {
            return new ResponseEntity<>(new ResponseDTO(e.getStatus().name() + " - " + e.getMessage()), e.getStatus());
        } catch (Exception e) {
            logException(e);
            return new ResponseEntity<>(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void logException(Exception e) {
        LOGGER.error(e.getMessage());
        if (e.getMessage() == null) {
            LOGGER.warn("The error message is null, printstacktrace to obtain more information...");
            e.printStackTrace();
        }
    }

}
