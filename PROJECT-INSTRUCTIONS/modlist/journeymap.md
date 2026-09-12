# JourneyMap

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db819b8bcbd8a7a63b4a06
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — core, API JarJar e integrações relevantes confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** JourneyMap
- **Arquivo JAR:** `journeymap-neoforge-1.21.1-6.0.7.jar`
- **Versão 1.21.1:** 1.21.1-6.0.7
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL, Exploração
- **Função:** Sistema de mapa/minimapa client-side com exploração automática, mapa em tela cheia, waypoints e API v2 para overlays/integrações; o servidor pode controlar permissões de recursos como teleport.
- **Dependências:** Java 21, Minecraft 1.21.1 e NeoForge 21.1.228+ segundo a release 6.0.7. Pack usa NeoForge 21.1.248. A própria distribuição contém JourneyMap API 2.0.0-1.21.1 como JarJar interno, não como top-level separado.
- **Sobreposição:** É o mapa/minimapa completo identificado no pack. JourneyMap Integration e outros addons ampliam seus overlays/API; não são mapas concorrentes. Não confundir informação descoberta no mapa com worldgen authority.
- **Compatibilidade/Riscos:** Mapa de alta integração. Riscos: dados locais stale, waypoint/dimension mismatch, API plugin drift, radar/teleport divergente das permissões server-side, tile cache após mudanças de mundo e addons sobrepondo overlays. 6.0.7 corrige dois bugs de teleport admin/creative.
- **Observações:** Release 6.0.7: corrige Teleport dropdown ausente das server admin settings e teleport bloqueado para creative/singleplayer quando opção do servidor estava em Ops. Implementa JourneyMap API v2.0.0-1.21.1. Webmap é addon separado em JourneyMap 6.0 e não foi presumido instalado.
- **Procedência:** modlist.txt física atual + Modrinth/CurseForge oficiais JourneyMap 1.21.1-6.0.7 NeoForge + documentação JourneyMap 6.0.x + JarJar físico JourneyMap API 2.0.0-1.21.1 observado na modlist.
- **Fonte:** https://modrinth.com/plugin/journeymap/version/IAVJBO0C
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — JourneyMap 1.21.1-6.0.7 release-pinned; minimap/fullscreen map, waypoints, API v2/JarJar, server permission boundary, 6.0.7 teleport fixes, lifecycle, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-30

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `journeymap-neoforge-1.21.1-6.0.7.jar`, mod id `journeymap`, versão `1.21.1-6.0.7`. A release exige Java 21, Minecraft 1.21.1 e NeoForge 21.1.228+; o pack usa NeoForge **21.1.248**.

## 1. Papel e authority
JourneyMap é o provider do mapa explorado, minimap, mapa em tela cheia, waypoints e API de integração. Ele **não gera terreno** e não é authority de biomas, estruturas ou entidades; representa visualmente o mundo que o cliente observa e dados autorizados pelo servidor/providers.

## 2. Superfícies principais
A documentação 6.0.x cobre minimap, full-screen map, markers, waypoints, opções de exibição e mapeamento automático conforme o jogador explora. O mapa em tela cheia é scroll/zoom e permite criar waypoints sobre posições conhecidas.

## 3. Waypoints
Waypoints possuem coordenadas/dimensão, visibilidade e apresentação próprias. Podem ser criados, editados, agrupados, compartilhados e removidos. Teleport para waypoint só deve ocorrer quando permitido pela authority do servidor; existência do waypoint não concede permissão.

## 4. API v2
JourneyMap 6.0 usa a API v2. A release 6.0.7 declara **JourneyMap API v2.0.0-1.21.1**. A modlist física mostra `journeymap-api-neoforge-2.0.0-1.21.1.jar` dentro de `META-INF/jarjar/` do próprio JourneyMap: é dependência embutida, **não mod top-level adicional**.

## 5. Overlays de integrações
A API permite que mods desenhem markers, polygons, imagens e outros overlays no minimap/full-screen map. JourneyMap Integration 1.9 usa essa superfície para FTB Chunks/Waystones. O addon continua owner apenas da tradução; o dado original pertence ao provider.

## 6. Map state e descoberta
Áreas são mapeadas conforme exploração/observação. O conteúdo visual salvo é convenience/client state e pode ficar desatualizado após worldgen/config changes em regiões antigas. Não usar tile conhecido como prova de que uma estrutura/bioma ainda existe no state atual do servidor.

## 7. Radar e informação de entidades
JourneyMap pode apresentar markers de jogadores/mobs conforme configuração e permissões. Isso é informação visual; spawn, posição e existência continuam server-authoritative. Servidor pode restringir recursos de radar.

## 8. Teleport e permissões
A 6.0.7 corrige dois bugs exatos: o dropdown de Teleport ausente das server admin settings e teleport indevidamente bloqueado para creative/singleplayer quando a opção estava configurada para Ops. Teleport deve obedecer à configuração server-side final.

## 9. Client / server
A interface/mapeamento é predominantemente client-side, mas JourneyMap também possui integração server-side para permissões/recursos compartilhados. O cliente não deve usar mapa/waypoint como autoridade para mover jogador, alterar claim ou modificar world state.

## 10. Lifecycle
Validar cold boot, join/reconnect, first mapping, chunk exploration, dimension change, resource reload, waypoint create/edit/delete, server setting changes e update de addons/API. Map caches e overlays precisam reconstruir corretamente o contexto do mundo/servidor.

## 11. Integrações concretas no pack
- **JourneyMap Integration 1.9:** FTB Chunks e Waystones.
- **FTB Chunks 2101.1.22:** claims exibíveis por addon.
- **Waystones 21.1.44:** markers/integrações condicionais.
- Outros mods podem usar API v2; presença deve ser confirmada pelo addon correspondente, não inferida por compatibilidade genérica.

## 12. Riscos técnicos
- waypoint em dimensão errada;
- tile/cache stale após mudança de mundo;
- overlay duplicado ou órfão;
- plugin API drift;
- teleport UI divergir de server permission;
- radar revelar informação além do pretendido pela configuração;
- dados de mapa confundidos com worldgen state authoritative;
- addon client-side sobreviver a reconnect com cache antigo.

## 13. Matriz de testes obrigatória
- [ ] Cliente e dedicated server iniciam com JourneyMap 6.0.7/NeoForge 21.1.248.
- [ ] Minimap e full-screen map carregam e mapeiam chunks explorados.
- [ ] Waypoint create/edit/delete persiste no mundo correto.
- [ ] Waypoint não teleporta quando server policy proíbe.
- [ ] Server admin Teleport dropdown aparece — regressão 6.0.7.
- [ ] Creative/singleplayer respeita o fix de teleport da 6.0.7.
- [ ] Dimension change não mistura tiles/waypoints.
- [ ] JMI overlays registram sem duplicate/ghost markers.
- [ ] Reconnect para outro servidor não reaproveita state incorreto.
- [ ] API v2 addons continuam carregando após update.

## 14. Evidências e limites
- **Modlist física:** JAR/mod id/version e JourneyMap API 2.0.0-1.21.1 JarJar.
- **Release oficial 6.0.7:** requisitos, API e dois fixes de teleport.
- **Documentação 6.0.x:** minimap, full-screen map, waypoints e integração por API.
- **Limite:** configurações locais, radar policy e dados de mapa do usuário não foram inspecionados.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
