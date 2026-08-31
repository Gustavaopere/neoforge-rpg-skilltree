# A0086 — Vampirismo Universal

## Estado

- **Design:** APROVADO EM FAIL-CLOSED após correção de availability transitiva em 2026-08-31.
- **Notion:** `3c569db9-f0db-819f-8216-fbbafe17b035`; Gate/Fallback/Regra corrigidos; re-fetch PASS.
- **Runtime observado:** regra de convergência existe no core, mas A0086 está **indisponível/não comprável** enquanto A0083/A0085 estiverem indisponíveis.

## Contrato canônico

- Keystone híbrida: A0082=3 + A0083=3 + A0085≥2, todos legitimamente disponíveis/adquiridos.
- 1 rank, custo 3.
- Dano físico direto, mágico direto e periódico elegível convergem no mesmo `SustainResolver`.
- Se houver coeficiente especializado elegível, usa-se o maior. A fonte universal de 1% só cobre root elegível sem coeficiente especializado.
- Uma root/pulse cura no máximo uma vez; cap global 3% max health/20 ticks; sem carry-over.

## Availability transitiva

A0086 não cria classificadores. Ela só converge fontes que já possuem autoria/tipo/root válidos. Logo não pode contornar a indisponibilidade de A0083 ou A0085 para “habilitar” magic/DoT por um fallback universal.

Enquanto qualquer predecessor obrigatório for estruturalmente indisponível, A0086 também é indisponível/não comprável. Quando os predecessors existirem, providers individuais ainda podem falhar fechado sem derrubar fontes seguras de outras famílias.

## Cobertura global de providers

- MARTIAL: vanilla/Epic Fight/Simply somente com provenance de arma e native lifesteal dedup.
- ARCANE: Iron's/Ars/addons somente por direct-magic receipt real.
- ELEMENTAL: A0084 continua bridge opcional; se aplicável à mesma root, maior coeficiente vence.
- OCCULT/DoT: Goety/Malum/Eidolon/Iron's/Ars somente por application+pulse receipt.
- Vampirism 1.10.12: native lifesteal só com correlação exata.
- Pufferfish's Attributes 0.8.3 não é provider genérico de sustain.
- Summons/companions, fake players, machines, contraptions, Black Arcana Backlash, Enshrouded/Volcanoes hazards e custos de vida/recurso permanecem inelegíveis.

## Evidência runtime

`A0081A0100CombatPolicy.sustainCoefficient(...)` já implementa `max(especializados, universal 1%)`. `SustainResolver` implementa uma claim por root, native correlation e cap. O bridge atual, porém, só produz roots físicos; portanto a keystone não pode ser considerada alcançável enquanto seus predecessores magic/DoT não forem implementáveis.

## Pendências para Chat 2

- **P-A0086-01 BLOQUEANTE:** availability transitiva A0083/A0085→A0086 no purchase/gate; não permitir bypass universal.
- **P-A0086-02:** preservar `max coefficient`, nunca soma integral A0082/A0083/A0084/A0085/native.
- **P-A0086-03:** fonte universal de 1% apenas quando a root já é causalmente elegível, mas nenhuma especialização se aplica; não usar 1% para classificar origem desconhecida.
- **P-A0086-04:** lifecycle/dedup cross-provider e testes de roots híbridas, native heal, cap e multiplayer.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS no design | A0082=3 + A0083=3 + A0085≥2 e availability real. |
| Integração global | PASS | keystone converge, não cria pipeline paralelo. |
| Qualidade/identidade | PASS | universalidade vem de cobertura, não de heurística. |
| Topologia | PASS | HYBRID/SUSTAIN_CONVERGENCE. |
| Especializações | PASS | não satisfaz Specialist por si só. |
| PT-BR | PASS | contrato explícito. |
| Notion | PASS após correção | re-fetch confirmado. |
| NeoVitae | PASS | ausente. |
| Providers | PASS no design | apenas providers já integrados causalmente. |

Os 18 critérios passam **no design** com availability transitiva explícita.