# Capability delta — A0181–A0190

## Escopo

Gate obrigatório do Chat 1 para o lote exato **A0181–A0190**, cobrindo:

- delta fresco dos quatro projetos próprios;
- provider → árvore;
- perk → provider;
- decisão explícita para capabilities novas;
- prevenção de promoção temática sem causalidade.

## Baselines observadas no ciclo

### RPG Skill Tree

A auditoria abriu sobre a linha de `main` que continha `TreeUnlockResolver`/`TreeUnlockDefinition` e Stage 04.01. Durante o ciclo, `main` avançou pela PR #373 até `efb79382e023f5e35a8f138bbc16c0d8088548aa` e depois recebeu commits concorrentes sem capability nova do lote.

A PR #373 alterou `SkillInvestmentMetadataParser` por refactor/hardening Sonar e testes associados. A comparação preserva a mesma validação de IDs, tags e pesos de domínio; **não cria domínio, gate, provider ou capability nova**.

Disposição: **SEM DELTA FUNCIONAL RELEVANTE** para A0181–A0190. Reutilizar a pipeline de unlock/investment já canônica; não criar `SpecialistGateResolver` paralelo.

### Volcanoes

Baseline/freshness confirmada: `eaddc3232dfc600780769f4a5e7e45ff1e50181c`.

Nenhuma mudança relevante desde o lote anterior. Volcanoes mantém authority própria de vulcanismo/atmosfera/geothermal/heat e bridge bounded com Cold Sweat.

Disposição: **NÃO INTEGRAR** heat/geothermal/ash como NATURE ou HOLY por tema. Nenhuma capability nova para o lote.

### Enshrouded

Baseline/freshness confirmada: `5671114c361be8cbb6fd2dadafdaa05f27d1fe2c`.

Sem delta de runtime pertinente ao lote desde a baseline anterior. Classificações defensivas próprias do Enshrouded não viram producer de direct magic outcome do RPG Skill Tree.

Disposição: **SEM DELTA RELEVANTE**.

### Black Arcana

Baseline anterior do gate de perks: `d8fb667cc5954d5811dacbbef4da1053fa296581`.

Freshness do ciclo: `6b77b5c0ec4f0ff4a8688bb105cef055860c061c`.

Delta real: Stage 06 Rituals foi integrado e passou a ser canônico. O delta inclui:

- core ritualístico próprio (`RitualEngine`, registry, session state, activation guard);
- completion ledger exatamente-once;
- reservation/rollback de componentes;
- grand ritual `black_arcana:veil_anchor_consecration`;
- bridge Eidolon `EidolonAnchorAttunementRitual`;
- provider transacional de espíritos Malum.

Evidência crítica de authority:

- o bridge Eidolon documenta que o hook público 1.21.1 não traz caster identity e, por isso, o resultado persistente é **anchor-scoped**, não atribuído a um jogador próximo inferido;
- Malum é component provider transacional do ritual, não producer de direct damage NATURE/HOLY;
- o outcome do grand ritual é fornecido pelo binding server-side e não equivale a spell outcome do RPG Skill Tree.

Disposição: **CAPABILITY REAL, MAS NÃO DEVE SER PROMOVIDA A ESTE LOTE**. Não usar Stage 06 para preencher `DIRECT_MAGIC_OUTCOME_V1`, `MAGIC_THERMAL_PARCEL_V1`, HOLY/NATURE Mastery, sustain ou affinity. Uma integração futura ritual→árvore exigirá contrato explícito de progressão/evento e nova auditoria.

## Provider → árvore

| Provider/capability | Região/uso permitido | Decisão |
|---|---|---|
| Iron's 3.16.3 `HOLY_MAGIC` | VITALITY/HOLY resistance + futura ARCANE/HOLY direct outcome | classifier defensivo aprovado; ofensivo exige DIRECT |
| Iron's 3.16.3 `NATURE_MAGIC` | futura ARCANE/NATURE direct outcome | identidade real; DIRECT ainda ausente |
| NeoForge `LivingDamageEvent.Pre` | A0186/A0187 | boundary de mitigação aprovado |
| Cold Sweat 2.4.2 | A0182/A0189 | authority térmica; requer parcel causal antes da aplicação |
| Eidolon 0.5.0.2 | HOLY somente por adapter explícito | tema/ritual não classifica sozinho |
| Ars Nouveau/Ars Elemental | NATURE somente por adapter causal | sem inferência temática |
| Hexalia 1.3.5 | nenhuma promoção automática | ecologia/visual não é NATURE outcome |
| Epic Fight/lane registry | A0181/A0188 futuras | somente melee lane inequívoca; fist depende P-0032 |
| Black Arcana Stage 06 | ritual/progressão futura explícita | não preencher damage/Mastery/thermal deste lote |

## Perk → provider

| Perk | Providers/capabilities necessárias | Estado |
|---|---|---|
| A0181 | NATURE direct outcome + melee lane + derived same-outcome + sustain futuro | unavailable |
| A0182 | NATURE thermal parcel + Cold Sweat | unavailable |
| A0183 | TreeUnlock canônico + A0182 | unavailable transitivo |
| A0184 | Iron's/Eidolon HOLY classifier + direct magic outcome | unavailable |
| A0185 | A0184 + direct outcome + undead/state receipt | unavailable |
| A0186 | NeoForge Pre + Iron's HOLY classifier + VITALITY | implementável |
| A0187 | A0186 + PRE-HP `<50%`; receipt HOLY de cura opcional futuro | implementável sem heal-window |
| A0188 | HOLY direct outcome + melee lane + derived same-outcome + absorption futuro | unavailable |
| A0189 | HOLY positive thermal parcel + Cold Sweat + A0184 closure | unavailable |
| A0190 | TreeUnlock canônico + A0189 | unavailable transitivo |

## Contracts ausentes que NÃO podem ser sintetizados localmente

- `DIRECT_MAGIC_OUTCOME_V1`;
- `DERIVED_DAMAGE_COMPONENT_V1`;
- `MAGIC_THERMAL_PARCEL_V1`;
- `HOLY_HEAL_RECEIPT_V1` ou equivalente formal;
- sustain service canônico identificado;
- absorption-contribution service canônico identificado.

A ausência desses contracts não autoriza listener por perk, segundo DamageSource, inference por escola, leitura global de temperatura, cura genérica ou bônus temático.

## Cobertura provider → árvore

- HOLY defensivo: coberto por A0186/A0187 via VITALITY/HOLY bridge.
- HOLY ofensivo: representado por A0184/A0185/A0188, porém corretamente fail-closed até direct outcome/same-outcome.
- HOLY térmico: representado por A0189, fail-closed até parcel causal.
- HOLY terminal: representado por A0190, usando unlock canônico quando dependencies forem válidas.
- NATURE imbuement/thermal/terminal: A0181/A0182/A0183 representam os eixos, mas permanecem fail-closed pela closure herdada de A0177 e contracts ausentes.
- Black Arcana Rituals: explicitamente disposto como sistema separado, sem promoção temática.

Não foi detectada capability nova relevante sem disposição explícita.

## Conclusão

O gate de delta dos quatro projetos próprios está **FECHADO** para A0181–A0190.

A única capability nova material desde o lote anterior é Black Arcana Stage 06 Rituals; ela foi classificada como **não integrável às perks deste lote sem contrato adicional**, preservando authority e causalidade. A0186/A0187 continuam as únicas perks implementáveis no snapshot atual.