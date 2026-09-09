# Companions!

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db815ca826f160ef858208
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Companions!
- **Arquivo JAR:** `companions-neoforge-1.21.1-1.3.2.jar`
- **Versão 1.21.1:** `1.3.2`
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — tame/owner/summon states, healing, combat/AI, boss/content authority, config, lifecycle e regressões 1.3.2 catalogados.
- **Categoria:** Mobs; RPG; Exploração
- **Compatibilidade/Riscos:** Interage com tame/owner AI, summons, healing, combat cooldowns, target selection, boss/spawn e animação. 1.3.2 corrige cooldown infinito de ataques, hostile imps sem perseguição, Dinamo atingindo alvos duplicados e altura de puppet cannon stakes; regression gates obrigatórios.
- **Decisão:** Sem decisão
- **Dependências:** A superfície oficial recuperada confirma build NeoForge 1.21.1 Client & Server e compat/recomendação de JEI para recipes. Nenhuma hard dependency adicional foi promovida sem metadata versionada explícita nesta auditoria.
- **Estado da pesquisa:** Verificado
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/companions-mod
- **Função:** Mod de conteúdo que adiciona criaturas domesticáveis chamadas companions, cada uma com habilidades próprias, além de mobs hostis, armas, boss e progressão/combate associados.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Companions! 1.3.2 foi reconfirmado fisicamente e reconstruído ao padrão técnico. A presença do conteúdo no pack não foi convertida em decisão automática de manter/remover.
- **Observações:** mod id `companions`; runtime 1.3.2. Companions possuem estados wandering/sitting/following; podem ser curados com Small/Great Essence obtidas de mobs hostis, enquanto summoned companions não podem ser curados. Summons podem seguir o companion ou o player.
- **Procedência:** Modlist física canônica de 07/09/2026 + runtime 1.3.2 + CurseForge oficial Companions! 1.3.2 NeoForge 1.21.1 e descrição/changelog oficiais.
- **Sobreposição:** Compartilha eixos de pets/mobs/RPG com outros mods, mas suas entidades, abilities, boss, weapons e healing economy são provider-owned. Integrações devem evitar double damage/heal/summon settlement.
- **Data da última decisão:** vazio

## Dossiê operacional — padrão Alex's Mobs

> 🐾 Versão física confirmada: `companions-neoforge-1.21.1-1.3.2.jar`, mod id `companions`, runtime `1.3.2`, NeoForge 1.21.1. Companions! é um **provider de entidades domesticáveis, summons, mobs hostis, armas e boss**, não apenas uma camada cosmética de pets.

## 1. Papel e authority
Companions! adiciona criaturas domesticáveis com abilities próprias, mobs hostis, armas e um boss. O mod controla identidade, AI, tame/owner state, abilities, healing e lifecycle de suas entidades.
Integrações externas de RPG/combat devem observar o resultado final do provider em vez de reaplicar damage, heal, summon ou target selection.

## 2. Estados dos companions
A documentação oficial descreve três estados principais para companions domesticados:
- wandering;
- sitting;
- following player.
Mudança de estado precisa permanecer server-authoritative e persistir corretamente em save/restart. A animação/feedback visual não pode ser usada como única prova de que o companion está sentado ou seguindo.

## 3. Taming e ownership
Taming cria uma relação de owner que deve sobreviver a chunk unload, server restart e dimension transitions quando a entidade continuar válida.
A 1.3.2 corrige uma falha de attack cooldown que também podia impedir a conclusão do tame de **Cornelius**, portanto tame + combat state são superfícies acopladas que exigem regression testing.

## 4. Healing economy
A documentação oficial informa que companions podem ser curados com **Small Essence** e **Great Essence**, obtidas de mobs hostis do mod.
Summoned companions seguem regra diferente: **não podem ser curados** por esse fluxo. Uma integração externa não deve ignorar essa distinção nem permitir que um summon seja convertido em pet persistente por um heal hook genérico.

## 5. Summons
Alguns companions podem gerar summons. A documentação informa que summons podem seguir o companion ou o player conforme o comportamento implementado.
Owner/caster attribution deve permanecer consistente; death, despawn, chunk unload ou remoção do summoner não pode deixar summon órfão indefinidamente sem regra explícita do provider.

## 6. Mobs hostis, boss e weapons
Além de companions, o projeto adiciona inimigos com skills próprias, armas e um boss. Essas entidades/itens pertencem ao namespace e progression loop do provider.
Loot, damage, status effects e boss progression não devem ser duplicados por uma bridge apenas por reconhecer o tipo de mob/item.

## 7. Release 1.3.2 — regression gates
O changelog 1.3.2 registra quatro correções concretas:
1. companions entrando em **attack cooldown infinito**, impedindo dano e afetando o tame de Cornelius;
2. hostile imps não perseguindo corretamente o alvo atual;
3. **Dinamo** selecionando/atingindo alvos duplicados;
4. stakes do **Puppet Cannon** surgindo/voando abaixo da altura pretendida.
Esses quatro casos devem ser testados especificamente na build física.

## 8. Target selection e causalidade de combate
AI e abilities podem selecionar múltiplos alvos. O fix de Dinamo mostra risco real de duplicação de target/settlement.
Combat bridges, perks e damage listeners devem usar IDs/eventos finais para evitar aplicar um efeito duas vezes quando uma única ability gera múltiplas fases visuais ou entidades auxiliares.

## 9. Configuração e descoberta de recipes
O projeto se descreve como altamente configurável. Valores exatos não são congelados nesta ficha quando não há pin de config da build.
JEI é recomendado para visualizar recipes; JEI é apresentação/descoberta e não authority do recipe manager.

## 10. Client/server e multiplayer
AI, tame/owner state, summon ownership, healing, damage, cooldowns, loot e boss state são server-authoritative. Models, animations, particles e screens são client presentation.
Em multiplayer, dois jogadores não podem adquirir ownership simultâneo do mesmo companion nem creditar heal/damage duas vezes por latência/retry.

## 11. Lifecycle
Validar tame, sit/follow/wander, summon spawn/despawn, companion death, player death, chunk unload/reload, dimension change, disconnect/reconnect e server restart.
Owner IDs, current target, cooldown e summon references não podem ficar stale entre transições.

## 12. Sobreposição no pack
- outros pet/companion mods: coexistência temática não prova duplicação;
- sistemas RPG/attributes: podem modificar stats finais, mas não devem substituir tame/owner AI;
- combat frameworks: observar damage final sem double-proc;
- animation libraries/mods: apresentação não deve controlar AI state.

## 13. Riscos
1. Attack cooldown travado — regressão 1.3.2.
2. Target selection duplicada.
3. Hostile mob perder pursuit state.
4. Summon perder owner/caster.
5. Heal aplicado a summoned companion apesar da regra do provider.
6. Tame state perdido em reload/dimension.
7. Combat bridge reaplicar damage/ability.
8. Boss/loot progression duplicada por quest scripts.

## 14. Matriz de testes
1. Dedicated server boot e registry de entities/items.
2. Tame de companions selecionados, incluindo Cornelius.
3. Sit/follow/wander + restart.
4. Heal com Small/Great Essence; confirmar impossibilidade em summoned companion.
5. Summons seguindo companion/player e cleanup no despawn/death.
6. Regression 1.3.2: attack cooldown não fica infinito.
7. Hostile imps perseguem target.
8. Dinamo não duplica target/settlement.
9. Puppet Cannon stakes na altura correta.
10. Multiplayer: owner, heal e damage exatamente uma vez.
11. Chunk unload/dimension/reconnect com companion ativo.

## 15. Evidência
- modlist física atual: Companions! 1.3.2;
- CurseForge oficial: companions tameáveis com abilities, mobs hostis, weapons e boss;
- descrição oficial: estados wandering/sitting/following, Small/Great Essence e regra distinta para summoned companions;
- changelog 1.3.2: fixes de cooldown, imps, Dinamo e Puppet Cannon stakes.

> 🛡️ Boundary canônico: Companions! controla **entidades, ownership, AI, summons e abilities próprias**. Integrações externas devem observar esse state, não criar um segundo sistema de pet/combate concorrente.
