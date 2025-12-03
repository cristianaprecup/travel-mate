package com.app.travel_mate.search_service.application;

import com.app.travel_mate.search_service.application.dto.SearchRequestDto;
import com.app.travel_mate.search_service.application.dto.SearchResultDto;
import com.app.travel_mate.search_service.domain.model.UserPrefs;
import com.app.travel_mate.search_service.domain.search.SearchEngine;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    private final SearchEngine searchEngine;

    public SearchService(SearchEngine searchEngine) {
        this.searchEngine = searchEngine;
    }

    public SearchResultDto search(SearchRequestDto request) {
        validate(request);

        UserPrefs prefs = new UserPrefs(
                request.from(),
                request.to(),
                request.startDate(),
                request.endDate(),
                request.maxBudget()
        );

        var result = searchEngine.search(prefs);
        return SearchResultDto.fromDomain(result);
    }

    private void validate(SearchRequestDto request) {
        if (request.from() == null || request.from().isBlank()) {
            throw new IllegalArgumentException("Field 'from' must not be empty");
        }
        if (request.to() == null || request.to().isBlank()) {
            throw new IllegalArgumentException("Field 'to' must not be empty");
        }
        if (request.maxBudget() <= 0) {
            throw new IllegalArgumentException("Field 'maxBudget' must be positive");
        }
    }
}
