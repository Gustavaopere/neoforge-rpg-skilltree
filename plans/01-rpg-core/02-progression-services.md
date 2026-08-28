# RPG Core Plan — Progression Services

**Goal:** fazer toda mutação de progressão passar por serviços canônicos.

- [ ] Uma rota para conceder/remover XP.
- [ ] Uma rota para level-up e recompensas associadas.
- [ ] Uma rota para mastery/progresso especializado.
- [ ] Impedir adapters/quests de escrever storage diretamente.
- [ ] Definir idempotência para ações que podem emitir múltiplos eventos auxiliares.
- [ ] Emitir eventos internos somente após mutação confirmada.

**Acceptance:** o mesmo evento semântico não duplica XP/mastery e nenhum sistema paralelo contorna o core.