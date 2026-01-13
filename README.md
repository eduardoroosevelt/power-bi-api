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
- `GET /api/admin/orgaos`
- `POST /api/admin/orgaos`
- `PUT /api/admin/orgaos/{id}`
- `DELETE /api/admin/orgaos/{id}`
- `GET /api/admin/unidades`
- `POST /api/admin/unidades`
- `PUT /api/admin/unidades/{id}`
- `DELETE /api/admin/unidades/{id}`
- `GET /api/admin/permissoes`
- `POST /api/admin/permissoes`
- `PUT /api/admin/permissoes/{id}`
- `DELETE /api/admin/permissoes/{id}`
- `GET /api/admin/grupos`
- `POST /api/admin/grupos`
- `PUT /api/admin/grupos/{id}`
- `DELETE /api/admin/grupos/{id}`
- `GET /api/admin/grupos/{grupoId}/permissoes`
- `POST /api/admin/grupos/{grupoId}/permissoes`
- `DELETE /api/admin/grupos/{grupoId}/permissoes/{permissaoId}`

## Usuários seed

- `admin` / `password`
- `userA` / `password`
- `userB` / `password`

Os dados iniciais são carregados via Flyway (`V2__seed.sql`).
