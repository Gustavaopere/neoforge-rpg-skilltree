# Stage 03.06 — texto player-facing A0021–A0030

Esta fatia continua o plano `06-content-wiki-generation.md` depois da materialização factual A0001–A0100 da PR #261.

## Escopo fechado

- publicar texto PT-BR player-facing somente para A0021–A0030;
- derivar cada descrição exclusivamente dos dossiês canônicos aprovados em `plans/03-skill-tree-perks/perks/`;
- preservar explicitamente os boundaries provider-native e estados fail-closed de A0028, A0029 e A0030;
- manter A0031+ sem descrição player-facing até auditoria/implementação própria;
- regenerar `wiki/combat-perks/A0021-A0030.md` pelo pipeline factual já existente;
- não alterar gameplay, coefficients, gates ou provider adapters nesta fatia.

## Autoridades

- design/texto: dossiês A0021–A0030 já aprovados;
- nomes: `NotionCombatPerkCatalog`;
- gates/ranks/custos: `CombatPerkTreeModel`;
- apresentação versionada: `CombatPerkPlayerTextCatalog`;
- geração factual: `CombatPerkWikiSnapshotGenerator` + `scripts/wiki_catalog.py`.

A PR #248 já foi mergeada em `main` como `c6faec4e889386b338b9205845efbcd8e0e9a747`. Isso permite promover o texto aprovado desse lote sem inferir conteúdo de A0031+.

## TDD

O RED deve demonstrar que o catálogo atual ainda contém somente A0001–A0020 e que o snapshot mantém A0021 sem descrição. O GREEN exige exatamente A0001–A0030 no catálogo player-facing, A0031 ainda ausente e o wiki drift-check consistente com o shard regenerado.

O Stage 03.06 global continua aberto depois desta fatia.