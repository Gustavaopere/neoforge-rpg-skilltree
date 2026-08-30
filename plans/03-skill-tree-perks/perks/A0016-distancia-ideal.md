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

## Chat 2 — implementação, testes e merge — PR #224

**Estado:** `IMPLEMENTAÇÃO VALIDADA EM CI`; torna-se `IMPLEMENTAÇÃO CONFIRMADA` após merge da PR #224.

- [x] Faixa ideal usa alcance efetivo server-side e limites inclusivos 70–100%.
- [x] Ganho de Controle de Distância é deduplicado e limitado a 3 cargas.
- [x] Duração 5/7 s, miss −1, stagger forte −1 e lifecycle cleanup preservados.
- [x] Stagger forte continua restrito a receipts `LONG`, `KNOCKDOWN` e `NEUTRALIZE` hostis.
- [x] Ausência de alcance seguro mantém o ganho fail-closed.
- [x] Testes de policy cobrem faixa, duração, miss e stagger.
- [x] `RPG Skill Tree CI` #2036 GREEN no SHA `bda08ca9748ad16d3352d0872f753976731424f8`.
- [x] JUnit, NeoForge GameTests, build, built-JAR verification e dedicated-server smoke verdes.

**Pendências técnicas:** nenhuma para A0016.
