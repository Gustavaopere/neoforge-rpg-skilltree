# Reconciliação atual da modlist — Mods de Tecnologia

> **AUTORIDADE DE PRESENÇA/JAR/VERSÃO — 2026-08-30.** Este arquivo complementa os capítulos descritivos e prevalece quando um capítulo histórico ainda mostra um JAR ou versão anterior. O GitHub é a fonte canônica dos três guias temáticos.

A `modlist.txt` atual contém **573 entradas top-level**, incluindo o NeoForge modloader. A validação cruzada dos três guias cobre **572/572 arquivos `.jar` atuais**; NeoForge é tratado separadamente como modloader. O recorte Tecnologia referencia **189 JARs**, com sobreposição intencional para integrações cross-domain.

## Entrada incorporada desde o snapshot temático anterior

- `sophisticated_jei_index-1.2.2+1.21.1.jar` — **Sophisticated JEI Index 1.2.2**, integração de consulta/indexação JEI para o ecossistema Sophisticated. É infraestrutura de interface/logística, não provider autônomo de progressão.

## Deltas de JAR/versão incorporados

- `ae2importexportcard-1.21.1-1.6.0.jar` — AE2 Import Export Card `1.6.0`.
- `aero_cam_sync-1.4.0.jar` — Aeronautics Camera Sync `1.4.0`.
- `climbable_ropes-2.1.3.jar` — Climbable Ropes for Create Aeronautics `2.1.3`.
- `create-aeronautics-bundled-1.21.1-1.3.2.jar` — Create Aeronautics bundled stack `1.3.2`.
- `CreateCyberGoggles-1.21.1-8.3.15-NeoForge.jar` — Create: Cyber Goggles `8.3.15`.
- `create-enchantment-industry-2.5.3b.jar` — Create: Enchantment Industry `2.5.3b`.
- `create_stats-1.4.1.jar` — Create Stats `1.4.1`.
- `createcobblestone-1.5.0+neoforge-1.21.1-153.jar` — Create Cobblestone `1.5.0+neoforge-1.21.1-153`.
- `kilagraph-neoforge-1.21.1-21.1.0.12.jar` — KilaGraph `21.1.0.12`.
- `oritech-neoforge-1.21.1-1.2.11.jar` — Oritech `1.2.11`.
- `sound-physics-remastered-neoforge-1.4.0.1.jar` — runtime **Sound Physics Aeronautics 1.4.0.1**; substitui a referência antiga `sound-physics-aeronautics-1.4.0.jar`/runtime `1.3.0.2`.
- `toms_storage-1.21-2.4.2.jar` — Tom's Simple Storage Mod `2.4.2`.

## Identificadores tecnológicos atuais que devem permanecer explícitos

| JAR | Papel |
|---|---|
| `create-1.21.1-6.0.10.jar` | Create `6.0.10`, núcleo cinético do stack tecnológico. |
| `create-new-age-1.2.0+neoforge-mc1.21.1.jar` | Create: New Age `1.2.0`. |
| `createnuclear-1.3.2-beta.3-neoforge.jar` | Create Nuclear `1.3.2-beta.3`. |
| `tfmg-1.21.1-1.2.4b-community.jar` | Create: The Factory Must Grow `1.2.4b-community`. |
| `create_ultimate_factory-2.2.4-neoforge-1.21.1.jar` | Create Ultimate Factory `2.2.4`. |
| `vintageimprovements-1.21.1-0.0.0.7.jar` | Create: Vintage Improvements `0.0.0.7`. |
| `appliedenergistics2-19.2.17.jar` | Applied Energistics 2 `19.2.17`. |
| `ae2wtlib-19.5.1.jar` | AE2 Wireless Terminals / AE2WTLib `19.5.1`. |
| `oritech-neoforge-1.21.1-1.2.11.jar` | Oritech `1.2.11`. |
| `ldlib2-neoforge-1.21.1-2.2.37-all.jar` | LowDragLib2 `2.2.37`. |
| `Immersive-Aeronautics1.1.4-1.21.1-NeoForge.jar` | integração Immersive Portals/Aeronautics instalada; preservar o metadata runtime local documentado pelo pack. |
| `aero_cam_sync-1.4.0.jar` | sincronização de câmera em estruturas/sublevels móveis. |
| `sablephysicscompat-1.3.0.jar` | Sable: Physics Compat `1.3.0`. |
| `SableStuffAdditionsCompat v1.0.3-1.21.1.jar` | Sable: Stuff & Additions Compatibility `1.0.3`. |
| `weight-1.2.0.jar` | Create Aeronautics: Weight `1.2.0`. |
| `create_aero_radar-0.1.1-1.21.1.jar` | Create Aero Radar `0.1.1`. |

## Boundaries

- Mods de biblioteca, interface e compatibilidade podem ser pré-requisitos técnicos sem constituir uma capacidade de perk.
- Mobstein continua classificado aqui apenas como **boundary não tecnológico**; suas capacidades pertencem aos recortes Gameplay/Magia e ao dossiê do projeto quando aplicável.
- Pontes magia↔tecnologia devem preservar a autoridade do provider real e não transformar mera compatibilidade em nova mecânica.

## Regra operacional

- `CURRENT-MODLIST.md` é a autoridade de **presença, JAR e versão** para este guia.
- Os capítulos continuam sendo a autoridade descritiva de função, interação, riscos e uso em perks, salvo correção posterior explícita.
- Toda nova modlist deve ser reconciliada aqui antes de um novo lote de perks ser fechado.
