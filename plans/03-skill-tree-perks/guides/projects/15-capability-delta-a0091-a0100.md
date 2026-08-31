# 15 — Capability Delta — A0091–A0100

Data de reconciliação: 2026-08-31.

Este suplemento executa o gate obrigatório de delta antes do fechamento do lote A0091–A0100. A comparação é por **capability real**, não por número de commits.

## Baselines promovidos pelo lote anterior

| Projeto | Baseline A0081–A0090 |
|---|---|
| RPG Skill Tree | `6975970d086d32985d83a0018c841cce9d1cbd63` |
| Volcanoes | `eaddc3232dfc600780769f4a5e7e45ff1e50181c` |
| Enshrouded | `391ea82203d30cb392a3397f92e2a3cbe7fb6128` |
| Black Arcana | `710077da89da5eb4418d3ac676e148849727ff07` |

## Heads auditados

| Projeto | Head | Delta classificado | Decisão para A0091–A0100 |
|---|---|---|---|
| RPG Skill Tree | `5098e38cbfb0e90d788de0722dd7e2f68753261d` | fechamento documental A0081–A0090 + Stage 04.02 confluences/bridges | `DELTA ARQUITETURAL RELEVANTE`; governa provenance/custo/PP de bridges, sem criar hooks defensivos novos |
| Volcanoes | `eaddc3232dfc600780769f4a5e7e45ff1e50181c` | sem delta | `SEM DELTA` |
| Enshrouded | `6642d4ed14bbae2a771075ca466e6749ac8f7fb8` | Stage 07.02 fog/render state/client config + hardening de sentinel | `NÃO DEVE SER INTEGRADO` às perks defensivas; capability é visual/client-side |
| Black Arcana | `462c5c4af403629a7092129cf7f3070472f03e59` | hardening/testes de Backlash, Arcane Resistance e exclusão de offensive credit | `REFORÇA EXCLUSÕES`; não transforma Backlash em dano físico/hostil/crit recebido elegível |

## RPG Skill Tree

O delta `6975970d...→5098e38c...` contém duas classes relevantes:

1. fechamento documental do lote A0081–A0090, sem runtime novo para A0091–A0100;
2. Stage 04.02 de confluences/bridges, com provenance persistida do pagamento, custo data-driven, revogação/reembolso exatos após respec e UI de requisitos.

Impacto no lote:

- A0093/A0094/A0098/A0099 são Bridge Nodes/topologias mistas, mas **não** podem confundir pontos da perk com o pagamento de confluência de classe;
- a `BRIDGE_PP_POLICY` continua: por padrão pontos de bridge não contam para thresholds puros; Specialist pode whitelistar no máximo um domínio, nunca ambos;
- o Stage 04.02 não fornece guard-cost receipt, guard-break recovery, ParCool movement receipt, forced-transition receipt ou incoming critical decomposition.

## Volcanoes

Sem delta desde `eaddc323...`. Hazards, geologia, gases, lava e pressão continuam fora das classificações de dano hostil físico/crit recebido do lote, salvo futuro adapter causal explícito. Volcanoes não é owner de Knockback Resistance, Stun Armor, guard stamina ou StationaryStateService.

## Enshrouded

O compare `391ea822...→6642d4ed...` toca apenas client experience/fog rendering, incluindo `ShroudColorProfile`, `ShroudFogController`, `ShroudRenderState`, config client-side e testes correspondentes. Não adiciona DamageSource defensivo, guard receipt, locomotion authority, critical decomposition ou Stun Armor.

Decisão: `SEM CAPABILITY CONSUMÍVEL POR A0091–A0100`.

## Black Arcana

O compare `710077da...→462c5c4a...` adiciona principalmente hardening e testes de `ARCANE_BACKLASH`, Arcane Resistance, stress e exclusão explícita de offensive credit/RPG mastery.

Impacto no lote:

- `ARCANE_BACKLASH` continua hazard terminal/self-cost semantics; não reinicia/consome A0097 como dano hostil;
- não é dano físico para A0092/A0096;
- não é crítico recebido decomposto para A0100;
- Arcane Resistance permanece eixo mágico próprio e não substitui Armor/Toughness/Stun Armor.

## Provider→árvore — conclusão

- **A0091:** Minecraft/NeoForge Knockback Resistance; Epic Fight compõe nativamente.
- **A0092/A0096:** tag física governada + adapters explícitos; projetos próprios não adicionam fonte física neste delta.
- **A0093/A0094:** nenhum projeto próprio fornece guard stamina/recovery receipt.
- **A0095:** Epic Fight `epicfight:stun_armor` é o provider-native positivo; nenhum projeto próprio deve duplicá-lo.
- **A0097:** atacante hostil causal; Backlash/environmental hazards ficam fora.
- **A0098/A0099:** movimento/estacionário pertencem ao player runtime; Enshrouded fog e Volcanoes não são movement authorities.
- **A0100:** nenhum projeto próprio fornece incoming critical decomposition.

## Baselines promovidos para o próximo gate

- RPG Skill Tree: `5098e38cbfb0e90d788de0722dd7e2f68753261d`
- Volcanoes: `eaddc3232dfc600780769f4a5e7e45ff1e50181c`
- Enshrouded: `6642d4ed14bbae2a771075ca466e6749ac8f7fb8`
- Black Arcana: `462c5c4af403629a7092129cf7f3070472f03e59`

Esses heads são baselines documentais de freshness, não aprovação automática de capabilities futuras. Se qualquer `main` avançar antes do fechamento da PR, o delta adicional deve ser classificado antes de declarar o lote pronto para handoff.