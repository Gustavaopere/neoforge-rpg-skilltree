# I0003 — Agilidade

- **ID runtime:** `AttributeId.AGILITY`
- **Família:** Internal Attribute / Ponto 0
- **Status:** `CONCEPT-REVIEWED / TECH-PENDING`
- **Escopo:** global; rank investido sem limite canônico
- **Identidade:** movimento, cadência corporal e entrega de projéteis
- **Fonte do rank:** RPG Core `AttributeRanks` / ledger de Core Points
- **Custo de rank:** `AttributeRankCostPolicy` (proposta no contrato; não implementada aqui)

## Progressão proposta

Todos os bônus começam em rank 0 com zero; cada marco é cumulativo, mas cada marco posterior vale menos que o anterior. Fórmula completa em [`INVESTMENT-BALANCE-V0.1.md`](./INVESTMENT-BALANCE-V0.1.md).

| Estatística | Primeiro ganho | A cada | Fator q | Limite teórico | Rank 30 | Rank 100 |
|---|---:|---:|---:|---:|---:|---:|
| Velocidade de movimento | 0,32% | 5 ranks | 0,96 | 8% | 1,738% | 4,464% |
| Velocidade de ataque | 0,28% | 7 ranks | 0,96 | 7% | 1,055% | 3,047% |
| Velocidade inicial de projéteis | 0,32% | 10 ranks | 0,96 | 8% | 0,922% | 2,681% |

## Contrato de cada trilha

### 1. Velocidade de movimento

Bônus relativo pequeno à velocidade a pé; não alterar veículos/voo/montarias sem provider real.

**Boundary e evidência pendente:** minecraft:movement_speed e modifier persistente idempotente.

### 2. Velocidade de ataque

Cadência adicional somente em ataques cujo pipeline suporta mudança segura de velocidade.

**Boundary e evidência pendente:** minecraft:attack_speed; Epic Fight 21.17.3.1 requer adapter próprio para combos/cooldowns.

### 3. Velocidade inicial de projéteis

Projéteis elegíveis lançados pelo jogador saem discretamente mais rápidos; não acelera todo objeto ou projétil em voo.

**Boundary e evidência pendente:** Evento nativo de disparo, ownership e colisões provados por provider.

## Fronteiras e sinergias

- **Não deve fazer:** Não concede esquiva garantida, críticos gratuitos, projéteis extras ou detecção de tesouro.
- **Sinergias pretendidas:** AGILITY, MARTIAL de projéteis, movimento e posicionamento.
- **Investimento como gate:** perks P/S/T podem pedir `AGILITY >= N` sem consumir rank na compra; os valores N serão balanceados no catálogo e validados por `NodeAccessResolver`.
- **Arquitetura:** calculado somente a partir do rank canônico; nunca acumular bonus por login/cast/tick e nunca duplicar o adapter nativo com um segundo buff de mesmo efeito.
- **Versionamento:** se houver provider externo, consultar `PROJECT-INSTRUCTIONS/modlist` e source/API/JAR **exatamente da versão instalada** antes de marcar `TECH-AUDITED`. Ausência de hook seguro significa fail-closed.

## Critérios específicos de validação

- Conferir rank 0, primeiro marco, rank 30, 100, 300, 1000 e rank extremo sem overflow.
- Verificar que o limite teórico desta trilha não pode ser ultrapassado apenas pelo investimento em Agilidade.
- Testar refresh idempotente, respec e remoção do modifier; reiniciar servidor sem drift.
- Testar stacking com equipamento e outras perks em um único pipeline real.

**Pendência:** a presente ficha aprova a **proposta de design**, não afirma existência de runtime, hooks ou balanceamento final.
