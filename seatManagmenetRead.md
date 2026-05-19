# Seat Management Architecture

This document explains the seat management module in the Flickora project, with a focus on the Java files you own and how they connect to the full system.

## 1. Overview

Seat management is part of the Online Movie Ticket System. It handles:

- creating seats for showtimes
- reading the seat list
- updating seat details
- deleting seats
- updating seat status for reservations

The module uses a text-file database (`seats.txt`) under `WEB-INF/data/` and follows a common Java web MVC architecture.

## 2. Key files in the seat management module

### Model classes

- `src/model/Seat.java`
  - Base entity for seat records.
  - Fields: `id`, `showtimeId`, `seatNumber`, `status`, `type`, `price`.
  - Provides getters/setters and `getCategory()`.

- `src/model/RegularSeat.java`
  - Extends `Seat`.
  - Implements concrete seat type `Regular`.
  - Sets price to `15.00`.
  - Overrides `getCategory()`.

- `src/model/VIPSeat.java`
  - Extends `Seat`.
  - Implements concrete seat type `VIP`.
  - Sets price to `25.00`.
  - Overrides `getCategory()`.

### Service classes

- `src/service/SeatService.java`
  - Main seat business logic.
  - Extends `BaseTextService<Seat>`.
  - Reads/writes `seats.txt`.
  - Converts between `Seat` objects and text lines.
  - Provides seat-specific operations such as:
    - `findByShowtimeId()`
    - `findAvailable()`
    - `updateStatus()`
    - `createSeat(type, showtimeId, seatNumber, status)`

- `src/service/BaseTextService.java`
  - Generic file-based CRUD service for text records.
  - Implements common operations:
    - `findAll()`
    - `findById()`
    - `add()`
    - `update()`
    - `delete()`
  - Generates unique IDs using `nextId()` and the subclass `getIdPrefix()`.
  - Resolves the file path to `WEB-INF/data/<filename>` at runtime.

- `src/util/FileHandler.java`
  - Low-level file utility.
  - Implements:
    - `readLines()`
    - `appendLine()`
    - `writeLines()`
    - `updateLine()`
    - `deleteLine()`
  - Ensures the file and parent directory exist.
  - Uses `|` as the record delimiter in the text file.

### Servlet and UI files

- `src/servlet/SeatServlet.java`
  - Controller for seat HTTP requests.
  - Handles both GET and POST requests.
  - Uses `SeatService` and `ShowtimeService`.
  - Supports actions:
    - `list` (default) → show seat list
    - `add` → show add-seat form
    - `edit` → show edit-seat form
    - `delete` → remove a seat
    - `save` / `update` → persist seat data

- JSP pages under `web/jsp/seat/`
  - `list-seat.jsp` → displays all seats and actions
  - `add-seat.jsp` → form to add a seat
  - `edit-seat.jsp` → form to update a seat

## 3. Data storage / database design

The module uses a text file as the database:

- `web/WEB-INF/data/seats.txt`
- `src/data/seats.txt` for development/source reference

Each seat line is stored as a pipe-delimited record:
`id|showtimeId|seatNumber|status|type|price`

Example:
`SE1|ST3|A1|Available|Regular|15.00`

This design is a simple flat-file database, not a relational database system. It meets the file handling requirement from the evaluation criteria.

## 4. CRUD flow for seat management

### Create

1. User clicks `Add Seat` in `list-seat.jsp`.
2. `SeatServlet.doGet()` receives `action=add`.
3. Servlet loads showtimes using `ShowtimeService.findAll()`.
4. The add form is shown in `add-seat.jsp`.
5. User submits the form to `/seats` with `action=save`.
6. `SeatServlet.doPost()` constructs a `Seat` using `seatService.createSeat(type, showtimeId, seatNumber, status)`.
7. The servlet calls `seatService.add()`.
8. `BaseTextService.add()` assigns a new ID (`SE...`) and calls `FileHandler.appendLine()`.

### Read

- Seat listing is handled by `SeatServlet.doGet()` with default `action=list`.
- It calls `seatService.findAll()`, which:
  - reads all lines from `seats.txt`
  - deserializes lines into `Seat`, `RegularSeat`, or `VIPSeat`
- The list is forwarded to `list-seat.jsp`.

### Update

1. User clicks `Edit` for a specific seat.
2. `SeatServlet.doGet()` receives `action=edit` and loads the current seat via `findById()`.
3. The edit form is shown in `edit-seat.jsp` with current values.
4. User submits the form with `action=update`.
5. `SeatServlet.doPost()` rebuilds the seat object and calls `seatService.update()`.
6. `BaseTextService.update()` writes the updated record by calling `FileHandler.updateLine()`.

### Delete

1. User clicks `Delete` in the seat list.
2. `SeatServlet.doGet()` receives `action=delete` and calls `seatService.delete()`.
3. `BaseTextService.delete()` removes the matching line using `FileHandler.deleteLine()`.
4. Page reloads with a message.

## 5. How seat management is linked to the rest of the application

### Showtime integration

- `SeatServlet` loads showtime options through `ShowtimeService` when adding or editing a seat.
- This links seat records to a showtime ID, ensuring every seat belongs to a particular movie session.

### Reservation integration

- `src/service/ReservationService.java` uses `SeatService`.
- When booking a reservation, it fetches the seat by ID and updates its status.
- When canceling a reservation, it sets the seat status back to `Available`.
- This means seat management is not isolated: it participates in the booking lifecycle.

## 6. Object-oriented design in seat management

### Encapsulation

- Seat data is wrapped inside `Seat`, `RegularSeat`, and `VIPSeat`.
- Fields are private and accessed through getters/setters.
- `BaseTextService` handles persistence logic, keeping file operations separate from the servlet.

### Inheritance

- `RegularSeat` and `VIPSeat` both extend `Seat`.
- They share common fields and behavior from `Seat`.
- Specialized behavior is implemented in subclasses (`getCategory()`, default type/price).

### Polymorphism

- `SeatService.deserialize()` returns either `VIPSeat` or `RegularSeat` depending on the type string.
- `createSeat()` returns the correct subclass based on the requested seat type.
- The rest of the code treats these objects as `Seat`, allowing one service to manage both seat categories.

### Abstraction

- `BaseTextService` abstracts generic CRUD operations for any text-backed entity.
- `SeatService` only implements seat-specific serialization, deserialization, ID logic, and filters.

## 7. File handling and persistence

Seat management uses file-based persistence, satisfying the file handling criteria:

- `FileHandler.readLines()` reads all records from `seats.txt`.
- `appendLine()` adds a new record.
- `updateLine()` rewrites the file when a seat changes.
- `deleteLine()` removes a seat line from the file.
- `ensureFileExists()` creates the file and directories automatically.

This means the module uses persistent storage without an RDBMS, and the data is stored in a consistent plain-text format.

## 8. User interface design for seat management

The seat UI provides:

- A table showing seats with `ID`, `Showtime`, `Seat No.`, `Status`, `Type`, and `Price`.
- Buttons for `Edit` and `Delete`.
- Form pages for `Add Seat` and `Edit Seat`.
- Status badges for `Available` and `Booked`.

This supports usability by making seat state and type visible, and by giving clear add/update/delete actions.

## 9. Marking criteria alignment

### Functionality of CRUD operations (30 marks)

- Full create/read/update/delete support exists in `SeatServlet` + `SeatService`.
- `list-seat.jsp` displays records.
- `add-seat.jsp` and `edit-seat.jsp` provide forms.
- `delete` is implemented safely through the servlet.

### Application of OOP concepts (20 marks)

- Encapsulation: model fields are private.
- Inheritance: `RegularSeat` and `VIPSeat` extend `Seat`.
- Polymorphism: service returns concrete subclass objects but operates on `Seat`.
- Abstraction: `BaseTextService` generalizes CRUD for text-based models.

### File handling implementation (10 marks)

- Seat records are stored in `WEB-INF/data/seats.txt`.
- `FileHandler` safely reads, writes, updates, and deletes records.
- The module uses plain text as persistent storage.

### Correct use of user interface design (10 marks)

- Seat management UI pages are separate and consistent.
- The UI follows the same pattern as other modules.
- Forms validate required inputs and use showtime dropdowns.

### Individual contribution & GitHub commit history (10 marks)

- Your module scope is seat management: `Seat.java`, `RegularSeat.java`, `VIPSeat.java`, `SeatService.java`, `SeatServlet.java`, seat JSPs, and the file storage logic.
- If asked, mention that this module is responsible for supporting seat creation, updates, deletion, and the connection to reservations and showtimes.

### Presentation & viva performance (10 marks)

- Be ready to explain the flow from browser → servlet → service → file storage.
- Explain why `SeatService` extends `BaseTextService` and how that reduces duplicated CRUD code.
- Describe the difference between seat type classes and why `VIPSeat` and `RegularSeat` exist.

## 10. Typical question answers for viva

### Q: How does a new seat get stored?

A: The servlet receives POST data, `SeatService.createSeat()` constructs a `RegularSeat` or `VIPSeat`, and `BaseTextService.add()` writes a pipe-delimited line to `WEB-INF/data/seats.txt`.

### Q: How is the seat type decided?

A: `SeatService.createSeat()` checks the `type` parameter. If it is `VIP`, it returns `new VIPSeat(...)`; otherwise it returns `new RegularSeat(...)`.

### Q: How is seat status updated when a reservation is made?

A: `ReservationService` calls `seatService.updateStatus(context, seatId, "Booked")`, which loads the seat, changes its status, and updates the record in `seats.txt`.

### Q: Why use `BaseTextService`?

A: It abstracts repeated text-based CRUD logic so `SeatService` only provides seat-specific serialization and filter methods.

## 11. Simplified data flow diagram

1. Browser request → `SeatServlet`
2. Servlet calls `SeatService`
3. `SeatService` calls `BaseTextService` methods
4. `BaseTextService` uses `FileHandler`
5. `FileHandler` reads/writes `WEB-INF/data/seats.txt`
6. Result returns to servlet
7. Servlet forwards to JSP

## 12. Summary

Your seat management module is a complete CRUD component built on:

- Plain text persistence (`seats.txt`)
- Java OOP models (`Seat`, `RegularSeat`, `VIPSeat`)
- A reusable service layer (`BaseTextService`, `SeatService`)
- A servlet controller (`SeatServlet`)
- Web UI pages for list/add/edit actions

This architecture is well-suited for the OOP project evaluation and directly addresses the marking criteria.
