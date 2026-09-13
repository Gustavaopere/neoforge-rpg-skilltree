# Reliquified L_Ender's Cataclysm

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c969db9f0db815d8bdfc417ced02109
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `reliquified_lenders_cataclysm-1.21.1-0.1.1.jar`, mod id `reliquified_lenders_cataclysm`, runtime `0.1.1`, mixin `reliquified_lenders_cataclysm.mixins.json`; L_Ender's Cataclysm 3.33, Relics 0.12.8, OctoLib 0.6.2 e fix 1.0.2 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Reliquified L_Ender's Cataclysm 0.1.1, Cataclysm 3.33, Relics 0.12.8, OctoLib 0.6.2 e o New Relics Fix 1.0.2 estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Reliquified L_Ender's Cataclysm
- **Arquivo JAR:** `reliquified_lenders_cataclysm-1.21.1-0.1.1.jar`
- **Versão 1.21.1:** 0.1.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia, RPG, Compat
- **Função:** Addon Relics × L_Ender's Cataclysm que adiciona relics temáticas de bosses/mecânicas do Cataclysm e as integra à progressão Relics; no stack atual depende da bridge de compatibilidade 1.0.2 para Relics 0.12.x.
- **Dependências:** Required base: Relics 0.12.8 + L_Ender's Cataclysm 3.33. OctoLib 0.6.2 está presente e é alvo do fix da 0.1.1. O pack também requer operacionalmente o New Relics Fix 1.0.2 para adaptar o addon ao Relics 0.12.x.
- **Sobreposição:** Addon-base define conteúdo temático Cataclysm→Relics; o fix 1.0.2 apenas adapta API antiga. Cataclysm continua authority de bosses/content; Relics de progression/abilities.
- **Compatibilidade/Riscos:** Riscos: two-layer compatibility addon+fix, Relics/OctoLib drift, double-processing de boss loot/progression, Curios stale modifiers, cooldown/XP migration, motion packet desync e double adaptation caso surja suporte nativo.
- **Observações:** 0.1.1 corrige compatibilidade com OctoLib 0.6. O fix top-level 1.0.2 permanece separado e necessário ao stack Relics 0.12.x; não deve ser confundido com conteúdo do JAR 0.1.1.
- **Procedência:** modlist.txt física canônica de 10/09/2026 + publicação oficial Reliquified L_Ender's Cataclysm 0.1.1 + documentação/publicação do New Relics Fix 1.0.2 + stack físico atual.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/reliquified-l-ender-s-cataclysm
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Reliquified Cataclysm 0.1.1 reconstruído: ownership Cataclysm/Relics, OctoLib 0.6 fix, acoplamento ao compat fix 1.0.2, lifecycle/network, riscos e testes.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `reliquified_lenders_cataclysm-1.21.1-0.1.1.jar`, mod id `reliquified_lenders_cataclysm`, versão `0.1.1`, NeoForge 1.21.1. Este addon adiciona relics temáticas de **L_Ender's Cataclysm** ao ecossistema **Relics**. No pack atual ele depende operacionalmente também do fix top-level `reliquified-lenders-cataclysm-new-relics-fix-1.0.2.jar`, que adapta o addon antigo ao Relics 0.12.x.

## 1. Identidade e papel
- **Mod:** Reliquified L_Ender's Cataclysm.
- **JAR:** `reliquified_lenders_cataclysm-1.21.1-0.1.1.jar`.
- **Mod id:** `reliquified_lenders_cataclysm`.
- **Versão instalada:** `0.1.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Release.

## 2. Stack físico confirmado
- **Relics:** 0.12.8.
- **L_Ender's Cataclysm:** 3.33.
- **OctoLib:** 0.6.2.
- **Compat fix separado:** Reliquified L_Ender's Cataclysm — New Relics Fix 1.0.2.

A 0.1.1 foi criada contra uma API de Relics anterior; o fix 1.0.2 é o componente que mantém essa build utilizável com Relics 0.12.x no snapshot atual.

## 3. Papel no modpack
O addon cria conteúdo cruzado de late-game inspirado em bosses/mecânicas de Cataclysm e o expressa como relics/progressão do framework Relics.

Ele não adiciona um segundo sistema de bosses e não substitui Cataclysm. Também não é o componente que resolve sozinho a compatibilidade com Relics 0.12.x; essa responsabilidade está no fix separado.

## 4. Autoridade / ownership
- **L_Ender's Cataclysm:** bosses, mobs, drops, estruturas e mecânicas-base de origem.
- **Relics:** relic progression, XP, ranks/levels, ability state e framework.
- **Reliquified Cataclysm 0.1.1:** conteúdo temático e mapeamento Cataclysm → relics.
- **New Relics Fix 1.0.2:** tradução de API antiga para Relics 0.12.x.
- **Curios/stack Relics:** equip lifecycle.

Essa divisão é crítica para não duplicar fixes em mods próprios.

## 5. Conteúdo confirmado e limite
A publicação oficial descreve **powerful relics themed after bosses and mechanics from L_Ender's Cataclysm**, voltadas a late game. A documentação do fix 1.0.2 confirma que cinco relics do addon exigem adaptação específica: Void Cloak, Scouring Eye, Void Vortex in Bottle, Vacuum Glove e Void Bubble.

Essas cinco são evidência concreta do conteúdo existente, mas não foram assumidas como lista completa do addon.

## 6. Delta exato da 0.1.1
O changelog oficial da build instalada registra **fix de compatibilidade com OctoLib 0.6**.

O pack usa OctoLib 0.6.2, portanto esse fix é diretamente relevante. Ele não resolve, por si só, a incompatibilidade posterior com Relics 0.12; essa é a razão da presença do fix 1.0.2.

## 7. Relação obrigatória com o fix 1.0.2
O fix separado adapta referências da antiga API Relics 0.10 para a linha 0.12 e restaura, entre outras superfícies, templates, Curios modifiers, progression/cooldowns e behavior de abilities.

Consequência operacional: **0.1.1 e fix 1.0.2 formam um par acoplado no pack atual**. Remover o fix sem substituir o addon por versão nativamente compatível pode quebrar startup ou abilities.

## 8. Boss/content causalidade
Quando uma relic se baseia em boss/drop/mecânica de Cataclysm:
- Cataclysm continua source of truth do conteúdo de origem;
- o addon pode observar loot/kill/contexto para criar reward/progression;
- Relics continua authority da progressão da relic.

Kill/loot events não devem ser consumidos duas vezes por addons diferentes.

## 9. Client / Server
O stack funcional é Client & Server. Ability state, progression, loot e causalidade precisam ser server-authoritative. Renderers/tooltips/particles podem ser client-facing.

Como o fix 1.0.2 também substitui um antigo player-motion packet, multiplayer remoto é obrigatório no teste do conjunto.

## 10. Lifecycle
Validar em conjunto 0.1.1 + fix 1.0.2:
- boot;
- acquire/loot;
- Curios equip/unequip;
- login/relogin;
- death/respawn;
- dimension change;
- server restart;
- XP/level/rank;
- cooldown active/expire;
- active ability;
- boss kill/loot context;
- update de Relics, Cataclysm ou OctoLib.

## 11. Multiplayer
Owner, boss-credit, relic XP e active ability precisam permanecer por jogador. Motion/active abilities não devem executar uma vez no cliente e outra no servidor. Curios modifiers não podem ficar duplicados após reconnect.

## 12. Integrações concretas na modlist
- **Cataclysm 3.33:** provider de conteúdo temático.
- **Relics 0.12.8:** framework de progression.
- **OctoLib 0.6.2:** compatibilidade explicitamente corrigida na 0.1.1.
- **New Relics Fix 1.0.2:** bridge necessária ao stack atual de Relics 0.12.x.

## 13. Riscos técnicos
1. **Two-layer compatibility:** addon-base + fix precisam permanecer coerentes.
2. **Relics API drift:** update pode invalidar mixins/transforms do fix.
3. **OctoLib drift:** 0.1.1 corrigiu especificamente a linha 0.6.
4. **Double-processing:** boss kill/loot/progression observado por múltiplos addons.
5. **Curios stale modifiers:** equip lifecycle duplicado.
6. **Cooldown/XP migration:** state antigo não migra corretamente.
7. **Motion/network desync:** active ability depende do fix atual.
8. **Double adaptation:** se surgir versão nativa compatível, manter fix pode se tornar incorreto.

## 14. Matriz de testes
- [ ] Dedicated server inicia com Cataclysm 3.33 + Relics 0.12.8 + addon 0.1.1 + fix 1.0.2 + OctoLib 0.6.2.
- [ ] As cinco relics explicitamente cobertas pelo fix instanciam sem linkage error.
- [ ] Equip/unequip não duplica modifier.
- [ ] XP/rank/level persiste após relog/restart.
- [ ] Cooldown persiste e expira uma única vez.
- [ ] Active abilities funcionam em singleplayer e multiplayer remoto.
- [ ] Motion-related ability não causa rubber-band/double execution.
- [ ] Boss kill/loot não duplica reward/XP.
- [ ] OctoLib 0.6.2 não reproduz incompatibilidade corrigida na 0.1.1.
- [ ] Remoção experimental do fix reproduz a dependência esperada antes de qualquer decisão de remover do pack.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 15. Evidências e limites
- Modlist física: versões exatas de addon-base, fix, Relics, Cataclysm e OctoLib.
- Publicação oficial 0.1.1: propósito e fix de OctoLib 0.6.
- Publicação/documentação do fix 1.0.2: incompatibilidade Relics antiga→0.12 e cinco relics explicitamente adaptadas.
- **Limite:** não foi inventariado todo o conteúdo/recipes/loot do addon 0.1.1; apenas superfícies sustentadas por publicação e stack físico foram documentadas.
