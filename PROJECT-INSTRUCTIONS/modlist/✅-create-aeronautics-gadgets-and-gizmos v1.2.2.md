# Create Aeronautics: Gadgets & Gizmos

## Propriedades do registro

- **Mod:** Create Aeronautics: Gadgets & Gizmos
- **Arquivo JAR:** gadgets-and-gizmos-bundled-V1.2.2.jar
- **Versão 1.21.1:** 1.2.2
- **Categoria:** Tecnologia; Exploração; QoL
- **Função:** Addon de Create Aeronautics que adiciona gadgets, controles e componentes para interagir com contraptions físicas, incluindo recursos de propulsão e controle de veículos.
- **Dependências:** Pack físico: Create 6.0.10 + Create Aeronautics 1.3.2/Sable stack atual. Wrapper top-level createthrusters_bundled 1.2.2 contém createthrusters 1.2.2 e gadgetsngizmos 1.2.2. CC:Tweaked e Propulsion Simulated permanecem superfícies de integração/regressão, não hard dependencies universais.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Riscos: node UUID/state stale, graph persistence, cross-controller feedback, multiplayer joystick ownership, schematic migration, Sable/Aeronautics drift, Propulsion Simulated e regressões de dedicated server ao desabilitar blocos por JSON/registry gating. 1.2.3 existe upstream, mas não está instalado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-gadgets-and-gizmos
- **Procedência:** modlist física de 17/09/2026 + releases/changelogs oficiais 1.2.0, 1.2.1 e 1.2.2; release 1.2.3 registrada apenas como atualização disponível não instalada.
- **Observações:** JAR físico top-level `gadgets-and-gizmos-bundled-V1.2.2.jar`; wrapper id `createthrusters_bundled` 1.2.2; gameplay mod embarcado `createthrusters` 1.2.2. Upstream publicou 1.2.3 em 17/09/2026, não instalado.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 17/09/2026 — runtime físico atualizado para 1.2.2; linha 1.2.0 e hotfixes 1.2.1/1.2.2 incorporados; 1.2.3 registrada somente como update upstream não instalado.
- **Decisão:** Sem decisão
- **Histórico da decisão:** 
- **Sobreposição:** Interseção parcial com Create Propulsion: Simulated e outros addons de thrusters/controle. Funções devem ser comparadas componente por componente; não há base para exclusão como duplicata integral.

> **ESCOPO CANÔNICO.** Runtime físico top-level: `gadgets-and-gizmos-bundled-V1.2.2.jar`, wrapper id `createthrusters_bundled`, versão `1.2.2`; o gameplay mod embarcado é `createthrusters-1.2.2.jar`, id `createthrusters`. O metadata chama o mod **Create Gadgets & Gizmos**; o projeto público é **Create Aeronautics: Gadgets & Gizmos**. É um addon Client & Server de controle/interação com contraptions físicas.
## 1. Papel no stack
Aeronautics/Sable continuam owners do body/sublevel e física. Gadgets & Gizmos fornece controladores, inputs, data links, bearings e gadgets que observam/alteram o comportamento do veículo através das APIs do provider.
Não confundir com Propulsion: Simulated: há interseção em propulsão/controle, mas não equivalência total.
## 2. Advanced Contraption Controller
A linha 1.1.3 já consolidava o controlador baseado em node graph com Mouse Control, UUIDs em copy/paste, Comment Groups, Save/Undo/Redo, rolling history, Cross-Controller Named Events, Graph Failure highlighting, Controller Tracker e simulação mais próxima do runtime.
Na 1.2.0, a superfície avançou com compartilhamento de graphs por website, novos controles e vários ajustes de nodes/UI. A 1.2.2 instalada é um hotfix posterior da mesma linha. O grafo permanece state persistente e deve ser tratado como configuração operacional do veículo.
## 3. Inputs e ownership multiplayer
Analogue Joystick usa **server-side player drag ownership** quando múltiplos jogadores tentam operar o mesmo joystick. Isso é um contrato importante: input concorrente precisa ter owner determinístico no servidor, não simplesmente “último packet vence”.
Aeroworks Joystick também foi adicionado como input válido para Advanced Data Link na 1.1.3.
## 4. Linha 1.2.x — controles, propulsão e hotfixes
A 1.2.0 adicionou/alterou componentes e UX relevantes, incluindo **Ship Control Module**, **RCS Thruster**, novas peças/materiais (Blackstone Alloy, sheet/block, Computation Mechanism e Coupler), além de mudanças de recipe/model e renames como **Contraption Controller → Sable Contraption Controller** e **Physics Goggles → Smart Glasses**. Também trouxe correções de áudio/render de thrusters, métodos de CC:Tweaked e compartilhamento de graphs.
A 1.2.1 corrigiu um problema em dedicated server no qual desabilitar um bloco por erro de JSON podia impedir qualquer block breaking.
A **1.2.2 física** corrige outro crash de dedicated server quando blocos ficam desabilitados por startup registry gating. Esses hotfixes tornam configuração de blocos e boot dedicado gates obrigatórios.
## 5. Controller graph persistence e migration
O changelog histórico alerta que **upgrades vindos de 1.0.6 ou anteriores podem apagar linkers dentro de controllers**. O pack já está em 1.1.3, mas esse fato mostra que controller/linker state é migration-sensitive.
Backups, copy/paste, UUIDs e rolling history são superfícies de integridade, não meramente UI.
## 6. CC:Tweaked, Sable e estado upstream
A linha 1.1.3 já corrigia Navigation Table methods via Sable API e um mixin crash com Propulsion Simulated. A 1.2.0 ampliou métodos de CC:Tweaked e os controles de veículo.
Em **17/09/2026** upstream publicou **1.2.3**, posterior à build física 1.2.2. Essa versão não é tratada como instalada: fica registrada apenas como update disponível. O catálogo permanece em 1.2.2 até a modlist física mudar.
## 7. Client/server boundary
Node editor/HUD/graph rendering são client-facing; applied graph, joystick ownership, data links e vehicle control precisam convergir server-side. Profiler nodes podem mostrar FPS/frametime/TPS/MSPT, mas leitura de cliente não deve ser usada como authority de gameplay.
## 8. Riscos
1. Node UUID stale/duplicado após copy/paste.
2. Controller graph salva parcialmente e entra em estado inválido.
3. Cross-controller event loop/feedback.
4. Dois jogadores disputam joystick e ownership oscila.
5. Schematic perde settings/linkers.
6. Bearing/thrust diverge após update Aeronautics/Sable.
7. Propulsion Simulated reintroduz mixin/API conflict.
8. HUD/client graph diverge do state aplicado no servidor.
9. Block-disable JSON/registry gating reintroduzir crash ou bloquear block breaking em dedicated server.
10. Atualizar para 1.2.3 sem validar o delta físico e os addons Aeronautics/Sable.
## 9. Boundary para quests/perks
Mover joystick, editar graph ou mostrar HUD não é milestone de domínio por si. Crédito deve usar ação server-side causal do veículo, com deduplicação. Profiler/debug nodes jamais devem gerar gameplay progression.
## 10. Matriz de testes
- [ ] Dedicated server inicia com 1.2.2 + Aeronautics 1.3.2.
- [ ] Advanced Controller graph salva/reabre com UUIDs estáveis.
- [ ] Copy/paste não mantém valores stale.
- [ ] Undo/redo/history não altera graph aplicado de forma inesperada.
- [ ] Dois players usando joystick têm ownership determinístico.
- [ ] Cross-controller event não cria loop não intencional.
- [ ] Schematic preserva controller/bearing settings.
- [ ] Vector Bearing/Physics Gantry produzem motion/thrust esperado.
- [ ] Propulsion Simulated coexistente não causa mixin crash.
- [ ] Bloco desabilitado por JSON/registry gating não derruba o dedicated server nem bloqueia block breaking global.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 11. Evidências e limite
- Modlist física de 17/09/2026: `gadgets-and-gizmos-bundled-V1.2.2.jar`, wrapper `createthrusters_bundled` 1.2.2, com `createthrusters-1.2.2.jar` e `gadgetsngizmos-1.2.2.jar` embarcados.
- Changelog 1.2.0: novos controles/componentes, graph sharing, Ship Control Module, RCS Thruster, materiais/recipes/renames e correções de thrusters/CC:Tweaked.
- Hotfix 1.2.1: block breaking em dedicated server quando bloco era desabilitado.
- Hotfix 1.2.2: crash de dedicated server durante startup registry gating com blocos desabilitados.
- Upstream 1.2.3 foi publicado em 17/09/2026, mas não está instalado; é somente update disponível.
- A ficha prioriza control surfaces, persistence, authority e regressions relevantes à build física 1.2.2.
