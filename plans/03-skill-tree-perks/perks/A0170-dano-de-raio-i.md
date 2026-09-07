# A0170 — Dano de Raio I

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE`.**

A identidade ofensiva LIGHTNING é válida, mas a `main` ainda não fornece o outcome mágico direto canônico necessário para aplicar a perk uma única vez com autoria inequívoca e sem atingir raio ambiental, tecnologia elétrica, DoT, summons ou componentes derivados.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db8187a3aaf1e3270a673b`.

## Contrato

- ARCANE/LIGHTNING; camada 4; Ramo; 4 ranks; 1 PP/rank.
- Dependências: A0144 Poder Mágico ≥2 + Gateway ARCANE + pelo menos uma técnica entre A0148–A0155.
- Efeito: +3% de dano LIGHTNING **direto mágico elegível** por rank; máximo +12%.
- Multiplicadores: ×1,03 / ×1,06 / ×1,09 / ×1,12.
- Não escala raio ambiental, energia FE, máquinas, DoT/ticks, fields, summons, secondary/derived outcomes ou dano LIGHTNING sem autoria mágica direta comprovada.

## Evidência provider-native

Iron's Spells 'n Spellbooks 3.16.3, snapshot auditado `e4056af90302d37eb1739f5ff05020b020e6e252`, possui identidades nativas distintas:

- school `irons_spellbooks:lightning`;
- DamageType `irons_spellbooks:lightning_magic`.

Isso é evidência válida para um adapter exato de classificação LIGHTNING. Não prova, sozinho, que qualquer dano `IS_LIGHTNING`, qualquer entidade lightning ou qualquer DamageSource do namespace seja uma ação mágica direta do jogador.

Ars Nouveau 5.13.1/Ars Elemental 0.7.10.1 só entram por adapter da versão instalada que prove spell identity, autoria, elemento e DIRECT vs derived. Conteúdo de branch mais nova não substitui evidência da build 0.7.10.1 instalada.

Create, Oritech, FE e demais sistemas tecnológicos permanecem fora desta classificação: eletricidade tecnológica não é automaticamente magia LIGHTNING.

## Capability ausente

Requer `DIRECT_MAGIC_OUTCOME_V1` com pelo menos:

- `root_action_id`/`outcome_id` estáveis;
- ator/owner autoritativo;
- provider + spell identity;
- classificação DIRECT vs DERIVED/DoT/summon;
- classificação LIGHTNING explícita;
- fase mutável única antes das especializações posteriores.

A busca na `main` não encontrou esse producer/boundary canônico.

## Pipeline futuro obrigatório

`provider direct spell -> DIRECT_MAGIC_OUTCOME_V1 -> classificador LIGHTNING exato -> Potência Mágica universal -> A0170 uma vez no componente LIGHTNING direto -> especializações posteriores -> mitigation/target`.

Cada `outcome_id` recebe A0170 no máximo uma vez.

## Separações obrigatórias

- LIGHTNING magic != raio ambiental;
- LIGHTNING magic != FE/Create/Oritech/electricidade tecnológica;
- DamageType/tag LIGHTNING != autoria mágica direta automaticamente;
- LIGHTNING damage != estados elétricos/controle/movimento;
- LIGHTNING damage != futura `RPG_LIGHTNING_RESISTANCE`;
- Enshrouded/Black Arcana/Volcanoes não se tornam classifiers LIGHTNING por tema.

## Fail-closed

Enquanto `DIRECT_MAGIC_OUTCOME_V1` não existir com ao menos um adapter LIGHTNING completo:

- compra falha antes de consumir PP;
- rank legado unavailable vale 0 PP para gates/thresholds e permanece reembolsável/migrável;
- não inferir LIGHTNING por `DamageTypeTags.IS_LIGHTNING` isoladamente quando faltar autoria mágica, por FE, máquina, partícula, alvo eletrificado, nome textual ou provider instalado;
- não degradar para dano mágico genérico ou bônus tecnológico.

## Handoff Chat 2

Implementar o estado `UNAVAILABLE_NODE` e fail-before-spend. Não criar pipeline paralela só para A0170 e não registrar listeners que convertam toda lightning vanilla/tecnológica em magia. Quando `DIRECT_MAGIC_OUTCOME_V1` existir, a perk deve consumir esse boundary canônico.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend sem `DIRECT_MAGIC_OUTCOME_V1`;
2. legacy unavailable rank = 0 PP para gates;
3. Iron's `lightning_magic` positivo quando adapter futuro existir;
4. raio ambiental negativo sem autoria mágica;
5. FE/Create/Oritech negativos;
6. DoT, fields, summons e derived outcomes negativos;
7. futuramente: ranks 0–4 = 0/3/6/9/12% e uma aplicação por `outcome_id`;
8. provider/version mismatch, multiplayer/reload e dedicated-server fail-closed.