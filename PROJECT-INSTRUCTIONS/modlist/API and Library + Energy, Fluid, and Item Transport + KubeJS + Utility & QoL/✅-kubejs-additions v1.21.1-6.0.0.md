# KubeJS Additions

## Propriedades do registro

- **Mod:** KubeJS Additions
- **Arquivo JAR:** kubejsadditions-neoforge-1.21.1-6.0.0.jar
- **Versão 1.21.1:** 1.21.1-6.0.0
- **Categoria:** QoL, Automação, Compat
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/kubejs-additions
- **Função:** Extensão do KubeJS que adiciona APIs/eventos/helpers extras para packdev, com destaque para categorias/recipe types JEI customizados e integração programável com Jade.
- **Dependências:** KubeJS NeoForge 2101.7.2-build.377 está fisicamente presente. JEI/Jade são superfícies de integração documentadas; a presença real de cada consumer deve ser confirmada antes de assumir UI ativa.
- **Compatibilidade/Riscos:** Addon de scripting acoplado a KubeJS. Riscos: scripts incompatíveis após API drift, registration em fase errada, client-only JEI/Jade code em server path, reload parcial e handlers duplicados. Não atribuir gameplay ao addon sem script efetivo que o implemente.
- **Sobreposição:** Complementa KubeJS; pode cruzar com outros addons de scripting/JEI/Jade, mas APIs distintas podem coexistir. O comportamento efetivo depende dos scripts do pack.
- **Observações:** JAR físico `kubejsadditions-neoforge-1.21.1-6.0.0.jar`, mod id `kubejsadditions`, runtime 1.21.1-6.0.0. A superfície histórica de eventos Fabric/Architectury não foi presumida integralmente sem pin de source da build.
- **Procedência:** modlist(1).txt física anexada e reconferida em 25/09/2026 + CurseForge oficial KubeJS Additions 1.21.1-6.0.0 + documentação pública do projeto.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 25/09/2026 — KubeJS Additions 1.21.1-6.0.0/JAR físico reconfirmado; matriz física reconciliada com KubeJS 2101.7.2-build.377. Corpo técnico preservado.
- **Data da última decisão:** 2026-09-06

> **Última autoridade física verificada — snapshot de 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #365: JAR `kubejsadditions-neoforge-1.21.1-6.0.0.jar`, mod id `kubejsadditions`, runtime `1.21.1-6.0.0`, SHA-1 `a7b90a34b8a56d187fbd957d4353fa80f93a3553`.

> **ESCOPO CANÔNICO.** O snapshot físico de 25/09/2026 registra `kubejsadditions-neoforge-1.21.1-6.0.0.jar` (mod ID `kubejsadditions`). KubeJS Additions amplia as APIs de scripting do KubeJS; os efeitos concretos dependem dos scripts consumidores. Não se revalidou o JAR físico nesta etapa.
## 1. Superfície confirmada
A publicação oficial 1.21.1 destaca suporte a **custom JEI categories/recipe types** e **Jade integration**. O projeto também se define como conjunto de events, wrappers e funcionalidades que não estão no KubeJS base.
## 2. Authority
KubeJS Additions é authority apenas das APIs/wrappers que registra. Recipes, tooltips, overlays e regras criadas por scripts pertencem ao script/config do pack e aos providers que eles modificam.
Não registrar no catálogo uma regra custom como feature nativa do addon sem localizar o script correspondente.
## 3. JEI integration
Categorias e recipe types customizados podem representar processos do pack. A representação no JEI não cria a recipe: o backend de recipe/processing continua sendo o registry/script/provider real.
Client-side registration deve permanecer em phase/context correto e não ser referenciado em dedicated-server paths.
## 4. Jade integration
A integração permite customizar informação exibida por Jade. Tooltip/overlay é representação; state de bloco/entity deve ser obtido do servidor/provider. Evitar usar texto renderizado como source of truth para quest/progressão.
## 5. Scripting lifecycle
KubeJS diferencia fases como startup/server/client e reloadable data. Uma API registrada ou chamada na fase errada pode causar missing registry, stale script state ou duplicate handler após reload. Scripts que dependem de KubeJS Additions devem ser testados em cold boot e `/reload`/resource reload conforme aplicável.
## 6. Client/server boundary
Custom JEI/Jade surfaces são predominantemente client-facing. Mutação de recipes, loot ou gameplay deve permanecer server-authoritative por meio do KubeJS/provider apropriado. Não enviar mutation arbitrária a partir de callbacks visuais.
## 7. Riscos
1. **API drift:** scripts quebram após update do addon/KubeJS.
2. **Wrong phase:** registration executada em startup/client/server incorreto.
3. **Duplicate handlers:** reload acumula listeners.
4. **Client leakage:** JEI/Jade class carregada em dedicated server.
5. **False authority:** GUI/tooltip tratado como state causal.
6. **Script dependency:** remover o addon sem procurar usos nos scripts gera falhas silenciosas ou startup errors.
## 8. Boundary para quests/perks
KubeJS Additions não concede Mastery por si. Se scripts criarem milestones, a causalidade e deduplicação pertencem ao script/provider. Uma categoria JEI, tooltip Jade ou callback visual nunca é completion event por si só.
## 9. Matriz de testes
- [ ] Dedicated server inicia com KubeJS Additions 6.0.0 + KubeJS físico atual.
- [ ] Startup scripts não geram missing method/class.
- [ ] Custom JEI categories registram sem duplicação após resource reload.
- [ ] Jade integrations exibem state sem alterar authority server-side.
- [ ] Script errors apontam claramente consumer/API incompatível.
- [ ] `/reload` não duplica handlers nem mantém state stale.
- [ ] Remoção/update só ocorre após busca por usos nos scripts do pack.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 10. Evidências e limitação
- Modlist física: KubeJS Additions 1.21.1-6.0.0 e KubeJS 2101.7.2-build.377 presentes.
- CurseForge oficial: Release NeoForge 1.21.1-6.0.0, custom JEI categories/recipe types e Jade integration.
- Os scripts efetivos do modpack não foram auditados nesta ficha; portanto nenhum uso concreto da API é inferido sem evidência de script.

## 11. Auditoria dos contratos NeoForge 1.21.1 / source 6.0.0 — 14/10/2026

**Proveniência de release.** O [arquivo oficial CurseForge #8290338](https://www.curseforge.com/minecraft/mc-mods/kubejs-additions/files/8290338), publicado em **21/06/2026**, distribui `kubejsadditions-neoforge-1.21.1-6.0.0.jar` para NeoForge / Minecraft 1.21.1 (e 1.21). É a release compatível mais recente localizada na lista oficial para o alvo 1.21.1 nesta verificação. **Não há changelog específico nessa página**, portanto o anúncio não demonstra sozinho a existência de cada método nesta versão. O snapshot histórico do pack (#365, 25/09/2026) registra o mesmo filename, `mod ID=kubejsadditions` e SHA-1 `a7b90a34b8a56d187fbd957d4353fa80f93a3553`; **não foi reaberto nem descompilado o JAR do perfil atual**.

**Código-fonte oficial auditado:** [`Hunter19823/kubejsadditions` em `685ec5bef46202ac518824f52ff895206ed81d21`](https://github.com/Hunter19823/kubejsadditions/tree/685ec5bef46202ac518824f52ff895206ed81d21), branch `1.21.1`. O [`gradle.properties`](https://github.com/Hunter19823/kubejsadditions/blob/685ec5bef46202ac518824f52ff895206ed81d21/gradle.properties) declara `modVersion=6.0.0`, `minecraftVersion=1.21.1`, `modId=kubejsadditions`, Java 21 no [`build.gradle.kts`](https://github.com/Hunter19823/kubejsadditions/blob/685ec5bef46202ac518824f52ff895206ed81d21/build.gradle.kts) e versões usadas na compilação **NeoForge 21.1.31**, **KubeJS 2101.7.1-build.192**, **Architectury 13.0.6**, **JEI 19.14.1.143**, **Jade 15.10.5+neoforge**. A parte `1.21.1-` pertence ao *nome da distribuição*; o source declara `modVersion=6.0.0`. **Sem metadata aberta do JAR, não declarar que a versão interna efetiva é exatamente igual ao texto integral do filename.** Comparando com o inventário histórico do pack (NeoForge 21.1.250, KubeJS 2101.7.2-build.377, Architectury 13.0.11, JEI 19.56.0.440, Jade 15.10.6), há **drift de versões compiladas versus versões catalogadas**, e não um teste de incompatibilidade.

**Divergência de licença a reconciliar.** O `gradle.properties` desta branch declara `modLicense=All Rights Reserved`, enquanto o CurseForge do autor exibe **MIT License**. Essa contradição documental não autoriza presumir um regime jurídico prevalecente, republicar binários ou alterar atribuições; conferir a licença **do artefato distribuído** e os termos do autor antes de redistribuição.

### 11.1. Grupos reais de eventos e semântica

O [`AdditionalEvents.java`](https://github.com/Hunter19823/kubejsadditions/blob/685ec5bef46202ac518824f52ff895206ed81d21/src/main/java/pie/ilikepiefoo/events/AdditionalEvents.java) registra o grupo **`CommonAddedEvents`**:
- **SERVER:** `entityEnterChunk`, `entityTame`, `playerChangeDimension`, `playerClone`, `playerRespawn`. O evento `entityTame` é declarado com `.hasResult()`; seu resultado pode afetar o evento nativo. Não executar efeitos de progressão ou recompensas sem verificar cancelamento, causalidade e deduplicação.
- O [`EventHandler.java`](https://github.com/Hunter19823/kubejsadditions/blob/685ec5bef46202ac518824f52ff895206ed81d21/src/main/java/pie/ilikepiefoo/EventHandler.java) faz bridge de eventos NeoForge para o `ScriptType.SERVER`. O handler de `entityEnterChunk` primeiro exige `didChunkChange()`; **não** interpretar esse hook como evento contínuo por tick. `playerClone` fornece jogador original, clone e marcador de morte; `playerRespawn` fornece o sinal de fim conquistado. Revalidar persistência de perks na transição entre entidades antes de usar esses hooks.

O mesmo `AdditionalEvents.java` registra **`ArchEvents` somente quando `architectury` está carregado**:
- **STARTUP:** `ArchEvents.registry`, chamado em `AdditionsPlugin.initStartup()`;
- **STARTUP/CLIENT/SERVER:** respectivamente `ArchEvents.handleStartup`, `ArchEvents.handleClient` e `ArchEvents.handleServer`, cada qual exigindo target string.
- Em [`ArchEventRegisterEventJS.java`](https://github.com/Hunter19823/kubejsadditions/blob/685ec5bef46202ac518824f52ff895206ed81d21/src/main/java/pie/ilikepiefoo/events/custom/ArchEventRegisterEventJS.java), `register(name, eventProvider, fieldName)` usa reflexão para obter um `dev.architectury.event.Event` e registra um `EventAdapter`. **O registro no barramento externo em startup não é automaticamente desfeito por `/reload`**. Scripts devem usar chaves estáveis, separar criação de registro e processamento e impedir listeners duplicados em restart/reload.

O README público da branch 1.21.1 ainda preserva exemplos históricos de diferentes loaders. **Não transferir o antigo `FabricEvents` para o runtime NeoForge 6.0.0 sem contrato correspondente no source:** `AdditionalEvents.java` desta versão registra `CommonAddedEvents` e o `ArchEvents` condicional, não um grupo Fabric.

### 11.2. JEI, Jade e wrappers — integração condicional, não propriedade intrínseca de gameplay

O [`JEIEvents.java`](https://github.com/Hunter19823/kubejsadditions/blob/685ec5bef46202ac518824f52ff895206ed81d21/src/main/java/pie/ilikepiefoo/compat/jei/JEIEvents.java) só registra **`JEIAddedEvents`** se `jei` estiver carregado. Todos os hooks desse grupo são **CLIENT**: `onRuntimeAvailable`, `registerAdvanced`, `registerCategories`, `registerFluidSubtypes`, `registerGUIHandlers`, `registerIngredients`, `registerItemSubtypes`, `registerRecipeCatalysts`, `registerRecipes`, `registerRecipeTransferHandlers` e `registerVanillaCategoryExtensions`. Categorias/receitas expostas visualmente **não provam** uma recipe de servidor. Não usar esses callbacks como autoridade para consumir materiais, concluir quests ou conceder perks.

O [`JadeEvents.java`](https://github.com/Hunter19823/kubejsadditions/blob/685ec5bef46202ac518824f52ff895206ed81d21/src/main/java/pie/ilikepiefoo/compat/jade/JadeEvents.java) só registra **`JadeEvents`** se `jade` estiver carregado: `onCommonRegistration` em **STARTUP** e `onClientRegistration` em **CLIENT**. Para informação contextual de bloco/entidade, o provider de dados deve ser a autoridade; o tooltip no cliente é somente apresentação.

O [`AdditionsPlugin.java`](https://github.com/Hunter19823/kubejsadditions/blob/685ec5bef46202ac518824f52ff895206ed81d21/src/main/java/pie/ilikepiefoo/AdditionsPlugin.java) confirma `ChunkPos` como wrapper aceitando `ChunkPos`, `BlockPos`, `Long`, `BlockContainerJS` e `Entity`. Para tipo não reconhecido retorna **`ChunkPos.ZERO`**; validar origem/tipo da entrada antes de mover entidades ou associar coordenadas a progressão. Com JEI carregado, há wrappers adicionais para `IDrawable` (**somente CLIENT**) e `RecipeType`. A conversão para `RecipeType` pode retornar `null` enquanto `JEIEventJS.JEI_HELPERS` não estiver disponível: evitar acesso prematuro e tratar inexistência de categoria como condição válida.

**Integrações presentes no último snapshot, não retestadas:** Architectury #039 `13.0.11`; Jade #347 `15.10.6+neoforge`; JEI #349 `19.56.0.440`; KubeJS #363 `2101.7.2-build.377`; KubeJS Additions #365 `1.21.1-6.0.0`. Em [`build.gradle.kts`](https://github.com/Hunter19823/kubejsadditions/blob/685ec5bef46202ac518824f52ff895206ed81d21/build.gradle.kts) a publicação declara KubeJS como **depends** e Architectury, Jade, JEI como **optional**, em consonância com o registro condicional do source. Presença catalogada não significa que scripts do pack consumam as integrações.

### 11.3. Aplicações e QA para perks — PENDENTE

A utilidade de `playerClone`, `playerRespawn` e `playerChangeDimension` em perks é como **sinal de lifecycle** a ser reconciliado com o estado persistente mantido pelo sistema de perks. `entityTame` pode representar uma ação causal apenas quando confirmada no servidor, sem duplas premiações, e respeitando cancelamento. `ArchEvents` pode conectar eventos adicionais, mas **cada evento precisa de contrato de classe/assinatura real da versão dos providers**; jamais deduzir APIs de nomes ou de exemplos 1.20.1. JEI/Jade não substituem hooks de progresso server-side.

**Testes recomendados — nenhum executado nesta auditoria:** (1) client e dedicated server com KubeJS build.377 + Architectury 13.0.11 + Jade 15.10.6 + JEI 19.56.0.440; (2) identificar scripts reais que usam `CommonAddedEvents`, `ArchEvents`, `JEIAddedEvents`, `JadeEvents`; (3) testar `playerClone` em morte versus outras clonagens, relog, respawn e mudanças de dimensão sem double-award; (4) verificar cancelamento e evento de domesticação única; (5) acompanhar chunk changes sem disparo por tick ou falso `ChunkPos.ZERO`; (6) reload de scripts/event registry sem duplicação de adaptadores; (7) startup/headless server sem classloading indevido de JEI/Jade; (8) JEI recipe indexing e Jade tooltip sem mutação de estado; (9) comparar o JAR real com metadata, hashes, licenciamento e dependências declaradas na distribuição.

**Decisão mantida:** `Manter`. Alteração **exclusivamente documental**, sem upgrade, instalação, alteração de modlist/script, teste de runtime ou comprovação binária do JAR. Qualquer efeito concreto de perk exige leitura dos consumers e evidência de execução.
