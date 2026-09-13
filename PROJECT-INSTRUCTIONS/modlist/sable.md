# Sable

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db814399cefd33348035d2
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `sable-neoforge-1.21.1-2.0.5.jar`, mod id `sable`, runtime `2.0.5`, mixins `sable.mixins.json` e `sable-neoforge.mixins.json`; `sable_rapier` 2.0.5, Sable Companion common 1.6.0 e Veil NeoForge 4.3.2 embarcados; Create 6.0.10 presente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Sable 2.0.5, seus módulos embarcados e Create 6.0.10 estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Sable
- **Arquivo JAR:** `sable-neoforge-1.21.1-2.0.5.jar`
- **Versão 1.21.1:** 2.0.5
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Biblioteca, Compat
- **Função:** Infraestrutura física intrusiva para sub-levels: estruturas móveis com chunks, entidades e block entities reais em pose dinâmica, base do ecossistema Aeronautics e de numerosos compats do pack.
- **Dependências:** JAR inclui `sable_rapier` 2.0.5, Sable Companion common 1.6.0 e Veil NeoForge 4.3.2 embarcados. Create 6.0.10 é integração concreta presente; vários addons/bridges atuais dependem funcionalmente de Sable.
- **Sobreposição:** Não é duplicata do Create. Sable owns sublevels/physics/transforms; Create owns kinetics/contraptions. Bridges e addons devem respeitar essa boundary.
- **Compatibilidade/Riscos:** Infraestrutura altamente intrusiva com mixins extensos. Riscos: coordinate-space errors, invalid body handles, save/tracking corruption, chunk/forceload leaks, duplicate BE/gameplay ticks, networking divergence, provider API drift e physics-data migration. 2.0.5 corrige crash de server ao montar contraptions Create sem massa.
- **Observações:** Source público `main` declara version 2.0.5 / Minecraft 1.21.1, permitindo mapear bootstrap e APIs da mesma linha instalada. Componentes embarcados pertencem ao JAR e não viram entradas top-level.
- **Procedência:** modlist.txt física canônica de 10/09/2026 + CurseForge oficial Sable 2.0.5 + repositório/wiki oficial 2.0.5/current + changelog 2.0.0 usado apenas como lineage de regressão.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/sable
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Sable 2.0.5 reconstruído extensivamente: sublevels, transforms, Rapier/Companion/Veil embedded, physics datapacks, dimension physics, save/network lifecycle, Create integration, riscos e testes.
- **Histórico da decisão:** Correção de auditoria em 22/08/2026: esta linha é Sable e havia recebido por engano histórico/versão de Northstar Redux. Sable 2.0.5 permanece instalado como infraestrutura do ecossistema Aeronautics/sublevels; decisões sobre Northstar, Creating Space e Stellaris pertencem a registros separados.
- **Data da última decisão:** 2026-08-22

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `sable-neoforge-1.21.1-2.0.5.jar`, mod id `sable`, versão `2.0.5`, NeoForge 1.21.1. Sable é uma infraestrutura física intrusiva para **sub-levels**: estruturas móveis que continuam contendo chunks, entidades e block entities Minecraft normais enquanto existem em posição/orientação dinâmica dentro de um level. A decisão vigente **Manter** é estrutural: vários addons e bridges do pack dependem de Sable.

## 1. Identidade e papel
- **Mod:** Sable.
- **JAR:** `sable-neoforge-1.21.1-2.0.5.jar`.
- **Mod id:** `sable`.
- **Versão instalada:** `2.0.5`.
- **Minecraft:** 1.21.1.
- **Loader:** NeoForge.
- **Java:** 21 no ambiente do pack.
- **Canal:** Release.
- **Ambiente publicado:** Client & Server.
- **Mixin configs físicos:** `sable.mixins.json` e `sable-neoforge.mixins.json`.

## 2. Componentes embarcados do JAR
A modlist física mostra, dentro do JAR Sable 2.0.5:
- `dev.ryanhcode.sable.sable-sable_rapier-1.21.1-2.0.5.jar` — módulo de física Rapier;
- `sable-companion-common-1.21.1-1.6.0.jar` — API leve de compatibilidade/projeção;
- `veil-neoforge-1.21.1-4.3.2.jar` — componente render embarcado;
- bibliotecas internas adicionais do Veil, como GLSL Processor e Molang Compiler.

Esses componentes pertencem ao empacotamento do Sable e **não recebem entradas top-level separadas** neste catálogo.

## 3. Conceito central: sub-levels
A documentação oficial define Sable como uma library intrusiva para estruturas móveis interativas chamadas **sub-levels**. Um sub-level contém chunks, entidades e block entities normais, porém vive em pose dinâmica — posição e orientação próprias — dentro de um Minecraft level.

Isso permite que uma estrutura móvel mantenha lógica Minecraft real em vez de ser apenas um modelo renderizado. Consequentemente, quase todo sistema que presume coordenadas world-space fixas pode exigir compatibilidade explícita.

## 4. Autoridade e ownership
- **Sable:** ownership do sublevel, pose/transform, body físico, tracking e infraestrutura de física.
- **Minecraft/NeoForge:** continuam authority dos registries, chunks, entidades/block entities e regras-base do jogo dentro de cada contexto.
- **Create:** continua authority de contraptions, cinética e assembly Create quando participa do fluxo.
- **Addons Sable/Aeronautics:** adicionam regras/conteúdo sobre a infraestrutura, mas não devem manter um segundo transform/body canônico paralelo.
- **Bridges de compatibilidade:** convertem coordenadas/ownership na boundary entre Sable e providers externos.

## 5. Intrusividade e mixins
O README oficial contém um aviso explícito: Sable é **incredibly intrusive**, usa mixins extensivamente e é propenso a problemas de compatibilidade com outros mods.

Isso deve ser tratado como propriedade arquitetural, não como motivo automático de remoção. Em um pack com muitos mods, cada atualização do Sable precisa ser considerada uma mudança de infraestrutura de alto impacto.

## 6. Bootstrap confirmado pelo source 2.0.5
O branch público atual declara `version=2.0.5` e `minecraft_version=1.21.1`, alinhado ao JAR físico. No bootstrap comum, o projeto inicializa superfícies como:
- TCP packets do Sable;
- tags do Sable;
- tipos de propriedades físicas de blocos;
- grupos de força.

O source também inicializa containers de sublevel com sistemas de física e tracking no servidor. Esses pontos sustentam o dossiê sem extrapolar classes de versões diferentes.

## 7. Propriedades físicas de blocos
Sable permite definir propriedades físicas por **block-state** via datapacks. A documentação 2.0.5/current registra propriedades padrão como:
- `sable:mass` — massa; default 1.0;
- `sable:inertia` — multiplicador de inércia por eixo; default `[1/6, 1/6, 1/6]`, aplicado em conjunto com massa;
- `sable:volume` — volume usado para buoyancy; default 1.0;
- `sable:restitution` — bounciness 0–1; default 0.0;
- `sable:friction` — multiplicador de atrito; default 1.0;
- `sable:fragile` — quebra por impacto; default false;
- `sable:floating_material` — material de floating block; default null;
- `sable:floating_scale` — multiplicador correspondente; default 1.0.

Essas propriedades são data-driven e podem ser sobrescritas por selector, prioridade e condições de block-state.

## 8. Tags físicas built-in
A documentação fornece tags built-in para casos comuns, por exemplo famílias de massa leve/pesada, volume reduzido, superfície slippery e bouncy.

O uso de tags é preferível quando um mod só precisa declarar comportamento padrão; definições JSON custom são necessárias quando o bloco exige valores específicos ou overrides de estado.

A presença da API não prova quais blocos do pack têm overrides custom além dos dados carregados.

## 9. Física por dimensão
Datapacks podem definir `/data/<namespace>/dimension_physics/<name>.json` com parâmetros físicos por dimensão. Campos documentados incluem:
- `dimension` obrigatório;
- `priority`, default 1000;
- `base_gravity`, default `[0.0, -11.0, 0.0]` m/s²;
- `base_pressure`, default 1.0.

Isso significa que gravity/pressure não devem ser hardcoded por integrações próprias: a dimensão e o data stack podem alterar a base física.

## 10. Sable Companion e coordenadas
Sable Companion existe para compatibilidade leve. Sua documentação explica que posições internas de sublevels vivem em uma plot grid com valores extremos e precisam ser projetadas para global/world space quando comparadas com elementos externos.

Companion expõe utilidades para:
- localizar sublevel que contém determinada posição;
- projetar posição para fora do sublevel;
- calcular distância global entre pontos em world/sublevels.

Erros de compatibilidade comuns surgem quando um mod usa coordenadas locais/plot diretamente como se fossem world coordinates.

## 11. Physics pipeline e Rapier
O módulo `sable_rapier` 2.0.5 está embarcado e fornece a implementação de física baseada no engine Rapier, citado pelo próprio projeto como pipeline padrão.

Sable continua authority da integração Minecraft↔physics pipeline. Addons não devem armazenar ponteiros/handles como state eterno sem respeitar lifecycle do body, pois unload/removal pode invalidá-los.

## 12. Mass tracking e assembly
A massa de um sublevel deriva das propriedades físicas dos blocos e do assembly correspondente. O delta **2.0.5** corrige especificamente um crash de servidor ao montar **Create contraptions sem massa**.

Esse fix torna massless assembly um regression gate obrigatório. Uma integração que produz massa zero precisa resultar em fallback/estado válido, não body inválido ou crash.

## 13. Create e contraptions
Create é uma das integrações mais importantes do Sable. A linha 2.0 trouxe extensa manutenção para rope pulley, mechanical arms, assembly, explosions, item duplication em algumas block entities e outras boundaries Create.

Esses itens de 2.0.0 são lineage da linha atual, não deltas exclusivos da 2.0.5. Continuam como regression tests porque o pack usa Create 6.0.10 intensamente.

## 14. Force-loading e unload de sublevels
A linha 2.0 adicionou `/sable forceload <add|remove>` e configuração para impedir unload de sublevels contendo jogadores.

Lifecycle precisa distinguir:
- sublevel ativo e rastreado;
- sublevel descarregável;
- sublevel forceloaded;
- sublevel contendo player;
- body físico removido/desmontado.

Tickets/containers não podem sobreviver indefinidamente após remoção, nem descarregar um sublevel ainda necessário.

## 15. Persistência e save
Sublevels precisam serializar pose, conteúdo e state físico suficiente para reconstrução após reload/restart. A linha 2.x já recebeu mudanças/configs relacionadas a mensagens de save de sublevels.

Smoke tests essenciais:
- save durante movimento;
- restart com sublevel existente;
- crash/recovery em cópia de mundo;
- unload/reload de chunks que hospedam tracking data;
- desmontagem antes/depois de save.

Não marcar persistência como segura apenas porque a estrutura reaparece visualmente.

## 16. Networking
A linha 2.0 possui TCP networking próprio e config client-side relacionada a tentativa de UDP networking. A escolha de transporte/config não muda ownership: o servidor continua authority da pose/state físico.

Networking deve sincronizar transform/velocity/state sem o cliente criar body alternativo. Packet loss/reorder precisa resultar em correção/interpolação, não teleporte permanente ou divergência de collision.

## 17. Client / Server e render
Servidor:
- body/physics state;
- sublevel lifecycle;
- entity/block entity tick funcional;
- force/constraint authority;
- save/tracking.

Cliente:
- render pose/interpolation;
- visualização de chunks/sublevels;
- efeitos e compat de render.

Sodium/Veil/Create render integrations não devem alterar a pose funcional apenas para corrigir visual.

## 18. Multiplayer
Cada cliente pode observar o mesmo sublevel de ângulos/latências diferentes, mas todos precisam convergir para o mesmo state autoritativo. Testar:
- dois players no mesmo sublevel;
- player no world observando player dentro do sublevel;
- entrar/sair da estrutura em movimento;
- reconnect durante movimento;
- spectator;
- mudança de dimensão/portal com sublevel próximo.

Velocity inheritance e player tracking são boundaries historicamente sensíveis na linha 2.0.

## 19. Portais, POI, entidades e vanilla systems
A lineage 2.0 registra correções em áreas como:
- nether portal linking;
- POI updates durante assembly;
- loyalty tridents;
- wind/breeze charges;
- crawling/standing;
- particles;
- spectator velocity.

Isso evidencia por que Sable é intrusivo: sistemas vanilla que usam posição, chunk ou velocity podem precisar ser transform-aware.

Não declarar cada fix histórico como bug atual da 2.0.5; usá-los como regression surfaces.

## 20. Block entities e ticking
Sublevels contêm block entities reais. APIs current 2.0.5 incluem hooks para atores de block entity em sublevels e o source distingue game ticks de physics ticks — múltiplos physics ticks podem ocorrer por game tick dependendo do pipeline.

Integrações não devem executar lógica de gameplay novamente em cada physics tick quando deveriam executar uma vez por server tick.

## 21. Integrações concretas da modlist
Neste trecho e no pack há diversos consumers/bridges reais:
- Create Aeronautics 1.3.2;
- Sable Dynamic Lights 2.0.1;
- Sable x CPM 0.3.2;
- Sable Create Addition Compat 0.1.13;
- Sable / Flowing Fluids Compat 1.0.2;
- Sable Ragdolls 0.7.5;
- Sable Ragdolls Patch 1.9;
- Sable Beyond 0.5.0;
- vários outros compats Sable catalogados separadamente.

Isso torna Sable uma dependência estrutural do pack, não uma feature isolada.

## 22. Performance
Custos potenciais incluem:
- physics steps;
- transform/tracking de muitos chunks/entities;
- render de sublevels;
- networking de pose/velocity;
- collision queries;
- block entity ticking.

Avaliar com número real de sublevels, tamanho e movimento. Uma estrutura parada pequena não representa o pior caso de Aeronautics/pack grande.

## 23. Riscos técnicos
1. **Mixin conflict:** extensa superfície de mixins com outros mods.
2. **Coordinate-space bug:** local/plot/world confundidos.
3. **Invalid body handle:** consumer usa body removido.
4. **Save corruption/stale index:** crash durante save/unload.
5. **Massless assembly:** regression direta do fix 2.0.5.
6. **Duplicate gameplay tick:** physics tick confundido com game tick.
7. **Chunk/sublevel leak:** tracking/forceload não é liberado.
8. **Networking divergence:** pose/velocity cliente-servidor não convergem.
9. **Block entity duplication:** assembly/desassembly copia state duas vezes.
10. **Portal/POI/entity boundary:** sistemas vanilla não transform-aware.
11. **Provider API drift:** Create/Sodium/addons esperam outra API.
12. **Physics data drift:** datapack de massa/gravity muda e estruturas existentes entram em state inesperado.

## 24. Matriz de testes
- [ ] Dedicated server inicia com Sable 2.0.5 e stack físico atual.
- [ ] Sublevel simples monta, move, gira e desmonta preservando blocks/entities/BEs.
- [ ] Create contraption com massa normal monta e opera.
- [ ] Contraption massless não reproduz o crash corrigido na 2.0.5.
- [ ] Block physics datapack altera mass/friction/restitution conforme selector/state esperado.
- [ ] Dimension physics override altera gravity/pressure apenas na dimensão alvo.
- [ ] Player entra/sai de sublevel em movimento sem teleport/desync persistente.
- [ ] Dois players observam a mesma pose/collision.
- [ ] Save + restart recupera sublevel e conteúdo de forma coerente.
- [ ] Unload/reload libera e recria tracking/body sem handle stale.
- [ ] `/sable forceload` add/remove não deixa ticket órfão.
- [ ] Portal/POI/entity interactions básicas funcionam em cenário Sable.
- [ ] Mechanical arm e block entities Create continuam tickando uma vez.
- [ ] Stress com vários sublevels não produz crescimento contínuo de memory/chunk tickets.
- [ ] Addons/bridges desta sequência carregam sem mixin/API failure.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 25. Evidências e limites
- Modlist física canônica de 10/09/2026: Sable 2.0.5, mixins e módulos embarcados.
- CurseForge oficial 2.0.5: Release NeoForge 1.21.1 e fix de crash em massless Create contraptions.
- Repositório oficial `main`: `version=2.0.5`, Minecraft 1.21.1, arquitetura de bootstrap/sublevels alinhada ao binário atual.
- Wiki/source 2.0.5/current: physics block properties, dimension physics e APIs de sublevel.
- README oficial: sublevels, intrusividade/mixins e papel do Sable Companion.
- Changelog 2.0.0: usado apenas como lineage/regression surface da série 2.x.
- **Limite:** não foram inventados thresholds de solver, tick rate de physics, formato de save ou parâmetros de networking não publicados.
