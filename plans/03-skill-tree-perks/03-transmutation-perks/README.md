# 03 — Transmutation Perks

Transmutation Perks modificam uma Ability existente em vez de conceder a Ability.

Cada árvore terá exatamente `N_transmutation_per_tree` raízes `Txxxx`.

## Identidade

Uma raiz `Txxxx` representa uma Ability.

Os modificadores daquela Ability usam `Txxxx.n`. Um novo número inteiro é usado somente para outra Ability.

## Estrutura mínima inicial

Cada `Txxxx` terá:

- raiz: requisito de Ability conhecida/desbloqueada;
- potência/impacto: mínimo 2 modificadores;
- forma/eficiência: mínimo 2 modificadores;
- metamorfose: mínimo 3 modificadores;
- um arquivo `.md` por modificador.

As faixas são organizacionais.

## Stacking

Todos os modificadores são acumuláveis por padrão.

Um jogador pode adquirir/usar vários filhos da mesma raiz simultaneamente. Exclusividade deve ser excepcional e declarada apenas quando dois efeitos forem semanticamente incompatíveis.

Exemplo: `T5000.1 + T5000.2 + T5000.7` podem operar juntos. Se `.7` possuir várias afinidades possíveis, a seleção de uma afinidade é uma escolha interna de `.7`, não exclusividade contra os outros modificadores.

## Escopo

Ability inclui spells, weapon skills, active skills, summons, stances, movement e ações vanilla integráveis.

A implementação técnica de cada provider só é aprofundada depois que o catálogo conceitual estiver fechado.
