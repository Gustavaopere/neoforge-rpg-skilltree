# 04 — Classes, Masteries & Specializations

Classes, Masteries e especializações são server-authoritative e data-driven. O runtime histórico resolve identidades deterministicamente; o redesign de produto aprovado em 2026-09-12 passa a orientar a experiência em torno de **uma Classe de Origem + uma Árvore 2 única com 23 regiões de classe interligadas + multiclass por Class Gateways + Tree 3 class-centric**.

Base física corrente para o redesign: modlist 2026-09-08, 595 entradas, NeoForge 21.1.248. O catálogo runtime ainda contém 23 classes e 25 specialization definitions, mas esses números são **inventário de migração**, não confirmação de que toda semântica antiga permanece final.

Estado formal dos subplanos históricos:

- ✅ 04.01 — Class Resolution — authority determinística existente; deve ser preservada durante a migração;
- ✅ 04.02 — Confluences and Bridges — runtime existente; a regra antiga de domínios/bridge fixa será revisada pelo 04.07;
- ⏳ 04.03 — Masteries;
- ⏳ 04.04 — Provider Identities;
- ✅ 04.05 — Specializations and Gateways — infraestrutura existente; taxonomia provider-centric será reauditada;
- ✅ 04.06 — Class Subtrees — quatro subtrees históricas materializadas; Druid/Metamorph/Technomancer contêm assumptions obsoletos de providers removidos;
- 🧭 **04.07 — Class-Oriented Progression Overhaul** — plano arquitetural aprovado; define Origem, 23 regiões, custos progressivos, requisitos de atributo, multiclass sem hard cap e migração da Tree 3.

## Autoridade do redesign

Ver [`07-class-oriented-progression-overhaul.md`](07-class-oriented-progression-overhaul.md).

Decisões centrais:

- a Árvore 2 é uma única malha; não existem 23 árvores isoladas;
- a UI é orientada às classes, não aos 11 domínios antigos;
- comprar perk de outra região não concede automaticamente aquela classe;
- segunda/terceira classe é conquistada por Class Gateway e requisitos reais;
- não há limite rígido de número de classes;
- custo progressivo, distância, atributos-base, Mastery e provider gates produzem o soft cap;
- a Classe de Origem possui afinidade/desconto nos nodes explicitamente associados a ela;
- requisitos de atributo usam apenas investimento permanente da Árvore 1, nunca buffs/equipamento;
- Tree 3 passa a representar especializações de classe; providers permanecem authority mecânica, não taxonomia obrigatória de personagem;
- AE2/Oritech/Identity2 removidos da modlist atual não podem permanecer como requirements ativos.

## Ordem a partir deste redesign

Antes de implementar conteúdo novo, auditar as 23 classes e as 25 definitions contra a modlist física corrente. Perks individuais continuam obrigatoriamente seguindo o fluxo de lotes exatos de 10; 04.07 não fecha automaticamente nenhum lote de perks nem altera seus estados formais.