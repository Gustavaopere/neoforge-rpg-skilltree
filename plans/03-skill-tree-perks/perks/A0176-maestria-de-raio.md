# A0176 — Maestria de Raio

## Estado Chat 1

**DESIGN APROVADO / `UNAVAILABLE_NODE` POR DEPENDENCY CLOSURE.**

Chat 1 não implementa runtime. A correção central desta auditoria é que A0176 **não** está bloqueada por ausência de infraestrutura genérica de unlock: `TreeUnlockResolver` e `TreeUnlockDefinition` já existem na `main`, e o Stage 04.01 adicionou projeção canônica de investimento. A compra continua inalcançável no snapshot atual porque a dependência A0175 está `UNAVAILABLE_NODE`.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db81869740fdbc9e268b0d`.

## Contrato de gameplay

- ARCANE/LIGHTNING; camada 7; Capstone; 1 rank; 3 PP.
- Compra da terminal: A0175 + Lightning Mastery ≥80 + pelo menos um entre A0171=1, A0173≥2 ou A0174≥2.
- Possuir A0176 satisfaz **somente o Gate C** da futura Árvore de Especialista de Raio.
- A0176 não concede dano, resistência, Afinidade, CHARGED, mobilidade, FE ou imunidade por si só.

## Specialist Gates

O unlock de Specialist Raio exige simultaneamente:

- **Gate A:** fundamentos exteriores ARCANE/POWER e ARCANE/LIGHTNING exigidos pelo mapeamento canônico;
- **Gate B:** ≥100 Passive Points válidos em `SPECIALIST_REGION:LIGHTNING`;
- **Gate C:** A0176 possuída.

`SPECIALIST_REGION:LIGHTNING` conta núcleo ARCANE compartilhado explicitamente elegível + ARCANE/LIGHTNING. Exclui por padrão nodes específicos FIRE/ICE/NATURE e PP de ponte, salvo whitelist semântica explícita com contagem única.

## Infraestrutura existente

`TreeUnlockResolver.canUnlock(...)` já avalia:

- minimum domain scores em `InvestmentState`;
- required tags em `InvestmentState`;
- minimum Mastery experience em `MasteryState`.

`TreeUnlockDefinition` já modela esses três conjuntos. O Stage 04.01 (`canonical investment projection`, PR #365) adiciona projeção de ranks comprados para investimento canônico e falha fechado quando metadados/revisões divergem.

Portanto A0176 **não deve criar** `SpecialistGateResolver` paralelo. O futuro mapeamento de Specialist deve compor a infraestrutura canônica existente e adicionar apenas a semântica que ainda faltar para região/terminal, sem segundo authority.

## Dependency closure

A0175 exige `MAGIC_THERMAL_PARCEL_V1` e A0170 depende de `DIRECT_MAGIC_OUTCOME_V1`. Enquanto A0175 não puder ser adquirida, A0176 também não pode ser adquirida, independentemente de o runtime genérico de unlock existir.

## Fail-closed / disponibilidade

No snapshot atual:

- compra falha antes do gasto devido à dependência A0175 indisponível;
- rank legado indisponível vale 0 PP para gates/thresholds e permanece reembolsável/migrável;
- não flexibilizar a dependência para permitir terminal órfã;
- não substituir Gate B por contagem visual/geometria/UI;
- não usar 8 PP/12 PP ou outro gate legado arbitrário.

## Respec e coerência

Quando Specialist existir, o sistema deve impedir respec que torne Gate A/B/C inválido enquanto houver nodes internos dependentes, ou aplicar a política canônica de cascata/reembolso definida pelo sistema. Nunca deixar especialização interna ativa com Gate C removido silenciosamente.

## Handoff Chat 2

Implementar somente o estado transitivo de availability. Reutilizar `TreeUnlockResolver`/`TreeUnlockDefinition` e Stage 04.01; não criar resolver Specialist concorrente. A0176 só se torna adquirível quando A0175 e todas as dependências aprovadas estiverem realmente disponíveis.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend enquanto A0175 estiver unavailable;
2. rank legado unavailable = 0 PP e reembolsável/migrável;
3. A0176 sozinha não libera Specialist;
4. Gate A, B e C devem ser simultâneos quando o Specialist existir;
5. Gate B usa região semântica, nunca geometria/UI;
6. PP de bridge não duplica contagem;
7. FIRE/ICE/NATURE específicos não contam em LIGHTNING por padrão;
8. `TreeUnlockResolver` existente é reutilizado, sem segundo resolver authority;
9. respec seguro preserva invariantes;
10. A0176 não concede pacote de poder colateral.
