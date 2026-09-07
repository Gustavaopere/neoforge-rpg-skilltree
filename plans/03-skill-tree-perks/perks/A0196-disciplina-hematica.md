# A0196 — Disciplina Hemática

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE`.**

A0196 é um keystone de risco/recompensa inseparável: aumenta dano BLOOD direto em baixa vida e simultaneamente reduz cura externa efetivamente recebida. O snapshot atual não possui um contrato canônico suficiente para autoria DIRECT e autoria de cura externa; portanto não é permitido implementar apenas a metade benéfica.

Notion revalidado após hardening: `https://app.notion.com/p/3c569db9f0db815eba92d17890f6b2e3`.

## Contrato

- ARCANE; camada 6; Keystone; 1 rank; 2 PP.
- Dependências:
  - A0191 ≥3;
  - Blood Mastery ≥30;
  - pelo menos um: A0192=1, A0194≥2 ou A0195≥2.
- No primeiro outcome BLOOD direto elegível e aceito `>0` de uma `action_id`, se a vida registrada para a ação antes do primeiro outcome direto elegível estiver estritamente `<60%` da máxima, conceder 1 carga `HEMATIC_DISCIPLINE`.
- Máximo 3 cargas.
- Máximo 1 carga por `action_id`.
- Duração compartilhada: 160 ticks / 8 s.
- Nova carga renova a expiração compartilhada.

Por carga:

- dano BLOOD direto elegível: +4%;
- cura externa efetivamente recebida: −4%.

Escala:

| Cargas | Dano BLOOD | Cura externa recebida |
|---:|---:|---:|
| 0 | ×1,00 | ×1,00 |
| 1 | ×1,04 | ×0,96 |
| 2 | ×1,08 | ×0,92 |
| 3 | ×1,12 | ×0,88 |

## Contracts obrigatórios

- `DIRECT_MAGIC_OUTCOME_V1`;
- `EXTERNAL_HEAL_ATTRIBUTION_V1` ou boundary canônico equivalente que prove cura efetiva e autoria externa.

Um simples hook de healing sem autoria causal suficiente não serve para classificar “cura externa”.

## Snapshot e geração de cargas

O `action_health_snapshot` deve ser capturado no início/commit da ação BLOOD de forma estável para toda a ação. A geração ocorre apenas no primeiro outcome BLOOD direto aceito `>0` daquela ação.

- `<60%` = elegível;
- exatamente 60% = não elegível;
- outcomes posteriores da mesma `action_id` não adicionam cargas;
- callback duplicado não adiciona carga extra.

## Cura externa

Somente cura efetiva recebida cuja autoria seja comprovadamente externa ao jogador recebe a penalidade.

Não classificar como externa:

- self-heal;
- sustain/lifesteal próprio;
- regen sem autor causal externo comprovado;
- autoria ambígua;
- absorção.

A penalidade deve atuar no settlement de cura efetiva, não em overheal inexistente nem em um valor teórico pré-cap.

## Provider separation

Iron's 3.16.3 fornece identidade BLOOD real, mas não o contrato inteiro de direct outcome + external heal attribution.

Vampirism mantém resource/lifesteal/sustain próprios e não é convertido em ledger da Disciplina. Black Arcana Stage 06 atual não publica outcome BLOOD equivalente.

## Fail-closed all-or-nothing

Benefício e tradeoff são inseparáveis. Se a autoria de cura externa não puder ser aplicada com segurança, A0196 fica integralmente unavailable.

É proibido:

- manter +4%/carga removendo a penalidade;
- substituir a penalidade por custo de HP;
- inventar `BLOOD_MAGIC_COST`;
- usar Corruption/Arcane Strain como substituto;
- usar Vampirism blood meter como recurso Skill Tree.

Purchase fail-before-spend; legacy unavailable =0 PP para gates e reembolsável/migrável.

## Lifecycle

Morte, logout, mudança de dimensão e respec relevante limpam cargas/expiry transitórios. Reload não pode reconstruir cargas por heurística.

## Handoff Chat 2

Preservar A0196 `UNAVAILABLE_NODE`. Não criar `HealingResolver` local só para esta perk e não implementar metade do tradeoff.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend;
2. legacy unavailable =0 PP + refund/migration;
3. somente primeiro outcome direto BLOOD aceito da action pode gerar carga;
4. dano zero/cancelado não gera;
5. snapshot 59,999% gera; exatamente 60% não;
6. no máximo 1 carga por `action_id`;
7. máximo 3 cargas;
8. nova carga renova expiry 160t;
9. multiplicadores 1,00/1,04/1,08/1,12 e 1,00/0,96/0,92/0,88;
10. cura externa comprovada sofre penalidade;
11. self-heal/sustain/autoria ambígua não são cura externa;
12. sem external-heal contract, nenhum bônus parcial fica ativo;
13. DoT/summon/automation/self-damage/custos/derived não geram cargas;
14. morte/logout/dimensão/respec limpam estado;
15. duplicate callback é idempotente.