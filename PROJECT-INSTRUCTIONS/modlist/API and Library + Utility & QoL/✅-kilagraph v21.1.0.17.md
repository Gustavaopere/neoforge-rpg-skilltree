# KilaGraph — 21.1.0.17

## Propriedades vigentes — 27/11/2026

- **Mod:** KilaGraph
- **Arquivo JAR instalado (declarado):** `kilagraph-neoforge-1.21.1-21.1.0.17.jar` — basename corroborado por evidência cruzada; ficha individual CurseForge não recuperada.
- **Versão instalada declarada:** `21.1.0.17` — Release, Minecraft 1.21.1 / NeoForge.
- **Ordem física histórica:** #357 (preservada).
- **Categoria CurseForge / pasta:** API and Library + Utility & QoL.
- **Função:** Toolkit programável de graphs e shaders sobre LDLib2; API e editor visual para consumers.
- **Atualização/Status:** `21.1.0.14` → `21.1.0.17`.
- **Estado no pack:** instalação declarada pelo usuário; JAR local/metadata não relidos.
- **Estado da pesquisa:** auditoria incremental das publicações identificadas; limites de changelog, metadata e procedência indicados abaixo; sem homologação de runtime.
- **Dependências / compatibilidade / riscos:** [gradle.properties](https://github.com/Low-Drag-MC/KilaGraph/blob/1.21/gradle.properties) e [metadata template](https://github.com/Low-Drag-MC/KilaGraph/blob/1.21/src/main/resources/META-INF/neoforge.mods.toml) da branch consultada: NeoForge >=21.1.217, LDLib2 >=2.2.33; build LDLib2 2.2.41, índice #370 2.2.39.a supera piso mas é anterior ao alvo de build (item 38 LDLib). Photon 2.2.6.a tinha JarJar KilaGraph 0.14 no snapshot; divergência top-level/embedded exige verificar resolução real e consumers; não criar entrada para JarJar. Testar graph persistence, tipos enum/pins, side effects de hurt/NBT, callback de escrita, execução off-thread e shader compilation.
- **Fonte:** [publicação ou catálogo de referência](https://www.curseforge.com/minecraft/mc-mods/kilagraph/files/all).
- **Procedência:** solicitação expressa de atualização, fontes oficiais e, onde explicitado, corroboradores secundários; não atribuir hash/metadata da versão nova sem leitura do binário.

## Auditoria incremental das publicações

- 21.1.0.15 — 21/09, [arquivo 8941172](https://www.curseforge.com/minecraft/mc-mods/kilagraph/files/8941172), Beta: quaternions/rotações, vetores, matemática, declaração de execução off-thread por node, variable store API, Set Var ligado à declaração, setSampler com parâmetros, correções de ordem/tipo de ports e pins carregados.
- 21.1.0.16 — 05/10, [arquivo 9070731](https://www.curseforge.com/minecraft/mc-mods/kilagraph/files/9070731), Beta: nearest entity, graph type damage source e nodes de construção/leitura/tag/hurt; NBT Copy e copy pins mantendo mutação in-place como default, cor→vetor RGB, reavaliação de pure nodes por exec consumer, correções de loops/subgraphs/outputs e localização de ports. As builds 26.x .16.a não são desta linha.
- 21.1.0.17 — 26/11, Release: Enum node, opção de tipo em Equals/Not Equals permitindo valores constantes sem fio, callback EvaluationEnvironment#onVariableSet após SetVar para notificar o host. [Changelog branch 1.21](https://github.com/Low-Drag-MC/KilaGraph/blob/1.21/CHANGELOG.md) confirma o delta. O catálogo CF confirma a versão e loader; ficha individual e ID não recuperados. Basename acima corroborado por [Modgrade](https://modgrade.com/minecraft/mod/kilagraph) e pelo build upstream (mod_version e archivesName de projeto); não alegar inspeção binária nem usar sources.jar adicional como instalado.

## Histórico documental preservado

> Os dados abaixo descrevem o snapshot ou a auditoria na sua data original. Menções a versão física, atual, candidata, bloqueada, não instalada, hashes e requisitos anteriores são históricas; as propriedades vigentes acima prevalecem. A declaração atual não constitui teste de execução.

## Propriedades do registro

- **Mod:** KilaGraph
- **Arquivo JAR:** kilagraph-neoforge-1.21.1-21.1.0.14.jar
- **Versão 1.21.1:** 21.1.0.14
- **Categoria:** Biblioteca, Visual, Tecnologia
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://github.com/Low-Drag-MC/KilaGraph/tree/1.21
- **Função:** Toolkit de node graphs e shader graphs construído sobre o ecossistema LDLib2, oferecendo grafos programáveis, RenderType/Shader Function graphs e editor visual para mods consumidores.
- **Dependências:** Source exato 21.1.0.14 requer NeoForge \>=21.1.217 e LDLib2 \>=2.2.33. Pack físico: NeoForge 21.1.250 e LowDragLib2 2.2.39.a, satisfazendo os ranges. Photon 2.2.6.a também embute KilaGraph 21.1.0.14 como JarJar.
- **Compatibilidade/Riscos:** Build física 21.1.0.14 é source-pinned e Beta. Riscos: LDLib2 ABI drift, graph serialization/schema drift, shader compilation/render-state errors, duplicate nested/top-level resolution e consumers usando nodes/graphs incompatíveis. Upstream 21.1.0.15 adiciona novos tipos/nodes e execução off-thread declarativa; uma atualização pode quebrar serialized pins/ports e exige auditoria de thread-safety dos consumers. Top-level e JarJar de Photon estão atualmente 0.14, portanto atualizar apenas um lado criaria version divergence.
- **Sobreposição:** Não é shaderpack nem renderer standalone. É framework/editor consumido por outros mods. A cópia embutida em Photon é nested dependency do host e não mod top-level adicional; a igualdade de versão reduz, mas não elimina, necessidade de validar resolução do loader.
- **Observações:** Runtime físico/top-level permanece 21.1.0.14, com cópia JarJar 21.1.0.14 dentro de Photon. Upstream publicou **21.1.0.15** para NeoForge 1.21.1 em 21/09/2026; ela não está instalada.
- **Procedência:** modlist física atual + source oficial Low-Drag-MC/KilaGraph branch 1.21 para a baseline 21.1.0.14 + CurseForge 21.1.0.15 + boundary JarJar do Photon. A autoridade física continua 21.1.0.14.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — KilaGraph físico permanece 21.1.0.14. A release **21.1.0.15** para NeoForge 1.21.1 foi revisada integralmente e está registrada abaixo.
- **Data da última decisão:** 2026-08-30

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #357: JAR `kilagraph-neoforge-1.21.1-21.1.0.14.jar`, mod id `kilagraph`, runtime `21.1.0.14`, SHA-1 `9a349840268ab8d06f69f2f1edab6704f59f78aa`.

<callout icon="🕸️" color="purple_bg">
	**ESCOPO CANÔNICO.** Runtime físico top-level: `kilagraph-neoforge-1.21.1-21.1.0.14.jar`, mod id `kilagraph`, versão `21.1.0.14`. A mesma versão aparece também como `META-INF/jarjar/kilagraph...21.1.0.14.jar` dentro de Photon 2.2.6.a; esse nested JAR é dependência do host e **não recebe posição top-level própria**.
</callout>
## 1. Papel e authority
KilaGraph é infraestrutura de grafos programáveis e shader graphs para mods. Ele fornece modelos/editor/runtime de graph; o consumer continua authority do significado gameplay ou visual dos nodes que registra.
## 2. Blueprint Graphs
A documentação pública descreve Blueprint Graphs como grafos programáveis de lógica/data flow. Graph execution precisa ser determinístico conforme o contrato do consumer e não deve ser tratado como gameplay próprio de KilaGraph quando o efeito final pertence a outro mod.
## 3. RenderType Graphs
KilaGraph permite construir grafos ligados a pipelines `RenderType`/shader. Compilação de shader, resources e render state são client-side; falha gráfica não autoriza mutation de gameplay ou server state.
## 4. Shader Function Graphs
Subgrafos/funções reutilizáveis permitem compartilhar lógica de shader. Mudança de assinatura, port type ou resource ID pode quebrar graphs persistidos/empacotados por consumers mesmo sem alterar o nome visível do efeito.
## 5. LDLib2 e version gate
O source exato `Low-Drag-MC/KilaGraph:1.21` declara `mod_version=21.1.0.14`, NeoForge mínimo **21.1.217** e `ldlib2_version_range=[2.2.33,)`. O pack usa **NeoForge 21.1.250** e **LowDragLib2 2.2.39.a**, satisfazendo ambos os ranges publicados.
## 6. Photon — nested same-version
O top-level Photon 2.2.6.a contém um JarJar de **KilaGraph 21.1.0.14**, exatamente igual ao top-level. O loader deve resolver a identidade/mod version sem criar dois providers independentes. Não contar nested copy como mod separado e não criar ficha adicional.
## 7. Source pin 21.1.0.14
A modlist física continua authority de presença/JAR, e o source oficial branch `1.21` declara exatamente **`mod_version=21.1.0.14`**. Portanto esta ficha é **source-pinned**. Um changelog específico da 0.14 não foi localizado; não atribuir mudanças version-specific sem evidência.
## 8. Histórico anterior sem retroprojeção
A 21.1.0.12 documentou correções em whole-number/list ports e preparação para execução multithread; 0.13 refatorou vector nodes, adicionou tangent-space support e completou node wiki entries. São contexto acumulado da linha, não changelog atribuído à 0.14.
## 9. Serialization e resources
Graphs podem depender de node IDs, port types, settings e resources. Atualizar KilaGraph/LDLib2 pode invalidar serialized graphs de consumers. Migração precisa preservar IDs e falhar diagnosticavelmente quando node/type deixa de existir.
## 10. Client / server
Editor e shader/render graphs são majoritariamente client-facing, mas Blueprint Graphs podem participar de lógica de consumers. Sem metadata/source exato da 0.14, não se classifica toda a library como client-only. Qualquer path de gameplay deve permanecer server-authoritative no consumer.
## 11. Lifecycle
Validar registry/bootstrap, carregamento de graph resources, editor open/save, resource reload, shader compilation, world join e consumer load. Nested/top-level resolution deve permanecer estável em cold boot e dedicated server.
## 12. Riscos técnicos
- LDLib2 ABI drift;
- graph/node/port schema incompatível;
- serialized graph quebrar após update;
- shader compile/render-state failure;
- execução duplicada por registro repetido;
- nested/top-level version divergence futura;
- consumer assumir node removido;
- atribuir mudança 0.12/0.13 à 0.14 sem evidência.
## 13. Matriz de testes obrigatória
- [ ] Cliente e dedicated server iniciam com top-level KilaGraph 21.1.0.14.
- [ ] Loader não cria dois mods por causa do JarJar de Photon.
- [ ] LDLib2 2.2.39.a carrega sem linkage error.
- [ ] Photon abre/usa seus graph resources sem duplicate registration.
- [ ] Blueprint graph de consumer executa exatamente uma vez.
- [ ] Shader/RenderType graph compila sem missing node/resource.
- [ ] Resource reload não duplica graphs.
- [ ] Serialized graph sobrevive restart quando aplicável.
- [ ] Update futuro compara top-level e nested versions antes de adoção.
## 14. Evidências e limites
- **Modlist física:** top-level 21.1.0.14, LDLib2 2.2.39.a e nested KilaGraph 21.1.0.14 dentro de Photon.
- **Source oficial:** branch `1.21`, `mod_version=21.1.0.14`, NeoForge \>=21.1.217 e LDLib2 \>=2.2.33.
- **Documentação pública:** Blueprint/RenderType/Shader Function graphs e LDLib2 editor/toolkit.
- **Limite:** changelog específico da 21.1.0.14 não foi localizado; mudanças version-specific permanecem fail-closed.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.


## 15. Atualização upstream 21.1.0.15 — não instalada
A autoridade física continua em **KilaGraph 21.1.0.14**. A release **21.1.0.15** Beta foi publicada em 21/09/2026.

### Novos tipos e nodes
- adiciona tipo **quaternion** e nodes de rotação: axis-angle, Euler, from-to, compose, inverse, slerp, rotate-vector, angle-between e bridge Vec4;
- adiciona vector nodes: direction-to, set-length, slerp, perpendicular, wrap e conversões Vec2/Vec3/Vec4;
- adiciona math nodes: wrap, snap, step, smoothstep, inverse lerp, delta angle, move-towards, nearly-equal e wave.

### Execução e APIs
- cada node pode declarar se o graph pode executá-lo **fora da game thread**;
- adiciona **variable store access API**;
- `Set Var` passa a referenciar a **declaração da variável**, em vez de depender do nome escrito;
- adiciona `setSampler`, vinculando parâmetros de um `Sampler2D` além da texture.

### Correções de editor/schema
- corrige declared ports aparecendo abaixo de ports dinâmicos;
- corrige port sem accessor perdendo o editor registrado para seu próprio tipo;
- corrige constant de pin carregado mantendo o tipo salvo antigo em vez do **tipo declarado pelo pin**.

Impacto: a 0.15 amplia fortemente a superfície de graph schema e adiciona potencial execução off-thread. Nodes de consumers não podem ser executados off-thread sem thread-safety explícita; game state/render state não deve ser tocado de worker threads. As correções de pin type/variable declaration também são migration-sensitive para graphs persistidos.

Gate de promoção 0.14→0.15: abrir/salvar graphs antigos; pins com tipo alterado; dynamic+declared ports; Set Var após rename; quaternion/vector/math nodes; sampler params; off-thread node em graph puro; bloquear off-thread para node que toca game state; resource reload; shader compilation; LDLib2 compat; loader resolution com o JarJar 0.14 do Photon.

Fonte upstream: CurseForge file ID 8941172, `kilagraph-neoforge-1.21.1-21.1.0.15.jar`.

## 17. Revalidação 21.1.0.15 e source posterior — 10/10/2026

**Físico:** `kilagraph-neoforge-1.21.1-21.1.0.14.jar` e componente interno **JarJar 21.1.0.14 dentro de Photon**, conforme snapshot. **Mais recente pública CurseForge para NeoForge Minecraft 1.21.1:** **21.1.0.15** de 21/09/2026. O histórico completo 0.14→0.15 de quaternions, vectors, math, node scheduling off-thread, pins e graph schema já foi incorporado na seção anterior; mantê-lo como autoridade para a release pública.

O **source GitHub `Low-Drag-MC/KilaGraph` branch `1.21`** já contém seção de changelog **v21.1.0.16** com recursos **não confirmados como release CurseForge 1.21.1**:
- nearest-entity node;
- `damage source` como graph type, com nós para leitura, teste de damage-type tag, criação e `hurt`;
- NBT Copy e copy pins em NBT Set/Path Set/Remove, que continuam mutando in-place por default;
- conversão de cor em vector RGB;
- avaliação de pure nodes por consumer exec, correções de loops/subgraph/flow state, atualização de port labels.

**Importante:** 0.16 **não** foi promovida a versão disponível no CurseForge nem tratada como código instalado. Essas mudanças sugerem risco futuro maior por exporem side effects de dano e mutação NBT em graph execution; avaliar server authority, duplicação de hurt e write ordering se forem publicadas.

**Gate:** não atualizar apenas KilaGraph top-level sem confrontar a versão embarcada em Photon e a ABI LDLib2 2.2.39.a. Verificar graph persistence/pin migration, sampler/shader, tool/editor e ambientes client+server. Nenhum teste de runtime foi realizado.

**Fontes:** https://www.curseforge.com/minecraft/mc-mods/kilagraph/files/all ; https://github.com/Low-Drag-MC/KilaGraph/blob/1.21/CHANGELOG.md

**Decisão:** 21.1.0.15 permanece candidata pública, 0.16 em observação de source; físico 0.14 preservado.

