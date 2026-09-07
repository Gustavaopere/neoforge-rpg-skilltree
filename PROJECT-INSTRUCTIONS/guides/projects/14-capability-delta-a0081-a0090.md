# 14 — Capability Delta — A0081–A0090

Data de reconciliação: 2026-08-31.

Este suplemento promove os baselines próprios usados pelo Chat 1 antes da primeira perk do lote A0081–A0090. A regra é por **capability**, não por quantidade de commits: toda mudança desde o baseline anterior foi classificada antes de a auditoria avançar.

## Baselines anteriores

| Projeto | Baseline A0071–A0080 |
|---|---|
| RPG Skill Tree | `877120acf4f20a693e971282e8fca35bef72c6e7` |
| Volcanoes | `bbb273d61984e2c9bb84e8f8a56668ae7e315532` |
| Enshrouded | `391ea82203d30cb392a3397f92e2a3cbe7fb6128` |
| Black Arcana | `526d8196087c863e9df64051d5d39d88c3050856` |

## Freshness de abertura

| Projeto | Head auditado | Delta | Decisão para A0081–A0090 |
|---|---|---|---|
| RPG Skill Tree | `d20e7d666b627615f4af26dffb7c794b9a0b0fbd` | documentação narrativa/história após #304 | `SEM DELTA DE CAPABILITY`; não altera perks/runtime/providers |
| Volcanoes | `eaddc3232dfc600780769f4a5e7e45ff1e50181c` | Stage 07 release-readiness, hardening, provenance/licenças e validators | `NÃO DEVE SER INTEGRADO`; nenhuma nova mecânica jogável para sustain/vitality |
| Enshrouded | `391ea82203d30cb392a3397f92e2a3cbe7fb6128` | sem alteração | `SEM DELTA` |
| Black Arcana | `710077da89da5eb4418d3ac676e148849727ff07` | hardening Stage 05A: Backlash/optional-provider snapshot/guardas | `NÃO CRIA SUSTAIN`; reforça exclusão de `ARCANE_BACKLASH` |

## RPG Skill Tree

O delta desde `877120acf...` até `d20e7d666...` é editorial/narrativo. Não adiciona damage receipt, body adapter, lifesteal correlation, magic/element/DoT producer nem atributo novo. Portanto nenhuma capacidade do lote pode ser marcada como implementada por esse delta.

## Volcanoes

O avanço para `eaddc323...` fecha release/hardening do estágio já existente. Hazards/geologia continuam autoridade própria e **não** entram como dano causado pelo jogador em A0082–A0087.

Decisões de exclusão:

- lava/calor/gás/pressão/geologia não geram vampirismo/sifão;
- Cold Sweat permanece o owner do eixo de temperatura corporal de A0087, não Volcanoes;
- nenhuma mudança Stage 07 fornece owner/pulse/root de sustain.

## Enshrouded

Sem delta. Shroud/Exposure/Madness/Flame/Story/MagicResistance continuam sistemas distintos. Shroud/environmental damage não é magia/DoT ofensivo do jogador para A0083–A0087.

## Black Arcana

O delta até `710077da...` endurece Stage 05A e a semântica de Backlash/snapshots. Não transforma Backlash em ataque.

Para o lote:

- `ARCANE_BACKLASH` é terminal/hazard e não ativa A0083/A0084/A0085/A0086/A0087;
- `BLOOD_MAGIC_COST` segue custo, não dano causado;
- nenhum hardening de snapshot é producer de lifesteal/sustain.

## Impacto nos contracts A0081–A0090

- A0081/A0087 continuam condicionadas ao binding corporal de A0075/Cold Sweat.
- A0083/A0084/A0085 continuam sem producer runtime no RPG e exigem unavailable-node até adapters reais.
- A0086 herda availability dos predecessors; projetos próprios não fornecem bypass.
- A0088/A0089/A0090 permanecem owned por atributos vanilla/NeoForge.

## Freshness de fechamento antes do PR

Durante a auditoria e a preparação da PR, a `main` do RPG Skill Tree avançou em quatro frentes concorrentes. Todas foram classificadas antes do fechamento:

1. `d20e7d666b627615f4af26dffb7c794b9a0b0fbd` → `eb073733fbde62190860eb2f739acae9a797c8dc` via PR #300: exclusivamente o lote editorial TFC de arbustos frutíferos do Compêndio (`plans/10-compendio-natural/10-10-corpus-lotes.md`, `berries-batch4.json` e JUnit correspondente).
2. `eb073733fbde62190860eb2f739acae9a797c8dc` → `bc8b3d571b1a3cc85a21b7b206543a47c9a8eab4` via PR #306: narrative continuity auditor (`historia/`, `scripts/narrative_auditor.py`, teste e workflow), sem alteração de árvore, runtime de perks, sustain, atributos ou providers A0081–A0090.
3. `bc8b3d571b1a3cc85a21b7b206543a47c9a8eab4` → `54221183b61a43d5b5bb9494ddc3b98f6ee702b0` via PR #307: adiciona availability/gating server-side de especializações por provider/adapter. É uma capability arquitetural real, mas não adiciona damage receipt, `BodyProvider`, correlação de lifesteal, producer de magia/elemento/DoT ou atributo consumido por A0081–A0090. Portanto não altera os contratos deste lote nem autoriza bypass de seus fail-closed.
4. `54221183b61a43d5b5bb9494ddc3b98f6ee702b0` → `6975970d086d32985d83a0018c841cce9d1cbd63` via PR #311: corpus editorial/revisado do Compêndio TFC para pomar/acácia e testes de corpus, sem alteração de perks, sustain, providers ou contratos A0081–A0090.

**Decisão:** #300, #306 e #311 são `SEM DELTA DE CAPABILITY PARA O LOTE`. #307 é `DELTA ARQUITETURAL RELEVANTE, SEM IMPACTO CONTRATUAL NESTE LOTE`: melhora o gateway de availability de especializações, mas não implementa nenhum producer/binding requerido pelas dez perks. Nenhum dos quatro avanços cria uma 11ª perk, torna node fail-closed comprável ou transforma hazard/custo em dano ofensivo do jogador.

## Baselines promovidos para o próximo gate

Após esta reconciliação, os heads abaixo são os novos baselines documentais do Chat 1 para detectar **mudanças posteriores**. Eles não significam que capabilities planejadas ou preparatórias foram promovidas a runtime.

- RPG Skill Tree: `6975970d086d32985d83a0018c841cce9d1cbd63`
- Volcanoes: `eaddc3232dfc600780769f4a5e7e45ff1e50181c`
- Enshrouded: `391ea82203d30cb392a3397f92e2a3cbe7fb6128`
- Black Arcana: `710077da89da5eb4418d3ac676e148849727ff07`

O gate de delta A0081–A0090 fica fechado para abertura/atualização da PR. Se a `main` avançar novamente antes do merge, o novo delta deve ser classificado antes do fechamento operacional.