# Create: Stats & Power

## Propriedades do registro

- **Mod:** Create: Stats & Power
- **Arquivo JAR:** create_stats-1.13.1.jar
- **Versão 1.21.1:** 1.13.1
- **Categoria:** Tecnologia, Automação, QoL
- **Função:** Addon Create de telemetria e infraestrutura elétrica FE com displays/recorders/alarms, Power Distribution Units, cables/dynamos/motors/capacitors, factory logic e sistemas de controle veicular.
- **Dependências:** NeoForge + Create obrigatórios; pack físico atual usa Create 6.0.10. O source público atual também declara LambDynamicLights como required dependency para Stage Light; GeckoLib e JEI como integrações opcionais; Sable Companion embarcado via JarJar; integrações com Simulated/Create Aeronautics condicionais à presença real.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Adiciona grid FE próprio, PDU, vehicle control e monitoramento; pode sobrepor energy bridges e control systems. Preservar regression gates 1.5.1 de PDU/cables/UI. Na linha física 1.13.1, ampliar testes para Stage Light/LambDynamicLights, optional JEI/GeckoLib, Sable Companion embarcado e HUD/Stressometer.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-stats-power
- **Procedência:** modlist física atual de 16/09/2026 + metadata física create_stats 1.13.1 + páginas oficiais CurseForge/Modrinth + source público atual Erbell9520/Create-Graphs-Additions-public revalidados em 20/09/2026. A autoridade da versão instalada permanece a modlist física.
- **Observações:** Runtime físico atual 1.13.1. O antigo dossiê estava em 1.5.1. A página pública continua descrevendo o mesmo core funcional. O source público atual expõe LambDynamicLights required para Stage Light, GeckoLib/JEI opcionais, Sable Companion JarJar e código P1.13 de HUD/Stressometer. Não foi localizado, nas fontes oficiais acessíveis nesta auditoria, um changelog público exato e completo da build 1.13.1; nenhuma mudança específica adicional é atribuída sem evidência.
- **Atualização/Status:** REVALIDADO EM 20/09/2026 — registro histórico do lote físico #160 na snapshot então vigente; posição física atual #161: create_stats-1.13.1.jar / 1.13.1 reconfirmados; FE-grid/PDU authority, monitoring/logistics/vehicle-control surfaces, regressões 1.5.1 preservadas e HUD/Stressometer P1.13 permanecem atuais. Changelog exato da build 1.13.1 continua não localizado; nenhuma mudança não comprovada foi inventada.
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
