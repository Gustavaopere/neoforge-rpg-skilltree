# A0198 — Dano de Eldritch I

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE`.**

Iron's Spells 'n Spellbooks 3.16.3 base já possui school e DamageType ELDRITCH nativos. Isso resolve a identidade elemental, mas não fornece o contrato de autoria/direct outcome exigido para modificar dano ofensivo com segurança.

Notion revalidado após hardening: `https://app.notion.com/p/3c569db9f0db814dacacf8a0d6efd3c0`.

## Contrato

- ARCANE; camada 4; Ramo; até 4 ranks; 1 PP/rank.
- Gate:
  - Gateway ARCANE ativo;
  - A0144 Poder Mágico ≥2;
  - `(investimento OCCULT ≥2 OU Eldritch Mastery ≥10)`.
- O agrupamento OR acima é parte do gate; VITALITY não substitui formação arcano-oculta.
- +3% de dano ELDRITCH direto elegível por rank:
  - rank 1: ×1,03;
  - rank 2: ×1,06;
  - rank 3: ×1,09;
  - rank 4: ×1,12.
- Teto próprio: +12%.

## Provider evidence

Snapshot Iron's auditado: `iron431/irons-spells-n-spellbooks@e4056af90302d37eb1739f5ff05020b020e6e252`.

`SchoolRegistry` registra `eldritch` usando `ISSDamageTypes.ELDRITCH_MAGIC`. Portanto ELDRITCH não é uma categoria temática inventada: existe identidade provider-native real.

Addons presentes no pack, como Discerning The Eldritch e Deeper and Darker Spellbooks, só podem participar pelo caminho semântico exato/versionado da school/provider. A presença do addon não cria automaticamente receipt DIRECT.

## Black Arcana

O Stage 06 canônico do Black Arcana (`6b77b5c0ec4f0ff4a8688bb105cef055860c061c`) implementa engine/eventos/ledger/reservas de rituais server-authoritative, mas não publica outcome ELDRITCH direto do Skill Tree.

Ritual, Corruption, Arcane Strain, Soul Energy ou estética ocultista não qualificam A0198 sem adapter explícito.

## Contract obrigatório

`DIRECT_MAGIC_OUTCOME_V1` player-owned, contendo identidade estável de ação/outcome e classificação ELDRITCH.

Pipeline futuro:

`provider action aceita → DIRECT_MAGIC_OUTCOME_V1(... school=ELDRITCH, direct=true ...) → A0198 aplica multiplicador uma vez ao componente ELDRITCH → settlement do mesmo outcome`.

## Deduplicação / causalidade

- uma aplicação A0198 por `outcome_id`;
- adapter classifica/prova; não cria dano paralelo;
- derived/secondary não vira direct por inferência;
- critical/authorship continuam do outcome pai;
- provider namespace/visual não substitui receipt.

## Exclusões

Não inferir ELDRITCH de:

- void/dark damage;
- End/dimension theme;
- teleport;
- curse/debuff genérico;
- occult school conceitual sem identidade exata;
- Corruption/Arcane Strain;
- Soul Energy/spirits;
- ritual;
- cor/VFX/nome/namespace;
- summon/DoT/automação/FakePlayer.

## Fail-closed

Sem `DIRECT_MAGIC_OUTCOME_V1`, A0198 é `UNAVAILABLE_NODE`: purchase falha antes do gasto; rank legado conta 0 PP nos gates e permanece reembolsável/migrável.

Não usar `ELDRITCH_MAGIC` isoladamente como prova de player-owned/direct e não converter a perk em spell-power genérico.

## Handoff Chat 2

Manter A0198 unavailable. Não criar producer DIRECT local nem promover Black Arcana/addons por associação temática.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend;
2. legacy unavailable =0 PP + refund/migration;
3. Iron's ELDRITCH identity positiva como classifier, mas sem DIRECT não ativa a perk;
4. direct player-owned ELDRITCH futuro aplica 3/6/9/12%;
5. aplicação uma vez por `outcome_id`;
6. void/dark/End/teleport/curse não qualificam por tema;
7. ritual Black Arcana Stage 06 não qualifica sem adapter;
8. addons Eldritch não bypassam direct receipt;
9. DoT/summon/automação/FakePlayer/derived negativos;
10. provider absent/version mismatch falha fechado sem crash.