# Create: Stats & Power

> **Autoridade física atual — 23/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#161**: JAR `create_stats-1.13.1.jar`, mod id `create_stats`, runtime `1.13.1`, SHA-1 `3f088d59ffc81ddc85c8dd62cecff0ce8e7e2271`.

## Propriedades do registro

- **Mod:** Create: Stats & Power
- **Arquivo JAR:** create_stats-1.13.1.jar
- **Versão 1.21.1:** 1.13.1
- **Categoria:** Tecnologia, Automação, QoL
- **Função:** Addon Create de telemetria e infraestrutura elétrica FE com displays/recorders/alarms, Power Distribution Units, cables/dynamos/motors/capacitors, factory logic e sistemas de controle veicular.
- **Dependências:** NeoForge + Create obrigatórios; pack físico atual usa Create 6.0.10. O source público atual também declara LambDynamicLights como required dependency para Stage Light; GeckoLib e JEI como integrações opcionais; Sable Companion embarcado via JarJar; integrações com Simulated/Create Aeronautics condicionais à presença real.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Adiciona grid FE próprio, PDU, vehicle control e monitoramento; pode sobrepor energy bridges e control systems. Preservar regression gates 1.5.1 de PDU/cables/UI. Upstream 1.14.5 adiciona Portable Power Interface e capacitor banks ativos em trens, além de fixes de routing, FE conservation, security/contraptions, freight sync e render caching.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-stats-power
- **Procedência:** modlist física atual + CurseForge oficial Create: Stats & Power 1.13.1/1.14.5 + source público Erbell9520/Create-Graphs-Additions-public. A autoridade instalada permanece `create_stats-1.13.1.jar`.
- **Observações:** Runtime físico atual 1.13.1. Na listagem oficial, a próxima release publicada após 1.13.1 é diretamente **1.14.5** em 17/09/2026; não há artefatos públicos intermediários 1.13.2/1.14.0–1.14.4 localizados. A 1.14.5 não está instalada.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — runtime físico permanece 1.13.1. A sequência pública foi conferida: o próximo artefato é diretamente **1.14.5**, sem releases públicas intermediárias localizadas; o changelog 1.14.5 foi incorporado integralmente abaixo.
- **Decisão:** Sem decisão
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Create: Stats & Power 1.5.1 foi reconfirmado como instalado; presença não foi convertida em decisão curatorial. Em 16/09/2026, a modlist física confirma create_stats-1.13.1.jar; a decisão permanece Sem decisão.
- **Sobreposição:** Monitoring, FE e vehicle control podem cruzar outros addons, mas o mod owns seu PDU/grid, displays e controllers. Comparar topology/rates e companion integrations antes de classificar redundância.

# Dossiê operacional — padrão Alex's Mobs
> 📊 **ESCOPO CANÔNICO ATUAL.** Versão física confirmada: `create_stats-1.13.1.jar`, runtime `1.13.1`, NeoForge 1.21.1. O projeto se apresenta como **Create: Stats & Power / Create Stats & Numbers** e combina monitoramento industrial, grid FE, lógica de fábrica, iluminação e controle veicular. O conteúdo funcional documentado em 1.5.1 continua preservado; os acréscimos abaixo refletem somente superfícies atuais verificáveis no source público e na instalação.

## 1. Papel e authority
Stats & Power controla seus displays, recorders, alarmes, grid FE, PDU, controllers e dispositivos. Create continua authority de kinetics/contraptions; companion mods continuam authority da física/veículo que os controllers observam ou comandam.
## 2. Grid FE
Cable, wire terminals, dynamos, motors e capacitor banks formam uma rede FE. Energia precisa ser contabilizada uma vez entre geração, storage, distribution e consumo; bridges externas não devem criar crédito/debito paralelo.
## 3. Power Distribution Unit
A **PDU** centraliza distribuição e prioridades. O modo Active Auto pode desligar cargas de menor prioridade quando geração é insuficiente. Priority/load shedding precisa ser server-authoritative e persistente.
## 4. PDU chaining — regressão preservada da 1.5.1
A 1.5.1 introduziu boards encadeadas: uma PDU pode virar device em branch de outra; power do back boss é contabilizado e dois controllers podem alimentar uma board. Topologia precisa evitar loops/double-counting. Como o pack agora está em 1.13.1, esse comportamento permanece regression gate, não “novidade atual”.
## 5. Chain page
A linha 1.5.1 adicionou uma página de cadeia na tela da PDU para mostrar upstream/downstream e starvation. Essa UI observa o grafo lógico; não deve alterar fluxo apenas por estar aberta.
## 6. Indoor Cable
A linha 1.5.1 permitiu Indoor Cable fazer os doze corner cases documentados e sneak-right-click para remover uma run completa. Geometry/render deve acompanhar a conexão lógica sem deixar segmentos fantasma no server.
## 7. Power Outlets e Smart Home Panel
Outlets aparecem individualmente no Smart Home Panel, com switches e rename de devices. Nome/UI não é identidade energética; o endpoint real deve continuar vinculando consumo e permissões corretamente.
## 8. Monitoring
O projeto inclui Stats Displays, Recorders, Alarms, Data Dials e Production Detectors. Há também battle screen/damage observer para monitoramento seccionado de ships/machines. Métricas devem observar state sem gerar um segundo processamento da máquina monitorada.
## 9. Factory logic e logistics
Factory Logic Controller, Dispatch Board, Load Balancer e Calculator adicionam decisão/logística. O source público atual também lista Freight Manifest Clipboard, Flow Meter e Overflow Gate. Commands/outputs precisam ser idempotentes em multiplayer e não executar a mesma transferência por dois controladores concorrentes.
## 10. Vehicles
Flight Control Computer, RPM Governor e cockpit console controlam veículos suportados. Path Scanner + Pathfinder Computer fornecem route guidance. Aeronautics/Simulated continuam owners da transformação/física; Stats & Power fornece sensores/controladores.
## 11. Bread Slicer e Toaster — regressão preservada da 1.5.1
A 1.5.1 reconstruiu o Bread Slicer e tornou o Toaster dependente de slices. O changelog publicou custos do Slicer por espessura: **20 FE/t Thick, 40 FE/t Medium, 80 FE/t Thin**. Esses valores são version-specific da release-base documentada e devem ser confirmados em runtime/config 1.13.1 antes de qualquer rebalanceamento externo.
## 12. Correções 1.5.1 preservadas como regression gates
A 1.5.1 corrigiu crashes de Cables/PDU em certas configs, flicker de PDU/Battery Controller/Capacitor Bank e a linha inferior da PDU escondida pelos breakers. Essas correções continuam sendo regression gates úteis mesmo após o avanço para 1.13.1.
## 13. Optional companion features
O upstream informa que algumas features só ativam com companion mods como **Create Aeronautics** e **Simulated**. Compat code não deve ser carregado como requisito obrigatório quando o provider está ausente.
## 14. Client/server e multiplayer
FE, topology, priorities, telemetry state e control commands são server/common. Screens, graphs, lights e cockpit presentation são client-facing. Pacotes de UI precisam ser validados para não permitir energy/control mutation arbitrária.
## 15. Lifecycle
Validar cable connect/disconnect, PDU topology changes, chunk unload/reload, device rename, controller loss, restart, vehicle assembly/disassembly e optional-mod load conditions.
## 16. Infraestrutura adicional verificável no source público atual
O source público atualmente indexado acrescenta detalhes que não estavam representados no dossiê 1.5.1:
- **LambDynamicLights** é tratado como dependência obrigatória da implementação de iluminação dinâmica do **Stage Light**; a modlist física contém `lambdynamiclights-4.8.11+1.21.1.jar`.
- **GeckoLib** é integração opcional para caminhos visuais/helmet; a modlist física contém GeckoLib 4.9.2.
- **JEI** é integração opcional para recipe catalysts/filtragem; a modlist física contém JEI 19.56.0.440.
- **Sable Companion** é incluído via JarJar no source atual; a modlist física também evidencia cópias embarcadas de Sable Companion em componentes do ecossistema.
- O source contém implementação **P1.13** de tela/configuração de HUD do Stressometer, incluindo snap-to-grid e drag/resize handles.
Esses fatos descrevem o source público atual e a instalação; não são apresentados como um changelog completo da build 1.13.1.
## 17. Riscos
1. FE contado duas vezes em bridges.
2. Loop de PDU chaining.
3. Load shedding desligar carga errada.
4. Cable visual divergir de topology.
5. Telemetry listener duplicar processamento.
6. Vehicle command usar coordenada/frame errado.
7. Companion integration classload sem provider.
8. Crash paths históricos de PDU/cables reaparecerem.
9. Stage Light depender de LambDynamicLights ausente/incompatível.
10. Integrações opcionais JEI/GeckoLib carregarem paths indevidos quando ausentes.
11. Sable Companion embarcado divergir do provider/runtime esperado.
12. HUD/Stressometer manter state visual inconsistente após resize, reconnect ou mudança de resolução.
## 18. Matriz de testes
1. Dedicated server boot com Create 6.0.10 + Stats & Power 1.13.1.
2. Dynamo→Cable→PDU→device e conservation de FE.
3. Priority tiers + Active Auto em geração insuficiente.
4. Duas PDUs encadeadas e dois controllers numa board.
5. Indoor Cable corners/removal.
6. Outlet + Smart Home Panel + rename.
7. Displays/recorders/alarms e Production Detector.
8. Flight Control/RPM/pathfinding com companion realmente instalado.
9. Bread Slicer/Toaster; confirmar se custos históricos 20/40/80 FE/t permanecem no runtime/config 1.13.1.
10. Configurations que antes crashavam PDU/cables.
11. Restart/chunk reload preservando topology.
12. Stage Light com LambDynamicLights 4.8.11.
13. Boot e uso básico com/sem integrações opcionais JEI/GeckoLib quando suportado.
14. HUD/Stressometer: drag, resize, snap-to-grid, mudança de resolução e reconnect.
15. Sable/Aeronautics/Simulated: validar que integração não duplica authority física.
## 19. Evidência e limite de atualização
- **Modlist física atual:** `create_stats-1.13.1.jar`, mod id `create_stats`, runtime 1.13.1 e `create_stats.mixins.json`.
- **Create físico:** `create-1.21.1-6.0.10.jar`.
- **CurseForge/Modrinth oficiais:** descrição funcional atual do projeto permanece centrada em power grid, monitoring, lighting, logistics, vehicles e outras utilities.
- **Source público atual:** Erbell9520/Create-Graphs-Additions-public documenta power/wiring, displays, lighting, logistics, drivetrain, vehicles, heating, antennas e dependências/integrations atuais; código indexado contém marcadores P1.13 para HUD/Stressometer.
- **Histórico 1.5.1:** changelog oficial preservado para board chaining, chain page, Indoor Cable corners, outlets/Smart Home Panel, Bread Slicer/Toaster e correções de PDU/cables/UI.
- **Limite:** não foi localizado, nas fontes oficiais acessíveis nesta auditoria, um changelog público exato e completo da build `1.13.1`. Por isso o catálogo atualiza a autoridade física e registra somente deltas verificáveis no source público, sem atribuir à 1.13.1 mudanças não comprovadas.
> 🔒 Boundary canônico: **Stats & Power controla seu grid/telemetria/controllers; Create e companion mods continuam controlando a máquina/física subjacente**. Medir ou comandar não deve duplicar energia, movimento ou produção.


## 20. Atualização upstream 1.14.5 — não instalada
A autoridade física continua em **Create: Stats & Power 1.13.1**. A listagem oficial salta diretamente para **1.14.5**; não foram localizadas releases públicas 1.13.2, 1.14.0, 1.14.1, 1.14.2, 1.14.3 ou 1.14.4.

### Portable Power Interface e energia em trens
- adiciona **Portable Power Interface**, permitindo dockar um trem e carregar/drenar seus Capacitor Banks de forma análoga às Portable Storage/Fluid Interfaces do Create;
- inclui dial de taxa de transferência, **charge floor** e status lamp;
- adiciona schedule condition **Train Charge**;
- Capacitor Banks em trem passam a permanecer **ativos durante movimento**;
- gauges/FE/t exibem carga real dos banks em trem;
- goggles passam a funcionar em blocos do trem e exibem resumo de **Train Charge**.

Essas mudanças transformam trens em parte ativa do grid FE do addon. O settlement deve conservar energia exatamente uma vez entre rede estacionária, interface e storage móvel.

### Montagem e displays
- cables, switches, wall plates e **Cable Diagnostic Junctions** podem ser montados em Create girders;
- Stats Display Mk III recebe charts com Y-axis **Auto** ou **Fixed**.

### Correções de routing e energia
- corrige PDU deixando de reconhecer Battery Controller atrás de múltiplos Power Connectors;
- terminal ports passam a reportar corretamente a rede em vez de `carries nothing`;
- corrige bugs de routing envolvendo PDU, Battery Controller e Power Limiter;
- corrige Portable Power Interface **apagando energia** quando o dial configurado excedia a capacidade do wire.

O último fix é um regression gate de conservação: limitar throughput nunca pode destruir FE excedente.

### Segurança e contraptions
- corrige bypass de **redstone** em doors governadas;
- corrige security blocks podendo ser **roubados via contraptions**;
- adiciona operação **Household Remove** para owner/admin.

Security state precisa continuar server-authoritative durante assembly/disassembly; mover um block não pode apagar ownership/permission.

### Freight / Dispatch Board
- melhora clareza de Freight Manifest e Dispatch Board;
- corrige orders presos;
- corrige chart freeze;
- corrige **sync drift**.

### Rendering e performance
- corrige faces de Battery Controller, PDU e Capacitor Bank aparecendo com noise/blank;
- caches de display de Battery Controller/PDU/Capacitor Bank passam a evitar redraw a cada frame.

### Gate de promoção 1.13.1 → 1.14.5
1. PPI dock/undock com trem parado e em movimento;
2. charge/drain, dial acima/abaixo da capacidade do wire e conservation exata de FE;
3. charge floor e Train Charge schedule condition;
4. Capacitor Bank móvel, gauges e goggles;
5. cables/switches/wall plates/CDJ em girders;
6. PDU→Power Connectors→Battery Controller em cadeia;
7. Power Limiter e priority/load shedding;
8. governed doors sob redstone e contraption movement;
9. security block assembly/disassembly + Household Remove;
10. Freight Manifest/Dispatch Board, stuck order, chart e multiplayer sync;
11. displays em long-running client para verificar cache/render;
12. restart/chunk unload, Sable/Aeronautics/Simulated integrations e exactly-once FE settlement.

Fonte upstream: CurseForge file ID 8906784, `create_stats-1.14.5.jar`, release NeoForge 1.21.1.

## 21. Atualização upstream 1.15.0 — 10/10/2026

**Físico:** create_stats-1.13.1.jar / 1.13.1. A seção anterior documenta integralmente **1.14.5** (Portable Power Interface, Power Controller e corrections). **Nova release NeoForge Minecraft 1.21.1:** create_stats-1.15.0.jar, 05/10/2026, CurseForge file 9074215. O autor exige atualização **conjunta do servidor e de todos os clientes**.

### 1.15.0 — novo escopo operacional
- **Power Track**: trilhos energizados permitem carregar trens em movimento ou transportar energia até bases remotas.
- **VCC Mk IV Ground** com trailer, luzes, Driver Helmet e HUD; requer **Create Aeronautics + Create Offroad**; rotas autônomas geradas via Surveyor's Staff e Create Schedule.
- **VCC Mk IV Aerial** com airship/autopilot, Aviator Helmet e reflector sight, ampliando as surfaces do stack Sable/Aeronautics.
- **Battle Bridge** reconstruída para Create Big Cannons, com aiming/leading target e proteção contra disparo no próprio navio; deve ser server-authoritative.
- **Cine Camera** grava vídeo MP4 com áudio em Windows e captura de fotos, timelapse/slow motion; testar permissão, I/O e desempenho.
- **Seismic Scanner** identifica minérios, cavernas e lava até 64 blocos. **Solar Panels** (3 tiers), Biofuel Burner, Starter Capacitor, HV Line/Busbar Trunk (quarto nível de wiring), Contactor redstone/FE, Oven + seis cakes/quatro crops, Vacuum Collector/Cleaner/Sniffer e recursos de iluminação/temporização, advancements e suporte administrativo.
- Smart Home Panel recebe floor plan e mob-spawn light view, Battery Controller mostra previsão de 30min, PDU ganha In/Out; cabos e controls revisados, shader/dynamic lights integrados ao stack Sodium/Iris.

### Mudanças potencialmente breaking / migração
- **Industrial Connector agora limita 65.536 FE/t, antes 98.304 FE/t**; qualquer desenho de rede que dependa do throughput antigo pode deixar de satisfazer consumo. Power Connector não transporta mais que a capacidade de sua própria grade, mesmo como hub.
- **Stats Recorder** e **Memory Cog** deixam de ser craftáveis, embora blocos colocados permaneçam; receitas, quests e scripts precisam ser revisados.
- Factory Manual substituído pelo Student's Manual (WIP); **lâmpadas voltam ao brilho máximo uma vez**, as receitas de fios passam a usar máquinas Create.
- Support terminal vem **bloqueado inclusive para operadores**, exigindo permissão explícita pelo comando de administração divulgado no upstream.

**Regressões de maior risco:** conservação de FE/SU entre rede estacionária e veículos, train charging e schedules, throughput/cabo grade, segurança de blocos/doors/admin, station routing, VCC autônomo e multiplayer, compat de aeronaves/physics + Create Big Cannons, nova dynamic lighting e concorrência com outros lighting providers, UI e codecs, migração de configurações/permissões, world save/restart. Não instalar em mundo principal sem cópia de teste.

**Fontes:** https://www.curseforge.com/minecraft/mc-mods/create-stats-power/files/9074215 ; https://www.curseforge.com/minecraft/mc-mods/create-stats-power/files/8906784

**Estado:** 1.15.0 upstream, atualização física e smoke-tests pendentes.
