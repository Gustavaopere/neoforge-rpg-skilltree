# A0162 — Maestria de Fogo

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE`.**

A0162 é uma terminal exterior do corredor FIRE. Ela não concede poder bruto; sua única responsabilidade futura é satisfazer o Gate C de uma Specialist Fogo quando o runtime de gates semânticos existir. Esse runtime não está comprovado na `main` atual.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db81b3ac7ee38d99bcdfe8`.

## Contrato

- ARCANE/FIRE; camada 7; Capstone/terminal exterior; 1 rank; 3 PP.
- Compra da terminal: A0161 + Fire Mastery ≥80 + pelo menos um entre A0157=1, A0159≥2 ou A0160≥2.
- Possuir A0162 satisfaz **somente Gate C**.
- A terminal não concede dano FIRE, resistência, imunidade, afinidade adicional, Mastery ou unlock mecânico de provider.
- A0162 não pertence às 30 perks internas da Specialist Fogo.

## Authority e capability ausente

Authority do Specialist/gating pertence ao RPG Skill Tree. Iron's/Ars/Ars Elemental são providers de gameplay FIRE, não autoridade do gate estrutural.

Capacidade requerida: `SPECIALIST_GATE_V1`.

Contrato futuro mínimo:

- Gate A = conjunto de fundamentos exteriores semanticamente mapeados e disponíveis;
- Gate B = pelo menos 100 PP **válidos** em `SPECIALIST_REGION:FIRE`;
- Gate C = A0162 possuída;
- avaliação server-side;
- reavaliação em compra, respec, migração e mudança de availability/provider;
- `UNAVAILABLE_NODE` conta 0 PP para Gate B;
- bridge PP não pode ser contado duas vezes.

A busca na `main` não encontrou `SpecialistGateResolver` nem boundary equivalente comprovado. Nomes existentes no catálogo não são evidência de runtime.

## Availability transitiva

A0161 está `UNAVAILABLE_NODE` porque requer `MAGIC_THERMAL_PARCEL_V1` e porque sua cadeia passa por A0156, atualmente indisponível sem `DIRECT_MAGIC_OUTCOME_V1`. Portanto A0162 permanece não adquirível mesmo antes de considerar a ausência de `SPECIALIST_GATE_V1`.

Fire Mastery também deve possuir producer canônico causal; storage genérico ou nome de requisito não autoriza grant por tick/tempo/exposição.

## Respec seguro futuro

Quando `SPECIALIST_GATE_V1` existir e houver perk interna FIRE possuída:

- bloquear refund de A0162;
- bloquear refund de fundamentos/dependency closure necessários ao Gate A;
- bloquear refund que reduza Gate B abaixo de 100 PP válidos;
- exigir refund da Specialist interna antes de quebrar o gate exterior.

`BORDER_HOPPING` por geometria da UI é proibido.

## Fail-closed

Enquanto A0161, Fire Mastery causal ou `SPECIALIST_GATE_V1` estiverem indisponíveis:

- compra falha antes do gasto;
- rank legado indisponível vale 0 PP para gates e permanece reembolsável/migrável;
- não retornar aos gates legados de 8 PP FIRE/ARCANE total 12;
- não conceder efeitos substitutos.

## Handoff Chat 2

Implementar somente a disponibilidade fail-closed prevista. Não criar uma Specialist ad hoc, não usar topologia da UI como gate e não transformar a terminal em bônus de combate. A criação de `SPECIALIST_GATE_V1` como infraestrutura global exige o contrato arquitetural correspondente; se isso alterar o Stage 04, devolver ao Chat 1.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend com A0161 unavailable;
2. purchase fail-before-spend sem `SPECIALIST_GATE_V1`;
3. rank legado unavailable vale 0 PP no Gate B;
4. nenhum efeito bruto ao possuir a terminal;
5. futuramente: Gates A/B/C devem ser simultâneos;
6. Gate B usa PP semântico válido, sem bridge double-count;
7. respec seguro bloqueia quebra do gate com Specialist interna possuída;
8. nenhum retorno silencioso aos thresholds legados.