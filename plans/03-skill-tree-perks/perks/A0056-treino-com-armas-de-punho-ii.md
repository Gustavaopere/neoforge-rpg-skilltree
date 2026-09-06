# A0056 — Treino com Armas de Punho II

## Estado

- **Design:** APROVADO; sem mutação funcional no Notion nesta reauditoria.
- **Notion:** `3c569db9-f0db-81c2-a2ce-fe2a2fa8714c`.
- **Runtime:** **IMPLEMENTAÇÃO CONFIRMADA PELO CHAT 3**. O caminho provider-native de attack speed está ligado ao ramo FIST/knuckle e A0055 possui gateway/Mastery alcançáveis.

## Contrato canônico

- A0055 ≥2 + gateway `combat_fist`.
- +2% de ritmo efetivo FIST por rank, máximo +6%.
- Usar apenas `EpicFightAttributes.ATTACK_SPEED_MODIFIER`/`ModifyAttackSpeedEvent` quando o moveset FIST/knuckle realmente consumir o valor server-authoritative.
- Sem contrato de cadência seguro, omitir o componente; não converter para Stamina, movimento, dano ou alteração direta de animação.
- Se A0055/gateway for invalidado por rank loss/respec/rules reload, A0056 deixa de ser elegível imediatamente; nenhum estado descendente pode persistir por bypass.

## Evidência runtime

`A0041A0060EpicFightHooks.onAttackSpeed(...)` aplica `NotionCombatPerkRules.rhythmBonus(FIST, ranks)` somente quando a capability Epic Fight é FIST/knuckle. O handler usa progression server-side no servidor e snapshot sincronizado apenas para o cliente local, sem criar segundo atributo ou timer paralelo.

A0055 está estruturalmente reconciliada (`combat:fist` + `combat_fist`) e o runtime reconcilia estados descendentes quando ranks/pré-requisitos efetivos são removidos.

## Pendências técnicas

- **RESOLVIDAS herdadas de A0055:** producer `combat:fist` e architecture `combat_fist` existem.
- **VALIDADA:** prova provider-present/absent do caminho FIST e dedicated-server cobertas pela bateria do lote.
- **VALIDADA:** rank/gateway reconciliation sem estado persistente próprio de A0056.
- Nenhuma pendência bloqueante restante.

## Implementação e validação

- [x] Hook `ModifyAttackSpeedEvent` presente.
- [x] Classificação FIST/knuckle provider-native presente.
- [x] Gate estrutural A0055/`combat_fist` presente.
- [x] Sem fallback para Stamina/movimento/dano/animação.
- [x] Código presente.
- [x] **VALIDAÇÃO CHAT 3:** JUnit/core e regressão de rank/gateway.
- [x] **VALIDAÇÃO CHAT 3:** NeoForge adapter/GameTests verdes.
- [x] **VALIDAÇÃO CHAT 3:** build NeoForge e dedicated-server smoke verdes.
- [x] **VALIDAÇÃO CHAT 3:** CI funcional GREEN em `6826c50896c4ad586b8942031465b6a0a3ce44af` (run `34006356029`).
- [x] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA.

## Provider→árvore

Volcanoes, Enshrouded, Black Arcana, Mobstein e Punchy não fornecem cadência FIST.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS** | A0055 ≥2 + `combat_fist`. |
| 2. Integração global | **PASS** | Usa attack-speed real do Epic Fight; não converte para recursos paralelos. |
| 3. Qualidade e identidade | **PASS** | Ranked training incremental coerente. |
| 4. Ramificação, distância e topologia | **PASS** | Segue A0055 no ramo FIST publicado. |
| 5. Especializações | **PASS** | MARTIAL/ARMAS_DE_PUNHO. |
| 6. PT-BR | **PASS** | Player-facing em PT-BR. |
| 7. Notion completo | **PASS** | Campos pertinentes completos. |
| 8. NeoVitae | **PASS** | Ausente. |
| 9. Cobertura modlist/providers | **PASS** | Epic Fight é provider; own-projects/Mobstein/Punchy não foram promovidos artificialmente. |

## Notion

Fetch fresco sem drift; nenhuma mutação cosmética.

## Fechamento Chat 3 — PR #387

Contrato revisado e bateria automatizada real concluída no HEAD funcional `6826c50896c4ad586b8942031465b6a0a3ce44af`. A0056 fica **IMPLEMENTAÇÃO CONFIRMADA**.