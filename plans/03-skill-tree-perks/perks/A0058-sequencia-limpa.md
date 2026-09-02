# A0058 — Sequência Limpa

## Estado

- **Design:** APROVADO após boundary retroativo e correção de lifecycle.
- **Notion:** `3c569db9-f0db-81cd-bd48-c3ed8f11ef76`.
- **Runtime:** **CÓDIGO PRESENTE NO FALLBACK CANÔNICO / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**. Hits/miss/troca/lifecycle estão implementados; reset por impacto pesado recebido e modulação corporal permanecem omitidos enquanto não houver receipt/config segura.

## Contrato canônico

- A0057 ≥2 + gateway `combat_fist`.
- Hits FIST diretos consecutivos em 2/2,5 s geram Sequência, cap 5.
- Miss confirmado, troca para arma não elegível ou **impacto pesado recebido com receipt provider-native** encerra Sequência.
- Alvo de treino/invulnerável, fake player, summon, proc/follow-up e callback duplicado não contam.
- Exhaustion/fome pode penalizar apenas manutenção quando houver configuração real; Stamina é recurso distinto.
- Sequência é transiente e condicionada à posse válida do ramo: rank loss, respec ou rules reload que invalide A0058/A0057/gateway deve zerar cargas/janela antes de futura recompra.

## Evidência runtime

O adapter adiciona Sequência somente no `DELIVER_DAMAGE_POST` confirmado, encerra em `ATTACK_PHASE_END` sem alvo e reseta ao trocar para arma não-FIST. O state usa `claimOnce(actor, root, "A0058:gain")`, janela rank-aware e cap 5, de modo que callback duplicado do mesmo root não duplica carga.

O Chat 2 acrescentou `reconcileForRanks(...)` ao owner transiente: se A0058/A0057 deixam de ser efetivamente válidas, Sequência, timestamp/janela e cooldown específico de A0060 são limpos. Logout, dimensão, respawn e server stop continuam limpando o actor inteiro.

Não existe receipt server-authoritative inequívoco de **impacto pesado recebido** no bridge auditado; esse reset continua fail-closed em vez de ser inferido por dano alto, velocidade ou animação. A modulação exhaustion/fome também permanece omitida, conforme fallback aprovado.

## Pendências para Chat 2

- **P-A0058-01 — PENDÊNCIA PROVIDER:** integrar heavy-impact receipt real apenas quando o provider o expuser; no estado atual a parcela permanece fail-closed.
- **P-A0058-02 — OPCIONAL/OMITIDA:** exhaustion/fome só entra com configuração real; nenhum proxy foi criado.
- **RESOLVIDA P-A0058-03:** rank loss/respec/rules reload limpa Sequência/janela; recompra inicia em zero.
- **RESOLVIDAS herdadas de A0055:** ledger/gateway FIST já estão reconciliados.

## Implementação Chat 2 — PR #386

- [x] Gain pós-hit confirmado implementado.
- [x] Deduplicação por root implementada.
- [x] Reset por miss confirmado/troca não-FIST presente.
- [x] Lifecycle rank/respec/rules reload implementado.
- [x] Fallback sem heurística de heavy impact preservado.
- [x] Fallback sem proxy de exhaustion/Stamina preservado.
- [x] Código presente.
- [ ] **PENDÊNCIA PROVIDER:** heavy-impact recebido inequívoco.
- [ ] **VALIDAÇÃO CHAT 3:** sequência, timeout, miss, switch, duplicate callback e reconciliation.
- [ ] **VALIDAÇÃO CHAT 3:** GameTests/build/dedicated-server/CI de fechamento.
- [ ] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA.

## Boundaries

Backlash, Shroud/Exposure, hazards Volcanoes e companion-owned damage não são receipts de heavy impact nem produtores de Sequência.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS no design/código** | A0057 ≥2 + `combat_fist`; perda de rank/gateway invalida e limpa o recurso, sem carry-over. |
| 2. Integração global | **PASS** | Sequência é recurso próprio; heavy impact só por receipt provider-native; exhaustion/fome opcional não substitui Stamina; magia/hazards/companions ficam fora. |
| 3. Qualidade e identidade | **PASS** | Notable de execução contínua, cria ritmo/risco e condição de manutenção; não é percentual passivo genérico. |
| 4. Ramificação, distância e topologia | **PASS** | Camada 3 após A0057 no ramo Combinação/Ritmo; infraestrutura FIST materializada. |
| 5. Especializações | **PASS** | Progressão MARTIAL/ARMAS_DE_PUNHO; não invade recursos de outra especialização e não transforma Punchy/Epic Fight em classe. |
| 6. PT-BR | **PASS** | Nome, efeito e condições em PT-BR; termos técnicos ficam restritos ao dossiê. |
| 7. Notion completo | **PASS** | Hook/Fallback/Regra completos; heavy boundary e lifecycle rank/respec/rules reload persistidos e re-fetched. |
| 8. NeoVitae | **PASS** | Ausente. |
| 9. Cobertura modlist/providers | **PASS** | Epic Fight/Minecraft/RPG, WoM/Punchy quando pertinentes e own-projects/Mobstein foram dispostos; não há provider falso de heavy impact. |

Os critérios técnicos cumulativos passam no design; código presente com fallback canônico explícito e validação final reservada ao Chat 3.

## Notion

Hook/Fallback/Regra foram corrigidos inicialmente; após segundo review da PR #249 receberam lifecycle obrigatório de rank loss/respec/rules reload. Re-fetch pós-review PASS em 2026-08-30.