# A0304 — Crescimento

## Estado
- **Chat 1:** DESIGN APROVADO / FAIL-CLOSED.
- **Disponibilidade atual:** `UNAVAILABLE_NODE` por A0183.
- **Authority:** TreeUnlock canônico.
- **Fonte:** https://app.notion.com/3c569db9f0db81f8a639e741936d46cd

## Contrato
+5% de cura recebida por rank (+5/+10/+15%) somente em `healing_outcome` explicitamente classificado `NATURE_HEALING` ou `REGEN_HEALING`. Multiplicador `RPG_NATURE_HEALING_RECEIVED` ×1,05/×1,10/×1,15, uma vez após resolução da fonte/quantidade-base e antes do clamp final.

## Gate/closure
Exige `SPECIALIST_UNLOCK:NATURE`; Gate C A0183 está indisponível. Compra fail-before-spend; legacy unavailable = 0 PP em gates e reembolsável/migrável.

## Boundary requerido
`LivingHealEvent` expõe entidade/amount e pode ser boundary final de amount, mas não fornece source/category suficiente. Ainda é necessário `HEALING_OUTCOME_V1` e classifiers/adapters versionados que distingam NATURE/REGEN de food, hunger regen, lifesteal, potion genérica, absorção e outras curas.

## Invariantes
Não cria cura, não gera regen onde não existe e não classifica Iron's/Ars/Hexalia por tema. Um mesmo healing outcome recebe a contribuição uma única vez, preservando source, base amount, overheal e caps nativos.

## Fallback
Heal sem source/category comprovados recebe 0 bônus. Node permanece indisponível enquanto A0183 estiver fechado.

## Testes Chat 3
1. fail-before-spend A0183;
2. NATURE_HEALING/REGEN_HEALING aplicam exatamente +5/+10/+15%;
3. food/hunger/lifesteal/potion/absorption não entram por inferência;
4. sem double application no mesmo outcome;
5. caps/overheal preservados;
6. provider absent/mismatch fail-closed;
7. multiplayer/dedicated server.

## Handoff Chat 2
Não usar `LivingHealEvent` sozinho como classifier. Sem receipt causal, manter indisponível.