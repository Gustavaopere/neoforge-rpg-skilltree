# A0090 — Têmpera

## Estado

- **Design:** APROVADO sem mutação funcional no Notion em 2026-08-31.
- **Notion:** `3c569db9-f0db-8120-a929-fafebea41235`; fetch fresco PASS.
- **Runtime observado:** modifier data-driven presente em `minecraft:generic.armor_toughness`, `MULTIPLY_TOTAL` +2%/rank.

## Contrato canônico

- Gateway VITALITY + A0089 Couro Endurecido ≥2.
- 5 ranks: +2% relativo de Armor Toughness por rank, máximo +10%.
- Owner canônico: Minecraft/NeoForge `Attributes.ARMOR_TOUGHNESS`.
- Se toughness elegível for 0, bônus continua 0; a perk não cria toughness flat.
- Não reduz dano verdadeiro/não mitigável, não altera durabilidade/NBT e não toca `ARMOR`, `STUN_ARMOR` ou Resistência Física.

## Evidência runtime

`a0081_a0100.json` publica `rpgskilltree:node/combat/a0090/armor_toughness` em `minecraft:generic.armor_toughness`, operação `MULTIPLY_TOTAL`, 0.02/rank. `AttributeNodeEffectRuntime` resolve pelo registro vanilla e reaplica o modifier com effectId estável.

## Cobertura de providers

- Armor/toughness de equipamentos vanilla e de mods, inclusive afixos/raridades de Apotheosis/Apothic, compõem pela pilha vanilla; A0090 não reimplementa o cálculo do provider.
- Epic Fight `STUN_ARMOR`, RPG Resistência Física, guard/posture e magic/shroud resistances são grandezas distintas.
- Pufferfish's Attributes 0.8.3 não substitui `ARMOR_TOUGHNESS` nesta perk.
- Simply, magia, Enshrouded, Black Arcana, Volcanoes e tecnologia não são owners de Têmpera apenas por integrarem o modpack.
- Provider que futuramente substituir integralmente a mitigação só poderá participar por adapter explícito; ausência não cria heurística.

## Pendências para Chat 2

- **P-A0090-01:** testes zero toughness→zero bonus, composição com equipamentos/afixos e modifier idempotente.
- **P-A0090-02:** testar dependência A0089≥2 e limpeza em rank loss/respec/rules reload/relog.
- **P-A0090-03:** regressão provando que A0090 não altera ARMOR/STUN_ARMOR/Resistência Física/durabilidade nem fontes que ignoram armor.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | VITALITY + A0089≥2. |
| Integração global | PASS | usa toughness vanilla, sem segunda fórmula. |
| Qualidade/identidade | PASS | aprofundamento de armor existente. |
| Topologia | PASS | VITALITY/ARMOR, Camada 2. |
| Especializações | PASS | PP apenas quando semanticamente mapeado. |
| PT-BR | PASS | Têmpera/tenacidade consistentes. |
| Notion | PASS sem mudança | fetch fresco. |
| NeoVitae | PASS | ausente. |
| Providers | PASS | composição nativa; adapters futuros somente explícitos. |

Os 18 critérios passam no design e o binding principal já está presente.