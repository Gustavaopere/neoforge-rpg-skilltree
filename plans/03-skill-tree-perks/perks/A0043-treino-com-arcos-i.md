# A0043 — Treino com Arcos I

## Estado

- **Design:** APROVADO após correção provider-native/Mastery.
- **Implementação:** **IMPLEMENTAÇÃO CONFIRMADA PELO CHAT 3**.
- **Notion:** `3c569db9-f0db-81ba-86de-cc43d7d3dae3`; fonte canônica mantém `epicfight:bow`.

## Contrato canônico

- Fundamento exterior de Arcos; nível 8 + `epicfight:bow` ≥60 + gateway `epic_bow`.
- +3% de dano com arcos por rank, até +9%.
- Classificador BOW único para A0043–A0048:
  - vanilla `BowItem`/subclasse + owner real do projétil = classificação segura;
  - item externo somente por capability/categoria provider-native ou mapping versionado explícito;
  - unknown = fail-closed.
- Mastery **canônica** `epicfight:bow`: +10 uma única vez por tipo hostil inédito atingido por projétil BOW elegível; 6 tipos = 60, 8 tipos = 80.

## Evidência runtime

- `A0041A0060ProjectileEvents.family(...)` classifica `BowItem` como BOW e exige `AbstractArrow` com owner `ServerPlayer` elegível.
- `ProjectileMeta` preserva root action/origem e `onIncomingDamage` aplica `baseDamageMultiplier(BOW, ranks)` no impacto hostil.
- `PhysicalProjectileMasteryEvents` correlaciona `ArrowLooseEvent` → `AbstractArrow` → `LivingDamageEvent.Post` e chama `WeaponMasteryMilestonePolicy.confirmedPhysicalProjectileHit(...)`.
- `WeaponMasteryMilestoneRuntime.awardIfNew(...)` persiste a discovery `mastery:epicfight:weapon/bow/hostile_type/<tipo>` e impede premiação repetida do mesmo tipo.
- A identity de discovery é compartilhada entre origins/providers, impedindo double-award quando outro adapter observar o mesmo outcome semântico.
- `tree_architecture/combat.json` atual já exige `requiredMastery: {"epicfight:bow":60}`; a divergência histórica `combat:bow` foi resolvida por evolução posterior da `main`.

## Provider→árvore

- **RPG Skill Tree:** authority de rank, Mastery, discovery/dedup e root action; namespace canônico `epicfight:bow`.
- **Minecraft/NeoForge:** `BowItem`, release física, owner do projétil e POST de dano real.
- **Epic Fight:** pode observar a mesma identidade sem criar ledger paralela.
- **Black Arcana:** `ARCANE_BACKLASH`/spell projectile não é projétil BOW físico.
- **Mobstein 5.4.4:** projéteis/ataques de allies/bodyguards permanecem Mobstein-owned e não creditam dano/Mastery do dono.
- **Volcanoes / Enshrouded:** não classificam arco nem projétil.
- **Stage 11.01 itemização:** `SEM HOOK SEGURO`; rolls não projetam dano de A0043.

## Pendências históricas resolvidas

- `P-A0043-01`: resolvida por `PhysicalProjectileMasteryEvents` + `WeaponMasteryMilestoneRuntime`.
- `P-A0043-02`: resolvida; architecture, model e runtime usam `epicfight:bow`.

## Pendência Chat 3

- validar 6/8 tipos hostis distintos, dedup por tipo e gate 59/60 em integração real;
- validar vanilla BowItem/subclasse, fake/summon/spell/derived e coexistência com Epic Fight sem premiação dupla;
- validar dedicated-server/multiplayer.

## Testes exigidos

- vanilla BowItem/subclasse;
- item externo provider-native vs unknown;
- owner real vs fake/summon/spell/derived;
- uma aplicação de dano por projétil/root;
- 6/8 tipos hostis distintos para Mastery;
- gate architecture/model usando exatamente a mesma chave de Mastery;
- coexistência Epic Fight sem double-award;
- dedicated server/multiplayer.

## Fechamento Chat 2 — 2026-09-01

Nenhum producer novo duplicado foi criado: o Chat 2 reutilizou a infraestrutura persistente já existente na `main`. Não foi executada a bateria final do Chat 3.

## Fechamento Chat 3 — 2026-09-02

Mastery BOW por discovery finita, dedup por tipo, chave `epicfight:bow`, owner/provenance e dano foram revalidados. CI #3378 (`33665545963`) GREEN completo. **Estado final: IMPLEMENTAÇÃO CONFIRMADA.**