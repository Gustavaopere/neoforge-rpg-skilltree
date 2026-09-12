# Underground Village, Stoneholm

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81dba8e3e497fc8aec36
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `underground_village-neoforge-1.21.1-2.0.jar`, mod id `underground_village`, runtime `2.0`; Better Library 1.0.111 presente top-level
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência/modlist física de 11/09/2026. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Stoneholm 2.0 e Better Library 1.0.111 estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Underground Village, Stoneholm
- **Arquivo JAR:** `underground_village-neoforge-1.21.1-2.0.jar`
- **Versão 1.21.1:** 2.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Worldgen, Exploração
- **Função:** Provider de settlements subterrâneos: gera grandes Stoneholm villages com halls, múltiplos níveis, casas, farms, meeting rooms, villagers e áreas abandonadas/treasure; v2 melhora placement conforme terreno/superfície.
- **Dependências:** Better Library é required dependency upstream e está presente top-level como `better_lib-neoforge-1.21.1-1.0.111.jar`, mod id `better_lib`, versão 1.0.111. NeoForge 1.21.1. Compatibilidade efetiva deve ser validada em dedicated server.
- **Sobreposição:** Sobreposição de exploração/worldgen com Integrated Villages, Create: Dynamic Village e outros structure providers; não é integração automática com MineColonies nem framework de colony management.
- **Compatibilidade/Riscos:** Worldgen collision/density, terrain-provider drift, hidrologia, loot progression, villager pathfinding, chunk seams e version coupling com Better Library. V2 evita oceans/rivers/lakes/mountains e exige ground adequado acima. Better Library 1.0.111 está fisicamente presente; não há missing-dependency ambiguity.
- **Observações:** mod id `underground_village`; runtime 2.0 Release. Decisão Sem decisão preservada. CORREÇÃO 11/09/2026: Better Library não está ausente; o pack instala Better lib 1.0.111 como top-level #69.
- **Procedência:** modlist.txt física atual de 11/09/2026 + Modrinth/CurseForge oficiais Underground Village, Stoneholm 2.0 + relations oficiais confirmando Better Library required + JAR físico `better_lib-neoforge-1.21.1-1.0.111.jar` presente top-level. Dossiê anterior corrigido quanto à dependência.
- **Fonte:** https://modrinth.com/mod/underground-village%2C-stoneholm/version/tTkOkmZ6 ; https://www.curseforge.com/minecraft/mc-mods/underground-villages-stoneholm
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — Stoneholm 2.0 permanece exatamente instalado; settlement/worldgen e placement v2 preservados. CORREÇÃO: Better Library requerida está presente top-level como 1.0.111.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-09

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `underground_village-neoforge-1.21.1-2.0.jar`, mod id `underground_village`, versão `2.0`. Underground Village / Stoneholm é um **provider de settlements subterrâneos**: gera grandes vilas em redes de corredores, salas, casas, farms e áreas abandonadas abaixo da superfície. A versão 2.0 reescreve critérios de placement para produzir localizações mais naturais.

## 1. Identidade, versão e papel
- **Mod:** Underground Village, Stoneholm.
- **JAR físico:** `underground_village-neoforge-1.21.1-2.0.jar`.
- **Mod id:** `underground_village`.
- **Versão:** `2.0`.
- **Minecraft/loader:** NeoForge 1.21.1.
- **Canal:** Release.
- **Publicação da build 2.0:** 31/05/2026.
- **Ambiente oficial:** Client & Server.
- **Decisão vigente:** Sem decisão; preservada.

## 2. Authority e ownership
Stoneholm é authority apenas de **suas estruturas/settlements subterrâneos e regras de placement**. Villagers continuam obedecendo às regras do sistema vanilla/mods que alteram villagers; Stoneholm não deve ser tratado como um segundo framework de jobs, colony management ou AI.

MineColonies, Integrated Villages, Create: Dynamic Village e outros providers de settlements permanecem sistemas separados, mesmo quando ocupam o mesmo domínio temático de aldeias.

## 3. Conceito de Stoneholm
A descrição oficial define Stoneholm como vilas subterrâneas inspiradas em **Strongholds + Villages**. Elas formam:
- corredores e halls extensos;
- múltiplos pisos/níveis;
- casas e rooms subterrâneas;
- farms;
- meeting rooms;
- iluminação e decoração;
- seções abandonadas associadas à exploração/treasure.

O mod transforma o subsolo em settlement explorável, não apenas em uma estrutura única compacta.

## 4. Villagers e uso do settlement
As vilas são habitadas por villagers. A documentação pública sugere que o jogador pode explorar, ocupar a estrutura ou interagir com os villagers encontrados.

Sem source/version pin interno da 2.0, esta ficha não inventa:
- contagem fixa de villagers;
- profession distribution;
- POI list específica;
- raid/hero behavior específico;
- trade changes próprios do mod.

Essas superfícies devem ser observadas no runtime.

## 5. Worldgen v2 — mudanças exatas de placement
O changelog oficial da 2.0 registra que as villages:
- geram em **locais melhores e mais naturais**;
- fazem checks para evitar **oceans, rivers, lakes e mountain areas**;
- exigem **suitable ground above** antes de gerar;
- tiveram consistency/placement quality melhoradas;
- reduzem spawns estranhos ou irreais.

Isso torna o terreno/superfície acima da estrutura parte explícita do algoritmo de seleção de local.

## 6. Relação com terrain/worldgen do pack
O pack possui Tectonic, Terralith, Streams Reflowing e grande número de structure/worldgen mods. Como Stoneholm 2.0 avalia condições de terreno/superfície, esses providers podem alterar a frequência ou os locais elegíveis sem integrarem diretamente o mod.

Não assumir incompatibilidade automática: a superfície correta de teste é distribuição, colisão e placement em chunks novos.

## 7. Estruturas, loot e exploração
A descrição oficial menciona **abandoned sections** e exploração em busca de treasure. Isso confirma uma dimensão de loot/exploração, mas a auditoria não encontrou tabela oficial completa de loot da build 2.0.

Não enumerar chest pools, raridades ou itens específicos sem datapack/source pin. Validar diretamente nos loot tables/runtime se for necessário balancear progressão.

## 8. Persistência e chunk generation
Stoneholm é worldgen persistente por chunk:
- estruturas já geradas permanecem mesmo se config/versão mudar;
- update/removal afeta sobretudo chunks futuros e referências do conteúdo registrado;
- mudanças de placement não retroativamente reposicionam vilas antigas.

Testes precisam separar mundo novo de região já gerada.

## 9. Dependência Better Library — corrigida contra a modlist física
A página oficial atual do projeto lista **Better Library** como required dependency, e a listagem pública do arquivo 2.0 associa a build a essa dependência.

A modlist física atual de 11/09/2026 **contém Better Library como JAR top-level**: `better_lib-neoforge-1.21.1-1.0.111.jar`, mod id `better_lib`, nome `Better lib`, versão `1.0.111` (#69 físico).

Estado corrigido desta auditoria:
- dependency upstream: **confirmada**;
- Better Library top-level no pack: **presente**;
- versão física: **1.0.111**;
- não há necessidade de presumir library embarcada para explicar o runtime atual.

Dedicated-server boot continua sendo regression gate para compatibilidade efetiva Stoneholm 2.0 ↔ Better Library 1.0.111, mas a antiga ambiguidade de dependência ausente foi eliminada.

## 10. Client / server
A release é marcada como **Client & Server**. Worldgen e settlement state são server-authoritative; o cliente recebe chunks/entities e renderiza a estrutura.

Validar que dedicated server possui todas as dependencies efetivas e que client join não exige resources/classes ausentes.

## 11. Multiplayer
Em multiplayer, os riscos vêm do worldgen/settlement compartilhado:
- dois players explorando a mesma village devem ver o mesmo state de chunks/chests/villagers;
- loot containers não podem ser regenerados por unload/reload;
- villager state deve persistir após players deixarem a área;
- worldgen não deve executar duas vezes por concorrência de chunk generation.

## 12. Integrações concretas e coexistências no pack
- **Tectonic 3.0.26:** altera macro-terrain e pode mudar locais elegíveis para placement.
- **Terralith 2.6.2:** altera terrain/biomes e pode influenciar distribuição.
- **Streams Reflowing 2.13.1:** rios/água são relevantes porque v2 evita rivers/lakes.
- **Integrated Villages:** outro provider de village structures; risco é densidade/sobreposição, não integração assumida.
- **Create: Dynamic Village:** outro conteúdo de village; manter ownership separado.
- **Village Spawn Point:** pode usar villages como referência, mas não transforma Stoneholm em dependency sem evidência explícita.
- **MineColonies:** settlement framework independente; não assumir conversão de Stoneholm em colony.

## 13. Riscos técnicos
1. **Dependency/version coupling:** Better Library 1.0.111 está presente top-level; validar compatibilidade efetiva com Stoneholm 2.0 e reauditar se qualquer lado mudar de versão.
2. **Worldgen collision:** grande footprint subterrâneo pode intersectar dungeons, caves, mineshafts ou estruturas modded.
3. **Terrain-provider drift:** Tectonic/Terralith podem alterar eligible locations/frequência.
4. **Water placement:** v2 evita ocean/river/lake; Streams Reflowing pode mudar hidrologia final.
5. **Structure density:** múltiplos village/structure providers podem saturar exploração/loot.
6. **Loot progression:** treasure sem auditoria de loot tables pode antecipar recursos.
7. **Chunk seams:** update/removal gera fronteiras entre regiões antigas e novas.
8. **Villager lifecycle:** AI/POI mods podem afetar pathfinding em corredores/níveis subterrâneos.
9. **Performance:** grandes estruturas com muitos villagers e chunks subterrâneos podem aumentar custo de geração/AI.

## 14. Matriz de testes
- [ ] Dedicated server inicia com Stoneholm 2.0 + Better Library 1.0.111 sem missing-dependency, registry ou classloading error.
- [ ] Mundo novo gera Stoneholm em NeoForge 1.21.1 sem registry/worldgen error.
- [ ] Placement evita oceans/rivers/lakes/mountain areas conforme v2.
- [ ] Estrutura possui terreno adequado acima e não emerge de forma irreal.
- [ ] Tectonic + Terralith não causam villages suspensas/expostas/enterradas incorretamente.
- [ ] Streams Reflowing não cria água atravessando a estrutura de forma sistêmica após geração.
- [ ] Corredores/floors/houses/farms/meeting rooms geram sem pieces faltantes.
- [ ] Villagers conseguem navegar e persistir após chunk unload/reload.
- [ ] Loot containers não duplicam após restart/unload.
- [ ] Estrutura não colide de forma crítica com Integrated Villages/outros structure mods.
- [ ] Pregeneration em área ampla não produz erro ou custo excessivo de tick.
- [ ] Dois players explorando a mesma village observam state consistente.

Nenhum teste foi marcado como aprovado nesta auditoria.

## 15. Evidências
- Modlist física canônica 11/09/2026: Stoneholm 2.0 e Better Library 1.0.111 presentes como JARs top-level; Tectonic/Terralith/Streams e outros village mods também presentes.
- CurseForge/Modrinth oficiais Underground Village, Stoneholm: settlement subterrâneo, halls/floors/homes/farms/meeting rooms, Client & Server e release 2.0.
- Changelog oficial 2.0: nova lógica de placement, checks contra oceans/rivers/lakes/mountains e exigência de terreno adequado acima.
- Relations oficiais: Better Library como required dependency; modlist física atual confirma `better_lib-neoforge-1.21.1-1.0.111.jar` top-level.

## 16. Revalidação física — 11/09/2026
O runtime permanece `underground_village-neoforge-1.21.1-2.0.jar`, mod id `underground_village`, versão `2.0`; esta continua sendo a release NeoForge 1.21.1 pertinente. Releases numericamente posteriores do projeto pertencem a linhas mais novas do Minecraft e não substituem automaticamente este runtime.

A dependência Better Library foi corrigida contra a modlist física: `better_lib-neoforge-1.21.1-1.0.111.jar` está presente top-level. Nenhum teste de dedicated server, placement v2, worldgen com Tectonic/Terralith/Streams, loot ou pathfinding foi executado nesta recatalogação.
