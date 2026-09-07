# A0146 — Reserva Arcana

## Estado Chat 1

**DESIGN APROVADO.**

A perk é implementável em Iron's Spells 'n Spellbooks 3.16.3 e Ars Nouveau 5.13.1 por hooks nativos de capacidade máxima. O contrato preserva a quantidade absoluta de mana atual e proíbe top-up gratuito.

## Contrato

- Domínio ARCANE; ramo Fundamentos — Reserva de Mana; camada 2; Ramo.
- Até 5 ranks; 1 PP/rank.
- Pré-requisito: A0144 ≥1 **OU** A0145 ≥1 + Gateway ARCANE.
- +2% de mana máxima nativa por rank: +2% / +4% / +6% / +8% / +10%.
- Cada provider mantém seu próprio reservatório; pools nunca são somados ou convertidos.

## Iron's Spells 'n Spellbooks 3.16.3

Snapshot: `iron431/irons-spells-n-spellbooks@e4056af90302d37eb1739f5ff05020b020e6e252`.

Hook aprovado: modifier estável no `AttributeRegistry.MAX_MANA`, com operação percentual semanticamente equivalente a `ADD_MULTIPLIED_BASE`, valor `0,02 × rank`.

Após refresh/rank-down/respec, consultar o novo máximo. Se `currentMana > newMax`, fazer apenas clamp descendente. Se o máximo aumentou, **não** adicionar mana atual.

## Ars Nouveau 5.13.1

Snapshot: `baileyholl/Ars-Nouveau@112920ff774831f204031da75b4c4e73d3765157`.

`MaxManaCalcEvent` ocorre após o cálculo preliminar de gear/glyph/book e expõe `getMax()/setMax(int)`.

Hook aprovado:

`event.setMax(floor(event.getMax() × (1 + 0,02 × rank)))`.

No reconcile de rank/provider, `IManaCap`/ManaCap preserva o valor absoluto atual e só faz clamp descendente quando exceder o novo teto.

## Authority e lifecycle

- effectId deve ser estável;
- refresh deve ser idempotente em login, reload, equipment change, rank change e respec;
- nenhum evento de refresh pode ser tratado como fonte de mana;
- login/dimensão/reload não devem produzir top-up;
- Iron's e Ars podem coexistir com pools separados.

## Fallback

Sem atributo/evento seguro de capacidade MANA, A0146 omite apenas aquele provider. Não substituir por regen, pulso, refund, Source, Soul Energy, HP ou outro recurso.

## Handoff Chat 2

- Iron's: `MAX_MANA` nativo, modifier estável;
- Ars: `MaxManaCalcEvent`;
- implementar reconcile sem top-up;
- garantir clamp somente descendente após redução de capacidade;
- não criar pool combinado ou adapter que escolha "mana ativa".

## Testes Chat 3

1. +2/+4/+6/+8/+10% por provider;
2. Iron's e Ars coexistem com pools independentes;
3. rank-up aumenta teto sem aumentar current;
4. rank-down/respec apenas clampa quando current>novo máximo;
5. login/reload/equip/dimension não concedem mana;
6. effectId/handler idempotente e sem stacking duplicado;
7. provider absent/removal omite somente aquele pool;
8. Source/Soul Energy/HP/energia tecnológica não são afetados;
9. multiplayer e persistence mantêm valor absoluto.