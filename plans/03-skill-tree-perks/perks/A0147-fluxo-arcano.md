# A0147 — Fluxo Arcano

## Estado Chat 1

**DESIGN APROVADO.**

A perk amplifica somente regeneração MANA nativa já positiva. O provider continua owner da cadence, bloqueios, máximo e settlement. Não existe scheduler paralelo do Skill Tree.

## Contrato

- Domínio ARCANE; ramo Fundamentos — Fluxo de Mana; camada 2; Ramo.
- Até 5 ranks; 1 PP/rank.
- Pré-requisito: A0146 ≥2.
- +3% de regeneração nativa de mana por rank: +3% / +6% / +9% / +12% / +15%.
- Taxa nativa 0 permanece 0.

## Iron's Spells 'n Spellbooks 3.16.3

Snapshot: `iron431/irons-spells-n-spellbooks@e4056af90302d37eb1739f5ff05020b020e6e252`.

Hook aprovado: modifier estável por effectId em `AttributeRegistry.MANA_REGEN`, operação percentual semanticamente equivalente a `ADD_MULTIPLIED_BASE`, valor `0,03 × rank`.

`MagicManager.regenPlayerMana(...)` permanece owner do tick, do `MAX_MANA`, do config multiplier e do clamp. A0147 não chama `addMana()` diretamente para representar o bônus.

## Ars Nouveau 5.13.1

Snapshot: `baileyholl/Ars-Nouveau@112920ff774831f204031da75b4c4e73d3765157`.

Hook aprovado: `ManaRegenCalcEvent`.

Se `event.getRegen() > 0`:

`event.setRegen(event.getRegen() × (1 + 0,03 × rank))`.

Se o provider calcular 0, permanecer 0.

## Authority e deduplicação

- cada provider resolve seu próprio reservatório;
- handler/modifier deve ser idempotente;
- uma taxa/tick recebe no máximo uma contribuição A0147;
- bloqueio nativo não pode ser contornado por pulse direto;
- Source, Soul Energy, spirits/souls, HP/sangue e energia tecnológica não são MANA.

## Fallback

Sem atributo/hook confiável de regen MANA, omitir apenas aquele provider. Não criar pulsos periódicos, não converter outro recurso em mana e não tratar zero como taxa a ser criada.

## Handoff Chat 2

- Iron's: somente `MANA_REGEN` nativo;
- Ars: somente `ManaRegenCalcEvent`;
- nenhum scheduler próprio ou `addMana` direto;
- dedup por provider/tick/cálculo;
- preservar todas as condições nativas do provider.

## Testes Chat 3

1. +3/+6/+9/+12/+15%;
2. taxa 0 permanece 0;
3. Iron's conserva cadence/config/clamp nativos;
4. Ars evento é multiplicado uma única vez;
5. nenhum `addMana`/scheduler extra;
6. providers coexistentes regeneram pools separados;
7. provider absent/removal fail-soft por canal;
8. login/reload/respec/equipment refresh não duplica modifiers;
9. recursos não-MANA inalterados.