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


## Estado técnico da implementação — 14/10/2026

**Custo em uso:** `UnitAttributeRankCostPolicy.INSTANCE`, 1 Core Progression Point por rank; a proposta crescente de 50 ranks **não foi ativada**. Ranks e refunds persistem via `CoreProgressionState.attributeRanks()` e ledger Core. Todos os três ganhos seguem `a × (1−q^floor(rank/k))/(1−q)`, com valores/intervalos da tabela de design, sempre recalculados. Esses limites restringem a contribuição da trilha, não a estatística global.

| Trilha | Situação da integração |
|---|---|
| Velocidade de movimento | IMPLEMENTADO (validação pendente) — Minecraft 1.21.1 `minecraft:generic.movement_speed`; modificador `rpgskilltree:investment/agility_movement`, ADD_MULTIPLIED_BASE |
| Velocidade de ataque | IMPLEMENTADO somente no atributo vanilla (validação pendente) — `minecraft:generic.attack_speed`; modificador `rpgskilltree:investment/agility_attack_speed`, ADD_MULTIPLIED_BASE; efetividade nos combos Epic Fight NÃO verificada |
| Velocidade inicial de projéteis | IMPLEMENTADO NO CÓDIGO / NOVO CI PENDENTE — **somente arco vanilla** (`Items.BOW`) com autoria de `ServerPlayer` e correlação `ArrowLooseEvent` → `EntityJoinLevelEvent`. Multiplica a velocidade inicial uma vez, no servidor, até +8%; não afeta besta, projéteis mágicos, disparos sem recibo, spawns cancelados nem entidades carregadas do disco |

A implementação vanilla usa `AttributeInvestmentBonuses` e `InvestedAttributeEffectsRuntime.refresh`, calculada pelo servidor a partir dos ranks, com IDs transitórios removíveis. Benefícios de providers não verificados continuam inativos (*fail-closed*). Não houve teste físico do modpack; JUnit/GameTests foram adicionados para verificação em CI. Uma integração marcada IMPLEMENTADO **ainda não é VALIDADO** antes de resultados dos testes de runtime.


### Auditoria incremental — Agilidade/projéteis (14/10/2026)

- **Hook NeoForge 1.21.1:** `ArrowLooseEvent` marca um release válido de arco com munição; `PhysicalProjectileMasteryEvents` já correlacionava esse receipt com `AbstractArrow` em `EntityJoinLevelEvent` por jogador/arma/janela curta, para mastery física. O novo tratamento em `EventPriority.LOWEST` aproveita **somente** essa correlação, só para `Items.BOW`, com proprietário `ServerPlayer` elegível e velocidade não nula e finita. O evento pode ocorrer em cliente ou carregamento do disco; ambos são excluídos.
- **Efeito nativo:** multiplica `arrow.getDeltaMovement()` por `1+AttributeInvestmentBonuses.AGILITY_PROJECTILE_SPEED` no spawn, sem alterar o módulo da direção, dano, gravidade, pierce, itens, estoque ou colisão. Primeiro marco no rank 10 (+0,32%); contribuição assintótica limitada a +8%. Lê apenas o rank persistido em `CoreProgressionState.attributeRanks()`. Não instala um atributo permanente/estatística global nem executa em ticks futuros.
- **Idempotência e limpeza:** `AGILITY_ACCELERATED` é um `WeakHashMap` transitório de identidade para impedir segunda aplicação em `EntityJoinLevelEvent`; a referência ao projétil não é persistida e o mapa é limpo no encerramento do servidor. Entidades `loadedFromDisk()` são excluídas, assim como eventos cancelados. Essa estrutura **não** constitui armazenamento de progresso.
- **Limites:** crossbows, modded bows, projéteis de spells/mobs, dardos, tridentes e fontes sem receipt não recebem bônus. A correlação breve não é autenticação criptográfica de lançamento; exige QA com entidades derivadas e addons que possam fabricar flechas com metadados vanilla durante um disparo legítimo. Epic Fight e suas animações devem ser testados no modpack completo. Não estender o scope sem provas.
- **Teste:** `AgilityProjectileInvestmentGameTests` valida proprietário sem receipt, ranks 0/9/10/300/`Long.MAX_VALUE`, idempotência, crossbow, loading e cancelamento. Testes adicionados ao repositório; resultado do novo CI **pendente**.
- **Fonte:** NeoForge 1.21.1 Javadoc de `EntityJoinLevelEvent` e `ArrowLooseEvent`, além de construtores de `Arrow` do Minecraft 1.21.1.
