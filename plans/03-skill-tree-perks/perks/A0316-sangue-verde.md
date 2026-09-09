# A0316 — Sangue Verde

## Estado

- **Chat 1:** DESIGN APROVADO / FAIL-CLOSED.
- **Disponibilidade atual:** `UNAVAILABLE_NODE`.
- **Fonte canônica:** Notion `A0316` — https://app.notion.com/3c569db9f0db8163a85ed803fa846422
- **Persistência:** fetch 2026-09-05.

## Contrato aprovado

Uma aplicação hostil e real de **POISON** pode ativar Sangue Verde. Quando o provider não expuser identity de aplicação segura, o fallback causal permitido é somente o **primeiro dano hostil positivo de POISON após commit**; esse primeiro dano apenas ativa e não recebe mitigação retroativa.

Ativação:

- duração 100t;
- cooldown 1800t;
- em danos POISON subsequentes, aplicar primeiro a resistência permanente/canônica `RPG_POISON_RESISTANCE` e depois um segundo estágio distinto `RPG_GREEN_BLOOD_POISON_MITIGATION ×0,10`;
- 30% do dano **efetivamente prevenido pelo segundo estágio** vira crédito de cura;
- a cada 20t, pode entregar cura derived limitada a 2% da vida máxima atual por pulse;
- total de cura efetiva por ativação: máximo 8% da vida máxima;
- overheal não conta como cura efetiva e crédito não usado expira com a ativação.

## Gate e closure

Compra exige Specialist Natureza/A0183, A0303 3/3, (A0311 **ou** A0312) e Nature Mastery ≥90. A closure torna o node indisponível.

## Authority e pipeline

- RPG Skill Tree: activation state, segundo mitigador, heal-credit ledger e caps.
- POISON classifier deve ser canônico e compartilhado; não classificar qualquer `MobEffect`/magic damage como Poison.
- A0303/resistência permanente é primeiro reducer. A0316 é segundo reducer com effect ID distinto, aplicado uma vez.
- Healing derived usa o pipeline canônico de heal e não se apresenta como heal direto NATURE.

## Anti-abuso

Primeiro evento causal não é mitigado retroativamente. Crédito usa **actual prevented damage** do estágio A0316, nunca requested damage. Overheal não cria crédito/efetividade fictícia. Um dano recebe cada reducer uma vez. Derived heal não retriggera A0313 nem outros direct-heal procs.

## Fallback

Sem POISON classifier ou shared mitigation lane seguro, node/componente falha fechado. Não observar simplesmente o efeito Poison no tick e não devolver HP estimado depois do fato.

## Testes obrigatórios para Chat 3

1. fail-before-spend;
2. aplicação hostil real ativa; fallback por primeiro positive POISON damage só ativa e não mitiga retroativamente;
3. duração 100t e cooldown 1800t;
4. ordem `RPG_POISON_RESISTANCE` → A0316 ×0,10, cada um uma vez;
5. heal credit = 30% do actual prevented segundo estágio;
6. pulse 20t, cap 2% max-health por pulse e 8% de cura efetiva por ativação;
7. overheal não conta e crédito expira;
8. self/ally/non-POISON/ambiguous não ativa;
9. derived heal não retriggera direct-heal effects;
10. provider absent/version mismatch, multiplayer e dedicated server.

## Handoff Chat 2

Não criar reducer POISON paralelo fora do compositor canônico e não converter prevenção estimada em cura.