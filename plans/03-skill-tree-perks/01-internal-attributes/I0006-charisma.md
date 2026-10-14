# I0006 — Carisma

- **ID runtime:** `AttributeId.CHARISMA`
- **Família:** Internal Attribute / Ponto 0
- **Status:** `CONCEPT-REVIEWED / TECH-PENDING`
- **Escopo:** global; rank investido sem limite canônico
- **Identidade:** negociação, liderança de aliados e cura prestada
- **Fonte do rank:** RPG Core `AttributeRanks` / ledger de Core Points
- **Custo de rank:** `AttributeRankCostPolicy` (proposta no contrato; não implementada aqui)

## Progressão proposta

Todos os bônus começam em rank 0 com zero; cada marco é cumulativo, mas cada marco posterior vale menos que o anterior. Fórmula completa em [`INVESTMENT-BALANCE-V0.1.md`](./INVESTMENT-BALANCE-V0.1.md).

| Estatística | Primeiro ganho | A cada | Fator q | Limite teórico | Rank 30 | Rank 100 |
|---|---:|---:|---:|---:|---:|---:|
| Desconto em compras de aldeões | 0,32% | 5 ranks | 0,96 | 8% | 1,738% | 4,464% |
| Dano de companheiros do jogador | 0,4% | 8 ranks | 0,96 | 10% | 1,153% | 3,873% |
| Cura realizada em aliados | 0,4% | 10 ranks | 0,96 | 10% | 1,153% | 3,352% |

## Contrato de cada trilha

### 1. Desconto em compras de aldeões

Aplica desconto apenas a transações de compra elegíveis, sem permitir preço zero e sem reduzir também o preço de venda.

**Boundary e evidência pendente:** Trading vanilla; respeitar gossip/cura/Hero of the Village e antifraude; boundary técnico a investigar.

### 2. Dano de companheiros do jogador

Melhora pequena o dano de summons/pets legítimos com owner e autoria válidos.

**Boundary e evidência pendente:** Minecraft tameable e summons modded apenas quando o ownership estiver comprovado.

### 3. Cura realizada em aliados

Bônus à cura que o jogador realiza em outras entidades elegíveis, excluindo autocura e overheal.

**Boundary e evidência pendente:** Healing event com caster/recipient distintos, sem proc de proc ou cura de mobs hostis.

## Fronteiras e sinergias

- **Não deve fazer:** Não concede reputação, novos summons, domesticação, produção automática ou descontos em trocas exploráveis.
- **Sinergias pretendidas:** SUMMONING, HEALING e jogabilidade social/aldeões; úteis sem exigir que CHA seja poder mágico puro.
- **Investimento como gate:** perks P/S/T podem pedir `CHARISMA >= N` sem consumir rank na compra; os valores N serão balanceados no catálogo e validados por `NodeAccessResolver`.
- **Arquitetura:** calculado somente a partir do rank canônico; nunca acumular bonus por login/cast/tick e nunca duplicar o adapter nativo com um segundo buff de mesmo efeito.
- **Versionamento:** se houver provider externo, consultar `PROJECT-INSTRUCTIONS/modlist` e source/API/JAR **exatamente da versão instalada** antes de marcar `TECH-AUDITED`. Ausência de hook seguro significa fail-closed.

## Critérios específicos de validação

- Conferir rank 0, primeiro marco, rank 30, 100, 300, 1000 e rank extremo sem overflow.
- Verificar que o limite teórico desta trilha não pode ser ultrapassado apenas pelo investimento em Carisma.
- Testar refresh idempotente, respec e remoção do modifier; reiniciar servidor sem drift.
- Testar stacking com equipamento e outras perks em um único pipeline real.

**Pendência:** a presente ficha aprova a **proposta de design**, não afirma existência de runtime, hooks ou balanceamento final.


## Estado técnico da implementação — 14/10/2026

**Custo em uso:** `UnitAttributeRankCostPolicy.INSTANCE`, 1 Core Progression Point por rank; a proposta crescente de 50 ranks **não foi ativada**. Ranks e refunds persistem via `CoreProgressionState.attributeRanks()` e ledger Core. Todos os três ganhos seguem `a × (1−q^floor(rank/k))/(1−q)`, com valores/intervalos da tabela de design, sempre recalculados. Esses limites restringem a contribuição da trilha, não a estatística global.

| Trilha | Situação da integração |
|---|---|
| Desconto em compras de aldeões | BLOQUEADO — falta hook sem economia abusiva, preço zero ou redução na venda |
| Dano de companheiros do jogador | BLOQUEADO — autoria e owner de summons de múltiplos providers não validados |
| Cura realizada em aliados | BLOQUEADO — falta boundary de cura com caster/recipient sem dupla aplicação |

A implementação vanilla usa `AttributeInvestmentBonuses` e `InvestedAttributeEffectsRuntime.refresh`, calculada pelo servidor a partir dos ranks, com IDs transitórios removíveis. Benefícios de providers não verificados continuam inativos (*fail-closed*). Não houve teste físico do modpack; JUnit/GameTests foram adicionados para verificação em CI. Uma integração marcada IMPLEMENTADO **ainda não é VALIDADO** antes de resultados dos testes de runtime.
