# A0184 — Dano de Sagrado I

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE`.**

Iron's Spells 'n Spellbooks 3.16.3 fornece identidade HOLY real, mas a `main` ainda não possui `DIRECT_MAGIC_OUTCOME_V1`. Classificação elemental não substitui autoria/DIRECT.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db815c9c42c285872e62ee`.

## Contrato de gameplay

- ARCANE ↔ HEALING; camada 4; Ramo/fundamento exterior; até 4 ranks; 1 PP/rank.
- Pré-requisito: Gateway ARCANE + A0144 Poder Mágico ≥2.
- Resultado mágico ofensivo HOLY direto elegível do jogador recebe:
  - rank 1: ×1,03;
  - rank 2: ×1,06;
  - rank 3: ×1,09;
  - rank 4: ×1,12.
- Teto próprio: +12%.
- Cura, absorção, blessing, DoT derivado, summon e dano não-HOLY não entram.

## Evidência provider-native

Iron's 3.16.3, snapshot auditado `e4056af90302d37eb1739f5ff05020b020e6e252`, registra:

- school `irons_spellbooks:holy`;
- `ISSDamageTypes.HOLY_MAGIC`;
- tag de DamageType `irons_spellbooks:holy_magic` / `HOLY_MAGIC`;
- a tag HOLY integra o conjunto magic do provider.

Isso prova classifier HOLY. Não prova, sozinho, que qualquer callback representa um outcome DIRECT do jogador.

Eidolon: Repraised 0.5.0.2 só participa quando uma ação ofensiva concreta tiver adapter HOLY versionado; ritual, luz, oração/reputação ou tema religioso não classificam dano por si só.

## Blocker — `DIRECT_MAGIC_OUTCOME_V1`

O producer futuro precisa fornecer:

- `action_id/outcome_id`;
- player ownership causal;
- DIRECT vs derived;
- classifier HOLY explícito;
- valor do componente no pipeline canônico de dano mágico.

A0184 deve aplicar sua camada exatamente uma vez por outcome.

## Pipeline futuro

`provider spell event/outcome -> DIRECT_MAGIC_OUTCOME_V1 -> Potência Mágica universal -> camada HOLY A0184 ×1.03..×1.12 -> demais especializações -> damage settlement`.

Não criar segundo `hurt` nem aplicar novamente a componentes derivados.

## Fail-closed

Enquanto o producer direct estiver ausente:

- compra falha antes do gasto;
- rank legado unavailable vale 0 PP e é reembolsável/migrável;
- não degradar para dano mágico genérico;
- não inferir HOLY por alvo undead, luz, partícula, cura associada, nome ou simples presença do provider.

## Specialist semantics

`FUNDAMENTO_EXTERIOR: ARCANE/HOLY`. `PP_REGION: ARCANE/HOLY`. Pode compor Gate A da Specialist Sagrado quando explicitamente mapeada, mas nunca substitui Gate B ≥100 PP ou terminal.

## Handoff Chat 2

Preservar fail-closed. Não criar producer direct exclusivo de A0184. Quando `DIRECT_MAGIC_OUTCOME_V1` existir canonicamente, qualquer promoção de availability volta ao Chat 1 para reconciliação.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend;
2. legacy unavailable =0 PP/refund;
3. Iron's `HOLY_MAGIC` classifica HOLY mas não ativa sem direct outcome;
4. luz/undead/cura/religião não classificam dano HOLY;
5. provider absent/version mismatch fail-closed;
6. quando producer existir: ranks 1,03/1,06/1,09/1,12;
7. uma aplicação por outcome_id;
8. derived component não reescala;
9. nenhuma segunda chamada de dano/crítico;
10. multiplayer/reload lifecycle.