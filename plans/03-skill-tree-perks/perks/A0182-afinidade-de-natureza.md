# A0182 — Afinidade de Natureza

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE`.**

A perk preserva a identidade de afinidade térmica causal, mas não pode ser adquirida no snapshot atual porque `MAGIC_THERMAL_PARCEL_V1` ainda não existe e a dependency closure obrigatória passa por A0177/A0178/A0181, que permanecem indisponíveis.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db8118815bdb348fb9c0ac`.

## Contrato de gameplay

- ARCANE ↔ SURVIVAL/HEALING; camada 6; Keystone exterior; 1 rank; 2 PP.
- Dependências: A0177 ≥3 + Nature Mastery ≥30 + pelo menos um entre A0178=1, A0180≥2 ou A0181≥2.
- Conjuração NATURE própria com parcela térmica explícita: multiplicar somente essa parcela por `0,80`, preservando o sinal quente/frio.
- Exposição mágica NATURE externa explicitamente adaptada: multiplicar somente a parcela causal por `0,90`.
- NATURE sem parcela térmica explícita permanece termicamente neutro.
- Não altera dano, cura, controle ou `RPG_NATURE_RESISTANCE`.

## Authority

Cold Sweat 2.4.2 é o único owner da temperatura corporal. A0182 pode transformar uma parcela causal antes da aplicação, mas não cria segunda temperatura e não escreve diretamente BODY/CORE/RATE/BURNING_POINT/FREEZING_POINT.

Providers NATURE só classificam origem/ação quando houver adapter versionado. Iron's 3.16.3, Ars Nouveau 5.13.1, Ars Elemental 0.7.10.1 e Hexalia 1.3.5 não autorizam inferência térmica por escola, estética ou ambiente.

## Blocker canônico — `MAGIC_THERMAL_PARCEL_V1`

Receipt futuro mínimo:

- `action_id`/identidade causal estável;
- actor/origin;
- elemento NATURE explícito;
- signed thermal delta daquela parcela;
- fase anterior à mutação canônica do Cold Sweat.

O evento global de mudança de temperatura não prova qual ação produziu qual parcela. Não repartir retrospectivamente BODY old/new.

## Pipeline futuro obrigatório

`provider NATURE causal -> MAGIC_THERMAL_PARCEL_V1 -> A0182 transforma signed_delta elegível -> Cold Sweat aplica a parcela uma única vez`.

- self NATURE: `delta * 0.80`;
- external magic NATURE: `delta * 0.90`.

Uma mesma action/parcela recebe A0182 no máximo uma vez.

## Fail-closed

Enquanto faltar thermal parcel ou dependency closure:

- compra falha antes do gasto;
- rank legado unavailable vale 0 PP e permanece reembolsável/migrável;
- não inferir calor/frio por bioma, planta, veneno, temperatura atual, school isolada ou delta global;
- não degradar para dano, cura, resistência ou bônus ambiental.

## Specialist region

`PP_REGION: ARCANE/NATURE`. A0182 é Keystone exterior, não Specialist e não terminal. `SPECIALIST_REGION:NATURE` usa núcleo ARCANE compartilhado elegível + ARCANE/NATURE; bridges só entram quando explicitamente whitelisted uma vez.

## Handoff Chat 2

Preservar `UNAVAILABLE_NODE`. Não escrever diretamente no Cold Sweat e não criar parcel térmico local só para esta perk. Se um boundary térmico causal real surgir, retornar ao Chat 1 antes de mudar a availability.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend;
2. legacy unavailable rank = 0 PP e refund/migration;
3. ausência de inferência por BODY old/new;
4. ausência de escrita direta BODY/CORE/RATE/thresholds;
5. NATURE sem parcel explícito permanece termicamente neutro;
6. quando capability existir: self ×0,80 e external ×0,90, sinal preservado;
7. uma transformação por action/parcela;
8. nenhum efeito em dano/cura/controle/resistência;
9. provider absent/version mismatch fail-closed;
10. reload/multiplayer/dimension safety.