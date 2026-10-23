# AUDITORIA — CHAT 1 — A0320–A0329

Data/freshness: 2026-09-05.

Escopo: **exatamente 10 perks consecutivas, A0320–A0329**.

Responsabilidade: auditoria, correção de design, integração documental e handoff. Nenhum runtime de perk ou bateria final de testes pertence a este Chat 1; Chat 1 não faz merge.

## 1. Determinação do lote

O lote anterior A0310–A0319 está formalmente fechado pelo Chat 1 na PR #406, branch `docs/chat1-a0310-a0319-audit`, com dez dossiês e tracker próprio. Conforme o protocolo do projeto, Chat 1 para após abrir a PR e não faz merge; o usuário autorizou novo ciclo com `PROSSIGA`.

Busca de branch/conteúdo não encontrou ciclo A0320 já iniciado antes deste trabalho. Portanto:

- **INÍCIO: A0320**
- **FIM: A0329**

A0330+ permanece fora do escopo.

## 2. Fontes obrigatórias e modlist

Foram cruzados integralmente os snapshots consolidados obrigatórios do projeto:

- `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`;
- `GUIA-COMPLETO-GAMEPLAY-E-SISTEMAS.md`;
- `GUIA-COMPLETO-MODS-DE-MAGIA.md`;
- `GUIA-COMPLETO-MODS-DE-TECNOLOGIA.md`;
- `GUIA-COMPLETO-PROJETOS-PROPRIOS.md`;
- protocolo `CHAT-1-AUDITORIA-DESIGN-PERKS-ANEXOS-PROJETO.md`.

A modlist foi conferida antes da auditoria na File Library (`modlist.txt`, 2026-08-30) e no Notion/Auditoria Mestre. A modlist real permanece a authority de presença/versão quando snapshots históricos divergem.

Providers relevantes confirmados no recorte:

- Epic Fight `21.17.3.1`;
- ParCool `4.0.0.3`;
- Epic ParCool `21.0.0`;
- Pufferfish Attributes `0.8.3` quando citado para boundary negativo de accuracy;
- Sable `2.0.5`/Create Aeronautics para regras de frame/sublevel;
- MineColonies `1.1.1375-1.21.1-snapshot` e Iron's `3.16.3` na bridge Battle Mage nova, sem authority positiva para player AGILITY;
- Mobstein `5.4.4` sem authority legítima para estas perks AGILITY.

## 3. Notion

- Fetch inicial das perks: **10/10 PASS**.
- Páginas alteradas neste ciclo: **5/10** — A0321, A0323, A0324, A0326 e A0328.
- Re-fetch pós-escrita: **5/5 PASS** em 2026-09-05.
- Sem alteração de efeito, custo, ranks ou topologia nas correções abaixo.

### A0321 — correção de infrastructure assumption

O efeito +4% `SWIM_SPEED`/rank permanece. Foi removida dependência documental de runtime/helper genérico não existente. A `main` auditada não possui `AttributeNodeEffectRuntime`; Chat 2 deve reconciliar localmente um único `AttributeModifier` transitório estável no lifecycle do node.

### A0323 — correção de dedup assumption

`LivingDamageEvent.Pre` + `DamageTypes.FALL` continuam o boundary implementável. O Notion não presume mais serviço genérico de claim/outcome existente; aplicação única é responsabilidade explícita do adapter A0323 no boundary real.

### A0324 — correção de debit/dedup assumption

Epic Fight `SkillConsumeEvent` permanece precommit provider-native seguro para DODGE/STAMINA. O próprio evento é o boundary primário; a `main` não possui serviço genérico de `debit claim`. Identidade adicional só é necessária se bridges futuras convergirem no mesmo debit/pool.

### A0326 — correção factual crítica

A referência a `A0001A0020CriticalService` era documentalmente obsoleta. A inspeção direta da `main` confirmou que essa classe/serviço **não existe**. A0326 continua `UNAVAILABLE_NODE` por falta do snapshot causal de movimento no RELEASE/LAUNCH e de decomposition provider-native de movement accuracy. Crítico futuro deve compor com a única lane concreta do provider, sem criar segunda rolagem e sem criar serviço apenas para satisfazer nome antigo.

### A0328 — correção de infrastructure assumption

`Attributes.MOVEMENT_SPEED` continua primitive válida para a projeção futura. A `main` não possui `AttributeNodeEffectRuntime`/helper genérico; além disso permanecem ausentes `VOLUNTARY_MOVEMENT_CONTEXT_V1` e `MOMENTUM_LEDGER_V1`. Portanto o node continua unavailable.

## 4. Dependency closure e availability

### A0320 — Salto Econômico

Preserva duas lanes independentes: STAMINA −3%/rank e METABOLIC −2%/rank somente sobre custo causal real de JUMP. Nenhuma lane possui precommit server-authoritative comprovado no snapshot. **`UNAVAILABLE_NODE`**.

### A0321 — Nadador

NeoForge `SWIM_SPEED` + `LivingEntity.isSwimming()` fornecem boundary suficiente. **IMPLEMENTÁVEL**.

### A0322 — Escalador

ParCool possui ações de climb, mas não foi comprovado `CLIMB_SPEED` mutável server-authoritative provider-native. **`UNAVAILABLE_NODE`**.

### A0323 — Queda Controlada

`LivingDamageEvent.Pre` + `DamageTypes.FALL` permitem redução causal explícita de FALL. **IMPLEMENTÁVEL**.

### A0324 — Esquiva Econômica

Epic Fight `SkillConsumeEvent` expõe DODGE/STAMINA amount mutável antes do consumo. Rota Epic Fight **IMPLEMENTÁVEL**. Rota ParCool continua fail-closed sem precommit server-authoritative.

### A0325 — Janela de Esquiva

Epic Fight `ON_DODGE` prova sucesso de dodge, mas não expõe janela semântica mutável de PERFECT_DODGE. I-frames ParCool não são equivalentes. **`UNAVAILABLE_NODE`**.

### A0326 — Precisão em Movimento

Falta snapshot causal de locomoção voluntária no RELEASE/LAUNCH e decomposition provider-native da parcela de penalidade por movimento. **`UNAVAILABLE_NODE`**.

### A0327 — Dano Após Esquiva

A0325 está unavailable; closure transitiva bloqueia A0327. Também faltam `PERFECT_DODGE_RECEIPT_V1` e compositor ofensivo direto genérico comprovado. **`UNAVAILABLE_NODE`**.

### A0328 — Ímpeto

A primitive `MOVEMENT_SPEED` existe, mas não existem contexto causal/ledger determinístico para gerar/decair cargas. **`UNAVAILABLE_NODE`**.

### A0329 — Freio Técnico

Depende de A0328≥2 e de `DIRECTION_BREAK_V1`/policy claim-once. A0328 está unavailable e os contratos locais também faltam. **`UNAVAILABLE_NODE`**.

Resultado: **10/10 designs aprovados; 7/10 unavailable/fail-closed; 3/10 implementáveis (A0321, A0323, A0324).**

## 5. Resultado por perk

| Código | Perk | Decisão | Estado atual | Principal boundary/blocker |
|---|---|---|---|---|
| A0320 | Salto Econômico | APROVADA | `UNAVAILABLE_NODE` | JUMP STAMINA/METABOLIC causal precommit ausente |
| A0321 | Nadador | APROVADA | IMPLEMENTÁVEL | NeoForge `SWIM_SPEED` + `isSwimming()` |
| A0322 | Escalador | APROVADA | `UNAVAILABLE_NODE` | `CLIMB_SPEED` server-authoritative mutável ausente |
| A0323 | Queda Controlada | APROVADA | IMPLEMENTÁVEL | `LivingDamageEvent.Pre` + FALL classifier |
| A0324 | Esquiva Econômica | APROVADA | IMPLEMENTÁVEL via Epic Fight | `SkillConsumeEvent` DODGE/STAMINA precommit |
| A0325 | Janela de Esquiva | APROVADA | `UNAVAILABLE_NODE` | perfect-dodge window mutável ausente |
| A0326 | Precisão em Movimento | APROVADA | `UNAVAILABLE_NODE` | voluntary movement launch snapshot + movement penalty decomposition |
| A0327 | Dano Após Esquiva | APROVADA | `UNAVAILABLE_NODE` | closure A0325 + receipt/compositor direto |
| A0328 | Ímpeto | APROVADA | `UNAVAILABLE_NODE` | voluntary movement context + momentum ledger |
| A0329 | Freio Técnico | APROVADA | `UNAVAILABLE_NODE` | closure A0328 + direction-break policy |

## 6. Nove eixos obrigatórios

| Critério | Status do lote |
|---|---|
| 1. Dependências e bloqueios | ✅ closure transitiva e blockers locais preservados |
| 2. Integrações globais/corpo/recursos | ✅ STAMINA, METABOLIC, movement, dodge, damage e sublevel mantêm authorities distintas |
| 3. Qualidade/identidade | ✅ efeitos percentuais e Notable/branch semantics preservados sem fallback genérico |
| 4. Ramificação/distância/topologia | ✅ AGILITY mantém ramos Locomoção, Esquiva/Reação, Ranged em Movimento e Ímpeto/Controle sem bypass |
| 5. Especializações | ✅ perks gerais de AGILITY não foram promovidas a Specialist nem misturadas com Nature/Magic |
| 6. PT-BR | ✅ nomes/effects player-facing preservados em PT-BR |
| 7. Notion completo | ✅ 10/10 revisadas; 5/5 alterações persistidas |
| 8. NeoVitae removido | ✅ nenhuma dependência ativa/residual usada |
| 9. Cobertura modlist/providers | ✅ modlist File Library+Notion, projetos próprios e providers periféricos pertinentes revisados |

## 7. Provider-native e pipelines canônicos

### Movimento

`SWIM_SPEED`, `MOVEMENT_SPEED`, climb progress e movimento voluntário são conceitos distintos. A0321 pode usar atributo nativo; A0322 não pode transformar Speed em climb; A0328/A0329 não podem inferir voluntary movement/hard turn por delta de posição ou yaw.

### Stamina e metabolismo

Epic Fight/ParCool stamina e Minecraft FoodData/exhaustion permanecem authorities separadas. A0320 só modifica debit causal de JUMP; A0324 só modifica DODGE/STAMINA. Refund pós-fato, polling e resource injection são proibidos.

### Dodge

Epic Fight `SkillConsumeEvent` é precommit de custo; `ON_DODGE` é success signal. Eles não são a mesma capability. `ON_DODGE` não expõe perfect-dodge window e ParCool i-frame não pode ser rebatizado como perfect dodge.

### Damage

A0323 modifica apenas FALL no boundary NeoForge. A0327, se abrir no futuro, aplica contribuição somente em direct offensive outcomes de autoria correta; DoT/derived/summon/retaliação não herdam automaticamente.

### Ranged em movimento

A0326 exige movement snapshot no launch e decomposition de movement penalty. Projectile speed não é accuracy; hit posterior não pode rerrolar crítico.

### Spatial/sublevels

Sable/Create Aeronautics exigem avaliação no frame/space local. Movimento passivo do parent/sublevel não satisfaz voluntary movement nem direction break.

### MineColonies Battle Mage

A `main` avançou concorrentemente durante o fechamento e incorporou runtime real de MineColonies Battle Mages × Iron's Spellbooks em `d4422e3e...`. O delta foi reaberto e reclassificado antes do handoff.

A bridge preserva authorities: MineColonies é owner do cidadão/job/inventário/target/lifecycle; Iron's é owner de `SpellData`, `MagicData`, mana, cooldown e cast; RPG apenas orquestra profiles/safety/lifecycle de integração. O cast usa `CastSource.MOB` e caster `EntityCitizen`.

Consequência para este lote: Battle Mage **não é ação direta do jogador** e não abre A0327, A0326, A0324, A0328 ou A0329. Colony owner, equipe ou proximidade não transferem autoria. Qualquer futuro crédito player-facing exige contrato causal explícito.

## 8. Projetos próprios e delta bidirecional

Detalhamento completo: `guides/projects/25-capability-delta-a0320-a0329.md`.

Freshness final:

- **RPG Skill Tree:** `main@d4422e3ee07e6cfa17cceac0fddd87be81cf78e4`; Compêndio BWG = `COBERTO POR SISTEMA UNIVERSAL`; MineColonies Economy = `PLANEJADO / NÃO RUNTIME / SEM HOOK SEGURO`; Battle Mages = **BRIDGE IMPLEMENTADA / PROGRESSÃO NATIVA AUTORITATIVA**, sem player perk equivalente.
- **Volcanoes standalone:** GET fresco retorna 404; authority viva permanece consolidada no RPG. Isso não cria movement/dodge/stamina semantics.
- **Enshrouded:** `67f4ab90...` → mesmo SHA; `plans/STATUS.md` confirma Stage 08 5/5 e Stage 09 ainda não iniciado. `SEM DELTA RELEVANTE`.
- **Black Arcana:** `d1388127...`; `plans/STATUS.md` confirma 07.01/07.02 canônicos e 07.03–07.07 pendentes; commit fresco é documental/ordenação de domínios, sem delta runtime pertinente.

Todos os deltas receberam disposição explícita; nenhum exige 11ª perk.

## 9. Causalidade, deduplicação e anti-abuso

Regras transversais:

1. uma action/debit/outcome recebe no máximo uma contribuição da mesma perk;
2. provider-native precommit precede qualquer modificação de custo;
3. derived outcomes não viram ações diretas por proximidade/parent owner;
4. client animation/HUD não prova authority;
5. movement external/passive não satisfaz voluntary movement;
6. frame/space local é obrigatório em sublevels;
7. modifiers transitórios usam ID estável e lifecycle idempotente;
8. perfect dodge não é inferido de i-frame/dodge success sem janela/receipt correto;
9. crítico permanece uma única decisão/rolagem por root;
10. unavailable purchase falha antes de gastar PP e allocation legado unavailable vale 0 PP;
11. `EntityCitizen` + `CastSource.MOB` não recebe autoria ofensiva do player por colony ownership/equipe/proximidade.

## 10. Testes destinados ao Chat 3

Além dos testes individuais dos dez dossiês, validar transversalmente:

- purchase fail-before-spend para os sete unavailable nodes;
- allocation legado unavailable = 0 PP + migration/respec segura;
- A0321 modifier único, ranks, swimming gate e lifecycle;
- A0323 FALL-only, aplicação única e interação upstream com breakfall/roll;
- A0324 Epic Fight DODGE/STAMINA precommit e dedup cross-bridge;
- provider absent/version mismatch;
- nenhuma aproximação por polling/refund/delta de posição/yaw/i-frame;
- direct vs derived provenance;
- Battle Mage `EntityCitizen`/`CastSource.MOB` não aciona player direct-offense/perk credit;
- same-space/sublevel local frame;
- multiplayer e dedicated server.

Chat 1 **não executou** essa bateria; execução/validação pertence ao Chat 3.

## 11. STATUS e concorrência documental

`perks/STATUS.md` possui histórico amplo e PRs de lotes anteriores ainda abertas. Para evitar reescrita concorrente, este ciclo materializa o estado do lote em `audits/STATUS-A0320-A0329.md`, seguindo o padrão de A0300–A0309/A0310–A0319. O tracker deve ser reconciliado no agregado quando a cadeia de PRs for serializada.

A branch/PR também deve ser reconciliada com `main@d4422e3e...` antes do fechamento, preservando somente documentação A0321–A0329 + auditoria/status/delta como diferença própria quando A0320 já estiver presente na `main` por ancestry concorrente.

## 12. Handoff Chat 2

Chat 2 deve continuar **a mesma branch/PR** criada por este ciclo.

- Implementar A0321/A0323/A0324 exatamente pelos boundaries aprovados.
- Preservar A0320/A0322/A0325/A0326/A0327/A0328/A0329 como unavailable até capabilities reais existirem.
- Não depender de `AttributeNodeEffectRuntime` ou `A0001A0020CriticalService`: não existem na `main` auditada.
- Não criar engine global nova apenas para satisfazer nomes documentais antigos.
- Não inferir climb/movement/perfect dodge/crit/root por heurística.
- Não atribuir casts Battle Mage ao jogador; caster/authority atuais são cidadão MineColonies + Iron's `MOB`.
- Divergência real de provider que altere identity/effect/gate/topologia/authority volta ao Chat 1.

## 13. Estado final Chat 1

**A0320–A0329 — DESIGN APROVADO / LOTE FECHADO PELO CHAT 1.**

Chat 1 para aqui: não implementa runtime, não executa bateria final, não declara `IMPLEMENTAÇÃO CONFIRMADA`, não faz merge e não inicia A0330+.
