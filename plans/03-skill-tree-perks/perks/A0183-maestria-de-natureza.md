# A0183 — Maestria de Natureza

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE` TRANSITIVO.**

A infraestrutura genérica de unlock não é o blocker: `TreeUnlockResolver`/`TreeUnlockDefinition` já existem e o Stage 04.01 fornece projeção canônica de investimento. A compra está fechada porque A0182 permanece `UNAVAILABLE_NODE`.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db81db8e4ae7df4de9079a`.

## Contrato estrutural

- ARCANE; camada 7; Capstone/terminal exterior; 1 rank; 3 PP.
- Compra da terminal: A0182 + Nature Mastery ≥80 + pelo menos um entre A0178=1, A0180≥2 ou A0181≥2.
- A0183 não concede dano, cura, crescimento, transformação ou interação ambiental.
- Possuir A0183 satisfaz **somente Gate C** da Specialist Natureza.

## Specialist Natureza

A liberação exige simultaneamente:

- **Gate A:** fundamentos semânticos exteriores ARCANE/POWER e ARCANE/NATURE definidos pelo mapeamento canônico;
- **Gate B:** ≥100 Passive Points válidos em `SPECIALIST_REGION:NATURE`;
- **Gate C:** A0183 possuída.

`SPECIALIST_REGION:NATURE` conta núcleo ARCANE compartilhado elegível + ARCANE/NATURE. FIRE/ICE/LIGHTNING/HOLY/BLOOD/ELDRITCH específicos e PP de bridge ficam fora por padrão.

## Runtime canônico existente

`TreeUnlockResolver` avalia domain scores, required tags e minimum Mastery experience. O Stage 04.01 projeta investimento comprado a partir de metadata/tags explícitas e falha fechado quando metadata/revisions divergem.

A0183 **não deve criar `SpecialistGateResolver` paralelo**. Gate A/B compõem os serviços canônicos existentes; Gate C é a posse da terminal.

## Fail-closed / dependency closure

Enquanto A0182 estiver unavailable:

- compra A0183 falha antes do gasto;
- rank legado unavailable conta 0 PP em gates;
- allocation legado continua reembolsável/migrável;
- não usar antigos gates de 8/12 PP;
- não liberar Specialist por geometria, proximidade ou rota SURVIVAL/HEALING.

## Respec seguro

Quando a Specialist existir, qualquer perk interna possuída deve bloquear refund que quebre Gate A, Gate B, Gate C ou dependency closure da terminal. A Specialist deve ser reembolsada antes.

## Handoff Chat 2

Implementar apenas availability/fail-closed enquanto A0182 permanecer fechada. Reutilizar `TreeUnlockResolver`/Stage 04.01; não criar nova authority de unlock.

## Testes obrigatórios para Chat 3

1. A0182 unavailable bloqueia compra antes do gasto;
2. legacy rank unavailable = 0 PP/refundable;
3. Gate C sozinho não libera Specialist;
4. Gate A false / B true / C true = bloqueado;
5. Gate A true / B <100 / C true = bloqueado;
6. A/B/C verdadeiros = elegível quando dependency closure estiver aberta;
7. PP de bridge não contam duas vezes;
8. geometria/UI não substituem tags/região semântica;
9. respec seguro impede quebra com perk interna possuída;
10. reload/migração/version mismatch permanecem fail-closed.