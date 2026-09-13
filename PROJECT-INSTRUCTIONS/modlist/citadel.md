# Citadel

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81509c74e177ce8d3abd  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: `modlist.txt`, 595 mods top-level  
> Exportado em: 2026-09-09

## Propriedades do registro

- **Mod:** Citadel
- **Arquivo JAR:** `citadel-2.7.1-1.21.1.jar`
- **Versão 1.21.1:** `2.7.1`
- **Categoria:** Biblioteca
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/citadel
- **Função:** Biblioteca leve usada por mods consumidores para infraestrutura de entidades/animação/rendering, pathfinding/collision e outras utilities compartilhadas; não é provider de gameplay autônomo.
- **Dependências:** A release oficial Citadel 2.7.1 não declara dependências próprias adicionais no CurseForge. Sua necessidade no pack é determinada pelos consumidores que compilam/rodam contra Citadel.
- **Compatibilidade/Riscos:** Library sensível a version drift e mixins/hooks em entidade, pathfinding, collision, rendering e worldgen hooks. 2.7.1 corrige Citadel quebrando surface rules de outros mods, corrige iluminação de entidades no book e ajusta pathfinding/collision/rendering. Não é substituível por AzureLib/GeckoLib.
- **Sobreposição:** Biblioteca técnica específica. Coexistência com AzureLib, GeckoLib e outras APIs de entidade/animação não implica redundância binária; consumers dependem de contratos distintos.
- **Observações:** mod id `citadel`; runtime 2.7.1. Release 18/07/2026. Changelog: fix de surface rules, book entity lighting e tweaks de pathfinding/collision/rendering.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Citadel 2.7.1 para NeoForge 1.21.1 + source oficial ligado pelo projeto; registro histórico do port não-oficial mantido separado.
- **Histórico da decisão:** Sem decisão formal. A auditoria histórica de 22/08/2026 confirmou o Citadel oficial `2.7.1` como entrada distinta do antigo `Citadel (Unofficial Port)` removido; essa confirmação de presença não constitui decisão curatorial. Em 08/09/2026, o dossiê foi reconstruído para o JAR físico atual sem inventar o consumer específico que o mantém no pack.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — Citadel 2.7.1 físico confirmado; library/consumer contract, entity/render/pathfinding/collision surfaces, surface-rules regression fix, side/lifecycle e version drift confirmados no QC global #94. Runtime QA não executado.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `citadel-2.7.1-1.21.1.jar`, mod id `citadel`, runtime `2.7.1`, NeoForge 1.21.1. Citadel é **library**, e a 2.7.1 corrige uma interferência concreta em surface rules de outros mods.

## 1. Papel e authority

Citadel fornece infraestrutura compartilhada para mods consumidores, especialmente em superfícies de entidades/animação/rendering e utilities relacionadas. O consumer continua authority do mob, item, AI, spawn, loot ou sistema final que usa a library.

Não atribuir conteúdo de Alex's Mobs/Cataclysm/outros consumers ao Citadel apenas porque eles dependem de sua API.

## 2. Contrato de dependência

A release oficial 2.7.1 não declara hard dependencies externas próprias. A necessidade de Citadel é **consumer-driven**:

- não remover enquanto houver consumer que exija sua API;
- não substituir por GeckoLib/AzureLib apenas por semelhança temática;
- atualizar junto com consumers sensíveis a ABI/mixins/hooks.

## 3. Entity/render infrastructure

O changelog 2.7.1 menciona explicitamente ajustes em **rendering**, além de correção de iluminação de entidades em book UI. Isso comprova que Citadel possui surface area client/render relevante.

Gameplay state de entidades continua server/common; render/model/lighting são apresentação e não podem virar authority de AI/damage.

## 4. Pathfinding e collision

A 2.7.1 também ajusta **pathfinding** e **collision logic**. Essas superfícies afetam comportamento observável de entities e podem interagir com mobs customizados ou outros mods que mexem nas mesmas rotas.

Não hardcodar que o tweak melhora/piora um mob específico sem teste do consumer real.

## 5. Surface rules — fix crítico 2.7.1

O changelog confirma fix de Citadel **breaking other mod's surface rules**. Em modpack com muitos worldgen providers, esse é um regression gate importante.

Citadel não deve assumir authority de surface generation de outros mods; qualquer hook/mixin seu precisa coexistir sem sobrescrever rules alheias.

## 6. Books/entity lighting

A release corrige iluminação de entidades em book rendering. Essa superfície é client presentation; ausência/erro de luz em preview não deve alterar estado da entidade real no mundo.

Resource reload e mudanças de shaders/render pipeline devem ser testadas sem classloading indevido no dedicated server.

## 7. Distinção do port não-oficial

O banco possui histórico separado de **Citadel (Unofficial Port)**, removido. O JAR atual `citadel-2.7.1-1.21.1.jar` é a entrada oficial corrente e deve permanecer semanticamente separado.

Não fundir decisões, versões ou histórico entre os dois artefatos.

## 8. Client/server

Citadel é Client & Server. Regras:

- entity/pathfinding/collision state que afeta gameplay pertence ao servidor/common;
- rendering/book lighting pertence ao cliente;
- consumers não podem carregar renderer em dedicated server;
- packets/sync usados por consumers devem convergir para uma única authority de entity state.

## 9. Lifecycle

Validar mod construction, consumer registry/bootstrap, world join, entity spawn/despawn, chunk unload/reload, datapack/resource reload e server restart.

Library caches/hooks não devem sobreviver incorretamente entre mundos nem duplicar registration de consumer.

## 10. Version drift

Sintomas de incompatibilidade podem incluir linkage errors, mixin failure, entity rendering crash, collision/pathfinding regressions ou worldgen interference. Diagnóstico precisa identificar **consumer + Citadel version + fase do lifecycle**.

Não classificar todo erro de um consumer como bug do Citadel sem stack trace/ownership.

## 11. Riscos

1. Remover Citadel com consumer ativo.
2. Atualização isolada quebrar ABI/mixin contract.
3. Regressão em surface rules/worldgen.
4. Pathfinding/collision hook competir com outro coremod.
5. Renderer/client class vazar para dedicated server.
6. Book/entity preview lighting falhar após resource/shader reload.
7. Confundir port não-oficial removido com a entrada oficial atual.

## 12. Matriz de testes

1. Dedicated server boot com todos consumers presentes.
2. Client boot e world join.
3. Spawn/AI/pathfinding de consumers Citadel selecionados.
4. Collision em mobs grandes/complexos e chunk borders.
5. Worldgen/surface rules de outros providers em chunks novos.
6. Book/entity previews e resource reload.
7. Restart/reconnect sem registry/cache duplication.
8. Logs sem mixin/linkage errors de Citadel/consumers.
9. Atualização futura: smoke-test dos consumers antes de aceitar nova build.

## 13. Evidência

- modlist física atual: Citadel 2.7.1;
- CurseForge oficial: lightweight library, Client & Server, sem dependencies próprias declaradas;
- changelog 2.7.1: surface rules fix, book entity lighting fix e tweaks de pathfinding/collision/rendering;
- histórico do catálogo mantém o antigo port não-oficial como entrada separada/removida.

> 🏛️ Boundary canônico: Citadel fornece **infraestrutura**. Conteúdo, AI e progressão permanecem sob authority dos mods consumidores.
