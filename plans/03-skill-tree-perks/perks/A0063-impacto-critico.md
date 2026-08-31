# A0063 — Impacto Crítico

## Estado de design

**APROVADA COM BOUNDARY.** Especialização do corredor crítico MARTIAL.

## Contrato final

- **Ranks:** 3; **custo:** 1/rank.
- **Dependência:** A0062 ≥ 2 ranks + gateway MARTIAL.
- **Efeito:** quando o **resolver crítico canônico** já declarou o root crítico, aumenta o dano desse crítico em +5% por rank, máximo +15%. Se o root não é crítico, multiplicador neutro.
- A0063 não altera chance crítica e não executa sua própria rolagem.

## Authority / hooks

A aplicação ocorre sobre a decisão crítica já consolidada para o mesmo root, inclusive nos adapters de famílias anteriores e no pipeline físico BOW/CROSSBOW. Não deve ser aplicada a dano secundário que apenas herdou owner/item.

## Simply Swords

Double damage de Katana, double strike de Warglaive, Ability/Implicit/Gem damage e scaling de Awakening permanecem provider-owned. A0063 não deve escalar novamente helpers Simply já escalados nem transformar esses resultados em crítico MARTIAL separado.

## Fail-closed

Sem receipt `canonicalCritical=true` correlacionado ao root, A0063 é neutra. Herda `P-A0061-01` para classificação melee.

## Testes obrigatórios

- crítico canônico: 1.05/1.10/1.15;
- não crítico: 1.0;
- uma aplicação/root mesmo quando NeoForge + Epic Fight observam o mesmo ataque;
- derived hits Simply/companions/hazards não recebem multiplicador por owner.

## Nove eixos

1. Gates: PASS — A0062≥2.
2. Integração: PASS — crítico canônico único.
3. Qualidade: PASS — só recompensa build crítica efetiva; não bônus genérico todo hit.
4. Topologia: PASS — continuação do corredor crítico.
5. Especialização: PASS — MARTIAL crítico.
6. PT-BR: PASS.
7. Registro: GitHub.
8. NeoVitae: PASS.
9. Providers: PASS com Simply provider-owned.