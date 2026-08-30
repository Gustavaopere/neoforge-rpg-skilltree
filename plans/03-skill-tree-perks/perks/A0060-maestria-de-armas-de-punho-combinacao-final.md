# A0060 — Maestria de Armas de Punho — Combinação Final

## Estado

- **Design:** APROVADO; fail-closed já estava correto no Notion.
- **Notion:** `3c569db9-f0db-813c-a90b-d92ed2f1ed75`.
- **Runtime:** NÃO CONFIRMADO; capstone permanece inativo por falta de heavy/finalizer receipt.

## Contrato canônico

- A0058 ≥2 + A0059 ≥1 + `combat:fist` ≥80 + gateway `combat_fist`.
- Em 5 Sequências, o próximo heavy/finalizer FIST confirmado consome todas as cargas e recebe +18% dano físico elegível e +25% Impact.
- Se acertar alvo hostil válido, pode recuperar 15% da soma de Stamina **realmente debitada** nas cinco ações que geraram a sequência, somente por receipts causais pós-consumo.
- Sem receipt de Stamina, omitir só a restituição; nunca estimar por barra, config, hunger/exhaustion ou animation timing.
- Cooldown 8/7/6 s para Mastery 80/90/100.

## Evidência runtime

`beforeFistHeavy(...)` possui matemática de A0060, cooldown e fallback de Stamina em `0.0`, explicitamente porque não há receipt causal seguro. O adapter Epic Fight não chama essa rota enquanto não existir heavy/finalizer receipt inequívoco.

## Pendências para Chat 2

- **P-A0060-01:** integrar heavy/finalizer receipt provider-native e liberar o capstone apenas então.
- **P-A0060-02:** manter restituição de Stamina fail-closed até existir ledger causal pós-consumo por cada uma das cinco ações; cada receipt só pode ser reclamado uma vez.
- **P-A0060-03:** A0060 usa a ledger única `combat:fist`; corrigir producer/architecture de A0055 antes de considerar o gate 80 alcançável.

## Boundaries

Backlash, procs, summons/companions e hazards não geram Sequência, heavy receipt ou Stamina ledger. Punchy é visual/compat.

## Notion

Fetch fresco sem drift; nenhuma mutação cosmética.
