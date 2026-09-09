# BetterNether: New Dawn — 21.0.26

> ✅ Versão física confirmada: `BetterNether-21.0.26.jar`, mod id `betternether`, runtime `21.0.26`, NeoForge 1.21.1. A build pertence ao stack New Dawn e usa BCLib, WorldWeaver e WunderLib. A decisão histórica **MANTER** permanece preservada.

## 1. Papel e autoridade
BetterNether: New Dawn é uma expansão ampla do **Nether**. É authority de seus próprios biomas, estruturas, flora, mobs, materiais, equipamentos, brewing e worldgen. BCLib/WorldWeaver/WunderLib fornecem infraestrutura; não são substitutos do conteúdo BetterNether.

Outros mods de Nether podem coexistir, mas placement, loot e registries não devem ser duplicados por compat externa.

## 2. Stack New Dawn físico
- BetterNether 21.0.26.
- BCLib 21.0.26.
- WorldWeaver 21.0.25.
- WunderLib 21.0.10.

O projeto New Dawn documenta esses três componentes como dependências da linha NeoForge atual.

## 3. Gloomwood — subsistema atual principal
A release 21.0.26 expande fortemente o Gloomwood:
- natural Gloomwood groves;
- sparse grove edges;
- solitary Gloomwood trees;
- rare Bleached Gloomwood trees com foliage invertida e Ancient Debris sob as raízes;
- Lumabus Vines esparsas;
- Molten Dark Gloomwood roots em parte das árvores;
- Gloomwood Sapling compatível com Cincinnasite Pots.

Isso deve ser tratado como biome/worldgen provider-native, não como feature genérica a ser reaplicada por KubeJS.

## 4. Gloomsculk
A build confirma conteúdo Gloomsculk com geodes/crystals e variante rara de textura de geode. Flaming Ruby equipment pode ser reparado com Gloomsculk Crystals/Geode Crystals na linha atual.

A aparência visual de geodes não deve ser usada como authority de material; recipes/tags devem consultar IDs/tags reais.

## 5. Mega Lava Lakes
A 21.0.26 adiciona **massive multi-chunk lava lakes**, localizáveis pela estrutura `betternether:mega_lava_lake`. Por atravessarem múltiplos chunks, são ponto crítico de parallel generation, chunk borders e estrutura persistente.

Não tentar regenerar a estrutura por evento de chunk load.

## 6. Árvores gigantes
A release inclui:
- growth 2×2 de giant Willow Tree;
- growth opcional 2×2 de giant Anchor Tree, controlado por gamerule `betternether:grow_large_anchor_trees`.

Growth deve respeitar server state/gamerule, espaço físico, saplings e lifecycle de bloco. Cliente não pode decidir se o crescimento é válido.

## 7. Famílias de madeira e blocos
A linha atual adiciona Chiseled Bookshelves para cada BetterNether wood set e mantém famílias próprias de madeira/material. Gloomwood doors usam fittings de iron/Cincinnasite na build atual.

Alterações de recipes por modpack devem usar datapack/KubeJS sem duplicar recipe IDs.

## 8. Mobs e spawn surfaces
A release 21.0.26 atualiza spawn egg artwork para entidades confirmadas do ecossistema:
- Naga;
- Jungle Skeleton;
- Flying Pig;
- Hydrogen Jellyfish;
- Skull.

Isso confirma que essas entidades fazem parte da superfície atual do mod. Spawn natural, weights, biome eligibility, AI e loot continuam authority do BetterNether/datapack; artwork do spawn egg é client-side.

## 9. Equipamentos e combate
Mudanças atuais confirmadas:
- Fire Ruby retaliation passa a ocorrer em metade dos hits elegíveis, em vez de um quarto;
- Flaming Ruby equipment recebe nova repair material compatibility;
- Obsidian Breaker minera netherrack, Nether ores, glass e glass panes mais rápido.

Integrações RPG/Epic Fight não devem reaplicar retaliation ou mining bonus fora dos hooks do item/provider.

## 10. Brewing, consumíveis e utilidades
A linha atual registra correções/comportamentos como:
- Hook Mushroom converte apenas Awkward Potions em Healing Potions;
- reagentes BetterNether funcionam em brewing stands;
- Medicine retorna exatamente um bowl após consumo;
- versões recentes da linha New Dawn incluem Nether Brewing Stand com regras próprias.

Brewing/consumo são server-authoritative; receita/return container deve ocorrer uma única vez.

## 11. Config e gamerules
Superfícies confirmadas na linha New Dawn incluem configs para desligar biomas/estruturas e gamerules específicos, incluindo geração de Blue Obsidian em ruined portals e growth de Anchor Trees gigantes.

Config de worldgen afeta principalmente novos chunks. Não assumir que desativar uma feature remove estruturas já geradas.

## 12. Parallel worldgen e thread safety
A 21.0.26 melhora explicitamente thread safety de **cities e destruction structures** durante worldgen paralelo. Isso é um risco técnico real:
- não usar caches globais mutáveis externos sobre essas estruturas;
- não disparar geração adicional em callbacks concorrentes sem synchronization/provider contract;
- testar geração paralela em dedicated server.

## 13. Client/server
- biome/structure placement, mobs, loot, growth, brewing e equipment effects: servidor/common;
- models, particles, spawn-egg artwork e render: cliente;
- fog/render de dimensão não deve ser authority de biome;
- dedicated server precisa carregar sem classes exclusivamente visuais.

## 14. Integrações no pack
### BCLib / WorldWeaver / WunderLib
Dependências estruturais do ecossistema New Dawn.

### TerraBlender e Lithostitched
O projeto declara compatibilidade em sua linha moderna. Isso não torna APIs intercambiáveis; cada consumer continua usando seu framework.

### Outros mods de Nether
Estruturas, biomas e mobs adicionais devem ser auditados por placement concreto. Sobreposição temática não equivale a incompatibilidade.

## 15. Lifecycle
Validar:
1. criação de mundo novo;
2. primeira entrada no Nether;
3. chunks novos após restart/config change;
4. mega lava lake atravessando chunks;
5. parallel worldgen;
6. datapack reload onde suportado;
7. dimension travel;
8. mob spawn/despawn;
9. giant tree growth;
10. resource reload client-side.

## 16. Riscos
1. Conflito de biome/structure placement.
2. Corrida em worldgen paralelo.
3. Registry/holder stale após reload.
4. Feature duplicada por compat/KubeJS.
5. Retaliation de Fire Ruby disparando duas vezes via combat bridge.
6. Medicine/container return duplicado.
7. Mega structures inconsistentes em chunk borders.
8. Atualização isolada de um componente New Dawn quebrar API compatibility.

## 17. Matriz de testes
1. Dedicated server boot com stack New Dawn físico.
2. Gloomwood biome/groves/solitary/bleached variants em chunks novos.
3. Mega Lava Lake via geração e `/locate structure betternether:mega_lava_lake`.
4. Giant Willow/Anchor growth com gamerule on/off.
5. Cinco famílias de mobs citadas acima: spawn egg, spawn/AI/loot básico.
6. Fire Ruby retaliation exactly-once.
7. Obsidian Breaker mining behavior e durability.
8. Hook Mushroom/brewing/Medicine sem duplication.
9. Parallel generation de city/destruction structures.
10. Config disable biomes/structures afetando apenas geração futura conforme esperado.

## 18. Evidência
- modlist física atual: BetterNether 21.0.26 + BCLib/WorldWeaver/WunderLib presentes;
- Modrinth oficial da release 21.0.26;
- GitHub/projeto oficial BetterNether: New Dawn para dependências;
- changelogs New Dawn sobre Gloomwood, Gloomsculk, mega lava lakes, trees, equipment, brewing, gamerules e thread safety;
- histórico curatorial no Notion: desativação de 22/08 foi diagnóstica; decisão final MANTER.

> 🔥 Authority canônica: BetterNether = conteúdo e geração do Nether; New Dawn libraries = infraestrutura. Mudanças de config/worldgen devem ser avaliadas em chunks novos, não inferidas sobre mundo já materializado.
