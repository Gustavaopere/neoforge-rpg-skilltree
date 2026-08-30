# A0004 — Ritmo do Duelista

## Status e proveniência

- **Design:** APROVADO após reauditoria obrigatória.
- **Código relevante:** PRESENTE.
- **Implementação:** CONFIRMÁVEL após CI/merge desta auditoria.
- **Notion:** https://app.notion.com/p/3c569db9f0db81aeb549d2500a67c0f4
- **Critérios locais:** `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`.

## Especificação canônica

A0004 é a Notable de Ímpeto da árvore de Espadas. Exige A0003 ≥2. Hit direto válido de espada gera 1 Ímpeto até 5; defesa técnica comprovada também pode gerar 1. Miss confirmado remove 1; stagger forte hostil remove 2; após 5 s sem ganho elegível, perde 1/s. Ganhos reiniciam a graça; perdas não. Estado limpo em morte/logout/troca de dimensão.

## Auditoria — 9 eixos

1. **Gates:** PASS — A0003 ≥2 reproduzido.
2. **Integração global:** PASS — Ímpeto é estado MARTIAL próprio, sem duplicar stamina.
3. **Identidade:** PASS — recompensa sequência limpa e defesa técnica.
4. **Topologia:** PASS — Notable camada 3.
5. **Especializações:** PASS — identidade exterior, sem classe automática.
6. **PT-BR:** PASS.
7. **Notion:** PASS — cap, decay, perdas, limpeza e dedup definidos.
8. **NeoVitae:** PASS.
9. **Modlist/integrações:** PASS — Epic Fight fornece hit/dodge/miss e agora stagger forte provider-native.

## Evidência técnica

- `A0001A0020CombatPolicy.afterConfirmedHit`: +1 Ímpeto com `claimOnce`.
- `onConfirmedTechnicalDefense`: defesa técnica; `ON_DODGE` é dodge bem-sucedido provider-native.
- `ATTACK_PHASE_END` com lista real vazia: miss confirmado, −1.
- `EpicFightEventHooks.Entity.ON_STUNNED`: receipt server-side para perda por stagger.
- O adapter só classifica `StunType.LONG`, `KNOCKDOWN` e `NEUTRALIZE` como stagger forte e exige fonte hostil antes de chamar `onConfirmedHostileHeavyStagger`, que remove 2 Ímpeto.
- `NotionCombatPerkState`: cap 5, graça de 5 s, decay 1/s e limpeza.

## Pendências

**Nenhuma bloqueante.** A antiga lacuna do stagger foi fechada por evento provider-native. Aparo/guarda perfeita adicionais continuam opcionais: só poderão gerar Ímpeto se existir receipt independente e seguro; sua ausência não é substituída por heurística.

## Testes

- [x] ganho/cap/decay;
- [x] miss confirmado;
- [x] dodge confirmado;
- [x] perda de 2 por stagger forte;
- [x] lifecycle cleanup;
- [x] CI/build e dedicated-server smoke exigidos antes do merge.

## Fechamento Chat 1 V3 — ciclo exato A0001–A0010

- **Re-fetch canônico:** Notion consultado novamente em 2026-08-30; geração, perdas, decay, cleanup, dependência e fallback permanecem coerentes.
- **Mutação no Notion neste ciclo:** não necessária.
- **Hooks confirmados:** hit direto, `ON_DODGE`, `ATTACK_PHASE_END` e `ON_STUNNED`; stagger forte aceita apenas `LONG`, `KNOCKDOWN` e `NEUTRALIZE` com fonte hostil.
- **Anti-abuso/deduplicação:** um resultado não gera duas cargas; miss/stagger não são inferidos por distância, animação ou dano bruto; estado transitório é limpo nos lifecycles definidos.
- **Integração global:** Ímpeto continua recurso MARTIAL próprio e não substitui nem duplica stamina de Epic Fight/ParCool.
- **Resultado:** **APROVADA / FECHADA** no lote A0001–A0010.

## Chat 2 — implementação, testes e merge — PR #221

**Estado:** `IMPLEMENTAÇÃO VALIDADA EM CI`; torna-se `IMPLEMENTAÇÃO CONFIRMADA` após merge da PR #221.

- [x] Receipts de hit, defesa técnica, miss e stagger forte implementados.
- [x] Deduplicação por ação/receipt implementada.
- [x] Cap, graça, decay e lifecycle cleanup implementados.
- [x] Stagger forte restrito a receipt provider-native; sem proxies heurísticos.
- [x] Ímpeto permanece separado de stamina.
- [x] Testes de policy/regressão presentes.
- [x] `RPG Skill Tree CI` #1996 verde no HEAD `b99ba35671dc92477c6b767ec4e4c5c22f0c71d0`.
- [x] JUnit, NeoForge GameTests, build, verificação do JAR e dedicated-server smoke verdes.

**Pendências técnicas:** nenhuma bloqueante; novos receipts de aparo/guarda perfeita permanecem opcionais e fail-closed até existir API causal.
