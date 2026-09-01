# Capability delta — A0161–A0170

**Data:** 2026-09-01  
**Lote Chat 1:** A0161–A0170  
**RPG base da branch:** `0be05cb9cee8c34ff5ceb9091d2b5cb5d4c55328`

## Gate obrigatório de delta

Antes do fechamento do lote, foram consultadas as `main` e `plans/STATUS.md` frescas dos quatro projetos próprios. O checkpoint operacional imediatamente anterior desta cadeia de Chat 1 era:

| Projeto | Checkpoint predecessor da cadeia |
|---|---|
| RPG Skill Tree | `0be05cb9cee8c34ff5ceb9091d2b5cb5d4c55328` |
| Volcanoes | `eaddc3232dfc600780769f4a5e7e45ff1e50181c` |
| Enshrouded | `bf97ea0eba64b3d24e02936b9f3fe384b833ed0a` |
| Black Arcana | `e573a0edfcb69d09e423b60ad75ab71b9d8e70c5` |

Heads frescos observados para A0161–A0170:

| Projeto | Head fresco | Resultado do delta |
|---|---|---|
| RPG Skill Tree | `0be05cb9cee8c34ff5ceb9091d2b5cb5d4c55328` | **SEM DELTA RELEVANTE**; nenhuma capability nova entre checkpoints |
| Volcanoes | `eaddc3232dfc600780769f4a5e7e45ff1e50181c` | **SEM DELTA RELEVANTE**; nenhuma capability nova entre checkpoints |
| Enshrouded | `5671114c361be8cbb6fd2dadafdaa05f27d1fe2c` | delta documental de proveniência do RED do Stage 08.02; nenhuma nova capability jogável |
| Black Arcana | `d8fb667cc5954d5811dacbbef4da1053fa296581` | delta documental sobre sequência de final validation diferida; nenhuma nova capability jogável |

## Disposição de cada mudança detectada

### Enshrouded `bf97ea0… → 5671114…`

Mudança detectada: correção documental da proveniência do estado RED/validação do Stage 08.02. Não foi introduzido novo recurso, resistência, hazard, ação, equipamento, query pública, serviço de gameplay ou progressão utilizável pelas perks deste lote.

**Classificação:** `NÃO DEVE SER INTEGRADO` como nova perk/capability.  
**Ação:** nenhuma nova perk; preservar contracts já conhecidos do Enshrouded.  
**Fail-closed:** qualquer integração futura continua exigindo hook real e authority do Enshrouded; documentação de QA não vira provider de gameplay.

### Black Arcana `e573a0e… → d8fb667…`

Mudança detectada: documentação da sequência canônica de implementação/final validation diferida. Os contracts server-side já congelados de Arcane Resistance, Corruption Resistance, Strain e danger continuam com a mesma autoridade; não surgiu novo elemento FIRE/ICE/LIGHTNING do RPG Skill Tree.

**Classificação:** `NÃO DEVE SER INTEGRADO` como nova perk/capability.  
**Ação:** nenhuma nova perk.  
**Fail-closed:** Arcane Resistance/Corruption Resistance/Strain não podem ser convertidos em FIRE/ICE/LIGHTNING, temperatura, CHILL ou Specialist gate.

Nenhuma capacidade detectada ficou sem disposição. Portanto os checkpoints deste ciclo podem avançar, para fins da cadeia operacional do Chat 1, aos heads frescos acima.

## Provider → árvore relevante ao lote

| Capability/provider | Disposição na árvore |
|---|---|
| Cold Sweat body temperature | A0161/A0168 representam afinidade térmica mágica, mas estão `SEM HOOK SEGURO` até existir `MAGIC_THERMAL_PARCEL_V1`; Cold Sweat conserva authority |
| Iron's FIRE school / `fire_magic` | coberto pelo corredor FIRE; outcome mágico direto continua `SEM HOOK SEGURO` no RPG sem `DIRECT_MAGIC_OUTCOME_V1` |
| Iron's ICE school / `ice_magic` | coberto por A0163–A0169; classificação defensiva ICE é utilizável em A0165/A0166, enquanto outcome direto ofensivo permanece fail-closed |
| Iron's LIGHTNING school / `lightning_magic` | coberto por A0170 e corredor posterior; outcome direto permanece `SEM HOOK SEGURO` sem producer canônico |
| Ars Nouveau / Ars Elemental elemental outcomes | cobertos conceitualmente pelos corredores elementais; cada provider exige adapter versionado e causal; sem adapter não há fallback genérico |
| NeoForge/Minecraft `LivingDamageEvent.Pre` + `IS_FREEZING` | `COBERTA POR PERK EXISTENTE`: A0165/A0166 podem usar o boundary canônico defensivo ICE |
| Create/Oritech/FE electricity | `NÃO DEVE SER INTEGRADO` automaticamente ao LIGHTNING mágico; tecnologia elétrica conserva semântica própria |
| Volcanoes heat/atmosphere | `PROGRESSÃO/HAZARD NATIVO AUTORITATIVO`; não converter em FIRE magic/temperature parcel por tema |
| Enshrouded Exposure/Flame/Sanctuary/Story | `PROGRESSÃO NATIVA AUTORITATIVA`/bridges somente onde contratos específicos existirem; nenhuma equivalência automática com FIRE/ICE/LIGHTNING |
| Black Arcana Arcane/Corruption Resistance/Strain | `PROGRESSÃO/ESTADO NATIVO AUTORITATIVO`; não converter em resistências elementais ou Specialist gates |

## Perk → provider

| Perk | Estado do contrato |
|---|---|
| A0161 Afinidade de Fogo | `UNAVAILABLE_NODE` — falta `MAGIC_THERMAL_PARCEL_V1`; dependency FIRE também indisponível |
| A0162 Maestria de Fogo | `UNAVAILABLE_NODE` — falta `SPECIALIST_GATE_V1` e dependency closure |
| A0163 Dano de Gelo I | `UNAVAILABLE_NODE` — falta `DIRECT_MAGIC_OUTCOME_V1` |
| A0164 Dano de Gelo II | `UNAVAILABLE_NODE` — faltam `DIRECT_MAGIC_OUTCOME_V1` + `ICE_CONTROL_RECEIPT_V1` |
| A0165 Resistência a Gelo I | **DESIGN IMPLEMENTÁVEL** — NeoForge pre-damage + classifier ICE seguro |
| A0166 Resistência a Gelo II | **DESIGN IMPLEMENTÁVEL** — mesmo resolver/bucket de A0165 + health pré-impacto |
| A0167 Imbuimento de Gelo | `UNAVAILABLE_NODE` — faltam `DIRECT_MAGIC_OUTCOME_V1` + `DERIVED_DAMAGE_COMPONENT_V1` |
| A0168 Afinidade de Gelo | `UNAVAILABLE_NODE` — falta `MAGIC_THERMAL_PARCEL_V1` + dependency closure |
| A0169 Maestria de Gelo | `UNAVAILABLE_NODE` — falta `SPECIALIST_GATE_V1` + dependency closure |
| A0170 Dano de Raio I | `UNAVAILABLE_NODE` — falta `DIRECT_MAGIC_OUTCOME_V1` LIGHTNING |

## Invariantes preservados

1. Integração temática não cria hook.
2. Cold Sweat continua autoridade da temperatura corporal.
3. Dano elementar, resistência elementar, afinidade térmica e estados de controle são pipelines distintos.
4. Black Arcana e Enshrouded mantêm suas resistências/hazards próprios.
5. Volcanoes não vira FIRE provider por conter calor/lava/atmosfera.
6. Eletricidade tecnológica não vira LIGHTNING mágico.
7. Um provider externo só entra quando a versão instalada possui adapter/hook comprovado.
8. Nenhum baseline foi avançado com capability detectada sem disposição explícita.
