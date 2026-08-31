# Projetos Próprios do Modpack — Fonte Canônica para Perks

Esta pasta documenta os quatro projetos próprios que precisam ser tratados como providers/sistemas de primeira classe durante a auditoria de perks:

1. [RPG Skill Tree](01-rpg-skill-tree.md)
2. [Volcanoes](02-volcanoes.md)
3. [Enshrouded](03-enshrouded.md)
4. [Black Arcana](04-black-arcana.md)
5. [Matriz de integração cruzada](05-cross-project-integration-matrix.md)
6. [Reconciliação dos snapshots](06-snapshot-reconciliation.md)
7. [Checklist do Chat 1 para listar providers](07-chat1-provider-listing-checklist.md)
8. [Regra de manutenção](08-maintenance-rule.md)
9. [Política de fontes](09-source-policy.md)
10. [Regra de autoridade](10-authority-rule.md)
11. [Não inferir hooks](11-do-not-infer-hooks.md)
12. [Matriz de cobertura e delta de capacidades](12-capability-delta-coverage.md)
13. [Delta e baseline A0071–A0080](13-capability-delta-a0071-a0080.md)
14. [Delta e baseline A0081–A0090](14-capability-delta-a0081-a0090.md)
15. A0091–A0100: checkpoint registrado na PR #326 em `15-capability-delta-a0091-a0100.md`; essa PR ainda não estava integrada à `main` na abertura de A0101–A0110.
16. [Delta e baseline A0101–A0110](16-capability-delta-a0101-a0110.md)

**Fonte editorial canônica no Notion:** https://app.notion.com/p/3cc69db9f0db81b09939eaca7c446fa2

## Por que esta pasta existe

Os guias históricos descrevem os mods externos e os quatro projetos próprios, mas esses projetos evoluem continuamente e podem inclusive mudar de forma de distribuição. O Chat 1 deve saber exatamente:

- quais subsistemas já são autoridade de gameplay em `main`;
- quais existem parcialmente;
- quais são preparatórios ou somente planejados;
- quais bridges são reais;
- quais relações são proibidas ou fail-closed;
- qual provider conserva a autoridade em integrações híbridas;
- quais **capacidades novas ou semanticamente alteradas** surgiram desde o último lote e se a árvore já as cobre.

## Taxonomia obrigatória de estado

- **IMPLEMENTADO E CANÔNICO:** presente em `main`, com contrato/runtime fechado e evidência suficiente.
- **IMPLEMENTADO PARCIALMENTE:** código útil presente em `main`, mas o estágio/contrato ainda possui fechamento pendente.
- **PREPARATÓRIO / NÃO CANÔNICO:** existe em branch/protótipo/trabalho downstream, mas não é authority da `main`.
- **PLANEJADO:** especificação futura; não pode ser usada como hook implementável pelo Chat 2.
- **BLOQUEADO / FAIL-CLOSED:** integração intencionalmente inativa até existir API/evidência segura.
- **NÃO APLICÁVEL:** não existe relação semântica legítima no estado auditado.

A presença de um arquivo em `plans/` **não** prova disponibilidade de runtime.

## Atualização estrutural — Volcanoes consolidado

A PR #308 do RPG Skill Tree foi mergeada em 2026-08-31 e internalizou a implementação canônica de Volcanoes `eaddc3232dfc600780769f4a5e7e45ff1e50181c` no mesmo JAR `rpgskilltree`.

Consequências para auditorias futuras:

- `rpgskilltree` é o único `@Mod`/JAR distribuído;
- Volcanoes continua sendo um **subsistema/provider semanticamente próprio**, preservando `volcanoes:*`, simulação, authorities e pipelines anti-duplicação;
- `NativeVolcanoesServices` é uma bridge **read-only** para geologia, vulcanismo, tectônica, atmosfera e pressão;
- o repositório `Gustavaopere/Volcanoes` permanece enquanto a limpeza separada não ocorre e deve ser tratado como **source/provenance/migration baseline**, não como segundo runtime a executar em paralelo;
- compartilhar o mesmo JAR não autoriza perks a inferir classifications a partir de atmosfera, pressão, geologia ou localização.

## Baseline reconciliado para o próximo delta

O checkpoint operacional mais recente está em [`16-capability-delta-a0101-a0110.md`](16-capability-delta-a0101-a0110.md), após disposição completa do lote A0101–A0110 e reconciliação do merge #308.

| Projeto | Baseline atual |
|---|---|
| RPG Skill Tree — inclui Volcanoes nativo | `b32a4c85946807c38339c640614b44670c78643f` |
| Volcanoes — source/migration baseline | `eaddc3232dfc600780769f4a5e7e45ff1e50181c` |
| Enshrouded | `29ae2d9b7a13bbdffd3291d2fe4213e0705eb8e3` |
| Black Arcana | `e89df6dc2c204c269d8f1811c6b3f309644c864a` |

Os SHAs registram somente um **checkpoint de comparação**. A verdade operacional continua sendo `main` + `plans/STATUS.md` frescos, reconciliados com plano/código/testes/CI quando necessário.

Os dossiês 01–04 preservam a análise extensa do snapshot original. Quando houver divergência posterior, [`06-snapshot-reconciliation.md`](06-snapshot-reconciliation.md), [`12-capability-delta-coverage.md`](12-capability-delta-coverage.md) e o suplemento mais recente [`16-capability-delta-a0101-a0110.md`](16-capability-delta-a0101-a0110.md) registram o delta que **substitui apenas os fatos afetados**, sem promover automaticamente o Stage inteiro.

## Regra para o Chat 1 — dois sentidos obrigatórios

Antes de fechar qualquer perk, o Chat 1 deve cruzá-la com os quatro dossiês e a matriz. Para cada projeto próprio, classificar a relação como uma destas opções:

- provider direto;
- bridge/consumer secundário;
- coberto por sistema universal;
- progressão nativa autoritativa;
- planejado, ainda sem hook implementável;
- bloqueado/fail-closed;
- não aplicável.

Se uma perk toca mais de um projeto, o dossiê individual precisa declarar um **pipeline principal**, os providers/consumers secundários, a identidade de deduplicação, o fallback e o comportamento fail-closed.

Além dessa auditoria **perk → provider**, cada lote deve executar obrigatoriamente a auditoria **provider → árvore** definida em [`12-capability-delta-coverage.md`](12-capability-delta-coverage.md):

1. fetch fresco de `main` e `plans/STATUS.md` dos quatro projetos;
2. comparação contra o baseline reconciliado;
3. extração de toda capacidade jogável nova ou semanticamente alterada, **mesmo que nenhuma perk atual a cite**;
4. classificação de cobertura antes do fechamento do lote.

Isso inclui, quando existirem ou mudarem, capacidades como O₂/respiração/pressão/proteção do Volcanoes nativo, Arcane Resistance/Corruption Resistance/Strain do Black Arcana, Exposure/Flame/Sanctuary/Story do Enshrouded e novas superfícies públicas do próprio RPG Skill Tree.

Detectar uma capacidade **não** significa criar automaticamente uma perk. Ela pode ser coberta por perk existente, perk própria, especialização, bridge, sistema universal, progressão nativa autoritativa, `SEM HOOK SEGURO` ou `NÃO DEVE SER INTEGRADO`.

A descoberta de uma lacuna também não altera a regra de **lotes exatos de 10**. Uma necessidade fora do lote atual é registrada para ciclo posterior; o Chat 1 não inicia uma décima primeira perk.

O preenchimento operacional de providers está em [`07-chat1-provider-listing-checklist.md`](07-chat1-provider-listing-checklist.md); o delta global está em [`12-capability-delta-coverage.md`](12-capability-delta-coverage.md), com o baseline substituto mais recente em [`16-capability-delta-a0101-a0110.md`](16-capability-delta-a0101-a0110.md).

## Regra de autoridade

Integração temática ou co-localização no mesmo JAR não cria hook. Não converter automaticamente:

- Black Arcana Corruption em Enshrouded Shroud;
- Volcanoes Atmosphere em Shroud;
- pressão/temperatura/gases em Arcane Resistance;
- Volcanoes atmosphere/pressure/geology em classifier de dano ambiental A0103;
- Flame Passage em resistência arcana;
- estado de veículos em encumbrance do jogador;
- dados client-side/HUD em authority de gameplay.

Quando um provider não expõe um hook seguro, a parte dependente permanece inativa/pending em vez de receber um bônus genérico substituto.

## Relação com os três guias

Os três guias continuam obrigatórios e recebem um apêndice final com o recorte pertinente destes quatro projetos:

- [Gameplay e Sistemas](../gameplay/13-projetos-proprios-do-modpack.md)
- [Mods de Magia](../magic/17-projetos-proprios-do-modpack.md)
- [Mods de Tecnologia](../technology/19-projetos-proprios-do-modpack.md)

Os apêndices são mapas temáticos. Para decidir provider, hook, status, delta ou fail-closed, estes dossiês, a reconciliação, a matriz de delta e a evidência fresca da `main` prevalecem.
