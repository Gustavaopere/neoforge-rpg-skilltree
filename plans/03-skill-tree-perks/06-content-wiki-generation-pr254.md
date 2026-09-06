# Stage 03.06 — Catálogo factual das perks semânticas

Esta nota registra a fatia executada pela PR #254. Ela complementa `06-content-wiki-generation.md` e **não fecha o Stage 03.06 inteiro**.

## Escopo fechado nesta fatia

- a árvore server-authoritative `rpgskilltree:runtime/combat_perks` passa a possuir catálogo factual regenerável para A0001–A0100;
- `CombatPerkTreeModel` continua sendo a autoridade de aquisição, ranks, custos, nível mínimo, mastery e dependências;
- `NotionCombatPerkCatalog` continua sendo a autoridade versionada dos nomes;
- `CombatPerkPlayerTextCatalog` só fornece descrições já auditadas; ausência permanece `—`;
- nenhuma policy Java é convertida em prosa e nenhum efeito é inferido;
- o snapshot intermediário é derivado em `build/generated-wiki/combat-perks.json` e não é versionado como segunda fonte de verdade;
- `PERK_CATALOG.md` continua reservado à malha histórica de 512 nós;
- `COMBAT_PERK_CATALOG.md` indexa dez arquivos gerados, em lotes A0001–A0010 até A0091–A0100;
- `python3 scripts/generate-wiki-catalog.py --check` valida também todos os dez lotes e falha se um estiver ausente ou divergente.

## Evidência TDD

- CI #2295: RED — `CombatPerkWikiSnapshotGenerator` ainda inexistente;
- CI #2300: RED seguinte — implementação compilava, mas o contrato exigia materialização do snapshot;
- CI #2322: RED da integração Python — três falhas específicas para consumo do snapshot, fail-closed e presença de A0021;
- CI #2328: Core e oito testes do gerador GREEN; única falha restante foi drift do catálogo factual ainda não materializado;
- a fatia final só pode ser considerada integrada após o HEAD final passar a matriz completa do RPG Skill Tree CI, incluindo drift-check, JUnit, NeoForge GameTests, build, verificação do JAR e dedicated-server smoke.

## O que permanece aberto no Stage 03.06

O fechamento global continua dependendo da revisão dos nós estruturais, da conclusão de todo texto player-facing da release e do balanceamento final de triads/bridges/keystones. A existência do catálogo factual não substitui essas decisões de design.
