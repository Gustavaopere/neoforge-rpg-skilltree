# A0169 — Maestria de Gelo

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE` TRANSITIVO.**

A0169 é a terminal exterior do corredor ICE. Ela não concede poder bruto e satisfaz somente o Gate C da Specialist Gelo. A correção desta auditoria remove a premissa antiga de que faltaria um `SPECIALIST_GATE_V1`: o RPG Skill Tree já possui a pipeline canônica `TreeUnlockResolver` + `TreeUnlockDefinition`, alimentada pela projeção canônica de investimento do Stage 04.01.

Notion reconciliado com esta correção: `https://app.notion.com/p/3c569db9f0db8154b807ce1825d5aeb8`.

## Contrato

- ARCANE/ICE; camada 7; Capstone/terminal exterior; 1 rank; 3 PP.
- Compra da terminal: A0168 + Ice Mastery ≥80 + pelo menos um entre A0164=1, A0166≥2 ou A0167≥2.
- Possuir A0169 satisfaz **somente Gate C** da Specialist Gelo.
- Não concede dano ICE, `RPG_ICE_RESISTANCE`, CHILL, Congelamento, imunidade, afinidade adicional ou Mastery.
- A0169 não pertence às perks internas da Specialist Gelo.

## Authority e pipeline canônica

A authority do Specialist/gating pertence ao RPG Skill Tree. Iron's Spells 'n Spellbooks, Ars Nouveau e Ars Elemental são providers de gameplay ICE, não authority do gate estrutural.

Reutilizar exclusivamente:

- `TreeUnlockResolver`;
- `TreeUnlockDefinition`;
- projeção canônica de investimento do Stage 04.01.

Gate A = fundamentos exteriores semanticamente mapeados e disponíveis. Gate B = pelo menos 100 PP válidos em `SPECIALIST_REGION:ICE`. Gate C = A0169 possuída. `UNAVAILABLE_NODE` conta 0 PP no Gate B e bridge PP não pode ser contado duas vezes.

É proibido criar `SpecialistGateResolver`, `SPECIALIST_GATE_V1` ou qualquer resolver paralelo para este caso.

## Availability transitiva

A0168 continua `UNAVAILABLE_NODE` porque exige `MAGIC_THERMAL_PARCEL_V1` e sua cadeia passa por A0163, indisponível sem `DIRECT_MAGIC_OUTCOME_V1`. Portanto A0169 permanece não adquirível **por dependency closure**, não por ausência de infraestrutura Specialist.

Ice Mastery também precisa de producer canônico causal. Não criar grant por tick, tempo congelando, temperatura corporal, duração de efeito ou mera conjuração sem outcome/milestone auditado.

## Respec seguro

Enquanto houver perk interna ICE possuída:

- bloquear refund de A0169;
- bloquear refund de fundamentos/dependency closure necessários ao Gate A;
- bloquear refund que reduza Gate B abaixo de 100 PP válidos;
- exigir refund da Specialist interna antes de quebrar o gate exterior.

`BORDER_HOPPING` por geometria da UI é proibido.

## Fail-closed

Enquanto A0168 ou Ice Mastery causal estiverem indisponíveis:

- compra falha antes do gasto;
- rank legado indisponível vale 0 PP para gates e permanece reembolsável/migrável;
- definição/snapshot de unlock inválido ou incompatível falha fechado na pipeline canônica;
- não retornar aos gates legados de 8 PP ICE/ARCANE total 12;
- não conceder efeitos substitutos.

## Handoff Chat 2

Preservar A0169 como `UNAVAILABLE_NODE` transitivo enquanto A0168/dependencies estiverem bloqueadas. Não criar resolver Specialist novo. Quando a dependency closure deixar de bloquear a terminal, o unlock deve reutilizar `TreeUnlockResolver`/`TreeUnlockDefinition` + Stage 04.01.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend com A0168 unavailable;
2. rank legado unavailable vale 0 PP no Gate B e permanece reembolsável/migrável;
3. A0169 isolada nunca libera Specialist;
4. Gate A sem Gate B/C falha;
5. Gate B sem Gate A/C falha;
6. Gate A + Gate B + A0169 válidos liberam somente pela pipeline canônica;
7. 99 PP falha e 100 PP passa quando os demais gates forem válidos;
8. bridge PP não é contado duas vezes;
9. respec seguro bloqueia quebra do gate com perks internas possuídas;
10. nenhuma geometria/UI substitui o gate;
11. ausência de resolver Specialist paralelo.