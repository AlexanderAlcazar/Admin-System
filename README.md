# Student-Manager

This repository contains a comprehensive student management system project, implemented entirely from scratch by Alexander Alcazar with the help of a group of students.

## Project Overview

The project is a full-stack, Java-based student management platform built as an educational exercise. It features:

- **Custom TCP Server:** Handles multiple client connections and manages all student-related operations.
- **Persistent Database Layer:** Student information is stored and retrieved from files, with custom logic for serialization and deserialization.
- **JavaFX User Interface:** A graphical frontend for administration, data entry, and user operations.

## Main Features

- **User Authentication:** Supports login for both students and administrators, each with distinct verification logic.
- **Student Management:** 
  - Add new students with personal details.
  - Remove students by ID.
  - List all students in the database.
  - Retrieve detailed information for a specific student.
- **Admin Features:** Additional administrative operations accessible via admin login.
- **Data Persistence:** All data is loaded from and saved to files, ensuring information persists between server restarts.
- **Client-Server Communication:** Uses a custom protocol with line-based commands parsed and routed on the server.

## Technical Architecture

- **Server:**  
  - Listens for client connections and processes commands such as `login`, `add`, `remove`, `list`, and `info`.
  - Implements logic for verifying credentials, handling concurrent operations, and managing state changes.
  - Built to run on port 5000 by default.

- **Database:**  
  - Manages a collection of `Student` objects with CRUD operations.
  - Reads from and writes to a flat file format for persistence.
  - Provides methods for listing, retrieving, and verifying students.

- **JavaFX GUI:**  
  - Controls window transitions and user interactions.
  - Presents forms and lists for student data management.
  - Handles navigation between administrative and user views.

- **Model:**  
  - `Person` interface defines the contract for user information.
  - `Student` class implements `Person` and encapsulates all relevant fields (name, ID, contact info, etc.).

## Getting Started

1. **Clone the repository:**
   ```bash
   git clone https://github.com/AlexanderAlcazar/Student-Manager.git
   cd Student-Manager
   ```

2. **Build and Run:**
   - Ensure you have JDK 8+ and JavaFX libraries installed.
   - Compile the project using your preferred IDE or CLI tools.
   - Start the server (`Server.java`), then launch the JavaFX client.

3. **Usage:**
   - Connect to the server as a student or admin.
   - Use the GUI to add, remove, or view students.

## Educational Value

This project demonstrates core concepts in:

- Network programming (socket servers)
- File I/O and persistence
- JavaFX GUI design
- Object-oriented principles (interfaces, encapsulation, etc.)
- Group collaboration and code from scratch

## Acknowledgments

- Special thanks to the group of students who contributed their time and expertise.
- Project was completed as part of the CS56 course curriculum.

## License



---
*Built from scratch for educational purposes—no external frameworks or codebases used.*
