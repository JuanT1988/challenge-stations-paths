package org.example.challengestationspaths.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.challengestationspaths.dto.request.StationDTO;
import org.example.challengestationspaths.dto.response.ResponseDTO;
import org.example.challengestationspaths.exception.CustomHttpException;
import org.example.challengestationspaths.model.Station;
import org.example.challengestationspaths.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Station Controller", description = "Controller to add station")
@RestController
@RequestMapping("/stations")
public class StationController {

    @Autowired
    private StationService service;

    @Operation(
            summary = "Add station",
            description = "Add station with id in endpoint with path variable and name into request body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Add is ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request, the same name do not insert")
    })
    @PutMapping(value = "/{station_id}", produces = "application/json")
    public ResponseDTO updateStation(@PathVariable(value = "station_id") Long stationId,
                                  @RequestBody StationDTO request) {
        try {
            service.addStation(stationId, request.getName());
            return new ResponseDTO(HttpStatus.OK.name());
        } catch (CustomHttpException e) {
            return new ResponseDTO(e.getStatus().name() + " - " + e.getMessage());
        } catch (Exception e) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.name());
        }
    }

    @Operation(
            summary = "Get all Station's",
            description = "Search all Station's in memory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok, search return data"),
            @ApiResponse(responseCode = "404", description = "Not Found, the search return empty data")
    })
    @GetMapping
    public List<Station> getAllStations() {
        return service.getAllStations();
    }

}
