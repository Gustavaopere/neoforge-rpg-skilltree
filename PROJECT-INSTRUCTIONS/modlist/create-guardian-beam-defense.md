# Create Guardian Beam Defense

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db814aad1bf64e3173cfb0
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Guardian Beam Defense
- **Arquivo JAR:** `Create-Guardian-Beam-Defense-1.3.7.1b-1.21.1-neoforge.jar`
- **Versão 1.21.1:** 1.3.7.1b
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Automação
- **Função:** Addon defensivo de Create com Basic/Advanced Guardian Beam Turrets e Beam Reactor Helmet, usando rotação Create para cadence/charge e Guardian-themed beams para combate automatizado.
- **Dependências:** Create. O JAR físico embarca `sable-companion-common-1.21.1-1.4.2.jar` em `/META-INF/jarjar/`; componente interno, não top-level. Build 1.3.7.1b é Client & Server.
- **Sobreposição:** Compartilha defesa/armas com outros mods, mas owns seus turrets, target config e beam damage. Create owns rotação; Sable/Aeronautics pode hospedar os turrets em contraptions sem transferir ownership do combate.
- **Compatibilidade/Riscos:** Riscos em ownership/PvP target filters, player-kill attribution, XP/loot duplicado, line-of-sight, rotation-speed cadence e Sable contraptions. 1.3.7b passa a atribuir turret damage ao player owner; 1.3.7.1b corrige XP incorreto ao derrotar targets.
- **Observações:** mod id `creategbd`; runtime 1.3.7.1b. Basic Turret: um hostile em alcance documentado de 17 blocos; Advanced: até 10 hostiles no mesmo raio. Beam Reactor Helmet usa beam contínuo e durability. Turret charge time depende da rotação Create.
- **Procedência:** Modlist física canônica de 08/09/2026 + runtime `creategbd` 1.3.7.1b + Modrinth/CurseForge oficiais da release 1.3.7.1b e descrição oficial do projeto.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-guardian-beam-defense
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — turret/ownership/targeting authority, kinetic charge rate, Beam Reactor Helmet, Sable-contraption behavior, XP/loot attribution e regressões 1.3.7.1b catalogados.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Create Guardian Beam Defense 1.3.7.1b foi reconfirmado como `Instalado` e reconstruído ao padrão técnico; presença não foi convertida em decisão curatorial.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🔦 **Versão física confirmada:** `Create-Guardian-Beam-Defense-1.3.7.1b-1.21.1-neoforge.jar`, mod id `creategbd`, runtime `1.3.7.1b`, NeoForge 1.21.1. É um **provider de defesa automatizada** alimentado pela rotação do Create.

## 1. Papel e authority
Create Guardian Beam Defense adiciona turrets e um equipamento de beam próprios. **Create controla a rede cinética/RPM**; Guardian Beam Defense controla target selection, ownership, beam combat e seus drops/equipamentos.

## 2. Basic Guardian Beam Turret
O Basic Turret trava em **um hostile** dentro do alcance documentado de **17 blocos**. Crafting/progressão usa material associado a Guardians mortos por jogador conforme o projeto.
Target acquisition e damage devem ser server-authoritative.

## 3. Advanced Guardian Beam Turret
O Advanced Turret consegue mirar **até 10 hostiles** simultaneamente dentro do raio documentado de **17 blocos**. Multi-target não pode gerar duplicate damage/XP para a mesma death event.

## 4. Rotação e cadence
O charge-up time depende da velocidade da fonte cinética: RPM menor implica ataque mais lento; RPM maior reduz o tempo de carga. O addon deve ler o state da rede Create, não manter uma segunda velocidade independente.
Overstress/parada da rede precisa interromper o comportamento conforme o contract real do bloco.

## 5. Line of sight
Turrets estacionários exigem line of sight para lock/damage. A documentação registra exceção em **Simulated/Create Aeronautics contraptions** nas versões modernas.
Isso é uma superfície de compatibilidade específica; não generalizar para todo veículo/sublevel sem teste.

## 6. Ownership e permissões
Desde a linha 1.2, turrets podem pertencer a players e oferecem opções de target incluindo outros players. Outros jogadores não devem alterar/pickup um turret owned sem a regra/permissão prevista.
Ownership precisa persistir em save/restart e acompanhar a contraption quando suportado.

## 7. Player-kill attribution — 1.3.7b
A 1.3.7b altera o damage de turrets colocados por player para ser atribuído a esse player. Isso habilita drops reservados a player kills e cruza diretamente loot/advancement/XP hooks de outros mods.
Integrações devem usar a causalidade final do damage source, não criar uma segunda fake kill.

## 8. XP hotfix — 1.3.7.1b
A build física `1.3.7.1b` corrige turrets concedendo **quantidade incorreta de experiência** ao derrotar um alvo.
Regression gate: XP final precisa corresponder a uma única kill, sem duplicação por owner attribution ou listeners externos.

## 9. Sable contraptions
1.3.7b também corrige lasers que às vezes se desconectavam do turret após matar um alvo quando faziam parte de **Sable contraptions**.
Assembly/disassembly e target reacquisition não podem deixar beam reference stale.

## 10. Beam Reactor Helmet
O addon inclui Beam Reactor Helmet: armor/equipamento que dispara beam contínuo e pode atingir múltiplos inimigos no trajeto, consumindo durability, com keybind padrão documentado como `R`.
Client keybind é input; damage/durability precisam ser server-authoritative.

## 11. Embedded Sable Companion
O JAR contém `sable-companion-common-1.21.1-1.4.2.jar` em `/META-INF/jarjar/`. Essa library faz parte do host e não vira mod top-level.

## 12. Client/server e multiplayer
Targeting, damage, ownership, XP, loot attribution e durability são common/server. Beam render, particles, HUD/keybind feedback são client-facing.
PvP target modes precisam respeitar owner/team/server rules e não confiar apenas na opção visual do cliente.

## 13. Lifecycle
Validar turret placement/pickup, ownership, chunk unload/reload, target death/reacquisition, assembly em Sable contraption, disconnect/reconnect, server restart e dimension lifecycle quando o body suportar.

## 14. Riscos
1. Damage aplicado duas vezes por combat hooks.
2. XP duplicado/incorreto — regression 1.3.7.1b.
3. Player-kill attribution gerar loot duplicado.
4. Owner permission perdida no restart.
5. PvP filter permitir alvo indevido.
6. Beam reference stale após target death/contraption movement.
7. RPM/cadence calculado fora da rede Create.
8. Helmet durability cobrada client/server duas vezes.

## 15. Matriz de testes
1. Dedicated server boot.
2. Basic Turret: single-target/17-block boundary/LoS.
3. Advanced Turret: múltiplos alvos, até o limite documentado.
4. RPM baixo/alto e overstress.
5. Owner permissions e target modes com dois players.
6. Player-only mob drop via turret — attribution 1.3.7b.
7. XP de uma kill exatamente uma vez — hotfix 1.3.7.1b.
8. Turret em Sable/Aeronautics contraption após matar e adquirir novo alvo.
9. Beam Reactor Helmet: keybind, damage path e durability.
10. Restart/chunk reload preservando ownership/state.

## 16. Evidência
- modlist física 08/09/2026: 1.3.7.1b;
- projeto oficial: Basic/Advanced turrets, 17-block range, rotation-dependent charge, ownership e Beam Reactor Helmet;
- changelog 1.3.7b: player-attributed damage + Sable laser reconnect fix;
- changelog 1.3.7.1b: XP amount fix.

> 🔒 **Boundary canônico:** **Create fornece RPM; Guardian Beam Defense decide targeting/damage/ownership**. Loot e XP devem derivar da mesma kill causal, exatamente uma vez.