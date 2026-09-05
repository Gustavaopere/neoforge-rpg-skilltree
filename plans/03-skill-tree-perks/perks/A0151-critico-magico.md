# A0151 — Crítico Mágico

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / UNAVAILABLE_NODE.**

A `main` atual possui resolvedor crítico canônico por root, mas não possui `DIRECT_MAGIC_OUTCOME_V1` capaz de produzir um outcome mágico direto, player-owned e inequívoco para Iron's/Ars. Compra deve falhar antes do gasto; rank legado vale 0 PP em gates.

## Contrato

- ARCANE; camada 3; Ramo; 4 ranks; 1 PP/rank.
- Pré-requisitos: A0144 ≥2 + Gateway ARCANE.
- +2 pontos percentuais de chance crítica/rank, máximo +8 pp.
- Base = 0% quando o provider não possui crítico mágico nativo aprovado.
- Multiplicador crítico base do lane = ×1,50.
- Uma única decisão crítica por `magic_direct_outcome_id`.

## Authority e evidência

`A0001A0020CriticalService` continua sendo o owner da decisão crítica e não pode ser duplicado.

Iron's 3.16.3 (`e4056af…`) expõe `SpellDamageEvent` mutável antes de `hurt` e `SpellDamageSource` com spell/source. Ars 5.13.1 (`112920ff…`) expõe `SpellDamageEvent.Pre` com caster/context. Esses hooks permitem adapters futuros, mas não provam sozinhos DIRECT vs derived/DoT/summon nem fornecem o outcome id canônico.

## Availability

Exige `DIRECT_MAGIC_OUTCOME_V1` com ao menos um adapter versionado completo. Sem nenhum canal completo, `UNAVAILABLE_NODE`.

## Pipeline futuro

`provider direct spell action → DIRECT_MAGIC classifier/correlation → magic_direct_outcome_id → CriticalService uma vez → dano do mesmo outcome`.

O adapter deve falhar fechado para autoria/correlação ambígua.

## Exclusões

- DoT, field, summon, familiar, automação e fake player;
- custos/self-damage, ambiente e derived outcomes;
- namespace, partícula, cor ou DamageSource genérico como prova de magia direta;
- segunda rolagem no `LivingDamageEvent`/`LivingHurt`.

## Handoff Chat 2

Manter indisponível até publicar o producer canônico. Se implementar o producer como infraestrutura necessária, ele deve convergir no CriticalService existente e não criar novo resolver.

## Testes Chat 3

1. purchase fail-before-spend e legacy PP 0 enquanto capability ausente;
2. provider present/absent/version mismatch;
3. exatamente uma rolagem por outcome/root;
4. direct spell positivo e derived/DoT/summon negativos;
5. sem dupla rolagem entre adapter e hooks NeoForge;
6. multiplayer, logout/reload e dedup bounded.