# Dynamic Trees - Quark

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#229**: JAR `dtquark-2.6.1.jar`, mod id `dtquark`, runtime metadata **vazia**, SHA-1 `8d13799a1893faba90b3364c07ed7c076b41a7be`.

## Propriedades do registro

- **Mod:** Dynamic Trees - Quark
- **Arquivo JAR:** `dtquark-2.6.1.jar`
- **Versão 1.21.1:** `2.6.1`
- **Categoria:** Compat, Worldgen
- **Função:** Bridge Quark ↔ Dynamic Trees: integra espécies, árvores especiais, cogumelos, sementes/loot e worldgen do Quark à representação dinâmica do Dynamic Trees, incluindo cancelamento/substituição de features quando necessário.
- **Dependências:** Runtime físico atual: Dynamic Trees 1.7.2 + Quark 4.1-484. O source oficial 1.21.1 de dtquark 2.6.1 usa como baseline Dynamic Trees 1.6.0 e Quark 4.1-473. Esse drift exige regressão de worldgen, Glow Shroom, loot e seeds; a modlist física permanece autoridade.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Acoplamento entre Quark, Dynamic Trees e o bridge; runtime atual DT 1.7.2 + Quark 4.1-484 difere do baseline do source 1.6.0 + 4.1-473. Riscos de drift em IDs/features de worldgen, biome modifiers, seed/loot replacement e diferenças entre chunks antigos/novos.
- **Fonte:** Modlist física atual + source oficial DynamicTreesTeam/DynamicTrees-Quark, branch 1.21.1.
- **Procedência:** modlist.txt física atual de 21/09/2026 — 587 mods incluindo o modloader — confirma `dtquark-2.6.1.jar`, mod id `dtquark` e campo de versão runtime vazio. CurseForge/source oficiais revalidados em 21/09/2026 confirmam 2.6.1 como latest Release NeoForge 1.21.1; essa versão permanece identificação por filename/source, não metadata física.
- **Observações:** Divergência fail-closed preservada: filename/source/publicação = 2.6.1, mas metadata física não declara versão. Runtime atual das bases permanece Dynamic Trees 1.7.2 + Quark 4.1-484; source baseline 1.6.0 + 4.1-473 segue apenas referência de desenvolvimento.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 21/09/2026 — lote físico #228: dtquark-2.6.1.jar reconfirmado; metadata física continua sem versão. 2.6.1 é sustentado pelo filename e pelo source/publicação oficial, não por campo runtime.
- **Decisão:** Manter
- **Histórico da decisão:** Mantido como bridge necessário enquanto Quark e Dynamic Trees permanecerem ativos. Dossiê técnico reconstruído em 08/09/2026 com autoridade física + source oficial 1.21.1 e regra fail-closed para a versão da metadata.
- **Sobreposição:** Sobreposição intencional: Quark fornece conteúdo/worldgen; Dynamic Trees fornece a representação dinâmica; dtquark traduz/cancela/substitui pontos suportados. Pode conflitar com outros datapacks/mods que editem as mesmas features.
- **Data da última decisão:** 2026-09-08

## 1. Resumo executivo
Dynamic Trees for Quark é o bridge entre Quark e Dynamic Trees no pack. Sua função não é fornecer um sistema arbóreo independente, mas converter/adaptar espécies, árvores especiais, cogumelos e pontos de worldgen do Quark para a infraestrutura dinâmica de crescimento, ramos, folhas, sementes e substituição de features do Dynamic Trees.
> **Autoridade desta ficha:** presença e nome do arquivo vêm da modlist física atual. O JAR é `dtquark-2.6.1.jar`, porém a metadata física disponível não declarou um campo de versão. A identificação `2.6.1` é sustentada pelo nome físico do JAR e pelo source oficial da branch 1.21.1; essa distinção deve ser preservada.
## 2. Identidade técnica
- **Arquivo físico:** `dtquark-2.6.1.jar`
- **Mod ID no source oficial 1.21.1:** `dtquark`
- **Nome:** Dynamic Trees for Quark
- **Versão do source oficial 1.21.1:** `2.6.1`
- **Minecraft:** 1.21.1
- **Loader:** NeoForge
- **Autores declarados no source:** supermassimo, Max Hyper
- **Licença:** MIT
## 3. Papel e autoridade no pack
Este addon ocupa a camada de compatibilidade Quark ↔ Dynamic Trees. Dynamic Trees continua sendo a autoridade sobre crescimento e representação dinâmica; Quark continua sendo a autoridade sobre suas espécies, blocos e biomas. O addon faz a tradução entre esses domínios e, em pontos específicos, cancela/substitui geração estática para impedir coexistência duplicada.
## 4. Dependências e matriz de versão
O source oficial 1.21.1 declara como baseline de build **Quark 4.1-473** e **Dynamic Trees 1.6.0**, sobre NeoForge 21.1.195. A modlist física atual usa **Quark 4.1-484** e **Dynamic Trees 1.7.2**. Portanto há drift real entre o ambiente de desenvolvimento do bridge e o runtime do pack. Esses números do source são referência de desenvolvimento, não autorização para rebaixar ou sobrescrever as versões físicas; a combinação instalada exige regressão de worldgen, Glow Shroom, seeds e loot.
## 5. Conteúdo técnico confirmado no source 1.21.1
O source contém famílias dinâmicas para `ancient`, `azalea`, `blossom` e `glow_shroom`. Também há espécies em `trees/dtquark/species/` para `ancient`, `fiery_blossom`, `frosty_blossom`, `glow_shroom`, `serene_blossom`, `sunny_blossom` e `warm_blossom`, além de uma extensão da espécie `dynamictrees:azalea`.
A camada Java inclui comportamento especializado como `BlossomLeavesProperties`, célula de folhas para Ancient, lógica de crescimento `AncientLogic`, propriedades de copa de Glow Shroom e `GlowShroomSpecies`. Isso confirma que o addon não é apenas um datapack de aliases: há comportamento customizado associado à integração.
## 6. Worldgen e substituição
Há arquivos próprios de `world_gen`, inclusive `default.json` e `feature_cancellers.json`. O source 1.21.1 também contém biome modifiers NeoForge que adicionam o patch dinâmico de Glow Shroom no Glimmering Weald e removem a geração equivalente estática do Quark nesse contexto. Esse mecanismo de cancelamento/substituição é uma área crítica para regressão quando Quark, Dynamic Trees ou o worldgen do pack mudam.
## 7. Loot, sementes e dados
O addon registra tabelas de loot para ramos, folhas, drops voluntários e capas de cogumelo, além de tags do Dynamic Trees para branches, stripped branches, leaves, saplings, seeds e fungus branches/caps. Há ainda um global loot modifier para substituição de Ancient seed e suporte a Ancient Fruit.
## 8. Ciclo de vida
Na inicialização, o mod registra tipos e componentes usados pelo Dynamic Trees e participa da construção dos recursos de árvore. Na carga de datapacks/worldgen, suas definições de famílias, espécies, leaves properties, JO codes, worldgen e modificadores entram na composição final. No jogo, o comportamento observado depende da versão conjunta de Quark, Dynamic Trees e desses dados.
## 9. Cliente e servidor
A lógica de crescimento/worldgen é relevante para o lado servidor e para singleplayer integrado. Assets, modelos e texturas são relevantes ao cliente. Em multiplayer, cliente e servidor devem compartilhar a mesma composição de mods e dados para evitar diferenças de registro, visualização ou geração.
## 10. Multiplayer e determinismo
A geração e substituição de árvores deve ser controlada pelo servidor. Diferenças de versão entre servidor e clientes, ou datapacks divergentes, não são uma configuração suportável para auditoria. Mundos já gerados podem manter árvores antigas em chunks existentes enquanto chunks novos passam a usar regras novas após atualização.
## 11. Integrações e sobreposição
A sobreposição esperada é intencional: Quark define conteúdo arbóreo e biomas; Dynamic Trees redefine sua representação dinâmica; este addon faz a ponte. O risco aparece quando outro mod/datapack tenta cancelar ou substituir as mesmas features, ou quando um resource/worldgen pack altera os alvos esperados pelo bridge.
## 12. Configuração e superfície de dados
A maior superfície operacional está nos recursos `trees/dtquark`, loot tables, tags e biome modifiers. Qualquer customização de worldgen deve preservar os ResourceLocations que o addon usa como alvo, ou ser testada como uma integração nova.
## 13. Riscos operacionais
- **Acoplamento triplo:** Quark, Dynamic Trees e o bridge precisam permanecer compatíveis.
- **Worldgen cancellation:** mudanças de feature IDs podem fazer o cancelamento deixar de atingir o alvo ou remover geração indevidamente.
- **Loot/seed replacement:** alterações upstream podem causar seed/drop duplicado ou ausente.
- **Chunks antigos vs. novos:** atualização não reescreve automaticamente chunks já gerados.
- **Versionamento físico:** a metadata física não declarou versão; não tratar `2.6.1` como valor lido da metadata.
## 14. Matriz mínima de testes
1. Inicializar o pack sem erro de dependência/registro.
2. Criar mundo novo e confirmar árvores Quark suportadas em forma dinâmica.
3. Validar Glimmering Weald para ausência de duplicação entre Glow Shroom estático e dinâmico.
4. Plantar sementes/mudas suportadas e acompanhar crescimento completo.
5. Cortar árvores e verificar drops, sementes e loot.
6. Testar reload de datapack e reinício do mundo.
7. Comparar chunk antigo e chunk novo após atualização de Quark/Dynamic Trees/addon.
8. Em multiplayer, validar conexão e mesma geração no servidor.
## 15. Evidência e limitações
A modlist física atual é a autoridade para presença e filename. O source oficial da branch 1.21.1 sustenta mod ID, versão publicada 2.6.1, baseline de build e o conteúdo técnico descrito acima. **Limitação explícita:** o campo de versão da metadata física disponível estava vazio; portanto a ficha registra `2.6.1` como versão identificada pelo filename/source, não como valor extraído da metadata runtime.
## 16. Conclusão operacional
**Manter** enquanto Quark e Dynamic Trees permanecerem pilares ativos do pack. Qualquer atualização de um dos dois deve disparar regressão de worldgen, sementes/loot, Glow Shroom e árvores especiais antes de considerar a atualização validada.
