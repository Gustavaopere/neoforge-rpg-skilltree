# A0043 — Treino com Arcos I

## Estado

- **Design:** APROVADO após correção provider-native/Mastery.
- **Implementação:** PARCIAL; dano físico BOW existe, mas `P-A0043-01` mantém o gate de Mastery sem producer projetil comprovado para a rota vanilla/NeoForge e `P-A0043-02` registra divergência de namespace no catálogo de arquitetura.
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
- O bridge de projéteis não chama producer de Mastery/discovery para a rota BOW.
- `A0041A0060ProjectileEvents` consulta `A0041A0060RuntimeState.mastery(player, "epicfight:bow")` e `CombatPerkTreeModel` também exige `epicfight:bow`.
- **Divergência encontrada no review da PR #243:** `src/main/resources/data/rpgskilltree/tree_architecture/combat.json` ainda publica `requiredMastery: {"combat:bow":60}`. Essa chave não é a ledger canônica do Notion/model/runtime e torna a documentação/arquitetura divergente do servidor.

## Provider→árvore

- **RPG Skill Tree:** authority de rank, Mastery, dedup e root action; namespace canônico deste contrato é `epicfight:bow` até eventual migração formal de ledger.
- **Black Arcana:** `ARCANE_BACKLASH`/spell projectile não é projétil BOW físico.
- **Mobstein 5.4.4:** projéteis/ataques de allies/bodyguards permanecem Mobstein-owned e não creditam dano/Mastery do dono.
- **Volcanoes / Enshrouded:** não classificam arco nem projétil.
- **Stage 11.01 itemização:** `SEM HOOK SEGURO`; rolls não projetam dano de A0043.

## Pendências Chat 2

### P-A0043-01 — producer de Mastery BOW para projétil físico

Adicionar um producer persistente e deduplicado no receipt pós-hit real da mesma classificação BOW: `+10` uma vez por tipo hostil inédito em `DiscoveryProgress`, sem spam por dano, sem summon/fake/spell/derived e sem dupla premiação quando Epic Fight já reivindicar o mesmo outcome. Provar gate 59/60 e terminal 79/80.

### P-A0043-02 — reconciliar namespace de Mastery BOW

Reconciliar `tree_architecture/combat.json` com a fonte canônica `epicfight:bow` usada por Notion, `CombatPerkTreeModel` e projectile runtime, ou executar uma migração de ledger explicitamente desenhada antes de escolher outro ID. Não manter `combat:bow` e `epicfight:bow` como ledgers paralelos. Adicionar teste que falhe se a architecture gate divergir do `CombatPerkTreeModel`/contrato canônico.

## Testes exigidos

- vanilla BowItem/subclasse;
- item externo provider-native vs unknown;
- owner real vs fake/summon/spell/derived;
- uma aplicação de dano por projétil/root;
- 6/8 tipos hostis distintos para Mastery;
- gate architecture/model usando exatamente a mesma chave de Mastery;
- coexistência Epic Fight sem double-award;
- dedicated server/multiplayer.
