# A0058 — Sequência Limpa

## Estado

- **Design:** APROVADO após boundary retroativo.
- **Notion:** `3c569db9-f0db-81cd-bd48-c3ed8f11ef76`.
- **Runtime:** IMPLEMENTAÇÃO PARCIAL.

## Contrato canônico

- A0057 ≥2 + gateway `combat_fist`.
- Hits FIST diretos consecutivos em 2/2,5 s geram Sequência, cap 5.
- Miss confirmado, troca para arma não elegível ou **impacto pesado recebido com receipt provider-native** encerra a Sequência.
- Alvo de treino/invulnerável, fake player, summon, proc/follow-up e callback duplicado não contam.
- Exhaustion/fome pode penalizar apenas manutenção quando houver configuração real; Stamina é recurso distinto.

## Evidência runtime

O adapter já adiciona Sequência somente no `DELIVER_DAMAGE_POST` confirmado, encerra em `ATTACK_PHASE_END` sem alvo e reseta ao trocar para arma não-FIST. Entretanto não há, no bridge A0041–A0060 auditado, consumer de receipt hostil de impacto pesado recebido para encerrar Sequência. Também não existe modulação corporal exhaustion/fome; esta segunda parcela pode permanecer omitida pelo fallback.

## Pendências para Chat 2

- **P-A0058-01:** integrar receipt provider-native seguro de impacto pesado recebido e resetar Sequência uma única vez por outcome; não inferir por dano alto/velocidade/animação.
- **P-A0058-02:** opcional — integrar exhaustion/fome real apenas se configurado; ausência deve omitir a penalidade sem criar proxy.
- Herdadas de A0055: ledger/gateway FIST precisam ser reconciliados.

## Boundaries

Backlash, Shroud/Exposure, hazards Volcanoes e companion-owned damage não são receipts de heavy impact nem produtores de Sequência.

## Notion

Hook/Fallback/Regra corrigidos; re-fetch PASS.
