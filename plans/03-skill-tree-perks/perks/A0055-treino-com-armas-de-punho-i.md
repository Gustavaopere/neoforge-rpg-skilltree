# A0055 — Treino com Armas de Punho I

## Estado

- **Design:** APROVADO após correção de classificação/Mastery.
- **Notion:** `3c569db9-f0db-8137-9c8f-cd4c54ad59ef`.
- **Runtime:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**. Producer `combat:fist`, gateway `combat_fist` e classificação FIST/knuckle já existem na linha predecessora; A0055 consome essa infraestrutura sem criar ledger paralela.

## Contrato canônico

- Nível 8 + `combat:fist` ≥60 + gateway `combat_fist`.
- +3% dano FIST por rank, máximo +9%.
- Categoria FIST/knuckle somente provider-native ou mapping versionado explícito; mãos vazias não contam por padrão.
- Não usar `rpgskilltree:fist_weapons`, nome, aparência, tooltip, attack speed ou Punchy como classificador.
- Mastery `combat:fist`: +10 uma única vez por tipo hostil inédito; 6 tipos =60; 8 tipos =80 para A0060.
- A ledger paralela `epicfight:fist` não pode coexistir como segunda progressão da mesma disciplina.

## Evidência runtime

A linha predecessora incorpora o fechamento posterior do producer FIST: `EpicFightProgressionHooks` normaliza `fist|knuckle → fist` e `FistMasteryMilestonePolicy`/`WeaponMasteryMilestoneRuntime` produzem discovery finita `mastery:combat:fist/hostile_type/<entity_type>` com +10 `combat:fist` por tipo hostil inédito, sem dano/tick farming.

`src/main/resources/data/rpgskilltree/tree_architecture/combat.json` já publica `rpgskilltree:combat_fist` com Mastery `combat:fist`, e `A0041A0060EpicFightHooks` aplica o multiplicador de dano apenas quando a capability provider-native classifica FIST/knuckle. O Chat 2 não introduziu classificador por mão vazia, nome, tag paralela ou Punchy.

## Pendências para Chat 2

- **RESOLVIDA P-A0055-01:** producer finite-discovery `combat:fist` existe e é a ledger única da disciplina.
- **RESOLVIDA P-A0055-02:** `combat_fist` está publicado no architecture catalog usado pela branch predecessora.
- **P-A0055-03:** regressão architecture↔model↔producer/provider permanece para validação do Chat 3; o Chat 2 adicionou cobertura focal do lifecycle descendente, mas não declara validação final.

## Implementação Chat 2 — PR #386

- [x] Hook de dano FIST provider-native presente.
- [x] Gate `combat:fist` + `combat_fist` presente.
- [x] Producer Mastery finito/anti-farm presente na linha predecessora.
- [x] Sem ledger paralela `epicfight:fist` para a disciplina.
- [x] Fallback/fail-closed preservado para categoria externa não mapeada.
- [x] Código presente.
- [ ] **VALIDAÇÃO CHAT 3:** regressão architecture↔model↔producer.
- [ ] **VALIDAÇÃO CHAT 3:** provider-present/absent FIST/knuckle.
- [ ] **VALIDAÇÃO CHAT 3:** GameTests/build/dedicated-server/CI de fechamento.
- [ ] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA.

## Boundaries

Punchy é visual/compat. `ARCANE_BACKLASH`, summons, procs e allies/bodyguards Mobstein não geram dano/Mastery FIST do jogador.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS no design/código** | Nível 8 + `combat:fist` 60 + gateway `combat_fist`; producer e architecture estão materializados na linha usada pela PR. |
| 2. Integração global | **PASS** | Dano usa pipeline MARTIAL canônico; Mastery é discovery finita e causal; Punchy, magia, hazards e companions não alimentam o ramo. |
| 3. Qualidade e identidade | **PASS** | Node fundacional/ranked de entrada FIST; bônus pequeno é compatível com função estrutural e prepara o ramo sem fingir ser Notable. |
| 4. Ramificação, distância e topologia | **PASS** | Entrada do ramo `combat_fist` materializada sem teleporte/atalho. |
| 5. Especializações | **PASS** | Armas de Punho permanecem subdisciplina MARTIAL; Epic Fight/Punchy/WoM não viram classes automaticamente. |
| 6. PT-BR | **PASS** | Nome/efeito/gates visíveis em PT-BR; IDs técnicos permanecem em inglês. |
| 7. Notion completo | **PASS** | Gate/Hook/Fallback/Regra completos e re-fetch confirmado. |
| 8. NeoVitae | **PASS** | Ausente. |
| 9. Cobertura modlist/providers | **PASS** | Epic Fight/WoM/Punchy e own-projects/Mobstein foram classificados; classificação externa é provider-native/fail-closed. |

Os critérios técnicos cumulativos passam no design; o código está presente e aguarda a validação final do Chat 3.

## Notion

Gate, Hook, Fallback e Regra corrigidos; re-fetch PASS em 2026-08-30.