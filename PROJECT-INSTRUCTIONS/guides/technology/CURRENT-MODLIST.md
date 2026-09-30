# Snapshot histórico da modlist — Mods de Tecnologia — 07/09/2026

> **SNAPSHOT HISTÓRICO — 2026-09-07.** Este arquivo preserva o snapshot de 06/09/2026 mais o delta de 07/09. Para presença/JAR/runtime atuais, use `../../modlist/modlist.md`; para contrato mecânico, use o dossier individual atual. Decisões curatoriais abaixo pertencem ao checkpoint histórico correspondente.

Naquele checkpoint, a modlist continha **612 entradas top-level**, incluindo NeoForge. O recorte Tecnologia permanece com **200 JARs tecnológicos/cross-domain**: o delta de 07/09 não adicionou nem removeu um provider tecnológico do recorte, mas atualizou cinco artefatos já classificados.

## Updates tecnológicos

- Create: Bits 'n' Bobs: `2.3.0 → 2.3.1`.
- Create: Copycats+: `3.0.8 → 3.0.9`.
- Create: Cyber Goggles: `8.5.1 → 8.5.2`.
- Lychee Tweaker: `6.6.1 → 6.7.0`.
- Petrolpark's Library: `1.5.8 → 1.5.9`.

O detalhamento está em [`21-atualizacao-modlist-2026-09-07.md`](21-atualizacao-modlist-2026-09-07.md).

## Bits 'n' Bobs — decisão revisada em 07/09

`bits_n_bobs-2.3.1.jar` continua fisicamente instalado e a decisão curatorial vigente foi **revisada para `Manter` em 07/09/2026**. A antiga decisão `Tirar` de 06/09 não é mais operacional.

O conflito visual com os Thermochemical Cogwheels de Create: Sulfuric Resonance 0.4.1 permanece documentado e é **risco aceito**, não incompatibilidade considerada resolvida. Manter ambos exige continuar monitorando/render-testando esse caso específico após updates de qualquer lado.

## Baseline tecnológico

A tabela completa de **200 IDs/JARs** reconciliada em 06/09 permanece auditável no histórico Git imediatamente anterior a este delta. Dentro daquele checkpoint, o baseline descreve módulos não alterados e o delta 07/09 descreve os cinco updates; ambos são históricos frente à modlist vigente.

## Boundaries de novos mods cross-domain

- `CerbonsAPI-NeoForge-1.21-1.3.0.jar` é biblioteca/dependência de Bosses of Mass Destruction; não é provider tecnológico de gameplay.
- `ironsable-wind-1.0.0.jar` é bridge magia↔Sable e pertence ao guia de Magia; a interação com blocos simulados não a transforma em provider tecnológico autônomo.

## Regra operacional

- Este arquivo + o delta 07/09 fixam presença/JAR/runtime apenas para aquele checkpoint histórico; o estado atual vem de `../../modlist/modlist.md`.
- [`../gameplay/CURATION-DECISIONS-2026-09-07.md`](../gameplay/CURATION-DECISIONS-2026-09-07.md) prevalece para decisões explicitamente revistas em 07/09.
- Descrições funcionais permanecem nos capítulos do guia.
- Update de versão não autoriza inferir API, hook ou compatibilidade sem inspeção.
- Biblioteca, bridge, UI ou compatibilidade não se torna provider mecânico apenas por participar do stack tecnológico.
