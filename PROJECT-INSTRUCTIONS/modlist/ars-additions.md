# Ars Additions

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8146aa41f177e808d415
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Ars Additions
- **Arquivo JAR:** `ars_additions-1.21.1-21.3.0.jar`
- **Versão 1.21.1:** 1.21.1-21.3.0
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Magia, QoL, Armazenamento, Exploração, Automação
- **Função:** Addon de Ars Nouveau com warp/Source/storage/automação e conteúdo próprio amplo. Registry auditado: 35 blocos, 61 item entries, 12 charms, 3 glyphs, 2 rituals, 1 perk, 1 mob effect, 5 recipe types, 11 data components, 4 attachments, 4 tag modifiers e 1 painting; inclui Warp Nexus/Index, Ender Source Jar, Source Spawner, Wixie Enchanting, Mark/Recall/Retaliate e estruturas.
- **Dependências:** Ars Nouveau 5.13.1 é o provider-base físico atual de Source, spell grammar, Storage Lectern, Enchanting Apparatus e warp contracts consumidos pelo addon.
- **Sobreposição:** Cruza objetivos com teleporte, armazenamento remoto, chunkloading e automação de outros mods, mas usa contratos nativos do ecossistema Ars. Não é nova escola nem sistema de Source alternativo.
- **Compatibilidade/Riscos:** Cross-dimensional storage/Source exigem teste contra reconnect/unload/concorrência; Ritual of Arcane Permanent é chunkloader configurável e desabilitado por padrão. Mark/Recall deve falhar com segurança para alvos inválidos. Há issues upstream 1.21.1 sobre Lootr/Explorer Warp Scroll e retenção em busca assíncrona de estruturas; tratar como riscos a validar, não bugs locais confirmados. Não presumir compatibilidade com Sable sublevels.
- **Observações:** mod id: `ars_additions`; runtime 1.21.1-21.3.0. Corpo da página contém inventário de registry ids/famílias e separa gameplay registries de persistência/codec internals. Ars Nouveau permanece authority de Source, spell grammar e infraestrutura-base.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge/GitHub oficiais Ars Additions 21.3.0 + Ars Nouveau 5.13.1 físico e fontes já auditadas no dossiê. Reconciliação final: JAR/runtime permanecem exatamente `ars_additions-1.21.1-21.3.0.jar` / `1.21.1-21.3.0`; sem divergência física.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ars-additions
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #40: `ars_additions-1.21.1-21.3.0.jar` / `1.21.1-21.3.0` conferidos contra a modlist atual; corpo técnico, decisão e estado preservados.
- **Histórico da decisão:** Manter. Em 07/09/2026 a ficha foi refeita do zero com base no README/source e release 21.3.0, documentando estruturas, glyphs, storage/Source remoto, automação, chunkloading, boundaries e riscos upstream.
- **Data da última decisão:** 2026-09-07

> 🌀 **PADRÃO ALEX'S MOBS — DOSSIÊ OPERACIONAL EXAUSTIVO.** Esta ficha documenta `ars_additions-1.21.1-21.3.0.jar`, mod id `ars_additions`, em NeoForge 1.21.1. A segunda passada foi reconstruída contra os registries/source da linha 1.21: **35 blocos, 5 block-entity types, 61 item entries, 12 charms, 3 glyphs, 2 rituals, 1 perk, 1 mob effect, 5 recipe types, 11 data components, 4 attachments, 4 tag modifiers, 1 condition codec, 1 loot function e 1 painting**, além das estruturas e sistemas de warp/Source/automação. Ars Nouveau continua authority de Source, spellcraft e infraestrutura-base.

## 1. Identidade e versão
- **Mod:** Ars Additions.
- **Runtime instalado:** `1.21.1-21.3.0`.
- **JAR físico:** `ars_additions-1.21.1-21.3.0.jar`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** cliente + servidor.
- **Canal:** Release.
- **Licença:** LGPLv3.
- **Papel:** extensão de qualidade de vida e utilidades para Ars Nouveau.
- **Decisão:** **Manter**.

## 1.1 Inventário técnico de registries — versão auditada
### Blocos registrados — 35
**Funcionais — 4**
1. `enchanting_wixie_cauldron` — Wixie Enchanting Apparatus.
2. `ender_source_jar` — storage/ligação remota de Source.
3. `source_spawner` — bloco funcional associado ao recipe/provider `source_spawner`.
4. `warp_nexus` — núcleo da infraestrutura de warp do addon.

**Cracked/Decorative Sourcestone — 6**
- `cracked_sourcestone`
- `cracked_polished_sourcestone`
- `cracked_sourcestone_large_bricks`
- `cracked_polished_sourcestone_large_bricks`
- `cracked_sourcestone_small_bricks`
- `cracked_polished_sourcestone_small_bricks`

**Chains — 4**
- `archwood_chain`
- `sourcestone_chain`
- `polished_sourcestone_chain`
- `golden_chain`

**Magelight Lanterns — 6**
- `magelight_lantern`
- `golden_magelight_lantern`
- `sourcestone_magelight_lantern`
- `polished_sourcestone_magelight_lantern`
- `archwood_magelight_lantern`
- `soul_magelight_lantern`

**Lanterns — 4**
- `golden_lantern`
- `sourcestone_lantern`
- `polished_sourcestone_lantern`
- `archwood_lantern`

**Walls — 4**
- `sourcestone_wall`
- `polished_sourcestone_wall`
- `cracked_sourcestone_wall`
- `cracked_polished_sourcestone_wall`

**Buttons — 2:** `sourcestone_button`, `polished_sourcestone_button`.
**Doors — 2:** `sourcestone_door`, `polished_sourcestone_door`.
**Trapdoors — 2:** `sourcestone_trapdoor`, `polished_sourcestone_trapdoor`.
**Carpet — 1:** `magebloom_carpet`.

### Block entities — 5
O registry cria block-entity types para:
- `enchanting_wixie_cauldron`;
- `ender_source_jar`;
- `source_spawner`;
- `warp_nexus`;
- família `magelight_lantern`.

Esses estados precisam ser considerados em save/load, chunk unload e movimentação por sistemas externos; não tratar os blocos como decoração simples.

### Item registry — 61 entries
Os 35 blocos acima registram seus respectivos **block-items**. Além deles, há **26 itens standalone**: 14 utilitários e 12 charms.

#### Utilitários standalone — 14
1. `warp_index`
2. `stabilized_warp_index`
3. `codex_entry`
4. `lost_codex_entry`
5. `ancient_codex_entry`
6. `unstable_reliquary`
7. `exploration_warp_scroll`
8. `nexus_warp_scroll`
9. `xp_jar`
10. `handy_haversack`
11. `advanced_dominion_wand`
12. `wayfinder`
13. `imbued_spell_parchment`
14. `memory_crystal`

#### Charms — 12
Os charms possuem **charges** e custo por ativação/intervalo; em creative o consumo é tratado de forma especial pelo provider.
1. `fire_resistance_charm` — **Emberward**: anula dano de fogo enquanto há cargas.
2. `undying_charm` — **Second Wind**: impede morte conforme a lógica/cargas do charm.
3. `dispel_protection_charm` — **Unyielding Magic**: protege contra dispel.
4. `fall_prevention_charm` — **Featherlight**: permite descida lenta.
5. `water_breathing_charm` — **Ocean's Breath**: respiração subaquática.
6. `ender_mask_charm` — **Ender Serenity**: mascara o portador da agressão de Endermen.
7. `void_protection_charm` — **Void's Salvation**: proteção/salvamento contra void.
8. `sonic_boom_protection_charm` — **Resonant Shield**: proteção contra Sonic Boom do Warden.
9. `wither_protection_charm` — **Decay's End**: impede Wither conforme o evento do provider.
10. `golden_charm` — **Gilded Friendship**: torna Piglins neutros ao portador.
11. `night_vision_charm` — **Darkvision**: visão em baixa luminosidade.
12. `powdered_snow_walk_charm` — **Snowstride**: permite caminhar em powdered snow.

### Ars Nouveau registries próprios
- **3 glyphs:** `Retaliate`, `Mark`, `Recall`.
- **2 rituals:** `RitualChunkLoading` e `RitualLocateStructure`.
- **1 perk:** `ReachPerk`.
- **1 mob effect:** `marked`.

O próprio addon registra comportamento de turret para `Recall`; portanto automação por turret é provider-native e não deve ser replicada por handler paralelo.

### Recipe types/serializers — 5
1. `source_spawner`
2. `locate_structure`
3. `charm_charging`
4. `bulk_scribing`
5. `imbue_scroll`

Os três últimos são adicionados ao `ImbuementRecipeRegistry` do Ars Nouveau. Datapacks/automação devem usar esses recipe types reais em vez de inferir crafting pelo output.

### Data components persistentes — 11
- `haversack_data`
- `charm_data`
- `advanced_dominion_data`
- `wayfinder_data`
- `mark_data`
- `warp_bind_data`
- `exploration_scroll_data`
- `structure_lookup_data`
- `override_perks`
- `xp_jar_remainder`
- `memory_crystal_data`

A maioria é explicitamente persistente e vários são network-synchronized. Copiar item/NBT sem respeitar esses components pode perder vínculo, cargas ou estado.

### Attachment types — 4
- `single_item_handler` — inventory serializável de 1 slot;
- `warp_nexus_inventory` — inventory serializável de 9 slots, `copyOnDeath`;
- `particle_color` — cor persistente via codec;
- `local_weather_status` — estado de clima local.

### Tag modifier codecs — 4
Registry interno `tag_modifiers`:
- `remove_guaranteed_drops`
- `remove_tag`
- `set_tag`
- `append_tag`

São superfícies de transformação de NBT/tag usadas pelo addon. Não duplicar mutações de entidade/item em mixins externos sem rastrear a mesma etapa.

### Outros registries auxiliares
- **Condition codec:** `config`.
- **Loot function:** `exploration_scroll`.
- **Painting:** `snoozebuncle`, variante 32×32.

### Leitura operacional do inventário
- Conteúdo decorativo e funcional compartilha o mesmo namespace, mas só os blocos/itens com estado real devem ser tratados como providers de gameplay.
- Persistência de Warp/Reliquary/Wayfinder/Charms está em data components/attachments reais; não reconstruir por lore ou tooltip.
- Os counts acima vêm do source da linha 1.21 auditada e substituem a ficha anterior, que descrevia apenas os recursos mais visíveis.

## 2. Estruturas de exploração
### Ruined Warp Portals
Variante temática de ruined portals ligada ao sistema de warp. Essas estruturas podem conter um **Explorer's Warp Scroll**, servindo como ponto de descoberta de novos destinos.

### Nexus Tower
Torre de mago voltada a facilitar teleporte/navegação pelo mundo. Ela participa da fantasia de rede de warp do addon sem substituir os contratos de teleporte/warp do Ars Nouveau.

## 3. Glyphs próprios
### Retaliate — Form
Permite lançar um spell tendo como alvo a entidade que atingiu o caster nos últimos **5 segundos**. É uma forma reativa de targeting e deve respeitar o contexto real do cast.

### Mark — Effect
Armazena em uma **Reliquary** uma referência ao bloco ou entidade atingida.

### Recall — Form
Usa uma referência previamente armazenada em Reliquary para lançar um spell sobre esse alvo.

Esses glyphs ampliam a gramática do Ars; mana, spell construction, casting e demais custos continuam no pipeline do Ars Nouveau.

## 4. Reliquary e referências remotas
A **Unstable Reliquary** é usada por Mark/Recall para guardar referências a blocos, entidades ou jogadores. Isso cria uma superfície de targeting remoto persistente.

Integrações externas precisam tratar referência e alvo como dados sensíveis a mudança de dimensão, unload, remoção de entidade e permissões/proteção. Uma referência existente não significa que o alvo permanece válido ou acessível.

## 5. Warp Index e armazenamento remoto
O **Warp Index** permite acesso remoto a **Storage Lecterns**. As versões estabilizadas/upgraded ampliam o alcance e podem permitir **acesso cross-dimensional**.

Boundary obrigatória:
- Storage Lectern/inventários continuam pertencendo ao ecossistema Ars/provider real;
- Warp Index fornece acesso remoto;
- não criar cópia paralela de inventário para “cachear” o conteúdo;
- consultas/GUI não devem duplicar itens ao reconectar ou trocar de dimensão.

## 6. Ender Source Jar
O **Ender Source Jar** funciona conceitualmente como um “Ender Chest para Source”: jars vinculados permitem que Source colocado em uma base seja acessado a partir de outra localização.

Isso não cria uma segunda moeda mágica. **Source continua sendo o recurso canônico do Ars Nouveau.** Qualquer perk/bridge deve consumir/consultar o mesmo Source e nunca gerar reserva paralela gratuita.

## 7. Codex Entries
O addon inclui **Codex Entry** em variantes Lost/Ancient que ensinam um glyph aleatório do tier correspondente. A documentação upstream informa que Tier 1 aparece em basic dungeon loot, enquanto tiers superiores são unobtainable por padrão.

Logo, datapacks/modpack progression podem mudar disponibilidade, mas não se deve presumir que todos os tiers existam naturalmente em loot sem configuração específica.

## 8. Ritual of Arcane Permanent
Ritual configurável capaz de **chunkload** a área ao redor. O recurso é **desabilitado por padrão** segundo o projeto.

Em um pack grande, esse detalhe é crítico:
- não habilitar automaticamente por perk;
- limitar raio/custo conforme config;
- evitar sobreposição com outros chunkloaders;
- monitorar quantidade de chunks forçados por jogador/base;
- dedicated server deve ser o cenário principal de performance.

## 9. Wixie Enchanting Apparatus
Usando Wixie Charm em um Enchanting Apparatus, o addon pode convertê-lo em **Wixie Enchanting Apparatus**, permitindo automatizar recipes do Apparatus, inclusive aplicação de enchantments.

A automação continua consumindo inputs/Source/recipes do ecossistema real. Não conceder outputs antecipadamente e não executar a mesma recipe por outro pipeline paralelo.

## 10. Decoração
O addon também fornece conteúdo visual Ars-themed:
- lanterns com casings temáticos;
- Magelight Lanterns;
- chains correspondentes;
- Sourcestone Walls;
- Cracked Sourcestone;
- pintura Snoozebuncle.

Esses itens enriquecem construção/ambientação, mas não devem ser promovidos a provider mecânico de perk.

## 11. Authority e boundaries
### Ars Nouveau permanece authority de
- Source;
- glyph/spell grammar base;
- mana/cast pipeline;
- Storage Lectern;
- Enchanting Apparatus;
- warp infrastructure-base e demais estados centrais do ecossistema.

### Ars Additions fornece
- glyphs/formas adicionais;
- referências Reliquary;
- acesso remoto/cross-dimensional ao storage;
- Source remoto via Ender Source Jar;
- estruturas de warp;
- chunkloading opcional;
- automação Wixie e conteúdo auxiliar.

Não transformar o addon em nova escola mágica ou novo sistema de mana.

## 12. Integrações e riscos do pack
1. **Lootr:** existe issue upstream 1.21.1 relatando comportamento potencialmente problemático do Explorer Warp Scroll em inventories controlados por Lootr; isso é risco upstream, não bug local confirmado. Testar se Lootr estiver presente/ativo.
2. **Locate/async tasks:** o tracker upstream contém issue de retenção de memória associada a busca assíncrona de estruturas em uptime longo. Tratar como risco de servidor a monitorar, não como falha confirmada no pack.
3. **Cross-dimensional storage:** testar reconnect, unload e concorrência para impedir dupe/perda.
4. **Chunkloading:** risco de performance e sobreposição com outros chunkloaders.
5. **Mark/Recall:** alvo removido, teleportado ou em dimensão indisponível deve falhar com segurança.
6. **Warp/sublevels:** Sable/Aeronautics mudam espaços/coordenadas; não presumir compatibilidade de warp/reference com sublevels sem teste específico.
7. **Wixie automation:** recipes não podem ser processadas duas vezes por bridges externas.

## 13. Sobreposição e decisão
Ars Additions toca teleporte, storage remoto, Source e automação — áreas em que o pack possui outros mods — mas a implementação é nativa do ecossistema Ars e não é equivalência simples com waystones, AE2, chunkloaders genéricos ou máquinas Create.

**Decisão operacional: manter.** O addon adiciona utilidades coerentes com Ars Nouveau e evita precisar reproduzir essas bridges manualmente.

## 14. Matriz mínima de teste
- spawn/loot de Ruined Warp Portal e Nexus Tower;
- Explorer's Warp Scroll e destinos válidos;
- Retaliate dentro/fora da janela de 5 s;
- Mark/Recall com bloco, entidade, player, unload e mudança de dimensão;
- Warp Index local e cross-dimensional;
- Ender Source Jar com consumo/produção simultâneos e restart;
- Codex Entry e tiers configurados;
- Ritual of Arcane Permanent default off, limites e chunk tickets;
- Wixie Enchanting Apparatus com recipes e custos reais;
- dedicated-server uptime/memória em uso de buscas/warp;
- teste com Sable/sublevels quando aplicável;
- reload de datapacks/config sem duplicação de storage/Source.

## 15. Fontes
- [CurseForge — Ars Additions](https://www.curseforge.com/minecraft/mc-mods/ars-additions)
- [Source oficial — Ars Additions](https://github.com/Jarva/Ars-Additions)
- Modlist física e guia consolidado de Magia do projeto.
