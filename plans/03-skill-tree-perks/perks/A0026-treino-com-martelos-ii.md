# A0026 — Treino com Martelos II

## Estado

- **Design:** APROVADO; re-fetch sem drift e sem mutação cosmética.
- **Implementação:** presente via attack-speed provider-native; confirmação definitiva depende do Chat 2.
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

- `A0021A0040EpicFightHooks` possui caminho de attack-speed para a família HAMMER.
- Volcanoes, Enshrouded e Black Arcana não fornecem cadência de martelo.
- Companions Mobstein não herdam A0026 do dono.

## Pendências

Nenhuma de design. A validade do hook deve ser revalidada pelo Chat 2 junto da correção provider-native de A0025; se a família HAMMER não puder ser classificada com segurança, A0026 também fica fail-closed para o item.
