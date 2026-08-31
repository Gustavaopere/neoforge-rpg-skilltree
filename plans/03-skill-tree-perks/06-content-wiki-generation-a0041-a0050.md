# Stage 03.06 — texto player-facing A0041–A0050

Esta fatia continua o plano `06-content-wiki-generation.md` depois da publicação A0031–A0040 da PR #268.

## Escopo fechado

- publicar texto PT-BR player-facing somente para A0041–A0050;
- derivar cada descrição exclusivamente dos dossiês canônicos aprovados em `plans/03-skill-tree-perks/perks/` e da auditoria retroativa do lote;
- preservar explicitamente indisponibilidade, classificação provider-native/fail-closed e estados de implementação parcial/não confirmada;
- manter A0051+ sem descrição player-facing;
- regenerar `wiki/combat-perks/A0041-A0050.md` pelo pipeline factual existente;
- não alterar gameplay, coefficients, gates, provider adapters nem resolver pendências Chat 2 nesta fatia.

## Autoridades

- design/texto: dossiês A0041–A0050 já aprovados e `perks/audits/AUDITORIA-RETROATIVA-PROVIDERS-A0041-A0050.md`;
- nomes: `NotionCombatPerkCatalog`;
- gates/ranks/custos: `CombatPerkTreeModel`;
- apresentação versionada: `CombatPerkPlayerTextCatalog`;
- geração factual: `CombatPerkWikiSnapshotGenerator` + `scripts/wiki_catalog.py`.

## Boundaries preservados

- A0041: Corte de Ceifa continua parcial enquanto a Marca Madura puder ser consumida no PRE; o contrato player-facing exige reservation no PRE e commit pós-hit somente com dano efetivo >0.
- A0042: `eligible_kill` permanece dependente de anti-abuso/dedup causal; sem receipt causal de custo real, somente o refund de Stamina é omitido e permanece 0.
- A0043/A0049: dano BOW/CROSSBOW existe, mas os producers de Mastery por discovery finita ainda não estão confirmados; os IDs canônicos são `epicfight:bow` e `epicfight:crossbow`, sem ledgers paralelas `combat:*`.
- A0044: sem binding semântico server-authoritative de draw/preparation speed, o nó é explicitamente INDISPONÍVEL/NÃO COMPRÁVEL; nenhum rank/no-op é permitido.
- A0046: heavy-impact e escalares corporais permanecem parciais; cada eixo corporal ausente é omitido isoladamente e Volcanoes só compõe indiretamente via Cold Sweat.
- A0047: a indisponibilidade estrutural de A0044 se propaga; `setDeltaMovement` não substitui provider semântico de projectile speed.
- A0048: o shot/custo/distância/cooldown podem ser descritos, mas aquisição ainda depende do producer de Mastery BOW e de prova gameplay/provider-present.
- A0050: sem binding semântico server-authoritative de reload/preparation speed, o nó é explicitamente INDISPONÍVEL/NÃO COMPRÁVEL; dependências posteriores permanecem insatisfeitas.

## TDD

- RED: RPG Skill Tree CI #2395, HEAD `bd57571f13cd7c0c9a73b9ed5d57c1a20276e58f`, merge-ref `faa34266c7d238d6412d1e40c26587594930a9af`, falhou somente em JUnit 5 com 5 falhas esperadas em 141 testes. Core, gerador wiki, drift-check e coverage permaneceram verdes; as falhas foram exclusivamente os contratos que exigiam A0041–A0050 enquanto a produção ainda terminava em A0040.
- GREEN exige exatamente A0001–A0050 no catálogo player-facing, A0051 ainda ausente, snapshot factual com A0041–A0050 descritos e shard A0041–A0050 sem drift.

O Stage 03.06 global continua aberto depois desta fatia.
