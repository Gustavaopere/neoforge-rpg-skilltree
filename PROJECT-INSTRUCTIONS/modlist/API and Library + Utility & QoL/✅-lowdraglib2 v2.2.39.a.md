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
- **Compatibilidade/Riscos:** Rewrite para 1.21+, não assumir compatibilidade drop-in com LDLib antiga. Riscos: API/ABI e schema drift, RPC authority leakage, duplicate plugins/listeners, graph persistence e JarJar skew. Upstream 2.2.40/2.2.41 amplia diretamente editor/keymap e graph layout; consumers que persistem graphs ou interceptam input devem ser regressados.
- **Sobreposição:** Infraestrutura, não sistema de gameplay. Pode compartilhar superfícies de UI/render/data com outras libraries, mas ownership funcional permanece nos consumers.
- **Observações:** Runtime físico permanece 2.2.39.a. O upstream NeoForge 1.21.1 avançou depois por **2.2.40** e **2.2.41**; os JarJar físicos do bundle instalado permanecem Taffy 1.1.4, Yoga 1.0.0 e Kotlin stdlib 2.4.20-RC3.
- **Procedência:** modlist.txt física reconferida + CurseForge oficial LDLib2 2.2.39.a/2.2.40/2.2.41 para NeoForge 1.21.1 + source oficial branch 1.21 + JarJar físico do host. A autoridade instalada continua 2.2.39.a.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — runtime físico permanece LowDragLib2 2.2.39.a. CurseForge publicou **2.2.40** em 12/09/2026 e **2.2.41** em 21/09/2026 para NeoForge 1.21.1; ambas foram revisadas e registradas abaixo.
- **Data da última decisão:** 2026-08-26

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #370: JAR `ldlib2-neoforge-1.21.1-2.2.39.a-all.jar`, mod id `ldlib2`, runtime `2.2.39.a`, SHA-1 `628c9cd7f63f217c554673925854800643948b6a`.

<callout icon="🧩" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `ldlib2-neoforge-1.21.1-2.2.39.a-all.jar`, mod id `ldlib2`, versão `2.2.39.a`. É uma biblioteca/framework técnico amplo para UI, sincronização/persistência de dados, graph tooling e editores in-game; não é gameplay autônomo.
</callout>
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

## 15. Histórico upstream 2.2.39.a → 2.2.41 — não instalado
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