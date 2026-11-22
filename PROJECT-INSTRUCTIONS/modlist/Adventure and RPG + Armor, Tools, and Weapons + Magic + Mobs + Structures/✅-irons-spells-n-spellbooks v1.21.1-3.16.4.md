# Iron's Spells 'n Spellbooks — versão 1.21.1-3.16.4 (atualização declarada em 22/11/2026)

> **Estado vigente do índice — 22/11/2026:** atualização **declarada pelo usuário** para `1.21.1-3.16.4` (`irons_spellbooks-1.21.1-3.16.4.jar`). Evidência de versão/filename: [CurseForge, file 9702219](https://www.curseforge.com/minecraft/mc-mods/irons-spells-n-spellbooks/files/9702219). Posição física original preservada. O JAR atual não foi inspecionado localmente.

> **Dependências e segurança:** 3.16.4: Alchemist Cauldron aceita automação via pipes e itens lançados; Amulet of Teleportation modifica Evasion, Ice Spider Dens considera aquifers e diversas correções de spells, summons, worldgen, configurações e desempenho. Iron's Lib e outros consumers serão sincronizados nos seus itens, sem impedir esta atualização. Este registro não significa que a combinação inteira do pack tenha sido testada. **Seções antigas com 'instalado 1.x' referem-se à baseline histórica anterior ao pedido atual.**


## Propriedades do registro

- **Mod:** Iron's Spells 'n Spellbooks
- **Arquivo JAR instalado (declarado pelo usuário):** `irons_spellbooks-1.21.1-3.16.4.jar` — arquivo de distribuição oficial CurseForge 9702219.
- **Versão instalada declarada (Minecraft 1.21.1 / NeoForge):** `1.21.1-3.16.4`; fonte da declaração: pedido de atualização enviado pelo usuário em 22/11/2026.
- **Categoria:** Magia, RPG, Worldgen, Exploração
- **Decisão:** Manter
- **Estado da pesquisa:** publicações e alterações upstream auditadas; ausência de smoke test no pack não impede o versionamento documental. Não atestar compatibilidade global antes da atualização das dependências de outros lotes.
- **Estado no pack:** atualização para `1.21.1-3.16.4` **declarada pelo usuário e sincronizada neste inventário documental**. O JAR do computador não foi lido diretamente.
- **Fonte:** https://github.com/iron431/Irons-Spells-n-Spellbooks/tree/1.21
- **Função:** Núcleo RPG de magia do pack: registra 9 escolas e 111 spells base, controla mana/casting/recasts/spell data, spellbooks/equipamentos, entidades/bosses, efeitos, alquimia e conteúdo de exploração/worldgen consumido por numerosos addons.
- **Dependências:** Source 3.16.3: GeckoLib, Player Animator, Patchouli, Curios e Iron's Lib; JEI é integração/dev surface. Pack físico: GeckoLib 4.9.2, Player Animator 2.0.4+1.21.1, Patchouli 1.21.1-93-NEOFORGE, Curios 9.5.1+1.21.1, Iron's Lib 2.1.0 e JEI 19.56.0.440.
- **Compatibilidade/Riscos:** Core com muitos addons físicos. Riscos: duplicate spell/school IDs, mana/cooldown/recast double-settlement, school attribute stacking, summon/projectile ownership, damage/heal recursion, worldgen/loot inflation, server/client spell-data drift e addons compilados contra 3.16.2/3.16.3 diferentes.
- **Sobreposição:** Coexiste com Ars Nouveau/Goety, mas mantém mana, schools, spell registry e casting próprios. Addons devem estender o provider, não duplicar mana/cooldown/cast. Bridges físicas do pack precisam respeitar Iron's como authority do spell-base.
- **Observações (estado atual declarado):** `irons_spellbooks-1.21.1-3.16.4.jar`, versão `1.21.1-3.16.4`; arquivo oficial 9702219. Os identificadores de JAR/SHA do snapshot anterior permanecem como histórico, não evidência de que a versão antiga ainda está instalada.
- **Procedência:** o snapshot físico histórico permanece fonte da posição e da versão **anterior**. O pedido explícito de 22/11/2026 é a fonte da versão atual declarada; filename novo obtido do CurseForge 9702219. Não houve leitura direta do computador, hash do JAR novo ou testes de runtime.
- **Atualização/Status:** 22/11/2026 — `1.21.1-3.16.3` → `1.21.1-3.16.4` (declaração do usuário); versão, JAR oficial, filename do dossier e índice mestre sincronizados. 3.16.4: Alchemist Cauldron aceita automação via pipes e itens lançados; Amulet of Teleportation modifica Evasion, Ice Spider Dens considera aquifers e diversas correções de spells, summons, worldgen, configurações e desempenho. Iron's Lib e outros consumers serão sincronizados nos seus itens, sem impedir esta atualização.
- **Histórico da decisão:** Núcleo do ecossistema Iron's Spells no pack. A versão 3.16.2 aparece nos logs de 18/08; o snapshot atual de 22/08 confirma atualização para 1.21.1-3.16.3. Addons devem ser avaliados contra esta versão-base atual, sem confundir conteúdo de spells com sistemas de progressão/balanceamento.
- **Data da última decisão:** 2026-08-26

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #342: JAR `irons_spellbooks-1.21.1-3.16.3.jar`, mod id `irons_spellbooks`, runtime `1.21.1-3.16.3`, SHA-1 `017fd8140c477f9ae602cf95594f1c23bef1d6e3`.

<callout icon="📖" color="purple_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `irons_spellbooks-1.21.1-3.16.3.jar`, mod id `irons_spellbooks`, versão `1.21.1-3.16.3`. O source oficial branch `1.21` declara exatamente essa versão; o commit auditado `e4056af…` contém os registries usados abaixo. **Decisão ****`Manter`**** preservada.**
</callout>
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
- **LATEST_**[**CHANGES.MD**](http://CHANGES.MD)**:** adições/fixes específicos da 3.16.3.
- **Limite:** 111 é o provider-base; não representa os spells adicionados por todos os addons do pack.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.

## Auditoria incremental upstream — 3.16.4 (22/11/2026; não instalada)

A versão top-level instalada continua `irons_spellbooks-1.21.1-3.16.3.jar`, runtime `1.21.1-3.16.3`, posição física #342. A publicação [**3.16.4**, CurseForge 9702219](https://www.curseforge.com/minecraft/mc-mods/irons-spells-n-spellbooks/files/9702219), lançada em **19/11/2026** para Minecraft **1.21.1 / NeoForge**, sucede a baseline documentada. As notas completas são do [changelog oficial do desenvolvedor](https://iron.wiki/changelog/), seção 3.16.4.

**Novidades e comportamento:** o **Alchemist Cauldron** aceita transferência de ingredientes por pipes de mods e itens lançados que entram no primeiro slot válido; itens inválidos ou quando não há vaga não devem ser consumidos. O **Amulet of Teleportation** deixa de conceder `Evasion` na teleportação por Evasion. A geração de **Ice Spider Dens** passa a detectar melhor interferências com aquíferos. São mudanças distintas de integração de automação, equilíbrio de combate e worldgen, não meramente bugfixes cosméticos.

**Correções publicadas:** dispenser do Alchemist Cauldron mantém nível coerente; escudos deixam de bloquear line of sight indevidamente; atributos de spellcasting de mobs não são sobrescritos por defaults; checagem de aliados de summons deixa de aceitar alvos incorretos; configuração de um spell corrompido não invalida todas as configs; mudanças de configuração de spell atualizam caches. `Counterspell`/unsummon removem corretamente o Undead Rift do Dead King, evitam aplicar cooldown indevido aos recasts de summons; Shadow Slash não aplica knockback excessivo; Ascension corrige dano/hitbox; upgrades preservam serialização; `Touch Dig`/`Spectral Hammer` lançados por mobs respeitam proteção de blocos; `Recall` passa a funcionar com mobs com home position; overlays de recast usam a âncora adequada; Echoing Arrow encontra alvos; worldgen não deve causar crash ao salvar chunks com portal frames; Black Hole reduz o custo excessivo em ticks de fluido; correções da tela de inscrição, pedestal de Cryomancer, Raise Dead (summons classificados como undead), ocorrência rara de Necromancers em trial spawner e teleportes em cultivos/farmland.

**Limite de escopo:** notas rotuladas especificamente como mudanças para Minecraft 1.20.1 não são atribuídas a NeoForge 1.21.1. A descrição upstream não demonstra que cada correção foi validada no pack.

**Gates de promoção:** conciliar requisitos binários com Iron's Lib, GeckoLib, Curios e Player Animator e addons; testar `Alchemist Cauldron` com pipes e itens, proteção/claims, débito e cooldown únicos em spells e summons, atributos, saves/config reload, teleportes, worldgen de portal, Black Hole e multiplayer. **Nenhum JAR ou config foi atualizado, instalado ou testado**; preservar nome, categorias e version pin `1.21.1-3.16.3`.


## Sincronização documental do usuário — 22/11/2026

**Mudança declarada e registrada:** `1.21.1-3.16.3` → `1.21.1-3.16.4`; JAR oficial `irons_spellbooks-1.21.1-3.16.4.jar` (CurseForge 9702219). A nomeação anterior e os trechos sobre 'não instalada' no histórico referem-se a auditorias anteriores a este pedido, não ao estado declarativo corrente.

**Conteúdo técnico verificado e riscos:** 3.16.4: Alchemist Cauldron aceita automação via pipes e itens lançados; Amulet of Teleportation modifica Evasion, Ice Spider Dens considera aquifers e diversas correções de spells, summons, worldgen, configurações e desempenho. Iron's Lib e outros consumers serão sincronizados nos seus itens, sem impedir esta atualização.

**Autoridade e limite:** versão instalada **declarada pelo usuário**, não leitura do diretório `mods/`; inventário físico antigo só mantém posições históricas. Requisitos de dependências que aparecem desatualizados em outros dossiers são pendências entre lotes e não impedem o versionamento deste mod. Testes de compatibilidade global não foram executados.

**Fonte da versão-alvo e filename:** https://www.curseforge.com/minecraft/mc-mods/irons-spells-n-spellbooks/files/9702219
