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
- **Compatibilidade/Riscos:** Riscos: node UUID/state stale, graph persistence, cross-controller feedback, multiplayer joystick ownership, schematic migration, Sable/Aeronautics drift, Propulsion Simulated e regressões de dedicated server ao desabilitar blocos por JSON/registry gating. As releases 1.2.3–1.2.5 não estão instaladas; 1.2.4 endurece Physics Gantry/Set Data e 1.2.5 corrige runtime de node graphs, Contraption Network Linker, Physics Staff, lazy chunk loading e lifecycle visual dos Smart Glasses.
- **Sobreposição:** Interseção parcial com Create Propulsion: Simulated e outros addons de thrusters/controle. Funções devem ser comparadas componente por componente; não há base para exclusão como duplicata integral.
- **Observações:** JAR físico top-level `gadgets-and-gizmos-bundled-V1.2.2.jar`; wrapper id `createthrusters_bundled` 1.2.2; gameplay mod embarcado `createthrusters` 1.2.2. Upstream avançou por 1.2.3, 1.2.4 e **1.2.5**; nenhuma dessas releases posteriores está instalada.
- **Procedência:** modlist(1).txt física atual de 22/09/2026 — 587 entradas top-level incluindo o modloader — confirma `gadgets-and-gizmos-bundled-V1.2.2.jar`, wrapper id `createthrusters_bundled`, runtime `1.2.2` e SHA-1 `1694bb6f99100557faf80fece178a2a4dd4892de`. CurseForge oficial foi revisado de 1.2.3 até **1.2.5**, latest NeoForge 1.21.1 em 26/09/2026; nenhuma dessas versões posteriores está instalada.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — runtime físico permanece 1.2.2. O histórico posterior **1.2.3 → 1.2.4 → 1.2.5** foi revisado; 1.2.5 é a release NeoForge 1.21.1 mais recente localizada no CurseForge, publicada em 26/09/2026.

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
- Upstream 1.2.3 foi publicado em 17/09/2026, 1.2.4 em 18/09/2026 e 1.2.5 em 26/09/2026; nenhuma está instalada. A 1.2.4 corrige Physics Gantry/sub-level e performance, Physics Gantry Belt Wheel simultâneo, adiciona recipe de ION Thruster e advancements e remove portas inseguras de Set Data. A 1.2.5 adiciona novo hardening de Physics Staff/sublevels, controller graph/network links, lazy chunk loading e Smart Glasses.
- A ficha prioriza control surfaces, persistence, authority e regressions relevantes à build física 1.2.2.

## 12. Atualização upstream 1.2.5 — não instalada
A authority física continua em **1.2.2**. Depois das releases 1.2.3 e 1.2.4 já registradas acima, o CurseForge publicou **Create Aeronautics: Gadgets & Gizmos 1.2.5** para NeoForge 1.21.1 em 26/09/2026.

Changelog 1.2.5:
- corrige **Smart Glasses** deixando widgets de GUI na tela mesmo depois de o jogador remover os óculos;
- altera o **Physics Staff** para permitir lock/unlock do sub-level em que o jogador está;
- adiciona verificação de child sub-level para impedir o Physics Staff de pegar/mover o sub-level atual do jogador ou um child dele;
- corrige a interação de mob haunting do **Haunting Upgrade**;
- o **Ship Control Module** passa a iniciar `lazy chunk loading` assim que o Advanced Control Module é colocado, antes da inicialização completa do SCM;
- **Supporter Mannequins** passam a aceitar skins customizadas de jogador pelo poser GUI;
- **Physics Gantry Carriage** passa a usar attachment de rigid joint real e deixa de sofrer influência indevida de inércia;
- corrige regressão do **Contraption Network Linker** que parava de acompanhar blocos vinculados após assembly/disassembly;
- corrige runtime do **Advanced Contraption Controller** que podia produzir outputs inconsistentes e pular nodes quando encadeados;
- corrige links quebrados em schematic builds usando o mod **Toolgun**;
- corrige falha no primeiro save do node graph de um Advanced Contraption Controller recém-criado.

Impacto no pack: 1.2.5 toca exatamente superfícies de alto risco já documentadas — sublevels, assembly/disassembly, graph persistence, chunk loading, links e ownership visual. A mudança do Physics Staff precisa ser testada em parent/child sublevels para evitar movimentar a própria estrutura que contém o jogador. O lazy chunk loading do SCM também merece teste de cleanup para não deixar chunks carregados após desmontagem/desconexão.

Gate de promoção 1.2.2→1.2.5: aplicar primeiro os regressions 1.2.3/1.2.4 já documentados e acrescentar Smart Glasses equip/unequip, Physics Staff em parent/child sublevels, Haunting Upgrade, SCM lazy chunk loading + cleanup, rigid joint do Gantry Carriage, Network Linker através de assembly/disassembly, controller graph encadeado sem node skip, Toolgun schematic links e primeiro save de controller novo.

Fonte upstream: CurseForge file ID 8983532, `gadgets-and-gizmos-bundled-V1.2.5.jar`, release NeoForge 1.21.1.

## Atualizações upstream 1.2.3 → 1.2.8 — 10/10/2026

**Autoridade física:** `gadgets-and-gizmos-bundled-V1.2.2.jar` / mod V1.2.2 (último snapshot). No CurseForge oficial de NeoForge Minecraft 1.21.1 foram localizadas **todas as seis releases posteriores**, em ordem: **1.2.3 (17/09) → 1.2.4 (18/09) → 1.2.5 (26/09) → 1.2.6 (04/10) → 1.2.7 (05/10) → 1.2.8 (09/10)**. A última disponível é **`gadgets-and-gizmos-bundled-V1.2.8.jar`**. O nome do dossiê continua ancorado no JAR físico 1.2.2.

### 1.2.3 — correções e controle de contraptions
- **BiDirectional Gearbox**: ajuste na GUI e seleção de faces de bloco.
- **RCS Thrusters**: controle estendido por servos e periféricos CC:Tweaked.
- Corrige problemas com **pickaxes em dedicated servers**, hanging de servidor no **Physics Gantry** quando chunks carregam, e nodes pulados na execução de **Advanced Contraption Controller (ACC)**.
- Edit de ângulos inline em Servo/Aileron, scale de GUI, tooltip de combustível e consumo correto de fuel buckets pelos thrusters (sem derramar fluidos).
- As correções no ACC afetam execução de grafos, outputs e sincronização e precisam ser exercitadas independentemente da UI.

### 1.2.4 — Physics Gantry, montagem e segurança do editor
- Corrige regressão de montagem de **Physics Gantry em sublevels** e overhead de performance.
- Corrige **Belt Wheels** que quebravam belts quando ambos montados/desmontados simultaneamente.
- **ION Thruster** craftável com 1 Thruster + 1 Lens; adiciona **advancements**.
- Remove port **NBT** do node **Set Data**, passível de crash, e os ports do mesmo node quando o alvo é um Copycat. Grafos antigos com ports removidos exigem teste de migration/load.

### 1.2.5 — estado persistente e permissões
- **Smart Glasses:** corrige widgets permanecendo após desequipar.
- **Physics Staff:** permite lock/unlock do sublevel atual e impede mover o sublevel (ou child sublevel) ocupado pelo jogador.
- Corrige interação mob de **Haunting Upgrade** e habilita lazy chunk loading do **Ship Control Module** já ao colocar ACC.
- Supporter Mannequin passa a carregar skins de jogador configuradas pela GUI.
- **Physics Gantry Carriage:** passa a usar rigid joint real, impedindo efeito indevido da inércia.
- Corrige perda de links do **Contraption Network Linker** na montagem/desmontagem, linker quebrado em schematics com Toolgun e ACC falhando no primeiro save de graph.
- Corrige outputs inconsistentes/nodes pulados em ACC encadeado.

### 1.2.6 — release confirmada, changelog individual não isolado
O arquivo `gadgets-and-gizmos-bundled-V1.2.6.jar` está publicado para NeoForge 1.21.1 desde 04/10, porém **as notas individuais não foram recuperadas de uma fonte primária verificável**. Uma descrição secundária sugere alterações em ACC, Belt Wheels, thrusters e receitas, mas **não é suficiente para atribuir alterações de API ou comportamento precisas**. Esse gap deve ser preenchido por página oficial/commit/tag ou comparação binária antes de liberação física.

### 1.2.7 — UI, thrusters e node typing
- Corrige goggle tooltip do **ACC** mostrando indevidamente `Sable Contraption Controller`.
- Goggle tooltip de **Aileron Bearing** informa `Head Assembled`.
- Aileron GUI mostra hover de frequências de redstone fora de modo Precise.
- Adiciona config de **max thrust dos RCS Thrusters**.
- **Vector Bearing** ganha opção de inverter thrust direction e node **Set Data** passa a aceitar input **Thrust Direction** com Vector Bearing como alvo.

### 1.2.8 — graph runtime e correção crítica de inicialização
- **ACC** recebe novos math nodes.
- Corrige **Ship Control Module/autopilot**: ao iniciar servidor dedicado, ships não devem mais cair do céu por erro de inicialização dos controladores.
- Corrige **shared graphs removendo binding dos linkers**, com impacto direto na preservação de redes de contraptions e connections de automação. Falha nessa superfície pode causar controle incorreto ou desincronizado de ships após restart.

### Gates de regressão integrados
1. Backup restaurável, cópia de mundo com graph ACC e links existentes; verificar schema de nodes **Set Data removidos/adicionados**, graph saving e migration sem perda.
2. **Dedicated server restart com ships ativos** em autopilot, ACC e Ship Control Module; posição/velocidade e inputs não podem ficar órfãos ou fazer o navio cair.
3. Network Linkers/CC:Tweaked/ACC encadeado com outputs e consumers, assembly/disassembly, Toolgun schematics e reload de mundo; nunca duplicar/remover binding.
4. Physics Gantry, Belt Wheels e rigid joint de carriage em sublevels Sable; lock/child permissions do Physics Staff.
5. Servo/Aileron redstone GUI, Vector Bearing invert thrust, RCS max thrust, fuels/buckets, ION Thruster recipe, redstone events e Sable gravity constraints.
6. Smart Glasses equip/desequip, Mannequin skins, tooltip localization/scale, save/restart e multiplayer com dois operadores concorrentes.
7. Comparar manifests e bundled libraries de V1.2.8 com Create, Create Aeronautics/Sable e CC:Tweaked físicos; test de loader NeoForge21.1.250 sem tratar releases de outros Minecrafts como compatíveis.

**Fontes oficiais:**
- índice de todas releases: https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-gadgets-and-gizmos/files/all?version=1.21.1
- 1.2.3: https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-gadgets-and-gizmos/files/8899907
- 1.2.4: https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-gadgets-and-gizmos/files/8915440
- 1.2.5: https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-gadgets-and-gizmos/files/8983532
- 1.2.7: https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-gadgets-and-gizmos/files/9070149
- 1.2.8: https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-gadgets-and-gizmos/files/9110305
- source oficial: https://github.com/Riieno/Gadgets-And-Gizmos

**Decisão:** 1.2.8 documentada como upstream, **não instalada**; promoção pendente de compatibilidade, source da 1.2.6 e smoke test de persistência/autopilot.
