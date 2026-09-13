# Hazen N Stuff

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81a2a9c2d60549cea57f
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Hazen N Stuff
- **Arquivo JAR:** `hazennstuff-1.4.0.14.jar`
- **Versão 1.21.1:** 1.4.0.14
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia, RPG
- **Função:** Addon de Iron's Spells 'n Spellbooks com spells, armaduras/set bonuses, curios, staves, weapons/tools, blocos, entities/effects e integrações mágicas próprias.
- **Dependências:** Iron's Spells 'n Spellbooks 1.21.1-3.16.3 + HazentouveLib 1.0.9. Source 1.4.0.14 declara NeoForge `[21.1.0,)` e foi desenvolvido contra 21.1.224; pack usa 21.1.248.
- **Sobreposição:** Sobreposição temática com outros addons de Iron's/Ars/Goety/Malum; não unificar schools, mana, spell IDs ou settlement. Hazen N Stuff continua owner apenas de seu conteúdo e set bonuses.
- **Compatibilidade/Riscos:** Riscos: ABI drift Iron's/HazentouveLib, set bonus/modifier duplicado, spell double-settlement, recipes/tags/ore-gen em reload, animation/attribute conflicts e client/server registry mismatch. Compats declaradas com Ars Nouveau, Malum, Obscure Tooltips e Discerning the Eldritch estão fisicamente presentes.
- **Observações:** Baseline público: 9 Pure armor sets, 37 weapons, 2 tools, 10 staves e 24 spells listados. Contagens são coverage documental, não claim de enumeração binária integral dos registries.
- **Procedência:** modlist.txt física atual de 09/09/2026 + source oficial Hazentouvel/Hazen_N_Stuff branch 1.21.1 exatamente em 1.4.0.14 + CurseForge/wiki oficial para inventário público e compats.
- **Fonte:** https://github.com/Hazentouvel/Hazen_N_Stuff/tree/1.21.1
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Hazen N Stuff 1.4.0.14 source-pinned; registries, 9 Pure sets, 37 weapons/2 tools/10 staves, 24 spells públicos, compats, side/lifecycle, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `hazennstuff-1.4.0.14.jar`, mod id `hazennstuff`, versão `1.4.0.14`, Minecraft 1.21.1 / NeoForge. O source oficial `Hazentouvel/Hazen_N_Stuff:1.21.1` declara exatamente `mod_version=1.4.0.14`, portanto esta ficha é source-pinned à build instalada.

## 1. Papel e authority
Hazen N Stuff é addon de **Iron's Spells 'n Spellbooks** focado em spells, equipamentos, armor sets, curios, staves, weapons/tools e blocos. Iron's continua authority do framework de spells, mana, schools e casting-base; Hazen N Stuff é owner apenas do conteúdo que registra e de seus set bonuses/regras próprias. HazentouveLib fornece infraestrutura compartilhada e é catalogada separadamente.

## 2. Dependências e matriz física
O pack contém `irons_spellbooks-1.21.1-3.16.3.jar` e `hazentouvelib-1.0.9.jar`. O source 1.4.0.14 declara Minecraft 1.21.1, NeoForge range `[21.1.0,)` e foi desenvolvido contra 21.1.224; o pack usa NeoForge 21.1.248, dentro do range declarado. O source também referencia GeckoLib, JEI, Player Animator, Curios, AttributeLib, AzureLib, Better Combat/Placebo/Apotheosis em desenvolvimento; presença em build-dev não é promovida automaticamente a hard dependency runtime.

## 3. Superfícies de registry confirmadas no source
A árvore source-pinned expõe registries dedicados para itens, blocos, block entities, spells, entities, effects, sounds, particles, menus, recipes, enchantment effects e creative tabs. Isso confirma que o addon possui conteúdo e lifecycle próprios em várias registries, não apenas datapack de spells.

## 4. Inventário público — armor/equipment
A documentação oficial da versão atual descreve **9 Pure armor sets**, um para cada school-base considerada pelo addon, com set bonus ao usar o conjunto completo. Também publica armor sets cosméticos/dedicados. A documentação registra, como baseline público, **37 weapons + 2 tools** e **10 staves**. Esses números são cobertura documental; não são substituto para enumeração binária de todos os registry IDs.

## 5. Spells publicamente listados
A página oficial lista 24 spells distribuídos nas categorias: Fire — Brimstone Hellblast, Cinderous Step, Fiery Daggers, Scorching Slash; Ice — Ice Arrow, Hailstorm; Nature — Thorn Chakram, Counterspell Spider Lily, Shard Sword, Death Sentence; Lightning — Energy Burst, Ionic Slash; Evocation — Spectral Axe, Parry; Holy — Golden Shower; Eldritch — Soul Seekers; Shadow — Night's Edge Slash, Umbrashift Barrage; Radiance — Syringe Barrage, Shooting Star, Terraprismic Barrage, Call Forth Terraprisma e Prismatic Shift. School membership e números devem seguir runtime/provider; não unificar spells homônimos de outros addons.

## 6. Blocks e world/data surfaces
O projeto adiciona blocos decorativos, ore-related blocks e complementos como um conjunto completo de Wisewood. Recipes, tags, loot/data e geração de ore são superfícies relevantes. Mudanças via datapack/KubeJS devem tratar IDs do addon como provider-owned e validar recipes/tags após `/reload`.

## 7. Set bonuses e atributos
Armor sets e equipamentos concedem modifiers/efeitos mágicos. O provider deve ser a única origem de cada modifier persistente; equip/unequip, relog e death não podem duplicar bônus. Integrações de RPG próprias devem observar os atributos resultantes ou APIs estáveis, não reaplicar os mesmos modifiers.

## 8. Compatibilidades declaradas
O projeto declara compatibilidade com **Iron's Gems 'n Jewelry, Ars Nouveau, Malum, Obscure Tooltips e Discerning the Eldritch**. No pack atual estão fisicamente presentes Ars Nouveau 5.13.1, Malum 1.8.2, Obscure Tooltips 4.2.4 e Discerning the Eldritch 1.4.4. Compatibilidade declarada não significa ausência de balance overlap; smoke tests continuam obrigatórios.

## 9. Client / server
A página de distribuição marca Environment `Server`, mas o addon registra itens/armaduras/spells/assets. Não interpretar essa etiqueta como prova de que um cliente vanilla pode consumir todo o conteúdo registrado. Gameplay, dano, mana/cooldown e rewards devem permanecer server-authoritative; models, animations, particles, sounds e UI são client presentation.

## 10. Lifecycle e multiplayer
Validar registry sync no login, equip/unequip de sets, spell cast/cancel/cooldown, death/respawn, dimension transfer, reload de data, reconnect e restart. Dois jogadores usando o mesmo spell/equipment não devem compartilhar state transitório nem modifier UUID/key. Projectile/summon-like spells devem atribuir owner/caster corretamente.

## 11. Sobreposição com o stack mágico
Há sobreposição temática com outros addons de Iron's, Goety, Ars Nouveau e Malum, mas resources, schools, spell IDs e settlement são provider-specific. Não deduplicar por nome/efeito visual sem comparar o contrato real. HazentouveLib Radiance/Shadow/Cosmic também é infraestrutura do ecossistema Hazen, não uma segunda implementação do spell framework de Iron's.

## 12. Riscos técnicos
- ABI drift com Iron's 3.16.3 ou HazentouveLib 1.0.9;
- modifier/set bonus duplicado após relog/equip swap;
- spell effect processado duas vezes por compat layer;
- recipe/tag/ore-gen drift em datapack reload;
- animation library conflict em armors/staves;
- client/server registry mismatch;
- compat com Ars/Malum/Discerning alterar atributo ou school no mesmo path;
- balance inflation por muitos equipamentos/spells concorrentes;
- update unilateral do addon sem testar dependencies.

## 13. Matriz de testes obrigatória
- [ ] Dedicated server boot com Hazen N Stuff 1.4.0.14 + HazentouveLib 1.0.9 + Iron's 3.16.3.
- [ ] Cliente conecta sem missing registry/model/linkage error.
- [ ] 9 Pure armor sets aplicam/removem bônus exatamente uma vez.
- [ ] Staves/weapons/tools principais mantêm atributos após equip/relog.
- [ ] Cada família de spell usada pelo pack lança/cancela/cooldown sem double settlement.
- [ ] Projectile/area spells atribuem dano ao caster correto em multiplayer.
- [ ] `/reload` preserva recipes/tags sem errors.
- [ ] Ars Nouveau, Malum, Obscure Tooltips e Discerning compat carregam sem conflito.
- [ ] Death/respawn/dimension change não deixam modifiers stale.
- [ ] Atualização de Iron's/HazentouveLib é bloqueada até regression test conjunto.

## 14. Evidências e limites
- **Modlist física:** confirma JAR/version e integrações instaladas.
- **Source oficial:** branch `1.21.1`, `mod_version=1.4.0.14`, Minecraft 1.21.1 e NeoForge range declarado.
- **Documentação oficial:** inventário público de armor sets, weapons/tools, staves, spells e compats.
- **Limite:** números públicos não são declarados como enumeração 1:1 dos registries do JAR; configs/defaults e stats não explicitamente publicados permanecem fail-closed.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
