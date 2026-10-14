# I0004 — Inteligência

- **ID runtime:** `AttributeId.INTELLIGENCE`
- **Família:** Internal Attribute / Ponto 0
- **Status:** `CONCEPT-REVIEWED / TECH-PENDING`
- **Escopo:** global; rank investido sem limite canônico
- **Identidade:** reserva de magia, regeneração e potência de habilidades mágicas
- **Fonte do rank:** RPG Core `AttributeRanks` / ledger de Core Points
- **Custo de rank:** `AttributeRankCostPolicy` (proposta no contrato; não implementada aqui)

## Progressão proposta

Todos os bônus começam em rank 0 com zero; cada marco é cumulativo, mas cada marco posterior vale menos que o anterior. Fórmula completa em [`INVESTMENT-BALANCE-V0.1.md`](./INVESTMENT-BALANCE-V0.1.md).

| Estatística | Primeiro ganho | A cada | Fator q | Limite teórico | Rank 30 | Rank 100 |
|---|---:|---:|---:|---:|---:|---:|
| Mana máxima | 1 mana | 3 ranks | 0,98 | 50 mana | 9,146 mana | 24,33 mana |
| Regeneração de mana | 0,2% | 5 ranks | 0,96 | 5% | 1,086% | 2,79% |
| Poder mágico geral | 0,4% | 10 ranks | 0,96 | 10% | 1,153% | 3,352% |

## Contrato de cada trilha

### 1. Mana máxima

+1 unidade de mana no primeiro marco, com crescimento progressivamente menor nos seguintes; limites reais respeitados.

**Boundary e evidência pendente:** irons_spellbooks:max_mana do Iron's Spells 'n Spellbooks 1.21.1-3.16.3 (dossiê atual); outras manas somente por adapter.

### 2. Regeneração de mana

Bônus PERCENTUAL RELATIVO à taxa de regen existente; +0,2% significa multiplicar a regen por 1,002, não adicionar +0,2 ao atributo interno `mana_regen`.

**Boundary e evidência pendente:** irons_spellbooks:mana_regen com conversão de unidade/contrato auditada da 3.16.3.

### 3. Poder mágico geral

Incremento percentual RELATIVO no scaling real da magia; não é +1 dano absoluto nem +1 ponto nativo de `spell_power`.

**Boundary e evidência pendente:** irons_spellbooks:spell_power 1.21.1-3.16.3 e outros providers com adapter; não amplificar duas vezes.

## Fronteiras e sinergias

- **Não deve fazer:** Não concede spells, elementos, redução de mana arbitrária nem buffs universais a ataques físicos.
- **Sinergias pretendidas:** ARCANE, OCCULT, HEALING mágico e summons com dano classificado como spell.
- **Investimento como gate:** perks P/S/T podem pedir `INTELLIGENCE >= N` sem consumir rank na compra; os valores N serão balanceados no catálogo e validados por `NodeAccessResolver`.
- **Arquitetura:** calculado somente a partir do rank canônico; nunca acumular bonus por login/cast/tick e nunca duplicar o adapter nativo com um segundo buff de mesmo efeito.
- **Versionamento:** se houver provider externo, consultar `PROJECT-INSTRUCTIONS/modlist` e source/API/JAR **exatamente da versão instalada** antes de marcar `TECH-AUDITED`. Ausência de hook seguro significa fail-closed.

## Critérios específicos de validação

- Conferir rank 0, primeiro marco, rank 30, 100, 300, 1000 e rank extremo sem overflow.
- Verificar que o limite teórico desta trilha não pode ser ultrapassado apenas pelo investimento em Inteligência.
- Testar refresh idempotente, respec e remoção do modifier; reiniciar servidor sem drift.
- Testar stacking com equipamento e outras perks em um único pipeline real.

**Pendência:** a presente ficha aprova a **proposta de design**, não afirma existência de runtime, hooks ou balanceamento final.


## Estado técnico da implementação — 14/10/2026

**Custo em uso:** `UnitAttributeRankCostPolicy.INSTANCE`, 1 Core Progression Point por rank; a proposta crescente de 50 ranks **não foi ativada**. Ranks e refunds persistem via `CoreProgressionState.attributeRanks()` e ledger Core. Todos os três ganhos seguem `a × (1−q^floor(rank/k))/(1−q)`, com valores/intervalos da tabela de design, sempre recalculados. Esses limites restringem a contribuição da trilha, não a estatística global.

| Trilha | Situação da integração |
|---|---|
| Mana máxima | BLOQUEADO — sem prova da API do Iron's 3.16.3; Ars Nouveau 5.13.1 exige integração separada |
| Regeneração de mana | BLOQUEADO — contrato/semântica relativa de mana_regen não comprovado na release instalada |
| Poder mágico geral | BLOQUEADO — multiplicação de spell_power e outras escolas/providers não auditada |

A implementação vanilla usa `AttributeInvestmentBonuses` e `InvestedAttributeEffectsRuntime.refresh`, calculada pelo servidor a partir dos ranks, com IDs transitórios removíveis. Benefícios de providers não verificados continuam inativos (*fail-closed*). Não houve teste físico do modpack; JUnit/GameTests foram adicionados para verificação em CI. Uma integração marcada IMPLEMENTADO **ainda não é VALIDADO** antes de resultados dos testes de runtime.
