# Contrato do novo catálogo de perks

## 1. Unidades de progressão

O catálogo possui quatro famílias:

1. **Internal Attributes** — seis atributos fundamentais comuns.
2. **Standard Perks** — passivos e regras de build que não modificam uma Ability concreta.
3. **Transmutation Perks** — uma raiz `Txxxx` associada a uma Ability conhecida/desbloqueada, com três grupos de escolhas `Txxxx.xx`.
4. **Specialization Perks** — perks pertencentes a ramificações temáticas de uma árvore principal.

## 2. As 11 árvores

`MARTIAL`, `AGILITY`, `VITALITY`, `HEALING`, `ARCANE`, `ENGINEERING`, `MINING`, `SURVIVAL`, `SUMMONING`, `OCCULT` e `LOGISTICS`.

## 3. Paridade obrigatória

Depois da auditoria de modlist/capacidades serão congelados globalmente:

- `N_standard_per_tree`;
- `N_transmutation_per_tree`;
- `K_specializations_per_tree`;
- `M_perks_per_specialization`.

Todas as árvores terão exatamente os mesmos valores.

O total de perks de especialização por árvore será:

`N_specialization_per_tree = K_specializations_per_tree * M_perks_per_specialization`.

Se uma árvore não sustentar a quota sem filler, a quota é reduzida globalmente. Não existe exceção quantitativa para ARCANE, magia ou qualquer outro domínio.

## 4. Especializações

Especialização é uma ramificação temática da árvore principal.

Ela não corresponde automaticamente a um mod, escola ou provider. Uma especialização pode integrar vários providers quando eles sustentarem a mesma fantasia de build.

Exemplo: uma futura especialização Piromante pode consumir capacidades de múltiplos mods e vanilla; ela não precisa ser “a especialização do Iron's Fire”.

A posição visual e os gateways definitivos são definidos somente depois do catálogo conceitual.

## 5. Transmutation Perks

Somente a raiz `Txxxx` conta para `N_transmutation_per_tree`.

### Unicidade por Ability

Cada Ability pode possuir **uma única raiz de Transmutation em todo o catálogo**.

Se Chain Lightning for `T5000`, qualquer modificação futura específica dela permanece dentro de `T5000.xx`. Ela não reaparece como `T5050`, `T5100` ou outra raiz.

Um novo inteiro, como `T5001`, é reservado para outra Ability.

Standard Perks e Specialization Perks podem afetar categorias gerais, mas não devem duplicar uma segunda customização específica da mesma Ability.

### Três grupos de escolha

Toda raiz possui três grupos independentes:

- **LEFT / esquerda:** `Txxxx.11`–`Txxxx.19`;
- **RIGHT / direita:** `Txxxx.21`–`Txxxx.29`;
- **METAMORPHOSIS / metamorfose:** `Txxxx.31`–`Txxxx.39`.

Mínimos:

- esquerda: 2 opções;
- direita: 2 opções;
- metamorfose: 3 opções.

Máximo: 9 opções em cada grupo.

### Exclusividade e coexistência

**Dentro de cada grupo, somente uma opção pode estar ativa.**

Ao mesmo tempo, os três grupos coexistem. A configuração da Ability pode usar:

`1 LEFT + 1 RIGHT + 1 METAMORPHOSIS`.

Exemplo válido:

`T5000.11 + T5000.22 + T5000.33`.

Exemplo inválido:

`T5000.11 + T5000.12`, porque ambas pertencem ao grupo LEFT.

A quantidade de opções pode variar entre Abilities. A paridade entre árvores mede raízes/Abilities `Txxxx`, não a quantidade de filhos.

### Função dos grupos

LEFT e RIGHT são dois eixos menores distintos de customização. Eles não precisam representar universalmente o mesmo atributo em todas as Abilities.

METAMORPHOSIS contém mudanças profundas de identidade ou funcionamento: conversão elemental, nova geometria, novo targeting, transformação de projectile/area/stance, alteração forte de recurso ou outro comportamento que mude a forma de jogar a Ability.

## 6. Ability, não Spell

`Ability` é conceito genérico e pode representar spell, weapon skill, active skill, summon, stance, movement, ação vanilla ou outra capacidade integrável.

Vanilla faz parte da matriz de capacidades.

Uma transmutação só fica disponível quando a Ability exigida estiver efetivamente conhecida/desbloqueada segundo a authority pertinente. Estar temporariamente equipada não é prova universal de conhecimento.

A integração final deve passar por adapters apropriados, por exemplo `AbilityKnowledgeProvider`, quando necessário.

## 7. Ordem de design e auditoria

O catálogo conceitual é criado **depois** da modlist e da matriz de capacidades, mas **antes** da auditoria profunda de APIs/hooks.

Primeiro definimos a experiência desejada. Depois descobrimos como cada provider real pode implementá-la de forma segura.

## 8. Ownership

Toda perk possui exatamente uma árvore proprietária para contagem e numeração, mesmo quando requisitos ou efeitos atravessam múltiplos domínios.

Um provider pode alimentar várias árvores. Um mod não se torna uma árvore nem uma especialização automaticamente.

## 9. Valores e balanceamento

Referências externas podem inspirar comportamento, mas não fornecem automaticamente números finais. Valores devem ser validados contra Minecraft, o provider instalado e o pipeline do RPG Skill Tree.
