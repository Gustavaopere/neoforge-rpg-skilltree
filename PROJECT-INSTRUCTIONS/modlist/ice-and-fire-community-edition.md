# Ice And Fire Community Edition

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db816bb456cebe9d79aa8f
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Ice And Fire Community Edition
- **Arquivo JAR:** `iceandfire-2.1.2.jar`
- **Versão 1.21.1:** 2.1.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Mobs, Worldgen, Exploração, RPG
- **Função:** Provider principal Ice and Fire CE: dragões Fire/Ice/Lightning, criaturas míticas, tame/growth/combat, equipment, Dragon Forge, structures/worldgen, recipes/loot/trades, Bestiary e progressão.
- **Dependências:** NeoForge 21.1.248; source 2.1.2 declara GeckoLib, Curios e Integration 0.2. Jupiter 2.3.7 está presente como config system. Addons físicos: Dragon Care 1.3.1, Dread Land 0.1.2 e armor compat Epic Fight 1.0.0.
- **Sobreposição:** Sobreposição de categoria com outros mob/worldgen mods não implica redundância. Dragões, tame/growth, Dragon Forge, Bestiary e estruturas IAF permanecem provider-specific. Epic Fight base não é full-compat sem bridges específicas.
- **Compatibilidade/Riscos:** Riscos: save migration entre forks, registry/entity missing mappings, tame/owner/growth state, Dragon Forge dupe/partial settlement, worldgen collisions, Jupiter config drift, GeckoLib rendering, attachment state e addon ABI drift. Epic Fight não tem suporte nativo completo; há bridge de armadura separada.
- **Observações:** JAR físico contém `integration-neoforge-0.2.jar` como JarJar interno; source declara `integration_version=0.2`. Source registra entidades/dragon types/items/blocks/attachments/recipes/loot/trades/features/structures/processors/etc. CE não inclui a série Myrmex segundo documentação oficial.
- **Procedência:** modlist.txt física atual de 09/09/2026 + source oficial IAFEnvoy/IceAndFire-CE branch 1.21.1 exatamente em mod_version 2.1.2 / NeoForge 21.1.248 + documentação oficial CE e compat matrix.
- **Fonte:** https://github.com/IAFEnvoy/IceAndFire-CE/tree/1.21.1
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Ice And Fire CE 2.1.2 source-pinned ao NeoForge 21.1.248; dragon types/lifecycle, registries, attachments, Dragon Forge, worldgen/config/Jupiter, integrations, save risks e matriz de testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-30

# Dossiê operacional — padrão Alex's Mobs

> **PADRÃO ALEX'S MOBS — ESCOPO CANÔNICO.** Runtime físico: `iceandfire-2.1.2.jar`, mod id `iceandfire`, versão `2.1.2`, Minecraft 1.21.1 / NeoForge **21.1.248**. O source oficial `IAFEnvoy/IceAndFire-CE:1.21.1` declara exatamente `mod_version=2.1.2` e `neo_version=21.1.248`; esta ficha está source-pinned à build e loader físicos.

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
