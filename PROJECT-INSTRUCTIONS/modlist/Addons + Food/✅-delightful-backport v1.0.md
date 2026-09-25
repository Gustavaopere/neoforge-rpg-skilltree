# Delightful Backport

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#216**: JAR `Delightful-Backport-1.0-1.21.1-neoforge.jar`, mod id `delightfulbackport`, runtime `1.0`, SHA-1 `2ab7909dcde4adc69586ca4a6030fecaa86b2598`.

## Propriedades do registro

- **Mod:** Delightful Backport
- **Arquivo JAR:** `Delightful-Backport-1.0-1.21.1-neoforge.jar`
- **Versão 1.21.1:** `1.0`
- **Categoria:** Compat, Comida
- **Função:** Bridge server-side entre VanillaBackport e Farmer's Delight, adicionando compatibilidade de recipes/cooking para conteúdo backportado sem criar um sistema culinário paralelo.
- **Dependências:** Integração concreta com VanillaBackport e Farmer's Delight. Pack físico atual confirma VanillaBackport 1.1.7.10 e Farmer's Delight 1.3.4. A bridge é server-side/data-driven e não substitui nenhum dos dois providers.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Build 1.0 NeoForge 1.21.1 é Alpha. Riscos principais: recipe/tag drift, receitas duplicadas com outros addons culinários, output duplo em automação, reload de recipes e version drift dos providers. Não assumir suporte a itens além dos confirmados nem maturidade Release para este artefato.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/vanillabackport-x-farmers-delight-compat
- **Procedência:** modlist.txt física atual de 20/09/2026 — 587 mods incluindo o modloader — confirma `Delightful-Backport-1.0-1.21.1-neoforge.jar` / runtime 1.0. CurseForge oficial revalidado em 20/09/2026 identifica a publicação NeoForge 1.21.1 de 08/07/2026 como **Alpha** e a mantém como build mais recente dessa linha.
- **Observações:** JAR `Delightful-Backport-1.0-1.21.1-neoforge.jar`, mod id `delightfulbackport`, runtime 1.0. A publicação oficial de 08/07/2026 é marcada **Alpha** para NeoForge 1.21.1; a listagem padrão de arquivos pode ocultar alphas, mas o projeto a mostra entre os recent files quando essa categoria é considerada. Blue/brown eggs e rotas culinárias previamente confirmadas foram preservadas; IDs internos não confirmados continuam fail-closed.
- **Atualização/Status:** REATUALIZADO EM 20/09/2026 — lote físico #215: Delightful-Backport-1.0-1.21.1-neoforge.jar / runtime 1.0 reconfirmados como a build NeoForge 1.21.1 mais recente do projeto; publicação oficial é explicitamente Alpha. Bridge VanillaBackport/Farmer's Delight e riscos de recipe/tag drift permanecem atuais.
- **Decisão:** Sem decisão
- **Histórico da decisão:** Ficha reconstruída em 08/09/2026 contra a modlist física de 595, corrigindo Farmer's Delight para 1.3.4 e documentando a bridge de forma fail-closed.
- **Sobreposição:** Pode cruzar recipes/tags com outros addons Farmer's Delight; coexistência temática não é conflito automático. Provider de item permanece VanillaBackport e provider culinário permanece Farmer's Delight.
- **Data da última decisão:** 2026-09-08

# Dossiê operacional — padrão Alex's Mobs
> **Runtime físico confirmado:** `Delightful-Backport-1.0-1.21.1-neoforge.jar` · mod id `delightfulbackport` · versão `1.0` · NeoForge 1.21.1.
## 1. Identidade
- **Projeto:** VanillaBackport X Farmer's Delight Compat / Delightful Backport.
- **JAR instalado:** `Delightful-Backport-1.0-1.21.1-neoforge.jar`.
- **Mod id:** `delightfulbackport`.
- **Versão:** `1.0`.
- **Loader/jogo:** NeoForge / Minecraft 1.21.1.
- **Publicação:** build **Alpha** de 08/07/2026 no projeto CurseForge oficial.
- **Environment oficial:** Server.
## 2. Papel no modpack
É uma **bridge de compatibilidade alimentar/data-driven** entre **VanillaBackport** e **Farmer's Delight**. Sua função é fazer conteúdo alimentar trazido pelo backport participar de rotas de cozinha do Farmer's Delight em vez de criar um sistema culinário paralelo.
## 3. Authority / ownership
- **VanillaBackport** continua dono dos itens vanilla backportados.
- **Farmer's Delight** continua dono do stove, skillet, cooking/cutting semantics e de seus recipes próprios.
- **Delightful Backport** só deve ser authority das receitas/tags/compatibilidade que conecta os dois.
Não criar uma terceira regra de fome, cooking state ou item ownership em mods próprios.
## 4. Conteúdo confirmado
A descrição/metadata já catalogada para esta bridge confirma integração de **blue egg** e **brown egg** com rotas culinárias do Farmer's Delight, incluindo preparação de fried egg em superfícies compatíveis de cocção.
A ficha não enumera recipe IDs porque eles não foram confirmados no JAR/source exato durante esta rodada. O comportamento deve ser validado pelo recipe registry efetivo em runtime.
## 5. Dependências concretas atuais
A modlist física atual contém:
- **Farmer's Delight ****`1.3.4`**;
- **VanillaBackport ****`1.1.7.10`**.
A referência antiga a Farmer's Delight 1.3.3 estava desatualizada e não deve ser usada como autoridade.
## 6. Configuração e dados
Pelo escopo e distribuição do projeto, a compatibilidade é essencialmente de recipes/tags/dados. Não foi confirmada uma API própria nem config TOML de gameplay para esta build; não inventar opções.
Alterações em recipes dependem do datapack/recipe reload do servidor.
## 7. Client / Server
O projeto oficial marca a bridge como **Server**. Isso é coerente com integração de recipes/dados: o servidor deve ser authority sobre crafting/cooking/output.
Assets visuais do item continuam pertencendo aos providers-base. Não presumir renderer próprio da bridge.
## 8. Lifecycle
Validar:
- server boot;
- recipe/datapack reload;
- world restart;
- atualização de Farmer's Delight/VanillaBackport;
- presença/ausência dos recipes de compatibilidade;
- automação que insere/remove ingredientes nos blocos culinários suportados.
## 9. Multiplayer
Recipes são state compartilhado do servidor. Dois jogadores executando a mesma rota de cooking não podem provocar output duplo por bridge paralela. Não há evidência de state per-player próprio nesta build.
## 10. Integrações no pack
Integração **real**: VanillaBackport ↔ Farmer's Delight.
Outros addons de Farmer's Delight podem também registrar receitas para ovos ou ingredientes equivalentes. Isso é possível sobreposição de recipe surface, não prova de conflito.
## 11. Riscos
1. recipe duplicado para o mesmo input/output;
2. tags divergentes após update de qualquer provider;
3. recipe não carregar após datapack reload;
4. output duplicado em automação;
5. assumir suporte a todo item backportado quando a evidência só confirma escopo específico;
6. version drift — especialmente porque esta distribuição `1.0` é **Alpha** e inicial.
## 12. Matriz de testes
1. Dedicated server boot com os três mods.
2. Conferir recipe registry para blue/brown egg.
3. Testar rotas de cocção documentadas uma a uma.
4. Comparar comportamento manual e automatizado.
5. Datapack `/reload` preservando as receitas.
6. Reiniciar o mundo e repetir.
7. Procurar receitas duplicadas/ambíguas em JEI/recipe browser.
8. Remover temporariamente um provider em instância de teste e confirmar failure behavior sem classloading indevido.
**Nenhum desses testes é declarado como executado nesta catalogação.**
## 13. Evidências
- modlist física canônica de 08/09/2026 para JAR/mod id/versão e dependências presentes;
- projeto oficial CurseForge **VanillaBackport X Farmer's Delight Compat** (Project ID 1560060);
- build **Alpha** NeoForge `Delightful-Backport-1.0-1.21.1-neoforge.jar` publicada em 08/07/2026;
- metadata do catálogo já existente para o escopo de eggs/cooking, preservada sem extrapolar recipe IDs não confirmados.
> **Boundary canônico:** esta bridge conecta dados de cozinha. VanillaBackport e Farmer's Delight continuam sendo os providers de conteúdo e mecânica; a bridge não deve ser usada como authority de um terceiro sistema culinário.
