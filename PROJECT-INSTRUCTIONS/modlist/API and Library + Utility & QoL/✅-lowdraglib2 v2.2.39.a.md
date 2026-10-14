# LowDragLib2

## Propriedades do registro

- **Mod:** LowDragLib2
- **Arquivo JAR:** ldlib2-neoforge-1.21.1-2.2.39.a-all.jar
- **Versão 1.21.1:** 2.2.39.a
- **Categoria:** Biblioteca, Visual
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://github.com/Low-Drag-MC/LDLib2
- **Função:** Framework/biblioteca para Modular UI, data binding, persistência/sincronização, RPC, node graphs, editores in-game, rendering e integrações de consumers.
- **Dependências:** Runtime físico NeoForge 1.21.1. Bundle contém Taffy 1.1.4, Yoga 1.0.0 e Kotlin stdlib 2.4.20-RC3 como JarJar internos; integrações adicionais do source não são hard dependencies presumidas.
- **Compatibilidade/Riscos:** Rewrite para 1.21+, não assumir compatibilidade drop-in com LDLib antiga. Riscos: API/ABI e schema drift, RPC authority leakage, duplicate plugins/listeners, graph persistence e JarJar skew. Upstream 2.2.40/2.2.41 amplia editor/keymap e graph layout; a 2.2.42 acrescenta correções de serialização de graphs, permissões de painéis read-only e do JarJar Kotlin pre-release. Regressão dos consumers obrigatória.
- **Sobreposição:** Infraestrutura, não sistema de gameplay. Pode compartilhar superfícies de UI/render/data com outras libraries, mas ownership funcional permanece nos consumers.
- **Observações:** Runtime físico permanece 2.2.39.a. O upstream NeoForge 1.21.1 avançou por **2.2.40**, **2.2.41** e **2.2.42 (04/10/2026)**; os JarJar históricos do bundle instalado permanecem Taffy 1.1.4, Yoga 1.0.0 e Kotlin stdlib 2.4.20-RC3.
- **Procedência:** modlist.txt física reconferida + CurseForge oficial LDLib2 2.2.39.a/2.2.40/2.2.41/**2.2.42** para NeoForge 1.21.1 + source oficial branch 1.21 e CHANGELOGS.md 2.2.42 + JarJar do snapshot histórico. A autoridade instalada continua 2.2.39.a.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 14/10/2026 — último snapshot físico LowDragLib2 **2.2.39.a**; CurseForge publicou **2.2.40** (12/09), **2.2.41** (21/09) e **2.2.42** (04/10) para NeoForge 1.21.1. O delta da 2.2.42, incluindo JarJar Kotlin e graph/editor, está na seção 16. Instalação/testes atuais pendentes.
- **Data da última decisão:** 2026-08-26

> **Última autoridade física verificada — snapshot de 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #370: JAR `ldlib2-neoforge-1.21.1-2.2.39.a-all.jar`, mod id `ldlib2`, runtime `2.2.39.a`, SHA-1 `628c9cd7f63f217c554673925854800643948b6a`.

> **ESCOPO CANÔNICO.** No snapshot físico de 25/09/2026 consta `ldlib2-neoforge-1.21.1-2.2.39.a-all.jar`, mod ID `ldlib2`, runtime 2.2.39.a. Biblioteca central de UI, sincronização, graph/editor e persistência; sua atualização requer teste de todos os consumers, sem promover automaticamente uma release externa.
## 1. Identidade e source pin
A modlist física confirma o JAR e a versão 2.2.39.a. O source oficial `Low-Drag-MC/LDLib2`, branch `1.21`, está pinado no commit `d30e86b56b2a31982063ef907a335d36371b0bc4` de 05/09/2026, cujo `gradle.properties` também declara `mod_version=2.2.39.a`, Minecraft 1.21.1 e NeoForge 21.1.x. Esse é o source correspondente usado como referência principal.
## 2. Papel no modpack
LDLib2 é uma reescrita completa da antiga LowDragLib para Minecraft 1.21+. O framework concentra infraestrutura que mods consumidores podem usar para telas, layouts, data binding, persistência, sincronização, graphs, rendering e ferramentas de edição. Não atribuir ao LDLib2 o conteúdo visível de um consumer apenas porque este usa seu framework.
## 3. Modular UI
O README/source documenta um sistema de UI baseado em `ModularUI`/`UIElement`, com componentes reutilizáveis, XML, styling/LSS, data binding e eventos/RPC. A authority de inventários, recipes ou máquinas continua pertencendo ao consumer; LDLib2 fornece a camada de apresentação e comunicação.
## 4. Persistência e sincronização
O framework expõe mecanismos como `@Persisted` e `@DescSynced`, serialização NBT/codecs e sincronização de campos/dirty state. Esses mecanismos podem transportar ou persistir state de consumers, portanto alterações de schema ou annotations são potencialmente save-sensitive. O dado continua semanticamente pertencendo ao mod consumidor.
## 5. RPC e authority
RPC/eventos de UI não devem transformar callbacks client-side em authority de gameplay. Qualquer mutação de inventário, máquina ou mundo precisa ser validada no lado servidor pelo consumer. Inputs recebidos da UI devem ser tratados como pedidos, não como state confiável.
## 6. Node Graph toolkit
LDLib2 inclui toolkit de graphs com nodes, ports, wires, variables, subgraphs, blackboards, undo/history e assets de graph. Consumers podem construir editores ou pipelines em cima disso. IDs/formatos de graph persistidos são contratos do consumer + LDLib2 e precisam de migração quando houver mudança de schema.
## 7. Editor e tooling in-game
O projeto fornece infraestrutura de editor com dockable views, project files, resource browsers, inspectors, history e settings, além de scene/render tooling. Essas superfícies são predominantemente client-facing, mas podem editar dados que depois são consumidos pelo servidor; salvar/publicar alterações exige boundary claro de authority.
## 8. Rendering e integrações
O source documenta suporte a shader/texture/model rendering e integrações/plugins para JEI, REI, EMI e KubeJS, além de superfícies compiladas contra AE2, Iris e Sodium. Presença dessas dependências no build não significa que todas sejam hard dependencies no runtime. Integração deve ser tratada como opcional até metadata/runtime comprovar obrigatoriedade.
## 9. Plugin lifecycle
O framework documenta `@LDLibPlugin`, `ILDLibPlugin` e callbacks como `onLoad`. Plugins precisam registrar uma única vez por lifecycle e evitar handlers duplicados após reload/re-entry. Falha em um plugin pode ter fan-out para vários consumers.
## 10. JarJar físico
O JAR físico contém componentes embarcados, portanto **não top-level**: `taffy-1.1.4.jar`, `yoga-1.0.0.jar` e `kotlin-stdlib-2.4.20-RC3.jar`. O source pin referencia versões de build que podem diferir em detalhe do bundle final; para o runtime desta instância, a composição física prevalece.
## 11. Compatibilidade e migração
LDLib2 não é drop-in replacement garantido da LDLib antiga. O próprio projeto se apresenta como rewrite para 1.21+. Consumers compilados contra API velha ou outra revisão 2.x podem falhar por linkage/API drift. Atualização deve ser feita em conjunto com regressão dos consumers.
## 12. Riscos técnicos
1. **API/ABI drift** quebra consumers em bootstrap ou ao abrir UI.
2. **Schema drift** em dados `@Persisted` pode corromper/perder state de consumer.
3. **RPC authority leakage** se o consumer confiar no cliente.
4. **Duplicate plugin/listener registration** após lifecycle incorreto.
5. **Graph serialization drift** invalida assets/projetos persistidos.
6. **Render integration conflicts** com Iris/Sodium ou renderers de consumers.
7. **JarJar skew** se outro host empacotar versões incompatíveis de Taffy/Yoga/Kotlin.
8. **Removal fan-out:** remover a biblioteca pode derrubar múltiplos mods dependentes.
## 13. Matriz de testes
- [ ] Dedicated server inicia com LDLib2 2.2.39.a e consumers atuais.
- [ ] Client abre todas as UIs de consumers sem missing class/method.
- [ ] UI RPC não permite mutation não autorizada pelo servidor.
- [ ] Relog/restart preserva state persistido por consumers.
- [ ] Reload/reabertura de telas não duplica listeners ou packets.
- [ ] Assets de graph/editor carregam e salvam sem perda.
- [ ] Iris/Sodium coexistem sem render corruption nas UIs/scene tools usadas.
- [ ] Atualização futura só ocorre após regressão dos consumers.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 14. Evidências e limites
- modlist física: JAR 2.2.39.a, mod id e JarJar internos;
- source oficial branch `1.21`, commit `d30e86b…`: versão 2.2.39.a, MC 1.21.1, NeoForge 21.1.x e contratos públicos do framework;
- README oficial do mesmo commit: Modular UI, persist/sync, graph toolkit, editor, rendering e plugin model.
Não foi inferido quais consumers do pack utilizam cada API específica; isso exige auditoria das dependências/imports desses mods.

## 15. Histórico intermediário upstream 2.2.39.a → 2.2.41 — não instalado (2.2.42 na seção 16)
A autoridade física continua em **LowDragLib2 2.2.39.a**. Existem duas releases posteriores na linha NeoForge 1.21.1.

### 2.2.40 — 12/09/2026
- adiciona framework configurável de **keymaps para o editor**;
- permite chords rebindáveis;
- adiciona **key contexts**;
- adiciona uma página de settings para configuração desses atalhos.

Impacto: consumers/editor tooling que interceptem teclado podem mudar comportamento de input mesmo sem alteração de gameplay. Rebind/context devem ser client-side e não disparar RPC/mutation duas vezes.

### 2.2.41 — 21/09/2026
- adiciona **auto layout** ao menu contextual de graphs;
- algoritmos disponíveis: **layered, grid e force-directed**;
- placemats podem ser organizados como uma única caixa ou de dentro para fora.

Impacto: a 2.2.41 atua na organização/editor de graph, mas graphs são assets persistíveis em vários consumers. Auto-layout não deve alterar IDs, connections, variables, blackboards ou semântica de execução; somente geometria/layout visual quando aplicado.

### Gate de promoção
1. keybind default e rebind;
2. conflito de chords entre contextos;
3. salvar/reabrir settings de keymap;
4. graph existente antes/depois de layered/grid/force-directed layout;
5. placemats e nested/subgraphs;
6. undo/redo após auto-layout;
7. persistência sem alteração de node IDs/wires;
8. RPC exatamente uma vez para atalhos que executem ações;
9. UIs dos consumers atuais;
10. dedicated-server boot e ausência de classloading de editor client-only.

Fontes upstream: CurseForge files 8866144 (`2.2.40`) e 8940836 (`2.2.41`) para NeoForge 1.21.1.

## Atualização upstream e gate de manutenção — 11/10/2026

**Identificação:** o item `LDLib` corresponde à biblioteca **LowDragLib2** neste modpack (Minecraft 1.21.1); o último físico é `ldlib2-neoforge-1.21.1-2.2.39.a-all.jar`. **Upstream NeoForge 1.21.1:** **2.2.42** disponível desde 04/10/2026, sucedendo 2.2.40/2.2.41. O changelog exclusivo de 2.2.42 foi localizado e auditado em 14/10/2026; consultar a seção 16 para mudanças verificadas, inclusive fix do Kotlin pre-release embarcado. **Gate:** APIs de UI, editors/graph, IDs/links, RPC exactly-once, rebinds/chords, undo/redo, persistência de nodes, regressões de consumers, JarJar e dedicated-server boot. **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ldlib/files/all?version=1.21.1

> **Limite:** revisão documental baseada no último snapshot físico; nenhuma instalação de JAR ou validação in-game nesta execução.

## 16. Auditoria técnica da disponibilidade 2.2.42 e decisão de não atualizar às cegas — 14/10/2026

### 16.1. Versão no pack, latest compatível e diferença de artefato

- **Instalado no último snapshot físico #370 (25/09/2026):** `ldlib2-neoforge-1.21.1-2.2.39.a-all.jar`, mod ID `ldlib2`, versão `2.2.39.a`, SHA-1 `628c9cd7f63f217c554673925854800643948b6a`. JarJar do snapshot: `taffy-1.1.4.jar`, `yoga-1.0.0.jar` e `kotlin-stdlib-2.4.20-RC3.jar`. Esses componentes embarcados **não são mods top-level**. A presença do artefato **em outubro** não foi revalidada nesta execução.
- **Última publicação compatível verificada:** [CurseForge File ID `9059591`](https://www.curseforge.com/minecraft/mc-mods/ldlib/files/9059591), **04/10/2026**, release **2.2.42** para NeoForge Minecraft `1.21.1` (também 1.21). O campo oficial **File name** declara `ldlib2-neoforge-1.21.1-2.2.42-all.jar`; é um artefato distinto e não instalado/certificado no perfil. O [índice oficial do projeto](https://www.curseforge.com/minecraft/mc-mods/ldlib/files/all) distingue as publicações **26.x** das releases Minecraft **1.21.1**.
- **Source 2.2.39.a anteriormente pinado:** [commit `d30e86b56b2a31982063ef907a335d36371b0bc4`](https://github.com/Low-Drag-MC/LDLib2/tree/d30e86b56b2a31982063ef907a335d36371b0bc4), branch `1.21`, cujo `gradle.properties` confirma `mod_version=2.2.39.a`. Mantê-lo como referência dos contratos históricos, sem confundi-lo com a 2.2.42.
- **Source mais recente consultado:** [commit `ae11d71607ada1364de154634a2652a73b66ba0c`](https://github.com/Low-Drag-MC/LDLib2/tree/ae11d71607ada1364de154634a2652a73b66ba0c), branch `1.21`, cujo [`gradle.properties`](https://github.com/Low-Drag-MC/LDLib2/blob/ae11d71607ada1364de154634a2652a73b66ba0c/gradle.properties) declara `mod_version=2.2.42`, Minecraft `1.21.1`, NeoForge compile `21.1.238`, `taffy_version=1.1.5`, `yoga_version=1.0.0` e `kotlin_stdlib_version=2.1.20`. **O commit público consultado não foi certificado como fonte byte-for-byte da release File 9059591**.

### 16.2. Changelog oficial 2.2.42 — novidades e correções materiais

O [`CHANGELOGS.md` oficial da branch `1.21`](https://github.com/Low-Drag-MC/LDLib2/blob/ae11d71607ada1364de154634a2652a73b66ba0c/CHANGELOGS.md) documenta expressamente as seguintes mudanças da **2.2.42**, que antes estavam sem detalhamento:

| Subsistema | Alteração 2.2.42 verificada | Consequência e teste necessário |
| --- | --- | --- |
| **Window/editor** | Mover uma janela de editor para janela nativa do SO; diálogos lembram tamanho e se ajustam à tela. | Testar foco, escala GUI, fechamento, vários monitores, minimizing/reabrir e persistência de bounds; evitar comandos duplicados. |
| **Asset browser** | Busca global, pasta como provider, busca/sort em grids, menu por project type e drag-drop de views para arquivos; correções de cache/providers e paths. | Testar colisão de nomes, rename, pastas aninhadas, providers dinâmicos, exclusão/recriação de arquivos e integridade de assets. |
| **UI editor e undo/redo** | Undo/redo para adicionar, remover, colar, mover elementos; debugger acessível de simulation via F3; seleção Ctrl+A para graph. | Garantir que comandos de UI não gerem duplo RPC ou mutação server-side não autorizada; rollback de IDs/state. |
| **Keyboard/input** | Shortcut bubbling do elemento focado até view; configuradores lembram campo editado; menús contextuais enriquecidos. | Regressar keymap 2.2.40 e text inputs para não executar ações de jogo enquanto usuário edita texto. |
| **Graph schema** | Correções para nós aninhados herdando portas/opções indevidas, options que mudam portas e declaração de tipo de variável do próprio graph perdida no reload. | Testar graph persistente, blackboards, connections, node IDs/ports, migração e round-trip sem perda semântica. |
| **Packaging** | **Corrige empacotamento no mod JAR de uma Kotlin standard library pre-release diferente da versão de compilação**. | **Crítico para o snapshot:** ele contém `kotlin-stdlib-2.4.20-RC3.jar`. A 2.2.42 declara compile `2.1.20`, mas não presumir o conteúdo exato do JAR novo sem abrir o artefato; auditar JarJar de qualquer candidato. |
| **Taffy / Yoga** | Source 2.2.42 declara `taffy_version=1.1.5` e Yoga `1.0.0`; snapshot antigo inclui Taffy `1.1.4` e Yoga `1.0.0`. | Verificar metadata JarJar, transitive deps e eventuais consumidores do engine de layout ao atualizar; **não instalar Taffy separadamente apenas por estar embutido**. |
| **Segurança do editor** | Corrigiu painel disabled/read-only ainda permitir alterações por campos, pickers, paste, reordering ou dialogs. | Validar boundary de leitura/escrita: callbacks client-side não podem forçar mutação de state autoritativo do consumer. |

**Não reduzir a release 2.2.42 a “apenas cosmética”:** há mudanças em serialização de graph, visibilidade de controles read-only, encaminhamento de shortcuts, resources e JarJar. Ao mesmo tempo, **nenhuma dessas correções prova incompatibilidade atual** na 2.2.39.a.

### 16.3. Ownership e gate de promoção

**Consumers concretamente conhecidos pelo catálogo histórico:** **KilaGraph #357** (`kilagraph-neoforge-1.21.1-21.1.0.14.jar`) declara dependência direta LDLib2 com range `[2.2.33,)`, conforme o [dossiê KilaGraph](../API%20and%20Library%20%2B%20Utility%20%26%20QoL/%E2%9C%85-kilagraph%20v21.1.0.14.md); **Photon #442** (`photon-neoforge-1.21.1-2.2.6.a-all.jar`) incorpora uma cópia **JarJar KilaGraph 21.1.0.14**, de acordo com o mesmo snapshot. Photon é **host do KilaGraph embarcado** e parte da cadeia a regredir; isso não certifica que Photon declare uma dependência direta adicional de LDLib2 na sua metadata. O top-level KilaGraph 21.1.0.14 e seu homônimo nested no Photon devem resolver sem dois providers, inclusive após mudança de LDLib2. Outros consumers são **desconhecidos**, até auditoria física completa de dependências/imports.

A biblioteca só fornece componentes e infraestrutura. Um addon consumidor continua responsável pelos seus próprios dados, UIs, workflows e autorização server-side; a existência de integração no Gradle (`JEI`, `REI`, `EMI`, `KubeJS`, `AE2`, `Iris`, `Sodium`) não torna tudo dependência hard nem evidencia consumers instalados específicos. A lista real de consumidores deve ser obtida por dependências/metadata/imports do **perfil físico**.

**Sem promover o JAR ainda.** Exigir:
- [ ] Inspeção atual do `ldlib2` instalado, SHA-1/SHA-256, `neoforge.mods.toml`, `META-INF/jarjar`, classloading e consumers atuais por mod ID, dependências e chamadas de API.
- [ ] Conferir a assinatura/hash/file ID da distribuição 2.2.42, layout dos JARs Kotlin/Taffy/Yoga, comparar bytecode e metadata; registrar plano de rollback para 2.2.39.a.
- [ ] **Boot CLIENT** antes/depois com **KilaGraph top-level #357 (21.1.0.14)**, **Photon #442 (2.2.6.a) e JarJar KilaGraph 21.1.0.14**, **JEI #349 (19.56.0.440)**, **KubeJS #363 (2101.7.2-build.377)**, **Iris #338 (1.8.14-beta.1)** e **Sodium #512 (0.8.13)**. Validar UI, graph, render, linkage/API e resolução nested/top-level sem dois providers.
- [ ] **Boot DEDICATED SERVER** antes/depois com LDLib2, KilaGraph, Photon e demais mods **server-compatible realmente presentes**, sem pressupor **Iris/Sodium** no ambiente servidor. Verificar ausência opcional dos renderers, ausência de classloading de classes client-only e preservação de RPC/data authority no servidor.
- [ ] **Provider-absence gate:** **REI**, **EMI** e **Applied Energistics 2 (AE2)** não constam como mods top-level no snapshot certificado. Confirmar inicialização dos hooks de integração **sem** esses providers e tratamento correto de soft dependencies/ausência; não exigir testes funcionais de mods ausentes.
- [ ] Testar todos os consumers instalados que exibem `ModularUI` e telas remotas com packets/RPC; não aceitar input não autorizado, duplication nem desync.
- [ ] Abrir e salvar **graphs reais KilaGraph #357 e resources Photon #442**, verificar Blueprint / RenderType / Shader Function graphs, compilation, node IDs, ports dinâmicos e declarados, variables/blackboards, undo/redo, project files e ausência de corrupção após rename/move/reload/restart; certificar igualdade de KilaGraph nested e top-level antes/depois.
- [ ] Testar keymap 2.2.40 e shortcuts: teclas em text inputs, foco, Ctrl+A, copy/paste, múltiplas janelas OS e GUI scale.
- [ ] Validar Taffy layout, UI render/font, shaders Iris, reload, relog, restart e estado de editor read-only.
- [ ] Só após critérios claros e backup dos saves/configs, avaliar atualização física e **então** reconciliar modlist e filename de dossiê pela nova versão.

**Status: PENDENTE — update 2.2.42 justificado para avaliação, não autorizado como compatível sem QA.** Histórico 2.2.39.a e decisões/documentos existentes foram preservados. Não houve alteração de JAR, item top-level, shader, resource pack, datapack, mundo, config, script ou `modlist.md`; **somente o dossiê** foi atualizado neste ciclo.
