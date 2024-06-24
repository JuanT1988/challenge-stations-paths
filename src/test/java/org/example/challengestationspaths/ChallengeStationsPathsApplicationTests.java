package org.example.challengestationspaths;

import org.assertj.core.api.Assertions;
import org.example.challengestationspaths.controller.PathController;
import org.example.challengestationspaths.controller.StationController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChallengeStationsPathsApplicationTests {

	@Autowired
	StationController stationController;
	@Autowired
	PathController pathController;

	@Test
	void contextLoads() {
		Assertions.assertThat(stationController).isNotNull();
		Assertions.assertThat(pathController).isNotNull();
	}

}
