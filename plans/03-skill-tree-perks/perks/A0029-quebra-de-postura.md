# A0029 — Quebra de Postura

## Estado

- **Design:** APROVADO após auditoria retroativa.
- **Implementação:** NÃO CONFIRMADA; `P-A0029-01` aberta. A restauração de stamina possui fallback canônico.
- **Notion:** `3c569db9-f0db-81eb-8a3e-c67450cbc041`.

## Contrato canônico

- A0026 ≥2 + A0027 ≥1 + gateway `epic_hammer`.
- Com 3 Abalo, o próximo hit direto HAMMER/heavy inequivocamente confirmado consome as 3 cargas.
- Rank 1/2: +30%/+45% pressão de guarda/postura e +10%/+15% impacto.
- Se heavy for confirmado, mas pressão de guarda não existir, manter impacto-only.
- Restauração de 10% do custo de stamina somente se a mesma ação causar quebra real e o custo exato pago for observável; cooldown 8 s. Sem receipt de custo, omitir apenas a restauração.

## Auditoria — 9 eixos

1. Gates: PASS.
2. Integração: PASS de design — Epic Fight owner de heavy/impact/stamina/guarda.
3. Identidade: PASS — gasto de preparação em ataque pesado real.
4. Topologia: PASS — Notable convergente.
5. Especializações: PASS — exterior.
6. PT-BR: PASS.
7. Notion: PASS após endurecimento causal.
8. NeoVitae: PASS.
9. Providers: PASS de design; heavy receipt ausente no adapter atual.

## Evidência e pendências

- `A0021A0040CombatPolicy` exige `abalo>=3 && heavyConfirmed` antes de consumir cargas.
- O adapter atual envia `heavyConfirmed=false`, então A0029 nunca ativa no caminho Epic Fight real.
- **P-A0029-01:** Chat 2 deve encontrar/integrar receipt provider-native inequívoco de heavy attack. É proibido inferir por animação, dano, arma lenta, impacto ou charge time estimado.
- A parcela de stamina é fallback legítimo: sem custo exato da mesma ação, omitir restauração de 10% sem redesenhar a perk.
- `ARCANE_BACKLASH` e companions Mobstein não satisfazem heavy, guard-break ou stamina receipt.

`P-A0029-01` bloqueia `IMPLEMENTAÇÃO CONFIRMADA`, não o design.
