# A0148 — Conjuração Rápida

## Estado Chat 1

**DESIGN APROVADO.**

A perk é implementável no Iron's 3.16.3 por `AttributeRegistry.CAST_TIME_REDUCTION`. O design preserva a semântica nativa de LONG/CONTINUOUS/INSTANT e não impõe fórmula universal ao provider.

## Contrato

- Domínio ARCANE; ramo Técnica de Conjuração — Velocidade; camada 2; Ramo.
- Até 4 ranks; 1 PP/rank.
- Pré-requisitos: A0144 ≥2 + A0145 ≥2.
- Contribuição de técnica: +0,02 / +0,04 / +0,06 / +0,08 sobre o atributo nativo do Iron's.
- Cooldown, attack speed, duração de efeito e animação cosmética são grandezas distintas.

## Iron's Spells 'n Spellbooks 3.16.3

Snapshot: `iron431/irons-spells-n-spellbooks@e4056af90302d37eb1739f5ff05020b020e6e252`.

`AbstractSpell.getEffectiveCastTime(...)` é o consumer do `AttributeRegistry.CAST_TIME_REDUCTION`:

- `INSTANT`: cast time nativo = 0;
- não-CONTINUOUS: usa `2 - Utils.softCapFormula(attribute)`;
- CONTINUOUS: usa diretamente o valor do atributo segundo a regra nativa do provider.

Hook aprovado: `NodeEffectRuntime → modifier estável no CAST_TIME_REDUCTION`, preservando a semântica acima.

Não substituir isso por `tempo_nativo / (1 + bônus)` dentro do Iron's, porque essa fórmula alteraria o comportamento nativo de continuous/channel.

## Ars Nouveau 5.13.1 e outros providers

Nenhum contrato genérico de cast-time foi aprovado neste ciclo. Só entram por `FUTURE_PROVIDER_CONTRACT` quando uma forma/spell possuir estágio temporal server-authoritative e modificável.

Acelerar animação sem alterar o tempo lógico não satisfaz a perk.

## Availability

A0145 é predecessor estrutural e deve ser capability-eligible conforme seu próprio contrato. Rotas laterais não substituem esse requisito.

A ativação por cast exige canal temporal suportado. Iron's INSTANT não qualifica; Ars/outros permanecem fail-closed por canal sem hook.

## Fallback

Sem hook seguro, omitir apenas aquele provider/spell. Não converter A0148 em cooldown reduction, attack speed, effect duration, dano ou mana economy.

## Handoff Chat 2

- Iron's: modifier estável somente em `CAST_TIME_REDUCTION`;
- preservar `getEffectiveCastTime()` como authority;
- não implementar fórmula universal paralela;
- Ars/outros permanecem fail-closed até contrato versionado;
- refresh/rank/respec deve ser idempotente.

## Testes Chat 3

1. ranks +0,02/+0,04/+0,06/+0,08 no atributo nativo;
2. INSTANT permanece 0;
3. LONG/non-continuous segue soft-cap nativo;
4. CONTINUOUS preserva semântica própria do provider;
5. cooldown/attack speed/effect duration inalterados;
6. Ars sem hook permanece inativo;
7. no visual-only acceleration;
8. modifier idempotente após login/reload/respec;
9. provider removal/absence fail-soft.