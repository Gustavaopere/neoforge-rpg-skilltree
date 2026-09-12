# Chipped

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db816582e5d657d24d1e2f  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: `modlist.txt`, 595 mods top-level  
> Exportado em: 2026-09-09

## Propriedades do registro

- **Mod:** Chipped
- **Arquivo JAR:** `chipped-neoforge-1.21.1-4.0.2.jar`
- **Versão 1.21.1:** `4.0.2`
- **Categoria:** Visual
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/chipped
- **Função:** Mod decorativo de grande escala que adiciona mais de 10.000 variantes de blocos de construção e as produz por workbenches próprios, com integração de recipe viewers na linha 4.0.2.
- **Dependências:** A build NeoForge 4.0.2 é Client & Server. Dependências técnicas devem ser determinadas pela metadata/JAR do artefato e não por versões antigas; nenhuma hard dependency adicional foi inferida nesta ficha.
- **Compatibilidade/Riscos:** Grande superfície de registries/assets/recipes. Riscos em tags de blocos, connected/translucent rendering, recipe viewers, UI de workbench e compat com mods que adicionam variantes do mesmo material. A 4.0.2 corrige crafting e adiciona integração JEI/agrupamento REI; issues atuais ainda mostram edge cases de render/tags e interação com Create.
- **Sobreposição:** Sobreposição estética ampla com outros mods decorativos, mas não equivalência automática. Conflito só deve ser afirmado quando houver registry/tag/recipe/resource collision concreta.
- **Observações:** mod id `chipped`; runtime 4.0.2. Projeto oficial declara 10.000+ building blocks. A release 4.0.2 corrige bug de crafting, adiciona feature JEI e agrupamento automático REI.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Chipped 4.0.2 + source oficial terrarium-earth/Chipped branch 1.21.x + changelog/issues oficiais usados como regression evidence.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Chipped 4.0.2 foi reconfirmado no JAR físico e reconstruído ao padrão técnico. A instalação atual não foi convertida automaticamente em decisão de manter/remover.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — Chipped 4.0.2 físico confirmado; 10.000+ decorative scope, workbench/recipe-viewer pipeline, rendering/tags, client/server lifecycle e regressões 4.0.2 confirmados no QC global #92. Runtime QA não executado.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `chipped-neoforge-1.21.1-4.0.2.jar`, mod id `chipped`, runtime `4.0.2`, NeoForge 1.21.1. O projeto oficial descreve **10.000+ building blocks** e a 4.0.2 corrige crafting, adiciona feature JEI e agrupamento automático REI.

## 1. Papel e authority

Chipped é um provider de **variantes decorativas**. Ele registra seus próprios blocos, assets e recipes/workbench interactions; o bloco-base usado como matéria-prima continua pertencendo ao provider original.

Não inferir que uma variante Chipped herda automaticamente todas as propriedades funcionais de outro mod apenas por copiar madeira, pedra, vidro ou metal visualmente semelhante.

## 2. Escopo de conteúdo

O source oficial resume o projeto como mais de **10.000 blocos de construção**. O volume é parte essencial do risco operacional: registry, tags, loot/drop, models, textures, recipes e recipe-viewer indexing precisam permanecer consistentes em conjunto.

A ficha não congela uma contagem exata por família porque o projeto não publica inventário 4.0.2 completo e estático na página de release.

## 3. Workbenches

Chipped usa workbenches próprios para converter blocos elegíveis em variantes. A linha histórica inclui mesas temáticas como Carpenter, Mason, Glassblower, Botanist, Shepherd, Scholar e Tinkerer; issues da própria 4.0.2 confirmam que a UI de `WorkbenchScreen` continua sendo a superfície de escolha de variantes.

O output deve ser liquidado uma vez por operação; compat externa não deve duplicar a conversão por adicionar recipes paralelas sob os mesmos inputs sem decisão explícita.

## 4. Recipe viewers — 4.0.2

O changelog 4.0.2 confirma:

- fix de bug de crafting;
- nova feature JEI;
- agrupamento automático REI.

Essas integrações são apresentação/descoberta de recipes; JEI/REI não são authority do craft. O recipe manager/provider continua decidindo validade e output.

## 5. Tags e equivalência de materiais

Com milhares de variantes, tags são críticas para recipes, tools e integração de materiais. Não inserir automaticamente todas as variantes em tags funcionais do bloco-base sem verificar a tag real do JAR/datapack.

Issues oficiais da 4.0.2 registram, por exemplo, casos de glass blocks fora de tags esperadas e iron bars com drop incorreto; são regression gates, não prova de que toda família esteja quebrada.

## 6. Rendering e assets

Blocos decorativos incluem superfícies com requisitos de translucência, culling, orientação e connected appearance. Issues da linha 4.0.2 mostram edge cases em ice/translucency e alguns render crashes.

Render é client-side; hardness, drops, collision e demais propriedades de gameplay continuam state do bloco no servidor/common.

## 7. UI e compatibilidade de tela

A UI dos workbenches mostra grades grandes de variantes. Mods que transformam screens podem interceptar essa mesma superfície; há issue oficial da 4.0.2 sobre elementos invisíveis ao usar Flow.

QA deve validar UI scale, search/scroll, recipe viewers e mods que alteram screens antes de atribuir falha ao crafting server-side.

## 8. Integração com Create e outros providers

Uma issue recente da 4.0.2 relata crash envolvendo Chipped + Create durante advancement/bootstrap por referência a `create:chocolate_bucket`. Isso é evidência de regression risk no stack, não autorização para afirmar incompatibilidade universal.

No pack, testar startup com Create físico atual e preservar o stack trace original caso haja falha de registry/advancement.

## 9. Client/server e multiplayer

Registry, recipes, block placement/break, drops e inventories de workbench são common/server-authoritative. Models, textures, CTM/translucency e screens são client presentation.

Em multiplayer, seleção de variante no cliente precisa resultar em craft/transfer validado uma única vez pelo servidor.

## 10. Lifecycle

Validar bootstrap de registries, recipe/datapack reload, resource reload, language change, world join, chunk load de construções massivas e server restart. Remover/atualizar o mod em mundo existente é sensível porque milhares de block IDs podem estar persistidos em chunks.

## 11. Riscos

1. Missing block/tag após update.
2. Recipe viewer mostrar rota que o servidor não aceita.
3. Craft output duplicado por scripts externos.
4. Render/translucency/culling regressions.
5. Drop/loot incorreto em variantes específicas.
6. UI transform mod ocultar a grade de variantes.
7. Registry/advancement reference quebrar bootstrap com outro mod.
8. Remoção do mod causar missing blocks em mundo existente.

## 12. Matriz de testes

1. Dedicated server boot com a modlist completa.
2. Abrir cada família de workbench disponível e converter blocos.
3. JEI/REI: recipe display vs craft real exactly once.
4. Datapack reload e resource reload.
5. Vidro/gelo: translucência, culling e tags.
6. Barras/blocos selecionados: drops corretos.
7. Create presente: bootstrap/advancements sem crash.
8. Construção grande com muitas variantes após save/restart.
9. Multiplayer: dois usuários no mesmo workbench sem dupe.
10. Atualização somente em cópia de mundo, verificando missing mappings/blocks.

## 13. Evidência

- modlist física atual: Chipped 4.0.2 NeoForge;
- CurseForge oficial 4.0.2: crafting fix, JEI feature e REI grouping;
- source oficial: projeto com 10.000+ building blocks;
- issues oficiais 4.0.2 usadas apenas como regression evidence para rendering, tags, UI e integração Create.

> 🧱 Boundary canônico: Chipped é um **provider decorativo massivo**. Similaridade visual não transfere automaticamente propriedades, tags ou ownership do bloco-base para a variante.
