package org.example.challengestationspaths.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.challengestationspaths.dto.request.PathDTO;
import org.example.challengestationspaths.dto.response.ResponseDTO;
import org.example.challengestationspaths.exception.CustomHttpException;
import org.example.challengestationspaths.service.PathService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Path Controller", description = "Controller to add Path, and search the optimal route.")
@RestController
@RequestMapping("/paths")
public class PathController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PathController.class);

    @Autowired
    private PathService service;

    @Operation(
            summary = "Add path",
            description = "Add path with path_id in endpoint with path variable and source_id, destination_id and cost into request body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Add is ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request, validate source_id and destination_id, and exist errors, do not insert")
    })
    @RequestMapping(value = "/{path_id}", consumes = "application/json", produces = "application/json", method = RequestMethod.PUT)
    public ResponseEntity<ResponseDTO> updatePath(@PathVariable(name = "path_id") Long pathId,
                                     @RequestBody PathDTO request) {
        LOGGER.info("Request to update path with path_id {}", pathId);
        try {
            service.addPath(pathId, request);
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.name()));
        } catch (CustomHttpException e) {
            return new ResponseEntity<>(new ResponseDTO(e.getStatus().name() + " - " + e.getMessage()), e.getStatus());
        } catch (Exception e) {
            logException(e);
            return new ResponseEntity<>(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Get optimal route",
            description = "Get optimal route, using source_id and destination_id to obtain route with minimal cost")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok, when the optimal route is find"),
            @ApiResponse(responseCode = "400", description = "Bad Request, when error with source_id or destination_id")
    })
    @RequestMapping(value = "/{source_id}/{destination_id}", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<?> getShortestPath(@PathVariable(name = "source_id") Long sourceId,
                                           @PathVariable(name = "destination_id") Long destinationId) {
        LOGGER.info("Get optimal route with source_id {} and destination_id {}", sourceId, destinationId);
        try {
            return ResponseEntity.ok(service.getPaths(sourceId, destinationId));
        } catch (CustomHttpException e) {
            return new ResponseEntity<>(new ResponseDTO(e.getStatus().name() + " - " + e.getMessage()), e.getStatus());
        } catch (Exception e) {
            logException(e);
            return new ResponseEntity<>(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.name()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Get all Path's",
            description = "Search all Path's in memory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok, search return data"),
            @ApiResponse(responseCode = "404", description = "Not Found, the search return empty data")
    })
    @RequestMapping(produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPaths() {
        LOGGER.info("Search all Path's");
        try {
            return ResponseEntity.ok(service.getAllPaths());
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
