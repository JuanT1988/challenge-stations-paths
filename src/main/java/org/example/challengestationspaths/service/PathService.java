package org.example.challengestationspaths.service;

import org.example.challengestationspaths.dto.request.PathDTO;
import org.example.challengestationspaths.dto.response.PathResponseDTO;
import org.example.challengestationspaths.exception.BadRequestException;
import org.example.challengestationspaths.exception.NotFoundException;
import org.example.challengestationspaths.model.Path;

import java.util.List;

public interface PathService {

    void addPath(Long pathId, PathDTO request) throws BadRequestException;

    PathResponseDTO getPaths(long sourceId, long destinationId) throws BadRequestException, NotFoundException;

    List<Path> getAllPaths();

}
