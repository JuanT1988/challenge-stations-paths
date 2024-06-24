package org.example.challengestationspaths.data;

import org.example.challengestationspaths.dto.request.PathDTO;
import org.example.challengestationspaths.model.Path;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DataContainerTest {

    @Autowired
    private DataContainer dataContainer;

    @Test
    @Order(1)
    public void testAddStations() {
        dataContainer.addStation(1, "Buenos Aires");
        dataContainer.addStation(2, "Cordoba");
        Assert.isTrue(dataContainer.getStations().size() == 2, "There are 11 stations in list");
        Assert.isTrue(dataContainer.getStationMap().size() == 2, "There are 11 stations in list");
    }

    @Test
    @Order(2)
    public void testAddPaths() {
        dataContainer.addPath(new Path(1L, new PathDTO(2L, 1L, 10)));
        Assert.isTrue(dataContainer.getPaths().size() == 1, "There are 1 paths in list");
    }

    @Test
    @Order(3)
    public void testGetPathByStationIdsNull() {
        Assert.notNull(dataContainer.getPathByStationIds(2L, 1L), "Exist path");
    }

    @Test
    @Order(4)
    public void testGetPathByStationIds() {
        Assert.isNull(dataContainer.getPathByStationIds(16L, 20L), "Path do not exist");
    }

    @Test
    @Order(5)
    public void testGetStationsByPaths() {
        Assert.isTrue(dataContainer.getStationsByPaths(2L).size() == 2, "There are 2 stations in list");
    }

}
