# Integrated Dungeons Arise

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db814682e6ef441c7b2cb4
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Integrated Dungeons Arise
- **Arquivo JAR:** `IDA v2.1.1-1.21.1.jar`
- **Versão 1.21.1:** 2.1.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Worldgen, Exploração, RPG
- **Função:** Server-side structure/worldgen overhaul que reinterpreta When Dungeons Arise com blocks, loot, spawners e integrações de Create, Quark, Supplementaries e outros mods via Integrated API.
- **Dependências:** Obrigatórias e presentes: Create 6.0.10, Farmer's Delight 1.3.4, Integrated API 1.8.0, Quark 4.1-483, Supplementaries 3.9.8, Amendments 2.1.10. Opcionais presentes: Waystones, Alex's Mobs, Cataclysm, Mowzie's Mobs. IDAS 1.13.7 também está presente.
- **Sobreposição:** Coexiste intencionalmente com IDAS, mas não é recomendado junto do When Dungeons Arise original sem datapack custom. Com outros structure mods, auditar structure_set/biome tags/terrain/loot por seed.
- **Compatibilidade/Riscos:** Riscos: hard dependency missing refs, worldgen/structure-set collisions, terrain placement, loot/spawner inflation, optional-registry conditions, hybrid chunks após update, remoção de dependency após geração e performance em exploração. WDA original é desaconselhado e não foi encontrado top-level.
- **Observações:** Release 2.1.1 altera Lighthouse, Aviary, Ceryneian Hind, Scorched Mines e Thornborn Towers; Guard Villagers/Better Archeology são opcionais e não aparecem top-level no pack atual. IDAS #321 é projeto distinto do When Dungeons Arise original.
- **Procedência:** modlist.txt física atual de 09/09/2026 + CurseForge oficial Integrated Dungeons Arise 2.1.1 file 8283181 + documentação oficial de dependencies/worldgen/structure_set + changelog 2.1.1.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/integrated-dungeons-arise/files/8283181
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Integrated Dungeons Arise 2.1.1 release-pinned; hard/optional dependencies, structure generation/sets/processors, loot/spawners, 2.1.1 structure changes, IDAS/WDA boundary, lifecycle/performance, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `IDA v2.1.1-1.21.1.jar`, mod id `integrated_dungeons_arise`, versão `2.1.1`, NeoForge 1.21.1. A release oficial file `8283181` é exata e o projeto é marcado **Server**. O mod/datapack reestrutura o conteúdo de When Dungeons Arise usando assets e mechanics de outros mods; o original When Dungeons Arise não aparece como top-level na modlist atual.

## 1. Papel e authority
Integrated Dungeons Arise (IDA) é overhaul/integration de structures: redesenha geração, decoração, loot e spawners de estruturas inspiradas no When Dungeons Arise para incorporar Create, Quark, Supplementaries e outros mods. IDA é authority de suas próprias structure templates/structure_sets/processors/loot; cada dependency continua owner dos blocks/items/mobs que fornece.

## 2. Dependências obrigatórias e snapshot físico
A documentação oficial exige **Create, Farmer's Delight, Integrated API, Quark, Supplementaries e Amendments**. Todos estão presentes no pack: Create 6.0.10, Farmer's Delight 1.3.4, Integrated API 1.8.0, Quark 4.1-483, Supplementaries 3.9.8 e Amendments 2.1.10. Remover qualquer hard dependency pode invalidar palettes/templates ou impedir data loading.

## 3. Dependências opcionais presentes
A documentação lista Waystones, Alex's Mobs, L_Ender's Cataclysm e Mowzie's Mobs como optional integrations; todos estão fisicamente presentes no pack. BetterEnd e Trials Backport habilitam dungeons específicas segundo a documentação, mas não foram encontrados como top-level nesta modlist; portanto essas estruturas opcionais não são presumidas disponíveis.

## 4. Relação com IDAS e When Dungeons Arise
O projeto afirma ter sido feito para coexistir com **Integrated Dungeons and Structures (IDAS)** e seus addons; `idas-1.13.7+1.21.1-neoforge.jar` está fisicamente presente como #321. Isso é diferente do **When Dungeons Arise original**, que a documentação desaconselha usar em conjunto por incompatibilidades de structure generation, loot e balance. A modlist atual não mostra o WDA original como top-level.

## 5. Structure generation
IDA altera a implantação das structures para melhorar integração com terrain. Integrated API fornece tags/mecanismos de compatibilidade worldgen. Spacing/separation em `structure_set` é a surface documentada para frequência. Alterar esses valores afeta distribuição futura e deve ser testado em seed fixa/mundo novo; não presumir realocação de estruturas já geradas.

## 6. Loot tables
O projeto redesenha loot para tornar recompensa proporcional à exploração integral das structures. Loot injection/overrides podem somar com Loot Integrations, quests e outros structure mods do pack. Reward duplication deve ser testada por chest/table concreta; não conceder reward paralelo apenas porque uma estrutura foi descoberta.

## 7. Spawners e mob authority
A documentação remove a dependência de “modified mobs” e usa mobs vanilla em spawners com ranges/quantidades ajustados. Structure processors permitem alterar stats/mobs gerados. Mobs opcionais de Alex's/Mowzie/Cataclysm só devem aparecer onde a data/integration real os declara; IDA não se torna owner da AI desses providers.

## 8. Release 2.1.1 — Lighthouse
A release física integra uma nova versão de **Lighthouse** com style ajustado ao ecossistema Integrated. Como alteração estrutural, validar template placement, terrain adaptation, loot/spawners e referências a blocks de dependencies.

## 9. Release 2.1.1 — Aviary
Aviary recebeu mais iluminação, loot revisado e melhor adaptação ao terrain. Esses três pontos são regression gates específicos: light level/spawns, loot balance e geração sem floating/cut terrain.

## 10. Release 2.1.1 — Ceryneian Hind
Ceryneian Hind foi retrabalhada como dig site, ganhou compat opcional com **Better Archeology** e ficou mais rara porque exige área plana. Better Archeology não foi encontrado na modlist top-level atual, então essa integração não é declarada ativa. A estrutura ainda deve gerar sem a dependência opcional.

## 11. Guard Villagers integration
A 2.1.1 adiciona Guard Villagers no Ceryneian Hind e Merchant Campsite **se o mod estiver instalado**. Guard Villagers não foi encontrado como top-level na modlist atual; portanto não presumir esses spawns. Ausência deve degradar de forma limpa, sem missing registry em templates/processors.

## 12. Scorched Mines e Thornborn Towers
Scorched Mines ganhou hallway rooms e substituiu Piglins por Wither Skeletons. Thornborn Towers recebeu geração melhor em dark-forest hills e redução de towers anormalmente grandes. Essas mudanças são específicas da 2.1.1 e devem ser validadas em chunks novos/seed controlada.

## 13. Optional structures por dependency
A documentação cita Heavenly Rider, Conqueror e Challenger condicionadas a BetterEnd, e Mining Complex condicionado a Trials Backport. Como essas dependencies não aparecem na modlist física atual, não registrar ausência dessas structures como bug sem antes verificar as conditions e logs de datapack.

## 14. Client / server
O projeto é marcado **Server**. A lógica primária é data/worldgen/loot/spawners; clients recebem estruturas/mobs/items através do state normal do servidor e dos próprios mods dependencies. Contudo, todos os mods que registram blocks/items usados nas structures precisam estar de forma compatível conforme suas próprias exigências de client/server.

## 15. Data-driven surfaces e reload
Structure templates, `structure_set`, processors, loot tables e tags são surfaces críticas. `/reload` pode recompilar data sem regenerar chunks existentes. Erros de registry reference, missing block ou malformed processor devem falhar de modo diagnósticável; não editar data em produção sem backup/seed comparison.

## 16. Lifecycle e world persistence
Validar datapack load no startup, criação de mundo, chunk generation, restart, update de versão, `/reload` e remoção/adição de optional dependency. Structures geradas permanecem no save mesmo se a data futura mudar; remover dependency depois de gerar blocks daquele mod pode causar missing content.

## 17. Multiplayer e performance
Structures grandes concentram spawners, loot e block entities de vários providers. Em servidor, testar chunk-generation spikes, mob density, pathfinding e loot contention com múltiplos jogadores. Aumentar frequência via spacing/separation pode multiplicar custo de worldgen e reward economy.

## 18. Sobreposição com outros structure packs
O pack possui IDAS e muitos outros worldgen/structure mods. O risco não é apenas “duas estruturas parecidas”, mas structure-set placement, biome tags, terrain adaptation, spawn density e loot inflation. Compatibilidade deve ser medida por seed/region scan, não por nome de mod.

## 19. Riscos técnicos
- hard dependency ausente quebrar template/data load;
- confundir IDAS com When Dungeons Arise original;
- structure_set spacing/separation causar densidade excessiva;
- worldgen collisions/floating/cut terrain;
- missing optional registry em conditional structure;
- loot inflation por integração com outros reward systems;
- spawner density/performance em multiplayer;
- update de template gerar fronteiras entre chunks antigos/novos;
- remover dependency após estruturas já geradas;
- `/reload` aceitar data nova sem alterar structures persistidas, causando estado híbrido.

## 20. Matriz de testes obrigatória
- [ ] Dedicated server boot com IDA 2.1.1 e seis hard dependencies físicas.
- [ ] Datapack/structure registry carrega sem missing reference.
- [ ] Seed nova gera amostra representativa de structures sem terrain corruption.
- [ ] Lighthouse 2.1.1: placement/loot/spawners/dependencies válidos.
- [ ] Aviary: iluminação, loot e terrain generation conforme changelog.
- [ ] Ceryneian Hind gera sem Better Archeology e permanece rara por requisito de área plana.
- [ ] Ausência de Guard Villagers não produz missing-registry error.
- [ ] Scorched Mines usa Wither Skeletons e hallway rooms esperados.
- [ ] Thornborn Towers melhora placement em dark-forest hills.
- [ ] Optional structures condicionadas a mods ausentes não são tratadas como bug.
- [ ] IDAS 1.13.7 coexistindo não gera colisões/loot inflation críticos.
- [ ] `/reload` conclui sem structure/processor/loot errors.
- [ ] Comparar geração/tempo de tick com 1 e múltiplos jogadores explorando chunks novos.

## 21. Evidências e limites
- **Modlist física:** IDA 2.1.1; seis hard dependencies presentes; optional Alex's Mobs/Cataclysm/Mowzie/Waystones presentes; IDAS 1.13.7 presente; WDA original não encontrado top-level.
- **CurseForge oficial:** Environment Server, função de overhaul/integration, dependencies e regras de coexistência.
- **Release 2.1.1:** Lighthouse, Aviary, Ceryneian Hind, Guard Villagers optional, Scorched Mines, Thornborn Towers e fixes.
- **Limite:** source code não é a authority desta ficha; o projeto é fortemente data-driven e os arquivos reais de structure_set/templates/processors do JAR não foram extraídos nesta execução.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
