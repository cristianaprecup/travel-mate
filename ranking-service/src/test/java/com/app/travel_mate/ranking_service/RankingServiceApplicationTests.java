package com.app.travel_mate.ranking_service;

import com.app.travel_mate.domain.model.options.StayOption;
import com.app.travel_mate.domain.model.options.TransportOption;
import com.app.travel_mate.ranking_service.controller.RankingController;
import com.app.travel_mate.ranking_service.strategy.CheapestRankingStrategy;
import com.app.travel_mate.ranking_service.strategy.FastestRankingStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RankingServiceApplicationTests {

	@Autowired
	private RankingController rankingController;

	@Autowired
	private CheapestRankingStrategy cheapestStrategy;

	@Autowired
	private FastestRankingStrategy fastestStrategy;

	@Test
	void contextLoads() {
		assertThat(rankingController).isNotNull();
		assertThat(cheapestStrategy).isNotNull();
		assertThat(fastestStrategy).isNotNull();
	}

	@Test
	void strategyLogicTest() {
		assertThat(cheapestStrategy.getStrategyName()).isEqualTo("cheapest");
	}

	@Test
	void cheapestStrategyShouldCalculateCorrectScore() {
		TransportOption t = new TransportOption(
				"1", null, "Flight", "A", "B",
				LocalDateTime.now(), LocalDateTime.now().plusHours(2),
				120, 0, true, 100
		);

		StayOption s = new StayOption(
				"2", "Hotel", "Hotel", "Address",
				LocalDateTime.now(), LocalDateTime.now(),
				200, 0
		);

		int score = cheapestStrategy.score(t, s, Collections.emptyList());
		assertThat(score).isEqualTo(-300);
	}

	@Test
	void fastestStrategyShouldCalculateCorrectScore() {
		TransportOption t = new TransportOption(
				"1", null, "Flight", "A", "B",
				LocalDateTime.now(), LocalDateTime.now().plusHours(2),
				120, 0, true, 100
		);

		int score = fastestStrategy.score(t, null, Collections.emptyList());
		assertThat(score).isEqualTo(-120);
	}
}
