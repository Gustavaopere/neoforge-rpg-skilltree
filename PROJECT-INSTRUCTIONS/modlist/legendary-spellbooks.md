# Legendary Spellbooks

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db81afa8a9c86cca23016a
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — Legendary Spellbooks `0.3.2`, Iron's `3.16.3` e Legendary Monsters `2.2.2` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Legendary Spellbooks
- **Arquivo JAR:** `legendary_spellbooks-1.21.1+neo-0.3.2.jar`
- **Versão 1.21.1:** 0.3.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Magia, RPG
- **Função:** Integra Iron's Spells com Legendary Monsters por conteúdo próprio: spells, escola Annihilation, summons/projectiles, spellbooks/equipment, attributes/effects e global loot modifiers.
- **Dependências:** Iron's Spells 'n Spellbooks + Legendary Monsters. Runtime físico atual: Iron's 3.16.3 e Legendary Monsters 2.2.2; source 0.3.2 foi pinado contra linhas anteriores e exige regressão.
- **Sobreposição:** Bridge/content addon; Iron's permanece authority de spell engine/mana/cast e Legendary Monsters de seus mobs/encounters. Não duplicar esses states em integrações próprias.
- **Compatibilidade/Riscos:** Release 0.3.2 Beta. Riscos principais: provider/API drift, summon persistence, duplicate loot, registry IDs, attribute stacking e spell-level boundaries. Fix conhecido de crash com Iron's <3.16.0 não prova compat integral com 3.16.3.
- **Observações:** Source oficial pinado em `Higurashi34m/Legendary-Spellbooks@62ced2f2b2693aa841251473cbbd726fdd928ed3`. Registry tem 30 spells ativos; `PossessedFallingSoulBlade` existe no source, mas o registro está comentado.
- **Procedência:** modlist.txt física atual + source oficial Legendary Spellbooks commit 62ced2f2b2693aa841251473cbbd726fdd928ed3 + publicação/changelog oficial 0.3.2.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/legendary-spellbooks
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — source 0.3.2 pinado; 30 spells ativos, escola/registries, summons/entities, equipment, loot modifiers, provider boundaries, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `legendary_spellbooks-1.21.1+neo-0.3.2.jar`, mod id `legendary_spellbooks`, versão `0.3.2`. É uma integração de conteúdo entre **Iron's Spells 'n Spellbooks** e **Legendary Monsters**, com spells, summons, equipamentos, escola própria e loot modifiers.

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
A auditoria source-pinned registra pontos que exigem teste e não devem ser “normalizados” sem reproduzir runtime:
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
