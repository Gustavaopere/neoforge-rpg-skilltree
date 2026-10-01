# YUNG's API

> **Autoridade física atual — 28/09/2026.** Ordem física **#576**: JAR `YungsApi-1.21.1-NeoForge-5.1.8.jar`, mod id `yungsapi`, runtime `1.21.1-NeoForge-5.1.8`, SHA-1 `eb5757e5cb387553ed4d342e7aecf5b7dcb384a0`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** YUNG's API
- **Arquivo JAR:** `YungsApi-1.21.1-NeoForge-5.1.8.jar`
- **Versão 1.21.1:** 1.21.1-NeoForge-5.1.8
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** Biblioteca, Worldgen
- **Função:** Biblioteca/API compartilhada exigida pelos mods de estruturas e worldgen de YUNG.
- **Dependências:** Biblioteca-base para a família YUNG instalada: Better Caves, Desert Temples, Dungeons, End Island, Jungle Temples, Mineshafts, Nether Fortresses, Ocean Monuments, Better Witch Huts e Cave Biomes.
- **Sobreposição:** Infraestrutura compartilhada; não substitui qualquer Better Structure individual nem é intercambiável com outra library sem dependency graph real.
- **Compatibilidade/Riscos:** Riscos principais: consumer/API drift, mixin interaction, version skew e client/server classloading. A própria 5.1.8 é revisão de compatibilidade após regressões da 5.1.7; updates exigem smoke com todos os consumers.
- **Observações:** Mod id `yungsapi`, runtime `1.21.1-NeoForge-5.1.8`. A 5.1.8 reverte regressões da 5.1.7 e corrige Moog's Structure Lib. Family YUNG atual depende da API; não remover isoladamente.
- **Procedência:** modlist.txt física atual consultada em 13/09/2026 + CurseForge/Modrinth oficiais YUNG's API 5.1.8 + família YUNG física atual. Decisão Dependência preservada; nenhum boot conjunto, registry/data load, chunkgen ou linkage test foi executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/yungs-api-neoforge
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — runtime físico permanece 1.21.1-NeoForge-5.1.8; o projeto separado `YUNG's API (NeoForge) [1.20.4-1.21.1 ONLY]` publicou 5.1.9 para NeoForge 1.21.1 em 16/09/2026.
- **Histórico da decisão:** 2026-08-27 — atualizado de 5.1.7 para 5.1.8 e classificado como Dependência após confirmação de consumidor YUNG atual na modlist.
- **Data da última decisão:** 2026-09-09

# Dossiê operacional — padrão Alex's Mobs

> 🧩 **ESCOPO CANÔNICO.** Runtime físico: `YungsApi-1.21.1-NeoForge-5.1.8.jar`, mod id `yungsapi`, versão `1.21.1-NeoForge-5.1.8`. YUNG's API é biblioteca compartilhada dos mods YUNG; não gera estruturas ou progressão por si só.
## 1. Identidade e release
A build 5.1.8 é Release oficial NeoForge 1.21/1.21.1 publicada em 21/08/2026. O changelog reverte mudanças da 5.1.7 que causavam incompatibilidades — com destaque upstream para Villages & Pillages — e corrige integração com Moog's Structure Lib.
## 2. Consumers atuais
No snapshot físico atual, a API atende uma família YUNG extensa: Better Caves, Better Desert Temples, Better Dungeons, Better End Island, Better Jungle Temples, Better Mineshafts, Better Nether Fortresses, Better Ocean Monuments e, após este lote, ainda Better Witch Huts e YUNG's Cave Biomes. Remover a API isoladamente quebraria consumers atuais.
## 3. Authority e ownership
YUNG's API é authority apenas de primitives/helpers/config/worldgen infrastructure que expõe. Estruturas, biomas, loot e encounters pertencem a cada mod consumidor.
Não criar quests/perks “de YUNG's API”, não usar presença da library como proxy de uma estrutura específica e não substituir a API por outra biblioteca sem dependency graph real.
## 4. Client/server e classloading
A release é Client & Server. Dedicated server precisa carregar API + consumers sem classes client-only ou mixin/linkage errors. Updates devem ser testados em conjunto com a família YUNG, não isoladamente.
## 5. Version drift
A 5.1.8 existe justamente porque a 5.1.7 introduziu incompatibilidades externas. Isso é evidência de que pequenas revisões da library podem alterar interoperabilidade mesmo sem mudar o conteúdo visível dos consumers.
## 6. Lifecycle
Validar server/client boot, criação de mundo, data/registry loading, chunk generation, datapack/config reload quando suportado, save/restart e update simultâneo de API/consumers.
## 7. Riscos
1. **Consumer coupling:** remoção/update isolado causa missing class/method.
2. **Mixin/API drift:** revisão da library interfere com outros worldgen/structure mods.
3. **False provider classification:** library tratada como source de structure identity.
4. **Version skew:** consumers compilados contra faixa diferente.
5. **Dedicated-server leakage:** codepath client carregado incorretamente por integração externa.
## 8. Matriz de testes
- [ ] Dedicated server inicia com YUNG's API 5.1.8 + todos os consumers atuais.
- [ ] Nenhum `NoSuchMethodError`/`ClassNotFoundException` no boot.
- [ ] Mundo novo carrega registries/structures sem data errors.
- [ ] Better Caves e structures YUNG geram em chunks novos.
- [ ] Save/restart preserva worldgen state normalmente.
- [ ] API 5.1.8 não reproduz regressões de 5.1.7.
- [ ] Update futuro é testado com todos os consumers, não apenas um.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 9. Evidências
- Modlist física: `YungsApi-1.21.1-NeoForge-5.1.8.jar`, mod id `yungsapi`.
- CurseForge oficial: Release 5.1.8 NeoForge 1.21.1; revert de regressões 5.1.7 e fix para Moog's Structure Lib.
## 10. Limitação
Não foi decompilada nesta etapa a superfície completa de API/mixins. Integrações próprias devem depender de consumer-native hooks sempre que possível, não de internals da library.
## 11. Revalidação física — 11/09/2026
A modlist física mantém exatamente `YungsApi-1.21.1-NeoForge-5.1.8.jar`, mod id `yungsapi`, versão `1.21.1-NeoForge-5.1.8`. A file page oficial continua apontando 5.1.8 como latest release NeoForge 1.21.1, publicada em 21/08/2026.
O changelog exato permanece: revert de mudanças da 5.1.7 que causaram incompatibilidades, especialmente com Villages & Pillages, e fix de incompatibilidade com Moog's Structure Lib. A decisão **Dependência** e o estado **Integrado ao Github** foram preservados. Nenhum boot conjunto da família YUNG, registry/data load, chunkgen ou linkage test foi executado nesta recatalogação.
## 12. Revalidação — 13/09/2026
YUNG's API 5.1.8 permanece atual para NeoForge 1.21.1. O revert das regressões 5.1.7 e o fix de compatibilidade com Moog's Structure Lib continuam sendo os deltas relevantes. Decisão Dependência preservada. Nenhum teste runtime foi executado.

## 13. Atualização upstream 5.1.9 — não instalada
A versão instalada continua **5.1.8**. Para NeoForge 1.21.1, a release correta está no projeto específico **YUNG's API (NeoForge) [1.20.4-1.21.1 ONLY]** e foi publicada como **5.1.9** em 16/09/2026.

Deltas publicados:
- adiciona uma API para **substituir estruturas vanilla em utilidades de localização de estruturas**, como o comando `/locate`;
- corrige chamada insegura a `ServiceLoader#load`.

A nova API é particularmente relevante para mods YUNG que substituem estruturas vanilla: consumers podem informar a substituição também à camada de localização, reduzindo discrepância entre worldgen real e resultados de `/locate`/utilities.

Gate de regressão: `/locate` em estruturas substituídas, boot/reload com todos os YUNG consumers instalados, ServiceLoader em dedicated server e ausência de regressão nas integrações já documentadas com Moog's Structure Lib.

Fonte upstream: https://www.curseforge.com/minecraft/mc-mods/yungs-api-neoforge/files/8894736
