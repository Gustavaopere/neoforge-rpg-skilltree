# Skill Tree Plan — Graph and Layout Validation

**Status:** ✅ concluído em 2026-08-29.

**Goal:** garantir que a malha 512/512 seja estruturalmente alcançável e coerente.

- [x] Detectar ciclos inválidos e requisitos inalcançáveis.
- [x] Validar roots, regiões, final triads, bridges e keystones.
- [x] Confirmar orçamento `target_node_count = 512` e `actual_node_count = 512` enquanto esse contrato for canônico.
- [x] Validar posições/links consumidos pela UI.
- [x] Impedir node orphan sem intenção explícita.

## Contrato implementado

- `SkillTreeTopologyValidator` rejeita relações de vizinhança assimétricas, árvores sem `startingPoint`, nós não alcançáveis a partir de qualquer root e ciclos dirigidos formados por `requiredNodes`/`requiredNodeRanks`.
- `PreparedSkillTreeData` executa o topology gate antes de um candidato poder ser publicado no catálogo atômico do Stage 03.01.
- `CanonicalMainTreeTopologyJUnitTest` prova o contrato canônico da árvore principal diretamente contra os artefatos reais gerados e consumidos pelo runtime/UI.
- A árvore principal possui exatamente 512 nós e mantém paridade exata de IDs entre `generated/main-tree-layout.json`, `node_rules/main.json` e os 512 JSONs `skills/main`.
- As arestas normalizadas de `node_rules` e `directConnections` coincidem exatamente com o layout gerado; as posições `positionX/positionY` também coincidem com `x/y` do layout.
- O único root canônico da árvore principal é `rpgskilltree:core_00`, e ele alcança todos os 512 nós.
- Os budgets das 11 regiões coincidem com o blueprint; `shared_core_nodes`, `hybrid_bridge_nodes` e `outer_keystone_nodes` também são verificados contra o blueprint.
- Existem exatamente 33 nós `final_triad` (3 por domínio), com slots `0/1/2` e regra autoritativa `maxRank = 3` correspondente.

## Evidência

- TDD RED: RPG Skill Tree CI `33278090550` / run #1694 falhou em `:compileTestJava` exclusivamente porque `SkillTreeTopologyValidator` ainda não existia; o Core anterior permaneceu GREEN.
- Head funcional `f641e99e87882575705b88b21fa68d582a1f9db8`: RPG Skill Tree CI `33278390140` / run #1700 passou Core, JUnit 5, NeoForge GameTests, validators, NeoForge build, verificação do JAR e dedicated-server smoke antes deste fechamento documental.
- Os workflows Foundation e Compendium associados ao mesmo head também fecharam GREEN.

**Acceptance:** satisfied — o validator prova navegabilidade/consistência estrutural antes da publicação e a suíte canônica prova que blueprint, layout, regras server-authoritative e dados consumidos pela UI não divergem.