# Apothic Enchanting

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db816c92c9f603c681432f
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Apothic Enchanting
- **Arquivo JAR:** `ApothicEnchanting-1.21.1-1.6.2.jar`
- **Versão 1.21.1:** 1.6.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** RPG, Magia
- **Função:** Módulo oficial do ecossistema Apothic responsável pelo enchanting: Eterna/Quanta/Arcana, shelves, infusion enchanting, Enchantment Library, tomes/utilitários, encantamentos e mesas avançadas. Na linha 1.6.x inclui Table of the Raven e max_eterna.
- **Dependências:** Placebo 9.9.2 + ecossistema Apothic atual; integra com Apothic Attributes 2.10.1 e Apothic Spawners 1.4.0 em superfícies explícitas. Não assume authority de affixes/gems.
- **Sobreposição:** Complementa Apotheosis, mas não duplica affixes/gems. Spawner logic continua em Apothic Spawners; atributos em Apothic Attributes. Outros mods de enchanting podem sobrepor UX/caps, exigindo teste concreto.
- **Compatibilidade/Riscos:** Não usar documentação antiga de Enchantability: desde 1.6.1 ele não dá Arcana; fornece chance de +1 nível de encantamento. Respeitar max_eterna/World Tiers, caps de encantamento, infusion costs e blacklist de spawners. Testar conflitos com mods que alteram anvil/enchant caps e automação.
- **Observações:** mod id: `apothic_enchanting`; runtime 1.6.2. Dossiê interno documenta tables, shelves, infusion, Library, Enchantability atual, max_eterna, integração com World Tiers, Ender Leads e matriz de teste.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge/source/changelog oficiais Apothic Enchanting 1.6.2 + dossiê operacional existente.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/apothic-enchanting
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — runtime 1.6.2, enchanting authority, Eterna/Quanta/Arcana/Enchantability, max_eterna, 30 blocks/20 enchantments and module boundaries confirmed in global QC #35. Estado anterior `Integrado ao Github` preservado como histórico documental.
- **Histórico da decisão:** Manter. Em 07/09/2026 a ficha foi refeita contra o changelog oficial 1.21 até 1.6.2, corrigindo a semântica atual de Enchantability e separando authority de enchanting de affixes/gems/spawners.
- **Data da última decisão:** 2026-09-07

> ✨ **PADRÃO ALEX'S MOBS — DOSSIÊ OPERACIONAL EXAUSTIVO.** Esta página documenta `ApothicEnchanting-1.21.1-1.6.2.jar`, mod id `apothic_enchanting`, em NeoForge 1.21.1. O inventário abaixo cobre **30 blocos registrados, 20 enchantments, 15 effect-components registrados, tomes/itens especiais, stats de enchanting, infusion, Library, Ender Leads, integrações e matriz de teste**.

## 1. Identidade, versão e authority
- **Mod:** Apothic Enchanting.
- **Runtime instalado:** `1.6.2`.
- **JAR:** `ApothicEnchanting-1.21.1-1.6.2.jar`.
- **Mod id:** `apothic_enchanting`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Authority:** enchanting Apothic — stats Eterna/Quanta/Arcana, shelves, infusion, Library, enchantments e itens associados.
- **Não é authority de:** affixes/gems (Apotheosis), atributos gerais (Apothic Attributes), stats/blacklist de spawners (Apothic Spawners).
- **Decisão:** **Manter**.

## 2. Modelo de enchanting — stats canônicos
O sistema expande a Enchanting Table por estatísticas próprias:
- **Eterna** — capacidade/potência base usada para determinar a faixa do enchanting.
- **Quanta** — componente de variância/instabilidade do resultado.
- **Arcana** — componente de qualidade/raridade/tesouro conforme o algoritmo do mod.
- **Stability** — reduz/estabiliza a variância de Quanta quando aplicável.
- **Clues** — quantidade de informações/pistas apresentadas ao jogador conforme o setup.
- **Enchantability** — na linha atual **não é Arcana**; desde `1.6.1`, representa chance percentual de elevar em +1 o nível de um enchantment durante o processo.
- **`apothic_enchanting:max_eterna`** — atributo que limita quanta Eterna pode ser efetivamente utilizada; a linha 1.6.0 registra base/max 100.

### Enchantability — regra que corrige documentação antiga
Desde `1.6.1`:
- não fornece Arcana;
- dá chance de +1 nível;
- pode ultrapassar o max level configurado pelo Apothic;
- níveis acima do cap podem ser marcados com estrela na apresentação.

Qualquer perk/docs anterior que diga “Enchantability aumenta Arcana” está errada para `1.6.2`.

## 3. Catálogo completo de blocos registrados — 30
### Mesas e armazenamento
1. `apothic_enchanting_table` — variante Apothic da mesa de encantamento.
2. `raven_enchanting_table` — **Enchanting Table of the Raven**, mesa endgame com controle manual de Eterna/Quanta/Arcana; em `1.6.2`, recebe JEI transfer handler para infusion.
3. `library` — Enchantment Library para armazenamento/gerenciamento de encantamentos.
4. `ender_library` — variante avançada/Ender da Library.

### Shelves / progressão de enchanting
1. `basic_bookshelf`
2. `beeshelf`
3. `blazing_hellshelf`
4. `crystal_seashelf`
5. `deepshelf`
6. `dormant_deepshelf`
7. `draconic_endshelf`
8. `echoing_deepshelf`
9. `echoing_sculkshelf`
10. `endshelf`
11. `filtering_shelf`
12. `geode_shelf`
13. `glowing_hellshelf`
14. `heart_seashelf`
15. `hellshelf`
16. `infused_hellshelf`
17. `infused_seashelf`
18. `melonshelf`
19. `pearl_endshelf`
20. `seashelf`
21. `sightshelf`
22. `sightshelf_t2`
23. `soul_touched_deepshelf`
24. `soul_touched_sculkshelf`
25. `stoneshelf`
26. `treasure_shelf`

> **Como ler as shelves:** cada shelf contribui com stats de enchanting segundo seus dados/configuração. Esta ficha cataloga integralmente as famílias registradas, mas não congela números históricos de Eterna/Quanta/Arcana sem ler o data component efetivo da versão instalada. Para balanceamento/perk, consulte o stat resultante do provider.

## 4. Progressão estrutural das shelves
O ecossistema usa famílias que formam estágios de progressão, incluindo:
- vanilla/basic;
- Hellshelves / Seashelves;
- Deep/Sculk shelves;
- End shelves;
- shelves especializadas em filtering, treasure, sight, geode, bee/melon e variantes infused/soul-touched/echoing.

A árvore RPG não deve entregar Eterna/Arcana suficiente para pular o gate de estrutura/World Tier sem um contrato explícito. A progressão física das shelves continua parte do gameplay nativo.

## 5. Catálogo completo de enchantments registrados — 20
| Registry id | Função confirmada / estado da auditoria |
| --- | --- |
| `berserkers_fury` | Usa efeitos configuráveis, custo de HP e cooldown; comportamento de berserk é controlado pelo effect-component do mod. |
| `boon_of_the_earth` | Ao minerar alvos suportados, pode produzir item adicional por loot table; respeita o contexto de loot e integrações como Fortune/Silk Touch conforme implementação. |
| `chainsaw` | Quebra árvores/conjuntos de logs no comportamento de “chainsaw”; a linha 1.21 recebeu correções para árvores modded. |
| `chromatic` | Atua sobre wool/cor, randomizando/alterando cor conforme o effect-component. |
| `crescendo_of_bolts` | Permite disparos adicionais de crossbow sem reload entre eles; há integração específica com Ars Spell Crossbow. |
| `endless_quiver` | Enchantment registrado. **Comportamento específico não é inferido nesta ficha sem inspeção do component/runtime correspondente**. |
| `growth_serum` | Chance/efeito de crescimento/regrowth associado a sheep segundo component registrado. |
| `icy_thorns` | Enchantment registrado. Semântica detalhada deve ser tomada do component/runtime da versão antes de integração específica. |
| `infusion` | Enchantment/marker ligado ao sistema de infusion do módulo. Não confundir com recipe infusion em si. |
| `knowledge_of_the_ages` | Converte drops em XP conforme regras do effect; usa tag `apothic_enchanting:cannot_be_converted_to_xp` para exclusões. |
| `life_mending` | Redireciona cura/HP para reparo de durabilidade conforme effect-component do mod. |
| `miners_fervor` | Altera mining speed conforme nível/implementação do effect. |
| `natures_blessing` | Aplica bonemeal/crescimento a crops com custo de durabilidade conforme implementação. |
| `rebounding` | Enchantment registrado. Não inventar trajetória/knockback específico sem source da versão. |
| `reflective_defenses` | Reflete parte do dano bloqueado conforme o effect-component registrado. |
| `scavenger` | Adiciona extra loot roll segundo o pipeline de loot do mod. |
| `shield_bash` | Enchantment registrado. Semântica fina de bash deve ser validada em source/runtime antes de escrever perk. |
| `stable_footing` | Remove/mitiga penalidade de mineração durante voo conforme implementação. |
| `tempting` | Faz mobs compatíveis seguirem o portador conforme o effect registrado. |
| `worker_exploitation` | Dobra wool obtida e causa dano ao sheep segundo o effect-component auditado. |

### Regra de precisão
O registro confirma os 20 enchantments. Onde a mecânica detalhada não foi inspecionada, a ficha **declara explicitamente a lacuna** em vez de inventar descrição a partir do nome.

## 6. Effect components registrados — 15
1. `berserking`
2. `bonemeal_crops`
3. `chainsaw`
4. `chromatic`
5. `crescendo`
6. `drops_to_xp`
7. `earths_boon`
8. `exploitation`
9. `extra_loot_roll`
10. `growth_serum`
11. `miners_fervor`
12. `reflective`
13. `repair_with_hp`
14. `stable_footing`
15. `tempting`

Esses components são evidence de comportamento provider-native. Não duplicar o mesmo efeito com listener externo quando o enchantment já o executa.

## 7. Tomes — catálogo
A linha auditada registra nove typed tomes:
- Boots Tome;
- Bow Tome;
- Chestplate Tome;
- Fishing Tome;
- Helmet Tome;
- Leggings Tome;
- Other Tome;
- Pickaxe Tome;
- Weapon Tome.

Também existem:
- **Extraction Tome**;
- **Scrap Tome**;
- **Improved Scrap Tome**.

Esses itens participam de manipulação/seleção/extração de enchantments. Qualquer automação deve respeitar consumo e resultado nativos.

## 8. Itens especiais registrados
Além dos tomes e block items, o source registra:
- `inert_trident`;
- `infused_breath`;
- `prismatic_web`;
- `warden_tendril`;
- `flimsy_ender_lead` — durabilidade 8;
- `ender_lead` — durabilidade 64;
- `occult_ender_lead` — durabilidade 1024, armazena entity component e pode interagir com spawners;
- três music discs temáticos: **Eterna**, **Quanta** e **Arcana**.

## 9. Ender Leads
### Flimsy Ender Lead
Versão limitada e de baixa durabilidade.

### Ender Lead
Versão mais robusta para manipulação de entidades suportadas.

### Occult Ender Lead
- armazena identidade/estado de entity conforme data component do item;
- pode alterar o mob-alvo de spawner quando a interação é válida;
- **deve respeitar** `apothic_spawners:blacklisted_from_spawners`.

Boundary:
- item = Apothic Enchanting;
- blacklist/stats do spawner = Apothic Spawners;
- não bypassar target proibido por outro adapter.

## 10. Infusion Enchanting
Infusion é um sistema data-driven de transformação que usa requisitos de enchanting stats/nível.
- recipes declaram requisitos;
- a mesa valida stats/custo;
- a UI mostra requisitos;
- na linha 1.6.0, o nível exibido passou a ser limitado ao requisito de Eterna da recipe;
- `1.6.2` adiciona JEI transfer no Table of the Raven para infusion.

### Anti-dupe
Processing externo não pode conceder output “previsto” antes da recipe provider-native concluir. Consumo parcial, cancelamento, reload e transfer JEI devem ser testados.

## 11. Enchantment Library
A Library permite armazenar e gerenciar enchantments como recurso organizado.

Superfícies críticas:
- inserção;
- retirada;
- persistência;
- níveis armazenados;
- compatibilidade com enchantments modded;
- save/load/restart;
- automação/menus quando suportados.

A Ender Library é uma variante registrada distinta e deve ser tratada pelo provider, não assumida como “mesma inventory global” sem confirmar sua semântica.

## 12. Enchanting Table of the Raven
Mesa endgame registrada como `raven_enchanting_table`.
- permite definir manualmente Eterna/Quanta/Arcana dentro das regras do provider;
- conecta-se ao gate de `max_eterna`/progressão quando aplicável;
- `1.6.2` possui JEI transfer handler para infusion.

Não é justificativa para ignorar World Tier ou conceder stats sem custo/gate.

## 13. `max_eterna` e World Tiers
A linha 1.6.0 adicionou `apothic_enchanting:max_eterna`.

Quando Apotheosis está presente, a UI pode indicar **World Tiers** como fonte do limite. Isso produz boundary explícita:
- Apotheosis / progression = gate global;
- Apothic Enchanting = execução do enchanting;
- perk externa = consumer, não bypass.

## 14. Loot / XP enchantments e riscos de double-processing
### Boon of the Earth
Usa loot tables para output. Não adicionar segundo drop pela mesma ação.

### Scavenger
Adiciona extra loot roll. Outros Looting/loot modifiers precisam ser avaliados pelo pipeline correto.

### Knowledge of the Ages
Converte drops em XP; respeita exclusion tag. Não conceder também XP equivalente a partir dos drops já convertidos.

### Worker Exploitation
Modifica wool/drop e causa dano ao animal; não duplicar wool em evento pós-loot.

## 15. Combat/support enchantments
- Berserker's Fury: HP/cooldown/effects provider-native.
- Crescendo of Bolts: múltiplos bolts/disparos e integração com Spell Crossbow.
- Reflective Defenses: dano refletido a partir de bloqueio.
- Life Mending: canal de cura→reparo.
- Stable Footing: contexto de mineração em voo.
- outros enchantments registrados devem permanecer fail-closed para integração específica até semântica confirmada no source/runtime.

## 16. Integrações do pack
### Apotheosis
Fornece progression/World Tier/contexto e receitas complementares; não duplica o domínio separado do módulo.

### Apothic Attributes
Expõe atributo `max_eterna`/infra relevante; outros atributos continuam naquele provider.

### Apothic Spawners
Blacklist/stats de spawner são authority dele; Occult Ender Lead é consumer.

### Ars Nouveau / Spell Crossbow
Crescendo possui integração específica. Isso **não** unifica todos os enchantments com spellcasting Ars.

### JEI
UI/transfer para recipes; JEI não executa a recipe.

### Outros mods de enchanting/anvil
Podem alterar caps/regras. Overcap com estrela, max levels e anvil behavior precisam de teste conjunto.

## 17. Riscos técnicos
1. documentação antiga de Enchantability;
2. bypass de `max_eterna`/World Tier;
3. duplicate loot/XP em Boon/Scavenger/Knowledge;
4. double-healing/repair em Life Mending;
5. anvil/cap interaction com overcap levels;
6. Occult Ender Lead bypassando blacklist;
7. infusion automation dupe/perda;
8. Library persistence em restart;
9. enchantments modded com categorias não previstas;
10. integração específica inventada para enchantment cujo behavior não foi source-verified.

## 18. Regras para RPG Skill Tree
- Não conceder Eterna/Arcana suficiente para pular progression sem contrato.
- Não duplicar effect components dos enchantments.
- Mastery por enchanting deve usar ação discreta e resultado real, nunca tick/mesa aberta.
- JEI/tooltip/preview são read-only; não contam como craft/enchant concluído.
- Provider ausente/incompatível = fail-closed.
- Recipe/loot conversion precisa de causal identity para evitar recompensa dupla.

## 19. Matriz de validação
### Enchanting stats
- Eterna/Quanta/Arcana/Stability/Clues;
- Enchantability atual (+1 chance, sem Arcana);
- `max_eterna` e World Tier.

### 30 blocos
- smoke de registry/model/recipe;
- cada família de shelf contribuindo com stats esperados;
- Library/Ender Library save/load;
- Table of Raven.

### 20 enchantments
- aplicar cada enchantment em item elegível;
- confirmar effect quando conhecido;
- para os não detalhados nesta auditoria, validar behavior antes de qualquer integração RPG.

### Itens
- 9 typed tomes;
- extraction/scrap/improved scrap;
- materiais especiais;
- 3 Ender Leads;
- music discs.

### Integração
- infusion + JEI transfer;
- Occult Ender Lead com target permitido/bloqueado;
- Crescendo + Ars Spell Crossbow;
- loot/XP conversions sem double count;
- dedicated-server smoke;
- reload/restart e persistência.

## 20. Fontes
- Source oficial `Shadows-of-Fire/Apothic-Enchanting`, branch `1.21`, especialmente `Ench.java` e effect components.
- Changelog oficial da linha 1.21 até `1.6.2`.
- CurseForge oficial.
- Modlist física do projeto — authority de JAR/runtime.
