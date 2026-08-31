# A0097 — Primeira Defesa

## Estado

- **Design:** APROVADO após correção da definição de hostilidade em 2026-08-31.
- **Notion:** `3c569db9-f0db-814d-a37e-c21276901665`; mutado e re-fetch PASS.
- **Runtime observado:** `A0081A0100DefenseState` já mantém janela de 10 s, mas o bridge atual restringe atacante a `Enemy || Player`.

## Contrato canônico

- Gateway VITALITY + A0088 Constituição ≥1.
- 3 ranks: após 10 s/200 ticks sem dano hostil elegível, o próximo dano hostil recebe −5% por rank, máximo −15%, e consome a preparação.
- Hostilidade é causal: atacante `LivingEntity`, diferente do jogador e não aliado. PvP não aliado conta.
- Ambiente, self-damage, aliados e resource costs não consomem nem reiniciam a janela.

## Evidência runtime

`A0081A0100DefenseState` registra o último dano hostil elegível e possui `openingReady/consumeOpeningDefense`. O defeito auditado está no classificador `hostile(...)`, que usa `target instanceof Enemy || target instanceof Player`; isso exclui entidades modded hostis que não implementem `Enemy` e mistura semântica de hostilidade com hierarquia Java.

## Cobertura de providers

- Minecraft/NeoForge fornece o evento e attacker causal.
- Epic Fight e mobs modded participam quando preservam `DamageSource.entity` como atacante causal não aliado.
- Summons/companions aliados não são hostis; owner indireto não deve reclassificar a origem sem receipt explícito.
- Hazards de Volcanoes/Enshrouded e Black Arcana resource costs ficam fora sem atacante hostil causal.

## Pendências para Chat 2

- **P-A0097-01 BLOQUEANTE DE CONFORMIDADE:** substituir `Enemy || Player` por classificador causal compartilhado de atacante vivo/não aliado.
- **P-A0097-02:** dedup de múltiplos callbacks do mesmo hit e consumo único da preparação.
- **P-A0097-03:** lifecycle morte/logout/respawn/dimensão/shutdown e testes PvE/PvP/modded mob/environment/self-cost.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | A0088≥1. |
| Integração global | PASS | estado defensivo server-authoritative. |
| Qualidade/identidade | PASS | opener defensivo após calm window. |
| Topologia | PASS | VITALITY/OPENING_DEFENSE. |
| Especializações | PASS | PP por semântica defensiva. |
| PT-BR | PASS | janela e consumo claros. |
| Notion | PASS | hardening persistido. |
| NeoVitae | PASS | ausente. |
| Providers | PASS | attacker causal, sem `Enemy` como authority. |

Os 18 critérios passam no design; o classificador runtime precisa ser alinhado.