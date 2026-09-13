# Create: Stats & Power

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81a8a655f94acdb67a2c  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: modlist física mais recente, 595 mods  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** Create: Stats & Power
- **Arquivo JAR:** `create_stats-1.5.1.jar`
- **Versão 1.21.1:** `1.5.1`
- **Categoria:** Tecnologia; Automação; QoL
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-stats-power
- **Função:** Addon Create de telemetria e infraestrutura elétrica FE com displays/recorders/alarms, Power Distribution Units, cables/dynamos/motors/capacitors, factory logic e sistemas de controle veicular.
- **Dependências:** NeoForge + Create obrigatórios. Algumas features ativam apenas com companion mods como Create Aeronautics, Simulated e outros; essas integrações são condicionais à presença real.
- **Compatibilidade/Riscos:** Adiciona grid FE próprio, PDU, vehicle control e monitoramento; pode sobrepor energy bridges e control systems. 1.5.1 corrige crashes de cables/PDU em certas configs, flicker de telas e layout do PDU, e adiciona chaining de boards, outlets/Smart Home Panel e cable cornering.
- **Sobreposição:** Monitoring, FE e vehicle control podem cruzar outros addons, mas o mod owns seu PDU/grid, displays e controllers. Comparar topology/rates e companion integrations antes de classificar redundância.
- **Observações:** runtime 1.5.1; projeto público agora se chama `Create: Stats & Power`, enquanto descrição também usa `Create Stats & Numbers`. 1.5.1 é Release NeoForge 1.21.1 de 31/08/2026. O título do Notion foi alinhado ao nome/runtime atual.
- **Procedência:** Modlist física canônica de 08/09/2026 + runtime create_stats 1.5.1 + CurseForge oficial da release 1.5.1 e descrição atual do projeto.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Create: Stats & Power 1.5.1 foi reconfirmado como `Instalado`; a antiga data 30/08 associada apenas à presença foi removida. Presença não foi convertida em decisão curatorial.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — FE-grid/PDU authority, monitoring/logistics/vehicle-control surfaces, 1.5.1 PDU/cable fixes, client/server lifecycle e optional-companion boundaries catalogados.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> 📊 Versão física confirmada: `create_stats-1.5.1.jar`, runtime `1.5.1`, NeoForge 1.21.1. O projeto atual se apresenta como **Create: Stats & Power** e combina monitoramento industrial, grid FE, lógica de fábrica e controle veicular.

## 1. Papel e authority
Stats & Power controla seus displays, recorders, alarmes, grid FE, PDU, controllers e dispositivos. Create continua authority de kinetics/contraptions; companion mods continuam authority da física/veículo que os controllers observam ou comandam.

## 2. Grid FE
Cable, wire terminals, dynamos, motors e capacitor banks formam uma rede FE. Energia precisa ser contabilizada uma vez entre geração, storage, distribution e consumo; bridges externas não devem criar crédito/debito paralelo.

## 3. Power Distribution Unit
A **PDU** centraliza distribuição e prioridades. O modo Active Auto pode desligar cargas de menor prioridade quando geração é insuficiente. Priority/load shedding precisa ser server-authoritative e persistente.

## 4. PDU chaining — 1.5.1
A 1.5.1 permite boards encadeadas: uma PDU pode virar device em branch de outra; power do back boss é contabilizado e dois controllers podem alimentar uma board. Topologia precisa evitar loops/double-counting.

## 5. Chain page
A release adiciona uma página de cadeia na tela da PDU para mostrar upstream/downstream e starvation. Essa UI observa o grafo lógico; não deve alterar fluxo apenas por estar aberta.

## 6. Indoor Cable
1.5.1 permite Indoor Cable fazer os doze corner cases documentados e sneak-right-click para remover uma run completa. Geometry/render deve acompanhar a conexão lógica sem deixar segmentos fantasma no server.

## 7. Power Outlets e Smart Home Panel
Outlets passam a aparecer individualmente no Smart Home Panel, com switches e rename de devices. Nome/UI não é identidade energética; o endpoint real deve continuar vinculando consumo e permissões corretamente.

## 8. Monitoring
O projeto inclui Stats Displays, Recorders, Alarms, Data Dials e Production Detectors. Há também battle screen/damage observer para monitoramento seccionado de ships/machines. Métricas devem observar state sem gerar um segundo processamento da máquina monitorada.

## 9. Factory logic e logistics
Factory Logic Controller, Dispatch Board, Load Balancer e Calculator adicionam decisão/logística. Commands/outputs precisam ser idempotentes em multiplayer e não executar a mesma transferência por dois controladores concorrentes.

## 10. Vehicles
Flight Control Computer, RPM Governor e cockpit console controlam veículos suportados. Path Scanner + Pathfinder Computer fornecem route guidance. Aeronautics/Simulated continuam owners da transformação/física; Stats & Power fornece sensores/controladores.

## 11. Bread Slicer e Toaster — 1.5.1
A build reconstrói Bread Slicer e torna o Toaster dependente de slices. O changelog publica custos do Slicer por espessura: **20 FE/t Thick, 40 FE/t Medium, 80 FE/t Thin**. Esses valores são version-specific e devem ser testados contra config/runtime antes de rebalanceamento externo.

## 12. Correções 1.5.1
A release corrige crashes de Cables/PDU em certas configs, flicker de PDU/Battery Controller/Capacitor Bank e a linha inferior da PDU escondida pelos breakers. São regression gates concretos do runtime instalado.

## 13. Optional companion features
O upstream informa que algumas features só ativam com companion mods como **Create Aeronautics** e **Simulated**. Compat code não deve ser carregado como requisito obrigatório quando o provider está ausente.

## 14. Client/server e multiplayer
FE, topology, priorities, telemetry state e control commands são server/common. Screens, graphs, lights e cockpit presentation são client-facing. Pacotes de UI precisam ser validados para não permitir energy/control mutation arbitrária.

## 15. Lifecycle
Validar cable connect/disconnect, PDU topology changes, chunk unload/reload, device rename, controller loss, restart, vehicle assembly/disassembly e optional-mod load conditions.

## 16. Riscos
1. FE contado duas vezes em bridges.
2. Loop de PDU chaining.
3. Load shedding desligar carga errada.
4. Cable visual divergir de topology.
5. Telemetry listener duplicar processamento.
6. Vehicle command usar coordenada/frame errado.
7. Companion integration classload sem provider.
8. Crash path 1.5.1 reaparecer em configs específicas.

## 17. Matriz de testes
1. Dedicated server boot.
2. Dynamo→Cable→PDU→device e conservation de FE.
3. Priority tiers + Active Auto em geração insuficiente.
4. Duas PDUs encadeadas e dois controllers numa board.
5. Indoor Cable corners/removal.
6. Outlet + Smart Home Panel + rename.
7. Displays/recorders/alarms e Production Detector.
8. Flight Control/RPM/pathfinding com companion realmente instalado.
9. Bread Slicer/Toaster e custos 20/40/80 FE/t.
10. Configurations que antes crashavam PDU/cables.
11. Restart/chunk reload preservando topology.

## 18. Evidência
- modlist física 08/09/2026: `create_stats-1.5.1.jar`;
- CurseForge oficial: FE grid, monitoring, factory logic e vehicle control;
- changelog 1.5.1: PDU/cable crash fixes, UI fixes, board chaining, chain page, cable corners, outlets/Smart Home Panel, rename, rebuilt Slicer e Toaster-on-slices.

> 🔒 Boundary canônico: **Stats & Power controla seu grid/telemetria/controllers; Create e companion mods continuam controlando a máquina/física subjacente**. Medir ou comandar não deve duplicar energia, movimento ou produção.
