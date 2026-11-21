# ModernFix

## Propriedades do registro

- **Mod:** ModernFix
- **Arquivo JAR:** modernfix-neoforge-5.27.24+mc1.21.1.jar
- **Versão 1.21.1:** 5.27.24+mc1.21.1
- **Categoria:** Performance
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/modernfix/files/8774737 | https://github.com/embeddedt/ModernFix
- **Função:** Conjunto configurável de correções e otimizações para Minecraft modded, abrangendo launch/world load, memória, resources/models/caches e outros caminhos internos.
- **Dependências:** NeoForge/Minecraft 1.21.1 conforme a release instalada. Não tratar FerriteCore/FastSuite como dependências; são coinstalados com funções parcialmente complementares.
- **Compatibilidade/Riscos:** Stack de patches configuráveis. Riscos: mixin collision, config drift, caches/resources/models e regressões de world load. Há histórico na linha 1.21.1 envolvendo `mixin.perf.dynamic_resources` + Create Teleporters; o pack possui Create Teleporters Remastered, mas nenhum conflito atual 5.27.24 foi confirmado.
- **Sobreposição:** Objetivos parcialmente se cruzam com FerriteCore/FastSuite/outros performance mods, mas não há redundância integral comprovada. Remoção só com profiling ou conflito reproduzível.
- **Observações:** JAR físico `modernfix-neoforge-5.27.24+mc1.21.1.jar`, file ID 8774737, Release de 31/08/2026. Upstream usa desenvolvimento contínuo; não foi pinado tag/commit byte-equivalente a 5.27.24 neste lote.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial da release 5.27.24 + source/wiki oficial embeddedt/ModernFix; source HEAD usado apenas para arquitetura, não como pin binário.
- **Atualização/Status:** REVALIDADO EM 13/09/2026.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-09-10

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #402: JAR `modernfix-neoforge-5.27.24+mc1.21.1.jar`, mod id `modernfix`, runtime `5.27.24+mc1.21.1`, SHA-1 `1fe7f8b4176e86507cc8ed5460b6acdd3dac5529`.

<callout icon="🔎" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `modernfix-neoforge-5.27.24+mc1.21.1.jar`, mod id `modernfix`, versão `5.27.24+mc1.21.1`. A release oficial exata é o CurseForge file ID `8774737`, publicada em 31/08/2026 para NeoForge 1.21.1. O projeto upstream usa desenvolvimento contínuo e nem toda build publicada corresponde a uma GitHub Release/tag; por isso o JAR físico + arquivo CurseForge são authority de versão, enquanto a branch `1.21.1` do source é usada para arquitetura e superfícies de configuração.
</callout>
## 1. Identidade e papel
- **Mod:** ModernFix.
- **JAR físico:** `modernfix-neoforge-5.27.24+mc1.21.1.jar`.
- **Mod id:** `modernfix`.
- **Runtime:** `5.27.24+mc1.21.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor/projeto:** embeddedt / ModernFix.
- **CurseForge file ID:** 8774737.
- **Papel:** pacote amplo de correções e otimizações para Minecraft modded, com foco em launch/load time, uso de memória, carregamento de recursos/modelos e outras superfícies de performance/bugfix.
ModernFix não é um único “FPS mod”. Ele aplica um conjunto de patches/mixins independentes, muitos configuráveis, que atuam em subsistemas diferentes do jogo e do ambiente modded.
## 2. Arquitetura por patches/mixins
A modlist física registra `modernfix-modernfix.mixins.json`. O projeto organiza grande parte do comportamento em patches/mixins que podem ser habilitados/desabilitados por configuração.
A consequência operacional é importante: dizer apenas “ModernFix está instalado” não informa quais otimizações estão efetivamente ativas. Diagnóstico de conflito deve identificar o patch/mixin específico antes de recomendar remoção do mod inteiro.
## 3. Configuração
A linha 1.21.1 usa configuração de mixins, historicamente exposta por `config/modernfix-mixins.properties`. Opções individuais controlam patches específicos.
Ao auditar bugs:
- preservar o arquivo real do pack;
- comparar overrides com defaults da versão instalada;
- desabilitar apenas o patch suspeito quando houver evidência;
- não copiar uma configuração de outra versão do Minecraft como se fosse equivalente.
## 4. Performance e memória
O README upstream define ModernFix como mod voltado a melhorar:
- tempo de inicialização;
- tempo de carregamento de mundos;
- uso de memória;
- performance/robustez de vários caminhos internos.
O benefício exato depende do conjunto de mods, hardware, datapacks, quantidade de modelos/recursos e opções habilitadas. Esta ficha não atribui percentuais de ganho sem benchmark da instância.
## 5. Resource/model pipeline
Uma parte relevante da superfície histórica do ModernFix envolve recursos dinâmicos, model baking, caches e otimizações de carregamento. Isso é especialmente sensível neste pack porque há grande quantidade de mods Create, assets, resource packs e extensões de rendering.
Não se deve presumir que qualquer model/render bug é causado pelo ModernFix; porém, quando um problema só aparece com carregamento/reload de recursos, a configuração de patches de recursos/modelos é uma área prioritária de triagem.
## 6. Compatibilidade histórica relevante: Create Teleporters
Existe histórico público de conflito na linha 1.21.1 envolvendo `mixin.perf.dynamic_resources=true` e Create: Teleporters em ModernFix 5.26.1. O workaround relatado era desabilitar aquela opção específica. O issue foi encerrado e **não prova incompatibilidade da build 5.27.24 instalada**.
Este pack contém fisicamente `createteleporters-remastered-2.0.2b-neoforge-1.21.1.jar`. Portanto, o caso histórico justifica um teste dirigido após updates de ModernFix/Create Teleporters, mas não é motivo suficiente para remover nenhum dos dois sem reprodução atual.
## 7. Relação com outros mods de otimização
O pack também usa outros componentes de performance, incluindo FerriteCore e FastSuite. Sobreposição de objetivo não significa redundância integral:
- FerriteCore tem foco forte em estruturas de memória;
- FastSuite atua em caminhos de recipes/caches próprios;
- ModernFix agrega patches de múltiplas categorias.
Remoção por “faz a mesma coisa” só deve ocorrer após profiling, documentação de patches ou conflito reproduzível.
## 8. Client/server
ModernFix contém patches aplicáveis a mais de um side. Portanto:
- testar cliente e dedicated server;
- diferenciar crash client/render/resource de problema server/world/data;
- não assumir que uma opção client-only precisa existir no servidor nem o inverso sem conferir o arquivo/metadata da versão instalada.
## 9. Lifecycle e atualização
Por alterar caminhos internos do Minecraft e de integração modded, ModernFix é sensível a:
- update de NeoForge;
- update de mods que transformam as mesmas classes;
- mudanças de loaders de resources/models;
- datapack/resource reload;
- geração/entrada em mundo;
- shutdown/restart.
Uma atualização que inicia normalmente ainda precisa de regressão em world load e reload; boot verde sozinho não cobre o escopo.
## 10. Changelog 5.27.24
A publicação exata 5.27.24 no CurseForge orienta consultar o GitHub/wiki para mudanças maiores e não fornece uma lista completa de patches no texto curto do arquivo. Esta ficha, portanto, não inventa uma lista de “novidades 5.27.24” a partir de commits de versões anteriores.
## 11. Riscos técnicos
1. **Mixin collision:** patches podem tocar classes também alteradas por outros performance/render/content mods.
2. **Config drift:** copiar `modernfix-mixins.properties` de outra versão pode habilitar/desabilitar opções inexistentes ou com comportamento alterado.
3. **Dynamic resources:** superfície histórica de conflito com Create Teleporters; precisa de reteste na build atual.
4. **Model/resource caching:** bugs podem aparecer apenas após `/reload`, troca de resource pack ou client reconnect.
5. **World loading:** otimização/caching pode mascarar problema até restart completo.
6. **Attribution error:** não culpar ModernFix apenas porque aparece no stacktrace de mixin; verificar transformação real e reprodução A/B.
7. **Continuous upstream:** source HEAD não deve ser tratado como byte-equivalente ao file ID 8774737 sem pin confirmado.
## 12. Matriz de testes
- [ ] Dedicated server inicia com ModernFix 5.27.24 e NeoForge atual do pack.
- [ ] Cliente inicia e entra no mundo sem mixin/config errors.
- [ ] Criar e carregar mundo mede tempo/memória de forma comparável quando necessário.
- [ ] Restart completo do servidor não produz registry/cache corruption.
- [ ] `/reload` conclui sem model/resource exceptions.
- [ ] Troca/reload de resource packs no cliente não produz modelos ausentes/stale.
- [ ] Create Teleporters Remastered carrega, renderiza e executa sua função com `dynamic_resources` conforme config atual.
- [ ] FastSuite/FerriteCore coexistem sem regressão reproduzível.
- [ ] Testar conexão/reconexão de dois clientes em mundo já carregado.
- [ ] Qualquer suspeita de conflito é reproduzida com alteração de **uma opção por vez**, não removendo vários performance mods simultaneamente.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 13. Evidências e limites
- Modlist física: JAR, mod id, runtime e `modernfix-modernfix.mixins.json`.
- CurseForge oficial: file ID 8774737, Release NeoForge 1.21.1, 31/08/2026.
- Source oficial: `embeddedt/ModernFix`, branch 1.21.1 e documentação/wiki do projeto.
- Evidência histórica de compat: issue público sobre `mixin.perf.dynamic_resources` + Create Teleporters em versão anterior da mesma linha.
- **Limite:** não foi localizado um tag/commit público inequivocamente pinado como 5.27.24; source HEAD não é apresentado como binário exato.

## Atualização upstream e gate de manutenção — 11/10/2026

**Upstream NeoForge Minecraft 1.21.1:** `modernfix-neoforge-5.27.26+mc1.21.1.jar`, release em 08/10/2026, após a 5.27.25; **JAR físico histórico** mantém **5.27.24**. O changelog específico 5.27.25–26 não foi isolado para atribuição de uma correção técnica. **Gate:** mixin compatibility e flags, carga massiva de registries/models/resources do pack, config `mixin.perf.dynamic_resources`, Create Teleporters, FastSuite, FerriteCore, resource/datapack reload, memória e classloading client/dedicated server, save/restart, baseline de tempos de boot. Não tratar maior número de versão como benchmark automaticamente. **Fonte:** https://www.curseforge.com/minecraft/mc-mods/modernfix/files/all?version=1.21.1

> **Estado:** atualização documental sem qualquer substituição de JAR, edição da autoridade física ou teste in-game nesta execução.

## Auditoria incremental parcial — 18/11/2026 — 5.27.25 a 5.27.27

**Autoridade física histórica:** #402, `modernfix-neoforge-5.27.24+mc1.21.1.jar`, runtime 5.27.24+mc1.21.1. O [catálogo NeoForge 1.21.1](https://www.curseforge.com/minecraft/mc-mods/modernfix/files/all?gameVersionTypeId=6&page=1&pageSize=20&version=1.21.1) lista exatamente as três releases seguintes: [5.27.25](https://www.curseforge.com/minecraft/mc-mods/modernfix/files/9101699) e [5.27.26](https://www.curseforge.com/minecraft/mc-mods/modernfix/files/9101836), ambas 08/10/2026, e [5.27.27](https://www.curseforge.com/minecraft/mc-mods/modernfix/files/9309399), 24/10/2026. Categorias oficiais: Performance + Server Utility + Utility & QoL. As três páginas remetem à wiki sem changelog granular.

**Evidência técnica do source oficial da branch 1.21.1, no intervalo temporal:**
- 02/10: [fallback do executor para stronghold rings sem cache](https://github.com/embeddedt/ModernFix/commit/5c1318b1c651df3e8611f3abc98b7e1825ac0401) e [não desabilitar o patch de cache de strongholds quando C2ME está presente](https://github.com/embeddedt/ModernFix/commit/aa3fff5971affbce1ca0501960158d41c11349b3).
- 08/10: [construção eager mais eficiente da RTree climática](https://github.com/embeddedt/ModernFix/commit/c3fa11c28623bd77d609e6dd84d36a9098d23818), evitando índice nulo para consumidores do campo; [preservação do manifesto de JARs assinados](https://github.com/embeddedt/ModernFix/commit/cf04b47d10ac5c6a748b177ce0348a3b1e4a9871), evitando falsa detecção de adulteração; merges na branch alvo.
- 11–24/10: [isolar entrada malformada de um resource pack ao carregar blockstates](https://github.com/embeddedt/ModernFix/commit/1df6471ba9ff98c8b031c8978f56ba38baa0c931); [evitar bloqueio prolongado de lookups do cache de structure templates](https://github.com/embeddedt/ModernFix/commit/355755b17342ab576cb182c0abbcd699f628797e); [materializar o mapa de idiomas antes de o reload fechar os packs](https://github.com/embeddedt/ModernFix/commit/246d72e0e6580dc4ec5593990af22ece3b664522), evitando acesso a ZIP fechado.

**Relevância:** worldgen/cache e concorrência na geração de estruturas, compatibilidade com JARs assinados, reload de modelos/blockstates e resource packs/idiomas. Testar junto aos mods que manipulam estruturas/worldgen e recursos dinâmicos, sem pressupor presença de C2ME. Avaliar retenção de memória e tempo de carregamento com baseline controlado.

**Limite:** commits da branch e datas dos artefatos não estabelecem equivalência binária nem atribuição exata entre as duas publicações de 08/10; não certificar cada delta como contido em um JAR específico sem pin/tag/manifesto verificável. Releases identificadas, auditoria granular ainda pendente. Nenhuma alteração física ou teste realizado.

## Fechamento documental por versionamento Git first-parent — ModernFix 5.27.24 → 5.27.27 (21/11/2026)

**Conciliação exata da linhagem NeoForge 1.21.1:** os três arquivos subsequentes ao JAR top-level instalado `modernfix-neoforge-5.27.24+mc1.21.1.jar` são [5.27.25 (CurseForge ID 9101699, 08/10/2026)](https://www.curseforge.com/minecraft/mc-mods/modernfix/files/9101699), [5.27.26 (ID 9101836, 08/10/2026)](https://www.curseforge.com/minecraft/mc-mods/modernfix/files/9101836) e [5.27.27 (ID 9309399, 24/10/2026)](https://www.curseforge.com/minecraft/mc-mods/modernfix/files/9309399), todos **Release, Minecraft 1.21.1, NeoForge**. Não confundir `+mc1.21.1` com arquivos `+mc26.1.2` ou `+mc1.20.1`.

O [gerador de versão oficial `GitVersionSource.kt`](https://github.com/embeddedt/ModernFix/blob/a5882ba3c779e185115ac37e95c81dd8beb7e44b/buildSrc/src/main/kotlin/GitVersionSource.kt) compõe a versão como **`release_line.txt + "." + número de commits na primeira linha de parentes desde a mudança de release_line.txt + "+mc" + minecraft_version`**; `release_line.txt` é **`5.27`** nos quatro commits abaixo, e `gradle.properties` dos merges fixa **`minecraft_version=1.21.1` / `enabled_platforms=neoforge`**. A cadeia de **primeiros pais** de Git foi conferida diretamente:
- baseline **5.27.24**: [`e5ede992`](https://github.com/embeddedt/ModernFix/commit/e5ede9929c359adbe9629e4e59f1c2f83263225c), **31/08/2026 02:32 UTC**, correspondente ao arquivo instalado publicado nessa data;
- **5.27.25**: [merge `0b987b80`](https://github.com/embeddedt/ModernFix/commit/0b987b80a2b1a011d7c07bf5dae2ea3156dd7da2), **08/10 20:00 UTC**, primeiro pai `e5ede992`, publicação .25 pouco depois;
- **5.27.26**: [merge `1957e779`](https://github.com/embeddedt/ModernFix/commit/1957e7798d9b0c51dfacec1cd39a74c58b4dd2b5), **08/10 20:26 UTC**, primeiro pai `0b987b80`, publicação .26 pouco depois;
- **5.27.27**: [merge `a5882ba3`](https://github.com/embeddedt/ModernFix/commit/a5882ba3c779e185115ac37e95c81dd8beb7e44b), **24/10 13:21 UTC**, primeiro pai `1957e779`, publicação .27 no mesmo dia.

Essa cadeia e a regra de versionamento **resolvem a atribuição documental por build** que estava bloqueada na seção anterior; as três [comparações Git oficiais](https://github.com/embeddedt/ModernFix/compare/e5ede9929c359adbe9629e4e59f1c2f83263225c...0b987b80a2b1a011d7c07bf5dae2ea3156dd7da2) são descritas abaixo. A correspondência foi aferida por código/source, first-parent e datas, **não** por decompilação ou SHA binário dos JARs distribuídos.

### 5.27.25 — 08/10/2026 — performance de worldgen, estruturas e C2ME

[Diff oficial da branch `1.21.1`, baseline .24 → merge .25](https://github.com/embeddedt/ModernFix/compare/e5ede9929c359adbe9629e4e59f1c2f83263225c...0b987b80a2b1a011d7c07bf5dae2ea3156dd7da2): **três commits técnicos**:
- [`5c1318b1`](https://github.com/embeddedt/ModernFix/commit/5c1318b1c651df3e8611f3abc98b7e1825ac0401): fallback para executor de tarefas quando a geração de *stronghold rings* não estiver cacheada; evita assumir disponibilidade do cache.
- [`aa3fff59`](https://github.com/embeddedt/ModernFix/commit/aa3fff5971affbce1ca0501960158d41c11349b3): não desliga indevidamente `mixin.perf.cache_strongholds` na presença de **C2ME**. A simples correção de compatibilidade condicional **não prova que C2ME esteja instalado** nesta modlist.
- [`c3fa11c2`](https://github.com/embeddedt/ModernFix/commit/c3fa11c28623bd77d609e6dd84d36a9098d23818): substitui construção lazy da árvore climática **RTree** por builder eager mais eficiente, reduzindo a possibilidade de inicialização tardia inconsistente; efeitos e métricas de geração precisam ser testados com seed fixa.

### 5.27.26 — 08/10/2026 — preservação de assinatura de outros JARs

[Diff oficial entre os merges .25 e .26](https://github.com/embeddedt/ModernFix/compare/0b987b80a2b1a011d7c07bf5dae2ea3156dd7da2...1957e7798d9b0c51dfacec1cd39a74c58b4dd2b5): um único commit de mudança funcional, [`cf04b47d`](https://github.com/embeddedt/ModernFix/commit/cf04b47d10ac5c6a748b177ce0348a3b1e4a9871), em **`ManifestCompactor.java`**, impede compactar indevidamente o manifesto de **JARs assinados**. Isso preserva metadata de assinatura e reduz risco de validação incorreta; não altera mecânicas ou itens do pack.

### 5.27.27 — 24/10/2026 — resources, structures e reload

[Diff oficial entre os merges .26 e .27](https://github.com/embeddedt/ModernFix/compare/1957e7798d9b0c51dfacec1cd39a74c58b4dd2b5...a5882ba3c779e185115ac37e95c81dd8beb7e44b): três commits técnicos e um de documentação:
- [`1df6471b`](https://github.com/embeddedt/ModernFix/commit/1df6471ba9ff98c8b031c8978f56ba38baa0c931) (**11/10**): ao carregar blockstates dinamicamente, isola **somente a cópia malformada** do resource pack, sem inutilizar indiscriminadamente as demais fontes válidas; relevante para overlays e packs com JSON defeituoso.
- [`355755b1`](https://github.com/embeddedt/ModernFix/commit/355755b17342ab576cb182c0abbcd699f628797e) (**19/10**): permite que requisições duplicadas de carregamento de *structure templates* concorram, removendo espera serial indevida. Conferir cache/reload e resultados de worldgen com consumers de estruturas; **não** afirmar ganho de FPS ou estabilidade numérica sem benchmark.
- [`246d72e0`](https://github.com/embeddedt/ModernFix/commit/246d72e0e6580dc4ec5593990af22ece3b664522) (**24/10**): materializa o mapa de traduções dinâmicas antes do fechamento dos packs no **resource reload**, evitando referências a ZIP já fechado. Regressão pertinente ao The Brazilian Localization Project, sobreposição de traduções, `F3+T` e menus modded.
- [`441fe622`](https://github.com/embeddedt/ModernFix/commit/441fe62210157ae62e925eaa2ea95c43652836ad) (**24/10**): documentação de projeto, sem nova mecânica de gameplay.

**Escopo e conclusão:** a [wiki oficial do ModernFix](https://github.com/embeddedt/ModernFix/wiki/Changelog) explica o regime de *continuous deployment* e que releases de patch concentram correções de recursos existentes. A lista de commits em **primeiro-pai da branch 1.21.1**, o gerador determinístico de versão e as três distribuições CurseForge identificam cada intervalo sem misturar os deltas com a branch `1.20`. **Auditoria documental da cadeia 5.27.24 → 5.27.25 → 5.27.26 → 5.27.27: CONCLUÍDA.** A versão **instalada e certificada segue 5.27.24+mc1.21.1**, com o mesmo nome/caminho/JAR; nenhum binário ou config foi alterado.

**Gate separado de upgrade físico:** confirmar SHA/metadata/patch set dos JARs reais; preservar backup de configs e mundo; testar dedicated server e cliente em NeoForge 21.1.248, `mixin.perf.dynamic_resources`, comparação de gerações de estruturas, operações com resource packs e idiomas (especialmente Brazilian Project 1.3.0 físico), `/reload`, `F3+T`, C2ME **somente se estiver instalado**, Create Teleporters, boot/memória e mixin collisions. **Não foi feito QA em runtime.** Após confirmação do GitHub, o **MOD 103** pode ser removido **apenas da lista de auditoria documental**, não da eventual fila de atualização física.

