# 03 — Transmutation Perks

Transmutation Perks modificam uma Ability existente em vez de conceder a Ability.

Cada árvore terá exatamente `N_transmutation` nós-base `Txxxx`.

## Estrutura mínima de cada Txxxx

- nó-base: requisito de Ability conhecida/desbloqueada;
- ramo esquerdo: mínimo 2 opções exclusivas;
- ramo direito: mínimo 2 opções exclusivas;
- metamorfose: mínimo 3 opções exclusivas;
- cada opção possui seu próprio arquivo `.md` com sub-ID `Txxxx.n`.

Metamorfoses podem alterar dano, elemento/afinidade, trajetória, número de alvos, comportamento espacial, forma de entrega, recurso, status, VFX ou outras propriedades — desde que o provider e os hooks reais permitam.
