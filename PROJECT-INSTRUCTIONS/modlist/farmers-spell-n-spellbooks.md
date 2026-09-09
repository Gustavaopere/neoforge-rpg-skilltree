# Farmer's Spell 'n Spellbooks — 1.0.5.1-1.21.1

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