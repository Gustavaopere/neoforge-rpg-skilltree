# Ars Nouveau's Flavors & Delight

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db818c82b7d1318bf03fb8
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Ars Nouveau's Flavors & Delight
- **Arquivo JAR:** `arsdelight-2.2.2.jar`
- **Versão 1.21.1:** 2.2.2
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia, Comida, Compat
- **Função:** Crossover Ars Nouveau + Farmer's Delight com alimentos/bebidas, feasts, jellies, pies, efeitos mágicos culinários e materiais Wilden/Chimera/Archwood.
- **Dependências:** Ars Nouveau 5.13.1 + Farmer's Delight 1.3.4. Ars Elemental 0.7.10.1 está presente para integrações condicionais; Archwood Good e Diet permanecem ausentes do snapshot top-level atual.
- **Sobreposição:** Adiciona alimentos próprios e integração Ars↔Farmer's Delight; não é apenas uma bridge técnica vazia.
- **Compatibilidade/Riscos:** Heal/spell-damage food effects podem double-dip com outros hooks; feasts/pies/jellies exigem drop/serving idempotente; optional compat deve ser classloading-safe. Mana/Source permanecem authorities do Ars.
- **Observações:** mod id `arsdelight`, runtime 2.2.2. Conteúdo e optional-provider gates permanecem documentados sem promover providers ausentes.
- **Procedência:** modlist.txt física atual de 11/09/2026 + source oficial Ars Nouveau's Flavors & Delight 2.2.2 + Farmer's Delight 1.3.4 físico e fontes já auditadas no dossiê. Reconciliação final: JAR/runtime permanecem exatamente `arsdelight-2.2.2.jar` / `2.2.2`; sem divergência física.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ars-nouveaus-flavors-delight
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #53: `arsdelight-2.2.2.jar` / `2.2.2` conferidos contra a modlist atual; corpo técnico, decisão e optional-provider gates preservados.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, a auditoria confirmou o runtime 2.2.2, o inventário culinário/efeitos e as integrações com Ars Nouveau/Farmer's Delight. A ficha técnica está concluída, mas nenhuma decisão de manter/remover foi inferida da instalação atual.
- **Data da última decisão:** não definida

# Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física e source coincidem: `arsdelight-2.2.2.jar`, mod id `arsdelight`, versão `2.2.2`, Minecraft 1.21.1. O source branch `1.21.1` foi usado para inventário concreto.

## 1. Papel e autoridade
Ars Nouveau's Flavors & Delight é uma integração Ars Nouveau ↔ Farmer's Delight que converte ingredientes, frutos, mobs e efeitos do Ars em alimentos, bebidas, feasts, jellies, pies e utilidades culinárias. **Ars Nouveau** continua autoridade de mana/spell power/efeitos Ars; **Farmer's Delight** continua autoridade dos contratos de food/feast/cabinet. O addon é autoridade apenas do conteúdo crossover que registra.

## 2. Alimentos registrados — 42 entradas confirmadas
### Wilden / Chimera
- `wilden_meat`
- `grilled_wilden_meat`
- `wilden_meat_slice`
- `grilled_wilden_meat_slice`
- `chimera_meat`
- `grilled_chimera_meat`
- `chimera_meat_slice`
- `grilled_chimera_meat_slice`
- `wilden_skewer`
- `grilled_wilden_skewer`
- `chimera_skewer`
- `grilled_chimera_skewer`
- `wilden_sauce`
- `wilden_stew`
- `bowl_of_wilden_salad`
- `horn_roll`
- `bowl_of_honey_glazed_chimera`

### Source Berry / Archwood
- `source_berry_cookie`
- `source_berry_pie_slice`
- `source_berry_cupcake`
- `arch_sauce`
- `arch_soup`

### Teas
- `mendosteen_tea`
- `bastion_tea`
- `bombegrante_tea`
- `frostaya_tea`
- `source_berry_tea`

### Cocktails / hornbeers
- `unstable_cocktail`
- `mendosteen_hornbeer`
- `bastion_hornbeer`
- `bombegrante_hornbeer`
- `frostaya_hornbeer`
- `source_berry_hornbeer`

### Jams
- `activated_mendosteen_jam`
- `activated_bastion_jam`
- `neutralized_bombegrante_jam`
- `neutralized_frostaya_jam`
- `source_berry_jam`

### Pratos de carne + frutos mágicos
- `mendosteen_chicken`
- `bastion_pork`
- `bombegrante_steak`
- `frostaya_mutton`

## 3. Efeitos alimentares e ownership
O source aplica combinações de efeitos Ars/Farmer's Delight e cinco efeitos próprios. Exemplos confirmados incluem Mana Regen, Recovery, Defense e Nourishment onde definidos. Wilden/Chimera e frutos mágicos possuem duração/amplificador/chance próprios por receita.

**Regra:** o consumo de um item deve produzir uma única aplicação dos efeitos codificados pelo alimento. Integrações de dieta/nutrição podem observar a refeição, mas não devem reaplicar mana/efeitos.

## 4. Efeitos próprios — 5
- `blast_resistance`: reduz dano de explosão recebido.
- `flourishing`: quando o jogador recebe cura, também recupera mana.
- `freezing_spell`: spell damage também aplica freezing.
- `synchronized_shield`: cura também gera absorption.
- `wilden`: aumenta max mana, mana restoration e spell damage.

Esses efeitos podem interceptar heal/spell damage. Em um pack com vários sistemas de buffs, evitar aplicar o mesmo heal/damage event duas vezes.

## 5. Itens não-alimentares — 8
- `flourishing_bark`
- `vexing_bark`
- `cascading_bark`
- `blazing_bark`
- `wilden_horn_powder`
- `wilden_spike_powder`
- `chimera_horn`
- `enchanters_knife`

Os barks possuem integração de fuel/compost no source; blazing bark usa valor de fuel superior aos demais. O Enchanter's Knife deve permanecer ferramenta do addon/Farmer's Delight, sem ser reinterpretado como caster genérico.

## 6. Blocos de armazenamento/feast — 8
### Crates — 5
- `mendosteen_crate`
- `bastion_crate`
- `bombegrante_crate`
- `frostaya_crate`
- `source_berry_crate`

### Cabinet — 1
- `archwood_cabinet`

### Feasts — 2
- `wilden_salad`
- `honey_glazed_chimera`

Os feasts usam o contrato de servings do Farmer's Delight e retornam bowls/portions conforme estado. Não tratar o block item e as servings como drops independentes simultâneos.

## 7. Jellies — 5 + 1 block entity type
- `mendosteen_jelly`
- `bastion_jelly`
- `bombegrante_jelly`
- `frostaya_jelly`
- `source_berry_jelly`

Todos usam o block entity type `jelly`. Os jellies são edible block-items e fazem parte das tags de magic food/fruit/sugar conforme source. O código registra extensões opcionais para Ars Elemental e Archwood Good somente quando esses providers estão carregados.

No pack atual, **Ars Elemental está presente**; Archwood Good não foi confirmado como top-level na checagem física desta etapa, portanto sua bridge não é tratada como ativa.

## 8. Pies — 4 blocos + 4 slices
- `mendosteen_pie` / `mendosteen_pie_slice` → Flourishing.
- `bastion_pie` / `bastion_pie_slice` → Synchronized Shield.
- `bombegrante_pie` / `bombegrante_pie_slice` → Blast Resistance.
- `frostaya_pie` / `frostaya_pie_slice` → Freezing Spell.

Pies usam `PieBlock` do Farmer's Delight e estado por bites. Loot precisa respeitar o estado do bloco para não duplicar slice + pie completo.

## 9. Integrações opcionais detectadas no código
- Ars Elemental: registro condicional em Jelly BE/compat; provider presente no pack.
- Archwood Good: compat condicional por `ModList`; provider não confirmado no top-level atual.
- Diet: tags de compat existem no source; provider não foi confirmado como top-level nesta checagem.

Fail-closed: classes opcionais só devem ser tocadas atrás do gate correspondente.

## 10. Client/server, lifecycle e multiplayer
- Consumo, efeitos, heal→mana, heal→absorption e spell-damage→freezing precisam ser server-authoritative.
- Modelos/render de Jelly/feast são client-side.
- Block entity de Jelly deve carregar/descarregar sem duplicar item/serving.
- Em multiplayer, efeito de um jogador não pode usar mana/estado de outro.
- Datapack/reload de receitas e tags deve recomputar compatibilidade sem manter cache de receita inválido.

## 11. Riscos no pack
1. **Heal hooks:** Flourishing e Synchronized Shield podem double-dip se outro addon reemite eventos de cura.
2. **Spell damage hooks:** Freezing Spell precisa preservar caster/causalidade.
3. **Mana unification:** Ars 'n' Spells pode alterar a pool de mana do jogador; este addon deve usar a API/provider vigente, não criar pool própria.
4. **Nutrition mods:** efeitos do alimento não devem ser reaplicados por bridges genéricas.
5. **Feasts/Pies:** garantir drop/servings idempotentes em break/explosion.
6. **Optional compat:** ausência de Archwood Good/Diet não pode causar classloading failure.

## 12. Evidência
- Modlist física: `arsdelight-2.2.2.jar`.
- Source `Minecraft-LightLand/Ars-Nouveau-Flavors-Delight`, branch `1.21.1`, `gradle.properties` 2.2.2.
- `ADFood`, `ADItems`, `ADBlocks`, `ADJellys`, `ADPie` e `ADEffects` auditados.

> 📦 Inventário concreto confirmado: 42 food entries, 8 itens utilitários, 8 blocos crate/cabinet/feast, 5 jellies + 1 BE type, 4 pies + 4 slices e 5 efeitos próprios. Entradas podem se sobrepor entre categorias de item/bloco por block item; as contagens são por registries/superfícies descritas, não uma soma artificial de “conteúdo único”.
