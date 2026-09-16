# MineColonies — 1.1.1387-1.21.1-snapshot

> **Reauditoria física e de migração — 16/09/2026.** A autoridade física atual é `minecolonies-1.1.1387-1.21.1-snapshot.jar`, mod id `minecolonies`, runtime `1.1.1387-1.21.1-snapshot`, SHA-1 `07752c101305dde5e4621cda39b5d7607f614871`, em NeoForge 1.21.1. O conteúdo arquitetural migrado do Notion foi preservado, mas as referências que tratavam 1.1.1381 como instalada foram atualizadas. A snapshot 1.1.1387 introduz no delta direto o port **Cavalry 4of4**. Em 16/09/2026 upstream já publicou `1.1.1392-1.21.1-snapshot`; ela é **update candidate**, não a build instalada. Nenhum teste de runtime foi promovido como executado.

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Autoridade física atual:** `minecolonies-1.1.1387-1.21.1-snapshot.jar`, runtime `1.1.1387-1.21.1-snapshot`
- **Update candidate upstream:** `minecolonies-1.1.1392-1.21.1-snapshot.jar`, publicado em 16/09/2026; não instalado nesta modlist
- **Auditoria de migração Notion → GitHub:** 2026-09-15; reauditoria física 2026-09-16

## Propriedades do registro

- **Mod:** MineColonies
- **Arquivo JAR:** `minecolonies-1.1.1387-1.21.1-snapshot.jar`
- **Versão 1.21.1 / runtime:** `1.1.1387-1.21.1-snapshot`
- **Minecraft / loader:** 1.21.1 / NeoForge
- **Natureza:** snapshot/beta; não tratar como release estável final
- **Categoria:** RPG; Automação; Worldgen; Armazenamento
- **Tipo de conteúdo:** Mod
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/minecolonies
- **Função:** Provider central de simulação colonial: colônias persistentes, cidadãos, jobs, buildings/módulos, construção e WorkOrders, request/logística, research, permissões, IA e sincronização Model/View server-authoritative. Addons devem estender essas surfaces sem duplicar authority.
- **Dependências:** A snapshot 1.1.1387 exige Structurize `>=1.0.832`, Multi-Piston `>=1.2.51`, BlockUI `>=1.0.199` e Domum Ornamentum `>=1.0.223`. Pack físico atual: Structurize `1.0.833`, Multi-Piston `1.2.58`, BlockUI `1.0.212` e Domum Ornamentum `1.0.236`, todos acima dos mínimos. JEI opcional `>=19.19.6.235`; pack usa `19.56.0.440`.
- **Compatibilidade/Riscos:** Snapshot central de colônias. Riscos: churn de API, requests/logística, colony-data migration, chunk/entity lifecycle, performance/pathfinding, permission bypass, View stale e drift com dependências/addons. A build instalada herda correções das snapshots intermediárias e adiciona o port Cavalry 4of4; a 1.1.1392 upstream permanece candidata e não deve ser confundida com runtime atual.
- **Sobreposição:** MineColonies é authority da colônia. Structurize/BlockUI/Domum/Multi-Piston fornecem infraestrutura especializada. Jade crops apenas apresenta crops; Compatibility/Tweaks estendem comportamento/integrações. Sistemas próprios de RPG/quests não devem espelhar colony research/job/permission como authority paralela.
- **Observações:** Runtime físico/tag auditada `v1.21.1-1.1.1387-snapshot`, publicada em 13/09/2026. O changelog direto dessa tag registra `Cavalry 4of4 port`. Em 16/09/2026 a linha upstream avançou para `1.1.1392`; seu changelog inclui novos fixes/port work, mas nada disso é atribuído à build instalada.
- **Procedência:** modlist física de 16/09/2026 + release/tag oficial `v1.21.1-1.1.1387-snapshot` + release upstream `v1.21.1-1.1.1392-snapshot` apenas como update candidate + arquitetura/source já auditada na linha 1.21.1.
- **Histórico da decisão:**
- **Atualização/Status:** REAUDITADO EM 16/09/2026 — estado físico atualizado de 1.1.1381 para 1.1.1387; dependências e addons atuais reconciliados; upstream 1.1.1392 registrado separadamente como candidato.
- **Data da última decisão:** 2026-08-30

> 🏛️ **ESCOPO CANÔNICO.** Runtime físico: `minecolonies-1.1.1387-1.21.1-snapshot.jar`, mod id `minecolonies`, snapshot oficial `v1.21.1-1.1.1387-snapshot`. A modlist física governa presença e versão instalada. A arquitetura Model/View, persistência de Colony/Citizen/Job/Building, WorkOrders, request system, research e permissões permanece o núcleo operacional documentado; o delta direto publicado da 1.1.1387 é o port Cavalry 4of4.

## 1. Identidade, versão e release
- **Mod:** MineColonies.
- **JAR físico:** `minecolonies-1.1.1387-1.21.1-snapshot.jar`.
- **Versão runtime:** `1.1.1387-1.21.1-snapshot`.
- **Minecraft:** 1.21.1.
- **Loader:** NeoForge.
- **Java:** 21.
- **Upstream:** `ldtteam/minecolonies`.
- **Tag instalada:** `v1.21.1-1.1.1387-snapshot`.
- **Commit da release instalada:** `495c35113698c2a48c58fcbd0cfbd5568b23a67e`.
- **SHA-1 físico do JAR instalado:** `07752c101305dde5e4621cda39b5d7607f614871`.
- **Natureza da build:** snapshot/beta.

O changelog direto da release 1.1.1387 registra **Cavalry 4of4 port**. Os subsistemas coloniais abaixo continuam sendo o baseline funcional do mod; a atualização não autoriza tratar Cavalry ou qualquer integração como validada em runtime sem teste.

### Histórico preservado da origem
A página Notion auditada estava ancorada em `1.1.1381` e registrava para aquele asset o SHA-256 `b6a7d66e2d37b4aa699a2573025715e07db4c2230a94540045385d093c4255bc`, além de request-related fixes e um version gate posterior para 1.1.1383. Esse fingerprint é **histórico da build antiga**, não fingerprint da 1.1.1387 atual.

### Update candidate — 1.1.1392
Upstream publicou `v1.21.1-1.1.1392-snapshot` em 16/09/2026. Os mínimos de dependência permanecem na mesma linha e o changelog inclui fixes/port work adicionais. A modlist física continua em 1.1.1387; portanto 1.1.1392 é somente candidato e exige regressão antes de qualquer promoção.

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
A documentação/source da linha 1.21.1 auditada descreve explicitamente a arquitetura do `ColonyManager`.

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
A documentação interna separa deliberadamente o estado por domínio:
- **AITask:** dados necessários ao trabalho imediato, não necessariamente persistentes;
- **CitizenData:** dados do cidadão independentes de job/building;
- **Job:** estado persistente específico do trabalho daquele cidadão;
- **Building:** estado do edifício, não dos trabalhadores;
- **Colony:** estado global da colônia.

A finalidade do ColonyManager é manter residência permanente dessas estruturas **sem depender de chunk/entity estar carregado**. Uma entidade de cidadão descarregada não significa que o cidadão deixou de existir no modelo da colônia.

## 5. Cidadãos, entidades e jobs
Colônias rastreiam cidadãos por `CitizenData`, com IDs inteiros. Quando a entidade correspondente está carregada, o `CitizenData` se vincula ao `CitizenEntity`; o cliente recebe um `CitizenData.View` e, quando aplicável, o entity ID associado.

Jobs ligam um cidadão a um building e fornecem armazenamento persistente específico da função. Eles também participam da representação/modelo do trabalhador.

Consequência operacional: perks, quests ou integrações que precisam identificar profissão devem consultar o job/citizen data apropriado e não inferir ocupação apenas pela skin, entidade próxima ou posição no edifício.

## 6. Buildings, huts e módulos
Buildings server-side representam o estado funcional dos edifícios da colônia e são vinculados ao bloco/hut correspondente no mundo por coordenada. Views equivalentes são sincronizadas a clientes interessados.

O ecossistema moderno de MineColonies usa módulos de building para decompor capacidades. Addons desta modlist podem registrar/alterar módulos, recipes/jobs ou comportamento conectado, mas o building base e seu lifecycle permanecem sob MineColonies.

## 7. WorkManager e WorkOrders
Tarefas coordenadas da colônia são centralizadas em `WorkManager` e subclasses de `WorkOrder`. Construção/upgrade, por exemplo, cria WorkOrders para coordenar o trabalho.

WorkManager/WorkOrders **não são espelhados diretamente ao cliente** como as Views principais. Automação ou UI externa deve usar APIs/mensagens suportadas; não assumir que existe uma lista client-side completa e autoritativa de todas as work orders.

## 8. Request System e logística
O request system é uma das superfícies centrais para cadeia de suprimento: trabalhadores/buildings criam requests, resolvers encontram fontes/crafting/entrega possíveis e o estado do pedido percorre seu lifecycle até resolução/cancelamento.

A origem Notion registrava request-related fixes da snapshot 1.1.1381 e o fix de food handling da 1.1.1383. A 1.1.1387 instalada é posterior e herda essa linha; esses pontos permanecem regression gates para recipes, warehouse/network storage, delivery e crafting.

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

O build também referencia **TownTalk** no ecossistema/publicações; presença/forma de empacotamento deve ser tratada conforme metadata física e loader, não inferida apenas por referências de build.

## 11. Integrações opcionais oficiais
A release lista como opcionais:
- **JEI** `19.19.6.235+`;
- **JourneyMap** `1.21.1-6.0.0-beta.29+`;
- **Dynamic Trees** `1.5.0-BETA07+`.

A autoridade física atual confirma:
- **JEI `19.56.0.440`**;
- **JourneyMap `6.0.8`**;
- **Dynamic Trees `1.7.2`**.

As três integrações existem no inventário atual e entram na regressão, sem transformar compatibilidade de loader em prova de comportamento perfeito.

## 12. Research e progressão
A linha 1.21.1 contém APIs/implementações de **research** e registries relacionados. Research em MineColonies é progressão colonial: unlocks e requisitos pertencem ao provider MineColonies e podem condicionar buildings/jobs/capacidades.

Mods próprios de árvore de habilidades do jogador não devem espelhar research como se fosse atributo individual. A integração correta é consultar/gatear a research colonial quando necessário, preservando as duas authorities.

## 13. Permissões e multiplayer
Cada colônia mantém `Permissions`, modelando relacionamento de jogadores com a colônia; o cliente recebe `Permissions.View`.

Em multiplayer, operações administrativas, building interaction, placement/ownership e mutações de colony data precisam respeitar validação server-side. UI ou addon não deve considerar botão visível como autorização suficiente.

Testes devem incluir owner, officer/friend/neutral/hostile conforme papéis efetivamente configurados, além de jogadores desconectando durante requests/builds.

## 14. Rede, sync e UI
O padrão View/subscriber existe para que clientes recebam dados relevantes sem tornar o cliente owner. BlockUI é infraestrutura central da apresentação.

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

### Compatibility addon 3.57
Extende jobs/recipes/compatibilidades conforme seu próprio dossiê; MineColonies permanece provider da colônia. A versão física atual é `3.57`.

### Tweaks addon 3.33
Aplica tweaks/integrações sobre MineColonies; a versão física atual é `3.33`. Compatibilidade declarada não substitui regressão comportamental.

### Referência histórica a Let's Do
A página Notion anterior registrava um addon Let's Do 2.1 como integração ativa. **Esse addon não foi identificado como top-level na modlist física atual de 16/09/2026**, portanto não é tratado como integração instalada nesta reauditoria. A referência fica preservada apenas como histórico da origem.

## 17. Riscos específicos da snapshot 1.1.1387 neste pack
1. **Snapshot churn:** APIs internas podem mudar entre snapshots próximas.
2. **Request regressions:** requests/logística continuam área prioritária devido ao histórico de fixes.
3. **Dependency drift:** quatro libs obrigatórias estão em builds acima dos mínimos e precisam de teste conjunto.
4. **Addon ABI drift:** Compatibility/Tweaks podem compilar contra baselines diferentes.
5. **Colony data corruption:** falhas durante save/restart/migration são de alto impacto; backups são mandatórios antes de atualização.
6. **Chunk/entity lifecycle:** citizen entity descarregada não pode ser tratada como ausência de CitizenData.
7. **Performance:** pathfinding, cidadãos, request resolution, construction scanning/placement e sincronização podem escalar com tamanho/número de colônias.
8. **Permission bypass:** addons/mod próprios não devem contornar checks server-side.
9. **UI stale state:** BlockUI/View pode ficar temporariamente atrás do estado real; não usar UI como authority.
10. **Economia/logística duplicada:** bridges Create/storage precisam evitar satisfazer request duas vezes.
11. **Cavalry 4of4:** conteúdo portado na 1.1.1387 precisa de regressão de spawn/AI/mount/combat/persistência conforme superfícies efetivamente expostas.
12. **Update candidate 1.1.1392:** não misturar fixes desse snapshot com a build 1.1.1387 instalada.

## 18. Matriz de testes obrigatória
- [ ] Dedicated server NeoForge `21.1.250` inicia com MineColonies 1.1.1387 + quatro dependências físicas atuais.
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
- [ ] JEI, JourneyMap e Dynamic Trees atuais não quebram integração.
- [ ] Compatibility 3.57 e Tweaks 3.33 passam smoke test conjunto.
- [ ] Cavalry da 1.1.1387 passa smoke test funcional/persistência conforme uso real.
- [ ] Backup/restore de mundo conserva colony data antes de qualquer upgrade para 1.1.1392 ou snapshot posterior.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 19. Evidências consultadas
- Modlist física atual: `minecolonies-1.1.1387-1.21.1-snapshot.jar`, runtime, SHA-1 e dependências/addons presentes.
- Release/tag oficial `v1.21.1-1.1.1387-snapshot`, commit `495c35113698c2a48c58fcbd0cfbd5568b23a67e`, changelog `Cavalry 4of4 port` e mínimos de dependência.
- Release/tag upstream `v1.21.1-1.1.1392-snapshot` de 16/09/2026 usada somente como update candidate.
- Arquitetura ColonyManager/Views, CitizenData, Buildings, Permissions, WorkManager/WorkOrders, Jobs e research preservada do source auditado da linha 1.21.1.
- Modlist física para versões atuais de Structurize, Multi-Piston, BlockUI, Domum Ornamentum, JEI, JourneyMap, Dynamic Trees, Jade crops, Compatibility e Tweaks.
- Fingerprint SHA-256 da antiga 1.1.1381 preservado apenas como histórico da origem Notion, não como hash da build atual.

> ⚠️ **Limite de evidência:** MineColonies é grande e a ficha documenta subsistemas/contratos operacionais, não uma enumeração de cada registry ID, building, job ou recipe. A build é snapshot. O conteúdo Cavalry e o update candidate 1.1.1392 não foram runtime-tested nesta auditoria.