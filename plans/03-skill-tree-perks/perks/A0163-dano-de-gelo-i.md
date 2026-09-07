# A0163 — Dano de Gelo I

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE`.**

A identidade ofensiva ICE é válida, mas a `main` ainda não expõe o outcome mágico direto canônico necessário para aplicar o multiplicador uma única vez sem atingir DoT, summons ou componentes derivados.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db8107ad9ccd43194a6f53`.

## Contrato

- ARCANE/ICE; camada 4; Ramo; 4 ranks; 1 PP/rank.
- Dependências: A0144 Poder Mágico ≥2 + Gateway ARCANE + pelo menos uma técnica entre A0148–A0155.
- Efeito: +3% de dano ICE **direto mágico elegível** por rank; máximo +12%.
- Multiplicadores: ×1,03 / ×1,06 / ×1,09 / ×1,12.
- Não escala DoT/ticks, fields, summons, secondary/derived outcomes ou dano ICE não atribuível diretamente ao jogador.

## Evidência provider-native

Iron's Spells 'n Spellbooks 3.16.3, snapshot auditado `e4056af90302d37eb1739f5ff05020b020e6e252`, possui identidades nativas distintas:

- school `irons_spellbooks:ice`;
- DamageType `irons_spellbooks:ice_magic`.

Isso comprova que ICE pode ser classificado no adapter do Iron's. Não comprova, sozinho, que todo DamageSource ICE seja um **direct player magic outcome**.

Ars Nouveau 5.13.1 expõe `SpellDamageEvent.Pre/Post` com caster, target, DamageSource, SpellContext e damage, mas o RPG Skill Tree ainda não possui o producer canônico unificado que transforme isso em um outcome direto deduplicável. Ars Elemental 0.7.10.1 também exige adapter versionado; conteúdo de branch 0.7.10.2 não é prova automática da build instalada.

## Capability ausente

Requer `DIRECT_MAGIC_OUTCOME_V1` com pelo menos:

- `root_action_id`/`outcome_id` estáveis;
- ator/owner autoritativo;
- provider + spell identity;
- classificação DIRECT vs DERIVED/DoT/summon;
- componente elementar classificável;
- fase mutável única antes das especializações posteriores.

A busca na `main` não encontrou `DIRECT_MAGIC_OUTCOME_V1` nem boundary equivalente.

## Pipeline futuro obrigatório

`provider direct spell -> DIRECT_MAGIC_OUTCOME_V1 -> classificador ICE exato -> Potência Mágica universal -> A0163 uma vez no componente ICE direto -> especializações posteriores -> mitigation/target`.

Cada `outcome_id` pode receber A0163 uma única vez.

## Separações obrigatórias

- ICE damage != Cold Sweat body temperature;
- ICE damage != CHILL/freezing buildup;
- ICE damage != `RPG_ICE_RESISTANCE`;
- school/DamageType != autoria DIRECT automaticamente;
- Enshrouded/Black Arcana/Volcanoes não são classifiers ICE por tema.

## Fail-closed

Enquanto `DIRECT_MAGIC_OUTCOME_V1` não existir com ao menos um adapter ICE completo:

- compra falha antes de consumir PP;
- rank legado unavailable vale 0 PP para gates/thresholds e permanece reembolsável/migrável;
- não inferir ICE por Slowness, CHILL, bioma, temperatura, partícula, visual congelado, namespace ou provider instalado;
- não degradar para dano mágico genérico.

## Handoff Chat 2

Implementar o estado `UNAVAILABLE_NODE` e o fail-before-spend. Não criar uma pipeline paralela só para A0163. Quando `DIRECT_MAGIC_OUTCOME_V1` existir como boundary canônico, esta perk deve ser ligada a ele, não diretamente a listeners independentes por provider.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend sem `DIRECT_MAGIC_OUTCOME_V1`;
2. legacy unavailable rank = 0 PP para gates;
3. classifier Iron's `ice_magic` positivo quando adapter futuro existir;
4. DoT, fields, summons e derived outcomes negativos;
5. não-ICE magic negativo;
6. nenhuma interação com Cold Sweat/CHILL/resistência;
7. futuramente: ranks 0–4 = 0/3/6/9/12% e uma aplicação por `outcome_id`;
8. provider/version mismatch fail-closed.