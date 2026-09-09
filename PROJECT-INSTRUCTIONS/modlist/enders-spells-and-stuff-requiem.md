# Ender's Spells and Stuff: Requiem — 0.1.7

> **Runtime físico confirmado:** `ess_requiem-0.1.7.jar` · mod id `ess_requiem` · versão `0.1.7` · NeoForge 1.21.1 · Client & Server. Base física: Iron's Spells `1.21.1-3.16.3`, Ace's Spell Utils `1.2.7.2-1.21.1`, Apothic Attributes `2.10.1`, GTBC's SpellLib `2.2.0-1.21.1` e Discerning The Eldritch `1.4.4-1.21` presentes.

## 1. Papel no modpack
Ender's Spells and Stuff: Requiem é um addon de conteúdo para **Iron's Spells 'n Spellbooks**, focado em novas spells, armas e mecânicas mágicas. A página oficial o trata como sucessor de Ender's Spells and Stuff e afirma incompatibilidade intencional com o mod antigo porque parte do conteúdo retorna no Requiem.

## 2. Estado do conteúdo 0.1.7
A página oficial atualizada para 0.1.7 declara **16 armas e 42 spells**, com conteúdo extra opcional de **+1 arma e +2 spells** quando **Discerning The Eldritch** está instalado. Esse addon está fisicamente presente, então essa integração é relevante neste pack.

## 3. Dependências confirmadas
A página oficial lista como dependências:
- Ace's Spell Utils;
- Iron's Spells and Spellbooks;
- Apothic Attributes.

Índices de relações também associam GTBC's SpellLib/API à linha do projeto; ele está presente fisicamente. Como o manifesto do JAR não foi decompilado neste ciclo, não se afirma aqui qual desses vínculos é hard/soft além do que a página oficial publica.

## 4. Authority / ownership
- **Iron's Spells:** spell framework, mana/casting base, schools e spell containers.
- **Requiem:** suas spells, weapons, effects e regras próprias.
- **Ace's Spell Utils / GTBC SpellLib:** helpers/APIs consumidos por addons, não gameplay authority do Requiem inteiro.
- **Apothic Attributes:** attributes compartilhados quando usados por effects/items.

Não duplicar cooldown, mana debit ou damage settlement externamente.

## 5. Update 'Serious Summoning'
A descrição oficial de 0.1.7 destaca a atualização **Serious Summoning**. Entre os exemplos publicados estão uma mecânica Blood chamada **Strain**, além de spells de gelo e Eldritch. A ficha registra esses nomes como conteúdo documentado, sem inferir valores numéricos não publicados.

## 6. Blood — Strain
`Strain` é descrito oficialmente como troca de **max HP por blood spell power**. Isso cria integração direta com qualquer sistema global de atributos/vida do pack. O cálculo exato, stacking e persistência devem ser validados no runtime; não assumir fórmula.

## 7. Ice spells publicadas
A página oficial cita:
- **Glacial Sculpting** — transforma um summon em estátua congelada que explode, marcada como weapon-exclusive;
- **Lord of the Final Frost** — summons mortos congelam e explodem em estátuas de gelo, também weapon-exclusive.

Essas mechanics cruzam summon lifecycle, death events e explosion visuals.

## 8. Eldritch spells publicadas
A descrição atual lista exemplos como **Ebony Armor, Cursed Immortality, Tentacle Whip, Eternal Battlefield, Protection of the Fallen, Twilight Assault, Pale Flame, Spikes of Agony** e **Ebony Cataphract**.

Algumas mexem em survivability, spellcasting restriction, mana, death prevention, area control e melee damage. Cada spell continua authority apenas do próprio effect contract.

## 9. Integração Discerning The Eldritch
A página oficial diz que os `+1/+2` extras só aparecem quando Discerning The Eldritch está instalado. Como o pack usa 1.4.4-1.21, validar registry presence, recipe/loot visibility e spell school linkage após `/reload` e restart.

## 10. Incompatibilidade com o ESS antigo
O autor declara Requiem inerentemente incompatível com **Ender's Spells and Stuff** original. A modlist física atual não contém o mod antigo como top-level, portanto essa incompatibilidade não está ativa no pack atual.

## 11. Client / Server
**Servidor:** mana, spell cast validation, damage/effects, summon state, health/max-health changes, cooldowns e item state.

**Cliente:** animation, particles, sounds, HUD/tooltips e model rendering.

Uma animação/particle não pode ser usada como settlement de spell.

## 12. Lifecycle
Validar spell learn/equip, weapon equip, cast start/cancel/finish, summon spawn/death/despawn, player death/respawn, max-HP modifiers, dimension change, logout during effects, `/reload`, server restart e update de Iron's/Ace/GTBC/DTE.

## 13. Multiplayer
Spells AOE, summons, immortality/death prevention e health modifiers precisam ser server-authoritative. Dois clients observadores não podem duplicar summon, damage ou effect application.

## 14. Riscos
1. spell ID/config drift após update;
2. mana/cooldown aplicado duas vezes por compat externo;
3. Strain deixar max HP modifier stale após death/relog;
4. summon death disparar explosão duas vezes;
5. weapon-exclusive spell perder ownership ao trocar item durante cast;
6. DTE extras não registrarem por version drift;
7. Apothic Attributes modifier stacking inesperado;
8. GTBC/Ace API drift;
9. explosion-heavy spells cruzarem Explosive Enhancement visualmente;
10. addon antigo ESS ser reinstalado e causar conflito declarado.

## 15. Matriz de testes
1. Boot dedicado com todas as dependências físicas atuais.
2. Confirmar registry/creative/loot visibility de conteúdo 0.1.7.
3. Spell normal e weapon-exclusive spell.
4. Strain: apply/remove, death/respawn e relog.
5. Glacial Sculpting + summon death.
6. Lord of the Final Frost com múltiplos summons.
7. Cursed Immortality em death event controlado.
8. Ebony Armor/Cataphract e spellcasting restriction.
9. DTE extra content com 1.4.4 ativo.
10. Dois jogadores em AOE/summon scenario.
11. `/reload` + restart.
12. Smoke-test após update do Iron's Spells ou APIs.

**Esta catalogação não afirma que esses testes foram executados.**

## 16. Evidências
- modlist física canônica: JAR/mod id/version/hash e dependências/integrations presentes;
- CurseForge oficial 0.1.7: environment, 16 weapons, 42 spells, +1/+2 via DTE, incompatibilidade com ESS original e descrição do update Serious Summoning;
- páginas oficiais das APIs base para seus papéis de library/helper.

> **Boundary canônico:** Requiem é authority do **conteúdo que registra**; Iron's Spells permanece authority do casting/mana framework base.