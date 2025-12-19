# Milestone 5

## Message Queue Integration

This milestone introduces **Asynchronous Messaging** into the architecture to decouple the trip planning process from the ranking calculation process.

### 1. Architecture Overview
We have integrated **RabbitMQ** to enable event-driven communication between the **Trip Service** and the **Ranking Service**. This transition moves the system from a purely synchronous REST-based architecture to a more resilient, scalable Event-Driven Architecture.

**Data Flow:**
1.  **User** sends a `POST /api/trip/plan` request to the **Trip Service**.
2.  **Trip Service** calculates the itinerary, saves it, and **immediately returns** the result to the user to ensure low latency.
3.  **Simultaneously**, the Trip Service publishes a `TripPlannedEvent` message to the **RabbitMQ Exchange** (`travelmate.trip.x`).
4.  **RabbitMQ** routes this message to the durable queue (`travelmate.trip.planned.q`).
5.  **Ranking Service** listens to this queue, picks up the event asynchronously, and performs complex ranking calculations in the background.

### 2. Integration Details
* **Message Broker:** RabbitMQ (deployed via Docker).
* **Framework:** Spring Boot AMQP (Spring Rabbit).
* **Serialization:** `Jackson2JsonMessageConverter` is used to convert Java Objects (Events) into JSON format, ensuring interoperability and readability.
* **Configuration:** We utilize `RabbitAdmin` beans to automatically declare exchanges, queues, and bindings upon application startup, ensuring the infrastructure is always ready.

### 3. Key Benefits Implemented

#### Decoupling
The **Trip Service** (Producer) no longer depends on the **Ranking Service** (Consumer) being online or responsive.
* *Before:* If the Ranking Service was slow or down, the user request would hang or fail.
* *Now:* The Trip Service returns immediately ("fire and forget"), and the Ranking Service processes the data at its own pace.

#### Fault Tolerance
If the **Ranking Service crashes** or is taken offline for maintenance:
1.  The **Trip Service** continues to function perfectly (users can still plan trips).
2.  Messages accumulate safely in the **RabbitMQ Queue**.
3.  When the **Ranking Service restarts**, it automatically consumes and processes all the missed messages. No data is lost.

#### Scalability
Because the services are decoupled via a queue, we can scale them independently. If traffic spikes to 10,000 trip requests per second:
* We can spin up multiple instances (replicas) of the **Ranking Service**.
* They will automatically share the workload from the queue (Round-Robin consumption) without requiring any changes to the Trip Service code.

---

## How to Run

### Prerequisites
* Docker & Docker Compose
* Java 17+ (for local development)
* Maven

### Steps
1.  **Build the Project:**
    ```bash
    ./mvnw clean package -DskipTests
    ```

2.  **Start the Infrastructure:**
    ```bash
    docker-compose up --build -d
    ```

3.  **Verify Services are Running:**
    * **Trip Service:** `http://localhost:8080`
    * **Ranking Service:** `http://localhost:8082`
    * **RabbitMQ Dashboard:** `http://localhost:15672` (User: `guest`, Pass: `guest`)

### Testing the Event Flow
You can trigger a trip plan to see the messaging in action:

```bash
curl -X POST http://localhost:8080/api/trip/plan \
-H "Content-Type: application/json" \
-d '{
    "origin": "London",
    "destination": "Paris",
    "departDate": "2025-06-01T10:00:00",
    "returnDate": "2025-06-10T18:00:00",
    "passengers": 2,
    "maxBudget": 1000,
    "maxDurationMinutes": 600,
    "maxStops": 1,
    "baggageRequired": true
}'