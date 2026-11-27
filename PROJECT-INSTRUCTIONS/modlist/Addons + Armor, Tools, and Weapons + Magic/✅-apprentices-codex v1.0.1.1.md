# Apprentice's Codex — 1.0.1.1

## Propriedades vigentes — 27/11/2026

- **Mod:** Apprentice's Codex
- **Arquivo JAR instalado (declarado):** `apprentice_codex-1.0.1.1-mc1.21.1.jar`
- **Versão instalada declarada:** `1.0.1.1` — Release, Minecraft 1.21.1 / NeoForge.
- **Ordem física histórica:** #038 (preservada).
- **Categoria CurseForge / pasta:** Addons + Armor, Tools, and Weapons + Magic.
- **Função:** Addon de Iron's Spells com equipamento mágico, ferramentas de casting, integrações e progressão.
- **Atualização/Status:** `0.9.7.1` → `1.0.1.1`.
- **Estado no pack:** instalação declarada pelo usuário, sem leitura física nova.
- **Estado da pesquisa:** publicações identificadas e deltas disponíveis auditados/reutilizados; limites de evidência explicitados, sem certificação de runtime.
- **Dependências / compatibilidade / riscos:** Iron's Spells 3.16.4 no índice #342, Create, Patchouli e integrações opcionais conforme histórico; mínimos novos não confirmados. Não presumir Magitech/Spectrum presentes; itens condicionais não devem ser apresentados como garantidos no pack. Testar inventário, summons, progressão, scrolls, mana e compat de Accessories; sem QA.
- **Fonte:** [publicação oficial CurseForge](https://www.curseforge.com/minecraft/mc-mods/apprentices-codex/files/9773393).
- **Procedência:** declaração expressa do usuário e distribuição oficial; não inferir hashes nem metadata do JAR novo.

## Auditoria incremental e reconciliação

0.9.8.1 (27/09, Beta): grandes mudanças em armas, slots/scrolls, enchantments, cooldown, economia de cartuchos, Spell Wheel e integrações; histórico discrimina valores e correções. 1.0.0 (10/10, file 9115440): manual Apprentice's Memo via Patchouli, migração de descrições JEI, mudanças em Silent Assassin, Commence Fire, Fly Swatter, Quick Arms e Heavenly Fist. 1.0.1 (01/11, file 9391553): Mana Force Release Device, Gauntlets of Psychic-drive, módulos condicionais, Shock, integração Jewelry, Spectator e política de suporte de Accessories Compatibility Layer. 1.0.1.1 (23/11, file 9773393): dois equipamentos condicionais a Magitech (Magi-Engineer's Casting Staff-Device e Leg Exoskeleton, este permite caminhar no ar); suporte condicional a enchantments no Enchanter de Spectrum; rendering de partículas luminosas; elimina processamento melee desnecessário de certos summons, corrige atualização de inventário ao clicar Storage Stabilizer/Spellcaster's Accessory Case e clipping da skin em armaduras. Fix Better Combat explicitamente 1.20.1, excluído do alvo. Rótulo +mc1.21.1 versus campo File name -mc1.21.1; metadata não relida. Catálogo confirma as quatro publicações.

## Histórico documental preservado

> O conteúdo abaixo corresponde aos snapshots e auditorias anteriores. Menções a versão física, atual, candidata, não instalada, hashes e dependências são históricas; as propriedades vigentes prevalecem.

> **Autoridade física atual — 22/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#38**: `apprentice_codex-0.9.7.1+mc1.21.1.jar`, mod id `apprenticecodex`, runtime `0.9.7.1`, SHA-1 `b514315add32b93b0049c8673075627d7ef812e0`.

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist(1).txt` de 22/09/2026 — autoridade física atual
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Apprentice's Codex
- **Arquivo JAR:** apprentice_codex-0.9.7.1+mc1.21.1.jar
- **Versão 1.21.1:** 0.9.7.1
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Magia, RPG, Automação
- **Função:** Addon side-grade de Iron's Spells 'n Spellbooks. Na versão instalada 0.9.7.1, o SpellRegistry do source pin exato 305ea6a registra 83 spells distribuídos entre Blood, Ender, Evocation, Fire, Holy, Ice, Lightning, Nature e Eldritch, além de grande catálogo de armas/staves/shields/Curios/armaduras, blocos de crafting e Spell Dispenser com automação/redstone/Create. Não cria nova escola nem mana/progressão obrigatória paralela.
- **Dependências:** Iron's Spells 'n Spellbooks 3.16.3, Create 6.0.10 e NeoForge 21.1.248 satisfazem os gates documentados da linha 0.9.7.x. O addon permanece Beta; updates maiores exigem revalidação.
- **Sobreposição:** Não é o mesmo papel do Spell Codex. Apprentice's Codex adiciona conteúdo jogável/automação sobre Iron's; Spell Codex atua na organização/progressão da coleção. Iron's conserva authority do cast/mana/schools.
- **Compatibilidade/Riscos:** Addon beta e datapack formats podem mudar entre versões. Spell Dispenser usa redstone + mana potions e possui suporte opcional a Create contraptions: impedir double-processing de cast/mana/XP/Mastery e não presumir autoria do jogador em automação. Linha 0.9.7 alterou Arcane in a Jar; validar blocos existentes após upgrade.
- **Observações:** mod id: apprenticecodex; runtime 0.9.7.1, beta. O upstream textual pode resumir o addon como \~60 spells; a ficha chegou a registrar 85 por erro editorial, mas o source pin exato da própria versão 0.9.7.1 (305ea6aef7bb9642a9d1fc2b9042f3dfb2ce5b2e) registra 83 spells. Para catalogação técnica prevalece o registry desse pin.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge/wiki/source oficiais Apprentice's Codex 0.9.7.1 + source pin já auditado `305ea6aef7bb9642a9d1fc2b9042f3dfb2ce5b2e`. Reconciliação final: JAR/runtime permanecem exatamente `apprentice_codex-0.9.7.1+mc1.21.1.jar` / `0.9.7.1`; sem divergência física.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/apprentices-codex
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #38: `apprentice_codex-0.9.7.1+mc1.21.1.jar` / `0.9.7.1` conferidos contra a modlist atual; source pin e dossiê operacional preservados.
- **Histórico da decisão:** Manter. Em 07/09/2026 a ficha foi refeita e elevada ao padrão Alex's Mobs. A reconciliação final com o source pin exato 0.9.7.1 substituiu tanto o resumo upstream de 'cerca de 60 spells' quanto a contagem editorial intermediária de 85 pelo inventário técnico correto do SpellRegistry: 83 registry ids, preservando Iron's como authority de mana/schools/cast/cooldown.
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

## Atualização upstream e gate de manutenção — 11/10/2026

**Estado upstream:** a última build exibida para NeoForge 1.21.1 continua **`0.9.7.1` Beta** (30/08/2026), idêntica ao JAR no snapshot. **Decisão:** manter enquanto não houver release mais nova compatível e aprovada. **Gate:** Iron's Spells 3.16.3, Create 6.0.10, Spell Dispenser em automação/redstone e contraptions, single-source casting/mana/progressão, datapack schema, compatibilidade de Arcane in a Jar e restart de mundo; especialmente relevante se o pack migrar versão dos provedores. **Fonte:** https://www.curseforge.com/minecraft/mc-mods/apprentices-codex/files/all?version=1.21.1

> **Autoridade e limites:** revisão documental baseada no último inventário físico histórico, sem novo dump de JARs/SHA e sem testes in-game nesta execução. Não alterar versão instalada, filename ou modlist sem evidência física atual.


## Auditoria incremental — versões NeoForge 1.21.1 após 0.9.7.1 até 1.0.1 (17/11/2026)

**Autoridade física preservada:** modlist instalada #038, JAR top-level \`apprentice_codex-0.9.7.1+mc1.21.1.jar\`, mod id \`apprenticecodex\`, runtime **0.9.7.1**, SHA-1 histórico \`b514315add32b93b0049c8673075627d7ef812e0\`. A contagem de **83 spells** e os IDs descritos acima derivam do source *da versão instalada* (pin \`305ea6a\`), **não** constituem contagem certificada da linha 1.0.1. Não reescrever este roster como se as versões novas estivessem instaladas.

**Identidade:** [Apprentice's Codex (CurseForge 1450885)](https://www.curseforge.com/minecraft/mc-mods/apprentices-codex), autor Aquid / source hexqua; categorias oficiais **Addons + Armor, Tools, and Weapons + Magic**, já equivalentes à pasta atual. O [catálogo de arquivos NeoForge 1.21.1](https://www.curseforge.com/minecraft/mc-mods/apprentices-codex/files/all?page=1&pageSize=20&showAlphaFiles=hide&sortBy=dateCreated&sortOrder=desc) foi percorrido entre o baseline 0.9.7.1 e o último JAR listado. Cadeia pós-baseline de artefatos **NeoForge 1.21.1**: **0.9.8.1 (27/09) → 1.0.0 (10/10) → 1.0.1 (01/11)**. A 0.9.8.2 disponível no catálogo é **Forge 1.20.1**, não uma etapa NeoForge 1.21.1.

### 0.9.8.1 — Beta, 27/09/2026

[Nota da release oficial, file ID 8990953](https://www.curseforge.com/minecraft/mc-mods/apprentices-codex/files/8990953). **Mudança de grande porte, exige backup do mundo** segundo o autor, devido a reestruturações internas de itens.

- **Oito spells novos:** Thermal Slice, Lightning Arrow, Bloody Arrow, Sacred Arrow, Lunar Aim, Echo Arrow, Lock-on Ray e Quick Blink. Não atribuir sua presença ao roster físico 0.9.7.1.
- **Equipamentos e itens novos:** Spell-Reaper's Scythe, Full-auto Rapidcast Spellrifle, Charm of Monarch Bond, Emblem of Undying, Mana Maneuver Gear, Quickcast Scroll Cartridge, Mantle of Shooting Star e um Transducer oculto condicionado à presença de Malum.
- **Balanceamento de spells:** Thermal Process distingue dano não bloqueado por Fire Resistance de debuff bloqueável; Combustion Jet também passa a causar dano mesmo diante dessa resistência e incendeia antes do dano. Harvest Moon com Craftsman's Delight pode tosquiar ovelhas.
- **Transcendence reformulado:** nível máximo 1 e bônus fixo +1; muda o conjunto de itens elegíveis, retira o efeito de itens antigos agora inelegíveis e elimina exclusão mútua com Surge/Attunement.
- **Elemental Bow reformulado:** exige scroll, passa a ter 3 adjustment slots e 1 scroll slot; overheat compartilhado entre escolas por padrão, com recuperação do comportamento antigo via Recovery Rune. Remove bônus de níveis via enchantments exceto Transcendence e altera compatibilidade de datapacks.
- **Multi-purpose Staffrifle reformulado:** separa a função rapid-fire no novo item; passa a usar scroll e adjustment slots, cast imbuído, recoil, menor deslocamento durante ADS e limita spells com cast time.
- **Economia e cooldown:** retorno padrão de cartuchos 20%, ou 90% com Spellcaster's Ammo Pouch; cooldown de Spellguns específicos passa a somar cast time; Twin Bladed Channeling Staff altera Channeling/Riptide e cooldown.
- **Spell wheel e scrolls:** itens multi-scroll passam a usar armazenamento e prioridade próprios; fontes distintas do mesmo spell podem aparecer separadamente no wheel, ainda compartilhando cooldown. Scrollcaster Gauntlet passa a usar spell selecionado no wheel por padrão; configuração no Workbench recupera o comportamento anterior.
- **Compat/integrações e QA:** novas receitas exibidas em JEI para Arcanum In a Jar; Storage Stabilizer abre Ender Chest ao clicar no inventário mediante gasto de mana; alterados drops de acessórios (não mais em Ominous Vaults), acesso a Silver Ring, interações Malum/Protection Spell Supporter e controles de movimento. Corrigidos crash com terceira mão, dano letal que escapava de proteção Malum Soul Ward, Echo Cast, Mirage Avoidance, Plunder apenas 1.21.1 e workaround de pesca para Malum **1.8.2** especificamente; este último coincide com a versão histórica do pack.

**Impacto documental: ALTO** — conteúdo jogável, balanceamento, migração de itens/scrolls, economia de munição, integração JEI/Malum e persistência.

### 1.0.0 — Release, 10/10/2026

[Nota específica do arquivo NeoForge 1.21.1, ID 9115440](https://www.curseforge.com/minecraft/mc-mods/apprentices-codex/files/9115440).

- Introduz **Apprentice's Memo**, manual in-game que usa **Patchouli** (opcional, mas fortemente recomendado); o snapshot físico da modlist registra Patchouli #438. A maior parte das antigas descrições de itens foi removida do JEI por essa mudança.
- **Silent Assassin:** dano passa a ser fixo, sem escalar com spell power; mudam multiplicador de headshot, bônus por vida quase cheia e é removido o bônus de stealth.
- **Commence Fire:** multiplicador de headshot e número de recasts tornam-se fixos; corrige orientação eventual da arma.
- **Fly Swatter:** lock-on por recasts, trajetória de projéteis, explosão, dano e limite de alvos retrabalhados.
- **Quick Arms:** intervalo mínimo 0,25 s entre tiros, balas atravessam i-frames de dano, recasts dependem só do spell level.
- **Heavenly Fist:** coleta materiais de ametista sem exigir Craftsman's Delight; o addon segue exigido para efeitos de enchantment.
- Corrige dano anormalmente baixo contra Ender Dragon, otimiza mana de Echo Cast do Full-auto Rapidcast Spellrifle, corrige partículas e efeitos de movimentação de Craftsman's Delight. Os dois fixes de Mixin identificados como **exclusivos de 1.20.1** não são atribuídos a NeoForge 1.21.1.

**Impacto documental: ALTO** — documentação migrada ao manual, alterações de fórmula/cadência de combate e dependência opcional de Patchouli.

### 1.0.1 — Release, 01/11/2026

[Nota específica do arquivo NeoForge 1.21.1, ID 9391553](https://www.curseforge.com/minecraft/mc-mods/apprentices-codex/files/9391553).

- Adiciona **Mana Force Release Device** (série de armas acessíveis na progressão de ferro), **Gauntlets of Psychic-drive** e, **somente com addons correspondentes**, Ring-Bearing Ring (L_Ender's Cataclysm), Commence Magi Module, Spellbound Arms Module e Mana Stabilizer Module (Iron's Arms 'n Artifice). A existência dessas integrações não comprova que seus requisitos opcionais estejam instalados.
- **Magi-Agent's Hood** passa a afetar também o consumo de munição dos guns de Iron's Arms 'n Artifice quando esse mod está presente.
- Melhora a detecção de acertos de **Shock** e seus efeitos visuais; em Minecraft 1.21.1, **Magic Bullet Rune** integra Iron's Gems 'n Jewelry.
- Corrige ataques que atingiam jogadores no modo Spectator e erros de verificação de spells imbuídos de outros mods.
- **Accessories Compatibility Layer:** o autor declara *unsupported* problemas futuros quando este mod estiver instalado, emite aviso no chat ao login (1.21.1) e remove a restrição de versão do workaround. É **gate de compatibilidade**, não evidência de incompatibilidade reproduzida no pack.
- **Divergência de rótulo do artefato:** o catálogo exibe \`apprentice_codex-1.0.1+mc1.21.1.jar\`, enquanto o campo “File name” na página do ID 9391553 informa \`apprentice_codex-1.0.1-mc1.21.1.jar\`. Preservar essa diferença documental; não presumir o nome físico do candidato sem baixar/verificar o binário.

**Impacto documental: ALTO** — novos itens/progressão e integrações condicionais, segurança de alvo Spectator e política de suporte.

### Gates de promoção física — não executados

**Nenhum JAR foi substituído, nem testes de jogo/migração foram realizados.** Antes de qualquer upgrade, conferir manifest e hashes dos JARs candidatos, requisitos de NeoForge/Iron's Spells/Create/Patchouli/Malum, existência real dos consumidores opcionais, permissões do servidor e compatibilidade com accessories. Fazer backup e restaurar *em cópia* mundo, inventories, item NBT, enchantments antigos, scrolls, Spell Wheel, Elemental Bow/Staffrifle, Spell Dispenser em contraptions, Spellcaster Workbench, datapacks/recipes, efeitos Malum, mana/XP/cooldown de cast automatizado e progressão. Validar client + dedicated server e multiplayer em condições reais.

**Resultado documental:** todas as **três publicações NeoForge 1.21.1 posteriores à versão instalada 0.9.7.1** identificadas no catálogo oficial foram auditadas por seus changelogs específicos, até **1.0.1**. O dossier mantém **\`✅-apprentices-codex v1.0.1.1.md\`**, sua pasta original e versão física; promoção do runtime 1.0.1 permanece **pendente de testes e de reconciliação do binário**.
