# Reconciliação atual da modlist — Gameplay e Sistemas

> **AUTORIDADE DE PRESENÇA/JAR/VERSÃO — 2026-09-08.** O estado físico atual é o [`MODLIST-SNAPSHOT-2026-09-08.md`](../../modlist/MODLIST-SNAPSHOT-2026-09-08.md), extraído da modlist física com **595 entradas top-level**, incluindo o modloader **NeoForge 21.1.248**. Dependências `jarjar` embutidas não contam como mods top-level.

A autoridade corrente é: **NeoForge 21.1.248 + 594 JARs top-level = 595 entradas**. O arquivo-fonte físico usado no snapshot possui SHA-256 `7c0a23d6013101383d196526e4b6ba6940fb54a0fed10eaed5956ab015cfcc00`.

## Inventário auditável de 08/09

- [Snapshot e método de extração](../../modlist/MODLIST-SNAPSHOT-2026-09-08.md)
- [Entradas 001–150](../../modlist/MODLIST-SNAPSHOT-2026-09-08-001-150.tsv)
- [Entradas 151–300](../../modlist/MODLIST-SNAPSHOT-2026-09-08-151-300.tsv)
- [Entradas 301–450](../../modlist/MODLIST-SNAPSHOT-2026-09-08-301-450.tsv)
- [Entradas 451–595](../../modlist/MODLIST-SNAPSHOT-2026-09-08-451-595.tsv)

Os campos `mod_id`, `mod_name` e `mod_version` são copiados da fonte física. Campos vazios continuam vazios; filename/publicação não são promovidos a runtime metadata por inferência.

## Estado do Notion neste checkpoint

O snapshot físico de 08/09 **não reutiliza** os fechamentos quantitativos do Notion de 07/09 como se fossem atuais. Os números históricos `612 Instalado`, `613/613 Verificado`, `605/605 modVersion` e `7/7 sem modVersion` permanecem evidência do checkpoint anterior e exigem reconciliação própria contra a authority de 595 antes de qualquer nova declaração global.

## Histórico congelado — checkpoint de 07/09/2026

> Tudo nesta seção descreve o checkpoint anterior e **não** prevalece sobre o snapshot físico de 08/09 para presença, JAR ou versão.

O estado de 07/09 era definido pelo snapshot auditável de 06/09/2026 mais o `MODLIST-DELTA-2026-09-07.md`, totalizando **612 entradas top-level** naquele checkpoint.

### Fechamento histórico da reconciliação de 07/09

- baseline 06/09: **607** entradas top-level;
- **21** updates do mesmo mod;
- **9** adições reais;
- **4** remoções físicas reais;
- saldo: **+5**;
- estado histórico em 07/09: **612** entradas top-level;
- Notion então reconciliado: **612 `Instalado`**, **612 JARs físicos distintos** e **613/613 registros `Verificado`**; a 613ª linha era não física e não representava outro JAR instalado;
- auditoria de versão runtime daquele checkpoint: **605/605 JARs que declaravam `modVersion`** batiam exatamente com `Versão 1.21.1` no Notion;
- **7/7 JARs sem `modVersion` físico** permaneciam com `Versão 1.21.1` vazia no Notion.

### Snapshot temático de 06/09

As sete páginas abaixo são o baseline temático congelado de 06/09 e servem apenas como histórico descritivo quando divergirem da authority de 08/09:

- [Registros 001-050 — baseline 06/09](CURRENT-MODLIST-001-050.md)
- [Registros 051-100 — baseline 06/09](CURRENT-MODLIST-051-100.md)
- [Registros 101-150 — baseline 06/09](CURRENT-MODLIST-101-150.md)
- [Registros 151-200 — baseline 06/09](CURRENT-MODLIST-151-200.md)
- [Registros 201-250 — baseline 06/09](CURRENT-MODLIST-201-250.md)
- [Registros 251-300 — baseline 06/09](CURRENT-MODLIST-251-300.md)
- [Registros 301-342 — baseline 06/09](CURRENT-MODLIST-301-342.md)

### Mudanças registradas em 07/09

Os módulos e boundaries daquele checkpoint foram documentados em [`19-atualizacao-modlist-2026-09-07.md`](19-atualizacao-modlist-2026-09-07.md), incluindo Bosses of Mass Destruction 1.3.3, Bosses'Rise 2.1.2, CERBON's API 1.3.0, Integrated Mowzie's Mobs, Simple Inventory Sorter 24.0.24, [Let's Do] Furniture 1.1.4 e MineColonies: Jade crops 1.1.1300.

### Remoções e bloqueios registrados em 07/09

Naquele checkpoint foram registradas como removidas fisicamente:

- `alexscaves-2.0.2.jar`;
- `alexsmobs-1.22.9.jar`;
- `spore_1.21.1_2.2.0j_neo.jar`;
- `Infnexus-2.0.4-1.21.1.jar`.

Essas observações continuam úteis como histórico causal, mas a presença física atual deve sempre ser resolvida no inventário de 08/09.

## Authority de curadoria

Presença física e decisão curatorial são estados distintos. Os arquivos de decisões curatoriais continuam preservando o histórico de escolha; uma decisão `Manter` ou `Tirar` não altera sozinha a presença comprovada pelo snapshot físico.

- **Create: Bits 'n' Bobs — `Manter`.** Conflitos visuais documentados continuam riscos a validar; presença e versão atuais devem ser consultadas no snapshot 08/09.
- **More Relics — `Manter`.** Desvios de compatibilidade documentados continuam fail-closed até teste real; presença e versão atuais devem ser consultadas no snapshot 08/09.
- **Integrated Mowzie's Mobs — `Manter`.** A decisão permanece curatorial; identidade runtime deve ser lida do snapshot 08/09.

## Regra operacional

- [`MODLIST-SNAPSHOT-2026-09-08.md`](../../modlist/MODLIST-SNAPSHOT-2026-09-08.md) + suas quatro partes TSV são a **authority atual de presença/JAR/versão**.
- O snapshot 06/09 e o delta 07/09 são históricos e não podem sobrepor a authority de 08/09.
- Os capítulos continuam authority descritiva de função, integração, riscos e classificação provider/bridge/library/presentation quando não contradizem a presença/JAR/versão atual.
- Biblioteca, UI, visual, compat ou scripting não deve ser promovido automaticamente a provider mecânico.
- Um update de versão não revalida automaticamente hooks/APIs usados por perks.
- Quando um capítulo histórico contradizer explicitamente o snapshot de 08/09 sobre presença, JAR ou runtime, **o snapshot de 08/09 prevalece** até o capítulo ser atualizado.
