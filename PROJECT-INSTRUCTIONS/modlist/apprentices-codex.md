# Apprentice's Codex

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db81409055e2ac36a86d40
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Apprentice's Codex
- **Arquivo JAR:** `apprentice_codex-0.9.7.1+mc1.21.1.jar`
- **Versão 1.21.1:** 0.9.7.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Magia, RPG, Automação
- **Função:** Addon side-grade de Iron's Spells 'n Spellbooks. Na versão instalada 0.9.7.1, o SpellRegistry do source pin exato 305ea6a registra 83 spells distribuídos entre Blood, Ender, Evocation, Fire, Holy, Ice, Lightning, Nature e Eldritch, além de grande catálogo de armas/staves/shields/Curios/armaduras, blocos de crafting e Spell Dispenser com automação/redstone/Create. Não cria nova escola nem mana/progressão obrigatória paralela.
- **Dependências:** Iron's Spells 'n Spellbooks 3.16.3, Create 6.0.10 e NeoForge 21.1.248 satisfazem os gates documentados da linha 0.9.7.x. O addon permanece Beta; updates maiores exigem revalidação.
- **Sobreposição:** Não é o mesmo papel do Spell Codex. Apprentice's Codex adiciona conteúdo jogável/automação sobre Iron's; Spell Codex atua na organização/progressão da coleção. Iron's conserva authority do cast/mana/schools.
- **Compatibilidade/Riscos:** Addon beta e datapack formats podem mudar entre versões. Spell Dispenser usa redstone + mana potions e possui suporte opcional a Create contraptions: impedir double-processing de cast/mana/XP/Mastery e não presumir autoria do jogador em automação. Linha 0.9.7 alterou Arcane in a Jar; validar blocos existentes após upgrade.
- **Observações:** mod id: `apprenticecodex`; runtime 0.9.7.1, beta. O upstream textual pode resumir o addon como ~60 spells; a ficha chegou a registrar 85 por erro editorial, mas o source pin exato da própria versão 0.9.7.1 (`305ea6aef7bb9642a9d1fc2b9042f3dfb2ce5b2e`) registra 83 spells. Para catalogação técnica prevalece o registry desse pin.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge/wiki/source oficiais Apprentice's Codex 0.9.7.1 + source pin `305ea6aef7bb9642a9d1fc2b9042f3dfb2ce5b2e` + dossiê operacional existente.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/apprentices-codex
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — Apprentice's Codex 0.9.7.1, source-pin roster 83 spells, Iron's 3.16.3/Create 6.0.10/NeoForge 21.1.248 gates and Spell Dispenser causality confirmed in global QC #38. Estado anterior `Integrado ao Github` preservado como histórico documental.
- **Histórico da decisão:** Manter. Em 07/09/2026 a ficha foi refeita e elevada ao padrão Alex's Mobs. A reconciliação final com o source pin exato 0.9.7.1 substituiu tanto o resumo upstream de “cerca de 60 spells” quanto a contagem editorial intermediária de 85 pelo inventário técnico correto do SpellRegistry: 83 registry ids, preservando Iron's como authority de mana/schools/cast/cooldown.
- **Data da última decisão:** 2026-09-07

> 📖 **PADRÃO ALEX'S MOBS — DOSSIÊ OPERACIONAL EXAUSTIVO.** Esta ficha documenta `apprentice_codex-0.9.7.1+mc1.21.1.jar`, mod id `apprenticecodex`, em NeoForge 1.21.1. O `gradle.properties` upstream fixa `mod_version=0.9.7.1` e o `SpellRegistry` do source pin exato `305ea6aef7bb9642a9d1fc2b9042f3dfb2ce5b2e` registra **83 spells**, não apenas o resumo textual antigo de “cerca de 60”. O inventário abaixo usa o registry como authority e separa conteúdo, gear, automação, datapacks, ownership e riscos sem criar escola/mana/progressão paralela ao Iron's.

## 1. Identidade, versão e estado
- **Mod:** Apprentice's Codex.
- **Runtime instalado:** `0.9.7.1`.
- **JAR físico:** `apprentice_codex-0.9.7.1+mc1.21.1.jar`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal upstream:** Beta.
- **Ambiente:** cliente + servidor.
- **Papel:** ampliar Iron's Spells com side-grades de combate, suporte, exploração, gathering/crafting, gear e blocos utilitários.
- **Decisão:** **Manter**.

## 2. Escopo do addon
O projeto se define como um addon leve de Iron's Spells focado em **side-grade magic**. A documentação atual afirma explicitamente que ele **não adiciona novos mobs, novas spell schools, minérios, biomas ou progressão obrigatória**. Mundos existentes podem receber o conteúdo sem restart; novas villages podem incluir uma profissão de villager do addon.

Essa distinção é importante para o pack: Apprentice's Codex amplia opções dentro do ecossistema Iron's, mas não deve receber uma árvore de escola independente só por ter muito conteúdo.

## 3. Catálogo técnico completo de spells — 83 registry ids
O `SpellRegistry` do source pin exato da versão `0.9.7.1` registra **83 spells**. A organização abaixo segue os grupos comentados no próprio source; os nomes em `code` são os registry ids canônicos do addon.

### 3.1 Blood — 3
1. `higanbana`
2. `mist_form`
3. `blood_brand`

### 3.2 Ender — 15
1. `arcane_blast`
2. `arcane_beam`
3. `personal_shelf`
4. `assist_wings`
5. `manifestation_grimoire`
6. `mantis_leap`
7. `auto_magnet`
8. `remote_eye`
9. `mana_slash`
10. `long_stride`
11. `rift_hole`
12. `demicreator_wings`
13. `mirage_avoidance`
14. `anchor_blink`
15. `servant_gaze`

### 3.3 Evocation — 17
1. `archer_multiple`
2. `feather_rush`
3. `slash_blade`
4. `precision_jack`
5. `auto_turret`
6. `companion_trunk`
7. `search_beacon`
8. `tamers_pocket`
9. `silent_assassin`
10. `tiro_volley`
11. `bound_sword`
12. `bound_bow`
13. `lethal_assault`
14. `edge_dancer`
15. `linear_build`
16. `fujin`
17. `call_broom`

### 3.4 Fire — 5
1. `thermal_process`
2. `magic_spear`
3. `artisan_smash`
4. `combustion_jet`
5. `catch_flame`

### 3.5 Holy — 11
1. `mage_light`
2. `phalanx_charge`
3. `mana_charge`
4. `force_field`
5. `sense_evil`
6. `illuminate_stellar`
7. `unite_luna`
8. `mystic_shield`
9. `divine_possession`
10. `mana_mending`
11. `wizardlamp`

### 3.6 Ice — 3
1. `frost_rune`
2. `inscribe_ice`
3. `totem_of_permafrost`

### 3.7 Lightning — 10
1. `sky_edge`
2. `commence_fire`
3. `quick_arms`
4. `breaching_enemy`
5. `bullet_stream`
6. `fly_swatter`
7. `shock`
8. `dual_acrobat`
9. `field_overseer`
10. `shiden`

### 3.8 Nature — 12
1. `compound_phial`
2. `tiny_lumberjack`
3. `graced_rain`
4. `world_flatter`
5. `earth_forge`
6. `grind_runner`
7. `treasure_divination`
8. `healing_bloom`
9. `harvest_moon`
10. `extract`
11. `heavenly_fist`
12. `terra_resonance`

### 3.9 Eldritch — 7
1. `palette_shift`
2. `moon_light`
3. `deep_sensor`
4. `spectral_wing`
5. `echo_cast`
6. `otherworld_lens`
7. `mana_transcription`

### 3.10 Interpretação operacional do roster
- **83 é a contagem de registry ids de spell no source pin exato da versão auditada**, não estimativa de marketing.
- Os quatro additions destacados na release `0.9.7.1` — `combustion_jet`, `blood_brand`, `shiden` e `catch_flame` — já aparecem no roster acima.
- Os grupos misturam combate, defesa, mobilidade, armazenamento, summon, gathering, sensing e crafting; portanto perk/provider deve classificar pelo spell real, não por uma categoria genérica “Apprentice”.
- Mana, cooldown, cast time, spell school/power e resolução de cast permanecem no pipeline Iron's.
- Spells automatizados ou executados por dispositivos não recebem autoria de jogador por presunção; causalidade precisa ser explícita.

## 4. Equipamentos de casting
O addon adiciona itens em que **conjuração faz parte do próprio moveset/uso**:
- **Spellcaster Guns**;
- **Swingcast Staves**;
- **Offhand Spell Amplifiers**;
- **Smashcast Scepter**;
- **Focus Staffbow**;
- **Multipurpose Staffrifle**;
- **Reflectcast Shield**;
- **Mana Force Blade**;
- outros equipamentos de spell-triggering.

Há também spellbooks e curios como **Ender Grimoire**, **Archivist's Grimoire** e **Spell-stained Runic Tablet**, além de acessórios de mana/defesa e utilidade.

## 5. Armaduras e identidade de build
A documentação descreve mage armor sets incluindo **glass-cannon robes** e gear de caster sem escola fixa. O design oferece mais spell power em troca real de defesa em alguns conjuntos, funcionando como escolha de build em vez de upgrade linear obrigatório.

Isso deve ser preservado em futuras perks: não neutralizar o tradeoff adicionando defesa gratuita só porque a armadura é “mágica”.

## 6. Blocos utilitários e automação
### Spellcaster Workbench
Integra fabricação/configuração do conteúdo próprio de spellcasting.

### Atelier Station
Bloco de suporte do addon usado em fluxos próprios de equipamento/conteúdo.

### Spell Dispenser
É a superfície de automação mais importante para o pack:
- lança **scroll spells suportados**;
- é acionável por **redstone**;
- usa **mana potions como combustível**;
- possui suporte opcional a **Create contraptions**.

Isso significa que casting automatizado existe de verdade, mas **não deve ser interpretado como autoria automática do jogador** em Mastery/XP. Redstone, máquina ou contraption só podem conceder progressão se houver contrato de ownership causal explícito.

## 7. Datapack customization
O mod expõe personalização por datapacks, incluindo perfis/comportamentos relacionados a spells, deny lists, dados semelhantes a receitas e outras definições de gameplay.

O próprio autor alerta que **formatos de datapack podem mudar entre versões** e que tooltips/JEI não necessariamente exibem todas as opções. Portanto qualquer customização local precisa ser versionada contra `0.9.7.1`, não copiada de wiki de outra build.

## 8. Dependências e compatibilidade de versão
A auditoria anterior da linha 0.9.7 registrou como requisitos da build 1.21.1:
- **Iron's Spells 'n Spellbooks 3.16.3+ e abaixo de 4.0**;
- **Create 6.0.10+** para as integrações/superfícies exigidas nessa linha;
- **NeoForge 21.1.228+**.

O pack usa NeoForge 21.1.248 e contém Iron's/Create compatíveis no snapshot atual. Ainda assim, qualquer update maior desses providers exige revalidação, porque o addon está em beta.

## 9. Boundary com Iron's Spells
**Iron's Spells continua authority de:**
- mana;
- spell schools;
- cast pipeline;
- cooldown/cast time;
- spell power/resistance;
- spellbook/scroll semantics base.

**Apprentice's Codex fornece:** conteúdo e adapters próprios construídos sobre essas superfícies.

Não criar segunda mana, segundo cooldown ou damage event paralelo para “integrar” seus spells ao RPG Skill Tree.

## 10. Boundary com Create
O Spell Dispenser pode funcionar em contraptions quando a integração opcional é suportada. Isso não converte todo o addon em sistema Create nem prova que qualquer bloco/spell é sublevel-safe. Testar especificamente automação móvel e ownership de casts.

## 11. Riscos conhecidos e validação
1. **Beta:** mudanças de comportamento e datapack schema ainda são plausíveis.
2. **Arcane in a Jar:** a linha 0.9.7 alterou seu comportamento; mundos atualizados devem seguir a orientação específica do release para blocos já existentes.
3. **Spell Dispenser:** risco de double-processing de mana/cast/progressão se outro integration hook observar o mesmo evento.
4. **Create contraptions:** validar casting, coordenadas, chunk transitions e persistência em estruturas móveis.
5. **Spells de gathering/crafting:** não conceder outputs extras em uma segunda camada; o spell real deve ser o executor canônico.
6. **Personal storage/pet storage:** verificar persistência e ownership antes de expor a sistemas externos.
7. **Structure/treasure sensing:** consultas read-only não devem gerar loot, carregar chunks ou conceder Mastery por spam.

## 12. Sobreposição e decisão
Apprentice's Codex não é duplicata de **Spell Codex**: este addon adiciona spells/gear/utility blocks; Spell Codex organiza/progride coleção/uso de spells por outro eixo. Também não é nova spell school.

**Decisão operacional: manter.** Ele amplia significativamente a variedade do Iron's sem criar um ecossistema mágico paralelo e possui integração concreta com automação Create.

## 13. Matriz mínima de teste
- boot client + dedicated server;
- casts de cada família funcional em amostra representativa;
- consumo de mana/cooldown exatamente uma vez;
- spellcasting weapons e offhand amplifiers;
- spellbooks/Curios e persistência;
- glass-cannon armor e tradeoff defensivo;
- Spell Dispenser com redstone + mana potion;
- Spell Dispenser em Create contraption;
- datapack reload/deny lists na versão 0.9.7.1;
- Arcane in a Jar após upgrade de mundo;
- assegurar ausência de progressão duplicada em casts automatizados.

## 14. Fontes
- [CurseForge — Apprentice's Codex](https://www.curseforge.com/minecraft/mc-mods/apprentices-codex)
- [Wiki/source oficial](https://github.com/hexqua/apprentice_codex/wiki)
- Modlist física e guia consolidado de Magia do projeto.
