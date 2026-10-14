# I0005 — Determinação

- **ID runtime:** `AttributeId.DETERMINATION`
- **Família:** Internal Attribute / Ponto 0
- **Status:** `CONCEPT-REVIEWED / TECH-PENDING`
- **Escopo:** global; rank investido sem limite canônico
- **Identidade:** disciplina, constância, recuperação e resistência a interrupções
- **Fonte do rank:** RPG Core `AttributeRanks` / ledger de Core Points
- **Custo de rank:** `AttributeRankCostPolicy` (proposta no contrato; não implementada aqui)

## Progressão proposta

Todos os bônus começam em rank 0 com zero; cada marco é cumulativo, mas cada marco posterior vale menos que o anterior. Fórmula completa em [`INVESTMENT-BALANCE-V0.1.md`](./INVESTMENT-BALANCE-V0.1.md).

| Estatística | Primeiro ganho | A cada | Fator q | Limite teórico | Rank 30 | Rank 100 |
|---|---:|---:|---:|---:|---:|---:|
| Recuperação de stamina | 0,4% | 5 ranks | 0,96 | 10% | 2,172% | 5,58% |
| Redução da duração de controle nocivo | 0,4% | 8 ranks | 0,96 | 10% | 1,153% | 3,873% |
| Recuperação de cooldown elegível | 0,2% | 10 ranks | 0,96 | 5% | 0,576% | 1,676% |

## Contrato de cada trilha

### 1. Recuperação de stamina

Melhora relativa da regeneração já permitida pelo provedor de stamina, sem criar uma barra própria.

**Boundary e evidência pendente:** Epic Fight 21.17.3.1, somente com evento/API real da release auditada.

### 2. Redução da duração de controle nocivo

Reduz discretamente duração de efeitos nocivos de controle elegíveis na aplicação, nunca tempos de benefícios.

**Boundary e evidência pendente:** Effects/tags vanilla e bridges auditadas; não encurtar timers de boss ou status sem ownership.

### 3. Recuperação de cooldown elegível

Redução pequena em tempo de recarga de habilidades para as quais há modificação segura.

**Boundary e evidência pendente:** irons_spellbooks:cooldown_reduction apenas para Iron's, outras Abilities somente com bridge específica.

## Fronteiras e sinergias

- **Não deve fazer:** Não é Percepção do BG3, não revela minérios ou estruturas, nem garante imunidade a crowd control.
- **Sinergias pretendidas:** MARTIAL, SURVIVAL, ARCANE e habilidades canalizadas; complementa sem substituir eficiência de mana da INT.
- **Investimento como gate:** perks P/S/T podem pedir `DETERMINATION >= N` sem consumir rank na compra; os valores N serão balanceados no catálogo e validados por `NodeAccessResolver`.
- **Arquitetura:** calculado somente a partir do rank canônico; nunca acumular bonus por login/cast/tick e nunca duplicar o adapter nativo com um segundo buff de mesmo efeito.
- **Versionamento:** se houver provider externo, consultar `PROJECT-INSTRUCTIONS/modlist` e source/API/JAR **exatamente da versão instalada** antes de marcar `TECH-AUDITED`. Ausência de hook seguro significa fail-closed.

## Critérios específicos de validação

- Conferir rank 0, primeiro marco, rank 30, 100, 300, 1000 e rank extremo sem overflow.
- Verificar que o limite teórico desta trilha não pode ser ultrapassado apenas pelo investimento em Determinação.
- Testar refresh idempotente, respec e remoção do modifier; reiniciar servidor sem drift.
- Testar stacking com equipamento e outras perks em um único pipeline real.

**Pendência:** a presente ficha aprova a **proposta de design**, não afirma existência de runtime, hooks ou balanceamento final.
