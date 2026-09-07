# A0159 — Resistência a Fogo II

## Estado Chat 1

**DESIGN APROVADO / IMPLEMENTÁVEL NO MESMO BOUNDARY DE A0158.**

Não existe segundo reducer: A0159 apenas contribui condicionalmente ao mesmo `RPG_FIRE_RESISTANCE`.

## Contrato

- VITALITY; camada 5; Ramo; 3 ranks; 1 PP/rank.
- Pré-requisito: A0158 ≥2.
- Se `health / maxHealth < 0,50` no snapshot PRE-impacto: +4% FIRE resistance/rank, máximo +12%.
- Exatamente 50% ou acima: contribuição 0.
- A0158 máximo + A0159 máximo = 28% local no mesmo bucket.

## Authority e hook

No `LivingDamageEvent.Pre`, capturar vida e vida máxima server-authoritative antes de alterar `newDamage`. A mesma classificação FIRE e o mesmo `DamageMitigationResolver` de A0158 resolvem a soma uma única vez.

## Ordem

1. classificar o DamageSource;
2. capturar pre-impact health ratio;
3. agregar A0158 e, quando `<50%`, A0159;
4. aplicar o bucket uma única vez;
5. nunca reavaliar o threshold usando vida já reduzida por este impacto.

## Exclusões

- usar predicted post-hit health;
- ativar em exatamente 50%;
- segundo `setNewDamage` específico de A0159;
- thermal/Volcanoes/Armor/magic resistance geral;
- contar A0159 quando A0158/predecessor perdeu eligibility.

## Handoff Chat 2

Implementar como contribuição do resolver compartilhado, não como listener separado. Perda de A0158/rank/prerequisite invalida a contribuição imediatamente.

## Testes Chat 3

1. 49,999% ativa e exatamente 50% não ativa;
2. ranks 1–3: 4/8/12%;
3. A0158 max + A0159 max = 28% no mesmo bucket;
4. somente uma aplicação de mitigation;
5. snapshot anterior ao dano;
6. respec/rules reload/prerequisite loss;
7. multiplayer e dedicated server.