# A0302 — Virulência

## Estado
- **Chat 1:** DESIGN APROVADO / FAIL-CLOSED.
- **Disponibilidade atual:** `UNAVAILABLE_NODE` por A0183→A0301.
- **Authority:** TreeUnlock canônico.
- **Fonte:** https://app.notion.com/3c569db9f0db817483e0d4f61aac9037

## Contrato
+8% de duração do Veneno aplicado pelo jogador por rank (+8/+16/+24%), sem alterar frequência de pulse nem dano-base. Para cada aplicação/renewal elegível: `duration_final = duration_native_before_A0302 × (1 + 0.08×rank)`, respeitando somente hard caps nativos documentados. Nunca reaplicar o multiplicador sobre duração já aumentada por A0302.

## Gate/closure
Exige `SPECIALIST_UNLOCK:NATURE` + A0301≥2. Gate C A0183 e A0301 estão indisponíveis; compra fail-before-spend. Legacy unavailable = 0 PP para gates e reembolsável/migrável.

## Boundary requerido
`MobEffectEvent.Added` pode observar lifecycle/source, mas não deve ser promovido a hook mutável precommit. O contrato exige `POISON_DURATION_MODIFIER_V1` ou boundary provider-native equivalente sobre a candidate duration **antes** do commit/renewal.

## Causalidade e invariantes
Preservar amplifier, source, owner/application identity e pulse cadence. É proibido remover/reaplicar efeito após o fato para simular duração, fazer refresh por tick ou criar aplicação nova.

## Fallback
Sem hook precommit seguro, contribuição = 0; node continua indisponível enquanto closure externa estiver fechada.

## Testes Chat 3
1. fail-before-spend A0183/A0301;
2. +8/+16/+24% exatamente uma vez;
3. renewal usa duração nativa candidata, sem crescimento exponencial;
4. amplifier/pulse cadence/source preservados;
5. ausência de remove/re-add e callbacks duplicados;
6. provider hard cap respeitado;
7. provider absent/mismatch fail-closed;
8. multiplayer/dedicated server.

## Handoff Chat 2
Não redesenhar duração por listener reativo. Sem precommit seguro, manter indisponível.