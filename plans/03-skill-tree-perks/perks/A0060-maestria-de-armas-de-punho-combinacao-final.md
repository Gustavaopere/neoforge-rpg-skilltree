# A0060 — Maestria de Armas de Punho — Combinação Final

## Estado

- **Design:** APROVADO; fail-closed já estava correto e lifecycle foi explicitado após review.
- **Notion:** `3c569db9-f0db-813c-a90b-d92ed2f1ed75`.
- **Runtime:** **NÃO CONFIRMADA COMO JOGÁVEL / IMPLEMENTAÇÃO FAIL-CLOSED CONFIRMADA PELO CHAT 3**. Matemática, consumo de Sequência, cooldown e fallback de Stamina existem; o adapter permanece inerte sem heavy/finalizer receipt inequívoco.

## Contrato canônico

- A0058 ≥2 + A0059 ≥1 + `combat:fist` ≥80 + gateway `combat_fist`.
- Em 5 Sequências, o próximo heavy/finalizer FIST confirmado consome todas as cargas e recebe +18% dano físico elegível e +25% Impact.
- Se acertar alvo hostil válido, pode recuperar 15% da soma de Stamina **realmente debitada** nas cinco ações que geraram a sequência, somente por receipts causais pós-consumo.
- Sem receipt de Stamina, omitir só a restituição; nunca estimar por barra, config, hunger/exhaustion ou animation timing.
- Cooldown 8/7/6 s para Mastery 80/90/100.
- Rank loss/respec/rules reload que invalide A0060 limpa cooldown/reserva específicos do capstone; Sequência pertence a A0058 e segue o lifecycle/reconciliação daquele owner.

## Evidência runtime

`beforeFistHeavy(...)` possui matemática de A0060, consumo de 5 Sequências, +18% dano, +25% Impact quando semanticamente disponível e cooldown 8/7/6 s. O refund retorna `0.0` por design enquanto não existir ledger causal pós-consumo de Stamina.

O adapter Epic Fight não chama essa rota sem heavy/finalizer receipt inequívoco, portanto o capstone não consome Sequência/cooldown por heurística. A reconciliation limpa Sequência/cooldown quando A0058/A0057 deixam de ser válidos e limpa `finalCombinationCooldownUntil` quando A0060 deixa de ser aprendido.

O producer `combat:fist` existe com discovery finita e ledger única. A modlist fresca do fechamento mantém Epic Fight em `21.17.3.1`; Punchy `2.7e` continua visual/compat e não constitui receipt de heavy/finalizer nem Stamina.

## Pendências técnicas

- **P-A0060-01 — PENDÊNCIA PROVIDER NÃO BLOQUEANTE PARA O MERGE:** integrar heavy/finalizer receipt provider-native e liberar o capstone apenas então.
- **P-A0060-02 — PENDÊNCIA PROVIDER NÃO BLOQUEANTE PARA O MERGE:** manter restituição de Stamina fail-closed até existir ledger causal pós-consumo por cada uma das cinco ações; cada receipt só pode ser reclamado uma vez.
- **RESOLVIDA P-A0060-03:** A0060 usa a ledger única `combat:fist`; producer/architecture de A0055 estão fechados.
- **RESOLVIDA P-A0060-04:** cooldown terminal é limpo por reconciliation em rank loss/respec/rules reload; nenhuma reserva específica é criada enquanto o adapter está fail-closed.

## Implementação e validação

- [x] Design aprovado pelo Chat 1.
- [x] Código presente pelo Chat 2.
- [x] Contrato revisado contra o código pelo Chat 3.
- [x] Policy do capstone presente.
- [x] Gate Mastery `combat:fist` 80 alcançável.
- [x] Cooldown 8/7/6 implementado e lifecycle reconciliado.
- [x] Fallback de Stamina em zero preservado, sem estimativa.
- [x] Adapter fail-closed sem heurística de heavy/finalizer.
- [x] **VALIDAÇÃO CHAT 3:** JUnit/core e regressões de lifecycle do lote verdes.
- [x] **VALIDAÇÃO CHAT 3:** NeoForge JUnit adapter e GameTests verdes.
- [x] **VALIDAÇÃO CHAT 3:** build NeoForge e dedicated-server smoke verdes.
- [x] **VALIDAÇÃO CHAT 3:** CI funcional GREEN em `6826c50896c4ad586b8942031465b6a0a3ce44af` (run `34006356029`).
- [x] **IMPLEMENTAÇÃO CONFIRMADA DO COMPORTAMENTO FAIL-CLOSED ATUAL**.
- [ ] **IMPLEMENTAÇÃO CONFIRMADA COMO JOGÁVEL:** depende de heavy/finalizer receipt e, para o refund, ledger causal real de Stamina.

## Boundaries

Backlash, procs, summons/companions e hazards não geram Sequência, heavy receipt ou Stamina ledger. Punchy é visual/compat e não pode ser usado como authority causal.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS no design/runtime estrutural** | A0058 ≥2 + A0059 ≥1 + `combat:fist` 80 + gateway; sem heavy receipt o capstone não ativa/consome. |
| 2. Integração global | **PASS** | Stamina só poderá usar receipts pós-consumo reais; hunger/exhaustion/mana não substituem. |
| 3. Qualidade e identidade | **PASS** | Capstone de Sequência máxima + finalizador confirmado. |
| 4. Ramificação, distância e topologia | **PASS** | Camada 4 terminal com A0058/A0059 + Mastery 80. |
| 5. Especializações | **PASS** | `TERMINAL_EXTERIOR: MARTIAL/ARMAS_DE_PUNHO`. |
| 6. PT-BR | **PASS** | Player-facing em PT-BR. |
| 7. Notion completo | **PASS** | Lifecycle/fallback registrados e persistidos. |
| 8. NeoVitae | **PASS** | Ausente. |
| 9. Cobertura modlist/providers | **PASS** | Epic Fight/RPG são authorities pertinentes; Punchy/own-projects/Mobstein não foram promovidos artificialmente a heavy/Stamina provider. |

## Notion

O contrato de fail-closed/lifecycle já estava persistido e não exigiu redesign ou mutação funcional pelo Chat 3.

## Fechamento Chat 3 — PR #387

O comportamento aplicável foi validado no HEAD funcional `6826c50896c4ad586b8942031465b6a0a3ce44af`, cuja bateria passou JUnit 5, NeoForge JUnit adapter, NeoForge GameTests, build, verificação do JAR e dedicated-server smoke. A0060 permanece **NÃO CONFIRMADA COMO JOGÁVEL**, mas seu **FAIL-CLOSED ATUAL ESTÁ IMPLEMENTADO E CONFIRMADO**. Os receipts futuros de heavy/finalizer e Stamina continuam pendências explícitas de provider, sem heurística substituta e sem bloquear o merge seguro atual.