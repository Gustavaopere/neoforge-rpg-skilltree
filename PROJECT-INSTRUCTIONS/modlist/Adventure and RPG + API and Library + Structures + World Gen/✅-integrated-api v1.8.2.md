# Integrated API

## Propriedades do registro

- **Mod:** Integrated API
- **Arquivo JAR:** `integrated_api-neoforge-1.21.1-1.8.2.jar`
- **Versão 1.21.1:** `1.8.2`
- **Categoria:** `Adventure and RPG + API and Library + Structures + World Gen` — conjunto completo das categorias temáticas oficiais do [projeto CurseForge 817709](https://www.curseforge.com/minecraft/mc-mods/integrated-api); não confundir com uma descrição funcional abreviada.
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/integrated-api/files/8636576
- **Função:** Biblioteca/worldgen API da série Integrated Structures, fornecendo abstrações e utilitários de estruturas, jigsaw/terrain adaptation, conditional block replacement, cartographer maps e opções data-driven compartilhadas.
- **Dependências:** NeoForge 1.21.1. Consumers físicos confirmados no pack incluem IDAS 1.13.7, Integrated Dungeons Arise 2.1.1, Integrated Mowzie's Mobs 1.3.0, Integrated Cataclysm 1.0.6, Integrated Stronghold 1.1.4 e Integrated Villages 1.3.3. Integrated Patches continua ausente fisicamente.
- **Compatibilidade/Riscos:** Infraestrutura central do cluster Integrated. Riscos: ABI/data drift entre consumers, jigsaw/terrain-placement regressions, processor/schema drift, conditional replacement com mod ausente, shared worldgen bugs e atualização unilateral. 1.8.2 adiciona ceiling structure type; upstream 1.9.0 adiciona Trial Spawner processor.
- **Sobreposição:** Não é mod de estruturas independente; centraliza infraestrutura usada por vários Integrated. Não duplicar jigsaw/terrain/data logic nos consumers sem necessidade, e não atribuir automaticamente a um consumer bugs que pertencem à API compartilhada.
- **Observações:** Runtime físico permanece 1.8.2. A release 1.9.0 para NeoForge 1.21.1, publicada em 21/09/2026, adiciona Trial Spawner processor; Integrated Patches segue não instalado.
- **Procedência:** modlist física atual + Integrated API 1.8.2 instalada + CurseForge oficial 1.9.0 (file ID 8943419) para NeoForge 1.21.1 + documentação oficial já auditada do ecossistema Integrated. Revalidado em 01/10/2026.
- **Atualização/Status:** PENDENTE — runtime físico permanece Integrated API `1.8.2`; releases NeoForge 1.21.1 `1.9.0` (Trial Spawner processor, 21/09/2026), `1.9.1` (correções de trades/mapas e logspam, 12/10/2026) e `1.9.2` (11/11/2026, sem changelog específico) foram identificadas. A evolução cumulativa do source até `1.9.2` foi registrada abaixo, mas o delta binário exclusivo de `1.9.2` ainda não foi certificado; nenhuma versão nova foi instalada ou testada.
- **Data da última decisão:** 2026-08-26

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #332: JAR `integrated_api-neoforge-1.21.1-1.8.2.jar`, mod id `integrated_api`, runtime `1.8.2`, SHA-1 `e0a0e44235b98f21a05ebbe0171fe8487319a173`.

<callout icon="🏗️" color="green_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `integrated_api-neoforge-1.21.1-1.8.2.jar`, mod id `integrated_api`, versão `1.8.2`. A distribuição física corresponde à release oficial NeoForge 1.21.1. A ficha é release-pinned para comportamento version-sensitive.
</callout>
## 1. Papel e authority
Integrated API é a biblioteca de infraestrutura da série Integrated Structures. Ela não é authority dos catálogos de estruturas dos consumers; fornece contratos comuns para worldgen, jigsaw, terrain adaptation, configuração e integrações reutilizáveis.
## 2. Consumers físicos relevantes
O pack atual contém vários consumers da família: IDAS 1.13.7, Integrated Dungeons Arise 2.1.1, Integrated Mowzie's Mobs 1.3.0, Integrated Cataclysm 1.0.6, Integrated Stronghold 1.1.4 e Integrated Villages 1.3.3. Uma atualização da API deve ser tratada como alteração transversal a todos eles.
## 3. Create contraption compatibility
A documentação oficial inclui utilidades para compatibilidade com contraptions do Create. Isso não torna a API owner da física/Create state; ela apenas fornece integração compartilhada. Consumers devem preservar block/entity ownership do provider original.
## 4. Conditional block replacement
A API suporta substituir blocos condicionalmente quando outro mod está instalado. Esse mecanismo deve degradar de forma segura quando o provider opcional estiver ausente e não pode produzir registry references inválidas durante data load.
## 5. Cartographer map trades
O projeto oferece suporte facilitado a mapas de cartógrafo associados a estruturas. Map/trade generation é uma ponte de descoberta; quests externas não devem tratar a obtenção do mapa e a descoberta da estrutura como o mesmo settlement sem regra explícita.
## 6. Limite expandido de estrutura
**Retificação do limite de Structure Block (source oficial 1.21.1):** a afirmação anterior de limite dimensional `256` estava incorreta. O código `common-1.21.1` do commit upstream `455dbee` define `IntegratedAPI.NEW_STRUCTURE_SIZE = 512`; os mixins `StructureBlockUnlimit` e `UpdateStructureBlockUnlimit` utilizam esse valor nos limites de leitura/NBT e de pacote. Já `StructureBlockScreenMixin` aplica `256` ao **comprimento máximo do nome da estrutura**, não às dimensões do template. O README do mesmo commit, anterior à publicação 1.8.2, distingue expressamente **tamanho 512** e **nome 256**. Isso é evidência do **source** e não um teste do JAR físico 1.8.2: confirmar os mixins no binário instalado e o comportamento in-game antes de assumir o teto efetivo, pois alguns pontos de injeção usam `require=0`. Templates grandes aumentam risco de custo, limites de chunks e armazenamento; não inferir que todos os consumers usem o máximo.

**Fontes primárias (source pinado):** [constante de tamanho](https://github.com/craisinlord/IntegratedAPI/blob/455dbee0d3fd15726dc475cff50df2e54b0aaeb8/common-1.21.1/src/main/java/com/craisinlord/integrated_api/IntegratedAPI.java), [mixins de Structure Block](https://github.com/craisinlord/IntegratedAPI/blob/455dbee0d3fd15726dc475cff50df2e54b0aaeb8/common-1.21.1/src/main/java/com/craisinlord/integrated_api/mixins/blocks/StructureBlockUnlimit.java), [mixins do pacote](https://github.com/craisinlord/IntegratedAPI/blob/455dbee0d3fd15726dc475cff50df2e54b0aaeb8/common-1.21.1/src/main/java/com/craisinlord/integrated_api/mixins/blocks/UpdateStructureBlockUnlimit.java), [limite do nome](https://github.com/craisinlord/IntegratedAPI/blob/455dbee0d3fd15726dc475cff50df2e54b0aaeb8/common-1.21.1/src/main/java/com/craisinlord/integrated_api/mixins/blocks/StructureBlockScreenMixin.java) e [README pinado](https://github.com/craisinlord/IntegratedAPI/blob/455dbee0d3fd15726dc475cff50df2e54b0aaeb8/README.md). **Status da auditoria 1.8.2→1.9.2 permanece PENDENTE**, sem atribuição artificial de mudanças aos JARs intermediários.
## 7. Jigsaw manager
A API fornece um jigsaw manager próprio/melhorado para a série. O placement resultante deve permanecer deterministicamente compatível com registries e data da versão instalada. Outros worldgen mods não devem tentar reprocessar o mesmo jigsaw output.
## 8. Structure JSON e configuração
O upstream expõe opções adicionais em structure JSONs. Isso torna datapacks/config uma superfície de integração de primeira classe: codecs, defaults, tags, spacing/separation e parâmetros de terrain fit precisam ser validados por release e por seed.
## 9. Terrain adaptation — 1.8.0 → 1.8.2
A 1.8.0 adicionou **upside-down structure terrain adaptation**, capability preservada na linha atual.
A **1.8.2**, instalada atualmente, adiciona **ceiling structure type**. Como a documentação pública não fornece nesta auditoria um schema completo por consumer, a ficha não inventa chaves JSON nem atribui uso concreto a estruturas específicas sem evidência. Ambos os recursos são tratados como capabilities da API e regression gates de worldgen.
## 10. Boundary de bugs compartilhados
Consumers podem manifestar defeitos cuja causa real está na API. O caso documentado de geração estranha de Umvuthana Grove em Integrated Mowzie's Mobs é atribuído pelo upstream ao Integrated API. Diagnóstico deve separar template consumer, API, biome/terrain provider e seed.
Em 10/09/2026 foi publicado **Integrated Patches 1.0.0** para NeoForge 1.21.1, descrito como correção de problemas de worldgen do Integrated API via ajuste do `structure_type` genérico para melhor adaptação ao terreno. O changelog de Integrated Mowzie's Mobs 1.3.0 passa a declarar esse patch como dependência. A modlist física atual não contém Integrated Patches top-level; por isso esta ficha registra o patch como **companion corretivo externo relacionado**, não como dependência própria do Integrated API nem como feature embutida na 1.8.0.
## 11. Client / server
A distribuição é Client & Server. Worldgen, structure placement, trades e state persistente são server-authoritative. Assets/menus/visualização podem exigir cliente, mas o cliente não decide placement ou loot.
## 12. Lifecycle e persistência
Validar datapack load, registry setup, `/reload`, world creation, geração de chunks novos, server restart e update conjunto API+consumers. Mudanças de JSON/jigsaw não reescrevem automaticamente estruturas já persistidas; mundos podem ficar híbridos entre versões.
## 13. Riscos técnicos
- consumer compilado contra ABI diferente;
- source/branch de desenvolvimento ser confundido com a release física 1.8.2;
- conditional replacement criar missing registry;
- jigsaw/terrain adaptation quebrar placement;
- structure JSON inválido ou schema drift;
- mapa/trade apontar para estrutura impossível de gerar;
- shared bug ser corrigido no consumer errado;
- assumir que Integrated Patches está presente/embutido quando a modlist física atual não o contém;
- update unilateral da API quebrar vários mods simultaneamente;
- estruturas grandes elevarem custo de geração.
## 14. Matriz de testes obrigatória
- [ ] Dedicated server boot com API 1.8.2 e todos os consumers físicos.
- [ ] Datapacks/structure JSONs carregam sem codec/registry errors.
- [ ] Consumer sem integração opcional disponível degrada sem hard-fail.
- [ ] Cartographer maps/trades apontam para estruturas válidas quando usados.
- [ ] Jigsaw structures geram sem peças órfãs/duplicadas.
- [ ] Terrain adaptation padrão, upside-down e ceiling structure type não corrompem terreno.
- [ ] `/reload` não quebra registries/config data.
- [ ] Restart preserva estruturas já geradas.
- [ ] Update futuro da API é bloqueado até regression test dos consumers.
## 15. Evidências e limites
- **Modlist física de 17/09/2026:** `integrated_api-neoforge-1.21.1-1.8.2.jar`, mod id `integrated_api`, versão 1.8.2.
- **Release oficial 1.8.2:** adiciona ceiling structure type.
- **Histórico 1.8.0:** upside-down terrain adaptation permanece capability herdada da linha.
- **Documentação oficial:** contraption compat, conditional replacement, cartographer maps, limite dimensional `512` e comprimento máximo do nome da estrutura `256` demonstrados no source `common-1.21.1` (sem teste do JAR físico), jigsaw manager e opções JSON.
- **Integrated Patches:** continua ausente da modlist física atual e não é tratado como feature embutida/dependência própria da API.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.

## 16. Atualização upstream 1.9.0 — não instalada

A autoridade física continua em **Integrated API 1.8.2**. A release **1.9.0** para NeoForge 1.21.1 foi publicada em 21/09/2026.

### Delta oficial
- adiciona **Trial Spawner processor**.

O changelog público da 1.9.0 não enumera outros deltas. Por isso esta ficha não atribui mudanças adicionais de API, terrain adaptation ou jigsaw sem evidência.

**Contrato do novo processor, confirmado no source `common-1.21.1` do commit `803ae70` da 1.9.0:** o ID de processor para a lista JSON de um template é `integrated_api:trial_spawner_processor`. O codec exige `loot_table` (recompensa normal) e `integrated_api_spawner_resourcelocation` (pool data-driven de mobs); `ominous_loot_table`, `normal_config`, `ominous_config`, `valid_block_light_level`, `valid_sky_light_level`, `target_cooldown_length` (inteiro não negativo) e `required_player_range` (1–128) são opcionais. `replace_vanilla_spawners` é `false` por padrão; sem ele, o processor atua em **trial spawners existentes no template**, não converte indiscriminadamente spawners normais. Quando a conversão está habilitada, um spawner vanilla do template pode virar trial spawner. A recompensa ominosa usa `ominous_loot_table`, se fornecida, ou a tabela normal como fallback. Campos `normal_config`/`ominous_config` permitem configurar parâmetros de geração separadamente; não atribuir esse comportamento à versão instalada 1.8.2.

**Evidência primária versionada:** [codec e condição de conversão, `TrialSpawnerProcessor.java` no commit `803ae70`](https://github.com/craisinlord/IntegratedAPI/blob/803ae70cc0879910fec29834381769f12f885490/common-1.21.1/src/main/java/com/craisinlord/integrated_api/world/processors/TrialSpawnerProcessor.java) e [exemplo JSON do README da mesma revisão](https://github.com/craisinlord/IntegratedAPI/blob/803ae70cc0879910fec29834381769f12f885490/README.md). São evidências do **source da linha 1.9.0**, não prova de configuração aplicada aos consumers físicos atuais nem de QA do JAR.

### Impacto para o pack
Processors de estrutura pertencem ao caminho server/worldgen e podem ser consumidos pelos mods Integrated. A promoção da API deve ser coordenada com consumers porque schema/processor IDs e templates data-driven podem criar erro de codec/registry ou alteração de conteúdo gerado.

### Gate de promoção 1.8.2 → 1.9.0
- [ ] Dedicated server inicia com todos os consumers Integrated físicos.
- [ ] Datapacks/structure JSONs carregam sem missing processor/codec/registry errors.
- [ ] Trial Spawner processor, quando usado por consumer, gera state/blocks esperados uma única vez.
- [ ] Jigsaw, upside-down adaptation e ceiling structure type permanecem funcionais.
- [ ] `/reload` não deixa processor/template cache stale.
- [ ] Mundo existente continua carregando; chunks novos são comparados com baseline 1.8.2.
- [ ] Integrated Patches continua tratado separadamente enquanto ausente do pack.

Fonte upstream: CurseForge Integrated API 1.9.0, file ID 8943419. Nenhum teste acima foi executado nesta atualização documental.

## Atualização de publicação e gates — 13/10/2026

**Mod:** Integrated API. **Retificação do registro histórico de 13/10/2026:** a afirmação de que `1.9.0` era a última release NeoForge para Minecraft `1.21.1` já estava incorreta nessa data: a [1.9.1](https://www.curseforge.com/minecraft/mc-mods/integrated-api/files/9151098) havia sido publicada em **12/10/2026**. Posteriormente, em **11/11/2026**, foi publicada a [1.9.2](https://www.curseforge.com/minecraft/mc-mods/integrated-api/files/9552190). A instalação física histórica permanece `1.8.2`; ver a auditoria incremental posterior para deltas, limites de atribuição e pendências. [Catálogo oficial](https://www.curseforge.com/minecraft/mc-mods/integrated-api/files/all).

Release **`integrated_api-neoforge-1.21.1-1.9.0.jar`**, 21/09/2026, disponível para NeoForge 1.21.1. O snapshot histórico permanece **1.8.2**; não confundir builds 26.x também 1.9.0. Biblioteca dos mods **Integrated Structures**: nenhuma estrutura nova deve ser atribuída à biblioteca automaticamente. O changelog **1.9.0** informa o novo **Trial Spawner processor** (seção 16), mas não comprova outras modificações específicas; tratar a mudança de processor/codec/registry como superfície de risco de worldgen, validando consumidores Integrated Structures, bootstrap de datapacks, structure template/loot/biome tags, startup dedicated server, mundo salvo e seed fixa em chunks novos.

**Autoridade física:** JAR/metadata e versão efetivamente instalados permanecem os do snapshot histórico `modlist(1).txt`; não há novo dump. Preservar o prefixo ✅ e a versão física no filename do `.md`; não substituir o arquivo com uma versão candidata. **Estado:** reauditoria documental; QA Minecraft (client/server/mundo) não executado nesta ação.

## Auditoria incremental parcial — 20/11/2026 — Integrated API 1.9.1–1.9.2

**Instalação física continua:** `integrated_api-neoforge-1.21.1-1.8.2.jar` (posição #332, versão 1.8.2) — não promover nem renomear o dossier. O [catálogo CurseForge do projeto 817709](https://www.curseforge.com/minecraft/mc-mods/integrated-api/files/all) registra as publicações posteriores para **NeoForge 1.21.1**: **1.9.0** em 21/09/2026 (ID 8943419, processador Trial Spawner já descrito na seção 16), **1.9.1** em 12/10/2026 ([ID 9151098](https://www.curseforge.com/minecraft/mc-mods/integrated-api/files/9151098)), e **1.9.2** em 11/11/2026 ([ID 9552190](https://www.curseforge.com/minecraft/mc-mods/integrated-api/files/9552190)). Não confundir arquivos 26.1/26.2/26.3, Forge e Fabric com a instância 1.21.1. As categorias oficiais permanecem **Adventure and RPG + API and Library + Structures + World Gen**, compatíveis com a pasta existente.

### Mudanças publicamente atribuíveis

- **1.9.0, 21/09:** adiciona `integrated_api:trial_spawner_processor`, documentado anteriormente; o [source oficial 803ae70](https://github.com/craisinlord/IntegratedAPI/commit/803ae70cc0) registra esse recurso e seu codec/registro para 1.21.1, com controle de spawner e tabela normal/ominous. O consumidor precisa referenciar a configuração explicitamente; não presumir alteração automática de templated structures existentes.
- **1.9.1, 12/10:** o [changelog da publicação NeoForge](https://www.curseforge.com/minecraft/mc-mods/integrated-api/files/9151098) lista: (i) integração/port para Minecraft 26.3 — **não aplicável ao alvo 1.21.1**; (ii) corrigir mapas de estrutura do **wandering trader** incluídos no pool `rare` em vez do grupo normal; (iii) corrigir crash ao carregar **dois arquivos de trades para a mesma profissão**; (iv) diminuir logspam emitido por `biome_facing_structure`. Os fixes de mapas/trades e logs podem afetar IDAS e consumidores com múltiplos datapacks, sem introduzir mecânicas novas de gameplay.
- **1.9.2, 11/11:** o [arquivo oficial NeoForge 1.21.1](https://www.curseforge.com/minecraft/mc-mods/integrated-api/files/9552190) está listado como Release, mas a página de arquivo consultada não oferece changelog específico recuperável. **Não inventar um changelog exclusivo da 1.9.2.** O [source publicado no commit `8b7020c`, intitulado `1.9.2`](https://github.com/craisinlord/IntegratedAPI/commit/8b7020c3bc), permite confrontar alterações técnicas acumuladas da linha 1.9.0→1.9.2, resumidas abaixo; ele não permite, sozinho, pin binário exato das versões .1 e .2.

### Delta acumulado do source 1.9.0 → 1.9.2 (não atribuível integralmente a um JAR intermediário)

A [comparação primária `803ae70...8b7020c`](https://github.com/craisinlord/IntegratedAPI/compare/803ae70cc0...8b7020c3bc) contém **um único commit público 1.9.2** no repositório, mas mudanças relevantes na pasta **`common-1.21.1/`**:

1. **Worldgen/estrutura:** adiciona `override_piece_terrain_adaptation` (padrão `false`) em `JigsawStructure`, `CeilingStructure`, `BiomeFacingStructure`, `ModAdaptiveStructure`, `OptionalDependencyStructure`, `NetherJigsawStructure` e `OverLavaNetherStructure`. No source 1.21.1 do commit `8b7020c`, quando `override_piece_terrain_adaptation=true`, a estrutura aplica seu próprio `enhanced_terrain_adaptation` **a todas as peças**, ignorando os valores específicos de adaptação dos elementos/peças; o codec assume `false` na ausência do campo. Portanto, a opção pode alterar a conformação do terreno em **chunks recém-gerados** para estruturas que a habilitem; não presumir que ela esteja ativada por padrão ou que reescreva chunks já salvos. [README 1.9.2, seção de opções de estrutura](https://github.com/craisinlord/IntegratedAPI/blob/8b7020c3bc145359ea61a91080d77f3e234f518a/README.md) e [codec `JigsawStructure.java`, source `common-1.21.1`](https://github.com/craisinlord/IntegratedAPI/blob/8b7020c3bc145359ea61a91080d77f3e234f518a/common-1.21.1/src/main/java/com/craisinlord/integrated_api/world/structures/JigsawStructure.java). A migração de schemas/JSON e o comportamento real exigem testes por seed; esta é uma propriedade do source acumulado até 1.9.2, **sem prova de fronteira binária individual 1.9.1/1.9.2**.
2. **Mapas de estruturas:** `StructureMapManager` acumula trades para a mesma profissão/tipo com `LinkedHashMap` e `computeIfAbsent` em vez de entradas imutáveis duplicadas que geravam exceção; `StructureMapTradesEvents` põe wandering-trader `COMMON` em `addBasicTrade`, não em `addRareTrade`. Esses dois diffs corroboram os dois fixes do changelog 1.9.1, mas **não** definem a fronteira exata entre os binários 1.9.1/1.9.2.
3. **Spawner/processor:** campos de limite de luminosidade corrigem a semântica de NBT `max_exclusive → max_inclusive` em `SpawnerRandomizingProcessor` e `TrialSpawnerProcessor`; `WorkstationProcessor` corrige caminho mostrado no log para `integrated_workstations`, sem afirmar mudança do registro real de datapacks.
4. **Placement e segurança matemática:** distância mínima de origem em `AdvancedRandomSpread` e cálculo de raio de stronghold em `StrongholdPlacement` passam a utilizar `long`, mitigando overflow de multiplicações `int` em coordenadas extremas. `PieceLimitedJigsawManager` e `EnhancedBeardifierHelper` também foram alterados no diff acumulado para a gestão de peças e adaptação de terreno. Não inferir comportamento em mundo existente sem comparação de seed/chunk.

**Distinção de source e release:** o repositório público mostra commits de 1.9.0 (21/09) e 1.9.2 (11/11), **sem pin separável da 1.9.1** nesse histórico. Portanto, os itens 1–4 acima são comprovados como evolução acumulada até o source da 1.9.2, mas não se pode atribuí-los exclusivamente ao JAR 1.9.2. Tampouco foi realizada comparação binária de .0/.1/.2.

**Gates de promoção:** exigir backup/mundo clone, compatibilidade com Integrated Patches ausente no inventário histórico e consumers IDAS, Integrated Dungeons Arise, Integrated Stronghold, Integrated Villages, Integrated Mowzie's Mobs e Integrated Cataclysm; testes de structure JSON/datapacks, duplicate profession trade files, trial spawners, wandering trader, ceiling/terrain adaptation, chunk regeneration versus mundo salvo, registry codecs e dedicated server. Nenhum teste foi realizado.

**Status final: PENDENTE.** A versão-alvo 1.9.2, os dois releases intermediários e o delta source foram identificados e registrados com evidência. **Falta** comprovação de quais mudanças da linha 1.9.0→1.9.2 pertencem ao binário 1.9.1 e quais à 1.9.2 (ou changelog primário completo desta), além de validações de QA. Não marcar auditoria integral como concluída nem liberar este item para remoção do bloco de notas.

## Revalidação cruzada da lacuna 1.9.2 — 21/11/2026

O [registro primário **Modrinth**, arquivo NeoForge **1.9.2**, ID `W2kSUAja`](https://api.modrinth.com/v2/version/W2kSUAja), apresenta **Minecraft 1.21.1**, publicação **11/11/2026 02:48 UTC**, arquivo `integrated_api-neoforge-1.21.1-1.9.2.jar`, tamanho declarado **486.108 bytes**, SHA-512 `d9133213ef19fae4852f270ec7e144a6c683c581c24746e693b8c88a4229d3b7e471f280bd7a358f85f2cc2b545321297bf8301b2166a40b2514cd5bbbdad3dd` — mas, crucialmente, o **campo oficial `changelog` é vazio**. Isso confirma, em **segunda plataforma primária da distribuição**, a lacuna de notas da release .2, não a resolve.

A [**1.9.1** para NeoForge 1.21.1, ID `oddRiz2I`](https://api.modrinth.com/v2/version/oddRiz2I), **12/10/2026**, possui as três correções concretas já atribuídas anteriormente: pool dos mapas do wandering trader, tratamento de duas definições de trades da mesma profissão e redução de logspam da biome-facing structure; a menção adicional ao port **26.3** não demonstra funcionalidade 26.3 no alvo 1.21.1. A [1.8.2 original, ID `cc365ykf`](https://api.modrinth.com/v2/version/cc365ykf) confirma a baseline do ceiling structure type. A cadeia de distribuições **1.8.2 → 1.9.0 → 1.9.1 → 1.9.2** é conhecida.

**Por que não encerrar:** o source `1.9.2` [`8b7020c3bc`](https://github.com/craisinlord/IntegratedAPI/commit/8b7020c3bc) contém deltas acumulados de worldgen e trades já detalhados, mas a árvore pública de commits não disponibiliza um pin de código independente e inequívoco da **1.9.1**. Sem changelog da própria **1.9.2** ou confronto de JARs 1.9.1/1.9.2, o delta **exclusivo** dessa publicação permanece desconhecido. O SHA-512 acima é **declarado pela plataforma**, não foi recalculado sobre um arquivo baixado. Não inventar atribuição por versão. **PENDENTE**, preservar JAR instalado **1.8.2**, nome do dossier, categoria, posição **#332**, e manter o item **121** na lista de auditoria.

