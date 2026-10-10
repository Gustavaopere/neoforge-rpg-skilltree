# Ars Elemental

> **Autoridade física atual — 22/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#44**: `ars_elemental-1.21.1-0.7.10.1.jar`, mod id `ars_elemental`, runtime `0.7.10.1`, SHA-1 `a1e4021177aae0e16c1f7c6487a82f0b68bbade3`.

> **Reauditoria física — 19/09/2026.** JAR top-level reconfirmado na modlist física atual: `ars_elemental-1.21.1-0.7.10.1.jar`, versão `0.7.10.1`. A URL da própria página Notion foi removida; o conteúdo canônico foi preservado.

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist(1).txt` de 22/09/2026 — autoridade física atual
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Ars Elemental
- **Arquivo JAR:** `ars_elemental-1.21.1-0.7.10.1.jar`
- **Versão 1.21.1:** 0.7.10.1
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Magia, RPG
- **Função:** Expansão elemental de Ars Nouveau com 39 glyphs/spell parts normais na build 0.7.10.1, 8 rituals, 3 familiars, 3 perks, 12 armor sets, casters e mappings de resistências Air/Earth/Fire/Water.
- **Dependências:** Ars Nouveau 5.13.1. Sauce permanece jarjar/embedded; Ars Elemancy 1.18.3 consome a base elemental sem transferir authority.
- **Sobreposição:** Complementa Ars Nouveau e fornece a base elemental consumida por Ars Elemancy. Não duplicar schools/perks/resistances em integração própria.
- **Compatibilidade/Riscos:** Addon de Ars Nouveau com schools, glyphs, familiars, perks, armor e rituals. Riscos: school power/resistance double-dip, perk duplication, spell/turret causalidade, Sauce jarjar drift e source/publication skew. Upstream público chegou a 0.7.10.3; o source passou por 0.7.10.2 intermediário e 0.7.10.3 exige Ars Nouveau atualizado.
- **Observações:** Runtime físico permanece 0.7.10.1. O branch oficial 1.21 passou por **0.7.10.2** no source e chegou a **0.7.10.3**; o CurseForge publica 0.7.10.3 diretamente após 0.7.10.1, sem artefato público 0.7.10.2 localizado.
- **Procedência:** modlist física atual + source oficial `Alexthw46/Ars-Elemental` branch 1.21 para 0.7.10.1→0.7.10.2→0.7.10.3 + CurseForge oficial 0.7.10.3. A autoridade instalada permanece 0.7.10.1.
- **Fonte:** https://github.com/Alexthw46/Ars-Elemental
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — Ars Elemental físico permanece 0.7.10.1. O estado intermediário de source **0.7.10.2** e a release pública **0.7.10.3** foram auditados separadamente; 0.7.10.3 não deve ser promovido isoladamente sem reconciliar Ars Nouveau.
- **Histórico da decisão:** Manter. Ficha reconstruída em 07/09/2026 contra o commit exato da build 0.7.10.1 para impedir contaminação por features da 0.7.10.2 upstream.
- **Data da última decisão:** 2026-09-07

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


## 18. Histórico upstream 0.7.10.2 → 0.7.10.3 — não instalado
A autoridade física continua em **Ars Elemental 0.7.10.1**.

### 0.7.10.2 — estado intermediário no source, sem artefato público localizado
O branch oficial altera `mod_version` para **0.7.10.2**, atualiza Ars Nouveau de 5.12.0.1368 para **5.13.0.1390** e Sauce de 0.0.47.92 para **0.0.50.96**.

Deltas confirmados no source 0.7.10.2:
- reordena checks de world height/distance para evitar **out-of-range block checks**;
- adiciona spawn eggs de **Siren** e **Flashjack**;
- corrige **Enderference** que não funcionava porque o subscriber estava registrado no event bus/mod id de Ars Nouveau em vez do próprio Ars Elemental;
- remove color data não intencional dos **Mermaid Tokens**.

O branch também contém mudanças recentes já citadas no dossiê histórico, incluindo features posteriores à 0.7.10.1; elas não devem ser tratadas como instaladas.

### 0.7.10.3 — release pública de 28/09/2026
A release 0.7.10.3:
- corrige a **Prism API** quebrada;
- corrige problemas de **Oxidize AoE**;
- atualiza a build contra **Ars Nouveau 5.13.2.1418** e mantém Sauce 0.0.50.96.

O próprio changelog da release exige **latest Ars Nouveau**. O pack físico está em Ars Nouveau 5.13.1; portanto a promoção de Ars Elemental 0.7.10.3 deve ser tratada como bloqueada até Ars Nouveau também ser reconciliado para a linha 5.13.2 compatível.

Gate de promoção: Ars Nouveau 5.13.2; Sauce jarjar; Prism API consumers; Oxidize com single/AoE targets; Enderference teleport blocking; Siren/Flashjack eggs; Mermaid Tokens; worldgen near min build height; turrets; school power/resistance; Ars Elemancy sem double-dip.

Fontes upstream: source oficial branch `1.21` — commits `d86b5202...`, `3c377816...`, `93d80bc7...`, `8c0bdff2...`; CurseForge file ID 8999174 para 0.7.10.3.

## 29. Upstream 0.7.10.4 — 10/10/2026

**Versão física:** `ars_elemental-1.21.1-0.7.10.1.jar` / 0.7.10.1. O histórico anteriormente registrado inclui 0.7.10.2 **no source, sem artefato publicado identificado**, e 0.7.10.3 publicada em 28/09/2026, com correções da Prism API e Oxidize AoE. A nova release **0.7.10.4**, publicada em 04/10/2026 (CurseForge file **9057244**), é a última NeoForge 1.21.1 encontrada.

**Delta oficial 0.7.10.4:** aumenta o **SauceLib embarcado/referenciado para ver61**, descrito como conjunto de minor fixes, e faz limpeza de referências a código de APIs deprecated cuja remoção está prevista. Não há nova spell/glyph ou criatura anunciada na descrição dessa release. Dependências indiretas e API/ABI merecem mais atenção do que o número do patch sugere.

**Bloqueio de integração:** a versão 0.7.10.3 já havia sido compilada contra Ars Nouveau 5.13.2.1418, enquanto o snapshot físico de Ars Nouveau ainda apresentava 5.13.1. É necessário reconciliar o Ars Nouveau atual (item #61 nesta rodada), além da relação com SauceLib, antes de testar e promover 0.7.10.4. A atualização física isolada pode introduzir linkage errors e behavior drift.

**Regressões:** build client/dedicated server, Sauce library jarjar, Prism API consumers, Oxidize/AoE, Enderference, Siren/Flashjack eggs, Mermaid Tokens, Ars Nouveau spells e turret behavior, event bus e datapack reload; não marcar testes como executados.

**Fontes:** https://www.curseforge.com/minecraft/mc-mods/ars-elemental/files/9057244 ; https://www.curseforge.com/minecraft/mc-mods/ars-elemental/files/8999174

**Decisão:** versão upstream 0.7.10.4 documentada; **não promover fisicamente** antes da reconciliação Ars Nouveau/Sauce e smoke test.

