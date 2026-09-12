# Werewolves

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c969db9f0db814ca1f7cd889aef4c13
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `Werewolves-1.21-2.0.3.3.jar`, mod id `werewolves`, runtime `2.0.3.3`; Vampirism `1.10.13`, Vampiric Ageing `1.4.21` e Epic Fight `21.17.3.1` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026” e contém uma seção de revalidação física de 11/09. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Werewolves 2.0.3.3, Vampirism 1.10.13, Vampiric Ageing 1.4.21 e Epic Fight 21.17.3.1 estão confirmados. O corpo-fonte abaixo é preservado.

## Propriedades do banco

- **Mod:** Werewolves
- **Arquivo JAR:** `Werewolves-1.21-2.0.3.3.jar`
- **Versão 1.21.1:** 2.0.3.3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Magia, RPG, Mobs
- **Função:** Addon de Vampirism que adiciona uma terceira facção jogável: lobisomens. O jogador pode ser infectado, transformar-se em formas bestiais e seguir progressão própria com níveis, rituais, skill tree, habilidades de combate/mobilidade e escolhas de especialização mais ofensivas ou voltadas à sobrevivência. A facção ganha força e proteção natural em forma de besta, mas possui fraqueza a prata, além de conteúdo/worldgen próprio como o biome Werewolf Forest (`werewolves:werewolf_heaven`) no Overworld.
- **Dependências:** Vampirism 1.10.13 instalado e dentro do range `[1.10.0-beta.2,1.11.0)`. Vampiric Ageing 1.4.21 presente como extensão; Epic Fight 21.17.3.1 presente com compat mixin runtime para Werewolves.
- **Sobreposição:** Expande Vampirism com facção própria; Vampirism continua host da infraestrutura compartilhada. Vampiric Ageing estende forms/bite/progressão, mas não deve duplicar state provider-native.
- **Compatibilidade/Riscos:** Source pin exato preservado. Static blockers continuam: Howling AABB/config, Sense cooldown/radius, toughness config mismatch, health_reg consumer ambiguity, skills sem tree path, bite cooldown duplication e Epic Fight form/render risk. Runtime/dedicated-server QA obrigatório.
- **Observações:** Mod id `werewolves`, runtime 2.0.3.3. Source catalog: 1 faction, levels 1–14 + Lord 1–5, 9 actions, 38 skills registradas/36 tree-reachable, 8 effects, 14 tasks, 13 refinements. `werewolves:werewolf_heaven` é biome Werewolf Forest no Overworld, não dimensão.
- **Procedência:** modlist.txt física atual de 11/09/2026 + source pin `TeamLapen/Werewolves@b72635b3e014e406b25bb79adb9d340f7443660b` + CurseForge oficial Werewolves 2.0.3.3, ainda latest release NeoForge 1.21.1 de 25/03/2026 + Vampirism 1.10.13, Vampiric Ageing 1.4.21 e Epic Fight 21.17.3.1 físicos. Decisão Manter preservada; runtime QA não executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/werewolves-become-a-beast
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — Werewolves 2.0.3.3 permanece exatamente instalado e continua a latest release NeoForge 1.21.1; source audit pinado, Vampirism range, Vampiric Ageing/Epic Fight surfaces, blockers estáticos e runtime QA preservados.
- **Histórico da decisão:** 07/09/2026: auditoria granular source-level 2.0.3.3 concluída no pin b72635b3e014e406b25bb79adb9d340f7443660b. Inventariados faction, 9 actions, 38 skills registradas, árvores normal/Lord, níveis 1-14, Lord 1-5, Stone Altar, 8 effects, 14 tasks, 13 refinements, minions, bite, infection/cure, Silver/Wolfsbane e worldgen. Werewolf Heaven corrigido: é registry key do biome Werewolf Forest no Overworld, não dimensão separada. Runtime QA pendente.
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO — FECHAMENTO DO LOTE 09/09/2026.** Runtime físico reconfirmado: `Werewolves-1.21-2.0.3.3.jar`, mod id `werewolves`, versão `2.0.3.3`. A auditoria source-pinned abaixo (`TeamLapen/Werewolves@b72635b3e014e406b25bb79adb9d340f7443660b`) permanece integralmente válida como catálogo estático da build. Vampirism 1.10.13 está dentro do range declarado; Epic Fight 21.17.3.1 está presente e inclusive carrega compat mixin para Werewolves no runtime, mas **runtime/dedicated-server/form-render QA continua pendente**.

## Boundary consolidado do pack atual
Werewolves é authority da terceira facção jogável, forms, bite, Stone Altar, Silver/Wolfsbane/Bleeding, skills/actions/refinements e conteúdo de lobisomem. Vampirism continua host/authority da infraestrutura compartilhada de faction/Lord/minions/refinements. Vampiric Ageing 1.4.21 estende Werewolves, mas não substitui esses states.

Para RPG Skill Tree/Black Arcana: não duplicar faction level, form state, bite settlement, action cooldown, Silver/Wolfsbane weakness, Lord progression ou minion ownership. Static blockers listados no source audit permanecem **FAIL-CLOSED** até runtime QA.

## Auditoria source-level — Werewolves 2.0.3.3
Source exato: `TeamLapen/Werewolves@b72635b3e014e406b25bb79adb9d340f7443660b`.

### Inventário canônico
- 1 playable faction Werewolf, registrada via Vampirism API.
- Normal levels 1–14; Lord levels 1–5.
- 9 player actions: Human Form, Beast Form, Survival Form, Howling, Rage, Sense, Fear, Leap e Hide Name.
- 38 Werewolves-owned skills registradas. Generated normal tree conecta 33; generated Lord tree conecta 3 próprias e reutiliza 3 Lord skills do Vampirism. `resistance` e `sixth_sense` não têm generated-tree path no pin auditado.
- 5 `WerewolfForm` states: none, human, beast, beast4l e survivalist. `beast4l` é usado pelo Alpha Werewolf e não possui player form action.
- 8 effects próprios: Lupus Sanguinem, Howling, Silver, Wolfsbane, Bleeding, Un Werewolf, Bad Omen Werewolf e Stun.
- 14 provider tasks, incluindo 5 Lord-level tasks, 4 minion progression tasks, 4 refinement reward tasks e Oblivion Potion.
- 13 Werewolves-owned refinements.
- 1 Werewolves-owned minion task + 4 Vampirism minion tasks reutilizadas.
- 4 custom attributes: bite_damage, time_regain, food_consumption e food_gain.

### Progressão normal
Kills provider-confirmadas aumentam `levelProgress` em `floor(victim.maxHealth × 0.2)`. Levels 2–14 exigem Stone Altar à noite, quatro Fire Bowls acesos, progress threshold e Liver + Cracked Bone crescentes. Itens são consumidos no início do ritual; faction level só sobe no final via `FactionPlayerHandler.setFactionLevel`.

Requisitos nível 2→14: progress 50→350 em passos de 25; Liver 3→15; Cracked Bone 2→14.

### Forms e combate
Human, Beast e Survivalist têm modifiers próprios de armor, toughness, speed, food e, quando aplicável, health/attack/bite. Form damage reduction declarada: Human 5%, Beast 20%, Beast4L 30%, Survivalist 40%. O provider preserva health percentage ao trocar de form, aplica day/night modifiers e usa um transformation-time budget de 80 s default durante o dia fora do Werewolf Forest.

Full moon pode forçar/restringir transformação sem Free Will. Beast Rage suspende consumo de form time e usa night-strength modifiers durante Beast + Rage.

Bite requer transformed form, level >0, range/permissões e provider cooldown. Damage vem de `werewolves:bite_damage`; successful bite pode aplicar Stun/Bleeding e Lupus Sanguinem.

### Infection e cure
Lupus Sanguinem é precursor. Ao resolver, chama `FactionPlayerHandler.joinFaction(WEREWOLF_FACTION)`. O sono/wake provider resolve a infecção.

Cure path: Vampirism Empty Injection usada em WerewolfBaseEntity → Un Werewolf Injection → uso no Vampirism Med Chair → `UN_WEREWOLF` por 2000 ticks → no último tick `setFactionAndLevel(null, 0)`.

### Silver, Wolfsbane e Bleeding
Silver é weakness provider-native e pode reduzir speed/attack e aumentar incoming damage. Silver Blooded reduz amplifier ou duration apenas em human-like form; não concede imunidade.

Wolfsbane é world/spatial weakness provider-native.

Bleeding causa `bloodLoss` damage e pode drenar Vampirism blood state; não é apenas generic DOT.

### Lord/minions
Lord progression 1–5 é task-driven pela infraestrutura Vampirism. Werewolf minion tem max level 6, com upgrade items para 1–2, 3–4 e 5–6. Ele reutiliza Follow Lord, Defend Area, Stay e Protect Lord do Vampirism e adiciona `collect_werewolf_items`.

### Worldgen terminology
`werewolves:werewolf_heaven` é a **registry key do biome Werewolf Forest**. Generated tags o classificam como Overworld. Não é uma dimensão Werewolf Heaven separada.

### Static QA blockers
1. Howling chama AABB inflate sem reatribuir a caixa; effective aura radius precisa runtime QA.
2. Howling config expõe attack-speed amount 2.0, mas effect hard-coda +0.5.
3. Howling effect duration é criada como duration + disabled_duration; buff/lock split não está evidente.
4. Sense cooldown mistura segundos/ticks no cálculo source-level.
5. Sense renderer usa Vampirism blood-vision distance; `sense_radius=25` não foi localizado nesse path.
6. Level armor-toughness modifier usa `werewolf_speed_amount` em vez de `werewolf_armor_toughness`.
7. `health_reg_modifier=0.2` existe, mas consumer localizado apenas acelera FoodData timer.
8. `resistance` registrada, sem generated-tree path e sem consumer localizado.
9. `sixth_sense` tem consumer funcional, mas sem generated-tree path.
10. `no_leap_cooldown` refinement tem consumer, mas nenhum RefinementSet/acquisition path localizado.
11. Refinement set `damage3_n_armor1` usa `N_ARMOR_2` no corpo source.
12. Player bite usa PLAYER bite cooldown 200 ticks; SKILLS bite cooldown 5 s também existe, mas não aparece nessa transaction.
13. Post-bite feeding predicate é mais estreito do que um generic feeding interpretation.
14. Health After Kill aplica Regeneration amp 10 por apenas 4/5 ticks; runtime result precisa medição.
15. Epic Fight form/render/animation compatibility permanece risco ativo e precisa dedicated-server/client QA.

### Contrato Black Arcana
Provider-native first. Não duplicar faction, levels, skill wallet, action state, form state, bite settlement, Silver/Wolfsbane/Bleeding, Stone Altar, refinements ou minion ownership. Manter causal provenance e exactly-once settlement. Client render state não autoriza gameplay. Static mismatches permanecem fail-closed até teste/compat explícito.

**Estado:** `SOURCE-PINNED 2.0.3.3 / CATALOG GRANULAR FECHADO EM SOURCE / RUNTIME + EXACT-JAR + EPIC FIGHT QA PENDENTES`.

## Revalidação física — 11/09/2026
A modlist física continua contendo `Werewolves-1.21-2.0.3.3.jar`, mod id `werewolves`, versão `2.0.3.3`. A file list oficial continua apontando 2.0.3.3 como latest release NeoForge 1.21.1, publicada em 25/03/2026.

Vampirism `1.10.13` permanece dentro do range documentado `[1.10.0-beta.2,1.11.0)`. Vampiric Ageing `1.4.21` e Epic Fight `21.17.3.1` continuam presentes. O source pin `b72635b3e014e406b25bb79adb9d340f7443660b`, o catálogo granular e todos os blockers estáticos foram preservados sem promover achados estáticos a bugs runtime confirmados. A decisão **Manter** e o estado **Instalado — Dossiê completo** permanecem. Nenhum dedicated-server, form, bite, Howling, Sense, Silver/Wolfsbane, Lord/minion ou Epic Fight render/animation test foi executado nesta recatalogação.
