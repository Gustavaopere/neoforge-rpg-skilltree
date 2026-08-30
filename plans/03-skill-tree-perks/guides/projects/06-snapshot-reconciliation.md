# Snapshot reconciliation — 2026-08-30

Esta nota registra a janela de auditoria usada para construir os dossiês de projetos próprios.

## Repositórios externos

- Volcanoes: `main@1d0da7ae7f19e06f60390fdeb0835720e2e40f1b`.
- Enshrouded: `main@de145be720f7f500f55e060982693312ed7f7bc3`.
- Black Arcana: `main@07263ae9bad12eba6ed500992991faa36ad598b2`.

## RPG Skill Tree

A auditoria inicial dos dossiês começou em `main@e49a1fa651abecfe096adb03c822482fcf9c3e7b`. Durante a preparação desta documentação a `main` avançou até `55463a195f8c3a87436399f71db19f29c8e85488`.

A reconciliação detectou avanço no Stage 03.06: os PRs #222/#223 implementaram e registraram o gate de drift do catálogo/wiki em CI. O arquivo `06-content-wiki-generation.md` continua aberto como plano integral, portanto somente esse subcomponente comprovado pode ser tratado como canônico; o Stage 03.06 inteiro não foi promovido.

## Regra de uso

O SHA anotado em cada dossiê representa a base usada para a análise extensa daquele projeto. Quando `main` avançar, `plans/STATUS.md`, arquivos de plano fechados/abertos e código/CI mais recentes prevalecem para determinar se um hook continua planejado, parcial ou canônico.

O Chat 1 deve revalidar apenas a superfície pertinente quando houver avanço posterior ao snapshot; não precisa refazer a leitura histórica completa se nenhuma área relacionada à perk mudou.
