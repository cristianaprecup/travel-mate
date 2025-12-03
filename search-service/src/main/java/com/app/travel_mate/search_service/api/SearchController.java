package com.app.travel_mate.search_service.api;

import com.app.travel_mate.search_service.application.SearchService;
import com.app.travel_mate.search_service.application.dto.SearchRequestDto;
import com.app.travel_mate.search_service.application.dto.SearchResultDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping
    public SearchResultDto search(@RequestBody SearchRequestDto request) {
        return searchService.search(request);
    }

    @GetMapping("/health")
    public String health() {
        return "search-service OK";
    }
}
