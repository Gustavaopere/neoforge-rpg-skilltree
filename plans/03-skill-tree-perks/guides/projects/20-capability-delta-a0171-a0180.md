# Capability delta — A0171–A0180

**Auditoria inicial:** 2026-09-01  
**Revalidação final do Chat 1:** 2026-09-02  
**Lote:** A0171–A0180, exatamente 10 perks consecutivas.  
**Escopo:** LIGHTNING/NATURE + gate obrigatório provider→árvore dos quatro projetos/sistemas próprios.

## 1. Freshness operacional final

A revalidação de 2026-09-02 substitui, para fins de freshness, os heads usados no fechamento inicial de 2026-09-01.

| Projeto/sistema | Fonte operacional atual | Head auditado | Disposição |
|---|---|---|---|
| RPG Skill Tree | `Gustavaopere/neoforge-rpg-skilltree/main` + `plans/STATUS.md` | `5213d068a91c95f45b9e119dec0be0636abc426d` | delta amplo desde `d4ce3176...`; nenhum dos cinco contracts mágicos bloqueantes do lote apareceu |
| Volcanoes | **subsystem nativo do mesmo RPG Skill Tree** + `docs/archive/volcanoes/STATUS.md` | mesmo `5213d068...` | authority ambiental/geológica preservada; consolidação/retirada do standalone não cria LIGHTNING/NATURE mágico |
| Volcanoes standalone | somente proveniência/tombstone | `298352973e941c2034c97465929dc67f6a0400e2` | **NÃO é mais fonte operacional de runtime**; não usar para concluir freshness futura |
| Enshrouded | `Gustavaopere/Enshrouded/main` + `plans/STATUS.md` | `5fb7e1da39288cae82beaccf2a869e6ebbb099a3` | Stage 08.03 é integração de ownership/protection/claims; não cria outcome LIGHTNING/NATURE |
| Black Arcana | `Gustavaopere/Black-Arcana/main` + `plans/STATUS.md` | `6b77b5c0ec4f0ff4a8688bb105cef055860c061c` | Stage 06 Rituals é capability nova relevante provider→árvore, classificada abaixo; não cria direct LIGHTNING/NATURE outcome |

Nenhum baseline/checkpoint é promovido ignorando capability detectada: as mudanças acima recebem decisão explícita antes do fechamento final deste lote.

## 2. Mudança de topologia — Volcanoes consolidado

Desde a consolidação do Volcanoes, a fonte operacional mudou. O runtime canônico vive dentro do `neoforge-rpg-skilltree`, preservando namespaces e authority próprias para geologia, tectônica, atmosfera, respiração, pressão, proteção e integrações.

Consequências obrigatórias:

- o antigo `Gustavaopere/Volcanoes@eaddc323...` é proveniência do snapshot importado, não upstream operacional;
- o head tombstone `298352973...` apenas aposenta o runtime standalone após a consolidação;
- futuras auditorias de delta Volcanoes devem filtrar as superfícies Volcanoes na `main` unificada e ler `docs/archive/volcanoes/STATUS.md`;
- hospedar Volcanoes no mesmo JAR **não** transfere authority semântica para progressão RPG;
- calor, gases, O₂, pressão, poluição, geologia e vulcanismo não se tornam FIRE/LIGHTNING/NATURE por proximidade temática.

Classificação provider→árvore: **BRIDGE / COBERTO POR SISTEMA UNIVERSAL** no nível arquitetural e **PROGRESSÃO NATIVA AUTORITATIVA** para as mecânicas ambientais/geológicas do subsystem. Nenhuma perk deste lote ganha hook novo por essa consolidação.

## 3. Delta do RPG Skill Tree desde o fechamento inicial

O avanço `d4ce3176... -> 5213d068...` contém, entre outros, consolidação/closeout Volcanoes, manutenção de Compêndio/CI e ajustes internos de metadata/investment. A busca fresca na `main` não encontrou implementação dos contracts exigidos pelo corredor LIGHTNING/NATURE:

- `DIRECT_MAGIC_OUTCOME_V1` — ausente;
- `LIGHTNING_CONSUMABLE_STATE_V1` — ausente;
- `DERIVED_DAMAGE_COMPONENT_V1` — ausente;
- `MAGIC_THERMAL_PARCEL_V1` — ausente;
- `NATURE_CONTROL_RECEIPT_V1` — ausente.

A infraestrutura genérica de unlock/investment usada por A0176 continua existente; não criar um segundo resolver Specialist.

Portanto, o delta da `main` **não altera a availability** das dez perks.

## 4. Delta do Enshrouded

Entre o head originalmente auditado `5671114...` e `5fb7e1da...`, o Enshrouded fechou Stage 08.03:

- integração FTB Teams para ownership futuro;
- FTB Chunks/MineColonies para proteção/claims;
- composição central de `ProtectedAreaService`/mutation authority;
- Epic Fight permanece compatibilidade/presença, sem segundo hook/reducer de dano.

Classificação provider→árvore: **NÃO DEVE SER INTEGRADO** como capability LIGHTNING/NATURE. Trata-se de ownership/safety do Enshrouded e não de producer de magia ou progressão elemental do RPG Skill Tree.

## 5. Delta do Black Arcana — Stage 06 Rituals

O Black Arcana avançou de `d8fb667...` para `6b77b5c...` e tornou o Stage 06 Rituals canônico como `IMPLEMENTED / FINAL VALIDATION DEFERRED` para a matriz manual do modpack. O runtime agora possui:

- `ArcanaRitualId` e `RitualActivationId`;
- `RitualActivationGuard`;
- `RitualSessionRegistry` persistente;
- `RitualCompletionLedger` / SavedData com exactly-once completion;
- reserva/commit/refund transacional de componentes;
- `RitualEngine` como pipeline único de ativação/interrupção/conclusão;
- bridge de attunement com Eidolon;
- componentes espirituais tipados de Malum para grand rituals.

### 5.1 Authority

Black Arcana conserva authority sobre identidade, sessão, consumo, conclusão e outcome de seus rituais. O RPG Skill Tree não deve duplicar esses ledgers nem inferir conclusão por presença de altar, duração, partículas ou consumo de item.

Classificação: **PROGRESSÃO NATIVA AUTORITATIVA** para o lifecycle ritual do Black Arcana.

### 5.2 Cobertura na árvore

O catálogo já possui perks ritualísticas genéricas fora deste lote, especialmente A0405 `Disciplina Ritual` e A0588 `Pureza Ritual`. O novo ledger exatamente-once do Black Arcana é semanticamente compatível com uma futura bridge de Ritual Mastery, mas a auditoria atual não encontrou um receipt/API RPG-facing estável já publicado no RPG Skill Tree que transforme conclusão Black Arcana em award canônico.

Decisão provider→árvore:

- cobertura conceitual: **COBERTA POR PERK EXISTENTE / BRIDGE** para a família Ritual Mastery;
- estado implementável hoje: **SEM HOOK SEGURO** para integrar Black Arcana ao award do RPG;
- ação: registrar para ciclo posterior exato de 10 que contenha/revise as perks ritualísticas; não editar A0405/A0588 dentro de A0171–A0180;
- fail-closed: conclusão Black Arcana concede 0 Ritual Mastery do RPG até existir adapter/receipt explícito;
- deduplicação futura: usar a identidade canônica de activation/completion do Black Arcana e impedir que o mesmo ritual seja creditado novamente pela bridge Eidolon/Malum subjacente.

Essa lacuna recebeu disposição explícita e, portanto, não bloqueia o fechamento de A0171–A0180 nem autoriza uma 11ª perk.

### 5.3 Relação com LIGHTNING/NATURE

Stage 06 não publica `DIRECT_MAGIC_OUTCOME_V1`, `LIGHTNING_CONSUMABLE_STATE_V1`, `MAGIC_THERMAL_PARCEL_V1` ou `NATURE_CONTROL_RECEIPT_V1`. Ritual, Arcane Resistance, Corruption Resistance e Strain não são aliases de LIGHTNING/NATURE.

## 6. Provider → árvore para o lote

| Provider/capability | Cobertura A0171–A0180 | Decisão |
|---|---|---|
| NeoForge 1.21.1 `LivingDamageEvent.Pre` | A0172/A0173/A0179/A0180 | boundary único de mitigação server-side |
| Minecraft `DamageTypeTags.IS_LIGHTNING` | A0172/A0173 | classifier LIGHTNING defensivo; não prova magia DIRECT |
| Iron's 3.16.3 `lightning_magic` / `LIGHTNING_MAGIC` | defesa LIGHTNING e identidade futura ofensiva | adapter exato/versionado; identidade sozinha não prova autoria DIRECT |
| Iron's 3.16.3 `nature_magic` / `NATURE_MAGIC` | defesa NATURE e identidade futura ofensiva | adapter exato/versionado; não existe tag vanilla NATURE genérica aprovada |
| Iron's `CHARGED` | A0171 | **inelegível**: self-buff do caster, não state consumível do alvo |
| Iron's RootSpell/RootEntity | A0178 | não promover a receipt NATURE genérica sem adapter explícito |
| Ars Nouveau 5.13.1 / Ars Elemental 0.7.10.1 | futuros producers possíveis | fail-closed até fechar direct outcome/state receipt versionado |
| Cold Sweat 2.4.2 | A0175 | único owner de temperatura corporal; exigir thermal parcel causal antes da mutação |
| Create/Oritech/FE | corredor LIGHTNING | tecnologia não é magia LIGHTNING por tema |
| Volcanoes nativo no RPG | ambiente/geologia | authority própria; nenhum mapping LIGHTNING/NATURE automático |
| Enshrouded Stage 08.03 | ownership/claims/protection | **NÃO DEVE SER INTEGRADO** ao corredor elemental |
| Black Arcana Stage 06 Rituals | lifecycle ritual | **PROGRESSÃO NATIVA AUTORITATIVA**; bridge Ritual Mastery futura `SEM HOOK SEGURO`; nenhuma equivalência LIGHTNING/NATURE |

## 7. Perk → provider/capability

| Perk | Boundary necessário | Estado final Chat 1 |
|---|---|---|
| A0171 Dano de Raio II | `DIRECT_MAGIC_OUTCOME_V1` + `LIGHTNING_CONSUMABLE_STATE_V1` | `UNAVAILABLE_NODE` |
| A0172 Resistência a Raio I | NeoForge Pre + `IS_LIGHTNING` + adapter Iron's | **IMPLEMENTÁVEL** |
| A0173 Resistência a Raio II | mesmo resolver/bucket + vida PRE-impacto | **IMPLEMENTÁVEL** |
| A0174 Imbuimento de Raio | `DIRECT_MAGIC_OUTCOME_V1` + `DERIVED_DAMAGE_COMPONENT_V1` | `UNAVAILABLE_NODE` |
| A0175 Afinidade de Raio | Cold Sweat + `MAGIC_THERMAL_PARCEL_V1` | `UNAVAILABLE_NODE` |
| A0176 Maestria de Raio | unlock/investment canônico + dependency A0175 | `UNAVAILABLE_NODE` transitivo |
| A0177 Dano de Natureza I | `DIRECT_MAGIC_OUTCOME_V1` + classifier NATURE | `UNAVAILABLE_NODE` |
| A0178 Dano de Natureza II | direct outcome + `NATURE_CONTROL_RECEIPT_V1` | `UNAVAILABLE_NODE` |
| A0179 Resistência a Natureza I | NeoForge Pre + adapter Iron's `nature_magic` | **IMPLEMENTÁVEL** |
| A0180 Resistência a Natureza II | mesmo resolver/bucket + vida PRE-impacto | **IMPLEMENTÁVEL** |

## 8. Authorities e deduplicação preservadas

- Cold Sweat continua único owner da temperatura corporal.
- Volcanoes continua owner semântico de suas grandezas ambientais/geológicas mesmo dentro do mesmo artefato RPG.
- RPG Skill Tree modifica o dano defensivo por um único resolver/bucket por família; adapters apenas classificam.
- Provider mágico fornece identidade/state/autoria quando comprovado; não recebe authority sobre progression/gates do RPG.
- Black Arcana conserva lifecycle/transaction de seus rituais; futura bridge de Mastery deve consumir receipt, nunca reexecutar ou inferir o ritual.
- Eidolon/Malum não podem gerar segundo crédito quando o mesmo ritual for executado pelo pipeline Black Arcana.
- Tecnologia permanece tecnologia; FE não é LIGHTNING mágico.

## 9. Fechamento do gate

**PASS — revalidado em 2026-09-02.**

Todas as capabilities novas/alteradas detectadas nos quatro projetos/sistemas receberam disposição explícita. O delta recente não abriu nenhum dos cinco contracts mágicos ausentes e não altera o design das dez perks. A capability ritual nova do Black Arcana foi classificada como progression native autoritativa, com cobertura conceitual por perks ritualísticas existentes e bridge RPG mantida `SEM HOOK SEGURO` até adapter futuro.

Resultado preservado: **6/10 `UNAVAILABLE_NODE`; 4/10 implementáveis.**
