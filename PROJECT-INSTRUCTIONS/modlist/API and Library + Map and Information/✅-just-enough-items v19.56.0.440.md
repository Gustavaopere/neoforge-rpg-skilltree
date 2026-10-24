# Just Enough Items

## Propriedades do registro

- **Mod:** Just Enough Items
- **Arquivo JAR:** jei-1.21.1-neoforge-19.56.0.440.jar
- **Versão 1.21.1:** 19.56.0.440
- **Categoria:** QoL
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/jei/files/all?version=1.21.1
- **Função:** Infraestrutura central de visualização e indexação de itens/ingredientes, recipes e usos, com busca/bookmarks e API de plugins usada por numerosos addons de informação/compatibilidade do pack.
- **Dependências:** NeoForge 1.21.1; pack físico usa NeoForge 21.1.250. O pack contém múltiplas integrações/plugins JEI, portanto updates da linha Beta devem ser testados em conjunto.
- **Compatibilidade/Riscos:** Build física 19.56.0.440 é Beta oficial. Riscos: JEI plugin API drift, recipe/ingredient reload mismatch, bookmark/search UI state, plugins compilados contra builds anteriores, client/server divergence e atualização Beta isolada. Upstream avançou até 19.57.0.450 com MezzConfig GUI integration, bookmark/focus fixes, tooltip lazy caching, proxy refresh e outras mudanças de UI/config.
- **Sobreposição:** Pode coexistir com viewers alternativos em alguns ambientes, mas este pack possui vários plugins especificamente JEI. Não substituir por EMI/REI sem auditorar todo o dependency/plugin graph e a paridade de recipes/categories.
- **Observações:** Runtime físico 19.56.0.440. A sequência publicada depois é **19.56.0.441 → 19.57.0.444 → .445 → .446 → .447 → .448 → .449 → .450**; não há builds públicas .442/.443 na lista 1.21.1. A .450 é a latest NeoForge 1.21.1 em 29/09/2026.
- **Procedência:** modlist física atual + CurseForge/Modrinth oficiais JEI 19.56.0.441 e 19.57.0.444–450 + source `mezz/JustEnoughItems` branch 1.21.1. A autoridade instalada permanece 19.56.0.440.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — JEI físico permanece 19.56.0.440 / NeoForge 21.1.250. Todas as builds publicadas posteriores até **19.57.0.450** foram enumeradas e os deltas materiais registrados abaixo.
- **Histórico da decisão:** 2026-09-06 — presença do JEI aprovada; Manter. Snapshot da época usava 19.53.0.425 Beta e registrava 19.51.0.418 como stable. 2026-09-10 — modlist física atualizada para 19.53.0.426; release oficial Beta confirmada e pesquisa revalidada contra o source line 19.53.0/NeoForge 21.1.248. Decisão Manter preservada; nenhum downgrade/troca física foi executado.
- **Data da última decisão:** 2026-09-06

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #349: JAR `jei-1.21.1-neoforge-19.56.0.440.jar`, mod id `jei`, runtime `19.56.0.440`, SHA-1 `8f18e13b4cc84d2140642ec2411514b7f5b0883b`.

<callout icon="📚" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `jei-1.21.1-neoforge-19.56.0.440.jar`, mod id `jei`, versão `19.56.0.440`. A distribuição oficial continua no canal **Beta** para NeoForge 1.21.1. O pack usa NeoForge **21.1.250**. **Decisão ****`Manter`**** preservada.**
</callout>
## 1. Papel e authority
Just Enough Items é a infraestrutura central de descoberta de ingredientes, recipes e usos do pack. JEI indexa e apresenta dados e oferece API para plugins; não é owner da recipe de gameplay. O recipe manager/datapack/provider real continua authority sobre ingredientes, outputs, machines e condições.
## 2. Canal da build física
A versão **19.56.0.440** é a build física instalada e uma Beta oficial para NeoForge 1.21.1. O canal Beta permanece um risco operacional: plugins JEI do pack devem ser testados em conjunto.
Em 16/09/2026 foi publicada **19.56.0.441**, também Beta, posterior à instalação atual. Ela não é tratada como runtime do pack: fica apenas registrada como update disponível até a modlist física mudar.
## 3. Source line e NeoForge
O pack físico usa NeoForge **21.1.250** e JEI 19.56.0.440. A antiga pinagem documental da branch 19.53.0/NeoForge 21.1.248 não é promovida para a build atual sem novo source/tag pin exato.
Retificação de 24/10/2026: o CurseForge publicou um changelog acumulado da série 19.56.0 no arquivo `.440` e a issue oficial #4491 vincula a correção de datagen precisamente à `.440`. O detalhamento verificável está ao fim do dossier; o texto histórico anterior fica superado.
## 4. Ingredient list e busca
JEI mantém uma lista pesquisável de ingredientes registrados e interfaces de filtragem. Search/index state é client UI/cache; a existência visual de um item no painel não significa que ele seja craftável, obtível ou permitido pela progressão atual.
## 5. Recipes e usos
A função central é navegar recipe categories e usos de ingredientes. Plugins podem adicionar categories/handlers para máquinas modded. O resultado exibido deve refletir o data state carregado; KubeJS/datapacks e configs podem alterar recipes sem mudar o JAR JEI.
## 6. Bookmarks e UI state
Bookmarks e preferências de interface são convenience state do cliente. Não devem ser usados por quests/progressão como prova de descoberta ou obtenção. Mudança de modlist pode deixar bookmark apontando para item removido; o cliente deve degradar sem quebrar world state.
## 7. Plugin API
Numerosos mods registram integração JEI por API. Registration order, ingredient types, recipe types/categories e GUI handlers são contracts version-sensitive. Um plugin compilado contra API anterior pode falhar mesmo quando o gameplay do mod-base continua funcionando.
## 8. Integrações físicas do pack
O pack contém **JEED 2.3.2** e outras integrações JEI específicas, além de Create e vários content mods com recipe categories. Isso torna JEI infraestrutura operacional, não apenas um overlay opcional. Qualquer substituição por outro viewer exige auditar plugin graph e cobertura das categories antes de remover JEI.
## 9. Relação com KubeJS/datapacks
Recipes podem ser adicionadas/removidas/redefinidas por KubeJS ou datapacks. JEI deve refletir o recipe set final, mas não controla o settlement do craft. Após mudança/reload, stale display precisa ser tratado como cache/UI até verificar o recipe manager authoritative.
## 10. Client / server
JEI tem forte componente client-side de UI/index, mas o projeto é distribuído Client & Server e pode consumir dados sincronizados. O servidor continua authority de recipes e inventários reais. Um recipe visível no cliente não autoriza craft se o servidor não possuir/aceitar a mesma definição.
## 11. Lifecycle e reload
Validar client boot, plugin registration, world join, recipe sync, resource/data reload, KubeJS reload quando aplicável, language/resource-pack change e reconnect. Índices precisam ser reconstruídos sem duplicar categories ou manter entries removidas.
## 12. Beta risk
Por estar em Beta, 19.56.0.440 deve ser tratada como version gate mais estrito. Não atualizar JEI isoladamente sem confirmar os plugins do pack. O loader físico NeoForge 21.1.250 está registrado, mas a ausência de source/tag pin exato para .440 nesta auditoria mantém o comportamento version-sensitive fail-closed.
## 13. Version-specific limit
Retificação de 24/10/2026: a issue #4491 associa diretamente à build `.440` a correção de falha durante datagen; a página exata CurseForge 8886909 também lista um changelog acumulado da série 19.56.0. Consultar seção complementar para separar fix exclusivo de mudanças cumulativas.
A build **19.56.0.441** é numericamente posterior e oficial, porém não está instalada. O catálogo permanece em 19.56.0.440 pela autoridade física.
## 14. Riscos técnicos
- plugin API drift entre builds beta;
- category/recipe registration duplicada;
- stale recipe/ingredient index após reload;
- client/server recipe divergence;
- KubeJS/data change não refletida na UI;
- plugin de mod removido ainda manter reference inválida;
- bookmark/search state apontar para ingredient ausente;
- substituir JEI sem paridade dos addons específicos;
- tratar Beta como stable ou assumir fixes não publicados.
## 15. Matriz de testes obrigatória
- [ ] Cliente + dedicated server iniciam com JEI 19.56.0.440 e NeoForge 21.1.250.
- [ ] Ingredient list/search abre sem crash e encontra amostra vanilla/modded.
- [ ] Recipes e usos de Create/containers modded aparecem nas categories corretas.
- [ ] JEED 2.3.2 registra sem API error.
- [ ] Outros plugins JEI físicos registram sem duplicate/NoSuchMethod errors.
- [ ] KubeJS/datapack recipe change é refletida após lifecycle suportado.
- [ ] Recipe exibida corresponde ao servidor e craft real quando aplicável.
- [ ] Resource/language reload não duplica entries/categories.
- [ ] Bookmark de item removido/alterado degrada sem crash.
- [ ] Reconnect reconstrói dados sem estado stale crítico.
- [ ] Upgrade futuro da Beta é bloqueado até smoke test do plugin graph.
## 16. Evidências e limites
- **Modlist física de 17/09/2026:** `jei-1.21.1-neoforge-19.56.0.440.jar`, mod id `jei`, runtime 19.56.0.440 e NeoForge 21.1.250.
- **Distribuição oficial:** 19.56.0.440 Beta para NeoForge 1.21.1; 19.56.0.441 Beta foi publicada depois e não está instalada.
- **Retificação de 24/10/2026:** a falha de datagen #4491 foi confirmada como corrigida na `.440`. Demais entradas de seu changelog CurseForge são cumulativas da série 19.56.0, e não necessariamente exclusivas dessa build.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.


## 17. Histórico upstream 19.56.0.441 → 19.57.0.450 — não instalado
A autoridade física continua em **JEI 19.56.0.440**. Não existem builds públicas 1.21.1 `.442` ou `.443` na sequência consultada.

### 19.56.0.441 — 16/09/2026
- adiciona damage-bar ingredients ao debug plugin;
- corrige item decorations renderizando **atrás de tooltips** (#4479);
- corrige crash de **client config registration durante datagen** (#4491).

### 19.57.0.444 — 20/09/2026
- atualiza MezzConfig/MezzConfig GUI e adiciona integração opcional com a GUI;
- mostra key mappings, runtime toggles e sorting orders na config screen;
- adiciona controles combinados de grid alignment;
- lê mod icons da loader metadata;
- passa a rastrear plugins que registram ingredient types;
- compartilha controles entre ingredient/bookmark lists;
- atualiza tooling/development dependencies e ForgeGradle 7.

### 19.57.0.445 — 20/09/2026
Mudanças materiais publicadas incluem:
- corrige typing da **search focus key**;
- corrige traduções de bookmark-output config e **legacy config migration**;
- corrige controles JEI escurecidos na recipe screen;
- mantém paths de configuração de Realms estáveis;
- prepara/cacha tooltip slot candidates de modo **lazy conforme ficam visíveis**, reduzindo trabalho de UIs grandes;
- prioriza input no transfer de recipe bookmark (#4490);
- migra assets de UI para o GUI atlas do Minecraft;
- preserva ordem de focused ingredients e corrige recipes focus-linked escondidas;
- adiciona recipes de **fireworks**;
- atualiza recipes após **proxy server switch** (#4478).

### 19.57.0.446 — 21/09/2026
- declara metadata compatível para **MezzConfigGui opcional**;
- atualiza release notifier tooling;
- corrige **drag de bookmarks entre páginas** (#4193/#4493).

### 19.57.0.447 — 22/09/2026
- passa a **sugerir MezzConfig GUI** para configuração in-game.

### 19.57.0.448 e 19.57.0.449 — 24/09/2026
As duas builds são publicadas na linha 1.21.1. O source/release lineage entre elas é predominantemente tooling de publicação: update do Jenkins release notifier para 0.3.0 e depois **0.3.1**. Não projeto isso como mudança de recipe/gameplay. A .449 registra explicitamente o notifier 0.3.1.

### 19.57.0.450 — 29/09/2026
A alteração de source que leva à .450 corrige **abertura da MezzConfig GUI no Fabric sem Mod Menu**. Como a build NeoForge recebe o mesmo número, ela continua sendo o teto distribuído para 1.21.1, mas esse fix específico é platform-scoped e não é tratado como regressão NeoForge comprovada.

### Impacto para o pack
O salto 19.56.0.440→19.57.0.450 é principalmente UI/config/index lifecycle, mas JEI tem grande fan-out de plugins. Os fixes de proxy refresh, focus/bookmarks e lazy tooltip preparation são particularmente relevantes em modpack grande. A integração MezzConfigGui precisa coexistir com MezzConfig 0.5.9+ já documentado sem criar duas authorities de config.

Gate de promoção: client + dedicated server; todos os plugins JEI principais; search focus key; bookmark drag/transfer e várias páginas; legacy config migration; MezzConfigGui presente/ausente; recipe focus order; proxy/reconnect recipe refresh; `/reload`; KubeJS/datapacks; large-tooltip stress; fireworks recipes; GUI/resource reload.

Fontes upstream: CurseForge 19.56.0.441 e file list 19.57.0.444–450; Modrinth release notes 19.57.0.444–449; source `mezz/JustEnoughItems` branch 1.21.1.

## 18. Atualização JEI 19.57.0.451 — 10/10/2026

**Físico:** `jei-1.21.1-neoforge-19.56.0.440.jar` / 19.56.0.440 (Beta). O dossiê já audita **todas as releases 19.56.0.441 → 19.57.0.450**, incluindo mudanças importantes de MezzConfig GUI, bookmarks/focus, cache lazy tooltip, recipe transfer e refresh ao trocar de proxy server. O CurseForge publica **19.57.0.451 for NeoForge 1.21.1** desde **05/10/2026**, última build Beta da linha aplicável em 10/10.

**Delta 19.57.0.451:** o índice oficial confirma a release e seu target NeoForge 1.21.1, mas **não foi identificado um changelog individual suficientemente verificável** nesta auditoria. Não atribuir a ela conserto de recipes/plugins nem mudança de API de mod sem fonte vinculada ao build exato.

**Cadeia de promoção:** 19.56.0.440 → .441 → 19.57.0.444/.445/.446/.447/.448/.449/.450 → **.451**, preservando no corpo anterior todos os deltas publicados. O caráter Beta e o fan-out de plugins/consumers exigem atenção a MezzConfig e MezzConfigGUI (componentes separados com próprios versions), Sophisticated JEI Index, ALI, JEED, KubeJS/loot recipes, custom ingredient types e alterações de Gui atlas.

**Gate antes de promover:** startup cliente/servidor, enumeração de plugins JEI com API/ABI matching, recipe and uses display, dynamic recipe reload, tooltip lazy cache com muitas categories, bookmarked recipes/recipe transfer from backpacks, focus/search, config GUI, proxy reconnect, registro de ingredients de terceiros, resource pack pt_BR e validação de missing ingredients. Não se afirmar alteração funcional específica da 451 até release note/source pinado.

**Fonte:** https://www.curseforge.com/minecraft/mc-mods/jei/files/all?version=1.21.1

**Estado:** 19.57.0.451 disponível, 19.56.0.440 permanece a última versão instalada comprovada.

## Release e compatibilidade — reauditoria 13/10/2026

**Atualização upstream NeoForge 1.21.1:** **19.64.0.453 Beta**, publicada em **12/10/2026**, sucedendo 19.57.0.451 de 05/10 e o JAR físico histórico `justenoughitems-neoforge-1.21.1-19.56.0.440.jar` (19.56.0.440). Fonte oficial: https://www.curseforge.com/minecraft/mc-mods/jei/files/all?page=1&pageSize=20&version=1.21.1

A publicação é um salto de linha em uma API central de receitas. Changelog técnico granular da 19.64.0.453 não foi isolado: **não** atribuir novas categorias/plugins/handlers sem evidência. **Gate:** receitas Create/automation, magia/Ars/Iron's, Sophisticated JEI Index, Polymorph+, KubeJS e datapacks, ingredient indexing/search, hidden recipes, tooltips, client-only UI, F3+T e custo de CPU/memória em centenas de mods. Build beta não deve ser promovida sem reconciliação dos addons. **Estado instalado e filename `.md`:** manter última evidência física, prefixo ✅ e versão 19.56.0.440 até novo inventário JAR/metadata/SHA; nenhum teste in-game executado nesta reauditoria.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** Just Enough Items (JEI)
- **Último candidato 1.21.1 / NeoForge localizado:** **19.64.0.455 (Release estável, 16/10); 19.69.0.457 (Beta, 20/10)**
- **Estado:** **19.64.0.455 CANDIDATO PENDENTE; 19.69.0.457 BLOQUEADO**
- **Gate de promoção:** Coordenar com Polymorph+ 1.4.1 e addons JEI; validar recipe transfer/indexing/plugins/GUI. Não usar beta como baseline.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/jei/files/all?version=1.21.1

## Auditoria cumulativa #49 — JEI 19.56.0.440 → 19.69.0.457 (22/10/2026)

**Versão física:** Just Enough Items (JEI) NeoForge 1.21.1 19.56.0.440, não atualizar na coluna física. **Release não Beta posterior:** 19.64.0.455 Release NeoForge 1.21.1 (16/10). **Última Beta:** 19.69.0.457 (20/10), antecedida por 19.69.0.456 (19/10). Os filenames e changelogs de Forge/Fabric não são prova de ABI NeoForge.

**Releases pós-instalação já documentadas neste dossier:** 19.56.0.441; 19.57.0.444, .445, .446, .447, .448, .449, .450; 19.57.0.451 (05/10); 19.64.0.453 Beta (12/10), 19.64.0.454 Beta (14/10), 19.64.0.455 Release (16/10), 19.69.0.456 Beta (19/10) e 19.69.0.457 Beta (20/10). Algumas alterações 19.57 foram auditadas nos subitens 17–18; **não foi obtido changelog técnico individual verificável dos cinco builds 19.64/19.69** nem prova de enumeração exaustiva das builds intermediárias numéricas, especialmente a transição 19.57→19.64.

**Observação de empacotamento:** arquivo NeoForge 19.64.0.455 cerca de 2,3 MB, Beta 19.69.0.457 cerca de 7,0 MB; diferença de tamanho **não comprova** inclusão/exclusão de API ou libraries específicas sem comparar JARs.

**BLOQUEADO para 'tudo detalhado' e exclusão da lista:** faltam changelogs individuais de 19.64.0.453/.454/.455 e 19.69.0.456/.457 vinculados à linha NeoForge 1.21.1. Não atribuir melhorias específicas de recipe UI, indexing, security ou performance por mera diferença de versões. **Gates futuros:** Polymorph+, Lychee, Sophisticated Backpacks/Storage, Ars/Iron's, Create, ACI/ALI, recipe/bookmarks/transfer, 500+ mods, item components, KubeJS/datapacks, plugin API, resource reload e client/dedicated compatibility.

Catálogo oficial que confirma os artefatos e canais: https://www.curseforge.com/minecraft/mc-mods/jei/files/all?page=1&pageSize=20&version=1.21.1 .

## Auditoria ampliada 19.64.0.453 → 19.69.0.457 — 23/10/2026

**Base física:** `jei-1.21.1-neoforge-19.56.0.440.jar` / `19.56.0.440`; não houve troca de JAR. Este complemento corrige a antiga afirmação de ausência total de changelogs específicos da série 19.64: foi localizado um changelog técnico substancial do artefato **NeoForge 1.21.1 19.64.0.453** no Modrinth oficial do autor, além da confirmação das builds posteriores no CurseForge/Maven.

### 19.64.0.453 — Beta NeoForge 1.21.1 (12/10)
Changelog do arquivo publicado: https://modrinth.com/project/u6dRKJwZ/version/OZIoQboh .
- **Receitas e APIs:** separa identidades dos dados-fonte de receitas das identidades da interface JEI; expõe bookmarks de receitas em `IBookmarkManager`, índices de slots de crafting e links de foco entre coleções; transferência de receitas com alternativas de input e novos shortcuts no recipe GUI.
- **Busca/indexação:** normalização de busca conforme locale de Minecraft; pesquisa com completamento, sugestões configuráveis e realce de sintaxe; reconstrução somente de índices marcados como sujos, reduzindo trabalho após mudanças localizadas.
- **UI/UX:** redimensionamento por drag em GUIs JEI, receitas em colunas adaptáveis, rolagem lazy para categorias grandes, scrollbars configuráveis/compartilhadas, scroll de tooltip fixada, grids menores quando com pouco conteúdo e ocultação de grids vazios sem desabilitar busca. Corrigidos scroll com Shift, tratamento de inputs capturados e tooltips sobre os grids.
- **Configuração/render:** `MezzConfig` também para servidor, migrações de configuração, opções aninhadas, novos estilos de borda/fundo, composição visual com efeitos de inventário e compatibilidade com modificadores AMECS. Correções especificamente descritas para Fabric não foram automaticamente atribuídas ao NeoForge.
- **Compatibilidade e risco:** mudanças de API de bookmarks/crafting/focus e de identidade de receitas devem ser confrontadas com addons JEI do pack; invalidation incremental de índices, layouts responsivos e settings migrados precisam de backup e testes de usuário.

### 19.64.0.454 — Beta NeoForge 1.21.1 (14/10)
O artefato publicado está confirmado no catálogo oficial e no repositório Maven do autor; **não foi identificado um changelog exclusivo e inequivocamente associado** à build. Não atribuir detalhes da 19.64.0.453 ou 19.64.0.455 a ela. Fontes: https://www.curseforge.com/minecraft/mc-mods/jei/files/all?gameVersionTypeId=6&version=1.21 ; https://maven.blamejared.com/mezz/jei/jei-1.21.1-common-api/ .

### 19.64.0.455 — Release NeoForge 1.21.1 (16/10)
**Última Release estável da linha 1.21.1 identificada**, separada das versões Beta 19.69.x. Artefato Maven publicado com fontes das bibliotecas 1.21.1. O delta exclusivo **.454→.455** não foi isolado em changelog público recuperado; a publicação de uma Release não comprova que bugs específicos tenham sido corrigidos nessa build. Fontes: https://www.curseforge.com/minecraft/mc-mods/jei/files/all?gameVersionTypeId=6&version=1.21 ; https://maven.blamejared.com/mezz/jei/jei-1.21.1-common/19.64.0.455/ .

### 19.69.0.456 — Beta NeoForge 1.21.1 (19/10)
**Nova linha Beta (19.69)**, aproximadamente 7 MB versus ~2,3 MB da Release 19.64.0.455. A diferença de tamanho **não demonstra** quais bibliotecas/classes estão embarcadas; examinar o artefato e manifest antes de atribuir eventuais mudanças de empacotamento. Artefato e Maven confirmados; **delta exclusivo ainda sem changelog individual identificado**. Fontes: https://www.curseforge.com/minecraft/mc-mods/jei/files/all?gameVersionTypeId=6&version=1.21 ; https://maven.blamejared.com/mezz/jei/jei-1.21.1-common/19.69.0.456/ .

### 19.69.0.457 — Beta NeoForge 1.21.1 (20/10)
**Mais recente Beta verificada**; não confundir com a última Release 19.64.0.455. A publicação foi confirmada por CurseForge e Maven mas, sem changelog/tag inequívoca, o delta **.456→.457** permanece indeterminado. Fontes: https://www.curseforge.com/minecraft/mc-mods/jei/files/all?gameVersionTypeId=6&version=1.21 ; https://maven.blamejared.com/mezz/jei/jei-1.21.1-common/19.69.0.457/ .

**Intervalo integral publicado:** `19.56.0.440 → .441 → 19.57.0.444/.445/.446/.447/.448/.449/.450/.451 → 19.64.0.453/.454/.455 → 19.69.0.456/.457`; as builds numéricas não presentes no catálogo (p.ex. `19.57.0.442`, `19.64.0.452`) não devem ser inventadas. Revisão técnica da 19.64.0.453 agora detalhada. **Ainda bloqueado para certificação release-by-release** pela falta dos quatro changelogs exclusivos .454, .455, .456 e .457. O pack depende de addons JEI e integrações (Create, KubeJS, Lychee, Polymorph+, Sophisticated), então não migrar cegamente para Beta. Nenhum QA de runtime realizado; **manter item 49 na lista**.

## Correções adicionais atribuídas por release: 19.64.0.454 e 19.69.0.456 — 23/10/2026

**Correção à auditoria ampliada anterior:** há agora **evidência primária específica** para 2 das 4 builds anteriormente não atribuídas. A autoridade física continua `jei-1.21.1-neoforge-19.56.0.440.jar`; nenhuma promoção de JAR foi feita.

- **19.64.0.454 — 14/10/2026, CurseForge File ID 9173201, Beta NeoForge 1.21.1.** O **changelog da página deste artefato exato** adiciona a correção **issue #4549: navegação com mouse sobre ingredientes das receitas** (recipe ingredient mouse navigation). A lista é cumulativa, intitulada "Current release 19.64.0"; alterações enumeradas como base podem ter vindo da 19.64.0.453 e não devem ser atribuídas novamente à .454. Fonte: https://www.curseforge.com/minecraft/mc-mods/jei/files/9173201 . Consequência: testar recipe ingredient click/hover, itens de transferência e overlays de input antes de subir o mod.
- **19.69.0.456 — 19/10/2026, Beta NeoForge 1.21.1.** O bot colaborador **mezz-bot**, em comentário publicado em 19/10 na **issue oficial #4554**, vincula a correção à versão exata **19.69.0.456** para **Minecraft 1.21.1 NeoForge/Fabric/Forge**. A alteração trata de **excluir IDs sintéticos dos IDs de receitas fonte retornados por `IRecipeCategory#getSourceRecipeIds`**, evitando conflitos de identificação de receitas artificiais/de exibição. Commits associados à issue: `3cbb5e6` e `8697aae`. Fonte primária: https://github.com/mezz/JustEnoughItems/issues/4554 . Consequência: validar plugins/addons que criam recipe displays sintéticos, bookmarks, categorias e transferência.
- **19.64.0.455 (16/10, Release)** e **19.69.0.457 (20/10, Beta)**: publicação verificada na listagem oficial de arquivos NeoForge 1.21.1, mas **sem delta individual exclusivo recuperado** nesta rodada. Um marcador Release/Beta ou diferença de tamanho entre .455 e .457 não prova correção específica nem causa de empacotamento.

**Estado agora:** baseline `19.56.0.440`; cadeia de releases publicada já catalogada integralmente; changelogs próprios confirmados para `.453`, `.454` e `.456`; **lacunas restantes exclusivamente `.455` e `.457`**. **Não considerar a auditoria estrita concluída** até mapear as diferenças dessas duas builds por changelog, source/tag ou diff do artefato. Regressões: recipe source/display identity, `IBookmarkManager`, category plugins, synthetic recipe IDs, mouse navigation, addons JEI/Polymorph/RecipeTransfer/Create/Sophisticated, build loader e migrations. Fonte catálogo: https://www.curseforge.com/minecraft/mc-mods/jei/files/all?version=1.21.1 .

## Complemento de changelog exato NeoForge — 24/10/2026: JEI 19.69.0.456

**Preservação física:** `jei-1.21.1-neoforge-19.56.0.440.jar`, runtime 19.56.0.440, mod ID `jei`. Este complemento **não** aprova upgrade e **não** supõe diferenças de bytecode entre loaders. Identidade do projeto CurseForge oficial **Just Enough Items (JEI)**; categorias temáticas **API and Library + Map and Information**, pasta preservada.

### Fonte primária própria da build NeoForge (não de Forge/Fabric)

**Modrinth oficial do autor, versão `FLLyiWf8`:** https://modrinth.com/mod/jei/version/FLLyiWf8 — título **19.69.0.456 for NeoForge 1.21.1**, canal **Beta**, mostra uma lista de commits/mudanças da própria publicação. Isso permite ampliar a descrição anteriormente restrita à issue **#4554** para a **.456**, sem transportar mudanças de outras plataformas como fatos já testados. O autor também mantém o source `mezz/JustEnoughItems`, branch `1.21.1`; os commits descritos podem ser encontrados na janela **18–19/10/2026**.

| Superfície | Mudanças listadas para a versão oficial **19.69.0.456 NeoForge 1.21.1** |
|---|---|
| **Busca/indexação** | **Lucene** passa a sustentar a busca de ingredientes; adiciona API de **refresh da busca em runtime**; **oculta ingredientes quando não está pesquisando**; desenha sugestões de autocompletar acima dos itens na grade. O último ajuste foi comitado em **19/10**. |
| **Identidade de receitas e integração com plugins** | **Source IDs sintéticos** filtrados e com prefixo barra; decoradores podem sobrescrever IDs de origem das receitas; ignora ingredientes apenas de renderização na indexação; introduz extensões de categorias vanilla de cocção e widgets para botões de receitas. |
| **UX / controles** | Corrige atalhos de teclado em **slots sintéticos de containers** (#4561), atalhos do modo cheat no botão de configuração (#4548), persistência de visibilidade do overlay entre inicializações (#2296), layout de bookmarks quando sidebars ficam nos cantos (#4556) e introduz atalhos de cheat no inventário. |
| **Plataformas / regressões** | A publicação também lista correção de **carregamento da API em ambiente de desenvolvimento Forge**, hotkey modifiers no Fabric e marcação de Minecraft 26.1.2 como não suportado. **Essas mudanças são restritas às respectivas plataformas**, e sua presença na lista não significa que sejam mudanças de runtime NeoForge. |

**Conferência de source:** commits `c35dd12f6cb9b89ed803c621c879d382c67bca80` (autocomplete por cima dos itens), `a4a5321b3a30e0f1d88af1edf4a762685e6d0f36` (search-refresh API), `fd1aa7cf44104782e273bacee1223f38f09001c0` (Lucene), `3cbb5e65714dce00d3ac0332845d51714a6dee77` e `803883f6e5596dc01ff2450e996ee507014fcd53` (IDs sintéticos). A publicação **.456** atribui a lista inteira ao seu changelog; **não supor que cada entrada tenha surgido exatamente na diferença binária .455→.456 sem comparar JARs**, pois alguns changelogs de projeto podem listar mudanças acumuladas.

### Lacunas remanescentes e testes

- **19.64.0.455, Release NeoForge 1.21.1 de 16/10:** código `mezz/JustEnoughItems` da janela 15/10 mostra remoção de CI Travis e teste de ligação de interfaces empacotadas de **Forge**, mas **a página Modrinth localizada para `19.64.0.455` refere-se ao JAR Forge**, não ao artefato NeoForge. Não atribuir o fix exclusivo a NeoForge por versão textual igual; é necessário release note do NeoForge ou diff do JAR exato.
- **19.69.0.457, Beta NeoForge 1.21.1 de 20/10:** há commit source `5f8f1418804ba217737153d1af7854529d5407c1` em 20/10 com teste de **compatibilidade de bytecode dos JARs publicados**, mas não foi recuperada evidência de que ele componha a build **.457** nem de um changelog exclusivo do artefato. Não declarar fix funcional/runtime deste número a partir do commit.
- **QA ainda não realizado:** Lucene/search refresh com 500+ mods, regex/locale de idioma PT-BR, plugins de receitas sintéticas e APIs de IDs, bookmarks, cheat shortcuts, recipe widgets, incremental index rebuild, KubeJS/datapacks, Polymorph, Create, Sophisticated/JEI Index, servidor dedicado e resource reload; a estabilidade com addons não decorre de notas de release.

**Resultado:** a 19.69.0.456 está **substancialmente mais detalhada por fonte oficial NeoForge** do que na documentação anterior. O inventário de releases posteriores segue completo, mas **.455 e .457 permanecem sem vínculo suficiente do delta exclusivo ao JAR NeoForge exato**. **#49 permanece PENDENTE**, JAR físico e nome certificado do dossier preservados.

## Evidência externa de integração — FTB StoneBlock 4 1.24.1 (24/10/2026)

**Fato observado em uma distribuição de terceiros (NÃO no pack RPG):** o changelog público da [release FTB StoneBlock 4 1.24.1, publicada em 21/10/2026](https://www.curseforge.com/minecraft/modpacks/ftb-stoneblock-4/files/9282328), registra `Fixed: Bad JEI install` e, na lista de alterações de JARs, o **rollback** de `jei-1.21.1-neoforge-19.69.0.457.jar` para `jei-1.21.1-neoforge-19.69.0.456.jar`. É uma evidência concreta de que um *modpack* de Minecraft 1.21.1 / NeoForge abandonou temporariamente a build `.457` ao corrigir **sua instalação** do JEI. **Não demonstra** que a `.457` tenha defeito intrínseco ou que a `.456` seja universalmente mais estável: a descrição não fornece stacktrace, reprodução, configuração, motivo técnico, incompatibilidade de API ou delta binário. A página ainda alerta que sua listagem de updates é gerada automaticamente e pode conter imprecisões.

**Classificação de distribuição, conferida na [listagem oficial CurseForge do JEI 1.21.1](https://www.curseforge.com/minecraft/mc-mods/jei/files/all?page=1&pageSize=20&version=1.21.1):** `19.64.0.455` é marcada **Release**, enquanto `19.69.0.456` e `19.69.0.457` são **Beta**. A página oficial de versões [Modrinth](https://modrinth.com/mod/jei/versions) usa **Beta** inclusive para alguns artefatos com o mesmo número `19.64.0.455`; essa discrepância de *canal de publicação* exige evitar descrever `.455` como estável em **todas** as plataformas/distribuições. A versão do JAR, loader e artefato exato prevalecem sobre rótulo genérico de release.

**Impacto para este perfil:** a decisão prudente é manter o JAR físico `jei-1.21.1-neoforge-19.56.0.440.jar` enquanto não houver homologação da atualização. Antes de candidatar `.457`, coletar o changelog/commit ou diff **exclusivo `.456 → .457`**, validar disponibilidade e versão dos plugins do JEI, integridade/download do JAR e execuções de startup/reload, receitas sintéticas, bookmarks, busca Lucene, transferências e integração com Create/KubeJS/Sophisticated. Caso seja testada a `.456`, o rollback de terceiros é apenas **sinal de risco de integração**, nunca certificação de compatibilidade neste pack. **Pendência `.455`/`.457` permanece aberta; não retirar #49 da lista.** Nenhuma instalação, execução binária ou QA foi efetuada neste complemento.

## Gate de dependências do JEI upstream — 24/10/2026

**Nova evidência primária verificável:** a [página oficial de dependências do projeto Just Enough Items (JEI), CurseForge ID 238222](https://www.curseforge.com/minecraft/mc-mods/jei/relations/dependencies) identifica **MezzConfig como dependência obrigatória** e **MezzConfigGUI como dependência opcional**. Esta é uma distinção importante para atualizar o JEI: **interface de configurações opcional não substitui a biblioteca exigida**, mas a relação por projeto não fixa sozinha a versão mínima exata para cada JAR/canal.

**Inventário histórico top-level do pack (modlist.md, 587 entradas):**
- **#394 — MezzConfig:** `mezz_config-1.21.1-neoforge-0.5.9.jar`, versão física registrada `0.5.9`.
- **MezzConfigGUI:** não foi identificada como entrada top-level nominada no mesmo inventário físico histórico. Isso **não comprova ausência em um dump futuro** ou eventual dependência embarcada; apenas não há entrada própria no snapshot de autoridade.
- **#349 — JEI:** `jei-1.21.1-neoforge-19.56.0.440.jar`, versão física preservada `19.56.0.440`.

**Implicação para as releases 19.64.0.455 e 19.69.0.457:** há evidência externa de modpack que, ao atualizar para **JEI 19.64.0.455**, adicionou **MezzConfig 0.6.8**, declarando que JEI passou a necessitar dessa biblioteca. Trata-se do changelog público de [OneBlock to Rule Them All v0.13](https://www.curseforge.com/minecraft/modpacks/one-block-to-rule-them-all/files/9225265); **não prova**, sem a metadata exata, que `0.5.9` necessariamente falha com a build específica de NeoForge no nosso perfil. A página oficial do CurseForge fornece a **relação de dependência**, enquanto as faixas mínimas/máximas e o caráter opcional/obrigatório por artefato ainda devem ser confrontados com **`META-INF/neoforge.mods.toml` (ou metadata equivalente) e os arquivos do MezzConfig exato**.

**Matriz de validação antes da promoção:**
1. Inspecionar, separadamente, os artefatos oficiais **JEI `19.64.0.455` Release NeoForge 1.21.1**, **`19.69.0.456` Beta** e **`19.69.0.457` Beta**, extraindo `modId`, versão, dependências, `versionRange` e declaração de side.
2. Confirmar qual versão de **MezzConfig NeoForge 1.21.1** satisfaz cada `versionRange`; não atualizar MezzConfig isoladamente por associação numérica.
3. Avaliar **MezzConfigGUI** opcional somente para a GUI de configurações — seus requisitos próprios também precisam ser verificados se instalado; não tratar ausência do plugin de GUI como defeito comprovado no JEI.
4. Testar boot dedicado/cliente, recipe displays, pesquisa Lucene, bookmarks, interfaces de configuração e addons existentes depois de atualizar em staging.

**Resultado:** a pesquisa encontrou um **gate de dependências anteriormente não explicitado**, mas não preencheu os deltas exclusivos **`.455`** e **`.457`** nem autorizou atualização de JARs. **#49 permanece pendente.**

## Retificação documental 19.56.0.440 — changelog oficial, datagen e Aeronautics (24/10/2026)

**Autoridade de artefato:** [CurseForge File ID 8886909](https://www.curseforge.com/minecraft/mc-mods/jei/files/8886909), denominado `jei-1.21.1-neoforge-19.56.0.440.jar`, **Beta**, publicado em 15/09/2026 para Minecraft 1.21.1 / NeoForge; é exatamente o JAR instalado na posição física #349 da modlist histórica. A página do próprio arquivo contém a lista intitulada **Current release 19.56.0**. Por ser changelog **cumulativo da família 19.56.0**, não atribuir cada item exclusivamente ao salto `.439→.440`.

### Fix confirmado na própria build .440

- **Issue #4491 — falha de datagen durante registro da configuração cliente:** a versão `.439` podia tentar acessar `Minecraft.getInstance().getUser()` quando o singleton de cliente era nulo no ambiente de datagen, desencadeando `NullPointerException` e erro de carregamento do JEI. A página do JAR `.440` lista explicitamente **Fix #4491**; o comentário do bot oficial também vincula a correção à release `19.56.0.440` para Minecraft 1.21.1 e NeoForge, arquivo CurseForge 8886909 e [Modrinth NeoForge `9myHusbW`](https://modrinth.com/mod/u6dRKJwZ/version/9myHusbW). Fonte: https://github.com/mezz/JustEnoughItems/issues/4491 . A confirmação de publicação não significa que datagen do RPG tenha sido executado nesta auditoria.

### Superfícies presentes no changelog acumulado 19.56.0 publicado com .440

| Área | Itens declarados pelo autor na ficha 8886909 | Cuidados de integração |
|---|---|---|
| Receitas / ingredientes | Agrupamento e ajuste do layout das variantes de desencantamento em grindstone; ingredientes representativos por tipo; filtro de buscas por categoria oculta; entradas removidas de configurações de ordenação também ocultas | Validar receita grindstone, abas de categorias, alternância de configurações e plugins que registram ingredientes próprios |
| MezzConfig / configuração | Atualizações para MezzConfig `0.5.4` e `0.5.6` ao longo da série; changelog menciona **embed do artefato completo de loader do MezzConfig**, sorting config, telas abertas por mod ID, key mappings selecionados, tradução EN de config e valores server-side nativos | **Não concluir** desse texto que o JAR top-level MezzConfig `0.5.9` registrado em #394 seja redundante ou incompatível; inspecionar `META-INF/jarjar/`, dependências declaradas, loader e metadata do artefato real antes de alteração |
| UI / interação | Centralização do modo cheat; distinção de contextos de conflito de atalhos, nomes localizados de teclas modificadoras, refresh do estado de botões de overlay por tick e overlays ajustados à tela de configuração | Verificar cheat shortcuts, sobreposição de widgets, teclado PT-BR e persistência de visibilidade |
| Reload e ciclo de vida | Aliases de busca passam a ser aplicáveis sem reinicialização completa; mudanças de ordenação aplicáveis em runtime, reinício de JEI para lote de configurações; encerramento de listeners antigos | Testar mudança de aliases/sorting em mundo aberto, alternância de perfil, reload e leak de listeners |
| Internals / distribuição | Separação de GUI entre módulos comuns e GUI; serializer legado de ingredientes movido ao módulo GUI; metadata NeoForge passa a declarar logo; ajustes de quebra de linha splitLines | Não atribuir nova API pública não declarada; inspecionar consumidores de classes internas e startup dedicado |

**Integração Aeronautics verificada no mesmo upstream:** em https://github.com/mezz/JustEnoughItems/issues/4459 , o mantenedor `mezz` atribuiu um crash ao abrir inventário a um mixin obsoleto de **Create Simulated/Aeronautics 1.3.1**; informou que o problema específico foi corrigido na versão **1.3.2**. O snapshot físico deste RPG contém **#117 `create-aeronautics-bundled-1.21.1-1.3.2.jar`**, então o pack registra nominalmente a linha corrigida, mas **isso não certifica funcionamento com JEI 19.64.0.455 ou 19.69.0.457**. O addon **Create Aeronautics: Automated Logistics** em #128 é projeto diferente e não foi apontado como causa nessa issue.

**Conclusão restrita:** o dossier agora dispõe de changelog acumulado 19.56.0 e de um fix exato da `.440`, solucionando sua lacuna documental anterior. Continuam pendentes a comparação por artefato dos deltas NeoForge exclusivos de **19.64.0.455** e **19.69.0.457**, a matriz MezzConfig por metadata e os testes do pack. A versão física, o basename `✅-just-enough-items v19.56.0.440.md`, o diretório e os JARs permanecem inalterados. **Não apagar #49.**

## Fichas diretas de JEI .455 e .457 NeoForge 1.21.1 — 24/10/2026

**Autoridade física intocada:** posição real #349, JEI 19.56.0.440. As fichas verificadas são do mesmo CurseForge oficial ID 238222, para NeoForge e Minecraft 1.21.1, não de outro loader.

| Build correta | Fonte primária | Evidência |
|---|---|---|
| 19.64.0.455 Release, 16/10/2026 | [CurseForge ID 9205121](https://www.curseforge.com/minecraft/mc-mods/jei/files/9205121) | Artefato jei-1.21.1-neoforge-19.64.0.455.jar, aproximadamente 2,3 MB; notas com cabeçalho **Current release 19.64.0**. |
| 19.69.0.457 Beta, 20/10/2026 | [CurseForge ID 9267535](https://www.curseforge.com/minecraft/mc-mods/jei/files/9267535) | Artefato jei-1.21.1-neoforge-19.69.0.457.jar, aproximadamente 7,0 MB; notas com cabeçalho **Current release 19.69.0**. |

### Declarações da ficha 19.64.0.455 — changelog cumulativo 19.64.0

- Separação entre IDs da receita-fonte e identidades de display JEI; possibilidade de substituir slots de scroll-grid e configurar scrollbar; bookmarks de receitas no contrato IBookmarkManager (#4522).
- Correção da navegação por mouse em ingredientes (#4549), ajuste de scroll de categorias com shift nos dois eixos da roda e roteamento de inputs GUI por áreas capturadas.
- Configuração de **servidor via MezzConfig**, grades de ingredientes e bookmarks ajustadas ao conteúdo, grades vazias ocultadas sem desativar busca, tooltips interativos e pinned tooltips roláveis.
- Drag resize de GUIs, colunas configuráveis, scroll lazy de listas de receitas grandes, normalização de pesquisa pela locale do Minecraft e organização da interface de configuração em categorias aninhadas.
- Manutenção de build e linkage de Forge documentada pelo autor, sem pressupor efeitos no runtime NeoForge.

**Limite:** a lista é publicada na página do arquivo exato .455, mas o cabeçalho cobre a família 19.64.0. Ela não revela por si qual alteração foi introduzida exclusivamente entre as builds .454 e .455.

### Declarações da ficha 19.69.0.457 — changelog cumulativo 19.69.0

- Busca de ingredientes por **Lucene**, API de refresh dos resultados durante execução, autocompletar acima da lista de ingredientes, ocultação de ingredientes fora de pesquisa e exclusão de objetos somente de renderização da indexação de receitas.
- IDs sintéticos com separador de barra, filtragem de IDs sintéticos, override de source ID por decorator e extensões de categorias culinárias vanilla.
- Atalhos de cheat mode (#4548), atalhos em slots sintéticos (#4561), persistência da visibilidade do overlay entre inicializações (#2296) e correção da posição de bookmarks com sidebars em cantos (#4556).
- Checagem de bytecode compatível com Java em JARs publicados; ajustes de API Forge e modificadores Fabric registrados como específicos de outro loader, sem afirmar efeito NeoForge.
- Muitas notas da família 19.64.0 são repetidas no texto 19.69.0; repetições **não** são mudanças exclusivas da .457.

**Risco de distribuição:** o tamanho publicado passa de aproximadamente 2,3 MB (.455) a 7,0 MB (.457). Isto exige inspeção de classes, metadata, versionRange e bibliotecas embarcadas, sem presumir a causa exata da diferença nem contar jars internos como mods top-level.

**Gate fail-closed:** ambas as páginas exatas e seus changelogs oficiais estão agora identificados. Ainda falta demonstrar os deltas exclusivos entre .454→.455 e .456→.457 por source/tag de publicação ou comparação de binários, validar versionRange MezzConfig/MezzConfigGUI e executar QA dos addons. **Não apagar #49.** Não houve atualização do mod físico, configuração, pasta ou nome documental.

## Diff das listas oficiais .455/.457 e gate MezzConfig pinado no source — 24/10/2026

**Evidência primária comparada:** [CurseForge JEI NeoForge 19.64.0.455, ID 9205121](https://www.curseforge.com/minecraft/mc-mods/jei/files/9205121), e [NeoForge 19.69.0.457, ID 9267535](https://www.curseforge.com/minecraft/mc-mods/jei/files/9267535). Ambas expõem *Current release* da sua família e listas de **30 commits GitHub por página**. Foram extraídos os SHAs dessas duas listas sem atribuir a um artefato uma alteração que não conste de sua própria ficha.

### Verificação da diferença DOCUMENTAL entre as duas listas oficiais

- **30 commits visíveis na ficha .455; 30 na ficha .457; 10 SHAs em comum, 20 na ficha .457 que não constam entre os 30 visíveis da .455.** O inverso também vale: 20 commits antigos da lista .455 deixam de constar da lista .457. **Isso é uma comparação entre janelas das release notes, não um diff completo de JARs nem evidência de que os 20 commits sejam mudanças exclusivas de .456→.457.**
- **Busca e APIs (5):** [Lucene — fd1aa7c](https://github.com/mezz/JustEnoughItems/commit/fd1aa7cf44104782e273bacee1223f38f09001c0), [refresh de busca em runtime — a4a5321](https://github.com/mezz/JustEnoughItems/commit/a4a5321b3a30e0f1d88af1edf4a762685e6d0f36), [desenhar autocompletar sobre ingredientes — c35dd12](https://github.com/mezz/JustEnoughItems/commit/c35dd12f6cb9b89ed803c621c879d382c67bca80), [ocultar ingredientes sem busca — 7dd2b4f](https://github.com/mezz/JustEnoughItems/commit/7dd2b4faa3d69d09e077b51a38294cadd114c02e), [ignorar ingredientes render-only na indexação — 1dae9a0](https://github.com/mezz/JustEnoughItems/commit/1dae9a0a3f3e012aa52e1282c292406dd7970fbe).
- **Receitas, IDs, slots e testes (7):** [IDs sintéticos com slash — 3cbb5e6](https://github.com/mezz/JustEnoughItems/commit/3cbb5e65714dce00d3ac0332845d51714a6dee77), [filtro de ID sintético — 803883f](https://github.com/mezz/JustEnoughItems/commit/803883f6e5596dc01ff2450e996ee507014fcd53), [decorator substitui source ID — 340838d](https://github.com/mezz/JustEnoughItems/commit/340838d5f0268ad8f492e16a83997455ab14454e), [extensões de culinária vanilla — 5e74b39](https://github.com/mezz/JustEnoughItems/commit/5e74b39ef483e62967a28c270ba5ff2ed649f69b), [atalhos em slots sintéticos #4561 — 1061cb1](https://github.com/mezz/JustEnoughItems/commit/1061cb105dd5d2dfa4ff4269e0aff86426d61f21), [teste regressão de navegação #4543 — 09165b3](https://github.com/mezz/JustEnoughItems/commit/09165b3078843180db77783fc6ce02b1f878728c), [widgets de botões de receitas — 8a9ecbc](https://github.com/mezz/JustEnoughItems/commit/8a9ecbccb3c82089c853e1d0e8244ca7df93b350).
- **Interface, atalhos e estado (5):** [cheat em inventário — 8f2f3e9](https://github.com/mezz/JustEnoughItems/commit/8f2f3e9e4187bc19acecd32c4021a0255892c08c), [atalho reatribuído no botão de config #4548 — c9bc968](https://github.com/mezz/JustEnoughItems/commit/c9bc9688672968e6f206b45e4d5ec5f0899bd630), [persistência de overlay #2296 — c08c5c9](https://github.com/mezz/JustEnoughItems/commit/c08c5c9a28c79b5af79222eca1fec4cc2aa13957), [layout de bookmarks #4556 — 588fdff](https://github.com/mezz/JustEnoughItems/commit/588fdff00fb2955e7ec54c244d58b0d6b488), [modificadores Fabric — 2194b95](https://github.com/mezz/JustEnoughItems/commit/2194b958c5ecaed242330882595ed3303b99e986). **O commit Fabric não atesta um efeito equivalente no runtime NeoForge.**
- **Build/plataformas (3):** [verificar bytecode Java de JARs publicados — 5f8f141](https://github.com/mezz/JustEnoughItems/commit/5f8f1418804ba217737153d1af7854529d5407c1), [carregamento de classes em desenvolvimento Forge — 4d56c26](https://github.com/mezz/JustEnoughItems/commit/4d56c2680aeac686c67c26fbf569d1732d66e5cc), [marcar MC 26.1.2 incompatível — b8a20b1](https://github.com/mezz/JustEnoughItems/commit/b8a20b17dc8dfacdeefca937a2399ba946e8a777). **Não atribuir efeitos runtime NeoForge a mudanças de outra plataforma ou só de CI.**

### Novo gate de dependência: evidência do source dos dois canais candidatos

Os **commits mais recentes citados no topo de cada uma das páginas** permitem identificar duas revisões da **branch upstream 1.21.1**, com configuração de versão coerente com a respectiva família de release. Eles são **pins do código-fonte consultado**, não prova de que o JAR publicado seja byte-a-byte a build desse commit:

| Evidência fonte pinada | JEI 19.64.0 (.455) | JEI 19.69.0 (.457) |
|---|---|---|
| Primeiro commit da página oficial | [881197311f260c8b241813436081543ff7dc42a4](https://github.com/mezz/JustEnoughItems/commit/881197311f260c8b241813436081543ff7dc42a4) | [5f8f1418804ba217737153d1af7854529d5407c1](https://github.com/mezz/JustEnoughItems/commit/5f8f1418804ba217737153d1af7854529d5407c1) |
| `specificationVersion` no `gradle.properties` | `19.64.0` | `19.69.0` |
| `mezzConfigVersionRange` | **`[0.6.3,1.0.0)`** | **`[0.6.3,1.0.0)`** |
| `mezzConfigVersion` preferido | `0.6.6` | `0.6.6` |
| `mezzConfigGuiMinimumVersion` (GUI opcional) | `0.5.5` | `0.5.5` |
| `neoforgeVersionRange` | **`[21.1.238,)`** | **`[21.1.238,)`** |

**Verificação do contrato real do projeto fonte, não somente dos nomes de propriedades:** o template [NeoForge `META-INF/neoforge.mods.toml` do source 19.69.0](https://github.com/mezz/JustEnoughItems/blob/5f8f1418804ba217737153d1af7854529d5407c1/NeoForge/src/main/resources/META-INF/neoforge.mods.toml) define `modId="${configModId}"`, `type="required"`, `versionRange="${mezzConfigVersionRange}"`, `side="BOTH"` para MezzConfig; MezzConfigGUI aparece como `type="optional"`, `side="CLIENT"`. O template foi conferido também no pin 19.64.0.

**Jar-in-Jar planejado pelo upstream para AMBAS as revisões:** o [NeoForge `build.gradle.kts` pinado na 19.69.0](https://github.com/mezz/JustEnoughItems/blob/5f8f1418804ba217737153d1af7854529d5407c1/NeoForge/build.gradle.kts) e seu equivalente no pin 19.64.0 configuram `jarJar(mezzConfigNeoForgeDependency)` com `strictly(mezzConfigVersionRange)` e `prefer(mezzConfigVersion)` (**0.6.6**). **Isso prova a configuração de empacotamento do source, NÃO confirma que qualquer arquivo JAR distribuído contenha efetivamente a biblioteca, qual cópia o loader escolheria ou se uma versão top-level antiga impediria inicialização.** Uma biblioteca interna JarJar não recebe entrada adicional na modlist física.

**Cruzamento com a instalação histórica:** **#349** JEI `jei-1.21.1-neoforge-19.56.0.440.jar` e **#394** MezzConfig `mezz_config-1.21.1-neoforge-0.5.9.jar`. Comparação semântica de versões: `0.5.9` **não pertence** à faixa `[0.6.3,1.0.0)` indicada no source **das candidatas .455 e .457**. Portanto **não propor trocar apenas o JEI** sem primeiro examinar as bibliotecas embutidas e top-level, a regra do loader/JarJar e a metadata interpolada dos JARs exatos (inclusive mínimo NeoForge `21.1.238`). Essa conclusão **não declara quebrado o JEI físico .440**, nem prova que um upgrade coordenado com MezzConfig 0.6.x funcionaria com o resto do perfil.

**Pendências explícitas para apagar o JEI #49 do Bloco de Notas:** (i) identificar o pin de build/executável específico dos incrementos `.454→.455` e `.456→.457`, que **não decorre** da comparação entre as duas janelas de 30 commits; (ii) ler os JARs .455/.457 e top-level MezzConfig 0.5.9 (`neoforge.mods.toml` e `META-INF/jarjar/`), inclusive ABI e rangos de biblioteca; (iii) conferir versão NeoForge física e QA com Aeronautics e demais consumidores de JEI. **Ainda pendente**. Nenhum JAR, configuração, pasta, basename ou índice físico foi movido nesta pesquisa.
