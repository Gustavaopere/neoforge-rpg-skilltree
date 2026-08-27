# Catálogo dos 512 nós da árvore principal

Este é o inventário exaustivo por família de `data/rpgskilltree/skills/main` na base auditada. O layout gerado confirma **512/512 nós**.

| Família | Quantidade | IDs materializados |
| --- | ---: | --- |
| Core | 28 | `core_00` … `core_27` |
| Martial | 40 | `martial_000` … `martial_039` |
| Vitality | 38 | `vitality_000` … `vitality_037` |
| Healing | 34 | `healing_000` … `healing_033` |
| Arcane | 50 | `arcane_000` … `arcane_049` |
| Engineering | 46 | `engineering_000` … `engineering_045` |
| Mining | 36 | `mining_000` … `mining_035` |
| Survival | 38 | `survival_000` … `survival_037` |
| Summoning | 34 | `summoning_000` … `summoning_033` |
| Occult | 38 | `occult_000` … `occult_037` |
| Logistics | 30 | `logistics_000` … `logistics_029` |
| Agility | 36 | `agility_000` … `agility_035` |
| Bridges | 48 | 6 grupos × 8 |
| Keystones | 16 | `keystone_00` … `keystone_15` |
| **Total** | **512** | |

## Bridges — todos os 48 IDs

- `bridge_paladin_00` … `bridge_paladin_07`
- `bridge_cleric_00` … `bridge_cleric_07`
- `bridge_technomancer_00` … `bridge_technomancer_07`
- `bridge_druid_00` … `bridge_druid_07`
- `bridge_necromancer_00` … `bridge_necromancer_07`
- `bridge_duelist_00` … `bridge_duelist_07`

## O que “512 nós” significa

São 512 arquivos/nós materializados na árvore principal, com layout 512/512. Isso **não** significa 512 bônus exclusivos já finalizados. Arquivos representativos como nós Core/Bridge/Keystone e diversos nós regionais podem ter `bonuses: []`; o efeito resolvido pode vir de outra camada ou ainda não estar preenchido.

## Onde estão os efeitos

O catálogo de efeitos atuais está em [EFFECT_CATALOG.md](EFFECT_CATALOG.md). Os JSONs em `node_effects/*.json` permanecem a fonte canônica para atributos, operação e `amountPerRank`.