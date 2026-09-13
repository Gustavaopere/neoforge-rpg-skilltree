# LowDragLib2

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81f7a67fe0fd02bc3cbd
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR `ldlib2-neoforge-1.21.1-2.2.39.a-all.jar`, mod id `ldlib2`, runtime `2.2.39.a` e mixin `ldlib2.mixins.json` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** LowDragLib2
- **Arquivo JAR:** `ldlib2-neoforge-1.21.1-2.2.39.a-all.jar`
- **Versão 1.21.1:** 2.2.39.a
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca, Visual
- **Função:** Framework/biblioteca para Modular UI, data binding, persistência/sincronização, RPC, node graphs, editores in-game, rendering e integrações de consumers.
- **Dependências:** Runtime físico NeoForge 1.21.1. Bundle contém Taffy 1.1.4, Yoga 1.0.0 e Kotlin stdlib 2.4.20-RC3 como JarJar internos; integrações adicionais do source não são hard dependencies presumidas.
- **Sobreposição:** Infraestrutura, não sistema de gameplay. Pode compartilhar superfícies de UI/render/data com outras libraries, mas ownership funcional permanece nos consumers.
- **Compatibilidade/Riscos:** Rewrite para 1.21+, não assumir compatibilidade drop-in com LDLib antiga. Riscos: API/ABI e schema drift, RPC authority leakage, duplicate plugins/listeners, graph persistence e JarJar skew.
- **Observações:** Source oficial `Low-Drag-MC/LDLib2` branch 1.21 está em commit `d30e86b56b2a31982063ef907a335d36371b0bc4`, que declara exatamente 2.2.39.a. Composição física do `-all.jar` prevalece para libs internas.
- **Procedência:** modlist.txt física atual + source oficial Low-Drag-MC/LDLib2 branch 1.21 commit d30e86b56b2a31982063ef907a335d36371b0bc4 + arquivos de build e README da mesma revisão.
- **Fonte:** https://github.com/Low-Drag-MC/LDLib2
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — source 2.2.39.a pinado; Modular UI, persist/sync, RPC, graph/editor tooling, plugins, JarJar, lifecycle, risks e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `ldlib2-neoforge-1.21.1-2.2.39.a-all.jar`, mod id `ldlib2`, versão `2.2.39.a`. É uma biblioteca/framework técnico amplo para UI, sincronização/persistência de dados, graph tooling e editores in-game; não é gameplay autônomo.

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
