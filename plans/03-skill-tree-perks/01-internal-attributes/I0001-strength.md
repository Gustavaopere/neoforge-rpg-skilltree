# I0001 — Força

- **ID runtime:** `AttributeId.STRENGTH`
- **Família:** Internal Attribute / Ponto 0
- **Status:** `CONCEPT-REVIEWED / TECH-PENDING`
- **Escopo:** global; rank investido sem limite canônico
- **Identidade:** impacto físico, esforço muscular e tarefas manuais
- **Fonte do rank:** RPG Core `AttributeRanks` / ledger de Core Points
- **Custo de rank:** `AttributeRankCostPolicy` (proposta no contrato; não implementada aqui)

## Progressão proposta

Todos os bônus começam em rank 0 com zero; cada marco é cumulativo, mas cada marco posterior vale menos que o anterior. Fórmula completa em [`INVESTMENT-BALANCE-V0.1.md`](./INVESTMENT-BALANCE-V0.1.md).

| Estatística | Primeiro ganho | A cada | Fator q | Limite teórico | Rank 30 | Rank 100 |
|---|---:|---:|---:|---:|---:|---:|
| Dano físico corpo a corpo | 0,4% | 5 ranks | 0,96 | 10% | 2,172% | 5,58% |
| Mineração manual | 0,3% | 8 ranks | 0,96 | 7,5% | 0,864% | 2,905% |
| Resistência a empurrão | 0,4 pp | 12 ranks | 0,96 | 10 pp | 0,784 pp | 2,786 pp |

## Contrato de cada trilha

### 1. Dano físico corpo a corpo

Aplicar somente a ataques físicos classificados como corpo a corpo, sem duplicar o pipeline de Epic Fight ou bônus de atributo vanilla.

**Boundary e evidência pendente:** minecraft:attack_damage / resolução canônica de dano físico; Epic Fight 21.17.3.1 se o adapter provar o boundary.

### 2. Mineração manual

Aumentar discretamente a velocidade legítima de quebra com ferramenta, sem produzir drops ou mudar tier de ferramenta.

**Boundary e evidência pendente:** break speed atribuído ao jogador; não aplicar em máquinas, furadeiras automáticas ou processos industriais.

### 3. Resistência a empurrão

Adicionar resistência limitada ao knockback. 'pp' são pontos percentuais de resistência (ex.: 0,4 pp = 0,004 no atributo 0–1).

**Boundary e evidência pendente:** minecraft:knockback_resistance; respeitar limite agregado do atributo.

## Fronteiras e sinergias

- **Não deve fazer:** Não concede buffs de magia, automação industrial, invulnerabilidade ou loot extra.
- **Sinergias pretendidas:** MARTIAL, MINING e combate físico; não substitui perks próprias de armas/skills.
- **Investimento como gate:** perks P/S/T podem pedir `STRENGTH >= N` sem consumir rank na compra; os valores N serão balanceados no catálogo e validados por `NodeAccessResolver`.
- **Arquitetura:** calculado somente a partir do rank canônico; nunca acumular bonus por login/cast/tick e nunca duplicar o adapter nativo com um segundo buff de mesmo efeito.
- **Versionamento:** se houver provider externo, consultar `PROJECT-INSTRUCTIONS/modlist` e source/API/JAR **exatamente da versão instalada** antes de marcar `TECH-AUDITED`. Ausência de hook seguro significa fail-closed.

## Critérios específicos de validação

- Conferir rank 0, primeiro marco, rank 30, 100, 300, 1000 e rank extremo sem overflow.
- Verificar que o limite teórico desta trilha não pode ser ultrapassado apenas pelo investimento em Força.
- Testar refresh idempotente, respec e remoção do modifier; reiniciar servidor sem drift.
- Testar stacking com equipamento e outras perks em um único pipeline real.

**Pendência:** a presente ficha aprova a **proposta de design**, não afirma existência de runtime, hooks ou balanceamento final.


## Estado técnico da implementação — 14/10/2026

**Custo em uso:** `UnitAttributeRankCostPolicy.INSTANCE`, 1 Core Progression Point por rank; a proposta crescente de 50 ranks **não foi ativada**. Ranks e refunds persistem via `CoreProgressionState.attributeRanks()` e ledger Core. Todos os três ganhos seguem `a × (1−q^floor(rank/k))/(1−q)`, com valores/intervalos da tabela de design, sempre recalculados. Esses limites restringem a contribuição da trilha, não a estatística global.

| Trilha | Situação da integração |
|---|---|
| Dano físico corpo a corpo | IMPLEMENTADO NO CÓDIGO / CI NOVO PENDENTE — atributo vanilla `minecraft:generic.attack_damage`, `ADD_MULTIPLIED_TOTAL`, ID `rpgskilltree:investment/strength_melee_damage`; o pipeline de dano não recebe segundo multiplicador. Valida ataque físico vanilla; compatibilidade com técnicas próprias do Epic Fight ainda requer QA cruzado |
| Mineração manual | IMPLEMENTADO NO CÓDIGO / CI PENDENTE — `minecraft:player.block_break_speed`; modificador `rpgskilltree:investment/strength_manual_mining`, `ADD_MULTIPLIED_TOTAL`. Afeta velocidade de quebra da entidade jogador, não eventos industriais de break de máquina |
| Resistência a empurrão | IMPLEMENTADO (validação pendente) — Minecraft 1.21.1 `minecraft:generic.knockback_resistance`; modificador `rpgskilltree:investment/strength_knockback`, ADD_VALUE |

A implementação vanilla usa `AttributeInvestmentBonuses` e `InvestedAttributeEffectsRuntime.refresh`, calculada pelo servidor a partir dos ranks, com IDs transitórios removíveis. Benefícios de providers não verificados continuam inativos (*fail-closed*). Não houve teste físico do modpack; JUnit/GameTests foram adicionados para verificação em CI. Uma integração marcada IMPLEMENTADO **ainda não é VALIDADO** antes de resultados dos testes de runtime.


### Dano de Força — bridge nativa (incremento posterior)

A contribuição `STRENGTH_MELEE_DAMAGE` usa o atributo do jogador `minecraft:generic.attack_damage` (Minecraft/NeoForge 1.21.1), operação `ADD_MULTIPLIED_TOTAL`. A fonte é a curva de ranks canônicos de Força: primeiro marco no rank 5 (+0,4%), com limite de contribuição teórico de +10%. O modifier transitório tem ID `rpgskilltree:investment/strength_melee_damage`; o refresh remove e recria esse ID uma única vez. A remoção por rank 0/respec não mantém resíduos.

**Escopo certificado apenas estruturalmente antes do CI:** o atributo vanilla determina o dano de ataques corpo a corpo que o utilizam. Não se promete cobertura de skills com cálculo próprio, nem se modifica `LivingDamageEvent`/`AttackEntityEvent`. Modificações existentes do mesmo atributo permanecem no pipeline vanilla e podem combinar multiplicativamente conforme operação; isso não equivale a um cap global de dano. `InvestedAttributeGameTests` verifica o atributo real, marcos, idempotência, rank extremo e remoção. O comportamento com Epic Fight e o modpack completo requer testes dedicados; compilação ou GameTest isolado não atestam essa compatibilidade.
