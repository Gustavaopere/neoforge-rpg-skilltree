# A0050 — Treino com Bestas II

## Estado

- **Design:** APROVADO; sem mutação no Notion nesta retroauditoria.
- **Implementação:** FAIL-CLOSED CORRETO; não há consumer seguro de reload/preparation speed comprovado.
- **Notion:** `3c569db9-f0db-812f-9e64-ca806740e883`.

## Contrato canônico

- A0049 ≥2 + gateway `epic_crossbow`.
- +2% de ritmo efetivo de recarga/preparo com bestas por rank, até +6%, somente quando provider expuser parâmetro server-authoritative com essa semântica.
- Projectile speed, mobilidade, stamina, dano, tooltip ou manipulação de timers por heurística não são substitutos.

## Evidência runtime

- Catálogo/ruleset/topologia contêm A0050.
- `A0041A0060ProjectileEvents.tickCrossbow(...)` observa estado carregado/uso para perks posteriores, mas não implementa modifier semântico de reload/preparation speed de A0050.
- Nenhum provider retroauditado fornece API segura de reload speed para esta perk.

## Provider→árvore

- RPG Skill Tree permanece consumer apenas quando houver hook real; Stage 11 itemização não fornece reload projection.
- Volcanoes, Enshrouded, Black Arcana e Mobstein não são providers de recarga da besta.
- WoM/itens externos só participam se explicitamente classificados CROSSBOW; classificação não cria reload-speed API.

## Pendência Chat 2

Nenhuma implementação deve ser inventada. Manter a parcela de A0050 inativa até existir API/atributo server-authoritative de reload/preparation speed. Futuro adapter deve ser versionado e não alterar projectile speed, dano, Stamina ou timers internos por mixin frágil.

## Testes exigidos para futura ativação

- provider presente/ausente;
- rank 1/2/3;
- redução real de tempo de recarga/preparo no server;
- nenhum efeito em projectile speed/dano/Stamina;
- mainhand/offhand e multiplayer;
- dedicated server.
