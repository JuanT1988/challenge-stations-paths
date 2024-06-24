package org.example.challengestationspaths.service;

import org.example.challengestationspaths.exception.BadRequestException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomStationServiceTest {

    @Autowired
    private CustomStationService customStationService;

    @Test
    @Order(1)
    public void testAddStations() {
        customStationService.addStation(10, "Barcelona");
        customStationService.addStation(11, "Paris");
        customStationService.addStation(12, "Berlin");
        customStationService.addStation(13, "Roma");
        customStationService.addStation(14, "Moscu");
        customStationService.addStation(15, "Madrid");
        customStationService.addStation(16, "Lisboa");
        customStationService.addStation(17, "Londres");
        customStationService.addStation(18, "Ginebra");
        customStationService.addStation(19, "El Cairo");
        customStationService.addStation(20, "Johanesburgo");
        Assert.notEmpty(customStationService.getAllStations(), "There should be " + customStationService.getAllStations().size() + " stations");
    }

    @Test
    @Order(2)
    public void testAddStationsError() {
        Assertions.assertThrows(BadRequestException.class, () -> customStationService.addStation(10, "Barcelona"));
    }
}
