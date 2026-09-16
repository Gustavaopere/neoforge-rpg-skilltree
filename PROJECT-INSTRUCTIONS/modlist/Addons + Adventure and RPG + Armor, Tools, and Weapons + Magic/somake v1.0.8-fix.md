# Somake

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Arquivo JAR:** `somakespells-1.0.9-1.21.1.jar`
- **Versão 1.21.1:** 1.0.9
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia, RPG
- **Função:** Addon de Iron's Spells com mais de 50 feitiços, Aqua School própria, elemental charges, equipamentos/armas e, em 1.0.9, sistema Red Soul e novos spells/progressão.
- **Dependências:** Iron's Spells 'n Spellbooks é provider central. Integrações publicadas relevantes incluem L_Ender's Cataclysm e Born in Chaos, fisicamente presentes; outras schools/providers opcionais só são paths ativos quando instalados.
- **Sobreposição:** Amplia Iron's Spells; não substitui mana/casting/attribute framework. Sobreposição com outros addons deve ser avaliada por IDs, schools, effects e balance, não apenas tema.
- **Compatibilidade/Riscos:** Spell/attribute API drift, charge duplication, school registration conflict, projectile/AoE double-hit, equipment modifier stacking, missing external-school IDs, grimoire/progression persistence, Red Soul lifecycle e combat-engine interaction.
- **Observações:** JAR físico `somakespells-1.0.9-1.21.1.jar`, mod id `somakespells`, runtime 1.0.9. O antigo sufixo `-fix` pertencia ao filename 1.0.8 e não existe no JAR atual.
- **Procedência:** modlist física de 16/09/2026 + CurseForge oficial Somake 1.0.9 para NeoForge 1.21.1 + stack/integrations já auditados. Nenhum teste runtime foi executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/somake-spells-irons-spells-addon
- **Atualização/Status:** REAUDITADO EM 16/09/2026 — runtime físico atualizado de 1.0.8-fix para 1.0.9; dossiê reconciliado às mudanças Red Soul, novos spells/items, compatibility e balance. Certificação pendente de QC/re-fetch final.
- **Histórico da decisão:**
- **Data da última decisão:**

> 🌊 **ESCOPO CANÔNICO.** Runtime físico: `somakespells-1.0.9-1.21.1.jar`, mod id `somakespells`, versão `1.0.9`, NeoForge 1.21.1. Somake é addon de **Iron's Spells 'n Spellbooks** com catálogo próprio de spells, Aqua School, elemental charges, equipamentos e progressão adicional.

## 1. Provider central — Iron's Spells
Iron's Spells continua authority de casting, mana, spell attributes, base registration e infraestrutura compartilhada de spellbooks/equipment. Somake registra conteúdo sobre esse framework; não deve criar segunda fonte de mana ou ignorar authority server-side sem comportamento explícito próprio.

## 2. Catálogo e schools
A página oficial anuncia **mais de 50 spells**, concentrados em Lightning, Fire, Aqua e Symmetry, além de Blood/Ender e conteúdo de outras linhas. Somake introduz **Aqua School** própria. School registration, focus/icon, power/resistance, localization, IDs/tags e spell lists precisam coexistir sem alias ou overwrite duplicado.

## 3. Elemental charges
O sistema de charges elementais contempla schools suportadas e pode reconhecer schools de addons externos (ex.: Sound, Symmetry, Spirit, Geo) quando providers existem. Gain/consume deve ser server-authoritative e idempotente; cast/hit não pode conceder/consumir duas vezes por hooks duplicados. Provider ausente deve degradar com segurança.

## 4. Gameplay de spells e projectiles
Lightning/Fire/Aqua/Symmetry/Blood/Ender podem envolver projectiles, AoE, damage, control e effects. Servidor decide hit/damage/effect; cliente renderiza projectile/particles/sounds/prediction. Multi-target/AoE permanece regression surface contra double-hit por collision + explosion/event hook.

## 5. Armas e armaduras
O escopo migrado inclui armas como Glacium Greataxe, Core Splitter, Witherite Glaive, Ruined Blade, Clef Sword, Hallow Sword, Rock Sword e Boltcutter, além de conjuntos Dark Metal Battlemage, Ceranium, Aquamancer e Abyssium. Stats/bonuses/abilities pertencem ao addon; equip/unequip/death/relog deve aplicar/remover modifiers exatamente uma vez, inclusive com combat engines externos.

## 6. Grimoires e progressão
O catálogo anterior registra **Grimoires evolutivos ligados a schools mágicas**. A progressão deve usar state real do item/player. Thresholds/fórmulas/state machine não são inventados sem source/runtime específico; save/restart não pode duplicar modifier/progress.

## 7. Cataclysm e Born in Chaos
Integrações publicadas com **L_Ender's Cataclysm** e **Born in Chaos** permanecem concretas porque os providers estão no pack. Só paths explicitamente registrados devem ser atribuídos a Somake. Bosses/entities custom podem ter phases/imunidades/damage handling; spells não devem assumir mob vanilla.

## 8. Epic Fight / combat-engine boundary
O pack usa Epic Fight. Mesmo sem atribuir integração nativa não comprovada, melee weapons/cast animations podem atravessar hooks externos. Testar combat mode, weapon swap, interruption e hit registration; boot sem crash não valida state/animation completo.

## 9. Atualização instalada — 1.0.9
A release oficial 1.0.9 para NeoForge 1.21.1 adiciona/muda substancialmente o addon. Mudanças comprovadas incluem:
- novo sistema **Red Soul**, com Red Soul Lantern, Crimson Reflection, Red Echo, timer/HUD, bônus e recuperação via kills;
- conteúdo relacionado a **Legendary Monsters Spirit**;
- mudanças de **Sunbeam elemental charge effects**;
- novos itens **Red Soul Lantern**, **Corrupted Red Soul** e **Lullaby Flute**;
- novos spells: **Procession of Souls**, **Grave Sigil**, **Soul Bastion**, **Soul Latch**, **Spiral of Ruin**, **Soulfall Judgment** e **Funeral Bloom**;
- Summon Zombie passa a envolver Drowned em seu comportamento atualizado;
- ajustes/balance de Aqua e Symmetry, Rose's Secret e atributos de staffs/weapons/spellbooks;
- ajustes para Born in Chaos;
- compat opcional com Legendary Monsters;
- atualização de compat Cataclysm, incluindo correção ligada à API de projectile de Desert Wrath;
- melhoria de fallbacks de progressão quando mods opcionais estão ausentes.

Essas mudanças são agora parte do runtime físico e criam novos gates de HUD/state, progression, optional-mod loading, entity/projectile compat e equipment attributes.

## 10. Red Soul authority e lifecycle
Red Soul acrescenta state/progress próprio. A documentação pública confirma lantern/timer/HUD/bonuses/recovery, mas não autoriza inventar storage internals. Servidor deve decidir ganhos/consumo/bonus; HUD é apresentação. Validar kill attribution, death/relog, dimension transfer, save/restart e multiplayer para impedir vazamento/duplicação de state.

## 11. Casting lifecycle e multiplayer
Validar spell learn/equip, cast start/channel/release, mana/cooldown, interruption, projectile spawn/hit/despawn, death/relog, dimension transfer, save/restart e equipment swap. Server authority: mana/cooldown, charges, spell damage/effects, projectile owner, equipment modifiers e progression. Dois players não podem compartilhar charge/Red Soul/cooldown por chave incorreta.

## 12. Integrações concretas atuais
- **Iron's Spells:** provider obrigatório.
- **L_Ender's Cataclysm:** integração publicada; 1.0.9 inclui ajuste compat.
- **Born in Chaos:** integração publicada; 1.0.9 inclui ajustes.
- **Legendary Monsters:** path opcional; não presumir ativo sem presença física.
- **Epic Fight:** regression surface de combat lifecycle.
- Outros addons Iron's: auditar IDs/attributes reais antes de atribuir conflito.

## 13. Riscos técnicos
1. Iron's API drift;
2. charge duplication;
3. school collision/missing optional provider;
4. projectile/AoE double-hit;
5. equipment modifier stacking;
6. boss/entity custom incompat;
7. combat-engine timing conflict;
8. grimoire/progression persistence drift;
9. Red Soul HUD/state desync ou kill attribution duplicada;
10. optional-mod fallback quebrando boot/progression;
11. Cataclysm projectile API regressão.

## 14. Matriz de testes
- [ ] Dedicated server inicia com Somake 1.0.9 + Iron's atual.
- [ ] Aqua School registra uma única vez.
- [ ] Spell representativo de cada school aprende/equipa/casta.
- [ ] Mana/cooldown e elemental charges liquidam uma única vez.
- [ ] Provider opcional ausente não impede boot.
- [ ] Projectile/AoE não duplica dano.
- [ ] Armor/weapon modifiers não duplicam após relog/swap.
- [ ] Epic Fight não cria double-hit/state preso.
- [ ] Cataclysm/Born in Chaos integrations funcionam com providers atuais.
- [ ] Red Soul Lantern/HUD/timer/bonus/recovery mantêm state por player.
- [ ] Kill attribution de Red Soul não duplica em multiplayer.
- [ ] Novos spells 1.0.9 limpam effects após death/relog.
- [ ] Grimoire/progressão persiste corretamente quando aplicável.

**Nenhum teste foi executado nesta reauditoria documental.**

## 15. Evidências e limites
- modlist física de 16/09/2026: `somakespells-1.0.9-1.21.1.jar` / runtime 1.0.9;
- CurseForge oficial: catálogo base, schools, charges/equipment e changelog 1.0.9;
- catálogo anterior: Grimoires evolutivos, integrations e boundaries preservados;
- **limite:** fórmulas/thresholds/storage internals dos spells, Grimoires e Red Soul não foram inferidos sem prova específica.

## 16. Reauditoria física — 16/09/2026
Runtime atualizado de 1.0.8-fix para 1.0.9. O conteúdo técnico anterior foi preservado e os deltas 1.0.9 incorporados. Decisão permanece **Sem decisão**; nenhum teste de casting, Red Soul, charge, equipment ou integration foi executado.