# Iron's Spells 'n Spellbooks

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db810ca904f2b2ec00c0ff
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Iron's Spells 'n Spellbooks
- **Arquivo JAR:** `irons_spellbooks-1.21.1-3.16.3.jar`
- **Versão 1.21.1:** 1.21.1-3.16.3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Magia, RPG, Worldgen, Exploração
- **Função:** Núcleo RPG de magia do pack: registra 9 escolas e 111 spells base, controla mana/casting/recasts/spell data, spellbooks/equipamentos, entidades/bosses, efeitos, alquimia e conteúdo de exploração/worldgen consumido por numerosos addons.
- **Dependências:** Source 3.16.3: GeckoLib, Player Animator, Patchouli, Curios e Iron's Lib; JEI é integração/dev surface. Pack físico: GeckoLib 4.9.2, Player Animator 2.0.4+1.21.1, Patchouli 1.21.1-93-NEOFORGE, Curios 9.5.1+1.21.1, Iron's Lib 2.1.0 e JEI 19.53.0.426.
- **Sobreposição:** Coexiste com Ars Nouveau/Goety, mas mantém mana, schools, spell registry e casting próprios. Addons devem estender o provider, não duplicar mana/cooldown/cast. Bridges físicas do pack precisam respeitar Iron's como authority do spell-base.
- **Compatibilidade/Riscos:** Core com muitos addons físicos. Riscos: duplicate spell/school IDs, mana/cooldown/recast double-settlement, school attribute stacking, summon/projectile ownership, damage/heal recursion, worldgen/loot inflation, server/client spell-data drift e addons compilados contra 3.16.2/3.16.3 diferentes.
- **Observações:** Registry exato no source 3.16.3: 9 schools e 111 spells ativos — Blood 10, Ender 16, Evocation 17, Fire 13, Holy 13, Ice 12, Lightning 10, Nature 13, Eldritch 7. 3.16.3 adiciona Cooked Ice Spider Egg/Satiation e corrige Chain Creeper, Arcane Shackle e Alchemist Cauldron edge cases.
- **Procedência:** modlist.txt física atual + source oficial Iron431 branch 1.21, commit auditado e4056af..., com mod_version 1.21.1-3.16.3 + CurseForge oficial release 3.16.3 de 18/08/2026 + LATEST_CHANGES.MD e registries SchoolRegistry/SpellRegistry exatos.
- **Fonte:** https://github.com/iron431/Irons-Spells-n-Spellbooks/tree/1.21
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Iron's Spells 'n Spellbooks 1.21.1-3.16.3 source-pinned; 9 schools/111 spells ativos exatos, cast/mana/recast/damage, spellbooks/equipment, mobs/worldgen, Alchemist Cauldron, 3.16.3 fixes, addon authority, lifecycle/multiplayer, riscos e testes catalogados.
- **Histórico da decisão:** Núcleo do ecossistema Iron's Spells no pack. A versão 3.16.2 aparece nos logs de 18/08; o snapshot atual de 22/08 confirma atualização para 1.21.1-3.16.3. Addons devem ser avaliados contra esta versão-base atual, sem confundir conteúdo de spells com sistemas de progressão/balanceamento.
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `irons_spellbooks-1.21.1-3.16.3.jar`, mod id `irons_spellbooks`, versão `1.21.1-3.16.3`. O source oficial branch `1.21` declara exatamente essa versão; o commit auditado `e4056af…` contém os registries usados abaixo. **Decisão `Manter` preservada.**

## 1. Papel e authority
Iron's Spells 'n Spellbooks é o núcleo de magia RPG deste ecossistema. É authority do spell registry base, schools, mana/cast pipeline, cooldown/recast, spell data, spellbooks e do conteúdo próprio de mobs, efeitos, equipamentos, alquimia e worldgen. Addons podem registrar escolas/spells/bridges, mas não devem criar segunda ledger de mana ou liquidar o mesmo cast duas vezes.

## 2. Matriz física atual
O source 3.16.3 foi desenvolvido com GeckoLib 4.7.5.1, Player Animator 2.0.1, Patchouli 1.21-87, Curios 9.5.1 e Iron's Lib 2.1.0-SNAPSHOT. O pack usa GeckoLib 4.9.2, Player Animator 2.0.4, Patchouli 1.21.1-93, Curios 9.5.1+1.21.1 e Iron's Lib 2.1.0. Essas diferenças são version matrix, não incompatibilidades presumidas.

## 3. Registry exato de escolas — 9
`SchoolRegistry` registra exatamente nove escolas base: **Fire, Ice, Lightning, Holy, Ender, Blood, Evocation, Nature e Eldritch**. Cada school associa focus/tag, Spell Power, Magic Resist, cast sound e damage type próprios. Addons podem adicionar schools ao registry, portanto a contagem total do pack será maior que a contagem base.

## 4. Registry exato de spells — 111 ativos
O `SpellRegistry` do source pin possui **111 registrations ativos**, distribuídos assim:
- Blood: 10.
- Ender: 16.
- Evocation: 17.
- Fire: 13.
- Holy: 13.
- Ice: 12.
- Lightning: 10.
- Nature: 13.
- Eldritch: 7.
Linhas comentadas não entram na contagem. Essa é a cobertura exata do provider-base 3.16.3, não o total após addons.

## 5. Spell registry e configuração
Spells vivem em registry próprio `irons_spellbooks:spells`; schools em `irons_spellbooks:schools`. `getEnabledSpells()` filtra pelo estado habilitado e o cache school→spells é limpo em config reload. Consequentemente registry presence, enabled state e disponibilidade efetiva são conceitos diferentes.

## 6. Mana, cast e recasts
O source possui `MagicData`, networking de casting e estruturas de recast/cast source. Mana, cast admission, spell level, cooldown e recasts precisam ser server-authoritative. Animation, particles ou um packet client-side não são evidência suficiente de conclusão de cast para quests/perks.

## 7. Damage, healing e ownership
O mod mantém damage types escolares e efeitos de spells que podem causar dano, cura, summons, projectiles e movement. A attribution causal precisa preservar caster/owner/source. Bridges como Iron's Apothic, Epic Fight, IronSable e projetos próprios não devem reaplicar damage/heal ao observar o evento final.

## 8. Spellbooks, scrolls e equipamento
Spellbooks armazenam/selecionam spells e integram atributos, slots e progressão próprios. Scrolls/equipment são parte do ecossistema de aquisição/casting. Curios e addons podem complementar slots ou modifiers; o item/provider original permanece authority de seus componentes e spell container.

## 9. Mobs, bosses e summons
O source inclui entidades e conteúdo de encounters além do spell framework. Summons precisam preservar owner, friendly-fire rules, dimension/chunk lifecycle e cleanup em morte/despawn/reconnect. Quests externas devem creditar kill/cast com causalidade real, não por VFX ou nome da entidade.

## 10. Alchemist Cauldron
O Alchemist Cauldron é uma superfície própria de crafting/alquimia. A 3.16.3 corrige um server check ausente em particles durante edge-case damage e corrige retenção indevida de `level` durante dispenser interactions. Isso é um regression gate concreto para automação, client/server e lifecycle.

## 11. Worldgen, loot e progressão
Iron's adiciona conteúdo de exploração/worldgen e loot que distribui seus recursos/spells/equipamentos. Com muitos addons, pools de loot/spells e estruturas podem inflar. Balance deve ser feito pelo data final carregado, sem remover arbitrariamente o core que os addons requerem.

## 12. Addons físicos e boundaries
O pack contém várias extensões: Iron's Spellbooks KubeJS, IronSable, Magic From The East, Iron's Apothic, Goety Iron, Portal bridge, compat de animação e outras. Regra central: **Iron's continua authority do spell-base/mana/cast**; addon é owner apenas do conteúdo ou tradução que adiciona. Um addon que reage a cast não pode liquidar mana/cooldown novamente.

## 13. Release 3.16.3 — adições
A release adiciona **Cooked Ice Spider Egg** e um **Ice Spider Satiation System**. Esses recursos pertencem ao provider-base e devem ser validados junto do conteúdo Ice Spider sem presumir integração adicional de addons.

## 14. Release 3.16.3 — fixes
Correções exatas: Chain Creeper damage; mobs sufocados podendo quebrar blocos inquebráveis; Arcane Shackle raycasting em lava; server check do Alchemist Cauldron para particles em edge-case damage; e stale level state do Cauldron em dispenser interactions. Não atribuir outros fixes à 3.16.3.

## 15. Client / server
Gameplay state de mana, cast, cooldown, damage, summons, loot e crafting é server-authoritative. Cliente executa GUI, animation, particles, sound e rendering. Registry/spell-data mismatch entre lados pode impedir conexão ou produzir state divergente; addon client callback não pode conceder progresso.

## 16. Lifecycle e multiplayer
Validar login/reconnect, spell selection, cast/cancel, recast, death/respawn, dimension transfer, summon owner lifecycle, projectile despawn, chunk unload, config/data reload, Cauldron automation e server restart. Dois jogadores usando o mesmo spell não podem compartilhar cooldown/recast/state.

## 17. Riscos técnicos
- duplicate spell/school IDs entre addons;
- spell config enabled/disabled divergir de expectations externas;
- mana/cooldown/recast liquidado duas vezes;
- damage/heal recursive proc;
- summon/projectile attribution errada;
- school Spell Power/Resist stacking excessivo;
- addon 3.16.2 assumptions contra core 3.16.3;
- loot/spell-pool/worldgen inflation;
- Cauldron automation/state regression;
- client animation/VFX usado como gameplay authority;
- config reload deixar caches ou scripts stale.

## 18. Matriz de testes obrigatória
- [ ] Dedicated server + cliente iniciam com core 3.16.3 e addons físicos.
- [ ] Registry base contém 9 schools/111 spells e addons não colidem IDs.
- [ ] Cast válido consome mana/cooldown exatamente uma vez.
- [ ] Cast cancelado não concede progresso nem deixa recast stale.
- [ ] Spell disable/config reload atualiza disponibilidade sem registry corruption.
- [ ] Damage/heal attribution permanece no caster correto.
- [ ] Summons persistem/limpam owner state corretamente em relog/dimension change.
- [ ] IronSable/Iron's Apothic/KubeJS não duplicam settlement do cast.
- [ ] Chain Creeper e Arcane Shackle exercitam regressões 3.16.3.
- [ ] Alchemist Cauldron funciona em player e dispenser paths sem stale level/client-side particle mutation.
- [ ] Worldgen/loot de Iron's e addons não cria bloqueio severo de progressão.

## 19. Evidências e limites
- **Modlist física:** JAR/mod id/version e versões das dependencies atuais.
- **Source exato:** branch 1.21 / commit auditado `e4056af…`, `mod_version=1.21.1-3.16.3`.
- **Registries exatos:** `SchoolRegistry` = 9 schools; `SpellRegistry` = 111 registrations ativos.
- **LATEST_CHANGES.MD:** adições/fixes específicos da 3.16.3.
- **Limite:** 111 é o provider-base; não representa os spells adicionados por todos os addons do pack.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
