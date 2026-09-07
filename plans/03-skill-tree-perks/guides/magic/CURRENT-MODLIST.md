# Reconciliação atual da modlist — Mods de Magia

> **AUTORIDADE DE PRESENÇA/JAR/VERSÃO — 2026-09-07.** O estado físico atual do pack é o snapshot de 06/09/2026 mais o [`MODLIST-DELTA-2026-09-07.md`](../MODLIST-DELTA-2026-09-07.md). O delta prevalece quando qualquer JAR/versão divergir do snapshot anterior.

A modlist física atual contém **612 entradas top-level**, incluindo NeoForge. O recorte mágico do snapshot de 06/09 tinha **101 JARs**; em 07/09 entram dois módulos mágicos/cross-domain novos, totalizando **103 referências temáticas** antes de sobreposições com outros guias.

## Baseline preservado

A tabela completa de 101 JARs reconciliada em 06/09 permanece auditável no histórico Git da `main` imediatamente anterior a este delta. O estado atual não exige reescrever linhas não afetadas: para elas, o baseline permanece válido; para os artefatos abaixo, prevalece o delta de 07/09.

## Updates mágicos

- Apotheosis: `8.7.0 → 8.8.0`.
- Ars 'n' Spells: `3.2.4 → 3.3.0`.
- GTBC's SpellLib: `2.1.0 → 2.2.0` (`2.2.0-1.21.1` no runtime).
- Vampirism: `1.10.12 → 1.10.13`.

Updates de versão não revalidam automaticamente hooks/APIs de perks.

## Novos módulos mágicos/cross-domain

### Ironsable x Wind's Spellbooks — 1.0.0

`ironsable-wind-1.0.0.jar` — bridge Wind's Spellbooks ↔ IronSable/Sable. É compatibilidade de causalidade física, não nova escola nem provider autônomo.

### More Relics — 1.7.7 — ALTO RISCO

`morerelics-1.7.7-1.21.1.jar` está fisicamente instalado, porém a documentação oficial do projeto para NeoForge 1.21.1 informa que Relics `0.11`/`0.12` ainda não são suportados e recomenda Relics `0.10.7.8`. O pack usa `relics-1.21.1-0.12.8.jar`.

Portanto **More Relics permanece fail-closed para perks/integrações** até a compatibilidade ser comprovada ou o stack ser ajustado.

A descrição, dependências e gates estão em [`19-atualizacao-modlist-2026-09-07.md`](19-atualizacao-modlist-2026-09-07.md).

## Regra operacional

- Este arquivo + [`../MODLIST-DELTA-2026-09-07.md`](../MODLIST-DELTA-2026-09-07.md) fixam a identidade física atual do domínio mágico.
- Para itens não alterados em 07/09, o snapshot de 06/09 continua válido.
- Descrições funcionais permanecem nos capítulos temáticos.
- Bridge, biblioteca, UI ou compatibilidade não vira provider apenas por estar no recorte mágico.
- Nenhum contrato de perk pode assumir More Relics enquanto o desvio Relics `0.12.8` estiver aberto.
