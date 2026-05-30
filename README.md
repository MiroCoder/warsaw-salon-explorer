# Beauty of Warsaw — Salon Explorer
A small full-stack web application for browsing beauty and hair salons in Warsaw.

## Screenshots
### Listing page

<img width="1914" height="987" alt="{8750F3AC-31D3-4910-B841-30E3F8AE80A4}" src="https://github.com/user-attachments/assets/01745ca1-3106-4e9d-8f24-43e1ddf89933" />

### Salon details
<img width="1908" height="946" alt="{327B958A-390F-491A-BF07-1D5A8C1E455F}" src="https://github.com/user-attachments/assets/e277d654-c147-4496-85dd-26cc6edf53a8" />

### Admin edit mode
<img width="1130" height="644" alt="{B2E0B0B2-BAEA-4BD9-9780-7CA30F9574ED}" src="https://github.com/user-attachments/assets/e856ac86-cafd-49b1-ae14-1109304aeb07" />

## The application allows a user to:

browse Warsaw beauty / hair salons

see key information:
name
district
rating
price range
filter salons by district
click a salon and see full details:
address
phone
website
services
rating
review count

There is also an admin edit mode for correcting salon data.

Editing is protected with Basic Auth on the backend.
For this test project the admin user is stored in memory.

## Tech stack
### Backend
-Java
-Spring Boot
-Spring Web
-Spring Data JPA
-Spring Security
-PostgreSQL
-Maven

### Frontend
-React
-Vite
-JavaScript
-CSS

### Data
JSON seed file
PostgreSQL database

The JSON file is used to seed the database when the application starts.

src/main/resources/data/salons.json

## Data source
The salon dataset was collected from Booksy.

Some fields can be missing or inconsistent between sources, especially phone numbers and websites.
When a value was not visible in the source, I kept it as "not listed on source" instead of inventing data.

The current dataset contains 50+ Warsaw salons.

## Main features
### Public user features
list all salons
filter salons by district
view salon details
see loading / error messages when the backend is unavailable

### Admin features
open admin edit mode
edit salon details
save changes through the backend API
protected POST and PUT endpoints with Basic Auth

Demo admin credentials:

username: admin
password: admin123

This is only for the home task demo.
In a real production app I would use proper user accounts, sessions/JWT, roles, and environment variables for secrets.

## How to run the project

### Requirements

Before running the application, make sure you have installed:

- Java 21
- Maven
- Node.js
- npm
- PostgreSQL

---

## 1. PostgreSQL setup

Create a local PostgreSQL database.

Open PostgreSQL console and run:

```sql
CREATE DATABASE beauty_of_warsaw;
```

The application uses this local database config:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/beauty_of_warsaw
spring.datasource.username=postgres
spring.datasource.password=postgres
```

If your PostgreSQL username or password is different, update this file:

```text
src/main/resources/application.properties
```

Example for Windows PowerShell, if `psql` is not added to PATH:

```powershell
& "C:\Program Files\PostgreSQL\18\bin\psql.exe" -U postgres -h localhost
```

Then inside PostgreSQL console:

```sql
CREATE DATABASE beauty_of_warsaw;
\q
```

---

## 2. Run backend

From the project root:

```powershell
cd C:\path\to\Beauty-of-Warsaw
mvn spring-boot:run
```

Backend should start on:

```text
http://localhost:8080
```

You can test if backend works in browser:

```text
http://localhost:8080/api/salons
```

Or in PowerShell:

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/salons" -Method Get
```

Expected result: list of salons in JSON format.

---

## 3. Run frontend

Open a second terminal.

Go to the frontend folder:

```powershell
cd frontend
```

Install frontend dependencies:

```powershell
npm install
```

Run frontend:

```powershell
npm run dev
```

Vite will show a local URL, usually:

```text
http://localhost:5173
```

or:

```text
http://localhost:5174
```

Open this URL in the browser.

---

## API endpoints

Base backend URL:

```text
http://localhost:8080
```

---

### Get all salons

```http
GET /api/salons
```

PowerShell example:

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/salons" -Method Get
```

Returns all salons.

---

### Filter salons by district

```http
GET /api/salons?district=Mokotów
```

PowerShell example:

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/salons?district=Mokotów" -Method Get
```

Returns salons from selected district.

---

### Get one salon by id

```http
GET /api/salons/{id}
```

Example:

```http
GET /api/salons/1
```

PowerShell example:

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/salons/1" -Method Get
```

Returns full details for one salon.

---

### Create salon

```http
POST /api/salons
```

This endpoint is protected by Basic Auth.

Demo admin credentials:

```text
username: admin
password: admin123
```

PowerShell example:

```powershell
$pair = "admin:admin123"
$bytes = [System.Text.Encoding]::ASCII.GetBytes($pair)
$base64 = [Convert]::ToBase64String($bytes)

$headers = @{
  Authorization = "Basic $base64"
}

$body = @{
  name = "Example Salon"
  address = "Example Street 1, Warszawa"
  district = "Śródmieście"
  phone = "+48 000 000 000"
  website = "https://example.com"
  services = "haircut, coloring"
  priceRange = "100-300 PLN"
  rating = 4.8
  reviewCount = 120
} | ConvertTo-Json

Invoke-RestMethod `
  -Uri "http://localhost:8080/api/salons" `
  -Method Post `
  -ContentType "application/json" `
  -Headers $headers `
  -Body $body
```

---

### Update salon

```http
PUT /api/salons/{id}
```

This endpoint is protected by Basic Auth.

Example:

```http
PUT /api/salons/1
```

PowerShell example:

```powershell
$pair = "admin:admin123"
$bytes = [System.Text.Encoding]::ASCII.GetBytes($pair)
$base64 = [Convert]::ToBase64String($bytes)

$headers = @{
  Authorization = "Basic $base64"
}

$body = @{
  name = "Updated Salon Name"
  address = "Updated Address, Warszawa"
  district = "Mokotów"
  phone = "+48 111 222 333"
  website = "https://example.com"
  services = "haircut, manicure, coloring"
  priceRange = "120-350 PLN"
  rating = 4.9
  reviewCount = 150
} | ConvertTo-Json

Invoke-RestMethod `
  -Uri "http://localhost:8080/api/salons/1" `
  -Method Put `
  -ContentType "application/json" `
  -Headers $headers `
  -Body $body
```

---

### Check that update is protected

This request should fail with `401 Unauthorized`:

```powershell
Invoke-WebRequest `
  -UseBasicParsing `
  -Uri "http://localhost:8080/api/salons/1" `
  -Method Put `
  -ContentType "application/json" `
  -Body "{}"
```

If it returns `401 Unauthorized`, backend protection works correctly.
Protected by Basic Auth.

## Project structure

```text
src/main/java/com/mirocoder/salonexplorer
├── config
│   ├── DataLoader.java
│   └── SecurityConfig.java
├── controller
│   └── SalonController.java
├── model
│   └── Salon.java
├── repository
│   └── SalonRepository.java
└── service
    └── SalonService.java

src/main/resources
├── application.properties
└── data
    └── salons.json

frontend/src
├── App.jsx
├── App.css
└── index.css
```

## Backend architecture

### Backend layered structure

```text
Controller → Service → Repository → Database
```

### Controller

Handles HTTP requests and responses.

Example:

```http
GET /api/salons
PUT /api/salons/{id}
```

### Service

Contains application logic.

### Repository

Communicates with the database through Spring Data JPA.

### Model

Represents salon data as a JPA entity.

## Frontend architecture

The frontend is intentionally simple.

It uses:

* React state for salons, selected salon, filter text, edit mode, and errors
* fetch() to call the backend API
* conditional rendering for detail view and admin edit form

### Main frontend flow

```text
React page opens
→ fetch salons from backend
→ render salon cards
→ user clicks salon
→ details are shown
→ admin can edit and save through PUT request
```

## Error handling

The frontend shows an error message when:

* salons cannot be loaded
* backend is not running
* saving changes fails
* wrong admin password is used

## Security note

For the task, I used Spring Security with Basic Auth.

Current behavior:

* GET /api/salons is public
* GET /api/salons/{id} is public
* POST /api/salons requires admin credentials
* PUT /api/salons/{id} requires admin credentials

## Implemented

* backend REST API
* PostgreSQL database
* JSON data loader
* 50+ real salon records
* React frontend
* district filter
* salon detail view
* admin edit mode
* protected edit API
* frontend error handling
