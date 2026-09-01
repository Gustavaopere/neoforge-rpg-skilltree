# A0141 — Adaptação Boreal

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / UNAVAILABLE_NODE.**

No snapshot auditado, `BodyCostResolver`, `AcclimationLedger(ENVIRONMENTAL_COLD)` e um mapper COLD causal/quantificado não estão disponíveis na `main`. Compra nova deve falhar antes do gasto; allocation legado vale 0 PP para gates e permanece reembolsável/migrável.

## Contrato

- Domínio/árvore: SURVIVAL / Principal — SURVIVAL.
- Ramo: Aclimatação — Boreal.
- Camada 5; Keystone; 1 rank; 2 PP.
- Pré-requisitos: A0137 ≥2 + A0138 ≥2 + Gateway SURVIVAL.
- Usa 0–5 cargas `ENVIRONMENTAL_COLD` sem consumi-las.
- Qualquer componente fisiológico/econômico só pode reduzir penalidade/receipt COLD real e explicitamente quantificado.

## Authority e providers

- Cold Sweat 2.4.2 é autoridade térmica read-only.
- RPG Skill Tree será consumer por adapter versionado/AcclimationLedger quando o serviço existir.
- `ADVERSE_COLD` corporal de A0137/A0138 e `ENVIRONMENTAL_COLD` desta perk são estados distintos.
- Volcanoes, bioma, freezing point, ICE damage ou HUD não se tornam segundo provider térmico.

## Availability

Compra exige que A0137/A0138 sejam capability-eligible, `AcclimationLedger(ENVIRONMENTAL_COLD)` esteja live e exista pelo menos um componente COLD real, seguro e versionado para esta perk modificar. Ausência de qualquer binding obrigatório => `UNAVAILABLE_NODE`.

Quando bindings existirem, ausência de estado/carga numa ação específica apenas produz no-op daquela ação; isso não autoriza vender o node antes de existir capability.

## Boundary futuro

`Cold Sweat server-authoritative environmental state → AcclimationLedger(ENVIRONMENTAL_COLD) → mapper de penalidade/receipt COLD real → A0141 reducer → provider settlement uma vez`.

O Skill Tree nunca escreve thresholds, traits térmicas ou estado corporal do provider.

## Exclusões

- não usar BODY/WORLD/freezing point/bioma como aproximação de receipt;
- não inferir custo por polling de hunger/thirst/temperatura;
- não converter dano ICE ou Volcanoes heat em penalidade COLD;
- não criar segunda temperatura nem penalidade artificial.

## Handoff Chat 2

- manter indisponível até todos os bindings obrigatórios existirem;
- se provider/API real divergir, preservar fail-closed e devolver redesign ao Chat 1;
- não implementar bônus substituto.

## Testes Chat 3

1. purchase fail-before-spend e legacy PP 0 no snapshot atual;
2. availability transitiva A0137→A0138→A0141;
3. ENVIRONMENTAL_COLD distinto de ADVERSE_COLD;
4. ledger bounded 0–5, sem progresso offline, com lifecycle/reload/restart;
5. uma única aplicação por receipt real;
6. provider removal/respec/rules reload/dimension/logout;
7. testes negativos para bioma, ICE damage, BODY/WORLD e polling.