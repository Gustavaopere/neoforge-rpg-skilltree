# A0044 — Treino com Arcos II

## Estado

- **Design:** APROVADO; sem mutação no Notion nesta retroauditoria.
- **Implementação:** FAIL-CLOSED CORRETO; não há consumer seguro de draw/preparation speed comprovado.
- **Notion:** `3c569db9-f0db-81d4-9c22-c7fc0ebcd482`.

## Contrato canônico

- A0043 ≥2 + gateway `epic_bow`.
- +2% de ritmo efetivo de preparo/disparo com arcos por rank, até +6%, somente quando provider expuser parâmetro server-authoritative com essa semântica.
- Projectile speed, movimento, stamina, dano, tooltip ou manipulação de animação não são substitutos.

## Evidência runtime

- Catálogo/ruleset/topologia contêm A0044.
- Busca no runtime A0041–A0060 não encontra consumer de A0044 que modifique draw/preparation time.
- `A0041A0060ProjectileEvents` manipula propriedades de disparo/projétil, mas não apresenta API semântica de velocidade de preparo do arco.

## Provider→árvore

- Nenhum dos providers retroauditados fornece draw speed seguro para este contrato.
- Stage 11.01 de itemização não possui projeção de efeito que autorize esta cadência.
- Volcanoes/Enshrouded/Black Arcana/Mobstein não são providers de preparação de arco.

## Pendência Chat 2

Nenhuma correção deve ser inventada. Manter A0044 inativa até existir API/atributo server-authoritative com semântica real de draw/preparation speed. Se surgir provider futuro, adicionar adapter versionado e testes de ausência/presença.

## Testes exigidos para futura ativação

- provider presente/ausente;
- rank 1/2/3;
- efeito em preparo real, não projectile speed;
- nenhum dano/stamina/movement fallback;
- dedicated server.
