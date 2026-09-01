# CI note — A0111–A0120 / PR #341

## Head funcional/documental verificado

No head `60f4f437be6807e19f9c3bd3b8ee6c7ffd1e69bf`, o `RPG Skill Tree CI`, SonarQube e todos os acceptance workflows que efetivamente dispararam concluíram `success`.

O workflow `Volcanoes Consolidated Release Readiness` terminou `failure` exclusivamente no job `aggregate-exact-head`, com resultado `9/10`, porque esperava `Volcanoes Consolidation Contract` no mesmo head.

## Causa verificada

`.github/workflows/volcanoes-consolidation-contract.yml` possui `pull_request.paths` restrito a runtime/resources/build/licença/scripts específicos de Volcanoes. A PR #341 altera apenas documentação em `plans/**`, portanto esse sibling workflow não é disparado pelo próprio contrato de paths.

O release aggregator, entretanto, exige a presença do workflow mesmo em PRs docs-only e ficou aguardando até timeout, embora os outros nove siblings tenham terminado `success`.

Classificação: **CI ORCHESTRATION / PATH-FILTER MISMATCH**, não falha funcional do lote de perks.

Commits documentais posteriores que apenas registram esta evidência podem repetir o mesmo padrão. Chat 1 não altera workflows nesta PR documental. O estado deve permanecer explícito para Chat 2/Chat 3; uma correção do agregador/path policy deve ser tratada separadamente se o projeto decidir exigir consolidated-release GREEN também para mudanças fora do escopo Volcanoes.
