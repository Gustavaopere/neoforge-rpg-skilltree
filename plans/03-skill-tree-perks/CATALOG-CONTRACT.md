# Contrato do novo catálogo de perks

## 1. Unidades de progressão

O catálogo possui quatro famílias:

1. **Internal Attributes** — seis atributos fundamentais comuns.
2. **Standard Perks** — passivos e regras de build que não modificam uma Ability concreta.
3. **Transmutation Perks** — uma raiz `Txxxx` associada a uma Ability conhecida/desbloqueada, com modificadores `Txxxx.n`.
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

## 5. O que conta como uma Transmutation Perk

Somente a raiz `Txxxx` conta para `N_transmutation_per_tree`.

Os filhos `Txxxx.n` pertencem à mesma Ability e não entram na quota.

Cada raiz deve possuir inicialmente no mínimo:

- 2 modificadores de potência/impacto;
- 2 modificadores de forma/eficiência;
- 3 metamorfoses.

Essas três faixas são uma organização de design, não um sistema de exclusividade.

### Stacking

Modificadores `Txxxx.n` são **acumuláveis por padrão**.

Exemplo válido:

`T5000.1 + T5000.2 + T5000.7` ativos simultaneamente sobre Chain Lightning.

Exclusividade só pode existir quando dois efeitos forem realmente incompatíveis. Nesse caso o conflito deve ser declarado explicitamente no dossiê; não pode ser inferido pela posição esquerda/direita/metamorfose.

Uma escolha interna de modo também não exclui outros modificadores. Exemplo: `T5000.7` pode permitir escolher uma afinidade persistente; apenas uma afinidade fica selecionada dentro de `.7`, mas `.7` continua podendo coexistir com `.1`, `.2`, etc.

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
