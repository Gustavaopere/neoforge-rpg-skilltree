# Ars Nouveau

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8143bbeafa26cf67cf33
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Ars Nouveau
- **Arquivo JAR:** `ars_nouveau-1.21.1-5.13.1.jar`
- **Versão 1.21.1:** 5.13.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Magia, Automação
- **Função:** Provider/authority central do ecossistema Ars: 85 spell parts de produção, 24 rituals, 6 familiars, 20 perks, 3 famílias de armor com perk slots, Source, Sourcelinks, relays, turrets, apparatus, storage/crafting e APIs extensíveis.
- **Dependências:** Provider-base do ecossistema Ars. Addons físicos atuais consomem seus registries/Source/cast contracts; Ars 'n' Spells 3.3.2 integra player mana com Iron's sem substituir Source.
- **Sobreposição:** Há sobreposição temática com outros mods de magia, mas o ownership técnico é próprio. Addons Ars estendem o core; Ars 'n' Spells unifica player mana com Iron's; nenhum deles substitui Source, GlyphRegistry, rituals ou cast engine do Ars Nouveau.
- **Compatibilidade/Riscos:** Riscos centrais: double-cast, double-dip de mana/spell power, duplicação de Source em bridges, automação creditada como cast manual, coordinates incorretas em portals/sublevels, recipe conflicts no Storage Lectern e client visual tratado como authority.
- **Observações:** mod id `ars_nouveau`. APIRegistry 5.13.1 registra 85 spell parts (5 methods, 13 augments, 67 effects), 24 rituals, 6 familiars, 20 perks e 3 scryers. O core mantém Source separado de player mana.
- **Procedência:** modlist.txt física atual de 08/09/2026 + source Ars Nouveau exact release commit `112920ff774831f204031da75b4c4e73d3765157` (5.13.1) + dossiê operacional existente.
- **Fonte:** https://github.com/baileyholl/Ars-Nouveau
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — Ars Nouveau 5.13.1 exact release checkpoint, 85 production spell parts, 24 rituals, 6 familiars, 20 perks, Source/relays/turrets/apparatus/storage authority confirmed in global QC #47. Estado anterior `Integrado ao Github` preservado como histórico documental.
- **Histórico da decisão:** Manter. Ficha reconstruída do zero em 07/09/2026 contra o commit exato 5.13.1. Ars Nouveau é provider central e não deve ser tratado como redundante com seus próprios addons.
- **Data da última decisão:** 2026-09-07

> 📘 **PADRÃO ALEX'S MOBS — DOSSIÊ OPERACIONAL EXAUSTIVO.** Runtime físico: `ars_nouveau-1.21.1-5.13.1.jar`, mod id `ars_nouveau`, NeoForge 1.21.1. Esta ficha usa como **exact release checkpoint** o commit upstream **`112920ff774831f204031da75b4c4e73d3765157`**, commit `5.13.1`, onde `gradle.properties` muda `mod_version=5.13.0` para `5.13.1`. O snapshot posterior `d16c9398...` ainda declara 5.13.1, mas está 3 commits à frente e contém mudanças pós-release; não é tratado como equivalência exata do JAR instalado. Ars Nouveau é **provider/authority central** de spell grammar, Source, spellbooks, glyphs, rituals, familiars, perks, Source automation e grande parte da infraestrutura consumida pelos addons Ars do pack.

## 1. Identidade e versão
- **Mod:** Ars Nouveau.
- **JAR:** `ars_nouveau-1.21.1-5.13.1.jar`.
- **Runtime:** `5.13.1`.
- **Mod id:** `ars_nouveau`.
- **Loader/jogo:** NeoForge 1.21.1 / Java 21.
- **Exact release checkpoint:** `112920ff774831f204031da75b4c4e73d3765157`.
- **Later 5.13.1-labelled snapshot (not exact JAR equivalence):** `d16c939835ec9eae27d2eece42d19c572b46389c`.
- **Decisão:** **Manter**.

## 2. Papel arquitetural
Ars Nouveau não é apenas um mod de spells. Ele fornece:
1. gramática modular de feitiços;
2. spellbooks/casters e aprendizagem de glyphs;
3. recurso **Source** e sua infraestrutura;
4. rituais;
5. enchantment/apparatus recipes;
6. familiars e criaturas utilitárias;
7. perks e armor perk providers;
8. turrets e automação;
9. storage/crafting mágico;
10. API/registries usados por numerosos addons.

Qualquer integração própria deve consumir os contracts reais do Ars, não reimplementar um segundo cast engine, Source ledger ou perk registry.

## 3. Spell grammar — 85 spell parts de produção
O `APIRegistry.setup()` da build 5.13.1 registra **85 spell parts normais**: **5 methods/forms + 13 augments + 67 effects**. Conteúdo WIP é separado e não entra em production.

### Methods/forms — 5
1. Projectile
2. Touch
3. Self
4. Pantomime
5. Underfoot

### Augments — 13
1. Accelerate
2. Decelerate
3. Split
4. Amplify
5. AOE
6. Extend Time
7. Pierce
8. Dampen
9. Extract
10. Fortune
11. Duration Down
12. Sensitive
13. Randomize

### Effects — 67
1. Break
2. Harm
3. Ignite
4. Phantom Block
5. Heal
6. Grow
7. Knockback
8. Light
9. Dispel
10. Launch
11. Pull
12. Blink
13. Explosion
14. Lightning
15. Slowfall
16. Fangs
17. Summon Vex
18. Ender Chest
19. Harvest
20. Fell
21. Pickup
22. Interact
23. Place Block
24. Snare
25. Smelt
26. Leap
27. Delay
28. Redstone
29. Intangible
30. Invisibility
31. Wither
32. Exchange
33. Craft
34. Flare
35. Cold Snap
36. Conjure Water
37. Gravity
38. Cut
39. Crush
40. Summon Wolves
41. Summon Steed
42. Summon Decoy
43. Hex
44. Glide
45. Rune
46. Freeze
47. Name
48. Summon Undead
49. Firework
50. Toss
51. Bounce
52. Windshear
53. Evaporate
54. Linger
55. Sense Magic
56. Infuse
57. Rotate
58. Wall
59. Animate
60. Burst
61. Orbit
62. Reset
63. Wololo
64. Rewind
65. Bubble
66. Windburst
67. Prestidigitation

## 4. Spell authority
Um spell é uma sequência de spell parts. Methods determinam como o spell resolve; effects executam a ação; augments modificam propriedades compatíveis. Addons podem registrar novas parts no mesmo `GlyphRegistry`, mas isso **não transfere a authority do cast engine** para o addon.

Regras para integrações/perks:
- causalidade deve seguir o cast real;
- custo/mana deve ser aplicado uma vez;
- projectile atrasado continua ligado ao cast que o criou;
- turret/familiar/automation cast não deve ser confundido com cast manual do jogador sem evidência.

## 5. Ritual registry — 24 rituals
A build 5.13.1 registra:
1. Dig
2. Moonfall
3. Cloudshaper
4. Sunrise
5. Disintegration
6. Pillager Raid
7. Overgrowth
8. Breed
9. Healing
10. Warp
11. Scrying
12. Flight
13. Gravity
14. Wilden Summoning
15. Animal Summoning
16. Binding
17. Awakening
18. Harvest
19. Mob Capture
20. Conjure Plains
21. Forestation
22. Flowering
23. Conjure Desert
24. Deny Spawn

Rituals são provider-native do Ars. Addons como Ars 'n' Spells registram rituals adicionais no mesmo registry, mas devem manter IDs/costs/lifecycle próprios.

## 6. Familiars — 6
O core registra familiar holders para:
- Starbuncle;
- Drygmy;
- Whirlisprig;
- Wixie;
- Bookwyrm;
- Amethyst Golem/Familiar.

Familiars têm identity/behavior próprios e não devem ser tratados genericamente como pets comuns quando um sistema depende de autoria, summon ownership ou automação.

## 7. Perks nativos — 20
O `PerkRegistry` recebe:
1. Empty
2. Starbuncle
3. Step Height
4. Immolate
5. Depths
6. Feather
7. Gliding
8. Jump Height
9. Looting
10. Magic Capacity
11. Magic Resist
12. Potion Duration
13. Repairing
14. Saturation
15. Spell Damage
16. Chilling
17. Ignite
18. Totem
19. Vampiric
20. Knockback Resist

Esses perks são o provider real de armor-thread/perk behavior Ars. Não duplicar seus efeitos em perks do RPG Skill Tree sem bridge explícita e deduplicação.

## 8. Armor perk providers — 3 famílias
O core registra perk-slot layouts para três famílias de armor:
- **Battlemage**;
- **Arcanist**;
- **Sorcerer**.

Cada família possui hood/chest/leggings/boots e progressão de slots por tier. A distribuição de slots varia por peça e família; não assumir uniformidade.

## 9. Source — recurso central
**Source** é o recurso energético/mágico da infraestrutura Ars. Ele é produzido por Sourcelinks, armazenado em Source Jars e movimentado por relays. Regras de integração:
- Source não é mana do jogador;
- Source não deve ser duplicado como FE/AE energy;
- transferências devem respeitar quantidade real e lifecycle do block entity;
- cross-dimension/sublevel behavior só é válido quando uma bridge específica o implementa.

## 10. Sourcelinks — 5 famílias core
A documentação/registry da build cobre:
1. **Agronomic Sourcelink**;
2. **Volcanic Sourcelink**;
3. **Vitalic Sourcelink**;
4. **Mycelial Sourcelink**;
5. **Alchemical Sourcelink**.

Cada tipo converte uma família de atividade/recurso em Source segundo suas próprias regras. Não usar um evento genérico de “atividade ocorreu” para conceder Source sem reproduzir o provider correto.

## 11. Source storage e relay network
Componentes centrais confirmados:
- **Source Jar**;
- **Creative Source Jar**;
- **Source Relay**;
- **Relay Deposit**;
- **Relay Splitter**;
- **Relay Collector**;
- **Relay Warp**.

Esses blocos formam a rede de armazenamento/roteamento de Source. Addons como Ars Controle, Ars Creo, Ars Sable e Sophisticated compat atravessam esse domínio; o core continua sendo authority do valor Source.

## 12. Spell Turrets e automação
Família core inclui pelo menos:
- **Basic Spell Turret**;
- **Enchanted Spell Turret**;
- **Rotating Spell Turret**;
- demais variantes/behaviors do core utilizadas pelos registries/addons.

Turrets executam spells reais sob behavior registrado. Em Mastery/XP, a existência de um spell resolver não prova autoria manual do jogador.

## 13. Enchanting Apparatus e Arcane Pedestal
O **Enchanting Apparatus** é uma estação central de crafting/infusion Ars operando em conjunto com pedestals. A API expõe recipe types de enchanting e adiciona no post-init:
- apparatus;
- enchantment;
- reactive;
- spell write;
- armor upgrade;
- prestidigitation.

Addons podem fornecer recipes nesses tipos. Não substituir o apparatus por crafting genérico se o recipe provider requer Source/pedestals/ritual semantics.

## 14. Imbuement Chamber
A **Imbuement Chamber** possui recipe type próprio registrado no `ImbuementRecipeRegistry`. É outra superfície de progressão/crafting e não é equivalente ao Enchanting Apparatus.

## 15. Scribes Table e glyph learning
A **Scribes Table** participa da criação/aprendizado de glyphs e itens associados. A 5.13.1 corrige crash quando glyph recipes possuem mais de 10 itens e corrige animação jagged dos itens sobre a mesa. Isto é parte específica do patch instalado.

## 16. Storage Lectern / Crafting Lectern
O `storage_lectern` integra inventários conectados ao fluxo de crafting/storage Ars. Ars Polymorphia atua especificamente nessa superfície para resolver conflitos de recipes. O lectern continua provider de storage/crafting; Polymorphia é apenas a camada de escolha.

## 17. Mob Jar
O core registra **Mob Jar** e possui registry de behaviors específicos. Na 5.13.1 existem behaviors confirmados para 24 tipos/entidades:
Elder Guardian, Creeper, Chicken, Villager, Sheep, Frog, Piglin, Ghast, Squid, Glow Squid, Blaze, Panda, Mooshroom, Ender Dragon, Pufferfish, Allay, Ars Dummy/Decoy, Item, Wither, Armadillo, Cat, Snow Golem, Breeze e Sniffer.

Mob Jar não é apenas armazenamento visual; alguns tipos têm behavior custom quando interagidos/contidos.

## 18. Scrying
O core registra três scryers:
- Single Block Scryer;
- Compound Scryer;
- Tag Scryer.

Também possui infraestrutura como **Scryer's Oculus** e **Scryer's Crystal**. Integrações de scanning/knowledge devem consultar o estado real de scrying em vez de inferir descoberta por proximidade.

## 19. Planarium
`Planarium` e `Planarium Projector` são blocks/block entities do core ligados a representação/posição espacial. Ars Sable possui adaptações específicas para coordinates/sublevels; logo qualquer uso em espaço físico móvel precisa respeitar essa bridge.

## 20. Portals e Warp
O core registra Portal Block/Tile e Ritual of Warp. Addons de Two-Way Portals e Sable estendem essa superfície. Boundary:
- core Ars define portal/warp primitives;
- addon de Two-Way Portals adiciona vínculo bidirecional;
- Ars Sable corrige semântica espacial em sublevels;
- não duplicar teleport cooldown/target resolution em integração própria.

## 21. Automation creatures
Além de familiars, criaturas como Starbuncle, Drygmy, Whirlisprig e Wixie participam de logística/produção/crafting. Addons podem aumentar jobs/transportes, mas ownership das entidades e behaviors base continua Ars.

## 22. Wixie Cauldron
O core registra `Wixie Cauldron` + block entity. É superfície de automation/crafting específica do Wixie e deve ser tratada separadamente de crafting table ou apparatus.

## 23. Archwood ecosystem
O core registra quatro famílias principais de Archwood:
- Cascading;
- Blazing;
- Vexing;
- Flourishing.

Cada uma possui logs/leaves/saplings/wood e componentes decorativos derivados. Também existem fruits/pods associados, como Mendosteen, Bastion, Frostaya e Bombegranate. Para esta ficha, variantes puramente decorativas de slab/stair/sourcestone não são enumeradas individualmente por não alterarem mecânica.

## 24. Utility/functional blocks confirmados
Além das famílias acima, a build registra superfícies funcionais como:
- Arcane Core;
- Alteration Table;
- Potion Diffuser;
- Repository/Repository Controller;
- Ritual Brazier + Brazier Relay;
- Item Detector;
- Spell Sensor;
- Redstone Relay;
- Arcane Platform/Mini Pedestal;
- Magelight Torch;
- Rune;
- Void Prism;
- Mage Block;
- False Weave;
- Mirror Weave;
- Ghost Weave;
- Sky Weave;
- temporary/intangible/light blocks de execução de spells.

Esses componentes devem ser avaliados por função real antes de serem integrados a automações externas.

## 25. Spell sounds e particles
O post-init registra spell sound families como default, empty, Gaia, Tempestry e Fire, além de registries extensíveis de particles/timelines/motions. Isto é apresentação/telemetria; não é authority de gameplay.

## 26. Data-driven e extensibilidade
Ars Nouveau expõe registries/API para addons registrarem:
- spell parts;
- rituals;
- familiars;
- perks;
- scryers;
- jar behaviors;
- spell sounds;
- recipe types;
- particle timelines/configurations.

Essa extensibilidade explica por que tantos addons do pack dependem do core. Integração própria deve preferir API/registry nativo antes de mixin/reflection.

## 27. Patch específico 5.13.1
O changelog version-pinned confirma:
- mais relações na documentação;
- nomes/flavor de caster tomes traduzíveis;
- projectiles preservam momentum ao interagir com spell prisms;
- correção de animação dos itens sobre Scribes Table;
- correção de crash em glyph recipes com mais de 10 itens;
- correção de Abjuration Essence não limpando Prestidigitation.

Não importar mudanças de releases posteriores sem atualizar a modlist física.

## 28. Addons instalados que dependem/estendem o core
O pack físico contém múltiplos addons Ars, entre eles Ars Additions, Controle, Creo, Elemancy, Elemental, Hex, Morph, 'n' Spells, Polymorphia, Sable, Technica, Two-Way Portals, Zero, Flavors & Delight e Sophisticated Compatibility. Cada addon deve manter seu ownership específico; nenhum substitui o core.

## 29. Authority com Ars 'n' Spells
Quando Ars 'n' Spells está ativo, mana do jogador pode ser unificada com Iron's conforme config. Isso **não transforma Source em mana**. Separação obrigatória:
- player mana authority = definida pela bridge Ars 'n' Spells;
- Source infrastructure = Ars Nouveau;
- casting grammar Ars = Ars Nouveau.

## 30. Dedicated server / client boundary
- spell resolution, Source, inventories, rituals e persistent state devem ser server-authoritative;
- renderers, particles, HUD e client extensions não podem decidir resultado de cast;
- addons com client-only render hooks devem ser gated corretamente;
- não carregar classes de renderer em dedicated server paths.

## 31. Riscos principais no pack
1. double-cast entre spell resolver e integração própria;
2. double-dip de mana/spell power com Ars 'n' Spells;
3. Source duplicado em bridges Create/Sophisticated/Sable;
4. turret/familiar automation creditada como ação manual;
5. spell school/perk modifiers somados novamente por RPG perks;
6. cross-dimension coordinates incorretas em portals/Planarium/sublevels;
7. addon registrar spell/ritual/perk duplicado;
8. recipe conflict no Storage Lectern sem Polymorphia;
9. stale references após chunk unload/restart;
10. client visual usado como autoridade.

## 32. Matriz de validação mínima do pack
- client + dedicated server boot;
- 85 spell parts registrados e spellbook compose/cast;
- glyph learning/Scribes Table;
- 24 rituals;
- 6 familiars;
- 20 perks e 3 armor families;
- cada um dos 5 Sourcelinks;
- Source Jar + relay/deposit/splitter/collector/warp;
- Basic/Enchanted/Rotating turret paths;
- Enchanting Apparatus e Imbuement Chamber;
- Storage Lectern/crafting conflict;
- Mob Jar behaviors;
- Wixie/Drygmy/Starbuncle/Whirlisprig automation;
- portal/warp + Two-Way Portals;
- Planarium + Ars Sable;
- Ars Creo moving blocks;
- Ars 'n' Spells mana/scaling;
- death/respawn/reconnect/dimension change;
- chunk unload/server restart;
- reload/datapack recipes;
- no client-only classloading on dedicated server.

## 33. Fontes
- Modlist física do projeto, 07/09/2026.
- Guia consolidado de Magia do projeto, snapshot 07/09/2026.
- Source oficial `baileyholl/Ars-Nouveau`, commit exato `d16c939835ec9eae27d2eece42d19c572b46389c` (5.13.1).
- `APIRegistry`, `BlockRegistry` e `changelog.md` da build version-pinned.
