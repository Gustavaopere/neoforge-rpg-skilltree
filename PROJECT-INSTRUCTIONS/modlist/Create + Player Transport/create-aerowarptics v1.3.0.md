# Create: AeroWarptics — 1.3.0

> **Adição solicitada — 03/10/2026.** O usuário determinou a entrada de **Create: AeroWarptics** como parte da substituição de Create Aeronautics: Alcubierre. O snapshot físico verificável mais recente é anterior a essa solicitação; por isso esta ficha documenta o alvo upstream **1.3.0**, mas não inventa SHA-1, metadata carregada ou ordem física antes de um novo dump do perfil.

> **Status de certificação:** **SEM `✅-`**. É uma ficha nova pós-migração. Estado: **PENDENTE DE CONFIRMAÇÃO FÍSICA E SMOKE TEST**.

## Propriedades do catálogo

- **Mod:** Create: AeroWarptics
- **Arquivo JAR alvo:** `aerowarptics-1.3.0.jar`
- **Versão upstream publicada:** `1.3.0`
- **Minecraft / loader:** 1.21.1 / NeoForge
- **Ambiente:** client + server
- **Categoria CurseForge:** Create; Player Transport
- **Mod id upstream:** `aerowarptics`
- **Projeto CurseForge:** ID 1659672
- **File ID 1.3.0:** 8787426
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aerowarptics
- **Source oficial:** https://github.com/ICEconchy/Aerowarptics
- **Função:** sistema de warp para airships/vehicles do ecossistema Create: Aeronautics, com Rift Drives, destinos/anchors, gates e efeitos de rift; move hull, machinery, cargo e crew por meio do pipeline de physics/sublevels.
- **Decisão:** Adicionar como substituto parcial do Alcubierre no domínio de warp.
- **Estado físico:** solicitado em 03/10/2026; confirmação por novo dump pendente.
- **Licença:** **DIVERGENTE ENTRE FONTES** — CurseForge exibe “All Rights Reserved”; o `LICENSE` do source oficial declara CC BY-NC-SA 4.0. Não normalizar esse campo sem resolução upstream.
- **Risco principal:** projeto recente e de alta superfície; a branch `main` já contém correções 1.3.1 ainda não publicadas no CurseForge para save/force-loading, launch clearance e large/multi-sub-level ships.

# Dossiê operacional — padrão Alex's Mobs

## 1. Papel no modpack

AeroWarptics adiciona uma camada de **travel/warp de contraptions físicas** sobre Create: Aeronautics. Um Rift Drive instalado no vehicle recebe força rotacional de Create, mantém curso/destino e abre uma rift pela qual a estrutura é transportada.

O mod não é um sistema genérico de teleporte de jogador nem um substituto de portal vanilla. Seu objeto operacional é o **vehicle/airship como sublevel físico**, incluindo máquinas e passageiros.

## 2. Authority / ownership

- **Create:** kinetic network, stress/rotation e infraestrutura mecânica.
- **Sable:** sublevels, body/physics pose, transformação entre espaço local e world, teleporte do corpo e persistência de sublevel.
- **Create: Aeronautics / Simulated:** assembly/airship vehicle e controles físicos.
- **AeroWarptics:** curso de warp, Rift Drives, anchors/probes/beacons/gates, custo de Rift Essence, aperture/corridor e regras próprias de entrada/saída do warp.

AeroWarptics não deve assumir authority sobre inventário interno, fluid storage ou state de uma máquina transportada: ele deve preservar esses estados ao mover o sublevel.

## 3. Dependências e baseline

A página CurseForge declara dependências obrigatórias:
- Create;
- Create Aeronautics;
- GeckoLib.

O source atual também explicita o contrato interno:
- Minecraft `[1.21.1,1.22)`;
- NeoForge `[21,)`;
- Create `[6.0.10,)`;
- Sable `[2.0.0,3.0.0)`;
- Simulated `[1.3.0,)`;
- Aeronautics `[1.3.0,)`;
- GeckoLib `[4.8.0,)`;
- JEI `[19.0.0,)` opcional.

O inventário físico conhecido do pack registra Create 6.0.10, Create Aeronautics 1.3.2, Sable 2.0.5 e GeckoLib 4.9.2, todos na linha nominal exigida. O componente `simulated` faz parte do ecossistema/bundle de Create Aeronautics e deve ter sua metadata efetiva reconfirmada no JAR físico antes de declarar o dependency gate fechado.

## 4. Rift Drives

O README/source documenta cinco tiers:
- Mk I;
- Mk II;
- Mk III;
- Singularity;
- Creative.

Cada tier possui requisitos próprios de RPM, stress, range, charge time, cooldown e efficiency. Esses valores são defaults de config, não constantes imutáveis.

A mecânica cruza:
- kinetic stress de Create;
- estado persistente por ship;
- target/course;
- clearance de lançamento e chegada;
- cooldown;
- effects/rendering client-side.

## 5. Warp Anchors, curso e destinos

Warp Anchors registram destinos que podem ser selecionados pelo sistema de curso. O fluxo não equivale ao antigo Alcubierre Controller de coordenada/dimensão direta e não existe migração automática conhecida de dados entre os dois mods.

A 1.3.x também trabalha com probes/beacons e sistemas de curso/destino ampliados. Para manutenção, destinos devem ser tratados como state do AeroWarptics e não como waypoints universais compartilhados com outros teleport mods.

## 6. Rift Gates e Rift Portal

Na 1.3.0, a abertura de um gate passou a ser um **Rift Portal block real**, em vez de apenas efeito client-side. O portal:
- mantém a forma real do ring/gate;
- emite/participa do lighting do mundo;
- persiste visualmente com chunk load;
- pode ser atravessado em gates no mundo e em ships.

Manter gates abertos passa a consumir **Rift Essence ao longo do tempo**, com custo base + custo por bloco. Apenas a ponta que discou paga; os valores são configuráveis.

Isso cria novas superfícies de lifecycle: chunk unload/reload, gate endpoint loss, resource depletion, ship movement e entity crossing.

## 7. Rift Modulator e temas

A 1.3.0 adiciona **Rift Modulator**, com:
- core colour e rim colour;
- intensidade 25–200%;
- cinco temas publicados: Standard, Clockwork, Arcane, Ember e Starlight;
- efeitos aplicados à aperture, corridor e arrival shockwave;
- consumo de Rift Essence durante warp.

Também há rift lightning configurável e presentation layers client-side. Esses efeitos não são authority para confirmar que o teleporte foi liquidado no servidor.

## 8. Rift Essence e fissures

A linha anterior introduziu Rift Fissures/Scars, Rift Infused Goggles e Spatial Siphon para descoberta/coleta de Rift Essence. A 1.3.0 reaproveita esse recurso em gates/modulation.

O ecossistema deve ser testado em chunks novos/existentes, porque qualquer conteúdo ligado a estrutura/world placement ou scans pode interagir com outros worldgen providers.

## 9. 1.3.0 — mudanças relevantes

### Adições
- Rift Modulator;
- cinco rift themes;
- rift lightning;
- integração de Display Link em máquinas;
- novos Ponder scenes/advancements;
- polish de UI/theme buttons.

### Mudanças
- gate aperture se torna Rift Portal block real;
- upkeep de gate passa a consumir essência;
- Rift Drives recebem novo modelo tesseract;
- Singularity instability passa a afetar efetivamente a saída do warp.

### Correções
- gates em ships passam a funcionar nos dois sentidos;
- remove idle-disconnect timer de gates;
- corrige incompatibilidade ligada a algoritmo/JDK;
- estabiliza warp;
- corrige transparência de tanks e escala de GUIs.

## 10. Estado upstream além do CurseForge

**Não confundir source `main` com release publicada.** Em 03/10/2026, CurseForge continua em **1.3.0**, mas o source oficial já declara `mod_version=1.3.1` e possui commits ainda não publicados como release CurseForge.

Esses commits incluem correções materialmente relevantes:
- server/world hang por force-loading/residency de plot chunks;
- world preso em “Saving worlds” com drive;
- crash quando plot é liberado sob ticking block entity;
- large ships recusados ou desaparecendo na chegada;
- multi-sub-level ships falsamente bloqueados;
- clearance/aperture incorretos por propeller sweep boxes;
- launch clearance inconsistente;
- novos Rift Storms e arrival-height controls.

**Implicação:** 1.3.0 é a release publicada, mas há evidência upstream de bugs corrigidos apenas no desenvolvimento 1.3.1. Para um modpack principal, isso aumenta o peso do smoke-test e recomenda testar em cópia de mundo antes de promoção definitiva.

## 11. Substituição do Alcubierre

### Sobreposição
Ambos fornecem **warp de physics ships**.

### Diferenças
Alcubierre:
- Antigravity Drive;
- controller com warp direto para coordenadas/dimensão;
- dependência explícita de Dimensional Sable em sua linha recente.

AeroWarptics:
- Rift Drives com tiers, stress/charge/cooldown;
- anchors/course;
- rift gates/portals;
- Rift Essence;
- themes/modulator;
- integração mais extensa com o vehicle/sublevel lifecycle.

### Perda funcional
AeroWarptics **não substitui o Antigravity Drive**. Ao remover Alcubierre, a antigravidade específica daquele mod deixa de existir salvo provider alternativo explicitamente configurado.

### Migração
Não há evidência de migração automática de:
- blocks/items;
- controller coordinates;
- destination data;
- configs;
- persistent ship state.

A troca deve ser feita como remoção + instalação separadas, não como upgrade in-place.

## 12. Client / server e networking

Warp settlement, ship teleport, destination validation, essence/cost e passenger transfer devem ser server-authoritative.

O cliente é responsável por:
- renderer/model GeckoLib;
- rift effects;
- corridor/shockwave;
- GUIs/overlays;
- lightning/theme presentation.

A confirmação visual não pode ser usada como prova única de que server state foi persistido.

## 13. Lifecycle crítico

Validar:
1. assemble ship;
2. instalar/alimentar Rift Drive;
3. registrar destino;
4. charge;
5. launch;
6. transit;
7. arrival;
8. passenger reattachment;
9. chunk unload/reload;
10. logout/relogin;
11. server restart;
12. ship disassembly/reassembly;
13. gate em world ↔ gate em moving ship;
14. save during/after warp.

## 14. Persistência e integridade

O source usa state associado ao sublevel/ship e APIs Sable de transformação/teleporte. Por isso os riscos principais são:
- stale ship residency;
- forced-chunk leak;
- target/course state stale;
- passengers não reposicionados;
- block entities/inventories/fluids divergentes após teleport;
- constraints/kinetics não retomarem;
- save hang/crash em teardown de sublevel.

Os fixes já presentes no source 1.3.1 tornam esses pontos **regression gates obrigatórios** para 1.3.0.

## 15. Multiplayer

Testar pelo menos:
- piloto + passageiros;
- observador remoto;
- dois jogadores atravessando gate em sentidos opostos;
- reconnect após warp;
- passageiro desconectando antes/depois da transição;
- vehicle com seats/inventories/fluids ativos;
- permissions/claims caso existam em destino.

## 16. Performance

As áreas mais sensíveis são:
- chunk residency/force-loading;
- clearance scans;
- rendering de rift/lightning;
- gates persistentes;
- physics teleport de assemblies grandes.

O próprio histórico upstream de 1.3.1 corrige force-loading excessivo. Não assumir performance segura em large ships apenas porque um ship pequeno funciona.

## 17. Compatibilidade e overlaps

- **Create Aeronautics:** dependência/base, não redundância.
- **Sable:** provider físico/sublevel.
- **Aeroworks:** controle/estabilização; não substitui warp.
- **Alcubierre:** sobreposição forte no warp; removido por decisão atual, mas antigravidade não migra.
- **Waystones/portals gerais:** não equivalentes; operam outro domínio.
- **Shaders:** Create Aeronautics já documenta problemas visuais com Iris; effects de rift devem ser testados junto do shader stack efetivo.
- **JEI:** opcional para description pages segundo o manifest upstream atual.

## 18. Configuração relevante

Config defaults/publicados incluem parâmetros de:
- Rift Drive tiers;
- range/charge/cooldown/efficiency;
- launch/clearance behavior;
- gate upkeep;
- rift lightning;
- effects/theme behavior;
- ship residency/loading.

Antes de KubeJS/quests/automation dependerem desses valores, ler o config efetivamente gerado pela versão instalada.

## 19. Riscos operacionais

1. **Save hang / force-loading** — corrigido em commits 1.3.1 ainda não publicados.
2. **Large ship warp** — bugs de refusal/disappearance aparecem no histórico 1.3.1.
3. **Multi-sub-level false collision** — corrigido upstream depois da 1.3.0.
4. **Passenger/state transfer** — risco de desync após physics teleport.
5. **Gate on moving ship** — superfície corrigida na 1.3.0, ainda exige regressão.
6. **Chunk leak** — testar unload depois que ship deixa a área.
7. **Shader/render** — rift + Aeronautics + shader stack.
8. **Config/world persistence** — target/course/residency após restart.
9. **Registry removal do Alcubierre** — mundos antigos precisam de cópia/backup.
10. **Simulated dependency metadata** — reconfirmar no bundle físico.

## 20. Matriz de testes para esta instância

- [ ] confirmar `aerowarptics-1.3.0.jar`, mod id, metadata e SHA-1 em novo snapshot;
- [ ] dependency scan: Create 6.0.10+, Sable 2.x, Simulated 1.3+, Aeronautics 1.3+, GeckoLib 4.8+;
- [ ] client boot;
- [ ] dedicated server boot;
- [ ] criar mundo de teste novo;
- [ ] abrir cópia de mundo existente;
- [ ] Mk I → Creative: charge/cooldown/range;
- [ ] small ship e large ship;
- [ ] inventories, tanks, redstone, kinetic blocks e block entities;
- [ ] seats + múltiplos passageiros;
- [ ] warp, save/reload, restart;
- [ ] chunk unload depois do warp;
- [ ] gate world↔world, world↔ship e ship↔world;
- [ ] Rift Modulator/themes;
- [ ] shader stack;
- [ ] perf/forced chunks;
- [ ] confirmar ausência do JAR Alcubierre no estado-alvo;
- [ ] confirmar que a perda de Antigravity Drive é aceita.

Nenhum teste acima foi marcado como executado nesta atualização documental.

## 21. Histórico público de releases revisado

### 1.0 BETA — 19/08/2026
Primeiro upload público, Alpha, com features-base e placeholder blocks/item models.

### 1.2.0 — 23/08/2026
Adiciona Rift Beacon, Rift Fissures, Rift Infused Goggles e Astrolabe; melhora modelos; corrige crash de server em mundos explorados causado por listas grandes de anchors/gates e corrige offsets/stutter do mapa projetado do Astrolabe.

### 1.3.0 — 01/09/2026
Release atual do CurseForge; adiciona Rift Modulator/themes/lightning, transforma gate aperture em Rift Portal real, adiciona upkeep, melhora gates em moving ships e estabiliza warp.

## 22. Evidências e limites

**Alta confiança:** versão publicada, loader/game version, categorias e dependências CurseForge; source oficial e manifest atual.

**Média/pendente:** presença física local, SHA-1, metadata runtime e posição no inventário após a solicitação de 03/10/2026.

**Divergência:** licença CurseForge “All Rights Reserved” vs source `LICENSE` CC BY-NC-SA 4.0.

**Limite crítico:** source `main` está à frente da release pública. Bugs/fixes de 1.3.1 são usados aqui como evidência de risco da 1.3.0, não como recursos já instalados.

> **Boundary canônico:** AeroWarptics é provider de **warp/rift de airships**. Sable/Aeronautics continuam providers de physics/sublevels/assembly; Create continua provider de kinetic stress. A substituição de Alcubierre é parcial porque antigravidade não migra.
