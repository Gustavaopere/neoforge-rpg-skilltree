# Acolyte

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c769db9f0db818bb27ecb761143e405
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Acolyte
- **Arquivo JAR:** `acolyte-1.0.3.jar`
- **Versão 1.21.1:** 1.0.3
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia, RPG, Mobs, Exploração
- **Função:** Addon de conteúdo/worldgen para Iron's Spells: Horn Merchant; Human/Demon Mage, Warrior e Archer com biome variations; Lieutenant demon mini-boss; 18 estruturas publicadas (5 Taverns, 7 Watchtowers, 2 Towers, 3 Castles, 1 Lieutenant Castle); recruit system por esmeraldas por tempo limitado; Demon Horn economy para scrolls/magic loot. Usa Iron's como authority de magia.
- **Dependências:** REQUIRED upstream: Iron's Spells 'n Spellbooks. Runtime do pack: Iron's 1.21.1-3.16.3. Nenhuma segunda hard dependency é publicada na relação CurseForge da release 1.0.3.
- **Sobreposição:** Estruturas se sobrepõem em finalidade a dungeon/settlement packs; spellcasters se sobrepõem tematicamente a mobs mágicos de outros addons; recruitment cruza companion systems. Nenhuma dessas sobreposições é incompatibilidade formal sem conflito de placement/AI/ownership comprovado.
- **Compatibilidade/Riscos:** Alta superfície de worldgen/entidades. O pack possui muitos structure providers e world scaling; validar placement/densidade, biome tags, loot e dificuldade do Lieutenant. Epic Fight/EFIS e outros patches podem alterar animação/AI. Recruit system toca followers/summons, porém é contratação temporária, não familiar/summon persistente. Upstream não publica spell loadouts exatos, biome mapping, duration, template IDs/weights ou trade tables no overview; esses detalhes devem ser extraídos do JAR/data/runtime antes de integrações.
- **Observações:** Não inventar duração do contrato, custo exato em emeralds, spell list por archetype, biome list concreta, loot pool ou structure weights: o overview upstream não publica esses valores. Antes de quest/perk/compat, extrair os dados reais da 1.0.3.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge/Modrinth oficiais Acolyte e fontes já auditadas no dossiê. Reconciliação final: JAR/runtime permanecem exatamente `acolyte-1.0.3.jar` / `1.0.3`; sem divergência física.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/acolyte
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #4: `acolyte-1.0.3.jar` / `1.0.3` conferidos contra a modlist atual; corpo técnico, decisão e estado preservados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

> 🔎 **Escopo canônico.** Runtime físico: `acolyte-1.0.3.jar`, NeoForge 1.21.1. Acolyte é um addon de **mobs + estruturas + recrutamento + economia mágica** sobre Iron's Spells. O overview público lista o conteúdo macro, mas não publica vários valores internos; esta ficha marca esses limites em vez de inventá-los.

## 1. Dependência e authority mágica
CurseForge relations declara apenas:
- **Iron's Spells 'n Spellbooks — Required Dependency**.

Runtime atual do pack:
`irons_spellbooks-1.21.1-3.16.3.jar`

Acolyte usa o ecossistema de spellcasting do Iron's. Portanto mana, spell registry, schools e infraestrutura de cast continuam sob a authority de Iron's.

## 2. Roster de entidades publicado
O publisher lista sete famílias/roles principais:
1. Horn Merchant.
2. Human Mage.
3. Demon Mage.
4. Human Warrior.
5. Demon Warrior.
6. Human Archer.
7. Demon Archer.
8. Lieutenant — mini-boss demoníaco.

Mages, Warriors e Archers possuem **biome variations** segundo o overview.

## 3. Horn Merchant
Descrito como **magic wandering trader**. Sua função é conectar exploração/combate demoníaco à economia de scrolls e itens mágicos.

Fluxo publicado:
- jogador obtém **Demon Horns**;
- Horn Merchant aceita horns;
- jogador compra **scrolls e magic stuff**.

### O que a fonte pública NÃO informa
- tabela completa de trades;
- preço de cada trade;
- stock/max uses;
- refresh/restock;
- frequência/spawn rule do merchant;
- se todos os scroll tiers podem aparecer;
- weight/chance de cada oferta.

Antes de balancear economia/quests, extrair trade data real do JAR/runtime 1.0.3.

## 4. Human Mage
Spellcaster humano com **variações por bioma**.

O overview confirma archetype e biome variation, mas não publica:
- lista de spells;
- school(s);
- spell level;
- cooldown;
- equipment table;
- health/armor/damage;
- biome mapping exato.

### Integração
Como caster de Iron's, testar cast goals, targeting, line of sight, mana/cooldown interno e compat com Epic Fight/entity animation patches.

## 5. Demon Mage
Equivalente demoníaco do caster, também com biome variations.

Sua existência estabelece faction/hostile magic content, mas o overview não fornece spellbook completo ou atributos. Não assumir que “Demon Mage = Human Mage com skin diferente”; confirmar data/classes no runtime antes de quest/perk por archetype.

## 6. Human Warrior
Combatente melee humano com variações por bioma.

O archetype é relevante ao **recruit system** e aos encontros de estrutura. Valores de weapon/armor/AI não estão publicados no overview e devem ser inspecionados antes de scaling custom.

## 7. Demon Warrior
Combatente melee demoníaco, também com biome variations.

Interações críticas:
- Epic Fight combat patching;
- World Scaling do projeto;
- armor/damage modifiers;
- loot de Demon Horn quando aplicável — confirmar no runtime, não presumir por nome.

## 8. Human Archer
Archetype ranged humano com biome variations.

Testar:
- projectile type;
- attack interval;
- friendly-fire quando recrutado;
- distância de follow/target;
- interaction com combat mods.

O overview não publica esses números.

## 9. Demon Archer
Archetype ranged demoníaco com biome variations.

Não presumir que usa arrow vanilla ou mesma AI do Human Archer sem verificar entidade/goal real.

## 10. Lieutenant — mini-boss
O publisher descreve o **Lieutenant** como mini-boss demoníaco e fornece uma estrutura exclusiva: Lieutenant Castle.

### Superfícies obrigatórias
- boss/miniboss health/damage;
- AI e spell/melee/ranged behavior real;
- loot;
- Demon Horn economy;
- world scaling;
- structure spawn;
- death/advancement quando existir.

Nenhum número concreto deve ser inventado a partir do rótulo “mini boss”.

## 11. Estruturas — contagem total publicada
Acolyte publica **18 estruturas** no total:
- **5 Taverns**;
- **7 Watchtowers**;
- **2 Towers**;
- **3 Castles**;
- **1 Lieutenant Castle**.

Essa contagem é muito mais importante para o pack do que dizer apenas “adiciona estruturas”: são 18 templates/variantes declaradas entrando num mundo que já possui um stack muito grande de structures.

## 12. Taverns — 5 variantes
Função provável/publicamente contextual: pontos ligados a humanos/recrutamento/exploração, mas o overview não detalha occupant table ou loot de cada variante.

Para cada uma das cinco:
- localizar/generar;
- registrar biome/dimension/placement;
- occupants;
- chests/loot tables;
- recruitment NPCs;
- collision com villages/roads/other structures;
- bounding box.

## 13. Watchtowers — 7 variantes
Sete variantes indicam forte presença de outposts/encounters.

Testar individualmente:
- terrain adaptation;
- se ficam suspensas/enterradas;
- acesso vertical;
- mobs spawnados;
- chest loot;
- biome restriction;
- distância entre estruturas.

## 14. Towers — 2 variantes
Duas Towers separadas das Watchtowers. Não fundir as categorias em documentação: o publisher conta **7 watchtowers + 2 towers**.

O conteúdo interior/encounter precisa ser extraído do runtime/data pack porque o overview não enumera rooms/mobs.

## 15. Castles — 3 variantes
Três castles comuns além do Lieutenant Castle.

Como são estruturas maiores, verificar:
- jigsaw/template assembly se usado;
- terrain intersection;
- loot density;
- mob density;
- performance na geração;
- sobreposição espacial com dungeons/settlements.

## 16. Lieutenant Castle — 1
Estrutura exclusiva do miniboss. É um ponto de progressão potencial e precisa de teste end-to-end:
1. localizar/generar;
2. entrar;
3. confirmar Lieutenant;
4. matar;
5. loot/reward;
6. retorno/revisit;
7. respawn ou não do boss conforme lógica real.

Não definir respawn behavior sem runtime.

## 17. Recruit system
Feature publicada: **hire allies for a limited time in exchange for emeralds**.

Elementos confirmados:
- custo usa emeralds;
- aliado é temporário;
- sistema é de recruitment/hire.

### Dados não publicados no overview
- custo exato;
- duração exata;
- quais archetypes podem ser contratados;
- máximo de recruits;
- owner UUID/persistence;
- reação à morte do owner;
- dimension transfer;
- relog;
- friendly-fire;
- refund/renewal.

Esses pontos devem ser medidos no runtime antes de qualquer integração de companions.

## 18. Recruitment vs familiars/summons
No pack existem sistemas como Alshanex's Familiars, summons de Iron's e outros minions.

Acolyte é semanticamente diferente:
- custo econômico em emeralds;
- contrato limitado no tempo;
- aliado/NPC recrutado.

Não converter recruits em “summon genérico” na árvore RPG sem redesign, porque isso altera a identidade econômica e temporal do sistema.

## 19. Horn economy
Feature publicada: **buy scrolls and magic stuff in exchange for demon horns**.

Isso cria um loop:
1. enfrentar/explorar conteúdo demoníaco;
2. obter Demon Horns pela rota real;
3. encontrar Horn Merchant;
4. converter horns em magia/scrolls.

### Risco econômico
Qualquer perk/mod que aumente drops ou duplique loot pode acelerar acesso a scrolls. Antes de buffar Demon Horn yield, quantificar o trade pool real.

## 20. Biome variations
Human/Demon Mage/Warrior/Archer são descritos com biome variations.

Variação pode significir texture/model/equipment/behavior/spawn — o overview não especifica quais dimensões variam. Portanto a auditoria runtime deve registrar por variante:
- biome condition;
- visual;
- equipment;
- stats;
- spell/AI differences;
- registry entity type vs variant data.

## 21. Worldgen no pack
O pack possui grande quantidade de structures/worldgen mods. Acolyte acrescenta 18 variantes, então riscos reais incluem:
- saturação visual de estruturas;
- competição por terreno;
- estruturas próximas demais;
- incompatibilidade de biome tags;
- castle/tower cortados por relevo extremo;
- loot density excessiva;
- chunk-generation cost.

Sobreposição por categoria **não é incompatibilidade formal**, mas precisa de amostragem estatística no mundo.

## 22. Amplified/overhauled terrain
Em regiões onde terrain mods alteram fortemente relevo, testar placement de towers/castles para:
- foundation;
- air gaps;
- buried entrances;
- water/lava intersection;
- cliffs.

A ficha não afirma que Acolyte suporta ou não cada terrain provider; exige evidência runtime.

## 23. Epic Fight e EFIS
O pack usa Epic Fight e compat de Iron's animations. Acolyte adiciona spellcasters, warriors e archers.

Testar:
- melee warrior attack animation;
- archer draw/fire;
- mage cast;
- knockback/stagger;
- Lieutenant;
- recruited ally.

Se um entity não possui patch Epic Fight específico, não declarar incompatibilidade; registrar o comportamento observado.

## 24. World Scaling / RPG Skill Tree
Acolyte não deve manter um segundo nível paralelo se o projeto próprio possui world scaling canônico.

Para cada combat entity:
- passar pelo pipeline canônico de scaling se aplicável;
- evitar re-aplicar health/damage multiplicador duas vezes;
- Lieutenant deve ser classificado como miniboss conforme design do projeto, não apenas por nome.

## 25. Iron's spell authority
Acolyte mobs lançam magia no ecossistema Iron's. Perks/defenses que reagem a spell damage devem observar o damage source/spell event real do Iron's, não `instanceof AcolyteMage` salvo quando a intenção é especificamente o archetype.

## 26. Loot e progressão
O publisher anuncia “Get new loots” e horn trading, mas não publica lista completa de drops/chests.

Para documentação realmente completa do conteúdo no futuro, extrair:
- entity loot tables;
- structure chest loot tables;
- Horn Merchant trade definitions;
- items registrados pelo addon;
- advancements.

Enquanto esses dados não forem inspecionados, não inventar nomes/rarities.

## 27. Dependências
**Required:** Iron's Spells 'n Spellbooks.

Nenhuma outra hard dependency é listada no CurseForge relations da release. Isso não impede que o JAR use APIs transitivas fornecidas por Iron's; apenas evita catalogar uma library externa sem evidência.

## 28. Incompatibilidades formais
Nenhum projeto foi localizado como `Incompatible` na relação upstream consultada.

Portanto:
- structure density = risco de composição;
- Epic Fight = integração a testar;
- followers = sobreposição funcional;
- não transformar esses pontos em blacklist automática.

## 29. Matriz de validação — entidades
1. Human Mage em cada biome variant encontrada.
2. Demon Mage idem.
3. Human Warrior.
4. Demon Warrior.
5. Human Archer.
6. Demon Archer.
7. Horn Merchant.
8. Lieutenant.
9. Registrar stats/equipment/spells/loot de cada archetype.
10. Dedicated server AI/casting.

## 30. Matriz de validação — 18 estruturas
11–15. Cinco Taverns.
16–22. Sete Watchtowers.
23–24. Duas Towers.
25–27. Três Castles.
1. Lieutenant Castle.

Para cada uma: biome, Y, integrity, occupants, loot, overlap, generation time.

## 31. Matriz de validação — recruitment
1. Contratar aliado com emeralds.
2. Medir custo real.
3. Medir duração real.
4. Recrutar múltiplos e determinar limite.
5. Owner death.
6. Recruit death.
7. Relog.
8. Server restart.
9. Dimension change.
10. Chunk unload.
11. Friendly-fire.
12. Epic Fight interaction.

## 32. Matriz de validação — Horn Merchant
1. Spawn/encontro real.
2. Listar todos os trades.
3. Custos em Demon Horns.
4. Scroll tiers.
5. Outros magic items.
6. Restock/max uses.
7. Efeito de mods de villager/trade UI.

## 33. Regras para outros chats
- Acolyte possui **18 estruturas publicadas**, não “algumas estruturas”.
- Não inventar spell loadouts/biome mapping/trade prices/recruit duration.
- Recruits são temporários e pagos; não tratá-los como summon persistente.
- Iron's é authority do spell system.
- Lieutenant é miniboss upstream, mas stats/loot precisam ser medidos.
- Para balance, primeiro extrair os dados internos que o overview não publica.

## 34. Fontes e confiança
**Authority física:** modlist 07/09/2026.

**Upstream:** [Acolyte — CurseForge](https://www.curseforge.com/minecraft/mc-mods/acolyte), relations e publicação 1.0.3.

**Fonte interna:** guia completo de magia.

**Confiança:** alta para roster macro, contagem de estruturas, recruitment e horn trading. Baixa/indeterminada para números e tabelas não publicados; esses pontos estão explicitamente marcados para inspeção de JAR/runtime em vez de preenchidos por suposição.