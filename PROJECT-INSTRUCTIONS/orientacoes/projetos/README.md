# Projetos Próprios do Modpack — Fonte Canônica para Perks

Esta pasta documenta quatro projetos/sistemas próprios que precisam ser tratados como providers/sistemas de primeira classe durante a auditoria de perks:

### Referências técnicas e estado

1. [RPG Skill Tree](referencias/01-rpg-skill-tree.md)
2. [Volcanoes](referencias/02-volcanoes.md)
3. [Enshrouded](referencias/03-enshrouded.md)
4. [Black Arcana](referencias/04-black-arcana.md)
5. [Matriz de integração cruzada](referencias/05-cross-project-integration-matrix.md)
6. [Reconciliação dos snapshots](referencias/06-snapshot-reconciliation.md)
7. [Matriz de cobertura e delta de capacidades](referencias/12-capability-delta-coverage.md)
8. [Delta e baseline A0071–A0080](referencias/13-capability-delta-a0071-a0080.md)
9. [Delta e baseline A0081–A0090](referencias/14-capability-delta-a0081-a0090.md)
10. [Delta e baseline A0200–A0209](referencias/15-capability-delta-a0200-a0209.md)
11. [Delta e baseline especial A0200–A0299](referencias/16-capability-delta-a0200-a0299.md)

### Regras operacionais para IA

1. [Checklist do Chat 1 para listar providers](regras-ia/07-chat1-provider-listing-checklist.md)
2. [Regra de manutenção](regras-ia/08-maintenance-rule.md)
3. [Política de fontes](regras-ia/09-source-policy.md)
4. [Regra de autoridade](regras-ia/10-authority-rule.md)
5. [Não inferir hooks](regras-ia/11-do-not-infer-hooks.md)

**Fonte editorial canônica no Notion:** https://app.notion.com/p/3cc69db9f0db81b09939eaca7c446fa2

## Topologia operacional dos projetos

Desde a consolidação da PR #308, **Volcanoes não possui mais um repositório operacional independente para evolução de runtime**. O código canônico do Volcanoes é um subsistema nativo do próprio `Gustavaopere/neoforge-rpg-skilltree`, preservando seus namespaces/authority ambientais e geológicos dentro do único mod/JAR `rpgskilltree`.

Portanto, para freshness/delta:

- **RPG Skill Tree:** `Gustavaopere/neoforge-rpg-skilltree/main` + `plans/STATUS.md` e os paths do estágio pertinente;
- **Volcanoes:** o mesmo `Gustavaopere/neoforge-rpg-skilltree/main`, mas o delta deve ser filtrado pelas superfícies Volcanoes (`docs/archive/volcanoes/**`, `src/main/java/dev/gustavopere/volcanoes/**`, `src/main/java/dev/gustavopere/rpgskilltree/runtime/volcanoes/**`, recursos `volcanoes:*`, `docs/volcanoes/**`, workflows/scripts Volcanoes e contratos compartilhados que o subsystem realmente consuma) + `docs/archive/volcanoes/STATUS.md`;
- **Enshrouded:** seu repositório próprio + `plans/STATUS.md` enquanto continuar separado;
- **Black Arcana:** seu repositório próprio + `plans/STATUS.md` enquanto continuar separado.

O antigo `Gustavaopere/Volcanoes@eaddc3232dfc600780769f4a5e7e45ff1e50181c` permanece somente como **proveniência do snapshot importado**. Ele não é a fonte de mudanças posteriores à consolidação e não deve ser usado para concluir `SEM DELTA` no Volcanoes atual.

## Por que esta pasta existe

Os guias históricos descrevem os mods externos do pack, mas os quatro projetos/sistemas próprios evoluem continuamente e contêm sistemas que não podem ser inferidos por README, nome de classe ou intenção futura. O Chat 1 deve saber exatamente:

- quais subsistemas já são autoridade de gameplay em `main`;
- quais existem parcialmente;
- quais são preparatórios ou somente planejados;
- quais bridges são reais;
- quais relações são proibidas ou fail-closed;
- qual provider conserva a autoridade em integrações híbridas;
- quais **capacidades novas ou semanticamente alteradas** surgiram desde o último lote e se a árvore já as cobre.

## Taxonomia obrigatória de estado

- **IMPLEMENTADO E CANÔNICO:** presente na fonte operacional atual, com contrato/runtime fechado e evidência suficiente.
- **IMPLEMENTADO PARCIALMENTE:** código útil presente, mas o estágio/contrato ainda possui fechamento pendente.
- **PREPARATÓRIO / NÃO CANÔNICO:** existe em branch/protótipo/trabalho downstream, mas não é autoridade da `main`.
- **PLANEJADO:** especificação futura; não pode ser usada como hook implementável pelo Chat 2.
- **BLOQUEADO / FAIL-CLOSED:** integração intencionalmente inativa até existir API/evidência segura.
- **NÃO APLICÁVEL:** não existe relação semântica legítima no estado auditado.

A presença de um arquivo em `plans/` **não** prova disponibilidade de runtime.

## Baseline reconciliado e regra pós-consolidação

O último checkpoint pré-consolidação está em [`14-capability-delta-a0081-a0090.md`](referencias/14-capability-delta-a0081-a0090.md):

| Projeto | Baseline histórico do fechamento A0081–A0090 |
|---|---|
| RPG Skill Tree | `6975970d086d32985d83a0018c841cce9d1cbd63` |
| Volcanoes standalone | `eaddc3232dfc600780769f4a5e7e45ff1e50181c` |
| Enshrouded | `391ea82203d30cb392a3397f92e2a3cbe7fb6128` |
| Black Arcana | `710077da89da5eb4418d3ac676e148849727ff07` |

Para Volcanoes, esse SHA **não é mais um baseline operacional de repositório**. Ele é a proveniência exata importada pela consolidação. A PR #308, merge `f613dac5a15b26c7a92e07a9d9cb537c2412ddf2`, é o boundary de transição: depois dele, o próximo gate deve comparar a superfície Volcanoes dentro da `main` unificada contra o estado consolidado, e não consultar o standalone para procurar mudanças novas.

Os SHAs registram checkpoints de comparação/proveniência, não congelamento de verdade. A fonte operacional fresca e seus status/plans/código/testes/CI prevalecem em todo novo lote.

Os dossiês 01–04 preservam análises extensas e seus deltas. Quando houver divergência posterior, [`06-snapshot-reconciliation.md`](referencias/06-snapshot-reconciliation.md), [`12-capability-delta-coverage.md`](referencias/12-capability-delta-coverage.md) e o suplemento mais recente registram o delta que **substitui apenas os fatos afetados**, sem promover automaticamente o Stage inteiro.

## Regra para o Chat 1 — dois sentidos obrigatórios

Antes de fechar qualquer perk, o Chat 1 deve cruzá-la com os quatro dossiês e a matriz. Para cada projeto próprio pertinente, classificar a relação como uma destas opções:

- provider direto;
- bridge/consumer secundário;
- coberto por sistema universal;
- progressão nativa autoritativa;
- planejado, ainda sem hook implementável;
- bloqueado/fail-closed;
- não aplicável.

Se uma perk toca mais de um projeto, o dossiê individual precisa declarar um **pipeline principal**, os providers/consumers secundários, a identidade de deduplicação, o fallback e o comportamento fail-closed.

Além dessa auditoria **perk → provider**, cada lote deve executar obrigatoriamente a auditoria **provider → árvore** definida em [`12-capability-delta-coverage.md`](referencias/12-capability-delta-coverage.md):

1. fetch fresco das fontes operacionais e status dos quatro projetos/sistemas;
2. comparação contra o baseline/boundary reconciliado pertinente;
3. extração de toda capacidade jogável nova ou semanticamente alterada, **mesmo que nenhuma perk atual a cite**;
4. classificação de cobertura antes do fechamento do lote.

Isso inclui, quando existirem ou mudarem, capacidades como O₂/respiração/pressão/proteção do Volcanoes, Arcane Resistance/Corruption Resistance/Strain do Black Arcana, Exposure/Flame/Sanctuary/Story do Enshrouded e novas superfícies públicas do próprio RPG Skill Tree.

Detectar uma capacidade **não** significa criar automaticamente uma perk. Ela pode ser coberta por perk existente, perk própria, especialização, bridge, sistema universal, progressão nativa autoritativa, `SEM HOOK SEGURO` ou `NÃO DEVE SER INTEGRADO`.

A descoberta de uma lacuna também não altera a regra de **lotes exatos de 10**. Uma necessidade fora do lote atual é registrada para ciclo posterior; o Chat 1 não inicia uma décima primeira perk.

O preenchimento operacional de providers está em [`07-chat1-provider-listing-checklist.md`](regras-ia/07-chat1-provider-listing-checklist.md); o delta global está em [`12-capability-delta-coverage.md`](referencias/12-capability-delta-coverage.md).

## Regra de autoridade

Integração temática não cria hook nem copropriedade. A consolidação também não muda a authority semântica: `rpgskilltree` hospedar Volcanoes não transforma progressão RPG em dona da Atmosphere, pressão, tectônica, erupções ou depósitos.

Não converter automaticamente:

- Black Arcana Corruption em Enshrouded Shroud;
- Volcanoes Atmosphere em Shroud;
- pressão/temperatura/gases em Arcane Resistance;
- Flame Passage em resistência arcana;
- estado de veículos em encumbrance do jogador;
- dados client-side/HUD em autoridade de gameplay.

Quando um provider não expõe um hook seguro, a parte dependente permanece inativa/pending em vez de receber um bônus genérico substituto.

## Relação com os antigos guias temáticos

Os guias agregados de Gameplay, Magia e Tecnologia foram retirados da árvore ativa em 01/10/2026 depois da reconciliação contra os dossiers individuais dos mods. Eles permanecem apenas no histórico Git.

Para providers externos, use `PROJECT-INSTRUCTIONS/modlist/modlist.md` e o dossier individual atual. Para RPG Skill Tree, Volcanoes, Enshrouded e Black Arcana, esta pasta continua sendo a fonte transversal de contratos, authority, deltas e fail-closed.
