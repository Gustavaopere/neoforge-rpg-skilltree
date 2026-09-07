# A0191 — Dano de Sangue I

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE`.**

A identidade BLOOD existe de forma nativa no Iron's Spells 'n Spellbooks 3.16.3, porém classificação elemental não prova autoria, ação direta, root/outcome ou deduplicação. A perk só pode operar quando o Skill Tree possuir um `DIRECT_MAGIC_OUTCOME_V1` canônico.

Notion revalidado após hardening: `https://app.notion.com/p/3c569db9f0db81c4a4a1c7eea1ee7ba1`.

## Contrato

- Domínio: ARCANE.
- Árvore: Principal — ARCANE ↔ OCCULT/VITALITY.
- Camada 4; função Ramo; tier Pequeno.
- Até 4 ranks; 1 PP/rank.
- Pré-requisito: Gateway ARCANE ativo + A0144 Poder Mágico ≥2.
- Efeito: +3% de dano mágico BLOOD direto elegível por rank:
  - rank 1: ×1,03;
  - rank 2: ×1,06;
  - rank 3: ×1,09;
  - rank 4: ×1,12.
- Teto próprio: +12%.

O multiplicador atua exclusivamente no componente BLOOD direto do mesmo outcome ofensivo mágico player-owned.

## Provider evidence

Snapshot exato auditado: `iron431/irons-spells-n-spellbooks@e4056af90302d37eb1739f5ff05020b020e6e252`.

`SchoolRegistry` registra a school `blood` usando `ISSDamageTypes.BLOOD_MAGIC`; a identidade de dano correspondente é semanticamente BLOOD. Isso é evidência suficiente para classificação elemental, mas não para produzir localmente um receipt de ação direta.

Vampirism 1.10.12 e seus addons/compatibilidades preservam sua própria economia de sangue, lifesteal, custos e curas. O fato de uma spell poder consumir/restaurar blood do Vampirism não transforma esse recurso em `DIRECT_MAGIC_OUTCOME_V1` nem em dano BLOOD do Skill Tree.

## Authority / pipeline canônico

Contrato futuro obrigatório:

`cast/action provider-native aceita → DIRECT_MAGIC_OUTCOME_V1(player, action_id, outcome_id, school=BLOOD, direct=true) → modifier A0191 uma única vez → settlement do mesmo outcome`.

O adapter pode classificar/provar autoria e identidade; ele não pode criar uma segunda pipeline de dano nem chamar `hurt` novamente.

## Causalidade e deduplicação

- apenas jogador real server-authoritative;
- `outcome_id` obrigatório e estável;
- A0191 no máximo uma vez por `outcome_id`;
- secondary area-chain só entra se o producer canônico declarar aquele outcome como DIRECT segundo o contrato aprovado;
- derived component nunca volta a ser raiz elegível;
- critical decision, autoria e demais metadados continuam pertencendo ao outcome pai.

## Exclusões / anti-abuso

Não qualificam por si só:

- bleed físico;
- perda/custo de HP;
- lifesteal;
- blood meter/economia do Vampirism;
- summon sacrificado;
- reagente ou mana consumidos;
- DoT;
- summon/companion;
- ambiente/reflect;
- automação/FakePlayer;
- cor, partículas, nome, namespace ou lore de sangue.

Custos de recurso continuam custos nativos e nunca são amplificados por A0191.

## Fail-closed

Enquanto `DIRECT_MAGIC_OUTCOME_V1` não existir na `main`:

- purchase de A0191 falha antes do gasto;
- rank legado unavailable conta 0 PP para gates/thresholds semânticos;
- rank legado permanece reembolsável/migrável;
- não usar `SpellDamageSource`, DamageType BLOOD ou school ID isoladamente como substituto de autoria/direct;
- não implementar um listener exclusivo da perk;
- não converter em bônus mágico geral.

## Handoff Chat 2

Preservar A0191 como `UNAVAILABLE_NODE`. Não criar producer DIRECT local para destravar esta perk. Se uma capability canônica surgir em trabalho transversal, sua compatibilidade com este contrato deve ser confirmada antes de promover o node.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend enquanto DIRECT estiver ausente;
2. legacy rank unavailable = 0 PP em gates + refund/migration;
3. Iron's BLOOD identity sozinha não ativa A0191 sem direct receipt;
4. direct player-owned BLOOD válido aplica 3/6/9/12% conforme rank quando o producer existir;
5. mesmo `outcome_id` duplicado aplica uma vez;
6. DoT, summon, ambiente, reflect, FakePlayer e automação não ativam;
7. bleed/lifesteal/Vampirism blood economy não classificam por tema;
8. custo de HP/mana/reagente não é outcome ofensivo;
9. provider ausente/version mismatch falha fechado sem crash;
10. reload/login/respec não ressuscitam benefício oculto.