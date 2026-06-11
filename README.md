# hire-radar

금융권/보험권 채용 공고를 주기적으로 수집하고, 개인 프로필 기준 적합도를 계산하며, 신규 공고를 Discord로 알리는 개인용 채용 모니터링 시스템입니다.

## Stack

- Backend: Spring Boot 3.2, Java 17, Spring Data JPA
- Frontend: React, TypeScript, Vite
- Database: SQLite
- Parsing: Jsoup
- Notification: Discord Webhook
- Runtime: Docker Compose

## Structure

```text
hire-radar-backend/
  src/main/java/com/hyome/hire_radar_backend/
    domain/
    application/
    infrastructure/
    webapi/
hire-radar-frontend/
  src/
    components/
    hooks/
    pages/
    services/
```

## Run With Docker

```bash
docker compose up --build
```

- Frontend: http://localhost:5173
- Backend API: http://localhost:8080/api
- Swagger UI: http://localhost:8080/swagger-ui.html

## Environment

```bash
DISCORD_WEBHOOK_URL=https://discord.com/api/webhooks/...
SCRAPING_FIXED_DELAY_MS=3600000
```

`DISCORD_WEBHOOK_URL` is optional. If it is empty, postings are still saved but notifications are skipped.

## Local Development

```bash
cd hire-radar-backend
mvn spring-boot:run
```

```bash
cd hire-radar-frontend
npm install
npm run dev
```

For local Vite development, create `hire-radar-frontend/.env` if needed:

```bash
VITE_API_BASE_URL=http://localhost:8080/api
```
