# A0072 — Retaliação

## Estado

- **Design:** APROVADO após correção de availability em 2026-08-31.
- **Notion:** `3c569db9-f0db-813a-945d-f4f198f1c038`; Gate/Fallback/Regra corrigidos; re-fetch pós-escrita PASS.
- **Estado Chat 2:** **CÓDIGO PRESENTE EM FAIL-CLOSED / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.

## Contrato canônico

- Gateway MARTIAL + A0067 Firmeza Ofensiva ≥ 1 rank.
- 3 ranks, 1 ponto por rank.
- Após dano direto hostil efetivamente recebido, +4% de dano físico elegível por rank durante 3 s, máximo +12%.
- Novo dano válido renova duração; magnitude nunca empilha.

## Authority / boundary

`LivingDamageEvent.Post` é o boundary correto: só dano pós-mitigação >0, direto, de fonte hostil real. Black Arcana `BLOOD_MAGIC_COST`, self-damage, ambiente, reflexão própria, DoT próprio e custos de recurso são inelegíveis.

## Evidência runtime

`A0061A0080EpicFightHooks.onDirectHostileDamageTaken(...)` usa POST, exige dano positivo e atacante direto hostil. A policy deduplica evento causal e mantém uma única janela.

## Availability

A0067 permanece indisponível/não comprável até existir offensive attack-window binding seguro. Pela dependência obrigatória, **A0072 também permanece indisponível/não comprável**. `CombatPerkAvailabilityRuntime` mascara o rank efetivo e `NodePurchaseRequestProcessor`/`PlayerProgressionRuntime` recusam aquisição do node indisponível, preservando alocações persistidas preexistentes sem efeito runtime.

## Implementação Chat 2 — 2026-09-01

- unavailable-node invariant A0067 → A0072 implementado no purchase/gate runtime;
- `effectiveRanks` garante contribuição runtime zero enquanto a cadeia estiver indisponível;
- hook POST hostil existente foi preservado, sem abrir fallback por PRE, self-damage, hazard ou custo de recurso;
- alteração de disponibilidade/rank efetivo limpa estado transitório por `A0061A0080RuntimeState`;
- nenhuma solução genérica foi inventada para tornar A0067 adquirível.

## Pendências para Chat 3

- validar compra recusada quando A0067 estiver indisponível, inclusive pelos dois caminhos server-authoritative de purchase;
- validar refresh de 3 s sem stacking de magnitude;
- validar exclusões de self/hazard/BLOOD_MAGIC_COST, recursão e callbacks duplicados;
- validar limpeza de estado em rank loss/respec/rules reload/lifecycle.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | availability de A0067 é herdada. |
| Integração global | PASS | ameaça externa real; Black Arcana/custos excluídos. |
| Qualidade/identidade | PASS | reação temporal sem stacking. |
| Topologia | PASS | Camada 2, `MARTIAL/RETALIATION`. |
| Especializações | PASS | região reativa explícita. |
| PT-BR | PASS | Texto em PT-BR. |
| Notion | PASS após correção | Re-fetch confirmado. |
| NeoVitae | PASS | Ausente. |
| Providers | PASS | NeoForge/Epic Fight/RPG; outros não inventados. |

Chat 2 não executou a bateria final de testes/build/smoke/CI e não declara `IMPLEMENTAÇÃO CONFIRMADA`.
