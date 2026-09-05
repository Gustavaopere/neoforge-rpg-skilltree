# A0153 — Alcance Arcano

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / UNAVAILABLE_NODE.**

Nenhum hook/atributo genérico de alcance semântico foi provado nas APIs exatas de Iron's 3.16.3 ou Ars 5.13.1.

## Contrato

- ARCANE; camada 3; Ramo; 4 ranks; 1 PP/rank.
- Pré-requisitos: Gateway ARCANE + (A0148 ≥1 OU A0144 ≥2).
- +4% de alcance nativo/rank, máximo +16%.
- Só modifica range/max distance realmente definido pelo provider/spell.

## Authority

O provider da spell continua owner de geometria, caps e targeting. O RPG só pode modular um valor semântico exposto por `SPELL_RANGE_ADAPTER_V1`.

## Availability

Ao menos um adapter explícito provider+spell/forma deve expor a distância base real e o boundary de mutação. Sem canal completo, compra fail-before-spend e rank legado vale 0 PP.

## Pipeline futuro

`provider spell range base → A0153 multiplier → provider clamp/cap → resolução nativa`.

O cap mais restritivo do provider vence.

## Exclusões

- projectile velocity ou lifetime;
- AoE/radius, hitbox, duration ou cast time;
- teleport distance como equivalência genérica;
- atributo global inventado de spell range;
- patch por nome de classe/spell sem adapter versionado.

## Handoff Chat 2

Não criar fallback geométrico artificial. Cada adapter futuro deve declarar providers/spells elegíveis e sua semântica exata; canais desconhecidos ficam no-op/fail-closed.

## Testes Chat 3

1. unavailable purchase no snapshot atual;
2. adapter provider-present/absent/version mismatch;
3. ranks 0–4: 0/4/8/12/16%;
4. cap provider-native preservado;
5. velocidade/lifetime/AoE/hitbox não mudam;
6. respec/rules reload remove apenas a contribuição da perk.