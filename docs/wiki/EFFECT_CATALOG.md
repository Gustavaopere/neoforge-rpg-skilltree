# Catálogo de efeitos mecânicos

Este documento cataloga as entradas de **atributo** atualmente declaradas em `src/main/resources/data/rpgskilltree/node_effects/*.json` na base auditada. Existem **119 entradas**: 66 em `main.json` e 53 nos seis packs adicionais.

Este número não deve ser confundido com os 512 nós da árvore principal. Um nó pode ser estrutural, um node effect pode apontar para uma árvore/subárvore especializada, e comportamentos não baseados em atributo podem viver no runtime.

Operações são mantidas com os nomes do dado: `ADD_FLAT`, `ADD_PERCENT_BASE` e `MULTIPLY_TOTAL` quando presentes.

## Main — 66 efeitos

Cada região recebe três efeitos iniciais e três efeitos de final triad.

| Região | Nós iniciais e efeito por rank | Final triad e efeito por rank |
| --- | --- | --- |
| Martial | `martial_000` attack_damage `ADD_FLAT 0.35`; `001` attack_speed `ADD_PERCENT_BASE 0.02`; `002` apothic armor_pierce `ADD_PERCENT_BASE 0.02` | `martial_036` attack_damage `ADD_FLAT 0.75`; `037` attack_speed `ADD_PERCENT_BASE 0.03`; `038` apothic armor_pierce `ADD_PERCENT_BASE 0.03` |
| Vitality | `vitality_000` max_health `ADD_FLAT 1.0`; `001` armor `ADD_FLAT 0.5`; `002` knockback_resistance `ADD_FLAT 0.02` | `vitality_034` max_health `ADD_FLAT 2.0`; `035` armor `ADD_FLAT 1.0`; `036` knockback_resistance `ADD_FLAT 0.03` |
| Healing | `healing_000` apothic healing_received `ADD_PERCENT_BASE 0.03`; `001` Iron's holy_spell_power `ADD_PERCENT_BASE 0.025`; `002` Iron's spell_resist `ADD_PERCENT_BASE 0.02` | `healing_030` healing_received `0.05`; `031` holy_spell_power `0.04`; `032` spell_resist `0.03`, todos `ADD_PERCENT_BASE` |
| Arcane | `arcane_000` Iron's max_mana `ADD_FLAT 20`; `001` spell_power `ADD_PERCENT_BASE 0.025`; `002` mana_regen `ADD_PERCENT_BASE 0.03` | `arcane_046` max_mana `ADD_FLAT 35`; `047` spell_power `ADD_PERCENT_BASE 0.04`; `048` cooldown_reduction `ADD_PERCENT_BASE 0.03` |
| Engineering | `engineering_000` attack_speed `ADD_PERCENT_BASE 0.015`; `001` luck `ADD_FLAT 0.25`; `002` movement_speed `ADD_PERCENT_BASE 0.01` | `engineering_042` attack_speed `0.03`; `043` luck `ADD_FLAT 0.5`; `044` movement_speed `0.02` |
| Mining | `mining_000` apothic mining_speed `ADD_PERCENT_BASE 0.04`; `001` luck `ADD_FLAT 0.25`; `002` apothic armor_pierce `ADD_PERCENT_BASE 0.015` | `mining_032` mining_speed `0.06`; `033` luck `ADD_FLAT 0.5`; `034` armor_pierce `0.025` |
| Survival | `survival_000` max_health `ADD_FLAT 0.75`; `001` armor `ADD_FLAT 0.35`; `002` movement_speed `ADD_PERCENT_BASE 0.01` | `survival_034` armor `ADD_FLAT 1.0`; `035` max_health `ADD_FLAT 1.5`; `036` movement_speed `ADD_PERCENT_BASE 0.02` |
| Summoning | `summoning_000` Iron's summon_damage `ADD_PERCENT_BASE 0.03`; `001` max_mana `ADD_FLAT 12`; `002` mana_regen `ADD_PERCENT_BASE 0.02` | `summoning_030` summon_damage `0.05`; `031` mana_regen `0.04`; `032` max_mana `ADD_FLAT 20` |
| Occult | `occult_000` Iron's blood_spell_power `ADD_PERCENT_BASE 0.03`; `001` eldritch_spell_power `0.03`; `002` spell_power `0.015` | `occult_034` blood_spell_power `0.05`; `035` eldritch_spell_power `0.05`; `036` spell_power `0.03` |
| Logistics | `logistics_000` movement_speed `ADD_PERCENT_BASE 0.015`; `001` luck `ADD_FLAT 0.2`; `002` attack_speed `ADD_PERCENT_BASE 0.01` | `logistics_026` movement_speed `0.03`; `027` luck `ADD_FLAT 0.5`; `028` attack_speed `0.02` |
| Agility | `agility_000` movement_speed `ADD_PERCENT_BASE 0.02`; `001` attack_speed `0.02`; `002` apothic dodge_chance `0.02` | `agility_032` movement_speed `0.03`; `033` attack_speed `0.03`; `034` dodge_chance `0.03`, todos `ADD_PERCENT_BASE` |

Quando a operação foi omitida na segunda metade de uma célula por brevidade, ela permanece `ADD_PERCENT_BASE`, exceto valores explicitamente marcados `ADD_FLAT`.

## Epic Fight — 5 efeitos

| Nó | Atributo | Operação | Por rank |
| --- | --- | --- | ---: |
| `martial_000` | `epicfight:stamina` | ADD_FLAT | 1.0 |
| `martial_001` | `epicfight:stamina_regen` | ADD_PERCENT_BASE | 0.03 |
| `martial_002` | `epicfight:impact` | ADD_FLAT | 0.03 |
| `agility_000` | `epicfight:stamina` | ADD_FLAT | 0.75 |
| `agility_001` | `epicfight:stamina_regen` | ADD_PERCENT_BASE | 0.04 |

## Malum — 4 efeitos

| Nó | Atributo | Operação | Por rank |
| --- | --- | --- | ---: |
| `occult_000` | `malum:spirit_spoils` | ADD_FLAT | 1.0 |
| `occult_001` | `malum:arcane_resonance` | ADD_PERCENT_BASE | 0.10 |
| `occult_002` | `malum:soul_ward_capacity` | ADD_FLAT | 2.0 |
| `occult_027` | `malum:geas_limit` | ADD_FLAT | 1.0 |

## Druid — 7 efeitos

| Nó | Atributo | Operação | Por rank |
| --- | --- | --- | ---: |
| `druid/wild_shape` | max_health | ADD_FLAT | 1.0 |
| `druid/natural_resilience` | armor | ADD_FLAT | 0.5 |
| `druid/deep_lungs` | movement_speed | ADD_PERCENT_BASE | 0.02 |
| `druid/sky_instinct` | movement_speed | ADD_PERCENT_BASE | 0.02 |
| `druid/predator_form` | attack_damage | ADD_PERCENT_BASE | 0.04 |
| `druid/pack_guardian` | max_health | ADD_FLAT | 1.0 |
| `druid/primal_spirit` | `irons_spellbooks:nature_spell_power` | ADD_PERCENT_BASE | 0.08 |

## Metamorph — 5 efeitos

| Nó | Atributo | Operação | Por rank |
| --- | --- | --- | ---: |
| `metamorph/borrowed_face` | movement_speed | ADD_PERCENT_BASE | 0.02 |
| `metamorph/mutable_bones` | armor | ADD_FLAT | 0.5 |
| `metamorph/predatory_mimicry` | attack_damage | ADD_PERCENT_BASE | 0.04 |
| `metamorph/unstable_anatomy` | max_health | ADD_FLAT | 1.0 |
| `metamorph/chimeric_memory` | `apothic_attributes:dodge_chance` | ADD_PERCENT_BASE | 0.02 |

## Technomancer — 15 efeitos

| Nó | Atributo | Operação | Por rank |
| --- | --- | --- | ---: |
| `technomancer/core` | Iron's max_mana | ADD_FLAT | 25 |
| `technomancer/conductive_mana` | Iron's max_mana | ADD_FLAT | 20 |
| `technomancer/arcane_bus` | Iron's mana_regen | ADD_PERCENT_BASE | 0.03 |
| `technomancer/create_resonance` | Iron's spell_power | ADD_PERCENT_BASE | 0.02 |
| `technomancer/kinetic_casting` | Iron's cast_time_reduction | ADD_PERCENT_BASE | 0.025 |
| `technomancer/create_overdrive` | Iron's cooldown_reduction | ADD_PERCENT_BASE | 0.05 |
| `technomancer/network_memory` | Iron's max_mana | ADD_FLAT | 15 |
| `technomancer/pattern_mind` | Iron's spell_power | ADD_PERCENT_BASE | 0.025 |
| `technomancer/arcane_autocrafting` | Iron's mana_regen | ADD_PERCENT_BASE | 0.06 |
| `technomancer/power_conduit` | Iron's mana_regen | ADD_PERCENT_BASE | 0.025 |
| `technomancer/charged_focus` | Iron's spell_power | ADD_PERCENT_BASE | 0.025 |
| `technomancer/overcharged_caster` | `apothic_attributes:crit_chance` | ADD_PERCENT_BASE | 0.05 |
| `technomancer/dual_systems` | Iron's cooldown_reduction | ADD_PERCENT_BASE | 0.04 |
| `technomancer/triune_core` | Iron's spell_power | ADD_PERCENT_BASE | 0.08 |
| `technomancer/triune_core` | Iron's max_mana | ADD_FLAT | 50 |

Os nomes `create_resonance` e `create_overdrive` são IDs reais de node effects; seus efeitos declarados acima operam em atributos do Iron's. Isso não prova, sozinho, um adapter de eventos/máquinas Create.

## Warlock — 17 efeitos

| Nó | Atributo | Operação | Por rank |
| --- | --- | --- | ---: |
| `warlock/forbidden_lore` | Iron's max_mana | ADD_FLAT | 8 |
| `warlock/pact_confluence` | Iron's spell_power | ADD_PERCENT_BASE | 0.04 |
| `warlock/blade_bond` | attack_damage | ADD_FLAT | 0.5 |
| `warlock/hex_edge` | attack_speed | ADD_PERCENT_BASE | 0.03 |
| `warlock/soulsteel_keystone` | Iron's spell_power | MULTIPLY_TOTAL | 0.10 |
| `warlock/blood_sacrifice` | Iron's blood_spell_power | ADD_PERCENT_BASE | 0.06 |
| `warlock/blood_hunger` | `apothic_attributes:life_steal` | ADD_FLAT | 0.01 |
| `warlock/crimson_covenant` | `apothic_attributes:overheal` | ADD_PERCENT_BASE | 0.12 |
| `warlock/familiar_bond` | Iron's summon_damage | ADD_PERCENT_BASE | 0.07 |
| `warlock/servant_command` | max_health | ADD_FLAT | 1.0 |
| `warlock/anchored_familiar` | Iron's summon_damage | MULTIPLY_TOTAL | 0.12 |
| `warlock/grimoire_pages` | Iron's max_mana | ADD_FLAT | 12 |
| `warlock/ritual_memory` | Iron's mana_regen | ADD_PERCENT_BASE | 0.05 |
| `warlock/forbidden_formula` | Iron's spell_power | MULTIPLY_TOTAL | 0.10 |
| `warlock/soul_harvest` | Iron's spell_power | ADD_PERCENT_BASE | 0.04 |
| `warlock/servant_link` | Iron's summon_damage | ADD_PERCENT_BASE | 0.06 |
| `warlock/soul_conduit` | max_health | ADD_FLAT | 2.0 |

## O que ainda não pode ser inferido deste catálogo

Este arquivo não afirma que os 119 efeitos representam todas as mecânicas do mod. Gating, mastery, ações especiais e integrações comportamentais podem estar em Java ou em outros dados. Também não afirma que todo `nodeId` especializado listado aqui esteja necessariamente visível/obtenível na árvore principal de 512; o pack apenas comprova que o efeito está declarado para aquele ID.

Os arquivos JSON e handlers runtime continuam sendo a autoridade final.