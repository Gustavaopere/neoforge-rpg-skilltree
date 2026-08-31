# AUDITORIA — CHAT 1 — A0091–A0100

Data: 2026-08-31  
Escopo: **exatamente 10 perks consecutivas, A0091–A0100**.  
Responsabilidade: auditoria/design; nenhum runtime alterado neste Chat 1. O merge pertence ao Chat 3.

## 1. Fontes obrigatórias

Foram aplicados integralmente `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`, `CHAT-1-AUDITORIA-DESIGN-PERKS-ANEXOS-PROJETO.md`, os três guias consolidados de Gameplay/Sistemas, Magia e Tecnologia e o guia de Projetos Próprios.

A cobertura foi feita nos dois sentidos:

1. `perk → provider`: identificar toda authority pertinente ao contrato;
2. `provider → árvore`: percorrer as capacidades reais dos guias e projetos próprios para verificar se alguma deveria integrar o lote.

Provider sem relação causal foi marcado N/A/excluído; provider pertinente sem hook seguro resulta em fail-closed/unavailable, nunca heurística.

## 2. Determinação do lote

O catálogo Notion confirmou exatamente os 10 registros consecutivos A0091–A0100 ainda sem fechamento formal do Chat 1.

- **INÍCIO:** A0091.
- **FIM:** A0100.
- **Quantidade:** 10.

A implementação histórica da PR #168 (`feat: implement Notion perks A0081-A0100`) foi tratada como evidência runtime, não como aprovação de design. Código presente, fórmula pura e implementação confirmada são estados distintos.

## 3. Gate de delta dos projetos próprios

Baseline promovido pelo lote anterior:

- RPG Skill Tree `6975970d086d32985d83a0018c841cce9d1cbd63`;
- Volcanoes `eaddc3232dfc600780769f4a5e7e45ff1e50181c`;
- Enshrouded `391ea82203d30cb392a3397f92e2a3cbe7fb6128`;
- Black Arcana `710077da89da5eb4418d3ac676e148849727ff07`.

Heads auditados:

- RPG `5098e38cbfb0e90d788de0722dd7e2f68753261d` — Stage 04.02 confluences/bridges é delta arquitetural real, relevante para provenance/custo/PP das bridges, mas não cria guard/movement/crit receipts.
- Volcanoes `eaddc323...` — sem delta.
- Enshrouded `6642d4ed...` — Stage 07.02 fog/rendering client-side, sem capability defensiva consumível.
- Black Arcana `462c5c4a...` — hardening/testes de Backlash/Arcane Resistance e exclusão de offensive credit; reforça exclusões do lote.

Delta detalhado: `guides/projects/15-capability-delta-a0091-a0100.md`.

## 4. Resultado por perk

| Código | Perk | Design | Estado runtime auditado | Decisão principal |
|---|---|---|---|---|
| A0091 | Base Firme | APROVADO | binding data-driven presente | `KNOCKBACK_RESISTANCE` +0,03/rank; não confundir com stun/Impact |
| A0092 | Resistência Física | APROVADO após hardening | hook físico presente | tag `rpgskilltree:physical` é authority; modded desconhecido fail-closed |
| A0093 | Guarda Econômica | APROVADO EM FAIL-CLOSED | fórmula pura; bridge runtime explicitamente fechado | `UNAVAILABLE_NODE` até adapter causal de custo real de guarda |
| A0094 | Recuperação de Guarda | APROVADO EM FAIL-CLOSED | fórmula pura; bridge runtime explicitamente fechado | `UNAVAILABLE_NODE` transitivo A0093 + ausência GUARD_BREAK/recovery hook |
| A0095 | Tenacidade | APROVADO APÓS REDESIGN | runtime atual usa reducer paralelo não conectado | migrar para `epicfight:stun_armor` +0,25/rank; remover dependência A0094 |
| A0096 | Último Fôlego | APROVADO após hardening | branch já presente em physical damage | snapshot pré-impacto <30% + físico hostil causal |
| A0097 | Primeira Defesa | APROVADO após hardening | state/window presentes; hostilidade estreita | remover `Enemy || Player` como authority; usar atacante causal não aliado |
| A0098 | Defesa em Movimento | APROVADO | fallback sprint vanilla presente | `player.isSprinting()` válido; ParCool apenas por adapter real |
| A0099 | Defesa Estacionária | APROVADO após hardening | detector compartilhado presente | fechar forced-transition lifecycle sem segundo detector |
| A0100 | Anti-Crítico | APROVADO EM FAIL-CLOSED | fórmula pura; nenhum caller incoming | `UNAVAILABLE_NODE` até critical receipt decomposto base+parcela crítica |

## 5. Findings estruturais

### 5.1 A0095 — reducer paralelo substituído por provider-native

Epic Fight 21.17.3.1 registra `STUN_ARMOR` como atributo próprio e deriva o baseline de stun reduction da própria grandeza. O design anterior de A0095 criava `interruptionMultiplier` linear, paralelizando semântica que já possui owner real.

Correção:

- efeito canônico: +0,25 `epicfight:stun_armor`/rank, até +1,25;
- binding data-driven por `AttributeNodeEffectRuntime`, `ADD_FLAT`;
- remover A0094 como predecessor obrigatório; A0091≥2 permanece;
- Epic Fight ausente/incompatível → `UNAVAILABLE_NODE`.

### 5.2 A0093/A0094 — fórmula não é binding

O código do Epic Fight prova internamente custo de guarda `penalty × impact` e diferencia `GUARD_BREAK`, mas não foi comprovado boundary público/suportado para modificar o débito antes de `consumeForSkill` nem recovery pós-break.

Portanto:

- A0093 e A0094 são nodes indisponíveis/não compráveis;
- refund pós-debito, estimativa por animação/impact e cooldown inventado são proibidos;
- availability transitiva é obrigatória.

### 5.3 A0100 — crítico ofensivo não prova crítico recebido

O RPG possui resolvedor crítico canônico para ataques do jogador, mas A0100 requer um evento recebido que exponha `critical=true`, `baseDamage` e `additionalCriticalDamage` no mesmo root. Essa decomposição não existe no bridge auditado.

A0100 permanece `UNAVAILABLE_NODE`; inferir parcela crítica pelo dano final ou critical attributes é proibido.

### 5.4 A0097 — hostilidade causal

O runtime atual usa `Enemy || Player`, o que é estreito para mobs modded. O contrato aprovado usa atacante `LivingEntity` causal, diferente do jogador e não aliado. Environment/self/resource costs ficam fora.

### 5.5 A0092/A0096 — classificação física governada

A tag `rpgskilltree:physical` contém hoje tipos físicos vanilla explícitos. DamageTypes modded não são considerados físicos por namespace, arma equipada ou aparência. Adapters futuros devem convergir para o mesmo classificador.

## 6. Notion

Fetch fresco: **10/10**.

Páginas mutadas neste ciclo: **7/10**:

- A0092;
- A0093;
- A0094;
- A0095;
- A0096;
- A0097;
- A0100.

Re-fetch pós-escrita confirmado para as páginas críticas mutadas, incluindo A0092, A0093, A0094, A0095, A0096, A0097 e A0100.

Sem mutação funcional: A0091, A0098, A0099.

Correções principais:

- authority física explícita para A0092/A0096;
- unavailable-node estrutural A0093/A0094/A0100;
- redesign A0095 para Stun Armor provider-native;
- definição causal de hostilidade A0097;
- bridge governance reconciliada com Stage 04.02.

## 7. Provider coverage — Gameplay/Sistemas

### Minecraft/NeoForge

- A0091: owner canônico de Knockback Resistance.
- A0092/A0096: owner do evento e dos DamageTypes vanilla classificados.
- A0097: attacker causal no DamageSource.
- A0098: sprint server-side válido.
- A0099: posição/lifecycle alimentam o detector RPG-owned.

### Epic Fight 21.17.3.1

- A0091: compõe Knockback Resistance vanilla em knockback provider-native.
- A0093/A0094: authority de guarda/stamina/break, porém sem extension point suportado auditado; nodes unavailable.
- A0095: owner positivo de `epicfight:stun_armor` e stun reduction.
- A0098/A0099: contexto de combate, não authority universal de movimento/posição.
- A0100: não foi comprovado incoming critical decomposition.

### ParCool 4.0.0.3 / Epic ParCool 21.0.0

Pertinentes apenas a A0098 quando um adapter expuser estado mecânico server-authoritative de movimento autopropelido. Animação/câmera/delta de posição isolado não basta.

### Apothic / Pufferfish's Attributes

- podem compor atributos/crit upstream;
- não são incoming critical providers presumidos para A0100;
- nenhum atributo de crit chance/damage substitui decomposição causal do dano recebido.

## 8. Provider coverage — Magia

Nenhum mod mágico é owner positivo de A0091–A0100 neste lote.

- magic/arcane/shroud resistance não substitui Armor, Toughness, physical resistance ou Stun Armor;
- spell knockback pode ser mitigado por Knockback Resistance quando o provider usa a grandeza vanilla, sem integração especial da perk;
- `ARCANE_BACKLASH`/`BLOOD_MAGIC_COST` não são hostilidade causal A0097 nem físico A0092/A0096;
- crítico mágico recebido não ativa A0100 sem decomposição explícita.

## 9. Provider coverage — Tecnologia

Nenhum mod tecnológico é owner positivo do lote.

- belts, contraptions, veículos e máquinas não ativam A0098 como movimento autopropelido;
- displacement externo deve invalidar/reiniciar A0099 quando identificado;
- machine damage não recebe classificação física/critical por autoria indireta do jogador.

## 10. Projetos próprios

- **Volcanoes:** sem delta; hazards continuam fora.
- **Enshrouded:** fog/render state client-side não cria hook defensivo.
- **Black Arcana:** hardening atual reforça Backlash como terminal/hazard e exclusão de mastery/offensive credit.
- **RPG Stage 04.02:** authority de provenance/custo/reembolso das confluências; Bridge Nodes A0093/A0094/A0098/A0099 não podem duplicar PP/custo entre domínios.

## 11. Pendências destinadas ao Chat 2

1. `P-A0091-01/-02` — modifier uniqueness/lifecycle/cap +0,15 e separação de Stun Armor/Impact.
2. `P-A0092-01/-02` — tag física, modded unknown fail-closed, composição única e lifecycle.
3. `P-A0093-01` **BLOQUEANTE** — unavailable-node no purchase/gate.
4. `P-A0093-02/-03` — futuro debit modifier pré-consumo; proibir refund/heurística; bridge PP/testes.
5. `P-A0094-01` **BLOQUEANTE** — unavailable transitivo de A0093.
6. `P-A0094-02/-03` — GUARD_BREAK + recovery extension point causal; regressões/lifecycle.
7. `P-A0095-01` **BLOQUEANTE DE CONFORMIDADE** — migrar para `epicfight:stun_armor` +0,25/rank.
8. `P-A0095-02` — remover dependência A0094 no catálogo/runtime e exigir A0091≥2 + provider compatível.
9. `P-A0095-03/-04` — availability/lifecycle e regressões contra Knockback/Armor/Toughness/Impact/guard.
10. `P-A0096-01/-03` — bordas do 30%, snapshot pré-impacto, hostilidade, classificação física, dedup/lifecycle.
11. `P-A0097-01` **BLOQUEANTE DE CONFORMIDADE** — classificador causal em vez de `Enemy || Player`.
12. `P-A0097-02/-03` — consumo único, PvE/PvP/modded/environment/self-cost e lifecycle.
13. `P-A0098-01/-03` — sprint/forced movement/ParCool adapter e bridge PP Stage 04.02.
14. `P-A0099-01` — forced-transition invalidation compartilhada com A0079.
15. `P-A0099-02/-03` — thresholds/lifecycle/multiplayer e bridge PP.
16. `P-A0100-01` **BLOQUEANTE** — unavailable-node sem incoming critical decomposition.
17. `P-A0100-02/-03` — futuro receipt `critical+base+additional`, single application e testes negativos de heurística.
18. `P-A0091-100-TEST-01` — harness/GameTests transversal provider-present/absent, attributes, physical classification, guard availability, Stun Armor, low-health snapshot, hostile opener, movement/stationary, incoming crit fail-closed, lifecycle, multiplayer e dedicated server.

## 12. Nove eixos / 18 critérios

Todos os 10 dossiês possuem os nove eixos individualizados. Resultado: **PASS no design**, usando `UNAVAILABLE_NODE` como saída correta quando o provider não expõe binding obrigatório.

Nenhuma perk foi aprovada por matemática isolada, nome de classe, animação, namespace, presença de mod ou inferência estatística.

## 13. Encerramento do design

O lote A0091–A0100 está suficientemente especificado para o Chat 2 implementar sem redesign.

Este Chat 1:

- não alterou runtime;
- não fará merge;
- deve deixar a PR pronta para implementação/handoff;
- o merge é responsabilidade do Chat 3 conforme instrução atual do projeto.

Após a PR deste design estar aberta e consistente, o Chat 1 deve parar e não iniciar A0101+ automaticamente.
