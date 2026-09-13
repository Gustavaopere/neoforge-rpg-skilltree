# ImmediatelyFast

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81978364e04d99418783
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** ImmediatelyFast
- **Arquivo JAR:** `ImmediatelyFast-NeoForge-1.6.13+1.21.1.jar`
- **Versão 1.21.1:** 1.6.13+1.21.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Performance
- **Função:** Otimização client-side de immediate-mode rendering via batching e upload mais eficiente para GPU, abrangendo entities, block entities, particles, text, GUI/HUD e render imediato de outros mods.
- **Dependências:** Cliente NeoForge 1.21.1. O JAR físico embarca Reflect 1.6.2 como dependência JarJar interna, não mod top-level. Source branch 1.21 atual usa NeoForge 21.1.221/Java 21, mas já avançou para 1.6.14-SNAPSHOT.
- **Sobreposição:** Complementa culling/LOD/distance optimizations; não substitui Entity Culling, Distant Horizons ou redução de distância. Atua principalmente no custo de submissão/render imediato do cliente.
- **Compatibilidade/Riscos:** Client render optimization. Riscos: shader/render-state leakage, mixin/render-pipeline drift, config incompatível, atlas/buffer issues e composição com outros render mods. Upstream lista OptiFine/OptiFabric, VulkanMod e alguns closed-source clients como incompatíveis.
- **Observações:** 1.6.13 restaura depth-test após flush de DrawContext, corrige exception ao fechar buffers não usados e corrige file-handle leak da config. Nested Reflect 1.6.2 corresponde ao build dependency e não recebe página top-level.
- **Procedência:** modlist.txt física atual + CurseForge oficial ImmediatelyFast 1.6.13 file 8749421 + changelog exato 1.6.13 + GitHub RaphiMC/ImmediatelyFast branch 1.21 usado apenas para arquitetura porque já está em 1.6.14-SNAPSHOT.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/immediatelyfast/files/8749421
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — ImmediatelyFast 1.6.13 release-pinned; immediate-mode batching/GPU upload, render domains, config, Reflect 1.6.2 JarJar, regressões 1.6.13, client lifecycle, riscos e testes catalogados; source branch atual já está em 1.6.14-SNAPSHOT.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `ImmediatelyFast-NeoForge-1.6.13+1.21.1.jar`, mod id `immediatelyfast`, versão `1.6.13+1.21.1`. A release oficial NeoForge 1.21.1 é file 8749421 de 27/08/2026. O branch source `1.21` já avançou para `1.6.14-SNAPSHOT`, por isso arquitetura pode ser consultada ali, mas a release/changelog 1.6.13 é authority do comportamento version-sensitive.

## 1. Papel e authority
ImmediatelyFast é mod **client-side** de performance gráfica. Ele otimiza immediate-mode rendering e caminhos específicos de render vanilla/modded. Não altera gameplay authority, world state, AI, recipes ou server tick. O servidor não deve depender de seus resultados visuais.

## 2. Arquitetura de batching
O projeto documenta uma implementação custom de buffers que agrupa draw calls e envia dados à GPU de forma mais eficiente. O ganho é maior quando o gargalo está no CPU/render submission. Não tratar benchmarks históricos do autor como garantia de FPS no pack atual; eles são exemplos de workloads específicos.

## 3. Domínios otimizados
A documentação upstream enumera immediate-mode rendering de **entities, block entities, particles, text, GUI/HUD e outros mods**. Também existem otimizações específicas para map rendering, HUD e text. Isso torna mudanças em buffer lifecycle e render state boundaries críticas para regressão visual.

## 4. Release 1.6.13 — DrawContext depth state
A 1.6.13 restaura o **depth-test state** depois de flush do DrawContext, corrigindo problema gráfico observado com Map Atlases. Qualquer HUD/map/GUI que manipule depth deve ser testado para garantir que um flush otimizado não vaze estado para o próximo draw pass.

## 5. Release 1.6.13 — buffers
A release corrige uma exceção ao fechar buffers não utilizados, associada a crash observado em ATM10. O lifecycle de buffers deve encerrar sem double-close/use-after-close e sem manter buffers órfãos após resource reload ou screen transitions.

## 6. Release 1.6.13 — config handle leak
A 1.6.13 também corrige vazamento de file handle do arquivo de configuração. Reabrir/recarregar config ou reiniciar o cliente não deve deixar handles acumulados. Em Windows, isso também é relevante para possibilidade de editar/substituir o arquivo enquanto o jogo não o mantém indevidamente aberto.

## 7. Configuração
ImmediatelyFast mantém arquivo de config na pasta `config`. O upstream recomenda manter a maioria das opções nos defaults e alterar apenas por necessidade de performance/compatibilidade. A documentação cita opções como `font_atlas_resizing` e `map_atlas_generation`; integrações não devem assumir defaults ou lista completa sem ler a config efetivamente instalada.

## 8. Reflect 1.6.2 JarJar
O JAR físico embarca `Reflect-1.6.2.jar` em `META-INF/jars`. O branch 1.21 também referencia Reflect 1.6.2. É dependência interna do host, **não mod top-level** e não recebe posição própria na modlist. Atualizar ImmediatelyFast pode trocar essa biblioteca sem aparecer como novo JAR físico externo.

## 9. Mixins e render pipeline
O JAR físico declara mixins common e NeoForge. Esses patches atingem caminhos sensíveis de render; update de Minecraft/NeoForge ou outro mod de render pode alterar targets/invariants. Um crash em render deve ser atribuído por stack trace/mixin target real, não apenas pela coexistência de vários mods gráficos.

## 10. Compatibilidade upstream
O projeto procura interferir pouco com outros mods, mas lista incompatibilidades conhecidas com **OptiFine/OptiFabric, VulkanMod e vários clients closed-source**. Essa lista é upstream e não implica que qualquer outro shader/render mod seja automaticamente compatível; o stack físico deve ser smoke-tested.

## 11. Relação com culling/LOD
ImmediatelyFast não é culling nem LOD. Entity Culling decide o que não renderizar; Distant Horizons muda representação de distância; ImmediatelyFast reduz custo de submissão/render de coisas que continuam sendo desenhadas. Os três podem coexistir por atuarem em camadas diferentes, sujeito a compatibilidade real.

## 12. Client / server
A distribuição é **Client**. Não usar presença/ausência do mod como condição de gameplay server-side. Config, buffers, atlas, HUD, text e render state pertencem ao cliente. Em servidor dedicado, a ausência dessa otimização é esperada e não deve alterar save/progressão.

## 13. Lifecycle
Validar client boot, world join/leave, resource reload, shader reload/toggle quando aplicável, screen transitions, map/HUD-heavy scenes, entity-heavy scenes e client shutdown. Buffer/state caches precisam ser recriados/descartados sem leaks ou state residual.

## 14. Riscos técnicos
- render state/depth vazando entre passes;
- mixin target drift após update de render stack;
- buffer lifecycle exception/double-close;
- config file handle leak regressar;
- atlas generation interagir mal com resource packs/fonts/maps;
- shader/mod de render manter assumptions incompatíveis;
- comparar benchmarks antigos com FPS do pack como se fossem garantias;
- catalogar Reflect 1.6.2 como mod top-level separado.

## 15. Matriz de testes obrigatória
- [ ] Cliente NeoForge inicia com ImmediatelyFast 1.6.13 e render stack atual.
- [ ] Join/leave de mundo não gera buffer/mixin errors.
- [ ] GUI/HUD/map após vários flushes mantém depth/order correto.
- [ ] Map Atlases/visualizações equivalentes não reproduzem regression corrigida.
- [ ] Muitas entities/block entities/particles renderizam sem corruption.
- [ ] Text/UI custom e resource packs sobrevivem a F3+T.
- [ ] Edit/reload/restart não deixa config file handle preso.
- [ ] Fechamento do cliente não produz exception de unused buffers.
- [ ] Shader/render mods físicos são validados por cena, sem assumir compat automática.
- [ ] Dedicated server não requer o mod nem recebe gameplay dependency indevida.

## 16. Evidências e limites
- **Modlist física:** JAR/version, mixins e Reflect 1.6.2 nested.
- **CurseForge oficial:** release 1.6.13 file 8749421 e três correções version-specific.
- **GitHub oficial:** branch 1.21 documenta arquitetura/optimizations/config/known incompatibilities, mas já está em 1.6.14-SNAPSHOT e não é byte pin da 1.6.13.
- **Limite:** benchmarks históricos não foram repetidos no hardware/modpack atual.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
