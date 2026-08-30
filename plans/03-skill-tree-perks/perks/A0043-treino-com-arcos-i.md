# A0043 — Treino com Arcos I

## Estado

- **Design:** APROVADO após correção provider-native/Mastery.
- **Implementação:** PARCIAL; dano físico BOW existe, mas `P-A0043-01` mantém o gate de Mastery sem producer projetil comprovado para a rota vanilla/NeoForge.
- **Notion:** `3c569db9-f0db-81ba-86de-cc43d7d3dae3`.

## Contrato canônico

- Fundamento exterior de Arcos; nível 8 + `epicfight:bow` ≥60 + gateway `epic_bow`.
- +3% de dano com arcos por rank, até +9%.
- Classificador BOW único para A0043–A0048:
  - vanilla `BowItem`/subclasse + owner real do projétil = classificação segura;
  - item externo somente por capability/categoria provider-native ou mapping versionado explícito;
  - unknown = fail-closed.
- Mastery `epicfight:bow`: +10 uma única vez por tipo hostil inédito atingido por projétil BOW elegível; 6 tipos = 60, 8 tipos = 80.

## Evidência runtime

- `A0041A0060ProjectileEvents.family(...)` classifica `BowItem` como BOW e exige `AbstractArrow` com owner `ServerPlayer` elegível.
- `ProjectileMeta` preserva root action/origem e `onIncomingDamage` aplica `baseDamageMultiplier(BOW, ranks)` no impacto hostil.
- O bridge de projéteis não chama `awardMasteryAndDiscoveries`/`MasteryPolicies.forEpicFight`.
- `EpicFightProgressionHooks` gera milestones de arma apenas a partir de `DealDamageEvent.Post`; isso não comprova uma rota de Mastery para a classificação vanilla/NeoForge usada pelo projectile bridge.

## Provider→árvore

- **RPG Skill Tree:** authority de rank, Mastery, dedup e root action.
- **Black Arcana:** `ARCANE_BACKLASH`/spell projectile não é projétil BOW físico.
- **Mobstein 5.4.4:** projéteis/ataques de allies/bodyguards permanecem Mobstein-owned e não creditam dano/Mastery do dono.
- **Volcanoes / Enshrouded:** não classificam arco nem projétil.
- **Stage 11.01 itemização:** `SEM HOOK SEGURO`; rolls não projetam dano de A0043.

## Pendência Chat 2

### P-A0043-01 — producer de Mastery BOW para projétil físico

Adicionar um producer persistente e deduplicado no receipt pós-hit real da mesma classificação BOW: `+10` uma vez por tipo hostil inédito em `DiscoveryProgress`, sem spam por dano, sem summon/fake/spell/derived e sem dupla premiação quando Epic Fight já reivindicar o mesmo outcome. Provar gate 59/60 e terminal 79/80.

## Testes exigidos

- vanilla BowItem/subclasse;
- item externo provider-native vs unknown;
- owner real vs fake/summon/spell/derived;
- uma aplicação de dano por projétil/root;
- 6/8 tipos hostis distintos para Mastery;
- coexistência Epic Fight sem double-award;
- dedicated server/multiplayer.
