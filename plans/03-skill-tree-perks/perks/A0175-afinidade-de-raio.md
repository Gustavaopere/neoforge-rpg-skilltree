# A0175 — Afinidade de Raio

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE`.**

Chat 1 não implementa runtime. A identidade da perk é afinidade térmica causal com magia LIGHTNING; ela não pode observar a mudança global de temperatura depois do fato nem criar uma segunda authority térmica.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db81c2b13dd115e30c71f9`.

## Contrato de gameplay

- ARCANE/LIGHTNING; camada 6; Keystone; 1 rank; 2 PP.
- Dependências: A0170 rank ≥3 + Lightning Mastery ≥30 + pelo menos um entre A0171=1, A0173≥2 ou A0174≥2.
- Parcela térmica explícita de conjuração LIGHTNING própria: multiplicar por `0,75`, preservando o sinal definido pelo adapter.
- Parcela térmica explícita de exposição mágica LIGHTNING externa reconhecida: multiplicar por `0,90`.
- LIGHTNING sem componente térmico explícito é termicamente neutro.
- Não aumenta dano, movimento, Resistência a Raio, estado elétrico ou FE.

## Authority

Cold Sweat 2.4.2 permanece **única autoridade da temperatura corporal**.

Iron's 3.16.3 / Ars Nouveau 5.13.1 / Ars Elemental 0.7.10.1 podem fornecer identidade LIGHTNING e, somente quando comprovado por adapter versionado, uma parcela térmica causal daquela ação.

A school LIGHTNING isolada **não cria temperatura**.

## Blocker canônico

Capacidade requerida: `MAGIC_THERMAL_PARCEL_V1`.

O receipt deve carregar no mínimo:

- `action_id/outcome_id` estável;
- ator/origem causal;
- identidade LIGHTNING explícita;
- delta térmico assinado daquela ação/parcela;
- fase anterior à aplicação canônica no Cold Sweat.

`TemperatureChangedEvent` old/new global não identifica a parcela causal e não pode ser usado para repartir retroativamente a mudança corporal.

A dependency A0170 também está indisponível enquanto `DIRECT_MAGIC_OUTCOME_V1` faltar.

## Pipeline futuro obrigatório

`provider LIGHTNING causal -> MAGIC_THERMAL_PARCEL_V1 -> A0175 transforma somente signed_delta elegível -> Cold Sweat aplica uma vez`.

Self cast: `signed_delta × 0.75`.
External magic exposure: `signed_delta × 0.90`.

Uma parcela/action recebe A0175 no máximo uma vez.

## Fail-closed / disponibilidade

Enquanto faltar blocker ou dependência:

- compra falha antes do gasto;
- rank legado indisponível vale 0 PP em gates e permanece reembolsável/migrável;
- não inferir parcela por escola, dano LIGHTNING, `IS_LIGHTNING`, FE, partículas ou delta global BODY;
- não escrever BODY/CORE/RATE diretamente;
- não degradar para resistência ambiental, resistência de dano ou bônus ofensivo.

## Mastery e anti-abuso

Lightning Mastery do gate deve vir do serviço canônico com autoria causal. Tick de efeito, cooldown, tempo conectado, temperatura, FE transferida ou permanência em estado elétrico não concedem Mastery por esta perk.

## Handoff Chat 2

Implementar somente availability/fail-closed. Não criar segunda temperatura, não escrever diretamente no Cold Sweat e não inventar thermal delta para spells LIGHTNING que não o possuam.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend;
2. legacy unavailable rank = 0 PP e reembolsável/migrável;
3. LIGHTNING sem parcela térmica explícita permanece neutro;
4. ausência de inferência por `TemperatureChangedEvent` global;
5. ausência de escrita direta BODY/CORE/RATE;
6. futuro self parcel ×0,75;
7. futura external magic parcel ×0,90;
8. preservação do sinal térmico;
9. dedup uma transformação por action/parcela;
10. provider/version mismatch fail-closed.
