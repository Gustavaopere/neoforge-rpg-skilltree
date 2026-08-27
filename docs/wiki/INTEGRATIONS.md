# Integrações

## Epic Fight — IMPLEMENTED

Há integração runtime e um pack de efeitos dedicado. No estado auditado, nós Martial/Agility adicionam `epicfight:stamina`, `epicfight:stamina_regen` e `epicfight:impact`. O pipeline deve impedir aplicação dupla quando Epic Fight e fallback vanilla observam o mesmo ataque.

## Iron's Spellbooks — IMPLEMENTED

A integração confirmada conecta progressão Arcana ao mod. O runtime trata gating/acesso, autorização de inscrição permanente baseada em mastery + identidade de Mage e ganho de mastery por casts confirmados via spellbook/scroll.

Além disso, os node effects atuais usam atributos Iron's como `max_mana`, `mana_regen`, `spell_power`, `cooldown_reduction`, poderes de escola e `summon_damage` em Arcane, Healing, Summoning, Occult e especializações como Technomancer, Warlock e Druid.

Isso ainda não autoriza afirmar bônus por nome de spell sem handler correspondente. Ideias antigas como `Echo Cast` e `Overchannel` permanecem especificação/candidatas enquanto não houver implementação comprovada.

## Ars Nouveau — IMPLEMENTED

Existe integração runtime dedicada. Efeitos específicos devem ser descritos somente quando comprovados no adapter/handler; bônus mágicos genéricos não devem ser convertidos em uma lista inventada de glyphs/spells afetados.

## Goety — IMPLEMENTED

A integração usa progressão Occult e eventos reais do mod. Casts, mortes atribuídas a servants e comandos confirmados podem alimentar mastery conforme o adapter. Regras de Soul Energy e identidades como Warlock/Necromancer são tratadas pelo contrato runtime auditado.

## Malum — IMPLEMENTED

Além de mastery por spirit harvesting/reaping confirmado, o pack atual declara efeitos para `occult_000`, `occult_001`, `occult_002` e `occult_027`: `spirit_spoils`, `arcane_resonance`, `soul_ward_capacity` e `geas_limit`.

## Eidolon: Repraised — IMPLEMENTED

O adapter observa conclusão real de receitas do Crucible e registra progresso/mastery; descoberta da primeira conclusão também é rastreada pelo contrato auditado.

## Apothic Attributes — ATTRIBUTE REFERENCES PRESENT

Node effects atuais referenciam atributos como `armor_pierce`, `healing_received`, `mining_speed`, `dodge_chance`, `crit_chance`, `life_steal` e `overheal`. Isso comprova uso desses atributos, mas é diferente de provar uma bridge dedicada para bosses do ecossistema Apothic.

## Identity2 — PARTIAL/VERIFY

Há desenho/contratos de identidade no projeto, mas esta edição da wiki não atribui efeitos nominais sem revalidar o runtime exato.

## Create — SPEC/DATA + NODE NAMES/EFFECTS

Há material de especialização/progressão e node effects Technomancer com IDs como `create_resonance` e `create_overdrive`. Os efeitos comprovados desses IDs alteram atributos do Iron's; adapter runtime dedicado a eventos/máquinas Create não foi comprovado nesta revisão.

## Applied Energistics 2 — SPEC/DATA

Há material de especialização/progressão, mas runtime dedicado a redes/crafting precisa de prova antes de ser anunciado como integração completa.