# Dreamless Spells

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db815aa782e029be12663d
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Dreamless Spells
- **Arquivo JAR:** `dreamless_spells-1.1.9.jar`
- **Versão 1.21.1:** 1.1.9
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia, RPG
- **Função:** Addon de Iron's Spells 'n Spellbooks com school Empty, spells/effects anti-magia, atributos, gear/curios e conteúdo material/worldgen próprio.
- **Dependências:** Iron's Spells 'n Spellbooks e Apothic Attributes no stack atual. Source 1.1.9: Minecraft 1.21.1, NeoForge 21.1.128 com range 21.1.0+. ISS permanece authority do casting framework.
- **Sobreposição:** Compartilha casting/attributes/silence surfaces com ISS e outros addons, mas não é substituto do framework. Evitar segunda aplicação de cancel, spell power/resistance, damage ou cooldown.
- **Compatibilidade/Riscos:** Riscos de double-cancel em SpellPreCastEvent, Drained counter bug, Dullard duration drift, mana/cooldown settlement após cast negado, Empty Spell Power/Resistance duplicados, equip modifier stale e ambiguidade do Counterspell mixin.
- **Observações:** School `dreamless_spells:empty`; IDs preservados: jadeskin, drained, dullard e doorway_effect. Mute cancela SpellPreCastEvent. Empty spells têm mana 0 com gate Emptied no checkpoint. Empty Gem tem craft confirmado; fonte survival de Empty Rune segue não confirmada.
- **Procedência:** Runtime: modlist física canônica de 08/09/2026 (595 top-levels). Source principal atual declara mod_version 1.1.9. Checkpoint Black Arcana preservado para IDs/behavior específicos de school/spells/effects e lacuna de Empty Rune; source/resources 1.1.9 corroboram Jade/Tungsten/Empty Gem.
- **Fonte:** https://github.com/stikbug/Neoforge-Dreamless-Spells-N-Spellbooks
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — source 1.1.9 pinado; Empty school/spells, materials, cancel pipeline, attributes, lifecycle/MP, riscos e regressões catalogados.
- **Histórico da decisão:** Sem decisão curatorial formal. Em 08/09/2026, o checkpoint técnico Black Arcana foi consolidado numa ficha operacional completa contra o source/runtime 1.1.9, sem apagar bugs e lacunas já identificados.

# Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `dreamless_spells-1.1.9.jar` · mod id `dreamless_spells` · versão `1.1.9` · NeoForge 1.21.1. O `gradle.properties` do source público principal declara exatamente `mod_version=1.1.9`.

## 1. Papel no modpack
Dreamless Spells é um addon de **Iron's Spells 'n Spellbooks (ISS)** voltado a ampliar opções para personagens melee/ranged e adicionar curios/equipamentos, conteúdo material e uma **nova spell school**. O addon combina conteúdo mágico com meios anti-magia/não-mágicos e possui eventos/mixins que interferem diretamente no casting.

## 2. Source pin
O repositório público `stikbug/Neoforge-Dreamless-Spells-N-Spellbooks`, `main` no commit atualmente auditado, corresponde à build `1.1.9`:
- Minecraft `1.21.1`;
- NeoForge build `21.1.128`, range `21.1.0+`;
- mod id `dreamless_spells`;
- mod version `1.1.9`;
- autor declarado `Iceforkkk`.

A ficha também preserva o checkpoint técnico detalhado produzido no projeto Black Arcana para a mesma linha, separando bugs/ambiguidades já identificados de qualquer afirmação de teste runtime.

## 3. Authority / ownership
- **ISS:** spell registry framework, mana/cooldown/casting pipeline e school semantics gerais.
- **Dreamless Spells:** sua school, spells, effects, attributes, items/blocks e cancelamentos/event hooks próprios.
- **Apothic Attributes:** framework/atributos externos quando consumidos pelo addon; não deve haver duplicação de modifier.

Mods próprios devem observar o resultado final do casting em vez de executar um segundo damage/heal/cancel para o mesmo spell.

## 4. School e atributos
O checkpoint source-pinned confirma a school:
- `dreamless_spells:empty`

Também existem conceitos/atributos de **Empty Spell Power** e **Empty Spell Resistance**, além de damage type próprio ligado à escola. Esses valores entram no pipeline de spell power/resistance do ecossistema; integrações RPG devem evitar aplicar o mesmo multiplicador em duas camadas.

## 5. Spells próprios confirmados
O inventário técnico preservado confirma quatro IDs de spell próprios:
- `dreamless_spells:jadeskin`;
- `dreamless_spells:drained`;
- `dreamless_spells:dullard`;
- `dreamless_spells:doorway_effect`.

O source atual também contém traduções para Jadeskin e Dullard e permanece na versão 1.1.9, corroborando a identidade da linha auditada.

## 6. Jadeskin
**Jadeskin** é um spell/effect próprio com comportamento defensivo documentado no checkpoint técnico. Ele não deve ser reimplementado como armor modifier paralelo por perks externos.

O addon também registra **Jade Ore** no source 1.1.9, com blockstate/model/loot gerados. O material Jade é portanto conteúdo real do addon, não apenas nome do spell.

## 7. Empty spells e mana
O checkpoint técnico identifica spells da escola Empty com **mana cost 0** e requisito/condição relacionado ao estado **Emptied**. Custo zero não significa cast gratuito incondicional: o gate de estado pertence ao addon/ISS e precisa ser respeitado.

Qualquer integração que apenas veja `mana=0` e ignore o precondition pode abrir uma rota de cast inválida.

## 8. Drained
O checkpoint anterior registra um **bug de contador** ligado a Drained. Essa informação é tratada como risco/source finding, não como resultado de reprodução no runtime atual.

Regression gate: aplicar/remover/renovar Drained e observar contagem exatamente uma vez em login, death/respawn e recast.

## 9. Dullard
O checkpoint identifica uma **divergência de duração** para Dullard entre superfícies do código/descrição. Até a duração ser validada no runtime ou reconciliada no source final, não hardcodar duração em quests/perks/tooltips externos.

## 10. Doorway Effect / Mute
`dreamless_spells:doorway_effect` está ligado à superfície anti-casting. O checkpoint confirma que o efeito **Mute** cancela `SpellPreCastEvent`.

Isso é uma interceptação direta do pipeline ISS: outro sistema de silence/mute deve coexistir com precedence clara e sem disparar duas mensagens/cancelamentos para o mesmo cast.

## 11. Counterspell mixin
A auditoria anterior encontrou **ambiguidade no mixin de Counterspell**. Até inspection/runtime QA resolver a rota exata, não assumir que todo cancelamento de cast ou refund relacionado a counterspell pertence a um único handler.

Riscos: double-cancel, cooldown/mana settlement divergente e efeitos client-side disparados apesar de cast negado.

## 12. Materiais e blocos confirmados
O source 1.1.9 confirma:
- `dreamless_spells:jade_ore`;
- `dreamless_spells:tungsten_ore`;
- `dreamless_spells:tungsten_block`.

Há loot tables/blockstates/models gerados para esses blocos e tags de mineração com pickaxe. O conteúdo inclui ainda itens como **Empty Gem** e Pavarium Nugget na lang/resource inventory.

## 13. Empty Gem e Empty Rune
O checkpoint técnico confirma que `empty_gem` é craftável. Na auditoria anterior, não havia sido localizada uma fonte survival confirmada para `empty_rune`.

Essa lacuna deve permanecer explícita: não criar recipe/loot route por inferência e não declarar `empty_rune` survival-obtainable sem nova evidência do runtime/data atual.

## 14. Curios, armas e armaduras
A descrição oficial do projeto confirma foco em curios, armor/weapons e opções para melee/ranged. IDs e stats que não foram enumerados no checkpoint/source inspection deste ciclo não são inventados nesta ficha.

Equip/unequip deve invalidar modifiers corretamente e nunca deixar Empty Spell Power/Resistance ou effects duplicados após death/relogin.

## 15. Configuração e dados
Recipes, loot, tags e data-driven resources existem no source. O schema completo de config não foi congelado neste ciclo. Qualquer key/toggle deve ser lida do arquivo/runtime atual antes de tuning do modpack.

## 16. Client / Server
- spell resolution, damage/effects, cancelamento e attributes: server/common-authoritative;
- render/models/particles/tooltips: client-facing;
- `SpellPreCastEvent` cancellation deve convergir no servidor;
- mixins/event hooks não devem disparar a mesma settlement nos dois lados.

## 17. Lifecycle
Validar:
- login/relogin;
- death/respawn;
- dimension change;
- equip/unequip de curios/gear;
- application/expiry dos effects;
- spell cast/recast/cancel;
- datapack/resource reload quando aplicável;
- chunk unload para ores/world content;
- server restart.

## 18. Multiplayer / idempotência
Mute, Drained, Dullard e Jadeskin devem ser state por entidade/jogador. Dois jogadores não podem compartilhar counter/state por erro estático. Cast negado deve ser negado exatamente uma vez e manter mana/cooldown coerentes com ISS.

## 19. Integrações concretas no pack
- **Iron's Spells 'n Spellbooks:** framework mágico obrigatório.
- **Apothic Attributes:** dependency/integration relevante para attributes.
- Outros silence/counterspell providers e sistemas RPG podem atuar na mesma superfície, mas não são equivalentes automaticamente.

## 20. Riscos
1. double-cancel no `SpellPreCastEvent`;
2. mana/cooldown settlement incorreto após cancel;
3. Drained counter bug;
4. Dullard duration drift;
5. Empty spell cost 0 ignorando precondition;
6. modifier duplicado em equip/relogin;
7. Empty Spell Power/Resistance double-applied;
8. `empty_rune` ganhar rota survival inventada por integração externa;
9. worldgen/material recipe drift;
10. counterspell mixin ambiguity.

## 21. Matriz de testes
1. Dedicated server boot com ISS e Apothic Attributes atuais.
2. Confirmar school `empty` e os quatro spells próprios no registry runtime.
3. Jadeskin: cast, expire, recast, death/respawn.
4. Drained: stacks/counter exatamente uma vez.
5. Dullard: medir duração real e reconciliar tooltip/code.
6. Doorway/Mute: negar cast e verificar mana/cooldown/feedback.
7. Counterspell interaction com Mute/doorway.
8. Empty spells com/sem estado Emptied e mana 0.
9. Equip/unequip de gear/curios e relog sem modifier residual.
10. Mine/loot Jade Ore, Tungsten Ore e Tungsten Block.
11. Verificar recipe de Empty Gem e procurar rota real de Empty Rune.
12. Dois jogadores usando cancel/effects simultaneamente.

**A catalogação não afirma que esses testes passaram.**

## 22. Evidências
- modlist física canônica de 08/09/2026: runtime `dreamless_spells-1.1.9.jar`;
- source público principal `stikbug/Neoforge-Dreamless-Spells-N-Spellbooks`, `gradle.properties` = 1.1.9;
- source/resources 1.1.9 para Jade/Tungsten/Empty Gem e traduções;
- checkpoint técnico Black Arcana previamente registrado para school, spells, attributes, Drained/Dullard, Doorway/Mute, Counterspell e recipe gap de Empty Rune.

> **Boundary canônico:** ISS controla o casting framework; Dreamless Spells controla sua school, spells/effects e conteúdo. Cancelamentos e attributes devem ser processados uma única vez e as lacunas de evidência permanecem fail-closed.
