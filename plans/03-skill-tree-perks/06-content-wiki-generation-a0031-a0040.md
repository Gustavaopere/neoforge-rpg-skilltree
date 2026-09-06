# Stage 03.06 — texto player-facing A0031–A0040

Esta fatia continua o plano `06-content-wiki-generation.md` depois da publicação A0021–A0030 da PR #266.

## Escopo fechado

- publicar texto PT-BR player-facing somente para A0031–A0040;
- derivar cada descrição exclusivamente dos dossiês canônicos aprovados em `plans/03-skill-tree-perks/perks/`;
- preservar explicitamente classificação provider-native/fail-closed e estados de implementação parcial/não confirmada de A0031, A0035, A0036, A0037 e A0040;
- manter A0041+ sem descrição player-facing;
- regenerar `wiki/combat-perks/A0031-A0040.md` pelo pipeline factual já existente;
- não alterar gameplay, coefficients, gates, provider adapters nem resolver pendências Chat 2 nesta fatia.

## Autoridades

- design/texto: dossiês A0031–A0040 já aprovados;
- nomes: `NotionCombatPerkCatalog`;
- gates/ranks/custos: `CombatPerkTreeModel`;
- apresentação versionada: `CombatPerkPlayerTextCatalog`;
- geração factual: `CombatPerkWikiSnapshotGenerator` + `scripts/wiki_catalog.py`.

## Boundaries preservados

- A0031: MACE usa `minecraft:mace` por identidade exata ou classificação/mapping provider-native seguro; classificador paralelo não vira autoridade. A mastery canônica é anti-farm por tipo hostil distinto, e a implementação permanece não confirmada enquanto o runtime legado não for corrigido.
- A0035: Armadura Fendida requer commit pós-hit confirmado; o texto não mascara o gap transacional PRE→POST nem promete atenuação Mobstein sem classificação boss comprovada.
- A0036: Quebra-Ossos permanece fail-closed sem heavy receipt provider-native, ambos os debuffs de Descompasso e sequencing que exija Armadura Fendida anterior à ação atual.
- A0037: SCYTHE exige categoria/capability Epic Fight ou mapping explícito; enxada, nome/aparência e tag paralela não qualificam. Mastery mantém o contrato anti-farm aprovado.
- A0040: aplicação/maturação da Marca são descritas, mas o cleanup bounded em unload/despawn continua explicitamente não confirmado.

## TDD

- RED: RPG Skill Tree CI #2384, HEAD `a61e3134870f8338d19f290d74bd75172ade0a40`, falhou somente em JUnit 5 com 5 falhas esperadas. Core, gerador wiki, drift-check e coverage permaneceram verdes; as falhas foram exclusivamente os contratos que exigiam A0031–A0040 enquanto a produção ainda terminava em A0030.
- GREEN exige exatamente A0001–A0040 no catálogo player-facing, A0041 ainda ausente, snapshot factual com A0031–A0040 descritos e shard A0031–A0040 sem drift.

O Stage 03.06 global continua aberto depois desta fatia.