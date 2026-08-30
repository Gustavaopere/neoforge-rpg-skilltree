# A0028 — Abalo Crescente

## Estado

- **Design:** APROVADO após auditoria retroativa.
- **Implementação:** PARCIAL; `P-A0028-01` aberta.
- **Notion:** `3c569db9-f0db-8150-81cb-e559d7123a3d`.

## Contrato canônico

- A0027 ≥2 + gateway `epic_hammer`.
- Hit direto HAMMER/heavy do jogador gera 1 Abalo ator→alvo, cap 3.
- Rank 1/2: cada carga aumenta em +8%/+12% a pressão de guarda/postura do próximo golpe de martelo contra o mesmo alvo.
- Sem dano bruto adicional; estado do alvo expira 6 s após o último ganho e não transfere.
- Se pressão de guarda/postura não estiver disponível por receipt seguro, o benefício fica inativo; não converter para impacto/dano/knockback/crítico/Armor Negation.

## Auditoria — 9 eixos

1. Gates: PASS.
2. Integração: PASS de design — estado RPG + pressão provider-native.
3. Identidade: PASS — preparação crescente contra postura.
4. Topologia: PASS — Notable.
5. Especializações: PASS — exterior.
6. PT-BR: PASS.
7. Notion: PASS após boundary de guarda.
8. NeoVitae: PASS.
9. Providers: PASS de design; runtime em fallback parcial.

## Evidência e pendência

- `A0021A0040CombatState`/policy registram Abalo por alvo e cap/expiração.
- O adapter atual chama o policy com `guardPressureAvailable=false`; portanto as cargas existem, mas a parcela +8%/+12% não é aplicada.
- **P-A0028-01:** Chat 2 deve integrar somente receipt/provider-native seguro de pressão de guarda/postura do Epic Fight. Se não existir na versão real, manter o benefício fail-closed e A0028 sem implementação completa; não inventar substituto.
- Arcane Resistance/Corruption Resistance, Shroud/Exposure/Madness não são guarda/postura.
- `ARCANE_BACKLASH`, hazards e companions Mobstein não geram Abalo para o jogador.

`P-A0028-01` bloqueia a confirmação do efeito completo, não o design.
