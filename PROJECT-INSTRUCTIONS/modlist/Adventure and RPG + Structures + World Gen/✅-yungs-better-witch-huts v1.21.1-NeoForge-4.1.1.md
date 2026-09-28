# YUNG's Better Witch Huts

> **Autoridade física atual — 28/09/2026.** Ordem física **#585**: JAR `YungsBetterWitchHuts-1.21.1-NeoForge-4.1.1.jar`, mod id `betterwitchhuts`, runtime `1.21.1-NeoForge-4.1.1`, SHA-1 `bbf6c91f4a2829c58bc024eaaab821986bc93de6`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** YUNG's Better Witch Huts
- **Arquivo JAR:** `YungsBetterWitchHuts-1.21.1-NeoForge-4.1.1.jar`
- **Versão 1.21.1:** 1.21.1-NeoForge-4.1.1
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Worldgen, Exploração
- **Função:** Expande witch huts em estruturas mais detalhadas e interessantes, com variações e conteúdo adicional.
- **Dependências:** YUNG's API 5.1.8. Replacement/coexistence de Witch Huts vanilla depende da config efetiva.
- **Sobreposição:** Redesign específico de Witch Huts; overlap com outros structure mods é espacial/econômico, não authority. Vanilla coexistence é configurável.
- **Compatibilidade/Riscos:** Riscos: densidade/interseção de structures, loot inflation, coexistência vanilla habilitada por config, duplicate quest progress e seams em chunks antigos/novos. Compatibilidade upstream ampla não substitui seed/runtime QA.
- **Observações:** Mod id `betterwitchhuts`, runtime `1.21.1-NeoForge-4.1.1`. Múltiplas variantes + witch's circle; huts vanilla ficam desabilitadas por padrão e podem ser reativadas por config.
- **Procedência:** modlist.txt física atual consultada em 13/09/2026 + CurseForge oficial YUNG's Better Witch Huts NeoForge 4.1.1 revalidado em 13/09/2026 + YUNG's API 5.1.8. O changelog exato da 4.1.1 reduz o minimum version requirement para suportar 1.21 e 1.21.1; releases posteriores pertencem a Minecraft 1.21.4/26.1.2. Nenhum runtime/config QA executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/yungs-better-witch-huts-neoforge
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 13/09/2026 — YUNG's Better Witch Huts 4.1.1 permanece exatamente instalado e continua a latest release NeoForge 1.21.1; variants, witch's circle, vanilla coexistence, loot, lifecycle e quest boundaries preservados.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> 🧙 **ESCOPO CANÔNICO.** Runtime físico: `YungsBetterWitchHuts-1.21.1-NeoForge-4.1.1.jar`, mod id `betterwitchhuts`, versão `1.21.1-NeoForge-4.1.1`. Redesenha Witch Huts e adiciona variantes/encounters próprios; YUNG's API continua sendo apenas infraestrutura.
## 1. Conteúdo confirmado
O projeto oficial adiciona **múltiplas variantes novas de Witch Hut** e uma **witch's circle**. As huts recebem layouts mais elaborados e loot mais significativo; brewing stands podem conter itens que funcionam como pistas de receitas vanilla de poções.
## 2. Vanilla coexistence
Por padrão, Witch Huts vanilla deixam de gerar quando Better Witch Huts está ativo. O upstream permite **reativar as huts vanilla pela config**. Portanto replacement versus coexistence é config-driven e não deve ser hardcodado por integração própria.
## 3. Authority e ownership
- Better Witch Huts: structure/layout/pieces/loot/encounter que registra.
- YUNG's API 5.1.8: infraestrutura compartilhada.
- Minecraft e outros structure/worldgen mods: respectivos registries e placement.
Uma witch encontrada dentro da estrutura continua sendo uma entidade real; a estrutura não deve ser inferida somente pela presença de uma witch ou de blocos parecidos.
## 4. Stack de worldgen atual
O pack possui uma grande quantidade de structures e worldgen providers. O upstream declara intenção de compatibilidade com outros mods de worldgen, incluindo a própria família YUNG, mas isso não elimina a necessidade de validar densidade, intersections e loot economy no seed/preset reais do pack.
## 5. Boundary para quests/perks
- Discovery/completion deve usar **structure identity real**, não heurística por bloco ou entidade.
- Variantes e witch's circle não devem gerar múltiplos milestones se o design considerar tudo parte do mesmo sistema de estruturas.
- Reentrar, recarregar chunk ou reabrir loot não pode conceder progresso repetido.
- Kill de witch não equivale automaticamente a conclusão da structure salvo requisito explícito.
## 6. Lifecycle crítico
Validar world creation, chunk generation, structure start/pieces, loot generation, save/restart, chunk unload/reload e alteração de config que habilita huts vanilla.
Mudança de config afeta chunks futuros; não tratar o worldgen como retroativo em chunks já gerados.
## 7. Riscos
1. **Structure density/intersection:** overlap espacial com outros structure providers.
2. **Loot inflation:** huts e witch circles adicionam novas fontes de loot.
3. **Config ambiguity:** vanilla huts reativadas sem a curadoria esperar coexistência.
4. **Quest duplication:** variantes/pieces confundidas com structures independentes.
5. **World upgrade seams:** alteração de config/version em mundo já explorado.
## 8. Matriz de testes
- [ ] Dedicated server inicia com Better Witch Huts 4.1.1 + YUNG's API 5.1.8.
- [ ] Huts/witch's circle geram em chunks novos sem missing pieces.
- [ ] Config default substitui huts vanilla conforme documentado.
- [ ] Reativar huts vanilla produz coexistência somente em chunks novos/elegíveis.
- [ ] Loot/brewing hints geram uma única vez por container/provider.
- [ ] Interseções com o stack atual de structures não causam geração corrompida recorrente.
- [ ] Quest discovery/completion é deduplicada em multiplayer/reload.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 9. Evidências
- Modlist física atual: `YungsBetterWitchHuts-1.21.1-NeoForge-4.1.1.jar`, mod id `betterwitchhuts`.
- CurseForge oficial da linha NeoForge 1.21.1: release 4.1.1; múltiplas hut variants, witch's circle, loot melhorado, brewing hints e vanilla huts desabilitadas por padrão com reativação por config.
## 10. Limitação
Structure-set IDs, pools, loot-table IDs e config keys exatos não foram decompilados nesta etapa. Integrações provider-specific devem inspecionar resources/JAR antes de depender de identificadores internos.
## 11. Revalidação física — 11/09/2026
A modlist física mantém `YungsBetterWitchHuts-1.21.1-NeoForge-4.1.1.jar`, mod id `betterwitchhuts`, versão `1.21.1-NeoForge-4.1.1`. A file list oficial continua apontando 4.1.1 como latest release NeoForge 1.21.1.
Releases posteriores do projeto pertencem a outras linhas de Minecraft: 4.4.0 para `1.21.4` e 5.1.0 para `26.1.2`; nenhuma é atualização aplicável automática ao pack. YUNG's API `5.1.8` permanece instalada. Replacement versus coexistence com Witch Huts vanilla continua dependente da config efetiva. O estado **Integrado ao Github** e a decisão **Sem decisão** foram preservados. Nenhum hut/circle generation, vanilla-coexistence, loot, config ou multiplayer quest test foi executado nesta recatalogação.
## 12. Revalidação física e upstream — 13/09/2026
O runtime físico permanece `YungsBetterWitchHuts-1.21.1-NeoForge-4.1.1.jar`, versão `1.21.1-NeoForge-4.1.1`, e continua a latest release NeoForge 1.21.1 localizada. O changelog exato da 4.1.1 apenas reduz o minimum version requirement para cobrir Minecraft 1.21 e 1.21.1. Variants, witch's circle e a coexistência opcional com huts vanilla permanecem config-driven. Nenhum hut/circle generation, vanilla-coexistence, loot, config ou multiplayer quest test foi executado nesta revalidação.
