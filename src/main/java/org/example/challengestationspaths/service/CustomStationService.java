package org.example.challengestationspaths.service;

import org.example.challengestationspaths.data.DataContainer;
import org.example.challengestationspaths.exception.BadRequestException;
import org.example.challengestationspaths.exception.NotFoundException;
import org.example.challengestationspaths.model.Station;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("stationService")
public class CustomStationService implements StationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomStationService.class);

    @Autowired
    private DataContainer dataContainer;

    @Override
    public void addStation(long stationId, String name) throws BadRequestException {
        if (dataContainer.getStationMap().containsValue(name)) {
            LOGGER.warn("Station {} already exists", name);
            throw new BadRequestException("Station " + name + " already exists");
        }
        dataContainer.addStation(stationId, name);
    }

    @Override
    public List<Station> getAllStations() throws NotFoundException {
        if (dataContainer.getStations().isEmpty()) {
            LOGGER.warn("No stations found in memory");
            throw new NotFoundException("No stations found in memory");
        }
        return dataContainer.getStations();
    }

}
