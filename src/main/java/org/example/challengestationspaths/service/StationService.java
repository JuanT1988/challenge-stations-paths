package org.example.challengestationspaths.service;

import org.example.challengestationspaths.exception.BadRequestException;
import org.example.challengestationspaths.exception.NotFoundException;
import org.example.challengestationspaths.model.Station;

import java.util.List;

public interface StationService {

    void addStation(long stationId, String name) throws BadRequestException;

    List<Station> getAllStations() throws NotFoundException;
}
