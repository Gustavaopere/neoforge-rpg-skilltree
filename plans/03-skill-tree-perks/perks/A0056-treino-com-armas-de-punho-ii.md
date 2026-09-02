# A0056 — Treino com Armas de Punho II

## Estado

- **Design:** APROVADO; sem mutação funcional no Notion nesta reauditoria.
- **Notion:** `3c569db9-f0db-81c2-a2ce-fe2a2fa8714c`.
- **Runtime:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**. O caminho provider-native de attack speed está ligado ao ramo FIST/knuckle e A0055 já possui gateway/Mastery alcançáveis na linha predecessora.

## Contrato canônico

- A0055 ≥2 + gateway `combat_fist`.
- +2% de ritmo efetivo FIST por rank, máximo +6%.
- Usar apenas `EpicFightAttributes.ATTACK_SPEED_MODIFIER`/`ModifyAttackSpeedEvent` quando o moveset FIST/knuckle realmente consumir o valor server-authoritative.
- Sem contrato de cadência seguro, omitir o componente; não converter para Stamina, movimento, dano ou alteração direta de animação.
- Se A0055/gateway for invalidado por rank loss/respec/rules reload, A0056 deixa de ser elegível imediatamente; nenhum estado descendente pode persistir por bypass.

## Evidência runtime

`A0041A0060EpicFightHooks.onAttackSpeed(...)` aplica `NotionCombatPerkRules.rhythmBonus(FIST, ranks)` somente quando a capability Epic Fight é FIST/knuckle. O handler usa progression server-side no servidor e snapshot sincronizado apenas para o cliente local, sem criar segundo atributo ou timer paralelo.

A0055 já está estruturalmente reconciliada na branch predecessora (`combat:fist` + `combat_fist`). O Chat 2 também adicionou reconciliation dos estados descendentes A0058/A0060 quando ranks/pré-requisitos efetivos são removidos.

## Pendências para Chat 2

- **RESOLVIDAS herdadas de A0055:** producer `combat:fist` e architecture `combat_fist` já existem.
- A prova provider-present do moveset FIST real e dedicated-server permanece para Chat 3.
- Purchase/rank reconciliation deve ser validada pelo Chat 3; nenhum estado próprio persistente pertence a A0056.

## Implementação Chat 2 — PR #386

- [x] Hook `ModifyAttackSpeedEvent` presente.
- [x] Classificação FIST/knuckle provider-native presente.
- [x] Gate estrutural A0055/`combat_fist` presente.
- [x] Sem fallback para Stamina/movimento/dano/animação.
- [x] Código presente.
- [ ] **VALIDAÇÃO CHAT 3:** provider-present/absent e dedicated-server.
- [ ] **VALIDAÇÃO CHAT 3:** regressão de rank/gateway.
- [ ] **VALIDAÇÃO CHAT 3:** build/GameTests/smoke/CI de fechamento.
- [ ] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA.

## Provider→árvore

Volcanoes, Enshrouded, Black Arcana, Mobstein e Punchy não fornecem cadência FIST.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS no design/código** | A0055 ≥2 + `combat_fist`; a infraestrutura de A0055 está materializada. |
| 2. Integração global | **PASS** | Usa attack-speed real do Epic Fight; não converte para Stamina, movimento, dano, hunger ou outro recurso. |
| 3. Qualidade e identidade | **PASS** | Ranked training incremental coerente com função de caminho; bônus pequeno não é rotulado como Notable/Capstone. |
| 4. Ramificação, distância e topologia | **PASS** | Segue diretamente A0055 no ramo FIST publicado. |
| 5. Especializações | **PASS** | Mantém-se subdisciplina MARTIAL/ARMAS_DE_PUNHO, sem criar classe automática de provider. |
| 6. PT-BR | **PASS** | Nome, efeito e requisitos em PT-BR; API/IDs técnicos permanecem em inglês. |
| 7. Notion completo | **PASS** | Fetch fresco sem drift e campos pertinentes completos. |
| 8. NeoVitae | **PASS** | Nenhuma referência residual. |
| 9. Cobertura modlist/providers | **PASS** | Epic Fight é provider de cadence; own-projects, Mobstein e Punchy foram avaliados e corretamente não promovidos a provider dessa mecânica. |

Os critérios técnicos cumulativos passam no design; código presente, validação final reservada ao Chat 3.

## Notion

Fetch fresco sem drift; nenhuma mutação cosmética.