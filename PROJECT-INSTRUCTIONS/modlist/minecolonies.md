# MineColonies

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81c69689ea4c0d0bf87c
- **Baseline física pré-update:** `minecolonies-1.1.1381-1.21.1-snapshot.jar`
- **Artefato alvo selecionado no CurseForge:** `minecolonies-1.1.1383-1.21.1-snapshot.jar` — file `8854687`, Beta, publicado em 11/09/2026
- **Dependências físicas baseline:** Structurize `1.0.833`, Multi-Piston `1.2.58`, BlockUI `1.0.211`, Domum Ornamentum `1.0.236`, JEI `19.53.0.426`, JourneyMap `6.0.7`, Dynamic Trees `1.7.2`, TownTalk `1.2.0`
- **Data da atualização documental GitHub:** 2026-09-14

> **BOUNDARY FÍSICO.** O snapshot físico fornecido ainda contém MineColonies 1.1.1381. Este arquivo foi promovido no GitHub para registrar o **alvo de atualização 1.1.1383** pedido pelo usuário. Não interpretar a versão-alvo como confirmação de que o JAR já foi instalado; isso só será confirmado pela próxima modlist/JAR física.

## Propriedades equivalentes do catálogo

- **Mod:** MineColonies
- **Arquivo JAR alvo:** `minecolonies-1.1.1383-1.21.1-snapshot.jar`
- **Versão 1.21.1 alvo:** 1.1.1383-1.21.1-snapshot
- **Baseline física auditada:** 1.1.1381-1.21.1-snapshot
- **Estado da pesquisa:** Verificado documentalmente; validação física/runtime da 1.1.1383 pendente
- **Decisão:** Sem decisão
- **Categoria:** RPG, Automação, Worldgen, Armazenamento
- **Tipo de conteúdo:** Mod
- **Função:** Provider central de simulação colonial: colônias persistentes, cidadãos, jobs, buildings/módulos, construção e WorkOrders, request/logística, research, permissões, IA e sincronização Model/View server-authoritative. Addons devem estender essas surfaces sem duplicar authority.
- **Dependências:** A linha 1.1.1383 mantém os mínimos publicados do stack: Structurize >=1.0.832, Multi-Piston >=1.2.51, BlockUI >=1.0.199 e Domum Ornamentum >=1.0.223. O snapshot físico auditado possui 1.0.833 / 1.2.58 / 1.0.211 / 1.0.236, todos acima dos mínimos. JEI é opcional >=19.19.6.235.
- **Sobreposição:** MineColonies é authority da colônia. Structurize/BlockUI/Domum/Multi-Piston fornecem infraestrutura especializada. Jade crops apenas apresenta crops; Compatibility/Let's Do/Tweaks estendem comportamento/integrações. Sistemas próprios de RPG/quests não devem espelhar colony research/job/permission como authority paralela.
- **Compatibilidade/Riscos:** Snapshot central de colônias. Riscos: churn de API, requests/logística, food handling, drift com Structurize/BlockUI/Domum/Multi-Piston e addons 3.56/2.1/3.33, colony-data migration, chunk/entity lifecycle, performance/pathfinding, permission bypass e View stale.
- **Observações:** 1.1.1383 é snapshot/beta. O changelog público da atualização registra `fix food handling`; nenhuma mudança adicional foi inferida. O corpo arquitetural abaixo continua source-pinned à 1.1.1381 onde explicitamente indicado e é usado como baseline até inspeção source/JAR da 1.1.1383.
- **Procedência:** modlist.txt física baseline + CurseForge oficial MineColonies file 8854687 / 1.1.1383-snapshot + source/tag 1.1.1381 já auditada para arquitetura + relações/dependências oficiais.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/minecolonies
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 14/09/2026 — GitHub promovido para alvo 1.1.1383-snapshot; delta público `fix food handling` registrado. Baseline física 1.1.1381 preservada fail-closed até re-fetch da modlist/JAR pós-update.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-30

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO DE ARQUITETURA.** A arquitetura detalhada abaixo foi validada contra `minecolonies-1.1.1381-1.21.1-snapshot.jar` e a tag oficial correspondente. O alvo de atualização é 1.1.1383-snapshot. Onde não há evidência release-specific da 1.1.1383, o texto permanece explicitamente baseline e não projeta alterações internas.

## 1. Identidade, versão e release
- **Mod:** MineColonies.
- **JAR físico baseline:** `minecolonies-1.1.1381-1.21.1-snapshot.jar`.
- **JAR alvo:** `minecolonies-1.1.1383-1.21.1-snapshot.jar`.
- **Minecraft:** 1.21.1.
- **Loader:** NeoForge.
- **Java:** 21.
- **Upstream:** `ldtteam/minecolonies`.
- **Canal do alvo:** snapshot/beta; não tratar como release estável final.

A release 1.1.1383 foi publicada em 11/09/2026. O changelog público relevante para esse salto registra **`fix food handling`**. Não foram atribuídas outras mudanças à 1.1.1383 sem evidência.

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
O `package-info.java` da tag 1.1.1381 auditada documenta explicitamente a arquitetura do `ColonyManager`.

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
A documentação interna da baseline separa deliberadamente o estado por domínio:
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

A baseline física 1.1.1381 incluiu request-related fixes. Qualquer addon que modifique recipes, warehouse/network storage, delivery ou crafting precisa ser regressado após a atualização para 1.1.1383.

Riscos clássicos neste pack incluem request órfão, resolução duplicada, item entregue sem settlement, recipe incompatível, ciclo de dependências e cache stale após alteração de building/job.

## 9. Construção, Structurize e Domum Ornamentum
MineColonies não deve ser confundido com os providers auxiliares de construção:
- **Structurize** fornece scanning/placement/template infrastructure usada na construção;
- **Domum Ornamentum** fornece blocos decorativos parametrizados consumidos por estilos/construções;
- **Multi-Piston** fornece infraestrutura requerida associada ao ecossistema;
- **BlockUI** fornece a framework de UI usada extensivamente pelas telas.

MineColonies continua authority de decidir **o que a colônia pretende construir**, níveis, work orders e lógica de trabalhadores; esses providers executam partes especializadas do pipeline.

## 10. Dependências oficiais da linha alvo
Os mínimos publicados relevantes permanecem:
- **Structurize** `1.0.832-1.21.1-snapshot+`;
- **Multi-Piston** `1.2.51-1.21.1-snapshot+`;
- **BlockUI** `1.0.199-1.21.1-snapshot+`;
- **Domum Ornamentum** `1.0.223-snapshot+`.

A modlist física baseline contém:
- Structurize `1.0.833`;
- Multi-Piston `1.2.58`;
- BlockUI `1.0.211`;
- Domum Ornamentum `1.0.236`.

Todos satisfazem os mínimos publicados. O build também referencia **TownTalk** na lista de dependências Curse; a presença/forma de empacotamento deve ser tratada conforme metadata física e loader, não inferida apenas pelo nome do Gradle.

## 11. Integrações opcionais oficiais
A linha publica como opcionais:
- **JEI** `19.19.6.235+`;
- **JourneyMap** `1.21.1-6.0.0-beta.29+`;
- **Dynamic Trees** `1.5.0-BETA07+`.

O snapshot físico disponibilizado contém JEI `19.53.0.426`, JourneyMap `6.0.7` e Dynamic Trees `1.7.2`, portanto essas integrações existem no inventário atual e devem entrar na regressão pós-update.

## 12. Research e progressão
A árvore da tag baseline contém APIs/implementações de **research** e registries relacionados. Research em MineColonies é progressão colonial: unlocks e requisitos pertencem ao provider MineColonies e podem condicionar buildings/jobs/capacidades.

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

## 16. Addons físicos diretamente relacionados
### MineColonies: Jade crops 1.1.1300
Somente apresenta estágio de crops MineColonies no Jade. Não altera colony state.

### Compatibility addon 3.56
Estende jobs/recipes/compatibilidades e integra storage/crafting conforme seu próprio dossiê; MineColonies permanece provider da colônia.

### Let's Do addon 2.1
Integra conteúdo Let's Do ao ecossistema colonial; seu versionRange mínimo é menor que a baseline e precisa ser regressado com a 1.1.1383.

### Tweaks addon 3.33
Aplica tweaks/integrações sobre MineColonies; o mínimo declarado 1.1.1368 é satisfeito, mas isso valida contrato de loader, não todos os comportamentos.

## 17. Delta 1.1.1381 → 1.1.1383
O delta público confirmado para a versão-alvo é estreito: **`fix food handling`**.

Isso torna obrigatório regredir o caminho de alimentos da colônia, especialmente workers/citizens consumindo, solicitando, transportando e reconhecendo alimentos quando outros mods culinários do pack alteram tags/recipes. Não atribuir outros fixes à 1.1.1383 sem changelog/source correspondente.

## 18. Riscos específicos neste pack
1. **Snapshot churn:** APIs internas podem mudar entre snapshots próximas.
2. **Food handling regression:** é precisamente a área alterada pela 1.1.1383.
3. **Request/logística:** alimento passa pelo mesmo ecossistema de requests, warehouse/courier e crafting.
4. **Dependency drift:** quatro libs obrigatórias estão em snapshots mais novas que os mínimos; precisam de teste conjunto.
5. **Addon ABI drift:** Compatibility/Let's Do/Tweaks podem compilar contra baselines diferentes.
6. **Colony data corruption:** falhas durante save/restart/migration são de alto impacto; backups são mandatórios antes da troca.
7. **Chunk/entity lifecycle:** citizen entity descarregada não pode ser tratada como ausência de CitizenData.
8. **Performance:** pathfinding, cidadãos, request resolution, construction scanning/placement e sincronização podem escalar com tamanho/número de colônias.
9. **Permission bypass:** addons/mod próprios não devem contornar checks server-side.
10. **UI stale state:** BlockUI/View pode ficar temporariamente atrás do estado real; não usar UI como authority.
11. **Economia/logística duplicada:** bridges Create/Let's Do/storage precisam evitar satisfazer request duas vezes.

## 19. Matriz de testes obrigatória
- [ ] Nova modlist física confirma `minecolonies-1.1.1383-1.21.1-snapshot.jar` após a atualização.
- [ ] Dedicated server inicia com MineColonies 1.1.1383 + quatro dependências físicas atuais.
- [ ] Criar nova colônia e reiniciar servidor; ID, owner, território e dados persistem.
- [ ] CitizenData persiste após unload/reload e volta a vincular à entidade correta.
- [ ] Contratar/atribuir cidadão a job/building e validar persistência após restart.
- [ ] Construção e upgrade criam/concluem WorkOrder sem duplicação.
- [ ] Request simples de item: criação → resolução → entrega → settlement uma vez.
- [ ] Request de crafting encadeado não cria loop/órfão.
- [ ] Worker/citizen reconhece, solicita, recebe e consome food corretamente — regression gate 1.1.1383.
- [ ] Food/tags de Farmer's Delight e addons culinários não criam request órfão ou item inelegível inesperado.
- [ ] Warehouse/courier e addons de storage não duplicam entrega.
- [ ] Research unlock persiste e aplica somente uma vez.
- [ ] Owner vs outro jogador: permissões são validadas server-side.
- [ ] Dois clientes observando a mesma colônia recebem Views coerentes.
- [ ] Desconectar/reconectar durante construção/request não perde estado.
- [ ] Chunk unload do trabalhador não apaga CitizenData/job/request.
- [ ] Structurize placement e Domum Ornamentum blocks funcionam no estilo escolhido.
- [ ] JEI/JourneyMap/Dynamic Trees atuais não quebram integração.
- [ ] Compatibility 3.56, Let's Do 2.1 e Tweaks 3.33 passam smoke test conjunto.
- [ ] Backup/restore de mundo conserva colony data antes/depois do upgrade de snapshot.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 20. Evidências consultadas
- Modlist física baseline: `minecolonies-1.1.1381-1.21.1-snapshot.jar`.
- CurseForge oficial: `minecolonies-1.1.1383-1.21.1-snapshot.jar`, file 8854687, Beta, 11/09/2026.
- Changelog público 1.1.1383: `fix food handling`.
- Source/tag 1.1.1381 já auditada para ColonyManager/Views, CitizenData, Buildings, Permissions, WorkManager/WorkOrders e Jobs.
- Relações oficiais de dependências mínimas/opcionais da linha 1.21.1.
- Modlist física baseline para versões de Structurize, Multi-Piston, BlockUI, Domum Ornamentum, JEI, JourneyMap e Dynamic Trees.

> **Limite de evidência:** não foi realizada inspeção binária da 1.1.1383 nem runtime QA nesta etapa. O GitHub registra a versão-alvo e seu delta confirmado; a próxima modlist/JAR física continua sendo a autoridade para afirmar instalação real.