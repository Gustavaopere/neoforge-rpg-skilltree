# Snapshot físico da modlist — 2026-09-08

> **AUTORIDADE DE PRESENÇA/JAR/VERSÃO.** Este snapshot substitui o checkpoint de 07/09/2026 para presença física, nome de JAR e versão declarada no runtime. Dependências `jarjar` embutidas não contam como mods top-level.

## Fonte física

- arquivo-fonte local: `modlist(4).txt` / upload `modlist.txt` de 08/09/2026;
- SHA-256 do arquivo-fonte exato: `7c0a23d6013101383d196526e4b6ba6940fb54a0fed10eaed5956ab015cfcc00`;
- cabeçalho físico: `Mods count: 595`;
- entradas top-level normalizadas: **595/595**;
- primeira entrada: `neoforge-21.1.248 (modloader)`;
- composição: **NeoForge 21.1.248 + 594 JARs top-level**.

## Inventário normalizado

O inventário abaixo é uma extração determinística das linhas top-level da tabela física. Linhas indentadas sob `/META-INF/jarjar/` foram excluídas da contagem. Os campos são copiados da fonte sem preencher lacunas por inferência: `index`, `jar_name`, `mod_id`, `mod_name`, `mod_version`.

- [`MODLIST-SNAPSHOT-2026-09-08-001-150.tsv`](MODLIST-SNAPSHOT-2026-09-08-001-150.tsv) — 150 entradas — SHA-256 `ad41cd213754c489a0de01ad49779fbba71155aa4db650f41c9905376035eafc`;
- [`MODLIST-SNAPSHOT-2026-09-08-151-300.tsv`](MODLIST-SNAPSHOT-2026-09-08-151-300.tsv) — 150 entradas — SHA-256 `c60064332125aaae1ec00f4a449f25942b87e8781a3724c71c30c86174bbc49e`;
- [`MODLIST-SNAPSHOT-2026-09-08-301-450.tsv`](MODLIST-SNAPSHOT-2026-09-08-301-450.tsv) — 150 entradas — SHA-256 `230672cc4ba459d5c7284cb28a759403d8eb9b31507cad534c7eccc40436ab65`;
- [`MODLIST-SNAPSHOT-2026-09-08-451-595.tsv`](MODLIST-SNAPSHOT-2026-09-08-451-595.tsv) — 145 entradas — SHA-256 `d466a5def479dfec785e98b275836503efeb55b852c93b2504398a76e93cd229`.

Concatenação textual das quatro partes, cada uma preservando seu próprio header TSV: SHA-256 `a89230b4bae9cf7be25fcb2eada21b54df372d5928cda1e22785cc532f36612a`.

## Relação com snapshots anteriores

O snapshot de 06/09 e o delta de 07/09 permanecem como **histórico auditável**, mas não são mais authority de presença/JAR/versão. Qualquer documento que ainda diga que `612` é a modlist física "atual" deve ser interpretado como texto congelado do checkpoint de 07/09 até sua atualização editorial.

Este snapshot não afirma que o Notion possui 595 registros instalados nem reaproveita os antigos fechamentos `612 Instalado`, `613 Verificado`, `605/605 modVersion` ou `7/7 sem modVersion` como estado de 08/09. Esses números pertencem ao checkpoint anterior e precisam de reconciliação própria antes de serem promovidos para a nova authority.
