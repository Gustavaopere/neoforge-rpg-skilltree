# Ars 'n' Spells

> **Reauditoria física — 18/09/2026.** Versão catalogada atual: `3.3.4`. O conteúdo abaixo preserva a página Notion reconciliada; a URL da própria página Notion foi deliberadamente omitida.

## Propriedades do registro

- **Mod:** Ars 'n' Spells
- **Arquivo JAR:** ars_n_spells-3.3.4.jar
- **Versão 1.21.1:** 3.3.4
- **Categoria:** Magia; Compat; RPG
- **Função:** Bridge bidirecional Ars Nouveau↔Iron's Spells: 5 modos de mana, gear/potion routing, pre-cast authority, scaling bidirecional, resonance, cooldown categories, progression/affinity, cross-cast, Spell Loom, carrier scrolls e spell-wheel binding. 3.3.3/3.3.4 ampliam binding/carriers e endurecem ownership de payment/casting.
- **Dependências:** Ars Nouveau 5.13.1 obrigatório. Iron's Spells 3.16.3 é provider opcional para cross-mod; Ars Elemental 0.7.10.1 e Ars Elemancy 1.18.3 fornecem school data quando presentes.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Riscos: double-dip de spell power/perks, custo cross-cast duplicado, duas authorities de mana, affinity duplicada, unified cooldown competindo com handlers locais, carrier revisions/network protocol divergentes, payload NBT antigo, debit/refund obligation não resolvida e config client tratada como authority no dedicated server.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ars-n-spells
- **Procedência:** modlist física atual + CurseForge oficial Ars 'n Spells 3.3.1–3.3.4; release 3.3.4 NeoForge 1.21.1 file ID 8881108, 14/09/2026; source-level audit da baseline 3.3.0 já documentada.
- **Observações:** Runtime físico 3.3.4. Baseline técnica 3.3.0 preservada, com deltas oficiais 3.3.1–3.3.4. A 3.3.4 consolida payment/cast lifecycle, recovery debit/refund, protocolo 7, carrier revisions, Curios mirroring toggle e restaura blank-scroll drops.
- **Atualização/Status:** READITADO EM 18/09/2026 — runtime físico atualizado de 3.3.2 para 3.3.4; deltas 3.3.3/3.3.4 incorporados, incluindo binding/Spell Loom e ownership consolidado de payment/cast lifecycle.
- **Decisão:** Manter
- **Histórico da decisão:** Manter. Em 07/09/2026 a ficha foi reconstruída contra a 3.3.0 real; corrigida definitivamente a hipótese de redundância genérica: esta bridge possui cross-cast, spell wheel, mana/scaling/progression e contracts próprios entre Ars e Iron's.
- **Sobreposição:** Toca mana/scaling/progression que outras bridges podem tentar unificar. Enquanto ativa, não duplicar esses domínios em integração própria nem confundi-la com Ars Polymorphia/Ars Hex, que têm responsabilidades diferentes.
- **Data da última decisão:** 2026-09-07

> 🧬 **PADRÃO ALEX'S MOBS — DOSSIÊ OPERACIONAL EXAUSTIVO.** Runtime físico atual: `ars_n_spells-3.3.4.jar`, mod id `ars_n_spells`, NeoForge 1.21.1. A arquitetura funcional catalogada na 3.3.0 permanece a baseline documentada da bridge bidirecional Ars Nouveau ↔ Iron's Spells 'n Spellbooks; as releases oficiais 3.3.1–3.3.4 são aplicadas explicitamente, incluindo mudanças materiais de binding e payment/cast lifecycle.
## 1. Identidade e versão
- **Mod:** Ars 'n' Spells.
- **JAR físico atual:** `ars_n_spells-3.3.4.jar`.
- **Runtime:** `3.3.4`.
- **Mod id:** `ars_n_spells`.
- **Loader/jogo:** NeoForge 1.21.1 / Java 21.
- **Baseline source-level auditada:** branch `port/neoforge-1.21.1` na linha 3.3.0, usada para os contratos centrais abaixo.
- **Delta oficial 3.3.1:** remove o transaction receipt HUD.
- **Delta oficial 3.3.2:** corrige a visibilidade da contextual mana bar/XP bar; a release declara explicitamente **nenhuma mudança de config, network protocol ou save format em relação à 3.3.1**.
- **Decisão:** **Manter**.
## 1.1 Patch boundary 3.3.0 → 3.3.4
A 3.3.1 removeu somente o painel visual de transaction receipts. A 3.3.2 corrige a comparação de mana cheia quando modifiers deixam o máximo fracionário e restaura corretamente a XP bar quando a contextual mana bar some no mesmo anchor. Essas mudanças são de apresentação/visibilidade; não autorizam inventar alteração em mana modes, cross-cast, affinity, cooldown, attachments ou save format. Os contratos centrais abaixo continuam documentados a partir da auditoria source-level 3.3.0, com o delta 3.3.1/3.3.2 aplicado explicitamente.
## 2. Dependências e build targets
O source 3.3.0 declara como base:
- **Ars Nouveau 5.13+**; build target upstream `5.13.1.1400`;
- **Iron's Spells 'n Spellbooks 3.15.0+**; build target `3.16.3`;
- integração testada no source também contra Ars Elemental 0.7.10.1, Ars Elemancy 1.18.3 e Ars Zero 2.0.2.
No pack físico atual estão presentes Ars Nouveau **5.13.1** e Iron's **3.16.3**.
Iron's é tratado pelo addon como provider opcional: sem ele, a bridge cai para comportamento Ars nativo; payloads cross-cast ficam dormentes, mas dimensões que o próprio Iron's persistiu no level data continuam sendo responsabilidade upstream do Iron's.
## 3. Mana unification — 5 modos
Config `mana_unification_mode`:
1. `iss_primary` — **default**; Iron's mana é a fonte de verdade, Ars lê/drena esse pool.
2. `ars_primary` — Ars mana é authority; casts Iron's pagam Ars mana.
3. `hybrid` — pool compartilhado bidirecional; `hybrid_mana_bar` escolhe qual HUD mostrar.
4. `separate` — pools independentes; cross-casts dividem custos entre ambos.
5. `disabled` — sem integração de mana; cada mod usa seu pool.
Taxas configuráveis: `conversion_rate_ars_to_iron`, `conversion_rate_iron_to_ars`, `dual_cost_ars_percentage`, `dual_cost_iss_percentage`.
### Regra de authority
Nunca criar um terceiro pool no modpack. A authority deve ser exatamente a escolhida pelo modo da bridge.
## 4. Gear perks e enchantments
A bridge roteia bônus de equipamento conforme o modo:
- `iss_primary`/`hybrid`: perks Ars afetam atributos Iron's relevantes;
- `ars_primary`: perks Iron's alimentam cálculos Ars;
- `separate`: cada gear fica no próprio pool.
Isto é provider-native. Não reaplicar mana regen/spell power por perk local sem verificar o valor final após a bridge.
## 5. Mana potions
Em `iss_primary`, efeitos de mana Ars alimentam o pool unificado em vez de um pool Ars não consumido. A implementação lê o aggregate de `PerkAttributes.MANA_REGEN_BONUS` e espelha no `MANA_REGEN` do Iron's em refresh periódico de **1 Hz**. Não existe segundo código de potion para somar novamente o mesmo efeito.
## 6. Pre-cast validation
`CastingAuthority` faz check pré-cast **apenas de mana** contra o pool correto para o modo ativo. Isso existe porque checks nativos de cada mod podem olhar o pool errado em primary modes.
- creative e custo zero passam;
- rejeição usa action-bar;
- cooldown/target/efeito continuam sob os contracts correspondentes.
## 7. Spell scaling bidirecional
### Iron's → Ars
Dano Ars recebe scaling a partir do spell power Iron's. O source combina global spell power e school spell power, aplica política para multi-school (`primary`, `max` default, `average`), depois affinity/resonance e limita o scalar por `spell_power_cap` (default 3.0).
### Ars → Iron's
Dano Iron's recebe o valor Ars `SPELL_DAMAGE_BONUS` como adição flat, vindo de qualquer fonte real — armor threads, Curios, potions ou outros providers.
### Eventos reais de spell damage
A 3.3.0 usa eventos de dano de spell com contexto real de cast, evitando staging por último spell ou adivinhação por DamageSource. Projectiles atrasados devem manter a causalidade do spell que os lançou.
## 8. Multi-school policy
- scaling de dano pode usar várias escolas conforme a policy;
- affinity/progression creditam somente a **escola primária** resolvida.
Isto é particularmente crítico com Ars Elemental/Elemancy: não creditar duas ou três tracks pelo mesmo cast.
## 9. Resonance
Sistema opcional, default habilitado no README da build. Monitora percentual de mana e pode aumentar dano Iron's acima do threshold configurado; threshold default documentado: **95%**. Não duplicar este bônus com perks de “mana cheia”.
## 10. Unified cooldowns
Sistema opcional e **desabilitado por default**. Agrupa spells em quatro categorias:
- OFFENSIVE;
- DEFENSIVE;
- UTILITY;
- MOVEMENT.
Cooldown é global por categoria entre os dois mods: um cast Ars OFFENSIVE pode bloquear um Iron's OFFENSIVE e vice-versa. Se habilitado, essa colisão é design, não bug.
## 11. Progression e affinity
A bridge mantém:
- cast counts persistentes por school;
- affinity 0–100 por school;
- progresso nos dois sentidos, Ars e Iron's;
- bônus derivados retroalimentando scaling dos dois lados.
Affinity decay é opt-in (`enable_affinity_decay=false` por default), com intervalo default **1200 ticks** e rate default **0.01 por dia Minecraft**.
## 12. Cross-mod spell casting — fluxo completo
Qualquer item pode receber spell do outro ecossistema por inscrição.
### Spell Transcription
1. craft do tablet no Enchanting Apparatus;
2. recipe oficial usa Novice Ars spellbook como reagent + Iron's spellbook + archwood log + source gem block;
3. custo documentado: **2000 Source**;
4. recipe é gated por `mod_loaded irons_spellbooks`;
5. no Ritual Brazier, deve haver exatamente um **source** de spell e um **target** blank num raio de 3 blocos;
6. ambiguidades/múltiplos itens falham fechadas;
7. source é consumido e target recebe componente `CrossModSpellList`.
### Casting
- right-click lança a inscrição ativa;
- sneak-right-click alterna múltiplas inscrições;
- custo passa pelo `BridgeManager` e respeita o modo de mana;
- `cross_cast_cost_multiplier` default **1.25**, aplicado exatamente uma vez.
### Spell Uninscription
- tablet próprio;
- recipe documentado: blank parchment + water bucket + source gem + archwood log, **500 Source**;
- funciona mesmo sem Iron's instalado, permitindo limpar payload legado;
- exige um único item inscrito no raio e restaura estado blank sem sidecar cross-cast.
## 13. Spell Loom — bloco próprio
A release registra o primeiro bloco próprio do mod:
- `spell_loom` + BlockItem.
Função: criar carrier scroll Iron's a partir de um spell Ars, com:
- spell source Ars;
- blank Iron's scroll;
- nome escolhido pelo jogador;
- nature;
- icon.
O resultado é um `irons_spellbooks:scroll` real com sidecar ANS.
## 14. Ars spells no spell wheel do Iron's
### Spellbook Binding
- tablet `spellbook_binding`;
- recipe via Enchanting Apparatus, custo documentado **2500 Source**;
- ritual lê **item entities dropadas no chão**, não inventories;
- exige exatamente um carrier scroll + um Iron's spellbook em até 3 blocos;
- qualquer item inesperado aborta o ritual;
- scroll é consumido e o spell Ars entra no book.
### Proxy spells
A bridge registra pool finito de proxies `ars_n_spells:ars_cross_1..8` porque o wheel do Iron's agrega por spell id.
- até **8 Ars spells** por spellbook conforme default/cap documentado;
- proxy slot cresce a capacidade em vez de expulsar spells Iron's reais;
- proxy é zero-cost; custo real é pago pelo pipeline cross-cast uma única vez.
## 15. Rituals/tablets registrados — 5
### Sempre disponível
1. **Spell Uninscription**.
### Iron's-gated
1. **Mana Infusion**.
2. **Mana Well**.
3. **Spell Transcription**.
4. **Spellbook Binding**.
O source registra tablets próprios porque os rituals entram depois do population pass do Ars; a bridge faz splice no `RitualRegistry` correspondente. Mana Infusion e Mana Well ganharam tablets obtíveis na linha 3.2+.
## 16. Itens/blocos próprios
- 1 bloco + BlockItem: `spell_loom`.
- até 5 Ritual Tablets conforme presença do Iron's.
O addon não é um content pack volumoso; a complexidade está nos systems/bridges, não em dezenas de itens decorativos.
## 17. Cross-cast storage — DataComponent 1.21.1
A inscrição usa `DataComponentType<CrossModSpellList>` em `ars_n_spells:cross_spells`.
O componente contém:
- lista imutável de inscrições;
- selected index;
- JSON Codec;
- StreamCodec para sync.
As chaves root-NBT antigas da linha Forge foram removidas; itens antigos não migram automaticamente.
## 18. Player state — NeoForge attachments
Affinity, cooldown e progression usam entity attachments:
- affinity: `copyOnDeath`;
- progression: `copyOnDeath`;
- cooldown: **não** copyOnDeath, portanto reset em respawn é intencional.
Não adicionar Capability antiga como fallback na 1.21.1.
## 19. Config e dedicated server
Arquivo: `config/ars_n_spells-common.toml`.
Existe tela de config via Mod List. Como gameplay config é SERVER config:
- singleplayer pode salvar/resetar pela UI;
- dedicated server deve editar TOML do servidor ou usar comandos `/ans` suportados.
## 20. Master toggles relevantes
Defaults documentados:
- `enable_mana_unification=true`;
- `mana_unification_mode=iss_primary`;
- `enable_resonance_system=true`;
- `enable_cooldown_system=false`;
- `enable_progression_system=true`;
- `enable_affinity_system=true`;
- `enable_affinity_decay=false`;
- `debug_mode=false`.
## 21. Authority e ownership
- Ars Nouveau: Ars spell grammar/Source/native casting.
- Iron's: Iron's spells/mana/schools/native wheel.
- Ars 'n' Spells: bridge, shared state, cross-cast, unified mode selection.
- Ars Elemental/Elemancy: school membership adicional consumida pela bridge.
Não criar integração paralela de mana, scaling, affinity ou cooldown enquanto esta bridge estiver authority desses domínios.
## 22. Riscos
1. double-dip de spell power/perks;
2. custo cross-cast cobrado duas vezes;
3. dois pools tratados como authority no modo primary;
4. affinity duplicada em multi-school;
5. cooldown local competindo com unified cooldown;
6. itens Forge-NBT antigos não migrados;
7. remoção de Iron's em mundo que contém dimension resources upstream;
8. proxy spell recontabilizado como cast real e gerando XP duas vezes;
9. binding ritual capturando item errado — a implementação atual falha fechada por isso;
10. config client tratada como authority em dedicated server.
## 23. Matriz de validação
- boot com Ars+Iron's e boot sem Iron's;
- testar os 5 modos de mana;
- potion/gear routing em cada primary mode;
- scaling Ars→Iron's e Iron's→Ars;
- dual-element spell com `primary/max/average`;
- resonance threshold;
- cooldown categories habilitadas/desabilitadas;
- affinity/progression e copyOnDeath;
- Spell Transcription/Uninscription;
- Spell Loom;
- Spellbook Binding e 8 proxy slots;
- cross-cast multiplier exatamente uma vez;
- save/reload/network sync do DataComponent;
- dedicated server config;
- remoção temporária de Iron's com payloads dormentes.
## 24. Fontes
- Modlist física do projeto, 07/09/2026.
- Source oficial `otectus/ars-n-spells`, branch `port/neoforge-1.21.1`, v3.3.0.
- README 3.3.0, registries `ModItemsRegistry`/`ModBlocksRegistry`, `ManaUnificationMode` e ritual registry.
- Guia consolidado de Magia do projeto.
## 25. Atualização instalada — 3.3.3 e 3.3.4
A linha atualmente instalada acrescenta mudanças materiais além dos ajustes de HUD 3.3.1/3.3.2.
### 3.3.3
- Blank Scroll próprio e loot associado;
- binding direto via Iron's Inscription Table;
- redesign/robustez do Spell Loom e carrier scrolls;
- caminho de binding unificado; Ars spells não devem aparecer no extraction slot normal da Inscription Table.
### 3.3.4
A release NeoForge 1.21.1 de 14/09/2026 porta ownership nativo do pagamento para o ciclo consolidado de cast:
- final-cost listeners executam uma vez;
- o native debit substituído é suprimido;
- effects, channel cancellation, scroll consumption e native/category cooldowns compartilham o mesmo lifecycle;
- recovery medido de debit/refund, balances Ars em double, recusa por float precision, validation scopes exception-safe e obligations não resolvidas persistentes;
- gates opcionais dos mixins de payment/ticker de Iron's restaurados e MixinExtras 0.5.3 embarcado;
- Curios attribute mirroring toggle e alinhamentos de affinity helper, resonance threshold, Source Jar bounds e primary-school default;
- carrier revisions passam a cobrir todos os native item components;
- protocolo client/server avança para **7**;
- blank-scroll drops restaurados em loot de Iron's Catacombs/Citadel.
Regression gates adicionais:
- [ ] client/server ambos em 3.3.4/protocolo 7;
- [ ] final cost debitado exatamente uma vez, inclusive discounts/free casts;
- [ ] channel cancel/refund não deixa debit órfão nem duplica scroll consumption;
- [ ] unresolved obligations sobrevivem restart sem double payment;
- [ ] carrier revision stale é rejeitada antes de produzir efeito.

## Complemento técnico preservado da reauditoria GitHub anterior

> 🧬 **PADRÃO ALEX'S MOBS — DOSSIÊ OPERACIONAL EXAUSTIVO.** Runtime físico atual: `ars_n_spells-3.3.4.jar`, mod id `ars_n_spells`, NeoForge 1.21.1. A arquitetura funcional catalogada na 3.3.0 permanece a baseline documentada da bridge bidirecional Ars Nouveau ↔ Iron's Spells 'n Spellbooks; as releases 3.3.1–3.3.4 são aplicadas explicitamente abaixo.
- **SHA-1 físico:** `53966330a468e626cd6469259af6778a5a7d9305`.
- **Delta 3.3.1:** remove o transaction receipt HUD.
- **Delta 3.3.2:** corrige visibilidade da contextual mana bar/XP bar; declarou nenhuma mudança de config, network protocol ou save format em relação à 3.3.1.
- **Delta 3.3.3:** Blank Scroll próprio, loot correspondente, binding direto pela Iron's Inscription Table e redesign/robustez do Spell Loom/carriers.
- **Delta 3.3.4:** ownership do pagamento/cast lifecycle, recovery de debit/refund, config parity, carrier revisions, protocolo 7 e drops restaurados.
A 3.3.1 removeu somente o painel visual de transaction receipts. A 3.3.2 corrigiu comparação de mana cheia sob modifiers fracionários e restauração da XP bar no mesmo anchor.
A 3.3.3 adicionou:
- **Blank Scroll** de Ars 'n' Spells, craftado com Ars Nouveau blank parchment + Source Gem;
- Blank Scrolls em loot de dungeon, stronghold, mansion, Ancient City, mineshaft e estruturas Iron's quando o provider está presente;
- binding de Ars carrier scroll diretamente na **Iron's Inscription Table**, além do ritual/comando existentes;
- redesign do Spell Loom e suporte tanto ao Blank Scroll ANS quanto ao blank Iron's scroll;
- unificação do sistema de binding, melhores validation/repair/tooltips/instructions;
- proteção para impedir Ars spells de aparecerem incorretamente no extraction slot normal da Inscription Table;
- compatibilidade com carrier scrolls antigos e reparo automático quando necessário.
Limitação oficial 3.3.3: chests de estruturas Ars Nouveau não recebem Blank Scrolls nesse estado.
A 3.3.4 porta o ownership nativo do pagamento para o ciclo final do cast: final-cost listeners rodam uma vez, o native debit substituído é suprimido e effects, channel cancellation, scroll consumption e cooldowns nativos/categoria compartilham o mesmo lifecycle. Adiciona recovery medido de debit/refund, exact Ars double balances, recusa por float precision, validation scopes exception-safe e persistência de unresolved obligations.
Esses deltas são **instalados**, não apenas candidatos upstream.
O source baseline 3.3.0 declara como base:
- **Ars Nouveau 5.13+**; build target `5.13.1.1400`;
- integrações também auditadas contra Ars Elemental 0.7.10.1, Ars Elemancy 1.18.3 e Ars Zero 2.0.2.
No pack físico atual estão presentes Ars Nouveau **5.13.1** e Iron's **1.21.1-3.16.3**.
Iron's é tratado como provider opcional: sem ele, a bridge cai para comportamento Ars nativo; payloads cross-cast ficam dormentes, mas dimensões que o próprio Iron's persistiu continuam responsabilidade upstream do Iron's.
A 3.3.4 restaura gates opcionais dos mixins de payment/ticker de Iron's e inclui **MixinExtras 0.5.3**.
A 3.3.4 endurece essa regra operacional ao impedir que o debit nativo substituído seja executado junto com o pipeline consolidado.
A 3.3.4 adiciona um **Curios attribute mirroring toggle**, que precisa ser tratado como authority configurável e não como autorização para mirror paralelo externo.
Em `iss_primary`, efeitos de mana Ars alimentam o pool unificado em vez de um pool Ars não consumido. A implementação baseline lê o aggregate de `PerkAttributes.MANA_REGEN_BONUS` e espelha no `MANA_REGEN` do Iron's em refresh periódico de **1 Hz**. Não existe segundo código de potion para somar novamente o mesmo efeito.
## 6. Pre-cast validation e payment ownership
`CastingAuthority` faz check pré-cast de mana contra o pool correto para o modo ativo. Creative/custo zero passam; rejeição usa action-bar; cooldown/target/efeito continuam sob seus contracts.
Na 3.3.4 o payment ownership passa a ser explicitamente consolidado:
- final cost listeners uma vez;
- native debit substituído suprimido;
- effect/channel cancellation/scroll consumption/cooldowns no mesmo lifecycle;
- recovery medido de debit/refund;
- exact double balances para Ars;
- refusal em float precision inconsistente;
- validation scopes exception-safe;
- unresolved obligations persistentes em vez de descarte silencioso.
Esse é um regression gate crítico para qualquer integração própria de mana/casting.
Dano Ars recebe scaling a partir do spell power Iron's. O source combina global spell power e school spell power, aplica política multi-school (`primary`, `max` default, `average`), depois affinity/resonance e limita o scalar por `spell_power_cap` (default 3.0).
Dano Iron's recebe o valor Ars `SPELL_DAMAGE_BONUS` como adição flat, vindo de fontes reais — armor threads, Curios, potions ou outros providers.
A baseline 3.3.0 usa eventos de dano com contexto real de cast, evitando staging por último spell ou adivinhação por DamageSource. Projectiles atrasados devem manter causalidade do spell que os lançou.
A 3.3.4 alinha o **primary-school default** ao comportamento da linha Forge correspondente. Não creditar duas ou três tracks pelo mesmo cast.
Sistema opcional, default habilitado na baseline. Monitora percentual de mana e pode aumentar dano Iron's acima do threshold configurado.
A 3.3.4 documenta **95% para fresh config** como threshold alinhado; escolhas já salvas permanecem inalteradas. Não duplicar esse bônus com perks de “mana cheia”.
Cooldown é global por categoria entre os dois mods. Se habilitado, essa colisão é design, não bug. Na 3.3.4 native/category cooldowns passam a compartilhar explicitamente o mesmo cast lifecycle consolidado.
Affinity decay é opt-in (`enable_affinity_decay=false` por default), com intervalo baseline **1200 ticks** e rate **0.01 por dia Minecraft**.
A 3.3.4 alinha helper de affinity para **0.5%** no contrato correspondente.
4. recipe gated por `mod_loaded irons_spellbooks`;
- sneak-right-click alterna inscrições em carriers genéricos;
- `cross_cast_cost_multiplier` baseline default **1.25**, aplicado exatamente uma vez.
- recipe: blank parchment + water bucket + source gem + archwood log, **500 Source**;
- funciona mesmo sem Iron's instalado para limpar payload legado;
- exige item inscrito único e restaura state blank sem sidecar cross-cast.
## 13. Blank Scroll e Spell Loom
A linha mantém o bloco `spell_loom` + BlockItem como workstation de carriers.
Na 3.3.3:
- foi adicionado Blank Scroll próprio Ars 'n' Spells;
- Spell Loom passou por redesign completo de model/textures/collision;
- aceita Blank Scroll ANS **ou** blank Iron's scroll;
- scroll detection, validation, repair, tooltips e instructions foram reforçados;
- carrier scrolls existentes permanecem suportados e podem ser reparados automaticamente.
Na 3.3.4, carrier revisions passam a cobrir **todo native item component**, reduzindo risco de revisão parcial/stale.
- recipe via Enchanting Apparatus, custo baseline **2500 Source**;
- ritual lê item entities dropadas no chão;
- exige exatamente um carrier scroll + Iron's spellbook em até 3 blocos;
- item inesperado aborta;
A 3.3.3 acrescenta um segundo caminho oficial: **binding direto pela Iron's Inscription Table**. Todos os métodos de binding passam a usar o mesmo sistema subjacente. Ars spells não devem aparecer no extraction slot normal da Inscription Table.
A bridge registra pool finito `ars_n_spells:ars_cross_1..8` porque o wheel do Iron's agrega por spell id.
- proxy slot aumenta capacidade sem expulsar Iron's spells reais;
O source registra tablets próprios porque rituals entram depois do population pass do Ars; a bridge faz splice no `RitualRegistry` correspondente.
- Blank Scroll próprio na linha 3.3.3+.
O addon não é content pack volumoso; a complexidade está nos systems/bridges.
A 3.3.4 amplia carrier revisions para todos os native item components e avança o **network protocol para 7**. Clientes antigos não podem enviar revisions incompatíveis: client e server precisam estar alinhados.
Arquivo baseline: `config/ars_n_spells-common.toml`.
A 3.3.4 alinha fresh-config defaults/bounds para Curios mirroring, affinity helper, resonance threshold, Source Jar e primary school. **Existing saved configuration choices remain unchanged**; portanto não confundir fresh default com migração forçada de config existente.
Defaults baseline documentados:
A linha 3.3.4 adiciona/expõe o toggle de Curios attribute mirroring; não presumir valor de config salva sem ler o TOML real.
- Ars 'n' Spells: bridge, shared state, cross-cast, unified mode selection e payment ownership quando substitui o fluxo nativo.
- Ars Elemental/Elemancy: school membership adicional consumida pela bridge quando presentes.
Não criar integração paralela de mana, payment, scaling, affinity ou cooldown enquanto esta bridge estiver authority desses domínios.
3. debit/refund obligation perdido após exception/cancel;
8. client/server com protocolo anterior a 7;
9. carrier revision incompatível/stale;
10. remoção de Iron's em mundo com resources upstream;
12. binding ritual/Inscription Table duplicando ou extraindo carrier indevidamente;
13. config client tratada como authority em dedicated server;
14. Blank Scroll loot/repair divergindo por provider/load condition.
- [ ] boot com Ars 5.13.1 + Iron's 1.21.1-3.16.3 e boot sem Iron's;
- [ ] client/server ambos em 3.3.4/protocolo 7; client antigo deve ser rejeitado sem corrupção;
- [ ] final cost listeners exatamente uma vez;
- [ ] cancel/channel cancel/refund sem debit órfão ou cobrança dupla;
- [ ] recovery de debit/refund e unresolved obligations sob exception controlada;
- [ ] potion/gear routing e Curios mirroring;
- [ ] resonance fresh-config 95% vs config existente preservada;
- [ ] Blank Scroll craft/loot/repair;
- [ ] Spell Loom com Blank Scroll ANS e blank Iron's scroll;
- [ ] Spellbook Binding por ritual e Iron's Inscription Table;
- [ ] carrier não aparece no extraction slot indevido;
- [ ] 8 proxy slots sem expulsar spells Iron's;
- [ ] save/reload/network sync do DataComponent/revisions;
- [ ] blank-scroll drops em Iron's Catacombs armory e Citadel tomes;
Nenhum teste foi marcado como executado nesta auditoria documental. Os regression fixtures/GameTests citados pelo upstream não são promovidos a resultado local do pack.
- Modlist física do projeto, 16/09/2026: JAR, mod id, runtime 3.3.4, SHA-1, Ars Nouveau 5.13.1 e Iron's 1.21.1-3.16.3.
- Source oficial `otectus/ars-n-spells`, baseline branch `port/neoforge-1.21.1`, v3.3.0, usado para contratos centrais.
- Releases oficiais 3.3.1, 3.3.2, 3.3.3 e 3.3.4 para os deltas específicos.
- README/registries baseline `ModItemsRegistry`/`ModBlocksRegistry`, `ManaUnificationMode` e ritual registry.
> **Boundary canônico:** Ars 'n' Spells é authority da **bridge** entre Ars e Iron's, inclusive payment/cast lifecycle onde explicitamente substitui o fluxo nativo. Os dois mods-base continuam authority de seus sistemas nativos fora desse contrato.
