# Ponto 0 — Sistema de investimento dos seis atributos (proposta de balanceamento v0.1)

**Status:** `CONCEPT-REVIEWED / TECH-PENDING`. As fórmulas e números desta página são uma **proposta pronta para prototipagem, não código de gameplay instalado ou coeficientes definitivamente aprovados**.

## Intenção

- Seis atributos globais com ranks ilimitados, cada qual fornecendo três trilhas próprias de benefícios.
- Ganhos por **marcos de investimento** (a cada 3/4/5/7/8/10/12 ranks, conforme estatística), sem poder crescer indefinidamente de forma linear.
- Valores limitados matematicamente mesmo para ranks enormes; crescimento inicial palpável e retorno marginal cada vez menor.
- Benefícios universais usam stats do Minecraft; integrações com Iron's, Epic Fight ou outros providers só são ativadas mediante **hooks comprovados na versão física do mod**.
- O atributo nunca desbloqueia automaticamente uma skill nem substitui Standard Perks, Specialization Perks ou perfis de Transmutation.
- Atributos investidos também servem como **requisitos não consumíveis** para perks (ex.: INT >= 20); não debitar ranks ao comprar uma perk.

## Referências de design — inspiração, não reprodução

- [Baldur's Gate 3 — Abilities](https://bg3.wiki/wiki/Abilities): identidade separada entre força, destreza, constituição, inteligência, sabedoria e carisma; percepção, negociação e atletismo são checks situacionais, não números universais de dano.
- [Path of Exile — Attribute](https://www.poewiki.net/wiki/Attribute): bônus em intervalos fixos de pontos (por exemplo força, destreza e inteligência), além de requisitos de equipamento.
- [Elden Ring — Vigor](https://eldenring.wiki.gg/wiki/Vigor) e [Mind](https://eldenring.wiki.gg/wiki/Mind): retornos reduzidos depois de soft caps em HP e FP.

**Adaptação a Minecraft:** não existe uma rolagem nativa de Perception ou Persuasion que descubra automaticamente objetos ocultos como no BG3. Detecção especial de locais, análise de mecanismos, novas negociações, contratos e caça a tesouros devem existir como **perks/ações com provider real**, não como visão raio-X, scanner de minério ou loot grátis causado pelo simples atributo.

## Fórmula determinística de marcos

Seja `R` o rank **investido canônico** no atributo, `k` o intervalo em ranks, `a` o ganho do primeiro marco e `q` o fator de retorno marginal (0 < q < 1):

```text
m = floor(R / k)
bonus(R; k, a, q) = a * (1 - q^m) / (1 - q)
limite_teorico = a / (1 - q)
```

- Rank 0 → bônus 0.
- Primeiro marco → exatamente `a`.
- Cada marco posterior concede `a × q^(m-1)` além do acumulado.
- Nenhum atributo tem cap de ranks; os bônus se **aproximam** dos limites, sem explosão infinita.
- Os números são computados a partir do estado investido inteiro a cada refresh: **não** somar um bônus a cada evento de login/tick/cast. Aplicar modifier estável e idempotente.
- O `q` padrão proposto é 0,96; a mana plana usa 0,98 para dar uma curva de reserva mais gradual.
- `%` significa aumento/redução RELATIVA da estatística explicitada, não pontos percentuais no atributo nativo. Apenas o campo de knockback usa `pp` (pontos percentuais de resistência).
- Internamente usar precisão finita estável e arredondar **somente na projeção/apresentação**. Uma estatística discreta (ex.: mana inteira) aplica a política de arredondamento do provider sobre o valor agregado, não sobre cada marco.

### Curva de custo de investimento — proposta independente dos bônus

O RPG Core mantém `AttributeRankCostPolicy` como fonte de verdade, e **não** devemos substituir sua política em vários arquivos. Para o primeiro protótipo, a proposta de custo por **próximo rank** (1-indexado) é:

```text
cost(rank_novo) = 1 + floor((rank_novo - 1) / 50) Core Progression Points
```

Ex.: ranks 1–50 custam 1 ponto cada; 51–100 custam 2; 101–150 custam 3. Não existe rank máximo. Essa curva é **candidata**: precisa ser confrontada com a frequência real de ganho de Core Points e com o `Perk Budget` do Core antes de ser ativada. Custos de perks e Specialist Points **não** são substituídos por esta fórmula. Não permitir overflow ou custo negativo em ranks muito altos.

O gasto para múltiplos ranks deve somar os custos de todos os ranks no intervalo, usar aritmética checada e respeitar refunds/respec pela ledger canônica.

## Proposta quantitativa por atributo

| Atributo | Bônus e primeiro marco | Intervalo | q | Limite teórico de contribuição |
|---|---|---:|---:|---:|
| Força | Dano físico corpo a corpo: 0,4% | 5 ranks | 0,96 | 10% |
| Força | Mineração manual: 0,3% | 8 ranks | 0,96 | 7,5% |
| Força | Resistência a empurrão: 0,4 pp | 12 ranks | 0,96 | 10 pp |
| Constituição | Vida máxima: 0,5 HP | 4 ranks | 0,96 | 12,5 HP |
| Constituição | Curas recebidas: 0,4% | 8 ranks | 0,96 | 10% |
| Constituição | Mitigação de dano ao longo do tempo: 0,4% | 12 ranks | 0,96 | 10% |
| Agilidade | Velocidade de movimento: 0,32% | 5 ranks | 0,96 | 8% |
| Agilidade | Velocidade de ataque: 0,28% | 7 ranks | 0,96 | 7% |
| Agilidade | Velocidade inicial de projéteis: 0,32% | 10 ranks | 0,96 | 8% |
| Inteligência | Mana máxima: 1 mana | 3 ranks | 0,98 | 50 mana |
| Inteligência | Regeneração de mana: 0,2% | 5 ranks | 0,96 | 5% |
| Inteligência | Poder mágico geral: 0,4% | 10 ranks | 0,96 | 10% |
| Determinação | Recuperação de stamina: 0,4% | 5 ranks | 0,96 | 10% |
| Determinação | Redução da duração de controle nocivo: 0,4% | 8 ranks | 0,96 | 10% |
| Determinação | Recuperação de cooldown elegível: 0,2% | 10 ranks | 0,96 | 5% |
| Carisma | Desconto em compras de aldeões: 0,32% | 5 ranks | 0,96 | 8% |
| Carisma | Dano de companheiros do jogador: 0,4% | 8 ranks | 0,96 | 10% |
| Carisma | Cura realizada em aliados: 0,4% | 10 ranks | 0,96 | 10% |

**Todos os limites acima são do bônus concedido pelo investimento neste atributo**, não cap global da estatística após equipamentos, buffs e progressões nativas. Cap global, ordem de aplicação e stack dependem da política de cada stat/provider.

### Exemplos acumulados para o mesmo rank investido em cada atributo

Valores arredondados só para leitura; não são acréscimos por nível de personagem.

| Atributo e trilhas na ordem da tabela | Rank 30 | Rank 100 | Rank 300 |
|---|---|---|---|
| Força | 2,17% / 0,86% / 0,78 pp | 5,58% / 2,90% / 2,79 pp | 9,14% / 5,84% / 6,40 pp |
| Constituição | 3,11 HP / 1,15% / 0,78% | 8,00 HP / 3,87% / 2,79% | 11,91 HP / 7,79% / 6,40% |
| Agilidade | 1,74% / 1,05% / 0,92% | 4,46% / 3,05% / 2,68% | 7,31% / 5,74% / 5,65% |
| Inteligência | 9,15 mana / 1,09% / 1,15% | 24,33 mana / 2,79% / 3,35% | 43,37 mana / 4,57% / 7,06% |
| Determinação | 2,17% / 1,15% / 0,58% | 5,58% / 3,87% / 1,68% | 9,14% / 7,79% / 3,53% |
| Carisma | 1,74% / 1,15% / 1,15% | 4,46% / 3,87% / 3,35% | 7,31% / 7,79% / 7,06% |

## Regras de segurança e integração

1. **Fonte canônica:** `CoreProgressionState.attributeRanks()` (ou adapter equivalente no Core); `I0001..I0006` não representam perks compradas com Passive Points.
2. **Cálculo puro:** `AttributeId + long rank -> três contribuições`, gerado no servidor e recomputado em rank change, respec, login/relog, death/clone e reload de rules.
3. **Projeção única:** `AttributeRanks -> derived CanonicalStat contributions -> EffectiveStats/ModifierResolver -> adapter nativo`. Para um determinado atributo de provider, escolher **um** lugar para somar o bônus; não lançar simultaneamente modifier nativo e buff duplicado no damage event.
4. **Unidade explícita:** ex.: poder mágico relativo +4% é +0,04 no multiplicador quando apropriado, **não** +4 valores brutos no `spell_power` da release Iron's, cuja base/unidade precisam ser checadas.
5. **Compat opcional:** provider ausente, versão incompatível ou registry/hook não confirmado → só a contribuição daquele provider fica inativa, registrada em diagnóstico (fail-closed), sem quebrar o Core ou transformar o bônus em outro efeito.
6. **Resistências/cooldowns:** aplicar clamps e caps agregados que preservem riscos de gameplay; não permitir 100% esquiva, redução de cooldown para zero ou regen infinita.
7. **Autoridade:** recalcular no servidor; cliente recebe snapshot apenas para tooltip; nada de classloading de provider opcional quando ausente.
8. **Economia:** thresholds de atributos para P/S/T são requisitos **não consumíveis**; compras gastam seus próprios pontos e seguem os gates do `NodeAccessResolver`, que ainda requer extensão para atributo mínimo.
9. **Evidência de mod:** Iron's catalogado como `irons_spellbooks-1.21.1-3.16.3.jar` (`irons_spellbooks`); Epic Fight como `21.17.3.1`. Essas são versões **documentadas na modlist**, não prova de que adapters derivados já foram construídos/testados. Conferir JAR/source exato e runtime físico quando implementar.
10. **Revisão de power budgets:** testar builds concentradas (um atributo em ranks 30/100/300/1000), distribuídas, híbridas, equipamentos fortes, buffs e status adversos; efeitos por categoria devem ser comparáveis entre diferentes playstyles sem tentar forçar atributos a serem igualmente úteis para todas as classes.

## Matriz dos três benefícios por atributo

- **Força:** dano físico corpo a corpo, esforço de mineração manual, resistência a empurrão.
- **Constituição:** vida máxima, curas recebidas, mitigação de dano nocivo periódico.
- **Agilidade:** movimento, cadência de ataque, velocidade inicial de projéteis elegíveis.
- **Inteligência:** reserva máxima de mana, regeneração de mana, poder mágico geral.
- **Determinação:** recuperação de stamina, mitigação de duração de controles, recuperação de cooldown elegível.
- **Carisma:** desconto seguro nas compras de aldeões, dano de companheiros próprios, cura prestada a outros.

## O que o código já oferece e o que falta

**Existente no repositório:** `AttributeId`, `AttributeRanks`, `AttributeRankCostPolicy`, `AttributeRankMutationService`, `CanonicalStatCatalog`, `ModifierResolver`, `RpgEffectiveStatsService`, `AttributeNodeEffectRuntime`. O catálogo de stats já contém `minecraft:max_health`, `minecraft:movement_speed`, `irons:max_mana`, `irons:mana_regen`, `irons:spell_power` etc.

**Ainda não implementado nesta etapa:** tabela de marcos de rank I → stats derivados; custo de rank acima como policy instalada; bridge de requisitos mínimos I em `NodeAccessRequirement`; adapters específicos Epic Fight/Iron's/villagers/summons/healings; balance final; validação por GameTests. O runtime atual de `AttributeNodeEffectRuntime` aplica efeitos comprados dos **nós passivos**, não automaticamente os derivados de I.

## Testes de aceitação para implementação posterior

- [ ] `R=0` concede 0; no primeiro marco o bônus é exatamente `a`; entre marcos não muda.
- [ ] Monotonicidade e limite para `R=1,30,100,300,1000,10^6,Long.MAX_VALUE` (sem overflow/NaN/Infinity).
- [ ] Custo rank individual e vários ranks, arredondamento, ledger balance e refund; ranks não diminuem devido a compra de perk com threshold.
- [ ] Idempotência em login/reload/respec/clone e ausência de modifiers órfãos após remover ranks.
- [ ] Iron's: mana/mana regen/spell power sem duplicar buffs de equipamentos nem cast settlement; provider ausente não crasha o servidor.
- [ ] Vanilla: max HP recalcula sem exploit de cura, movimento e resistência sem drift; comércio não gera trocas 0 emeralds/loop de lucro.
- [ ] Epic Fight/projéteis: buffs só atingem autoria permitida, sem acelerar doubles de animação/hits.
- [ ] DoT/control/cooldown: efeitos classificados, limites, PvP e friendly fire, sem recursão.
- [ ] Tooltip PT-BR expõe bonus efetivo e próximo marco, não promete stats indisponíveis.

**Aprovação:** fórmula e design podem ser revistos agora; status `TECH-AUDITED` e `VALIDATED` apenas depois de implementação e evidência da versão real dos providers.


## Implementação incremental registrada — 14/10/2026

O design quantitativo v0.1 da tabela acima **foi implementado como cálculo puro para as 18 contribuições** em `core/AttributeInvestmentBonuses.java`, mas **cinco benefícios possuem projeção vanilla no runtime, mais uma cura nativa e três alvos Iron's versionados (CI incremental pendente)**:

| Atributo / benefício | Alvo vanilla NeoForge 1.21.1 | Status |
|---|---|---|
| Força — resistência a empurrão | `minecraft:generic.knockback_resistance` / ADD_VALUE | IMPLEMENTADO, AINDA NÃO VALIDADO |
| Constituição — vida máxima | `minecraft:generic.max_health` / ADD_VALUE | IMPLEMENTADO, AINDA NÃO VALIDADO |
| Agilidade — movimento | `minecraft:generic.movement_speed` / ADD_MULTIPLIED_BASE | IMPLEMENTADO, AINDA NÃO VALIDADO |
| Agilidade — velocidade de ataque | `minecraft:generic.attack_speed` / ADD_MULTIPLIED_BASE | IMPLEMENTADO, AINDA NÃO VALIDADO (Epic Fight ainda não certificado) |

Cada modifier tem ID permanente próprio no namespace `rpgskilltree:investment/` e é **transient**, removido e reaplicado sob autoridade do servidor em compra, refund, login/respawn, troca de dimensão e refresh de efeitos passivos. A vida atual preserva a proporção quando o total muda. Ausência de alvo registrado deixa somente aquele efeito inativo. As demais nove trilhas sem ponte de gameplay continuam bloqueadas; as adaptações para outros sistemas de mana/combate também não devem ser consideradas cobertas por Iron's.

**Preço real não alterado:** 1 Core Point por rank pela `UnitAttributeRankCostPolicy` já instalada; a curva crescente a cada 50 ranks continua **proposta não aprovada**. Ao alterar esta curva no futuro, auditar ledgers e refunds históricos: o custo de reembolso não pode ser recalculado sob uma política divergente do gasto original sem migração explícita.

**Requisitos:** `NodeAccessRequirement.requiredAttributeRanks` (mapa `AttributeId -> long`) lê ranks Core existentes e é avaliado antes do débito de Passive Points. Datapacks suportam `"requiredAttributes":{"INTELLIGENCE":20,"AGILITY":15}`. Ranks não são debitados como requisito; respec de Core pode invalidar nós já adquiridos, que são reconciliados usando o respec/refund canônico de Passive Points.

**Base documental conferida:** `PROJECT-INSTRUCTIONS/modlist/modlist.md` na `main@9e6436edb0049a8dcc4e47165db0f6850e5efa68` (14/10/2026), Iron's `irons_spellbooks-1.21.1-3.16.3.jar`, Ars Nouveau `ars_nouveau-1.21.1-5.13.1.jar`, Epic Fight `epic-fight-21.17.3.1-mc1.21.1-neoforge.jar` e Apothic Attributes `ApothicAttributes-1.21.1-2.10.1.jar`. Esses dados **não** certificam APIs ou hooks específicos; cópia física do pack, JARs correspondentes, QA de addons e dedicated server ainda pendentes.

**Testes adicionados (execução pendente):** `AttributeInvestmentBonusesJUnitTest` (18 curvas, limites, extremos), `AttributeInvestmentGateJUnitTest` (thresholds, compra, refund de nó), `InvestedAttributeGameTests` (quatro alvos de atributos reais e remoção). Status VALIDATED exige executar `./gradlew test`, `bash scripts/test-core.sh`, `python3 scripts/validate-data.py`, NeoForge GameTests e smoke de servidor. Não houve teste físico completo de gameplay ou modpack.

## Preview cliente, contrato de dados e testes de regressão

- `TreeDisplayProjector.project(..., AttributeRanks)` avalia requisitos de seis atributos via snapshot Core; sem snapshot recusa acesso visual a nós com requisito, sem interferir na compra server-authoritative.
- `ClientTreeLayout` lê o mapa `requiredAttributes` da árvore cliente; a UI apresenta nomes localizados, ranks atuais e mínimos (sem debitá-los).
- `scripts/export-client-tree.py` espelha o mapa de requisitos da árvore principal a partir de `data/rpgskilltree/node_rules/main.json`. O `scripts/validate-client-tree.py` exige paridade de `requiredAttributes` para main, technomancer, warlock, druid e metamorph.
- Testes adicionais: `AttributeTreeDisplayJUnitTest` e `AttributeRequirementDataJUnitTest`; o parser aceita ranks `long` positivos e rejeita chaves desconhecidas, zero, negativos, frações e overflow.
- Limitação: o layout cliente é estático; alterações arbitrárias via datapack de servidor externo não são refletidas automaticamente. O servidor é sempre a autoridade e pode negar uma compra sugerida pelo cliente se houver divergência. Requer protocolo versionado para futura UI totalmente dinâmica.

## Progresso de providers e efeitos universais (14/10/2026)

| Trilha adicionada | Provider/target | Projeção | Evidência/validação |
|---|---|---|---|
| STRENGTH_MANUAL_MINING | `minecraft:player.block_break_speed` | multiplicador nativo, exclusivamente estatística de `Player` | registry Minecraft 1.21.1; `InvestedAttributeGameTests`; novo CI pendente |
| CONSTITUTION_HEALING_RECEIVED | `LivingHealEvent`, NeoForge 21.1.250 | `event.setAmount(original×(1+bonus))`, somente receptor `ServerPlayer` | `AttributeInvestmentHealingPolicyJUnitTest`; CI pendente; sem emissão de novo evento |
| INTELLIGENCE_MAX_MANA | `irons_spellbooks:max_mana` | `ADD_VALUE` (unidades de mana) | source 3.16.3 + Gate de versão; provider-present GameTest pendente |
| INTELLIGENCE_MANA_REGEN | `irons_spellbooks:mana_regen` | `ADD_MULTIPLIED_TOTAL` (relativo) | source 3.16.3 + Gate de versão; provider-present GameTest pendente |
| INTELLIGENCE_SPELL_POWER | `irons_spellbooks:spell_power` | `ADD_MULTIPLIED_TOTAL` (relativo) | source 3.16.3 + Gate de versão; provider-present GameTest pendente |

**Fonte primária Iron's:** `iron431/irons-spells-n-spellbooks@e4056af90302d37eb1739f5ff05020b020e6e252` — `gradle.properties`, `api/registry/AttributeRegistry.java`, `capabilities/magic/MagicManager.java`. Este source aponta para a versão publicada, mas não substitui comparação binária de um JAR de pack real. A integração Iron's não assume presença em dedicated server nem equivalência com Ars Nouveau.


## Determinação — recuperação de cooldown Iron's (incremento de 14/10/2026)

A 10ª contribuição com boundary de gameplay utiliza o evento `SpellCooldownAddedEvent.Pre` da release exata Iron's `1.21.1-3.16.3`. Fonte primária pinada: `iron431/irons-spells-n-spellbooks@e4056af90302d37eb1739f5ff05020b020e6e252`, arquivos `api/events/SpellCooldownAddedEvent.java`, `api/spells/CastSource.java`, `capabilities/magic/MagicManager.java` e `api/util/Utils.java`.

**Boundary:** `SpellCooldownAddedEvent.Pre` roda depois de `getEffectiveSpellCooldown` (equipamento, `cooldown_reduction` e soft-cap Iron's) e antes de `PlayerCooldowns.addCooldown` e `SyncCooldownPacket`. `IronsInvestmentCooldownEvents` lê `CoreProgressionState.attributeRanks().rank(DETERMINATION)` apenas no servidor, verifica versão exata e `CastSource.respectsCooldown()` (`SPELLBOOK` e `SWORD`), calcula `newTicks=max(1, round(oldTicks × (1 − bonus(rank))))` para `oldTicks > 1`. Este método evita aplicar novamente o bônus ao atributo `irons_spellbooks:cooldown_reduction` e evita recarga positiva de zero tick. Recarregamentos já zero continuam intactos. Efeitos de cooldown de outros providers continuam bloqueados.

**Limite:** o bônus do próprio investimento tende a 5% de redução do número de ticks **anterior ao arredondamento**; em cooldowns curtos o arredondamento pode resultar em nenhum tick reduzido, conforme a semântica nativa discreta. Não há recurso, barra ou storage paralelo.

**Testes de regressão:** `IronsCooldownInvestmentPolicyJUnitTest` e `IronsCooldownInvestmentGameTests` cobrem marcos, rank extremo, cooldown mínimo, casts permitidos, ausência de provider e leitura do Core snapshot. CI atual precisa terminar verde; testes físicos do pack completo e presença de plugins adicionais ainda não realizados.

## Determinação — regeneração nativa Epic Fight (incremento de 14/10/2026)

A 11ª contribuição implementada no código integra `DETERMINATION_STAMINA_REGEN` ao atributo **Epic Fight** `epicfight:stamina_regen`. O modifier `rpgskilltree:investment/epicfight/determination_stamina_regen` é transitório e idempotente, aplicado no `InvestedAttributeEffectsRuntime.refresh` já utilizado para compra/refund, relog, respawn e mudança de dimensão. A operação `ADD_MULTIPLIED_TOTAL` usa a contribuição relative de marcos (primeiro ganho 0,4% em 5 ranks; limite matemático de 10%). Sem stamina paralela, sem mutação de `PlayerPatch` e sem buff em ParCool.

**Contrato de provider:** `epicfight` exatamente `21.17.3.1`, maven version ID `8HHhJt6i` no `gradle.properties`. A fonte pública da linha `21.17.3` em `Antikythera-Studios/epicfight@a78aa24b72e90a9d09f5fd61925e4369d117abaf` registra `stamina_regen` em `EpicFightAttributes` e a usa na recuperação em `PlayerPatch`. O site da [release 21.17.3.1](https://modrinth.com/mod/epic-fight/version/21.17.3.1-mc1.21.1-neoforge) publica sources correspondentes, mas esta auditoria leu a branch 21.17.3; somente GameTest com a release 21.17.3.1 comprovará que o registro e a operação persistiram. Falha de ABI/versão resulta em desativação apenas da contribuição afetada.

**Teste:** `EpicFightInvestmentAttributePolicyJUnitTest` e `EpicFightInvestmentAttributeGameTests` (com provider ausente e no runtime Epic real) verificam target, versão, limite, `ADD_MULTIPLIED_TOTAL`, refresh duplicado e restauração após respec. O CI incremental está pendente; não tratar resultados históricos como validação da nova branch.


## Auditoria incremental — Força/melee e Determinação/controle (14/10/2026)

- **12ª contribuição proposta em código (CI pendente):** `STRENGTH_MELEE_DAMAGE` → `minecraft:generic.attack_damage` com `ADD_MULTIPLIED_TOTAL`, ID estável transitório. Primeiro marco +0,4% em 5 ranks; aproxima-se do máximo teórico de +10%. Sem segunda multiplicação em evento de dano, sem atributo em criaturas, sem projéteis manipulados manualmente. Cobertura ao pipeline nativo vanilla; integração com golpes customizados Epic Fight exige QA.
- **DETERMINATION_CONTROL_DURATION permanece bloqueado:** `MobEffectEvent.Added` ocorre antes do merge em `addEffect`, mas `forceAddEffect` não percorre esse evento; `Applicable` pode receber instâncias mutáveis potencialmente reutilizadas, sem classificação segura do domínio. Ainda não há allowlist e semântica aprovada de fontes, buffs, efeitos ocultos e refresh. Não prometer redução genérica de controles.
- **Situação desta alteração:** 12 de 18 contribuições com boundary de gameplay no código; seis bloqueadas (`CONSTITUTION_DAMAGE_OVER_TIME`, `AGILITY_PROJECTILE_SPEED`, `DETERMINATION_CONTROL_DURATION`, `CHARISMA_VILLAGER_DISCOUNT`, `CHARISMA_COMPANION_DAMAGE`, `CHARISMA_ALLY_HEALING`). A qualidade final requer novos workflows verdes no SHA do novo gameplay e QA do pack; SonarQube registrou anteriormente 70,6% de cobertura no código novo contra gate de 80%.


## Agilidade — velocidade inicial de projéteis com origem validada (incremento posterior)

A 13ª contribuição de gameplay adiciona um ajuste de velocidade somente a flechas de `Items.BOW` geradas por lançamentos atribuídos a um `ServerPlayer` elegível. Usa `ArrowLooseEvent` e o registro de correlação pré-existente `PhysicalProjectileMasteryEvents.onEntityJoin` (NeoForge), então altera o vetor `getDeltaMovement` uma única vez em `EventPriority.LOWEST`. Exclui crossbow, projéteis de magia, mobs, `FakePlayer`, retorno do disco, spawn cancelado, ausência de recibo, velocidade zero ou não finita. A curva existente entrega +0,32% a cada primeiro grupo de 10 ranks, com teto assintótico de +8% de contribuição. Sem custo novo, estado salvo paralelo ou componente de rede.

O novo `AgilityProjectileInvestmentGameTests` cobre procedência, milestone, cap, `Long.MAX_VALUE`, repetição e exclusões. **CI do novo incremento pendente**, inclusive SonarQube >=80% New Code; anteriormente Sonar reportou **69,4%** no SHA `4ca60f2`. Continuam cinco contribuições de gameplay não implementadas: Constituição/DoT, Determinação/controle, Carisma/comércio, Carisma/dano de companheiros e Carisma/cura em aliados. Compatibilidade dos demais providers e QA físico do pack não certificados.


## Carisma — dano de pet vanilla verificado (incremento posterior)

O 14º benefício ligado a gameplay **não** é um multiplicador universal de summons: aplica `CHARISMA_COMPANION_DAMAGE` apenas a golpes físicos diretos de lobos `EntityType.WOLF`, domesticados, com dono `ServerPlayer` online no mesmo nível. O alvo deve ser `Enemy` hostil não aliado; eventos cancelados, dano zero/não finito, PvP, fontes mágicas/indiretas e classes wolf estendidas por mods são excluídas. A curva é +0,4% no rank 8 com teto teórico de contribuição +10%; 1 Core Point/rank continua sendo a política de custo. Usa `LivingIncomingDamageEvent`/NeoForge 1.21.1, sem segunda fonte de atributo, sem buffs salvos e com proteção de idempotência por instância de evento.

`CharismaCompanionInvestmentGameTests` cobre condições positivas e fail-closed; sua fixture de `ServerPlayer` é isolada, logo o lookup de dono online e a interação do lobo com os mods do pack **ainda exigem QA real**. O Sonar anterior estava em 72,4% (gate 80%) no commit `d7d878d`. Contagem provisória: 14 contribuições no código, quatro sem conexão de gameplay (DoT, controle, comércio, cura prestada); cobertura completa de summons modded permanece pendente.
