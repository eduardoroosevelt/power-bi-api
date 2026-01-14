# Front-end log de acesso (reports)

## Objetivo
Registrar o acesso ao painel/report quando o usuário abrir qualquer rota `/reports/*`, informando:
- usuário autenticado (via JWT no backend)
- IP do usuário (resolvido no backend)
- duração da sessão (em segundos)
- data/hora do acesso (gerada no backend)

O backend já expõe o endpoint:
- `POST /api/reports/{reportId}/access-logs`

## Quando disparar
1. **Ao entrar** na página `/reports/:reportId` (ou rota equivalente), iniciar um timer.
2. **Ao sair** da página, enviar o log com `durationSeconds`.

## Como calcular a duração
- Armazenar `startedAt = Date.now()` ao carregar a página.
- Ao sair, calcular `durationSeconds = Math.max(0, Math.floor((Date.now() - startedAt) / 1000))`.

## Eventos para envio do log
Usar pelo menos um dos gatilhos abaixo:
- `beforeunload` ou `pagehide` para navegar para outra página/fechar aba.
- `visibilitychange` quando a aba fica oculta (opcional, para sessões longas).

> Recomendação: disparar no `pagehide` e, se possível, usar `navigator.sendBeacon`.

## Payload
```json
{
  "durationSeconds": 123
}
```

## Exemplo (JavaScript)
```js
let startedAt = Date.now();

function sendAccessLog(reportId) {
  const durationSeconds = Math.max(0, Math.floor((Date.now() - startedAt) / 1000));
  const url = `/api/reports/${reportId}/access-logs`;
  const payload = JSON.stringify({ durationSeconds });

  if (navigator.sendBeacon) {
    const blob = new Blob([payload], { type: 'application/json' });
    navigator.sendBeacon(url, blob);
  } else {
    fetch(url, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: payload,
      keepalive: true
    });
  }
}

window.addEventListener('pagehide', () => sendAccessLog(reportId));
```

## Autenticação
- O front-end deve enviar o JWT no header `Authorization: Bearer <token>`.
- O backend resolve usuário e IP automaticamente.

## Observações
- Evitar múltiplos envios: garantir que o log seja enviado **uma única vez** por sessão/página.
- Em SPAs, reiniciar `startedAt` quando o usuário muda de report.
