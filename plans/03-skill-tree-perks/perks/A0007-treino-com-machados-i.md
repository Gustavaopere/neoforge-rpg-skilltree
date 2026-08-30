# A0007 — Treino com Machados I

## Status e proveniência

- **Design:** APROVADO após reauditoria obrigatória.
- **Código relevante:** PRESENTE.
- **Implementação:** CONFIRMÁVEL após CI/merge.
- **Notion:** https://app.notion.com/p/3c569db9f0db81db9d9fe826285f88b3
- **Critérios locais:** `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`.

## Especificação canônica

- **Domínio/Árvore:** MARTIAL / Epic Fight — Machados.
- **Ramo/Camada/Função:** Varredura e Pressão / 1 / Ramo.
- **Ranks/Custo:** 3 ranks; 1 ponto/rank.
- **Gate:** nível 8 + `epicfight:axe` ≥60 + Gateway `epic_axe`.
- **Efeito:** +3% de dano com machados por rank, máximo +9%.
- **Fallback corrigido:** sem classificação server-side segura de machado pelo provider, A0007 fica inativa para aquele item. Não inferir categoria nem manter tag paralela não versionada.
- **Regra:** `FUNDAMENTO_EXTERIOR: MACHADOS`; provider-native first; fail-closed sem classificação.

## Auditoria — 9 eixos

1. **Gates:** PASS.
2. **Integração global:** PASS — pipeline marcial único.
3. **Identidade:** PASS COMO FUNDAMENTO.
4. **Topologia:** PASS — camada 1.
5. **Especializações:** PASS — não desbloqueia especialista sozinho.
6. **PT-BR:** PASS.
7. **Notion:** PASS após remoção do fallback fictício.
8. **NeoVitae:** PASS.
9. **Modlist/integrações:** PASS — Epic Fight classifica; desconhecido fica fail-closed.

## Evidência técnica

- `NotionCombatPerkRules.baseDamageMultiplier`: `WeaponFamily.AXE -> A0007`.
- `A0001A0020CombatPolicy.beforeHit`: dano somente em hit direto/hostil.
- `A0001A0020EpicFightHooks.family`: capability provider-native; categoria desconhecida não é convertida heurísticamente.

## Pendências

**Nenhuma bloqueante.** A antiga tag `rpgskilltree:axes` deixou de fazer parte do contrato canônico.

## Testes

- [x] coeficiente/rank;
- [x] classificação Epic Fight;
- [x] fail-closed para categoria não resolvida;
- [x] CI/build e dedicated-server smoke exigidos antes do merge.

## Fechamento Chat 1 V3 — ciclo exato A0001–A0010

- **Re-fetch canônico:** registro do Notion consultado novamente em 2026-08-30; propriedades, gate, hook, fallback e regra permanecem alinhados ao dossiê.
- **Mutação no Notion neste ciclo:** não necessária; nenhum drift foi encontrado.
- **Cobertura de providers:** Epic Fight 21.17.3.1 é o owner da família de machado. Armas de addons só participam quando a capability do Epic Fight as classificar explicitamente; item desconhecido permanece fail-closed.
- **Mods periféricos:** Protection Pixel e outros equipamentos/bridges tecnológicos não são providers de categoria de machado para A0007 e não recebem integração artificial.
- **Resultado:** **APROVADA / FECHADA** no lote A0001–A0010. O Chat 2 não deve restaurar tags, nomes, materiais ou heurísticas como classificação paralela.

## Chat 2 — implementação, testes e merge — PR #221

**Estado:** `IMPLEMENTAÇÃO VALIDADA EM CI`; torna-se `IMPLEMENTAÇÃO CONFIRMADA` após merge da PR #221.

- [x] Hook de dano direto de machado no pipeline canônico implementado.
- [x] Gate/ranks/mastery representados no contrato runtime/modelo.
- [x] Família exclusivamente provider-native.
- [x] FAIL-CLOSED para categoria desconhecida; sem tag/heurística paralela.
- [x] Deduplicação/pipeline único preservados.
- [x] Testes de ruleset/policy presentes.
- [x] `RPG Skill Tree CI` #1996 verde no HEAD `b99ba35671dc92477c6b767ec4e4c5c22f0c71d0`.
- [x] JUnit, NeoForge GameTests, build, verificação do JAR e dedicated-server smoke verdes.

**Pendências técnicas:** nenhuma.
