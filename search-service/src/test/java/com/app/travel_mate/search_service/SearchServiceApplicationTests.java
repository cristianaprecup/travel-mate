package com.app.travel_mate.search_service;

import com.app.travel_mate.search_service.api.SearchController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SearchServiceApplicationTests {

	@Autowired
	private SearchController searchController;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
		assertThat(searchController).isNotNull();
		assertThat(mockMvc).isNotNull();
	}

	@Test
	void healthCheckShouldReturnOk() throws Exception {
		mockMvc.perform(get("/api/search/health")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string("search-service OK"));
	}
}
