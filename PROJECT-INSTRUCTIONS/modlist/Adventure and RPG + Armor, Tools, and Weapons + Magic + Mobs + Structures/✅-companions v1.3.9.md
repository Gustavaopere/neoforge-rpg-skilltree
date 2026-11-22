# Companions! — versão 1.3.9 (atualização declarada em 22/11/2026)

> **Estado vigente — 22/11/2026:** versão `1.3.9`, JAR oficial `companions-neoforge-1.21.1-1.3.9.jar`, **instalação declarada pelo usuário**; [CurseForge file 9732364](https://www.curseforge.com/minecraft/mc-mods/companions-mod/files/9732364). Posição histórica preservada. A confirmação deriva do pedido, não de novo dump do diretório local.

> **Mudanças e dependências:** Cadeia 1.3.5 → 1.3.9 já auditada individualmente no dossier. Inclui correções e evolução da mecânica de companions, taming, AI, ownership e demais melhorias documentadas pelas releases. Dependências KnightLib do inventário anterior serão reconciliadas quando seus dossiers forem processados. Notas anteriores sobre 'não instalado' descrevem o estado **anterior a esta sincronização**; não substituir por alegação de smoke test.


> **Autoridade física atual — 22/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#104**: JAR `companions-neoforge-1.21.1-1.3.4.jar`, mod id `companions`, runtime `1.3.4`, SHA-1 `23f6e4f27a457a8d016412e495e417c0b36fdcc1`.

## Propriedades do registro

- **Mod:** Companions!
- **Arquivo JAR instalado (declarado pelo usuário):** `companions-neoforge-1.21.1-1.3.9.jar` — arquivo oficial CurseForge 9732364.
- **Versão instalada declarada (Minecraft 1.21.1 / NeoForge):** `1.3.9`; declaração expressa do usuário em 22/11/2026.
- **Categoria:** Mobs; RPG; Exploração
- **Decisão:** Sem decisão
- **Estado da pesquisa:** histórico incremental já auditado e versão de distribuição identificada; execução do pack e compatibilidade global não testadas.
- **Estado no pack:** `1.3.9` instalado **conforme declaração do usuário**, sincronizado no inventário documental GitHub; JAR novo não lido diretamente no computador.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/companions-mod
- **Função:** Mod de conteúdo que adiciona criaturas domesticáveis chamadas companions, cada uma com habilidades próprias, além de mobs hostis, armas, boss e progressão/combate associados.
- **Dependências:** NeoForge 1.21.1; KnightLib 2.0.0+ pela compatibilidade da 1.3.4. Pack físico usa KnightLib 2.0.1. JEI permanece recomendado para descoberta de recipes; nenhuma outra hard dependency foi promovida sem metadata explícita.
- **Compatibilidade/Riscos:** Interage com tame/owner AI, summons, healing, combat cooldowns, target selection, boss/spawn, animação e KnightLib. Gates herdados: cooldown infinito, hostile imps, Dinamo e Puppet Cannon. 1.3.3 corrige Demon Flesh/JER; 1.3.4 adiciona KnightLib 2.0.0+ e tenta corrigir Mutated/Holy Teddy e Golden Allay atravessando terreno. Upstream 1.3.5 altera o Heal Book e cancela fall damage do Tamed Minion, exigindo regressão de healing/damage attribution.
- **Sobreposição:** Compartilha eixos de pets/mobs/RPG com outros mods, mas suas entidades, abilities, boss, weapons e healing economy são provider-owned. Integrações devem evitar double damage/heal/summon settlement.
- **Observações (estado vigente declarado):** distribuição `companions-neoforge-1.21.1-1.3.9.jar` / `1.3.9`, CurseForge 9732364. Antigos nomes de JAR, hashes e versões descritos nas demais seções representam snapshots históricos, não o pedido atual.
- **Procedência:** posição física herdada do snapshot de setembro de 2026; versão vigente declarada no pedido de 22/11/2026, filename de [CurseForge, arquivo 9732364](https://www.curseforge.com/minecraft/mc-mods/companions-mod/files/9732364). Sem hash da cópia local nova ou teste runtime.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Companions! 1.3.2 foi reconfirmado fisicamente e reconstruído ao padrão técnico. A presença do conteúdo no pack não foi convertida em decisão automática de manter/remover.
- **Atualização/Status:** 22/11/2026 — `1.3.4` → `1.3.9` confirmado pelo pedido do usuário para atualização do inventário. Cadeia 1.3.5 → 1.3.9 já auditada individualmente no dossier. Inclui correções e evolução da mecânica de companions, taming, AI, ownership e demais melhorias documentadas pelas releases. Dependências KnightLib do inventário anterior serão reconciliadas quando seus dossiers forem processados.
- **Data da última decisão:** não definida

# Dossiê operacional — padrão Alex's Mobs
> 🐾 Versão física confirmada: `companions-neoforge-1.21.1-1.3.4.jar`, mod id `companions`, runtime `1.3.4`, NeoForge 1.21.1. Companions! é um **provider de entidades domesticáveis, summons, mobs hostis, armas e boss**, não apenas uma camada cosmética de pets.
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
## 7. Releases 1.3.2 → 1.3.4 — regression gates
A 1.3.2 registrou quatro correções concretas que continuam como gates herdados:
1. companions entrando em **attack cooldown infinito**, impedindo dano e afetando o tame de Cornelius;
2. hostile imps não perseguindo corretamente o alvo atual;
3. **Dinamo** selecionando/atingindo alvos duplicados;
4. stakes do **Puppet Cannon** surgindo/voando abaixo da altura pretendida.
A 1.3.3 elevou o mínimo de KnightLib para 1.6.2 e corrigiu **Demon Flesh aparecendo na loot table de todas as entidades no Just Enough Resources**.
A 1.3.4, instalada atualmente, adiciona compatibilidade com **KnightLib 2.0.0+** e publica tentativa de correção para **Mutated/Holy Teddy** e **Golden Allay** atravessando/afundando no terreno. O pack usa KnightLib 2.0.1.
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
1. Attack cooldown travado — regressão histórica da 1.3.2.
2. Target selection duplicada.
3. Hostile mob perder pursuit state.
4. Summon perder owner/caster.
5. Heal aplicado a summoned companion apesar da regra do provider.
6. Tame state perdido em reload/dimension.
7. Combat bridge reaplicar damage/ability.
8. Boss/loot progression duplicada por quest scripts.
9. Mutated/Holy Teddy ou Golden Allay voltarem a atravessar o terreno após regressão.
10. KnightLib API drift após a transição para a linha 2.0.x.
## 14. Matriz de testes
1. Dedicated server boot e registry de entities/items.
2. Tame de companions selecionados, incluindo Cornelius.
3. Sit/follow/wander + restart.
4. Heal com Small/Great Essence; confirmar impossibilidade em summoned companion.
5. Summons seguindo companion/player e cleanup no despawn/death.
6. Regressões 1.3.2: attack cooldown não fica infinito; hostile imps mantêm pursuit; Dinamo não duplica target; Puppet Cannon respeita altura.
7. Demon Flesh não aparece globalmente em loot/JER fora dos alvos corretos.
8. Mutated/Holy Teddy e Golden Allay não atravessam/afundam no terreno.
9. Compatibilidade com KnightLib 2.0.1 carrega sem API/registry error.
10. Multiplayer: owner, heal e damage exatamente uma vez.
11. Chunk unload/dimension/reconnect com companion ativo.
## 15. Evidência
- modlist física de 17/09/2026: Companions! 1.3.4 e KnightLib 2.0.1;
- descrição oficial: companions tameáveis com abilities, mobs hostis, weapons e boss; estados wandering/sitting/following, Small/Great Essence e regra distinta para summoned companions;
- changelog 1.3.2: fixes de cooldown, imps, Dinamo e Puppet Cannon stakes;
- changelog 1.3.3: minimum KnightLib 1.6.2 e fix de Demon Flesh/JER;
- changelog 1.3.4: compatibilidade com KnightLib 2.0.0+ e tentativa de fix para Mutated/Holy Teddy e Golden Allay atravessando o terreno.
> 🛡️ Boundary canônico: Companions! controla **entidades, ownership, AI, summons e abilities próprias**. Integrações externas devem observar esse state, não criar um segundo sistema de pet/combate concorrente.

> **Atualização/Status — valor histórico preservado do Notion:** PADRÃO ALEX'S MOBS REVALIDADO EM 20/09/2026 — reconciliação final física #103: `companions-neoforge-1.21.1-1.3.4.jar` / `1.3.4` conferidos contra a modlist atual; KnightLib 2.0.x, Demon Flesh/JER, fixes de entidades atravessando terreno e regressões anteriores preservados.

## 16. Atualização upstream 1.3.5 — não instalada
A autoridade física continua em **Companions! 1.3.4**. A release **1.3.5** para NeoForge 1.21.1 foi publicada em 24/09/2026; não há release intermediária entre 1.3.4 e 1.3.5 na linha aplicável.

O changelog oficial contém dois deltas funcionais:
- o **Heal Book** passa a curar corretamente mobs **tamed** e **passive**, e a causar dano corretamente em inimigos **undead**;
- o **Tamed Minion companion** deixa de receber **fall damage**.

Essas mudanças são relevantes para o pack porque alteram diretamente settlement de cura/dano e um caso de dano ambiental de companion. Uma integração externa de RPG/healing não deve aplicar cura adicional sobre o mesmo evento do Heal Book nem reintroduzir fall damage por listener genérico.

Gate de promoção 1.3.4→1.3.5: Heal Book em companion domesticado, mob passivo e undead hostil; owner/target attribution; múltiplos alvos sem double-heal/double-damage; Tamed Minion em quedas de alturas variadas; death/respawn/relog; KnightLib 2.0.1; regressões herdadas de cooldown, Dinamo, Puppet Cannon e phasing de Teddy/Golden Allay.

Fonte upstream: CurseForge Companions! file ID 8962932, release 1.3.5 para NeoForge 1.21.1.

## 17. Atualização upstream 1.3.6 — 10/10/2026

**Físico comprovado:** `companions-neoforge-1.21.1-1.3.4.jar` / 1.3.4, com KnightLib 2.0.1 no snapshot. A seção 16 cobre **Companions! 1.3.5 (24/09)**: Heal Book cura mobs tamed/passive e atinge undead corretamente; Tamed Minion deixa de sofrer fall damage.

**Nova release 1.3.6 (04/10/2026; CurseForge file 9056538)** para NeoForge Minecraft 1.21.1:
- corrige **crash no cliente provocado pela animação sweeping dos Gloves**;
- acrescenta **configuração por itens e item tags** para custos/pagamentos de invocação de **Cornelius em copper, Nether e End**.

Os pagamentos configuráveis podem afetar progressão, recipes, KubeJS, obtenção de recursos e decisões de quests; a lista exata de tags/defaults deve ser extraída da configuração da build antes de definir receitas de progressão. A correção de animação é do **cliente** e não demonstra alteração server-side no dano.

**Cadeia:** 1.3.4 → 1.3.5 → 1.3.6; nenhuma dessas atualizações foi fisicamente confirmada no pack.

**QA:** Gloves sweeping com Epic Fight/resource packs; Cornelius summon com tags vanilla/modded, pagamentos insuficientes e consumo exactly-once; invocações nas três rotas; Heal Book em tamed, passive e undead sem aplicação dupla; fall damage de Tamed Minion; KnightLib compatibility e startup dedicado; multiplayer ownership, save/restart e cross-dimension travel.

**Fonte:** https://www.curseforge.com/minecraft/mc-mods/companions-mod/files/9056538

**Estado:** runtime físico 1.3.4 preservado; upstream 1.3.6 registrada e testes ainda pendentes.

## Auditoria incremental 1.3.4 → 1.3.8 (17/11/2026)

**Fonte física:** posição #104, `companions-neoforge-1.21.1-1.3.4.jar`, runtime 1.3.4, SHA-1 `23f6e4f27a457a8d016412e495e417c0b36fdcc1`. A candidata upstream **NeoForge 1.21.1 1.3.8** é **não instalada**. Projeto oficial CurseForge **1300341**; pasta temática completa `Adventure and RPG + Armor, Tools, and Weapons + Magic + Mobs + Structures` já correta.

| Release NeoForge Minecraft 1.21.1 | Data | Delta relevante | Fonte |
|---|---|---|---|
| **1.3.5** | 24/09/2026 | Heal Book passa a curar tamed/passive e danificar undead corretamente; Tamed Minion deixa de sofrer fall damage (detalhes na seção 16). | [CurseForge 8962932](https://www.curseforge.com/minecraft/mc-mods/companions-mod/files/8962932) |
| **1.3.6** | 04/10/2026 | Corrige crash na animação sweeping dos Gloves; pagamentos da invocação Cornelius em cobre/Nether/End passam a aceitar itens e tags via configuração (seção 17). | [CurseForge 9056538](https://www.curseforge.com/minecraft/mc-mods/companions-mod/files/9056538) |
| **1.3.7** | 05/11/2026 | Crystallized Blood Scythe aceita encantamentos de armas além de mineração; Eternal Lighter e Wrench aceitam Unbreaking/Mending; trilhas e movimento dos projéteis Blood Slash/Tornado melhorados; weapons/armor aceitam encantamentos vanilla em mesa e anvil. | [CurseForge 9457175](https://www.curseforge.com/minecraft/mc-mods/companions-mod/files/9457175) |
| **1.3.8** | 16/11/2026 | Livros mágicos podem ser guardados em **chiseled bookshelves**, com impacto de armazenamento e persistência; sem outro delta anunciado na nota específica. | [CurseForge 9640009](https://www.curseforge.com/minecraft/mc-mods/companions-mod/files/9640009) |

**Sequência publicada integral auditada:** 1.3.5 → 1.3.6 → 1.3.7 → 1.3.8, sem pular releases NeoForge 1.21.1. **Gates de instalação:** KnightLib presente 2.0.1 contra manifest do candidato, Heal Book, Cornelius, Gloves/Epic Fight, enchantments, projéteis, chiseled bookshelves, tame/ownership, multiplayer, startup dedicado e save/restart. Não houve instalação ou teste em jogo. **Dossier continua v1.3.4**, coerente com JAR comprovado.

## Auditoria incremental upstream — Companions! 1.3.9 (22/11/2026; não instalada)

Baseline física #104 `companions-neoforge-1.21.1-1.3.4.jar`, runtime `1.3.4`. As distribuições intermediárias **1.3.5, 1.3.6, 1.3.7 e 1.3.8** já estão discriminadas no dossier e não foram repetidas.

A [release oficial 1.3.9 para NeoForge 1.21.1, CurseForge 9732364](https://www.curseforge.com/minecraft/mc-mods/companions-mod/files/9732364), de **21/11/2026**, concentra-se no **Black Hole**:
- Impede a entidade/efeito de atravessar paredes ou tetos ao atingir um bloco.
- Ao encontrar superfícies de **água ou lava**, o Black Hole passa a parar na superfície em vez de afundar até o fundo.
- Corrige o **flash visual de abertura** e o **som de liberação**, que não eram disparados.
- Corrige nuvens renderizadas indevidamente **sobre** o Black Hole.

**Impacto e teste:** colisão e posição do Black Hole em blocos/fluidos influenciam legibilidade espacial e comportamento de combate; validar cliente/servidor, física, visuais e sons com shaders, save/relog, spawn em água/lava e interação com outros efeitos. O changelog não anuncia mobs, itens, boss ou progressão novos nesta release. **Instalação 1.3.4 e pasta já categorizada preservadas; QA 1.3.9 não executado.**


## Sincronização declarada da instalação — 22/11/2026

**Atualização documental:** `1.3.4` → `1.3.9`; JAR oficial `companions-neoforge-1.21.1-1.3.9.jar`; registro da versão como instalada por **declaração expressa do usuário**. As seções históricas sobre a versão antiga permanecem para rastreabilidade, mas seus termos 'atual'/'não instalado' são anteriores ao pedido.

**Resumo técnico auditado:** Cadeia 1.3.5 → 1.3.9 já auditada individualmente no dossier. Inclui correções e evolução da mecânica de companions, taming, AI, ownership e demais melhorias documentadas pelas releases. Dependências KnightLib do inventário anterior serão reconciliadas quando seus dossiers forem processados.

**Requisitos entre lotes:** divergências de dependências no inventário escrito devem ser revisadas quando o respectivo provider chegar à sua vez, sem bloquear a atualização documental deste item. Não afirmar que o conjunto inteiro foi executado/testado ou que o JAR novo foi obtido da instalação local.

**Fonte da distribuição:** https://www.curseforge.com/minecraft/mc-mods/companions-mod/files/9732364
