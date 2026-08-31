# A0100 — Anti-Crítico

## Estado

- **Design:** APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE` em 2026-08-31.
- **Notion:** `3c569db9-f0db-8184-bbc8-eb9162cc3d1b`; mutado e re-fetch PASS.
- **Runtime observado:** fórmula pura de decomposição existe, porém `FAIL_CLOSED_A0100=true`; nenhum incoming critical receipt fornece base + parcela crítica.

## Contrato canônico

- Gateway VITALITY + A0090 Têmpera ≥2.
- 4 ranks: reduzir **somente a parcela crítica adicional** em 4% por rank, máximo −16% relativo sobre a parcela crítica.
- Fórmula canônica: `dano_final = dano_base + parcela_critica × (1 − 0,04 × rank)`.
- O node exige, no mesmo evento causal recebido, `legitimatelyCritical`, `baseDamage` e `additionalCriticalDamage`.
- Sem essa decomposição, o node é indisponível e não pode gastar pontos.

## Evidência runtime

`A0081A0100CombatPolicy.antiCriticalDamage(...)` já representa corretamente a matemática, mas nenhum caller a usa no incoming damage. O resolvedor crítico canônico do RPG cobre ataques **do jogador**; ele não prova que um dano recebido foi crítico nem separa a parcela adicional. O próprio bridge atual mantém A0100 fail-closed.

## Cobertura de providers

- RPG Skill Tree ofensivo não é provider suficiente para incoming critical.
- Epic Fight 21.17.3.1 e Apothic Attributes 2.10.1 são candidatos somente se um adapter futuro entregar classificação e decomposição causal do crítico recebido.
- Pufferfish's Attributes não é provider presumido.
- Animação, som, critical chance/damage do atacante, comparação entre hits ou multiplicador típico não podem reconstruir a parcela crítica.

## Pendências para Chat 2

- **P-A0100-01 BLOQUEANTE:** availability: sem incoming critical receipt decomposto, node indisponível/não comprável.
- **P-A0100-02:** futuro adapter deve entregar `critical + base + additional` no mesmo root e aplicar A0100 uma única vez antes das demais etapas defensivas canônicas.
- **P-A0100-03:** testes negativos contra heurísticas, dano comum, crit provider-native e double mitigation.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS em fail-closed | A0090≥2 + incoming receipt obrigatório. |
| Integração global | PASS | reduz só parcela crítica. |
| Qualidade/identidade | PASS | anti-burst, não resistência universal. |
| Topologia | PASS | VITALITY/ANTI_BURST. |
| Especializações | PASS | PP por semântica anti-burst. |
| PT-BR | PASS | fórmula e limites explícitos. |
| Notion | PASS | mutação + re-fetch PASS. |
| NeoVitae | PASS | ausente. |
| Providers | PASS | nenhum crítico recebido inferido. |

Os 18 critérios passam no design porque a ausência do receipt resulta em indisponibilidade estrutural, não em no-op.