# YUNG's Better Mineshafts

> **Autoridade física atual — 28/09/2026.** Ordem física **#582**: JAR `YungsBetterMineshafts-1.21.1-NeoForge-5.1.1.jar`, mod id `bettermineshafts`, runtime `1.21.1-NeoForge-5.1.1`, SHA-1 `6a14e1d8201f068ca87ef8333e471c5378955906`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** YUNG's Better Mineshafts
- **Arquivo JAR:** `YungsBetterMineshafts-1.21.1-NeoForge-5.1.1.jar`
- **Versão 1.21.1:** 1.21.1-NeoForge-5.1.1
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Worldgen, Exploração
- **Função:** Reformula mineshafts com grande variedade de layouts, bioma/tema e estruturas subterrâneas mais elaboradas.
- **Dependências:** YUNG's API 5.1.8. Coexiste com Better Caves 3.1.6, Better Dungeons 5.1.4 e outras structures subterrâneas.
- **Sobreposição:** Better Mineshafts = structure generation; Better Caves = carving/hidrologia. Overlap é espacial, não authority duplicada.
- **Compatibilidade/Riscos:** Riscos: cave carving cortando pieces, densidade subterrânea, ore/loot inflation, variant/config drift e surface openings em terrain modded.
- **Observações:** Mod id `bettermineshafts`, runtime `1.21.1-NeoForge-5.1.1`. Upstream documenta 13 biome variants, workstations/cellars, ore deposits, outposts e surface openings.
- **Procedência:** modlist.txt física atual consultada em 13/09/2026 + CurseForge oficial YUNG's Better Mineshafts NeoForge 5.1.1 revalidado em 13/09/2026 + YUNG's API 5.1.8 e Better Caves 3.1.6 físicos. A 5.1.1 continua a latest release NeoForge 1.21.1; changelog exato: minimum version requirements reduzidos para funcionar em 1.21 e 1.21.1. Nenhum runtime worldgen QA executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/yungs-better-mineshafts-neoforge
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 13/09/2026 — YUNG's Better Mineshafts 5.1.1 permanece exatamente instalado e continua a latest release NeoForge 1.21.1; 13 biome variants, config, underground composition e loot/ore boundaries preservados.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-09-09

# Dossiê operacional — padrão Alex's Mobs

> ⛏️ **ESCOPO CANÔNICO.** Runtime físico: `YungsBetterMineshafts-1.21.1-NeoForge-5.1.1.jar`, mod id `bettermineshafts`, versão `1.21.1-NeoForge-5.1.1`. É overhaul de **mineshaft structures**; não controla cave carving.
## 1. Conteúdo confirmado
O projeto substitui mineshafts lineares por redes de túneis mais dinâmicas e variadas. A documentação oficial lista **13 biome variants**, incluindo variante rara de mushroom mineshaft, workstations/cellars, ore deposits, outposts e surface openings.
## 2. Configuração
Mineshafts são configuráveis pelo próprio mod. O pack precisa tratar densidade/raridade/variants como config-driven, não hardcode.
## 3. Authority
Better Mineshafts controla sua structure generation/pieces/loot. Better Caves 3.1.6 controla carving/hidrologia; os dois podem se intersectar, mas não são sistemas duplicados.
## 4. Stack subterrâneo
Além de Better Caves, o pack contém Better Dungeons e múltiplas structures subterrâneas. Interseções podem ser legítimas ou degradar navegação/loot dependendo da frequência; isso exige seed/worldgen QA.
## 5. Boundary para quests/perks
- Discovery deve usar structure identity real.
- Workstation/outpost/ore deposit são subpieces; não gerar milestones independentes sem design explícito.
- Recarregar chunks ou encontrar outra piece da mesma mineshaft não pode duplicar completion.
## 6. Riscos
- cave carving cortando peças críticas;
- densidade subterrânea excessiva;
- ore/loot inflation;
- variants/config divergindo entre server/world;
- surface opening aparecendo em terrain modded de forma inadequada.
## 7. Matriz de testes
- [ ] Dedicated server gera Better Mineshafts 5.1.1.
- [ ] Biome variants aparecem sem missing pieces.
- [ ] Better Caves 3.1.6 não destrói sistematicamente connectivity.
- [ ] Ore deposits/loot não criam economia desbalanceada.
- [ ] Surface openings se encaixam no terrain stack atual.
- [ ] Save/restart/chunk unload preservam structure state normal.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 8. Limitação
Não foram extraídos structure-set IDs, pools ou pesos de variantes da build atual. Quests/integrations devem inspecionar registry/resources antes de hook provider-specific.
## 9. Revalidação física — 11/09/2026
A modlist física continua contendo exatamente `YungsBetterMineshafts-1.21.1-NeoForge-5.1.1.jar`, mod id `bettermineshafts`, versão `1.21.1-NeoForge-5.1.1`. A página oficial mantém 5.1.1 como latest release NeoForge 1.21.1.
O escopo permanece o mesmo: overhaul de mineshaft structures com 13 biome variants, workstations/cellars, ore deposits, outposts e surface openings; Better Caves `3.1.6` continua responsável por carving/hidrologia, não por esta structure. YUNG's API `5.1.8` permanece instalada. O estado **Integrado ao Github** e a decisão **Sem decisão** foram preservados. Nenhum structure generation, variant, surface opening, loot/ore, cave-intersection ou multiplayer discovery test foi executado nesta recatalogação.
## 10. Revalidação física e upstream — 13/09/2026
O runtime físico permanece `YungsBetterMineshafts-1.21.1-NeoForge-5.1.1.jar`, versão `1.21.1-NeoForge-5.1.1`, e continua a latest release NeoForge 1.21.1 localizada. O escopo de 13 biome variants, workstations/cellars, ore deposits, outposts e surface openings permanece preservado; Better Caves `3.1.6` continua authority de carving/hidrologia. O changelog exato da 5.1.1 apenas reduz o minimum version requirement para cobrir 1.21 e 1.21.1. Nenhum structure generation, variant, surface opening, loot/ore ou cave-intersection test foi executado nesta revalidação.
