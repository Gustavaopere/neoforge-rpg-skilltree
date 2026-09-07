# A0197 — Maestria de Sangue

## Estado Chat 1

**DESIGN APROVADO / `UNAVAILABLE_NODE` TRANSITIVO.**

A0197 é exclusivamente a terminal exterior BLOOD e representa somente Gate C da futura Specialist Sangue/Hemomancia. A0196 está unavailable no snapshot atual, portanto A0197 não é comprável.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db8165b62febe403ef8ecf`.

## Contrato

- ARCANE; camada 7; Capstone exterior; 1 rank; 3 PP.
- Compra da terminal:
  - A0196;
  - Blood Mastery ≥80;
  - pelo menos um: A0192=1, A0194≥2 ou A0195≥2.
- A0197 não concede dano, lifesteal, recurso de sangue, redução de custo, cargas, resistência ou qualquer outro pacote de poder.
- Possuir A0197 satisfaz somente **Gate C** da Specialist BLOOD.

## Pipeline canônico de unlock

Não criar `SpecialistGateResolver` paralelo.

Reutilizar exclusivamente:

- `TreeUnlockResolver`;
- `TreeUnlockDefinition`;
- `TreeUnlockCatalog`;
- projeção canônica de investimento do Stage 04.01.

A0197 apenas fornece a identidade terminal `ARCANE/BLOOD` para a avaliação canônica.

## Gate A / B / C da Specialist Sangue

A futura Specialist só pode ser liberada quando os três gates forem simultaneamente válidos:

- **Gate A:** fundamentos exteriores ARCANE/POWER + ARCANE/BLOOD explicitamente mapeados;
- **Gate B:** ≥100 Passive Points válidos em `SPECIALIST_REGION:BLOOD`;
- **Gate C:** A0197 possuída.

Topologia, posição gráfica, conexão visual ou presença do provider não substituem nenhum gate.

## SPECIALIST_REGION:BLOOD

A região deve incluir apenas:

- núcleo ARCANE compartilhado explicitamente elegível;
- nodes/PP ARCANE/BLOOD explicitamente elegíveis.

Por padrão ficam fora:

- FIRE/ICE/LIGHTNING/NATURE/HOLY/ELDRITCH específicos;
- bridges híbridas;
- nodes sem metadata semântica explícita.

Uma bridge só pode entrar por whitelist semântica explícita e sem dupla contagem em thresholds pais.

## Fail-closed transitivo

Enquanto A0196 permanecer unavailable:

- purchase de A0197 falha antes do gasto;
- legacy rank unavailable conta 0 PP para gates/thresholds;
- legacy permanece reembolsável/migrável;
- Gate C não pode ser satisfeito por geometria ou provider presence;
- não voltar aos antigos gates pequenos/8 PP;
- não criar `BLOOD_MAGIC_COST`, lifesteal ou recurso hemático para justificar unlock.

## Respec seguro

Enquanto qualquer perk interna da Specialist Sangue estiver possuída, impedir refund que:

- remova A0197;
- invalide fundamentos do Gate A;
- reduza Gate B abaixo de 100;
- quebre dependency closure da terminal.

As perks internas/Specialist devem ser reembolsadas primeiro.

## Providers

Iron's Spells 'n Spellbooks 3.16.3 é provider BLOOD dos nodes que possuam adapters aprovados. Vampirism mantém economia própria. Nenhum provider externo é authority do unlock da Specialist.

Black Arcana Stage 06 possui authority ritual própria e não publica a terminal BLOOD do Skill Tree.

## Handoff Chat 2

Preservar A0197 `UNAVAILABLE_NODE` transitivo. Não criar resolver Specialist novo. A0197 não precisa de runtime de poder próprio; seu futuro papel é somente Gate C na pipeline canônica.

## Testes obrigatórios para Chat 3

1. purchase falha enquanto A0196 unavailable;
2. legacy unavailable =0 PP + refund/migration;
3. A0197 isolada nunca libera Specialist;
4. Gate A sem B/C falha;
5. Gate B sem A/C falha;
6. Gate C sem A/B falha;
7. 99 PP falha, 100 PP passa quando A/C válidos;
8. `SPECIALIST_REGION:BLOOD` exclui elementos específicos e bridges por padrão;
9. bridge não conta duas vezes;
10. topologia/UI/provider presence não substitui Gate A/B/C;
11. respec seguro bloqueia quebra de requisitos com perks internas possuídas;
12. reload/login/migração reavaliam pela pipeline canônica;
13. nenhuma classe `SpecialistGateResolver` paralela deve ser introduzida;
14. A0197 não concede nenhum pacote de poder.