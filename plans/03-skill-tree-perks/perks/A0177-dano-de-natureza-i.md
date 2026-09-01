# A0177 — Dano de Natureza I

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE`.**

Chat 1 não implementa runtime. Iron's Spells 'n Spellbooks 3.16.3 fornece identidade NATURE explícita, mas o snapshot atual ainda não possui um producer canônico que prove autoria, DIRECT vs derived e `outcome_id` antes da aplicação do bônus.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db81eea60cf4b437a3f1d7`.

## Contrato de gameplay

- ARCANE/NATURE; camada 4; Ramo; 4 ranks; 1 PP/rank.
- Dependências: A0144 Poder Mágico ≥2 + Gateway ARCANE + pelo menos uma técnica arcana com rank ≥1 entre A0148–A0155.
- +3% de dano NATURE mágico direto elegível por rank.
- Escalonamento: ×1,03 / ×1,06 / ×1,09 / ×1,12.
- Teto próprio: +12%.
- Uma aplicação por `outcome_id`.

## Authority e classifiers

Iron's 3.16.3 fornece:

- school `nature`;
- `irons_spellbooks:nature_magic`;
- tag/provider identity `NATURE_MAGIC`.

Isso é evidência válida de **classificação NATURE**, mas não prova sozinho que um dano observado é um `direct magic outcome` atribuído ao jogador.

Ars Nouveau 5.13.1 / Ars Elemental 0.7.10.1 só entram quando um adapter versionado provar o classifier NATURE e a autoria do outcome.

## Blocker canônico

Capacidade requerida: `DIRECT_MAGIC_OUTCOME_V1`, contendo no mínimo:

- `action_id/outcome_id`;
- caster/owner causal;
- DIRECT vs derived;
- componente elemental NATURE explicitamente classificado;
- ponto canônico anterior à aplicação do multiplicador elemental.

## Pipeline futuro obrigatório

`provider spell -> DIRECT_MAGIC_OUTCOME_V1 -> confirmar owner=player + DIRECT + NATURE -> multiplicador A0177 uma vez -> pipeline canônico continua`.

O bônus não cria segundo hit nem novo DamageSource.

## Exclusões e anti-inferência

Não classificar NATURE apenas por:

- veneno genérico;
- planta/folha/espinho;
- fauna;
- bioma/clima/ambiente;
- cor/partícula;
- namespace ou nome textual;
- magia genérica sem classifier NATURE;
- DoT já derivado.

Dano físico de criatura/planta e fontes ambientais permanecem fora.

## Mastery e anti-abuso

Nature Mastery futura deve usar milestones/eventos causais canônicos. A0177 não concede Mastery por tick de poison, permanência em vegetação, DoT, summon, equipamento ou exposição ambiental.

## Fail-closed / disponibilidade

Enquanto `DIRECT_MAGIC_OUTCOME_V1` faltar:

- compra falha antes do gasto;
- rank legado unavailable vale 0 PP para gates e permanece reembolsável/migrável;
- não degradar para `+dano mágico` genérico;
- não usar evento provider isolado como segunda pipeline local da perk.

## Handoff Chat 2

Implementar somente availability/fail-closed. Não criar producer local de direct magic outcome para A0177. O classifier Iron's `nature_magic` pode ser preparado como evidência/adaptor, mas não autoriza aplicação sem o boundary canônico.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend;
2. legacy unavailable rank = 0 PP e reembolsável/migrável;
3. ranks futuros = +3/+6/+9/+12%;
4. Iron's `nature_magic` classifica NATURE quando direct outcome existir;
5. poison/planta/fauna/ambiente negativos;
6. DoT/derived/summon/fake player negativos;
7. uma aplicação por `outcome_id`;
8. sem segundo DamageSource/proc/crítico;
9. provider/version mismatch fail-closed;
10. reload/multiplayer safety.
