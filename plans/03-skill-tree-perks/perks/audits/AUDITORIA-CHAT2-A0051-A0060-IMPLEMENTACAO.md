# Auditoria Chat 2 — Implementação A0051–A0060

**INÍCIO:** A0051  
**FIM:** A0060  
**Lote:** exatamente 10 perks consecutivas  
**Função:** Chat 2 — implementação, sem redesign, sem validação final, sem merge.  
**Branch:** `feat/chat2-a0051-a0060-stacked-handoff`  
**PR:** #386  
**Base lógica:** HEAD do lote predecessor A0041–A0050 (`2fcbb802ed1f02b8cea6acf938ce8de7c6ddea9a`).

## Anomalia de continuidade

O Chat 1 fechou o design A0051–A0060 na PR #249, porém essa PR documental já havia sido mergeada/encerrada. Não era possível continuar a mesma PR. O Chat 2 criou uma PR substituta empilhada sobre o HEAD real do lote predecessor A0041–A0050, preservando a cadeia de código. Nenhum merge é feito nesta etapa.

## Fontes e estado de entrada

- Critérios obrigatórios anexados ao projeto: lidos integralmente.
- Protocolo Chat 2 anexado ao projeto: lido integralmente.
- Modlist atual: reconciliada no File Library/Notion antes da implementação.
- Dossiês A0051–A0060 e `AUDITORIA-RETROATIVA-PROVIDERS-A0051-A0060.md`: lidos integralmente.
- A0050 já estava `UNAVAILABLE_NODE` por ausência de reload/preparation-speed semântico.
- A0049 já possuía producer finite-discovery CROSSBOW e ledger `epicfight:crossbow` na linha predecessora.
- `combat:fist` e gateway `combat_fist` já estavam materializados pela infraestrutura mergeada/linha predecessora, removendo os antigos blockers de A0055.

## Implementação por perk

| Perk | Estado Chat 2 | Implementação / decisão |
|---|---|---|
| A0051 | CÓDIGO PRESENTE | A parcela crítica A0051 só é aplicada a projectile CROSSBOW com `PendingLaunch.launchConfirmed`; owner + metadata sem launch receipt não recebem o bônus A0051. Resolver crítico canônico e uma resolução/root preservados. |
| A0052 | CÓDIGO PRESENTE EM FAIL-CLOSED | `UNAVAILABLE_NODE` propagado de A0050. Hit receipt passou a carregar identidade estável da mesma besta; troca limpa receipt; hit exige launch confirmado; lifecycle de Cadência/receipts reconciliado. Outcome Multishot `success-wins` ainda é pendência técnica antes de futura habilitação. |
| A0053 | CÓDIGO PRESENTE EM FAIL-CLOSED | `UNAVAILABLE_NODE` herdado. Consumo de 2 Cadências virou reservation→commit: `ArrowLoose` reserva e `EntityJoinLevelEvent` correlacionado commita; ausência de spawn/expiry/rank-loss libera reserva. |
| A0054 | CÓDIGO PRESENTE EM FAIL-CLOSED | `UNAVAILABLE_NODE` herdado. Armar janela não consome 3 Cadências; lançamento reserva; projectile correlacionado commita e consome. Reload-speed extra permanece omitido sem hook semântico. |
| A0055 | CÓDIGO PRESENTE | FIST/knuckle provider-native usa Mastery única `combat:fist` e gateway `combat_fist` já disponíveis na linha predecessora; dano A0055 não cria classificador paralelo. |
| A0056 | CÓDIGO PRESENTE | Attack speed via `ModifyAttackSpeedEvent` somente para FIST/knuckle provider-native; nenhum fallback para dano/movimento/Stamina. |
| A0057 | CÓDIGO PRESENTE | Bônus crítico FIST continua no resolver crítico canônico único, com categoria provider-native e root correlation. |
| A0058 | CÓDIGO PRESENTE NO FALLBACK CANÔNICO | Gain pós-hit, miss confirmado, troca e dedup por root presentes. Reconciliation limpa Sequência/janela em rank loss/respec/rules reload. Heavy-impact recebido e body modulation permanecem omitidos sem provider/config segura. |
| A0059 | CÓDIGO PRESENTE EM FAIL-CLOSED | Policy matemática existe, mas adapter permanece deliberadamente inerte sem heavy/finalizer receipt inequívoco. Guard-break/movement penalty também não são inventados. |
| A0060 | CÓDIGO PRESENTE EM FAIL-CLOSED | Policy, consumo de Sequência, cooldown 8/7/6 e fallback de Stamina em zero existem. Adapter inerte sem heavy/finalizer receipt; cooldown limpa em reconciliation; producer `combat:fist` 80 já alcançável por discovery finita. |

## Hardening implementado

### CROSSBOW

1. `CombatPerkAvailabilityRuntime` propaga `A0050 → A0052 → A0053 → A0054` como indisponível.
2. `A0041A0060CombatState` passou a manter hit receipt com `weaponId` e root.
3. Troca/remoção da besta limpa receipt causal pendente.
4. Projectile sem launch receipt confirmado não cria hit receipt de A0052 e não recebe a parcela crítica de A0051.
5. A0053/A0054 usam reservation→commit bounded em vez de consumo em `ArrowLooseEvent`.
6. TTL/reconciliation limpam reservas não commitadas.
7. Falha CROSSBOW possui claim por root para impedir perdas duplicadas triviais.

### FIST

1. Infraestrutura existente `combat:fist`/`combat_fist` foi consumida; nenhuma Mastery paralela foi criada.
2. `A0041A0060RuntimeState.ranks(...)` aplica `effectiveRanks(...)` e reconcilia state transiente.
3. Perda de A0058/A0057 limpa Sequência/janela e cooldown terminal.
4. Perda de A0060 limpa cooldown específico do capstone.
5. Heavy/finalizer, heavy-impact recebido, guard-break e Stamina refund permanecem fail-closed quando não existe receipt seguro.

## TDD focal de desenvolvimento

Foi criado `A0051A0060Chat2ContractJUnitTest` antes das mudanças de produção para capturar:

- indisponibilidade transitiva A0050→A0052/A0053/A0054;
- correlação da mesma besta;
- reservation→commit de A0053;
- reservation→commit de A0054;
- reconciliation de lifecycle.

`A0041A0060CombatPolicyTest` também foi atualizado para a nova semântica transacional.

Esses testes são evidência de desenvolvimento e **não substituem a bateria final do Chat 3**. O Chat 2 não promove nenhuma perk a `IMPLEMENTAÇÃO CONFIRMADA` com base neles.

## Pendências técnicas para o Chat 3

### Não exigem redesign

- **A0052 / P-A0052-04:** completar/validar outcome agregado de Multishot com regra `success-wins` antes de qualquer futura habilitação de A0052. Como A0050 mantém A0052 `UNAVAILABLE_NODE`, não há exploit ativo no estado atual.
- Validar cancelamento tardio/no-spawn/expiry de reservations A0053/A0054.
- Validar troca/clonagem de besta e mesma identidade causal de reload.
- Validar lifecycle de Cadência/Sequência/cooldown em respec/rules reload.
- Validar uma única resolução crítica/root em A0051/A0057.

### Dependem de provider real e permanecem fail-closed

- **A0050/A0052–A0054:** reload/preparation-speed semântico.
- **A0058:** receipt inequívoco de impacto pesado recebido; body modulation só com config real.
- **A0059:** heavy/finalizer receipt; guard-break causal + penalty de movimento.
- **A0060:** heavy/finalizer receipt; ledger causal pós-consumo de Stamina das cinco ações.

Nenhuma dessas ausências foi substituída por bônus genérico, heurística de dano/animação, Hunger, Shroud, Backlash, projectile speed ou outro recurso paralelo.

## Pontos para retorno ao Chat 1

**Nenhum redesign novo identificado.** As limitações atuais já estão previstas pelos dossiês aprovados como fallback/fail-closed. Caso um provider futuro exponha API cuja semântica diverja do contrato aprovado, o ponto deverá voltar ao Chat 1 antes de alterar identidade, gate, provider, topologia ou efeito.

## Validações reservadas ao Chat 3

Permanecem pendentes como fechamento formal:

- unit/JUnit completo;
- GameTests;
- integração provider-present/absent;
- deduplicação/idempotência em runtime real;
- build NeoForge;
- dedicated-server smoke;
- CI GREEN de fechamento;
- revisão final do diff;
- `IMPLEMENTAÇÃO CONFIRMADA`;
- merge e confirmação de `main`.

## Estado de saída do Chat 2

**A0051–A0060: CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**, com os estados component-wise fail-closed explicitados acima.

O Chat 2 para neste lote e não inicia A0061.