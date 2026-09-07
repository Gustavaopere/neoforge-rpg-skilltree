# A0187 — Resistência a Sagrado II

## Estado Chat 1

**DESIGN APROVADO / IMPLEMENTÁVEL COM FALLBACK COMPONENT-WISE.**

A rota de baixa vida é implementável hoje no mesmo boundary/bucket de A0186. A rota alternativa de 80 ticks após cura HOLY efetiva permanece fail-closed porque a `main` não contém `HealingResolver` nem receipt canônico de cura HOLY. Omitir somente essa segunda condição preserva a identidade defensiva da perk e está de acordo com a política de fallback.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db81158487f117f98841e1`.

## Contrato

- VITALITY ↔ HEALING ↔ ARCANE; camada 5; Ramo; até 3 ranks; 1 PP/rank.
- Pré-requisito: A0186 ≥2.
- A0187 adiciona ao **mesmo** `RPG_HOLY_RESISTANCE`:
  - rank 1: +4%;
  - rank 2: +8%;
  - rank 3: +12%.
- A contribuição existe quando pelo menos uma condição é verdadeira:
  1. vida imediatamente anterior ao impacto está **estritamente `<50%`** da vida máxima; ou
  2. futura `holy_heal_window` de 80 ticks está validamente ativa.
- As duas condições são OR e nunca somam magnitude entre si.
- A0186 max + A0187 max sob condição = 28% de contribuição local da família; não é cap defensivo global.

## Rota implementável atual — baixa vida

No `LivingDamageEvent.Pre`:

1. classificar DamageSource HOLY pelo mesmo adapter/resolver de A0186;
2. ler health/maxHealth **pré-impacto**;
3. somente se `health / maxHealth < 0.5`, adicionar +4%/rank ao mesmo bucket;
4. exatamente 50% não ativa;
5. mutar dano uma única vez no resolver compartilhado.

Não usar vida projetada após o hit.

## Rota futura — `HOLY_HEAL_RECEIPT_V1`

A `holy_heal_window` só poderá existir quando um receipt canônico comprovar:

- cura efetivamente aplicada `>0` após overheal;
- beneficiário;
- autoria/origem causal quando exigida;
- classificação HOLY explícita;
- action/outcome/receipt identity deduplicável;
- confirmação após settlement de cura.

Ao receber receipt elegível, registrar/atualizar `holy_heal_expiry_tick = now + 80` uma vez pelo evento causal.

Sem receipt, não criar janela e não inferir cura HOLY por spell school, partícula, absorção, regen ou oração.

## Fallback component-wise

A ausência do receipt de cura remove somente a condição 2. A condição de baixa vida permanece funcional. Isso não cria uma segunda perk: o efeito continua sendo aumento condicional da mesma Resistência HOLY.

Se o classifier de dano HOLY estiver indisponível, o evento inteiro fica inelegível.

## Deduplicação

- mesmo `ElementalDamageMitigationResolver` de A0186;
- mesmo `RPG_HOLY_RESISTANCE`;
- condições OR não aplicam o bucket duas vezes;
- receipt futuro de cura arma/renova uma janela, não adiciona resistência imediatamente;
- callbacks duplicados do mesmo receipt não estendem indefinidamente sem identidade causal.

## Anti-abuso

Overheal com cura efetiva zero, absorção, regeneração genérica, heal não-HOLY, tick de efeito e reaplicação duplicada não armam a janela. Não conceder Mastery por janela, cura ou dano recebido.

## Handoff Chat 2

Implementar A0187 agora somente pela rota de baixa vida no resolver/bucket de A0186. Não criar `HealingResolver` local nem janela sem receipt. Deixar a segunda rota explicitamente fail-closed até capability futura aprovada.

## Testes obrigatórios para Chat 3

1. A0186≥2 obrigatório;
2. ranks 0–3 = 0/4/8/12% adicionais;
3. 49,999% HP ativa; exatamente 50% não ativa; >50% não ativa;
4. usar vida pré-impacto, não pós-hit;
5. A0186 max + A0187 max = 28% contribuição local quando condição ativa;
6. sem `HOLY_HEAL_RECEIPT_V1`, cura/regen/absorção não armam janela;
7. futuras duas condições simultâneas não duplicam magnitude;
8. bucket processado uma vez;
9. provider mismatch fail-closed;
10. reload/logout/dimensão/dedicated-server safety.