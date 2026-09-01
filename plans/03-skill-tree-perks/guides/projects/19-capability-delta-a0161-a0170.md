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
| Black Arcana | `d8fb667cc5954d5811dacbbef4da1053fa296581` | delta de governança/final validation diferida; D032 é arquitetura planejada, não runtime de perk |

## Disposição de cada mudança detectada

### Enshrouded `bf97ea0… → 5671114…`

Mudança detectada: correção documental da proveniência do estado RED/validação do Stage 08.02. Não foi introduzido novo recurso, resistência, hazard, ação, equipamento, query pública, serviço de gameplay ou progressão utilizável pelas perks deste lote.

**Classificação:** `NÃO DEVE SER INTEGRADO` como nova perk/capability.  
**Ação:** nenhuma nova perk; preservar contracts já conhecidos do Enshrouded.  
**Fail-closed:** documentação de QA não vira provider de gameplay.

### Black Arcana `e573a0e… → d8fb667…`

Mudanças detectadas: D031 define governança para validation diferida e D032 escolhe, para futura Stage 07.06, bounded in-world fields como arquitetura padrão de Forbidden Domains. Nenhuma delas prova runtime novo em `main` para as perks atuais.

**Classificação:** D031 = `NÃO DEVE SER INTEGRADO`; D032 = `SEM HOOK SEGURO` / planejamento futuro, não capability ativa.  
**Ação:** nenhuma nova perk neste lote.  
**Fail-closed:** Arcane Resistance/Corruption Resistance/Strain e futuros Spell Domains não podem ser convertidos em FIRE/ICE/LIGHTNING, temperatura, CHILL ou Specialist gate sem boundary real.

Nenhuma capacidade detectada ficou sem disposição. Portanto os checkpoints deste ciclo podem avançar aos heads frescos acima.

## Provider → árvore relevante ao lote

| Capability/provider | Disposição na árvore |
|---|---|
| Cold Sweat body temperature | A0161/A0168 representam afinidade térmica mágica, mas ficam `SEM HOOK SEGURO` até existir `MAGIC_THERMAL_PARCEL_V1`; Cold Sweat conserva authority |
| Iron's FIRE school / `fire_magic` | coberto pelo corredor FIRE; outcome mágico direto continua `SEM HOOK SEGURO` no RPG sem `DIRECT_MAGIC_OUTCOME_V1` |
| Iron's ICE school / `ice_magic` | coberto por A0163–A0169; A0165/A0166 usam adapter exato para `ice_magic`, enquanto outcome direto ofensivo permanece fail-closed |
| Iron's LIGHTNING school / `lightning_magic` | coberto por A0170 e corredor posterior; outcome direto permanece `SEM HOOK SEGURO` sem producer canônico |
| Ars Nouveau 5.13.1 `cold_snap` | `COBERTA POR PERK EXISTENTE`: o próprio provider registra `COLD_SNAP` em `DamageTypeTags.IS_FREEZING`, portanto A0165/A0166 reconhecem essa fonte pela tag canônica sem adapter redundante |
| Ars/Ars Elemental demais outcomes elementais | somente quando tag semântica aplicável ou adapter versionado causal existir; sem prova, fail-closed |
| NeoForge/Minecraft `LivingDamageEvent.Pre` + `IS_FREEZING` | `COBERTA POR PERK EXISTENTE`: boundary/classifier canônico defensivo ICE de A0165/A0166 |
| Create/Oritech/FE electricity | `NÃO DEVE SER INTEGRADO` automaticamente ao LIGHTNING mágico; tecnologia elétrica conserva semântica própria |
| Volcanoes heat/atmosphere | `PROGRESSÃO/HAZARD NATIVO AUTORITATIVO`; não converter em FIRE magic/temperature parcel por tema |
| Enshrouded Exposure/Flame/Sanctuary/Story | `PROGRESSÃO NATIVA AUTORITATIVA`/bridges apenas em contracts específicos; nenhuma equivalência automática com FIRE/ICE/LIGHTNING |
| Black Arcana Arcane/Corruption Resistance/Strain | `PROGRESSÃO/ESTADO NATIVO AUTORITATIVO`; não converter em resistências elementais ou Specialist gates |

## Perk → provider

| Perk | Estado do contrato |
|---|---|
| A0161 Afinidade de Fogo | `UNAVAILABLE_NODE` — falta `MAGIC_THERMAL_PARCEL_V1`; dependency FIRE também indisponível |
| A0162 Maestria de Fogo | `UNAVAILABLE_NODE` — falta `SPECIALIST_GATE_V1` e dependency closure |
| A0163 Dano de Gelo I | `UNAVAILABLE_NODE` — falta `DIRECT_MAGIC_OUTCOME_V1` |
| A0164 Dano de Gelo II | `UNAVAILABLE_NODE` — faltam `DIRECT_MAGIC_OUTCOME_V1` + `ICE_CONTROL_RECEIPT_V1` |
| A0165 Resistência a Gelo I | **DESIGN IMPLEMENTÁVEL** — NeoForge Pre + `IS_FREEZING`; Ars Cold Snap pela tag; Iron's `ice_magic` por adapter exato |
| A0166 Resistência a Gelo II | **DESIGN IMPLEMENTÁVEL** — mesmo resolver/classifier/bucket de A0165 + health pré-impacto |
| A0167 Imbuimento de Gelo | `UNAVAILABLE_NODE` — faltam `DIRECT_MAGIC_OUTCOME_V1` + `DERIVED_DAMAGE_COMPONENT_V1` |
| A0168 Afinidade de Gelo | `UNAVAILABLE_NODE` — falta `MAGIC_THERMAL_PARCEL_V1` + dependency closure |
| A0169 Maestria de Gelo | `UNAVAILABLE_NODE` — falta `SPECIALIST_GATE_V1` + dependency closure |
| A0170 Dano de Raio I | `UNAVAILABLE_NODE` — falta `DIRECT_MAGIC_OUTCOME_V1` LIGHTNING |

## Invariantes preservados

1. Integração temática não cria hook.
2. Cold Sweat continua autoridade da temperatura corporal.
3. Dano elementar, resistência elementar, afinidade térmica e estados de controle são pipelines distintos.
4. `IS_FREEZING` é classificação defensiva de dano, não prova de magia direta.
5. Black Arcana e Enshrouded mantêm suas resistências/hazards próprios.
6. Volcanoes não vira FIRE provider por conter calor/lava/atmosfera.
7. Eletricidade tecnológica não vira LIGHTNING mágico.
8. Um provider externo só entra quando tag semântica ou versão/hook comprovado sustenta o contrato.
9. Nenhum baseline foi avançado com capability detectada sem disposição explícita.
