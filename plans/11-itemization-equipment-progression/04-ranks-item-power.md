# 11.04 — Ranks e Poder do Item

## Objetivo

Separar qualidade estatística (`Rank`) da escala contextual (`Poder do Item`) e da quantidade de modificadores.

## Passo a passo

### A — Registry/data de ranks

Ranks iniciais: Comum, Incomum, Raro, Épico, Lendário, Mítico e Único.

Cada definição poderá declarar cor/UI, peso de geração, multiplicadores/caps e materiais de salvaging, sem usar o nome traduzido como ID.

### B — Poder do Item

Resolver `ItemPower` a partir do contexto usando serviços canônicos existentes quando disponíveis:

- craft: nível relevante do jogador;
- mob/drop: nível efetivo da entidade;
- loot/container: nível territorial/área;
- boss: contexto da entidade + regra de recompensa;
- quest/reward: contexto explícito do reward;
- machine/trade: política própria/fallback documentado;
- migration/admin: contexto explícito, sem adivinhação irreversível.

### C — Curvas

Separar `baseValueCurve(itemPower)` de `rankScaling(rank, modifierScalingType)`.

Tipos mínimos:

- valor aditivo;
- porcentagem com cap;
- chance com cap;
- duração;
- cooldown/redução com piso;
- contagem/alvos discretos;
- habilidade parametrizada;
- não escalável.

Habilidades binárias nunca recebem multiplicação cega.

### D — Independência estatística

O gerador de rank não recebe o resultado da contagem de famílias e vice-versa. Correlação futura exige mudança explícita de design/versionamento.

### E — Policies por origem

Bosses, rewards e áreas podem deslocar pesos de rank/Poder sem alterar a regra de 1..5 por família.

## Testes previstos

- mesma seed/contexto -> mesmo rank/Poder;
- fronteiras de peso e caps;
- item Único com 1/1/1 possível;
- item Comum com 5/5/5 possível;
- habilidade binária não multiplicada como número.

## Acceptance

Rank, Poder do Item e quantidade são três dimensões separadas, data-driven, determinísticas sob seed/contexto e bounded por políticas testadas.
