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
- RPG Skill Tree `main@8b76a7cef1aa675fdd988bf694c876e751fb839d` na abertura do ciclo; este baseline já incorpora a revalidação Chat 2 A0001–A0020 da PR #237.
- Fechamento rechecado contra RPG Skill Tree `main@d1c29b1acca488f14e0741073f90502621a5ed39`: o delta desde `8b76a7c` contém somente corpus editorial pt-BR do Compendium e teste correspondente, sem alteração de contrato MARTIAL, perks A0041–A0050, providers ou boundaries deste lote.
- Volcanoes `main@7839db6d9b718e1e2becfe8b88e9b3d24282e2ef`.
- Enshrouded `main@f8d4d54cb5b8f12aa2149568bfaa2e25f00ef5e5`.
- Black Arcana `main@73c14ce55ff918bb8a81daeb99a352607ef11064`.
- Mobstein 5.4.4 conforme guia consolidado obrigatório.

## Gate provider→árvore

### RPG Skill Tree

- O runtime A0041–A0060 já possui policy/state/adapters para Foices, Arcos, Bestas e lane posterior.
- Stage 11.01 de itemização é **PROGRESSÃO NATIVA AUTORITATIVA** de identidade/rank/ItemPower/rolls.
- As projeções de efeitos de itemização não estão disponíveis como contrato destas perks; portanto, para A0041–A0050, o Stage 11 permanece **SEM HOOK SEGURO** e seus rolls não podem ser lidos diretamente para inventar dano, crítico, Focus, penetration ou reload.
- Mastery deve permanecer baseada em milestones/discoveries finitos, nunca em spam de dano.

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
- Ataque/projétil direto do jogador contra entidade Mobstein continua `COBERTO POR SISTEMA UNIVERSAL` quando o receipt real da disciplina e filtros anti-abuso forem satisfeitos.
- Corpses/órgãos/experimentos e progressão nativa Mobstein não criam integração com este lote.

## Matriz do lote

| Perk | Resultado | Boundary/pendência principal |
|---|---|---|
| A0041 | APROVADA após correção | `P-A0041-01`: reserva PRE, commit da Marca só pós-hit confirmado; classificador SCYTHE único |
| A0042 | APROVADA após correção | `P-A0042-01`: eligible_kill anti-abuso; `P-A0042-02`: unificar/deduplicar dois producers de death; Stamina refund fail-closed correto |
| A0043 | APROVADA após correção | `P-A0043-01`: producer de Mastery BOW por discovery finita; classificador BowItem/provider-native |
| A0044 | APROVADA | FAIL-CLOSED correto: sem provider semântico de draw/preparation speed |
| A0045 | APROVADA | crítico canônico presente; depende do gate A0043 alcançável e de teste gameplay |
| A0046 | APROVADA após correção | `P-A0046-01`: heavy-impact −25 Focus; `P-A0046-02`: escalares corporais reais; Volcanoes apenas via Cold Sweat |
| A0047 | APROVADA | `P-A0047-01`: não fabricar projectile speed por `setDeltaMovement`; manter speed omitido sem provider seguro |
| A0048 | APROVADA | shot/custo/distância/cooldown presentes; depende de Mastery BOW e prova gameplay |
| A0049 | APROVADA após correção | `P-A0049-01`: producer de Mastery CROSSBOW por discovery finita; classificador CrossbowItem/provider-native |
| A0050 | APROVADA | FAIL-CLOSED correto: sem provider semântico de reload/preparation speed |

## Notion

### Fetch fresco

A0041–A0050 = **10/10**.

### Páginas alteradas

- **A0041:** `Hook`, `Fallback`, `Regra` — classificador SCYTHE contínuo + reservation/commit pós-hit + provenance provider boundaries.
- **A0042:** `Hook`, `Fallback`, `Regra` — `eligible_kill` anti-abuso canônico + SCYTHE contínuo + Mobstein/Backlash/hazards sem autoria.
- **A0043:** `Hook`, `Fallback`, `Regra` — remove fallback genérico de tag BOW; BowItem/provider-native; Mastery +10 por tipo hostil inédito, 6→60 e 8→80.
- **A0046:** `Hook`, `Fallback`, `Regra` — heavy impact exige receipt causal; corpo por providers separados; Volcanoes só via Cold Sweat; companions/Backlash/Shroud não são proxies.
- **A0049:** `Hook`, `Fallback`, `Regra` — remove fallback genérico de tag CROSSBOW; CrossbowItem/provider-native; Mastery +10 por tipo hostil inédito, 6→60 e 8→80.

**Re-fetch pós-escrita:** 5/5 PASS em 2026-08-30.

### Sem mutação

- A0044, A0045, A0047, A0048 e A0050.
- Motivo: os contratos do Notion já estavam semanticamente corretos. A divergência de A0047 é do runtime, não do design.

## Pendências destinadas ao Chat 2

### P-A0041-01 — commit transacional da Marca

`scytheCut` hoje consome a Marca Madura no PRE. Implementar reservation→commit por `rootActionId`: dano/impacto podem ser calculados a partir da reserva; o consumo definitivo só ocorre no POST confirmado com dano >0. Cancel/zero libera a reserva.

### P-A0042-01 — kill anti-abuso

Substituir `Enemy || Player` como prova suficiente por receipt `eligible_kill` do serviço anti-abuso central, cobrindo training/summon/trivial/repetitive farm.

### P-A0042-02 — dois producers de death

`A0042ScytheKillHooks.onDeath` e `A0041A0060EpicFightHooks.onDeath` podem armar Colheita de Batalha. Unificar ou fazer ambos consumirem o mesmo receipt/root deduplicado, impedindo bypass de filtros.

### P-A0043-01 — Mastery BOW

O bridge de projéteis vanilla/NeoForge não produz `epicfight:bow`. Conceder +10 uma vez por tipo hostil inédito no pós-hit BOW real, via discovery persistente e dedup com eventual producer Epic Fight. Gate60=6; A0048 gate80=8.

### P-A0046-01 — heavy impact Focus

Há policy `loseFocusForHeavyImpact`, mas nenhum caller produtivo. Integrar receipt hostil pesado seguro e aplicar −25 uma vez por outcome.

### P-A0046-02 — estado corporal

A0046 menciona temperatura/hidratação/exhaustion, mas o bridge atual não possui adapters. Integrar somente Cold Sweat/Thirst/Minecraft reais; ausência de um eixo omite só seu escalar. Volcanoes nunca é lido diretamente.

### P-A0047-01 — projectile speed

O runtime passa `projectileSpeedAvailable=true` incondicionalmente e escala `deltaMovement`. Isso diverge do fallback. Sem provider semântico de launch speed, usar `false` e manter apenas penetration per-hit; não substituir o provider por mutação genérica.

### P-A0049-01 — Mastery CROSSBOW

Mesma correção de A0043 para `epicfight:crossbow`: +10 por tipo hostil inédito no pós-hit CROSSBOW real; 6→60, 8→80; dedup contra producer Epic Fight.

### P-A0041-50-TEST-01 — prova gameplay/provider-present

O lote possui tests de catálogo/policy e CI histórico, mas precisa de GameTest/harness server-side que exercite adapters reais: SCYTHE mark/kill/transfer, BOW Focus/distance/prepared shot, CROSSBOW projectile classification, Mastery, provider absent/present, dedup, lifecycle e multiplayer.

## Fallbacks aprovados

- **A0042 Stamina:** refund de 10% omitido sem receipt pós-consumo real; transferência de Marca continua funcional após eligible_kill legítimo.
- **A0044:** inteira inativa sem draw/preparation-speed API semântica.
- **A0046 body scalars:** eixo indisponível é simplesmente ignorado; não inferir de outro recurso.
- **A0047:** speed omitido sem provider; penetration segura pode manter o disparo especial válido e o custo de Focus.
- **A0050:** inteira inativa sem reload/preparation-speed API semântica.

## Nove eixos / 18 critérios

- Dependências/gates: PASS no design.
- Integração global/authority: PASS com boundaries e gaps explicitamente catalogados.
- Qualidade/identidade/topologia/especialização/PT-BR: PASS.
- Notion/re-fetch: PASS.
- NeoVitae: ausente.
- Provider→árvore: PASS com `SEM HOOK SEGURO`/`NÃO DEVE SER INTEGRADO` onde necessário.
- Causalidade/dedup: design fechado; runtime gaps A0041/A0042 documentados.
- Mastery anti-farm: design fechado para BOW/CROSSBOW; runtime producer pendente.
- Lifecycle/multiplayer/testabilidade: requisitos explícitos; prova gameplay transversal pendente.

## Fechamento de design

**A0041–A0050 — LOTE FECHADO NO DESIGN.**

Chat 2 deve implementar/corrigir somente os contratos e pendências listados, sem redesenhar perks. **A0051+ não foi iniciada.**
