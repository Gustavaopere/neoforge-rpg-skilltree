# 15 — Capability Delta — A0091–A0100

Data de reconciliação: 2026-08-31.

Este suplemento promove os baselines dos quatro projetos próprios usados pelo Chat 1 antes e durante a auditoria de A0091–A0100. A análise é por **capability**, não por quantidade de commits: cada mudança posterior ao baseline anterior recebeu disposição explícita antes do fechamento do lote.

## Baselines anteriores

| Projeto | Baseline A0081–A0090 |
|---|---|
| RPG Skill Tree | `6975970d086d32985d83a0018c841cce9d1cbd63` |
| Volcanoes | `eaddc3232dfc600780769f4a5e7e45ff1e50181c` |
| Enshrouded | `391ea82203d30cb392a3397f92e2a3cbe7fb6128` |
| Black Arcana | `710077da89da5eb4418d3ac676e148849727ff07` |

## Freshness de abertura e fechamento

| Projeto | Head auditado/promovido | Delta | Decisão para A0091–A0100 |
|---|---|---|---|
| RPG Skill Tree | `5530667f5303c3f628ea9f69dd947dbfed888915` | Stage 04.02 ganhou provenance persistente de bridge paga, cobrança única e refund exato; depois houve fechamento Chat 3 A0021–A0030 | `PROGRESSÃO NATIVA AUTORITATIVA` para bridges/confluências; fechamento A0021–A0030 sem impacto contratual neste lote |
| Volcanoes | `eaddc3232dfc600780769f4a5e7e45ff1e50181c` | sem avanço desde o baseline anterior | `SEM DELTA` |
| Enshrouded | `6642d4ed14bbae2a771075ca466e6749ac8f7fb8` | fog/render client-side, config e hardening pontual de reload | `SEM DELTA DE GAMEPLAY PARA O LOTE` |
| Black Arcana | `462c5c4af403629a7092129cf7f3070472f03e59` | hardening de Backlash, resistência numérica e exclusão de offensive credit/mastery | `SEM NOVO PROVIDER VITALITY`; reforça exclusões de hazard/custo |

## RPG Skill Tree

O avanço relevante desde `6975970...` é o fechamento do Stage 04.02 de confluências e bridges. A `main` passou a preservar provenance persistente de pagamento de bridges híbridas, cobrar uma única vez e reembolsar exatamente a bridge paga quando reconciliação/respec invalida a classe correspondente.

### Disposição

- Esta capability é **PROGRESSÃO NATIVA AUTORITATIVA**.
- A0093, A0098 e A0099 podem ser nodes-bridge/topológicos, mas **não** criam segundo ledger de custo de confluência.
- Nenhuma dessas perks cobra, persiste ou reembolsa custo de classe/bridge híbrida.
- `ProgressionService`/Stage 04.02 permanece authority exclusiva dessa provenance.
- PP de node-bridge e custo de desbloqueio de classe são grandezas distintas.
- Política de PP bridge do lote continua: por padrão não conta para os dois thresholds puros; whitelist de Specialist pode direcionar para no máximo um.

Durante o fechamento, a `main` avançou de `5098e38...` para `5530667...` com a auditoria/merge Chat 3 de A0021–A0030. Esse avanço corrige lote anterior e não adiciona atributo, classifier, guard-stamina hook, Stun Armor binding, movement receipt, stationary detector ou incoming-critical decomposition para A0091–A0100. Classificação: **SEM DELTA DE CAPABILITY PARA ESTE LOTE**.

## Volcanoes

Não houve avanço desde `eaddc323...`.

Os boundaries continuam os mesmos:

- pressão, gás, lava, calor, geologia e worldgen não são incoming physical attack por analogia;
- hazards ambientais não fornecem `LivingEntity` hostile-attacker receipt;
- nenhum estado de veículo/ambiente do Volcanoes substitui sprint autopropelido ou stationarity do jogador;
- não existe provider de guard stamina, Stun Armor ou critical decomposition para este lote.

Decisão: **SEM DELTA** e **NÃO DEVE SER INTEGRADO** onde a relação seria apenas temática.

## Enshrouded

O avanço até `6642d4e...` concentra-se em Client Experience: estado/renderização de fog, perfil de cor, config e testes, além de pequeno ajuste de ordenação no reload de purificação.

### Disposição

- fog/client presentation não é gameplay authority;
- Shroud/Exposure/Madness permanecem sistemas ambientais/estado próprios e não são classificados como ataque físico hostil por ausência de atacante causal;
- render state/client config não alimentam A0097/A0098/A0099;
- nenhuma nova API server-authoritative de guard stamina, Stun Armor ou incoming critical foi introduzida.

Decisão: **SEM DELTA DE GAMEPLAY PARA A0091–A0100**.

## Black Arcana

O avanço de `710077d...` para `462c5c4...` endurece o Stage 05A:

- testes de ledger de Arcane Backlash;
- stress/dedup concorrente/delayed;
- boundaries numéricos de Arcane/Corruption Resistance;
- testes explícitos de exclusão de offensive credit;
- testes de exclusão de RPG mastery em Backlash.

### Disposição

- `ARCANE_BACKLASH` continua hazard terminal, não ataque físico hostil de `LivingEntity`;
- `BLOOD_MAGIC_COST` continua custo, não incoming mitigable damage;
- Arcane/Corruption Resistance continuam canais próprios e não substituem A0092;
- nenhum desses hardenings fornece critical decomposition para A0100;
- nenhuma alteração fornece guard stamina/recovery hooks para A0093/A0094.

Decisão: **NÃO CRIA NOVO PROVIDER PARA O LOTE**; reforça fail-closed/exclusões existentes.

## Matriz de capacidades detectadas

| Projeto | Capacidade detectada | Estado real | Cobertura/decisão | Ação no lote | Authority/fail-closed |
|---|---|---|---|---|---|
| RPG Skill Tree | provenance de bridge paga + refund exato | CANÔNICO | **PROGRESSÃO NATIVA AUTORITATIVA** | A0093/A0098/A0099 não duplicam ledger; PP bridge separado | Stage04.02/`ProgressionService` |
| RPG Skill Tree | fechamento Chat3 A0021–A0030 | CANÔNICO | **COBERTA NO LOTE ANTERIOR** | nenhuma mudança A0091–A0100 | não reutilizar como atalho |
| Enshrouded | Shroud fog rendering/config | CANÔNICO client-side | **NÃO DEVE SER INTEGRADO** | nenhuma perk recebe authority client-side | client presentation não é gameplay |
| Black Arcana | Backlash/offensive-credit hardening | CANÔNICO/parcial conforme Stage05A | **NÃO DEVE SER INTEGRADO** como dano físico/crit recebido | manter exclusões | hazard terminal; no offensive credit |
| Volcanoes | nenhum delta | — | **SEM DELTA** | nenhuma ação | boundaries anteriores preservados |

## Impacto contratual nas dez perks

- A0091 continua vanilla/NeoForge `knockback_resistance`.
- A0092 precisa materializar seu próprio classifier/tag físico; projetos próprios não oferecem bypass.
- A0093/A0094 permanecem `UNAVAILABLE_NODE` por falta de hook causal de guarda/stamina/recovery.
- A0095 deve usar `epicfight:stun_armor`; projetos próprios não fornecem substituto.
- A0096 reutiliza A0092 e snapshot pre-impact.
- A0097 usa atacante causal `LivingEntity` não aliado e reservation→commit; hazards próprios sem atacante não consomem.
- A0098 usa self-propelled state; veículos/contraptions/hazards não ativam.
- A0099 usa exclusivamente `StationaryStateService`; estado ambiental/client não cria stationarity paralela.
- A0100 permanece `UNAVAILABLE_NODE` sem incoming critical decomposition real.

## Baselines promovidos para o próximo gate

Somente após todas as capacidades acima receberem disposição, os seguintes heads tornam-se os novos checkpoints de comparação:

- RPG Skill Tree: `5530667f5303c3f628ea9f69dd947dbfed888915`
- Volcanoes: `eaddc3232dfc600780769f4a5e7e45ff1e50181c`
- Enshrouded: `6642d4ed14bbae2a771075ca466e6749ac8f7fb8`
- Black Arcana: `462c5c4af403629a7092129cf7f3070472f03e59`

Estes SHAs são apenas baselines documentais. Não promovem planos downstream ou capabilities preparatórias a runtime. No próximo lote, `main` + `plans/STATUS.md` frescos dos quatro projetos voltam a prevalecer.
