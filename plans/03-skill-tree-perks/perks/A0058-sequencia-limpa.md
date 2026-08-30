# A0058 — Sequência Limpa

## Estado

- **Design:** APROVADO após boundary retroativo e correção de lifecycle.
- **Notion:** `3c569db9-f0db-81cd-bd48-c3ed8f11ef76`.
- **Runtime:** IMPLEMENTAÇÃO PARCIAL.

## Contrato canônico

- A0057 ≥2 + gateway `combat_fist`.
- Hits FIST diretos consecutivos em 2/2,5 s geram Sequência, cap 5.
- Miss confirmado, troca para arma não elegível ou **impacto pesado recebido com receipt provider-native** encerra Sequência.
- Alvo de treino/invulnerável, fake player, summon, proc/follow-up e callback duplicado não contam.
- Exhaustion/fome pode penalizar apenas manutenção quando houver configuração real; Stamina é recurso distinto.
- Sequência é transiente e condicionada à posse válida do ramo: rank loss, respec ou rules reload que invalide A0058/A0057/gateway deve zerar cargas/janela antes de futura recompra.

## Evidência runtime

O adapter adiciona Sequência somente no `DELIVER_DAMAGE_POST` confirmado, encerra em `ATTACK_PHASE_END` sem alvo e reseta ao trocar para arma não-FIST. Entretanto não há, no bridge A0041–A0060 auditado, consumer de receipt hostil de impacto pesado recebido para encerrar Sequência. Também não existe modulação corporal exhaustion/fome; esta parcela pode permanecer omitida pelo fallback.

O segundo review da PR #249 identificou ainda que `A0041A0060CombatState` limpa actor state em logout/dimensão/respawn/server stop, mas não necessariamente quando ranks/pré-requisitos são reconciliados. Sem limpeza em respec/rules reload, Sequência antiga poderia reaparecer após recompra. O contrato foi endurecido para impedir carry-over.

## Pendências para Chat 2

- **P-A0058-01:** integrar receipt provider-native seguro de impacto pesado recebido e resetar Sequência uma única vez por outcome; não inferir por dano alto/velocidade/animação.
- **P-A0058-02:** opcional — integrar exhaustion/fome real apenas se configurado; ausência omite a penalidade sem proxy.
- **P-A0058-03:** limpar Sequência e janela temporal em rank loss, respec ou rules reload que invalide A0058/A0057/gateway; recompra inicia em zero.
- Herdadas de A0055: ledger/gateway FIST precisam ser reconciliados.

## Boundaries

Backlash, Shroud/Exposure, hazards Volcanoes e companion-owned damage não são receipts de heavy impact nem produtores de Sequência.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS no design** | A0057 ≥2 + `combat_fist`; perda de rank/gateway invalida e limpa o recurso, sem carry-over. |
| 2. Integração global | **PASS** | Sequência é recurso próprio; heavy impact só por receipt provider-native; exhaustion/fome opcional não substitui Stamina; magia/hazards/companions ficam fora. |
| 3. Qualidade e identidade | **PASS** | Notable de execução contínua, cria ritmo/risco e condição de manutenção; não é percentual passivo genérico. |
| 4. Ramificação, distância e topologia | **PASS no design** | Camada 3 após A0057 no ramo Combinação/Ritmo; depende da infraestrutura FIST sem teleporte topológico. |
| 5. Especializações | **PASS** | Progressão MARTIAL/ARMAS_DE_PUNHO; não invade recursos de outra especialização e não transforma Punchy/Epic Fight em classe. |
| 6. PT-BR | **PASS** | Nome, efeito e condições em PT-BR; termos técnicos ficam restritos ao dossiê. |
| 7. Notion completo | **PASS** | Hook/Fallback/Regra completos; heavy boundary e lifecycle rank/respec/rules reload persistidos e re-fetched. |
| 8. NeoVitae | **PASS** | Ausente. |
| 9. Cobertura modlist/providers | **PASS** | Epic Fight/Minecraft/RPG, WoM/Punchy quando pertinentes e own-projects/Mobstein foram dispostos; não há provider falso de heavy impact. |

Os 18 critérios técnicos cumulativos passam **no design**; heavy-impact e body modulation continuam fail-closed/optional e lifecycle virou blocker explícito de implementação.

## Notion

Hook/Fallback/Regra foram corrigidos inicialmente; após segundo review da PR #249 receberam lifecycle obrigatório de rank loss/respec/rules reload. Re-fetch pós-review PASS em 2026-08-30.
