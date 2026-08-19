# Warsaw Salon Explorer

Full-stack application for browsing and managing beauty and hair salon data in Warsaw.

Built with **Java 21, Spring Boot, PostgreSQL, Spring Security, React and Vite**.

## Preview

### Listing
<img width="1914" height="987" alt="Salon listing" src="https://github.com/user-attachments/assets/01745ca1-3106-4e9d-8f24-43e1ddf89933" />

### Salon Details
<img width="1908" height="946" alt="Salon details" src="https://github.com/user-attachments/assets/e277d654-c147-4496-85dd-26cc6edf53a8" />

### Admin Edit Mode
<img width="1130" height="644" alt="Admin edit mode" src="https://github.com/user-attachments/assets/e856ac86-cafd-49b1-ae14-1109304aeb07" />

## Features

- Browse 50+ Warsaw salon records
- Filter salons by district
- View address, contact details, services, price range, rating and review count
- React detail views backed by REST endpoints
- Protected create/update operations with Spring Security
- PostgreSQL persistence with Spring Data JPA
- JSON-based initial data loading
- Frontend loading and error states
- Service-layer unit tests
- Environment-based database and admin configuration

## Tech Stack

**Backend:** Java 21, Spring Boot, Spring Web, Spring Data JPA, Spring Security  
**Database:** PostgreSQL  
**Frontend:** React, Vite, JavaScript, CSS  
**Testing:** JUnit, Mockito  
**Build:** Maven

## Architecture

```text
React / Vite
     ↓ REST
Controller
     ↓
Service
     ↓
Repository
     ↓
PostgreSQL
```

Backend source:

```text
src/main/java/com/mirocoder/salonexplorer/
├── config/
├── controller/
├── model/
├── repository/
└── service/
```

## Run Locally

### Requirements

- Java 21
- Maven
- Node.js + npm
- PostgreSQL

Clone the repository:

```bash
git clone https://github.com/MiroCoder/warsaw-salon-explorer.git
cd warsaw-salon-explorer
```

Create the database:

```sql
CREATE DATABASE beauty_of_warsaw;
```

Optional environment variables:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
ADMIN_USERNAME
ADMIN_PASSWORD
JPA_SHOW_SQL
```

Development defaults are defined in `application.properties`; credentials can be overridden without changing source code.

Run the backend:

```bash
mvn spring-boot:run
```

Backend:

```text
http://localhost:8080
```

Run the frontend in a second terminal:

```bash
cd frontend
npm install
npm run dev
```

Vite will print the local frontend URL.

## API

| Method | Endpoint | Access | Description |
|---|---|---|---|
| `GET` | `/api/salons` | Public | List salons; supports district filtering |
| `GET` | `/api/salons/{id}` | Public | Get one salon |
| `POST` | `/api/salons` | Admin | Create a salon |
| `PUT` | `/api/salons/{id}` | Admin | Update a salon |

Example filter:

```http
GET /api/salons?district=Mokotów
```

## Security

Read operations are public. Create and update operations are protected with Spring Security Basic Auth.

For local development, default demo credentials are available through configuration and can be replaced with `ADMIN_USERNAME` and `ADMIN_PASSWORD` environment variables.

## Data

Initial salon data is loaded from:

```text
src/main/resources/data/salons.json
```

The dataset was assembled from publicly visible salon information. Missing fields are kept explicitly missing rather than fabricated.

## Tests

Run backend tests with:

```bash
mvn test
```

The test suite includes service-layer behavior such as retrieval, filtering, updates and persistence interactions.

## Author

[Miroslav Nekhaev / MiroCoder](https://github.com/MiroCoder)
