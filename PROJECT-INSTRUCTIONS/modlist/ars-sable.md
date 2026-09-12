# Ars Sable

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db8172b2e4e61ba71ae851
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Ars Sable
- **Arquivo JAR:** `ars_sable-1.21.1-1.1.2.jar`
- **Versão 1.21.1:** 1.1.2
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Magia, Compat, QoL
- **Função:** Bridge espacial Ars Nouveau↔Sable: adapta Source Jar/SourceManager, Storage Lectern, pathfinding/logística, Mob Jar, Planarium, warp portals/scrolls, projectiles, scrying/camera e renderização a sublevels/physics objects.
- **Dependências:** Ars Nouveau 5.13.1 + Sable 2.0.5 físicos. Metadata da bridge aceita ranges abertos, mas a build 1.1.2 foi desenvolvida contra Sable 1.2.2; compatibilidade comportamental 2.x permanece pendente de runtime QA.
- **Sobreposição:** Bridge específica Ars↔Sable. Não substitui Ars Nouveau nem Sable e não cria Source/physics próprios. Evitar dupla projeção espacial com outras bridges e preservar provider-native ownership.
- **Compatibilidade/Riscos:** RISCO ACEITO/VALIDAÇÃO OBRIGATÓRIA: Ars Sable 1.1.2 foi compilado contra Sable 1.2.2 e usa 29 mixins dirigidos a internals; o pack usa Sable 2.0.5. Validar startup, Source/storage, warp, Planarium, pathfinding, render/client boundary e assembly lifecycle; fail-closed para contracts não testados.
- **Observações:** mod id `ars_sable`. Release 1.1.2 possui 24 mixins common + 5 client-only e GameTests para Planarium, Storage Lectern, tracked positions e Warp Portal. Não registra gameplay blocks próprios na build auditada; é bridge de infraestrutura.
- **Procedência:** modlist.txt física atual de 11/09/2026 + source ars-sable commit `1fd83f3a998e3a41b5a21d0d6529140a0b0a55ba` (1.1.2) + Sable 2.0.5 físico e fontes já auditadas no dossiê. Reconciliação final: JAR/runtime do addon permanecem exatamente `ars_sable-1.21.1-1.1.2.jar` / `1.1.2`; compatibilidade comportamental com Sable 2.0.5 continua pendente de runtime QA.
- **Fonte:** https://github.com/baileyholl/ars-sable
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #49: `ars_sable-1.21.1-1.1.2.jar` / `1.1.2` conferidos contra a modlist atual; risco Sable build-target 1.2.2 → runtime 2.0.5 preservado, sem aprovação automática de compatibilidade.
- **Histórico da decisão:** Manter com risco aceito/validação obrigatória. Em 07/09/2026 a ficha foi reconstruída contra o commit exato 1.1.2; metadata aceita Sable >=1.0, mas o salto do build target 1.2.2 para runtime físico 2.0.5 não foi tratado como prova automática de compatibilidade.
- **Data da última decisão:** 2026-09-07

> 🧭 **PADRÃO ALEX'S MOBS — DOSSIÊ OPERACIONAL EXAUSTIVO.** Runtime físico: `ars_sable-1.21.1-1.1.2.jar`, mod id `ars_sable`, NeoForge 1.21.1. A auditoria está fixada no commit upstream **`1fd83f3a998e3a41b5a21d0d6529140a0b0a55ba`**, cujo `gradle.properties` declara `1.1.2`. Ars Sable é uma **bridge espacial Ars Nouveau ↔ Sable**: projeta posições entre sublevels e mundo, preserva conexões e adapta storage, Source, warp, Planarium, pathfinding, projectiles, Mob Jar e renderização. Não adiciona Source paralelo nem physics engine próprio.

## 1. Identidade e version pin
- **Mod:** Ars Sable.
- **JAR:** `ars_sable-1.21.1-1.1.2.jar`.
- **Runtime:** `1.1.2`.
- **Mod id:** `ars_sable`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Commit upstream exato:** `1fd83f3a998e3a41b5a21d0d6529140a0b0a55ba`.
- **Decisão:** **Manter**, com validação obrigatória de compatibilidade contra o Sable físico atual.

## 2. Dependências e versão real do stack
A build 1.1.2 foi desenvolvida contra:
- Ars Nouveau `5.11.7.1354`;
- Sable `1.2.2`.

O metadata da própria release declara ranges:
- Ars Nouveau `[5.11.7,)`;
- Sable `[1.0,)`;
- Minecraft `[1.20.6,1.21.2)`.

No pack atual:
- Ars Nouveau = **5.13.1**, dentro do range;
- Sable = **2.0.5**, também aceito formalmente pelo range `[1.0,)`, mas muito além do build target 1.2.2.

Portanto o loader pode aceitar o stack, porém **compatibilidade binária/comportamental com Sable 2.0.5 exige runtime test**; não é correto transformar o range aberto em prova de comportamento perfeito.

## 3. Conteúdo próprio
O `ModBlockRegistry` 1.1.2 cria registries de blocks, block entities e items, mas `onBlockItemsRegistry()` está vazio e a árvore não registra uma linha de gameplay própria. O valor do addon está nos adapters, mixins, tracking data e networking.

Consequência: Ars Sable é **bridge**, não provider de conteúdo mágico nem físico independente.

## 4. Mixin footprint — 29 pontos direcionados
A configuração 1.1.2 registra **24 mixins common + 5 client-only**, organizados por domínio.

### Common — 24
**Assembly/Sable**
- `SubLevelAssemblyHelperMixin`

**Camera/tracking comum**
- `ICameraMountableMixin`
- `ScryerCameraMixin`
- `SubLevelTrackingSystemMixin`

**Entities/projectiles**
- `EntityFlyingItemMixin`
- `BookwyrmSublevelTrackingMixin`
- `EntityFollowProjectileMixin`
- `WhirlisprigSublevelTrackingMixin`
- `WixieSublevelTrackingMixin`

**Mob Jar**
- `MobJarTileMixin`

**Pathfinding/storage goals**
- `AbstractPathJobMixin`
- `AdvancedNavigateMixin`
- `BookwyrmRandomStorageVisitGoalMixin`
- `BookwyrmTransferGoalMixin`

**Planarium**
- `DimBoundaryMixin`
- `PlanariumTileMixin`

**Source**
- `SourceJarMixin`

**Storage**
- `CraftingLecternBlockMixin`
- `StorageLecternTileMixin`

**Spatial utilities**
- `BlockPosUtilMixin`
- `ParticleUtilMixin`

**Warp**
- `PortalTileMixin`
- `StableWarpScrollMixin`
- `WarpScrollMixin`

### Client-only — 5
- `CameraControllerMixin`
- `ClientHandlerMixin`
- `ArcanePedestalRendererMixin`
- `MobJarRendererMixin`
- `PlanariumRendererMixin`

A separação client/common é crítica para dedicated server.

## 5. Sublevel observer e lifecycle
Quando `ForgeSableSubLevelContainerReadyEvent` dispara, Ars Sable adiciona um `SableSublevelObserver` ao container. A bridge observa lifecycle dos sublevels para atualizar posições/tracking usados pelos sistemas Ars.

Integrações próprias não devem manter um segundo mapa espacial paralelo sem razão comprovada.

## 6. Source Jar em sublevels
`SourceJarMixin` faz `SourceJarTile` implementar `BlockEntitySubLevelActor`.

Durante tick do sublevel:
- cria/renova um `SableSourceProvider` quando ausente/inválido;
- registra esse provider no `SourceManager` do Ars;
- o provider considera válido o Source tile quando Sable identifica que o block entity está contido em objeto/sublevel;
- `getCurrentPos()` projeta a posição do jar **para fora do sublevel** usando `Sable.HELPER.projectOutOfSubLevel`.

Resultado: Source continua sendo o Source real do Ars; somente sua posição espacial é adaptada.

## 7. Source authority
`SableSourceProvider` implementa `ISpecialSourceProvider`, devolvendo o mesmo `ISourceTile` Ars. Portanto:
- não há novo tanque de Source;
- não há conversão para energia Sable/Create;
- não há cópia de saldo;
- a bridge só permite que o `SourceManager` enxergue corretamente o provider móvel.
Qualquer addon que copie Source para estado próprio cria risco de dupe.

## 8. Storage Lectern e posições persistentes
`StorageLecternTileMixin` torna o lectern um `TrackedWorldPositionBlockEntity` e adiciona um UUID persistente `ars_sable_tracking_id`.

Fluxos confirmados:
- salva/carrega o tracking UUID em NBT;
- restaura posições rastreadas em `onLoad`;
- sincroniza após conexão inicial/final e após adicionar handler;
- acompanha `mainLecternPos` + handlers conectados;
- quando uma posição muda, substitui o endereço antigo pelo novo;
- recria `BlockCapabilityCache<ItemHandler>` no novo local;
- limpa `transferTasks` stale;
- força `updateItems`/invalidation;
- deduplica handler positions.

Isso permite que storage conectado sobreviva à transformação mundo↔sublevel e ao movimento físico sem continuar apontando para coordenadas antigas.

## 9. Bookwyrm e logística
A build contém adaptações específicas para:
- Bookwyrm sublevel tracking;
- random storage visit goal;
- transfer goal;
- path job/navigator avançado.

O objetivo é manter logística/visitação de storage coerente quando destinos estão em espaço Sable. Não assumir que qualquer mob arbitrário ganha pathfinding cross-sublevel; o suporte é direcionado.

## 10. Whirlisprig e Wixie tracking
Existem mixins dedicados a Whirlisprig e Wixie para sublevel tracking. Isso confirma que automação desses mobs recebe adaptação específica, mas não autoriza afirmar suporte automático a toda entidade de addon Ars.

## 11. Starbuncle/pathfinding
A documentação do projeto descreve correção de pathfinding de Starbuncles em espaços físicos. No source 1.1.2, a camada genérica de path navigation (`AbstractPathJobMixin`/`AdvancedNavigateMixin`) compõe esse suporte. Em teste do pack, validar Starbuncle especificamente, além de Bookwyrm/Whirlisprig/Wixie.

## 12. Warp Portal — projeção real de coordenadas
`PortalTileMixin` intercepta o Portal Ars em três pontos:
1. ao configurar portal por Warp Scroll, registra o destino global para tracking;
2. teleport no mesmo dimension projeta `(x,y,z)` pela helper Sable antes de `Entity.teleportTo`;
3. cross-dimension recria `DimensionTransition` com posição projetada;
4. o `PacketWarpPosition` enviado ao cliente também recebe coordenadas projetadas.

Isso mantém server teleport e client correction na mesma posição física.

## 13. Warp Scroll e Stable Warp Scroll
A build possui mixins separados para `WarpScroll` e `StableWarpScroll`, além de `WarpSublevelTargetData`. O objetivo é preservar/resolver destinos que foram vinculados em sublevel, inclusive quando esse espaço não está carregado na forma convencional.

O changelog 1.1.2 confirma especificamente a correção de **warp portals e scrolls que enviavam para localização incorreta quando o sublevel estava unloaded**.

## 14. Planarium e DimBoundary
Mixins em `PlanariumTile` e `DimBoundary`, `PlanariumHelpers`, tracking data e render mixin tornam vínculos espaciais do Planarium conscientes do sublevel.

O changelog 1.1.2 confirma outra mudança específica: **Planariums passam a persistir conexões vinculadas entre assembly de sublevel**.

Portanto esta é funcionalidade da release instalada, não inferência de versão futura.

## 15. Tracked position data
A árvore 1.1.2 possui estruturas persistentes próprias:
- `SublevelPosData`;
- `TrackedBlockEntityPosData`;
- `TrackedPosIndex`;
- `WarpSublevelTargetData`.

Elas armazenam correspondências/targets necessários para reendereçar objetos Ars quando a geometria Sable muda. Não são um segundo world-save de conteúdo Ars; guardam metadados de localização/bridge.

## 16. Mob Jar
`MobJarTileMixin` + `MobJarHelpers` adaptam Mob Jar a sublevels; o renderer possui mixin client-only separado. Isso evita tratar posição/render do conteúdo do jar como coordenada estática de mundo principal.

## 17. Projectiles e flying items
A release modifica:
- `EntityFlyingItem`;
- `EntityFollowProjectile`.

Helpers dedicados ajustam comportamento/tracking em espaço Sable. Isso é especialmente importante para spells/projectiles lançados de/para estruturas móveis: causalidade do spell continua Ars, enquanto posição/seguimento é adaptado à física espacial.

## 18. Scrying e câmera
A build contém suporte direcionado para câmera/scrying em sublevels:
- `ScryerCameraMixin`;
- `ICameraMountableMixin`;
- `SubLevelTrackingSystemMixin`;
- client `CameraControllerMixin`/`ClientHandlerMixin`;
- `ScryerHelpers` e `SublevelCamera`.

Câmera/render é client-side; target/posição real continuam dependentes do estado sincronizado do servidor/Sable.

## 19. Rendering em espaços físicos
Client-only mixins cobrem:
- Arcane Pedestal renderer;
- Mob Jar renderer;
- Planarium renderer.

`SublevelItemRenderHelper` auxilia transformação da posição visual. Isso corrige apresentação sem criar authority de gameplay no cliente.

## 20. Partículas e BlockPos utilities
`BlockPosUtilMixin` e `ParticleUtilMixin` adaptam utilitários que normalmente assumem coordenadas vanilla. Em sublevels móveis, uma partícula correta visualmente não prova que a operação server-side atingiu o alvo correto; ambos os caminhos precisam ser testados.

## 21. Assembly/disassembly
`SubLevelAssemblyHelperMixin` participa do ciclo de assembly do Sable. Como a release 1.1.2 também persiste tracked positions e Planarium links, assembly/disassembly é um lifecycle crítico:
- não perder vínculos;
- não duplicar Source/inventários;
- não conservar coordenadas antigas;
- não reexecutar operações já concluídas.

## 22. GameTests incluídos no source 1.1.2
A árvore da release inclui suites específicas:
- `PlanariumPosTests`;
- `StorageLecternTests`;
- `TrackedBlockEntityPosDataTests`;
- `WarpPortalTests`.

Esses testes upstream são evidência de quais áreas o próprio addon considera críticas. Não equivalem a CI do modpack atual; precisam ser complementados por smoke/integration contra Sable 2.0.5 e Ars 5.13.1.

## 23. Compatibilidade Sable 2.0.5 — estado correto
Fatos:
- build target Ars Sable 1.1.2: **Sable 1.2.2**;
- metadata aceita **Sable `[1.0,)`**;
- pack físico: **Sable 2.0.5**.

Conclusão operacional: **carregamento é permitido pelo metadata, mas compatibilidade funcional completa não está provada apenas por isso**. Como o addon usa vários mixins em internals espaciais, update major de Sable é superfície de risco real.

Estado: **Manter / validação obrigatória**, fail-closed para qualquer integração própria que dependa de comportamento não testado.

## 24. Authority e ownership
- **Ars Nouveau:** Source, SourceManager, Storage Lectern, Mob Jar, Planarium, Portal/Warp, entities/spells.
- **Sable:** sublevels, transforms, containment, physics object lifecycle.
- **Ars Sable:** projeção/tracking/adapters entre os dois.

Não duplicar Source, physics state, target coordinates nem position index em outro mod sem necessidade arquitetural comprovada.

## 25. Riscos
1. mixin incompatível com internals Sable 2.0.5;
2. stale tracked position após assembly/disassembly;
3. Source Jar duplicado ou invisível ao SourceManager;
4. Storage Lectern apontando para handler antigo;
5. warp para coordenada original em vez de posição projetada;
6. sublevel unloaded resolvido incorretamente;
7. Planarium perder vínculo após assembly/restart;
8. Bookwyrm/Starbuncle/Wixie/Whirlisprig navigation desync;
9. projectile/flying item seguir coordenadas erradas;
10. client renderer/camera carregado indevidamente em dedicated server;
11. duas bridges espaciais corrigindo a mesma posição duas vezes.

## 26. Matriz de validação do pack
- boot client + dedicated server com Ars Nouveau 5.13.1 + Ars Sable 1.1.2 + Sable 2.0.5;
- confirmar nenhum mixin failure no startup;
- Source Jar dentro de sublevel: geração/transfer/lookup;
- relay/jar mundo↔sublevel e sublevel↔sublevel quando suportado;
- Storage Lectern com handlers antes/depois de assembly;
- Bookwyrm transfer e random visit;
- Starbuncle pathfinding;
- Wixie/Whirlisprig tracking;
- Warp Portal mesmo dimension;
- Warp Portal cross-dimension;
- Warp Scroll + Stable Warp Scroll com target carregado e unloaded;
- Planarium bind, assembly, unload, reload e restart;
- Mob Jar dentro de espaço móvel;
- projectile/flying item entre espaços;
- Scryer camera em sublevel;
- render de Arcane Pedestal/Mob Jar/Planarium;
- death/logout/reconnect/dimension change quando há warp/camera ativos;
- server stop durante objeto montado;
- verificar ausência de Source/item dupe.

## 27. Fontes
- Modlist física do projeto, 07/09/2026: Ars Sable 1.1.2 e Sable 2.0.5.
- Guia consolidado de Magia do projeto.
- Source oficial `baileyholl/ars-sable`, commit exato `1fd83f3a998e3a41b5a21d0d6529140a0b0a55ba` (1.1.2).
- `gradle.properties`, `neoforge.mods.toml`, `ars_sable.mixins.json`, `SourceJarMixin`, `SableSourceProvider`, `StorageLecternTileMixin`, `PortalTileMixin`, `changelog.md` e suites GameTest da release.