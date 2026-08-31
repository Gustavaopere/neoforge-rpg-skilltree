# A0096 — Último Fôlego

## Estado

- **Design:** APROVADO após hardening de classificação física/hostilidade em 2026-08-31.
- **Notion:** `3c569db9-f0db-81a4-962f-c0e807dda1af`; mutado e re-fetch PASS.
- **Runtime observado:** multiplicador já existe dentro do mesmo caminho de `physicalDamageMultiplier` usado por A0092.

## Contrato canônico

- Gateway VITALITY + A0092 Resistência Física ≥2.
- 3 ranks: enquanto a vida **pré-impacto** estiver abaixo de 30%, −4% de dano físico hostil elegível por rank, máximo próprio −12%.
- O limiar é avaliado antes do hit; o próprio golpe não pode retroativamente habilitar a perk.
- A0096 usa a mesma classificação física governada de A0092.

## Evidência runtime

`A0081A0100CombatEvents` captura `health/max_health` no `LivingIncomingDamageEvent` e chama `physicalDamageMultiplier`. A policy aplica A0096 apenas com fração <0,30. O contrato foi endurecido para exigir hostilidade causal e classificação física segura, em vez de tratar qualquer dano abaixo do limiar como elegível.

## Cobertura de providers

- Minecraft/NeoForge + tag `rpgskilltree:physical` são o caminho positivo atual.
- Epic Fight e outros providers entram apenas quando sua fonte física é classificada no mesmo contrato.
- `BLOOD_MAGIC_COST` pode deixar o jogador abaixo de 30%, mas nunca é mitigado por A0096.
- Volcanoes/Enshrouded/environmental hazards não entram sem atacante hostil causal e classificação física explícita.

## Pendências para Chat 2

- **P-A0096-01:** testar bordas 29,999%/30%, snapshot pré-impacto, hostilidade e classificação física.
- **P-A0096-02:** provar composição única com A0092 e ausência de efeito em self/resource/environmental damage.
- **P-A0096-03:** regressões rank/respec/rules reload e dano modded não classificado.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | A0092≥2. |
| Integração global | PASS | reutiliza pipeline físico único. |
| Qualidade/identidade | PASS | sobrevivência de baixa vida, não universal. |
| Topologia | PASS | VITALITY/LOW_HEALTH_DEFENSE. |
| Especializações | PASS | PP somente por semântica compatível. |
| PT-BR | PASS | limiar pré-impacto explícito. |
| Notion | PASS | mutação + re-fetch PASS. |
| NeoVitae | PASS | ausente. |
| Providers | PASS | tag/adapter; desconhecido fail-closed. |

Os 18 critérios passam no design com snapshot e causalidade fechados.