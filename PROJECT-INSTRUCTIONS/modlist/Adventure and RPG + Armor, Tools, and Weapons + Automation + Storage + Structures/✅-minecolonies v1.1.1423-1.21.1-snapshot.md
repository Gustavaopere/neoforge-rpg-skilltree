# MineColonies — 1.1.1423-1.21.1-snapshot (instalação declarada em 22/11/2026)

> **Estado vigente (declaração do usuário):** atualização `1.1.1387-1.21.1-snapshot` → **`1.1.1423-1.21.1-snapshot`**, arquivo oficial `minecolonies-1.1.1423-1.21.1-snapshot.jar` ([publicação verificada](https://www.curseforge.com/minecraft/mc-mods/minecolonies/files/9750650)). A presença do binário novo foi declarada pelo usuário; o JAR no computador, seu hash e os metadados internos não foram lidos. Ordem física histórica #395 preservada.

> **Cadeia de releases e deltas:** O histórico já audita 1.1.1392–1.1.1420 (Release de 15/11), incluindo decisões de AI/policies, pedidos, esquemas e acesso. A nova snapshot 1.1.1423 (22/11) corrige atribuição de trabalho dos cidadãos (PR #11893) e traz mudança descrita pelo autor como feature/height dif (PR #11898), além do merge da branch 1.21; não inferir qual regra de altura mudou sem diff específico. Não foi comprovada uma publicação intermediária 1.1.1421 ou 1.1.1422 separada para NeoForge 1.21.1 no catálogo analisado.

> **Dependências e proveniência:** Requisitos do release GitHub 1.1.1423: Structurize >=1.0.837, MultiPiston >=1.2.51, BlockUI >=1.0.199, Domum Ornamentum >=1.0.223. Structurize já consta 1.0.840 no índice atualizado de 22/11; não repetir o falso alerta histórico 1.0.833. Demais consumidores e addons MineColonies serão reconciliados em seus lotes.

> **Riscos e QA:** Alto risco de migração/serialização de colônias, jobs, builder scheduling, AI, economia, permissões e addons; backup de mundo e testes server/client necessários, não executados.

> **Leitura temporal:** todas as declarações antigas de versão 'atual', 'instalada' ou 'não instalada' nos trechos históricos abaixo se referem ao snapshot físico anterior ou à auditoria feita naquele momento. Não se sobrepõem à instalação declarada acima.

## Metadados documentais vigentes

- **Mod oficial:** MineColonies
- **Arquivo JAR instalado (declarado):** `minecolonies-1.1.1423-1.21.1-snapshot.jar` — https://www.curseforge.com/minecraft/mc-mods/minecolonies/files/9750650
- **Versão instalada declarada (NeoForge 1.21.1):** `1.1.1423-1.21.1-snapshot` (antes `1.1.1387-1.21.1-snapshot`); metadata nova não lida.
- **Estado no pack (vigente):** `1.1.1423-1.21.1-snapshot` declarado instalado; nenhuma cópia local lida.
- **Estado da pesquisa (vigente):** versões/cadeia e source já auditados; limites da última release e QA descritos no topo.
- **Dependências (vigentes):** Requisitos do release GitHub 1.1.1423: Structurize >=1.0.837, MultiPiston >=1.2.51, BlockUI >=1.0.199, Domum Ornamentum >=1.0.223. Structurize já consta 1.0.840 no índice atualizado de 22/11; não repetir o falso alerta histórico 1.0.833. Demais consumidores e addons MineColonies serão reconciliados em seus lotes.
- **Compatibilidade/Riscos (vigentes):** Alto risco de migração/serialização de colônias, jobs, builder scheduling, AI, economia, permissões e addons; backup de mundo e testes server/client necessários, não executados.
- **Procedência (vigente):** posição física histórica #395, declaração do usuário e publicação oficial https://www.curseforge.com/minecraft/mc-mods/minecolonies/files/9750650; nenhum novo SHA local.
- **Atualização/Status (vigente):** 22/11/2026, `1.1.1387-1.21.1-snapshot` → `1.1.1423-1.21.1-snapshot` declarada instalada; versões intermediárias e caveats descritas nesta ficha.

---

> **Histórico do dossier anterior:** versão-base `1.1.1387-1.21.1-snapshot`; preservado para a rastreabilidade de funcionalidades, requisitos, decisões e auditorias anteriores.


## Propriedades do registro

- **Mod (nome vigente):** MineColonies
- **Arquivo JAR (vigente declarado):** `minecolonies-1.1.1423-1.21.1-snapshot.jar`; nome do antigo JAR `minecolonies-1.1.1387-1.21.1-snapshot.jar` somente histórico.
- **Versão 1.21.1 (vigente declarada):** `1.1.1423-1.21.1-snapshot`; versão histórica `1.1.1387-1.21.1-snapshot`.
- **Categoria:** RPG, Automação, Worldgen, Armazenamento
- **Tipo de conteúdo:** Mod
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/minecolonies
- **Função:** Provider central de simulação colonial: colônias persistentes, cidadãos, jobs, buildings/módulos, construção e WorkOrders, request/logística, research, permissões, IA e sincronização Model/View server-authoritative. Addons devem estender essas surfaces sem duplicar authority.
- **Dependências:** Release 1.1.1387 exige Structurize \>=1.0.832, Multi-Piston \>=1.2.51, BlockUI \>=1.0.199 e Domum Ornamentum \>=1.0.223. Pack físico atual: 1.0.833 / 1.2.58 / 1.0.212 / 1.0.236, todos acima dos mínimos. Opcionais publicados: JEI \>=19.19.6.235, JourneyMap \>=1.21.1-6.0.0-beta.29 e Dynamic Trees \>=1.5.0-BETA07; pack usa 19.56.0.440 / 1.21.1-6.0.8 / 1.7.2.
- **Compatibilidade/Riscos:** Snapshot central de colônias. Riscos: churn de API, requests/logística, drift com Structurize/BlockUI/Domum/Multi-Piston e addons Compatibility 3.57/Tweaks 3.33/Jade crops 1.1.1300, colony-data migration, chunk/entity lifecycle, performance/pathfinding, permission bypass e View stale. Há gap atual 1.1.1387→1.1.1403; upstream 1.1.1403 não é o runtime instalado.
- **Sobreposição:** MineColonies é authority da colônia. Structurize/BlockUI/Domum/Multi-Piston fornecem infraestrutura especializada. Jade crops apenas apresenta crops; Compatibility/Tweaks estendem comportamento/integrações. O bridge Let's Do está ausente da modlist física atual. Sistemas próprios de RPG/quests não devem espelhar colony research/job/permission como authority paralela.
- **Observações (vigentes):** versão `1.1.1423-1.21.1-snapshot`, JAR `minecolonies-1.1.1423-1.21.1-snapshot.jar`; antigas observações sobre 1.1.1387-1.21.1-snapshot são históricas.
- **Procedência:** modlist(1).txt física anexada e reconferida em 25/09/2026 + release/tag oficial GitHub `v1.21.1-1.1.1387-snapshot` commit `495c35113698c2a48c58fcbd0cfbd5568b23a67e` + CurseForge oficial file 8872247 + sequência upstream 1.1.1392→1.1.1403 revisada em 01/10/2026.
- **Atualização/Status:** AUDITORIA INCREMENTAL DOCUMENTAL 17/11/2026 — último JAR físico confirmado permanece `minecolonies-1.1.1387-1.21.1-snapshot.jar`. Histórico de 1.1.1392 até 1.1.1405 já auditado, seguido das publicações 1.1.1406, 1.1.1407, 1.1.1410, 1.1.1411, 1.1.1412, 1.1.1413, 1.1.1414, 1.1.1416, 1.1.1418, 1.1.1419, 1.1.1420-snapshot e 1.1.1420 Release. Versão 1.1.1420 é candidata upstream, não instalada. Mínimo Structurize subiu para 1.0.837, acima de 1.0.833 no snapshot físico; promoção depende de atualização coordenada e QA. Seção 24.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-08-30

> **Snapshot físico histórico — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #395: JAR `minecolonies-1.1.1387-1.21.1-snapshot.jar`, mod id `minecolonies`, runtime `1.1.1387-1.21.1-snapshot`, SHA-1 `07752c101305dde5e4621cda39b5d7607f614871`.

<callout icon="🏛️" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `minecolonies-1.1.1387-1.21.1-snapshot.jar`, mod id `minecolonies`, exatamente a snapshot oficial `v1.21.1-1.1.1387-snapshot`, publicada em 13/09/2026. A tag oficial e o asset binário têm o mesmo nome do JAR instalado. Esta ficha usa essa tag como authority de source e não projeta código posterior da branch `1.21`.
</callout>
## 1. Identidade, versão e release
- **Mod:** MineColonies.
- **JAR físico:** `minecolonies-1.1.1387-1.21.1-snapshot.jar`.
- **Versão runtime:** `1.1.1387-1.21.1-snapshot`.
- **Minecraft:** 1.21.1.
- **Loader:** NeoForge.
- **Java:** 21.
- **Upstream:** `ldtteam/minecolonies`.
- **Tag exata:** `v1.21.1-1.1.1387-snapshot`.
- **Release oficial GitHub:** tag `v1.21.1-1.1.1387-snapshot`, commit `495c35113698c2a48c58fcbd0cfbd5568b23a67e`; CurseForge file ID `8872247`.
- **Natureza da build:** snapshot/beta; não tratar como release estável final.
O changelog da release 1.1.1387 registra **Cavalry 4of4 port (#11823)**. Isso não reduz o escopo do mod: a snapshot carrega o sistema completo de colônias acumulado pela linha 1.21.1.
**Version gate atual (01/10/2026):** upstream/CurseForge publicou `minecolonies-1.1.1403-1.21.1-snapshot.jar`. O pack permanece em 1.1.1387; a atualização não foi aplicada. Entre as duas versões existem mudanças de persistência/request system, alimentação do Dining Hall, graveyard, pathing/água, prevenção de loop potencialmente infinito e novos stables, detalhadas na seção 20. A promoção deve ser regressada junto de Compatibility 3.57, Tweaks 3.33 e Jade crops 1.1.1300. O bridge Let's Do não está presente na modlist física atual.
## 2. Papel funcional no pack
MineColonies é o provider central de **simulação de colônia** do pack. Ele mantém colônias persistentes, cidadãos, empregos, edifícios, construção/upgrade, pedidos e logística, pesquisas, permissões, interação do jogador, tarefas/AI, raids/defesa e interfaces de administração.
A autoridade deve permanecer nele para:
- identidade e território de colônia;
- cidadãos e seus dados persistentes;
- jobs e vínculo trabalhador↔building;
- buildings e módulos;
- work orders;
- request system;
- pesquisas e unlocks próprios;
- permissões/relacionamento de jogadores;
- regras internas de colony lifecycle.
Addons do pack podem observar, ampliar ou adaptar esses sistemas, mas não devem criar uma segunda fonte de verdade paralela.
## 3. Arquitetura ColonyManager — Model/View server-authoritative
O `package-info.java` da tag exata documenta explicitamente a arquitetura do `ColonyManager`.
No servidor:
- `ColonyManager` mantém ownership das colônias e lookup por ID;
- cada `Colony` contém os dados autoritativos da colônia;
- `Building` representa estado server-side de edifícios;
- `CitizenData` representa dados persistentes de cidadãos;
- `Permissions`, jobs e work orders vivem na camada autoritativa.
No cliente:
- `ColonyView`, `Building.View`, `CitizenData.View` e `Permissions.View` são espelhos de leitura;
- ações mutáveis iniciadas por uma View se transformam em request ao servidor.
Esse padrão é um boundary crítico: mod próprio/addon não deve alterar uma View esperando mutar a colônia real.
## 4. Persistência e separação de estado
A documentação interna da tag separa deliberadamente o estado por domínio:
- **AITask:** dados necessários ao trabalho imediato, não necessariamente persistentes;
- **CitizenData:** dados do cidadão independentes de job/building;
- **Job:** estado persistente específico do trabalho daquele cidadão;
- **Building:** estado do edifício, não dos trabalhadores;
- **Colony:** estado global da colônia.
A finalidade do ColonyManager é manter residência permanente dessas estruturas **sem depender de chunk/entity estar carregado**. Isso é essencial para integrações: uma entidade de cidadão descarregada não significa que o cidadão deixou de existir no modelo da colônia.
## 5. Cidadãos, entidades e jobs
Colônias rastreiam cidadãos por `CitizenData`, com IDs inteiros. Quando a entidade correspondente está carregada, o `CitizenData` se vincula ao `CitizenEntity`; o cliente recebe um `CitizenData.View` e, quando aplicável, o entity ID associado.
Jobs ligam um cidadão a um building e fornecem armazenamento persistente específico da função. Eles também participam da representação/modelo do trabalhador.
Consequência operacional: perks, quests ou integrações que precisam identificar profissão devem consultar o job/citizen data apropriado e não inferir ocupação apenas pela skin, entidade próxima ou posição no edifício.
## 6. Buildings, huts e módulos
Buildings server-side representam o estado funcional dos edifícios da colônia e são vinculados ao bloco/hut correspondente no mundo por coordenada. Views equivalentes são sincronizadas a clientes interessados.
O ecossistema moderno de MineColonies usa módulos de building para decompor capacidades. Addons desta modlist — Compatibility, Let's Do e Tweaks — podem registrar/alterar módulos, recipes/jobs ou comportamento conectado, mas o building base e seu lifecycle permanecem sob MineColonies.
## 7. WorkManager e WorkOrders
Tarefas coordenadas da colônia são centralizadas em `WorkManager` e subclasses de `WorkOrder`. A própria documentação dá como exemplo construção/upgrade, que cria `WorkOrderBuild` para coordenar o trabalho.
WorkManager/WorkOrders **não são espelhados diretamente ao cliente** como as Views principais. Portanto automação ou UI externa deve usar APIs/mensagens suportadas; não assumir que existe uma lista client-side completa e autoritativa de todas as work orders.
## 8. Request System e logística
O request system é uma das superfícies centrais para cadeia de suprimento: trabalhadores/buildings criam requests, resolvers encontram fontes/crafting/entrega possíveis e o estado do pedido percorre seu lifecycle até resolução/cancelamento.
A release física 1.1.1387 tem changelog centrado no port de Cavalry; a superfície de requests continua crítica por fazer parte do sistema central de logística. Qualquer addon que modifique recipes, warehouse/network storage, delivery ou crafting precisa ser regressado especificamente nesta build.
Riscos clássicos neste pack incluem request órfão, resolução duplicada, item entregue sem settlement, recipe incompatível, ciclo de dependências e cache stale após alteração de building/job.
## 9. Construção, Structurize e Domum Ornamentum
MineColonies não deve ser confundido com os providers auxiliares de construção:
- **Structurize** fornece scanning/placement/template infrastructure usada na construção;
- **Domum Ornamentum** fornece blocos decorativos parametrizados consumidos por estilos/construções;
- **Multi-Piston** fornece infraestrutura requerida associada ao ecossistema;
- **BlockUI** fornece a framework de UI usada extensivamente pelas telas.
MineColonies continua authority de decidir **o que a colônia pretende construir**, níveis, work orders e lógica de trabalhadores; esses providers executam partes especializadas do pipeline.
## 10. Dependências oficiais da snapshot 1.1.1387
A publicação oficial exige no mínimo:
- **Structurize** `1.0.832-1.21.1-snapshot+`;
- **Multi-Piston** `1.2.51-1.21.1-snapshot+`;
- **BlockUI** `1.0.199-1.21.1-snapshot+`;
- **Domum Ornamentum** `1.0.223-snapshot+`.
A modlist física atual contém:
- Structurize `1.0.833`;
- Multi-Piston `1.2.58`;
- BlockUI `1.0.212`;
- Domum Ornamentum `1.0.236`.
Todos satisfazem os mínimos publicados da 1.1.1387.
O build também referencia **TownTalk** na lista de dependências Curse; a presença/forma de empacotamento deve ser tratada conforme metadata física e loader, não inferida apenas pelo nome do Gradle.
## 11. Integrações opcionais oficiais
A release lista como opcionais:
- **JEI** `19.19.6.235+`;
- **JourneyMap** `1.21.1-6.0.0-beta.29+`;
- **Dynamic Trees** `1.5.0-BETA07+`.
O pack físico possui JEI `19.56.0.440`, JourneyMap `1.21.1-6.0.8` e Dynamic Trees `1.7.2`, todos acima dos baselines opcionais publicados.
## 12. Research e progressão
A árvore da tag exata contém APIs/implementações de **research** e registries relacionados. Research em MineColonies é progressão colonial: unlocks e requisitos pertencem ao provider MineColonies e podem condicionar buildings/jobs/capacidades.
Mods próprios de árvore de habilidades do jogador não devem espelhar research como se fosse atributo individual. A integração correta é consultar/gatear a research colonial quando necessário, preservando as duas authorities.
## 13. Permissões e multiplayer
Cada colônia mantém `Permissions`, modelando relacionamento de jogadores com a colônia; o cliente recebe `Permissions.View`.
Em multiplayer, operações administrativas, building interaction, placement/ownership e mutações de colony data precisam respeitar a validação server-side. UI ou addon não deve considerar botão visível como autorização suficiente.
Testes devem incluir owner, officer/friend/neutral/hostile conforme papéis efetivamente configurados, além de jogadores desconectando durante requests/builds.
## 14. Rede, sync e UI
O padrão View/subscriber existe para que clientes recebam os dados relevantes sem tornar o cliente owner. BlockUI é infraestrutura central da apresentação.
Superfícies de risco:
- view stale após alteração server-side;
- packet/message fora de ordem em telas abertas durante mudanças rápidas;
- referências a citizen/building removido;
- cliente reconectando durante work order/request ativo;
- addon enviando mutação sem checagem de permissão.
Qualquer integração própria deve separar command/request server-side de renderização client-side.
## 15. IA, chunks e lifecycle
Cidadãos carregados possuem entidades/AI, mas os dados coloniais sobrevivem a unload de entidade/chunk. Isso exige distinguir:
- estado persistente da colônia/cidadão/job/building;
- execução transitória da entidade e AI;
- retomada após chunk load;
- cancelamento/replanejamento de requests/work orders após mundo/restart.
Forçar chunks ou manter referências diretas a entidades descarregadas pode violar essa arquitetura e gerar leak/stale reference.
## 16. Addons físicos diretamente relacionados neste lote
### MineColonies: Jade crops 1.1.1300
Somente apresenta estágio de crops MineColonies no Jade. Não altera colony state.
### Compatibility addon 3.57
Extende jobs/recipes/compatibilidades e integra storage/crafting conforme seu próprio dossiê; MineColonies permanece provider da colônia.
### MineColonies Let's Do 2.1 — ausente no pack atual
O bridge foi removido da modlist física atual. Referências históricas podem permanecer como contexto, mas não contam como integração ativa deste runtime.
### Tweaks addon 3.33
Aplica tweaks/integrações sobre MineColonies; seu mínimo declarado 1.1.1368 é satisfeito pela 1.1.1387. Novamente, isso valida contrato de loader, não todos os comportamentos.
## 17. Riscos específicos da snapshot 1.1.1387 neste pack
1. **Snapshot churn:** APIs internas podem mudar entre snapshots próximas.
2. **Request regressions:** requests/logística continuam área prioritária por serem centrais ao runtime e compartilharem superfície com addons de recipes/storage/crafting.
3. **Dependency drift:** quatro libs obrigatórias estão em snapshots mais novas que os mínimos; precisam de teste conjunto.
4. **Addon ABI drift:** Compatibility/Tweaks e demais bridges ativos podem compilar contra baselines diferentes; Let's Do está ausente no pack atual.
5. **Colony data corruption:** falhas durante save/restart/migration são de alto impacto; backups são mandatórios antes de atualização.
6. **Chunk/entity lifecycle:** citizen entity descarregada não pode ser tratada como ausência de CitizenData.
7. **Performance:** pathfinding, cidadãos, request resolution, construction scanning/placement e sincronização podem escalar com tamanho/número de colônias.
8. **Permission bypass:** addons/mod próprios não devem contornar checks server-side.
9. **UI stale state:** BlockUI/View pode ficar temporariamente atrás do estado real; não usar UI como authority.
10. **Economia/logística duplicada:** bridges Create/storage e demais integrações ativas precisam evitar satisfazer request duas vezes.
## 18. Matriz de testes obrigatória
- [ ] Dedicated server 21.1.250 inicia com MineColonies 1.1.1387 + quatro dependências físicas atuais.
- [ ] Criar nova colônia e reiniciar servidor; ID, owner, território e dados persistem.
- [ ] CitizenData persiste após unload/reload e volta a vincular à entidade correta.
- [ ] Contratar/atribuir cidadão a job/building e validar persistência após restart.
- [ ] Construção e upgrade criam/concluem WorkOrder sem duplicação.
- [ ] Request simples de item: criação → resolução → entrega → settlement uma vez.
- [ ] Request de crafting encadeado não cria loop/órfão.
- [ ] Warehouse/courier e addons de storage não duplicam entrega.
- [ ] Research unlock persiste e aplica somente uma vez.
- [ ] Owner vs outro jogador: permissões são validadas server-side.
- [ ] Dois clientes observando a mesma colônia recebem Views coerentes.
- [ ] Desconectar/reconectar durante construção/request não perde estado.
- [ ] Chunk unload do trabalhador não apaga CitizenData/job/request.
- [ ] Structurize placement e Domum Ornamentum blocks funcionam no estilo escolhido.
- [ ] JEI atual não quebra recipe presentation.
- [ ] Compatibility 3.57, Let's Do 2.1 e Tweaks 3.33 passam smoke test conjunto.
- [ ] Backup/restore de mundo conserva colony data antes de qualquer upgrade de snapshot.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 19. Evidências consultadas
- Modlist física atual: `minecolonies-1.1.1387-1.21.1-snapshot.jar`.
- Release/tag oficial GitHub `v1.21.1-1.1.1387-snapshot`, commit `495c35113698c2a48c58fcbd0cfbd5568b23a67e`.
- CurseForge oficial file `8872247`: `minecolonies-1.1.1387-1.21.1-snapshot.jar`, NeoForge 1.21.1, com dependências mínimas/opcionais publicadas.
- Source exato da tag, incluindo `gradle.properties` e árvore do projeto.
- `core/colony/package-info.java` da tag: arquitetura ColonyManager/Views, CitizenData, Buildings, Permissions, WorkManager/WorkOrders e Jobs.
- Modlist física para versões atuais de Structurize, Multi-Piston, BlockUI, Domum Ornamentum e JEI.
<callout icon="⚠️" color="yellow_bg">
	**Limite de evidência:** MineColonies é grande e a ficha documenta subsistemas/contratos operacionais, não uma enumeração de cada registry ID, building, job ou recipe. Detalhes de comportamento não demonstrados pela tag/release não foram inferidos. A build é snapshot e exige regressão de runtime antes de upgrades.
</callout>


## 20. Histórico completo de atualização upstream — 1.1.1387 → 1.1.1403
A versão instalada continua **1.1.1387-1.21.1-snapshot**. Foram conferidas todas as snapshots públicas 1.21.1 posteriores até a versão mais recente disponível em 01/10/2026:

| Release | Data | Delta publicado relevante |
|---|---|---|
| **1.1.1392** | 16/09/2026 | Fixes de port/compile, port final de Cavalry/datagen e **fix #11808**. O #11808 tratava falha de serialização de requests com quantidades acima do limite do ItemStack durante save, com risco de request/logística não persistir corretamente. |
| **1.1.1393** | 20/09/2026 | Graveyard QoL: **min-stock** e indicação do número de posições de túmulo em uso na janela do módulo. |
| **1.1.1394** | 20/09/2026 | Fix #11827: cidadãos fora do restaurante passam a lidar melhor com alimentação eventual/leftovers sem consumir essa rota indefinidamente. |
| **1.1.1395** | 20/09/2026 | Port de **música da Tavern**. |
| **1.1.1396** | 21/09/2026 | Melhoria de **node reference e debug tracking**. |
| **1.1.1397** | 23/09/2026 | Corrige **slabs impedindo drift/subida na água**. |
| **1.1.1399** | 27/09/2026 | Hotfix #11834: Dining Hall/Waiter deixa de pedir o alimento já cozido quando deveria solicitar o ingrediente cru e cozinhar localmente; relevante para request system, warehouse e crafting. |
| **1.1.1402** | 29/09/2026 | Atalho para **potencial infinite loop** e eliminação de spam de tentativas de burial que falharam. |
| **1.1.1403** | 01/10/2026 | Adiciona **stables** aos estilos Fortress e Desert Oasis na linha 1.21.1. |

Os mínimos publicados de dependências continuam, na 1.1.1403, em Structurize 1.0.832+, MultiPiston 1.2.51+, BlockUI 1.0.199+ e Domum Ornamentum 1.0.223+; os opcionais publicados continuam JEI 19.19.6.235+, JourneyMap 6.0.0-beta.29+ e Dynamic Trees 1.5.0-BETA07+.

**Gates de regressão prioritários para promoção 1387→1403:** save/restart com requests grandes; Dining Hall + Warehouse + Courier; graveyard; citizens com alimentação fora do restaurante; pathing/drift em água com slabs; cenários capazes de formar loops no work/request graph; Tavern; Cavalry; Compatibility 3.57; Tweaks 3.33; Jade crops 1.1.1300.

**Fontes upstream:** releases oficiais ldtteam/minecolonies 1.1.1392–1.1.1403 e CurseForge file ID 9023811 para minecolonies-1.1.1403-1.21.1-snapshot.jar.

## 21. Revalidação upstream — 10/10/2026

**Autoridade física preservada:** `minecolonies-1.1.1387-1.21.1-snapshot.jar`; não há evidência de troca de JAR após a snapshot canônica. A versão mais recente da linha NeoForge 1.21.1 localizada para esta revisão continua **1.1.1403-1.21.1-snapshot**, publicada em 01/10/2026 (CurseForge file 9023811).

A cadeia integral já investigada em 01/10 permanece válida e está preservada na seção 20: 1.1.1392 → 1.1.1393 → 1.1.1394 → 1.1.1395 → 1.1.1396 → 1.1.1397 → 1.1.1399 → 1.1.1402 → 1.1.1403. **Nenhuma nova alteração comportamental foi atribuída sem changelog adicional verificável.** O delta mais crítico é a correção de serialização de requests de quantidades elevadas (#11808), seguido de ajuste de pedidos do Dining Hall/Waiter (#11834), possíveis loops de execução/burial, nova UI do Graveyard e stables em estilos de colônia.

**Interdependências deste lote:** a versão física de MineColonies também afeta #45 Epic Colonies e #50 Compatibility addon for MineColonies, ambos na relação total da rodada. Na promoção do mod-base, revalidar também a interface dos adapters próprios de colony economy e Battle Mage e executar as matrizes de claims/AI/persistência. Não atribuir sucesso a esses testes apenas porque o CI do repositório compila.

**Fontes:** https://www.curseforge.com/minecraft/mc-mods/minecolonies ; https://github.com/ldtteam/minecolonies/releases

**Decisão:** atualização upstream revisada; promoção física **pendente** de JAR/metadata/hash novos e teste integrado.

## 22. Retificação de canal de distribuição 1.1.1403 — 10/10/2026

**Esta seção complementa e prevalece sobre a classificação da seção 21 apenas quanto ao canal upstream.** O CurseForge publicou **`minecolonies-1.1.1403-1.21.1.jar`** como **Release (R)** para NeoForge 1.21.1 em **04/10/2026**, file ID **9058295**. É um artefato distinto de **`minecolonies-1.1.1403-1.21.1-snapshot.jar`**, publicado em **01/10/2026** como **Beta (B)**, file ID **9023811**. Embora compartilhem 1.1.1403, **não foi verificada igualdade binária nem de comportamento** entre esses JARs, portanto não podem ser tratados como downloads intercambiáveis.

**Mínimos informados na página oficial da Release:** Structurize `1.0.832-1.21.1-snapshot`+, MultiPiston `1.2.51-1.21.1-snapshot`+, BlockUI `1.0.199-1.21.1-snapshot`+, Domum Ornamentum `1.0.223-snapshot`+. Os mínimos conhecidos seguem abaixo das versões documentadas no snapshot do modpack, porém a compatibilidade efetiva dos addons MineColonies Compatibility 3.57, MineColonies Tweaks 3.33, Jade crops 1.1.1300, Epic Colonies e integrações próprias ainda exige teste.

**Gates específicos:** comparar a Release com a snapshot 1.1.1403 quanto ao changelog/tag exata e metadata, verificar dependentes, e testar em cópia de mundo save/restart, request system e limites de ItemStack, Dining Hall/Waiter, graveyard, estábulos, citizen pathfinding, claims/permissions, Battle Mage, addons, servidor dedicado e multiplayer. **A versão instalada permanece 1.1.1387-1.21.1-snapshot até novo dump físico**; nenhum download, substituição ou teste de boot foi executado nesta retificação documental.

**Fontes oficiais:** https://www.curseforge.com/minecraft/mc-mods/minecolonies/files/9058295 ; https://www.curseforge.com/minecraft/mc-mods/minecolonies/files/9023811

**Status:** release upstream identificada; promoção física **PENDENTE**.

## 23. Pré-validação da Release 1.1.1403 contra o snapshot de dependências — 10/10/2026

**Escopo e autoridade:** esta verificação compara as **restrições anunciadas na página oficial da Release 1.1.1403 para NeoForge 1.21.1** com as **versões presentes no último snapshot documental `modlist(1).txt`**. Isso **não** prova o estado físico atual dos JARs nem confirma compatibilidade ABI/runtime. A distribuição de referência é **`minecolonies-1.1.1403-1.21.1.jar`** (Release/R, CurseForge file **9058295**, 04/10/2026), não a build homônima `-snapshot.jar` (Beta/B, file **9023811**, 01/10/2026).

### 23.1. Gate estático de versões mínimas declaradas

| Dependência | Mínimo publicado pela Release 1.1.1403 | Snapshot do perfil | Comparação de versão |
|---|---|---|---|
| Structurize | `1.0.832-1.21.1-snapshot` ou superior | `structurize-1.0.833-1.21.1-snapshot.jar` (#529) | Mínimo numérico atendido no snapshot |
| MultiPiston | `1.2.51-1.21.1-snapshot` ou superior | `multipiston-1.2.58-1.21.1.jar` (#413) | Mínimo numérico atendido no snapshot |
| BlockUI | `1.0.199-1.21.1-snapshot` ou superior | `blockui-1.0.212-1.21.1-snapshot.jar` (#080) | Mínimo numérico atendido no snapshot |
| Domum Ornamentum | `1.0.223-snapshot` ou superior | `domum-ornamentum-1.0.236-snapshot-main.jar` (#224) | Mínimo numérico atendido no snapshot |

**Conclusão estritamente documental:** **4/4 mínimos publicados atendidos na modlist histórica**. O quarto campo não constitui resolução real de dependências pelo NeoForge, nem descarta APIs quebradas, remapeamento de mixins, patches de classes ou mudança de comportamento entre a 1.1.1387-snapshot física e a 1.1.1403 Release. Antes de liberar o conjunto, inspecionar os JARs e suas `neoforge.mods.toml`/metadata.

**Opcionais listados na página oficial da Release:** JEI `19.19.6.235+` e JourneyMap `1.21.1-6.0.0-beta.29+`. O snapshot contém **JEI `19.56.0.440`** e **JourneyMap `6.0.8`**, mas a validade do range de versões e do comportamento de integração precisa ser confirmada em runtime. Dynamic Trees constava como opcional em metadados de snapshots anteriores; não se deve automaticamente atribuir a mesma declaração à página da Release sem evidência.

### 23.2. Matriz de consumidores que precisam acompanhar o teste do mod-base

| Posição no inventário | Item/versão documentada | Superfície crítica |
|---|---|---|
| #262 | Epic Colonies `21.0.8` (externo 21.0.9) | render/armature e patch de cidadãos/raiders via Epic Fight; target de compilação EFM 21.16.4 versus Epic Fight físico 21.17.3.1 |
| #391 | MineColonies: Jade Crops `1.1.1300` | propriedades de crescimento/tooltip `age`, namespace e integração Jade (snapshot Jade 15.10.6) |
| #396 | MineColonies Compatibility `3.57` (externo **3.58 Beta**, 08/10/2026) | profissões, registries, jobs, receitas, courier e interop; 3.58 contém fix específico para Cultural Delights |
| #397 | MineColonies Tweaks `3.33` | mixins de citizen/jobs, request system, crops, config e reprodução de estados persistentes |

**Cultural Delights não consta como linha top-level no snapshot ordinal 587**: o fix específico da Compatibility 3.58 não pode ser classificado como regressão ativa do pack sem confirmar a presença física nova ou dependência embarcada/indireta. Não atualizar 3.57→3.58 automaticamente nem inferir compatibilidade de 3.58 com 1.1.1403 por estar publicada.

### 23.3. Gate operacional para o update coordenado

1. Recuperar os **cinco JARs MineColonies/addons efetivos** e os quatro JARs de dependência; registrar SHA-256, `modId`, versão runtime, loader, dependências obrigatórias/opcionais e eventuais versões duplicadas. Conferir se MineColonies continua 1.1.1387-snapshot no perfil vivo.
2. Em **cópia íntegra de mundo** com backup de `world`, configs e scripts, iniciar **dedicated server e cliente** com Release 1.1.1403 e conjunto de dependências que passe nos ranges **do artefato**. Não misturar `1.1.1403-snapshot` e `1.1.1403` como se fossem binários idênticos.
3. Regressões do core: save/restart com requests grandes/data components; Dining Hall/Waiter + Warehouse/Couriers; Graveyard/min-stock; Tavern/citizen jobs; cavalaria e estábulos Fortress/Desert Oasis; pathfinding em água/slabs; raids/claims/permissions e chunks descarregados.
4. Regressões dos consumidores: jobs e recipes da Compatibility, mixins/configs da Tweaks, Jade crops `age`, Epic Colonies com Epic Fight e render providers, client/server login-relogin, tracking, UI/reload, operações concorrentes e mudança de dimensão.
5. Registrar logs/erros, integridade do inventário e das `colony data`, hash/config do conjunto aprovado. Somente com gate real positivo atualizar JARs, recriar modlist física, renomear os dossiês com versão física nova e refazer os links. Falha material implica rollback integral do mundo **e** dos JARs, evitando reabrir saves migrados no runtime antigo isoladamente.

**Fontes primárias verificadas:** https://www.curseforge.com/minecraft/mc-mods/minecolonies/files/9058295 ; https://www.curseforge.com/minecraft/mc-mods/minecolonies/files/9023811 ; https://www.curseforge.com/minecraft/mc-mods/minecolonies-compatibility/files/9099509 .

**Status:** **versões mínimas documentais 4/4; compatibilidade em runtime NÃO VERIFICADA; promoção física BLOQUEADA** até recuperar os artefatos atuais e testar cópia de mundo. Este adendo não altera versão, filename, posição ou hash histórico.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** MineColonies
- **Último candidato 1.21.1 / NeoForge localizado:** **1.1.1403 (Release estável); 1.1.1405-snapshot (snapshot Beta, 21/10)**
- **Estado:** **PENDENTE para 1.1.1403; snapshot 1.1.1405 BLOQUEADO para promoção**
- **Gate de promoção:** Testar migração de colônia/save, workers/AI, raids, buildings/Structurize/BlockUI e dedicated server antes de trocar o JAR. Não promover snapshot como baseline de produção.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/minecolonies

## Revisão cumulativa auditada — 22/10/2026 — lote 01–10
**Instalado:** 1.1.1387-1.21.1-snapshot, imutável; release candidata 1.1.1403 (04/10, file 9058295) e snapshot 1.1.1405 (21/10, Beta, não aprovada).

### Histórico intermediário acumulado (NeoForge 1.21.1)

| Release | Delta da release confirmado |
|---|---|
| 1.1.1392-snapshot | Fix #11808: requests com quantidade superior ao limite de um ItemStack passam por serialização/persistência segura; ports de Cavalry/datagen. |
| 1.1.1393-snapshot | Graveyard: min-stock e contador de posições ocupadas. |
| 1.1.1394-snapshot | Fix #11827: tratamento de alimentação eventual de cidadãos fora do Restaurant. |
| 1.1.1395-snapshot | Música na Tavern. |
| 1.1.1396-snapshot | Melhorias nas referências de nós e debug tracking. |
| 1.1.1397-snapshot | Correção da movimentação vertical em água junto a slabs. |
| 1.1.1399-snapshot | Fix #11834: Waiter solicita ingrediente cru para cozinhar, em lugar de pedir comida pronta. |
| 1.1.1402-snapshot | Tratamento de possível loop infinito e tentativas de burial repetidas. |
| 1.1.1403-snapshot | Stables dos estilos Fortress e Desert Oasis. |
| 1.1.1403 Release | Artefato distinto da snapshot 1.1.1403: o changelog da tag oficial não indica novos commits; isto **não** comprova igualdade binária entre os dois JARs. |
| 1.1.1405-snapshot | **Novo desde a auditoria anterior:** Desert Oasis fix (#11859); **dois novos visitors e scraps** (#11846). É snapshot/Beta; não substituir a Release estável sem decisão explícita. |

Todas as versões efetivamente publicadas identificadas no intervalo constam acima. Nenhuma publicação 1.1.1404 foi comprovada na série consultada; números não publicados não foram tratados como alterações fictícias.

**Gates:** backup íntegro do mundo, request que excede stack, logística Warehouse/Waiter, Graveyard, Tavern e Cavalry, estilos Desert Oasis/Fortress, visitors/scraps, pathfinding água/slabs, claims/permissions e addons. Os pisos documentais da Release 1.1.1403 são Structurize 1.0.832+, MultiPiston 1.2.51+, BlockUI 1.0.199+ e Domum Ornamentum 1.0.223+: o último snapshot atende numericamente, mas não há prova ABI/teste de JAR. **Documentação da cadeia concluída; promoção física, rollback e QA não executados.**

Fontes primárias: https://github.com/ldtteam/minecolonies/releases ; https://github.com/ldtteam/minecolonies/releases/tag/v1.21.1-1.1.1405-snapshot ; https://www.curseforge.com/minecraft/mc-mods/minecolonies/files/9058295 ; https://www.curseforge.com/minecraft/mc-mods/minecolonies/files/9023811.

## 24. Auditoria das releases 1.1.1406 → 1.1.1420 (17/11/2026)

**Autoridade física:** #395 `minecolonies-1.1.1387-1.21.1-snapshot.jar`; versão **1.1.1387-snapshot**, NeoForge Minecraft **1.21.1**. O dossier já audita 1.1.1392, .1393, .1394, .1395, .1396, .1397, .1399, .1402, .1403-snapshot, .1403 Release e .1405-snapshot (seções 20–23 e revisão de 22/10). A sequência **nova** verificada abaixo vem de cada tag oficial do repositório `ldtteam/minecolonies`, com [publicação CurseForge 1.1.1420 Release](https://www.curseforge.com/minecraft/mc-mods/minecolonies/files/9624268), **15/11/2026**. Cada linha abaixo corresponde a uma publicação **real**, não a todos os números possíveis.

| Versão oficial para 1.21.1 | Publicação UTC | Delta documentado e prioridade |
|---|---|---|
| **1.1.1406-snapshot** | 28/10/2026 | **Segurança de interação:** corrige mensagem de pickup que permitia recolher **qualquer bloco fora dos limites de colônias** ([PR #11866](https://github.com/ldtteam/minecolonies/pull/11866)). Material para proteção de terreno, claims e autorização. |
| **1.1.1407-snapshot** | 01/11/2026 | Corrige lookup de **saplings/folhas** ([PR #11863, issue #11855](https://github.com/ldtteam/minecolonies/pull/11863)); relevante para árvores/workers e integrações de worldgen. |
| **1.1.1410-snapshot** | 01/11/2026 | **Nova tag para alvos de raiders**, antes hardcoded; permite a addons registrar novas entidades-alvo e inclui **visitors/mercenaries** ([PR #11876](https://github.com/ldtteam/minecolonies/pull/11876)); corrige updates desnecessários de **texto de signs** que marcavam bloco dirty ([PR #11873](https://github.com/ldtteam/minecolonies/pull/11873)); limita valor de posição inválida de pack tags para **evitar crash** ([PR #11875](https://github.com/ldtteam/minecolonies/pull/11875)). A versão foi construída após a 1.1.1407; não foram localizados binários 1.1.1408 ou .1409 publicados no canal consultado. |
| **1.1.1411-snapshot** | 02/11/2026 | Corrige o envio de **arrays de bytes muito grandes mas majoritariamente vazios** em vários caminhos de sincronização ([PR #11872](https://github.com/ldtteam/minecolonies/pull/11872), issue #11869). Material para tamanho de packets, latência/uso de memória e estabilidade multiplayer; não comprova eliminação de todas as ocorrências. |
| **1.1.1412-snapshot** | 07/11/2026 | Corrige mensagem de **direct placement** que permitia configurar **qualquer bloco, custo e lugar** ([PR #11868](https://github.com/ldtteam/minecolonies/pull/11868)). Impacto em permissões, balance de recursos e integridade de construção. |
| **1.1.1413-snapshot** | 07/11/2026 | Mensagem **GiveToolMessage** passa a ser menos genérica ([PR #11884](https://github.com/ldtteam/minecolonies/pull/11884)); relevância para diagnóstico/UX, sem nova mecânica confirmada. |
| **1.1.1414-snapshot** | 07/11/2026 | Commit upstream `fix order` ([tag](https://github.com/ldtteam/minecolonies/releases/tag/v1.21.1-1.1.1414-snapshot)); escopo funcional **não esclarecido no changelog**, por isso não é atribuído a uma mecânica determinada. |
| **1.1.1416-snapshot** | 08/11/2026 | Corrige **pathfinding em escadas** e crash ao processar resultado malsucedido de pathing ([PR #11883](https://github.com/ldtteam/minecolonies/pull/11883)); adiciona **AI policy** para orientar addons a evitar mixins internos da MineColonies, incluindo cópia em `META-INF` ([PR #11882](https://github.com/ldtteam/minecolonies/pull/11882)). A policy é contrato de compatibilidade, não novo sistema de IA em gameplay. |
| **1.1.1418-snapshot** | 08/11/2026 | **Nova ordem de construção por elementos estruturais de frame horizontal/vertical marcados em schematics** ([PR #11871](https://github.com/ldtteam/minecolonies/pull/11871)); melhora **item transfer** para mover exatamente o stack do jogador ([PR #11885](https://github.com/ldtteam/minecolonies/pull/11885)). Material para visuals de construção e inventário/quantidades, inclusive prevenção de perda/dupe a testar. Não foi localizada publicação 1.1.1417 na série oficial. |
| **1.1.1419-snapshot** | 08/11/2026 | **Restrições de alcance (range)** para interações de muitos packets, especialmente building interaction e colony creation ([PR #11888](https://github.com/ldtteam/minecolonies/pull/11888)); impacto de segurança/permissões e interoperabilidade client/server. |
| **1.1.1420-snapshot** | 11/11/2026 | **Rescan de estilos/schematics** Ancient Athens e MineColonies Original para Minecraft 1.21.1 ([PR #11896](https://github.com/ldtteam/minecolonies/pull/11896)); verificar variações de build plans em mundos já existentes. |
| **1.1.1420 Release** | 15/11/2026 | Artefato `minecolonies-1.1.1420-1.21.1.jar`, **Release/R** no CurseForge [file 9624268](https://www.curseforge.com/minecraft/mc-mods/minecolonies/files/9624268). [Release oficial GitHub](https://github.com/ldtteam/minecolonies/releases/tag/v1.21.1-1.1.1420) não lista commits entre a snapshot 1.1.1420 e essa tag. Isso **não prova identidade binária** entre snapshot e Release. |

**Dependência obrigatória alterada (gate determinante):** releases até 1.1.1416 indicam **Structurize `1.0.832-1.21.1-snapshot`+**; a partir de **1.1.1418** a exigência sobe para **`1.0.837-1.21.1-snapshot`+**. O último snapshot físico do pack aponta **`structurize-1.0.833-1.21.1-snapshot.jar` (#529)**. Logo **1.1.1420 Release não satisfaz esse mínimo com o inventário de referência** e **não deve ser promovida isoladamente**. Os outros mínimos publicados permanecem MultiPiston **1.2.51+**, BlockUI **1.0.199+**, Domum Ornamentum **1.0.223+**; eles eram numericamente atendidos na última comparação histórica, sem prova de runtime atual. Os opcionais publicados são JEI 19.19.6.235+, JourneyMap 1.21.1-6.0.0-beta.29+ e Dynamic Trees 1.5.0-BETA07+.

**Migração/homologação:** antes de trocar o mod-base, atualizar/verificar Structurize com seu próprio inventário e demais libs, e testar em cópia de mundo: (a) pickup/direct placement e packets de construção/claims; (b) raider targeting tag em addons, guards, mercenaries e visitors; (c) builders com schematics de frames e rescan Ancient Athens/Original; (d) demanda, transferência de stacks, couriers e Warehouse sem item loss/duplication; (e) pathfinding em escadas e chunks descarregados; (f) worldgen/saplings; (g) addons Epic Colonies, Tweaks, Compatibility e Jade Crops; (h) cliente e servidor dedicados, save/restart e rollback completo. A PR de AI policy exige cautela adicional com mixins de addons que usam APIs internas.

**Fontes:** [releases oficiais com histórico individual 1406→1420](https://github.com/ldtteam/minecolonies/releases), [file 9624268 CurseForge 1.21.1/NeoForge](https://www.curseforge.com/minecraft/mc-mods/minecolonies/files/9624268), PRs upstream vinculadas por linha. **Auditoria documental completa até a última Release 1.1.1420 identificada, sem execução de testes locais nem alteração do JAR físico.**
