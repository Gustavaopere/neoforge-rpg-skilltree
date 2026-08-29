# World Scaling Plan — Relevant Player Level

**Goal:** escolher uma referência de progressão coerente para cada encontro, especialmente em multiplayer.

- [x] Definir busca espacial/party de jogadores relevantes.
- [x] Tratar nenhum jogador, vários jogadores e grande disparidade de níveis.
- [x] Evitar usar jogador distante/aleatório como referência.
- [x] Implementar cache com invalidação apropriada.
- [x] Medir custo para impedir scans caros por tick.

**Acceptance:** a mesma situação multiplayer resolve nível relevante de forma previsível e com custo bounded.

## Contrato implementado

- `RelevantPlayerLevelResolver` continua sendo a autoridade pura que filtra e agrega candidatos já normalizados; nenhum jogador global/aleatório entra no cálculo sem passar pelo filtro explícito.
- `RelevantPlayerSearchPolicy` separa política de localidade dos limites técnicos: cell size, candidate radius, engagement radius, output cap e cache TTL são explícitos e validados; não existe raio de gameplay silenciosamente embutido no runtime.
- `RelevantPlayerSpatialIndex` materializa uma visão imutável e provider-neutral dos jogadores por células X/Z, faz distância 3D exata com aritmética saturada e retorna candidatos em ordem determinística por distância/ID.
- `RelevantPlayerSpatialQuery` expõe `indexedPlayers`, `scannedPlayers` e `visitedCells`, permitindo auditar custo real de cada consulta.
- `RelevantPlayerCandidateRuntime` constrói o snapshot NeoForge com `ServerLevel.getPlayers(predicate, max+1)`; se o limite de indexação for excedido, a evidência espacial falha fechado em vez de aceitar um prefixo arbitrário da lista de jogadores.
- O nível de cada `ServerPlayer` vem de `CorePlayerProgressionRuntime.queryProgression(player).level()`, preservando a query read-only do RPG Core e sem materializar mutação/storage apenas para scaling.
- O índice é cacheado por dimensão/política com TTL e limite LRU de entradas. Mutações canônicas `CORE` invalidam o cache imediatamente; login, respawn, mudança de dimensão, logout e server stop também invalidam a topologia cacheada.
- `RelevantPlayerCandidateMerger` fornece o seam opcional de party sem dependência hard de mod externo. Candidatos de party precisam marcar `partyMember=true`, conflitos de nível falham fechado e candidatos espaciais locais têm prioridade quando o output budget está cheio.
- O adapter de party também é bounded por `maxCandidates`; não pode devolver uma lista ilimitada e contornar o budget espacial.
- Métricas permanentes expõem hits/misses do cache, jogadores amostrados, builds saturados, consultas espaciais, jogadores efetivamente examinados e células visitadas.

## Evidência de testes e performance boundary

- `RelevantPlayerLevelFoundationTest` cobre nenhum jogador, múltiplos jogadores, grande disparidade, exclusão de jogador irrelevante/distante, grandes níveis, imutabilidade e merge de party fail-closed.
- `RelevantPlayerSpatialIndexTest` cobre ordenação determinística, exclusão por distância, cap de saída, limite de células visitadas, cenário vazio, coordenadas extremas sem overflow e inputs inválidos/IDs duplicados.
- `scripts/verify-relevant-player-runtime.py`, executado por `scripts/test-core.sh`, proíbe regressão para `level.players()` sem limite, exige o probe `max+1`, fail-closed em saturação, query canônica de nível, cache/TTL/LRU, métricas, invalidações e wiring do bootstrap.
- A API NeoForge utilizada para o scan bounded foi confirmada contra a documentação 1.21.1 antes da implementação: `ServerLevel.getPlayers(Predicate<? super ServerPlayer>, int)`.

## Verificação de fechamento

- O PR inicial #134 foi fechado sem merge após detectar duas arquiteturas concorrentes no mesmo branch.
- A implementação foi reconstruída de forma limpa e consolidada no PR #136.
- Merge canônico: `2ca59a9d3f1c704f1e92198102ab51d833607ef0`.
- O head final `f7c6d7c561c197e8ef52e9465850a8e165866316` passou no `RPG Skill Tree CI` run `33245813040`: Core tests, JUnit 5, NeoForge GameTests, validators, NeoForge build, verificação do JAR e dedicated-server smoke fecharam GREEN. Foundation Optional Integrations, Foundation Bootstrap, Foundation Diagnostics e todos os workflows Compendium associados também fecharam GREEN.

## Limite causal preservado

O subplano não instala ainda o `EntityScalingDecisionRequestFactory` completo nem inventa política final de território, rarity, affix ou recompensa. Esses inputs pertencem aos subplanos 02.02–02.05 e serão conectados na ordem causal do Stage 02.

**Acceptance: satisfied.**
