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

## Atualização upstream e gate de manutenção — 11/10/2026

**Release pública NeoForge 1.21.1:** Gadgets & Gizmos **1.2.9** (11/10/2026), após a cadeia 1.2.3–1.2.8 auditada anteriormente. **Último JAR físico documentado:** `gadgets-and-gizmos-bundled-V1.2.2.jar` com host `createthrusters_bundled` e módulos embarcados `createthrusters` e `gadgetsngizmos` 1.2.2. Não presumir equivalência da composição JarJar ou novas features sem inspecionar a build 1.2.9. **Gate:** Create Aeronautics/Sable/CC:Tweaked, autopilot e controles em ship, persistência de steering/rotations, reconnect/unload/restart, física authoritative em multiplayer. **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-gadgets-and-gizmos/files/all?version=1.21.1

> **Limite:** revisão documental baseada no último snapshot físico; nenhuma instalação de JAR ou validação in-game nesta execução.

## Release e compatibilidade — reauditoria 13/10/2026

**Atualização upstream NeoForge 1.21.1:** 1.2.10. **Fonte oficial:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-gadgets-and-gizmos/files/9141062.

**Mudanças reais da 1.2.10:** novo nodo `Counter` com incremento/decremento por `Step` a cada pulso; reset do `Do N`; comparação numérica int↔float (`0.0 == 0` retorna verdadeiro); correção de propagação do Advanced HUD Text Input; nodos **Watch Chat** (wake word) e **Send Chat Message** (`Local`/`Player`, com possibilidade de desativar Local na config do servidor); `Local` alcança jogadores no mesmo sublevel OU raio 3x3 chunks do Advanced Contraption Controller; nova entrada `Stop` para `Play Sound`; proteção de `null` world level para evitar crash com Supplementaries. **Alerta oficial:** a build 1.2.9 foi rotulada **DO NOT USE! Causes crashes**; não usá-la como etapa de migração. Smoke test de nós lógicos, chat/abuso spam/permissões, propagation em sublevels, sound stop, Supplementaries, multiplayer e persistência de graphs. Bundle 1.2.2 instalado no snapshot; nova composição JarJar deve ser conferida antes do upgrade.

**Instalação física:** não revalidada desde `modlist(1).txt`; versão instalada histórica e filename versionado com ✅ preservados. Sem substituição de JAR, execução in-game ou confirmação de compatibilidade plena nesta atualização documental. Não promover a versão upstream à coluna física do índice.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** Create Aeronautics: Gadgets & Gizmos
- **Último candidato 1.21.1 / NeoForge localizado:** **1.2.10 (última linha estável localizada); 2.0.0 Pre-Release 3 (16/10)**
- **Estado:** **1.2.10 PENDENTE; 2.0.0 PRE-RELEASE BLOQUEADA**
- **Gate de promoção:** Não usar 1.2.9 (historicamente marcada unsafe). Testar graphs/state, chat/permissions, Create/Aeronautics/Sable, save/restart e bundle; 2.0.0 somente em staging isolado.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-gadgets-and-gizmos

## Lote 21–30 — linha 1.2.3→1.2.10 e fork major pré-release (22/10/2026)

**Versão instalada:** Gadgets & Gizmos **1.2.2** no JAR bundle NeoForge 1.21.1. **Candidato Release 1.2.x:** **1.2.10**, 12/10/2026, `gadgets-and-gizmos-bundled-V1.2.10.jar` (file 9141062). Também existe linha **2.0.0 Pre-Release 3**, cuja migração major não deve ser confundida com hotfix de 1.2.10.

| Publicação | Mudança/risco com lastro |
|---|---|
| 1.2.3 | Fixes de controle e contraptions já detalhados na seção de histórico upstream deste dossier. |
| 1.2.4 | Physics Gantry, montage e proteção do editor de graphs. |
| 1.2.5 | Persistência e permissões do controller graph. |
| 1.2.6 | Publicação confirmada, **changelog individual não isolado** na auditoria anterior; continua sem atribuição funcional segura. |
| 1.2.7 | Ajustes de UI/thrusters e node typing, segundo registro anterior. |
| 1.2.8 | Estado de graph runtime e fix crítico de inicialização já documentados. |
| **1.2.9** | O autor classificou explicitamente a build **DO NOT USE — Causes crashes**. Não recomendar nem usar como passo de atualização. |
| **1.2.10** | Adiciona nodes `Counter` (step/pulse) e `Reset` em `Do N`; corrige Compare entre integer e float (0.0 == 0), propagação de Advanced HUD Text, introduz `Watch Chat` (wake word) e `Send Chat Message` com `Local`/ `Player` (Local desabilitável no server), `Stop` em Play Sound; `null world level guard` contra crash com Supplementaries. Changelog chama Performance Hotfix, mas não relata benchmark de TPS/FPS medido. |
| **2.0.0 pre-release** | Mudanças muito maiores em Ship Control Module, grupos explícitos de controle, network linker e algoritmos PD/LQR de pilotagem; **não é atualização in-place segura** de graphs 1.2.x por mera versão. |

**Gates:** Create 6.0.10, Aeronautics, Sable, node dataflow, math int/float, chat spam/permissões, propagation para sublevel e raio 3×3 chunks, controllers/ship modes, save/restart, ausência de graph duplication e bundle `META-INF/jarjar`/dependencies. Sem QA ou substituição física. **PENDENTE individualizar a 1.2.6** para certificação estritamente release-by-release; a 1.2.9 deve ser considerada unsafe mesmo quando o changelog de 1.2.10 a sucede.

Fontes: https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-gadgets-and-gizmos/files/9141062 ; https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/2.0-prerelease ; https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-gadgets-and-gizmos/files/all .

## Revisão de 17/11/2026 — changelog 1.2.6 recuperado e evolução 2.0.x

**Identidade e autoridade.** O top-level físico histórico continua `gadgets-and-gizmos-bundled-V1.2.2.jar`, id `createthrusters_bundled`, v`1.2.2`, ordem #298; `createthrusters` e `gadgetsngizmos` dentro do bundle **não** são entradas físicas independentes. Minecraft 1.21.1 / NeoForge. Nenhum JAR foi substituído, baixado ou inspecionado nesta revisão. A classificação CurseForge oficial permanece **Addons + Create** e o dossier não foi movido.

### Correção de lacuna: 1.2.6 (04/10/2026)

A nota anterior dizia que o changelog 1.2.6 não havia sido isolado. **Essa lacuna está resolvida pela página exata do CurseForge, file ID 9054313**, que confirma o release NeoForge 1.21.1 e publica as seguintes mudanças:
- **Belt Wheels** agora aceitam até **oito conexões**, e quebrá-las volta a derrubar o item de belt. A **Powered Zipline** passa por Rope Connectors adjacentes com corda e funciona em Chain Conveyors.
- **Advanced Contraption Controller:** renomeação do controlador para recuperar grafos após destruição acidental, novo nodo `Desmos: Plot Point`, movimentação de nodos por setas, ajustes `Set Data` para Smart Gearbox/Servo Bearing, limpeza das portas `SCM: Navigate` e correção de `SCM: Hover` bloqueando yaw. Há também plotagem `When pulsed` com exportação **CSV/JSON**.
- **CC:Tweaked:** corrige temporização de periféricos de Thruster/RCS para comandos sub-tick e expõe o tanque interno dos Thrusters para abastecimento via rede de periféricos.
- **Outros:** Advanced Navigation Table ativa automaticamente com item válido; Thrusters emitem luz proporcional ao output; novo **Creative RCS Thruster**; modelos Supporter Mannequin substituídos por vanilla Fake Player; receitas voltam a aparecer em EMI.

Essas alterações são **documentalmente relevantes** para grafos persistidos, automação, conectividade de belts, CC:Tweaked e testes de compatibilidade. A cadeia 1.2.3, 1.2.4, 1.2.5, 1.2.7, 1.2.8, 1.2.9 e 1.2.10 já está discriminada acima; **1.2.9 continua marcada pelo autor como insegura (crashes)**. Fonte primária exclusiva: https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-gadgets-and-gizmos/files/9054313 .

### Pré-releases e betas 2.0.0 localizadas (NeoForge 1.21.1)

O **CurseForge** parou publicamente em **2.0.0-Pre-Release-4 (19/10/2026)**; o mesmo autor disponibiliza em **Modrinth** a pré-release 5, as betas abaixo e a série final até **2.0.3**. Não concluir que o catálogo do CurseForge seja a última versão compatível do projeto em todas as plataformas. As páginas Modrinth confirmam o loader e o Minecraft de cada release; quando não foi possível pinçar a **data civil exata** pelo registro público recuperado, ela **não é inventada**. Algumas notas das pré-releases/betas são extensas ou cumulativas, portanto o quadro resume o que cada página relata sem presumir que cada item seja exclusivo daquela build.

| Publicação oficial | Mudança relevante observada na página da release | Relevância |
|---|---|---|
| [2.0-prerelease](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/2.0-prerelease) | Redesenha Advanced Control Computer/SCM Graph, permissões de sublevel, grupos explícitos de propulsion, Schedule Graph, Ratchet Cogwheel, Worker Pod, Smart Tank/Vault/Battery, Pilot Cap, câmera e novas interfaces; remove armazenamento de grafos no Network Linker. | **Alta; breaking changes** em grafos, SCM, segurança e logística |
| [2.0-pre-release-2](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/2.0-pre-release-2) | Hold Yaw, Brake com contrathrust, inicialização sem sondagem de todos os ângulos, correções de incompatibilidade Veil/containers, Redstone Link e variáveis. | **Alta**: controle, mixins, grafos |
| [2.0-pre-release-3](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/2.0-pre-release-3) | Novos nodos de lógica (XOR/XNOR/NOR/NAND/SR-Latch, Pulse If, listas/mapas), Flip-Flop, Count, port ordering e correções de grafos; conteúdo da nota também é encontrado na Beta-5, sem deduzir delta exclusivo. | **Alta**: migração dos grafos |
| [2.0-pre-release-4](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/2.0-pre-release-4) | Physics Gantry, render Flywheel, correções Flywheel/links, Canvas Search, Percentage, graph FPS, variáveis persistidas, Smart Glasses/Pilot Hat, câmera, sublevel e Axiom. | **Alta**: render e integridade de state |
| [2.0.0-Pre-Release-5](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/IDR4lPuz) | Worker Pod e transferências, Display Link nodes, permissões e visualização de sublevels, correções de import de grafos em servidor, Smart Battery e controle Sable. | **Alta**: worker/logística e links |
| [2.0.0-Beta-1](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/2.0.0-Beta-1) | Beta de automação com workers, grafos SCM/schedules, Smart Tablet e mudanças de SCM; publicada com alerta explícito de instabilidade e proibição de uso em mundos valiosos. | **Alta; beta imprópria para produção** |
| [2.0.0-Beta-1.1](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/9ci2hEic) | Corrige botão `Open Editor` no Advanced HUD Element e filtros no seletor 3D. | **Baixa para gameplay**, correção da interface |
| [2.0.0-Beta-2](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/2.0.0-Beta-2) | Otimiza workers/crafting em grandes packs e servidores; renomeia ACC para Advanced Control Computer e rebalanceia Thruster Upgrades; changelog inclui ajustes adicionais da fase beta. | **Alta**: recipes, API/estado do controlador |
| [2.0.0-Beta-3](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/2.0.0-Beta-3) | Otimiza runtimes de ACC/SCM/câmeras, habilita Schedule Graph funcional, introduz comando OP `/gizmos bypass_protection`, outputs Scalar/Redstone e fixa HUD. | **Alta**: operacional, permissões e schedule |
| [2.0.0-Beta-3.1](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/2.0.0-Beta-3.1) | `Counter`, reset de `Do N`, compare int/float, Watch/Send/Announce Chat, Stop Play Sound, permissões por owner, correções de câmera/OpenGL. | **Alta**: dataflow e segurança do chat |
| [2.0.0-Beta-4](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/2.0.0-Beta-4) | Seletor 3D inclui child-sublevels, toggle de chunk loading, Hold Yaw, configurações de câmera, Digisable schematic e correções de Veil/containers. | **Alta**: chunks, permissão, sublevels |
| [2.0.0-Beta-5](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/mYi0ANUx) | Novos nodos lógicos/listas/mapas, Flip-Flop, Pulse If, correções de Function subgraphs e Hover, orientação dos ports e graph share. | **Alta**: compatibilidade de grafos |
| [2.0.0-Beta-6](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/5scU0i96) | Corrige transparência com Flywheel, efeitos RCS, gizmos render through, schematics, Physics Gantry, Axiom e desempenho; camera remota inclui interação e novas opções. | **Alta**: render, física e interação remota |

### Linha final 2.0.x (publicada no Modrinth; não instalada)

| Versão | Mudança individual confirmada | Consequência |
|---|---|---|
| [2.0.0 (03/11/2026)](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/2.0.0) | **Aviso upstream expresso:** input/output de nodos `Get Data`/`Set Data` é fundamentalmente alterado, e grafos existentes precisam ser **reconfigurados manualmente**. Advanced Control Computer ganha SCM/Worker/Schedule Graphs, multitarget Get/Set Data, sistema de permissões para sublevels, lógica live PD+LQR, Worker Pod, Smart Tablet, Ship Dock, logística, controle de câmera e novos nodes; remove storage de grafos no Network Linker e compat do Physics Staff com OPAC/Aeroclaims. | **Major breaking**: proibir upgrade in-place automático; exige backup e migração manual de graph/permissions/sublevel |
| [2.0.1](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/2.0.1) | Correção de força/render do Vector Bearing, Claw agarrando mobs/players, câmera e HUD com spikes, fix de pacotes NBT grandes, compat Vibrant Vaults e teclados internacionais. | **Alta**: física, limites de rede e render |
| [2.0.2](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/2.0.2) | Corrige crash de lifecycle ao descarregar chunks de ships/SCM; remove limites artificiais de velocidade do G&G Auto (0,5–0,65 bloco/s) e SCM (28 blocos/s). | **Alta**: integridade após unload e segurança de velocidade/pathing |
| [2.0.3](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/2.0.3) | Corrige render de Scissor Arm e ACC Display; adiciona integração de SCM com **Let It Walk** para autopilot de mechs via `SCM Graph`, `Convert Angle` com output cardinal. | **Alta** se Let It Walk estiver no pack; impacto visual e API de nodos |

**Riscos e homologação (não executada).** Confirmar os manifests exatos dos binários 2.0.3 e bundled JarJar/mod IDs/dependências; compatibilidade de Create 6.x, Aeronautics/Sable, CC:Tweaked, Toolgun, Supplementaries, Physics Gantry e addons consumers; testar graph antigo em cópia restaurável, reconfiguração manual de novos targets, SCM/auto groups/schedules, multi-target Get/Set Data, permissões para owner/admin, Linker e worker, tablet/storage/Ship Dock, chunks unload/restart, multiplayer, NBT oversized, limites de velocidade e gráficos com shaders/Distant Horizons/Flywheel. A disponibilidade pública 2.0.3 **não prova** instalação, estabilidade no modpack ou segurança de migração. Versões Beta e 1.2.9 são impróprias para promoção.

**Status:** a identificação da 1.2.6 deixou de ser bloqueio. A evolução 2.0 foi documentada a partir das páginas oficiais e a versão final 2.0.3 foi verificada; **certificação estrita do histórico 2.0 inteiro ainda PENDENTE**, pois faltam o inventário exaustivo com datas civis exatas das builds intermediárias e a reconciliação técnica da família 2.x com o pack. A instalação histórica é `1.2.2`; este dossier não autoriza promoção física. Não declarar que todos os releases e testes foram definitivamente fechados.

## Fechamento do inventário de publicações — 18/11/2026

**Identidade e versão física:** #298, `gadgets-and-gizmos-bundled-V1.2.2.jar`, wrapper `createthrusters_bundled`, 1.2.2. A [API pública de versões do projeto Modrinth Sza3GgEL](https://api.modrinth.com/v2/project/Sza3GgEL/version) retornou 62 registros; a entrada 1.2.2 (MjBMc4RD) corresponde ao SHA-1 físico histórico `1694bb6f99100557faf80fece178a2a4dd4892de`. Após essa entrada, há **25 artefatos publicados, todos NeoForge 1.21.1**, incluindo as duas linhas contemporâneas, beta e alpha. A [listagem CurseForge oficial](https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-gadgets-and-gizmos/files/all) e a classificação Addons + Create foram confrontadas com o projeto correto. A consulta anterior havia documentado os deltas mas não confirmado as datas de todo o histórico: o inventário abaixo resolve a lacuna. Datas referem-se ao upload Modrinth (UTC), não ao cabeçalho do changelog nem ao horário de edição do dossier.

| Versão | Upload UTC em 2026 | Publicação oficial |
|---|---|---|
| 1.2.3 | 17/09/2026 | [aadW5uNY](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/aadW5uNY) |
| 1.2.4 | 18/09/2026 | [VY2Sg3qY](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/VY2Sg3qY) |
| 1.2.5 | 26/09/2026 | [S4f9BlLI](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/S4f9BlLI) |
| 1.2.6 | 04/10/2026 | [PXctFUSv](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/PXctFUSv) |
| 1.2.7 | 05/10/2026 | [HhiEx5tk](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/HhiEx5tk) |
| 2.0.0-Beta-1 | 05/10/2026 | [PRxR5qrC](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/PRxR5qrC) |
| 2.0.0-Beta-1.1 | 05/10/2026 | [9ci2hEic](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/9ci2hEic) |
| 1.2.8 | 09/10/2026 | [D1zhtUJo](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/D1zhtUJo) |
| 2.0.0-Beta-2 | 09/10/2026 | [19E5dCzP](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/19E5dCzP) |
| 2.0.0-Beta-3 | 11/10/2026 | [otoj8TZS](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/otoj8TZS) |
| 1.2.9 (alpha) | 11/10/2026 | [VTxjWBGy](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/VTxjWBGy) |
| 1.2.10 | 12/10/2026 | [MgD655V5](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/MgD655V5) |
| 2.0.0-Beta-3.1 | 12/10/2026 | [JF0rSDEY](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/JF0rSDEY) |
| 2.0-prerelease | 13/10/2026 | [EzkOG8cF](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/EzkOG8cF) |
| 2.0.0-Beta-4 | 15/10/2026 | [nAmjf9LH](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/nAmjf9LH) |
| 2.0-pre-release-2 | 15/10/2026 | [HYi0Szs6](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/HYi0Szs6) |
| 2.0-pre-release-3 | 16/10/2026 | [uVEW4V1J](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/uVEW4V1J) |
| 2.0.0-Beta-5 | 16/10/2026 | [mYi0ANUx](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/mYi0ANUx) |
| 2.0.0-Beta-6 | 19/10/2026 | [5scU0i96](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/5scU0i96) |
| 2.0-pre-release-4 | 19/10/2026 | [wkJ6vtdp](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/wkJ6vtdp) |
| 2.0.0-Pre-Release-5 | 25/10/2026 | [IDR4lPuz](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/IDR4lPuz) |
| 2.0.0 | 04/11/2026 | [aP8QzV0K](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/aP8QzV0K) |
| 2.0.1 | 06/11/2026 | [bJgeGffn](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/bJgeGffn) |
| 2.0.2 | 08/11/2026 | [j64Uveca](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/j64Uveca) |
| 2.0.3 | 09/11/2026 | [SeokxTA4](https://modrinth.com/mod/create-aeronautics-gadgets-and-gizmos/version/SeokxTA4) |

**Correções e complementos documentais:** 2.0.0 teve notas datadas de 03/11, mas upload em 04/11 UTC. As betas não sucedem todas as releases 1.2.x: foram publicadas em paralelo. A 1.2.9, distribuída como alpha e rotulada insegura, continha receita temporária de Oxidized Solid Fuel, correção de receitas mecânicas EMI, remoção de duplicação/armazenamento do grafo no Network Linker, seletor Scalar/Redstone nos Output nodes, ajuste do Advanced HUD Text Input e offset dos Smart Glasses pela roda do mouse. Não instalar essa build. As 1.2.3–1.2.8, 1.2.10 e pré-releases/betas já têm alterações individualizadas no histórico acima; conciliar as notas cumulativas sem contar duas vezes uma mudança. Em 2.0.1, além dos fixes previamente registrados, Redstone Link do tablet corrige atualização de frequência dos sliders e combinações de teclas modificadoras voltam a ser reconhecidas; em 2.0.3, Scissor Arm passa a usar machado de madeira ou superior em vez de picareta de ferro. O autor alerta para bugs potenciais da automação Worker e texturas provisórias de Smart Vault/Tank/Battery. Último arquivo confirmado `gadgets-and-gizmos-bundled-2.0.3.jar`, SHA-1 de distribuição `c5973b6e8f2e95537257fc809cc051336631f5cd` (API do Modrinth; não é hash de instalação).

**Conclusão documental:** lista publicada e changelogs da cadeia conciliados com a análise anterior. Mantida a certificação da versão física histórica, não promovida a 2.0.3. Migração manual de Get/Set Data, grafos, permissões e testes de ABI/dependências, incluindo Create/Aeronautics/Sable, ainda são gates de instalação. Nenhum runtime ou download binário foi testado. A conclusão documental é distinta da homologação física.
