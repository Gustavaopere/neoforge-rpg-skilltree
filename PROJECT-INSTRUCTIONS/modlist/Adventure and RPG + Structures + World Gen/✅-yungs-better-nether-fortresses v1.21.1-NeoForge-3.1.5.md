# YUNG's Better Nether Fortresses

> **Autoridade física atual — 28/09/2026.** Ordem física **#583**: JAR `YungsBetterNetherFortresses-1.21.1-NeoForge-3.1.5.jar`, mod id `betterfortresses`, runtime `1.21.1-NeoForge-3.1.5`, SHA-1 `4061ece2141475227646649046fc14c2da505922`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** YUNG's Better Nether Fortresses
- **Arquivo JAR:** `YungsBetterNetherFortresses-1.21.1-NeoForge-3.1.5.jar`
- **Versão 1.21.1:** 1.21.1-NeoForge-3.1.5
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Worldgen, Exploração
- **Função:** Substitui nether fortresses por estruturas muito maiores, variadas e mais complexas.
- **Dependências:** YUNG's API 5.1.8. Stack Nether atual inclui BetterNether 21.0.26, Amplified Nether 1.2.16 e Create 6.0.10.
- **Sobreposição:** Better Fortresses controla a structure; BetterNether/Amplified Nether controlam biomas/terrain. Overlap é espacial. Create compat reutiliza pieces sem transferir authority da fortress.
- **Compatibilidade/Riscos:** Riscos: placement/intersection em terrain Nether modded, Create-piece version drift, loot/spawn density e duplicate quest progress por confundir subpiece com structure root.
- **Observações:** Mod id `betterfortresses`, runtime `1.21.1-NeoForge-3.1.5`. Built-in Create special pieces são elegíveis porque Create 6.0.10 está presente; runtime selection ainda precisa de smoke.
- **Procedência:** modlist.txt física atual consultada em 13/09/2026 + CurseForge oficial YUNG's Better Nether Fortresses NeoForge 3.1.5 revalidado em 13/09/2026 + YUNG's API 5.1.8, BetterNether 21.0.26, Amplified Nether 1.2.16 e Create 6.0.10 físicos. A 3.1.5 continua a latest release 1.21.1; changelog exato corrige spawn behaviors de Wither Skeletons/Blazes e demais fortress mobs para equivalência com vanilla. Nenhum runtime QA executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/yungs-better-nether-fortresses-neoforge
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 13/09/2026 — YUNG's Better Nether Fortresses 3.1.5 permanece exatamente instalado e continua a latest release NeoForge 1.21.1; bridge networks/Keep/Lava Halls, Create compat, vanilla-style fortress mob spawning e Nether-provider boundaries preservados.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-09-09

# Dossiê operacional — padrão Alex's Mobs

> 🔥 **ESCOPO CANÔNICO.** Runtime físico: `YungsBetterNetherFortresses-1.21.1-NeoForge-3.1.5.jar`, mod id `betterfortresses`, versão `1.21.1-NeoForge-3.1.5`. Redesenha Nether Fortresses; não substitui o biome/worldgen provider do Nether.
## 1. Estrutura confirmada
Better Fortresses são maiores, mais complexas e divididas em três macroáreas documentadas: **bridge networks**, **the Keep** e **Lava Halls** subterrâneos.
## 2. Built-in Create compatibility
A build possui compatibilidade opcional com **Create**: quando Create está presente, certas partes da fortress podem usar special pieces do mod. O pack usa Create 6.0.10, portanto essa integração é elegível; runtime selection ainda precisa ser observada.
## 3. Stack Nether atual
BetterNether 21.0.26 e Amplified Nether 1.2.16 também estão presentes. Eles alteram biomas/terrain da dimensão, enquanto Better Fortresses controla a structure. A interação correta é composição espacial, não substituição entre providers.
## 4. Authority
Better Fortresses é authority das structure pieces/layout/loot/spawn placements que registra. BetterNether/Amplified Nether continuam authorities de seus respectivos biomas/terrain. Create continua authority dos blocks/pieces usados pela compat.
## 5. Boundary para quests/perks
- Encontrar uma fortress deve usar structure identity real.
- Bridge/Keep/Lava Hall são partes da mesma structure; não gerar múltiplos completions por piece.
- Blaze/Wither Skeleton kills continuam eventos de entidade, não prova automática de fortress completion.
## 6. Riscos
- placement/intersection com terrain Nether altamente modded;
- Create special pieces quebradas por version drift;
- loot/spawn density;
- fortress pathways cortados por terrain/caves;
- duplicate quest progress por piece.
## 7. Matriz de testes
- [ ] Dedicated server gera Better Fortress 3.1.5 em Nether atual.
- [ ] Bridge/Keep/Lava Halls permanecem conectados.
- [ ] BetterNether/Amplified Nether não causam placement impossível recorrente.
- [ ] Create 6.0.10 pieces opcionais carregam sem missing registry.
- [ ] Loot/spawns funcionam uma única vez conforme provider.
- [ ] Structure discovery é deduplicada em multiplayer/chunk reload.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 8. Limitação
Não foram enumeradas pools/pieces/structure IDs da 3.1.5. Integração provider-specific exige resources/JAR reais antes de implementação.
## 9. Revalidação física — 11/09/2026
A modlist física mantém `YungsBetterNetherFortresses-1.21.1-NeoForge-3.1.5.jar`, mod id `betterfortresses`, versão `1.21.1-NeoForge-3.1.5`. A file list oficial continua apontando 3.1.5 como latest release aplicável à linha Minecraft 1.21.1.
A release 4.1.1 publicada em 10/09/2026 pertence ao Minecraft `26.1.2`, portanto não é atualização aplicável ao pack. YUNG's API `5.1.8`, BetterNether `21.0.26`, Amplified Nether `1.2.16` e Create `6.0.10` continuam presentes. A divisão de authority permanece: Better Fortresses controla structure/layout; BetterNether/Amplified Nether controlam biome/terrain; Create só fornece special pieces quando selecionadas. O estado **Integrado ao Github** e a decisão **Sem decisão** foram preservados. Nenhum fortress generation, Create-piece selection, loot/spawn, intersection ou multiplayer discovery test foi executado nesta recatalogação.
## 10. Revalidação física e upstream — 13/09/2026
O runtime físico permanece `YungsBetterNetherFortresses-1.21.1-NeoForge-3.1.5.jar`, versão `1.21.1-NeoForge-3.1.5`, e continua a latest release NeoForge 1.21.1 localizada. O changelog exato da 3.1.5 corrige o comportamento de spawn de Wither Skeletons, Blazes e demais mobs de Nether Fortress para corresponder ao vanilla, inclusive permitindo farms no padrão vanilla. BetterNether/Amplified Nether continuam authorities de biome/terrain, e Create `6.0.10` continua apenas target de special pieces quando selecionadas. Nenhum fortress generation, spawn-behavior, Create-piece, loot ou intersection test foi executado nesta revalidação.
