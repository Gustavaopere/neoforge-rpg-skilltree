# JourneyMap

## Propriedades do registro

- **Mod:** JourneyMap
- **Arquivo JAR:** journeymap-neoforge-1.21.1-6.0.8.jar
- **Versão 1.21.1:** 1.21.1-6.0.8
- **Categoria:** QoL, Exploração
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://modrinth.com/plugin/journeymap/version/IAVJBO0C
- **Função:** Sistema de mapa/minimapa client-side com exploração automática, mapa em tela cheia, waypoints e API v2 para overlays/integrações; o servidor pode controlar permissões de recursos como teleport.
- **Dependências:** Java 21, Minecraft 1.21.1 e NeoForge 21.1.228+ segundo a linha 6.0.x. Pack usa NeoForge 21.1.250. O JAR físico 6.0.8 embarca via JarJar Common Networking 1.0.21-1.21.1, JourneyMap API 2.0.0-1.21.1 e PNGJ 2.1.0; nenhum é entrada top-level separada.
- **Compatibilidade/Riscos:** Mapa de alta integração. Riscos: dados locais stale, waypoint/dimension mismatch, API plugin drift, radar/teleport divergente das permissões server-side, tile cache após mudanças de mundo e addons sobrepondo overlays. 6.0.7 corrige dois bugs de teleport admin/creative.
- **Sobreposição:** É o mapa/minimapa completo identificado no pack. JourneyMap Integration e outros addons ampliam seus overlays/API; não são mapas concorrentes. Não confundir informação descoberta no mapa com worldgen authority.
- **Observações:** Runtime físico atual é 6.0.8. A release oficial JourneyMap 6.0.8 NeoForge 1.21.1 foi publicada em 09/09/2026 e está instalada. O changelog específico da 6.0.8 não foi pinado nesta auditoria, portanto nenhum fix novo foi inventado.
- **Procedência:** modlist(1).txt física anexada e reconferida em 25/09/2026 + JourneyMap 6.0.8 NeoForge 1.21.1 físico + documentação 6.0.x + inventário físico JarJar do host confirmando Common Networking 1.0.21, JourneyMap API 2.0.0 e PNGJ 2.1.0.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 25/09/2026 — JourneyMap 1.21.1-6.0.8/JAR físico reconfirmado; o antigo gap 6.0.7→6.0.8 foi encerrado pela modlist atual. JarJar físico completo preservado: Common Networking 1.0.21, JourneyMap API 2.0.0 e PNGJ 2.1.0.
- **Data da última decisão:** 2026-08-30

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #352: JAR `journeymap-neoforge-1.21.1-6.0.8.jar`, mod id `journeymap`, runtime `1.21.1-6.0.8`, SHA-1 `e964f91ee6319bb1d789a7011e30772907962b90`.

<callout icon="🧭" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `journeymap-neoforge-1.21.1-6.0.8.jar`, mod id `journeymap`, versão `1.21.1-6.0.8`. A linha 6.0.x exige Java 21, Minecraft 1.21.1 e NeoForge 21.1.228+; o pack usa NeoForge **21.1.250**.
</callout>
## 1. Papel e authority
JourneyMap é o provider do mapa explorado, minimap, mapa em tela cheia, waypoints e API de integração. Ele **não gera terreno** e não é authority de biomas, estruturas ou entidades; representa visualmente o mundo que o cliente observa e dados autorizados pelo servidor/providers.
## 2. Superfícies principais
A documentação 6.0.x cobre minimap, full-screen map, markers, waypoints, opções de exibição e mapeamento automático conforme o jogador explora. O mapa em tela cheia é scroll/zoom e permite criar waypoints sobre posições conhecidas.
## 3. Waypoints
Waypoints possuem coordenadas/dimensão, visibilidade e apresentação próprias. Podem ser criados, editados, agrupados, compartilhados e removidos. Teleport para waypoint só deve ocorrer quando permitido pela authority do servidor; existência do waypoint não concede permissão.
## 4. API v2 e dependências embarcadas
JourneyMap 6.0 usa a API v2. O JAR físico 6.0.8 contém, em `META-INF/jarjar/`, `journeymap-api-neoforge-2.0.0-1.21.1.jar`, `common-networking-neoforge-1.0.21-1.21.1.jar` e `pngj-2.1.0.jar`. Os três componentes — JourneyMap API 2.0.0, Common Networking 1.0.21 e PNGJ 2.1.0 — são **dependências internas do host**, não três mods top-level adicionais.
## 5. Overlays de integrações
A API permite que mods desenhem markers, polygons, imagens e outros overlays no minimap/full-screen map. JourneyMap Integration 1.9 usa essa superfície para FTB Chunks/Waystones. O addon continua owner apenas da tradução; o dado original pertence ao provider.
## 6. Map state e descoberta
Áreas são mapeadas conforme exploração/observação. O conteúdo visual salvo é convenience/client state e pode ficar desatualizado após worldgen/config changes em regiões antigas. Não usar tile conhecido como prova de que uma estrutura/bioma ainda existe no state atual do servidor.
## 7. Radar e informação de entidades
JourneyMap pode apresentar markers de jogadores/mobs conforme configuração e permissões. Isso é informação visual; spawn, posição e existência continuam server-authoritative. Servidor pode restringir recursos de radar.
## 8. Teleport e permissões
A 6.0.7 corrige dois bugs exatos: o dropdown de Teleport ausente das server admin settings e teleport indevidamente bloqueado para creative/singleplayer quando a opção estava configurada para Ops. Teleport deve obedecer à configuração server-side final.
### Runtime atual — 6.0.8
A modlist física atual contém **JourneyMap 6.0.8 para NeoForge 1.21.1**. O antigo gap 6.0.7 → 6.0.8 está encerrado. JourneyMap Integration 1.9 continua sendo o principal version gate operacional para overlays; como o changelog específico da 6.0.8 não foi pinado nesta execução, nenhum fix adicional é atribuído à nova build.
## 9. Client / server
A interface/mapeamento é predominantemente client-side, mas JourneyMap também possui integração server-side para permissões/recursos compartilhados. O cliente não deve usar mapa/waypoint como autoridade para mover jogador, alterar claim ou modificar world state.
## 10. Lifecycle
Validar cold boot, join/reconnect, first mapping, chunk exploration, dimension change, resource reload, waypoint create/edit/delete, server setting changes e update de addons/API. Map caches e overlays precisam reconstruir corretamente o contexto do mundo/servidor.
## 11. Integrações concretas no pack
- **JourneyMap Integration 1.9:** FTB Chunks e Waystones.
- **FTB Chunks 2101.1.22:** claims exibíveis por addon.
- **Waystones 21.1.45:** markers/integrações condicionais.
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
- [ ] Cliente e dedicated server iniciam com JourneyMap 6.0.8/NeoForge 21.1.250.
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
- **Modlist física:** JourneyMap 6.0.8, mod id/version e JarJars `common-networking-neoforge-1.0.21-1.21.1`, `journeymap-api-neoforge-2.0.0-1.21.1` e `pngj-2.1.0`.
- **Release oficial 6.0.7:** requisitos, API e dois fixes de teleport.
- **Documentação 6.0.x:** minimap, full-screen map, waypoints e integração por API.
- **Limite:** configurações locais, radar policy e dados de mapa do usuário não foram inspecionados.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
