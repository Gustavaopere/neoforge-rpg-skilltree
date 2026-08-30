# Snapshots Canônicos dos 3 Guias do Modpack

Esta pasta versiona cópias completas dos três guias canônicos usados durante a auditoria das perks. O objetivo é tornar a revisão no GitHub reproduzível mesmo quando o Notion sofrer alterações posteriores.

**Regra de autoridade:** o Notion continua sendo a fonte canônica. Estes arquivos são snapshots auditáveis e não substituem silenciosamente versões futuras das páginas.

## Guias

1. **Gameplay e Sistemas** — página canônica `3c569db9-f0db-81da-b0bd-d4c8fc783fb6` — https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6
2. **Mods de Magia** — página canônica `3c569db9-f0db-819e-9572-fd43820f9c03` — https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03
3. **Mods de Tecnologia** — página canônica `3c569db9-f0db-81a6-9e3e-e1232ee636ff` — https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff

## Formato

Cada guia é preservado em partes numeradas e contíguas para evitar truncamento de snapshots grandes. A leitura canônica de cada snapshot é a concatenação dos arquivos `part-XX.md` de sua pasta, em ordem lexical. Os marcadores estruturais do export/fetch do Notion (`callout`, `table`, `mention-page` etc.) são mantidos quando fazem parte do conteúdo capturado.

Snapshot realizado durante a auditoria A0001–A0020 em 2026-08-29/30, com os três guias reconciliados contra `modlist 28.08.26.txt` conforme seus próprios metadados canônicos.