A small full-stack web application for browsing beauty and hair salons in Warsaw.

The project was built as a home task for a Software Engineer Intern position.
It includes data collection, a Spring Boot REST API, and a React frontend.

Features
Browse Warsaw beauty / hair salons
See key salon information:
name
district
rating
price range
Filter salons by district
Click a salon to see full details:
address
phone
website
services
rating and review count

Admin edit mode for correcting salon details
Save edited salon data through the backend API

Tech Stack:
Backend
Java
Spring Boot
Spring Web
Spring Data JPA
H2 in-memory database
JSON seed data loader
Frontend
React
Vite
JavaScript
CSS
Data Storage

Salon data is stored in a JSON file:

src/main/resources/data/salons.json

When the backend starts, the JSON file is loaded into the H2 database.

Data Source

The initial dataset was collected from publicly available salon pages.

Main source:

Booksy salon pages

How to Run
Requirements:
Java 21
Maven
Node.js
npm
Backend

From the project root:

mvn spring-boot:run

Backend runs on:

http://localhost:8080

API example:

http://localhost:8080/api/salons
Frontend

Open a second terminal:

cd frontend
npm install
npm run dev

Frontend runs on Vite, usually:

http://localhost:5173

or:

http://localhost:5174
API Endpoints
Get all salons
GET /api/salons

Returns a list of salons.

Filter by district
GET /api/salons?district=Mokotów

Returns salons from the selected district.

Get one salon
GET /api/salons/{id}

Returns full salon details.

Create salon
POST /api/salons

Creates a new salon.

Update salon
PUT /api/salons/{id}

Updates one salon record.

Project Structure
src/main/java/com/mirocoder/salonexplorer
├── controller
│   └── SalonController.java
├── service
│   └── SalonService.java
├── repository
│   └── SalonRepository.java
├── model
│   └── Salon.java
└── config
    └── DataLoader.java

src/main/resources/data
└── salons.json

frontend/src
├── App.jsx
├── App.css
└── index.css

The backend structure:

Controller handles HTTP requests
Service contains application logic
Repository communicates with the database
Model represents salon data

The frontend uses React state and fetch() to communicate with the backend API.

The edit form is shown as an admin-edit mode.

H2 is in-memory, so manual changes are lost after backend restart
Edit mode is not protected by login

My next moves:
Use PostgreSQL instead of H2
Add authentication and admin-only editing
Add pagination for larger datasets
Add filtering by service type
Add better validation for salon fields
Add error handling messages in the frontend
Add integration tests for the REST API
