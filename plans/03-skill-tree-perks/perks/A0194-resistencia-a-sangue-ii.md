# A0194 — Resistência a Sangue II

## Estado Chat 1

**DESIGN APROVADO / IMPLEMENTÁVEL COM FALLBACK COMPONENT-WISE.**

A parcela principal de resistência em baixa vida é implementável hoje no mesmo boundary/bucket de A0193. A parcela secundária de redução de duração BLEED permanece fail-closed até existir um receipt seguro de aplicação/renovação com duração-base modificável.

Notion revalidado após reconciliação: `https://app.notion.com/p/3c569db9f0db8134842cf90e942c9aad`.

## Contrato

- VITALITY; camada 5; Ramo; até 3 ranks; 1 PP/rank.
- Pré-requisito: A0193 ≥2.

### Parcela 1 — resistência BLOOD condicional

Quando a vida **PRE-impacto** estiver estritamente `<50%` da vida máxima, adicionar ao mesmo `RPG_BLOOD_RESISTANCE`:

- rank 1: +4%;
- rank 2: +8%;
- rank 3: +12%.

Exatamente 50% não ativa. A0193 max + A0194 max = 28% de contribuição local BLOOD; não é cap global.

### Parcela 2 — duração BLEED

Quando um estado BLEED hostil, real, removível e modificável for criado ou renovado enquanto a vida **PRE-aplicação** estiver estritamente `<30%`, multiplicar sua **duração-base nativa** uma única vez:

- rank 1: ×0,98;
- rank 2: ×0,96;
- rank 3: ×0,94.

Exatamente 30% não ativa.

## Boundary da resistência

Mesmo `ElementalDamageMitigationResolver` e mesmo `RPG_BLOOD_RESISTANCE` de A0193 em `LivingDamageEvent.Pre`.

A vida usada para o gate é a vida antes do impacto atual, nunca a vida projetada depois de aplicar o dano.

## Boundary BLEED futuro

Exige `BLEED_DURATION_APPLICATION_V1` ou adapter equivalente que exponha antes do commit:

- estado BLEED explicitamente mapeado;
- origem hostil quando exigida;
- aplicação ou renovação causal;
- `base_duration` nativa;
- possibilidade real de modificar essa base;
- identidade deduplicável da aplicação.

A perk deve transformar a duração-base uma única vez naquela aplicação/renovação. É proibido reduzir `remaining_duration` a cada tick.

## Fallback component-wise

A ausência do seam BLEED desativa somente a parcela 2. Isso é fallback seguro porque a identidade principal da perk continua sendo resiliência BLOOD em baixa vida, já implementável pela parcela 1.

A ausência do classifier BLOOD, por outro lado, torna o evento de resistência inelegível.

`BLOOD` **não implica** `BLEED`.

## Anti-abuso / exclusões

Não inferir BLEED de:

- qualquer dano BLOOD;
- DoT físico genérico;
- Vampirism/lifesteal;
- self-damage/custo de HP;
- partículas/nome/namespace;
- estado autoaplicado como custo.

Estados de duração fixa/não modificável permanecem intocados.

## Handoff Chat 2

Implementar agora somente a parcela de resistência `<50%` no mesmo resolver/bucket de A0193. Manter a parcela BLEED fail-closed até receipt de aplicação/base-duration formalmente aprovado. Não criar ticker para encurtar duração restante.

## Testes obrigatórios para Chat 3

1. A0193≥2 obrigatório;
2. ranks 0–3 = 0/4/8/12% adicionais;
3. 49,999% HP PRE ativa; exatamente 50% não ativa;
4. usar PRE-impact, não pós-hit;
5. A0193 max + A0194 max = 28% local;
6. bucket aplicado uma única vez;
7. sem `BLEED_DURATION_APPLICATION_V1`, nenhum timer BLEED é alterado;
8. futuro BLEED: 29,999% PRE-aplicação ativa; exatamente 30% não ativa;
9. futuro BLEED usa duração-base uma vez por aplicação/renovação;
10. não reduzir remaining duration por tick;
11. generic BLOOD não classifica automaticamente BLEED;
12. self-cost/auto-bleed/Vampirism temáticos não qualificam;
13. provider mismatch e lifecycle permanecem fail-closed.