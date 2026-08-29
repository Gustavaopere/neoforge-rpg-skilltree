# World Scaling Plan — Relevant Player Level

**Goal:** escolher uma referência de progressão coerente para cada encontro, especialmente em multiplayer.

- [x] Definir busca espacial/party de jogadores relevantes.
- [x] Tratar nenhum jogador, vários jogadores e grande disparidade de níveis.
- [x] Evitar usar jogador distante/aleatório como referência.
- [x] Implementar cache com invalidação apropriada.
- [x] Medir custo para impedir scans caros por tick.

**Acceptance:** a mesma situação multiplayer resolve nível relevante de forma previsível e com custo bounded.

## Contrato implementado

- `RelevantPlayerLevelResolver` é a autoridade pura que filtra e agrega candidatos normalizados; nenhum jogador global/aleatório entra sem filtro explícito.
- `RelevantPlayerSearchPolicy` separa política de localidade dos limites técnicos: cell size, candidate radius, engagement radius, output cap e cache TTL são explícitos e validados.
- `RelevantPlayerSpatialIndex` mantém snapshot imutável/provider-neutral por células X/Z, distância 3D exata com aritmética saturada e ordenação determinística.
- `RelevantPlayerSpatialQuery` expõe `indexedPlayers`, `scannedPlayers` e `visitedCells` para auditoria de custo.
- `RelevantPlayerCandidateRuntime` usa `ServerLevel.getPlayers(predicate, max+1)` e falha fechado em saturação em vez de aceitar prefixo arbitrário.
- O level de cada `ServerPlayer` vem de `CorePlayerProgressionRuntime.queryProgression(player).level()`, preservando a query read-only do RPG Core.
- Cache é dimension/policy scoped, TTL + LRU bounded, invalidado por mutações canônicas CORE e lifecycle de login/respawn/dimension/logout/server stop.
- `RelevantPlayerCandidateMerger` é o seam opcional de party sem hard dependency; party também respeita `maxCandidates` e conflitos de level falham fechado.
- Métricas permanentes cobrem cache hits/misses, jogadores amostrados, builds saturados, queries espaciais, jogadores examinados e células visitadas.

## Evidência

- `RelevantPlayerLevelFoundationTest` cobre vazio, multiplayer, grande disparidade, exclusão de irrelevantes/distantes, níveis altos, imutabilidade e party merge fail-closed.
- `RelevantPlayerSpatialIndexTest` cobre ordenação, distância, cap de saída, limite de células, vazio, coordenadas extremas e IDs duplicados.
- `scripts/verify-relevant-player-runtime.py` está ligado ao `scripts/test-core.sh` e bloqueia scans globais sem limite, exige query canônica, cache/TTL/LRU, métricas e invalidações.
- Implementação canônica: merge `2ca59a9d3f1c704f1e92198102ab51d833607ef0`.
- `RPG Skill Tree CI` run `33245953398` fechou GREEN no merge canônico.

## Limite causal preservado

`02.01` não instala a factory completa de `EntityScalingDecisionRequest` nem inventa território, rarity, affix ou recompensa; essas decisões pertencem aos subplanos seguintes.

**Acceptance: satisfied.**
