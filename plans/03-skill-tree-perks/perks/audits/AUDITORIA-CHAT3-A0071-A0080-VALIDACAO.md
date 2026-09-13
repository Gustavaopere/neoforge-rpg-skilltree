# AUDITORIA CHAT 3 — A0071–A0080 — VALIDAÇÃO, PENDÊNCIAS E MERGE

## Escopo

- **Lote exato:** A0071–A0080.
- **PR:** #355.
- **Branch:** `feat/chat2-a0071-a0080-implementation`.
- **Minecraft:** 1.21.1.
- **Loader:** NeoForge.
- **Java:** 21.
- **HEAD funcional validado:** `201f038e2f145806f7111ef7d8376ba22769aad0`.
- Nenhuma perk A0081+ foi iniciada ou alterada por este ciclo do Chat 3.

## Preflight obrigatório

A modlist fresca de 2026-09-07 e o Notion foram rechecados antes do fechamento. O lote continua compatível com os providers/version gates usados pelo runtime: Epic Fight `21.17.3.1`, ParCool `4.0.0.3`, Epic ParCool `21.0.0`, Create `6.0.10` e Cold Sweat `2.4.2`. A expansão Simply Swords permanece provider-native e não redefine os estados das perks deste lote.

## Resultado por perk

| Código | Resultado Chat 3 | Evidência / disposição |
|---|---|---|
| A0071 | **IMPLEMENTAÇÃO CONFIRMADA NO CLASSIFICADOR CANÔNICO** | markers Apothic/captured key + BOSS>ELITE>HOSTILE exercitados; providers externos desconhecidos continuam fail-closed. |
| A0072 | **NÃO JOGÁVEL / FAIL-CLOSED CONFIRMADO** | availability transitiva de A0067 aplicada no servidor; hook POST permanece latente correto. |
| A0073 | **IMPLEMENTAÇÃO CONFIRMADA** | opener/finisher reservation→POST commit, rollback zero/cancel, root causal por projectile; Stamina refund = 0 sem receipt exato, conforme contrato. |
| A0074 | **IMPLEMENTAÇÃO CONFIRMADA** | opener, histórico, finisher e cooldown commitados apenas após dano positivo; rollback preserva estado. |
| A0075 | **NÃO JOGÁVEL / FAIL-CLOSED CONFIRMADO** | node indisponível; nenhum benefício parcial sem Stamina regen + thermal Cold Sweat + exhaustion. |
| A0076 | **IMPLEMENTAÇÃO CONFIRMADA** | keybind `Alternar Postura Marcial`, payload de intenção e validação server-authoritative; cooldown/exclusividade/reconcile preservados. |
| A0077 | **NÃO JOGÁVEL / FAIL-CLOSED CONFIRMADO** | infraestrutura CAUTIOUS existe, mas A0067 indisponível mantém A0077 mascarada. |
| A0078 | **IMPLEMENTAÇÃO CONFIRMADA NO FALLBACK CANÔNICO** | sprint vanilla server-side; ParCool/Epic ParCool extras permanecem fail-closed sem receipt real. |
| A0079 | **IMPLEMENTAÇÃO CONFIRMADA NOS BINDINGS AUDITADOS** | teleport/knockback/passenger + Create 6.0.10/Sable 2.0.5; versão desconhecida/linkage failure invalida fail-closed. |
| A0080 | **NÃO JOGÁVEL / FAIL-CLOSED CONFIRMADO** | node mascarado sem dodge-success receipt; consumer latente foi corrigido para reservation→POST commit. |

## Correções técnicas do Chat 3

### Reservation → commit A0073/A0074/A0080

O PRE não realiza mais transições irreversíveis. `A0061A0080CombatState` possui reservas específicas de execução, primeiro sangue e oportunidade; o POST positivo é o único boundary que commita arm/consume/cooldown. Cancelamento ou dano zero faz rollback.

Para projéteis, `A0073A0080ProjectileCommitEvents` associa `PendingHitReceipt` ao par arrow+target e carrega o `rootActionId` exato. O finding P1 da PR #355, no qual um projétil concorrente podia consumir a reserva de outro, foi corrigido e o thread foi resolvido.

### A0076 — authority da postura

`MartialStanceIntentPayload` carrega somente uma intenção booleana de ciclo. `MartialStanceRuntime` executa no servidor, lê ranks efetivos, availability, stance atual e cooldown e decide a próxima transição. Cliente não envia rank, stance final, dano ou resistência.

### A0079 — movimento forçado

`StationaryStateService` permanece detector único. Teleport, knockback e passenger invalidam a preparação. `A0079ForcedMovementCompat` usa gates exatos para Create `6.0.10` e Sable `2.0.5`; versão diferente, falha de linkage ou exceção de integração resulta em invalidação, não em falso stationary.

### A0071 — classificação de elites

Foi acrescentada cobertura NeoForge-loaded direta para fallback HOSTILE, `apoth.miniboss`, captured elite key e precedência `apoth.boss` como BOSS. Nenhuma heurística de HP/nome/tamanho/equipamento foi adicionada.

## Fail-closed preservado

- A0072 e A0077 herdam A0067 indisponível.
- A0075 permanece indisponível até existirem simultaneamente os três bindings do contrato all-or-nothing.
- A0080 permanece indisponível até receipt server-authoritative de ataque hostil realmente evitado.
- Extensões ParCool/Epic ParCool de A0078 e providers externos de A0071/A0079 não são inferidas.
- Ausência de receipt de Stamina para A0073 produz refund zero; não há geração sintética de recurso.

## Testes e CI

### HEAD funcional `201f038e...`

`RPG Skill Tree CI` #4079 / run `34081344643`: **SUCCESS**.

Passaram, entre outros:

- JUnit 5;
- NeoForge JUnit adapter tests;
- NeoForge GameTests;
- Battle Mage provider-present GameTests;
- validações de dados/runtime/provider bindings;
- NeoForge build;
- verificação do JAR;
- dedicated-server smoke.

`SonarQube Cloud` / run `34081344663`: **QUALITY GATE PASSED**:

- **80.2% Coverage on New Code**;
- **0.0% Duplication on New Code**;
- **0 Security Hotspots**;
- **0 New issues**.

A cobertura foi recuperada com testes comportamentais reais de A0071/A0079 e sem reduzir threshold ou excluir código novo. CodeQL e os workflows auxiliares do mesmo HEAD também concluíram com sucesso.

## Pendências residuais não bloqueantes

1. A0071: novos providers de elite exigem identidade/marker/tag exato e versionado.
2. A0073: Stamina refund só poderá ser diferente de zero quando existir receipt pós-consumo causal exato; até lá, zero é o fallback aprovado.
3. A0075: boundary Cold Sweat metabólico + transação all-or-nothing permanece requisito para futuro desbloqueio.
4. A0077: depende do futuro desbloqueio legítimo de A0067.
5. A0078: ParCool/Epic ParCool extras exigem receipt real de locomoção autopropelida.
6. A0079: novos transports/providers exigem adapter explícito; desconhecidos falham fechado.
7. A0080: producer de dodge-success com `avoidedAttackId` continua ausente; node permanece indisponível.

Nenhuma dessas pendências requer redesign para manter o estado atual seguro. Nenhuma autoriza bônus substituto.

## Redesign / retorno ao Chat 1

**Nenhum.** As correções preservaram identidade, gates, topologia, provider authority, dependências e semântica aprovados.

## Gate de merge

Após este fechamento documental, o HEAD documental deve receber CI/Sonar verde fresco. Somente então a PR #355 pode ser mergeada na `main`. Após o merge, confirmar a SHA da `main` e encerrar o ciclo sem iniciar A0081+.
