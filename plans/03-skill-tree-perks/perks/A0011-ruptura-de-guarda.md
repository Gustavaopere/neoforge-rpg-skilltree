# A0011 — Ruptura de Guarda

## Status e proveniência

- **Design:** APROVADO após correção canônica.
- **Código relevante:** PRESENTE.
- **Implementação:** CONFIRMÁVEL após CI/merge.
- **Notion:** https://app.notion.com/p/3c569db9f0db812bb55bf30113d24b9a
- **Critérios locais:** `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`.

## Especificação canônica

- Requer A0008 ≥2 + A0009 ≥1 e pelo menos 40 Fúria.
- Hit direto de machado contra guarda/postura real pode gastar 20 Fúria.
- Rank 1: +20% impacto/pressão e até 6% penetração; rank 2: +35% e até 10%.
- Se guarda/postura não for observável, somente defesa física server-side comprovável autoriza fallback de penetração-only.
- A antiga condição de “alvo classificado como pesado” foi removida: não havia provider obrigatório com classificação segura e qualquer aproximação por vida/tamanho/knockback seria heurística proibida.

## Auditoria — 9 eixos

1. **Gates:** PASS — A0008/A0009 + gateway.
2. **Integração global:** PASS — consome Fúria canônica e usa `IMPACT`/`ARMOR_NEGATION`.
3. **Identidade:** PASS — gasto deliberado para romper defesa real.
4. **Topologia:** PASS — Notable camada 3.
5. **Especializações:** PASS — exterior.
6. **PT-BR:** PASS.
7. **Notion:** PASS após remoção da condição não implementável.
8. **NeoVitae:** PASS.
9. **Modlist/integrações:** PASS — provider-native primeiro, fallback físico estrito.

## Evidência técnica

- `NotionCombatPerkRules`: threshold 40, custo 20, impacto 1,20/1,35, penetração 0,06/0,10.
- `A0001A0020CombatPolicy.beforeHit`: `nativeDefense` ou `armorFallback`; uma única claim `A0011:spend`.
- Se defesa é observável e ausente, Armor não ativa a perk.
- Fallback por Armor só existe quando o adapter não consegue observar guarda/postura e aplica somente penetração.
- `A0001A0020CombatPolicyTest.ruptureUsesNativeDefenseOrStrictArmorFallback` cobre as três rotas.

## Pendências

**Nenhuma bloqueante.** A antiga P-A0011-01 deixou de existir porque a condição de alvo pesado foi removida do design canônico em vez de ser substituída por heurística.

## Testes

- [x] valores e gasto de Fúria;
- [x] defesa nativa;
- [x] rejeição de alvo observavelmente desprotegido;
- [x] fallback de penetração-only;
- [x] CI/build e dedicated-server smoke exigidos antes do merge.

## Fechamento Chat 1 V3 — ciclo exato A0011–A0020

- **Re-fetch canônico:** Notion consultado novamente em 2026-08-30; efeito, dependências, gate, hook, fallback e regra persistem sem drift.
- **Mutação no Notion neste ciclo:** não necessária.
- **Provider-native first:** guarda/postura observável tem precedência; Armor só qualifica a rota de penetração quando o adapter não consegue observar guarda/postura.
- **Deduplicação/custo:** um único `A0011:spend` por root action; 20 de Fúria só são consumidos quando existe componente seguro aplicável.
- **Proibição:** não restaurar a heurística de “alvo pesado”, nem inferir defesa por vida, tamanho, knockback, aparência ou dano recebido.
- **Resultado:** **APROVADA / FECHADA** no lote A0011–A0020.