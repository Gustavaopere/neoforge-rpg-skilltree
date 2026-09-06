# A0072 — Retaliação

## Estado

- **Design:** APROVADO após correção de availability em 2026-08-31.
- **Notion:** `3c569db9-f0db-813a-945d-f4f198f1c038`; Gate/Fallback/Regra corrigidos; re-fetch pós-escrita PASS.
- **Runtime observado:** efeito possui hook POST adequado, porém a perk é estruturalmente indisponível enquanto A0067 for indisponível.

## Contrato canônico

- Gateway MARTIAL + A0067 Firmeza Ofensiva ≥ 1 rank.
- 3 ranks, 1 ponto por rank.
- Após dano direto hostil efetivamente recebido, +4% de dano físico elegível por rank durante 3 s, máximo +12%.
- Novo dano válido renova duração; magnitude nunca empilha.

## Authority / boundary

`LivingDamageEvent.Post` é o boundary correto: só dano pós-mitigação >0, direto, de fonte hostil real. Black Arcana `BLOOD_MAGIC_COST`, self-damage, ambiente, reflexão própria, DoT próprio e custos de recurso são inelegíveis.

## Evidência runtime

`A0061A0080EpicFightHooks.onDirectHostileDamageTaken(...)` já usa POST, exige dano positivo e atacante direto hostil. A policy deduplica evento causal e mantém uma única janela.

## Availability

A0067 foi fechada no design como indisponível/não comprável até existir offensive attack-window binding seguro. Pela dependência obrigatória, **A0072 também deve permanecer indisponível/não comprável**. Fallback não pode bypassar a cadeia.

## Pendências para Chat 2

- **P-A0072-01 BLOQUEANTE:** propagar unavailable-node invariant A0067 → A0072 no purchase/gate.
- **P-A0072-02:** testar refresh sem stacking, recursão, self/hazard/BLOOD_MAGIC_COST e callbacks duplicados.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS no design | availability de A0067 é herdada. |
| Integração global | PASS | ameaça externa real; Black Arcana/custos excluídos. |
| Qualidade/identidade | PASS | reação temporal sem stacking. |
| Topologia | PASS | Camada 2, `MARTIAL/RETALIATION`. |
| Especializações | PASS | região reativa explícita. |
| PT-BR | PASS | Texto em PT-BR. |
| Notion | PASS após correção | Re-fetch confirmado. |
| NeoVitae | PASS | Ausente. |
| Providers | PASS | NeoForge/Epic Fight/RPG; outros não inventados. |

Os 18 critérios passam **no design**; implementação não pode ser confirmada enquanto A0067 continuar indisponível.