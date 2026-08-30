# 08.19 — KubeJS Authoring & Prototyping Adapter

## Goal
Permitir prototipar e autorar eventos/integrações narrativas sem transformar scripts em banco de dados canônico.

## Baseline
Versão pública NeoForge 1.21.1 verificada em 2026-08-30: `2101.7.2-build.374`.

## API proposta
Expor superfície pequena e estável, por exemplo:
- query fact/event/knowledge/relationship;
- emit narrative event validado;
- request fact mutation validada;
- schedule consequence por definition ID;
- resolve beat/choice por ID;
- register provider-derived authoring hook quando permitido.

## Entregas
- [ ] Optional adapter/classloading seguro.
- [ ] Nenhum acesso direto a SavedData/attachments internos.
- [ ] Validation de IDs/payloads.
- [ ] Replay/dedup keys obrigatórias para mutations repetíveis.
- [ ] Scripts não podem conceder reward bypassando canonical reward service.
- [ ] Erros de script são fail-visible e não corrompem save.
- [ ] Reload preserva snapshot válido anterior quando schema/content inválido.
- [ ] Exemplos PT-BR de authoring.
- [ ] Diagnostics para listar event/fact/beat resolvido.

## Política de migração
KubeJS é ferramenta de authoring/protótipo. Lógicas estruturais críticas e invariantes migram para Java/data definitions quando estabilizadas; scripts podem continuar como conteúdo/bridge quando forem a melhor fronteira.

## Acceptance
Um protótipo de witness discovery em KubeJS consegue emitir o evento e acionar a consequência usando APIs públicas, mas não consegue editar diretamente o ledger ou duplicar reward com a mesma replay key.