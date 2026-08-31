# Auditoria Chat 1 — A0061–A0070

## Escopo

- **INÍCIO:** A0061 — Força Aplicada
- **FIM:** A0070 — Dano contra Chefes
- **Lote:** 10/10 perks consecutivas.
- **Base de escrita:** RPG Skill Tree `main@52bd7bd340e21b4020b4465214779f1d6bea072a`.
- **Runtime alterado por este Chat 1:** NÃO.
- **A0071+:** fora do ciclo.
- **Registro operacional:** GitHub; não há novas gravações no Notion por instrução do usuário.

## Fontes e evidência

Aplicados `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`, protocolo Chat 1 e os guias consolidados de Gameplay, Magia, Tecnologia e Projetos Próprios. Para superfícies que avançaram desde o snapshot, foi usada evidência mais nova no GitHub/provider.

Runtime/preparação existente inspecionado: `NotionCombatPerkCatalog`, `CombatPerkTreeModel`, `A0061A0080CombatPolicy`, `A0061A0080EpicFightHooks`, `A0041A0060ProjectileEvents`, `NotionCombatPerkRules`, `MartialTargetClassifier`, `BossRewardKeyResolver` e testes A0061–A0080. Código já presente não foi tratado como design aprovado automaticamente.

## Matriz das perks

| Perk | Design fechado | Disposição técnica |
|---|---|---|
| A0061 Força Aplicada | +2% dano físico direto/rank, máx. +10%; ponto inicial força | código presente; `P-A0061-01` remove classifiers paralelos por tag |
| A0062 Golpe Preciso | +2% chance crítica/rank, máx. +8%, no resolver único | código presente; herda provenance/classificação |
| A0063 Impacto Crítico | +5% dano do crítico/rank, máx. +15%, somente após crítico canônico | código presente nos adapters; nunca segunda rolagem |
| A0064 Ritmo de Combate | +2% cadência/rank, máx. +8%, apenas cadence hook semântico | código presente; não significa draw/reload/projectile speed |
| A0065 Penetração Física | +2 p.p./rank, máx. 8%, Armor/armor-negation canônicos | código presente; provider armor-ignore/sunder não é reexecutado |
| A0066 Impacto Marcial | +3% Impact/rank, máx. +12%, somente com Impact provider-native | melee presente; projectile permanece neutro corretamente |
| A0067 Firmeza Ofensiva | +4 p.p./rank durante janela ofensiva comprovada | **INDISPONÍVEL/NÃO COMPRÁVEL** enquanto `P-A0067-01` estiver aberta |
| A0068 Dano contra Feridos | +4%/rank, máx. +12%, pre-impact HP <35% | código presente; threshold estrito e snapshot pré-hit |
| A0069 Dano contra Íntegros | +4%/rank, máx. +12%, pre-impact HP >85% | código presente; threshold estrito e snapshot pré-hit |
| A0070 Dano contra Chefes | +3%/rank, máx. +15%, `TargetClass.BOSS` | código parcial; `P-A0070-01/-02` fecham cobertura modded |

## Qualidade / regra contra perks “sem sal”

A0061, A0062 e A0064 são permitidas como **Ranked Passives de fundação** porque são três pontos iniciais concorrentes de `martial_core` e escolhem corredores diferentes (força/contexto, crítico, cadência). Não são Notables/Keystones/Capstones e não serão duplicadas em cadeia apenas com números maiores. A0063/A0065/A0066/A0068/A0069/A0070 alteram uma condição ou subpipeline específico. A0067 muda compromisso ofensivo, mas fica indisponível até existir hook real.

## Boundary universal MARTIAL

- Uma ação direta possui um único `rootActionId` MARTIAL.
- Melee: Epic Fight 21.17.3.1 provider-native.
- BOW/CROSSBOW: apenas projectile/launch provenance server-side já canônico.
- Companion-owned damage, summons, DOT, hazards ambientais, magia e `ARCANE_BACKLASH` não herdam os buffs MARTIAL.
- Fan-out/Multishot/ability/delegated hits só compartilham efeitos quando o root/provenance canônico provar a semântica; owner/item isolado nunca cria root novo.

### `P-A0061-01` — classifier melee único

`A0061A0080EpicFightHooks.physicalMelee` ainda referencia `rpgskilltree:hammers`, `rpgskilltree:maces` e `rpgskilltree:scythes`. Isso viola provider-native first e contradiz correções históricas de famílias. Chat 2 deve remover esses fallbacks e usar capability/classification provider-native ou mapping explícito versionado. Categoria desconhecida = fail-closed.

## Simply Swords e ecossistema

Simply Swords 1.70.2, Simply More 1.3.0 ALPHA, Integrated Simply Swords 1.4.0 e Simply Swords: Cataclysm 1.0.2 podem fornecer armas que caem nas famílias MARTIAL existentes quando o adapter real as classifica. Authority continua no provider para Implicits, Runic Powers, Awakening, Uniques, sockets/gem powers e traits.

Regras específicas deste lote:

- A0061/A0068/A0069/A0070 não se repetem em Bleed, execute, ability ou derived damage.
- A0062/A0063 nunca criam crítica/critical-damage pass separado em Katana double damage, Warglaive double strike ou ability hit.
- A0064 não rerrola o attack-speed Implicit de Chakram/Twinblade.
- A0065 não copia/reaplica armor-ignore de Rapier/Spear nem sunder de Hammer/Greathammer.
- A0066 não traduz stun/knockback/Mecha effects em Impact.
- A0067 não usa Deflect/ability visual como receipt de firmeza.
- Simply Tooltips é apresentação e NÃO DEVE SER INTEGRADO mecanicamente.
- `P-SIMPLY-A0001-50-01` continua o acceptance provider-present canônico do stack; esta auditoria não o declara encerrado.

## Delta obrigatório dos projetos próprios

### RPG Skill Tree

- Baseline consolidado: `f448aa0b4f9df400011873e9ad26771209876ad4`.
- Fresh main usado para design: `52bd7bd340e21b4020b4465214779f1d6bea072a`.
- Houve avanço amplo. Capacidades pertinentes classificadas:
  - runtime/policy A0061–A0080 já existente: **COBERTA POR PERK EXISTENTE**, porém submetida a esta auditoria antes de ser canônica no design;
  - Class Resolution/query runtime: **COBERTO POR SISTEMA UNIVERSAL**, authority de resolução; não cria perk nova;
  - Weapon Mastery milestones: **NÃO DEVE SER INTEGRADO** a A0061–A0070, pois este lote não possui gate de Mastery;
  - Itemization identity/query/modifiers Stage 11: **PROGRESSÃO NATIVA AUTORITATIVA / NÃO DEVE SER INTEGRADO** neste lote; nenhum rolled modifier é lido para fabricar dano/crítico/cadência/penetração;
  - Compendium/narrativa: **NÃO DEVE SER INTEGRADO** a settlement MARTIAL.

### Volcanoes

- Baseline: `602e0188c123ac8531d3413a5630daa22e3d761f`.
- Fresh main: `a47bb868de9b4846d8ae9afb94374f9672ab381e`.
- Delta fecha RNS ownership, full-pack acceptance, performance e world-upgrade/hardening; não cria ação MARTIAL jogável nova para A0061–A0070.
- Classificação: **NÃO DEVE SER INTEGRADO** ao dano MARTIAL. Lava, pyroclastic, pressão, gases e calor continuam hazards/provider-owned; jamais recebem autoria direta do jogador por associação ambiental.

### Enshrouded

- Baseline: `77552a3d7f089a47908c109f5f8c19aff8a0f97d`.
- Fresh main: `ffc5007cc66c74cd6ae8087293955b865dc79e90`.
- Delta concluiu Lich & Story e adicionou boss físico nativo `enshrouded:shroud_lich` (`NativeShroudLichEntity`).
- Disposição: **COBERTA POR PERK EXISTENTE A0070**, exigindo integração de identidade explícita (`P-A0070-01`). Shroud/Exposure/Flame/Story permanecem provider-owned e não viram dano MARTIAL.

### Black Arcana

- Baseline: `07263ae9bad12eba6ed500992991faa36ad598b2`.
- Fresh main: `b2bf5e9507ab3e7510f1c69a91d0cc97ff576c42`.
- Delta é hardening/proveniência e não abre nova superfície MARTIAL pertinente. Arcane Danger/Resistance/Strain/Backlash permanecem authority Black Arcana.
- Disposição: **NÃO DEVE SER INTEGRADO** ao settlement A0061–A0070; `ARCANE_BACKLASH` é terminal e não recebe crítico, penetração, Impact ou bonus anti-boss MARTIAL.

Nenhum baseline acima deve ser reinterpretado como autorização de capability ainda planejada.

## A0070 — cobertura de bosses

`MartialTargetClassifier` é explicitamente não-heurístico, o que está correto. A tag atual cobre Wither/Ender Dragon e bosses Cataclysm; marcadores Apothic cobrem a rota correspondente.

### `P-A0070-01` — Enshrouded

Adicionar entrada opcional e testada para `enshrouded:shroud_lich`, ID comprovado no provider `ModEntities`. Ausência do mod não pode quebrar datapack/runtime.

### `P-A0070-02` — inventário boss da modlist

Gameplay documenta bosses/minibosses em Born in Chaos, Legendary Monsters, Mowzie's Mobs, Ice And Fire, Companions e Mobstein. Cada inclusão em A0070 exige registry ID/tag público verificado da versão instalada. Não classificar por nome, HP, tamanho, bossbar ou aparência. Witherstein permanece fail-closed até identidade exata ser comprovada.

## A0067 — availability

### `P-A0067-01`

O policy possui coeficiente (+4 p.p./rank), mas o adapter declara ausência de safe offensive stun-armor window. Enquanto isso, o node deve estar explicitamente **indisponível/não comprável**, preservando o invariant global: não gastar ponto em rank no-op. Se um binding futuro existir, deve ser versionado, server-authoritative, lifecycle-safe e limitado à janela ofensiva.

## Nove eixos — lote

| Eixo | Estado |
|---|---|
| Dependências/gates | PASS; A0067 adiciona availability gate operacional |
| Integração global | PASS; pipelines físico/mágico/hazard separados |
| Qualidade/identidade | PASS com justificativa de fundações e especializações |
| Topologia | PASS em `martial_core`; três starting points concorrentes |
| Especializações | PASS; universal MARTIAL, sem transformar mods em classes |
| PT-BR | PASS nos dossiês; player text runtime deve ser adicionado pelo Chat 2 |
| Registro | PASS no GitHub; Notion não é escrito neste ciclo por instrução do usuário |
| NeoVitae | PASS — ausente |
| Cobertura providers/modlist | PASS no design; runtime blockers catalogados abaixo |

## Handoff exato para Chat 2

1. `P-A0061-01` — remover tags paralelas como classifier melee; provider-native/mapping versionado only.
2. `P-A0067-01` — safe offensive interruption/stun-armor receipt **ou** availability=false sem gasto/rank fantasma.
3. `P-A0070-01` — classificar `enshrouded:shroud_lich` explicitamente como boss e testar optional-provider absence.
4. `P-A0070-02` — inventário/versionamento de boss identities para os demais providers pertinentes; desconhecidos fail-closed.
5. Manter `P-SIMPLY-A0001-50-01` para provar no artifact real que Implicit/ability/gem/trait derived damage não gera novo root MARTIAL.
6. Adicionar player-facing PT-BR A0061–A0070 ao catálogo de apresentação sem transformar texto em authority de gameplay.

## Resultado

**A0061–A0070: LOTE FECHADO NO DESIGN.** A0067 está aprovada como design fail-closed mas indisponível operacionalmente até binding seguro; A0070 está aprovada com taxonomia explícita e gaps provider-present catalogados. Nenhum runtime foi alterado por este Chat 1.