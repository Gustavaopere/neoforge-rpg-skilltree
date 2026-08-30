# Auditoria retroativa de integração — A0021–A0030

## Escopo

- **Lote exato:** A0021–A0030, 10 perks consecutivas.
- **Adagas:** A0021–A0024.
- **Martelos:** A0025–A0030.
- **Providers retroauditados:** RPG Skill Tree, Volcanoes, Enshrouded, Black Arcana e Mobstein 5.4.4, além dos providers já canônicos do próprio contrato (Epic Fight/ParCool/Weapons of Miracles quando aplicável).
- **Fora de escopo:** A0031+ e qualquer implementação runtime neste Chat 1.

## Fontes

- Critérios obrigatórios e três guias consolidados do projeto.
- Guia consolidado de projetos próprios.
- Catálogo Mestre no Notion, com fetch fresco individual A0021–A0030.
- Runtime real em `main@0087ef7e513664454b3d54cb70a9c3f24ec46e84`.
- `main`/`plans/STATUS.md` frescos dos projetos próprios.

## Delta provider→árvore

### RPG Skill Tree

- Runtime A0021–A0040 já contém critical resolver, Flow, flank/rear, Dance, Abalo e state machine de Demolição.
- Nenhum Stage planejado foi promovido a hook.
- Stage 12 Bodies ↔ Mobstein continua `SEM HOOK SEGURO`.

### Volcanoes

- `main@7839db6d9b718e1e2becfe8b88e9b3d24282e2ef` mantém o delta de coexistência RNS/prospecção hidrotermal.
- **Disposição:** futuro ciclo de GEOLOGIA/PROSPECÇÃO; `NÃO DEVE SER INTEGRADO` às perks MARTIAL A0021–A0030.

### Enshrouded

- `main@f8d4d54cb5b8f12aa2149568bfaa2e25f00ef5e5`.
- Shroud, Exposure, Madness, Flame, Story/Sanctuary e MagicResistance não são dodge, orientação, stamina, heavy, guarda/postura ou guard-break.
- **Disposição:** boundaries apenas; sem bridge nova neste lote.

### Black Arcana

- `main@73c14ce55ff918bb8a81daeb99a352607ef11064`.
- `ARCANE_BACKLASH` permanece terminal: não crita, não proca, não gera Mastery/Fluxo/Abalo, não abre/consome Dance/Demolição e não satisfaz heavy/guard-break.
- Arcane Resistance/Corruption Resistance/Arcane Strain não são guarda/postura/stamina MARTIAL.

### Mobstein 5.4.4

- Ataques diretos do jogador contra mobs/bosses Mobstein são cobertos pelo sistema universal quando o receipt Epic Fight é válido.
- Allies/bodyguards ressuscitados são Mobstein-owned e não herdam autoria do dono para crítico, Fluxo, Abalo, heavy, guard-break ou Mastery.
- Progressão Attack/Health/Speed/Template continua Mobstein-owned.

## Matriz do lote

| Perk | Resultado | Boundary/pendência principal |
|---|---|---|
| A0021 | APROVADA | critical root action única; Backlash/companions excluídos |
| A0022 | APROVADA / implementação parcial | `P-A0022-01`: perda −2 Fluxo por stagger forte; `P-A0022-02`: fallback geométrico sem `DodgeEvent`; `P-A0022-03`: idle decay sem alvo hostil — todos ainda ausentes/incompletos no adapter |
| A0023 | APROVADA | orientação server-side; sem heurística |
| A0024 | APROVADA | stamina Epic Fight only; fallback omite stamina-only; depende de A0022 não declarar rota geométrica inexistente |
| A0025 | APROVADA após correção | `P-A0025-01`: remover tag HAMMER paralela; `P-A0025-02`: Mastery anti-farm |
| A0026 | APROVADA | cadência Epic Fight; sem mutação Notion necessária |
| A0027 | APROVADA | crítico único; depende da família HAMMER corrigida |
| A0028 | APROVADA / implementação parcial | `P-A0028-01`: guard pressure receipt ausente no adapter |
| A0029 | APROVADA / não confirmada | `P-A0029-01`: heavy receipt ausente; stamina restore é fallback opcional |
| A0030 | APROVADA / não confirmada | `P-A0030-01`: guard-break caller + heavy receipt ausentes |

## Correção sistêmica — Mastery de Martelos

O runtime atual `A0021A0040MasteryPolicy`/`A0021A0040MasteryHooks` concede 3 XP por hit direto confirmado para HAMMER/`epicfight:heavy`. Isso viola o critério obrigatório de Mastery não farmável por dano repetitivo.

Contrato aprovado:

1. apenas hit direto do jogador, provider-native HAMMER/heavy, contra entidade hostil válida;
2. `DiscoveryProgress` persiste tipos hostis já creditados;
3. +10 uma única vez por tipo de entidade hostil inédita;
4. hits repetidos do mesmo tipo = 0 XP;
5. gate 70 = 7 tipos distintos;
6. terminal A0030 mastery 80 = 8 tipos distintos;
7. `ARCANE_BACKLASH` e companions Mobstein não concedem Mastery ao jogador.

## Pendências destinadas ao Chat 2

### P-A0022-01 — strong stagger → −2 Fluxo
- Integrar `ON_STUNNED` provider-native.
- Aceitar somente `LONG`, `KNOCKDOWN`, `NEUTRALIZE` + fonte hostil.
- Dano genérico não substitui.

### P-A0022-02 — fallback geométrico de reposicionamento
- O contrato permite ganho após deslocamento horizontal ≥1,5 blocos + mudança angular ≥60° antes do hit, mesmo sem `DodgeEvent`.
- O adapter atual não arma essa rota quando não há `DodgeEvent`.
- Implementar correlação server-side com o próximo hit direto de adaga sem aceitar câmera, teleport, knockback ou deslocamento sem hit como substitutos.
- A0024 não pode assumir essa rota como disponível até o adapter satisfazê-la.

### P-A0022-03 — idle decay sem alvo hostil vivo
- O contrato exige início de perda de 1 Fluxo/s após 3 s sem deslocamento horizontal relevante.
- Quando não há alvo hostil vivo no `PlayerPatch`, o adapter atual chama `tickFlow(..., false, ...)`, que retorna antes de aplicar o decay; o estado apenas expira em 5/7 s.
- Chat 2 deve remover essa dependência artificial de alvo hostil para o lifecycle de Fluxo, preservando a autoridade server-side.

### P-A0025-01 — classificação HAMMER paralela
- Remover/desativar `rpgskilltree:hammers` para HAMMER.
- Epic Fight classification first; unknown = fail-closed.
- Não interferir por acidente em MACE/SCYTHE A0031+; este lote não redesenha essas famílias.

### P-A0025-02 — Mastery heavy repetitiva
- Substituir o award 3 XP/hit pela política `DiscoveryProgress` acima.

### P-A0028-01 — pressão de guarda
- Adapter atual envia `guardPressureAvailable=false`.
- Integrar somente receipt provider-native seguro; se indisponível, benefício permanece inativo.
- Não converter para dano/impacto/knockback/crítico.

### P-A0029-01 — heavy receipt
- Adapter atual envia `heavyConfirmed=false`, logo A0029 não ativa.
- Integrar receipt inequívoco de heavy attack; nenhuma heurística de animação/dano/arma lenta.
- Sem custo exato de stamina, omitir somente o refund de 10% conforme fallback canônico.

### P-A0030-01 — guard break + heavy
- `A0021A0040CombatPolicy.onConfirmedGuardBreak(...)` existe, mas não foi encontrado caller runtime no adapter.
- Integrar quebra real de guarda/postura causada pela mesma ação HAMMER do jogador + heavy receipt seguro.
- Sem esses receipts, A0030 permanece fail-closed/unconfirmed.

## Alterações no Notion

- **Alteradas e re-fetched:** A0021, A0022, A0023, A0024, A0025, A0027, A0028, A0029 e A0030 = **9/9 PASS** em 2026-08-30.
- **Sem mutação:** A0026; o contrato já estava suficiente e não foi feita alteração cosmética.

## Nove eixos obrigatórios

1. Dependências/gates — PASS.
2. Integração global/recursos — PASS de design; pendências runtime registradas.
3. Identidade — PASS.
4. Ramificação/topologia — PASS.
5. Especializações — PASS; A0024/A0030 continuam terminais exteriores.
6. PT-BR — PASS.
7. Notion + re-fetch — PASS.
8. NeoVitae — PASS.
9. Cobertura provider→árvore — PASS, com fail-closed explícito onde API segura falta.

## 18 critérios técnicos

Resultado: **PASS ou N/A justificado no design**, com implementação bloqueada onde indicado. Foram rechecados: provider/version authority; server authority; causal root action; dedup; classificação provider-native; recursos únicos; crítico único; Mastery anti-farm; gates alcançáveis; topologia; state lifecycle; actor→target isolation; cooldowns; fallback sem mudança de identidade; ausência de heurística frágil; boundaries entre providers; PT-BR; implementabilidade/testabilidade.

## Fechamento

**A0021–A0030 — LOTE FECHADO NO DESIGN.**

O Chat 2 pode implementar/corrigir apenas os contratos acima. A0031+ não foi iniciada.
