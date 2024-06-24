package org.example.challengestationspaths.service;

import org.example.challengestationspaths.dto.request.PathDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomPathServiceTest {

    @Autowired
    private CustomPathService customPathService;

    @Test
    @Order(1)
    public void testAddPaths() {

        customPathService.addPath(10L, new PathDTO(10L, 11L, 50));
        customPathService.addPath(11L, new PathDTO(10L, 12L, 100));
        customPathService.addPath(12L, new PathDTO(10L, 13L, 60));
        customPathService.addPath(13L, new PathDTO(13L, 12L, 20));
        customPathService.addPath(14L, new PathDTO(16L, 13L, 10));
        customPathService.addPath(15L, new PathDTO(11L, 16L, 5));
        customPathService.addPath(16L, new PathDTO(14L, 12L, 30));
        customPathService.addPath(17L, new PathDTO(14L, 13L, 40));
        customPathService.addPath(18L, new PathDTO(15L, 13L, 120));
        customPathService.addPath(19L, new PathDTO(16L, 17L, 5));
        customPathService.addPath(20L, new PathDTO(17L, 18L, 5));
        customPathService.addPath(21L, new PathDTO(18L, 19L, 5));
        customPathService.addPath(22L, new PathDTO(19L, 20L, 5));
        customPathService.addPath(23L, new PathDTO(20L, 16L, 200));
        customPathService.addPath(24L, new PathDTO(20L, 17L, 10));
        Assert.notEmpty(customPathService.getAllPaths(), "Add 15 ppaths in list to " + customPathService.getAllPaths().size());
    }

}
