# Create Aeronautics

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81fdbdb6f057eae6e1c6
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist(4).txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Create Aeronautics
- **Arquivo JAR:** `create-aeronautics-bundled-1.21.1-1.3.2.jar`
- **Versão 1.21.1:** `1.3.2`
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — Create Aeronautics bundle 1.3.2, physics/vehicle authority, assembly/mass, Sable boundary e módulos jar-in-jar confirmados no QC global #117. Decisão `Manter` preservada; runtime QA não executado.
- **Categoria:** Tecnologia; Exploração
- **Compatibilidade/Riscos:** Núcleo do stack físico/veicular. Riscos em mass/inertia, assembly/disassembly, block entities, controls, collision, Sable sublevels e addons que mixinam a física. 1.3.2 corrige mass do Swivel Bearing após assembly e JEI/creative-tab compatibility.
- **Decisão:** Manter
- **Dependências:** Create + Sable. O host top-level `create-aeronautics-bundled-1.21.1-1.3.2.jar` embarca três módulos 1.3.2: `aeronautics` (Create Aeronautics), `offroad` (Create Offroad) e `simulated` (Create Simulated). São componentes jar-in-jar subordinados ao bundle, não top-level separados.
- **Estado da pesquisa:** Verificado
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics
- **Função:** Extensão física de Create que usa Sable para transformar estruturas de blocos em veículos e contraptions físicas, cobrindo aeronaves, carros, drones, balões e outros assemblies móveis construídos pelo jogador.
- **Histórico da decisão:** Decisão formal `Manter` registrada em 30/08/2026: Create Aeronautics foi escolhido como núcleo do sistema de contraptions físicas/aeronaves. Em 08/09/2026, a decisão foi preservada e a ficha reconciliada à build física 1.3.2.
- **Observações:** Top-level mod id `aeronautics_bundled`, runtime 1.3.2. Jar-in-jar físico: `dev.eriksonn.aeronautics...1.3.2.jar` → mod id `aeronautics`; `dev.ryanhcode.offroad...1.3.2.jar` → `offroad`; `dev.simulated_team.simulated...1.3.2.jar` → `simulated`. Decisão `Manter` preservada.
- **Procedência:** modlist.txt física atual de 08/09/2026 (595 top-levels) + runtime bundle 1.3.2 + inventário jar-in-jar físico + CurseForge/Modrinth oficiais Create Aeronautics 1.3.2 + decisão histórica `Manter`.
- **Sobreposição:** Sable fornece a camada física/sublevel subjacente; Create fornece primitives de contraption/kinetics; Aeronautics define o vehicle/physics integration. Addons Aero estendem esse stack e não substituem o bundle.
- **Data da última decisão:** 2026-08-30

## Dossiê operacional — padrão Alex's Mobs

> ✈️ Versão física confirmada: `create-aeronautics-bundled-1.21.1-1.3.2.jar`, top-level `aeronautics_bundled`, runtime `1.3.2`, NeoForge 1.21.1. A decisão formal é **Manter**. O bundle usa **Create + Sable** para veículos e physics contraptions.

## 1. Papel e authority
Create Aeronautics transforma estruturas construídas com blocos em veículos/contraptions físicas: airships, planes, drones, cars, trucks e outros assemblies. Create continua authority das primitives cinéticas/blocos Create; Sable fornece a camada física/sublevel; Aeronautics controla a integração veicular, assembly e componentes próprios.

## 2. Bundle top-level
A distribuição instalada é o único JAR top-level `create-aeronautics-bundled-1.21.1-1.3.2.jar`. Em `/META-INF/jarjar/`, o host contém três módulos runtime `1.3.2`:
- `aeronautics` — Create Aeronautics;
- `offroad` — Create Offroad;
- `simulated` — Create Simulated.
Esses módulos pertencem ao bundle e **não são entradas top-level separadas**. Esse boundary evita duplicar versões/dependências ou avançar incorretamente a ordem da modlist.

## 3. Assembly e disassembly
Veículos são construídos a partir de estruturas do mundo e convertidos em physics contraptions. Durante assembly, bloco, block entity, inventory e attachment precisam migrar para um state móvel único; no disassembly retornam ao mundo.
Bridges externas devem invalidar referências ao world state anterior para evitar ghost blocks, dupe ou block entity stale.

## 4. Massa e centro físico
Massa é um input central da simulação. A **1.3.2 corrige o Swivel Bearing que não ajustava sua massa corretamente quando assembled**, tornando assembly→mass recomputation um regression gate explícito.
Addons não devem manter uma segunda massa independente para o mesmo body sem contract com Sable/Aeronautics.

## 5. Movimento e controle
Propulsão, steering e control surfaces/componentes próprios transformam input do jogador e forças do veículo em movimento físico. Client keybind é intenção; a simulação/state final deve permanecer sincronizado com a authority comum/server.

## 6. Collision e entidades
Physics contraptions interagem com mundo, players e entidades. Collision pode cruzar a collision layer do Create/Sable e addons. Um contato não deve causar damage/impulse duplicado só porque dois listeners reconhecem o mesmo body.

## 7. Block entities e inventories móveis
Blocks com inventories/capabilities podem viajar na estrutura. Ao mover, capability/storage ownership deve permanecer único. Addons de storage/fluids precisam usar bridges suportadas e re-resolver referências após assembly/disassembly.

## 8. Sable
Sable é dependência requerida na release 1.3.2. Ele fornece infraestrutura física sobre a qual Aeronautics opera. Isso significa:
- Sable não é substituto do Aeronautics;
- Aeronautics não deve ser tratado como physics engine isolada;
- updates precisam ser testados como um **stack versionado**.

## 9. Create
Create 6.0.10 é o provider das primitives de contraption/kinetics utilizadas pelo ecossistema. Componentes Create montados em veículo podem precisar de compat específica para continuar operando em sublevels móveis.
Não assumir que todo block entity de qualquer addon funciona em movimento sem bridge/teste.

## 10. JEI e creative tabs — 1.3.2
A release 1.3.2 corrige problemas de **JEI integration** e compatibilidade de **creative tabs com mods**. Recipe/tab discovery é client-facing; não altera recipe authority.
QA deve verificar que categorias/tabs aparecem uma única vez e que nenhuma integração cria duplicate entries.

## 11. Simulated/Offroad
Os módulos embarcados ampliam o bundle para superfícies simuladas/terrestres. Como são parte da mesma distribuição, a ficha os trata como subsistemas do bundle sem atribuir versões top-level independentes.

## 12. Integrações do pack
O pack possui vários addons de Aeronautics/Sable — camera sync, player tilt, ropes, radar, logistics, transmission/linkage, toolgun e bridges específicas. Cada um deve consumir o mesmo body/sublevel state; nenhuma extensão deve criar um segundo lifecycle de assembly.

## 13. Client/server e multiplayer
Physics state, assembly, ownership de veículo e state de blocks relevantes são common/server-authoritative conforme o stack. Rendering, câmera, tilt, HUD e keybinds são client-facing.
Em multiplayer, dois players controlando/interagindo com o mesmo vehicle não podem produzir duplicate state transitions.

## 14. Lifecycle
Validar:
- assembly/disassembly;
- chunk unload/reload;
- dimension transition quando suportado;
- disconnect/reconnect do piloto;
- server restart com vehicles existentes;
- block entity/capability invalidation;
- resource reload apenas para presentation;
- update Sable/Aeronautics em conjunto.

## 15. Riscos
1. Massa incorreta após assembly — regression 1.3.2.
2. Ghost blocks/block entities após disassembly.
3. Inventory/fluid capability duplicada entre world e sublevel.
4. Collision/damage aplicado duas vezes.
5. Addon compilado contra outra versão de Sable/Aeronautics.
6. Client control divergir do state físico.
7. Vehicle persistido perder ownership/state no restart.
8. Embedded module ser confundido com top-level.
9. JEI/creative tab duplicate registration.

## 16. Matriz de testes
1. Dedicated server boot com Create 6.0.10 + Sable + bundle 1.3.2.
2. Montar veículo simples e desmontar sem perda/dupe.
3. Swivel Bearing: validar mass antes/depois do assembly.
4. Inventories/block entities móveis e capability invalidation.
5. Player/entity collision e queda/impacto.
6. Piloto disconnect/reconnect.
7. Server restart com contraption existente.
8. JEI e creative tabs — regressões 1.3.2.
9. Addons Aero principais sobre a mesma contraption.
10. Multiplayer com dois players interagindo no mesmo vehicle.

## 17. Evidência
- modlist física 08/09/2026: bundle 1.3.2 + módulos jar-in-jar `aeronautics`, `offroad` e `simulated`, todos 1.3.2;
- CurseForge/Modrinth oficiais: Create + Sable, Client & Server, vehicles/physics contraptions;
- changelog 1.3.2: JEI/creative-tab fixes e mass correction do Swivel Bearing;
- catálogo histórico: decisão formal `Manter` desde 30/08/2026.

> 🔒 Boundary canônico: **Aeronautics controla o vehicle integration; Sable controla a camada física; Create controla primitives cinéticas/contraptions**. Addons devem respeitar esse trio de authorities.
