# A0301 — Toxicidade

## Estado
- **Chat 1:** DESIGN APROVADO / FAIL-CLOSED.
- **Disponibilidade atual:** `UNAVAILABLE_NODE` por A0183/Specialist Natureza.
- **Authority:** TreeUnlock canônico; nenhum resolver Specialist paralelo.
- **Fonte:** https://app.notion.com/3c569db9f0db811a8342c86279a9f4c9

## Contrato
+5% de dano POISON por rank (+5/+10/+15%) somente em pulsos periódicos de uma aplicação POISON cuja autoria pertença ao jogador. A contribuição é snapshotada no commit da aplicação legítima. Contra BOSS, apenas a contribuição da perk recebe coeficiente 0,50: ×1,025/×1,05/×1,075.

## Gate/closure
Exige `SPECIALIST_UNLOCK:NATURE`. Gate C A0183 está `UNAVAILABLE_NODE`; compra falha antes do gasto. Allocation legado unavailable vale 0 PP para gates/thresholds e é reembolsável/migrável.

## Boundary requerido
`MobEffectEvent.Added.getEffectSource()` é apenas primitive de source. Ainda faltam `poison_application_id`, ownership normalizado, atribuição de cada pulse e classifier BOSS/NON_BOSS. Vanilla, Iron's, Ars e Toxony só entram por adapters explícitos/versionados.

## Causalidade/dedup
Um bônus por pulse associado à mesma aplicação canônica. Generic MAGIC, presença visual de Poison, namespace ou efeito sem owner não recebem crédito. Pulse não gera Mastery adicional.

## Fallback
Sem application ledger/ownership/pulse attribution seguros, bônus = 0. O node permanece não comprável enquanto A0183 estiver fechado.

## Testes Chat 3
1. fail-before-spend com Specialist Natureza fechado;
2. owner correto vs self/ally/ambiguous source;
3. mesma aplicação não duplica bônus por callback;
4. BOSS aplica coeficiente somente à contribuição A0301;
5. generic MAGIC/NATURE não vira POISON;
6. provider absent/mismatch fail-closed;
7. multiplayer/dedicated server.

## Handoff Chat 2
Não implementar pulse attribution por heurística. Se não houver ledger causal seguro, preservar `UNAVAILABLE_NODE`.