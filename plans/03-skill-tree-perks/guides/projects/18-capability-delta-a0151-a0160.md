# Capability delta — A0151–A0160

**Data:** 2026-09-01  
**RPG baseline:** `0be05cb9cee8c34ff5ceb9091d2b5cb5d4c55328`

## Heads frescos

| Projeto | Head | Classificação para o lote |
|---|---|---|
| RPG Skill Tree | `0be05cb9cee8c34ff5ceb9091d2b5cb5d4c55328` | hardening Sonar/SavedData do Volcanoes; sem capability nova de perk |
| Volcanoes | `eaddc3232dfc600780769f4a5e7e45ff1e50181c` | sem delta; heat/geologia não equivalem a FIRE damage |
| Enshrouded | `bf97ea0eba64b3d24e02936b9f3fe384b833ed0a` | Stage 08.02: classificação defensiva própria de magia Iron's/Ars; BRIDGE, não outcome do Skill Tree |
| Black Arcana | `e573a0edfcb69d09e423b60ad75ab71b9d8e70c5` | sem delta pertinente; Arcane/Corruption/Strain não equivalem às capabilities deste lote |

## RPG Skill Tree

O delta imediato `d7fdffcf… → 0be05cb9…` troca acesso estático a `Tag.TAG_INT` em SavedData do Volcanoes e adiciona verificação CI, sem mudança semântica. A arquitetura relevante permanece:

- `A0001A0020CriticalService` decide no máximo uma vez por `(actorId, rootActionId)`;
- não existe producer canônico `DIRECT_MAGIC_OUTCOME_V1`;
- não existe `DERIVED_DAMAGE_COMPONENT_V1` para A0160;
- `P-A0083-01` já registra a necessidade de producer `DIRECT_MAGIC`, portanto A0151/A0156 não podem abrir caminho paralelo ad hoc;
- A0158/A0159 podem criar o resolver FIRE aprovado porque NeoForge já fornece boundary público completo.

## Enshrouded

Stage 08.02 compõe evidência de magia de Ars Nouveau e Iron's em um único `CompositeMagicDamageClassifier`, preservando um único `MagicResistanceService`. Dano arbitrário dos namespaces permanece UNKNOWN.

Isso é authority defensiva do Enshrouded. Não publica `magic_direct_outcome_id`, crítica, radius/duration/range ou componente FIRE para o RPG. Quando um DamageSource também for FIRE, os reducers podem compor em seus pipelines próprios sem transformar Enshrouded em provider de perk.

## Providers externos exatos

### Iron's Spells 'n Spellbooks 3.16.3

Snapshot `e4056af90302d37eb1739f5ff05020b020e6e252`.

- `SpellDamageEvent` é mutável antes de `hurt`;
- `SpellDamageSource` expõe spell/source;
- `SchoolRegistry.FIRE_RESOURCE = irons_spellbooks:fire` e FIRE usa `FIRE_MAGIC`;
- post-hit fire termina em `igniteForTicks(...)`, sem ownership por fonte;
- nenhum hook genérico de range/duration/radius foi provado.

Consequência: A0157 não pode consumir 40 ticks do contador global sem `OWNED_FIRE_STATE_V1`.

### Ars Nouveau 5.13.1

Snapshot `112920ff774831f204031da75b4c4e73d3765157`.

- `SpellCastEvent` expõe `Spell` + `SpellContext`;
- `SpellContext` possui attachments propagáveis, permitindo future action correlation;
- `SpellDamageEvent.Pre` expõe caster/context e dano mutável;
- spell parts possuem registry identity;
- effects FIRE específicos existem, mas podem gerar outcomes derivados; namespace/damage type não distingue DIRECT genericamente;
- AoE não implica radius universal: Flare usa AoE para quantidade de cinders.

### NeoForge 1.21.1

`LivingDamageEvent.Pre` expõe `DamageSource`, `getNewDamage()` e `setNewDamage(...)` antes da perda de vida. É o boundary aprovado para um único `DamageMitigationResolver`/bucket `RPG_FIRE_RESISTANCE` em A0158/A0159.

## Provider → árvore

| Capability | Disposição |
|---|---|
| CriticalService | reutilizar em A0151/A0152; falta producer DIRECT_MAGIC/action state |
| Iron's/Ars pre-damage magic events | adapters futuros plausíveis; não bastam sem classifier/outcome |
| semantic range | A0153 fail-closed |
| base effect duration | A0154 fail-closed |
| radius + distinct targets + MANA receipt | A0155 fail-closed |
| explicit FIRE identity | disponível por provider, mas A0156 ainda exige DIRECT_MAGIC |
| owned removable fire duration | A0157 fail-closed |
| NeoForge FIRE pre-damage mitigation | A0158/A0159 boundary completo |
| same-outcome derived FIRE component | A0160 fail-closed |
| Enshrouded magic classifier | defesa nativa; não converter em outcome RPG |

## Perk → provider

| Perk | Estado |
|---|---|
| A0151 | `UNAVAILABLE_NODE` — `DIRECT_MAGIC_OUTCOME_V1` ausente |
| A0152 | `UNAVAILABLE_NODE` transitivo + action state ausente |
| A0153 | `UNAVAILABLE_NODE` — range adapter ausente |
| A0154 | `UNAVAILABLE_NODE` — duration adapter ausente |
| A0155 | `UNAVAILABLE_NODE` — radius/targets/MANA receipt incompletos |
| A0156 | `UNAVAILABLE_NODE` — DIRECT_MAGIC + FIRE classifier canônicos ausentes |
| A0157 | `UNAVAILABLE_NODE` — owned fire state ausente |
| A0158 | DESIGN IMPLEMENTÁVEL — NeoForge FIRE Pre |
| A0159 | DESIGN IMPLEMENTÁVEL — mesmo bucket + pre-impact health |
| A0160 | `UNAVAILABLE_NODE` — derived same-outcome component ausente |

Nenhuma capability detectada ficou sem disposição.
