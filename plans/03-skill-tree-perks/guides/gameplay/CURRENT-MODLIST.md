# Reconciliação atual da modlist — Gameplay e Sistemas

> **AUTORIDADE DE PRESENÇA/JAR/VERSÃO — 2026-09-07.** O estado físico atual é definido pelo snapshot auditável de 06/09/2026 **mais** o [`MODLIST-DELTA-2026-09-07.md`](../MODLIST-DELTA-2026-09-07.md). O delta prevalece para todo artefato que ele altera. Dependências `jarjar` embutidas não contam como mods top-level.

A `modlist.txt` atual contém **612 entradas top-level**, incluindo o NeoForge modloader: NeoForge + **611 JARs**.

## Fechamento da reconciliação

- baseline 06/09: **607** entradas top-level;
- **21** updates do mesmo mod;
- **9** adições reais;
- **4** remoções físicas reais;
- saldo: **+5**;
- estado atual: **612** entradas top-level;
- Notion reconciliado: **612 `Instalado` + 4 `Removido`** com JAR preenchido e **617/617** registros `Verificado`.

## Snapshot temático de 06/09

As sete páginas abaixo são o baseline temático congelado de 06/09. Elas não devem ser lidas isoladamente como estado físico atual quando uma linha estiver no delta de 07/09.

- [Registros 001-050 — baseline 06/09](CURRENT-MODLIST-001-050.md)
- [Registros 051-100 — baseline 06/09](CURRENT-MODLIST-051-100.md)
- [Registros 101-150 — baseline 06/09](CURRENT-MODLIST-101-150.md)
- [Registros 151-200 — baseline 06/09](CURRENT-MODLIST-151-200.md)
- [Registros 201-250 — baseline 06/09](CURRENT-MODLIST-201-250.md)
- [Registros 251-300 — baseline 06/09](CURRENT-MODLIST-251-300.md)
- [Registros 301-342 — baseline 06/09](CURRENT-MODLIST-301-342.md)

Para resolver qualquer presença atual: comece no baseline acima e aplique integralmente o [`delta canônico de 07/09`](../MODLIST-DELTA-2026-09-07.md). Esse par fecha exatamente o inventário físico atual.

## Mudanças de Gameplay/Sistemas em 07/09

Os novos módulos e seus boundaries estão documentados em [`19-atualizacao-modlist-2026-09-07.md`](19-atualizacao-modlist-2026-09-07.md):

- Bosses of Mass Destruction 1.3.3;
- Bosses'Rise 2.1.2;
- CERBON's API 1.3.0;
- Integrated Mowzie's Mobs 1.1.0;
- Simple Inventory Sorter 24.0.24;
- [Let's Do] Furniture 1.1.4;
- MineColonies: Jade crops 1.1.1300.

## Remoções e bloqueios resolvidos

A modlist de 07/09 confirma a remoção física de:

- `alexscaves-2.0.2.jar`;
- `alexsmobs-1.22.9.jar`;
- `spore_1.21.1_2.2.0j_neo.jar`;
- `Infnexus-2.0.4-1.21.1.jar`.

Os dois bloqueios de discovery por IDs duplicados `alexscaves` e `alexsmobs` estão, portanto, **fisicamente resolvidos**. Alex's Caves Continued 1.0.9 permanece; Alex's Mobs Continued permanece e foi atualizado para 2.1.10.

## Authority de curadoria

Presença física e decisão curatorial são estados distintos. [`CURATION-DECISIONS-2026-09-06.md`](CURATION-DECISIONS-2026-09-06.md) continua sendo authority das decisões fechadas em 06/09; o delta de 07/09 registra a execução física posterior. Em particular, Create: Bits 'n' Bobs continua fisicamente `Instalado`, agora em 2.3.1, mas a decisão **Tirar** permanece.

## Regra operacional

- `CURRENT-MODLIST.md` + `../MODLIST-DELTA-2026-09-07.md` formam a authority atual de presença/JAR/versão.
- Os capítulos são authority descritiva de função, integração, riscos e classificação provider/bridge/library/presentation.
- Biblioteca, UI, visual, compat ou scripting não deve ser promovido automaticamente a provider mecânico.
- Um update de versão não revalida automaticamente hooks/APIs usados por perks.
