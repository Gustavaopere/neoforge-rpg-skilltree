# YUNG's Better Desert Temples

> **Autoridade física atual — 28/09/2026.** Ordem física **#578**: JAR `YungsBetterDesertTemples-1.21.1-NeoForge-4.1.5.jar`, mod id `betterdeserttemples`, runtime `1.21.1-NeoForge-4.1.5`, SHA-1 `90529257dbd92558998c65178294bc7b2fd16a64`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** YUNG's Better Desert Temples
- **Arquivo JAR:** `YungsBetterDesertTemples-1.21.1-NeoForge-4.1.5.jar`
- **Versão 1.21.1:** 1.21.1-NeoForge-4.1.5
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Worldgen, Exploração
- **Função:** Substitui/expande desert temples por estruturas maiores com salas, puzzles, traps e loot.
- **Dependências:** YUNG's API 5.1.8. Structure/loot/state dependem da config e datapacks efetivos do mundo.
- **Sobreposição:** Redesign específico de Desert Temple; overlap com outros structure mods é de densidade/loot, não identidade. Vanilla temple coexistence é configurável.
- **Compatibilidade/Riscos:** Riscos: state do templo/Mining Fatigue stale, densidade/interseção, loot economy e vanilla+Better coexistence inesperada. 4.1.5 corrige restrições de enchantments em loot.
- **Observações:** Mod id `betterdeserttemples`, runtime `1.21.1-NeoForge-4.1.5`. Por default Mining Fatigue persiste até o Pharaoh do templo ser derrotado; vanilla desert temples podem ser reabilitados por config.
- **Procedência:** modlist.txt física atual consultada em 13/09/2026 + CurseForge oficial NeoForge Better Desert Temples 4.1.5 + YUNG's API 5.1.8. Releases posteriores pertencem a linhas mais novas do Minecraft. Nenhum runtime/config/loot test foi executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/yungs-better-desert-temples-neoforge
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 13/09/2026 — YUNG's Better Desert Temples 4.1.5 permanece exatamente instalado e continua a latest release NeoForge 1.21.1; Pharaoh/Mining Fatigue, vanilla coexistence e enchantment-loot fix preservados.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-09-09

# Dossiê operacional — padrão Alex's Mobs

> 🏜️ **ESCOPO CANÔNICO.** Runtime físico: `YungsBetterDesertTemples-1.21.1-NeoForge-4.1.5.jar`, mod id `betterdeserttemples`, versão `1.21.1-NeoForge-4.1.5`. Substitui/redesenha Desert Temples com puzzles, traps, parkour, loot e encounter próprio.
## 1. Comportamento confirmado
A estrutura é muito mais extensa que o templo vanilla. O upstream documenta puzzles, armadilhas, parkour e loot melhorado.
Por padrão, jogadores dentro do Better Desert Temple recebem **Mining Fatigue**. O efeito daquele templo é removido permanentemente quando o Pharaoh final é derrotado. Esse mecanismo pode ser desabilitado na config.
## 2. Vanilla coexistence
A config permite reabilitar Desert Temples vanilla para gerarem junto dos Better Desert Temples. Portanto `replacement` versus `coexistence` depende da configuração efetiva do mundo e não deve ser inferido apenas pela presença do JAR.
## 3. Build 4.1.5
A release exata 4.1.5 corrige enchanted loot que não restringia corretamente os enchantments possíveis. O JAR físico corresponde à build NeoForge oficial 1.21.1.
## 4. Authority
Better Desert Temples é authority da estrutura, puzzle/encounter e state específico do templo. Loot tables continuam data-driven. YUNG's API fornece infraestrutura, mas não deve ser usada como structure identity.
## 5. Boundary para quests/perks
- Discovery deve usar a structure identity real.
- Entrar/reentrar não pode conceder progresso repetido.
- Matar o Pharaoh pode ser milestone apenas com causalidade server-side e deduplicação.
- Mining Fatigue é provider-native; não criar segundo flag para “templo limpo”.
## 6. Riscos
- densidade/loot no stack amplo de estruturas;
- state do templo/Mining Fatigue ficando stale após unload/restart;
- loot enchantment interactions;
- estruturas concorrentes/interseções;
- config permitindo vanilla + Better simultaneamente sem a curadoria esperar isso.
## 7. Matriz de testes
- [ ] Dedicated server gera Better Desert Temple 4.1.5.
- [ ] Mining Fatigue aplica somente conforme config/state.
- [ ] Pharaoh morto remove permanentemente a restrição daquele templo.
- [ ] Reload/restart preserva state de templo limpo.
- [ ] Vanilla temple coexistence segue config efetiva.
- [ ] Loot enchanted respeita fix da 4.1.5.
- [ ] Multiplayer não duplica completion/reward.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 8. Limitação
IDs internos do Pharaoh/state/structure não foram decompilados nesta etapa; integração programática provider-specific exige inspeção do JAR/source antes de hook.
## 9. Revalidação física — 11/09/2026
A modlist física mantém exatamente `YungsBetterDesertTemples-1.21.1-NeoForge-4.1.5.jar`, mod id `betterdeserttemples`, versão `1.21.1-NeoForge-4.1.5`. A file page oficial continua apontando 4.1.5 como latest release NeoForge 1.21.1.
O changelog exato continua sendo o fix para enchanted loot não restringir corretamente os enchantments possíveis. YUNG's API `5.1.8` permanece instalada. Releases mais novas do projeto que aparecem na listagem pertencem a linhas posteriores do Minecraft e não são tratadas como atualização aplicável automática. O estado **Integrado ao Github** e a decisão **Sem decisão** foram preservados. Nenhum Pharaoh, Mining Fatigue, loot, config, vanilla-coexistence ou multiplayer test foi executado nesta recatalogação.
## 10. Revalidação — 13/09/2026
Better Desert Temples 4.1.5 permanece a latest release NeoForge 1.21.1. O fix de enchanted loot, o state de Mining Fatigue/Pharaoh e a coexistência vanilla configurável permanecem os principais regression gates. Nenhum teste runtime foi executado.
