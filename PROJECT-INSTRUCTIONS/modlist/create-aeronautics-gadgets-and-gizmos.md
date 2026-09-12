# Create Aeronautics: Gadgets & Gizmos

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81f7b1d2c33dfeff0c62
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Aeronautics: Gadgets & Gizmos
- **Arquivo JAR:** `createthrusters-bundled-V1.1.3.jar`
- **Versão 1.21.1:** 1.1.3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Exploração, QoL
- **Função:** Addon de Create Aeronautics que adiciona gadgets, controles e componentes para interagir com contraptions físicas, incluindo recursos de propulsão e controle de veículos.
- **Dependências:** Create 6.0.10 + Create Aeronautics 1.3.2/Sable stack físico. CC:Tweaked/Propulsion Simulated são integrações de regressão citadas na 1.1.3, não promovidas aqui a hard dependencies universais.
- **Sobreposição:** Interseção parcial com Create Propulsion: Simulated e outros addons de thrusters/controle. Funções devem ser comparadas componente por componente; não há base para exclusão como duplicata integral.
- **Compatibilidade/Riscos:** Riscos: node UUID/state stale, graph persistence, cross-controller feedback, multiplayer joystick ownership, schematic migration, Sable/Aeronautics drift e conflito com Propulsion Simulated. Upgrades desde <=1.0.6 tinham risco explícito de apagar linkers.
- **Observações:** JAR físico `createthrusters-bundled-V1.1.3.jar`, mod id `createthrusters`, runtime 1.1.3. Metadata local: Create Gadgets & Gizmos; projeto público: Create Aeronautics: Gadgets & Gizmos. Release NeoForge 1.21.1 de 27/07/2026.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge/Modrinth oficiais Create Aeronautics: Gadgets & Gizmos 1.1.3.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-gadgets-and-gizmos
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — corpo vazio corrigido; Advanced Controller node graph, joystick ownership, data links, bearings/thrust, migration e Sable risks catalogados para 1.1.3.
- **Histórico da decisão:**
- **Data da última decisão:**

> 🎛️ **ESCOPO CANÔNICO.** Runtime físico: `createthrusters-bundled-V1.1.3.jar`, mod id `createthrusters`, versão `1.1.3`. O metadata chama o mod **Create Gadgets & Gizmos**; o projeto público é **Create Aeronautics: Gadgets & Gizmos**. É um addon Client & Server de controle/interação com contraptions físicas.

## 1. Papel no stack
Aeronautics/Sable continuam owners do body/sublevel e física. Gadgets & Gizmos fornece controladores, inputs, data links, bearings e gadgets que observam/alteram o comportamento do veículo através das APIs do provider.
Não confundir com Propulsion: Simulated: há interseção em propulsão/controle, mas não equivalência total.

## 2. Advanced Contraption Controller
A 1.1.3 amplia fortemente o controlador baseado em node graph. Mudanças confirmadas incluem:
- `Mouse Control` com clicks, scroll e Mouse-X/Y;
- Variable nodes corrigidos;
- copy/paste atribuindo UUIDs para evitar valores stale;
- Comment Groups com seleção/cópia aprimorada;
- Save/Undo/Redo e Save on close;
- rolling history de até 15 versões;
- Cross-Controller Named Events;
- Graph Failure highlighting;
- Controller Tracker de posição do jogador/lectern;
- simulação do graph mais próxima do runtime.

Esse grafo é state persistente e precisa ser tratado como configuração operacional do veículo.

## 3. Inputs e ownership multiplayer
Analogue Joystick usa **server-side player drag ownership** quando múltiplos jogadores tentam operar o mesmo joystick. Isso é um contrato importante: input concorrente precisa ter owner determinístico no servidor, não simplesmente “último packet vence”.
Aeroworks Joystick também foi adicionado como input válido para Advanced Data Link na 1.1.3.

## 4. Bearings, thrust e schematics
A release corrige Vector Bearing piston partial animations, thrust ausente na rotating head e Physics Gantry com Sequenced Gearshift/TurnDistance. Também reforça schematic compatibility para Bearings e outros blocks, visando preservar settings durante blueprint/schematic workflows.

## 5. Controller graph persistence e migration
O changelog alerta que **upgrades vindos de 1.0.6 ou anteriores podem apagar linkers dentro de controllers**. O pack já está em 1.1.3, mas esse fato mostra que controller/linker state é migration-sensitive.
Backups, copy/paste, UUIDs e rolling history são superfícies de integridade, não meramente UI.

## 6. CC:Tweaked e Sable
1.1.3 corrige Navigation Table methods usando chamadas mais seguras via Sable API e um mixin crash que podia ocorrer com Propulsion Simulated recente. Mesmo sem usar CC:Tweaked em toda automação, isso torna Sable/Propulsion regression gates explícitos.

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

## 9. Boundary para quests/perks
Mover joystick, editar graph ou mostrar HUD não é milestone de domínio por si. Crédito deve usar ação server-side causal do veículo, com deduplicação. Profiler/debug nodes jamais devem gerar gameplay progression.

## 10. Matriz de testes
- [ ] Dedicated server inicia com 1.1.3 + Aeronautics 1.3.2.
- [ ] Advanced Controller graph salva/reabre com UUIDs estáveis.
- [ ] Copy/paste não mantém valores stale.
- [ ] Undo/redo/history não altera graph aplicado de forma inesperada.
- [ ] Dois players usando joystick têm ownership determinístico.
- [ ] Cross-controller event não cria loop não intencional.
- [ ] Schematic preserva controller/bearing settings.
- [ ] Vector Bearing/Physics Gantry produzem motion/thrust esperado.
- [ ] Propulsion Simulated coexistente não causa mixin crash.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 11. Evidências e limite
CurseForge/Modrinth oficiais confirmam Release 1.1.3 e o changelog detalhado acima. A ficha não tenta enumerar todo registry do addon; prioriza control surfaces, persistence, authority e regressions relevantes à build física.
