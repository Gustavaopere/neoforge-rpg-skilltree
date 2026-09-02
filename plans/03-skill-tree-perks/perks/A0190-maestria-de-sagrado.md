# A0190 — Maestria de Sagrado

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE` TRANSITIVO.**

A0190 é a terminal exterior HOLY e representa somente Gate C. A dependency closure obrigatória passa por A0189, que está `UNAVAILABLE_NODE`; portanto A0190 não é comprável no snapshot atual.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db814ebeb8f848e6c8cde3`.

## Contrato

- ARCANE; camada 7; Capstone exterior; 1 rank; 3 PP.
- Compra: A0189 + Holy Mastery ≥80 + pelo menos um entre A0185=1, A0187≥2 ou A0188≥2.
- A0190 não concede Julgamento, Exorcismo, cura, absorção, aura, dano, afinidade adicional ou resistência.
- Possuir A0190 satisfaz somente **Gate C** da Specialist Sagrado.
- Specialist Sagrado exige simultaneamente:
  - Gate A: fundamentos semânticos exteriores exigidos;
  - Gate B: ≥100 Passive Points válidos em `SPECIALIST_REGION:HOLY`;
  - Gate C: A0190 possuída.

## Authority / pipeline canônico

Não criar `SpecialistGateResolver` paralelo. Reutilizar o runtime canônico já existente:

- `TreeUnlockResolver`;
- `TreeUnlockDefinition`;
- projeção canônica de investimento do Stage 04.01.

A0190 somente publica/representa a identidade terminal HOLY para Gate C. Gate A/B permanecem responsabilidade da avaliação semântica canônica; geometria/UI nunca substituem requisito.

## Fail-closed

Enquanto A0189 estiver unavailable:

- compra de A0190 falha antes do gasto;
- legacy rank unavailable vale 0 PP em gates e permanece reembolsável/migrável;
- não voltar aos gates antigos de 8 PP HOLY / ARCANE 12;
- não liberar Specialist por rota Healing/Martial curta;
- não contar bridge PP em dois thresholds;
- não inventar unlock pela presença do Iron's/Eidolon.

## Specialist region / respec seguro

`TERMINAL_EXTERIOR: ARCANE/HOLY` e `PP_REGION: ARCANE/HOLY`.

`SPECIALIST_REGION:HOLY` = núcleo ARCANE compartilhado explicitamente elegível + PP ARCANE/HOLY; nodes específicos FIRE/ICE/LIGHTNING/NATURE/BLOOD/ELDRITCH e bridges ficam fora salvo whitelist explícita e única.

Enquanto qualquer perk interna da Specialist Sagrado estiver possuída, o respec deve impedir refund que remova A0190, fundamentos obrigatórios, dependency closure da terminal ou reduza Gate B abaixo de 100. A Specialist deve ser reembolsada primeiro.

## Providers

Iron's Spells 'n Spellbooks 3.16.3 é provider HOLY principal nos nodes que possuam adapters próprios. Eidolon: Repraised 0.5.0.2 só participa onde houver contrato explícito. Nenhum provider externo possui o gate Specialist.

O Stage 06 canônico do Black Arcana não altera isso: seus rituais/ledgers têm authority própria e não produzem terminal HOLY do RPG Skill Tree.

## Handoff Chat 2

Preservar A0190 como `UNAVAILABLE_NODE` transitivo. Não criar resolver Specialist novo e não implementar unlock paralelo. Se A0189/direct thermal closure for resolvida futuramente, promoção de A0190 volta ao Chat 1 para confirmar a dependency chain.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend enquanto A0189 unavailable;
2. legacy rank unavailable = 0 PP e refund/migration preservados;
3. Gate C isolado nunca libera Specialist;
4. Gate A sem Gate B/C falha;
5. Gate B ≥100 sem Gate A/C falha;
6. A0190 + Gate A + Gate B válidos libera somente pela avaliação canônica;
7. 99 PP falha, 100 PP passa quando demais gates válidos;
8. bridge PP não é contado duas vezes;
9. respec seguro bloqueia quebra de Gate A/B/C com perks internas possuídas;
10. provider absent/version mismatch não cria unlock implícito;
11. reload/login/migração reavaliam sem geometria/UI como authority;
12. ausência de `SpecialistGateResolver` paralelo / uma única pipeline de unlock.