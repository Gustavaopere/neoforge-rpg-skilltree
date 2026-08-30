# A0016 — Distância Ideal

## Status e proveniência

- **Design:** APROVADO após reauditoria obrigatória.
- **Código relevante:** PRESENTE.
- **Implementação:** CONFIRMÁVEL após CI/merge.
- **Notion:** https://app.notion.com/p/3c569db9f0db81c790c3d12c7669eca0
- **Critérios locais:** `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`.

## Especificação canônica

A0016 é a Notable de Controle de Distância. Exige A0015 ≥2. Hit direto de lança entre 70% e 100% do alcance efetivo gera 1 carga, cap 3. Rank 1 expira o estado inteiro 5 s após o último ganho; rank 2, 7 s. Miss confirmado remove 1; stagger forte hostil remove 1. Somente ganhos renovam duração. Sem alcance seguro, ganho fica fail-closed; nunca inferir alcance por animação.

## Auditoria — 9 eixos

1. **Gates:** PASS — A0015 ≥2 + gateway.
2. **Integração global:** PASS — estado transitório único e alcance provider-native.
3. **Identidade:** PASS — posicionamento real com janela geométrica e penalidades.
4. **Topologia:** PASS — camada 3 preparando A0018.
5. **Especializações:** PASS — exterior.
6. **PT-BR:** PASS.
7. **Notion:** PASS — faixa, cap, duração, perdas e fallback explícitos.
8. **NeoVitae:** PASS.
9. **Modlist/integrações:** PASS — alcance, miss e stagger fortes têm receipts Epic Fight.

## Evidência técnica

- `NotionCombatPerkRules`: cap 3, 70–100%, duração 5/7 s.
- `A0001A0020CombatPolicy.isIdealSpearRange`: limites inclusivos.
- `A0001A0020EpicFightHooks.onDamagePre`: `entityInteractionRange + capability.getReach()`.
- `afterConfirmedHit`: +1 somente em hit SPEAR ideal, deduplicado.
- `ATTACK_PHASE_END`: miss confirmado, −1.
- `ON_STUNNED`: `LONG`, `KNOCKDOWN` ou `NEUTRALIZE` com fonte hostil; `onConfirmedHostileHeavyStagger` remove 1 Controle de Distância.
- `NotionCombatPerkState`: expiração, consumo e lifecycle cleanup.

## Pendências

**Nenhuma bloqueante.** A antiga P-A0016-01 foi fechada pelo receipt provider-native de stagger forte. Fora do Epic Fight, ausência de alcance seguro continua fail-closed conforme o próprio contrato.

## Testes

- [x] cap e 5/7 s;
- [x] faixa inclusiva 70–100%;
- [x] ganho por hit e miss confirmado;
- [x] perda por stagger forte;
- [x] lifecycle cleanup;
- [x] CI/build e dedicated-server smoke exigidos antes do merge.

## Auditoria retroativa de integração — projetos próprios + Mobstein 5.4.4 — lote A0011–A0020

- **Resultado:** APROVADA com boundary de autoria retroativa; a geometria/alcance continuam Epic Fight/RPG-owned.
- **RPG Skill Tree:** Controle de Distância é estado transitório do jogador, server-authoritative, deduplicado e alimentado apenas por ação marcial direta elegível.
- **Black Arcana:** `ARCANE_BACKLASH` não é acerto direto de lança e não gera/renova Controle de Distância.
- **Mobstein 5.4.4:** ataques de allies/bodyguards ressuscitados não geram cargas para o dono; ataques diretos do jogador contra entidades Mobstein continuam elegíveis quando cumprem família/alcance/receipt.
- **Volcanoes / Enshrouded:** NÃO DEVE SER INTEGRADO à geração de cargas; hazards, Shroud, Exposure ou Story não substituem distância/alcance do combate.
- **Notion:** `Hook`, `Fallback` e `Regra` corrigidos em 2026-08-30; re-fetch confirmou persistência.
- **Chat 2:** preservar direct-player provenance e o fail-closed de alcance; não atribuir cargas por eventos secundários ou companion-owned.
