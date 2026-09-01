# A0169 — Maestria de Gelo

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE`.**

A0169 é a terminal exterior do corredor ICE. Ela não concede poder bruto e só poderá satisfazer o Gate C da Specialist Gelo quando existir um resolver semântico canônico de Specialist. Esse boundary não está comprovado na `main` atual.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db8154b807ce1825d5aeb8`.

## Contrato

- ARCANE/ICE; camada 7; Capstone/terminal exterior; 1 rank; 3 PP.
- Compra da terminal: A0168 + Ice Mastery ≥80 + pelo menos um entre A0164=1, A0166≥2 ou A0167≥2.
- Possuir A0169 satisfaz **somente Gate C** da Specialist Gelo.
- Não concede dano ICE, `RPG_ICE_RESISTANCE`, CHILL, Congelamento, imunidade, afinidade adicional ou Mastery.
- A0169 não pertence às perks internas da Specialist Gelo.

## Authority e capability ausente

A autoridade do Specialist/gating pertence ao RPG Skill Tree. Iron's Spells 'n Spellbooks, Ars Nouveau e Ars Elemental são providers de gameplay ICE, não authority do gate estrutural.

Capacidade requerida: `SPECIALIST_GATE_V1`.

Contrato futuro mínimo:

- Gate A = fundamentos exteriores semanticamente mapeados e disponíveis;
- Gate B = pelo menos 100 PP **válidos** em `SPECIALIST_REGION:ICE`;
- Gate C = A0169 possuída;
- avaliação server-side a partir do estado canônico;
- reavaliação em compra, respec, migração e mudança de capability/provider;
- `UNAVAILABLE_NODE` conta 0 PP para Gate B;
- bridge PP não pode ser contado duas vezes.

A busca na `main` não encontrou `SpecialistGateResolver` nem boundary equivalente comprovado. O nome presente no catálogo anterior era contrato aspiracional, não evidência de runtime.

## Availability transitiva

A0168 está `UNAVAILABLE_NODE` porque exige `MAGIC_THERMAL_PARCEL_V1` e porque sua cadeia passa por A0163, atualmente indisponível sem `DIRECT_MAGIC_OUTCOME_V1`. Portanto A0169 permanece não adquirível antes mesmo de considerar a ausência de `SPECIALIST_GATE_V1`.

Ice Mastery também precisa de producer canônico causal. Não criar grant por tick, tempo congelando, temperatura corporal, duração de efeito ou mera conjuração sem outcome/milestone auditado.

## Respec seguro futuro

Quando `SPECIALIST_GATE_V1` existir e houver perk interna ICE possuída:

- bloquear refund de A0169;
- bloquear refund de fundamentos/dependency closure necessários ao Gate A;
- bloquear refund que reduza Gate B abaixo de 100 PP válidos;
- exigir refund da Specialist interna antes de quebrar o gate exterior.

`BORDER_HOPPING` por geometria da UI é proibido.

## Fail-closed

Enquanto A0168, Ice Mastery causal ou `SPECIALIST_GATE_V1` estiverem indisponíveis:

- compra falha antes do gasto;
- rank legado indisponível vale 0 PP para gates e permanece reembolsável/migrável;
- não retornar aos gates legados de 8 PP ICE/ARCANE total 12;
- não conceder efeitos substitutos.

## Handoff Chat 2

Implementar somente o estado de disponibilidade/fail-closed previsto neste dossiê. Não criar Specialist ad hoc, não usar geometria da UI como gate e não transformar a terminal em bônus de combate/controle. Qualquer mudança estrutural no sistema de Specialist que altere Gates A/B/C deve voltar ao Chat 1.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend com A0168 unavailable;
2. purchase fail-before-spend sem `SPECIALIST_GATE_V1`;
3. rank legado unavailable vale 0 PP no Gate B;
4. nenhum efeito bruto ao possuir a terminal;
5. futuramente: Gates A/B/C devem ser simultâneos;
6. Gate B usa PP semanticamente válidos, sem bridge double-count;
7. respec seguro bloqueia quebra do gate com Specialist interna possuída;
8. nenhum retorno silencioso aos thresholds legados.