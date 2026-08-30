# 15.09 — Testes, performance e migração

## Core

- geometry validator;
- point-in-polygon;
- overlap/hierarchy;
- codec/versioning;
- policy references;
- simplificação de overlay sem alterar autoridade.

## GameTests

- criar/editar/remover distrito;
- permissions;
- save/load;
- consulta por BlockPos;
- crossing de fronteira;
- dedicated server sem renderer.

## Network

- payloads bounded;
- rate limit de edição;
- revision/delta sync;
- intent inválido não força full resync ilimitado.

## Performance

Medir antes de fixar budgets. Cenários: muitos districts, muitos vértices, consultas repetidas por cidadãos e overlay de mapa. Otimização deve preservar resposta exata.

## Migração

- schemaVersion explícito;
- unknown zoning/policy IDs preservados;
- polígono antigo inválido após nova regra entra em quarantine administrativa e não é apagado;
- export diagnóstico permite recuperação.

## Gate

Stage fecha com core/JUnit, GameTests, build, dedicated-server smoke, client overlay evidence e stress test do índice espacial.

## Acceptance

Distritos sobrevivem evolução do schema e não criam hot path linear pelo número total de territórios.