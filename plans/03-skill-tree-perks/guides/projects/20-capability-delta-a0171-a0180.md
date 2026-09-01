# Capability delta — A0171–A0180

**Data:** 2026-09-01  
**Lote:** A0171–A0180  
**Objetivo:** aplicar o gate obrigatório de delta dos quatro projetos próprios antes de fechar design de LIGHTNING/NATURE.

## 1. Baseline e heads auditados

| Projeto | Head usado | Delta desde o lote anterior | Disposição |
|---|---|---|---|
| RPG Skill Tree | `c6677431a5c7cb2050ffc445834286a6001026fe` | `main` avançou desde `0be05cb9...`; inclui Stage 04.01 canonical investment projection (PR #365) e consolidação Volcanoes #369 | **INTEGRAR somente a infraestrutura canônica de investimento/unlock pertinente a A0176; não promover consolidação Volcanoes a capability elemental** |
| Volcanoes | `eaddc3232dfc600780769f4a5e7e45ff1e50181c` | sem nova capability funcional do standalone desde o checkpoint do lote anterior | **NÃO DEVE SER INTEGRADO** como LIGHTNING/NATURE por tema |
| Enshrouded | `5671114c361be8cbb6fd2dadafdaa05f27d1fe2c` | sem nova capability funcional pertinente após a correção documental/proveniência Stage 08.02 já classificada | **NÃO DEVE SER INTEGRADO** como perk LIGHTNING/NATURE |
| Black Arcana | `d8fb667cc5954d5811dacbbef4da1053fa296581` | sem nova capability jogável pertinente; mudanças já classificadas como governança/sequencing planejado | **NÃO DEVE SER INTEGRADO** como perk LIGHTNING/NATURE |

Nenhum baseline foi avançado com capability detectada sem decisão explícita.

## 2. Delta do RPG Skill Tree

### Stage 04.01 — canonical investment projection

PR #365 foi mergeada e adicionou projeção canônica de investimento sem alterar live class authority/balance.

Capacidades relevantes comprovadas:

- projeção de ranks comprados a partir de `ProgressionState`;
- metadata de contribuição derivada somente de tags explícitas de skill resource;
- domínio canônico `rpgskilltree:domain/<domain>`;
- fail-closed quando node comprado não possui metadata ou quando revisions divergem;
- `InvestmentState` / `ArchetypeResolver` permanecem read-only;
- nenhuma inferência por node ID, posição ou topologia.

A `main` já possuía `TreeUnlockResolver`/`TreeUnlockDefinition`, que avaliam domain scores, required tags e minimum Mastery experience. Portanto, para A0176:

- a infraestrutura genérica de avaliação **existe**;
- não criar `SpecialistGateResolver` paralelo;
- Gate A/B futuro deve compor a projeção/investimento canônico;
- Gate C continua sendo a posse da terminal A0176;
- A0176 permanece indisponível hoje por dependency closure em A0175, não por ausência do resolver.

### Merge #369 — consolidação Volcanoes

A `main` atualiza pins de acceptance, arquiva planos concluídos do Volcanoes e adiciona verificação de paridade standalone. Isso não cria uma nova semântica LIGHTNING/NATURE, não fornece direct magic outcome, state receipt ou thermal parcel e não deve ser transformado em perk elemental.

## 3. Provider → árvore

| Provider/capability | Evidência | Cobertura no lote | Decisão |
|---|---|---|---|
| NeoForge 1.21.1 `LivingDamageEvent.Pre` | boundary mutável server-side | A0172/A0173/A0179/A0180 | usar um único `ElementalDamageMitigationResolver` |
| Minecraft `DamageTypeTags.IS_LIGHTNING` | classifier semântico vanilla | A0172/A0173 | LIGHTNING defensivo; não prova magia DIRECT |
| Iron's 3.16.3 `lightning_magic` / `LIGHTNING_MAGIC` | identidade provider-native | A0172/A0173; futuro A0171/A0174/A0175 somente com causalidade adicional | adapter exato/versionado |
| Iron's 3.16.3 `nature_magic` / `NATURE_MAGIC` | identidade provider-native | A0179/A0180; futuro A0177/A0178 somente com causalidade adicional | adapter exato/versionado |
| Iron's `CHARGED` | self-buff do caster | A0171 | **explicitamente inelegível** como state consumível do alvo |
| Iron's RootSpell/RootEntity | controle específico de provider | A0178 | não promover a `NATURE_CONTROL_RECEIPT_V1` genérico sem adapter explícito |
| Ars Nouveau 5.13.1 / Ars Elemental 0.7.10.1 | spell events/context e conteúdo elemental | A0171/A0174/A0175/A0177/A0178 somente quando adapters versionados fecharem contracts | fail-closed onde causalidade/state não foi provado |
| Cold Sweat 2.4.2 | authority corporal | A0175 | manter único owner; exigir `MAGIC_THERMAL_PARCEL_V1` antes da mutação |
| Create/Oritech/FE | tecnologia | corredor LIGHTNING | não converter energia/choque tecnológico em magia LIGHTNING por tema |
| Volcanoes | calor/ambiente/world systems | nenhum mapping direto | não classificar como FIRE/LIGHTNING/NATURE sem contract explícito |
| Enshrouded | sistemas próprios de magia/defesa | nenhum mapping novo | não criar producer do Skill Tree por proximidade temática |
| Black Arcana | magia/projeto próprio | nenhum mapping novo | capabilities planejadas não contam como runtime presente |

## 4. Perk → provider/capability

| Perk | Provider/boundary necessário | Estado |
|---|---|---|
| A0171 Dano de Raio II | `DIRECT_MAGIC_OUTCOME_V1` + `LIGHTNING_CONSUMABLE_STATE_V1` | `UNAVAILABLE_NODE` |
| A0172 Resistência a Raio I | NeoForge Pre + `IS_LIGHTNING` + Iron's `lightning_magic` | **implementável** |
| A0173 Resistência a Raio II | mesmo resolver/bucket + health PRE-impacto | **implementável** |
| A0174 Imbuimento de Raio | `DIRECT_MAGIC_OUTCOME_V1` + `DERIVED_DAMAGE_COMPONENT_V1` + melee lane mastery | `UNAVAILABLE_NODE` |
| A0175 Afinidade de Raio | Cold Sweat + `MAGIC_THERMAL_PARCEL_V1` | `UNAVAILABLE_NODE` |
| A0176 Maestria de Raio | `TreeUnlockResolver` + canonical investment projection + A0175 | `UNAVAILABLE_NODE` transitivo |
| A0177 Dano de Natureza I | `DIRECT_MAGIC_OUTCOME_V1` + NATURE classifier | `UNAVAILABLE_NODE` |
| A0178 Dano de Natureza II | direct outcome + `NATURE_CONTROL_RECEIPT_V1` | `UNAVAILABLE_NODE` |
| A0179 Resistência a Natureza I | NeoForge Pre + Iron's `nature_magic` exato | **implementável** |
| A0180 Resistência a Natureza II | mesmo resolver/bucket + health PRE-impacto | **implementável** |

## 5. Authorities preservadas

- **Cold Sweat** continua único owner da temperatura corporal.
- **RPG Skill Tree** pode modificar dano somente no boundary canônico compartilhado, sem reducers por perk/provider.
- **Provider mágico** fornece identidade/state quando explicitamente comprovado; não recebe authority sobre progressão/gates do Skill Tree.
- **Tecnologia** permanece tecnologia; FE não é LIGHTNING mágico.
- **Projetos próprios** não são promovidos por associação temática.

## 6. Capabilities ausentes detectadas

Continuam necessárias, mas não foram inventadas neste lote:

- `DIRECT_MAGIC_OUTCOME_V1`;
- `LIGHTNING_CONSUMABLE_STATE_V1`;
- `DERIVED_DAMAGE_COMPONENT_V1`;
- `MAGIC_THERMAL_PARCEL_V1`;
- `NATURE_CONTROL_RECEIPT_V1`.

`TreeUnlockResolver`/`TreeUnlockDefinition` **não** entram nesta lista de ausentes: estão presentes na `main` atual.

## 7. Fechamento do gate

**PASS.** Todos os deltas dos quatro projetos próprios receberam disposição explícita. Nenhuma capability ficou sem classificação provider→árvore ou perk→provider. Nenhuma feature planejada foi tratada como runtime e nenhum sistema foi convertido em LIGHTNING/NATURE somente por tema.
