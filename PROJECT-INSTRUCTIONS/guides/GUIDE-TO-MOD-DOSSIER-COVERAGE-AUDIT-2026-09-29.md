# Auditoria de cobertura — guides → dossiers individuais

**Data:** 29/09/2026  
**Baseline auditada:** `main@d1659e7abadcf03c386d17b1886a473dc6541195`  
**Objetivo:** garantir que informação de mod externo necessária para criação futura de perks não exista somente nos guias temáticos.

## 1. Escopo

Foram inventariados:

- **127** arquivos Markdown em `PROJECT-INSTRUCTIONS/guides/`;
- **587** dossiers Markdown em `PROJECT-INSTRUCTIONS/modlist/`;
- **645 associações seção/JAR** extraídas dos capítulos temáticos de Gameplay, Magia e Tecnologia que descrevem mods ou integrações concretas.

README, índices de fontes, snapshots `CURRENT-MODLIST*.md`, decisões históricas e os dossiês de `guides/projects/` foram tratados conforme sua função própria, não como fichas individuais de mod.

## 2. Regra de authority resultante

Para perks futuras:

1. `modlist/modlist.md` decide **presença, posição física, JAR e versão instalada**.
2. O dossier individual atual do mod decide **mecânicas, ownership/authority, lifecycle, multiplayer, riscos, hooks e limites conhecidos**.
3. Os guias temáticos fornecem **contexto transversal**, relações entre mods e histórico editorial.
4. `guides/projects/` continua fonte obrigatória dos quatro sistemas próprios — RPG Skill Tree, Volcanoes, Enshrouded e Black Arcana — e de suas regras de capability delta, authority e fail-closed.
5. Se uma mecânica perk-relevante aparecer no guia e não no dossier individual atual, a perk não deve ser fechada até a lacuna ser reconciliada.

## 3. Resultado da comparação

O padrão dominante é o dossier individual atual ser **mais detalhado** que a seção correspondente do guia. As fichas atuais normalmente acrescentam authority explícita, client/server, persistence, lifecycle, multiplayer, riscos, matriz de testes e versionamento físico que não existiam nos resumos antigos.

Exemplos conferidos em profundidade incluem Epic Fight, Better Lock On, Grappling Hook Mod: Skybound, Epic Fight x Curios, Soul Fire'd, Additional Attributes, Cold Sweat, Loot Integrations, Dynamic Trees, Ars Nouveau, Iron's Spells 'n Spellbooks, Spell Codex, Spell Actionbar, EFIS, Iron's Apothic, Apothic Enchanting/Attributes/Spawners/Compats, Create e Sable.

A auditoria encontrou **cinco lacunas perk-relevantes reais** em providers ainda atuais. Elas foram migradas para os dossiers correspondentes neste lote.

### 3.1 Simply Swords

Migrado para `✅-simply-swords v1.70.2-1.21.1.md`:

- matriz de Implicits por família com faixas default;
- riscos de double-dip para armor penetration, bleed, execute, multi-hit, attack speed etc.;
- nomes das superfícies públicas `SimplySwordsAPI` documentadas pelo guia;
- semântica de `WeaponImplicitDefinition` (damage/hit/incoming-damage/tooltip), persistência do roll e regra top-10% para `UniqueWeaponItem`;
- regra exactly-once de Awakening e gem scaling — `scaleAbilityDamage`/`scaleAbilityValue` e helpers de gem não devem receber scaling duplicado;
- pipeline de `UniqueWeaponActiveAbility`, hits delegados e `applyAbilityBoltDamage`, incluindo suppression de Implicits no ability-bolt;
- contrato público de gem sockets: `AdditionalGemSocketApi`, hooks `inventoryTickGemSocketLogic`, `onClickedGemSocketLogic`, `postHitGemSocketLogic`, `appendTooltipGemSocketLogic` e `onWeaponSwing`;
- `GemPowerRegistry` com IDs namespaced sincronizados e persistência de `GemPowerComponent` por ID, evitando estado paralelo incompatível em perks/itens customizados;
- gate para confirmar assinatura/ABI no JAR físico antes de implementação.

### 3.2 Fundamental Principles

Migrado para `✅-fundamental-principles-irons-spells-addon v1.1.7.1.md`:

- semântica das 13 Principles;
- exemplos de passivos/progressão 0–20;
- Spell Exhaustion;
- **Remedium's Law** e sua relação com food/saturation em healing spells;
- progressão própria de spellbooks por tiers/covers, incluindo aumento de slots e poder;
- boundary de Mana Reinforcement como mecânica provider-native até existir hook específico auditado.

### 3.3 Nutritional Balance

Migrado para `✅-nutritional-balance v1.21.1-7.0.3.md`:

- Nutritional Units (NUs);
- tooltip e GUI nutricional;
- faixas-alvo, malnutrition/engorgement e buffs/debuffs;
- regra especial de Sugar;
- regra especial de excesso de Vegetables.

### 3.4 Pufferfish's Unofficial Additions

Migrado para `✅-pufferfishs-unofficial-additions v2.2.8.md`:

- fishing como fonte de XP;
- dimensões da fórmula de XP de casts do Iron's;
- rewards concretas de efeitos/imunidade/amplifier/duração;
- reward de caminhar sobre powder snow.

### 3.5 Born in Chaos

Migrado para `✅-born-in-chaos v1.7.6.md`:

- Nightmare Stalker como exemplo explícito de ameaça com progressão dependente da idade do mundo.

A segunda revisão da PR também reclassificou `CURRENT-MODLIST*.md`, capítulos `00-visao-geral.md` e os trechos equivalentes dos guias consolidados como **snapshots históricos**, para impedir que inventários 595/607/612 sejam tratados como authority física atual.

## 4. Conteúdo dos guias que não deve ser copiado como authority atual

Os guias preservam snapshots históricos e, por isso, ainda citam:

- versões antigas de mods que continuam instalados;
- mods já removidos;
- duplicidades antigas;
- decisões curatoriais de snapshots anteriores.

Exemplos de **version drift** detectados durante a reconciliação:

- Apotheosis `8.7.0` no guia → `8.8.0` atual;
- Cold Sweat `2.4.2` → `2.4.3.1`;
- Waystones `21.1.42` → `21.1.45`;
- Vampirism `1.10.12` → `1.10.13`;
- Create: The Factory Must Grow `1.2.4b Community` → `1.3.1-community`;
- Create: Stats & Power `1.5.1` → `1.13.1`.

Exemplos de conteúdo histórico sem dossier de provider atual incluem partes antigas de Identity/Walkers, Fungal Infection: Spore/Infnexus, Dynamic Trees Universal Compat, antigos addons de AE2/Oritech, Create: Sulfuric Resonance e Create Stock Bridge. Esses registros podem permanecer como histórico editorial, mas **não são providers atuais de perks** apenas porque ainda aparecem no guia.

## 5. Casos distribuídos entre vários dossiers

Alguns capítulos do guia descrevem um **ecossistema**, e a cobertura correta não pertence ao core inteiro:

- Dynamic Trees: core no dossier Dynamic Trees; BetterEnd/BetterNether/BWG/Quark/Terralith/VanillaBackport em seus próprios dossiers;
- Ars Nouveau: core no dossier Ars Nouveau; glyph/addon/compat features nos dossiers dos addons;
- Iron's Spells: core no dossier Iron's; progression/Actionbar/EFIS/IronSable/Apothic nos dossiers correspondentes;
- Simply Swords: core/Implicits/Runic no core; Simply More, Cataclysm, Integrated Simply Swords e Simply Tooltips permanecem separados;
- Sable: physics/sublevels no core; ragdolls, compat layers e Aeronautics bridges permanecem em dossiers próprios.

Não concentrar artificialmente todos esses contratos no mod-base.

## 6. Projetos próprios — exceção obrigatória

`guides/projects/` não é uma duplicata dos dossiers de mods externos. Ele continua obrigatório porque documenta sistemas próprios e contratos transversais que não pertencem a um único mod da modlist:

- RPG Skill Tree;
- Volcanoes;
- Enshrouded;
- Black Arcana;
- matriz de integração;
- authority;
- fail-closed;
- capability delta provider → árvore.

Para uma perk híbrida, ler o dossier do mod externo **e** o dossiê/projeto próprio pertinente.

## 7. Bloqueio residual

A posição física **#272 — Factory Construction Registry Probe 0.1.0** continua sem dossier certificado correspondente. Portanto não é possível declarar cobertura individual 587/587 de conteúdo técnico por dossier. A entrada permanece fail-closed no índice; esta auditoria não inventa uma ficha nem conteúdo ausente.

Isso não invalida a cobertura dos providers documentados nos guias, mas é o único gap estrutural conhecido entre a modlist física e a existência de dossier individual.

## 8. Gate obrigatório para o fluxo de perks

Antes de criar/fechar uma perk:

1. confirmar provider atual em `modlist.md`;
2. ler o dossier individual da versão instalada;
3. usar os guias para contexto cross-mod e para detectar termos históricos ainda relevantes;
4. consultar `guides/projects/` quando houver sistema próprio envolvido;
5. se guide e dossier divergirem em versão, o dossier/modlist físico prevalece para identidade instalada;
6. se o guia possuir mecânica não presente no dossier, **não inferir**: reconciliar a ficha primeiro;
7. se o provider foi removido, não usar a seção histórica do guia como provider atual;
8. manter exactly-once, ownership e fail-closed quando múltiplos sistemas tocarem o mesmo evento.

## 9. Estado após esta auditoria

- Cobertura estrutural dos guias foi reconciliada contra a modlist física atual.
- As cinco lacunas perk-relevantes confirmadas foram incorporadas às fichas individuais, incluindo os follow-ups de review para gem sockets do Simply Swords e progressão de spellbooks do Fundamental Principles.
- Snapshots antigos dos guias foram reclassificados explicitamente como históricos.
- Conteúdo transversal dos projetos próprios permanece separado e obrigatório.
- O único bloqueio estrutural sem dossier continua sendo #272.

A próxima auditoria de perks deve partir dos dossiers individuais atuais, usando os guias como contexto e detector de regressões — não como inventário físico concorrente.
