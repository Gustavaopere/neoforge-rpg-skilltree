# PLANO — 01 RPG Core

Estado: **EM ANDAMENTO / fundação implementada**.

## Por que este estágio existe

O RPG Core é a fonte de verdade compartilhada por perks, classes, scaling, UI e integrações. Sem ele, cada sistema tende a guardar XP, atributos ou identidade por caminhos paralelos.

## Resultado esperado

Um estado canônico de jogador com APIs determinísticas para leitura/mutação, persistência versionável e sincronização mínima para cliente.

## Dependências

00 Foundation.

## Etapas de implementação

### 1 — Modelo canônico do jogador
- [ ] consolidar level, XP, pontos passivos, mastery, unlocks e identidades;
- [ ] definir invariantes e valores default;
- [ ] impedir estado impossível/negativo fora das regras.

### 2 — Serviços de progressão
- [ ] uma rota única para conceder/remover XP;
- [ ] uma rota única para level-up e recompensas associadas;
- [ ] eventos externos chamam serviços, não escrevem storage diretamente.

### 3 — Atributos e modificadores
- [ ] IDs estáveis de modifiers;
- [ ] aplicação determinística e removível;
- [ ] recomputação segura após respec/reload/login.

### 4 — Persistência
- [ ] definir versão de schema;
- [ ] salvar/carregar sem perda após restart;
- [ ] preparar migradores para versões futuras;
- [ ] definir comportamento para dados desconhecidos/corrompidos.

### 5 — API interna
- [ ] separar queries de commands/mutations;
- [ ] expor snapshots imutáveis para consumidores;
- [ ] impedir adapters de dependerem de classes de UI.

### 6 — Sync
- [ ] servidor envia somente estado necessário;
- [ ] cliente não autoriza compra/unlock;
- [ ] login, respawn, dimension change e reload mantêm coerência.

## Testes

- [ ] round-trip de save/load;
- [ ] level/XP em limites;
- [ ] modifiers não duplicam;
- [ ] respec/remove restaura atributos;
- [ ] cliente adulterado não consegue conceder progressão.

## Critérios de aceite

Uma única fonte de verdade, persistence segura, API consumível pelos demais estágios e sync server-authoritative.

## Definição de concluído

Após critérios e CI canônico verdes, renomear para `PLANO-✅.md`.