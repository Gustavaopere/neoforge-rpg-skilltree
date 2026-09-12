# Forgified Fabric API — 0.116.15+2.3.5+1.21.1

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81fabf63d54612f42938  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-09

## Propriedades do registro

- **Mod:** Forgified Fabric API
- **Arquivo JAR:** `forgified-fabric-api-0.116.15+2.3.5+1.21.1.jar`
- **Versão 1.21.1:** `0.116.15+2.3.5+1.21.1`
- **Categoria:** Biblioteca; Compat
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://github.com/Sinytra/ForgifiedFabricAPI/tree/1.21.1
- **Função:** Camada de compatibilidade/API que porta grande parte dos contratos Fabric API para NeoForge/Sinytra: registries/sync, lifecycle/events, networking, transfer, tags/recipes/resources, data attachments, biomes e APIs client de rendering/screens/model/keybind.
- **Dependências:** Infraestrutura Sinytra/NeoForge conforme consumidores. O JAR físico incorpora 44 módulos Forgified Fabric API e um Forgified Fabric Loader interno; esses componentes embarcados não são mods top-level separados.
- **Compatibilidade/Riscos:** Version skew entre consumidores e APIs, double-processing via hooks Fabric+NeoForge, registry mismatch, mixin collision, client classloading em dedicated server, adapters de transfer duplicados e stale caches após reload. 2.3.5 corrige incompatibilidade concreta em FabricTooltipType.
- **Sobreposição:** Sobreposição de infraestrutura com NeoForge nativo e Sinytra Connector é intencional para compatibilidade, não redundância simples. Evitar implementar a mesma regra simultaneamente por evento NeoForge e hook Forgified Fabric.
- **Observações:** Runtime host confirmado como fabric_api 0.116.15+2.3.5+1.21.1. O JAR contém 44 módulos/API jars + forgified-fabric-loader interno em META-INF/jars; não criar entradas top-level para eles. Puddles & Floods permanece registrado como consumidor confirmado no catálogo anterior.
- **Procedência:** modlist.txt física atual de 09/09/2026 + source oficial Sinytra/ForgifiedFabricAPI branch 1.21.1 + release oficial 0.116.15+2.3.5+1.21.1.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Forgified Fabric API 0.116.15+2.3.5+1.21.1; 44 módulos embarcados + loader interno, contratos, side/lifecycle, riscos de bridge e matriz de testes catalogados.
- **Data da última decisão:** 2026-08-26

## Dossiê operacional — padrão Alex's Mobs

> 🔎 **ESCOPO CANÔNICO.** Runtime físico: `forgified-fabric-api-0.116.15+2.3.5+1.21.1.jar`, mod id top-level `fabric_api`, Minecraft 1.21.1 / NeoForge. O branch oficial `Sinytra/ForgifiedFabricAPI:1.21.1` declara Fabric API base version `0.116.15`; a release distribuída adiciona o sufixo Forgified `+2.3.5+1.21.1`. Os módulos internos em `META-INF/jars/` pertencem ao host e **não são entradas top-level** da modlist.

## 1. Identidade, versão e authority
- **Mod:** Forgified Fabric API.
- **JAR físico:** `forgified-fabric-api-0.116.15+2.3.5+1.21.1.jar`.
- **Mod id exposto pelo host:** `fabric_api`.
- **Versão instalada:** `0.116.15+2.3.5+1.21.1`.
- **Minecraft / loader:** 1.21.1 / NeoForge.
- **Source pin:** branch oficial `1.21.1`, cujo `gradle.properties` está em `version=0.116.15` e `minecraft_version=1.21.1`.
- **Release oficial:** a distribuição 1.21.1 `0.116.15+2.3.5+1.21.1` corrige incompatibilidade de default method em `FabricTooltipType`.
- **Authority:** esta biblioteca é uma camada de compatibilidade/port de APIs Fabric para execução em ecossistema NeoForge/Sinytra. Ela não deve virar autoridade de gameplay de mods consumidores; cada consumidor continua responsável pelos próprios registries, estado e regras.

## 2. Papel no modpack
Forgified Fabric API fornece hooks e contratos equivalentes a grande parte do Fabric API para mods portados/compatibilizados no ambiente NeoForge. O escopo confirmado pelo projeto inclui APIs de registries, lifecycle, networking, recipes, tags, rendering, screens, particles, biomes, entity events, transfer, resource loading/conditions e data attachments. Removê-lo quebra consumidores que linkam contra essas APIs mesmo quando o usuário não interage diretamente com a biblioteca.

## 3. Módulos embarcados no JAR físico
A inspeção da modlist física identifica **44 módulos/API jars + o Forgified Fabric Loader interno** sob `META-INF/jars/`. Eles não devem ser catalogados como mods top-level separados:
1. `fabric-api-base` 0.4.42+d1308ded19
2. `fabric-api-lookup-api-v1` 1.6.72+6244b11119
3. `fabric-biome-api-v1` 13.0.31+1e62d33c19
4. `fabric-block-api-v1` 1.1.0+b0c22bb819
5. `fabric-block-view-api-v2` 1.0.11+e9036fd419
6. `fabric-blockrenderlayer-v1` 1.1.52+c290471319
7. `fabric-client-tags-api-v1` 1.2.0+5adff2d719
8. `fabric-command-api-v2` 2.2.28+36d727be19
9. `fabric-content-registries-v0` 8.0.19+5e0d320019
10. `fabric-convention-tags-v1` 2.1.7+7f945d5b19
11. `fabric-convention-tags-v2` 2.12.0+75d3639619
12. `fabric-data-attachment-api-v1` 1.4.7+26d408aa19
13. `fabric-data-generation-api-v1` 20.3.0+3e2bcb4b19
14. `fabric-entity-events-v1` 1.8.0+e46a02ca19
15. `fabric-events-interaction-v0` 0.7.14+057f5afd19
16. `fabric-game-rule-api-v1` 1.0.53+36d727be19
17. `fabric-gametest-api-v1` 2.0.5+29f188ce19
18. `fabric-item-api-v1` 11.3.0+b24c12f719
19. `fabric-item-group-api-v1` 4.1.7+e324903319
20. `fabric-key-binding-api-v1` 1.0.47+62cc7ce119
21. `fabric-lifecycle-events-v1` 2.6.0+e40d8add19
22. `fabric-loot-api-v2` 3.0.15+a3ee712d19
23. `fabric-loot-api-v3` 1.0.3+333dfad919
24. `fabric-message-api-v1` 6.0.14+6a754fce19
25. `fabric-model-loading-api-v1` 2.1.0+6e8f52c719
26. `fabric-networking-api-v1` 4.3.1+7e3d421f19
27. `fabric-object-builder-api-v1` 15.2.1+cc242efd19
28. `fabric-particles-v1` 4.0.2+824f924c19
29. `fabric-recipe-api-v1` 5.0.16+59440bcc19
30. `fabric-registry-sync-v0` 5.3.2+f9aace1619
31. `fabric-renderer-api-v1` 3.4.1+9125b6dc19
32. `fabric-renderer-indigo` 1.7.1+9125b6dc19
33. `fabric-renderer-registries-v1` 3.2.69+df3654b319
34. `fabric-rendering-data-attachment-v1` 0.3.49+73761d2e19
35. `fabric-rendering-fluids-v1` 3.1.6+ddc51b4719
36. `fabric-rendering-v1` 5.1.0+1a09bd5a19
37. `fabric-resource-conditions-api-v1` 4.3.0+5bdd099819
38. `fabric-resource-loader-v0` 1.3.1+4ea8954419
39. `fabric-screen-api-v1` 2.0.26+9991ca2f19
40. `fabric-screen-handler-api-v1` 1.3.91+8dbc56dd19
41. `fabric-sound-api-v1` 1.0.23+10b84f8419
42. `fabric-tag-api-v1` 1.3.0+3e2bcb4b19
43. `fabric-transfer-api-v1` 5.4.4+6244b11119
44. `fabric-transitive-access-wideners-v1` 6.2.0+6c854b6f19
45. **loader interno:** `forgified-fabric-loader-2.5.68+0.18.4+1.21.1-full.jar`.

A lista do branch source inclui também projetos/módulos de build/deprecated que não aparecem necessariamente como jars embarcados no binário físico; a lista acima é a autoridade para o runtime instalado.

## 4. Contratos por subsistema
### Registries e sync
`content-registries`, `registry-sync`, `object-builder` e APIs correlatas fornecem hooks de registro e sincronização esperados por consumidores Fabric. Integradores não devem registrar o mesmo conteúdo novamente no lado NeoForge para “compensar” a bridge sem confirmar necessidade; isso pode causar ids duplicados, lifecycle duplo ou divergência cliente/servidor.

### Lifecycle e eventos
`lifecycle-events`, `entity-events`, `events-interaction`, `game-rule` e `message-api` transportam callbacks semânticos esperados pelo código Fabric. Risco principal: um mod que simultaneamente registre listener Fabric-forgified e listener NeoForge para a mesma operação pode executar a regra duas vezes.

### Networking
`fabric-networking-api-v1` fornece contrato de comunicação a consumidores. O servidor continua autoridade sobre gameplay; packets vindos do cliente devem ser validados pelo mod consumidor. A bridge não autoriza confiar em state client-side.

### Dados, tags, recipes e resources
`data-attachment`, `recipe-api`, `tag-api`, `resource-loader`, `resource-conditions` e convention tags são superfícies de dados/reload. Cache de consumidores precisa ser invalidado conforme o lifecycle da API; não assumir que reload equivale a restart.

### Transfer
`fabric-transfer-api-v1` é particularmente sensível em armazenamento/fluido/item. Integrações devem respeitar simulation/transaction semantics da API consumidora e evitar adaptar o mesmo storage por dois providers concorrentes.

### Rendering e client
Renderer API/Indigo, rendering fluids/data attachment, model loading, block render layer, particles, screen e key binding são predominantemente client-facing. Esses módulos aumentam risco de classloading indevido em dedicated server se um consumidor referenciar classes client sem isolamento adequado.

### World/biome
Biome API e block-view podem afetar hooks de geração/leitura de mundo usados por consumidores. A biblioteca em si não deve ser documentada como geradora de biomas ou conteúdo: ela fornece contratos.

## 5. Conteúdo próprio
Não há evidência de que o host deva ser tratado como content mod de gameplay. O “conteúdo” operacional relevante são APIs, hooks, mixins/bridges e módulos embarcados. Não atribuir itens, mobs, blocos ou progressão à biblioteca sem evidência de um consumidor específico.

## 6. Client / server
- A distribuição oficial é marcada **Client & Server**.
- Há módulos claramente client-side: key bindings, screens, renderer, model loading, particles, client tags e rendering.
- Há módulos comuns/server-relevantes: registries, lifecycle, networking, transfer, tags, recipes e data attachments.
- Dedicated server deve carregar apenas caminhos compatíveis com server; consumidores com imports diretos de classes client são risco de `Dist`/classloading.

## 7. Lifecycle
Validar boot, registry construction/sync, login/relogin, datapack/resource reload, dimension change, disconnect/reconnect e dedicated server. Para APIs com caches ou attachments, testar criação, sincronização, descarte e reconstrução de state. Para rendering/model/resource APIs, testar reload de resources sem vazamento de referências antigas.

## 8. Multiplayer
A biblioteca é infraestrutura compartilhada por múltiplos consumidores. Problemas típicos são packet duplicado, evento executado duas vezes, registry mismatch, attachment não sincronizado, listener residual após reload e diferença de versão entre cliente/servidor. O teste deve focar nos consumidores presentes, não numa gameplay inexistente da API.

## 9. Integrações concretas no pack
- **Sinytra Connector 2.0.0-beta.17+1.21.1** também está fisicamente presente e é parte do mesmo eixo de compatibilidade Fabric→NeoForge, mas é uma entrada top-level separada com responsabilidades próprias.
- **Connector Extras 1.12.1+1.21.1** está presente e fornece bridges adicionais. Não confundir seus módulos internos com FFAPI.
- O catálogo anterior registra **Puddles & Floods** como consumidor confirmado; manter essa relação como dependência/consumo, não como ownership do FFAPI.
- Qualquer outro mod “Fabric” executando via Connector deve ser confirmado individualmente antes de ser listado como dependente direto de Forgified Fabric API.

## 10. Riscos técnicos
- **Version skew:** consumidores podem exigir símbolos de outra revisão do Fabric API/Forgified layer.
- **Double event processing:** registrar lógica simultaneamente por hooks NeoForge e Forgified Fabric.
- **Registry mismatch/sync:** diferenças cliente-servidor ou registro em ordem incorreta.
- **Mixin collision:** ampla superfície de hooks pode colidir com mods que patcham os mesmos alvos.
- **Client classloading no servidor:** renderer/screen/keybind/model APIs usadas incorretamente por consumidor.
- **Transfer adapter duplication:** um mesmo handler exposto por duas camadas pode permitir dupla operação ou semântica divergente.
- **Reload/stale cache:** consumidores que retêm referências após resource/datapack reload.
- **Tooltip compatibility:** a própria 2.3.5 corrigiu incompatibilidade de default method em `FabricTooltipType`, prova de risco real de drift de API.

## 11. Matriz de testes obrigatória
- [ ] Dedicated server boot com o conjunto Connector + Forgified Fabric API.
- [ ] Cliente conecta com exatamente o mesmo conjunto/versões; sem registry mismatch.
- [ ] Login/relogin e dimension change de consumidores com attachments/networking.
- [ ] Resource reload e datapack reload de consumers; sem listeners/cache duplicados.
- [ ] Mods consumidores de screen/model/render APIs funcionam no cliente sem carregar classes client no servidor.
- [ ] Networking de consumidor executa cada ação exatamente uma vez.
- [ ] Transfer API: insert/extract/rollback de consumer sem dupe e sem provider duplicado.
- [ ] Recipes/tags/resource conditions atualizam após reload.
- [ ] Smoke test específico do consumidor que motivou a presença da biblioteca, incluindo Puddles & Floods se continuar presente no runtime.
- [ ] Testar tooltips/caminhos que usam `FabricTooltipType` após update para 2.3.5.

## 12. Evidências e limites
**Evidência canônica:** modlist física atual, incluindo o JAR host e os jars em `META-INF/jars/`.

**Source oficial pinado:** `Sinytra/ForgifiedFabricAPI`, branch `1.21.1`; `gradle.properties` confirma Fabric API `0.116.15`, Minecraft `1.21.1` e as versões-base dos módulos.

**Release oficial:** `0.116.15+2.3.5+1.21.1`, com correção de `FabricTooltipType`.

**Limite:** a existência de uma API na biblioteca não prova que todo consumidor do pack a usa. Integrações são documentadas apenas quando confirmadas.

**Nenhum teste de runtime foi executado nesta catalogação.** A matriz é plano de validação.