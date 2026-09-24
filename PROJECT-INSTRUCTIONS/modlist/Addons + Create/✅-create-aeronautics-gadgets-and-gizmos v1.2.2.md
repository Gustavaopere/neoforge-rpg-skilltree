# Create Aeronautics: Gadgets & Gizmos

## Propriedades do registro

- **Mod:** Create Aeronautics: Gadgets & Gizmos
- **Arquivo JAR:** `gadgets-and-gizmos-bundled-V1.2.2.jar`
- **Versão 1.21.1:** `1.2.2`
- **Categoria:** Tecnologia, Exploração, QoL
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-gadgets-and-gizmos
- **Função:** Addon de Create Aeronautics que adiciona gadgets, controles e componentes para interagir com contraptions físicas, incluindo recursos de propulsão e controle de veículos.
- **Dependências:** Pack físico: Create 6.0.10 + Create Aeronautics 1.3.2/Sable stack atual. Wrapper top-level createthrusters_bundled 1.2.2 contém createthrusters 1.2.2 e gadgetsngizmos 1.2.2. CC:Tweaked e Propulsion Simulated permanecem superfícies de integração/regressão, não hard dependencies universais.
- **Compatibilidade/Riscos:** Riscos: node UUID/state stale, graph persistence, cross-controller feedback, multiplayer joystick ownership, schematic migration, Sable/Aeronautics drift, Propulsion Simulated e regressões de dedicated server ao desabilitar blocos por JSON/registry gating. A 1.2.4 existe upstream, corrige regressão/performance de Physics Gantry, belt wheel simultâneo e remove portas perigosas de Set Data, mas não está instalada.
- **Sobreposição:** Interseção parcial com Create Propulsion: Simulated e outros addons de thrusters/controle. Funções devem ser comparadas componente por componente; não há base para exclusão como duplicata integral.
- **Observações:** JAR físico top-level `gadgets-and-gizmos-bundled-V1.2.2.jar`; wrapper id `createthrusters_bundled` 1.2.2; gameplay mod embarcado `createthrusters` 1.2.2. Upstream publicou 1.2.4 em 18/09/2026; ela não está instalada.
- **Procedência:** modlist(1).txt física atual de 22/09/2026 — 587 entradas top-level incluindo o modloader — confirma `gadgets-and-gizmos-bundled-V1.2.2.jar`, wrapper id `createthrusters_bundled`, runtime `1.2.2` e SHA-1 `1694bb6f99100557faf80fece178a2a4dd4892de`. CurseForge oficial confirma 1.2.4 como release NeoForge 1.21.1 mais recente em 18/09/2026, não instalada.
- **Atualização/Status:** REAUDITADO EM 22/09/2026 — lote físico #298: Gadgets & Gizmos 1.2.2 reconfirmado; upstream 1.2.4 (18/09/2026) registrado como atualização disponível, não instalada.

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #298: JAR `gadgets-and-gizmos-bundled-V1.2.2.jar`, mod id `createthrusters_bundled`, runtime `1.2.2`, SHA-1 `1694bb6f99100557faf80fece178a2a4dd4892de`.

<callout icon="🎛️" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico top-level: `gadgets-and-gizmos-bundled-V1.2.2.jar`, wrapper id `createthrusters_bundled`, versão `1.2.2`; o gameplay mod embarcado é `createthrusters-1.2.2.jar`, id `createthrusters`. O metadata chama o mod **Create Gadgets & Gizmos**; o projeto público é **Create Aeronautics: Gadgets & Gizmos**. É um addon Client & Server de controle/interação com contraptions físicas.
</callout>

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
Upstream avançou além da build física 1.2.2: a **1.2.3** foi publicada em 17/09/2026 e a **1.2.4** em 18/09/2026. A 1.2.4 corrige regressão da patch anterior que quebrava assemblies de **Physics Gantry** em sub-levels e causava problemas de performance; corrige belts quebrando quando dois **Physics Gantry Belt Wheels** eram montados/desmontados simultaneamente; torna o **ION Thruster** craftável com 1× Thruster + 1× Lens; adiciona advancements; e remove portas do node **Set Data** que podiam causar crashes/estado inválido. Nenhuma dessas releases é tratada como instalada: a autoridade física permanece 1.2.2 até a modlist mudar.

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
10. Atualizar para 1.2.3/1.2.4 sem validar o delta físico e os addons Aeronautics/Sable.
11. Regressão de Physics Gantry/sub-level, belt wheel simultâneo ou Set Data node após update upstream.

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
- Upstream 1.2.3 foi publicado em 17/09/2026 e 1.2.4 em 18/09/2026; ambas não estão instaladas. A 1.2.4 corrige Physics Gantry/sub-level e performance, Physics Gantry Belt Wheel simultâneo, adiciona recipe de ION Thruster e advancements e remove portas inseguras de Set Data.
- A ficha prioriza control surfaces, persistence, authority e regressions relevantes à build física 1.2.2.
