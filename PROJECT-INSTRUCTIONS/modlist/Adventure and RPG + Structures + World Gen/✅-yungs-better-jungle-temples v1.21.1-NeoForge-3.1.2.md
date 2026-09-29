# YUNG's Better Jungle Temples

> **Autoridade física atual — 28/09/2026.** Ordem física **#581**: JAR `YungsBetterJungleTemples-1.21.1-NeoForge-3.1.2.jar`, mod id `betterjungletemples`, runtime `1.21.1-NeoForge-3.1.2`, SHA-1 `d6b7ce6cf351b09cbd23147ff166c35cfdc572e8`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** YUNG's Better Jungle Temples
- **Arquivo JAR:** `YungsBetterJungleTemples-1.21.1-NeoForge-3.1.2.jar`
- **Versão 1.21.1:** 1.21.1-NeoForge-3.1.2
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Worldgen, Exploração
- **Função:** Substitui/expande jungle temples por estruturas maiores com desafios, traps e loot.
- **Dependências:** YUNG's API 5.1.8. Targets de compat presentes: Create 6.0.10, Supplementaries 3.9.9 e Alex's Mobs Continued 2.1.13 (mod id `alexsmobs`).
- **Sobreposição:** Redesign específico de Jungle Temple. Compat pieces reutilizam conteúdo de outros mods, mas não transferem ownership da structure.
- **Compatibilidade/Riscos:** Riscos: special-piece version drift, missing block/entity em pieces condicionais, loot/density e quest duplicada por confundir piece com structure root.
- **Observações:** Mod id `betterjungletemples`, runtime `1.21.1-NeoForge-3.1.2`. Upstream possui special pieces opcionais para Create, Supplementaries e Alex's Mobs; todos os targets estão presentes no pack, mas runtime selection ainda precisa de smoke.
- **Procedência:** modlist(1).txt física atual de 27/09/2026 + Modrinth/CurseForge oficiais Better Jungle Temples 3.1.2 + YUNG's API 5.1.8 + targets físicos Create 6.0.10, Supplementaries 3.9.9 e Alex's Mobs Continued 2.1.13. Snapshots 11/09–13/09 com Supplementaries 3.9.8 e Alex's Mobs Continued 2.1.11 permanecem históricos. Nenhum structure/special-piece/deadlock test foi executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/yungs-better-jungle-temples-neoforge
- **Atualização/Status:** RECONCILIADO EM 28/09/2026 — YUNG's Better Jungle Temples 3.1.2 permanece exatamente instalado; targets físicos atuais Create 6.0.10, Supplementaries 3.9.9 e Alex's Mobs Continued 2.1.13 reconciliados. Layout, special pieces e worldgen-deadlock fix preservados.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-09-09

# Dossiê operacional — padrão Alex's Mobs

> 🌴 **ESCOPO CANÔNICO.** Runtime físico: `YungsBetterJungleTemples-1.21.1-NeoForge-3.1.2.jar`, mod id `betterjungletemples`, versão `1.21.1-NeoForge-3.1.2`. Redesenha Jungle Temples com layout, traps, puzzles e loot próprios.
## 1. Conteúdo confirmado
O projeto substitui o templo vanilla por uma estrutura totalmente redesenhada com novos desafios, traps, puzzles e loot.
## 2. Built-in compatibility
O upstream documenta peças especiais opcionais para **Create**, **Supplementaries** e **Alex's Mobs**. No pack atual, Create 6.0.10, Supplementaries 3.9.9 e Alex's Mobs Continued 2.1.13/mod id `alexsmobs` estão presentes.
A presença desses mods não prova que toda peça opcional foi realmente selecionada/gerada no runtime atual. Essa superfície deve ser observada em worldgen smoke.
## 3. Authority
Better Jungle Temples é authority da structure/layout e de suas pieces/loot. Create/Supplementaries/Alex's Mobs continuam authorities de seus próprios blocos/entidades quando usados por pieces compatíveis.
## 4. Boundary para quests/perks
Discovery/completion deve usar structure identity e causalidade real. Encontrar uma piece de Create/Alex's Mobs dentro do templo não transfere ownership da estrutura para esse mod nem deve gerar dois milestones.
## 5. Lifecycle
Validar chunk generation, save/restart, structure piece loading, loot generation, mob/entity spawn de pieces opcionais e datapack/config reload quando aplicável.
## 6. Riscos
- pieces opcionais quebradas por version drift dos mods-alvo;
- loot inflation;
- structure intersection/density;
- entity/block references ausentes em dedicated server;
- quest duplicate por reconhecer piece em vez da structure root.
## 7. Matriz de testes
- [ ] Dedicated server gera Better Jungle Temple 3.1.2.
- [ ] Layout/traps/puzzles funcionam sem missing pieces.
- [ ] Create 6.0.10 special pieces aparecem quando elegíveis sem registry errors.
- [ ] Supplementaries/Alex's Mobs pieces não causam missing block/entity.
- [ ] Loot gera uma vez e persiste normalmente.
- [ ] Quest discovery usa identity da structure root.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 8. Limitação
Não foram enumeradas as pools/pieces condicionais da 3.1.2; integrações específicas devem inspecionar resources/JAR antes de depender de piece IDs.
## 9. Revalidação física — 11/09/2026
A modlist física mantém exatamente `YungsBetterJungleTemples-1.21.1-NeoForge-3.1.2.jar`, mod id `betterjungletemples`, versão `1.21.1-NeoForge-3.1.2`. A file page oficial continua apontando 3.1.2 como latest release NeoForge 1.21.1.
O changelog exato da 3.1.2 corrige um problema em que **feature mixins podiam causar deadlocks de worldgen**. YUNG's API `5.1.8`, Create `6.0.10`, Supplementaries `3.9.8` e Alex's Mobs Continued `2.1.11` continuam presentes. A existência dos targets mantém as special pieces elegíveis, mas não prova seleção runtime. O estado **Integrado ao Github** e a decisão **Sem decisão** foram preservados. Nenhum structure generation, special-piece selection, loot, deadlock regression ou quest-discovery test foi executado nesta recatalogação.
## 10. Revalidação — 13/09/2026
Better Jungle Temples 3.1.2 permanece a latest release NeoForge 1.21.1. O fix de worldgen deadlocks e as special pieces opcionais para Create, Supplementaries e Alex's Mobs permanecem os principais regression gates. Nenhum teste runtime foi executado.
## 11. Reconciliação física — 28/09/2026
A autoridade física atual mantém `YungsBetterJungleTemples-1.21.1-NeoForge-3.1.2.jar`, mod id `betterjungletemples`, runtime `1.21.1-NeoForge-3.1.2`, com YUNG's API `1.21.1-NeoForge-5.1.8`. Os targets de compat presentes no pack são Create `6.0.10`, Supplementaries `3.9.9` e Alex's Mobs Continued `2.1.13` (`alexsmobs`). As referências de 11/09–13/09 a Supplementaries `3.9.8` e Alex's Mobs Continued `2.1.11` permanecem preservadas como snapshots históricos. A presença dos targets mantém as special pieces elegíveis, mas não comprova seleção runtime. Nenhum structure generation, special-piece selection, loot, deadlock regression ou quest-discovery test foi executado nesta reconciliação.
