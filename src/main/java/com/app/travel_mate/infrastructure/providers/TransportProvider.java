package com.app.travel_mate.infrastructure.providers;

import com.app.travel_mate.domain.model.options.TransportOption;
import com.app.travel_mate.domain.model.queries.TransportQuery;
import java.util.List;

public interface TransportProvider extends Provider {

    List<TransportOption> search(TransportQuery q);
}