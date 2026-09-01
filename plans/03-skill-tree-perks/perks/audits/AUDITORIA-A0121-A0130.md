# Auditoria Chat 1 — A0121–A0130

**Intervalo:** A0121–A0130, exatamente 10 perks consecutivas.  
**Data:** 2026-09-01.  
**Base:** `main@66fcec7b163320cfb0d79943969aae33f3adf862`.  
**Responsabilidade:** auditoria/design/documentação; nenhum runtime implementado por este Chat 1.

## Fontes obrigatórias

Foram lidos integralmente/reconsultados antes da auditoria:

- `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`;
- `GUIA-COMPLETO-GAMEPLAY-E-SISTEMAS.md`;
- `GUIA-COMPLETO-MODS-DE-MAGIA.md`;
- `GUIA-COMPLETO-MODS-DE-TECNOLOGIA.md`;
- `GUIA-COMPLETO-PROJETOS-PROPRIOS.md`;
- protocolo `CHAT-1-AUDITORIA-DESIGN-PERKS-ANEXOS-PROJETO.md`.

Também foram usados `CURRENT-MODLIST.md`, `AGENTS.md`, `plans/STATUS.md`, `plans/volcanoes/STATUS.md`, os status frescos de Enshrouded/Black Arcana e os contratos de design predecessores nas PRs abertas #326/#340/#341 sem promovê-los a runtime.

## Resultado executivo

| Código | Perk | Resultado Chat 1 | Estado runtime esperado hoje |
|---|---|---|---|
| A0121 | Economia Metabólica: Escalar | DESIGN APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE`: P-0037 + `METABOLIC_CLIMB` ausentes |
| A0122 | Conservação Hídrica: Escalar | DESIGN APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE`: predecessors + P-0037 + `HYDRATION_CLIMB` ausente |
| A0123 | Economia Metabólica: Minerar | DESIGN APROVADO APÓS HARDENING | `UNAVAILABLE_NODE`: BodyCostResolver/P-0037 ausente |
| A0124 | Conservação Hídrica: Minerar | DESIGN APROVADO APÓS HARDENING | `UNAVAILABLE_NODE`: A0123 + P-0037/TWR adapter |
| A0125 | Economia Metabólica: Cortar Madeira | DESIGN APROVADO APÓS HARDENING | `UNAVAILABLE_NODE`: BodyCostResolver/P-0037 ausente |
| A0126 | Conservação Hídrica: Cortar Madeira | DESIGN APROVADO APÓS HARDENING | `UNAVAILABLE_NODE`: A0125 + P-0037/TWR adapter |
| A0127 | Economia Metabólica: Lutar Corpo a Corpo | DESIGN APROVADO APÓS HARDENING | `UNAVAILABLE_NODE`: BodyCostResolver/P-0037 ausente |
| A0128 | Conservação Hídrica: Lutar Corpo a Corpo | DESIGN APROVADO APÓS HARDENING | `UNAVAILABLE_NODE`: A0127 + P-0037/TWR adapter |
| A0129 | Economia Metabólica: Usar Arco/Besta | DESIGN APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE`: `METABOLIC_RANGED` real ausente |
| A0130 | Conservação Hídrica: Usar Arco/Besta | DESIGN APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE`: A0129 + `HYDRATION_RANGED` ausente |

`UNAVAILABLE_NODE` = purchase falha antes do gasto; allocation legado vale 0 PP para gates e permanece reembolsável/migrável. Nenhuma perk recebe bônus substituto.

## Notion — fetch, correção e persistência

Fetch fresco: **10/10**.

### Versão ParCool

A0121 e A0122 ainda citavam ParCool 4.0.0.2. `plans/03-skill-tree-perks/guides/gameplay/CURRENT-MODLIST.md` é autoridade atual de presença/JAR/versão e fixa **ParCool 4.0.0.3**. As duas páginas foram corrigidas; Epic ParCool permanece 21.0.0.

### Availability A0123–A0128

Essas seis páginas tratavam ausência do receipt/BodyCostResolver como falha por evento, embora o resolver seja o único caminho do efeito inteiro. Isso permitiria purchase no-op.

Foi congelado:

- P-0037/`BodyCostResolver` global ausente/incompatível => node `UNAVAILABLE_NODE`, purchase fail-before-spend, allocation legado 0 PP;
- A0124/A0126/A0128 também exigem adapter causal Thirst Was Reclaimed 3.0.4 e predecessor adquirível;
- depois que os bindings globais existirem, receipt ausente numa **ação específica** apenas omite o proc daquele evento;
- nenhum refund pós-fato, polling de hunger/thirst, escrita direta de sede ou substituição por Stamina.

Páginas distintas alteradas: **8/10** — A0121–A0128.  
Sem mutação: A0129/A0130, pois já estavam corretamente fail-closed.  
Re-fetch pós-escrita: **8/8 PASS**.

## Versões/provider matrix relevante

- NeoForge: 21.1.248 / Minecraft 1.21.1 / Java 21.
- ParCool: **4.0.0.3**.
- Epic ParCool: 21.0.0.
- Epic Fight: 21.17.3.1.
- Thirst Was Reclaimed: 3.0.4, owner de HYDRATION.
- Thirst Was Fixed: 2.1.5, compat/fix; não owner paralelo.
- Minecraft/NeoForge `FoodData`: owner do custo METABOLIC vanilla quando houver debit causal real.

## Pipeline corporal canônico

O lote inteiro usa um único modelo lógico; não há dez reducers diretos independentes.

```text
action_id válida
→ classificação server-side
→ quote/receipt METABOLIC positivo e causal do provider corporal
→ agregar eficiências METABOLIC elegíveis
→ cap METABOLIC compartilhado 30%
→ settlement METABOLIC
→ adapter TWR correlaciona HYDRATION à mesma action_id quando o provider realmente a produz
→ agregar eficiências HYDRATION elegíveis
→ cap HYDRATION compartilhado 30%
→ commit provider exatamente uma vez por lane
```

Se a API real só permitir observar o débito depois do commit, não criar refund heurístico. O node permanece indisponível até existir boundary transacional seguro ou contrato provider-native equivalente.

## Decisões por atividade

### Escalar — A0121/A0122

ParCool 4.0.0.3 pode identificar escalada, mas não produz hunger/exhaustion corporal. Sem `METABOLIC_CLIMB`, não se fabrica custo. Consequentemente não existe `HYDRATION_CLIMB` causal derivada. Stamina ParCool/Epic Fight é separada.

### Minerar — A0123/A0124

Quebra manual pode possuir custo FoodData real, mas o Chat 2 precisa de um boundary causal para quote/commit. Blocos colocados não são excluídos por origem porque a perk economiza custo, não concede progressão; automação/fake player sem debit corporal ficam fora. Bulk/vein derived breaks não multiplicam receipts.

### Cortar madeira — A0125/A0126

Mesma regra de custo real. Mods de árvores podem classificar a ação; não viram owner de FoodData. Tree-felling/bulk não gera parcels extras sem débito próprio.

### Lutar corpo a corpo — A0127/A0128

FoodData é custo corporal; Epic Fight 21.17.3.1 somente identifica/classifica a root melee. Stamina permanece independente. Um root válido contra alvo hostil produz no máximo um settlement; DoT/proc/reflection/summon não abrem nova ação corporal.

### Arco/besta — A0129/A0130

Launch provenance prova o disparo, não prova custo corporal. No runtime auditado não há `METABOLIC_RANGED`; é proibido fabricar exhaustion ou substituir por Stamina, ammo, draw/reload, Focus/Cadence, mana ou durability. Sem METABOLIC real, HYDRATION ranged também inexiste.

## Capability delta dos quatro projetos próprios

Arquivo canônico: `guides/projects/18-capability-delta-a0121-a0130.md`.

- RPG Skill Tree: `main@66fcec7...`; busca fresca não encontra `BodyCostResolver` live.
- Volcanoes: runtime é subsistema nativo da mesma `main` desde PR #308. `plans/volcanoes/STATUS.md` confirma authority ambiental/geológica própria; nada vira custo corporal deste lote. O standalone `eaddc323...` é provenance, não fonte operacional atual.
- Enshrouded: `29ae2d9... → a08ff919...`; único delta é hardening de fixture two-boot/reload da corrupção de entidades, sem capability nova para estas perks. Baseline promovido para `a08ff919...`.
- Black Arcana: `e89df6d...`; sem delta. Custos arcanos/Arcane Danger continuam pipelines próprios.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | availability estrutural e transitiva; 10/10 atualmente indisponíveis |
| Integração global | PASS | um BodyCost pipeline; FoodData/TWR owners preservados |
| Qualidade/identidade | PASS | metabolismo ≠ hidratação ≠ Stamina ≠ recursos de magia/tecnologia |
| Topologia | PASS | mobilidade, MINING, forestry, MARTIAL e RANGED bridges mantêm identidade |
| Especializações | PASS/N/A | PP bridge não pode double-count sem allowlist semântica |
| PT-BR | PASS | nomenclatura e efeito preservados |
| Notion | PASS | 10/10 fetch; 8 mutadas; 8/8 re-fetch |
| NeoVitae | PASS/N/A | ausente/removido |
| Cobertura provider | PASS | versões/owners/classifiers/deltas explicitamente classificados |

## Checklist técnico — 18 critérios

| # | Critério | Resultado |
|---:|---|---|
| 1 | efeito real | PASS — só sobre custo corporal real |
| 2 | provider-native first | PASS — FoodData/TWR preservados |
| 3 | sem mecânica inventada | PASS — não fabricar exhaustion/hydration |
| 4 | fail-closed | PASS — 10/10 indisponíveis no snapshot atual |
| 5 | fallback mantém identidade | PASS — omite/indisponibiliza, não troca por Stamina/resources |
| 6 | Mastery por feitos | N/A |
| 7 | anti-farm | PASS — derived/bulk/callbacks não multiplicam parcels |
| 8 | atribuição causal | PASS — `action_id`/root explícita |
| 9 | sem pipelines duplicados | PASS — settlement corporal comum |
| 10 | custos reais | PASS — quote/receipt positivo do owner |
| 11 | sem geração gratuita | PASS — nenhuma fome/sede artificial ou refund fabricado |
| 12 | read-only correto | PASS/N/A — classificadores não viram owner |
| 13 | versionamento | PASS — versões exatas acima |
| 14 | coerência estrutural | PASS — ranks/custos/prereqs preservados |
| 15 | dependências semânticas | PASS — hydration herda predecessor/metabolic capability |
| 16 | sem sobreposição indevida | PASS — lanes/resource types separados |
| 17 | implementável posteriormente | PASS — hooks/authority/order/pendings/testes fechados |
| 18 | verificação pós-escrita | PASS — 8/8 re-fetch |

## Handoff Chat 2 — IMPLEMENTAÇÃO DIFERIDA

O design está fechado, mas **não iniciar runtime A0121–A0130 ainda**. Antes:

1. `main` corrente deve comprovar os requisitos mínimos de `AGENTS.md` / `Before expanding content` por código/testes/CI;
2. #326 (A0091–A0100), #340 (A0101–A0110) e #341 (A0111–A0120) devem atravessar Chat 2/Chat 3 e ser integradas à `main`;
3. a PR deste lote deve ser reconciliada com essa `main`;
4. somente então Chat 2 implementa os dossiês, mantendo indisponível qualquer capability ainda ausente.

Chat 1 não implementa as fundações, não implementa os predecessores e não faz merge.

## Testes transversais para Chat 3

Além dos testes por dossiê:

- purchase fail-before-spend + legacy PP 0 para todos os blockers;
- provider/version absent/present;
- action identity e dedup cross-adapter;
- METABOLIC cap 30% e HYDRATION cap 30% independentes;
- ordem METABOLIC→HYDRATION same-action;
- zero/cancel/rollback sem custo fantasma;
- forced/passive/automation/fake-player exclusions;
- bulk/tree-felling/Multishot/root dedup;
- Stamina/ammo/Focus/Cadence/mana/durability não substituem BodyCost;
- TWR causal sem direct writes/polling; TWF não-owner;
- lifecycle logout/dimension/respec/rules reload/provider removal;
- multiplayer attribution;
- unit/JUnit, NeoForge GameTests, validators, build, JAR e dedicated-server smoke quando o Chat 2 produzir código.

## Estado final Chat 1

**DESIGN APROVADO / LOTE A0121–A0130 FECHADO PELO CHAT 1 / IMPLEMENTAÇÃO DIFERIDA POR GATES GLOBAIS E PREDECESSORES.**

Chat 1 não faz merge. A PR permanece aberta. A0131+ não foi iniciado.
