# A0010 — Pressão do Carrasco

## Status e proveniência

- **Design:** APROVADO após reauditoria obrigatória.
- **Código relevante:** PRESENTE no receipt server-authoritative do Epic Fight.
- **Implementação:** CONFIRMÁVEL após CI/merge.
- **Notion:** https://app.notion.com/p/3c569db9f0db81fd9b1bcb501b7745ba
- **Critérios locais:** `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`.

## Especificação canônica

A0010 é Notable de Fúria para machados. Exige A0009 ≥2. Cada hit corpo a corpo direto, hostil, confirmado, com autoria real e categoria machado gera base 8 de Fúria; rank 1 aplica ×1,10, rank 2 ×1,20; troca legítima de alvo aplica depois ×1,50; cap 100. Uma única concessão por ação.

O fallback genérico anterior foi removido. Sem receipt server-authoritative que prove autoria, dano confirmado e categoria de machado, A0010 fica inativa. Tentativa de ataque, animação, nome/material do item ou dano observado não são autorização.

## Auditoria — 9 eixos

1. **Gates:** PASS — A0009 ≥2 + gateway.
2. **Integração global:** PASS — usa Fúria canônica única.
3. **Identidade:** PASS — recompensa pressão e alternância legítima de alvo.
4. **Topologia:** PASS — Notable camada 3 antes do terminal.
5. **Especializações:** PASS — exterior.
6. **PT-BR:** PASS.
7. **Notion:** PASS após remoção do fallback sem receipt.
8. **NeoVitae:** PASS.
9. **Modlist/integrações:** PASS/FALLBACK — Epic Fight é a rota comprovada; demais rotas ficam fail-closed.

## Evidência técnica

- `NotionCombatPerkRules.axeFuryGain`: ordem base 8 → rank → troca de alvo.
- `NotionCombatPerkState.addFury`: clamp 100.
- `A0001A0020CombatPolicy.afterConfirmedHit`: exige `direct`, `hostile`, `actualDamage`, usa `claimOnce` e registra troca de alvo.
- `A0001A0020EpicFightHooks.onDamagePost`: só encaminha `modifiedDamage > 0` de `ServerPlayer` elegível e alvo hostil.

## Pendências

**Nenhuma bloqueante.** A ausência de adapter genérico não é mais uma promessa de design; é fail-closed deliberado até existir um receipt equivalente.

## Testes

- [x] matemática rank/target switch;
- [x] clamp 100;
- [x] deduplicação por ação;
- [x] dano confirmado e autoria server-side;
- [x] CI/build e dedicated-server smoke exigidos antes do merge.
