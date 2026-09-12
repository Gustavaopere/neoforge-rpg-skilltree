# YUNG's Better Desert Temples

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81a8b167fda5ebbb9a93
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** YUNG's Better Desert Temples
- **Arquivo JAR:** `YungsBetterDesertTemples-1.21.1-NeoForge-4.1.5.jar`
- **Versão 1.21.1:** 1.21.1-NeoForge-4.1.5
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Worldgen, Exploração
- **Função:** Substitui/expande desert temples por estruturas maiores com salas, puzzles, traps e loot.
- **Dependências:** YUNG's API 5.1.8. Structure/loot/state dependem da config e datapacks efetivos do mundo.
- **Sobreposição:** Redesign específico de Desert Temple; overlap com outros structure mods é de densidade/loot, não identidade. Vanilla temple coexistence é configurável.
- **Compatibilidade/Riscos:** Riscos: state do templo/Mining Fatigue stale, densidade/interseção, loot economy e vanilla+Better coexistence inesperada. 4.1.5 corrige restrições de enchantments em loot.
- **Observações:** Mod id `betterdeserttemples`, runtime `1.21.1-NeoForge-4.1.5`. Por default Mining Fatigue persiste até o Pharaoh do templo ser derrotado; vanilla desert temples podem ser reabilitados por config.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Better Desert Temples NeoForge 4.1.5.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/yungs-better-desert-temples-neoforge
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — puzzles/traps, Pharaoh, Mining Fatigue, loot e vanilla coexistence catalogados.
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