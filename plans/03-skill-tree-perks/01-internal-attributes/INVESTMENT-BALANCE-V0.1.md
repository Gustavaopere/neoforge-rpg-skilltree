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

O design quantitativo v0.1 da tabela acima **foi implementado como cálculo puro para as 18 contribuições** em `core/AttributeInvestmentBonuses.java`, mas **somente quatro benefícios possuem projeção vanilla no runtime**:

| Atributo / benefício | Alvo vanilla NeoForge 1.21.1 | Status |
|---|---|---|
| Força — resistência a empurrão | `minecraft:generic.knockback_resistance` / ADD_VALUE | IMPLEMENTADO, AINDA NÃO VALIDADO |
| Constituição — vida máxima | `minecraft:generic.max_health` / ADD_VALUE | IMPLEMENTADO, AINDA NÃO VALIDADO |
| Agilidade — movimento | `minecraft:generic.movement_speed` / ADD_MULTIPLIED_BASE | IMPLEMENTADO, AINDA NÃO VALIDADO |
| Agilidade — velocidade de ataque | `minecraft:generic.attack_speed` / ADD_MULTIPLIED_BASE | IMPLEMENTADO, AINDA NÃO VALIDADO (Epic Fight ainda não certificado) |

Cada modifier tem ID permanente próprio no namespace `rpgskilltree:investment/` e é **transient**, removido e reaplicado sob autoridade do servidor em compra, refund, login/respawn, troca de dimensão e refresh de efeitos passivos. A vida atual preserva a proporção quando o total muda. Ausência de alvo registrado deixa somente aquele efeito inativo. As 14 trilhas restantes estão catalogadas nos seis dossiês como BLOQUEADO: nenhuma possui integração de provider fictícia.

**Preço real não alterado:** 1 Core Point por rank pela `UnitAttributeRankCostPolicy` já instalada; a curva crescente a cada 50 ranks continua **proposta não aprovada**. Ao alterar esta curva no futuro, auditar ledgers e refunds históricos: o custo de reembolso não pode ser recalculado sob uma política divergente do gasto original sem migração explícita.

**Requisitos:** `NodeAccessRequirement.requiredAttributeRanks` (mapa `AttributeId -> long`) lê ranks Core existentes e é avaliado antes do débito de Passive Points. Datapacks suportam `"requiredAttributes":{"INTELLIGENCE":20,"AGILITY":15}`. Ranks não são debitados como requisito; respec de Core pode invalidar nós já adquiridos, que são reconciliados usando o respec/refund canônico de Passive Points.

**Base documental conferida:** `PROJECT-INSTRUCTIONS/modlist/modlist.md` na `main@9e6436edb0049a8dcc4e47165db0f6850e5efa68` (14/10/2026), Iron's `irons_spellbooks-1.21.1-3.16.3.jar`, Ars Nouveau `ars_nouveau-1.21.1-5.13.1.jar`, Epic Fight `epic-fight-21.17.3.1-mc1.21.1-neoforge.jar` e Apothic Attributes `ApothicAttributes-1.21.1-2.10.1.jar`. Esses dados **não** certificam APIs ou hooks específicos; cópia física do pack, JARs correspondentes, QA de addons e dedicated server ainda pendentes.

**Testes adicionados (execução pendente):** `AttributeInvestmentBonusesJUnitTest` (18 curvas, limites, extremos), `AttributeInvestmentGateJUnitTest` (thresholds, compra, refund de nó), `InvestedAttributeGameTests` (quatro alvos de atributos reais e remoção). Status VALIDATED exige executar `./gradlew test`, `bash scripts/test-core.sh`, `python3 scripts/validate-data.py`, NeoForge GameTests e smoke de servidor. Não houve teste físico completo de gameplay ou modpack.
