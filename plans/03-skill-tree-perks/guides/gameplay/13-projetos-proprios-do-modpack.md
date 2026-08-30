# 13. Projetos próprios do modpack — integração canônica para perks

A partir de 30/08/2026, os quatro projetos próprios fazem parte da auditoria obrigatória das perks.

**Fonte canônica completa:** [Projetos Próprios do Modpack](../projects/README.md)

Este capítulo é apenas o recorte de **Gameplay e Sistemas**. Para decidir `Provider/Mods`, hook, status e fail-closed, o Chat 1 deve ler os quatro dossiês completos e a matriz cruzada.

## RPG Skill Tree — autoridade central de progressão

[Dossiê completo](../projects/01-rpg-skill-tree.md)

- **Canônico:** estado persistente de jogador, Level/XP/CPP/atributos, serviços de progressão, núcleo da Skill Tree 03.01–03.05, runtime único de node effects, world scaling completo e API pública read-only.
- **Canônico específico:** subtrees Technomancer, Warlock, Druid e Metamorph.
- **Misto/planejado:** fechamento geral de classes/masteries, Stage 05 combat-magic hooks, várias integrações Stage 06, itemização Stage 11, corpos/clones Stage 12 e cartografia Stage 13.
- Perks usam boundaries canônicos e nunca storage/attachments internos.
- Uma ação = uma mutação canônica; Mastery exige autoria causal e deduplicação.

## Volcanoes — geologia, hazards ambientais e engenharia física

[Dossiê completo](../projects/02-volcanoes.md)

- **Canônico:** geologia/strata/depósitos persistentes, tectônica/terremotos, volcano sites/magma/lava/erupções/cinzas/piroclastos/geotermia, Atmosphere, respiração/gases/poluição, pressão e protection equipment.
- `GeologicalDepositSource` é uma SPI read-only apropriada para descoberta/prospecção bounded; o core não cria scanner concorrente.
- Cold Sweat permanece autoridade de temperatura corporal; Destroy conserva sua autoridade de poluição.
- Sable/Aeronautics integram pressão física sem inventar cabine selada quando a API não prova o estado.
- **RNS parcial/fail-closed:** identidade hidrotermal Cu/Fe/Au existe, mas ownership de worldgen físico continua RNS até placement Volcanoes ser provado.
- Sem Mastery por tick de gás, pressão, calor, tremor ou mera permanência ambiental.

## Enshrouded — Shroud, Exposure, Corrupted Ecology e Flame

[Dossiê completo](../projects/03-enshrouded.md)

- **Canônico:** Shroud Field persistente/bounded, `ShroudQuery`, Terrain Corruption via `MutationAuthority`, Exposure/Madness/Deadly Shroud/Red Sludge e ecologia corrompida.
- **Flame Progression parcial:** Flame State, Flame Altar e Level 1 Ritual estão fechados; Sanctuary ainda está aberto.
- `FlamePassageQuery` é boundary real; lookup incerto falha fechado.
- Lich/Story, Client Experience, Integrations e Hardening ainda não podem ser usados como provider concluído.
- Shroud/Exposure não são Black Arcana Corruption/Strain.
- Sem Mastery por tick de exposição ou edge-dancing.

## Black Arcana — risco arcano como gameplay

[Dossiê completo](../projects/04-black-arcana.md)

- **Canônico:** Arcana Core, Integration Layer e World Safety.
- **Parcial:** Casting & UX possui código mergeado com QA manual aberta; Arcane Danger tem componentes reais, mas o Stage 05A segue ativo.
- Danger Model, Arcane Resistance, Corruption Resistance, Arcane Strain e Arcane Backlash precisam ser distinguidos de hazards físicos ou Shroud.
- `ARCANE_BACKLASH` é terminal: não recursa, não crita, não lifesteala e não concede Mastery/proc ofensivo.
- Equipment set bonus infrastructure já está em `main`, mas não representa fechamento integral de Equipment/Containment.
- Rituals, Spell Domains e Progression & Balance permanecem preparatórios/planejados.

## Matriz cruzada obrigatória

[Matriz de integração cruzada](../projects/05-cross-project-integration-matrix.md)

Antes de fechar uma perk híbrida, registrar explicitamente:

- authority principal;
- provider/consumer secundário;
- direção da bridge;
- hook causal;
- identidade de deduplicação;
- fallback;
- fail-closed.

Integração temática não cria automaticamente um hook.
