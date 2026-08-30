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

## Fechamento Chat 1 V3 — ciclo exato A0011–A0020

- **Re-fetch canônico:** Notion consultado novamente em 2026-08-30; faixa ideal, duração, perdas, gate, hook e fallback persistem sem drift.
- **Mutação no Notion neste ciclo:** não necessária.
- **Hooks confirmados:** reach provider-native, hit direto, `ATTACK_PHASE_END` para miss e `ON_STUNNED` para stagger forte hostil.
- **Anti-abuso:** ganho exige dano direto/hostil confirmado na faixa 70–100%; claims deduplicam root actions e somente ganhos renovam a duração compartilhada.
- **Fail-closed:** sem alcance efetivo/canônico seguro, a geração por faixa ideal fica inativa; nunca inferir por animação ou aparência da arma.
- **Resultado:** **APROVADA / FECHADA** no lote A0011–A0020.