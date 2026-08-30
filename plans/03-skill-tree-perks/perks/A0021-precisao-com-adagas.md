# A0021 — Precisão com Adagas

## Estado

- **Design:** APROVADO após auditoria retroativa.
- **Implementação:** presente no pipeline crítico canônico; confirmação definitiva depende do Chat 2.
- **Notion:** `3c569db9-f0db-814c-96e8-d1db9c2402b0`.

## Contrato canônico

- A0019 ≥ 1 + gateway `epic_dagger`.
- 3 ranks, custo 1/rank.
- +3% de chance crítica com adagas por rank, máximo +9%.
- Somente hit direto do jogador classificado provider-native como DAGGER.
- Uma única resolução crítica/root action; nunca segunda rolagem.

## Auditoria — 9 eixos

1. Gates: PASS.
2. Integração global: PASS — reutiliza o resolver crítico canônico.
3. Identidade: PASS — precisão específica de adagas.
4. Topologia: PASS — camada 2.
5. Especializações: PASS — Árvore Exterior.
6. PT-BR: PASS.
7. Notion: PASS após boundary causal.
8. NeoVitae: PASS.
9. Providers: PASS — Epic Fight first; Weapons of Miracles somente se a arma concreta for DAGGER.

## Evidência e boundaries

- `NotionCombatPerkCatalog`/`A0021A0040CombatPolicy` usam a chance crítica específica da família.
- `A0021A0040EpicFightHooks` reutiliza `A0001A0020CriticalService`, preservando uma única resolução.
- `ARCANE_BACKLASH` é terminal e não entra no resolver.
- Dano de ally/bodyguard Mobstein é provider-owned e não herda crítico do dono.
- Volcanoes e Enshrouded não fornecem critical receipt.

## Pendências

Nenhuma de design. Chat 2 deve apenas preservar provenance direta/root action e deduplicação.
