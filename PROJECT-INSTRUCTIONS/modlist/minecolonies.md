# MineColonies

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81c69689ea4c0d0bf87c
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — MineColonies `1.1.1381-1.21.1-snapshot`, Structurize `1.0.833`, Multi-Piston `1.2.58`, BlockUI `1.0.211`, Domum Ornamentum `1.0.236`, JEI `19.53.0.426`, JourneyMap `6.0.7`, Dynamic Trees `1.7.2` e TownTalk `1.2.0` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Divergências físicas detectadas na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 10/09/2026”, porém a autoridade física mais recente realmente acessível nesta execução continua sendo o snapshot de **08/09/2026 com 595 top-levels**.
- O texto-fonte do Notion registra JEI `19.27.0.340`; a autoridade física disponível contém **JEI `19.53.0.426`**.
- O texto-fonte afirma que JourneyMap e Dynamic Trees não foram identificados como top-level naquele lote; a autoridade física disponível contém **JourneyMap `1.21.1-6.0.7`** e **Dynamic Trees `1.7.2`**.
- O conteúdo-fonte abaixo é preservado como documentação do Notion; essas divergências não são normalizadas silenciosamente.

## Propriedades do banco

- **Mod:** MineColonies
- **Arquivo JAR:** `minecolonies-1.1.1381-1.21.1-snapshot.jar`
- **Versão 1.21.1:** 1.1.1381-1.21.1-snapshot
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** RPG, Automação, Worldgen, Armazenamento
- **Tipo de conteúdo:** Mod
- **Função:** Provider central de simulação colonial: colônias persistentes, cidadãos, jobs, buildings/módulos, construção e WorkOrders, request/logística, research, permissões, IA e sincronização Model/View server-authoritative. Addons devem estender essas surfaces sem duplicar authority.
- **Dependências:** Release 1.1.1381 exige Structurize >=1.0.832, Multi-Piston >=1.2.51, BlockUI >=1.0.199 e Domum Ornamentum >=1.0.223. Pack físico atual: 1.0.833 / 1.2.58 / 1.0.211 / 1.0.236, todos acima dos mínimos. JEI opcional >=19.19.6.235; pack usa 19.27.0.340.
- **Sobreposição:** MineColonies é authority da colônia. Structurize/BlockUI/Domum/Multi-Piston fornecem infraestrutura especializada. Jade crops apenas apresenta crops; Compatibility/Let's Do/Tweaks estendem comportamento/integrações. Sistemas próprios de RPG/quests não devem espelhar colony research/job/permission como authority paralela.
- **Compatibilidade/Riscos:** Snapshot central de colônias. Riscos: churn de API, requests/logística, drift com Structurize/BlockUI/Domum/Multi-Piston e addons 3.56/2.1/3.33, colony-data migration, chunk/entity lifecycle, performance/pathfinding, permission bypass e View stale. Request-related fixes são destaque da 1.1.1381.
- **Observações:** Runtime físico/tag oficial `v1.21.1-1.1.1381-snapshot`, publicado em 07/09/2026. Asset oficial possui SHA-256 b6a7d66e2d37b4aa699a2573025715e07db4c2230a94540045385d093c4255bc. Build é snapshot/beta; changelog inclui request-related fixes e fix compile error.
- **Procedência:** modlist.txt física atual de 10/09/2026 + release/tag oficial ldtteam/minecolonies v1.21.1-1.1.1381-snapshot + asset CurseForge 8829862 + source exato da tag + versões físicas atuais das dependências.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/minecolonies
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — release/tag exata 1.1.1381-snapshot; ColonyManager Model/View, persistência, citizens/jobs/buildings/work orders, request system, research, permissions, dependencies, addons, lifecycle, riscos e testes catalogados. QC 10/09 corrigiu versões físicas de Structurize/Multi-Piston/BlockUI/Domum para 1.0.833/1.2.58/1.0.211/1.0.236.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-30

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `minecolonies-1.1.1381-1.21.1-snapshot.jar`, mod id `minecolonies`, exatamente a snapshot oficial `v1.21.1-1.1.1381-snapshot`, publicada em 07/09/2026. A tag oficial e o asset binário têm o mesmo nome do JAR instalado. Esta ficha usa essa tag como authority de source e não projeta código posterior da branch `1.21`.

## 1. Identidade, versão e release
- **Mod:** MineColonies.
- **JAR físico:** `minecolonies-1.1.1381-1.21.1-snapshot.jar`.
- **Versão runtime:** `1.1.1381-1.21.1-snapshot`.
- **Minecraft:** 1.21.1.
- **Loader:** NeoForge.
- **Java:** 21.
- **Upstream:** `ldtteam/minecolonies`.
- **Tag exata:** `v1.21.1-1.1.1381-snapshot`.
- **Release asset SHA-256 oficial:** `b6a7d66e2d37b4aa699a2573025715e07db4c2230a94540045385d093c4255bc`.
- **Natureza da build:** snapshot/beta; não tratar como release estável final.

O changelog da release 1.1.1381 é pequeno e centrado em **request-related fixes** e correção de compilação. Isso não reduz o escopo do mod: a snapshot carrega o sistema completo de colônias acumulado pela linha 1.21.1.

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

A própria release física 1.1.1381 inclui **request-related fixes**, logo qualquer addon que modifique recipes, warehouse/network storage, delivery ou crafting precisa ser regressado especificamente nesta build.

Riscos clássicos neste pack incluem request órfão, resolução duplicada, item entregue sem settlement, recipe incompatível, ciclo de dependências e cache stale após alteração de building/job.

## 9. Construção, Structurize e Domum Ornamentum
MineColonies não deve ser confundido com os providers auxiliares de construção:
- **Structurize** fornece scanning/placement/template infrastructure usada na construção;
- **Domum Ornamentum** fornece blocos decorativos parametrizados consumidos por estilos/construções;
- **Multi-Piston** fornece infraestrutura requerida associada ao ecossistema;
- **BlockUI** fornece a framework de UI usada extensivamente pelas telas.

MineColonies continua authority de decidir **o que a colônia pretende construir**, níveis, work orders e lógica de trabalhadores; esses providers executam partes especializadas do pipeline.

## 10. Dependências oficiais da snapshot 1.1.1381
A publicação oficial exige no mínimo:
- **Structurize** `1.0.832-1.21.1-snapshot+`;
- **Multi-Piston** `1.2.51-1.21.1-snapshot+`;
- **BlockUI** `1.0.199-1.21.1-snapshot+`;
- **Domum Ornamentum** `1.0.223-snapshot+`.

A modlist física atual contém:
- Structurize `1.0.833`;
- Multi-Piston `1.2.58`;
- BlockUI `1.0.211`;
- Domum Ornamentum `1.0.236`.

Todos satisfazem os mínimos publicados da 1.1.1381.

O build também referencia **TownTalk** na lista de dependências Curse; a presença/forma de empacotamento deve ser tratada conforme metadata física e loader, não inferida apenas pelo nome do Gradle.

## 11. Integrações opcionais oficiais
A release lista como opcionais:
- **JEI** `19.19.6.235+`;
- **JourneyMap** `1.21.1-6.0.0-beta.29+`;
- **Dynamic Trees** `1.5.0-BETA07+`.

O pack físico possui JEI `19.27.0.340`, acima do baseline. JourneyMap e Dynamic Trees não foram identificados como top-level na modlist atual durante este lote, portanto suas integrações não são tratadas como ativas.

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

### Compatibility addon 3.56
Extende jobs/recipes/compatibilidades e integra storage/crafting conforme seu próprio dossiê; MineColonies permanece provider da colônia.

### Let's Do addon 2.1
Integra conteúdo Let's Do ao ecossistema colonial; seu versionRange mínimo é menor que 1.1.1381, então loader compatibility é satisfeita, mas regressão comportamental ainda precisa de teste.

### Tweaks addon 3.33
Aplica tweaks/integrações sobre MineColonies; seu mínimo declarado 1.1.1368 é satisfeito pela 1.1.1381. Novamente, isso valida contrato de loader, não todos os comportamentos.

## 17. Riscos específicos da snapshot 1.1.1381 neste pack
1. **Snapshot churn:** APIs internas podem mudar entre snapshots próximas.
2. **Request regressions:** a própria release corrige requests; addons de recipes/storage/crafting são área prioritária.
3. **Dependency drift:** quatro libs obrigatórias estão em snapshots mais novas que os mínimos; precisam de teste conjunto.
4. **Addon ABI drift:** Compatibility/Let's Do/Tweaks podem compilar contra baselines diferentes.
5. **Colony data corruption:** falhas durante save/restart/migration são de alto impacto; backups são mandatórios antes de atualização.
6. **Chunk/entity lifecycle:** citizen entity descarregada não pode ser tratada como ausência de CitizenData.
7. **Performance:** pathfinding, cidadãos, request resolution, construction scanning/placement e sincronização podem escalar com tamanho/número de colônias.
8. **Permission bypass:** addons/mod próprios não devem contornar checks server-side.
9. **UI stale state:** BlockUI/View pode ficar temporariamente atrás do estado real; não usar UI como authority.
10. **Economia/logística duplicada:** bridges Create/Let's Do/storage precisam evitar satisfazer request duas vezes.

## 18. Matriz de testes obrigatória
- [ ] Dedicated server 21.1.248 inicia com MineColonies 1.1.1381 + quatro dependências físicas atuais.
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
- [ ] Compatibility 3.56, Let's Do 2.1 e Tweaks 3.33 passam smoke test conjunto.
- [ ] Backup/restore de mundo conserva colony data antes de qualquer upgrade de snapshot.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 19. Evidências consultadas
- Modlist física atual: `minecolonies-1.1.1381-1.21.1-snapshot.jar`.
- Release/tag oficial `v1.21.1-1.1.1381-snapshot`, asset binário de mesmo nome.
- SHA-256 oficial do asset: `b6a7d66e2d37b4aa699a2573025715e07db4c2230a94540045385d093c4255bc`.
- CurseForge oficial file 8829862: release 1.1.1381 e dependências mínimas/opcionais.
- Source exato da tag, incluindo `gradle.properties` e árvore de ~6.7k entradas.
- `core/colony/package-info.java` da tag: arquitetura ColonyManager/Views, CitizenData, Buildings, Permissions, WorkManager/WorkOrders e Jobs.
- Modlist física para versões atuais de Structurize, Multi-Piston, BlockUI, Domum Ornamentum e JEI.

> **Limite de evidência:** MineColonies é grande e a ficha documenta subsistemas/contratos operacionais, não uma enumeração de cada registry ID, building, job ou recipe. Detalhes de comportamento não demonstrados pela tag/release não foram inferidos. A build é snapshot e exige regressão de runtime antes de upgrades.
