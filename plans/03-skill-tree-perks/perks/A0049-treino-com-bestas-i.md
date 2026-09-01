# A0049 — Treino com Bestas I

## Estado

- **Design:** APROVADO após correção provider-native/Mastery.
- **Implementação:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- **Notion:** `3c569db9-f0db-8193-bbba-d2e7a8837c58`; fonte canônica mantém `epicfight:crossbow`.

## Contrato canônico

- Fundamento exterior de Bestas; nível 8 + `epicfight:crossbow` ≥60 + gateway `epic_crossbow`.
- +3% de dano com bestas por rank, até +9%.
- Classificador CROSSBOW único para A0049–A0054:
  - vanilla `CrossbowItem`/subclasse + owner real do projétil = classificação segura;
  - item externo somente por capability/categoria provider-native ou mapping versionado explícito;
  - unknown = fail-closed.
- Mastery **canônica** `epicfight:crossbow`: +10 uma única vez por tipo hostil inédito atingido por projétil CROSSBOW elegível; 6 tipos = 60, 8 tipos = 80.

## Evidência runtime

- `A0041A0060ProjectileEvents.family(...)` classifica `CrossbowItem` como CROSSBOW e exige `AbstractArrow` com owner `ServerPlayer` elegível.
- `ProjectileMeta` preserva root action/origem; `onIncomingDamage` aplica `baseDamageMultiplier(CROSSBOW, ranks)` no impacto hostil.
- `PhysicalProjectileMasteryEvents` correlaciona release física, projétil e `LivingDamageEvent.Post`, usando `WeaponMasteryMilestonePolicy.confirmedPhysicalProjectileHit(...)` também para CROSSBOW.
- `WeaponMasteryMilestoneRuntime.awardIfNew(...)` persiste discovery por tipo hostil e impede spam/double-award pela identidade compartilhada.
- `tree_architecture/combat.json` atual já usa `requiredMastery: {"epicfight:crossbow":60}`; a divergência histórica `combat:crossbow` foi resolvida por evolução posterior.

## Provider→árvore

- **RPG Skill Tree:** authority de rank, Mastery, discovery/dedup e root action; namespace canônico `epicfight:crossbow`.
- **Minecraft/NeoForge:** `CrossbowItem`, owner real do projétil e POST de dano.
- **Epic Fight:** pode compartilhar a mesma discovery sem ledger paralela.
- **Black Arcana:** spell/Backlash não é projétil CROSSBOW físico.
- **Mobstein 5.4.4:** projectile/attack de allies/bodyguards não herda autoria ou Mastery do dono.
- **Volcanoes / Enshrouded:** não classificam besta.
- **Stage 11.01 itemização:** `SEM HOOK SEGURO`; rolls não projetam A0049.

## Pendências históricas resolvidas

- `P-A0049-01`: resolvida por `PhysicalProjectileMasteryEvents` + discovery persistente.
- `P-A0049-02`: resolvida; architecture/model/runtime usam `epicfight:crossbow`.

## Pendência Chat 3

- validar 6/8 hostile types, gate 59/60 e dedup por tipo;
- validar CrossbowItem/subclasse, owner real vs fake/summon/spell/derived;
- validar coexistência com Epic Fight sem double-award e dedicated server.

## Testes exigidos

- vanilla CrossbowItem/subclasse;
- external provider-native vs unknown;
- owner real vs fake/summon/spell/derived;
- uma aplicação por projétil/root;
- 6/8 hostile types para Mastery;
- gate architecture/model usando exatamente a mesma chave de Mastery;
- coexistência sem double-award;
- dedicated server/multiplayer.

## Fechamento Chat 2 — 2026-09-01

O Chat 2 reutilizou o producer persistente já existente e não criou uma segunda ledger. A validação final permanece do Chat 3.
