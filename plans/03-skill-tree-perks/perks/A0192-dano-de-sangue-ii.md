# A0192 — Dano de Sangue II

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE`.**

A0192 exige, além do outcome BLOOD direto, um estado BLOOD real e correlacionável que possa abrir uma janela por alvo. Nenhum `BLOOD_STATE_WINDOW_RECEIPT_V1` seguro foi comprovado no snapshot atual.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db81d8bf0fd94b531c755c`.

## Contrato

- ARCANE; camada 5; Notable; 1 rank; 2 PP.
- Pré-requisito: A0191 ≥3 + Blood Mastery ≥20.
- Uma ação BLOOD direta elegível que aplique estado BLOOD real/allowlisted no alvo abre uma janela de **120 ticks / 6 s**.
- A janela é vinculada a jogador + alvo + `source_spell_id` e conserva `source_action_id`/`state_id`.
- Uma segunda ação BLOOD direta com `spell_id` diferente contra o mesmo alvo dentro da janela consome a abertura uma vez e aplica ao componente BLOOD do mesmo outcome:
  - ×1,15 se a vida PRE-impacto do alvo for ≥50%;
  - ×1,20 se a vida PRE-impacto do alvo for estritamente `<50%`.
- Exatamente 50% usa ×1,15.
- Cooldown interno: **140 ticks / 7 s**, iniciado somente no commit bem-sucedido.

## Contracts obrigatórios

A0192 depende de ambos:

- `DIRECT_MAGIC_OUTCOME_V1`;
- `BLOOD_STATE_WINDOW_RECEIPT_V1`.

O receipt de estado deve provar no mínimo:

- ator/jogador;
- alvo;
- `source_action_id`;
- `source_spell_id`;
- `blood_state_id` explicitamente allowlisted;
- aplicação/ownership causal;
- validade temporal;
- identidade deduplicável.

## Provider evidence

Iron's 3.16.3 possui school/DamageType BLOOD real, mas isso não prova que uma spell específica tenha aplicado um estado BLOOD removível/allowlisted nem publica por si só o receipt acima.

Black Arcana Stage 06 possui engine/ledger/reservas ritualísticas canônicas, mas não publica `BLACK_ARCANA_BLOOD_OUTCOME` nem receipt de estado BLOOD equivalente. Vampirism/bleed físico também não qualificam por tema.

## Pipeline / commit

`DIRECT BLOOD action 1 → estado BLOOD real aplicado → receipt arma janela 120t → DIRECT BLOOD action 2 diferente no mesmo alvo → snapshot HP PRE → reservation → modifier ×1,15/×1,20 no mesmo outcome → consumo da janela + cooldown 140t em commit atômico`.

Falha/cancelamento/outcome sem dano elegível não consome a janela nem inicia cooldown.

## Deduplicação e anti-abuso

- mesma `spell_id` da ação que armou não ativa;
- alvo diferente não ativa;
- janela expirada não ativa;
- callback duplicado do mesmo `action_id/outcome_id` não ativa duas vezes;
- derived component não arma nem consome;
- self-damage/custo voluntário não arma;
- DoT/summon/automação/FakePlayer não armam;
- generic bleed, maldição, partículas ou Vampirism blood bar não são estado BLOOD por inferência.

## Fail-closed

Sem DIRECT ou state-window receipt, o node inteiro fica unavailable. Não substituir o estado por um simples DamageType BLOOD, não criar debuff RPG sintético apenas para habilitar a perk e não usar `last attacker`/namespace/VFX para ownership.

Purchase falha antes do gasto; rank legado unavailable = 0 PP para gates, permanecendo reembolsável/migrável.

## Handoff Chat 2

Manter A0192 `UNAVAILABLE_NODE`. Não implementar janela por heurística e não criar producer de estado local.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend;
2. rank legado unavailable =0 PP + refund/migration;
3. estado BLOOD real/direct action 1 arma janela 120t quando contracts existirem;
4. mesma `spell_id` não ativa;
5. spell diferente, mesmo alvo, janela válida ativa uma vez;
6. HP PRE 49,999% usa ×1,20; exatamente 50% usa ×1,15;
7. 120t boundary correto;
8. cooldown 140t só começa no commit elegível;
9. cancel/zero/rollback não consome janela nem inicia cooldown;
10. generic bleed/Vampirism/custo HP não arma;
11. target diferente não consome janela;
12. callback duplicado/action-outcome duplicado é idempotente;
13. provider absent/version mismatch falha fechado.