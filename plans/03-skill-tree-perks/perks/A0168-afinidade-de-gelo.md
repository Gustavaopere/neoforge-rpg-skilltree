# A0168 — Afinidade de Gelo

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE`.**

Assim como A0161, esta perk precisa modificar somente uma parcela térmica causal antes de o Cold Sweat consolidar a temperatura corporal. O evento térmico global disponível não carrega autoria/origem suficiente para isso.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db8143a47dcedf058bfcfb`.

## Contrato de gameplay

- ARCANE/ICE; camada 6; Keystone exterior; 1 rank; 2 PP.
- Dependências: A0163 rank ≥3 + Ice Mastery ≥30 + pelo menos um entre A0164=1, A0166≥2 ou A0167≥2.
- Conjuração ICE própria elegível: reduzir em 25% somente a parcela de deslocamento térmico para frio causada por aquela ação.
- Exposição mágica ICE externa explicitamente adaptada: reduzir em 10% somente a parcela térmica causal daquela exposição.
- Não reduz dano ICE, não altera `RPG_ICE_RESISTANCE`, não aumenta/reduz CHILL e não substitui aclimatação ambiental.

## Authority

- Cold Sweat 2.4.2 é a autoridade única da temperatura corporal.
- Iron's Spells 'n Spellbooks 3.16.3 e Ars Nouveau 5.13.1/Ars Elemental 0.7.10.1 podem fornecer identidade/autoria ICE somente por adapter versionado.
- RPG Skill Tree pode transformar uma parcela causal, mas não criar segunda temperatura ou escrever BODY/CORE/RATE/FREEZING_POINT diretamente.
- ICE damage, ICE_CONTROL/CHILL e temperatura são eixos distintos.

## Blocker canônico

Capacidade requerida: `MAGIC_THERMAL_PARCEL_V1`.

Receipt mínimo:

- `action_id` estável;
- ator/origem causal;
- classificação ICE explícita;
- delta térmico assinado daquela action/parcela;
- fase anterior à aplicação canônica do Cold Sweat.

`TemperatureChangedEvent` fornece apenas entidade, trait, old e new temperature; não identifica a origem causal. Não usar a diferença global de BODY para inferir a parcela da magia.

A0163 também está `UNAVAILABLE_NODE` sem `DIRECT_MAGIC_OUTCOME_V1`, tornando a dependency closure de A0168 fechada hoje.

## Pipeline futuro obrigatório

`provider ICE causal -> MAGIC_THERMAL_PARCEL_V1 -> A0168 transforma signed_delta elegível -> Cold Sweat aplica a parcela uma única vez`.

- self ICE frio: `transformed_delta = signed_delta * 0.75`;
- exposição mágica ICE externa adaptada: `transformed_delta = signed_delta * 0.90`.

Uma action/parcela recebe A0168 no máximo uma vez.

## Fail-closed

Enquanto faltar capability ou dependency closure:

- compra falha antes de consumir PP;
- rank legado unavailable vale 0 PP em gates/thresholds e permanece reembolsável/migrável;
- não inferir parcela por dano ICE, CHILL, Slowness, school isolada, partícula, temperatura atual ou before/after BODY;
- não converter afinidade em resistência, dano ou supressão global de frio.

## Mastery e anti-abuso

Ice Mastery deve vir do serviço canônico com autoria causal e milestones/eventos discretos. Exposição contínua ao frio, tempo conectado, temperatura corporal ou permanência com equipamento não geram Mastery por esta perk.

## Handoff Chat 2

Implementar somente disponibilidade/fail-closed. Não inventar `MAGIC_THERMAL_PARCEL_V1`, não modificar state do Cold Sweat diretamente e não substituir por bônus genérico.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend sem capability/A0163;
2. legacy unavailable rank = 0 PP para gates;
3. nenhuma inferência por old/new BODY global;
4. nenhuma escrita direta BODY/CORE/RATE/FREEZING_POINT;
5. nenhum efeito sobre dano ICE, `RPG_ICE_RESISTANCE` ou CHILL;
6. futuramente: 25% self ICE e 10% external magic ICE somente na parcela causal;
7. dedup por `action_id`/parcela;
8. mismatch de provider/version, reload, multiplayer e dedicated server fail-closed.