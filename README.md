# Power BI API (Spring Boot)

Projeto de exemplo com autenticação JWT, menu dinâmico RBAC e políticas ABAC para geração de embed token do Power BI.

## Como rodar

```bash
./mvnw spring-boot:run
```

Banco padrão (PostgreSQL):

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/powerbi
    username: powerbi
    password: powerbi
```

## Endpoints principais

- `POST /api/auth/login`
- `GET /api/me/menu`
- `GET /api/reports/{id}`
- `POST /api/reports/{id}/embed`
- `POST /api/admin/reports`
- `POST /api/admin/reports/{id}/dimensions`
- `POST /api/admin/reports/{id}/policies`
- `POST /api/admin/policies/{policyId}/rules`
- `POST /api/admin/rules/{ruleId}/values`

## Usuários seed

- `admin` / `password`
- `userA` / `password`
- `userB` / `password`

Os dados iniciais são carregados via Flyway (`V2__seed.sql`).
