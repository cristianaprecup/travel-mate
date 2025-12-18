package com.app.travel_mate.trip_service;

import com.app.travel_mate.application.facade.TravelPlanner;
import com.app.travel_mate.domain.model.Itinerary;
import com.app.travel_mate.interfaces.TripController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.app.travel_mate.domain.model.queries.TripQuery;

@SpringBootTest
@AutoConfigureMockMvc
class TripServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private TravelPlanner travelPlanner;

	private final ObjectMapper objectMapper =
			new ObjectMapper().registerModule(new JavaTimeModule());

	@Autowired
	private TripController tripController;

	@Test
	void planTripEndpointShouldReturn200() throws Exception {
		when(travelPlanner.planTrip(any(TripQuery.class)))
				.thenReturn(new Itinerary(
						Collections.emptyList(),
						Collections.emptyList(),
						Collections.emptyList(),
						Collections.emptyList()
				));

		TripQuery query = new TripQuery();
		query.setOrigin("OTP");
		query.setDestination("CDG");
		query.setDepartDate(LocalDateTime.now().plusDays(1));
		query.setReturnDate(LocalDateTime.now().plusDays(5));
		query.setPassengers(1);

		mockMvc.perform(post("/api/trip/plan")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(query)))
				.andExpect(status().isOk());
	}

	@Test
	void contextLoads() {
		assertThat(tripController).isNotNull();
		assertThat(mockMvc).isNotNull();
		assertThat(travelPlanner).isNotNull();
	}
}