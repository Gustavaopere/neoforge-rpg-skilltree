# A0089 — Couro Endurecido

## Estado

- **Design:** APROVADO sem mutação funcional no Notion em 2026-08-31.
- **Notion:** `3c569db9-f0db-810d-a0d7-e8926be6ae33`; fetch fresco PASS.
- **Runtime observado:** modifier data-driven presente em `minecraft:generic.armor`, `MULTIPLY_TOTAL` +2%/rank.

## Contrato canônico

- Gateway VITALITY; 5 ranks, +2% relativo de armadura efetiva por rank, máximo +10%.
- Owner canônico: Minecraft/NeoForge `Attributes.ARMOR`.
- Se armor elegível for 0, bônus percentual permanece 0; A0089 não cria proteção flat.
- Não altera NBT, durabilidade, reparo, item tier, `STUN_ARMOR` do Epic Fight nem Resistência Física do RPG.

## Evidência runtime

`a0081_a0100.json` publica `rpgskilltree:node/combat/a0089/armor` em `minecraft:generic.armor`, operação `MULTIPLY_TOTAL`, 0.02/rank. `AttributeNodeEffectRuntime` resolve e aplica o modifier pelo registro vanilla com effectId estável, removendo o anterior antes de reaplicar.

## Cobertura de providers

- Armaduras vanilla, Apotheosis/Apothic e equipamentos de outros mods continuam donos de seus próprios modifiers; A0089 compõe pelo valor final de `ARMOR` e não reimplementa afixos/socket/raridade.
- Epic Fight `STUN_ARMOR` é eixo distinto e não recebe A0089.
- Simply Swords e suas bridges não são provider de armor por estarem no stack de combate.
- Iron's/Ars/Goety/Malum/Eidolon, Enshrouded, Black Arcana, Volcanoes e tecnologia não substituem o atributo canônico para esta perk sem adapter semântico explícito futuro.
- Pufferfish's Attributes 0.8.3 não é owner de A0089.

## Pendências para Chat 2

- **P-A0089-01:** testes de composição com armor de equipamento/afixos providers, zero armor→zero bonus e modifier idempotente.
- **P-A0089-02:** regressão garantindo ausência de alteração em `STUN_ARMOR`, Resistência Física, durabilidade/NBT e damage sources que ignoram armor.
- **P-A0089-03:** rank loss/respec/relog/respawn sem modifier órfão ou duplicado.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | Gateway VITALITY. |
| Integração global | PASS | atributo vanilla é ponto único de composição. |
| Qualidade/identidade | PASS | melhora armor existente sem gerar heavy armor grátis. |
| Topologia | PASS | VITALITY/ARMOR, Camada 1. |
| Especializações | PASS | fundamento só conta por mapeamento explícito. |
| PT-BR | PASS | texto canônico. |
| Notion | PASS sem mudança | fetch fresco. |
| NeoVitae | PASS | ausente. |
| Providers | PASS | terceiros compõem nativamente; sem duplicação. |

Os 18 critérios passam no design e o binding principal já está presente.