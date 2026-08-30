# A0049 — Treino com Bestas I

## Estado

- **Design:** APROVADO após correção provider-native/Mastery.
- **Implementação:** PARCIAL; dano físico CROSSBOW existe, mas `P-A0049-01` mantém o gate de Mastery sem producer projetil comprovado para a rota vanilla/NeoForge.
- **Notion:** `3c569db9-f0db-8193-bbba-d2e7a8837c58`.

## Contrato canônico

- Fundamento exterior de Bestas; nível 8 + `epicfight:crossbow` ≥60 + gateway `epic_crossbow`.
- +3% de dano com bestas por rank, até +9%.
- Classificador CROSSBOW único para A0049–A0054:
  - vanilla `CrossbowItem`/subclasse + owner real do projétil = classificação segura;
  - item externo somente por capability/categoria provider-native ou mapping versionado explícito;
  - unknown = fail-closed.
- Mastery `epicfight:crossbow`: +10 uma única vez por tipo hostil inédito atingido por projétil CROSSBOW elegível; 6 tipos = 60, 8 tipos = 80.

## Evidência runtime

- `A0041A0060ProjectileEvents.family(...)` classifica `CrossbowItem` como CROSSBOW e exige `AbstractArrow` com owner `ServerPlayer` elegível.
- `ProjectileMeta` preserva root action/origem; `onIncomingDamage` aplica `baseDamageMultiplier(CROSSBOW, ranks)` no impacto hostil.
- O bridge de projéteis não possui producer de Mastery/discovery para `epicfight:crossbow`.
- O producer Epic Fight geral é baseado em `DealDamageEvent.Post`, não prova cobertura da rota vanilla/NeoForge do projectile bridge.

## Provider→árvore

- **RPG Skill Tree:** authority de rank, Mastery, dedup e root action.
- **Black Arcana:** spell/Backlash não é projétil CROSSBOW físico.
- **Mobstein 5.4.4:** projectile/attack de allies/bodyguards não herda autoria ou Mastery do dono.
- **Volcanoes / Enshrouded:** não classificam besta.
- **Stage 11.01 itemização:** `SEM HOOK SEGURO`; rolls não projetam A0049.

## Pendência Chat 2

### P-A0049-01 — producer de Mastery CROSSBOW

No receipt pós-hit real do mesmo classificador CROSSBOW, conceder `+10` uma vez por tipo hostil inédito em `DiscoveryProgress`, deduplicado contra eventual producer Epic Fight do mesmo outcome. Excluir summon/fake/spell/derived/companion. Provar gate 59/60 e terminal 79/80.

## Testes exigidos

- vanilla CrossbowItem/subclasse;
- external provider-native vs unknown;
- owner real vs fake/summon/spell/derived;
- uma aplicação por projétil/root;
- 6/8 hostile types para Mastery;
- coexistência sem double-award;
- dedicated server/multiplayer.
