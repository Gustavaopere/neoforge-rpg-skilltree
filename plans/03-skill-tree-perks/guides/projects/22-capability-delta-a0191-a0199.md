# Capability delta — A0191–A0199

## Escopo

Gate obrigatório do Chat 1 para o bloco residual explicitamente autorizado **A0191–A0199**.

Este documento cobre:

- delta fresco dos quatro projetos próprios;
- provider → árvore;
- perk → provider;
- capabilities novas ou ausentes;
- authority, causalidade e proibição de promoção temática;
- efeito da fronteira especial A0200+ já auditada anteriormente.

A0200+ não é reaberta neste ciclo.

## Baselines/freshness observadas

### RPG Skill Tree

Freshness final observada durante a auditoria: `main@be5ddad0b47b47c8a6d724574e1220684b668413`.

A `main` avançou durante o ciclo pela PR #381, `Validate Gradle Actions v6 with MIT basic caching`. O delta é de workflow/cache provider e não cria hook, provider, DamageType, resource authority, resolver de cura, outcome magic ou gate novo para A0191–A0199.

Capabilities relevantes já existentes:

- `TreeUnlockResolver` / `TreeUnlockDefinition` / `TreeUnlockCatalog`;
- projeção canônica de investimento do Stage 04.01;
- Mastery ledger canônica;
- pipeline de mitigação defensiva compartilhável por `LivingDamageEvent.Pre`.

Contracts não comprovados como canônicos na `main` para este lote:

- `DIRECT_MAGIC_OUTCOME_V1`;
- `BLOOD_STATE_WINDOW_RECEIPT_V1`;
- `BLEED_DURATION_APPLICATION_V1`;
- `DERIVED_DAMAGE_COMPONENT_V1`;
- `EXTERNAL_HEAL_ATTRIBUTION_V1` suficiente para A0196;
- `ELDRITCH_STATE_WINDOW_RECEIPT_V1`;
- `PRIMARY_RESOURCE_REGEN_MODIFIER_V1`.

Disposição: A0193 e a parcela defensiva de A0194 são implementáveis; demais contracts ausentes permanecem fail-closed. A0197 deve reutilizar TreeUnlock canônico, sem criar resolver Specialist paralelo.

### Volcanoes

Fonte completa standalone usada como provenance de consolidação: `eaddc3232dfc600780769f4a5e7e45ff1e50181c`.

O runtime canônico já foi consolidado no RPG Skill Tree; o standalone posterior foi reduzido a tombstone documental. Nenhuma capability BLOOD/ELDRITCH surge de worldgen, heat, atmosfera, geothermal, pressão ou bridge Cold Sweat.

Disposição: **SEM DELTA RELEVANTE**. Não integrar heat/ash/pressure como BLOOD ou ELDRITCH por tema.

### Enshrouded

Freshness: `5671114c361be8cbb6fd2dadafdaa05f27d1fe2c`.

Nenhuma capability nova pertinente ao bloco. Classificadores/reducers defensivos próprios não viram producer de direct magic outcome do Skill Tree.

Disposição: **SEM DELTA RELEVANTE**.

### Black Arcana

Freshness: `6b77b5c0ec4f0ff4a8688bb105cef055860c061c`, Stage 06 Rituals Canonical Foundations.

Capabilities reais:

- ritual engine server-authoritative;
- start/precommit/postcommit/cancel events;
- paid-start semantics;
- completion ledger exactly-once;
- reservas/rollback de componentes;
- bridge Eidolon anchor-scoped porque o hook público não fornece caster identity;
- Malum como provider transacional de componentes.

Busca/auditoria do snapshot não encontrou outcome ELDRITCH direto do Skill Tree. O Stage 06 também não publica `BLACK_ARCANA_BLOOD_OUTCOME`, BLOOD state receipt, ELDRITCH state receipt ou modifier do recurso primário de conjuração requerido por A0199.

Disposição: **CAPABILITY REAL, MAS AUTHORITY SEPARADA**. Não promover ritual/corruption/strain/soul/occult theme a BLOOD/ELDRITCH damage, Mastery, state receipt ou resource modifier sem bridge formal futura.

## Provider evidence externa exata

### Iron's Spells 'n Spellbooks 3.16.3

Snapshot auditado: `iron431/irons-spells-n-spellbooks@e4056af90302d37eb1739f5ff05020b020e6e252`.

`SchoolRegistry` confirma nativamente:

- `blood` → `ISSDamageTypes.BLOOD_MAGIC`;
- `eldritch` → `ISSDamageTypes.ELDRITCH_MAGIC`.

Conclusão: BLOOD e ELDRITCH são identidades semânticas reais do provider. Essa prova é suficiente para classificação quando a identity/tag exata está observável; não prova por si só player ownership, DIRECT, root/outcome, state application, primary-resource regen ou dedup.

### Vampirism 1.10.12 e ecossistema

Vampirism mantém economia de blood, lifesteal e recursos próprios. A compatibilidade Vampirism ↔ Iron's pode correlacionar custos/curas provider-native, mas isso não autoriza:

- transformar blood meter em recurso do Skill Tree;
- inferir BLOOD damage de qualquer perda/restauração de blood;
- tratar lifesteal como direct BLOOD outcome;
- tratar bleed físico como `BLOOD_MAGIC`.

### Addons Eldritch

Discerning The Eldritch e Deeper and Darker Spellbooks podem ampliar o conteúdo da school Eldritch do Iron's. Participação nas perks exige adapter/identity exato e o mesmo contrato DIRECT/state/resource exigido; presença do addon não reduz os gates.

## Provider → árvore

| Provider/capability | Região/uso permitido | Decisão |
|---|---|---|
| Iron's 3.16.3 `BLOOD_MAGIC` | ARCANE/BLOOD + VITALITY/BLOOD defensive bridge | classifier real; ofensivo exige DIRECT |
| Iron's 3.16.3 `ELDRITCH_MAGIC` | ARCANE/ELDRITCH | classifier real; ofensivo exige DIRECT |
| NeoForge `LivingDamageEvent.Pre` | A0193/A0194 defense | boundary aprovado |
| Vampirism blood/lifesteal | resource/sustain próprio do provider | não promover a BLOOD outcome/state por tema |
| Epic Fight/lane registry | A0195 futura | melee lane exata; fist depende P-0032 |
| TreeUnlock + Stage04.01 | A0197 | authority canônica Gate A/B/C; sem resolver paralelo |
| Black Arcana Stage06 | ritual/progressão futura explícita | não preencher BLOOD/ELDRITCH contracts deste bloco |
| Discerning/Deeper Eldritch addons | A0198/A0199 futuras | somente por adapter exato + contracts completos |

## Perk → provider

| Perk | Capabilities necessárias | Estado |
|---|---|---|
| A0191 | Iron's BLOOD identity + `DIRECT_MAGIC_OUTCOME_V1` | unavailable |
| A0192 | A0191 + DIRECT + `BLOOD_STATE_WINDOW_RECEIPT_V1` | unavailable |
| A0193 | NeoForge Pre + Iron's BLOOD classifier + VITALITY gate | implementável |
| A0194 | A0193 + PRE-HP `<50%`; BLEED receipt opcional component-wise | implementável; BLEED subcomponent unavailable |
| A0195 | BLOOD direct commit + melee lane + `DERIVED_DAMAGE_COMPONENT_V1` | unavailable |
| A0196 | DIRECT + action health snapshot + external-heal attribution | unavailable all-or-nothing |
| A0197 | TreeUnlock canônico + Stage04.01 + A0196 | unavailable transitivo |
| A0198 | Iron's ELDRITCH identity + `DIRECT_MAGIC_OUTCOME_V1` | unavailable |
| A0199 | DIRECT + ELDRITCH state window + primary-resource regen modifier | unavailable all-or-nothing |

## Cobertura provider → árvore

- BLOOD ofensivo está representado por A0191/A0192/A0195/A0196, corretamente fail-closed onde falta causalidade.
- BLOOD defensivo está coberto por A0193/A0194 via VITALITY e classifier exato.
- BLOOD terminal está representado por A0197 e deve usar unlock canônico.
- ELDRITCH ofensivo inicial está representado por A0198/A0199, fail-closed até DIRECT/state/resource seams.
- Vampirism continua com resource authority própria, sem duplicação de blood meter.
- Black Arcana Stage06 possui disposição explícita como sistema ritual separado.

Não foi detectada capability nova pertinente sem disposição explícita.

## Conclusão

O gate de delta dos quatro projetos próprios está **FECHADO** para A0191–A0199.

Resultado de capability no snapshot atual:

- A0193: implementável;
- A0194: resistência low-HP implementável, BLEED duration component fail-closed;
- A0191/A0192/A0195/A0196/A0197/A0198/A0199: unavailable.

Nenhuma dessas decisões autoriza reabrir A0200+ neste ciclo.