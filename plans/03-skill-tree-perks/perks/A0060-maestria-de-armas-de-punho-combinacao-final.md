# A0060 — Maestria de Armas de Punho — Combinação Final

## Estado

- **Design:** APROVADO; fail-closed já estava correto e lifecycle foi explicitado após review.
- **Notion:** `3c569db9-f0db-813c-a90b-d92ed2f1ed75`.
- **Runtime:** **CÓDIGO PRESENTE EM FAIL-CLOSED / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**. Matemática, consumo de Sequência, cooldown e fallback de Stamina existem; o adapter continua inerte sem heavy/finalizer receipt inequívoco.

## Contrato canônico

- A0058 ≥2 + A0059 ≥1 + `combat:fist` ≥80 + gateway `combat_fist`.
- Em 5 Sequências, o próximo heavy/finalizer FIST confirmado consome todas as cargas e recebe +18% dano físico elegível e +25% Impact.
- Se acertar alvo hostil válido, pode recuperar 15% da soma de Stamina **realmente debitada** nas cinco ações que geraram a sequência, somente por receipts causais pós-consumo.
- Sem receipt de Stamina, omitir só a restituição; nunca estimar por barra, config, hunger/exhaustion ou animation timing.
- Cooldown 8/7/6 s para Mastery 80/90/100.
- Rank loss/respec/rules reload que invalide A0060 limpa cooldown/reserva específicos do capstone; Sequência pertence a A0058 e segue o lifecycle/reconciliação daquele owner.

## Evidência runtime

`beforeFistHeavy(...)` possui matemática de A0060, consumo de 5 Sequências, +18% dano, +25% Impact quando semanticamente disponível e cooldown 8/7/6 s. O refund retorna `0.0` por design enquanto não existir ledger causal pós-consumo de Stamina.

O adapter Epic Fight não chama essa rota sem heavy/finalizer receipt inequívoco, portanto o capstone não consome Sequência/cooldown por heurística. O Chat 2 adicionou reconciliation: se A0058/A0057 deixam de ser válidos, Sequência e cooldown terminal são zerados; se A0060 deixa de ser aprendido, `finalCombinationCooldownUntil` também é limpo.

O producer `combat:fist` já existe na linha predecessora com discovery finita, permitindo atingir 80 por 8 tipos hostis inéditos sem criar `epicfight:fist` paralelo.

## Pendências para Chat 2

- **P-A0060-01 — PENDÊNCIA PROVIDER:** integrar heavy/finalizer receipt provider-native e liberar o capstone apenas então.
- **P-A0060-02 — PENDÊNCIA PROVIDER:** manter restituição de Stamina fail-closed até existir ledger causal pós-consumo por cada uma das cinco ações; cada receipt só pode ser reclamado uma vez.
- **RESOLVIDA P-A0060-03:** A0060 usa a ledger única `combat:fist`; producer/architecture de A0055 já foram fechados na linha predecessora.
- **RESOLVIDA P-A0060-04:** cooldown terminal é limpo por reconciliation em rank loss/respec/rules reload; nenhuma reserva específica é criada enquanto o adapter está fail-closed.

## Implementação Chat 2 — PR #386

- [x] Policy de capstone presente.
- [x] Gate Mastery `combat:fist` 80 alcançável na linha predecessora.
- [x] Cooldown 8/7/6 implementado.
- [x] Lifecycle do cooldown implementado.
- [x] Fallback de Stamina em zero preservado, sem estimativa.
- [x] Adapter fail-closed sem heurística de heavy/finalizer.
- [x] Código presente em fail-closed.
- [ ] **PENDÊNCIA PROVIDER:** heavy/finalizer receipt inequívoco.
- [ ] **PENDÊNCIA PROVIDER:** ledger causal de Stamina das cinco ações.
- [ ] **VALIDAÇÃO CHAT 3:** confirmar que sem receipt não há consumo/cooldown/refund.
- [ ] **VALIDAÇÃO CHAT 3:** cooldown/lifecycle/rank-loss.
- [ ] **VALIDAÇÃO CHAT 3:** build/GameTests/dedicated-server/CI de fechamento.
- [ ] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA somente com evidência real.

## Boundaries

Backlash, procs, summons/companions e hazards não geram Sequência, heavy receipt ou Stamina ledger. Punchy é visual/compat.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS no design/código estrutural** | A0058 ≥2 + A0059 ≥1 + `combat:fist` 80 + gateway; sem heavy receipt o capstone não ativa/consome. |
| 2. Integração global | **PASS** | Stamina permanece recurso real do Epic Fight e só usará receipts pós-consumo; hunger/exhaustion/mana não substituem; dano/Impact seguem pipelines canônicos. |
| 3. Qualidade e identidade | **PASS** | Capstone conclui a fantasia de combo: Sequência máxima + finalizador confirmado + dano/Impact e refund causal opcional; não é aumento numérico banal. |
| 4. Ramificação, distância e topologia | **PASS no design** | Camada 4 terminal com dependências convergentes A0058/A0059 e Mastery 80; posição é coerente com Capstone. |
| 5. Especializações | **PASS** | `TERMINAL_EXTERIOR: MARTIAL/ARMAS_DE_PUNHO`; só satisfaz Gate C por mapeamento explícito e não cria classe automática. |
| 6. PT-BR | **PASS** | Nome, efeito, requisitos e mensagens conceituais em PT-BR; IDs/API técnicos permanecem em inglês no dossiê. |
| 7. Notion completo | **PASS** | Campos pertinentes completos; lifecycle de cooldown/reserva foi adicionado após review e re-fetch confirmou persistência em 2026-08-30. |
| 8. NeoVitae | **PASS** | Nenhuma dependência residual. |
| 9. Cobertura modlist/providers | **PASS** | Epic Fight/RPG/WoM/Punchy e own-projects/Mobstein foram avaliados; sem provider seguro de heavy/Stamina receipt, componentes permanecem fail-closed. |

Os critérios técnicos cumulativos passam no design; código presente em fail-closed não equivale a implementação confirmada.

## Notion

Fetch fresco inicial sem drift. Após segundo review da PR #249, `Fallback` e `Regra` receberam lifecycle obrigatório de cooldown/reserva em rank loss/respec/rules reload; re-fetch pós-review PASS em 2026-08-30.