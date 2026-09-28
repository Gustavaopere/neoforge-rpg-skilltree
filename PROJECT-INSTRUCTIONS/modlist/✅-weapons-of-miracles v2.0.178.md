# Weapons of Miracles

> **Autoridade física atual — 27/09/2026.** Ordem física **#568**: JAR `WeaponsOfMiracles-2.0.178.jar`, mod id `wom`, runtime `2.0.178`, SHA-1 `b507eb376778cfd1cbecec2841c38891b26a7349`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** Weapons of Miracles
- **Arquivo JAR:** `WeaponsOfMiracles-2.0.178.jar`
- **Versão 1.21.1:** 2.0.178
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** RPG
- **Função:** Grande expansão provider-native de Epic Fight com 11 armas, 27 skills, 4 Artefacts, 1 enchantment, Lupus Aureum e gamerules opcionais; cada arma pode ter moveset/animações próprias.
- **Dependências:** Epic Fight 21.17.3.1 no pack, exatamente a versão declarada/testada pela release WOM 2.0.178; NeoForge 1.21.1.
- **Sobreposição:** Compartilha domínio com outros addons Epic Fight, mas armas/movesets/skills são conteúdo próprio; revisar stacking por skill/atributo, não remover por categoria genérica.
- **Compatibilidade/Riscos:** 2.0.178 é release muito recente. Riscos: double-hit/double-skill em bridges externas, power stacking, animation conflicts, gamerules opcionais assumidas como ativas e attribution de companion kills. Heartshield foi corrigido nesta build.
- **Observações:** Mod id `wom`, runtime 2.0.178. O guia anterior ainda citava 2.0.176; foi supersedido. Build 2.0.178 publicada em 07/09/2026 e testada contra Epic Fight 21.17.3.1.
- **Procedência:** modlist(1).txt física atual de 27/09/2026 + CurseForge oficial Weapons of Miracles 2.0.178 + Epic Fight 21.17.3.1 + NeoForge 21.1.250. A release WOM 2.0.178 permanece o runtime instalado e foi publicada/testada contra Epic Fight 21.17.3.1. Snapshot de 13/09 com NeoForge 21.1.248 preservado como histórico. Nenhum runtime QA foi executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/weapons-of-miracles
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 13/09/2026 — Weapons of Miracles 2.0.178 permanece exatamente instalado e continua a latest release NeoForge 1.21.1; alinhamento Epic Fight 21.17.3.1, conteúdo provider-native, Heartshield fix e causalidade preservados.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-09-09

# Dossiê operacional — padrão Alex's Mobs

> ⚔️ **ESCOPO CANÔNICO.** Runtime físico: `WeaponsOfMiracles-2.0.178.jar`, mod id `wom`, versão `2.0.178`. Weapons of Miracles é uma grande expansão **provider-native de Epic Fight**: armas, movesets, skills, artefatos e conteúdo próprio entram no pipeline do Epic Fight. Não tratar animação visual como evento separado de combate.
## 1. Versão atual e compatibilidade declarada
A build física 2.0.178 é a Release NeoForge 1.21.1 publicada em **07/09/2026**.
O changelog do arquivo exato declara teste contra:
- Minecraft 1.21.1;
- NeoForge 21.1.219;
- **Epic Fight 21.17.3.1-mc1.21.1-neoforge**.
O pack usa Epic Fight **21.17.3.1**, exatamente a versão declarada pelo addon. O NeoForge do pack é posterior dentro da linha 21.1, portanto ainda requer smoke real apesar do alinhamento do provider principal.
## 2. Escopo de conteúdo confirmado
A página oficial atual descreve:
- **11 weapons**;
- **27 skills**;
- **4 armor sets/Artefacts**;
- **1 enchantment**;
- **1 entity**, Lupus Aureum;
- gamerules opcionais que podem escalar mobs com distância, aumentar XP/emerald drops e alterar equipamento de skeletons.
Esses números documentam a superfície publicada pelo projeto; integrações específicas devem usar registries reais da build instalada.
## 3. Armas, movesets e skills
Cada arma pode possuir animações, sequências, técnicas e comportamento próprio construído sobre Epic Fight. O addon também publica skills de categorias como guard, dodge, passive, identity e mover.
Authority:
- **Epic Fight:** battle mode, animation/combat pipeline, stamina, skill framework e resolução-base do ataque;
- **Weapons of Miracles:** conteúdo/skills/movesets que registra sobre esse framework.
Não criar segundo hit, guard ou dodge listener que liquide a mesma ação novamente.
## 4. Artefacts e stats
Os quatro conjuntos de Artefacts alteram o perfil defensivo segundo regras próprias do addon. O texto público descreve troca de parte da redução de dano por health e peso zero.
Como isso toca attributes/weight/combat math, qualquer perk externa deve ler o modifier final provider-native; não reaplicar a descrição da armadura em código paralelo.
## 5. Lupus Aureum
O addon adiciona a entidade **Lupus Aureum**, companion/tameable com aquisição e interação próprias publicadas pelo projeto.
Boundary:
- ownership/taming deve vir da entidade/provider real;
- companion ativo não gera Mastery por tick;
- ações autônomas do companion não devem ser atribuídas ao jogador sem causalidade comprovada.
## 6. Gamerules opcionais
O projeto possui regras que podem, quando habilitadas, alterar força de mobs por distância, rewards e spawn de skeletons com melee weapons.
A página oficial informa que parte dessas regras é **desabilitada por padrão**. O dossiê não assume que estejam ativas no mundo do pack sem ler os gamerules efetivos.
## 7. Mudança específica da 2.0.178
O changelog da build 2.0.178 registra correção no **Heartshield**, ajustando o max shield para 20.
Não extrapolar outras mudanças para 2.0.178 sem changelog/source correspondente.
## 8. Boundaries para RPG Skill Tree
- Não conceder Mastery por cada animation frame, combo tick ou stamina tick.
- Um golpe só pontua quando a consequência server-authoritative é causalmente atribuída e deduplicada.
- Skills WOM continuam skills Epic Fight/provider-native; não duplicar cooldown/cost/passive modifier.
- Parry/guard/dodge devem ser observados pelo hook real quando uma perk depender deles.
- Companion kills e gamerule-driven mob scaling não devem gerar crédito indevido sem autoria.
- Hook provider-specific ausente = **FAIL-CLOSED**, não converter para bônus genérico.
## 9. Riscos
1. **Double-hit/double-skill:** listener externo processa ação já liquidada pelo Epic Fight/WOM.
2. **Power stacking:** 27 skills + outros addons Epic Fight + RPG attributes.
3. **Animation incompatibility:** outros animation/render mods interferem no moveset.
4. **Gamerule ambiguity:** regras opcionais tratadas como ativas sem confirmação.
5. **Companion attribution:** kills de Lupus Aureum creditadas incorretamente ao player.
6. **Version drift:** 2.0.178 é muito recente; NeoForge testado upstream é 21.1.219 e o pack usa uma revisão 21.1 mais nova.
## 10. Matriz de testes
- [ ] Dedicated server inicia com WOM 2.0.178 + Epic Fight 21.17.3.1.
- [ ] Registrar/carregar armas e 27 skills sem registry errors.
- [ ] Cada moveset usado por perk futura produz um único hit/cost/cooldown.
- [ ] Guard/parry/dodge não geram dupla resolução.
- [ ] Heartshield respeita o comportamento corrigido da 2.0.178.
- [ ] Artefacts aplicam/removem modifiers sem duplicação após relog/death.
- [ ] Lupus Aureum tame/ownership persiste e não duplica recompensa externa.
- [ ] Gamerules do mundo são lidos antes de assumir scaling/rewards.
- [ ] Multiplayer com dois players em combate simultâneo não mistura causalidade.
- [ ] Render/moveset permanece funcional com stack visual atual.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 11. Evidências
- **Modlist física 08/09/2026:** `WeaponsOfMiracles-2.0.178.jar`, mod id `wom`, runtime 2.0.178.
- CurseForge oficial: Release 2.0.178 de 07/09/2026, testada contra Epic Fight 21.17.3.1; fix Heartshield max shield 20.
- Página oficial do projeto: 11 weapons, 27 skills, 4 Artefacts, 1 enchantment, Lupus Aureum e gamerules opcionais.
- Guia Gameplay antigo registrava 2.0.176; essa referência foi supersedida pelo JAR físico atual.
## 12. Limitação
Não foi executado runtime QA da 2.0.178 nem inventário registry-by-registry de todas as armas/skills nesta etapa. Integrações específicas devem inspecionar o JAR/source e os hooks Epic Fight reais antes de implementação.
## 13. Revalidação física — 11/09/2026
A modlist física mantém exatamente `WeaponsOfMiracles-2.0.178.jar`, mod id `wom`, versão `2.0.178`. A file list oficial continua apontando 2.0.178 como latest release NeoForge 1.21.1, publicada em 07/09/2026.
O alinhamento com Epic Fight `21.17.3.1` permanece particularmente forte porque é a versão testada upstream para esta release; o pack usa exatamente essa revisão. O changelog exato continua sendo o fix do Heartshield com max shield 20. O estado **Integrado ao Github** e a decisão **Sem decisão** foram preservados. Nenhum moveset, skill, Heartshield, Artefact, Lupus Aureum, gamerule ou multiplayer combat test foi executado nesta recatalogação.
## 14. Revalidação física e upstream — 13/09/2026
O runtime físico permanece `WeaponsOfMiracles-2.0.178.jar`, mod id `wom`, versão `2.0.178`, e continua a latest release NeoForge 1.21.1 localizada. O alinhamento com Epic Fight `21.17.3.1` permanece particularmente forte porque esta é a revisão testada upstream para 2.0.178 e também a versão física do pack. O fix do Heartshield com max shield 20 e o catálogo provider-native permanecem preservados. Nenhum moveset, skill, Heartshield, Artefact, Lupus Aureum, gamerule ou multiplayer combat test foi executado nesta revalidação.
## 15. Reconciliação física — 27/09/2026
A autoridade física atual mantém `WeaponsOfMiracles-2.0.178.jar`, mod id `wom`, runtime `2.0.178`, junto de Epic Fight `21.17.3.1`. O modloader físico atual é NeoForge `21.1.250`; a referência anterior a `21.1.248` permanece apenas no snapshot histórico de 13/09. Nenhum runtime QA de movesets, skills, Artefacts, Lupus Aureum, Heartshield ou gamerules foi executado nesta reconciliação.
