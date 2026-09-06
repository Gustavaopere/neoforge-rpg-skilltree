# 08.13 — Settlements, Governance, Laws & Public Opinion

## Goal
Adicionar camada social/política sobre assentamentos reais, especialmente MineColonies, sem duplicar a simulação física do provider.

## Estado narrativo mínimo
- government model;
- player authority/legitimacy;
- public trust;
- public fear;
- stability;
- thematic tolerance/stance aggregates;
- institutions + influence;
- laws/policies;
- active crises;
- local history references.

## Governos iniciais data-driven
- monarchy/autocracy;
- council;
- republic;
- merchant oligarchy;
- theocracy;
- magocracy;
- military junta;
- commune;
- hybrid/custom profiles.

Nenhum governo possui moralidade fixa. Ele define mecanismos de decisão, instituições dominantes e disponibilidade de rotas.

## Laws
Implementar schema genérico de policy, com enum/value por law definition. Seeds de conteúdo:
- necromancy;
- vampirism;
- corpse experimentation;
- black arcana;
- religious freedom;
- shroud research;
- industrial pollution;
- nuclear research;
- conscription;
- non-human residency.

## Opinião pública
- [ ] derivada de grupos/instituições/eventos, não booleano global;
- [ ] pode divergir de happiness do MineColonies;
- [ ] mudanças por eventos discretos e propaganda/knowledge legítimo;
- [ ] thresholds geram pressão, protesto, apoio, migration/crisis hooks quando implementáveis;
- [ ] não executar loops por cidadão a cada tick.

## Acceptance
O jogador pode ser fundador e ainda perder legitimidade suficiente para a cidade rejeitar sua ordem. Uma lei `necromancy=FORBIDDEN` + clero forte pode criar julgamento; em outra cidade a mesma presença pode ser legal/licenciada.