# YUNG's Better Dungeons

> **Autoridade física atual — 28/09/2026.** Ordem física **#579**: JAR `YungsBetterDungeons-1.21.1-NeoForge-5.1.4.jar`, mod id `betterdungeons`, runtime `1.21.1-NeoForge-5.1.4`, SHA-1 `73a660c13063450e555e5e2c015ae24d606b32b4`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** YUNG's Better Dungeons
- **Arquivo JAR:** `YungsBetterDungeons-1.21.1-NeoForge-5.1.4.jar`
- **Versão 1.21.1:** 1.21.1-NeoForge-5.1.4
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Worldgen, Exploração
- **Função:** Adiciona/substitui dungeons vanilla por múltiplos tipos de dungeons maiores e mais complexos.
- **Dependências:** YUNG's API 5.1.8. No pack coexistem Integrated Dungeons Arise 2.1.1 e Integrated Dungeons and Structures 1.13.7, entre outros structure providers.
- **Sobreposição:** Overlap de exploração/dungeon com IDA/IDAS, mas Catacombs/Fortresses of the Undead/Spider Caves são conteúdo próprio. Better Caves controla carving, não dungeons.
- **Compatibilidade/Riscos:** Riscos principais: structure intersections, processor/NBT conflicts, loot inflation, spawner/difficulty stacking e features opt-in assumidas como ativas.
- **Observações:** Mod id `betterdungeons`, runtime `1.21.1-NeoForge-5.1.4`. Small Nether dungeons são opt-in. Vanilla dungeons podem coexistir via datapack. 5.1.4 corrige crash raro de intersection/processor NBT.
- **Procedência:** modlist.txt física atual consultada em 13/09/2026 + Modrinth/CurseForge oficiais Better Dungeons 5.1.4 + YUNG's API 5.1.8. Atualizações recentes do projeto são de linhas posteriores do Minecraft. Nenhum dungeon/intersection/datapack/loot test foi executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/yungs-better-dungeons-neoforge
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 13/09/2026 — YUNG's Better Dungeons 5.1.4 permanece exatamente instalado e continua a latest release NeoForge 1.21.1; Catacombs/Fortresses/Spider Caves, config/datapack e intersection/NBT fix preservados.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-09-09

# Dossiê operacional — padrão Alex's Mobs

> 🏰 **ESCOPO CANÔNICO.** Runtime físico: `YungsBetterDungeons-1.21.1-NeoForge-5.1.4.jar`, mod id `betterdungeons`, versão `1.21.1-NeoForge-5.1.4`. Redesenha dungeons vanilla e adiciona estruturas próprias; não é cave-carver.
## 1. Conteúdo confirmado
O projeto redesenha vanilla dungeons e adiciona três famílias próprias: **Catacombs**, **Fortresses of the Undead** e **Spider Caves**. Também há small Nether dungeons como recurso opt-in, desabilitado até ser habilitado na config.
## 2. Config e datapack
Cada dungeon pode ser ajustada por config/datapack. Vanilla dungeons podem ser reabilitadas para gerar ao lado das Better Dungeons via datapack. Portanto replacement/coexistence é data-driven.
## 3. Build 5.1.4
A release 5.1.4 corrige crash raro quando estruturas intersectavam e processors aplicavam NBT incompatível. Isso é relevante para o pack atual, que possui muitos structure providers.
## 4. Stack atual
Além de Better Dungeons, o pack contém Integrated Dungeons Arise 2.1.1, Integrated Dungeons and Structures 1.13.7 e outras estruturas. O risco principal é densidade, interseção, spawn/loot e dificuldade, não duplicação binária do mod.
## 5. Authority
Better Dungeons possui suas structure identities, pieces, encounter/loot data e config. Better Caves controla carving; YUNG's API é infrastructure. Não inferir dungeon completion por proximidade ou pelo cave shape.
## 6. Boundary para quests/perks
- Discovery deve usar structure identity real.
- Reentrar/recarregar não concede progresso repetido.
- Kills/spawners só contam quando a quest/perk exige causalidade específica.
- Small Nether dungeons não devem ser tratadas como ativas sem config real.
## 7. Riscos
- structure intersections e processor/NBT conflicts;
- loot inflation;
- spawner/difficulty stacking;
- small Nether dungeons habilitadas sem curadoria;
- vanilla dungeons coexistindo via datapack sem ser esperado.
## 8. Matriz de testes
- [ ] Dedicated server gera as famílias Better Dungeons sem crash.
- [ ] Interseções com IDA/IDAS não reproduzem processor/NBT failure.
- [ ] Config/datapack controla cada dungeon corretamente.
- [ ] Small Nether dungeons permanecem conforme config efetiva.
- [ ] Vanilla dungeons coexistem apenas quando explicitamente habilitadas.
- [ ] Loot/spawner balance é revisado no stack atual.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 9. Limitação
IDs/loot tables/structure sets exatos não foram extraídos do JAR nesta etapa; hooks de quest devem inspecionar registry real antes da implementação.
## 10. Revalidação física — 11/09/2026
A modlist física mantém `YungsBetterDungeons-1.21.1-NeoForge-5.1.4.jar`, mod id `betterdungeons`, versão `1.21.1-NeoForge-5.1.4`. A file page oficial continua apontando 5.1.4 como latest release NeoForge 1.21.1.
O changelog exato continua cobrindo o fix de crashes raros quando structures intersectam e processors aplicam NBT incompatível, além da tradução PT-BR. Atualizações de projeto publicadas em 10/09/2026 são da linha Minecraft `26.1.2`, não atualização aplicável ao pack 1.21.1. O estado **Integrado ao Github** e a decisão **Sem decisão** foram preservados. Nenhum Catacomb/Fortress/Spider Cave, intersection, datapack, loot ou spawner test foi executado nesta recatalogação.
## 11. Revalidação — 13/09/2026
Better Dungeons 5.1.4 permanece a latest release NeoForge 1.21.1. O fix de crashes raros em intersections/processors com NBT incompatível permanece o regression gate central no stack atual de estruturas. Nenhum teste runtime foi executado.
