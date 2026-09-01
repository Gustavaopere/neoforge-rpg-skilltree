# Auditoria Chat 1 — A0121–A0130

**Intervalo:** A0121–A0130, exatamente 10 perks consecutivas.  
**Data:** 2026-09-01.  
**Base documental reconciliada:** `main@c1597a34787b602e85139d565b9c1e1eb3481cda`.  
**Responsabilidade:** auditoria/design/documentação; nenhum runtime implementado por este Chat 1.

## Fontes obrigatórias

Foram usadas como fontes operacionais obrigatórias:

- `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`;
- `GUIA-COMPLETO-GAMEPLAY-E-SISTEMAS.md`;
- `GUIA-COMPLETO-MODS-DE-MAGIA.md`;
- `GUIA-COMPLETO-MODS-DE-TECNOLOGIA.md`;
- `GUIA-COMPLETO-PROJETOS-PROPRIOS.md`;
- `CHAT-1-AUDITORIA-DESIGN-PERKS-ANEXOS-PROJETO.md`;
- Notion `Catálogo Mestre — Atributos e Passivos`;
- `AGENTS.md`, `plans/STATUS.md`, `plans/volcanoes/STATUS.md` e status frescos de Enshrouded/Black Arcana.

As PRs abertas #326, #340 e #341 foram tratadas como fontes documentais predecessoras de design, nunca como runtime integrado.

## Resultado executivo

| Código | Perk | Resultado Chat 1 | Estado runtime atual |
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

`UNAVAILABLE_NODE` significa purchase falha antes do gasto; allocation legado vale 0 PP para gates e permanece reembolsável/migrável. Nenhuma perk recebe bônus substituto.

## Notion — fetch, hardening e persistência

Fetch fresco: **10/10**.

A0121/A0122 já estavam corretas no catálogo quanto à versão canônica **ParCool 4.0.0.2**; não foram mutadas neste ciclo. Os dossiês GitHub foram corrigidos para a mesma versão depois de detectar uma divergência documental concorrente que citava 4.0.0.3.

As páginas A0123–A0128 foram endurecidas porque o texto anterior permitia interpretar a ausência global de `BodyCostResolver` como mero miss por evento, criando risco de purchase no-op.

Foi congelado:

- P-0037/`BodyCostResolver` global ausente/incompatível => `UNAVAILABLE_NODE`, purchase fail-before-spend e allocation legado 0 PP;
- A0124/A0126/A0128 exigem também predecessor adquirível + adapter causal Thirst Was Reclaimed 3.0.4;
- somente depois que os bindings globais existirem a ausência de receipt numa ação específica omite apenas o proc daquele evento;
- refund pós-fato, polling de hunger/thirst, escrita direta de sede e substituição por Stamina são proibidos.

Páginas distintas alteradas: **6/10 — A0123–A0128**.  
Sem mutação: **A0121, A0122, A0129, A0130**.  
Re-fetch pós-escrita: **6/6 PASS**.

## Versões/provider matrix

- Minecraft: 1.21.1.
- NeoForge: 21.1.248.
- Java: 21.
- ParCool: **4.0.0.2**.
- Epic ParCool: 21.0.0.
- Epic Fight: 21.17.3.1.
- Thirst Was Reclaimed: 3.0.4, owner de HYDRATION.
- Thirst Was Fixed: 2.1.5, compat/fix; não owner paralelo.
- Minecraft/NeoForge `FoodData`: owner do custo METABOLIC vanilla quando existir débito causal real da ação.

## Pipeline corporal canônico

O lote usa uma única fronteira lógica de custo corporal; não são dez reducers independentes aplicados diretamente aos providers.

```text
action_id válida
→ classificação server-side
→ quote/receipt METABOLIC positivo e causal
→ agregar eficiências METABOLIC elegíveis
→ cap METABOLIC compartilhado 30%
→ settlement METABOLIC
→ adapter TWR correlaciona HYDRATION à mesma action_id quando o provider realmente a produz
→ agregar eficiências HYDRATION elegíveis
→ cap HYDRATION compartilhado 30%
→ commit provider exatamente uma vez por lane
```

Se a API real não oferecer seam seguro antes do commit, a perk permanece indisponível; refund heurístico posterior não substitui o contrato.

## Decisões por atividade

### Escalar — A0121/A0122

ParCool 4.0.0.2 e Epic ParCool podem classificar a escalada, mas não são owner de hunger/exhaustion. Sem `METABOLIC_CLIMB` não se fabrica custo e, portanto, também não existe `HYDRATION_CLIMB` causal derivada. Stamina permanece recurso separado.

### Minerar — A0123/A0124

Quebra manual pode possuir custo `FoodData` real, mas só é elegível com receipt causal da mesma action. Bloco recolocado não é automaticamente excluído, pois a perk economiza custo e não concede progressão. Automação/fake player sem débito corporal e blocos derivados de bulk/vein sem custo próprio são inelegíveis.

### Cortar madeira — A0125/A0126

Mesma regra de custo real. Mods de árvores podem classificar a ação, mas não se tornam owner de FoodData. Tree-felling/bulk não multiplica parcels sem débito corporal comprovado.

### Lutar corpo a corpo — A0127/A0128

FoodData é custo corporal; Epic Fight 21.17.3.1 apenas identifica/classifica a root melee. Um root direto contra alvo hostil válido produz no máximo um settlement. DoT, proc derivado, reflexão, summon e callback duplicado não abrem nova ação corporal.

### Arco/besta — A0129/A0130

Launch provenance comprova o disparo, não custo corporal. No runtime auditado não existe `METABOLIC_RANGED`; é proibido fabricar exhaustion ou substituir por Stamina, munição, draw/reload, Focus/Cadence, mana ou durabilidade. Sem METABOLIC real, HYDRATION ranged causal também não existe.

## Capability delta dos projetos próprios

Arquivo canônico: `guides/projects/18-capability-delta-a0121-a0130.md`.

- RPG Skill Tree/Volcanoes nativo: baseline reconciliado para `main@c1597a34787b602e85139d565b9c1e1eb3481cda`. O avanço desde `25ade2f...` é documental de A0200–A0209; não adiciona `BodyCostResolver`, `METABOLIC_CLIMB` ou `METABOLIC_RANGED`.
- Volcanoes standalone `eaddc3232dfc600780769f4a5e7e45ff1e50181c` permanece provenance do snapshot consolidado, não runtime operacional paralelo.
- Enshrouded: `a08ff919463cb6ce3ea2a8eda59d74feffa6b6b2`; delta recente foi hardening/teste de fixture two-boot de corrupção, sem nova capability para este lote.
- Black Arcana: `e89df6dc2c204c269d8f1811c6b3f309644c864a`; sem capability corporal aplicável.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | availability estrutural/transitiva; 10/10 atualmente indisponíveis |
| Integração global | PASS | um BodyCost pipeline; FoodData/TWR owners preservados |
| Qualidade/identidade | PASS | METABOLIC ≠ HYDRATION ≠ Stamina ≠ recursos mágicos/tecnológicos |
| Topologia | PASS | mobilidade, MINING, FORESTRY, MARTIAL e RANGED preservados |
| Especializações | PASS/N/A | bridge PP nunca double-count sem allowlist explícita |
| PT-BR | PASS | nomenclatura e identidade preservadas |
| Notion | PASS | 10/10 fetch; 6 mutadas; 6/6 re-fetch |
| NeoVitae | PASS/N/A | ausente/removido |
| Cobertura provider | PASS | owners/classifiers/versionamento/fail-closed explícitos |

## Checklist técnico — 18 critérios

| # | Critério | Resultado |
|---:|---|---|
| 1 | efeito real | PASS — apenas custo corporal real |
| 2 | provider-native first | PASS — FoodData/TWR preservados |
| 3 | sem mecânica inventada | PASS |
| 4 | fail-closed | PASS — 10/10 indisponíveis no snapshot atual |
| 5 | fallback mantém identidade | PASS |
| 6 | Mastery por feitos | N/A |
| 7 | anti-farm | PASS — derived/bulk/callbacks não multiplicam parcels |
| 8 | atribuição causal | PASS — `action_id`/root explícita |
| 9 | sem pipelines duplicados | PASS |
| 10 | custos reais | PASS — quote/receipt positivo do owner |
| 11 | sem geração gratuita | PASS |
| 12 | read-only correto | PASS/N/A — classifiers não viram owner |
| 13 | versionamento | PASS |
| 14 | coerência estrutural | PASS |
| 15 | dependências semânticas | PASS |
| 16 | sem sobreposição indevida | PASS |
| 17 | implementável posteriormente | PASS — hooks/authority/order/pendências/testes congelados |
| 18 | verificação pós-escrita | PASS — 6/6 re-fetch |

## Handoff Chat 2 — implementação diferida

O design está fechado, mas o runtime de A0121–A0130 **não deve iniciar ainda**. Antes:

1. a `main` deve comprovar os gates globais de `AGENTS.md / Before expanding content` por código/testes/CI;
2. PR #326 (A0091–A0100), PR #340 (A0101–A0110) e PR #341 (A0111–A0120) devem atravessar Chat 2 + Chat 3 e chegar à `main`;
3. esta PR A0121–A0130 deve ser reconciliada com a `main` resultante;
4. Chat 2 implementa exatamente os dossiês, preservando `UNAVAILABLE_NODE` para qualquer capability ainda ausente.

A PR #331/A0200–A0209 é uma exceção documental adiantada registrada na `main`; ela não fecha, substitui nem torna runtime A0091–A0199.

## Testes transversais exigidos ao Chat 3

- purchase fail-before-spend + legacy PP 0 para todos os blockers;
- provider/version absent/present;
- action identity e dedup cross-adapter;
- caps METABOLIC 30% e HYDRATION 30% independentes;
- ordem METABOLIC→HYDRATION na mesma action;
- zero/cancel/rollback sem custo fantasma;
- forced/passive/automation/fake-player exclusions;
- bulk/tree-felling/Multishot/root dedup;
- Stamina/ammo/Focus/Cadence/mana/durability nunca substituem BodyCost;
- TWR causal sem direct writes/polling; TWF não-owner;
- lifecycle logout/dimension/respec/rules reload/provider removal;
- multiplayer attribution;
- unit/JUnit, NeoForge GameTests, validators, build, JAR e dedicated-server smoke quando houver código.

## Estado final Chat 1

**DESIGN APROVADO / LOTE A0121–A0130 FECHADO PELO CHAT 1 / IMPLEMENTAÇÃO DIFERIDA POR GATES GLOBAIS E PREDECESSORES.**

Chat 1 não implementa runtime e não faz merge. A0131+ permanece fora deste ciclo.