# Collective

> **Autoridade física atual — 22/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#103**: JAR `collective-1.21.1-8.39.jar`, mod id `collective`, runtime `8.39`, SHA-1 `b1153f03c97bccaa6bc11d6199f07f16ef4318ff`.

## Propriedades do registro

- **Mod:** Collective
- **Arquivo JAR:** `collective-1.21.1-8.39.jar`
- **Versão 1.21.1:** `8.39`
- **Categoria:** Biblioteca
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/collective
- **Função:** Biblioteca compartilhada com código comum usado pelos mods do ecossistema Serilum; não adiciona um sistema jogável autônomo.
- **Dependências:** Library Client & Server. Necessidade determinada pelos mods Serilum instalados que declaram Collective; não remover nem substituir por outra API enquanto consumers ativos dependerem dela.
- **Compatibilidade/Riscos:** Shared library consumer-driven. Riscos: API/version drift, classloading client-only, duplicate callbacks e consumer attribution. Upstream 8.40 altera somente formatação/indentação de source e não publica mudança funcional.
- **Sobreposição:** Biblioteca específica de Serilum. Coexistência com outras utility/config/network libraries não implica redundância binária; consumers usam contracts próprios.
- **Observações:** Runtime físico permanece Collective 8.39. Upstream publicou **8.40** para Minecraft 1.21.1 em 21/09/2026; o changelog registra apenas normalização da indentação dos source files para tabs, sem delta funcional.
- **Procedência:** modlist física atual + CurseForge oficial Collective 8.39/8.40. A autoridade instalada permanece `collective-1.21.1-8.39.jar`.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Collective 8.39 foi reconfirmado na modlist física e reconstruído como library do ecossistema Serilum. A presença por dependência não foi convertida em decisão curatorial.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — Collective físico permanece 8.39. A release 8.40 foi comparada e não contém mudança funcional publicada.
- **Data da última decisão:** não definida

# Dossiê operacional — padrão Alex's Mobs
> ✅ Versão física confirmada: `collective-1.21.1-8.39.jar`, mod id `collective`, runtime `8.39`, Minecraft 1.21.1. Collective é a **shared library dos mods Serilum**.
## 1. Papel e authority
Collective concentra código comum reutilizado por mods Serilum. O consumer continua authority de qualquer gameplay, comando, configuração ou evento final.
A library não deve ser catalogada como feature autônoma apenas porque participa da implementação de vários mods.
## 2. Dependency graph
A necessidade é consumer-driven. Remover Collective com um consumer que a declara pode impedir bootstrap ou provocar linkage errors.
Outra biblioteca com helpers semelhantes não substitui Collective sem portar o consumer.
## 3. Release 8.39
A release física `1.21.1-8.39` atualiza `MessageFunctions` para lidar melhor com mensagens client-only. Esse é o delta oficial documentado para a build.
Não inferir mudanças de gameplay, registries ou APIs não descritas pelo changelog.
## 4. Mensagens e side boundary
Helpers de mensagens podem ser usados por consumers para feedback de chat/UI. Uma mensagem exibida no cliente não deve liquidar gameplay nem autorizar ação server-side.
Consumers precisam separar notification/presentation do state final do servidor.
## 5. Client/server
Collective é publicado como Client & Server. Common helpers podem existir em ambos os lados, mas caminhos client-only precisam permanecer protegidos contra classloading em dedicated server.
O fix 8.39 torna essa fronteira especialmente relevante para QA.
## 6. Config e utilities
Collective compartilha utilities entre muitos mods Serilum, mas cada consumer define sua própria configuração e semântica. Não há um config global do pack que deva ser tratado como authority de todos os consumers apenas por estar na library.
## 7. Lifecycle
Validar bootstrap dos consumers, world join, disconnect/reconnect, config load/reload quando aplicável e server restart. Shared callbacks/helpers não devem registrar duas vezes nem manter referência a mundo/client anterior.
## 8. Version drift
Sintomas típicos: missing method/class, erro de loader, consumer que abre mas perde feature, ou mensagem executada no side errado. Diagnóstico deve registrar Collective 8.39 e o consumer exato.
## 9. Riscos
1. Remover com consumer ativo.
2. Atualizar library fora da faixa esperada por consumer.
3. Client-only message path carregar em servidor.
4. Helper compartilhado duplicar callback após lifecycle.
5. Confundir utility da library com gameplay authority.
## 10. Matriz de testes
1. Dedicated server boot com todos consumers Serilum atuais.
2. Client join sem linkage errors.
3. Smoke-test de consumidores que emitem mensagens client-only.
4. Disconnect/reconnect sem duplicate callbacks/messages.
5. Config save/reload de consumidores selecionados.
6. Atualização futura: smoke-test conjunto de library + consumers.
## 11. Evidência
- modlist física atual: Collective 8.39;
- CurseForge oficial: shared library com common code para mods Serilum, Client & Server;
- release 1.21.1-8.39/File ID 8341460;
- changelog 8.39: melhoria de `MessageFunctions` para client-only messages.
> 📚 Boundary canônico: Collective fornece **código compartilhado**; cada mod Serilum consumidor mantém authority de sua própria feature.


## 12. Atualização upstream 8.40 — não instalada
A release **Collective 8.40** para Minecraft 1.21.1 foi publicada em 21/09/2026.

Changelog oficial: **normalização da indentação de todos os source files para tabs**.

Não há mudança funcional, API ou gameplay publicada para esta release. Portanto o dossiê mantém 8.39 como runtime físico e registra 8.40 apenas para rastreabilidade de versão.

Gate de eventual promoção: boot com consumers Serilum, linkage, mensagens client-only, reconnect e callbacks; nenhum comportamento novo é esperado pela release note.

Fonte upstream: CurseForge file ID 8940150, Collective 8.40.

## 13. Atualização upstream 8.41 — não instalada
Depois da 8.40 sem delta funcional, o upstream publicou **Collective 8.41** para Minecraft 1.21.1 em 02/10/2026.

O changelog desta release registra atualização das **informações/metadados do mod** em `mods.toml` e `fabric.mod.json`. Não foi publicada mudança de API, callback, networking, config ou gameplay associada à 8.41.

**Delta completo 8.39 → 8.41:**
- 8.40: normalização de indentação do source para tabs;
- 8.41: atualização de metadata do mod.

A autoridade física permanece **Collective 8.39**. A 8.41 é a mais recente upstream, mas não há benefício funcional documentado que justifique tratá-la como correção obrigatória; eventual promoção ainda deve passar pelo smoke-test de consumers Serilum.

Fonte upstream: CurseForge Collective 8.41, release NeoForge 1.21.1 de 02/10/2026.

## 14. Atualizações Collective 8.42 → 8.43 — 10/10/2026

**Físico:** `collective-1.21.1-8.39.jar` / 8.39. A cadeia já documentada inclui **8.40 (21/09, alteração de indentação do source)** e **8.41 (02/10, atualização de `mods.toml`/`fabric.mod.json`)**. A listagem oficial confirma releases adicionais **8.42 (04/10)** e **8.43 (10/10)** para **Minecraft 1.21.1**, com distribuição conjunta Fabric/Forge/NeoForge; **8.43 é a última da linha verificável nesta data**.

### 8.42
Publicação do artefato 1.21.1 confirmada, porém **não foi isolada a nota técnica específica da 8.42**; não presumir ausência de alterações ou atribuir automaticamente mudanças da 8.43 à 8.42.

### 8.43
O changelog publicado pelo autor para a versão 8.43 (também veiculada em builds de outras versões de Minecraft) menciona:
- adição de traduções do **Daily Quest**;
- helper **`MessageFunctions.getTranslatableMessageComponent`**;
- correção **Forge/NeoForge de packets ocasionalmente não registrados quando mods carregam simultaneamente**.

**Limite de evidência:** a versão 8.43 é confirmadamente publicada para MC 1.21.1, porém as notas acima foram consultadas no texto da série 8.43 em plataforma multicarga. **Verificar correspondência no JAR/changelog exato de `collective-1.21.1-8.43.jar` antes de garantir que todos os deltas estão presentes**. O registro de packets em carregamento paralelo representa impacto de confiabilidade de network/loader, não nova mecânica de gameplay da library.

**Cadeia integral:** 8.39 → 8.40 → 8.41 → 8.42 → 8.43. Testes: bootstrap NeoForge21.1.250, consumidores Serilum e seus packet handlers, handshake/multiplayer com load concorrente, mensagens traduzíveis, config/reload, client/dedicated server e regressão das mensagens client-only corrigidas na 8.39.

**Fontes:** https://www.curseforge.com/minecraft/mc-mods/collective/files/all?gameVersionTypeId=5&page=1 ; https://www.curseforge.com/minecraft/mc-mods/collective/files/9119660 (changelog 8.43 multiversão).

**Estado:** upstream 8.43; físico 8.39, sem testes locais.

## Atualização upstream e gate de manutenção — 11/10/2026

**Upstream NeoForge Minecraft 1.21.1:** `collective-1.21.1-8.45.jar`, publicado em **11/10/2026**, com 8.44 em 10/10 após a 8.43 já documentada. O último físico permanece **8.39**. O changelog individual das 8.44/8.45 ainda deve ser correlacionado ao JAR exato; não deduzir features pelo número. **Regressões prioritárias:** packet registration sob carga simultânea, side-correct networking client/server, helpers de mensagens e translations, config/reload, consumers Serilum, handshake multijogador e servidor dedicado. **Fonte:** https://www.curseforge.com/minecraft/mc-mods/collective/files/all

> **Limite:** revisão documental baseada no último snapshot físico; nenhuma instalação de JAR ou validação in-game nesta execução.

## Atualização de publicação e gates — 13/10/2026

**Mod:** Collective. **Última release NeoForge 1.21.1 identificada:** `8.47`. **Fonte:** https://www.curseforge.com/minecraft/mc-mods/collective/files/all?version=1.21.1.

**Release NeoForge 1.21.1 de 12/10/2026:** 8.47 (após 8.43–8.46). As notas públicas da 8.47 exibem correções específicas para **Minecraft 26.2+** e **1.20.1**, as quais **não** devem ser transferidas por analogia à build 1.21.1. Não é possível identificar delta funcional exclusivo da 1.21.1 8.47 sem artefato/changelog exato. Migrar em conjunto com consumers Serilum; riscos em packet registration durante boot paralelo, config, network client/server, localization, dedicated server, reload e handshake. Última física 8.39.

**Fonte física:** `modlist(1).txt` continua sendo o último snapshot acessível. Não se verificou instalação nova nem JAR/metadata/hash atual; manter versão histórica no filename `✅-... vX.md` e na linha física do índice até novo dump e smoke tests. **Estado:** atualização documental concluída na branch; promoção física **PENDENTE/BLOQUEADA** conforme os gates.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** Collective
- **Último candidato 1.21.1 / NeoForge localizado:** **8.62 (NeoForge 1.21.1, 21/10)**
- **Estado:** **CANDIDATO PENDENTE**
- **Gate de promoção:** Biblioteca compartilhada Serilum: revalidar todos os consumers instalados, configs/events e boot dedicado; não atualizar isoladamente sem smoke do conjunto.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/collective

## Revalidação incremental de catálogo — 23/10/2026 (Collective 8.63–8.65)

**Autoridade física inalterada:** `collective-1.21.1-8.39.jar` / 8.39 no último inventário físico documentado. Nenhuma instalação ou execução de testes foi feita.

A listagem oficial do projeto confirma três releases **Minecraft 1.21.1** posteriores ao candidato 8.62 registrado em 22/10:
- **8.63**, Release, 22/10/2026;
- **8.64**, Release, 22/10/2026;
- **8.65**, Release, 22/10/2026, último candidato exibido para Minecraft 1.21.1 na consulta desta revisão.

Fonte primária para versões, datas, loader e classificação `API and Library`: https://www.curseforge.com/minecraft/mc-mods/collective/files/all?page=1&version=1.21

**Deltas de funcionalidade ainda não certificados:** a listagem confirma as publicações, mas esta revisão não extraiu os três changelogs *dos artefatos exatos 1.21.1*. Os changelogs oficiais da série multicarga 8.61/8.62/8.64 relatam, respectivamente, editor de listas de texto e correção de checagens de block entities em chunks não carregados; correções de respawn anchors e de dados de schematics; e evento unificado de verificação de spawn com centralização de variáveis em cache. Essas notas aparecem em páginas de builds **26.2/26.3**, por isso **não são atribuídas sem prova aos JARs 1.21.1**. Fonte de controle: https://www.curseforge.com/minecraft/mc-mods/collective/files/9287886 .

**Cadeia a fechar:** as versões 8.40–8.47 já possuem registros anteriores com graus variáveis de evidência; faltam conciliar individualmente os deltas 8.48–8.65, inclusive a existência/ausência de builds numéricas intermediárias não publicadas para 1.21.1. Verificar o changelog exato ou source/tag e identificar mudanças de API, eventos, callbacks, packet registration, serialização e configs antes de aprovar o upgrade.

**Gate de promoção:** atualizar/testar em ambiente de cópia todos os consumidores Serilum do modpack, client/server dedicados, bootstrap concorrente, eventos de spawn, chunk boundaries, schematics, respawn anchor, config UI, reload, handshake e save/restart. **Status: documentação do catálogo atualizada; auditoria release-by-release e QA ainda pendentes. Manter o item 52 na lista do Bloco de Notas.**


## Conciliação integral da sequência publicada 8.39 → 8.65 — 23/10/2026

> **Esta seção substitui explicitamente o bloqueio do relatório "Revalidação incremental de catálogo — 23/10/2026".** Foi localizada a **branch upstream oficial `1.21.1` do Collective**, com mensagens de commit de versão que permitem identificar os deltas intermediários sem transplantar sem ressalvas changelogs de outras versões de Minecraft. A última autoridade física disponível continua **`collective-1.21.1-8.39.jar` / 8.39**, sem substituição de JAR.

### Matriz de releases e mudanças por versão

| Versão upstream | Evidência upstream 1.21.1 | Delta descrito pelo autor e aplicabilidade ao pack |
|---|---|---|
| 8.40 | [commit 5dc09d1](https://github.com/Serilum/Collective/commit/5dc09d1dd984) | Normalização de indentação do código-fonte para tabs; **sem mudança funcional**. |
| 8.41 | [commit 9ee1eea](https://github.com/Serilum/Collective/commit/9ee1eeab5606) | Atualização de informações em `mods.toml` e `fabric.mod.json`; mudança de metadata, não de gameplay. |
| 8.42 | [commit 1babce0](https://github.com/Serilum/Collective/commit/1babce01d1b2) | Fix do aviso de configurações experimentais do mod Hide Experimental Warning, **especificamente indicado como Forge**; não atribuir efeito NeoForge não comprovado. |
| 8.43 | [commit a7a996b](https://github.com/Serilum/Collective/commit/a7a996b87fc4) | Traduções do Daily Quest adicionadas; mudanças de localização. |
| 8.44 | [commit ca47368](https://github.com/Serilum/Collective/commit/ca47368c67ed) | Completadas linhas restantes de tradução do Daily Quests. |
| 8.45 | [commit 722c797](https://github.com/Serilum/Collective/commit/722c797b6e96) | Corrigido reset esporádico de configurações de mods para o default no startup; relevante para persistência e migração de config. |
| 8.46 | [commit dc6b633](https://github.com/Serilum/Collective/commit/dc6b63378943) | Fix de mineração de blocos através do Immersive Portals, **marcado Fabric-only**; não extrapolar para NeoForge. |
| 8.47 | [commit 4e1d7c4](https://github.com/Serilum/Collective/commit/4e1d7c4956ac) | Correção dos overlays de GUI Clock/Compass quando overlays de hitbox, chunk borders ou debug estão ativos, **indicada apenas para Minecraft 26.2+**, não para 1.21.1. |
| 8.49 | [commit c009865](https://github.com/Serilum/Collective/commit/c009865d3f49) | Traduções do Stack Refill. |
| 8.50 | [commit 196cf96](https://github.com/Serilum/Collective/commit/196cf960aacd) | Traduções do GUI Followers. |
| 8.51 | [commit ce98551](https://github.com/Serilum/Collective/commit/ce98551c27e5) | Evento `block break` no serviço de eventos cross-loader: nova superfície para addons/consumers. |
| 8.52 | [commit 76e427b](https://github.com/Serilum/Collective/commit/76e427b7dbde) | Corrigidas buscas/localizações de vilas e biomas quando idioma do jogo não é inglês. |
| 8.53 | [commit 5c93e46](https://github.com/Serilum/Collective/commit/5c93e466d9e7) | Evento de drops de entidades vivas, **descrito como específico do Fabric**; outras plataformas possuem seus próprios hooks. |
| 8.54 | [commit 0b39707](https://github.com/Serilum/Collective/commit/0b3970710ca9) | **Mudança estrutural importante**: reescrita das configurações, arquivos passam a **`.json`** e arquivos `.json5` antigos são convertidos automaticamente. Salvar cópia dos configs antes da promoção e comparar valores após a conversão. |
| 8.56 | [commit a023065](https://github.com/Serilum/Collective/commit/a023065aa2f8) | **Mudança estrutural importante** da série 8.55–8.56: configs de mods Serilum movidos para **`./config/serilum/...`**; checar scripts, paths, backups, sincronismo client/server e valores migrados. O upstream agrupa os commits como 8.55–8.56, mas o catálogo 1.21.1 mostra o pacote 8.56, **sem release 8.55 publicada nesse filtro**. |
| 8.57 | [commit 6e1c77b](https://github.com/Serilum/Collective/commit/6e1c77be1b8b) | Ajustes finais da ampla reestruturação das configurações. A mensagem não especifica a alteração exclusiva; sem inferir detalhe inexistente. |
| 8.58 | [commit d1770ba](https://github.com/Serilum/Collective/commit/d1770ba54189) | Correção de desaceleração do jogo provocada por checagem **a cada frame** pelo código da tela de configurações. |
| 8.59 | [commit dfb9375](https://github.com/Serilum/Collective/commit/dfb937533de7) | Novos filtros por dimensão e bioma no sistema de substituição de spawns: API/contrato importante para futuros mods de spawn. |
| 8.60 | [commit 7553a33](https://github.com/Serilum/Collective/commit/7553a33cabe6) | Detecção ampliada de villagers e traders modificados por mods, inclusive entidades fora da lista tradicional. |
| 8.61 | [commit 9fc2f77](https://github.com/Serilum/Collective/commit/9fc2f77000c1) | Novo editor de listas de texto na GUI de configuração, uma entrada por linha. |
| 8.62 | [commit 87f6183](https://github.com/Serilum/Collective/commit/87f618394940) | Corrigido consumo indevido de **uma carga do Respawn Anchor** quando um mod apenas consulta o ponto de respawn do jogador. |
| 8.63 | [commit 7e43d17](https://github.com/Serilum/Collective/commit/7e43d179f2a1) | Geração de drops de mobs pelo **All Loot Drops** passa a ocorrer gradualmente após o início do servidor; reduz pico de trabalho de boot. |
| 8.64 | [commit 546b802](https://github.com/Serilum/Collective/commit/546b8026539d) | Novo **evento unificado de verificação de spawn** com comportamento uniforme entre mod loaders e origens de spawn; impacto em consumidores que alteram spawning. |
| 8.65 | [commit eb15798](https://github.com/Serilum/Collective/commit/eb1579874369) | Último candidato da série; commit descreve apenas **backport para NeoForge 47.1 de Minecraft 1.20.1**, não uma correção funcional atribuível ao MC 1.21.1. Não inventar novo comportamento para a distribuição 1.21.1. |

**Números sem release 1.21.1:** 8.48 e 8.55 não constam como JARs separados na sequência de publicações NeoForge 1.21.1 consultada; o upstream registra alterações cruzadas entre branches, mas isso não cria artificialmente versões instaláveis nessa linha. Esta tabela descreve **todas as publicações posteriores ao baseline 8.39 até 8.65** e assinala quando um commit afeta exclusivamente Fabric, Forge, MC 26.2+ ou MC 1.20.1.

### Novos pontos técnicos obrigatórios no dossier

- **Migração de configuração:** as versões **8.54** e **8.56** alteram, respectivamente, formato `.json5` → `.json` e diretório padrão para `config/serilum/`. Antes da futura substituição do JAR, fazer backup de `config/` e conferir que nenhuma configuração ou script continue apontando a arquivos antigos; comparar valores carregados após primeira execução e após reinício.
- **Eventos/compatibilidade:** novos eventos de quebra de blocos (8.51) e de verificação de spawn (8.64), e filtros de dimensão/bioma (8.59), pedem verificação com mods Serilum e outros consumidores de eventos que possam interceptar essas operações.
- **Integridade de jogo:** 8.45 (reset de configurações), 8.52 (locale), 8.58 (UI performance), 8.62 (carga de respawn anchor), 8.63 (carga de geração de drops no startup) e 8.60 (villagers de mods) representam correções testáveis por smoke-tests direcionados.
- **Testes de regressão na futura instalação:** dedicated server/cliente, mudanças de side, multiplayer/handshake, load concorrente, translation UI, save/restart, backups de config, conversão e paths, eventos de bloco/spawn, respawn anchor, mods Serilum instalados, All Loot Drops se presente.

**Resultado auditável:** diferencial documental `8.39 → 8.65` **reconciliado versão por versão no histórico primário da branch 1.21.1**; ainda **não foi efetuada atualização física para 8.65 nem QA em runtime**. Este dossier pode sair da lista de **pendências de pesquisa/atualização documental**; testes de instalação, se decididos, são outra etapa. Fontes: https://github.com/Serilum/Collective/commits/1.21.1 ; https://www.curseforge.com/minecraft/mc-mods/collective/files/all?version=1.21.1 .
