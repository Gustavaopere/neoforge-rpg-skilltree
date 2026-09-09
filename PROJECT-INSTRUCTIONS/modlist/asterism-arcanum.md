# Asterism Arcanum — 1.21.1-0.1.0

> ✅ Versão física: `asterismarcanum-1.21.1-0.1.0.jar`, mod id `asterismarcanum`, versão runtime `1.21.1-0.1.0`. Source pin auditado: `BirdieVibes/Asterism-Arcanum@f1738c7813a85d31a6da10e6c9f2dbce18d2b583`. O source foi compilado contra Iron's Spells 'n Spellbooks 3.15.6; o pack usa 3.16.3.

## 1. Papel e autoridade
Asterism Arcanum é addon de Iron's Spells 'n Spellbooks que introduz a **Astral school**, spells próprios, equipamento, mobs, materiais e blocos decorativos/estruturais. Iron's continua autoridade do spell system, mana, cooldown, scrolls e school power; Asterism é autoridade apenas do conteúdo Astral que registra.

## 2. Spells registrados — 11
1. **Starfire** — projétil com piercing/ricochet.
2. **Brightburst** — dano/repulsão em área, escalado em relação à knockback resistance.
3. **Celestial Tether** — suspende o caster e anula quantidade de ataques durante a duração.
4. **Star Swarm** — gera grupos de projéteis em intervalos durante o spell.
5. **Luminous Beam** — ataque em cone estreito/alongado.
6. **Astral Gateway** — registrado, concebido como teleporte/dimensão; no source auditado permanece incompleto/não apto a progressão survival normal.
7. **Summon Lunar Moths** — invoca giant lunar moth mount.
8. **Astral Echo** — teleporte instantâneo com janela/echo passível de counterspell.
9. **Piercing Light** — múltiplos projéteis curtos ao redor do caster.
10. **Starcutter** — raycast em entidade e explosão atrasada.
11. **Silvery Barbs** — proteção temporária de aliados/summons contra dano.

`Trailblaze` existe como conceito/código comentado, porém **não está registrado** neste source pin e não conta como spell ativo.

## 3. Estado survival dos spells
O inventário anterior da página foi preservado e refinado: **10 spells são tratáveis como conteúdo de jogo**, enquanto Astral Gateway requer isolamento por estar registrado porém unfinished/creative-only no estado auditado. Não permitir que Gateway entre em loot/crafting/progressão apenas porque o registry entry existe.

## 4. Entidades registradas — 11

### Spell/projectile/misc — 8
- `starfire`
- `luminous_beam`
- `star_swarm`
- `piercing_light`
- `thrown_mothspawn`
- `starcutter_entity`
- `summoned_lunar_moth`
- `celestial_tether_entity`

### Mobs — 3
- `lunar_moth` — creature; source comenta spawn em mountains e relação com materiais/luminance.
- `dragonfly` — creature.
- `astromancer` — monster/caster Astral.

O source contém um biome modifier gerado para spawn de Lunar Moth. Spawn natural e pesos devem ser lidos do datapack/provider; não hardcodar em integração própria.

## 5. Itens standalone — 15 confirmados

### Materiais/utility
- `ethereal_talisman`
- `lumine_dust`
- `mothspawn`
- `astral_rune`
- `dragonfly_wings`
- `astral_upgrade_orb`

### Spawn eggs — 3
- `dragonfly_spawn_egg`
- `lunar_moth_spawn_egg`
- `astromancer_spawn_egg`

### Astral equipment — 6
- `astral_helmet`
- `astral_chestplate`
- `astral_leggings`
- `astral_boots`
- `celestial_staff`
- `astrolabe`

As quatro peças de armor usam `AstralMagicArmorItem`. Celestial Staff é caster/staff do school. Astrolabe é spellbook/Curios-like equipment próprio. Astral Upgrade Orb usa o upgrade-orb component do Iron's com tipo de upgrade Astral Spell Power.

## 6. Blocos registrados — 27

### Lumine family — 13
- `lumine`
- `chiseled_lumine`
- `lumine_bricks`
- `polished_lumine`
- stairs/slab/wall de Lumine
- stairs/slab/wall de Lumine Bricks
- stairs/slab/wall de Polished Lumine

### Umbral Lumine family — 13
- `umbral_lumine`
- `chiseled_umbral_lumine`
- `umbral_lumine_bricks`
- `polished_umbral_lumine`
- stairs/slab/wall de Umbral Lumine
- stairs/slab/wall de Umbral Lumine Bricks
- stairs/slab/wall de Polished Umbral Lumine

### Outro — 1
- `dragonfly_comb`

Cada bloco é registrado também como BlockItem; portanto há **27 block-item entries adicionais** além dos 15 itens standalone, para 42 item-registry surfaces observáveis no source.

## 7. School e progressão Astral
Há registry próprio de school Astral e upgrade-orb type para Astral spell power. O **Astromancer** gera/usa conteúdo Astral e a auditoria anterior confirmou seleção de scroll Astral com quality aleatória documentada no código. O Iron's Spell Registry e Spell School permanecem autoridade; Asterism não deve criar um segundo cálculo de mana/cooldown/power.

## 8. Riscos de implementação específicos preservados

### Astral Gateway
Registry entry existe, mas a implementação não está pronta para survival. Risco de leakage porque comportamento herdado pode permitir loot/crafting se não for explicitamente bloqueado. Tratar como **não-progression** até validação.

### Celestial Tether
A auditoria de código encontrou risco de divergência entre tooltip/contador e quantidade efetiva de hits cancelados. Criar regression test com múltiplos hits, incluindo hits no mesmo tick/janela.

### Silvery Barbs
A janela baseada em Luck pode cancelar múltiplos hits em intervalo curto. Confirmar se isso é intenção; não “corrigir” silenciosamente via integração externa.

### Starcutter
O source contém clamp de radius da entidade que pode limitar expansão esperada. Testar radius real em runtime.

### Starfire
Ricochet/friendly interaction exige validar aliados/summons e não reintroduzir alvo da mesma classe indevidamente.

### Star Swarm
A auditoria encontrou caminho em que `setDealDamageActive()` escreve estado falso; validar dano real e não mascarar bug com bônus externo.

### Luminous Beam
O collision path auditado não mostrou filtro aliado explícito suficiente para declarar friendly-fire seguro. Testar players, tamed/summoned allies e mobs neutros.

## 9. Client/server, causalidade e multiplayer
- Spell cast, damage, summons, teleport e protection são server-authoritative pelo pipeline Iron's/Asterism.
- Projectiles/entities devem manter owner/caster para damage attribution, ally checks, progression e death events.
- Renderers/particles/model são cliente.
- Summoned Lunar Moth mount precisa limpar owner/rider state em logout/death/dimension change.
- Astromancer scroll generation deve ocorrer apenas no server e não gerar múltiplos scrolls em reload/spawn race.

## 10. Compatibilidade de versão
Source compile: Iron's 3.15.6. Pack: Iron's 3.16.3. Isso não prova incompatibilidade, mas exige smoke/regression nos registries de spell school, spell casting, upgrade orb, equipment e entities. Nenhuma API 3.16.3 foi presumida a partir da compilação antiga.

## 11. Testes obrigatórios
1. Load em dedicated server com Iron's 3.16.3.
2. Os 10 spells survival aparecem somente nos caminhos de aquisição intencionais.
3. Astral Gateway não vaza para loot/crafting/progression.
4. Astromancer gera apenas scroll Astral válido.
5. Celestial Tether/Silvery Barbs: exactly-once damage cancellation por regra esperada.
6. Starfire/Luminous Beam: friendly-fire e caster attribution.
7. Star Swarm/Starcutter: dano/radius conforme código/tooltip.
8. Summoned Lunar Moth: mount lifecycle e unload.
9. Lunar Moth/Dragonfly/Astromancer: spawn/despawn e biome rules.
10. Armor/Staff/Astrolabe/Upgrade Orb: modifiers removidos corretamente ao desequipar.

## 12. Evidência
- Modlist física atual.
- Source pin `f1738c7813a85d31a6da10e6c9f2dbce18d2b583`.
- `ASARSpellRegistry`, `ASAREntityRegistry`, `ASARItemsRegistry`, `ASARModBlocksRegistry` e registries auxiliares auditados.
- Auditoria técnica preexistente preservada e incorporada, incluindo riscos de spells.

> 📚 Inventário source-pinned: 11 spells registrados; 11 entity types; 27 blocks; 15 itens standalone + 27 BlockItems. `Trailblaze` comentado é excluído; Astral Gateway é explicitamente isolado como registrado porém não pronto para survival.
