# 17.12 — Testes, balanceamento e migração

## Testes de domínio

- law precedence e provenance;
- economic regime combinations;
- voter eligibility/censitary threshold;
- election snapshot/replay;
- offices/authority;
- capitalism/communal ownership transitions;
- theocracy/technocracy/magocracy qualifications;
- feudal/serf/coercive labor statuses;
- legitimacy.

## Runtime

- player intent nunca envia law result autoritativo;
- reload transacional mantém previous snapshot em definition inválida;
- Stage 16/15/19 adapters ausentes deixam laws indisponíveis com diagnóstico;
- dedicated server sem client classes.

## Balanceamento

Não fixar números finais sem simulação. Criar cenários reproduzíveis com diferentes populações, rendas, tax rates, regimes e crises. Registrar métricas: treasury solvency, wage arrears, inequality, service coverage, business survival e legitimacy.

## Migração

Persistir history e IDs desconhecidos. Regime/law removido entra em quarantine e usa last-known-good administrativo até reconciliation explícita; nunca escolher outro regime automaticamente.

## Gate

Core/JUnit + GameTests + economic simulation + provider absence/presence + build/JAR + dedicated-server smoke + UI pt-BR coverage.

## Acceptance

Governança continua determinística e explicável após reload, save update e troca de regime.