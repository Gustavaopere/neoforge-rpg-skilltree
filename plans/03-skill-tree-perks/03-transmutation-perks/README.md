# 03 — Transmutation Perks

Transmutation Perks modificam uma Ability existente em vez de conceder a Ability.

Cada árvore terá exatamente `N_transmutation_per_tree` raízes `Txxxx`.

## Identidade e unicidade

Uma raiz `Txxxx` representa uma Ability.

**Cada Ability aparece uma única vez como raiz de Transmutation em todo o catálogo.**

Os modificadores daquela Ability usam `Txxxx.n`. Se a Ability receber novos modificadores no futuro, eles continuam nessa mesma sequência. Um novo número inteiro é usado somente para outra Ability.

Exemplo:

```
T5000 — Chain Lightning
├── T5000.1
├── ...
├── T5000.7
├── T5000.8
└── T5000.9
```

Não deve existir outra raiz de Chain Lightning mais adiante.

## Estrutura mínima inicial

Cada `Txxxx` terá:

- raiz: requisito de Ability conhecida/desbloqueada;
- potência/impacto: mínimo 2 modificadores;
- forma/eficiência: mínimo 2 modificadores;
- metamorfose: mínimo 3 modificadores;
- um arquivo `.md` por modificador.

**Metamorfose possui mínimo, não máximo.** Uma Ability pode ter 4, 5 ou mais metamorfoses se houver transformações relevantes e não redundantes.

A quantidade total de filhos `Txxxx.n` não precisa ser igual entre todas as Abilities. A igualdade entre árvores é medida pela quantidade de raízes `Txxxx`.

As faixas são organizacionais.

## Stacking

Todos os modificadores são acumuláveis por padrão.

Um jogador pode adquirir/usar vários filhos da mesma raiz simultaneamente. Exclusividade deve ser excepcional e declarada apenas quando dois efeitos forem semanticamente incompatíveis.

Exemplo: `T5000.1 + T5000.2 + T5000.7` podem operar juntos. Se `.7` possuir várias afinidades possíveis, a seleção de uma afinidade é uma escolha interna de `.7`, não exclusividade contra os outros modificadores.

## Separação de responsabilidades

A raiz de Transmutation é o único lugar dedicado a modificar especificamente aquela Ability.

Standard Perks e Specialization Perks podem afetar regras gerais, categorias, recursos ou sinergias, mas não devem criar uma segunda árvore específica para a mesma Ability.

## Escopo

Ability inclui spells, weapon skills, active skills, summons, stances, movement e ações vanilla integráveis.

A implementação técnica de cada provider só é aprofundada depois que o catálogo conceitual estiver fechado.
