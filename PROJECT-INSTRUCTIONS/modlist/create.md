# Create

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81e0b444ce2a8983f000  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** Create
- **Arquivo JAR:** `create-1.21.1-6.0.10.jar`
- **Versão 1.21.1:** `6.0.10`
- **Categoria:** Tecnologia; Automação
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create
- **Função:** Núcleo tecnológico e de automação cinética do pack: fornece redes de rotação/stress, processamento mecânico, belts/funnels, fluid handling, moving contraptions, trains e a infraestrutura High Logistics introduzida na série 6.0.
- **Dependências:** NeoForge 1.21.1. A distribuição 6.x usa Flywheel 1.0 e inclui Ponder como library embarcada no Create JAR; dependências embarcadas não são tratadas como top-level. É base obrigatória dos addons Create instalados.
- **Compatibilidade/Riscos:** Authority transversal do maior stack tecnológico do pack. Riscos em stress/speed propagation, contraption assembly/collision, mounted storage, train graphs, package logistics, recipe-viewer sync e mixins de addons. 6.0.10 otimiza contraption collision e GlobalRailwayManager player login e altera Stock Keeper recipe-viewer sync.
- **Sobreposição:** Create é provider base. Addons estendem suas APIs/blocks/processing; não devem substituir sua authority de stress, speed, kinetic network, contraption state, train graph ou package logistics sem integração explícita.
- **Observações:** mod id `create`; runtime 6.0.10. Série 6.0 introduziu High Logistics: Chain Conveyor, Packager/Re-packager, packages, Frogport/Postbox, Stock Link/Ticker, Redstone Requester e Factory Gauge. 6.0.10 adiciona quatro modos de sync de recipe viewer no Stock Keeper, modifier keybinds e otimizações/fixes.
- **Procedência:** Modlist física canônica de 08/09/2026 (602 top-levels) + runtime Create 6.0.10 + changelog/source oficial Creators-of-Create branch mc1.21.1/dev + wiki oficial Create 6.0/kinetics/contraptions.
- **Histórico da decisão:** Sem decisão formal registrada. Em 08/09/2026, Create 6.0.10 foi reconfirmado como `Instalado` e reconstruído ao padrão técnico. Ser núcleo de muitos addons não foi convertido automaticamente em decisão curatorial.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — kinetic/stress authority, processing, fluids, contraptions, trains, high logistics, Ponder/API, client/server, lifecycle e regressões 6.0.10 catalogados.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> ⚙️ Versão física confirmada: `create-1.21.1-6.0.10.jar`, mod id `create`, runtime `6.0.10`, NeoForge 1.21.1. Create é a **authority cinética/contraption/logistics base** para todo o ecossistema Create do pack.

## 1. Papel e authority

Create fornece a infraestrutura tecnológica central: rotação, velocidade, stress, processamento, transporte de itens/fluidos, moving contraptions, trains e High Logistics. Addons podem consumir essas APIs, mas não devem calcular um segundo saldo de stress/speed ou duplicar settlement de recipes/transferências.

## 2. Kinetic networks

Shafts, cogwheels, gearboxes, belts e transmissores propagam rotação. Direction/speed fazem parte do state da rede; alteração em uma ponta pode reconfigurar toda a topologia conectada.

Integrações devem observar o network final do Create em vez de manter cópia paralela de RPM/direction.

## 3. Stress

Create usa **Stress Capacity** nos geradores e **Stress Impact** nos consumidores. Se o impacto total exceder a capacidade, a rede fica overstressed e deixa de operar até o balanço ser corrigido.

Qualquer addon que acrescente máquina cinética deve registrar seus custos/capacidade no sistema real; não criar um segundo orçamento de torque.

## 4. Processamento mecânico

Pressing, crushing, mixing, compacting, deploying, sawing, spouting/filling e outros process types formam a economia de automação. Recipe Manager/datapacks são authority dos recipes; JEI/REI apenas exibem o resultado.

Um item em belt/depot/basin deve produzir output uma única vez por recipe completion.

## 5. Belts, depots, funnels e tunnels

Belts e depots expõem pontos de transporte/processamento; funnels/tunnels distribuem e filtram itens. Race conditions com inventories externos podem causar dupes se dois handlers tratarem a mesma movimentação como ownership próprio.

## 6. Fluids

Pipes, pumps, tanks, spouts e fluid processing pertencem ao sistema Create/NeoForge correspondente. Bridges externas não devem debitar ou creditar o mesmo volume duas vezes.

Infinite/bottomless-fluid eligibility precisa seguir a configuração/provider real da build.

## 7. Moving contraptions

Create monta estruturas em entidades/contraptions móveis por bearings, pistons, rope pulleys e outros controllers. Durante assembly, blocos deixam o world state estacionário e passam a mounted state até disassembly.

Inventories, actors e block entities montados precisam ter ownership único durante essa transição para evitar stale references/dupe.

## 8. Contraption collision

A 6.0.10 faz otimizações adicionais de **contraption collision**. Collision é regression surface crítica em um pack com Aeronautics/Sable, moving blocks e vários addons.

QA deve cobrir player/entity collision, contraption-vs-world, chunk boundaries e desmontagem após contato.

## 9. Trains

Create controla tracks, bogeys, train graphs, stations, signals, schedules e carriage state. A série 6.0 mantém integração especial com JourneyMap/FTB Chunks e adiciona instruções de schedule ligadas a packages.

Addons ferroviários devem estender o graph/state do Create, não manter uma malha concorrente para o mesmo trem.

## 10. High Logistics — série 6.0

Create 6.0 introduziu uma camada logística de alto nível com:

- Chain Conveyor;
- Item Hatch;
- Packager e Re-packager;
- Cardboard Packages;
- Package Frogport;
- Package Postbox;
- Stock Link;
- Stock Ticker;
- Redstone Requester;
- Factory Gauge;
- Package Filter.

Essa camada gerencia pedido, empacotamento, transporte e visualização de estoque sem transferir ownership do inventário original para viewers externos.

## 11. Stock Keeper e recipe-viewer sync — 6.0.10

A 6.0.10 melhora o sync entre Stock Keepers e recipe viewers e oferece quatro modos: disabled, somente viewer→Create, somente Create→viewer ou bidirecional.

É importante evitar loop de sync e duplicação de request quando outro addon também integra JEI/REI/EMI.

## 12. Mounted storage

A série 6.0 amplia API de storage em contraptions, incluindo tags para casos simples. Storage montado deve ter um único state authoritative durante assembly/disassembly; capabilities externas precisam invalidar/re-resolver referências.

## 13. Schematics

Schematicannon/schematics e a API de schematic requirements transformam estruturas em dados/build steps. Não confundir preview/requisito com world placement concluído.

Mods próprios que adicionam blocos complexos devem usar hooks/API suportados em vez de serializar NBT inseguro por fora.

## 14. Ponder

Ponder é a documentação interativa do ecossistema e, desde 6.0, é library separada porém **embarcada com o Create JAR**. Não catalogar a cópia embarcada como top-level.

Ponder scenes são apresentação; não são autoridade de recipe/config/runtime behavior.

## 15. Flywheel/rendering

Create 6 usa Flywheel 1.0 para rendering otimizado de componentes animados. Render state deve refletir state server/common real sem controlar gameplay.

Conflitos com Sodium/ImmediatelyFast/shaders precisam ser diagnosticados como pipeline/render issues, não como falha automática da kinetic network.

## 16. Configuração e dados

Create utiliza configs, tags, recipes, advancements, Ponder data e registries próprios. Alterações de modpack devem preferir datapack/KubeJS/config suportados e preservar IDs estáveis.

Reloads precisam invalidar recipe/data caches corretamente.

## 17. Client/server e multiplayer

Kinetic state, inventories, recipes, contraption assembly, train graph e package logistics são server-authoritative. Rendering, Ponder, GUIs e input são client-facing.

Em multiplayer, interação concorrente com inventories, controls e contraptions precisa executar exatamente uma vez por ação válida.

## 18. Lifecycle

Validar server boot, world load, chunk unload/reload, datapack/resource reload, contraption assembly/disassembly, train graph rebuild, player login/logout, dimension travel, restart e capability invalidation.

A 6.0.10 otimiza `GlobalRailwayManager` no player login; login com rede ferroviária grande é regression gate.

## 19. Integrações concretas do pack

O pack possui dezenas de addons Create, incluindo Aeronautics, Central Kitchen, Enchantment Industry, Gunsmithing, New Age, Connected e muitos outros. Cada addon deve ser documentado separadamente, mantendo Create como authority das primitives que consome.

## 20. Riscos técnicos

1. Double stress/speed accounting.
2. Dupe em transfer de belt/inventory/mounted storage.
3. Recipe process disparar duas vezes.
4. Capability/reference stale após contraption assembly.
5. Collision regression em contraptions grandes.
6. Train graph stale após chunk/restart.
7. Package request/sync loop com recipe viewers.
8. Addon mixin incompatível com 6.0.10.
9. Client render divergir de state comum.
10. Embedded libraries serem tratadas como top-level.

## 21. Matriz de testes

1. Dedicated server boot com stack Create completo.
2. Kinetic network: speed/direction/stress e overstress recovery.
3. Processing belt/depot/basin — output exactly once.
4. Fluid transfer/spout/tank sem double debit.
5. Contraption assembly/disassembly com inventories e block entities.
6. Collision com players/entities/world e chunk borders.
7. Trains: station/signal/schedule/restart/login.
8. High Logistics: package request→pack→transport→delivery exactly once.
9. Stock Keeper em cada modo de recipe-viewer sync.
10. Datapack/recipe reload.
11. Multiplayer concorrente em storage/controls.
12. Client render stack atual sem state divergence.

## 22. Evidência

- modlist física 08/09/2026: Create 6.0.10;
- source/changelog oficial `mc1.21.1/dev`: deltas 6.0.10;
- wiki oficial Create 6.0.0: High Logistics, mounted-storage/API e Ponder/Flywheel changes;
- wiki oficial: stress e moving contraptions.

> 🔒 Boundary canônico: **Create decide kinetic networks, stress, processing, contraptions, trains e package logistics**. Addons devem estender essas authorities, não recalculá-las em paralelo.
