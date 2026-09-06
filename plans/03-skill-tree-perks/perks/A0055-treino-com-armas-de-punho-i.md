# A0055 — Treino com Armas de Punho I

## Estado

- **Design:** APROVADO após correção de classificação/Mastery.
- **Notion:** `3c569db9-f0db-8137-9c8f-cd4c54ad59ef`.
- **Runtime:** **IMPLEMENTAÇÃO CONFIRMADA PELO CHAT 3**. Producer `combat:fist`, gateway `combat_fist` e classificação FIST/knuckle existem na linha predecessora e o runtime consome essa infraestrutura sem ledger paralela.

## Contrato canônico

- Nível 8 + `combat:fist` ≥60 + gateway `combat_fist`.
- +3% dano FIST por rank, máximo +9%.
- Categoria FIST/knuckle somente provider-native ou mapping versionado explícito; mãos vazias não contam por padrão.
- Não usar `rpgskilltree:fist_weapons`, nome, aparência, tooltip, attack speed ou Punchy como classificador.
- Mastery `combat:fist`: +10 uma única vez por tipo hostil inédito; 6 tipos =60; 8 tipos =80 para A0060.
- A ledger paralela `epicfight:fist` não pode coexistir como segunda progressão da mesma disciplina.

## Evidência runtime

`EpicFightProgressionHooks` normaliza `fist|knuckle → fist` e `FistMasteryMilestonePolicy`/`WeaponMasteryMilestoneRuntime` produzem discovery finita `mastery:combat:fist/hostile_type/<entity_type>` com +10 `combat:fist` por tipo hostil inédito, sem dano/tick farming.

`data/rpgskilltree/tree_architecture/combat.json` publica `rpgskilltree:combat_fist` com Mastery `combat:fist`, e `A0041A0060EpicFightHooks` aplica o multiplicador apenas quando a capability provider-native classifica FIST/knuckle.

## Pendências técnicas

- **RESOLVIDA P-A0055-01:** producer finite-discovery `combat:fist` e ledger única.
- **RESOLVIDA P-A0055-02:** `combat_fist` publicado no architecture catalog.
- **VALIDADA P-A0055-03:** regressão architecture↔model↔producer/provider coberta pela bateria do lote.
- Nenhuma pendência bloqueante restante.

## Implementação e validação

- [x] Hook de dano FIST provider-native presente.
- [x] Gate `combat:fist` + `combat_fist` presente.
- [x] Producer Mastery finito/anti-farm presente.
- [x] Sem ledger paralela `epicfight:fist`.
- [x] Fallback/fail-closed para categoria externa não mapeada.
- [x] Código presente.
- [x] **VALIDAÇÃO CHAT 3:** JUnit/core e regressões architecture/model/producer.
- [x] **VALIDAÇÃO CHAT 3:** NeoForge adapter/GameTests verdes.
- [x] **VALIDAÇÃO CHAT 3:** build NeoForge e dedicated-server smoke verdes.
- [x] **VALIDAÇÃO CHAT 3:** CI funcional GREEN em `6826c50896c4ad586b8942031465b6a0a3ce44af` (run `34006356029`).
- [x] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA.

## Boundaries

Punchy é visual/compat. `ARCANE_BACKLASH`, summons, procs e allies/bodyguards Mobstein não geram dano/Mastery FIST do jogador.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS** | Nível 8 + `combat:fist` 60 + gateway `combat_fist`. |
| 2. Integração global | **PASS** | Dano MARTIAL canônico; Mastery finita e causal. |
| 3. Qualidade e identidade | **PASS** | Node fundacional/ranked FIST. |
| 4. Ramificação, distância e topologia | **PASS** | Entrada do ramo `combat_fist` materializada. |
| 5. Especializações | **PASS** | Subdisciplina MARTIAL. |
| 6. PT-BR | **PASS** | Player-facing em PT-BR. |
| 7. Notion completo | **PASS** | Gate/Hook/Fallback/Regra completos. |
| 8. NeoVitae | **PASS** | Ausente. |
| 9. Cobertura modlist/providers | **PASS** | Epic Fight/WoM/Punchy e own-projects/Mobstein classificados. |

## Notion

Gate, Hook, Fallback e Regra corrigidos; re-fetch PASS em 2026-08-30.

## Fechamento Chat 3 — PR #387

Contrato revisado e bateria automatizada real concluída no HEAD funcional `6826c50896c4ad586b8942031465b6a0a3ce44af`. A0055 fica **IMPLEMENTAÇÃO CONFIRMADA**.