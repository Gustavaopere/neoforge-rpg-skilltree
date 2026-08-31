# A0091 — Base Firme

## Estado

- **Design:** APROVADO sem mutação funcional no Notion em 2026-08-31.
- **Notion:** `3c569db9-f0db-8123-a26b-d937658a3173`; fetch fresco PASS.
- **Runtime observado:** binding data-driven já presente em `minecraft:generic.knockback_resistance`, operação `ADD_FLAT`, +0,03/rank.

## Contrato canônico

- Gateway VITALITY desbloqueado.
- 5 ranks, 1 ponto por rank.
- +0,03 de Knockback Resistance por rank, máximo próprio +0,15.
- Owner canônico: Minecraft/NeoForge `Attributes.KNOCKBACK_RESISTANCE`.
- Não concede imunidade completa e não substitui stun/interruption resistance.

## Evidência runtime

`node_effects/a0081_a0100.json` publica `rpgskilltree:node/combat/a0091/knockback_resistance` em `minecraft:generic.knockback_resistance`, `ADD_FLAT`, 0,03/rank. O atributo também é lido pelo Epic Fight ao aplicar knockback, portanto o provider já compõe nativamente sem bridge paralela.

## Cobertura de providers

- Minecraft/NeoForge é o owner positivo.
- Epic Fight compõe quando usa o atributo vanilla; `IMPACT` e `STUN_ARMOR` permanecem grandezas distintas.
- Armaduras, afixos e mods que adicionem Knockback Resistance pelo atributo vanilla compõem naturalmente.
- Magia, tecnologia, Volcanoes, Enshrouded e Black Arcana não são owners desta perk apenas por poderem mover entidades.

## Pendências para Chat 2

- **P-A0091-01:** regressões rank/respec/rules reload/relog e unicidade do modifier.
- **P-A0091-02:** provar cap próprio +0,15 e ausência de mutação em `STUN_ARMOR`, `IMPACT`, Armor ou Toughness.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | Gateway VITALITY. |
| Integração global | PASS | usa atributo vanilla único. |
| Qualidade/identidade | PASS | estabilidade contra repulsão, sem sobrepor stun. |
| Topologia | PASS | VITALITY/STABILITY, Camada 1. |
| Especializações | PASS | PP somente por mapeamento explícito. |
| PT-BR | PASS | efeito e limites inequívocos. |
| Notion | PASS | fetch fresco, sem drift funcional. |
| NeoVitae | PASS | ausente. |
| Providers | PASS | owner vanilla; Epic Fight compõe nativamente. |

Os 18 critérios passam no design; o binding principal já existe e deve ser revalidado pelo Chat 2.