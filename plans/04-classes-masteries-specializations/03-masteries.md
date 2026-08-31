# Mastery Plan — Practice-Based Progression

**Goal:** medir domínio por ações reais, não por posse de item ou eventos auxiliares.

- [ ] Manter catálogo canônico de mastery IDs.
- [ ] Definir evento semântico confirmado para cada fonte.
- [ ] Ignorar ações canceladas/falhas.
- [ ] Impedir dupla concessão por eventos que representam a mesma ação.
- [ ] Definir curvas, caps e thresholds.
- [ ] Persistir e sincronizar progresso necessário à UI.

## Progresso runtime confirmado — BOW

- [x] `epicfight:bow` é o ledger canônico do gate BOW; `combat:bow` não permanece como ledger paralelo na arquitetura.
- [x] Tiro físico exige `ArrowLooseEvent` real, não cancelado, seguido de projétil `BowItem` correlacionado e `LivingDamageEvent.Post` com dano positivo contra alvo hostil.
- [x] Cada tipo hostil inédito concede o milestone uma única vez por `DiscoveryProgress`: `mastery:epicfight:weapon/bow/hostile_type/<entity_type>`.
- [x] O milestone BOW usa a política canônica de arma (`+10 epicfight:bow`, além do ledger geral de arma já definido pela política existente).
- [x] Creative, spectator e `FakePlayer` são inelegíveis; projéteis sintéticos/spell/derived sem release física correlacionada falham fechado.
- [x] A identidade de descoberta coincide com a usada pelo adapter Epic Fight, impedindo dupla concessão quando os dois providers observam o mesmo resultado semântico.
- [x] Contrato coberto por teste core; build NeoForge, GameTests e dedicated-server smoke permanecem gates de CI.

## Progresso runtime confirmado — CROSSBOW

- [x] `epicfight:crossbow` é o ledger canônico do gate CROSSBOW; `combat:crossbow` não permanece como ledger paralelo na arquitetura.
- [x] Disparo físico exige `ArrowLooseEvent` real, não cancelado, seguido de projétil associado a `CrossbowItem` da mesma categoria e `LivingDamageEvent.Post` com dano positivo contra alvo hostil.
- [x] BOW e CROSSBOW compartilham um único produtor físico server-authoritative, evitando correlatores de Mastery concorrentes.
- [x] Cada tipo hostil inédito concede o milestone uma única vez por `DiscoveryProgress`: `mastery:epicfight:weapon/crossbow/hostile_type/<entity_type>`.
- [x] O milestone CROSSBOW usa a política canônica de arma (`+10 epicfight:crossbow`, além de `+5 epicfight:weapon`).
- [x] Multishot pode correlacionar vários projéteis à mesma release física, mas o mesmo tipo hostil continua limitado a uma descoberta persistente.
- [x] Creative, spectator e `FakePlayer` são inelegíveis; projéteis sintéticos, spell-derived ou owner-only sem release física correlacionada falham fechado.
- [x] A identidade de descoberta coincide com a usada pelo adapter Epic Fight, impedindo dupla concessão quando os dois providers observam o mesmo resultado semântico.
- [x] Contrato coberto por teste core; build NeoForge, GameTests e dedicated-server smoke permanecem gates de CI.

As caixas gerais acima permanecem abertas porque o fechamento é por catálogo/fonte completa de Mastery, não por uma única categoria de arma.

**Acceptance:** repetir uma ação válida aumenta mastery exatamente uma vez e tentativas inválidas não aumentam.
