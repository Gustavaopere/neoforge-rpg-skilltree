# Ars 'n' Spells

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8121a6cafcc07fa28c1a
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Ars 'n' Spells
- **Arquivo JAR:** `ars_n_spells-3.3.2.jar`
- **Versão 1.21.1:** 3.3.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Magia, Compat, RPG
- **Função:** Bridge bidirecional Ars Nouveau↔Iron's Spells 3.3.2: 5 modos de mana, gear/potion routing, pre-cast authority, scaling bidirecional, resonance, cooldown categories, progression/affinity, cross-cast, Spell Loom e spell-wheel binding. A linha 3.3.1/3.3.2 altera HUD/visibilidade sem evidência de mudança dos contratos centrais auditados.
- **Dependências:** Ars Nouveau 5.13.1 obrigatório. Iron's Spells 3.16.3 é provider opcional para cross-mod; Ars Elemental 0.7.10.1 e Ars Elemancy 1.18.3 fornecem school data quando presentes.
- **Sobreposição:** Toca mana/scaling/progression que outras bridges podem tentar unificar. Enquanto ativa, não duplicar esses domínios em integração própria nem confundi-la com Ars Polymorphia/Ars Hex, que têm responsabilidades diferentes.
- **Compatibilidade/Riscos:** Riscos: double-dip de spell power/perks, custo cross-cast duplicado, duas authorities de mana, affinity duplicada em multi-school, unified cooldown competindo com handlers locais, payload NBT antigo sem migração e config client tratada como authority no dedicated server.
- **Observações:** mod id `ars_n_spells`; runtime atual 3.3.2. A auditoria técnica profunda permanece ancorada na arquitetura 3.3.0, acrescida dos deltas oficiais 3.3.1/3.3.2; não foram inventadas mudanças de hooks ou save format não declaradas pelo upstream.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Ars 'n' Spells 3.3.1/3.3.2 + source-level audit da baseline 3.3.0. Release 3.3.2 declara ausência de mudanças de config/network protocol/save format em relação à 3.3.1.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ars-n-spells
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — runtime físico reconciliado para Ars 'n' Spells 3.3.2. Baseline source-level 3.3.0 preservada; 3.3.1 remove transaction receipt HUD e 3.3.2 corrige contextual mana/XP visibility sem mudanças de config/network/save frente à 3.3.1. QC global #46.
- **Histórico da decisão:** Manter. Em 07/09/2026 a ficha foi reconstruída contra a 3.3.0 real; corrigida definitivamente a hipótese de redundância genérica: esta bridge possui cross-cast, spell wheel, mana/scaling/progression e contracts próprios entre Ars e Iron's.
- **Data da última decisão:** 2026-09-07

> 🧬 **PADRÃO ALEX'S MOBS — DOSSIÊ OPERACIONAL EXAUSTIVO.** Runtime físico atual: `ars_n_spells-3.3.2.jar`, mod id `ars_n_spells`, NeoForge 1.21.1. A arquitetura funcional catalogada na 3.3.0 permanece a baseline documentada da bridge bidirecional Ars Nouveau ↔ Iron's Spells 'n Spellbooks; as releases oficiais 3.3.1/3.3.2 introduzem deltas de HUD, sem evidência de mudança dos contratos centrais aqui inventariados.

## 1. Identidade e versão
- **Mod:** Ars 'n' Spells.
- **JAR físico atual:** `ars_n_spells-3.3.2.jar`.
- **Runtime:** `3.3.2`.
- **Mod id:** `ars_n_spells`.
- **Loader/jogo:** NeoForge 1.21.1 / Java 21.
- **Baseline source-level auditada:** branch `port/neoforge-1.21.1` na linha 3.3.0, usada para os contratos centrais abaixo.
- **Delta oficial 3.3.1:** remove o transaction receipt HUD.
- **Delta oficial 3.3.2:** corrige a visibilidade da contextual mana bar/XP bar; a release declara explicitamente **nenhuma mudança de config, network protocol ou save format em relação à 3.3.1**.
- **Decisão:** **Manter**.

## 1.1 Patch boundary 3.3.0 → 3.3.2
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
