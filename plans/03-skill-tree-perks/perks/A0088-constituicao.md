# A0088 — Constituição

## Estado

- **Design:** APROVADO sem mutação funcional no Notion em 2026-08-31.
- **Notion:** `3c569db9-f0db-8138-aa1b-f856663414a8`; fetch fresco PASS.
- **Runtime observado:** implementação data-driven presente em `minecraft:generic.max_health`, com preservação explícita da razão de vida no refresh.

## Contrato canônico

- Gateway VITALITY; 5 ranks, +2% de vida máxima por rank, máximo +10%.
- Owner canônico: Minecraft/NeoForge `Attributes.MAX_HEALTH`.
- Mudança de rank não pode produzir cura gratuita. Ao recalcular max health, preservar `vidaAtual/vidaMáxima` quando possível e clamp seguro à nova vida máxima.
- A perk não concede absorption, cura emergencial, regeneração ou trigger de low-health por alternância de rank.

## Evidência runtime

`a0081_a0100.json` publica A0088 como `MULTIPLY_TOTAL` +0.02/rank em `minecraft:generic.max_health`. `AttributeNodeEffectRuntime.refresh(...)` captura vida/max antigos, reaplica os modificadores e, se a vida máxima mudou, chama `A0081A0100CombatPolicy.preserveHealthRatio(...)` antes de `setHealth`. Isso materializa a regra anti-free-heal no caminho real.

O modifier é transitório em runtime, mas derivado de progression state persistente e reaplicado pelo refresh; não se deve criar segunda persistência/NBT para o mesmo benefício.

## Cobertura de providers

- Pufferfish's Attributes 0.8.3 não substitui `MAX_HEALTH` e não é owner desta perk.
- Apotheosis/Apothic, equipamentos vanilla, Simply Swords/armaduras, magia e demais mods podem adicionar seus próprios modifiers de health; A0088 deve **compor pela pilha de atributos vanilla**, não duplicar nem ler/modificar NBT alheio.
- Enshrouded/Black Arcana/Volcanoes não devem ser consultados para vida máxima desta perk.
- Mods tecnológicos não possuem papel funcional aqui.

## Pendências para Chat 2

- **P-A0088-01:** testes provider-present/composição com outros modifiers de `MAX_HEALTH`, rank up/down, respec, relog/respawn e ausência de cura líquida explorável.
- **P-A0088-02:** validar ordering/clamp quando outro provider altera max health no mesmo refresh/tick; uma única razão antiga→final.
- **P-A0088-03:** garantir remoção/reaplicação idempotente sem modifier duplicado por efeitoId.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | Gateway VITALITY. |
| Integração global | PASS | compõe via atributo vanilla, sem owner paralelo. |
| Qualidade/identidade | PASS | fundamento corporal simples e estável. |
| Topologia | PASS | VITALITY/CORE, Camada 1. |
| Especializações | PASS | fundamento só conta quando mapeado; não desbloqueia Specialist sozinho. |
| PT-BR | PASS | Constituição / vida máxima. |
| Notion | PASS sem mudança | fetch fresco. |
| NeoVitae | PASS | ausente. |
| Providers | PASS | Minecraft/NeoForge owner; terceiros apenas compõem modifiers. |

Os 18 critérios passam no design e o binding principal já existe no runtime.