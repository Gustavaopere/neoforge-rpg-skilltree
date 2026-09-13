# A0057 — Precisão com Armas de Punho

## Estado

- **Design:** APROVADO após correção de classificação/provenance.
- **Notion:** `3c569db9-f0db-81e5-a161-e615da182f4e`.
- **Runtime:** **IMPLEMENTAÇÃO CONFIRMADA PELO CHAT 3**. O resolver crítico FIST usa a infraestrutura `combat:fist`/`combat_fist` e preserva uma única resolução crítica por root action.

## Contrato canônico

- A0055 ≥1 + gateway `combat_fist`.
- +3% chance crítica FIST por rank, máximo +9%.
- Classificação FIST/knuckle provider-native/versionada e ataque direto do jogador.
- Uma única resolução crítica/root action; Apothic, se usado, integra o mesmo resolver.
- Sem categoria segura, fail-closed; `rpgskilltree:fist_weapons` não é fallback canônico.
- Perda de A0055/gateway por rank loss/respec/rules reload remove elegibilidade imediatamente.

## Evidência runtime

`A0041A0060EpicFightHooks.onCriticalHit(...)` e `rootAction(...)` usam categoria `fist`/`knuckle`, correlacionam crítico provider-native e chamam o serviço crítico canônico. A mesma root action é reutilizada no damage pipeline quando a correlação de `CriticalHitEvent` está disponível, impedindo segunda rolagem A0057 no mesmo ataque causal.

A infraestrutura FIST de A0055 possui Mastery `combat:fist` e gateway `combat_fist`. `A0041A0060RuntimeState.ranks(...)` usa ranks efetivos e reconcilia estados descendentes quando os pré-requisitos deixam de valer.

## Pendências técnicas

- **RESOLVIDAS herdadas de A0055:** `combat:fist` e `combat_fist` materializados.
- **VALIDADA:** uma única rolagem/root, direct-player provenance e provider-present/absent na bateria do lote.
- Nenhuma pendência bloqueante restante.

## Implementação e validação

- [x] Hook crítico FIST/knuckle presente.
- [x] Resolver crítico canônico único preservado.
- [x] Gate A0055/`combat_fist` alcançável.
- [x] Fail-closed para categoria não mapeada.
- [x] Código presente.
- [x] **VALIDAÇÃO CHAT 3:** JUnit/core e uma rolagem/root.
- [x] **VALIDAÇÃO CHAT 3:** NeoForge adapter/GameTests verdes.
- [x] **VALIDAÇÃO CHAT 3:** build NeoForge e dedicated-server smoke verdes.
- [x] **VALIDAÇÃO CHAT 3:** CI funcional GREEN em `6826c50896c4ad586b8942031465b6a0a3ce44af` (run `34006356029`).
- [x] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA.

## Boundaries

`ARCANE_BACKLASH`, dano de companion Mobstein, proc/follow-up derivado e callback visual Punchy são inelegíveis. Ataque FIST direto do jogador contra entidades dos próprios projetos continua universalmente elegível.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS** | A0055 ≥1 + `combat_fist`. |
| 2. Integração global | **PASS** | Crítico passa pelo resolver canônico único; autoria preservada. |
| 3. Qualidade e identidade | **PASS** | Node incremental de precisão FIST. |
| 4. Ramificação, distância e topologia | **PASS** | Camada coerente após A0055. |
| 5. Especializações | **PASS** | MARTIAL/ARMAS_DE_PUNHO. |
| 6. PT-BR | **PASS** | Player-facing em PT-BR. |
| 7. Notion completo | **PASS** | Hook/Fallback/Regra completos. |
| 8. NeoVitae | **PASS** | Ausente. |
| 9. Cobertura modlist/providers | **PASS** | Providers/boundaries pertinentes dispostos. |

## Notion

Hook/Fallback/Regra corrigidos; re-fetch PASS em 2026-08-30.

## Fechamento Chat 3 — PR #387

Contrato revisado e bateria automatizada real concluída no HEAD funcional `6826c50896c4ad586b8942031465b6a0a3ce44af`. A0057 fica **IMPLEMENTAÇÃO CONFIRMADA**.