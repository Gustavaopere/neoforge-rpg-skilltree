# I0002 — Constituição

- **ID runtime:** `AttributeId.CONSTITUTION`
- **Família:** Internal Attribute / Ponto 0
- **Status:** `CONCEPT-REVIEWED / TECH-PENDING`
- **Escopo:** global; rank investido sem limite canônico
- **Identidade:** saúde corporal e resistência a desgaste
- **Fonte do rank:** RPG Core `AttributeRanks` / ledger de Core Points
- **Custo de rank:** `AttributeRankCostPolicy` (proposta no contrato; não implementada aqui)

## Progressão proposta

Todos os bônus começam em rank 0 com zero; cada marco é cumulativo, mas cada marco posterior vale menos que o anterior. Fórmula completa em [`INVESTMENT-BALANCE-V0.1.md`](./INVESTMENT-BALANCE-V0.1.md).

| Estatística | Primeiro ganho | A cada | Fator q | Limite teórico | Rank 30 | Rank 100 |
|---|---:|---:|---:|---:|---:|---:|
| Vida máxima | 0,5 HP | 4 ranks | 0,96 | 12,5 HP | 3,107 HP | 7,995 HP |
| Curas recebidas | 0,4% | 8 ranks | 0,96 | 10% | 1,153% | 3,873% |
| Mitigação de dano ao longo do tempo | 0,4% | 12 ranks | 0,96 | 10% | 0,784% | 2,786% |

## Contrato de cada trilha

### 1. Vida máxima

+0,5 HP no primeiro marco; 2 HP correspondem a um coração. Recalcular vida atual com política de preservação de proporção ao equipar/remover bonus.

**Boundary e evidência pendente:** minecraft:max_health; reconciliar com cura/vida atual.

### 2. Curas recebidas

Aumentar apenas cura efetivamente recebida; não aplicar a overheal nem criar cura em ticks sem evento.

**Boundary e evidência pendente:** Pipeline único de healing (vanilla/mods), com causalidade sem dupla aplicação.

### 3. Mitigação de dano ao longo do tempo

Atenuar dano periódico de condições nocivas classificadas (ex.: veneno/wither), sem resistir a todo dano ambiental.

**Boundary e evidência pendente:** Bridge de damage types por tick; excluir void, queda, buffs, temperatura e hazards sem auditoria.

## Fronteiras e sinergias

- **Não deve fazer:** Não gera regeneração automática gratuita, imunidade a dano ou segunda barra de vida.
- **Sinergias pretendidas:** VITALITY, SURVIVAL, HEALING; sinergia com cura e defesa, sem substituir shields e mitigação especial.
- **Investimento como gate:** perks P/S/T podem pedir `CONSTITUTION >= N` sem consumir rank na compra; os valores N serão balanceados no catálogo e validados por `NodeAccessResolver`.
- **Arquitetura:** calculado somente a partir do rank canônico; nunca acumular bonus por login/cast/tick e nunca duplicar o adapter nativo com um segundo buff de mesmo efeito.
- **Versionamento:** se houver provider externo, consultar `PROJECT-INSTRUCTIONS/modlist` e source/API/JAR **exatamente da versão instalada** antes de marcar `TECH-AUDITED`. Ausência de hook seguro significa fail-closed.

## Critérios específicos de validação

- Conferir rank 0, primeiro marco, rank 30, 100, 300, 1000 e rank extremo sem overflow.
- Verificar que o limite teórico desta trilha não pode ser ultrapassado apenas pelo investimento em Constituição.
- Testar refresh idempotente, respec e remoção do modifier; reiniciar servidor sem drift.
- Testar stacking com equipamento e outras perks em um único pipeline real.

**Pendência:** a presente ficha aprova a **proposta de design**, não afirma existência de runtime, hooks ou balanceamento final.


## Estado técnico da implementação — 14/10/2026

**Custo em uso:** `UnitAttributeRankCostPolicy.INSTANCE`, 1 Core Progression Point por rank; a proposta crescente de 50 ranks **não foi ativada**. Ranks e refunds persistem via `CoreProgressionState.attributeRanks()` e ledger Core. Todos os três ganhos seguem `a × (1−q^floor(rank/k))/(1−q)`, com valores/intervalos da tabela de design, sempre recalculados. Esses limites restringem a contribuição da trilha, não a estatística global.

| Trilha | Situação da integração |
|---|---|
| Vida máxima | IMPLEMENTADO (validação pendente) — Minecraft 1.21.1 `minecraft:generic.max_health`; modificador `rpgskilltree:investment/constitution_health`, ADD_VALUE; preservação proporcional |
| Curas recebidas | IMPLEMENTADO NO CÓDIGO / CI PENDENTE — `LivingHealEvent` NeoForge 1.21.1 em `InvestedConstitutionHealingEvents`; apenas paciente `ServerPlayer`, coeficiente de `AttributeInvestmentHealingPolicy`; modifica o montante **do evento existente**, não chama `heal()` nem cria tick próprio |
| Mitigação de dano ao longo do tempo | IMPLEMENTADO NO CÓDIGO — **parcial e vanilla**: `minecraft:on_fire` e `minecraft:wither` sem entidade causadora. **Poison/magic, lava, contato com fogo e danos de addons continuam excluídos** por ambiguidade ou domínio externo; GameTests e CI novos pendentes |

A implementação vanilla usa `AttributeInvestmentBonuses` e `InvestedAttributeEffectsRuntime.refresh`, calculada pelo servidor a partir dos ranks, com IDs transitórios removíveis. Benefícios de providers não verificados continuam inativos (*fail-closed*). Não houve teste físico do modpack; JUnit/GameTests foram adicionados para verificação em CI. Uma integração marcada IMPLEMENTADO **ainda não é VALIDADO** antes de resultados dos testes de runtime.


### Boundary técnico — DoT vanilla, 15ª integração (14/10/2026)

`InvestedConstitutionDotEvents` atua exclusivamente em `LivingIncomingDamageEvent` do NeoForge 1.21.1 contra `ServerPlayer`, com `DamageSource` vanilla sem entidade causadora nem direta e com `DamageTypes.ON_FIRE` ou `DamageTypes.WITHER`. Essa allowlist representa **somente** dano de queimadura persistente e Wither nativos. Poison vanilla usa um tipo compartilhado com fontes mágicas não periódicas e permanece deliberadamente fora do boundary; também não há resistência a fogo no primeiro contato (`IN_FIRE`), lava, quedas, void, hazards térmicos/modded ou DoT de spells.

O hook multiplica `LivingIncomingDamageEvent.getAmount()` por `1 - AttributeInvestmentBonuses.CONSTITUTION_DAMAGE_OVER_TIME` uma vez por objeto de evento (controle fraco/transitório), **antes** das reduções nativas. Não cria outro evento de dano, não altera vida diretamente, não salva um estado adicional, não afeta alvos não jogadores e não altera o cooldown de dano. Primeiro ganho no rank 12: mitigação de 0,4%; teto teórico de redução de 10% da contribuição; rank persistido exclusivamente no Core.

`ConstitutionDotInvestmentGameTests` exerce `DamageContainer` e `LivingIncomingDamageEvent` nativos para ranks 0/11/12/30/300/`Long.MAX_VALUE`, repetição do mesmo evento, fontes ambientais e magia excluídas, cancelamento, respec e ausência de Core. O teste de evento não substitui QA da aplicação de efeitos periódicos em entidades reais no modpack; classificar como **IMPLEMENTADO / CI NOVO PENDENTE**, não certificado cross-mod. Fonte de semântica: `NeoForge/1.21.1`, `LivingIncomingDamageEvent` e `DamageContainer` (estágio anterior à mitigação).
