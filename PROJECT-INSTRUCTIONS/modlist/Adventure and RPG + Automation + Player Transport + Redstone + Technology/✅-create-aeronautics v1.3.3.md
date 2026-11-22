# Create Aeronautics — 1.3.3 (instalação declarada em 22/11/2026)

> **Vigente em 22/11/2026:** `1.3.2` → **`1.3.3`**; JAR oficial publicado `create-aeronautics-bundled-1.21.1-1.3.3.jar`, [CurseForge](https://www.curseforge.com/minecraft/mc-mods/create-aeronautics/files/9711672). Instalação confirmada pela declaração do usuário, não por leitura do JAR local ou metadados da nova build.

> **Auditoria das publicações intermediárias:** A 1.3.3 adiciona receita de dye de Envelopes e opção de rendering de Hot Air desligado automaticamente com Iris; corrige rendering de beams/ropes, Levitite, menu Wrench, constraint de Swivel Bearings após reload, lag de explosões em Honey Glue e introduz validações server-side de distância e itens em packets. A versão anterior 1.3.2 já tinha mass correction/JEI/creative tab fixes.

> **Dependências aguardando sincronização documental:** Create e Sable são providers do stack. Módulos aeronautics/offroad/simulated embarcados no JAR bundled não são mods top-level nem devem virar entradas próprias. Outras integrações Aeronautics aguardam lotes correspondentes.

> **Riscos/limites de evidência:** Veículos e contraptions exigem validação de massa, persistência de constraints, inventário, reload e autorização multiplayer; não houve execução do jogo.

> **Snapshot físico histórico (posição #117):** create-aeronautics-bundled-1.21.1-1.3.2.jar, 1.3.2; dados referentes à modlist física anterior à declaração de atualização.


> **Autoridade física atual — 22/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#117**: JAR `create-aeronautics-bundled-1.21.1-1.3.2.jar`, mod id `aeronautics_bundled`, runtime `1.3.2`, SHA-1 `ddbe9cbc25e8f663218aa1e9fdfc4b99f0e11e4b`.

## Propriedades do registro

- **Mod:** Create Aeronautics
- **Arquivo JAR instalado (declarado pelo usuário):** `create-aeronautics-bundled-1.21.1-1.3.3.jar` — [distribuição](https://www.curseforge.com/minecraft/mc-mods/create-aeronautics/files/9711672); local não relido.
- **Versão instalada declarada (Minecraft 1.21.1 / NeoForge):** `1.3.3` (antes `1.3.2`; metadata do novo JAR não lido).
- **Categoria:** Tecnologia; Exploração
- **Decisão:** Manter
- **Estado da pesquisa:** versão-alvo e distribuição verificadas; lançamentos intermediários documentados no dossier; lacunas e limites declarados no cabeçalho.
- **Estado no pack:** `1.3.3` declarado instalado pelo usuário em 22/11/2026; inventário documental sincronizado; sem inspeção local.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics
- **Função:** Extensão física de Create que usa Sable para transformar estruturas de blocos em veículos e contraptions físicas, cobrindo aeronaves, carros, drones, balões e outros assemblies móveis construídos pelo jogador.
- **Dependências:** Create e Sable são providers do stack. Módulos aeronautics/offroad/simulated embarcados no JAR bundled não são mods top-level nem devem virar entradas próprias. Outras integrações Aeronautics aguardam lotes correspondentes.
- **Compatibilidade/Riscos:** Veículos e contraptions exigem validação de massa, persistência de constraints, inventário, reload e autorização multiplayer; não houve execução do jogo.
- **Sobreposição:** Sable fornece a camada física/sublevel subjacente; Create fornece primitives de contraption/kinetics; Aeronautics define o vehicle/physics integration. Addons Aero estendem esse stack e não substituem o bundle.
- **Observações vigentes:** distribuição `create-aeronautics-bundled-1.21.1-1.3.3.jar`; versão `1.3.3` declarada instalada, sem metadata/SHA local. Dados anteriores na documentação detalhada são históricos.
- **Procedência:** posição física histórica #117; declaração de instalação em 22/11/2026 e publicação oficial [CurseForge](https://www.curseforge.com/minecraft/mc-mods/create-aeronautics/files/9711672); nenhum JAR local atual reinspecionado.
- **Histórico da decisão:** Decisão formal `Manter` registrada em 30/08/2026: Create Aeronautics foi escolhido como núcleo do sistema de contraptions físicas/aeronaves. Em 08/09/2026, a decisão foi preservada e a ficha reconciliada à build física 1.3.2.
- **Atualização/Status:** 22/11/2026 — `1.3.2` → `1.3.3` documentado. A 1.3.3 adiciona receita de dye de Envelopes e opção de rendering de Hot Air desligado automaticamente com Iris; corrige rendering de beams/ropes, Levitite, menu Wrench, constraint de Swivel Bearings após reload, lag de explosões em Honey Glue e introduz validações server-side de distância e itens em packets. A versão anterior 1.3.2 já tinha mass correction/JEI/creative tab fixes.
- **Data da última decisão:** 2026-08-30

# Dossiê operacional — padrão Alex's Mobs
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

## Auditoria incremental upstream — Create Aeronautics 1.3.3 (22/11/2026; não instalada)

Baseline top-level #117 `create-aeronautics-bundled-1.21.1-1.3.2.jar` / `1.3.2`. Os componentes embarcados do bundle (Sable/Simulated/Offroad/Aeronautics, conforme o artefato exato) **não** geram registros top-level autônomos na tabela.

A [**1.3.3, arquivo oficial CurseForge 9711672**](https://www.curseforge.com/minecraft/mc-mods/create-aeronautics/files/9711672), publicada em **20/11/2026** para NeoForge/Minecraft 1.21.1, distribui `create-aeronautics-bundled-1.21.1-1.3.3.jar`. O changelog específico documenta:
- Novas **receitas para tingir Envelopes**.
- Nova configuração para **desativar o rendering de Hot Air**; este rendering é desativado automaticamente quando shaders **Iris** estão ativos.
- Corrige **beams e ropes** renderizados incorretamente pelo **Creative Physics Staff** e pelo **Plunger Launcher** com Iris; corrige VFX de **Levitite** em certas rotações e textos das notificações de música.
- Remove blocos internos da lista de rotação do menu de wrench.
- Corrige caso no qual **Swivel Bearings perdiam constraint após reload**; é relevante à persistência/physics de contraptions em saves.
- Corrige **Honey Glue** que não ignorava explosões, causando lag.
- Acrescenta **validações server-side de distância e itens em diversos packets**, relevante à autorização e integridade de multiplayer. O changelog não lista os packets exatos; não presumir proteção completa sem inspeção de source.

**QA e compatibilidade:** Create 6.0.10, Sable 2.0.5 no histórico, aeronaves persistidas, constraints, physics/reload, superfícies Honey Glue, bombas/lançadores e Iris shaders, data/control packets, ACL e teste com cliente malformado em staging. Conferir manifest dos JARs 1.3.2/1.3.3 e embedded modules; **sem instalação, teste, mudança de config ou indexação de submods embarcados**. Categorias oficiais e basename físico mantidos.
