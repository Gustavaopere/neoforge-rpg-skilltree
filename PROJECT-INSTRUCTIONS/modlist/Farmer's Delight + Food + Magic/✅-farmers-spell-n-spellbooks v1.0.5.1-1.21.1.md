# Farmer's Spell 'n Spellbooks

## Propriedades do registro

- **Mod:** Farmer's Spell 'n Spellbooks
- **Arquivo JAR:** `farmers-spell-n-spellbook-1.0.5.1-1.21.1.jar`
- **Versão 1.21.1:** `1.0.5.1-1.21.1`
- **Categoria:** Compat, Comida, Magia
- **Função:** Crossover Farmer's Delight ↔ Iron's Spells com School of Gluttony, culinária mágica, food blocks, equipamentos e workstations/recipes próprias.
- **Dependências:** Farmer's Delight 1.3.4 + Iron's Spells 'n Spellbooks 3.16.3 estão fisicamente presentes e são as duas authorities base do addon.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Regression gates: dupe de block-form food por sticky piston corrigida em 1.0.5.1, double XP/output do Alchemist Pot corrigido na 1.0.5.0, spell API drift, recipe/tag overlap, food servings desync e armor modifier stale. A upstream 1.0.6.0 altera spells, effects, feast servings, loot/tags, equipment e food behavior; promoção exige regressão ampla de conteúdo e balanceamento.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/farmers-spell
- **Procedência:** modlist física atual confirma `farmers-spell-n-spellbook-1.0.5.1-1.21.1.jar`, mod id `farmers_spell`, runtime `1.0.5.1-1.21.1`. CurseForge oficial revalidado em 01/10/2026 publica 1.0.6.0 para NeoForge 1.21.1 como atualização posterior não instalada.
- **Observações:** Source 1.21.1 confirma Alchemist Pot, Cinderous Stove, Wisewood Cabinet, Amethyst Beetroot e food blocks. Runtime físico 1.0.5.1 mantém o sticky-piston dupe fix. Upstream 1.0.6.0 adiciona novos blocks/foods/effects e Pan Spell WIP, além de reworks de Goodberry, rings, loot/tags e behavior.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — runtime físico permanece Farmer's Spell 1.0.5.1. A release 1.0.6.0 foi comparada integralmente e os deltas relevantes de spells, food/effects, loot/tags e equipment foram incorporados abaixo.
- **Decisão:** Sem decisão
- **Sobreposição:** Bridge temático único entre FD e Iron's. Outros addons de comida/magia podem compartilhar tags/ingredientes/spells, mas conflito deve ser medido por IDs/contracts, não por tema.
- **Data da última decisão:** 2026-08-30

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #275: JAR `farmers-spell-n-spellbook-1.0.5.1-1.21.1.jar`, mod id `farmers_spell`, runtime `1.0.5.1-1.21.1`, SHA-1 `f77355e029af39bbaba3854e10cc087a608351ff`.

# Dossiê operacional — padrão Alex's Mobs
> **Runtime físico confirmado:** `farmers-spell-n-spellbook-1.0.5.1-1.21.1.jar` · mod id `farmers_spell` · versão `1.0.5.1-1.21.1` · NeoForge 1.21.1 · Client & Server. Bases físicas: Farmer's Delight 1.3.4 + Iron's Spells 'n Spellbooks 3.16.3.
## 1. Papel no modpack
Farmer's Spell 'n Spellbooks é uma bridge/content addon entre Farmer's Delight e Iron's Spells. Acrescenta culinária mágica, blocos/foods próprios, equipamentos e a **School of Gluttony**, usando os contratos dos dois mods-base em vez de substituir seus sistemas.
## 2. Authority / ownership
- **Farmer's Delight:** cooking framework, recipes base, feast/food semantics e estações que o addon reutiliza.
- **Iron's Spells:** spell registry, mana, cooldown, spell container e casting pipeline.
- **Farmer's Spell:** School of Gluttony, spells/items/foods/blocos e recipes que registra.

Uma recipe mágica não cria uma terceira authority para mana/casting, e um alimento mágico não deve liquidar food/effect duas vezes.
## 3. Conteúdo source-confirmed da linha 1.21.1
A árvore 1.21.1 contém blocos como `AlchemistPotBlock`, `AmethystBeetrootBlock`, `CinderousStoveBlock`, `EdenAppleTartBlock`, `GluttonHotchpotchBlock`, `IcebreakerBreadBlock`, `PumpkinSoupBlock`, `RedVelvetCakeBlock`, `SaingeziChickenBlock` e `WisewoodCabinetBlock`, além de BlockEntities para Alchemist Pot, Cinderous Stove e storage/cabinet correspondente.
Esses nomes são source-confirmed para a branch 1.21.1; o JAR físico permanece authority final do registry instalado.
## 4. School of Gluttony
A descrição oficial define a School of Gluttony como escola nova integrada ao Iron's Spells. Spells dessa escola continuam sujeitas ao framework de spell power, mana/cooldown e spell containers do Iron's; o addon é authority dos efeitos concretos que registra.
## 5. Processing e Alchemist Pot
`AlchemistPotBlock` e seu BlockEntity confirmam uma workstation/processo próprio. O changelog 1.0.5.0 registrou correção de caso em que a receita do Alchemist Pot podia conceder experiência duas vezes; esse histórico torna **single-settlement de output/XP** um regression gate obrigatório na 1.0.5.1.
## 6. 1.0.5.x — conteúdo e correções relevantes
A release 1.0.5.0 adicionou Icebreaker Bread com duas partes de food, Goodberry Crate, Icy Egg Crate, Upgrade Orb Gluttony e ajustes visuais/sonoros; também corrigiu problemas em Gluttony Armors, recipes/XP e modelos. A 1.0.5.1 instalada corrige adicionalmente duplicação de block-form food causada por sticky piston.
Como o pack usa 1.0.5.1, não reintroduzir versões anteriores sem reabrir esses regression gates.
## 7. Food blocks e piston semantics
A correção 1.0.5.1 está diretamente ligada a blocos de comida movidos por sticky piston. O servidor deve manter servings/inventory/output consistentes durante movimentação e quebra; client model não pode duplicar conteúdo lógico.
## 8. Dependências e version drift
A release física é posterior a várias builds iniciais da linha 1.21.1. Farmer's Delight 1.3.4 e Iron's Spells 3.16.3 estão presentes. Updates de qualquer base podem alterar recipe tags, feast/block behavior, spell APIs ou school registration e devem ser regression-tested.
## 9. Configuração e dados
Recipes, loot, tags e spell resources pertencem ao datapack/resource layer do addon. `/reload` precisa preservar registry references válidas e não duplicar recipe outputs ou spell entries. Valores de spell/food não são inventados nesta ficha quando não foram auditados diretamente no JAR/source correspondente.
## 10. Client / Server
**Servidor:** recipe validation, food consumption/effects, block entity inventory, XP, spell cast/damage/cooldown e equipment state.
**Cliente:** models, particles, sounds, screens, spell presentation e armor rendering.
Particles/animation não são gameplay authority.
## 11. Lifecycle
Validar workstation load/unload, recipe reload, food block place/move/break, sticky piston, spell learn/equip/cast, armor equip/unequip, death/respawn, chunk reload, dimension change e server restart.
## 12. Multiplayer
Dois jogadores no Alchemist Pot não podem duplicar output/XP. Shared food blocks precisam liquidar servings uma vez. Spells AOE/targets precisam ser server-authoritative e não duplicar damage via bridge.
## 13. Integrações no pack
A integração primária é Farmer's Delight 1.3.4 ↔ Iron's Spells 3.16.3. Outros addons culinários e mágicos podem compartilhar tags/ingredients/schools, mas sobreposição temática não prova compat ou redundância. Create automation precisa consumir recipes reais sem mudar ownership do resultado.
## 14. Riscos
1. double XP/output no Alchemist Pot;
2. regressão da dupe por sticky piston corrigida em 1.0.5.1;
3. recipe/tag overlap com outros addons FD;
4. school/spell API drift com Iron's;
5. armor modifier/effect stale;
6. food block servings desync;
7. `/reload` duplicar ou invalidar recipes/spells;
8. Create automation usar tag ampla e escolher ingredient errado;
9. client particle/model divergir do state real;
10. update de Farmer's Delight alterar feast/block semantics.

## 15. Matriz de testes
1. Dedicated server boot com as duas bases atuais.
2. Alchemist Pot recipe e XP exatamente uma vez.
3. Sticky piston com cada block-form food relevante.
4. Icebreaker Bread servings/partes.
5. Gluttony spell learn/equip/cast.
6. Gluttony armor equip/unequip/enchant.
7. `/reload` de recipes/spells.
8. Chunk unload/reload de workstation/food block.
9. Dois jogadores usando a mesma estação.
10. Create automation em recipes cruzadas relevantes.
11. Smoke-test após update de Iron's ou Farmer's Delight.

**Esta catalogação não afirma que esses testes foram executados.**
## 16. Evidências
- modlist física canônica: JAR/mod id/version/hash e bases presentes;
- CurseForge oficial 1.0.5.1: release NeoForge 1.21.1, função e correção de sticky-piston dupe;
- source oficial `GLDYM/Farmers-Spell-n-Spellbook` branch 1.21.1: classes de blocks/BlockEntities e arquitetura do addon;
- changelog 1.0.5.0: novos conteúdos e correção de double XP.

> **Boundary canônico:** Farmer's Spell é authority do **conteúdo crossover que registra**; Farmer's Delight mantém cooking/food base e Iron's Spells mantém casting/mana/spell framework.

## 17. Atualização upstream 1.0.6.0 — não instalada

A autoridade física continua em **Farmer's Spell 'n Spell Book 1.0.5.1**. O CurseForge oficial publicou **1.0.6.0** para NeoForge 1.21.1 em 29/09/2026.

### Features adicionadas
- novos variants de **Ember** block;
- **Frosted Ice Cream Bucket** e **Fufu** em forma de bloco;
- mais alimentos com efeitos;
- **Holy Spirit** oil effect;
- **Pan Spell**, marcado pelo upstream como WIP.

### Mudanças de gameplay/conteúdo
- alguns effects foram fortalecidos e efeitos de food foram ajustados;
- Eden Apple Tart e Red Velvet Cake recebem emissive models;
- **Foodgeist Gift** passa para Loot Table;
- **Goodberry Spell** é refeito: custo publicado de 50 mana, cast de 5 s e resultado de 10 Goodberries ou transformação de berries;
- rings são retrabalhados para melhorar eat speed;
- Gospel Knife recebe +2.5 damage contra undead e perde Smite I;
- Tiramisu Book passa a adicionar Ubiquitous I;
- feast block serving e food-block loot são atualizados;
- Foodgeist food tags são atualizadas;
- Chaos Slash e Foodgeist behavior são atualizados.

### Fix
- corrige weapon repair material que era sempre iron ingot.

### Impacto para o pack
É uma atualização de conteúdo e balanceamento, não um hotfix isolado. Spells/mana, food effects, feast serving, loot tables e tags podem afetar Iron's Spells, Farmer's Delight, Create automation, JEI/recipe discovery e qualquer datapack/KubeJS que referencie IDs ou recipes do addon.

### Gate de promoção 1.0.5.1 → 1.0.6.0
- [ ] Dedicated server boot com Farmer's Delight e Iron's Spells físicos atuais.
- [ ] Goodberry Spell cobra mana/cast uma vez e produz exatamente o resultado esperado.
- [ ] Pan Spell WIP não causa crash/duplication em cast, reload ou multiplayer.
- [ ] Holy Spirit oil e foods aplicam efeitos uma única vez e persistem corretamente.
- [ ] Feast serving não duplica portions em dois jogadores/automation.
- [ ] Foodgeist Gift usa loot table sem double-drop.
- [ ] Foodgeist tags/behavior e Chaos Slash são smoke-tested.
- [ ] Gospel Knife damage/repair material e Tiramisu Book enchant behavior conferem com a release.
- [ ] Sticky-piston dupe corrigida em 1.0.5.1 continua fechada.
- [ ] Alchemist Pot continua sem double XP/output.
- [ ] JEI/Create/KubeJS recipes/tags não ficam stale após `/reload`.

Fonte upstream: CurseForge Farmer's Spell 'n Spell Book 1.0.6.0, file ID 9008722, NeoForge 1.21.1. Nenhum teste acima foi executado nesta atualização documental.

## Auditoria incremental — 19/11/2026 — 1.0.7.0 e 1.0.7.1

**Autoridade física histórica:** #275, `farmers-spell-n-spellbook-1.0.5.1-1.21.1.jar`. O [catálogo oficial CurseForge 1631240](https://www.curseforge.com/minecraft/mc-mods/farmers-spell/files/all) identifica exatamente três releases NeoForge 1.21.1 posteriores à física: [1.0.6.0](https://www.curseforge.com/minecraft/mc-mods/farmers-spell/files/9008722) (29/09, já documentada na seção 17), 1.0.7.0 e 1.0.7.1. A 1.0.6_2 publicada para Forge 1.20.1 não é artefato do alvo. Categorias oficiais Farmer's Delight + Food + Magic, pasta preservada.

### [1.0.7.0 — 28/10/2026](https://www.curseforge.com/minecraft/mc-mods/farmers-spell/files/9350536)

- Novo cultivo Frost Egg Bush, Amethyst Beetroot Crates, feast Dragon-Skin Aspic com fatias, Fried Frost Egg, Frosted Tamago Sushi, Mithril Cheese e Mithril Knife. Novas superfícies de coleta, processamento e alimentação; não inventar IDs ou receitas.
- Novo spell White Altar Bloom. Rebalanceamento de Goodberry, Phantom Loot, Preserve Circle, Seal Coat, Bad Apple e Chaos Slash; os números exatos não constam nas notas.
- Foodgeist ganha comportamento sentado, invocação no stove e animações adicionais. Descrições de blocos expansíveis via Shift; receitas Create de filling/emptying, relevantes para automação.
- Regras de plantio de Amethyst Beetroot e Frost Egg passam a ser data-driven, assim como blacklist de blocos para Harvest Sickle; validar datapacks e reload antes de adotar IDs/schemas.
- Mudanças de efeitos de alimentos, receitas, Foodgeist gifts, loot tags e recursos audiovisuais. Corrige encoding da chance de loot de Wheat Tome e ingredientes de affinity rings.
- A correção explícita para 1.21.1 restaura bônus de mana máxima dos Foodgeist Ring e Gluttonous Bond Ring durante Nourished ou Comforted; testar equip/unequip, expiração, morte e relog sem bônus duplicado ou stale.

### [1.0.7.1 — 03/11/2026](https://www.curseforge.com/minecraft/mc-mods/farmers-spell/files/9419045)

Corrige textura e orientação incorretas de Chaos Slash. É correção de apresentação relevante à leitura visual do spell; a nota não comprova alteração de dano, trajetória lógica ou targeting.

**Gates:** manter QA de Iron's/FD, Alchemist Pot, piston dupe, food servings e Goodberry; testar novos cultivos, Harvest Sickle/data reload, mana dos anéis e efeitos condicionais, Foodgeist e ownership, filling/emptying Create, JEI/KubeJS e percepção visual de Chaos Slash. Auditoria documental das releases publicadas até 1.0.7.1 concluída; sem promoção física, teste runtime ou mudança de basename.

