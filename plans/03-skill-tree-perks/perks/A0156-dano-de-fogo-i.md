# A0156 — Dano de Fogo I

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / UNAVAILABLE_NODE.**

Iron's e Ars têm identidades FIRE comprováveis, mas a `main` ainda não possui a combinação canônica `DIRECT_MAGIC_OUTCOME_V1 + FIRE_MAGIC_CLASSIFIER_V1`. Namespace/damage type isolado não prova que um outcome seja direto.

## Contrato

- ARCANE; camada 4; Ramo; 4 ranks; 1 PP/rank.
- Pré-requisitos: A0144 ≥2 + Gateway ARCANE + ao menos uma técnica elegível entre A0148–A0155.
- +3% de dano/rank, máximo +12%.
- Somente componente mágico FIRE direto, player-owned.
- Camada posterior ao universal magic power e aplicada uma vez por `outcome_id`.

## Authority e evidência

Iron's 3.16.3: `SchoolRegistry.FIRE_RESOURCE = irons_spellbooks:fire`, FIRE usa `FIRE_MAGIC` e `SpellDamageEvent` é mutável.

Ars 5.13.1: effects/schools FIRE explícitos e `SpellDamageEvent.Pre`; porém effects como Flare podem gerar cinders/outcomes derivados. Logo FIRE identity e directness são duas provas separadas.

Enshrouded Stage 08.02 classifica magia para seu reducer defensivo, não publica outcome FIRE do Skill Tree.

## Availability

Ao menos um canal precisa fornecer DIRECT_MAGIC e FIRE explicitamente. Sem ambos, compra fail-before-spend e legacy PP 0.

## Exclusões

- DoT, field, cinder, summon, automation e derived effects;
- custos, ambiente e thermal;
- inferência por nome, partícula, cor, namespace, alvo pegando fogo ou mod instalado;
- reaplicar A0156 em um componente FIRE já derivado de outro outcome.

## Handoff Chat 2

Construir os classifiers como infraestrutura canônica compartilhada, nunca listeners locais exclusivos de A0156. Um mesmo outcome recebe a perk no máximo uma vez.

## Testes Chat 3

1. unavailable no snapshot sem producer canônico;
2. Iron FIRE direct positivo / non-FIRE negativo;
3. Ars FIRE direct positivo e cinder/derived negativo;
4. uma aplicação por outcome;
5. camada após A0144 sem double scaling;
6. thermal/Volcanoes/ambient negatives, provider mismatch e multiplayer.