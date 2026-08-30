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

## Auditoria retroativa de integração — projetos próprios + Mobstein 5.4.4 — lote A0011–A0020

- **Resultado:** APROVADA sem mutação adicional no Notion; o contrato provider-native já fecha a boundary necessária.
- **RPG Skill Tree:** bônus pertence ao pipeline MARTIAL canônico e só é aplicado a ação direta do jogador classificada como SPEAR pelo Epic Fight.
- **Volcanoes / Enshrouded / Black Arcana:** NÃO DEVE SER INTEGRADO a A0013; geologia/hazards, Shroud/Flame e Arcane Danger não classificam arma nem alteram alcance físico da lança.
- **Mobstein 5.4.4:** combate direto do jogador contra entidades Mobstein é coberto pelo sistema universal; ataques de allies/bodyguards não herdam o bônus de dano do dono.
- **Notion:** re-fetch em 2026-08-30 sem drift; nenhuma mutação cosmética foi realizada.
- **Fail-closed:** sem classificação SPEAR server-authoritative, A0013 permanece inativa para o item.
- **Chat 2:** nenhuma bridge nova; apenas preservar autoria direta e classificação provider-native.
