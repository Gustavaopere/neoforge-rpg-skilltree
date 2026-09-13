# Create: Gunsmithing

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81dd9a88f2d272fcf71a
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Gunsmithing
- **Arquivo JAR:** `create-gunsmithing-1.21.1-1.4.9.jar`
- **Versão 1.21.1:** 1.4.9
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, RPG
- **Função:** Addon de armamento steampunk para Create com armas de fogo animadas, munições/attachments e crafting/progressão industrial integrados ao ecossistema Create, usando NTGL para gun mechanics.
- **Dependências:** Requisitos do projeto NeoForge 1.21.1: Create 6.0.10+ e NukaTeam Gun Lib (NTGL) 3.0.0+; a release 1.4.9 declara suporte a NTGL 3.1.8. Source atual também lista integrações opcionais com Better Combat, Punchy, Entity Model Features e Subtle Effects.
- **Sobreposição:** Create: Gunsmithing owns suas armas/ammo/attachments; NTGL fornece gun framework; Create fornece crafting/industrial primitives. Create Big Cannons/CBC é artilharia pesada e não substituto direto. Combat mods devem integrar sem reexecutar damage/reload.
- **Compatibilidade/Riscos:** Riscos em damage attribution, ammo consumption, reload state, attachments, animation/combat-framework hooks e Create crafting. 1.4.9 existe para suporte NTGL 3.1.8; version drift com NTGL é regression gate. Evitar double damage com Epic Fight/Punchy/Better Combat-style hooks.
- **Observações:** mod id `cgs`; runtime 1.4.9. Arsenal documentado pelo projeto inclui Flintlock, Revolver, Shotgun, Nailgun, Gatling, Blazegun, Launcher, Pneumatic Hammer e Frag Grenade. 1.4.9: suporte a NTGL 3.1.8.
- **Procedência:** Modlist física canônica de 08/09/2026 + runtime `cgs` 1.4.9 + CurseForge/Modrinth oficiais 1.4.9 + source público NeoForge 1.21.1 do projeto para requirements/optional integrations.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/cgs
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — firearm/ammo/attachment authority, Create crafting integration, NTGL dependency, combat attribution, client/server, lifecycle e delta 1.4.9 catalogados.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Create: Gunsmithing 1.4.9 foi reconfirmado como `Instalado` e reconstruído ao padrão técnico; presença do arsenal não foi convertida em decisão curatorial.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🔫 Versão física confirmada: `create-gunsmithing-1.21.1-1.4.9.jar`, mod id `cgs`, runtime `1.4.9`, NeoForge 1.21.1. O mod requer **Create + NTGL**; a 1.4.9 adiciona suporte a **NTGL 3.1.8**.

## 1. Papel e authority
Create: Gunsmithing adiciona armas steampunk totalmente animadas e integra seu crafting/progressão ao ecossistema Create. **Gunsmithing controla armas, ammo, attachments e recipes próprios**; NTGL fornece o framework de gun mechanics; Create fornece as primitives industriais usadas no crafting.

## 2. Arsenal documentado
O projeto lista atualmente:
- Flintlock;
- Revolver;
- Shotgun;
- Nailgun;
- Gatling;
- Blazegun;
- Launcher;
- Pneumatic Hammer;
- Frag Grenade.
A lista é tratada como conteúdo público confirmado do projeto; stats exatos de dano, fire rate, magazine e recoil não são congelados sem leitura versionada dos data files/JAR 1.4.9.

## 3. Crafting Create-integrated
As armas são construídas usando mecânicas/processing do Create em vez de serem apenas recipes vanilla desconectados. Recipe Manager e recipes do addon são authority do custo; scripts não devem completar o mesmo assembly por segunda rota sem decisão explícita.

## 4. NTGL
NukaTeam Gun Lib fornece o framework usado pelas armas/animations. O source 1.21.1 declara NTGL 3.0.0+ e a release **1.4.9** registra especificamente **NTGL 3.1.8 support**.
Atualizar NTGL isoladamente pode alterar gun state, networking/animation ou API; sempre validar a combinação com CGS.

## 5. Ammo
Ammo pertence ao sistema Gunsmithing/NTGL. Consumo deve ocorrer exatamente uma vez por shot confirmado; prediction/animation client-side não pode retirar uma segunda munição.
Tipos especiais adicionados em releases históricas não são automaticamente atribuídos à 1.4.9 se não estiverem presentes no runtime/data atual; validar no JAR/JEI quando necessário.

## 6. Attachments
O projeto suporta attachments e permite extensão via **NTGL gun packs**. Attachments podem alterar comportamento da arma, mas o stat final precisa ser resolvido pelo framework uma única vez.
Equip/unequip/reload não pode deixar modifiers stale ou duplicados.

## 7. Reload e weapon state
Magazine/ammo selection/reload/cooldowns são stateful. Servidor deve validar o state final; animação cliente não é prova suficiente de que o reload terminou.
Disconnect, death, inventory move e dimension change não podem clonar ammo ou resetar indevidamente weapon state.

## 8. Damage e causalidade
Shot/projectile/beam/explosion damage precisa manter shooter/weapon causalidade para loot, XP, quests, perks e PvP. Combat bridges não devem gerar um segundo `hurt` equivalente ao já liquidado pelo framework.

## 9. Pneumatic/air interactions
A linha histórica do projeto inclui integração de armas/ferramentas pneumáticas com air/backtank. Valores e itens exatos precisam ser validados na build atual antes de scripts próprios.
Quando ativo, consumo de ar deve ser liquidado por um único provider para evitar double-charge com outros addons Create.

## 10. Integrações opcionais
O source NeoForge 1.21.1 lista integrações opcionais com **Better Combat, Punchy, Entity Model Features e Subtle Effects**. Presença de compat code não garante que todas estejam ativas no pack; cada integração precisa de consumer real e smoke-test.
Epic Fight e outros combat systems também são superfície de coexistência, mas nenhuma integração é presumida sem evidência específica.

## 11. Create Big Cannons
CBC/Create Big Cannons fornece artilharia pesada, cannons e munitions de escala diferente. Create: Gunsmithing foca **armamento portátil**. Há sobreposição temática, não substituição funcional direta.

## 12. Client/server
Ammo, weapon state, damage, projectile/explosion result, crafting e inventory são common/server-authoritative. Models, first-person animation, muzzle flash, sounds e input são client-facing.
Packets de fire/reload precisam ser idempotentes e validados pelo servidor.

## 13. Lifecycle
Validar equip/unequip, reload, inventory transfer, death/respawn, dimension change, disconnect/reconnect, server restart, resource reload de models e gun-pack/data reload quando suportado.

## 14. Riscos
1. Shot aplicar damage duas vezes por combat bridge.
2. Ammo consumir duas vezes ou não consumir.
3. Reload state divergir client/server.
4. Attachment modifier duplicar após equip/reconnect.
5. NTGL version drift quebrar gun mechanics.
6. Create recipe produzir arma/componente duas vezes.
7. Air/backtank ser debitado por dois providers.
8. Projectile/explosion perder shooter attribution.
9. First-person/third-person animation conflitar com combat/player-model mods.

## 15. Matriz de testes
1. Dedicated server boot com Create + NTGL + CGS 1.4.9.
2. Craft das armas principais via recipes Create reais.
3. Fire/reload/ammo consumption exactly once.
4. Flintlock, Revolver, Shotgun, Nailgun, Gatling, Blazegun, Launcher e Pneumatic Hammer smoke-test.
5. Frag Grenade: throw/explosion/owner attribution.
6. Attachments: apply/remove/reconnect sem modifier duplication.
7. NTGL 3.1.8 regression smoke-test.
8. Death/respawn e dimension change com arma carregada.
9. Multiplayer/PvP com latency/retry.
10. Combat/render integrations presentes no pack, uma a uma.

## 16. Evidência
- modlist física 08/09/2026: CGS 1.4.9;
- CurseForge/Modrinth oficiais: NeoForge 1.21.1, Client & Server, arsenal e 1.4.9 = NTGL 3.1.8 support;
- source público NeoForge 1.21.1: Create 6.0.10+, NTGL 3.0.0+ e optional integrations.

> 🔒 Boundary canônico: **Gunsmithing owns weapon content; NTGL owns gun framework; Create owns industrial crafting primitives**. Combat bridges devem preservar uma única causalidade de shot/damage/ammo.