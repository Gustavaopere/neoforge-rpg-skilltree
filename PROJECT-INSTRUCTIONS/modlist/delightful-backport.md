# Delightful Backport

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c869db9f0db813a888dc4ac84aa7d25  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: `modlist(4).txt`, 595 mods  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** Delightful Backport
- **Arquivo JAR:** `Delightful-Backport-1.0-1.21.1-neoforge.jar`
- **Versão 1.21.1:** `1.0`
- **Categoria:** Compat; Comida
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/vanillabackport-x-farmers-delight-compat
- **Função:** Bridge server-side entre VanillaBackport e Farmer's Delight, adicionando compatibilidade de recipes/cooking para conteúdo backportado sem criar um sistema culinário paralelo.
- **Dependências:** Integração concreta com VanillaBackport e Farmer's Delight. Runtime atual do pack: VanillaBackport 1.1.7.10 e Farmer's Delight 1.3.4; referência antiga a FD 1.3.3 foi corrigida.
- **Compatibilidade/Riscos:** Riscos principais: recipe/tag drift, receitas duplicadas com outros addons culinários, output duplo em automação, reload de recipes e version drift da build 1.0. Não assumir suporte a itens além dos confirmados.
- **Sobreposição:** Pode cruzar recipes/tags com outros addons Farmer's Delight; coexistência temática não é conflito automático. Provider de item permanece VanillaBackport e provider culinário permanece Farmer's Delight.
- **Observações:** Build NeoForge 1.21.1 inicial do projeto, publicada em 08/07/2026. Projeto oficial marcado Server. Blue/brown eggs e rotas culinárias registradas no catálogo foram preservadas; IDs internos não confirmados não foram inventados.
- **Procedência:** JAR/mod id/versão/dependências atuais: modlist física canônica de 08/09/2026, 595 top-levels. Escopo/environment/publicação: CurseForge oficial do projeto. Recipe IDs individuais não foram afirmados sem inspeção do recurso correspondente.
- **Histórico da decisão:** Ficha reconstruída em 08/09/2026 contra a modlist física de 595, corrigindo Farmer's Delight para 1.3.4 e documentando a bridge de forma fail-closed.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — bridge VanillaBackport/Farmer's Delight, authority de recipes, server/data lifecycle, automação, riscos de duplicação e versões atuais catalogados.
- **Data da última decisão:** 2026-09-08.

## Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `Delightful-Backport-1.0-1.21.1-neoforge.jar` · mod id `delightfulbackport` · versão `1.0` · NeoForge 1.21.1.

## 1. Identidade
- **Projeto:** VanillaBackport X Farmer's Delight Compat / Delightful Backport.
- **JAR instalado:** `Delightful-Backport-1.0-1.21.1-neoforge.jar`.
- **Mod id:** `delightfulbackport`.
- **Versão:** `1.0`.
- **Loader/jogo:** NeoForge / Minecraft 1.21.1.
- **Publicação:** build alpha/beta de 08/07/2026 no projeto CurseForge oficial.
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
- **Farmer's Delight `1.3.4`**;
- **VanillaBackport `1.1.7.10`**.

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
6. version drift — especialmente porque esta distribuição `1.0` é inicial.

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
- build NeoForge `Delightful-Backport-1.0-1.21.1-neoforge.jar` publicada em 08/07/2026;
- metadata do catálogo já existente para o escopo de eggs/cooking, preservada sem extrapolar recipe IDs não confirmados.

> **Boundary canônico:** esta bridge conecta dados de cozinha. VanillaBackport e Farmer's Delight continuam sendo os providers de conteúdo e mecânica; a bridge não deve ser usada como authority de um terceiro sistema culinário.
