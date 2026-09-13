# Dynamic Trees - Oh The Biomes We've Gone

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81688acae686600c03f8  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: modlist física mais recente, 595 mods  
> Exportado em: 2026-09-09

## Propriedades do registro

- **Mod:** Dynamic Trees - Oh The Biomes We've Gone
- **Arquivo JAR:** `dtbwg-1.1.0-BETA02.jar`
- **Versão 1.21.1:** `1.1.0-BETA02`
- **Categoria:** Compat; Worldgen
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/dynamic-trees-bwg/files/7208105
- **Função:** Bridge Dynamic Trees ↔ Oh The Biomes We've Gone que adapta árvores/fruits e worldgen BWG ao growth/felling dinâmico de Dynamic Trees.
- **Dependências:** Runtime atual: Dynamic Trees 1.21.1-1.7.2 e Oh The Biomes We've Gone 2.6.0. Build `1.1.0-BETA02` é NeoForge 1.21.1, Client & Server.
- **Compatibilidade/Riscos:** Beta: riscos de static+dynamic tree duplication, biome/species mapping drift, fruit double-processing, Spirit Tree/Pale Bog regression, Yucca/Baobab model-state mismatch, tree-felling double loot e seams entre chunks.
- **Sobreposição:** Adapta trees/fruits do BWG para Dynamic Trees; não substitui nenhum provider-base. A função é evitar/mediar coexistência entre geração estática BWG e árvore dinâmica.
- **Observações:** BETA02 confirma Spirit Tree no Pale Bog, Soul Fruit e atualização dos fruit models de Yucca/Baobab. Não foi inventada lista completa de species/families sem registry source-pinned da build exata.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `dtbwg-1.1.0-BETA02.jar`, DT 1.7.2 e BWG 2.6.0. Escopo/environment/changelog: CurseForge oficial File ID 7208105. Source/tag exato não foi localizado neste ciclo; enumeração não confirmada foi omitida.
- **Histórico da decisão:** Sem decisão curatorial formal. Em 08/09/2026, a ficha foi reconstruída contra o runtime físico BETA02 e a documentação oficial da release, com limite de source explícito.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — bridge BWG/DT, worldgen replacement, fruits, BETA02 regressions, lifecycle/MP, riscos, testes e limite de source catalogados.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `dtbwg-1.1.0-BETA02.jar` · mod id `dtbwg` · versão `1.1.0-BETA02` · NeoForge 1.21.1.

## 1. Papel no modpack
Dynamic Trees BWG é o addon de compatibilidade entre **Dynamic Trees** e **Oh The Biomes We've Gone (BWG)**. Sua função é adaptar a vegetação arbórea do BWG para growth/worldgen dinâmicos, mantendo BWG como provider dos biomas/materiais e Dynamic Trees como engine de árvores.

## 2. Release física
A build oficial para 1.21.1 é `1.1.0-BETA02`, classificada como **Beta**, para NeoForge e ambiente Client & Server. O filename oficial publicado coincide com o JAR físico `dtbwg-1.1.0-BETA02.jar`.

## 3. Authority / ownership
- **BWG:** biomas, blocos, materiais, frutas e vegetação original.
- **Dynamic Trees:** growth, branches/leaves, seed/species framework, felling e tree-worldgen engine.
- **DT BWG:** regras/adapters que fazem conteúdo BWG participar do modelo Dynamic Trees.

A bridge deve evitar que a mesma árvore seja gerada simultaneamente em forma estática BWG e dinâmica.

## 4. Conteúdo confirmado da BETA02
O changelog oficial da build física registra:
- **Spirit Tree adicionada ao Pale Bog**;
- **Soul Fruit adicionada**;
- modelos de fruits de **Yucca** e **Baobab** atualizados.

Esses são pontos confirmados para 1.1.0-BETA02. Esta ficha não inventa uma enumeração total de species/families porque um registry source-pinned da build exata não foi localizado nesta rodada.

## 5. Worldgen replacement
Como addon Dynamic Trees de biome mod, sua superfície crítica é o replacement/cancelamento de árvores estáticas e a escolha da species dinâmica por bioma. Em chunks novos, o resultado precisa respeitar tanto a distribuição do BWG quanto os contracts de Dynamic Trees.

Worldgen já materializado não é retroativamente convertido sem ferramenta específica; mudanças de versão podem criar seams entre chunks antigos e novos.

## 6. Growth e felling
Após a adaptação, growth/branch network/felling pertencem à engine Dynamic Trees. Mods externos de tree-felling devem usar os hooks/contracts compatíveis; quebrar blocos manualmente em massa pode gerar double loot ou estruturas órfãs.

## 7. Fruits
A BETA02 toca diretamente fruit content: Soul Fruit e models de Yucca/Baobab. Fruit placement/growth/drop state deve ser validado junto da árvore dinâmica, sem uma segunda rotina externa de loot/replant.

Model update é client presentation; spawn/growth/drop permanece server-side.

## 8. Runtime atual
A modlist física atual contém:
- Dynamic Trees `1.21.1-1.7.2`;
- Oh The Biomes We've Gone `2.6.0`;
- DT BWG `1.1.0-BETA02`.

A build é Beta, portanto atualização de qualquer provider deve ser seguida de worldgen/growth smoke-test antes de ser considerada segura.

## 9. Client / Server
- worldgen, growth, fruit state, felling e drops: server-authoritative;
- branch/leaves/fruit models: client-side presentation;
- biome selection vem de BWG/worldgen server-side.

## 10. Lifecycle
Validar:
- geração de chunks novos em biomas BWG;
- plantio/crescimento de trees adaptadas;
- fruit growth/drop;
- felling;
- chunk unload/reload;
- server restart;
- datapack/resource reload quando aplicável;
- dimension/world transitions;
- atualização de BWG/DT.

## 11. Multiplayer
Growth, felling e fruit drops devem liquidar uma vez no servidor. Dois jogadores interagindo com a mesma árvore não podem gerar double drops ou estados divergentes de branches/fruits.

## 12. Riscos
1. árvore estática + dinâmica duplicada;
2. biome/species mapping drift após update BWG;
3. Beta regression;
4. Spirit Tree ausente/incorreta no Pale Bog;
5. Soul Fruit dupe/loot duplicado;
6. Yucca/Baobab model/state mismatch;
7. tree-felling externo duplicar drops;
8. chunk seams;
9. resource reload deixar modelo stale;
10. assumir species não verificadas como presentes.

## 13. Matriz de testes
1. Dedicated server boot com DT 1.7.2 + BWG 2.6.0.
2. Gerar múltiplos biomas BWG em chunks novos.
3. Confirmar ausência de árvores estáticas duplicadas onde a bridge atua.
4. Pale Bog: verificar Spirit Tree — regression BETA02.
5. Soul Fruit: growth/drop/save-reload.
6. Yucca/Baobab: model + fruit state.
7. Felling manual e com sistema externo compatível.
8. Chunk unload/reload e server restart.
9. Dois jogadores na mesma árvore/fruit.
10. Smoke-test após update de DT ou BWG.

**A catalogação não afirma que esses testes foram executados.**

## 14. Evidências e limite
- modlist física canônica de 08/09/2026: JAR/mod id/version e stack runtime;
- CurseForge oficial `Dynamic Trees - Oh The Biomes We've Gone`, File ID 7208105, Client & Server, NeoForge 1.21.1;
- changelog oficial BETA02 para Spirit Tree, Soul Fruit e models Yucca/Baobab.

**Limite:** sem source/tag público da build exata localizado neste ciclo, não foi criada lista artificial de todas as species/families.

> **Boundary canônico:** BWG decide biomas/conteúdo; Dynamic Trees decide a engine arbórea; DT BWG fornece a compatibilidade/replacement entre ambos.
