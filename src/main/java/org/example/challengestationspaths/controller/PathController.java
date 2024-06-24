package org.example.challengestationspaths.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.challengestationspaths.dto.request.PathDTO;
import org.example.challengestationspaths.dto.response.ResponseDTO;
import org.example.challengestationspaths.exception.CustomHttpException;
import org.example.challengestationspaths.model.Path;
import org.example.challengestationspaths.service.PathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Path Controller", description = "Controller to add Path, and search the optimal route.")
@RestController
@RequestMapping("/paths")
public class PathController {

    @Autowired
    private PathService service;

    @Operation(
            summary = "Add path",
            description = "Add path with path_id in endpoint with path variable and source_id, destination_id and cost into request body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Add is ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request, validate source_id and destination_id, and exist errors, do not insert")
    })
    @PutMapping("/{path_id}")
    public ResponseDTO updatePath(@PathVariable(name = "path_id") Long pathId,
                                  @RequestBody PathDTO request) {
        try {
            service.addPath(pathId, request);
            return new ResponseDTO(HttpStatus.OK.name());
        } catch (CustomHttpException e) {
            return new ResponseDTO(e.getStatus().name() + " - " + e.getMessage());
        } catch (Exception e) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.name());
        }
    }

    @Operation(
            summary = "Get optimal route",
            description = "Get optimal route, using source_id and destination_id to obtain route with minimal cost")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok, when the optimal route is find"),
            @ApiResponse(responseCode = "400", description = "Bad Request, when error with source_id or destination_id")
    })
    @GetMapping("/{source_id}/{destination_id}")
    public Object getShortestPath(@PathVariable(name = "source_id") Long sourceId,
                                           @PathVariable(name = "destination_id") Long destinationId) {
        try {
            return service.getPaths(sourceId, destinationId);
        } catch (CustomHttpException e) {
            return new ResponseDTO(e.getStatus().name() + " - " + e.getMessage());
        } catch (Exception e) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.name());
        }
    }

    @Operation(
            summary = "Get all Path's",
            description = "Search all Path's in memory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok, search return data"),
            @ApiResponse(responseCode = "404", description = "Not Found, the search return empty data")
    })
    @GetMapping()
    public List<Path> getAllPaths() {
        return service.getAllPaths();
    }

}
