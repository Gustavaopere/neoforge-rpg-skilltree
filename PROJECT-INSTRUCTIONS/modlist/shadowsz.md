# Shadowsz

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c969db9f0db8126a827c6e6f636ea31
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `shadowsz-1.1.9.jar`, mod id `shadowsz`, runtime `1.1.9`, mixin `shadowsz.mixins.json`; Iron's Spells 3.16.3, Legendary Monsters, Bosses'Rise 2.1.2, Mowzie's Mobs 1.8.2 e L_Ender's Cataclysm 3.33 presentes; Monster Expansion não encontrado top-level
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, ShadowsZ 1.1.9 e as integrações físicas citadas acima estão presentes, enquanto Monster Expansion não aparece top-level. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Shadowsz
- **Arquivo JAR:** `shadowsz-1.1.9.jar`
- **Versão 1.21.1:** 1.1.9
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia, RPG, Mobs
- **Função:** Sistema de necromancia/Shadow Monarch integrado a Iron's Spells, com Shadow Arising, army roster/storage, commands/groups, XP/stats, progress mode, Umbral magic e compats de mobs.
- **Dependências:** Required: Iron's Spells 'n Spellbooks, fisicamente 3.16.3. Compats presentes: Legendary Monsters, Bosses'Rise 2.1.2, Mowzie's Mobs 1.8.2 e L_Ender's Cataclysm 3.33. Monster Expansion não foi encontrado top-level.
- **Sobreposição:** Sobreposição temática com outros sistemas de necromancia/minions do pack, mas ShadowsZ owns seu roster/progressão/storage/Umbral integration; não substituir sem decisão de gameplay.
- **Compatibilidade/Riscos:** Stateful army system. Riscos: chunk-ticket leaks, roster/storage dupe, modded-mob AI incompat, friendly-fire/team drift, Iron's API drift, unsafe Position Swap, ghost army após relog e performance sob muitos shadows/chunks.
- **Observações:** 1.1.9 corrige max shadows, Tyros, Maledictus/Frostmaw, transforms vanilla e adiciona semi-compats/RestrictPowers/revoke. Config local não foi lida; fusion/equipment/progress mode não são presumidos ativos.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial ShadowsZ 1.1.9, descrição completa e changelog da build.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/shadowsz
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — ShadowsZ 1.1.9 reconstruído: Arising, roster/storage, army commands, force-loading, progression/titles, Umbral magic, parties, compats, persistence, riscos e testes.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `shadowsz-1.1.9.jar`, mod id `shadowsz`, versão `1.1.9`, NeoForge 1.21.1. ShadowsZ é um sistema completo de necromancia/army progression inspirado no Shadow Monarch, **dependente de Iron's Spells 'n Spellbooks** e com state persistente de roster, summons, XP, equipamento e progressão configurável.

## 1. Identidade e dependência central
- **Mod:** ShadowsZ.
- **JAR:** `shadowsz-1.1.9.jar`.
- **Mod id:** `shadowsz`.
- **Versão:** `1.1.9`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Release.
- **Ambiente:** Client & Server.
- **Mixin físico:** `shadowsz.mixins.json`.
- **Required:** Iron's Spells 'n Spellbooks; o pack contém **3.16.3**.

## 2. Claiming the Power
Pouco após entrar no mundo, o jogador pode aceitar/rejeitar o poder das sombras. Enquanto não estiver attuned, keybinds, menu e spells do sistema ficam inativos.

Servidor pode restringir acesso via gamerule `shadowszRestrictPowers`; a concessão/revogação é server-authoritative e não pode depender de cliente honesto.

## 3. Shadow Eyes e Shadow Arising
A tecla N revela sombras de entidades derrotadas próximas. Shift + right-click tenta **Shadow Arising**:
- até 3 tentativas;
- falhar todas remove a sombra definitivamente;
- wild shadows não reclamadas despawnam após o tempo documentado pelo projeto;
- chance considera max HP do alvo e Mana atual do jogador, além de config.

A conversão deve ocorrer exatamente uma vez e preservar identidade/tipo compatível da entidade derrotada.

## 4. Army Menu e roster
A tecla B abre roster pesquisável + detalhe do shadow com preview 3D. O menu permite:
- nomear;
- hotkeys individuais (9 slots);
- comportamento Wander / Follow / Stay;
- stance Aggressive / Passive / Neutral;
- item pickup;
- gastar level-up points em Health, Speed, Damage e Armor;
- fusion/equipment quando habilitados;
- release permanente.

O roster persistente é state canônico do ShadowsZ, não deve ser duplicado por scripts externos.

## 5. Shadow Storage
A tecla Y abre inventory compartilhado semelhante a ender chest. Loot coletado por shadows é direcionado a esse storage.

Inserção, death, dismiss, relog e restart precisam preservar atomicidade: item deve existir no máximo uma vez entre mob pickup/storage/world.

## 6. Controle do exército
O projeto fornece:
- ordem de ataque global mirando um alvo;
- Summon All / Dismiss All / Despawn Wild Shadows;
- grupos/squads com behavior/stance próprios e até 3 hotkeys de grupo;
- **Position Swap** para trocar instantaneamente de posição com um shadow, sujeito a cooldown configurável.

Teleporte/swap precisa ser validado no servidor e respeitar dimension/chunk/lifecycle do target.

## 7. Force-loading de chunks
A documentação afirma que shadows **force-load seus próprios chunks** para continuarem trabalhando longe do jogador.

Isso é superfície crítica em um pack grande: muitos shadows distribuídos podem manter chunks ativos e interagir com ServerCore/chunk loaders. Dismiss/death/revoke/restart precisam liberar tickets corretamente.

## 8. Progressão individual dos shadows
Shadows ganham XP por kills e level-up points, investidos em Health, Speed, Damage e Armor.

Dois sistemas opcionais expandem isso:
- **Shadow Fusion** — desligado por default; sacrifica shadow para elevar outro do mesmo tipo;
- **Shadow Equipment** — desligado por default; gear real via menu de 6 slots, com stats/damage derivados do equipamento.

Nenhum dos dois é marcado como ativo localmente sem leitura da config.

## 9. Progress Mode e títulos
`progressMode` é opcional/off by default. Quando habilitado, jogador também recebe XP de kills próprias e de shadows; levels aumentam capacidade ativa/armazenada.

Títulos publicados:
- **Necromancer:** níveis 1–25, +5% Umbral spell power;
- **Shadow Overlord:** 26–74, +15% spell power, +5% mana regen, +100 max mana;
- **Shadow Monarch:** 75+, +25% spell power, +15% mana regen, +300 max mana.

Ao atingir Shadow Monarch, o projeto também altera aparência das shadows e aplica aura de health/armor próxima.

## 10. Umbral Magic
ShadowsZ adiciona escola **Umbral** integrada a Iron's Spells, com spell power/resistance próprios. A documentação atual marca essa parte como “under progress”.

Spells publicados incluem Miasma, Umbral Bond e Aura of the Monarch. Tratar o sistema como em evolução: update do Iron's Spells ou do addon exige regressão de attributes, mana e spell registration.

## 11. Parties e friendly-fire
O projeto permite formar runtime teams. Party members, shadows e pets associados tornam-se aliados permanentes para impedir dano entre si segundo as regras do mod.

Isso precisa coexistir com teams/party systems externos sem criar duas fontes conflitantes de alliance. A ficha não presume integração automática com FTB Teams.

## 12. Smart shadow behavior
Comportamentos oficiais incluem:
- inventory/loot próprio;
- não queimar ao sol;
- não dropar loot próprio;
- impedir transforms vanilla indesejadas de shadow entities;
- interações especiais de alguns mobs;
- tameables convertidos tornam-se tamed ao owner;
- boss shadows sem boss music/health bar;
- morte do player recolhe army ativa de forma segura.

Essas regras transformam entities complexas e exigem regressão por provider modded.

## 13. Server tools
Ferramentas publicadas:
- `/gamerule shadowszRestrictPowers true`;
- `/shadowsz grant <players>`;
- `/shadowsz revoke <players>` — revoga poder, recolhe army e preserva roster;
- `/shadowsz levelplayer <n>`;
- `/shadowsz levelshadows <n>`.

Commands administrativos não devem ignorar persistence invariants nem duplicar army state.

## 14. Compatibilidades concretas no pack
O projeto declara dedicated support para Iron's Spells, Legendary Monsters, Bosses Rise, Monster Expansion, Mowzie's Mobs e L_Ender's Cataclysm.

No snapshot físico atual estão confirmados:
- **Iron's Spells 3.16.3**;
- **Legendary Monsters**;
- **Bosses'Rise 2.1.2**;
- **Mowzie's Mobs 1.8.2**;
- **L_Ender's Cataclysm 3.33**.

**Monster Expansion não foi encontrado top-level**, então não é marcado como integração ativa.

## 15. Delta exato da 1.1.9
A build instalada registra:
- shadows azul/preto por default e roxo/preto em Shadow Monarch;
- fix do max number of shadows na config;
- semi-compat com Bosses Rise, Monster Expansion, Mowzie's Mobs e Ender Cataclysm;
- otimizações;
- remoção do inventory button por incompatibilidades;
- fix de Tyros/ISS;
- ferramenta RestrictPowers/revoke;
- prevenção de transforms vanilla em shadows;
- fix de shadow Maledictus grab;
- fix de shadow Frostmaw congelando aliados/players.

Esses são regression gates diretos da 1.1.9.

## 16. Iron's Spells boundary
Iron's Spells fornece mana/attributes/spell infrastructure; ShadowsZ adiciona escola Umbral e usa Mana na chance de Arising.

Atualização do Iron's Spells para a versão física atual 3.16.3 exige validar attribute IDs, mana APIs, boss/minion integrations e Tyros fixes; não assumir que compat histórica garante todos os paths atuais.

## 17. Persistência e multiplayer
State que precisa sobreviver corretamente:
- power granted/revoked;
- roster e nomes;
- XP/levels/allocated stats;
- equipment/fusion state se habilitado;
- groups/hotkeys;
- shared storage;
- active/dismissed state;
- chunk tickets;
- party/alliance.

Dois players não podem compartilhar acidentalmente roster/storage/ownership. Reconnect deve restaurar somente o owner correto.

## 18. Configuração
O upstream descreve ampla configuração: simultaneous/stored shadow caps, arising chance/scaling, mana cost/difficulty, base strength, attack-order range, teleport cooldown, Monarch aura, XP curves, blacklists/protected entities, visual texture e toggles para leveling/fusion/equipment/Progress Mode.

A config local **não foi lida**; nenhum valor/cap/toggle é presumido ativo além dos defaults explicitamente documentados.

## 19. Riscos técnicos
1. **Chunk-ticket leak:** shadow mantém área carregada após dismiss/death/revoke.
2. **Roster duplication:** mesma entidade/shadow é registrada duas vezes.
3. **Storage dupe/loss:** loot existe simultaneamente em world/inventory/storage.
4. **Modded mob incompat:** AI/boss state não tolera conversão.
5. **Friendly-fire drift:** party/shadow alliance diverge de teams externos.
6. **Iron's API drift:** mana/attributes/spells deixam de resolver.
7. **Position Swap safety:** destino inválido, unloaded ou perigoso.
8. **Death/relog ghost army:** entidades continuam ativas sem owner state coerente.
9. **Fusion/equipment duplication:** sacrificial shadow/item não é liquidado atomicamente.
10. **Performance:** army ampla + force-loaded chunks + modded AI pressiona TPS.

## 20. Matriz de testes
- [ ] Dedicated server inicia com ShadowsZ 1.1.9 + Iron's Spells 3.16.3.
- [ ] Claim/reject/grant/revoke respeitam gamerule e server authority.
- [ ] Shadow Eyes/Arising permite no máximo 3 tentativas e uma conversão válida.
- [ ] Roster, naming, stances e hotkeys persistem após relog/restart.
- [ ] Storage não duplica loot em pickup/dismiss/death.
- [ ] Position Swap funciona apenas com target válido e cooldown correto.
- [ ] Chunk tickets são liberados após dismiss/revoke/death.
- [ ] Shadow XP/stat points persistem e não duplicam kills.
- [ ] Fusion/equipment, se habilitados, são atômicos.
- [ ] Progress Mode/titles aplicam os attributes publicados uma única vez.
- [ ] Bosses'Rise/Mowzie/Cataclysm/Legendary Monsters shadows não atacam owner/allies incorretamente.
- [ ] Maledictus grab e Frostmaw friendly-freeze permanecem corrigidos.
- [ ] Dois players mantêm roster/storage/party ownership separados.
- [ ] Stress com army grande é medido com profiler e chunk-ticket count.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 21. Evidências e limites
- Modlist física atual: ShadowsZ 1.1.9; Iron's Spells 3.16.3; Bosses'Rise, Legendary Monsters, Mowzie's Mobs e Cataclysm presentes.
- CurseForge oficial do projeto: workflow completo de army, progression, storage, parties, config, server tools e compats.
- Changelog oficial 1.1.9: deltas e fixes específicos acima.
- **Limite:** Monster Expansion não foi encontrado na modlist; config local não foi aberta; sistemas marcados upstream como opcionais/under progress não são tratados como garantidamente ativos.
