# Quest Hooks Plan — Reward Idempotency

**Goal:** impedir duplicação de rewards por diálogo repetido, reload ou replay de evento.

- [ ] Definir `reward/event id` persistível.
- [ ] Registrar conclusão antes/de forma atômica com a concessão conforme arquitetura escolhida.
- [ ] Diferenciar rewards one-shot e repetíveis.
- [ ] Persistir ledger através de restart.
- [ ] Definir política de limpeza/versionamento do ledger.

**Acceptance:** a mesma quest one-shot nunca paga duas vezes e quests repetíveis obedecem sua regra explícita.