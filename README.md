
# Ticket Management Simulation System

This system simulates a ticket-purchasing environment where **vendors** add tickets to a shared `TicketPool`, and **customers** retrieve tickets from the pool. It is designed to handle concurrent operations, ensuring proper synchronization and interaction between vendors, customers, and the ticket pool.

## Prerequisites 

1. JDK 21 or higher
2. Node v20.18.0 or higher

## How to Run The Main Project

1.  Clone the repository.

    `git clone https://github.com/Deeshon/oop-cw.git`



2. Move into the oop-cw directory

   `cd oop-cw`


3. Compile the project.

    `mvn clean install`



4. Move into the cli directory and compile the project

    `cd cli`
   <br>
   `mvn clean install`


5. Move into the backend directory and compile the project

    `cd ..`

   `cd backend`
   
   `mvn clean install`


5.  Run the project:

    `mvn spring-boot:run`


6. Move into the frontend directory

    `cd ..`

    `cd frontend`


7. Install the node dependencies

    `npm install`


8. Run the frontend

    `npm run dev`

9. Open `http://localhost:3001` in your browser to access the frontend.

## Key Features

### 1. **TicketPool Management**

-   A `TicketPool` acts as a shared resource with a fixed capacity.
-   Vendors add tickets to the pool, and customers retrieve tickets.
-   Includes synchronization mechanisms to prevent race conditions:
    -   Vendors wait if the pool is full.
    -   Customers wait if the pool is empty.

### 2. **Concurrency and Synchronization**

-   Uses `ReentrantLock` and `Condition` to synchronize vendor and customer operations.
-   Supports multiple threads for vendors and customers operating concurrently.

### 3. **Ticket Sale Notification**

-   Whenever a customer retrieves a ticket, a notification is sent to a backend system via an HTTP request.
-   Notifications include details such as vendor ID, customer ID, ticket ID, and timestamp.

### 4. **Simulation Lifecycle**

-   Vendors and customers are dynamically created and managed using threads.
-   Supports starting and stopping simulations with various configurations.

## Architecture Overview

The system has three main components:

1.  **Frontend**: Provides a web-based interface for interacting with the simulation.
2.  **Backend**: Exposes APIs for managing vendors, customers, and ticket sales.
3.  **CLI**: Manages the concurrency and ticket-purchasing logic.

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

    `npm run dev`

4.  Open `http://localhost:3000` in your browser to access the frontend.

## Backend

The backend is built using **Spring Boot** and follows a structured design pattern to ensure scalability, maintainability, and testability. The system uses the **Entity-Repository-Service-Service Implementation-Controller** pattern, which provides a clear separation of concerns.

### Design Pattern Overview

1.  **Entity**  
    Represents the database model or business object. Entities are mapped to database tables and define the structure of the data.

    -   Example: `Vendor`, `Customer`, and `Ticket` classes represent the primary entities in the system.
2.  **Repository**  
    Handles data persistence and retrieval. Repositories are interfaces that use Spring Data JPA to simplify database operations.

    -   Example:
        -   `VendorRepository`: Handles CRUD operations for vendors.
        -   `CustomerRepository`: Manages customer-related data.
        -   `TicketRepository`: Handles ticket pool persistence.
3.  **Service**  
    Defines the business logic and core functionality. Services provide an abstraction layer between controllers and repositories.

    -   Example:
        -   `VendorService`: Defines methods for adding, retrieving, and removing vendors.
        -   `CustomerService`: Manages customer-related operations.
        -   `SimulationService`: Orchestrates the lifecycle of the simulation.
4.  **Service Implementation**  
    Provides concrete implementations for service interfaces. These classes contain the actual business logic and interact with repositories.

    -   Example:
        -   `VendorServiceImpl`: Implements the methods defined in `VendorService`.
        -   `CustomerServiceImpl`: Implements `CustomerService`.
5.  **Controller**  
    Exposes RESTful APIs for interacting with the backend. Controllers handle HTTP requests and responses and delegate operations to services.

    -   Example:
        -   `VendorController`: Manages vendor-related endpoints.
        -   `CustomerController`: Handles customer-related endpoints.
        -   `SimulationController`: Exposes APIs for starting, stopping, and monitoring the simulation.

----------

### Key Features Of The Design Pattern

-   **Entity Mapping**  
    The entities use annotations such as `@Entity`, `@Id`, and `@GeneratedValue` for ORM (Object-Relational Mapping) with JPA.

-   **Repository Simplification**  
    Repositories extend `JpaRepository` or `CrudRepository`, automatically providing common operations such as `save`, `findById`, and `delete`.

-   **Service Abstraction**  
    The service layer encapsulates the logic for operations such as:

    -   Adding or removing vendors/customers.
    -   Notifying the simulation of ticket updates.
    -   Managing ticket pools dynamically.
-   **Controller Interaction**  
    REST controllers expose endpoints for:

    -   Vendor management (`/api/simulation/vendor/add`, `/api/simulation/vendor/remove`).
    -   Customer management (`/api/simulation/customer/add`, `/api/simulation/customer/remove`).
    -   Simulation lifecycle (`/api/simulation/start`, `/api/simulation/stop`).
    -   Ticket sale notifications (`/api/sales`).

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
3.
`mvn clean install`

4.  Run the server:

    `java -jar target/backend-0.0.1-SNAPSHOT.jar`

5.  The server will start at `http://localhost:8080`.

Here’s an updated README overview that includes the **frontend** and **backend** sections:

----------

# Ticket Management Simulation System

This system simulates a ticket-purchasing environment where **vendors** add tickets to a shared `TicketPool`, and **customers** retrieve tickets from the pool. It is designed to handle concurrent operations, ensuring proper synchronization and interaction between vendors, customers, and the ticket pool.

----------

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