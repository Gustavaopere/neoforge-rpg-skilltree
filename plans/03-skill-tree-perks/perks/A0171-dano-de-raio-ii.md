# A0171 — Dano de Raio II

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE`.**

Chat 1 não implementa runtime. A identidade da perk foi preservada, mas ela não pode ser adquirida no estado atual porque ainda não existem na `main` os dois contratos necessários para provar um outcome mágico LIGHTNING direto e consumir atomicamente um estado elétrico real do alvo.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db81fb909fcacd96281d30`.

## Contrato de gameplay

- ARCANE/LIGHTNING; camada 5; Notable; 1 rank; 2 PP.
- Dependências: A0170 Dano de Raio I rank ≥3 + Lightning Mastery ≥20.
- O alvo deve possuir **antes do impacto** um `lightning_state_id` real, removível, consumível e explicitamente allowlisted pelo adapter da versão.
- Um hit mágico LIGHTNING direto elegível do jogador consome exatamente uma instância desse estado e aplica `×1,18` ao componente LIGHTNING direto do mesmo outcome.
- Cooldown interno: 80 ticks (4 s), iniciado somente quando o consumo e o bônus forem efetivamente commitados.
- O primeiro hit que cria o estado não recebe o bônus.

## Authority e classifiers

- Iron's Spells 'n Spellbooks 3.16.3 possui identidade LIGHTNING explícita, mas isso não fornece automaticamente um estado consumível no alvo.
- `CHARGED` do Iron's é self-buff do caster e **não** é `lightning_state_id` de alvo para A0171.
- Ars Nouveau 5.13.1 / Ars Elemental 0.7.10.1 só podem fornecer estado elétrico quando um adapter versionado provar identidade, instância, remoção e consumo.
- FE, Create, Oritech, máquinas, partículas, alvo molhado ou eletricidade temática não provam estado LIGHTNING mágico.

## Blockers canônicos

Capacidades requeridas:

1. `DIRECT_MAGIC_OUTCOME_V1` — autoria, `action_id/outcome_id`, DIRECT vs derived e componente LIGHTNING explícito;
2. `LIGHTNING_CONSUMABLE_STATE_V1` — `state_id`, alvo, instância pré-existente, regra de consumo e adapter exato/versionado.

A0170 também permanece indisponível enquanto `DIRECT_MAGIC_OUTCOME_V1` não existir, portanto a dependency closure já bloqueia A0171 no snapshot atual.

## Pipeline futuro obrigatório

`provider direct spell -> DIRECT_MAGIC_OUTCOME_V1 -> verificar state PRE-existente allowlisted -> reservar consumo -> aplicar ×1,18 somente ao componente LIGHTNING do mesmo outcome -> commit atômico consumo+bônus -> iniciar CD 80t`.

Se qualquer etapa falhar, nada é consumido e o cooldown não começa.

## Deduplicação e anti-abuso

- no máximo uma conversão A0171 por `outcome_id`;
- estado criado pelo próprio outcome não é elegível como PRE-state;
- DoT, summon/minion, fake player, automação, dano ambiental, componente derivado e tecnologia são inelegíveis;
- nenhum segundo `hurt`, DamageSource, crítico ou proc pode ser criado;
- Mastery não é concedida por consumo do estado, tick, duração do efeito ou cooldown.

## Fail-closed / disponibilidade

Enquanto faltar qualquer blocker ou dependência:

- compra falha antes do gasto;
- rank legado indisponível vale 0 PP em gates/thresholds e permanece reembolsável/migrável;
- não degradar para bônus LIGHTNING genérico;
- não tratar `CHARGED`, Wet, Slowness ou qualquer efeito visual como estado de alvo.

## Handoff Chat 2

Implementar somente o estado fail-closed deste dossiê. Não criar producer local de direct magic outcome e não inventar um estado elétrico universal. Se surgir um adapter real que feche `LIGHTNING_CONSUMABLE_STATE_V1`, a alteração de availability deve voltar ao Chat 1 antes de mudar a semântica aprovada.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend enquanto blockers/dependência faltarem;
2. rank legado indisponível = 0 PP e reembolsável/migrável;
3. `CHARGED` do Iron's inelegível como state de alvo;
4. state criado pelo hit atual não ativa;
5. state PRE-existente allowlisted ativa uma vez;
6. consumo e ×1,18 são atômicos;
7. cooldown só inicia no commit elegível e dura 80t;
8. DoT/summon/fake player/automação/FE/Create/Oritech negativos;
9. dedup por outcome em multiplayer/reload;
10. provider/version mismatch permanece fail-closed.
