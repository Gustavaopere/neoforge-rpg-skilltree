# A0019 — Treino com Adagas I

## Status e proveniência

- **Design:** APROVADO após reauditoria obrigatória.
- **Código relevante:** PRESENTE.
- **Implementação:** CONFIRMÁVEL após CI/merge.
- **Notion:** https://app.notion.com/p/3c569db9f0db81348e3eda212ea05d78
- **Critérios locais:** `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`.

## Especificação canônica

- **Domínio/Árvore:** MARTIAL / Epic Fight — Adagas.
- **Ramo/Camada/Função:** Mobilidade e Fluxo / 1 / Ramo.
- **Ranks/Custo:** 3 ranks; 1 ponto/rank.
- **Gate:** nível 8 + `epicfight:dagger` ≥60 + Gateway `epic_dagger`.
- **Efeito:** +3% de dano com adagas por rank, máximo +9%.
- **Fallback corrigido:** sem classificação server-side segura de adaga, A0019 fica inativa para o item. Não inferir por nome, material, aparência ou velocidade e não manter tag paralela não versionada.
- **Regra:** `FUNDAMENTO_EXTERIOR: ADAGAS`; provider-native first; fail-closed.

## Auditoria — 9 eixos

1. **Gates:** PASS.
2. **Integração global:** PASS — pipeline marcial único.
3. **Identidade:** PASS COMO FUNDAMENTO.
4. **Topologia:** PASS — root camada 1.
5. **Especializações:** PASS — exterior.
6. **PT-BR:** PASS.
7. **Notion:** PASS após remoção do fallback fictício.
8. **NeoVitae:** PASS.
9. **Modlist/integrações:** PASS — provider-native ou fail-closed.

## Evidência técnica

- `NotionCombatPerkRules.baseDamageMultiplier`: `WeaponFamily.DAGGER -> A0019`.
- `A0001A0020EpicFightHooks.family`: mapeia `dagger` pela capability do Epic Fight.
- `onDamagePre`: aplica o multiplicador no pipeline server-side.
- `CombatPerkTreeModel`: nível 8, mastery 60 e `epic_dagger` no root.

## Pendências

**Nenhuma bloqueante.** A antiga tag `rpgskilltree:daggers` foi removida do contrato canônico; categoria não resolvida fica fail-closed.

## Testes

- [x] coeficiente/rank;
- [x] classificação `dagger` provider-native;
- [x] fail-closed para categoria desconhecida;
- [x] CI/build e dedicated-server smoke exigidos antes do merge.

## Fechamento Chat 1 V3 — ciclo exato A0011–A0020

- **Re-fetch canônico:** Notion consultado novamente em 2026-08-30; propriedades, gate, hook, fallback e regra permanecem alinhados.
- **Mutação no Notion neste ciclo:** não necessária.
- **Provider/versão:** Epic Fight 21.17.3.1 é o owner da família DAGGER; armas externas só entram quando a capability provider-native as classifica.
- **Fail-closed:** item desconhecido permanece sem A0019; não inferir família por nome, material, aparência, velocidade ou dano-base.
- **Cobertura periférica:** compats/addons de armas podem alimentar a capability Epic Fight, mas não recebem classificação paralela criada pelo RPG Skill Tree.
- **Resultado:** **APROVADA / FECHADA** no lote A0011–A0020.