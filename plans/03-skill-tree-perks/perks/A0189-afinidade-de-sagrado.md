# A0189 — Afinidade de Sagrado

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE`.**

A perk continua termicamente causal, não “luz = calor”. `MAGIC_THERMAL_PARCEL_V1` está ausente e a dependency closure obrigatória passa por A0184, que está unavailable.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db81248281debe82bc5452`.

## Contrato

- ARCANE ↔ HEALING; camada 6; Keystone exterior; 1 rank; 2 PP.
- Dependências: A0184 ≥3 + Holy Mastery ≥30 + pelo menos um entre A0185=1, A0187≥2 ou A0188≥2.
- Somente parcela térmica **positiva** explicitamente atribuída a cast HOLY próprio: ×0,80.
- Exposição mágica HOLY externa com parcela positiva explícita: ×0,90.
- HOLY sem parcela térmica explícita permanece neutro.
- Não altera dano, cura, absorção ou `RPG_HOLY_RESISTANCE`.

## Authority

Cold Sweat 2.4.2 permanece único owner da temperatura corporal. A0189 não escreve BODY/CORE/RATE/thresholds diretamente.

Iron's 3.16.3 e Eidolon: Repraised 0.5.0.2 podem fornecer identidade HOLY, mas school, luz, ritual, cura ou religião não provam parcela térmica.

## Blocker — `MAGIC_THERMAL_PARCEL_V1`

Receipt futuro deve conter action/origin, HOLY explícito, signed thermal delta e fase anterior à aplicação no Cold Sweat. Para A0189, apenas `signed_delta > 0` é elegível.

Não inferir calor pela diferença global before/after de BODY.

## Pipeline futuro

`HOLY action com thermal parcel positivo -> MAGIC_THERMAL_PARCEL_V1 -> A0189 transforma -> Cold Sweat aplica uma vez`.

- self: `delta * 0.80`;
- external magic: `delta * 0.90`.

Uma action/parcela recebe A0189 no máximo uma vez.

## Fail-closed

Enquanto thermal parcel ou dependency closure estiverem fechados:

- compra falha antes do gasto;
- legacy rank unavailable vale 0 PP e é reembolsável/migrável;
- não inferir por luz, cura, absorção, dano HOLY, oração ou estética;
- não degradar para dano/resistência/bônus ambiental;
- não criar temperatura paralela.

## Specialist region

`PP_REGION: ARCANE/HOLY`. Keystone exterior; não Specialist nem terminal. `SPECIALIST_REGION:HOLY` usa núcleo ARCANE compartilhado elegível + ARCANE/HOLY; bridges ficam fora salvo whitelist explícita única.

## Handoff Chat 2

Preservar `UNAVAILABLE_NODE`; não criar parcel térmico local nem escrever no Cold Sweat. Promoção futura volta ao Chat 1.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend;
2. legacy=0 PP/refund;
3. A0184 unavailable fecha dependency;
4. HOLY sem parcel explícito é neutro;
5. delta zero/negativo não é modulado por A0189;
6. quando capability existir: self positivo ×0,80 e external positivo ×0,90;
7. nenhuma inferência por BODY old/new;
8. nenhuma alteração de dano/cura/absorção/resistência;
9. dedup por action/parcela;
10. provider mismatch/reload/multiplayer fail-closed.