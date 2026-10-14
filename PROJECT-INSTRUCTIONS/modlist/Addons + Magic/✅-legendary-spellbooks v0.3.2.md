# Legendary Spellbooks

## Propriedades do registro

- **Mod:** Legendary Spellbooks
- **Arquivo JAR:** legendary_spellbooks-1.21.1+neo-0.3.2.jar
- **Versão 1.21.1:** 0.3.2
- **Categoria:** Compat, Magia, RPG
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/legendary-spellbooks
- **Função:** Integra Iron's Spells com Legendary Monsters por conteúdo próprio: spells, escola Annihilation, summons/projectiles, spellbooks/equipment, attributes/effects e global loot modifiers.
- **Dependências:** Source 0.3.2: **ambos obrigatórios** no `neoforge.mods.toml` (`irons_spellbooks` em `[1.21.1-3.15.0,)` e `legendary_monsters` em `[1.21.1]`, segundo metadata interna do provider); NeoForge/Minecraft também obrigatórios. Último snapshot físico: Iron's **3.16.3** e Legendary Monsters **2.2.2 distribuição / metadata 1.21.1**. O source foi compilado com Iron's **3.16.1** e Legendary Monsters **2.1.14**; regressão de compatibilidade pendente.
- **Compatibilidade/Riscos:** Release 0.3.2 Beta. Riscos: provider/API drift de Iron's 3.16.1→3.16.3 e Legendary Monsters 2.1.14→2.2.2/2.2.3, mixins em `SpellData`/flight, summon persistence, duplicate loot/scrolls, registry IDs, attribute stacking, spell-level boundaries e version range interno `[1.21.1]`. Fix de crash com Iron's `<3.16.0` não comprova compatibilidade integral com 3.16.3.
- **Sobreposição:** Bridge/content addon; Iron's permanece authority de spell engine/mana/cast e Legendary Monsters de seus mobs/encounters. Não duplicar esses states em integrações próprias.
- **Observações:** Source oficial pinado em `Higurashi34m/Legendary-Spellbooks@62ced2f2b2693aa841251473cbbd726fdd928ed3`. Registry tem 30 spells ativos; `PossessedFallingSoulBlade` existe no source, mas o registro está comentado.
- **Procedência:** Snapshot físico de 25/09/2026 + CurseForge File ID **8255715**, Legendary Spellbooks 0.3.2 NeoForge 1.21.1 + source oficial commit `62ced2f2b2693aa841251473cbbd726fdd928ed3` (`neoforge.mods.toml`, version catalog, registries, mixins e loot modifiers) + dossiês dos providers Iron's 3.16.3 e Legendary Monsters 2.2.2.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 14/10/2026 — último snapshot físico 25/09/2026: Legendary Spellbooks **0.3.2**. CurseForge confirma **0.3.2 (16/06/2026)** como release NeoForge Minecraft 1.21.1 mais recente; **0.4.1 é Forge Minecraft 1.20.1**, não upgrade para o pack. Seção 17 detalha metadata obrigatória, compile-vs-runtime, 11 mixins, loot/modifiers e QA; sem teste runtime executado.
- **Data da última decisão:** 2026-08-26

> **Última autoridade física verificada — snapshot de 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #372: JAR `legendary_spellbooks-1.21.1+neo-0.3.2.jar`, mod id `legendary_spellbooks`, runtime `0.3.2`, SHA-1 `bda5b1fbe50ad834a6a76ad36789743c1057ebf5`.

> **ESCOPO CANÔNICO.** O último snapshot físico verificado (25/09/2026) registra `legendary_spellbooks-1.21.1+neo-0.3.2.jar`, mod ID `legendary_spellbooks`, versão 0.3.2. Integra conteúdo de Iron's Spells e Legendary Monsters; a release oficial 0.3.2 de NeoForge 1.21.1 permanece a mais recente localizada. O JAR atual em disco não foi reaberto nesta auditoria.
## 1. Identidade e source pin
O source oficial `Higurashi34m/Legendary-Spellbooks` está pinado no commit `62ced2f2b2693aa841251473cbbd726fdd928ed3`, de 16/06/2026, cujo commit é o bump 0.3.1→0.3.2. O `gradle.properties` da mesma revisão declara Minecraft 1.21.1, NeoForge 21.1.x, mod id `legendary_spellbooks` e versão 0.3.2. Essa revisão é a referência source-pinned desta ficha.
## 2. Papel e ownership
Legendary Spellbooks adiciona conteúdo próprio, mas depende semanticamente de dois providers externos: Iron's continua authority do spell engine, cast lifecycle, mana/cooldowns e spell data base; Legendary Monsters continua authority de seus mobs/encounters e IDs. O addon é authority apenas do conteúdo e das bridges que ele próprio registra.
## 3. Registries confirmados
A árvore source-pinned contém registries dedicados para armor materials, attributes, effects, entities, items, global loot modifiers, school, spells e upgrade-orb types. Também há classes de base para spells/summons, projéteis, summons e effects. Isso confirma que a integração vai muito além de recipes ou texturas.
## 4. Registry de spells
O source 0.3.2 possui **30 spells ativos** no registry. Organizados por escola/domínio:
- **Annihilation:** Annihilation Arrow, Annihilation Beam, Annihilation Bomb, Annihilation Geyser, Annihilation Resonance, Annihilation Shockwave, Flameborn Drift, Release Riftwalker Predator, Summon Flameborn Knights;
- **Blood:** Hematite Trishula, Possessed Soul Blade, Possessed Wing;
- **Evocation:** Collapsed Kingdoms Legion;
- **Fire:** Flame Eater, Flame Sector, Sentinel Saturation;
- **Ice:** Glacier Eruption, Glacier Ringburst;
- **Lightning:** Cloud Rail, Cloud Ring, Cumulo Charge, Energy Beam, Nimbus Array, Quad Tornado, Thunder Fanburst, Tornado, Triple Nimbus Array;
- **Nature:** Ambush Thorns, Fossilized Fury, Overgrown Shockwave.
A classe `PossessedFallingSoulBlade` existe no source, porém seu registro está comentado; portanto **não** entra na contagem de spells ativos.
## 5. Escola própria e spell engine
O addon registra uma escola própria de **Annihilation** e utiliza o framework de Iron's para spell data/casting. A escola/conteúdo são do addon; mana, cast validation, spell slots, cooldown e infraestrutura de spellbook pertencem ao provider Iron's. Integrações próprias não devem manter um segundo mana/cooldown paralelo.
## 6. Entities, projectiles e summons
O source-pinned inclui entities/projectiles e summons próprios, incluindo famílias de summons como Flameborn Guard/Warrior, Fractured Apostle, Haunted Guard/Knight, Skeloraptor e Summoned Annihilation Pursuer. Spawn, ownership, target selection e lifetime dessas entidades precisam permanecer server-authoritative.
## 7. Items e equipment
O addon registra spellbooks/equipamentos próprios, incluindo Annihilators Protocol Spellbook, Stormbound Spellbook, Tempest upgrade template e conjuntos temáticos como Oblivionmancer/Stormmancer. Esses items podem alterar atributos/spell behavior; não duplicar modifiers em RPG/Curios sem conferir o state real do item equipado.
## 8. Loot integration
`LSLootModifierRegistry` confirma global loot modifiers que conectam drops/recompensas a mobs do Legendary Monsters. Como o provider físico avançou para Legendary Monsters 2.2.2, IDs/loot hooks precisam de QA de runtime: o source 0.3.2 foi produzido contra uma linha anterior do mod-base.
## 9. Compatibilidade com Iron's
A release 0.3.2 é Beta para NeoForge 1.21.1. Seu changelog corrige um crash com versões de Iron's anteriores a 3.16.0. O pack físico usa **Iron's Spells 'n Spellbooks 3.16.3**, portanto esse bug específico está fora do range antigo, mas isso não prova compatibilidade integral com 3.16.3.
## 10. Static QA já identificado
A auditoria source-pinned registra pontos que exigem teste e não devem ser 'normalizados' sem reproduzir runtime:
- Glacier Ringburst declara max level 4 enquanto há loot/config que pode referenciar níveis 1–5;
- Stormbound possui tooltip/comportamento que precisa ser conferido contra `AffinityData` real;
- Fossilized Fury escala quantidade com `spellLevel`, exigindo verificação de limites/duplicação;
- Annihilation Resonance possui filtro de aliados que precisa ser validado em MP/teams.
Esses pontos são **riscos de QA**, não bugs confirmados no runtime atual.
## 11. Client / server
Cast acceptance, mana, damage, summon spawn, targeting, loot e progressão pertencem ao servidor/provider. Render, particles, sound presentation e tooltips são cliente. Um efeito visual não deve ser usado como evidência de cast concluído ou kill credit.
## 12. Lifecycle e persistência
Validar registry bootstrap, spellbook/item persistence, entity summon save/unload, death cleanup, loot modifier application e relog. Updates devem preservar registry IDs usados em items/saves; remover/renomear spell IDs em mundo existente pode invalidar spellbooks ou serialized spell data.
## 13. Boundary para projetos próprios
Black Arcana e RPG Skill Tree devem integrar via eventos/state causal de Iron's/entidades reais. Não duplicar mana, cooldown, school ownership, summon state ou loot logic. Mastery só deve ser creditada por cast/efeito confirmado no servidor, nunca pela existência do spell no registry ou tooltip.
## 14. Riscos técnicos
1. **Dependency drift:** runtime Iron's 3.16.3 e Legendary Monsters 2.2.2 são mais novos que o ambiente source-pinned.
2. **Registry drift:** IDs/classes do provider podem mudar e quebrar loot/summons.
3. **Duplicate loot:** global loot modifier + outro addon/quest recompensando o mesmo evento.
4. **Summon duplication/stale state** após relog/chunk unload.
5. **Double attributes** em equipment + RPG/Curios stack.
6. **Spell-level boundary errors** em conteúdo com ranges divergentes.
7. **Client authority leakage** se tooltip/VFX for tratado como state causal.
## 15. Matriz de testes
- [ ] Dedicated server inicia com Legendary Spellbooks 0.3.2 + Iron's 3.16.3 + Legendary Monsters 2.2.2.
- [ ] Os 30 spells ativos registram sem missing class/id.
- [ ] Spellbooks preservam spells após relog/restart.
- [ ] Cast consome mana/cooldown exatamente uma vez.
- [ ] Summons não duplicam após chunk unload/reload.
- [ ] Loot modifiers aplicam exatamente uma vez aos mobs-alvo.
- [ ] Glacier Ringburst respeita level bounds reais.
- [ ] Fossilized Fury não multiplica entities/items além do esperado.
- [ ] Annihilation Resonance respeita aliados em MP.
- [ ] Equipment não acumula modifiers após equip/unequip/relog.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 16. Evidências e limites
- modlist física: JAR 0.3.2 e providers atuais;
- source oficial commit `62ced2f2…`: version pin, registries, spells, entities, items, school e loot modifiers;
- publicação oficial 0.3.2: Beta NeoForge 1.21.1 e fix de compatibilidade com Iron's antigo.
O source é exato para o addon 0.3.2, mas os providers físicos atuais são mais novos; por isso a QA dinâmica permanece obrigatória.

## 17. Auditoria de release 0.3.2, contratos binários e integração de perks — 14/10/2026

### 17.1. Distribuição correta vs versão mais nova de outra linha

- **Último snapshot físico certificado (#372, 25/09/2026):** `legendary_spellbooks-1.21.1+neo-0.3.2.jar`, mod ID `legendary_spellbooks`, versão `0.3.2`, SHA-1 `bda5b1fbe50ad834a6a76ad36789743c1057ebf5`. **Não houve novo acesso ao JAR atual em disco.**
- **Fonte oficial para Minecraft 1.21.1 NeoForge:** [CurseForge File ID `8255715`](https://www.curseforge.com/minecraft/mc-mods/legendary-spellbooks/files/8255715), publicado em **16/06/2026** como **Beta**, ambiente **Client & Server**. O campo **File name** é `legendary_spellbooks-1.21.1+neo-0.3.2.jar`, igual ao snapshot. O hash do download não foi confrontado com o SHA-1 do perfil; identidade byte-a-byte **não certificada**.
- **Latest do projeto não é latest do perfil:** o [catálogo oficial de arquivos](https://www.curseforge.com/minecraft/mc-mods/legendary-spellbooks/files/all) mostra **0.4.1 de 18/08/2026** para **Minecraft 1.20.1 Forge**, não uma versão 0.4.1 de NeoForge 1.21.1. Para a linha NeoForge 1.21.1, **0.3.2 permanece a mais recente encontrada em 14/10/2026**. Não substituir o JAR por variante de loader/MC diferente.
- **Changelog próprio da 0.3.2:** atualização de tradução `zh_cn`, correção da localização da descrição de advancement, e correção de crash com versões de Iron’s anteriores a **3.16.0**. O fix não declara nova mecânica de magia, nem certifica compatibilidade total com Iron's 3.16.3. [Release oficial](https://www.curseforge.com/minecraft/mc-mods/legendary-spellbooks/files/8255715).

### 17.2. Metadata obrigatória da revisão exata

**Source oficial pinado:** [`Higurashi34m/Legendary-Spellbooks`, commit `62ced2f2b2693aa841251473cbbd726fdd928ed3`](https://github.com/Higurashi34m/Legendary-Spellbooks/tree/62ced2f2b2693aa841251473cbbd726fdd928ed3). Nesse commit, [`gradle.properties`](https://github.com/Higurashi34m/Legendary-Spellbooks/blob/62ced2f2b2693aa841251473cbbd726fdd928ed3/gradle.properties) declara Minecraft `1.21.1`, NeoForge compile `21.1.220`, `mod_version=0.3.2`, Java 21, mod ID e licença **LGPL-3.0**.

O [`neoforge.mods.toml` do source 0.3.2](https://github.com/Higurashi34m/Legendary-Spellbooks/blob/62ced2f2b2693aa841251473cbbd726fdd928ed3/src/main/resources/META-INF/neoforge.mods.toml) declara os seguintes `type="required"`, `side="BOTH"`:

| Dependência | Range declarado pelo source 0.3.2 | Snapshot do pack | Resultado |
| --- | --- | --- | --- |
| **Minecraft** | `[1.21, 1.21.2)` | 1.21.1 | Compatibilidade nominal do range |
| **NeoForge** | `[21,)` | 21.1.250 | Compatibilidade nominal do range |
| **Iron's Spells** (`irons_spellbooks`) | `[1.21.1-3.15.0,)` | #342: `irons_spellbooks-1.21.1-3.16.3.jar` | Range nominal satisfeito; APIs em runtime não testadas |
| **Legendary Monsters** (`legendary_monsters`) | `[1.21.1]` | #371: arquivo de distribuição `2.2.2`, **metadata interna `1.21.1`** | O range usa a **versão declarada internamente pelo provider**, não o número do filename; boot/loader metadata ainda precisam de confirmação atual |

**Versões do projeto usadas na compilação, não requisitos de runtime:** o [`gradle/libs.versions.toml` dessa revisão](https://github.com/Higurashi34m/Legendary-Spellbooks/blob/62ced2f2b2693aa841251473cbbd726fdd928ed3/gradle/libs.versions.toml) declara **Iron’s 1.21.1-3.16.1**, **Legendary Monsters CurseForge file `8241908` (2.1.14)**, **Curios 9.5.1+1.21.1**, **GeckoLib 4.8.4** e **Player Animator 2.0.4+1.21.1**. O [`build.gradle`](https://github.com/Higurashi34m/Legendary-Spellbooks/blob/62ced2f2b2693aa841251473cbbd726fdd928ed3/build.gradle) diferencia `implementation` dos providers Iron’s/Legendary Monsters/GeckoLib, `compileOnly` Curios API/JEI API e `runtimeOnly` auxiliares no **ambiente de build/teste**. Isso não transforma toda dependência de desenvolvimento em `type="required"` do mod distribuído; a metadata JAR real é a autoridade.

### 17.3. Contratos source-verified que afetam gameplay e RPG Skill Tree

**Registries/loot (commit exato, não binário inspecionado):** a [classe de entrada `LegendarySpellbooks.java`](https://github.com/Higurashi34m/Legendary-Spellbooks/blob/62ced2f2b2693aa841251473cbbd726fdd928ed3/src/main/java/dev/higurashi/legendary_spellbooks/LegendarySpellbooks.java) registra armor, attributes, tabs, effects, entities, items, loot modifiers, spells e escola. [`LSSpellRegistry`](https://github.com/Higurashi34m/Legendary-Spellbooks/blob/62ced2f2b2693aa841251473cbbd726fdd928ed3/src/main/java/dev/higurashi/legendary_spellbooks/registries/LSSpellRegistry.java) contém **30 registros ativos** e mantém `PossessedFallingSoulBladeSpell` comentado. [`LSLootModifierRegistry`](https://github.com/Higurashi34m/Legendary-Spellbooks/blob/62ced2f2b2693aa841251473cbbd726fdd928ed3/src/main/java/dev/higurashi/legendary_spellbooks/registries/LSLootModifierRegistry.java) registra os codecs NeoForge **`legendary_spellbooks:spell_scroll`** e **`legendary_spellbooks:item_drop`**.

- [`SpellScrollLootModifier.java`](https://github.com/Higurashi34m/Legendary-Spellbooks/blob/62ced2f2b2693aa841251473cbbd726fdd928ed3/src/main/java/dev/higurashi/legendary_spellbooks/common/loots/modifier/SpellScrollLootModifier.java) recebe campos `spells`, `drop_chance`, `count_min`, `count_max` e `difficulty_weight_multiplier`. A implementação usa RNG do loot context, `luck`, difficulty, weights/level rolls e adiciona os scrolls via `ISpellContainer.createScrollContainer` do Iron’s; repetições reduzem peso, mas **não são proibidas**. Para decidir **se** uma recompensa pode ser concedida, consumir o **resultado semântico confirmado do servidor**. Um evento/callback de loot por si só **não garante processamento exatamente uma vez**: o pipeline central de recompensas/mastery precisa de **chave estável de deduplicação** por ocorrência causal/resultado, persistência apropriada e tratamento de replay/reentrada; não conceder por VFX ou callback visual de morte.
- [`ItemLootModifier.java`](https://github.com/Higurashi34m/Legendary-Spellbooks/blob/62ced2f2b2693aa841251473cbbd726fdd928ed3/src/main/java/dev/higurashi/legendary_spellbooks/common/loots/modifier/ItemLootModifier.java) usa os campos `item` e `chance` e adiciona uma **cópia** da stack se RNG passar. Scripts próprios de loot de Legendary Monsters devem testar sobreposição em vez de gerar uma segunda recompensa.
- O [mixin `SpellDataMixin`](https://github.com/Higurashi34m/Legendary-Spellbooks/blob/62ced2f2b2693aa841251473cbbd726fdd928ed3/src/main/java/dev/higurashi/legendary_spellbooks/mixin/SpellDataMixin.java) intercepta `SpellData.getSpell()` de Iron’s e, somente quando o spell é `BaseDoubleSchoolSpell`, retorna uma instância reconstituída por `newDoubleSchoolSpell(spellLevel)`. A [classe `BaseDoubleSchoolSpell`](https://github.com/Higurashi34m/Legendary-Spellbooks/blob/62ced2f2b2693aa841251473cbbd726fdd928ed3/src/main/java/dev/higurashi/legendary_spellbooks/api/spells/BaseDoubleSchoolSpell.java) alterna escola conforme `getChangeSchoolLevel()` e incorpora atributo `SPELL_POWER` e poder da escola ao cálculo. **Risco para perks:** não cachear cegamente identidade de objeto `AbstractSpell` como chave estável de ownership, escola ou cooldown; resolver registry/spell IDs, nível e estado causal do casting no runtime real.
- O [mixin `IITemStackExtensionMixin`](https://github.com/Higurashi34m/Legendary-Spellbooks/blob/62ced2f2b2693aa841251473cbbd726fdd928ed3/src/main/java/dev/higurashi/legendary_spellbooks/mixin/IITemStackExtensionMixin.java) altera o retorno de `canElytraFly`/`elytraFlightTick` para `true` quando há `POSSESSED_WING_EFFECT`. No [`PossessedWingSpell`](https://github.com/Higurashi34m/Legendary-Spellbooks/blob/62ced2f2b2693aa841251473cbbd726fdd928ed3/src/main/java/dev/higurashi/legendary_spellbooks/common/spells/blood/PossessedWingSpell.java), o source 0.3.2 configura escola Blood, raridade Legendary, cooldown-base **240 s**, casting **20 ticks**, mana-base **250** + **25 por nível**, nível máximo **2**, e aplica o efeito por `400 × nível` ticks. São **constantes do source nominal**, sujeitas a modificadores/configuração e não confirmadas por execução; não tratá-las como valores finais observados no pack.
- O [`legendary_spellbooks.mixins.json`](https://github.com/Higurashi34m/Legendary-Spellbooks/blob/62ced2f2b2693aa841251473cbbd726fdd928ed3/src/main/resources/legendary_spellbooks.mixins.json) enumera **11 mixins comuns**: `SpellData`, extensões de voo e classes de summons, projectiles e entities (Annihilation Pursuer/Flame Strike/Portal, Ice Spike, Haunted Guard/Knight, Skeloraptor). **Compatibilidade de mixins** exige validar os targets da build Iron’s 3.16.3 e Monster 2.2.2, sem deduzir que o mixin sempre aplica com êxito apenas porque existe no source.

### 17.4. QA do pack atual, limites e decisão

- [ ] Inspecionar JAR atual `0.3.2` (SHA-1/SHA-256, versão, `neoforge.mods.toml`, mixins e dependências); comparar com distribuição CurseForge File ID `8255715`. Separar documentação de source e confirmação de bytecode empacotado.
- [ ] Confirmar dependências hard e metadata internas de **Iron’s #342 3.16.3** e **Legendary Monsters #371 distribuição 2.2.2 / metadata `1.21.1`**. Se testar Legendary Monsters 2.2.3 depois, refazer boot e regressão do addon por compatibilidade de classes/registries.
- [ ] Cold boot **client e dedicated server** com 0.3.2 e providers presentes; verificar mixin injection success e evitar classloading de VFX no servidor. Não inferir compatibilidade só pelo range de metadata.
- [ ] Para `SpellDataMixin`, testar `getSpell()` nos spells de escola dupla antes/depois do limiar, `getSpellPower`, mana/cooldown e serialização; verificar igualdade de registry IDs e impedir double-cast.
- [ ] Testar **Possessed Wing** em mundo e multiplayer: voo permissionado com efeito ativo, cessação após expire/death/dimension change, colisões com Elytra/Curios/slots, cooldown/consumo de mana exatamente uma vez.
- [ ] Testar modifiers `spell_scroll` e `item_drop` com difficulty, luck, rewards e scroll levels; simular **dupla entrega do mesmo callback** e **duas gerações de loot para a mesma ocorrência**. Conferir o **dedup key persistente** do pipeline central de progressão/recompensas para evitar crédito duplicado de mastery/quests; não atribuir automaticamente idempotência aos modifiers do addon.
- [ ] Verificar 30 spells ativos, school Annihilation, summons, projectiles, registry IDs, Scroll/Spellbook persistence, equip/unequip e mixed attribute stacking.
- [ ] Validar Legendary Monsters 2.2.2 bosses e loot/BlockEntities sem dupe após chunk unload/reload, world save, restart e multiplayer; especial atenção a alterações futuras de entidades quando se cogitar atualizar para 2.2.3.
- [ ] RPG Skill Tree: qualquer perk de dano, cooldown, duração ou spread deverá citar **Iron’s #342 3.16.3, Legendary Spellbooks #372 0.3.2** e o contrato verificado no JAR/SDK; usar **resultados server-authoritative, IDs, causalidade e uma chave estável de deduplicação por ação/resultado**, testando replays/reentradas (não tooltips, sprites ou novos `AbstractSpell` temporários).

**Estado:** **MANTER** a versão 0.3.2 na documentação até haver motivo comprovado para mudança (sem alterar a propriedade histórica `Decisão: Sem decisão`). Nada foi instalado ou removido; sem alterações de `modlist.md`, JARs, scripts, mods próprios, resource packs, datapacks, shaders ou mundo. **Nenhum teste runtime foi executado.**
