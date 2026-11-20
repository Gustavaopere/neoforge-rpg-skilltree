# Ice And Fire Community Edition

## Propriedades do registro

- **Mod:** Ice And Fire Community Edition
- **Arquivo JAR:** `iceandfire-2.1.2.jar`
- **Versão 1.21.1:** `2.1.2`
- **Categoria:** Mobs, Worldgen, Exploração, RPG
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://github.com/IAFEnvoy/IceAndFire-CE/tree/1.21.1
- **Função:** Provider principal Ice and Fire CE: dragões Fire/Ice/Lightning, criaturas míticas, tame/growth/combat, equipment, Dragon Forge, structures/worldgen, recipes/loot/trades, Bestiary e progressão.
- **Dependências:** NeoForge 21.1.248; source 2.1.2 declara GeckoLib, Curios e Integration 0.2. Jupiter 2.3.7 está presente como config system. Addons físicos: Dragon Care 1.3.1, Dread Land 0.1.2 e armor compat Epic Fight 1.0.0.
- **Compatibilidade/Riscos:** Riscos: save migration entre forks, registry/entity missing mappings, tame/owner/growth state, Dragon Forge dupe/partial settlement, worldgen collisions, Jupiter config drift, GeckoLib rendering, attachment state e addon ABI drift. Epic Fight não tem suporte nativo completo; há bridge de armadura separada.
- **Sobreposição:** Sobreposição de categoria com outros mob/worldgen mods não implica redundância. Dragões, tame/growth, Dragon Forge, Bestiary e estruturas IAF permanecem provider-specific. Epic Fight base não é full-compat sem bridges específicas.
- **Observações:** JAR físico contém `integration-neoforge-0.2.jar` como JarJar interno; source declara `integration_version=0.2`. Source registra entidades/dragon types/items/blocks/attachments/recipes/loot/trades/features/structures/processors/etc. CE não inclui a série Myrmex segundo documentação oficial.
- **Procedência:** modlist.txt física anexada e reconferida em 12/09/2026 + source oficial IAFEnvoy/IceAndFire-CE branch 1.21.1 exatamente em mod_version 2.1.2 / NeoForge 21.1.248 + documentação/release oficial 2.1.2 + revalidação de versões em 12/09/2026. A linha 2.2-alpha.3 observada é para Minecraft 26.1.2, não aplicável ao pack.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 12/09/2026 — Ice And Fire Community Edition 2.1.2/JAR físicos reconfirmados; 2.1.2 permanece a release aplicável mais recente para Minecraft 1.21.1. Release posterior 2.2-alpha.3 localizada é para Minecraft 26.1.2. Registrado também o fix 2.1.2 de `dread mob spawn`; addons, JarJar Integration 0.2 e boundaries preservados.
- **Data da última decisão:** 2026-08-30

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #316: JAR `iceandfire-2.1.2.jar`, mod id `iceandfire`, runtime `2.1.2`, SHA-1 `0786f4142b7cabd958688f68beef3e63e9c0ae8b`.

> **Reconciliação de autoridade física — 27/09/2026.** O conteúdo histórico do Notion registra NeoForge `21.1.248` como loader físico/source-pinned da build auditada e inclui essa versão na matriz de testes. A instalação física atual usa NeoForge `21.1.250`; `iceandfire-2.1.2.jar`, mod id `iceandfire` e runtime `2.1.2` permanecem inalterados. O snapshot histórico é preservado integralmente para paridade, mas referências ao loader físico atual devem usar `21.1.250`.

<callout icon="🐉" color="purple_bg">
	**PADRÃO ALEX'S MOBS — ESCOPO CANÔNICO.** Runtime físico: `iceandfire-2.1.2.jar`, mod id `iceandfire`, versão `2.1.2`, Minecraft 1.21.1 / NeoForge **21.1.248**. O source oficial `IAFEnvoy/IceAndFire-CE:1.21.1` declara exatamente `mod_version=2.1.2` e `neo_version=21.1.248`; esta ficha está source-pinned à build e loader físicos.
</callout>
## 1. Identidade e autoridade
Ice And Fire Community Edition é o provider principal do ecossistema Ice and Fire no pack. É um fork comunitário com otimizações, rewrites e conteúdo adicional. Ele mantém authority sobre dragões, criaturas míticas, tame/ownership, eggs/growth, combat/AI próprios, equipment/materials, structures/worldgen, Dragon Forge, Bestiary/progressão e data/config associados. Addons não devem duplicar esses ledgers.
## 2. Compatibilidade de save e migração
O projeto alerta que esta edição **não deve ser tratada como substituição drop-in de outra instalação incompatível em save existente**; mudanças de IDs/estrutura podem corromper ou invalidar state. Toda troca entre forks/linhas deve ser feita em cópia de mundo, com backup e verificação de missing mappings/entities/block entities antes de abrir o save principal.
## 3. Escopo de conteúdo da Community Edition
A documentação oficial declara que a CE mantém o conteúdo central do Ice and Fire original, com exceções explícitas como a série **Myrmex**, além de conteúdo/reworks próprios. Portanto não presumir que guias do IAF clássico descrevem 100% da CE 2.1.2. Para comportamento version-sensitive, source 1.21.1/2.1.2 prevalece.
## 4. Dragon types
O source 2.1.2 registra três dragon types de primeira classe: **Fire**, **Ice** e **Lightning**, cada um ligado a sua entidade, skull e summoning crystal. Eles compartilham o framework de dragão, mas breath/damage/environment/materials e interações são type-specific. Integrações devem usar o tipo/provider real, não inferir somente por textura ou nome de item.
## 5. Lifecycle dos dragões
Dragões possuem lifecycle persistente envolvendo spawn/egg, growth, tame/owner, inventory/equipment quando aplicável, riding/command, sleep/AI/combat, death/corpse/loot e movimentação entre chunks/dimensões. Owner UUID e state de crescimento não podem ser espelhados por addon externo como segunda authority. Reconnect e server restart precisam restaurar um único state authoritative.
## 6. Registries source-pinned
A build expõe registries dedicados para **entities, dragon types, items, blocks, block entities, attributes, mob effects, potions, recipes, menus, particles, features, structure types, structure processors, trades, loot, attachments** e outras superfícies auxiliares. Isso confirma que registry/data/worldgen são centrais à arquitetura e que update unilateral pode afetar muito além de mobs.
## 7. Attachments e state NeoForge
O source 2.1.2 usa registry de NeoForge attachments. State associado a entidades/jogadores deve ser validado em clone/death, login/relog, dimension transfer e save/restart conforme cada attachment. Addons próprios não devem serializar cópia paralela de state IAF quando um attachment/provider já é owner.
## 8. Dragon Forge
Dragon Forge é sistema provider-native com cores/tipos Fire, Ice e Lightning. O source 2.1.2 possui cores disabled/active e integração Ponder que demonstra as três variantes. Recipe admission, dragon-breath interaction, input/output e block-entity state devem ser liquidados uma vez; unload/restart não pode duplicar output nem perder inputs silenciosamente.
## 9. Recipes, loot e trades
A build registra recipe serializers/types, loot e trades. KubeJS/datapacks devem alterar conteúdo por IDs/tags reais da 2.1.2, preservando a authority do provider. `/reload` precisa validar recipes/tags/loot sem missing codec. Loot de criaturas/boss-like mobs não deve ser concedido novamente por quest listeners sem deduplicação.
## 10. Worldgen e structures
Ice and Fire CE registra features, structure types e processors. Dragons/creatures/structures podem depender de biome tags e condições data-driven. A documentação oficial indica que a configuração de biomas é feita por datapacks/tags. Mudanças de frequência/biome targeting afetam apenas chunks novos conforme as regras normais de worldgen; não presumir retrofit de estruturas já geradas.
## 11. Configuração — Jupiter
O projeto usa **Jupiter** como sistema de configuração e declara GUI client-side de config com controle de permissão de operador para opções server-relevant. O pack contém `jupiter-2.3.7-1.21.1-neoforge.jar`. Valores server-authoritative não devem ser aceitos simplesmente porque um cliente abriu/alterou UI; o servidor continua autoridade e deve distribuir/validar config efetiva.
## 12. Render stack e GeckoLib
A linha 2.1.x migra/usa GeckoLib em partes relevantes do render de dragões, e o source 2.1.2 declara GeckoLib como dependency de build. O pack possui GeckoLib 4.9.2. Resource reload, dragon animation state, rider pose e layer rendering são boundaries de regressão. Render callbacks não devem executar dano/spawn como segunda fonte de gameplay.
## 13. JarJar `Integration 0.2`
O JAR físico contém `META-INF/jarjar/integration-neoforge-0.2.jar`, mod name `Integration`, versão 0.2. O source 2.1.2 declara `integration_version=0.2`, confirmando correspondência. É dependência interna do host e **não conta como mod top-level**. Atualização do provider pode trocar esse runtime embutido mesmo sem novo JAR top-level.
## 14. Compatibilidades oficiais relevantes
A documentação oficial da CE 2.x declara suporte/integração com Curios, EMF/ETF, EMI, Jade, JEI e outros; para Iron's há addon dedicado, e Ponder oferece tutorial do Dragon Forge em MC 1.21+. O source contém compat para Ars Nouveau. O pack possui Curios, JEI, Ars Nouveau e várias bridges específicas. Cada integração deve ser catalogada no owner correspondente.
## 15. Epic Fight — boundary explícito
A tabela oficial da CE declara **Epic Fight sem suporte nativo**. O pack resolve uma parcela específica via `iceandfire-ce-epicfight-armor-compat-1.0.0.jar`, que adapta armaduras. Isso **não** transforma a CE em full Epic Fight integration: dragon AI, weapons, movesets e combat pipeline continuam sem compatibilidade presumida salvo bridge separada comprovada.
## 16. Dragon Care
`Dragon Care 1.3.1` está fisicamente presente e declara suporte explícito à CE 2.1.2. Ele adiciona bonding/care/harvest/Dragon Phone sem substituir tame/growth/combat authority do IAF. Ambos modificam a mesma entidade dragão, portanto update de um exige smoke test conjunto.
## 17. Dread Land
`Ice And Fire: Dread Land 0.1.2` está instalado e usa CE + Jupiter para uma dimensão/progressão adicional. Dread Land é addon early-alpha e não deve alterar a interpretação de stability do provider-base. Structures/keys/progression do addon ficam em ficha separada.
O changelog oficial da release **2.1.2** registra explicitamente o fix `dread mob spawn`. A build física já contém esse patch no provider-base; isso reduz um defeito conhecido dessa superfície, mas não deve ser extrapolado como garantia de estabilidade geral do addon Dread Land ou de seu world state.
## 18. Client / server
A distribuição é Client & Server.
- **Servidor:** entity/world state, tame/owner, AI/combat, breath/damage, growth, forge recipes, loot/trades, structures, configs e progression.
- **Cliente:** models/animations, particles/sounds, GUI/Bestiary/config presentation e input.
Registry/data mismatch entre lados é hard failure; visual prediction não é authority de dano ou movement final.
## 19. Multiplayer e exactly-once
Cenários críticos incluem dois jogadores atacando/montando/interagindo com o mesmo dragão, owner disconnect, rider dismount, breath AOE, egg hatch, Dragon Forge settlement, corpse/loot e quest reward. Dano, loot, tame changes, forge output e achievements/rewards devem ocorrer exatamente uma vez no owner/contexto correto.
## 20. Lifecycle operacional
Validar cold boot, registry sync, login, spawn/despawn, chunk unload/reload, dragon riding, dimension transfer, player death/respawn, owner reconnect, server save/restart, datapack reload, resource reload e atualização de mod. Block entities/attachments e entity state devem convergir após reload/restart sem orphan UUIDs.
## 21. Sobreposição com outros mob/worldgen mods
O pack possui Alex's Mobs, Mowzie's Mobs, Cataclysm e muitos structure packs. Sobreposição de categoria não significa redundância. Conflitos reais a vigiar são spawn density, biome tags, structure spacing, loot inflation, combat hooks e renderer layers. Remoção/deduplicação exige comparar provider e worldgen concretos.
## 22. Riscos técnicos
- migração inadequada de outro fork/save;
- entity/registry missing mappings após update;
- tame/owner/growth state perdido ou duplicado;
- Dragon Forge partial settlement/dupe;
- breath/damage processado duas vezes por combat addon;
- structure/feature collisions e worldgen cost;
- Jupiter config client/server divergente;
- GeckoLib render/cache regression;
- attachment state stale;
- addon ABI drift (Dragon Care/Dread Land/armor compat);
- Epic Fight ser considerado suportado além do escopo real da bridge;
- internal Integration 0.2 catalogado ou atualizado como mod independente.
## 23. Matriz de testes obrigatória
- [ ] Dedicated server boot com IAF CE 2.1.2 / NeoForge 21.1.248.
- [ ] Registry sync sem missing mappings ou duplicate IDs.
- [ ] Fire/Ice/Lightning dragons: spawn, AI, breath, damage, death e loot.
- [ ] Egg/growth/tame/owner persistem em relog/restart/dimension transfer.
- [ ] Riding/dismount com dois jogadores sem ownership/movement desync.
- [ ] Dragon Forge dos três tipos processa recipe exatamente uma vez, inclusive unload/restart.
- [ ] Bestiary/menus e GeckoLib render sobrevivem a F3+T/resource reload.
- [ ] Jupiter server config prevalece sobre UI client e persiste corretamente.
- [ ] Mundo novo gera estruturas/features conforme biome tags; mundo existente reabre sem corrupção.
- [ ] `/reload` conclui sem recipe/tag/loot/structure codec errors.
- [ ] Dragon Care 1.3.1 mantém bonding/harvest após update do provider.
- [ ] Armor compat + Epic Fight não causa deformação/dupla pose; nenhum suporte adicional é presumido.
- [ ] Dread Land 0.1.2 entra/sai de dimensão sem corromper entities/structures IAF.
- [ ] Backup de save é obrigatório antes de qualquer troca de fork/major line.
## 24. Evidências e limites
- **Modlist física:** `iceandfire-2.1.2.jar`, mod id/version, Integration 0.2 interno e addons presentes.
- **Source oficial:** branch `1.21.1`, `mod_version=2.1.2`, `neo_version=21.1.248`, GeckoLib/Curios/Integration versions e registries arquiteturais confirmados.
- **Documentação oficial:** escopo CE, warning de save migration, Myrmex ausente, Jupiter config, biome tags e matriz de compatibilidade.
- **Limite:** não foi transformada esta ficha em dump integral de centenas de registry IDs; registries e systems foram mapeados por domínio, com IDs concretos apenas onde necessários para contracts.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.

## Atualização upstream e gate de manutenção — 11/10/2026

**Release oficial inédita para esta auditoria:** `iceandfire-2.1.4.jar` (**NeoForge 1.21.1**, **11/10/2026**, file **9128508**). A release 2.1.3 (20/09) precede a 2.1.4; última versão física comprovada historicamente **2.1.2**. **Changelog 2.1.4:** adiciona valores **EMC**, opções de proteção/toughness por material de armadura e toggles de passivas separadas (escamas/dragonsteel breath resistance, troll projectile immunity, tide guardian effects), opção cosmética unbreakable; configurações de pixie wand cooldown, pixie charge duration, hydra heart regeneration, deathworm gauntlet range/knockback/ray targeting; modificadores de dificuldade de Sea Serpent, stage mínima de dragão para quebra de blocos, offset Y das cavernas, pathfinding de dragão evitando água e imunidade do Hippocampus em bubble column. **Gate de promoção:** auditar default/migration de Jupiter config sem perder valores locais, combate/armadura e efeitos passivos, dano e encantamentos, Pixie/Hydra/Deathworm, dragons + nests + structure gen em mundo novo e persistente, tame/bonding Dragon Care, Dread Land, armor Epic Fight compat, GeckoLib, Curios, server ownership, save backup e teste dedicado. **Fonte:** https://www.curseforge.com/minecraft/mc-mods/iceandfire-ce/files/9128508

> **Estado:** atualização documental sem qualquer substituição de JAR, edição da autoridade física ou teste in-game nesta execução.

## Auditoria incremental — 20/11/2026: 2.1.3, 2.1.4 e 2.2

O [catálogo CurseForge para 1.21.1](https://www.curseforge.com/minecraft/mc-mods/iceandfire-ce/files/all?page=1&pageSize=20&version=1.21.1) confirma três releases NeoForge posteriores à base física 2.1.2 (#316). A nova release 2.2 é efetivamente da linha 1.21.1, distinta dos arquivos para Minecraft 26.1.2.

- **2.1.3 — 20/09/2026:** [file 8929517](https://www.curseforge.com/minecraft/mc-mods/iceandfire-ce/files/8929517) registra correções genéricas, sem discriminar os defeitos. Não atribuir alterações específicas a esta versão sem diff de source pinado ou JARs.
- **2.1.4 — 11/10/2026:** [file 9128508](https://www.curseforge.com/minecraft/mc-mods/iceandfire-ce/files/9128508) revalida o delta de configuração de armaduras, itens lendários, criaturas e worldgen, além de EMC, já documentado na seção anterior.
- **2.2 — 05/11/2026:** [file 9455119](https://www.curseforge.com/minecraft/mc-mods/iceandfire-ce/files/9455119), JAR candidato `IceAndFireCE-2.2-1.21.1-neoforge.jar`. Notas oficiais relevantes:
  - Montarias deixam de aceitar coordenadas fornecidas pelo cliente; corrigidos controles de lectern e validação de alcance/menu. Integrações devem respeitar o servidor como authority.
  - Sincronização de podiums, pixie houses e jars pela dimensão/chunk; conteúdo inicial e persistência da produção/liberação corrigidos.
  - Dragon Forge: retenção de inventário na transição do core, reconexão, calor restante, matching de componentes, limites de stack e salvamento de progresso corrigidos. Priorizar testes de conservação de inputs/outputs durante substituição do core, unload e restart.
  - Summoning crystals: validação de propriedade, busca de dragões descarregados e invocação interdimensional melhoradas; tickets temporários devem desaparecer após término ou cancelamento.
  - AI/breeding/targeting de dragões e outras criaturas, pathfinding de deathworm, dimensionamento de partes de sea serpent e lifecycle de bandos de Stymphalian birds corrigidos.
  - Cetro de cockatrice (durabilidade e targets em multiplayer), dano repetido de chain lightning e ativação/durabilidade de ghost sword na mão secundária corrigidos; evitar contabilizar hits/efeitos em duplicidade.
  - Recuperação do terreno transformado por random tick e seleção de blocos de aldeias pixie corrigidas; caches de bestiary e modelos de estátuas, isolamento de NBT/render e reload corrigidos; recipes Farmer's Delight passam a respeitar ausência do provider; harvest levels, armadura de montaria, dread lich spawn e quebra de boats por dragões corrigidos.

**QA de promoção:** validar migração de configs, Dragon Forge exactly-once, ownership e invocação interdimensional com dois jogadores, descarregamento de chunks e cleanup de tickets, compatibilidade Farmer's Delight, Dragon Care, Dread Land e bridge Epic Fight, e abertura de cópia de mundo. Não presumir ABI compatível pela ausência de nota de breaking change. Nenhum JAR foi substituído ou teste executado. A lacuna técnica da 2.1.3 permanece aberta; auditoria documental parcial, nome e versão física históricos preservados.
## Auditoria incremental — 20/11/2026: 2.2.1 bugfix NeoForge 1.21.1

O [CurseForge file 9718410](https://www.curseforge.com/minecraft/mc-mods/iceandfire-ce/files/9718410) confirma **IceAndFireCE-2.2.1-bugfix-1.21.1-neoforge.jar**, Release publicada em **20/11/2026**, posterior à 2.2 de 05/11. Não confundir com 2.2.1 para Minecraft 26.1.2.

- Corrige Fire, Ice e Lightning Dragons mirando incorretamente a entrada da **Dragon Forge** em coordenadas muito altas devido a arredondamento float; o cálculo do centro do bloco mantém precisão double.
- Restaura a coleta de cadáveres após fix de death-state #284: loot com mão vazia, coleta de sangue com garrafa e extração final de crânio. O targeting requer mirar a região central do corpo.
- Evita que um único clique com a mão principal/secundária colete duas vezes em NeoForge (proteção contra duplicação de drop).

**QA antes de instalar:** Dragon Forge distante da origem; harvesting de dragões em vários estados e com mão direita/esquerda, dois jogadores, chunk unload/reload e interações com addons de loot/perks. A publicação mais recente não está comprovadamente instalada. Versão física documentada continua 2.1.2 e arquivo certificado preservado. **Auditoria ainda PENDENTE:** a 2.1.3 continua com changelog genérico sem comparação técnica isolada de source/JAR; não declarar todo o intervalo auditado.

## Fechamento da auditoria incremental — 20/11/2026: 2.1.2 → 2.2.1-bugfix (NeoForge 1.21.1)

**Autoridade física, sem promoção:** #316, `iceandfire-2.1.2.jar`, versão `2.1.2`, identidade de projeto **IceAndFire Community Edition** (`IAFEnvoy/IceAndFire-CE`, não o mod clássico nem outro fork). O source oficial [branch 1.21.1](https://github.com/IAFEnvoy/IceAndFire-CE/tree/1.21.1) mantém `minecraft_version=1.21.1` e `neo_version=21.1.248` no `gradle.properties` dos pins analisados. O loader 21.1.248 desses builds não rebaixa a autoridade do snapshot do modpack (21.1.250). **Não afirmar que novos JARs estejam instalados.**

### Cadeia de todas as publicações NeoForge 1.21.1 após 2.1.2

[Catálogo oficial filtrado 1.21.1](https://www.curseforge.com/minecraft/mc-mods/iceandfire-ce/files/all?page=1&pageSize=20&version=1.21.1) e [source oficial com commits de versão](https://github.com/IAFEnvoy/IceAndFire-CE/commits/1.21.1/) identificam:

| Publicação | Data | Referência da distribuição e do source | Escopo |
|---|---|---|---|
| **2.1.3**, `iceandfire-2.1.3.jar` | 20/09/2026 | [CurseForge file 8929517](https://www.curseforge.com/minecraft/mc-mods/iceandfire-ce/files/8929517), [pin `7d0bb2f`](https://github.com/IAFEnvoy/IceAndFire-CE/commit/7d0bb2f93d) (`mod_version=2.1.3`) | Quatro commits individuais, examinados abaixo. Fecha a lacuna anterior de changelog genérico. |
| **2.1.4**, `iceandfire-2.1.4.jar` | 11/10/2026 | [CurseForge file 9128508](https://www.curseforge.com/minecraft/mc-mods/iceandfire-ce/files/9128508), [source versionado `5a7f4de`](https://github.com/IAFEnvoy/IceAndFire-CE/commit/5a7f4de969) (`mod_version=2.1.4`) | Configuração e conteúdo da release já detalhados em seção anterior; source desde 2.1.3 acrescenta textura/EMC, correção de armor de hippogryph/hippocampus e altura de penas Stymphalian. |
| **2.2**, `IceAndFireCE-2.2-1.21.1-neoforge.jar` | 05/11/2026 | [CurseForge file 9455119](https://www.curseforge.com/minecraft/mc-mods/iceandfire-ce/files/9455119), [source pin `1e7855e`](https://github.com/IAFEnvoy/IceAndFire-CE/commit/1e7855e1bc) (`mod_version=2.2`) | Changelog da release documentado no dossier; source adiciona flight rework embutido, biome tags e fixes de loot/combat/worldgen, seguidos por revisão grande de IA, networking, persistência e forge. |
| **2.2.1**, `IceAndFireCE-2.2.1-1.21.1-neoforge.jar` | 20/11/2026 | [CurseForge: catálogo de arquivos 1.21.1](https://www.curseforge.com/minecraft/mc-mods/iceandfire-ce/files/all?page=1&pageSize=20&version=1.21.1); [source pin `397a5a8`](https://github.com/IAFEnvoy/IceAndFire-CE/commit/397a5a8cfd) (`mod_version=2.2.1`) | Fixes de criaturas/forge, proteção de geração e armadura, e restauração dos controles de montar Death Worm (#300). |
| **2.2.1-bugfix**, `IceAndFireCE-2.2.1-bugfix-1.21.1-neoforge.jar` | 20/11/2026 | [CurseForge file 9718410](https://www.curseforge.com/minecraft/mc-mods/iceandfire-ce/files/9718410); [source pin `f9abc8d`](https://github.com/IAFEnvoy/IceAndFire-CE/commit/f9abc8d2e5) (`mod_version=2.2.1-bugfix`) | Hotfix de precisão da Dragon Forge, loot/interação do cadáver e duplicação de colheita. É a última Release 1.21.1 encontrada. |

Não foram identificados outros arquivos NeoForge 1.21.1 publicados no intervalo após 2.1.2 no catálogo atual. O código fonte **não publica tags de release para essa série**; os pins usados são commits com `mod_version` verificado em `gradle.properties`, e as notas CurseForge continuam a autoridade sobre conteúdo do artefato distribuído. Comparar source não prova identidade binária por SHA. Não atribuir retroativamente commits posteriores ao pin de uma build anterior sem prova da distribuição.

### 2.1.3 — resolução da lacuna histórica (quatro commits)

A [comparação oficial entre source `2.1.2` (`52766c799d`) e `2.1.3` (`7d0bb2f93d`)](https://github.com/IAFEnvoy/IceAndFire-CE/compare/52766c799d...7d0bb2f93d) retorna `ahead=4`, `behind=0`, e expõe o delta até o bump versionado:

1. **15/09 — [`cd433b7`](https://github.com/IAFEnvoy/IceAndFire-CE/commit/cd433b7486)** (`fix #262 #266 #268`): introduz `BodyPartEntity` (hitbox do corpo pai acessível a consumidores de entidades multipartes) para Cyclops e Hydra; evita repetição de colisão e impede que a Hydra acerte a própria parte com sopro. Ajusta drop de sangue de dragão para `placeItemBackInInventory` e previne dano do próprio relâmpago da espada Dragon Bone ao invocador identificado.
2. **15/09 — [`a16787a`](https://github.com/IAFEnvoy/IceAndFire-CE/commit/a16787ae2f)**: baby dragon no ombro volta a desmontar via sneak. `DragonBaseEntity.rideTick` chama `super.rideTick()` para receber eventos/tick de passageiros e o servidor valida a relação de montaria nos dois sentidos antes de processar controle.
3. **20/09 — [`0cf5a24`](https://github.com/IAFEnvoy/IceAndFire-CE/commit/0cf5a2458e)**: Lightning Lily deixa de aceitar plantas de grama como bloco de suporte (`BlockTags.DIRT` em lugar de tag que incluía plantas).
4. **20/09 — [`7d0bb2f`](https://github.com/IAFEnvoy/IceAndFire-CE/commit/7d0bb2f93d)**: Dragon Roost corrige posição vertical de boulders, distinguindo blocos sólidos de plantas. O mesmo commit muda `mod_version=2.1.2 → 2.1.3`, permitindo localizar o limite do snapshot source.

**Impacto:** alterações efetivas de hit detection para Hydra/Cyclops, proteção contra self-hit e duplicação de dano, inventário de blood loot, baby dragon dismount/eventos NeoForge e posicionamento de worldgen/plantas. Testar multipart body, projéteis, eventos combat/hit de outros mods, baby shoulder dragon em dois clientes, e roosts em chunks novos. Isso substitui a classificação genérica da 2.1.3 nas seções anteriores: a versão não está mais tecnicamente não auditada.

### 2.1.4, 2.2 e duas publicações 2.2.1 — reconciliação adicional do source

- A [comparação 2.1.3→2.1.4 versionado](https://github.com/IAFEnvoy/IceAndFire-CE/compare/7d0bb2f93d...5a7f4de969) identifica 3 commits: texturas de items, EMC ProjectE e correções de valor de armadura Hippogryph/Hippocampus e posição de pena Stymphalian (#275/#280). As opções de configuração do changelog CurseForge 2.1.4 permanecem detalhadas na seção anterior; não supor que um pin isolado contenha todos os commits existentes na distribuição sem comparação binária.
- A [comparação 2.1.4 versionado→2.2 versionado](https://github.com/IAFEnvoy/IceAndFire-CE/compare/5a7f4de969...1e7855e1bc) registra 9 commits, incluindo: ajuste de spawn Dread Lich/mausoleum, suporte a categorias comuns de biome tags com separação cave/roost, níveis de coleta de ferramentas, dragões destruindo boats em vez de travar, dano de chained lightning baseado na arma, integração do **IAF Flight Rework** no provider-base (`dacbe48e2d`) e revisão da 2.2 com regressões. A integração de flight é mudança funcional relevante: verificar se existe addon independente de mesmo propósito antes de atualizar e evitar duplicar controle/stamina/packet state.
- A [comparação 2.2→2.2.1](https://github.com/IAFEnvoy/IceAndFire-CE/compare/1e7855e1bc...397a5a8cfd) tem 3 commits. Em [`4796224`](https://github.com/IAFEnvoy/IceAndFire-CE/commit/4796224bf7), a documentação de source registra correções das issues #284 (cadáver retorna death state real), #290 (Dragon Forge seleciona somente dragão elegível, awake/adult/tipo/linha de visão/range, cancela breath quando alvo inválido), #291 (Hippogryph voo sem destino, altitude e gravidade controladas) e #295 (Stymphalian flock sem líder). Em [`81f4821`](https://github.com/IAFEnvoy/IceAndFire-CE/commit/81f482165d), modelos de Dragonsteel e Sea Serpent ganham folga para placas/visor/ombros não ficarem escondidos nas camadas infladas de armor (#273); a documentação upstream informa 19 cenários automatizados de geometria aprovados naquele projeto, o que **não** equivale a teste local do modpack. Em [`397a5a8`](https://github.com/IAFEnvoy/IceAndFire-CE/commit/397a5a8cfd), Death Worm riding restaura WASD/jump no cliente controlador e execução coerente do movimento/controle de IA (#300); o source inclui regressões de riding, ainda sem teste in-game no pack.
- A [comparação 2.2.1→2.2.1-bugfix](https://github.com/IAFEnvoy/IceAndFire-CE/compare/397a5a8cfd...f9abc8d2e5) tem **um commit**, incluindo `mod_version=2.2.1-bugfix`: Dragon Forge target block em coordenadas grandes conserva precisão double, dragões mortos voltam a oferecer loot/sangue/crânio pela interação de mão, e um right-click NeoForge não colhe duas vezes por meio de ambas as mãos. Esses três itens coincidem com as [notas oficiais do hotfix](https://www.curseforge.com/minecraft/mc-mods/iceandfire-ce/files/9718410), que permanecem no bloco anterior.

**Risco de atualização e testes pendentes:** coordenar addons Dragon Care 1.3.1, Dread Land 0.1.2, armor compat Epic Fight, GeckoLib/Curios e mods de flight/control; preservar mundo e configs Jupiter. Testar dedicated server+cliente, Dragon Forge exactly-once (inclusive coordenadas altas/solicitações concorrentes), dragões multipartes e tame/owner, cadáver/loot via duas mãos, source de eventos/control packets, 3D armor render, geração de biomas/roosts em chunks novos, summon interdimensional, mundo salvo/unload/restart, e ausência de duplicação de itens ou state. **Nenhum desses testes executado nesta auditoria.**

**Resultado: CONCLUÍDO — DOCUMENTAÇÃO VERIFICADA até a Release 2.2.1-bugfix.** A antiga pendência de notas genéricas da 2.1.3 foi eliminada pela comparação exata de commits da branch oficial, e as duas distribuições da 2.2.1 foram individualizadas. A versão `2.1.2` no basename representa somente a instalação física histórica, inalterada. Não renomear, não mover e não aplicar promoção de versão sem JAR real. A auditoria documental não homologa compatibilidade de instalação.
