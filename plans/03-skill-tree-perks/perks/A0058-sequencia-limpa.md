# A0058 — Sequência Limpa

## Estado

- **Design:** APROVADO após boundary retroativo e correção de lifecycle.
- **Notion:** `3c569db9-f0db-81cd-bd48-c3ed8f11ef76`.
- **Runtime:** **IMPLEMENTAÇÃO CONFIRMADA NOS COMPONENTES COM RECEIPT REAL / FALLBACK CANÔNICO**. Hits/miss/troca/lifecycle foram validados; reset por impacto pesado recebido e modulação corporal permanecem omitidos enquanto não houver receipt/config segura, exatamente como previsto no fallback aprovado.

## Contrato canônico

- A0057 ≥2 + gateway `combat_fist`.
- Hits FIST diretos consecutivos em 2/2,5 s geram Sequência, cap 5.
- Miss confirmado, troca para arma não elegível ou **impacto pesado recebido com receipt provider-native** encerra Sequência.
- Alvo de treino/invulnerável, fake player, summon, proc/follow-up e callback duplicado não contam.
- Exhaustion/fome pode penalizar apenas manutenção quando houver configuração real; Stamina é recurso distinto.
- Sequência é transiente e condicionada à posse válida do ramo: rank loss, respec ou rules reload que invalide A0058/A0057/gateway zera cargas/janela.

## Evidência runtime

O adapter adiciona Sequência somente no `DELIVER_DAMAGE_POST` confirmado, encerra em `ATTACK_PHASE_END` sem alvo e reseta ao trocar para arma não-FIST. O state usa `claimOnce(actor, root, "A0058:gain")`, janela rank-aware e cap 5, impedindo duplicação por callback do mesmo root.

`reconcileForRanks(...)` limpa Sequência, timestamp/janela e cooldown específico de A0060 quando A0058/A0057 deixam de ser efetivamente válidas. Logout, dimensão, respawn e server stop limpam o actor inteiro.

Não existe receipt server-authoritative inequívoco de **impacto pesado recebido** no bridge auditado; esse reset continua fail-closed em vez de ser inferido por dano alto, velocidade ou animação. A modulação exhaustion/fome também permanece omitida.

## Pendências técnicas

- **P-A0058-01 — PENDÊNCIA PROVIDER NÃO BLOQUEANTE:** integrar heavy-impact receipt real apenas quando o provider o expuser; parcela permanece fail-closed.
- **P-A0058-02 — OPCIONAL/OMITIDA:** exhaustion/fome só entra com configuração real; nenhum proxy foi criado.
- **RESOLVIDA P-A0058-03:** rank loss/respec/rules reload limpa Sequência/janela; recompra inicia em zero.
- **VALIDADA:** sequência, timeout, miss, switch, duplicate callback e reconciliation passaram pela bateria do lote.

## Implementação e validação

- [x] Gain pós-hit confirmado implementado.
- [x] Deduplicação por root implementada.
- [x] Reset por miss confirmado/troca não-FIST presente.
- [x] Lifecycle rank/respec/rules reload implementado.
- [x] Fallback sem heurística de heavy impact preservado.
- [x] Fallback sem proxy de exhaustion/Stamina preservado.
- [x] Código presente.
- [ ] **PENDÊNCIA PROVIDER:** heavy-impact recebido inequívoco.
- [x] **VALIDAÇÃO CHAT 3:** JUnit/core e regressões do estado transiente.
- [x] **VALIDAÇÃO CHAT 3:** NeoForge adapter/GameTests verdes.
- [x] **VALIDAÇÃO CHAT 3:** build NeoForge e dedicated-server smoke verdes.
- [x] **VALIDAÇÃO CHAT 3:** CI funcional GREEN em `6826c50896c4ad586b8942031465b6a0a3ce44af` (run `34006356029`).
- [x] **VALIDAÇÃO CHAT 3:** implementação confirmada nos componentes disponíveis; fallback provider ausente confirmado.

## Boundaries

Backlash, Shroud/Exposure, hazards Volcanoes e companion-owned damage não são receipts de heavy impact nem produtores de Sequência.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS** | A0057 ≥2 + `combat_fist`; perda de rank/gateway invalida e limpa o recurso. |
| 2. Integração global | **PASS** | Heavy impact só por receipt provider-native; exhaustion/fome opcional não substitui Stamina. |
| 3. Qualidade e identidade | **PASS** | Notable de execução contínua. |
| 4. Ramificação, distância e topologia | **PASS** | Camada 3 após A0057. |
| 5. Especializações | **PASS** | MARTIAL/ARMAS_DE_PUNHO. |
| 6. PT-BR | **PASS** | Player-facing em PT-BR. |
| 7. Notion completo | **PASS** | Hook/Fallback/Regra completos. |
| 8. NeoVitae | **PASS** | Ausente. |
| 9. Cobertura modlist/providers | **PASS** | Nenhum provider falso de heavy impact foi criado. |

## Notion

Hook/Fallback/Regra e lifecycle foram persistidos no fechamento de design; re-fetch pós-review PASS em 2026-08-30.

## Fechamento Chat 3 — PR #387

O contrato implementável foi revisado e validado no HEAD funcional `6826c50896c4ad586b8942031465b6a0a3ce44af`. A0058 fica **IMPLEMENTAÇÃO CONFIRMADA NOS COMPONENTES COM RECEIPT REAL / FALLBACK CANÔNICO**; a ausência de heavy-impact receipt é explícita, segura e não bloqueia o merge.