# 13.14 — Migração, fallback e recuperação

## Objetivo

Preservar saves quando taxonomia, modlist, worldgen, nomes, POIs ou adapters mudarem entre versões.

## Versionamento

Persistências cartográficas devem conter `schemaVersion` e versão/classifier revision suficiente para distinguir mudanças incompatíveis.

Migrações precisam ser:

- explícitas;
- idempotentes;
- testáveis;
- fail-closed quando houver risco de revelar/atribuir informação errada;
- com `lastKnownGood`/backup quando mutação destrutiva for necessária.

## Mudança de taxonomia

Quando um biome muda de categoria ou regras de conectividade mudam:

- não rerrolar todas as regiões no login;
- calcular impacto bounded/administrável;
- preservar `regionId` sempre que semanticamente possível;
- em merge, manter ID sobrevivente + aliases;
- em split, definir migração de intel/quest sem duplicar rewards;
- registrar conflito que exige decisão manual em vez de inventar associação.

## Mod removido

Se mod que originou biome/estrutura for removido:

- não crashar ao decodificar registry key histórica;
- manter tombstone/provenance suficiente para diagnóstico;
- remover projeção inválida quando apropriado;
- preservar quests/POIs exclusivos em estado explicitamente inválido até política de recovery;
- não retargetar automaticamente objetivo narrativo único.

## JourneyMap removido ou atualizado

A base cartográfica permanece intacta. Ao retornar um renderer compatível, reconstruir a projeção a partir do estado canônico.

## Importações externas

Não importar automaticamente todos os waypoints/frontiers do usuário como conhecimento RPG. Se futuramente houver importador:

- opt-in explícito;
- provenance da origem;
- classificação como `USER_IMPORTED`;
- nenhuma reward/discovery XP automática;
- validação de dimensão/coordenadas.

## Recovery admin

Prever ferramentas para:

- dry-run de migração;
- listar aliases/tombstones;
- reparar índice espacial;
- reconstruir projeção visual;
- reindexar uma área limitada;
- restaurar snapshot cartográfico;
- invalidar cache sem apagar conhecimento.

## Acceptance

- save antigo migra uma única vez;
- mod removido não impede startup;
- merge/split não duplica discovery rewards;
- falha de migração preserva último estado válido;
- renderer pode ser removido/reinstalado sem perda do domínio.