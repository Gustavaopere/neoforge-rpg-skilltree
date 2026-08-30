# A0013 — Treino com Lanças I

## Status e proveniência

- **Design:** APROVADO após reauditoria obrigatória.
- **Código relevante:** PRESENTE.
- **Implementação:** CONFIRMÁVEL após CI/merge.
- **Notion:** https://app.notion.com/p/3c569db9f0db816cb2f6dd938fbd7562
- **Critérios locais:** `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`.

## Especificação canônica

- **Domínio/Árvore:** MARTIAL / Epic Fight — Lanças.
- **Ramo/Camada/Função:** Alcance e Controle de Distância / 1 / Ramo.
- **Ranks/Custo:** 3 ranks; 1 ponto/rank.
- **Gate:** nível 8 + `epicfight:spear` ≥60 + Gateway `epic_spear`.
- **Efeito:** +3% de dano com lanças por rank, máximo +9%.
- **Fallback corrigido:** sem classificação server-side segura de lança, A0013 fica inativa para o item. Não inferir categoria por nome, material, aparência ou alcance; não manter tag paralela não versionada; esta perk nunca altera alcance físico.
- **Regra:** `FUNDAMENTO_EXTERIOR: LANÇAS`; provider-native first e fail-closed.

## Auditoria — 9 eixos

1. **Gates:** PASS.
2. **Integração global:** PASS — dano no pipeline marcial, sem alcance paralelo.
3. **Identidade:** PASS COMO FUNDAMENTO.
4. **Topologia:** PASS — root camada 1.
5. **Especializações:** PASS — fundamento exterior.
6. **PT-BR:** PASS.
7. **Notion:** PASS após remoção do fallback fictício.
8. **NeoVitae:** PASS.
9. **Modlist/integrações:** PASS — classificação Epic Fight ou fail-closed.

## Evidência técnica

- `NotionCombatPerkRules.baseDamageMultiplier`: `WeaponFamily.SPEAR -> A0013`.
- `A0001A0020EpicFightHooks.family`: mapeia `spear` por capability provider-native.
- `onDamagePre`: usa a mesma pipeline server-side de dano.
- `CombatPerkTreeModel`: root com nível 8, mastery 60 e `epic_spear`.

## Pendências

**Nenhuma bloqueante.** A antiga tag `rpgskilltree:spears` foi removida do design canônico; ausência de classificação é fail-closed.

## Testes

- [x] coeficiente/rank;
- [x] classificação `spear` provider-native;
- [x] ausência de alteração artificial de alcance;
- [x] CI/build e dedicated-server smoke exigidos antes do merge.

## Fechamento Chat 1 V3 — ciclo exato A0011–A0020

- **Re-fetch canônico:** Notion consultado novamente em 2026-08-30; propriedades, gate, hook, fallback e regra permanecem alinhados.
- **Mutação no Notion neste ciclo:** não necessária.
- **Provider/versão:** Epic Fight 21.17.3.1 é o owner da classificação SPEAR; armas externas só entram quando a capability provider-native resolve a família.
- **Fail-closed:** categoria desconhecida deixa A0013 inativa; não inferir por alcance, nome, material, aparência ou estatísticas genéricas.
- **Integração periférica:** Weapons of Miracles/Epic Fight Compat podem ampliar o conjunto de armas classificadas pelo próprio Epic Fight, sem criar classificação paralela no RPG Skill Tree.
- **Resultado:** **APROVADA / FECHADA** no lote A0011–A0020.