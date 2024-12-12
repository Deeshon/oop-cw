## Key Features

### **1. TicketPool Management**

-   A `TicketPool` acts as a shared resource with a fixed capacity.
-   Vendors add tickets to the pool, and customers retrieve tickets.
-   Includes synchronization mechanisms to prevent race conditions:
    -   Vendors wait if the pool is full.
    -   Customers wait if the pool is empty.

### **2. Concurrency and Synchronization**

-   Uses `ReentrantLock` and `Condition` to synchronize vendor and customer operations.
-   Supports multiple threads for vendors and customers operating concurrently.

### **3. Ticket Sale Notification**

-   Whenever a customer retrieves a ticket, a notification is sent to a backend system via an HTTP request.
-   Notifications include details such as vendor ID, customer ID, ticket ID, and timestamp.

### **4. Simulation Lifecycle**

-   Vendors and customers are dynamically created and managed using threads.
-   Supports starting and stopping simulations with various configurations.

----------

## Architecture Overview

The system has three main components:

1.  **Frontend**: Provides a web-based interface for interacting with the simulation.
2.  **Backend**: Exposes APIs for managing vendors, customers, and ticket sales.
3.  **Core Simulation**: Manages the concurrency and ticket-purchasing logic.

----------

## Frontend

The frontend is a React-based application designed to interact with the simulation.

### Key Features:

-   **Control Panel**: Start, stop, and configure the simulation.
-   **Vendor Management**: Add and remove vendors dynamically.
-   **Customer Management**: Add and remove customers dynamically.
-   **Real-Time Monitoring**: Displays the current status of vendors, customers, and tickets.

### Key Technologies:

-   **React** with TypeScript for UI components.
-   **Ant Design** for styled components.
-   **Axios** or native fetch for API communication.

### How to Run:

1.  Navigate to the `frontend` directory:

    `cd frontend`

2.  Install dependencies:

    `npm install`

3.  Start the development server:

    `npm start`

4.  Open `http://localhost:3000` in your browser to access the frontend.

----------

## Backend

The backend is built with **Spring Boot** and serves as the API layer for the simulation.

### Key Features:

-   **API Endpoints**:
    -   **`/api/simulation/start`**: Starts the simulation.
    -   **`/api/simulation/stop`**: Stops the simulation.
    -   **`/api/simulation/vendor/all`**: Retrieves all active vendors.
    -   **`/api/simulation/vendor/add`**: Adds a new vendor.
    -   **`/api/simulation/vendor/remove`**: Removes an existing vendor.
    -   **`/api/sales`**: Receives ticket sale notifications.
-   **Centralized Configuration**: Dynamically adjust ticket release rates, retrieval rates, and pool capacity.
-   **Database Integration** (optional): Can be extended to log ticket sale events persistently.

### Key Technologies:

-   **Spring Boot** for API development.
-   **Jackson** for JSON serialization and deserialization.
-   **RESTful APIs** for communication with the frontend and simulation core.

### How to Run:

1.  Navigate to the `backend` directory:

    `cd backend`

2.  Build the application:

    `mvn clean install`

3.  Run the server:

    `java -jar target/backend-0.0.1-SNAPSHOT.jar`

4.  The server will start at `http://localhost:8080`.

----------

## Core Simulation

The core simulation handles the concurrent operations of vendors and customers on the `TicketPool`. It is implemented as a multi-threaded Java application.

### Classes Overview

**1. TicketPool**

Manages tickets with a synchronized queue:

-   **`addTickets`**: Allows vendors to add tickets. If the pool is full, the vendor waits.
-   **`removeTicket`**: Allows customers to purchase tickets. If the pool is empty, the customer waits.

**2. Vendor**

Represents a vendor that periodically adds tickets to the pool at a specified rate.

**3. Customer**

Represents a customer that retrieves tickets from the pool at a specified rate.

**4. Ticket**

A data structure representing individual tickets with attributes like:

-   `ticketId`
-   `vendorId`
-   `customerId` (set upon purchase)

**5. TicketSale**

A data structure for recording sales of tickets, including:

-   Vendor ID
-   Customer ID
-   Ticket ID
-   Number of tickets available
-   Timestamp of the sale

**6. Simulation**

Manages the lifecycle of the simulation, including:

-   Starting and stopping vendor/customer threads.
-   Adding/removing vendors and customers during runtime.

## Testing

Unit tests are provided for core functionalities like `addTickets` and `removeTicket` in the `TicketPool`. Test scenarios include:

-   Adding tickets until the pool is full.
-   Customers waiting for tickets when the pool is empty.
-   Concurrent access by multiple vendors and customers.

Run tests with:

`mvn test`