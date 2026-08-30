# Auditoria retroativa de integração — A0041–A0050

## Escopo

- **Lote exato:** A0041–A0050, 10 perks consecutivas.
- **Foices:** A0041–A0042.
- **Arcos:** A0043–A0048.
- **Bestas:** A0049–A0050.
- **Providers retroauditados:** RPG Skill Tree, Volcanoes, Enshrouded, Black Arcana e Mobstein 5.4.4, além dos providers canônicos do contrato.
- **Fora de escopo:** A0051+ e implementação runtime neste Chat 1.

## Organização documental corrigida neste ciclo

As auditorias deixaram de ficar espalhadas na raiz `perks/`.

- Pasta canônica: `plans/03-skill-tree-perks/perks/audits/`.
- Auditorias históricas e retroativas anteriores foram movidas preservando conteúdo/histórico.
- `audits/README.md` é o índice das auditorias.
- Dossiês individuais `Axxxx-*.md`, `STATUS.md`, critérios e regras operacionais continuam na raiz `perks/`.
- Novos lotes devem criar sua auditoria em `audits/`; não voltar a acumular `AUDITORIA-*.md` na raiz.

A opção por pasta, em vez de um único arquivo gigante, preserva rastreabilidade de lote, diffs menores e revisão por PR.

## Fontes e checkpoints frescos

- Critérios obrigatórios e guias consolidados anexados ao projeto.
- Catálogo Mestre do Notion com fetch fresco individual A0041–A0050.
- Runtime real A0041–A0060 no RPG Skill Tree.
- RPG Skill Tree `main@8b76a7cef1aa675fdd988bf694c876e751fb839d` na abertura do ciclo.
- Fechamento rechecado contra RPG Skill Tree `main@d1c29b1acca488f14e0741073f90502621a5ed39`: o delta desde `8b76a7c` contém somente corpus editorial pt-BR do Compendium e teste correspondente, sem alteração de contrato MARTIAL deste lote.
- Volcanoes `main@7839db6d9b718e1e2becfe8b88e9b3d24282e2ef`.
- Enshrouded `main@f8d4d54cb5b8f12aa2149568bfaa2e25f00ef5e5`.
- Black Arcana `main@73c14ce55ff918bb8a81daeb99a352607ef11064`.
- Mobstein 5.4.4 conforme guia consolidado obrigatório.
- Review da PR #243 revalidado contra `AGENTS.md`, `CombatPerkTreeModel`, `SkillTreeDataLoader`, `ProgressionService` e `tree_architecture/combat.json`.

## Gate provider→árvore

### RPG Skill Tree

- O runtime A0041–A0060 já possui policy/state/adapters para Foices, Arcos, Bestas e lane posterior.
- Stage 11.01 de itemização é **PROGRESSÃO NATIVA AUTORITATIVA** de identidade/rank/ItemPower/rolls.
- As projeções de efeitos de itemização não estão disponíveis como contrato destas perks; portanto Stage 11 permanece **SEM HOOK SEGURO** para inventar dano, crítico, Focus, penetration ou reload.
- Mastery deve permanecer baseada em milestones/discoveries finitos, nunca em spam de dano.
- Review #243 confirmou dois gaps sistêmicos adicionais:
  1. provider obrigatório ausente precisa gerar **estado explícito de nó indisponível/não comprável**, nunca silent no-op purchase;
  2. a architecture JSON publica `combat:bow`/`combat:crossbow`, enquanto Notion + `CombatPerkTreeModel` + projectile runtime usam `epicfight:bow`/`epicfight:crossbow`. O design canônico permanece nos IDs `epicfight:*`; o runtime/catalog deve ser reconciliado pelo Chat 2 ou migrado formalmente, nunca manter duas ledgers paralelas.

### Volcanoes

- Sem delta novo pertinente ao lote.
- Geologia, prospecção, Atmosphere, gases, pressão e hazards são `NÃO DEVE SER INTEGRADO` ao dano/classificação BOW/CROSSBOW/SCYTHE.
- Única composição legítima neste lote: A0046 pode receber efeito térmico **indiretamente** quando o estado corporal Cold Sweat já incorporou uma fonte ambiental Volcanoes. A0046 não consulta Volcanoes diretamente e não reaplica calor.

### Enshrouded

- Sem delta novo pertinente.
- Shroud, Exposure, Madness, Flame, Story/Sanctuary e MagicResistance continuam authorities próprias e não classificam armas, projectile root, Focus, critical ou kill de foice.

### Black Arcana

- Sem delta MARTIAL novo.
- `ARCANE_BACKLASH` continua terminal: não é SCYTHE hit, BOW/CROSSBOW projectile, critical root, Focus producer ou eligible kill.
- Arcane Strain/Corruption não são estado corporal de A0046.

### Mobstein 5.4.4

- Allies/bodyguards/ressuscitados continuam Mobstein-owned.
- Dano/projétil/kill de companion não herda autoria SCYTHE/BOW/CROSSBOW, Focus ou Mastery do dono.
- Ataque/projétil direto do jogador contra entidade Mobstein continua `COBERTO POR SISTEMA UNIVERSAL` quando receipt real da disciplina e filtros anti-abuso forem satisfeitos.
- Corpses/órgãos/experimentos e progressão nativa Mobstein não criam integração com este lote.

## Matriz do lote

| Perk | Resultado | Boundary/pendência principal |
|---|---|---|
| A0041 | APROVADA após correção | `P-A0041-01`: reserva PRE, commit da Marca só pós-hit confirmado; classificador SCYTHE único |
| A0042 | APROVADA após correção | `P-A0042-01`: eligible_kill anti-abuso; `P-A0042-02`: unificar/deduplicar producers de death |
| A0043 | APROVADA após correção | `P-A0043-01`: Mastery BOW por discovery finita; `P-A0043-02`: reconciliar architecture `combat:bow` → ledger canônica `epicfight:bow` |
| A0044 | APROVADA após review | `P-A0044-01`: nó deve ser indisponível/não comprável sem binding de draw/preparation speed; silent no-op atual não é fail-closed válido |
| A0045 | APROVADA | crítico canônico presente; depende do gate A0043 alcançável e de teste gameplay |
| A0046 | APROVADA após correção | `P-A0046-01`: heavy-impact −25 Focus; `P-A0046-02`: escalares corporais reais; Volcanoes apenas via Cold Sweat |
| A0047 | APROVADA após review | `P-A0047-01`: não fabricar projectile speed por `setDeltaMovement`; enquanto A0044 indisponível, A0047 também é não comprável |
| A0048 | APROVADA | shot/custo/distância/cooldown presentes; depende de Mastery BOW e prova gameplay |
| A0049 | APROVADA após correção | `P-A0049-01`: Mastery CROSSBOW por discovery finita; `P-A0049-02`: reconciliar architecture `combat:crossbow` → `epicfight:crossbow` |
| A0050 | APROVADA após review | `P-A0050-01`: nó deve ser indisponível/não comprável sem binding de reload/preparation speed |

## Notion

### Fetch fresco

A0041–A0050 = **10/10**.

### Páginas alteradas no fechamento inicial

- **A0041:** `Hook`, `Fallback`, `Regra` — classificador SCYTHE contínuo + reservation/commit pós-hit + provenance provider boundaries.
- **A0042:** `Hook`, `Fallback`, `Regra` — `eligible_kill` anti-abuso canônico + SCYTHE contínuo + Mobstein/Backlash/hazards sem autoria.
- **A0043:** `Hook`, `Fallback`, `Regra` — BowItem/provider-native + Mastery `epicfight:bow` +10 por tipo hostil inédito, 6→60 e 8→80.
- **A0046:** `Hook`, `Fallback`, `Regra` — heavy impact exige receipt causal; corpo por providers separados; Volcanoes só via Cold Sweat.
- **A0049:** `Hook`, `Fallback`, `Regra` — CrossbowItem/provider-native + Mastery `epicfight:crossbow` +10 por tipo hostil inédito, 6→60 e 8→80.

### Correções do review PR #243

- **A0044:** `Gate`, `Fallback`, `Regra` — provider ausente/incompatível => nó explicitamente indisponível/não comprável; nenhum ponto/rank no-op; A0047 permanece bloqueada.
- **A0047:** `Gate`, `Fallback`, `Regra` — availability de A0044 propaga para A0047; não criar bypass de dependência no runtime.
- **A0050:** `Gate`, `Fallback`, `Regra` — provider ausente/incompatível => nó explicitamente indisponível/não comprável; dependências posteriores não podem ser satisfeitas por rank fantasma/bypass.

**Total de páginas alteradas neste lote:** 8/10.

**Re-fetch pós-escrita:** 8/8 PASS em 2026-08-30 (5/5 fechamento inicial + 3/3 review).

### Sem mutação

- A0045 e A0048.
- Motivo: os contratos do Notion já permanecem semanticamente válidos para o recorte auditado.

## Pendências destinadas ao Chat 2

### P-A0041-01 — commit transacional da Marca

`scytheCut` hoje consome a Marca Madura no PRE. Implementar reservation→commit por `rootActionId`; consumo definitivo somente no POST confirmado com dano >0. Cancel/zero libera a reserva.

### P-A0042-01 — kill anti-abuso

Substituir `Enemy || Player` como prova suficiente por receipt `eligible_kill` do serviço anti-abuso central, cobrindo training/summon/trivial/repetitive farm.

### P-A0042-02 — dois producers de death

`A0042ScytheKillHooks.onDeath` e `A0041A0060EpicFightHooks.onDeath` podem armar Colheita de Batalha. Unificar ou fazer ambos consumirem o mesmo receipt/root deduplicado.

### P-A0043-01 — Mastery BOW

Conceder +10 uma vez por tipo hostil inédito no pós-hit BOW real, via discovery persistente e dedup com eventual producer Epic Fight. Gate60=6; A0048 gate80=8.

### P-A0043-02 — namespace BOW

`tree_architecture/combat.json` usa `combat:bow`, mas Notion, `CombatPerkTreeModel` e projectile runtime usam `epicfight:bow`. Reconciliar para a ledger canônica ou, se houver motivo para trocar ID, criar migração explícita primeiro. Adicionar teste architecture↔model↔contrato.

### P-A0044-01 — availability de draw speed

Sem provider semântico compatível, A0044 deve ser indisponível/não comprável no servidor e na projeção cliente. `ProgressionService.purchaseNode` não pode consumir pontos para um nó sem binding. Não usar custo zero/no-op como substituto.

### P-A0046-01 — heavy impact Focus

Há policy `loseFocusForHeavyImpact`, mas nenhum caller produtivo. Integrar receipt hostil pesado seguro e aplicar −25 uma vez por outcome.

### P-A0046-02 — estado corporal

Integrar somente Cold Sweat/Thirst/Minecraft reais; ausência de um eixo omite só seu escalar. Volcanoes nunca é lido diretamente.

### P-A0047-01 — projectile speed

Não declarar `projectileSpeedAvailable=true` por existir `AbstractArrow`. Sem provider semântico de launch speed, manter `false`; não substituir provider por `setDeltaMovement`. A0047 também deve permanecer indisponível enquanto A0044 não puder ser adquirida legitimamente.

### P-A0049-01 — Mastery CROSSBOW

+10 por tipo hostil inédito no pós-hit CROSSBOW real; 6→60, 8→80; dedup contra eventual producer Epic Fight.

### P-A0049-02 — namespace CROSSBOW

`tree_architecture/combat.json` usa `combat:crossbow`, enquanto Notion/model/runtime usam `epicfight:crossbow`. Reconciliar sem manter ledgers paralelas e cobrir por teste de coerência.

### P-A0050-01 — availability de reload speed

Sem provider semântico compatível, A0050 deve ser indisponível/não comprável; nenhum ponto/rank fantasma. Dependências posteriores continuam insatisfeitas até o lote correspondente decidir qualquer eventual reroute.

### P-A0041-50-TEST-01 — prova gameplay/provider-present

GameTest/harness server-side para SCYTHE mark/kill/transfer, BOW Focus/distance/prepared shot, CROSSBOW classification, Mastery, provider absent/present, availability, dedup, lifecycle e multiplayer.

## Fallbacks aprovados

- **A0042 Stamina:** refund omitido sem receipt pós-consumo real; transferência de Marca continua após eligible_kill legítimo.
- **A0044:** sem draw/preparation binding, **nó indisponível/não comprável**; não é permitido “efeito inativo mas rank comprável”.
- **A0046 body scalars:** eixo indisponível é ignorado; não inferir de outro recurso.
- **A0047:** speed omitido sem provider; penetration pode continuar sendo componente seguro somente quando A0047 estiver legitimamente disponível; A0044 indisponível bloqueia aquisição de A0047.
- **A0050:** sem reload/preparation binding, **nó indisponível/não comprável**.

## Nove eixos / 18 critérios

- Dependências/gates: **PASS NO DESIGN após review; runtime possui blockers explícitos P-A0043-02, P-A0044-01, P-A0049-02 e P-A0050-01**. Não considerar o estado implementado como confirmado antes do Chat 2.
- Integração global/authority: PASS com boundaries e gaps catalogados.
- Qualidade/identidade/topologia/especialização/PT-BR: PASS no design.
- Notion/re-fetch: PASS 8/8 nas páginas mutadas.
- NeoVitae: ausente.
- Provider→árvore: PASS com `SEM HOOK SEGURO`/`NÃO DEVE SER INTEGRADO` onde necessário.
- Causalidade/dedup: design fechado; runtime gaps A0041/A0042 documentados.
- Mastery anti-farm: design fechado para BOW/CROSSBOW; producer e namespace runtime pendentes.
- Lifecycle/multiplayer/testabilidade: requisitos explícitos; prova gameplay transversal pendente.

## Review PR #243

Os dois findings automáticos foram verificados contra o código real e incorporados:

1. **P1 — silent no-op purchase A0044/A0050:** confirmado. `CombatPerkTreeModel`/`SkillTreeDataLoader` publicam os nós normalmente e o runtime atual não possui availability gate de binding. O design/Notion foi corrigido para indisponível/não comprável e foram abertos `P-A0044-01`/`P-A0050-01`.
2. **P2 — Mastery IDs divergentes:** confirmado. Architecture JSON usa `combat:bow`/`combat:crossbow`; Notion/model/runtime usam `epicfight:bow`/`epicfight:crossbow`. Foram abertos `P-A0043-02`/`P-A0049-02`; o design não declara mais validação runtime de gates como totalmente resolvida.

Nenhum finding exige implementação runtime pelo Chat 1.

## Fechamento de design

**A0041–A0050 — LOTE FECHADO NO DESIGN, COM BLOCKERS RUNTIME EXPLICITAMENTE DESTINADOS AO CHAT 2.**

Chat 2 deve implementar/corrigir somente os contratos e pendências listados, sem redesenhar perks. **A0051+ não foi iniciada.**
