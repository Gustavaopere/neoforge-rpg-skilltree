[← Índice do guia](README.md)

# 15. Simply Swords e ecossistema — contratos para integração em perks

> **Objetivo deste capítulo:** documentar mecânicas, authority, hooks e riscos do stack Simply Swords instalado para que o Chat 1 possa decidir cobertura de perks sem inventar comportamento do provider. Este capítulo **não cria perks** e não substitui a auditoria da versão/JAR atual em [`CURRENT-MODLIST.md`](CURRENT-MODLIST.md).

## 15.1 Estado instalado e ownership

| Componente | JAR instalado | Papel / authority |
|---|---|---|
| Simply Swords | `simplyswords-neoforge-1.70.2-1.21.1.jar` | Provider principal de famílias de armas, implicits, Runic Powers, Unique Weapons, Awakening, Runic Forge e sockets/gem powers. |
| Simply More | `simplymore-forge-1.3.0_alpha.jar` | Addon de tipos de arma e Uniques adicionais; a linha `1.3.0 ALPHA` também migra seus weapon types para implicits e inicia compatibilidade com Iron's Spells. |
| Integrated Simply Swords | `integrated_simply_swords-1.4.0+1.21.1-neoforge.jar` | Bridge de materiais: preenche famílias de armas Simply Swords para materiais fornecidos por outros mods. |
| Simply Swords: Cataclysm | `simplycataclysm-1.0.2+1.21.1+neoforge.jar` | Bridge L_Ender's Cataclysm ↔ Simply Swords, com famílias por material e traits próprios em materiais específicos. |
| Simply Tooltips | `SimplyTooltips-neoforge-0.1.5.jar` | Camada client-side de apresentação data-driven. **Não é provider mecânico de perk.** |
| Epic Fight - Mod Compat | `epicfightcompat-1.1.0-mc1.21.1-neoforge.jar` | Adapter de presets/capabilities de combate Epic Fight para armas compatíveis. **Não é authority dos implicits, Awakening, Runic Powers ou habilidades dos Uniques.** |

A release instalada de Simply Swords `1.70.2` corrige explicitamente um crash com Epic Fight. Isso prova compatibilidade corrigida da linha instalada, mas não significa que toda arma exótica possua moveset bespoke.

## 15.2 Simply Swords — famílias de armas e progressão

Simply Swords não é apenas um pacote de modelos. A documentação atual da linha 1.21 descreve **15 famílias padrão**, além de Hammer e Dagger usados por determinados Uniques. Cada família possui tradeoff de dano/alcance/velocidade e um **Implicit** próprio.

Fluxo nativo principal:

1. armas padrão usam progressão de materiais, incluindo Iron/Gold/Diamond e upgrade para Netherite;
2. uma arma Netherite pode ser convertida em **Runic weapon** usando Runic Tablet como template de smithing e Diamond como addition;
3. a Runic weapon mantém seu tipo/Implicit e recebe um **Runic Power**;
4. **Unique Weapons** possuem efeitos próprios e, na linha atual, participam de Awakening e sockets quando aplicável;
5. Runic Forge e Runic Tablets são a progressão autoritativa de Awakening; a skill tree não deve duplicar essa progressão.

### Famílias e Implicits nativos

Os valores abaixo são **defaults do provider**. Config do modpack/servidor pode alterá-los; portanto o Chat 1 deve tratar o valor efetivo do stack/config como autoridade quando desenhar escalonamento.

| Família | Implicit nativo | Faixa default | Observação para auditoria de perk |
|---|---|---:|---|
| Rapier, Spear | chance de ignorar armor | 15–35% | Alto risco de double-dip com armor negation/penetration do Epic Fight ou outras perks. |
| Cutlass | chance de loot extra on-hit | 1–3% | Capacidade econômica; não tratar como simples dano. Exige auditoria anti-duplicação de drops. |
| Glaive, Greataxe, Halberd | chance de aplicar Bleed | 10–60% | O proc/status pertence ao Simply Swords; não reimplementar Bleed externamente. |
| Sai, Dagger | aumento de dano por backstab | 20–40% | Depende de causalidade posicional real do provider. |
| Claymore, Longsword | chance de defletir dano recebido | 5–15% | É defesa/IncomingDamageHandler, não proc ofensivo comum. |
| Greathammer | armor sunder por hit | 2–10% | Alteração de armor por acúmulo/hit; precisa de anti-stack e ownership claros. |
| Hammer | armor sunder por hit | 2–6% | Mesma família mecânica, faixa menor. |
| Katana | chance de dano duplo | 5–15% | Multiplicativo de alto risco; não somar outro “double damage” sem regra explícita. |
| Chakram, Twinblade | chance de ganhar attack speed on-hit | 5–25% | Interage diretamente com velocidade/cadência e Epic Fight. |
| Scythe | chance de executar inimigo com vida baixa | 5–15% | Execute deve permanecer provider-native; nunca substituir por dano genérico. |
| Warglaive | chance de atacar duas vezes | 5–15% | Alto risco de duplicar on-hit, enchant, gem power e outros procs. |

Os tipos built-in expostos pela API são `rapier`, `cutlass`, `sai`, `dagger`, `claymore`, `longsword`, `greathammer`, `hammer`, `katana`, `spear`, `glaive`, `halberd`, `warglaive`, `chakram`, `scythe`, `greataxe` e `twinblade`.

### Implicit como contrato público

A linha atual fornece API pública, portanto o RPG Skill Tree **não precisa adivinhar tipo de arma pelo nome** quando houver integração de código adequada:

- `SimplySwordsAPI.registerWeaponType(item, type)`;
- `SimplySwordsAPI.registerWeaponType(tag, type)`;
- `SimplySwordsAPI.getOrCreateWeaponImplicit(stack)`;
- `SimplySwordsAPI.appendWeaponImplicitTooltip(...)`;
- `SimplySwordsAPI.applyWeaponImplicitDamage(...)`;
- `SimplySwordsAPI.applyWeaponImplicitOnHit(...)`;
- `SimplySwordsAPI.registerWeaponImplicit(definition)` para addons que realmente definam um novo tipo.

`WeaponImplicitDefinition` possui handlers distintos para:

- `DamageHandler` — modifica dano de saída;
- `HitHandler` — executa depois de hit confirmado;
- `IncomingDamageHandler` — pode interferir/cancelar dano recebido enquanto a arma está segurada;
- `TooltipFormatter` — apresentação do roll.

O roll do Implicit fica persistido no stack. Armas comuns usam a faixa inteira; subclasses de `UniqueWeaponItem` rolam no **top 10%** da faixa. Socket/Awakening/save-reload não devem rerrolar esse valor.

**Consequência para o Chat 1:** Implicit é uma capacidade observável e estável do provider. Uma perk pode ser avaliada em torno de um tipo/roll real somente se respeitar o roll já existente; não deve criar um segundo sistema paralelo de “implicit da skill tree”.

## 15.3 Runic Powers e Runic weapons

Runic Powers são habilidades de arma do Simply Swords e podem ser passivas, acionadas por trigger ou ativas. A progressão nativa parte de uma arma Netherite e de um Runic Tablet; o resultado preserva o tipo/Implicit e adiciona a power.

Pontos importantes para design:

- a power pertence ao **stack da arma**, não ao jogador abstratamente;
- alguns rolls podem ser **Greater**, isto é, uma forma superior definida pelo próprio provider;
- Runic weapons ficam abaixo dos Uniques na progressão geral, mas sua Runic Power funciona em força integral e não usa a curva de Awakening dos Unique Weapons;
- o provider possui loot/pity próprios para Runic Tablets e permite reroll conforme suas regras;
- uma perk não deve conceder “Greater”, rerrolar power, ignorar custo de tablet ou substituir pity sem um contrato deliberado e hook real.

Para integração, o princípio é **provider-native first**: observar power/estado já existente ou modificar apenas parâmetros cuja API real permita. Não simular a mesma habilidade em eventos genéricos do RPG Skill Tree.

## 15.4 Unique Weapons e Awakening

A linha atual possui progressão **Awakening de 8 níveis**. `UniqueWeaponItem`, incluindo `UniqueSwordItem`, recebe automaticamente `AwakeningProfile.DEFAULT`.

No perfil default documentado:

- nível 0 começa com multiplicador de attack damage `0.50`;
- nível 0 começa com multiplicador de attack speed `0.75`;
- a habilidade é desbloqueada no nível `4`;
- os atributos interpolam até a força integral no nível `8`.

A API pública relevante inclui:

- `AwakeningApi.isAwakeningSystemEnabled()`;
- `AwakeningApi.usesAwakeningProgression(stack)`;
- `AwakeningApi.getLevel(stack)`;
- `AwakeningApi.isAbilityUnlocked(stack)`;
- `AwakeningApi.getAbilityUnlockLevel(stack)`;
- `AwakeningApi.getEffectMultiplier(stack)`;
- `AwakeningApi.getAttributeMultiplier(stack)`;
- `AwakeningApi.getAttackSpeedMultiplier(stack)`;
- `AwakeningApi.scaleEffect(...)` / `scaleChance(...)`;
- `AwakeningApi.areGemPowersActive(stack)` e helpers próprios de gem scaling.

`SimplySwordsAPI.scaleAbilityDamage(...)` e `scaleAbilityValue(...)` já aplicam a lógica de scaling apropriada. **Não aplicar `scaleEffect` de novo no resultado**, pois isso duplica o escalonamento de Awakening.

Aquisição também possui semântica própria:

- `AwakeningApi.initializeNaturalDrop(stack)` cria reward natural em nível 0;
- `AwakeningApi.initializeFullyAwakened(stack)` é uma concessão intencional de nível 8;
- pity loot e transformações por Contained Remnant já inicializam drops naturais corretamente.

Quando o sistema global de Awakening está desativado, Uniques comuns se comportam como nível 8, preservando o nível armazenado; por isso o Chat 1 deve consultar `usesAwakeningProgression` quando precisar saber se o progresso persistido está realmente ativo.

**Boundary obrigatório:** Awakening é progressão natural autoritativa do Simply Swords. Uma perk pode ser desenhada para interagir com esse estado somente se houver razão sistêmica; ela não deve “dar níveis de Awakening grátis”, contornar Runic Forge/Tablet ou desbloquear habilidade antes da progressão nativa sem uma decisão explícita extraordinária.

## 15.5 Sockets, Runefused/Netherfused gems e powers

Unique Weapons nativos possuem dois tipos de socket quando o sistema está habilitado:

- **Runefused**;
- **Netherfused**.

Na documentação de desenvolvimento atual, `UniqueWeaponItem.inventoryTick` cria os dois sockets nativos e é responsável por:

- tick de gem powers equipadas em main/off hand;
- socketing e replacement por inventory click;
- devolução da gem deslocada;
- post-hit das gem powers;
- tooltip dos sockets.

A API também permite sockets em itens externos configurados por item ID ou `#tag` por meio de `AdditionalGemSocketApi`.

Hooks públicos para bases customizadas:

- `SimplySwordsAPI.inventoryTickGemSocketLogic(...)`;
- `SimplySwordsAPI.onClickedGemSocketLogic(...)`;
- `SimplySwordsAPI.postHitGemSocketLogic(...)`;
- `SimplySwordsAPI.appendTooltipGemSocketLogic(...)`;
- `SimplySwordsAPI.onWeaponSwing(...)` para caminhos customizados de swing.

As powers públicas derivam de classes como:

- `RunicGemPower`;
- `RunefusedGemPower`;
- `NetherGemPower`;
- `GemPower`.

`GemPowerRegistry` usa IDs namespaced sincronizados entre servidor/cliente. Desde `1.70.0`, `GemPowerComponent` persiste **IDs**, não registry-entry identity. Isso foi feito justamente para evitar incompatibilidades/fuzzy replacement em stacks reconstruídos por storage mods como AE2/Create/Refined Storage.

Para dano/valor de gem powers, helpers como `gemPowerScaledDamage`/`gemPowerScaledValue` já escolhem o resultado físico/fixo versus spell scaling e aplicam Awakening de gem **uma vez**. Não escalar novamente no RPG Skill Tree.

## 15.6 Habilidades de Unique — como tratar no design

Uniques podem possuir passivas e/ou habilidades ativas próprias. O sistema público inclui `UniqueWeaponActiveAbility`, contextos de habilidade e cooldown autoritativo. `SimplySwordsAPI.tryActivateWeaponAbility(...)` verifica, entre outras condições, unlock de Awakening e cooldown antes de ativar.

Também existem helpers de hit/ability que preservam regras importantes:

- `applyDelegatedWeaponHit(...)` / `applyEntityWeaponHit(...)` executam pipeline de hit de arma com contexto, Implicit e post-hit controlados;
- `applyAbilityBoltDamage(...)` é server-side, valida target, aplica enchantments pertinentes, bypassa iframes para o bolt e **suprime Implicits** nesse dano para evitar proc indevido;
- helpers de target aplicam regras de alvo/friendly-fire próprias.

Isso significa que “uma habilidade causa outro hit” **não autoriza** a perk a disparar todos os on-hit novamente. O provider já possui caminhos explícitos para deduplicar/suprimir efeitos.

Exemplo representativo: **Tempest** é Chakram, recebe Implicit de attack-speed-on-hit e possui a habilidade Vortex, com efeitos elementais que se acumulam no alvo e uma ativação que recolhe esses elementos para formar um vortex ofensivo. O importante para o guia não é transformar Tempest em uma perk nominal, mas demonstrar que um Unique pode combinar:

- família/Implicit;
- passiva/stack próprio;
- active ability;
- Awakening;
- sockets/gem powers;
- scaling físico ou mágico.

Logo, qualquer perk genérica de armas deve ser auditada contra **todas essas camadas** para não contar o mesmo evento duas ou três vezes.

## 15.7 Simply More — escopo instalado e cautela de alpha

Simply More adiciona **10 weapon types** ao ecossistema:

1. Great Katanas;
2. Grandswords — podem desabilitar shields quando o oponente tenta bloquear;
3. Backhand Blades;
4. Lances — possuem forte bônus de dano/força quando usadas montado;
5. Khopeshs;
6. Daggers;
7. Pernachs;
8. Quarterstaffs;
9. Great Spears;
10. Deer Horns.

O projeto também declara **33 Unique Weapons**.

A linha instalada é `1.3.0 ALPHA`. O changelog da primeira build dessa linha registra mudanças especialmente relevantes para perks:

- weapon types do Simply More passam a ter **implicit abilities**, acompanhando o sistema introduzido/expandido pelo Simply Swords 1.70;
- o bônus da Lance passa a ser um **Implicit**, em vez de status effect;
- começa a implementação de compatibilidade com **Iron's Spells and Spellbooks**, inicialmente apenas para Uniques retrabalhados;
- vários efeitos on-hit passam a ocorrer **uma vez por swing**, não uma vez por entidade atingida;
- alguns efeitos deixam de ser status effects comuns e passam a estado próprio que não pode ser limpo com milk;
- Uniques marcados para rework tiveram funcionalidade removida e **podem atualmente não fazer nada**;
- o próprio autor alerta para crashes com Uniques antigos e quebra/reset de configs.

### Ambiguidade de artifact da linha alpha

O filename instalado `simplymore-forge-1.3.0_alpha.jar` não é suficiente, sozinho, para distinguir algumas revisões publicadas dessa série: páginas posteriores de Alpha 3/Alpha 5 também expõem o mesmo filename interno em certos uploads. Portanto:

- o guia considera como contrato seguro apenas o comportamento comum documentado para `1.3.0 ALPHA` e aquilo que puder ser confirmado no runtime/metadata do pack;
- recursos exclusivos de Alpha 2/3/4/5 **não devem ser presumidos** sem File ID/hash/runtime adicional;
- se uma perk depender de uma Unique específica do Simply More, o Chat 1 deve validar a classe/efeito no artifact instalado; se não puder provar, classificar **SEM HOOK SEGURO / FAIL-CLOSED**.

**Boundary:** Simply More é provider mecânico quando define seu próprio weapon type/Implicit/Unique. Não é apenas “conteúdo visual”. Entretanto, o estado alpha exige integração conservadora.

## 15.8 Integrated Simply Swords — bridge de materiais, não novo sistema de perks

Integrated Simply Swords `1.4.0+1.21.1-neoforge` é um addon de compatibilidade que **preenche lacunas de famílias de armas Simply Swords para materiais adicionados por outros mods**. A versão 1.4.0 é o port para 1.21.1 e incorporou temporariamente o escopo do antigo Knaves Needs.

Sua autoridade é principalmente:

- material/tier do mod de origem;
- família/tipo de arma e Implicit do Simply Swords;
- receita/integração fornecida pelo addon.

Portanto, “arma Integrated Simply Swords” não deve automaticamente criar uma perk própria. Em geral, ela deve cair sob cobertura universal por weapon type/material **a menos que** o addon prove uma mecânica própria adicional.

O Chat 1 deve evitar usar o namespace do addon como substituto da semântica real. Para uma arma integrada, perguntar:

1. qual mod é dono do material/tier?;
2. qual `weaponType` Simply Swords a arma recebe?;
3. existe efeito próprio além do Implicit padrão?;
4. Epic Fight está apenas atribuindo preset de animação ou há bridge mecânica adicional?

Sem efeito adicional provado, a classificação correta tende a **COBERTO POR SISTEMA UNIVERSAL**, não “perk de Integrated Simply Swords”.

## 15.9 Simply Swords: Cataclysm — material families e traits próprios

`simplycataclysm-1.0.2+1.21.1+neoforge.jar` conecta materiais de L_Ender's Cataclysm às famílias de armas Simply Swords.

O source atual expõe famílias para **Ancient Metal, Black Steel, Cursium, Ignitium e Witherite** e assets/recipes de quinze arquétipos Simply Swords: Chakram, Claymore, Cutlass, Glaive, Greataxe, Greathammer, Halberd, Katana, Longsword, Rapier, Sai, Scythe, Spear, Twinblade e Warglaive.

O addon também possui `weapon_attributes` data-driven por família/material. Portanto, dano/speed devem ser lidos do provider/config real e não hardcoded em perk.

### Cursium — Accursed Rage

`CursiumSwordItem` implementa callback de dano melee. A cada ciclo relevante, o sistema pode acumular **Accursed Rage** no atacante até um máximo configurável. Enquanto o efeito está presente, o dano recebe um adicional proporcional ao amplifier/stacks. Chance, duração, máximo e dano extra são configuráveis.

**Authority:** Simply Swords: Cataclysm / `CursiumSwordItem` + config do addon.

**Risco:** uma perk que acrescente stacks manualmente ou replique o dano adicional pode dobrar a curva nativa.

### Ignitium — Blazing Brand + lifesteal

`IgnitiumSwordItem` possui chance configurável de aplicar/acumular **Blazing Brand** no alvo. O stack pode alimentar lifesteal do atacante; o cálculo também normaliza o fator considerando attack speed do player dentro de limites internos.

**Authority:** Simply Swords: Cataclysm / `IgnitiumSwordItem`.

**Risco:** bônus externo de lifesteal ou attack speed pode alterar a economia pretendida. Não remover a normalização ou transformar cada hit derivado em novo heal.

### Witherite — Mecha Pulse e Mecha Smite

`WitheriteSwordItem` implementa duas famílias de efeito:

- **Mecha Pulse:** acumula charge no atacante quando não está em cooldown; ao atingir threshold, limpa a charge, aplica cooldown, stuna o alvo e acrescenta dano configurável;
- **Mecha Smite:** pode aplicar Wither/fire no alvo e pode conceder Regeneration ao atacante sob condição de vida, conforme chances/thresholds/config.

**Authority:** Simply Swords: Cataclysm / `WitheriteSwordItem` e seus effects/configs.

**Risco:** qualquer perk de “mais stun”, “menos cooldown”, “mais charge”, “mais regen” ou proc extra precisa usar estado real do addon; nunca criar contador paralelo.

### Ancient Metal e Black Steel

As famílias existem e possuem tier/weapon attributes próprios, mas este guia **não atribui trait especial** a elas sem evidência específica equivalente à encontrada para Cursium/Ignitium/Witherite. Se o Chat 1 quiser uma integração nominal baseada nesses materiais, deve reabrir source/artifact/config e provar a mecânica; do contrário, tratar como cobertura universal de material + weapon type.

### 1.0.2

A release `1.0.2` registra correção para que armas de **Ignitium, Cursium e Witherite** sejam corretamente unbreakable. Durabilidade/inquebrável, portanto, é propriedade do addon/material; uma perk não deve “restaurar” ou recriar essa regra.

## 15.10 Simply Tooltips — boundary estritamente de apresentação

Simply Tooltips é client-side e data-driven. Ele renderiza tooltips modernos para itens com temas válidos e permite expansão por config/resource pack.

Simply Swords oferece integração de addon por tag:

`data/simplytooltips/tags/item/simply_swords_compat.json`

Essa integração pode apresentar ability sections, action labels, linhas de Implicit, atributos e hints. Um addon também pode construir provider de tooltip que mostre progresso de Awakening.

**Classificação obrigatória:** `NÃO DEVE SER INTEGRADO` como provider de perk. Tooltip pode explicar estado, mas não é authority do estado. O RPG Skill Tree deve ler API/componente real de Simply Swords, não parsear texto do tooltip.

## 15.11 Epic Fight × Simply Swords / Simply More

O pack possui `Epic Fight - Mod Compat 1.1.0`, cuja lista de suporte inclui explicitamente **Simply Swords** e **Simply More** no escopo de armas. A bridge atribui presets Epic Fight quando uma arma modded encaixa de forma limpa em um arquétipo; quando não existe mapeamento limpo, não força um comportamento exótico inventado.

Separação de authority:

| Questão | Authority |
|---|---|
| Moveset/preset/animação/Battle Mode | Epic Fight + Epic Fight Compat |
| Família Simply Swords / Implicit | Simply Swords ou addon que registra o tipo |
| Runic Power | Simply Swords |
| Unique active/passive | Simply Swords / addon dono da Unique |
| Awakening e Runic Forge | Simply Swords |
| Socket/gem power | Simply Swords |
| Trait de material Cataclysm | Simply Swords: Cataclysm |
| Tooltips | Simply Tooltips / camada client-side, sem authority mecânica |

A release Simply Swords 1.70.2 instalada corrige um crash com Epic Fight, mas isso não altera a regra acima. Uma perk nunca deve inferir que “Epic Fight processou a arma” significa que Epic Fight é dono do Implicit ou da habilidade do item.

## 15.12 Matriz provider → árvore para o Chat 1

Esta matriz não decide a perk; ela classifica o que **precisa ser considerado** na auditoria provider → árvore.

| Capacidade detectada | Provider/authority | Hook/estado real | Disposição inicial para auditoria |
|---|---|---|---|
| Weapon type Simply Swords | Simply Swords | `registerWeaponType`, registry/tags de implicit | **COBERTO POR SISTEMA UNIVERSAL** ou especialização de armas; não precisa perk nominal por mod. |
| Rolled Implicit | Simply Swords / addon | `getOrCreateWeaponImplicit`, componente persistido | **CANDIDATO A INTEGRAÇÃO**, preservando roll nativo. |
| Armor ignore | Simply Swords | DamageHandler do Implicit | **CANDIDATO**, com auditoria anti-double-dip com armor negation. |
| Bleed | Simply Swords | HitHandler/estado provider | **CANDIDATO**, sem recriar status. |
| Backstab | Simply Swords | Implicit de tipo | **CANDIDATO**, mantendo causalidade posicional. |
| Deflect | Simply Swords | IncomingDamageHandler | **CANDIDATO**, domínio defensivo/arma; não converter em dodge genérico. |
| Armor sunder | Simply Swords | Implicit de Hammer/Greathammer | **CANDIDATO**, com limite/stack do provider. |
| Double damage / double strike / execute | Simply Swords | Implicit provider | **ALTO RISCO**; só integrar com deduplicação explícita. |
| Attack-speed proc | Simply Swords | Implicit provider | **CANDIDATO**, cruzar Epic Fight/stamina/cadência. |
| Extra loot | Simply Swords | Cutlass Implicit | **ALTO RISCO ECONÔMICO**; exigir anti-dup e evento autoritativo. |
| Runic Power | Simply Swords | GemPower/Runic stack state | **PROGRESSÃO NATIVA AUTORITATIVA**; integração mínima por padrão. |
| Greater Runic Power | Simply Swords | roll/provider state | **PROGRESSÃO NATIVA AUTORITATIVA**; não conceder/rerrolar gratuitamente. |
| Awakening level 0–8 | Simply Swords | `AwakeningApi` | **PROGRESSÃO NATIVA AUTORITATIVA**. |
| Unlock de ability | Simply Swords | `AwakeningApi.isAbilityUnlocked` | **PROGRESSÃO NATIVA AUTORITATIVA**. |
| Unique passive/active | arma Unique / addon | item class + ability API | **CANDIDATO ESPECÍFICO** somente quando uma perk tiver razão temática e hook provado. |
| Runefused/Netherfused sockets | Simply Swords | `GemPowerComponent`, socket API | **PROGRESSÃO NATIVA AUTORITATIVA** / integração mínima. |
| Gem power | Simply Swords / addon power | `GemPowerRegistry` + component IDs | **CANDIDATO**, jamais duplicar scaling de Awakening. |
| Simply More weapon types | Simply More | Implicits/addon state | **COBERTO POR SISTEMA UNIVERSAL** por padrão; perk nominal só com mecânica própria justificada. |
| Simply More Uniques | Simply More | item/effect específico | **SEM HOOK SEGURO** para Uniques não auditados do alpha; validar individualmente. |
| Material bridge do Integrated Simply Swords | material source + Simply Swords | item/tier/type | **COBERTO POR SISTEMA UNIVERSAL** por padrão. |
| Cursium Accursed Rage | Simply Swords: Cataclysm | Cursium item/effect/config | **CANDIDATO A BRIDGE/PERK**, se árvore pertinente justificar. |
| Ignitium Blazing Brand/lifesteal | Simply Swords: Cataclysm | Ignitium item/effect/config | **CANDIDATO A BRIDGE/PERK**, com anti-double-heal. |
| Witherite Pulse/Smite | Simply Swords: Cataclysm | Witherite item/effects/config | **CANDIDATO A BRIDGE/PERK**, preservando charge/cooldown/threshold. |
| Modern tooltip | Simply Tooltips | client render/data | **NÃO DEVE SER INTEGRADO** como perk. |
| Epic Fight preset | Epic Fight Compat | capability/preset mapping | **BRIDGE DE COMBATE**, não provider dos procs de Simply Swords. |

## 15.13 Contrato mínimo para qualquer perk que tocar este stack

Antes de o Chat 1 aprovar uma integração, registrar explicitamente:

1. **Provider/authority:** Simply Swords, Simply More, Simply Cataclysm, material source ou Epic Fight — sem fundir owners diferentes.
2. **Boundary/API/query/hook:** componente, API pública, item class, status/effect ou evento real da versão instalada.
3. **Causalidade:** hit normal, outgoing damage, incoming damage, post-hit, swing, active ability, tick de power, nível de Awakening etc.
4. **Deduplicação:** como impedir que extra hits, AoE, chain, double strike, Epic Fight ou ability damage reapliquem Implicit/gem/enchant indevidamente.
5. **Provider-native first:** manter Runic Forge, Runic Tablet, pity, cooldown, Awakening, sockets e rolls sob controle nativo.
6. **Fallback:** somente degradação segura. Se a API/classe esperada não existir, omitir a integração daquele provider.
7. **Fail-closed:** nunca substituir ausência de hook por `+dano`, `+velocidade`, `+loot`, `+lifesteal` ou outro bônus genérico.
8. **Config-awareness:** valores default deste capítulo não autorizam hardcode; ler config/runtime quando o efeito depender do número real.
9. **Anti-double-count:** Epic Fight animation hit, Simply Swords Implicit, Unique ability, gem power e addon trait podem coexistir no mesmo ataque.
10. **Teste dedicado:** validar servidor dedicado quando qualquer integração usar API/common code; não importar classes client-only de tooltip.

## 15.14 Riscos concretos para design/implementação

- **Dano multiplicativo:** Katana double damage, Warglaive double strike e execução da Scythe podem explodir escalonamento quando combinados com perks percentuais.
- **Proc fan-out:** AoE/chain/extra hit pode transformar “uma vez por swing” em “uma vez por entidade” se o RPG Skill Tree observar o evento errado. Simply More 1.3.0 corrigiu explicitamente vários casos nessa direção.
- **Iframe bypass:** helpers de ability podem bypassar iframes intencionalmente e suprimir Implicit. Reproduzir o dano fora desse pipeline muda gameplay.
- **Awakening duplicado:** `scaleAbilityDamage`, `scaleAbilityValue`, `gemPowerScaledDamage` e `gemPowerScaledValue` já incorporam curvas pertinentes; uma segunda multiplicação é bug.
- **Cooldown ownership:** active ability possui cooldown do item/provider; uma perk não deve resetar ou encurtar genericamente sem contrato explícito.
- **Sockets:** não conceder segunda camada de sockets da skill tree; utilizar o sistema nativo e seus IDs/componentes.
- **Storage compatibility:** não comparar identity de registry holder de gem power; a linha 1.70 migrou para IDs justamente para persistência estável.
- **Alpha do Simply More:** Uniques podem estar sem funcionalidade ou sujeitos a crash. Nenhum efeito individual deve ser assumido sem validação do artifact instalado.
- **Material ≠ perk:** Integrated Simply Swords e boa parte de Simply Cataclysm expandem cobertura de materiais. Material novo não implica automaticamente node novo.
- **Tooltip ≠ estado:** nunca parsear tooltip para descobrir Implicit/Awakening/gem; usar API/componente real.
- **Epic Fight ≠ owner da arma:** preset/moveset não transfere ownership de Implicit, Unique effect ou gem power.

## 15.15 Evidência técnica consultada

Fontes upstream usadas para esta especificação:

- Simply Swords — documentação principal: https://github.com/Sweenus/SimplySwords/blob/Architectury-1.21/docs/main.mdx
- Weapon Types & Progression: https://github.com/Sweenus/SimplySwords/blob/Architectury-1.21/docs/weapon-types/weapon_types.mdx
- Weapon Implicits — player docs: https://github.com/Sweenus/SimplySwords/blob/Architectury-1.21/docs/weapon-types/weapon_implicits.mdx
- Weapon Implicits — developer API: https://github.com/Sweenus/SimplySwords/blob/Architectury-1.21/developer-docs/api/weapon-implicits.md
- Awakening API: https://github.com/Sweenus/SimplySwords/blob/Architectury-1.21/developer-docs/api/awakening.md
- Gem sockets/powers API: https://github.com/Sweenus/SimplySwords/blob/Architectury-1.21/developer-docs/api/gem-sockets-and-powers.md
- Combat/damage API: https://github.com/Sweenus/SimplySwords/blob/Architectury-1.21/developer-docs/api/combat-and-damage.md
- Unique weapon walkthrough: https://github.com/Sweenus/SimplySwords/blob/Architectury-1.21/developer-docs/unique-weapon-walkthrough.md
- SimplySwordsAPI: https://github.com/Sweenus/SimplySwords/blob/Architectury-1.21/common/src/main/java/net/sweenus/simplyswords/api/SimplySwordsAPI.java
- Client / Simply Tooltips integration: https://github.com/Sweenus/SimplySwords/blob/Architectury-1.21/developer-docs/api/client-integration.md
- Simply Swords 1.70.2 NeoForge release: https://www.curseforge.com/minecraft/mc-mods/simply-swords/files/8746001
- Simply More project: https://www.curseforge.com/minecraft/mc-mods/simply-more
- Simply More 1.3.0 ALPHA installed line: https://www.curseforge.com/minecraft/mc-mods/simply-more/files/8721021
- Integrated Simply Swords 1.4.0: https://modrinth.com/mod/integrated-simply-swords/version/1.4.0%2B1.21.1-neoforge
- Epic Fight - Mod Compat: https://www.curseforge.com/minecraft/mc-mods/epic-fight-mod-compat
- Simply Swords: Cataclysm repository: https://github.com/Cephelo/SimplyCataclysmMod
- Cursium implementation: https://github.com/Cephelo/SimplyCataclysmMod/blob/master/src/main/java/dev/cephelo/simplycataclysm/item/CursiumSwordItem.java
- Ignitium implementation: https://github.com/Cephelo/SimplyCataclysmMod/blob/master/src/main/java/dev/cephelo/simplycataclysm/item/IgnitiumSwordItem.java
- Witherite implementation: https://github.com/Cephelo/SimplyCataclysmMod/blob/master/src/main/java/dev/cephelo/simplycataclysm/item/WitheriteSwordItem.java

## 15.16 Regra operacional para o Chat 1

Ao encontrar uma perk de combate/armas, o Chat 1 deve cruzá-la com este capítulo quando ela puder afetar **dano de arma, attack speed, armor penetration/sunder, backstab, bleed, execute, extra hit, loot on-hit, Runic Power, Unique ability, Awakening, sockets/gem powers ou materiais Cataclysm**.

A pergunta obrigatória não é apenas “a perk funciona com Simply Swords?”, mas:

> **qual camada do stack é a authority do efeito, qual evento real deve ser observado e como a integração evita duplicar Implicit, ability, gem power, addon trait ou pipeline do Epic Fight?**

Sem resposta comprovável para essas três partes, a integração deve permanecer **FAIL-CLOSED** e voltar para pesquisa em vez de receber um bônus genérico substituto.
