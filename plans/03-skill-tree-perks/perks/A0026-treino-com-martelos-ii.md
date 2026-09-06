# A0026 — Treino com Martelos II

## Estado

- **Design:** APROVADO; re-fetch sem drift e sem mutação cosmética.
- **Implementação:** VALIDADA EM CI na PR #242 via attack-speed provider-native; confirmação definitiva após merge em `main`.
- **Notion:** `3c569db9-f0db-8178-adc8-e5831ed803a7`.

## Contrato canônico

- A0025 ≥2 + gateway `epic_hammer`.
- 3 ranks, custo 1/rank.
- +2% de velocidade/ritmo efetivo com martelos por rank, máximo +6%.
- Somente cadência/attack speed server-authoritative do Epic Fight; não converter para stamina, movimento, dano ou animação.
- Sem hook estável, o efeito fica inativo.

## Auditoria — 9 eixos

1. Gates: PASS.
2. Integração: PASS — cadence owner Epic Fight.
3. Identidade: PASS como progressão basal.
4. Topologia: PASS — camada 2.
5. Especializações: PASS — exterior.
6. PT-BR: PASS.
7. Notion: PASS sem alteração necessária.
8. NeoVitae: PASS.
9. Providers: PASS — nenhum projeto próprio/Mobstein altera a cadência MARTIAL do jogador.

## Evidência e boundaries

- `A0021A0040EpicFightHooks` possui caminho `ModifyAttackSpeedEvent` para a família HAMMER.
- A correção de A0025 removeu a classificação paralela por tag: A0026 só alcança HAMMER quando a capability Epic Fight comprova a família.
- Volcanoes, Enshrouded e Black Arcana não fornecem cadência de martelo.
- Companions Mobstein não herdam A0026 do dono.

## Pendências

Nenhuma bloqueante após `P-A0025-01` ser resolvida na PR #242. Categoria desconhecida continua fail-closed.

## Chat 2 — implementação e regressão — PR #242

- Attack speed permaneceu no hook provider-native, sem conversão para stamina/movimento/dano/animação.
- O boundary HAMMER agora é exclusivamente Epic Fight para este lote.
- CI #2192 validou o caminho juntamente com GameTests, build e dedicated-server smoke.
