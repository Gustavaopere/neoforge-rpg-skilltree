# 03 — Transmutation Perks

Transmutation Perks modificam uma Ability existente em vez de conceder a Ability.

Cada árvore terá exatamente `N_transmutation_per_tree` raízes `Txxxx`.

## Identidade e unicidade

Uma raiz `Txxxx` representa uma Ability.

**Cada Ability aparece uma única vez como raiz de Transmutation em todo o catálogo.**

Um novo número inteiro é usado somente para outra Ability.

## Três grupos

Cada raiz possui:

### LEFT / esquerda
- IDs `.11`–`.19`;
- mínimo 2 opções;
- máximo 9;
- escolher somente 1.

### RIGHT / direita
- IDs `.21`–`.29`;
- mínimo 2 opções;
- máximo 9;
- escolher somente 1.

### METAMORPHOSIS / metamorfose
- IDs `.31`–`.39`;
- mínimo 3 opções;
- máximo 9;
- escolher somente 1.

Os três grupos coexistem. Portanto uma configuração completa pode manter simultaneamente uma opção de cada grupo.

Exemplo:

`T5000.11 + T5000.22 + T5000.33`.

O máximo de 9 é por grupo, não por Ability inteira. Uma raiz pode ter até 27 opções catalogadas, embora não exista obrigação de preencher todos os slots.

## Papel dos grupos

LEFT e RIGHT devem oferecer dois eixos menores de customização diferentes entre si.

METAMORPHOSIS contém transformações maiores: elemento/afinidade, geometria, trajetória, targeting, recurso, comportamento espacial, forma de entrega ou outra alteração que mude a identidade da Ability.

Esses significados podem variar conforme a natureza da Ability. O design não deve forçar “dano à esquerda” para todas as skills se isso produzir opções artificiais.

## Separação de responsabilidades

A raiz de Transmutation é o único lugar dedicado a modificar especificamente aquela Ability.

Standard Perks e Specialization Perks podem afetar regras gerais, categorias, recursos ou sinergias, mas não devem criar uma segunda árvore específica para a mesma Ability.

## Escopo

Ability inclui spells, weapon skills, active skills, summons, stances, movement e ações vanilla integráveis.

A implementação técnica de cada provider só é aprofundada depois que o catálogo conceitual estiver fechado.
