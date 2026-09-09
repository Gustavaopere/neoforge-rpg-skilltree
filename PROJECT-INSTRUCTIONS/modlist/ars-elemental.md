# Ars Elemental

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db814f9592dd10af855c22  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: modlist física mais recente, 595 mods  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** Ars Elemental
- **Arquivo JAR:** `ars_elemental-1.21.1-0.7.10.1.jar`
- **Versão 1.21.1:** `0.7.10.1`
- **Categoria:** Magia; RPG
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://github.com/Alexthw46/Ars-Elemental
- **Função:** Expansão elemental de Ars Nouveau com 39 glyphs/spell parts normais na build 0.7.10.1, 8 rituals, 3 familiars, 3 perks, 12 armor sets, casters e mappings de resistências Air/Earth/Fire/Water.
- **Dependências:** Ars Nouveau; Sauce é biblioteca embutida/jarjar usada internamente e não conta como mod top-level separado.
- **Compatibilidade/Riscos:** Riscos: double-dip de school power/resistance, perks/turrets processados duas vezes, perda de perk slots de armor e drift de patch. A branch upstream já está em 0.7.10.2; features exclusivas da .2 não pertencem ao runtime 0.7.10.1.
- **Sobreposição:** Complementa Ars Nouveau e fornece a base elemental consumida por Ars Elemancy. Não duplicar schools/perks/resistances em integração própria.
- **Observações:** mod id ars_elemental. Release normal registra 39 spell parts; MethodCarianPhalanx é dev-only e foi excluído. 8 rituals, 3 familiars, 3 perks e 12 armor sets confirmados no source 0.7.10.1.
- **Procedência:** Runtime/JAR: modlist física 07/09/2026. Conteúdo/registries: source oficial no commit exato a5bdb39567ee39bde2210203cf051f709d06e08a, cujo gradle declara 0.7.10.1. Contexto local: guia consolidado de Magia.
- **Histórico da decisão:** Manter. Ficha reconstruída em 07/09/2026 contra o commit exato da build 0.7.10.1 para impedir contaminação por features da 0.7.10.2 upstream.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 07/09/2026. Runtime confirmado: ars_elemental-1.21.1-0.7.10.1.jar. Dossiê exaustivo version-pinned com 39 glyphs, 8 rituals, 3 familiars, 3 perks, 12 armor sets, casters/resistance mappings, patch boundary 0.7.10.1→0.7.10.2 e testes.
- **Data da última decisão:** 2026-09-07.

## Dossiê operacional — padrão Alex's Mobs

> 🌊 **PADRÃO ALEX'S MOBS — DOSSIÊ OPERACIONAL EXAUSTIVO.** Runtime físico: `ars_elemental-1.21.1-0.7.10.1.jar`, mod id `ars_elemental`, NeoForge 1.21.1. A auditoria usa o commit upstream correspondente a **0.7.10.1**, porque a branch atual já está em 0.7.10.2. A build instalada registra **39 glyphs/spell parts normais, 8 rituals, 3 familiars, 3 perks e 12 armor sets elementais**, além de casters, resistance mappings e ajustes em spells Ars. Conteúdo exclusivo de 0.7.10.2 não foi importado.

## 1. Identidade e version pin
- **Mod:** Ars Elemental.
- **JAR:** `ars_elemental-1.21.1-0.7.10.1.jar`.
- **Runtime:** `0.7.10.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Commit upstream usado para a auditoria:** `a5bdb39567ee39bde2210203cf051f709d06e08a`, cujo `gradle.properties` declara `0.7.10.1`.
- **Decisão:** **Manter**.

## 2. Escopo
Ars Elemental expande Ars Nouveau em quatro escolas elementais — Air, Earth, Fire e Water — e uma escola agregada Elemental. Ele adiciona glyphs, familiars, perks, armor e rituals e também reclassifica/estende partes do Ars base para participação em escolas específicas.

## 3. Glyphs/spell parts normais — 39
### Efeitos básicos — 14
1. `EffectWaterGrave`
2. `EffectBubbleShield`
3. `EffectConjureTerrain`
4. `EffectCharm`
5. `EffectPhantom`
6. `EffectLifeLink`
7. `EffectSpores`
8. `EffectEnvenom`
9. `EffectSpike`
10. `EffectDischarge`
11. `EffectSpark`
12. `EffectConflagrate`
13. `EffectCauterize`
14. `EffectRage`

### Water update — 6
1. `EffectWaterJet`
2. `EffectGeyser`
3. `EffectMist`
4. `EffectSlipper`
5. `EffectCavitate`
6. `EffectOxidize`

### Summons — 2
1. `EffectSummonBee`
2. `EffectSummonSlime`

### Methods — 2
1. `MethodHomingProjectile`
2. `MethodArcProjectile`

### Propagators — 2
1. `PropagatorHoming`
2. `PropagatorArc`

### Filters — 12
27–28. Aquatic / NOT Aquatic  
29–30. Fiery / NOT Fiery  
31–32. Aerial / NOT Aerial  
33–34. Insect / NOT Insect  
35–36. Undead / NOT Undead  
37–38. Summon / NOT Summon

### Nullification — 1
1. `EffectNullify`

`MethodCarianPhalanx` aparece apenas sob `!isProduction()` e **não é contado como conteúdo normal da release**.

## 4. Rituals — 8
1. `SquirrelRitual`
2. `TeslaRitual`
3. `DetectionRitual`
4. `RepulsionRitual`
5. `AttractionRitual`
6. `PollinationRitual`
7. `ArchwoodForestRitual`
8. `ArchwoodForestationRitual`

Eles usam o registry ritual do Ars Nouveau; não são um engine paralelo.

## 5. Familiars — 3
O registry registra três familiar holders:
- Mermaid;
- Firenando;
- Flashjack.

O addon também registra iluminação dinâmica/emitida para entidades Firenando/Flashjack quando aplicável ao runtime do Ars.

## 6. Perks — 3
- `SporePerk`
- `ShockPerk`
- `SummonPerk`

Esses perks usam o PerkRegistry do Ars; integrações próprias devem ler o provider real e não manter um segundo estado de perk.

## 7. Armor elemental — 12 sets
A build define quatro elementos em três pesos:
- **Medium:** Air, Fire, Earth, Water.
- **Heavy:** Air, Fire, Earth, Water.
- **Light:** Air, Fire, Earth, Water.

Total: **12 armor sets**. Cada peça recebe perk slots pelo `PerkRegistry`, com distribuição diferente entre light/medium/heavy. O peso faz parte da identidade mecânica e não deve ser achatado por balanceamento externo genérico.

## 8. Spell casters registrados
A build registra como casters:
- `SPELL_HORN`;
- tomes Air, Fire, Earth, Water, Necromancy e Shapers;
- `CHAIN_LENS`.

Isso permite que esses itens carreguem/forneçam spell caster data pelo contrato Ars/Sauce usado na versão.

## 9. Mapeamento de resistências elementais
O addon liga escolas a damage tags Sauce:
- Elemental Fire → Fire Damage;
- Elemental Air → Air Damage;
- Elemental Earth → Earth Damage;
- Elemental Water → Water Damage.

Uma integração de resistência precisa respeitar esse mapping e não aplicar uma segunda mitigação por reconhecer apenas o nome da escola.

## 10. Extensões sobre spells Ars base
No `postInit`, a build acrescenta escolas a alguns spells Ars já existentes. Exemplos confirmados no source:
- Heal, Summon Vex, Wither, Hex, Life Link, Charm e Summon Undead entram em Necromancy;
- Cut entra em Elemental Air;
- Evaporate entra em Elemental Fire.

Também há tweaks de augments/behavior em spells Ars. Isso é alteração provider-native: não reaplicar via datapack/mixin local sem comparar o estado final.

## 11. Turrets e métodos
Os métodos/propagators próprios têm integração com turret/rotating turret no registry. Portanto um cast realizado por turret segue behavior registrado pelo addon, não uma segunda implementação do modpack.

## 12. Dependência Sauce embutida
O JAR físico contém Sauce via jarjar. Sauce **não é um mod top-level adicional** para esta catalogação. Seu contrato, porém, participa de armor/resistance/data internals e deve ser respeitado pelas integrações.

## 13. Patch boundary: 0.7.10.1 vs 0.7.10.2
A branch upstream atual já está em `0.7.10.2`. Conteúdo posterior, como os glyphs **Chill/Frost Condensation** e refactors recentes de Source Conduit/Caster's Pillar, **não deve ser tratado como instalado** enquanto o JAR físico permanecer `0.7.10.1`.

## 14. Authority e sobreposição
- **Ars Nouveau:** Source, spell grammar, casting, base glyph registries.
- **Ars Elemental:** escolas elementais, glyphs/rituals/familiars/perks/armor e mappings associados.
- **Ars Elemancy:** combina escolas elementais em híbridas; não deve duplicar o bônus de cada escola novamente.

## 15. Riscos
1. double-dip de school power/resistance;
2. perks elementais processados duas vezes;
3. armor perk slots perdidos em upgrade/copy;
4. spell base modificado por Elemental e depois reescrito por bridge local;
5. usar docs/source 0.7.10.2 como se fosse 0.7.10.1;
6. familiars/turrets gerando causalidade duplicada para XP/Mastery.

## 16. Matriz de validação
- boot client + dedicated server;
- 39 spell parts normais, filtros positivos/negativos e nullify;
- 8 rituals;
- 3 familiars;
- 3 perks;
- 12 armor sets e perk slots;
- school/damage-resistance mappings;
- casters/tomes/lens;
- turret behaviors;
- integração com Ars Elemancy sem double-dip;
- garantir ausência de features exclusivas de 0.7.10.2.

## 17. Fontes
- Modlist física do projeto, 07/09/2026.
- Source oficial `Alexthw46/Ars-Elemental`, commit exato `a5bdb39567ee39bde2210203cf051f709d06e08a` (0.7.10.1).
- Guia consolidado de Magia do projeto.
