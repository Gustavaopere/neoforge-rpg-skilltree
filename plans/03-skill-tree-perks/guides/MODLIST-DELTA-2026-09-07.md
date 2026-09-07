# Delta canônico da modlist — 2026-09-07

> **AUTORIDADE DE PRESENÇA/JAR/VERSÃO.** Este delta foi reconciliado contra a modlist física de 2026-09-07 (`Mods count: 612`) e contra a Auditoria Mestre do Notion. O estado físico atual é o snapshot de 2026-09-06 (607 entradas top-level) **mais** as mudanças exatas abaixo. Dependências `jarjar` embutidas não contam como mods top-level.

## Fechamento contábil

- baseline 2026-09-06: **607** entradas top-level, incluindo NeoForge;
- updates do mesmo mod: **21**;
- adições reais: **9**;
- remoções físicas reais: **4**;
- saldo líquido: **+5**;
- estado físico 2026-09-07: **612** entradas top-level = NeoForge + 611 JARs;
- Notion após reconciliação: **612 `Instalado`**, **612 `Arquivo JAR` físicos distintos** e **613/613 registros `Verificado`**; a linha adicional é não física e não representa outro mod instalado;
- runtime audit: **605/605** entradas que declaram `modVersion` físico correspondem exatamente a `Versão 1.21.1` no Notion;
- metadata ausente: **7/7** JARs sem `modVersion` físico permanecem com `Versão 1.21.1` vazia no Notion, sem inferência pelo filename.

## 21 updates de JAR/versão

| Mod ID | Antes (06/09) | Atual (07/09) | Runtime atual |
|---|---|---|---|
| `alexsmobs` | `alexsmobs-2.1.9-neoforge+1.21.1.jar` | `alexsmobs-2.1.10-neoforge+1.21.1.jar` | `2.1.10` |
| `amendments` | `amendments-1.21-2.1.9-neoforge.jar` | `amendments-1.21-2.1.10-neoforge.jar` | `1.21-2.1.10` |
| `apotheosis` | `Apotheosis-1.21.1-8.7.0.jar` | `Apotheosis-1.21.1-8.8.0.jar` | `8.8.0` |
| `ars_n_spells` | `ars_n_spells-3.2.4.jar` | `ars_n_spells-3.3.0.jar` | `3.3.0` |
| `bits_n_bobs` | `bits_n_bobs-2.3.0.jar` | `bits_n_bobs-2.3.1.jar` | `2.3.1` |
| `copycats` | `copycats-3.0.8+mc.1.21.1-neoforge.jar` | `copycats-3.0.9+mc.1.21.1-neoforge.jar` | `3.0.9+mc.1.21.1-neoforge` |
| `create_cyber_goggles` | `CreateCyberGoggles-1.21.1-8.5.1-NeoForge.jar` | `CreateCyberGoggles-1.21.1-8.5.2-NeoForge.jar` | `8.5.2` |
| `dtbetterend` | `dtbetterend-1.21.1-2.1.0.jar` | `dtbetterend-1.21.1-2.2.0.jar` | `2.2.0` |
| `dtbetternether` | `dtbetternether-1.21.1-2.1.0.jar` | `dtbetternether-1.21.1-2.2.0.jar` | `2.2.0` |
| `gtbcs_spell_lib` | `gtbcs_spell_lib-2.1.0-1.21.1.jar` | `gtbcs_spell_lib-2.2.0-1.21.1.jar` | `2.2.0-1.21.1` |
| `insanelib` | `insanelib-2.4.31.0.jar` | `insanelib-2.4.32.0.jar` | `2.4.32.0` |
| `lithostitched` | `lithostitched-1.8.0+beta4-neoforge-21.1.jar` | `lithostitched-1.8.0+beta6-neoforge-21.1.jar` | `1.8.0+beta6` |
| `lychee` | `Lychee-1.21.1-NeoForge-6.6.1.jar` | `Lychee-1.21.1-NeoForge-6.7.0.jar` | `6.7.0+neoforge` |
| `minecolonies` | `minecolonies-1.1.1376-1.21.1-snapshot.jar` | `minecolonies-1.1.1377-1.21.1-snapshot.jar` | `1.1.1377-1.21.1-snapshot` |
| `moonlight` | `moonlight-1.21.1-3.6.1-neoforge.jar` | `moonlight-1.21.1-3.6.3-neoforge.jar` | `1.21.1-3.6.3` |
| `petrolpark` | `petrolpark-1.21.1-1.5.8.jar` | `petrolpark-1.21.1-1.5.9.jar` | `1.5.9` |
| `photon` | `photon-neoforge-1.21.1-2.2.5-all.jar` | `photon-neoforge-1.21.1-2.2.6.a-all.jar` | `2.2.6.a` |
| `polytone` | `polytone-1.21-4.1.0-neoforge.jar` | `polytone-1.21-4.2.0-neoforge.jar` | `1.21-4.2.0` |
| `puzzleslib` | `PuzzlesLib-v21.1.56-mc1.21.1-NeoForge.jar` | `PuzzlesLib-v21.1.59-mc1.21.1-NeoForge.jar` | `21.1.59` |
| `supplementaries` | `supplementaries-1.21.1-3.9.7-neoforge.jar` | `supplementaries-1.21.1-3.9.8-neoforge.jar` | `1.21.1-3.9.8` |
| `vampirism` | `Vampirism-1.21-1.10.12.jar` | `Vampirism-1.21-1.10.13.jar` | `1.10.13` |

## 9 adições reais

| JAR atual | Mod ID | Nome runtime | Runtime | Guia principal |
|---|---|---|---|---|
| `BOMD-NeoForge-1.21-1.3.3.jar` | `bosses_of_mass_destruction` | Bosses of Mass Destruction | `1.3.3` | Gameplay |
| `block_factorys_bosses-2.1.2-neo-1.21.1.jar` | `block_factorys_bosses` | Bosses'Rise | `2.1.2` | Gameplay |
| `CerbonsAPI-NeoForge-1.21-1.3.0.jar` | `cerbons_api` | Cerbons API | `1.3.0` | Gameplay / biblioteca |
| `IMM v1.1.0-1.21.1.jar` | `integrated_mowzies_mobs` | Integrated Mowzie's Mobs | `1.1.0` | Gameplay / worldgen compat |
| `inventorysorter-1.21.1-24.0.24.jar` | `inventorysorter` | Simple Inventory Sorter | `24.0.24` | Gameplay / QoL |
| `ironsable-wind-1.0.0.jar` | `ironsable_wind` | Ironsable x Wind's Spellbooks | `1.0.0` | Magia / compat |
| `letsdo-furniture-neoforge-1.1.4.jar` | `furniture` | [Let's Do] Furniture | `1.1.4` | Gameplay / construção |
| `mcjadecrops-1.1.1300.jar` | `mcjadecrops` | MineColonies: Jade crops | `1.1.1300` | Gameplay / MineColonies QoL |
| `morerelics-1.7.7-1.21.1.jar` | `morerelics` | More Relics | `1.7.7` | Magia/RPG |

## 4 remoções físicas reais

| JAR removido | Mod ID | Estado anterior | Estado atual |
|---|---|---|---|
| `alexscaves-2.0.2.jar` | `alexscaves` | instalado e rejeitado por curadoria | **REMOVIDO FISICAMENTE**; Continued 1.0.9 permanece |
| `alexsmobs-1.22.9.jar` | `alexsmobs` | instalado e rejeitado por curadoria | **REMOVIDO FISICAMENTE**; Continued 2.1.10 permanece |
| `spore_1.21.1_2.2.0j_neo.jar` | `spore` | instalado | **REMOVIDO FISICAMENTE** |
| `Infnexus-2.0.4-1.21.1.jar` | `infnexus` | instalado | **REMOVIDO FISICAMENTE** |

Esses quatro artefatos não possuem linhas físicas separadas no estado final do Notion; o banco foi consolidado para representar a fotografia atual, não para manter quatro registros `Removido` artificiais.

## Consequências curatoriais e de compatibilidade

- Os bloqueios de discovery por mod IDs duplicados `alexscaves` e `alexsmobs` estão fisicamente resolvidos na modlist de 07/09.
- `Create: Bits 'n' Bobs` continua fisicamente instalado, agora em `2.3.1`, e a decisão vigente foi revista para **Manter**. A incompatibilidade visual documentada com os Thermochemical Cogwheels de Create: Sulfuric Resonance 0.4.1 é risco aceito; continuar monitorando/render-testando sem afirmar que o conflito foi corrigido.
- `Integrated Mowzie's Mobs` está fisicamente em `1.1.0` e o projeto oficial foi confirmado. O artefato local/runtime 1.1.0 é a autoridade física; a listagem pública consultada ainda exibe v1.0.0 para 1.21.1, portanto a proveniência pública exata da build local 1.1.0 permanece não demonstrada.
- **More Relics 1.7.7 — `Manter`, risco aceito:** o upstream declara que Relics `0.11`/`0.12` ainda não são suportados e recomenda Relics `0.10.7.8`, enquanto o pack usa Relics `0.12.8`. A decisão é manter o mod fisicamente; não interpretar isso como prova de compatibilidade. Perks/integrações provider-specific permanecem **fail-closed** até teste real comprovar o comportamento necessário.
- `CERBON's API` é dependência, não provider de progressão.
- `MineColonies: Jade crops` é camada de HUD/compatibilidade; não altera crescimento nem é provider mecânico.
- `Ironsable x Wind's Spellbooks` é bridge de causalidade magia↔Sable, não nova escola de magia.

## 7 JARs sem runtime metadata

A modlist física não declara `modVersion` para os sete artefatos abaixo. O Notion mantém `Versão 1.21.1` vazia para todos eles e registra filename/publicação separadamente:

- `connector-2.0.0-beta.17+1.21.1-full.jar`;
- `create_sophback_compat-1.0.jar`;
- `createmechanicalcompanion-1.9-neoforge-1.21.1.jar`;
- `dtquark-2.6.1.jar`;
- `easy_model_entities-neoforge-1.21.1-2.3.0.jar`;
- `easy_npc-neoforge-1.21.1-7.11.0.jar`;
- `kotlinforforge-5.12.0-all.jar`.

Não inferir runtime metadata a partir do nome do arquivo ou da versão publicada.

## Regra de leitura dos guias

Para presença física, JAR e runtime em 07/09/2026, este delta prevalece sobre qualquer linha de 06/09 que mencione um dos artefatos alterados acima. Para qualquer mod não listado no delta, a linha do snapshot de 06/09 continua válida. Descrições funcionais e contratos de provider permanecem nos capítulos temáticos, salvo correção explícita posterior.
