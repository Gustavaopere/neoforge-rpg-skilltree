# Ender's Spells and Stuff: Requiem — 0.1.7

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db811a8502f36f37296578  
> Estado no momento da reconciliação: `Integrado ao Github`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Reconciliado em: 2026-09-09

## Propriedades do registro

- **Mod:** Ender's Spells and Stuff: Requiem
- **Arquivo JAR:** `ess_requiem-0.1.7.jar`
- **Versão 1.21.1:** `0.1.7`
- **Categoria:** Magia; RPG
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/enders-spells-and-stuff-requiem
- **Função:** Addon de Iron's Spells 'n Spellbooks com 16 armas e 42 spells na linha 0.1.7, incluindo mecânicas de summoning, blood/Strain, ice e Eldritch; adiciona conteúdo extra quando Discerning The Eldritch está presente.
- **Dependências:** Página oficial lista Iron's Spells 'n Spellbooks, Ace's Spell Utils e Apothic Attributes; todos estão presentes: Iron's 3.16.3, Ace's 1.2.7.2 e Apothic Attributes 2.10.1. GTBC's SpellLib 2.2.0 também está fisicamente presente e aparece associado em índices de relações; DTE 1.4.4 habilita +1 arma/+2 spells extras.
- **Compatibilidade/Riscos:** Incompatibilidade declarada com o Ender's Spells and Stuff original; ele não está presente na modlist atual. Riscos locais: spell/API drift, Strain/max-HP modifier stale, summon-death double trigger, weapon-exclusive cast ownership, DTE extras, Apothic Attributes stacking e overlap visual de explosões.
- **Sobreposição:** Sobreposição temática ampla com outros addons Iron's, mas conteúdo próprio. É explicitamente incompatível com o Ender's Spells and Stuff original; não tratar outros addons como substitutos sem comparar spell/item IDs e mechanics.
- **Observações:** CurseForge 0.1.7 declara 16 armas e 42 spells; com Discerning The Eldritch instalado aparecem +1 arma e +2 spells. Update atual destacado: `Serious Summoning`; exemplos publicados incluem Strain, Glacial Sculpting, Lord of the Final Frost e várias spells Eldritch. Não foram inventadas fórmulas numéricas ausentes da documentação.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `ess_requiem-0.1.7.jar`, mod id `ess_requiem`, versão 0.1.7 e SHA-1 76eb6471698ce24d6fbdfcdd4b04397e96d401aa. Runtime também confirma Iron's 3.16.3, Ace's Spell Utils 1.2.7.2, Apothic Attributes 2.10.1, GTBC SpellLib 2.2.0 e DTE 1.4.4.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Requiem 0.1.7; content count, dependency stack, Serious Summoning, Strain/summon mechanics, DTE integration, authority, lifecycle, multiplayer, risks and tests cataloged.
- **Data da última decisão:** 2026-08-30

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
