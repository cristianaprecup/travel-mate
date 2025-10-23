# TravelMate

TravelMate is a single place to plan a trip from end to end:
  - search flights, trains, coaches and accommodation;
  - automatically combine connections across providers and transport modes;
  - get city activities (museums, tours, food spots) with typical time windows;
  - find luggage storage options near hubs or attractions;
  - receive a unified day-by-day itinerary with times and map routes;

## Team Members

- Cristiana Precup 
- Nitu-Sararu Gabriela
- Alkhatib Hoda 
    
## Problem Statement & Goals

Planning a trip often means switching between many apps. Each app uses different filters, units, and formats. It is hard to compare options across transport types. Also, ideas for what to do at the destination are rarely linked to your transport and hotel choices.

### Goals
Deliver a clear end-to-end planning flow (explore → search transport → build connections → choose lodging → pick activities → see one unified itinerary) by turning data from different providers into a single common format for consistent searches and results.

## Key Features
 - Multimodal Search: find flights, trains, and coaches with filters (price, duration, stops, baggage).
 - Smart Connections: combine segments safely (minimum transfer times, layover checks).
 - Accommodation: search hotels/hostels/apartments with filters (price, rating, distance).
 - City Activities: discover POIs and tours with typical opening times and durations.
 - Luggage Storage: see nearby storage spots and available time windows.
 - Unified Itinerary: day-by-day plan with times, walking/transfer estimates, and map route links.
 - Ranking Options: sort results by cheapest, fastest, eco, or a balanced mix.
 - Export/Share: serializable itinerary (e.g., JSON) so the plan is easy to share and reproduce.

## Design Patterns

### 1) Adapter
Purpose: Different providers (air, rail, coach, hotels, activities, luggage) have distinct APIs and data shapes. The Adapter hides these differences behind a shared interface.

#### Advantages:

Clean separation between our core logic and external services.
Easy to add or replace a provider without changing the rest of the system.
Improves testing-mock the unified interface instead of real APIs.

### 2) Strategy 
Purpose: Users prefer different ranking goals (cheapest, fastest, eco, mixed). The Strategy pattern lets us plug in the scoring method the user selects.

#### Advantages:

Eliminates long if/else chains in ranking logic.
Allows new ranking strategies without touching the search engine.
Simplifies testing for each algorithm.

### 3) Builder
Purpose: An itinerary contains many interdependent parts (segments, hotel nights, visits, transfers). Builder assembles them step-by-step while keeping the result valid.

#### Advantages:

Prevents inconsistent plans (e.g., wrong time order, missing transfers).
Makes creation of complex objects clearer and safer.
Enables immutable, validated final itineraries.

### 4) Chain of Responsibility 
Purpose: The search engine processes data through a pipeline—fetch providers, merge, enrich, filter. Each step should be independent and replaceable.

#### Advantages:

Build flexible pipelines by reordering or swapping steps.
Reuse handlers across different flows (transport, lodging, etc.).
Simplifies debugging and unit testing per stage.

### 5) Facade 
Purpose: The UI should not coordinate adapters, strategies, and pipelines directly. A Facade exposes high-level entry points like “plan trip” or “search lodging,” hiding internal complexity.

#### Advantages:

Keeps the UI simple and stable even as internals evolve.
Reduces coupling between presentation and core layers.
Makes end-to-end testing easier and clearer.

### 6) Observer / Publish–Subscribe 
Purpose: Travel data can change dynamically (price, availability, schedule). The Observer pattern lets data sources publish updates that interested parts (alerts, UI) subscribe to.

#### Advantages:

Removes the need for constant manual polling.
Maintains loose coupling between data sources and listeners.
Enables future asynchronous or message-queue integrations.
