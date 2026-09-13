# Petrolpark's Library

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81898a54dfdc13c7e9a1
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `petrolpark-1.21.1-1.5.10.jar`, mod id `petrolpark`, runtime `1.5.10`, `petrolpark.mixins.json`; Destroy `0.4.3` também confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026” e contém uma seção de revalidação física de 11/09. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Petrolpark 1.5.10 e Destroy 0.4.3 estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Petrolpark's Library
- **Arquivo JAR:** `petrolpark-1.21.1-1.5.10.jar`
- **Versão 1.21.1:** 1.5.10
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** Biblioteca, Tecnologia
- **Função:** Biblioteca/framework do ecossistema Petrolpark usada por addons Create e projetos como Destroy para recursos compartilhados.
- **Dependências:** NeoForge 1.21.1. Consumer físico confirmado: Destroy 0.4.3. Source exato da branch Destroy `1.21.1-neo` declara Petrolpark obrigatória em `[1.5.0,1.6.0)`; a Petrolpark física 1.5.10 satisfaz a faixa. Runtime/linkage QA permanece pendente.
- **Sobreposição:** Library específica do ecossistema Petrolpark; não substituível por libraries Create genéricas. É load-bearing enquanto Destroy permanecer.
- **Compatibilidade/Riscos:** Compatibilidade estática com Destroy 0.4.3 agora comprovada: o neoforge.mods.toml da branch 1.21.1-neo exige Petrolpark `[1.5.0,1.6.0)`, e o pack usa 1.5.10. Permanecem riscos de ABI/linkage e comportamento runtime entre builds; dedicated-server/client boot e features de Destroy continuam QA pendente, não presumidos.
- **Observações:** JAR físico `petrolpark-1.21.1-1.5.10.jar`, mod id `petrolpark`, runtime 1.5.10. Destroy físico 0.4.3. Source exato do port 1.21.1 declara `petrolpark_version=1.5.0` no `gradle.properties` e dependency range `[1.5.0,1.6.0)` no `neoforge.mods.toml`; 1.5.10 satisfaz formalmente essa faixa. Isso fecha a pendência estática, sem equivaler a runtime QA.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Petrolpark's Library 1.5.10 + source read-only `NHblock714/Destroy` branch `1.21.1-neo`: `gradle.properties` com `mod_version=0.4.3` e `petrolpark_version=1.5.0`; `src/main/templates/META-INF/neoforge.mods.toml` com dependência Petrolpark `[1.5.0,1.6.0)`. Decisão Dependência e estado `Instalado — Dossiê completo` preservados; nenhum runtime/linkage test executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/petrolpark-library
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — Petrolpark's Library 1.5.10 permanece exatamente instalada; compatibilidade estática com Destroy 0.4.3 fechada contra source exato: branch 1.21.1-neo declara Petrolpark `[1.5.0,1.6.0)`. Runtime/linkage QA permanece pendente.
- **Histórico da decisão:** 2026-08-26 — origem/versionamento reconciliados e classificado como Dependência por Destroy instalado. 2026-09-10 — Dependência preservada; dossiê concluído para runtime 1.5.10, mas Estado da pesquisa mantido em Rever até prova source-level do range de Destroy 0.4.3. 2026-09-11 — source exato do port Destroy 0.4.3 confirmou dependência Petrolpark `[1.5.0,1.6.0)`; 1.5.10 satisfaz a faixa e Estado da pesquisa passou a Verificado, com runtime QA ainda pendente.
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `petrolpark-1.21.1-1.5.10.jar`, mod id `petrolpark`, versão `1.5.10`, NeoForge 1.21.1. Petrolpark's Library é framework Client & Server de APIs/data systems compartilhados pelo ecossistema Petrolpark/Create. **Destroy 0.4.3 é consumer físico confirmado**, portanto a decisão permanece **Dependência**. A build 1.5.10 é Beta e foi publicada em 07/09/2026. A compatibilidade estática com Destroy 0.4.3 foi fechada em 11/09/2026: o source exato do port exige Petrolpark `[1.5.0,1.6.0)`, faixa satisfeita pela 1.5.10 física. O estado de pesquisa é **Verificado**; runtime/linkage continua QA pendente.

## 1. Identidade e papel
- **Mod:** Petrolpark's Library.
- **JAR físico:** `petrolpark-1.21.1-1.5.10.jar`.
- **Mod id:** `petrolpark`.
- **Runtime:** `1.5.10`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Projeto:** Petrolpark.
- **Canal:** Beta.
- **Ambiente:** Client & Server.
- **Licença:** All Rights Reserved.
- **Papel:** fornecer APIs, sistemas data-driven e código compartilhado para mods Petrolpark e outros consumers.
- **Decisão:** Dependência.
- **Estado da pesquisa:** Verificado — dependency range do Destroy 0.4.3 comprovado em source exato; runtime/linkage QA permanece pendente.

## 2. Consumer confirmado: Destroy
A modlist física contém `destroy-1.21.1-0.4.3.jar`. O catálogo/source já confirmou Destroy como consumer de Petrolpark's Library.

Ownership:
- Destroy é authority de sua química, machines, recipes e progressão;
- Petrolpark fornece infraestrutura compartilhada;
- stacktrace em Petrolpark pode ser consequência de consumer/API mismatch, não necessariamente bug intrínseco da library.

Remover Petrolpark isoladamente não é seguro enquanto Destroy permanecer.

## 3. Escopo de APIs publicado
A documentação oficial descreve Petrolpark como library de APIs amplamente data-driven e shared features. O projeto publica, entre outros sistemas:
- Contaminants;
- Decaying Items e Ageing Recipes;
- Teams/team-bound data;
- loot table modifications;
- Recycling;
- Recipe Books/gating;
- Item Compression;
- Advanced Ingredients/Recipes;
- Shops;
- compat recipe deserializers;
- loot number providers/conditions/functions;
- badges e creative-tab helpers;
- wood compat;
- events adicionais;
- client rendering/sprite helpers.

Nem todo consumer usa todos esses sistemas. A existência da API não prova ativação no pack.

## 4. Integração Create opcional na library
A documentação afirma que Petrolpark é **fortemente integrado ao Create, mas não depende dele**. Features Create-specific são desabilitadas quando Create não está presente.

No pack Create está presente, portanto APIs Create podem ser disponibilizadas, incluindo exemplos publicados como:
- splined tubular blocks;
- Ponder instructions;
- off-grid tiling blocks;
- remember placer behavior.

Essas APIs continuam infrastructure; o consumer decide se as usa.

## 5. Shared features condicionais
A library também contém shared content ativado por determinados dependents, como:
- Redstone Programmer;
- Basin Lid/lidded basin recipes;
- Extrusion Die.

A documentação cita Destroy/Petrol's Parts como consumers de algumas dessas features.

Não atribuir automaticamente cada feature a Destroy 0.4.3 sem verificar o runtime, mas tratá-las como superfícies disponíveis na linha da library.

## 6. Contaminants
Contaminants são flags data-driven que podem acompanhar ItemStacks, FluidStacks e outros objetos e propagar por processos configurados, incluindo crafting/smelting e vários processos Create/da própria library.

É uma superfície de persistência e recipe processing sensível:
- não duplicar contaminant ao processar;
- não perder data em inventory transfer;
- recipes/custom processes devem respeitar configs/propagation rules.

## 7. Decay, ageing e inventories
Decaying Items podem mudar após tempo mesmo dentro de inventories modded. Ageing Recipes podem iniciar/parar decay conforme container/recipe.

Esse tipo de sistema exige:
- save/restart;
- chunk unload;
- inventories de outros mods;
- clock/time jumps;
- retirada/reinserção de item.

A ficha não afirma que Destroy usa todos esses hooks sem prova de consumer-specific use.

## 8. Recipe scanning e data-driven systems
Recycling/Item Compression/Recipe Books e advanced recipe systems podem escanear recipes e gerar/condicionar comportamento a partir dos dados carregados.

Em pack grande, isso cria riscos de:
- startup/reload cost;
- recipe ambiguity;
- tag/ingredient drift;
- JEI presentation divergente do recipe server-side.

## 9. Release 1.5.10
A release física `Petrolpark's Library 1.5.10` foi publicada em **07/09/2026**, Beta, file ID `8832484`.

Changelog exato:
- **Bug Fixes: Update to latest JEI**.

Não há base para atribuir outra feature específica à 1.5.10. O restante deste dossiê descreve APIs da linha, não deltas exclusivos desta subversão.

## 10. Linha 1.5.x e churn recente
A série 1.5.0→1.5.10 recebeu múltiplas Betas em curto intervalo entre julho e setembro de 2026. Isso aumenta a importância de regression gates por consumer.

A rapidez de releases não prova instabilidade funcional, mas é sinal de API/compat churn que deve ser tratado com version pinning e smoke tests.

## 11. Compatibilidade estática Destroy 0.4.3 ↔ Petrolpark 1.5.10
O source exato do port Destroy para Minecraft 1.21.1 foi localizado em `NHblock714/Destroy`, branch `1.21.1-neo`. Nessa branch:
- `gradle.properties` declara `mod_version=0.4.3` e `petrolpark_version=1.5.0`;
- `src/main/templates/META-INF/neoforge.mods.toml` declara Petrolpark como dependência obrigatória com `versionRange="[1.5.0,1.6.0)"`.

A Petrolpark física `1.5.10` está dentro da faixa formal aceita pelo Destroy 0.4.3. Isso fecha a pendência **estática/source-level** e permite `Estado da pesquisa = Verificado`.

Boundary: faixa declarada não equivale a teste runtime. Boot, linkage, recipes, chemistry e demais caminhos do Destroy continuam na matriz de QA.

## 12. Client/server e lifecycle
Petrolpark é Client & Server. Superfícies críticas:
- registry/init de APIs;
- packet/networking usado por consumers;
- datapack/recipe reload;
- persistence de contaminants/decays/team data;
- JEI integration;
- shared Create content quando ativado.

Qualquer mutação funcional precisa permanecer server-authoritative.

## 13. Relação com JEI
A 1.5.10 foi publicada especificamente para **update ao JEI mais recente**. O pack possui JEI e várias integrations, então o smoke deve incluir:
- recipe categories do consumer;
- tooltips de advanced ingredients/recipe books;
- reload;
- abrir JEI após server join;
- ausência de NoSuchMethod/ClassNotFound causada por API drift.

## 14. Riscos
1. **Consumer/library runtime drift:** a faixa formal `[1.5.0,1.6.0)` inclui Petrolpark 1.5.10, mas regressões comportamentais/linkage ainda precisam de smoke real.
2. **Beta churn:** 1.5.x teve releases rápidas.
3. **JEI API:** 1.5.10 muda exatamente essa superfície.
4. **Data propagation:** contaminants/decay podem duplicar/perder state em integrations.
5. **Recipe scanning:** grande recipe graph pode elevar custo de reload/startup.
6. **Create integration:** Create updates podem alterar optional API surfaces.
7. **Attribution error:** crash em Petrolpark pode ser causado por consumer incompatível.
8. **Removal breakage:** Destroy depende da library.

## 15. Matriz de testes
- [ ] Dedicated server e cliente iniciam com Petrolpark 1.5.10 + Destroy 0.4.3.
- [ ] Destroy registra conteúdo sem missing class/method/service.
- [ ] Abrir JEI e categories/recipes Destroy não produz API error após o update 1.5.10.
- [ ] `/reload` mantém recipes/data e consumers estáveis.
- [ ] Processos Destroy representativos executam do input ao output sem state loss.
- [ ] Se contaminants forem usados, persistem/propagam exatamente segundo config.
- [ ] Se decay/ageing forem usados, save/restart/inventory transfer preservam lifecycle correto.
- [ ] Shared Create features ativadas por consumers funcionam com Create atual.
- [ ] Restart/reconnect não duplica team/recipe/book state.
- [ ] Stacktrace de qualquer falha é triado por consumer/API antes de atribuição à library.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 16. Evidências e limites
- Modlist física: `petrolpark-1.21.1-1.5.10.jar`, mod id/runtime e `petrolpark.mixins.json`; Destroy 0.4.3 presente.
- CurseForge oficial: project 1093595, file ID 8832484, Beta NeoForge 1.21.1 de 07/09/2026; changelog 1.5.10 = update ao latest JEI.
- Documentação oficial: APIs data-driven, Create-optional integration, shared features e dependents incluindo Destroy.
- Evidência de catálogo anterior: Destroy 0.4.3 com baseline Petrolpark 1.5.0.
- Source exato do port Destroy 0.4.3: branch `NHblock714/Destroy@1.21.1-neo`, com `petrolpark_version=1.5.0` e manifest declarando `[1.5.0,1.6.0)`.
- **Limite:** o range estático foi comprovado e `Estado da pesquisa` é **Verificado**; runtime/linkage e comportamento funcional permanecem não testados.

## Revalidação física — 11/09/2026
A modlist física mantém exatamente `petrolpark-1.21.1-1.5.10.jar`, mod id `petrolpark`, versão `1.5.10`, com Destroy `0.4.3` também presente.

A pendência estática anterior foi fechada contra o source exato do port Destroy 1.21.1. Na branch `NHblock714/Destroy@1.21.1-neo`, `gradle.properties` declara `mod_version=0.4.3` e `petrolpark_version=1.5.0`; o template `src/main/templates/META-INF/neoforge.mods.toml` declara Petrolpark como dependência obrigatória com `versionRange="[1.5.0,1.6.0)"`. A Petrolpark física `1.5.10` satisfaz formalmente essa faixa declarada.

Por isso `Estado da pesquisa` foi promovido de **Rever** para **Verificado**, preservando **Decisão = Dependência** e **Estado no pack = Instalado — Dossiê completo**. Esta conclusão é documental/source-level: nenhum dedicated-server/client boot, linkage, chemistry processing, recipe, vat ou outro teste runtime de Destroy foi executado nesta reconciliação.
