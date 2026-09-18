# Petrolpark's Library

> **Reauditoria física — 17/09/2026.** Versão catalogada atual: `1.5.11`. O conteúdo abaixo foi reconstruído a partir da página Notion reconciliada e da autoridade física atual; a URL da própria página Notion foi deliberadamente omitida.

## Propriedades do registro

- **Mod:** Petrolpark's Library
- **Arquivo JAR:** petrolpark-1.21.1-1.5.11.jar
- **Versão 1.21.1:** 1.5.11
- **Categoria:** Biblioteca; Tecnologia
- **Função:** Biblioteca/framework do ecossistema Petrolpark usada por addons Create e projetos como Destroy para recursos compartilhados.
- **Dependências:** NeoForge 1.21.1. Consumer físico confirmado: Destroy 0.4.3, cujo manifest do port 1.21.1 exige Petrolpark `[1.5.0,1.6.0)`; a física 1.5.11 continua dentro dessa faixa formal.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** A compat estática Destroy↔Petrolpark permanece comprovada pelo range `[1.5.0,1.6.0)`, mas runtime/linkage continua QA pendente. Riscos em API/ABI, JEI, data propagation, recipe scanning, Create optional surfaces e beta churn.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/petrolpark-library
- **Procedência:** modlist física de 16/09/2026 + CurseForge oficial Petrolpark 1.5.11 + source read-only do port Destroy `NHblock714/Destroy@1.21.1-neo`, já auditado, com `versionRange="[1.5.0,1.6.0)"`. Nenhum runtime/linkage test executado.
- **Observações:** JAR físico `petrolpark-1.21.1-1.5.11.jar`, mod id `petrolpark`, runtime 1.5.11. Release oficial Beta NeoForge 1.21.1 de 14/09/2026, file ID 8878365. Changelog 1.5.11: `Update to latest JEI`. JEI físico atual: 19.56.0.440.
- **Atualização/Status:** READITADO EM 17/09/2026 — runtime físico atualizado de 1.5.10 para 1.5.11. O range formal do Destroy 0.4.3 `[1.5.0,1.6.0)` continua satisfeito; changelog 1.5.11 volta a alterar a superfície JEI.
- **Decisão:** Dependência
- **Histórico da decisão:** 2026-08-26 — origem/versionamento reconciliados e classificado como Dependência por Destroy instalado. 2026-09-10 — Dependência preservada; dossiê concluído para runtime 1.5.10, mas Estado da pesquisa mantido em Rever até prova source-level do range de Destroy 0.4.3. 2026-09-11 — source exato do port Destroy 0.4.3 confirmou dependência Petrolpark `[1.5.0,1.6.0)`; 1.5.10 satisfaz a faixa e Estado da pesquisa passou a Verificado, com runtime QA ainda pendente.
- **Sobreposição:** Library específica do ecossistema Petrolpark; não substituível por libraries Create genéricas. É load-bearing enquanto Destroy permanecer.
- **Data da última decisão:** 2026-09-10

> 🔎 **ESCOPO CANÔNICO.** Runtime físico: `petrolpark-1.21.1-1.5.11.jar`, mod id `petrolpark`, versão `1.5.11`, NeoForge 1.21.1. Petrolpark's Library é framework Client & Server de APIs/data systems compartilhados pelo ecossistema Petrolpark/Create. **Destroy 0.4.3 é consumer físico confirmado**, portanto a decisão permanece **Dependência**. A build 1.5.11 é Beta e foi publicada em 14/09/2026. A compatibilidade estática com Destroy 0.4.3 permanece fechada: o source exato do port exige Petrolpark `[1.5.0,1.6.0)`, faixa satisfeita pela 1.5.11 física. O estado de pesquisa é **Verificado**; runtime/linkage continua QA pendente.
## 1. Identidade e papel
- **Mod:** Petrolpark's Library.
- **JAR físico:** `petrolpark-1.21.1-1.5.11.jar`.
- **Mod id:** `petrolpark`.
- **Runtime:** `1.5.11`.
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
## 11. Compatibilidade estática Destroy 0.4.3 ↔ Petrolpark 1.5.x
O source exato do port Destroy para Minecraft 1.21.1 foi localizado em `NHblock714/Destroy`, branch `1.21.1-neo`. Nessa branch:
- `gradle.properties` declara `mod_version=0.4.3` e `petrolpark_version=1.5.0`;
- `src/main/templates/META-INF/neoforge.mods.toml` declara Petrolpark como dependência obrigatória com `versionRange="[1.5.0,1.6.0)"`.
A Petrolpark física `1.5.11` está dentro da faixa formal aceita pelo Destroy 0.4.3. Isso mantém fechada a pendência **estática/source-level** e sustenta `Estado da pesquisa = Verificado`.
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
1. **Consumer/library runtime drift:** a faixa formal `[1.5.0,1.6.0)` inclui Petrolpark 1.5.11, mas regressões comportamentais/linkage ainda precisam de smoke real.
2. **Beta churn:** 1.5.x teve releases rápidas.
3. **JEI API:** 1.5.10 e 1.5.11 mudam explicitamente essa superfície.
4. **Data propagation:** contaminants/decay podem duplicar/perder state em integrations.
5. **Recipe scanning:** grande recipe graph pode elevar custo de reload/startup.
6. **Create integration:** Create updates podem alterar optional API surfaces.
7. **Attribution error:** crash em Petrolpark pode ser causado por consumer incompatível.
8. **Removal breakage:** Destroy depende da library.
## 15. Matriz de testes
- [ ] Dedicated server e cliente iniciam com Petrolpark 1.5.11 + Destroy 0.4.3.
- [ ] Destroy registra conteúdo sem missing class/method/service.
- [ ] Abrir JEI 19.56.0.440 e categories/recipes Destroy não produz API error após o update 1.5.11.
- [ ] `/reload` mantém recipes/data e consumers estáveis.
- [ ] Processos Destroy representativos executam do input ao output sem state loss.
- [ ] Se contaminants forem usados, persistem/propagam exatamente segundo config.
- [ ] Se decay/ageing forem usados, save/restart/inventory transfer preservam lifecycle correto.
- [ ] Shared Create features ativadas por consumers funcionam com Create atual.
- [ ] Restart/reconnect não duplica team/recipe/book state.
- [ ] Stacktrace de qualquer falha é triado por consumer/API antes de atribuição à library.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 16. Evidências e limites
- Modlist física de 16/09/2026: `petrolpark-1.21.1-1.5.11.jar`, mod id/runtime e `petrolpark.mixins.json`; Destroy 0.4.3 presente.
- CurseForge oficial: project 1093595, file ID 8832484, Beta NeoForge 1.21.1 de 07/09/2026; changelog 1.5.10 = update ao latest JEI.
- Documentação oficial: APIs data-driven, Create-optional integration, shared features e dependents incluindo Destroy.
- Evidência de catálogo anterior: Destroy 0.4.3 com baseline Petrolpark 1.5.0.
- Source exato do port Destroy 0.4.3: branch `NHblock714/Destroy@1.21.1-neo`, com `petrolpark_version=1.5.0` e manifest declarando `[1.5.0,1.6.0)`.
- **Limite:** o range estático foi comprovado e `Estado da pesquisa` é **Verificado**; runtime/linkage e comportamento funcional permanecem não testados.
## Revalidação física — 11/09/2026
Na revalidação de 11/09/2026, a modlist física ainda mantinha `petrolpark-1.21.1-1.5.10.jar`, mod id `petrolpark`, versão `1.5.10`, com Destroy `0.4.3` também presente. Esse registro permanece como histórico.
A pendência estática anterior foi fechada contra o source exato do port Destroy 1.21.1. Na branch `NHblock714/Destroy@1.21.1-neo`, `gradle.properties` declara `mod_version=0.4.3` e `petrolpark_version=1.5.0`; o template `src/main/templates/META-INF/neoforge.mods.toml` declara Petrolpark como dependência obrigatória com `versionRange="[1.5.0,1.6.0)"`. A Petrolpark física atual `1.5.11` também satisfaz formalmente essa faixa declarada.
Por isso `Estado da pesquisa` foi promovido de **Rever** para **Verificado**, preservando **Decisão = Dependência** e **Estado no pack = Instalado — Dossiê completo**. Esta conclusão é documental/source-level: nenhum dedicated-server/client boot, linkage, chemistry processing, recipe, vat ou outro teste runtime de Destroy foi executado nesta reconciliação.
## 17. Atualização instalada — 1.5.11
A física atual **1.5.11**, Beta NeoForge 1.21.1 publicada em 14/09/2026, registra como bug fix **Update to latest JEI**. A 1.5.10 anterior também tinha esse mesmo foco, portanto JEI continua regression surface direta; o pack atual usa JEI `19.56.0.440`.
O range formal do Destroy 0.4.3 continua `[1.5.0,1.6.0)`, então 1.5.11 permanece dentro do contract estático. Isso não substitui boot/linkage/JEI/chemistry smoke tests.
A ficha anterior comprovou JarJars do host 1.5.10. O interior do novo JAR 1.5.11 não foi re-inspecionado nesta passagem; versões internas antigas permanecem evidência histórica e não são promovidas automaticamente como metadata atual.
Nenhum boot/linkage/JEI/chemistry test foi executado nesta atualização documental.
### Regression gates da 1.5.11
- [ ] Dedicated server e cliente iniciam com Petrolpark 1.5.11 + Destroy 0.4.3.
- [ ] JEI 19.56.0.440 abre categories/recipes do consumer sem API error.
- [ ] Destroy registra conteúdo sem missing class/method/service.
- [ ] Processos Destroy representativos executam do input ao output sem state loss.
