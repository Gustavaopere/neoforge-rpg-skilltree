# A0199 — Dano de Eldritch II

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE`.**

A0199 é um notable all-or-nothing: o bônus de dano ELDRITCH e a penalidade temporária de regeneração do recurso primário do provider são inseparáveis. A `main` atual não possui os três contracts necessários para implementar esse commit composto com segurança.

Notion revalidado após reconciliação: `https://app.notion.com/p/3c569db9f0db81be8ff7d2c765f0a263`.

## Contrato

- ARCANE; camada 5; Notable; 1 rank; 2 PP.
- Pré-requisito: A0198 ≥3 + Eldritch Mastery ≥20.
- Uma ação ELDRITCH direta que aplique um `eldritch_state_id` real e allowlisted abre janela de **120 ticks / 6 s** por jogador + alvo + `source_spell_id`.
- Uma segunda ação ELDRITCH direta com `spell_id` diferente no mesmo alvo dentro da janela consome a abertura uma vez.
- No mesmo commit:
  1. componente ELDRITCH direto do segundo outcome recebe **×1,18**;
  2. regeneração nativa **positiva** do recurso primário de conjuração daquele provider recebe modifier **×0,85 por 80 ticks / 4 s**.
- Cooldown interno: **160 ticks / 8 s**, iniciado somente após o commit conjunto benefício + tradeoff.

## Contracts obrigatórios

Todos são obrigatórios:

- `DIRECT_MAGIC_OUTCOME_V1`;
- `ELDRITCH_STATE_WINDOW_RECEIPT_V1`;
- `PRIMARY_RESOURCE_REGEN_MODIFIER_V1`.

A identidade ELDRITCH do DamageType não substitui nenhum deles.

## Estado / window receipt

O receipt precisa preservar:

- jogador/ator;
- alvo;
- `source_action_id`;
- `source_spell_id`;
- `eldritch_state_id` real e allowlisted;
- aplicação causal;
- expiração;
- identidade deduplicável.

A segunda ação deve ter `spell_id` diferente. Mesmo spell não ativa. Outro alvo não ativa.

## Tradeoff de recurso

O provider precisa declarar qual é seu **recurso primário real de conjuração** e expor um seam seguro para modificar somente sua regeneração nativa positiva.

Regras:

- não converter Mana, Source, Soul Energy, spirits, HP ou reagente entre si;
- não inventar regeneração onde o recurso não possui;
- não alterar débito/custo do cast em vez de regen;
- valores zero/negativos de mudança de recurso não são “regen positiva” a modular;
- o modifier é temporário e deve ter lifecycle explícito.

## Commit all-or-nothing

`ELDRITCH action 1 aplica state → janela 120t → ELDRITCH action 2 diferente no mesmo alvo → reservar consumo/state + bonus + regen penalty → commit conjunto → ×1,18 no mesmo outcome + ×0,85 regen 80t + consumir janela + CD160`.

Se qualquer parte do tradeoff não puder ser aplicada, o bônus **não** é concedido e a janela/cooldown não podem ser consumidos parcialmente.

## Providers / authority

Iron's 3.16.3 fornece school/DamageType ELDRITCH nativos, mas isso não fornece state receipt nem seam canônico do recurso primário para A0199.

Discerning The Eldritch e Deeper and Darker Spellbooks só entram mediante adapters exatos/versionados que satisfaçam todos os contracts.

Black Arcana Stage 06 não publica state receipt ELDRITCH nem primary-resource regen modifier; ritual não é fallback.

## Exclusões / anti-abuso

Não ativam:

- mesma spell;
- debuff genérico;
- teleport/void/dark/End/occult por tema;
- ritual;
- VFX/nome/namespace;
- DoT;
- summons;
- automação/FakePlayer;
- derived component.

Dedup por `action_id/outcome_id`; callback duplicado não consome/beneficia duas vezes.

## Fail-closed

Sem qualquer um dos três contracts, A0199 permanece `UNAVAILABLE_NODE` integral. Purchase fail-before-spend; legacy unavailable =0 PP em gates e reembolsável/migrável.

É proibido manter +18% sem a penalidade ou substituir a penalidade por outro custo/recurso.

## Handoff Chat 2

Preservar A0199 unavailable. Não implementar somente o bônus, não inventar primary resource e não construir um regen hook local por perk.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend;
2. legacy unavailable =0 PP + refund/migration;
3. state real/direct action 1 arma janela 120t quando contracts existirem;
4. mesma spell não ativa;
5. spell diferente, mesmo alvo, ativa uma vez;
6. outro alvo não consome a janela;
7. commit aplica exatamente ×1,18 e regen ×0,85 por 80t juntos;
8. ausência de regen seam impede o bônus inteiro;
9. cooldown 160t só inicia após commit conjunto;
10. rollback/cancel/zero não produz consumo parcial;
11. não converter Mana/Source/Soul Energy/spirits/HP/reagentes;
12. zero/negative regen não é modulado como positiva;
13. generic debuff/void/teleport/ritual/theme não ativa;
14. duplicate action/outcome é idempotente;
15. lifecycle remove modifier após 80t e em cleanup relevante;
16. provider absent/version mismatch falha fechado.