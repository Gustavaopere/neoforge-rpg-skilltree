# Reconciliação atual da modlist — Mods de Tecnologia

> **AUTORIDADE DE PRESENÇA/JAR/VERSÃO — 2026-09-06.** Este arquivo prevalece quando qualquer capítulo descritivo ou histórico ainda cita um JAR, nome runtime ou versão anterior. A fonte de inventário é a `modlist.txt` atual do pack; o GitHub é a fonte canônica editorial deste guia.

A modlist atual contém **607 entradas top-level**, incluindo o NeoForge modloader. O recorte Tecnologia cobre **200 JARs tecnológicos/cross-domain** atuais.

A contagem anterior de `189` escondia uma inconsistência editorial: os capítulos descreviam 188 IDs, o `Sophisticated JEI Index` elevava o conjunto declarado a 189, mas `LowDragLib2` permanecia explicitamente listado fora dessa contagem. Reconciliando o conjunto real anterior, havia **190 IDs**; todos continuam presentes. O snapshot de 2026-09-06 acrescenta **10 módulos** e fecha o conjunto atual em **200 IDs únicos**.

## Novos módulos incorporados no snapshot 607

- `aero_player_tilt-0.1.3.jar` — **Aeronautics Player Tilt `0.1.3`**; inclinação de corpo/hitbox e gravidade relativa ao convés móvel de Create Aeronautics/Sable.
- `clockwork-neoforge-1.21.1-1.1.4.jar` — **Clockwork `1.1.4`**; ferramentas, armas/utilidades mecânicas e Clockwork Dragonfly; é tecnologia standalone, não addon de Create.
- `create_colony_logistics-1.3.2.jar` — **Create Colony Logistics `1.3.2`**; bridge automatizada MineColonies ↔ Create Logistics.
- `create_fantasizing-1.21.1-1.2.0-b3.jar` — **Create: Fantasizing Again `1.2.0-b3`**; addon Create de utilidades/conteúdo de alto poder, build beta instalada.
- `createliquidfuel-3.0.0-1.21.1.jar` — **Create Liquid Fuel `3.0.0-1.21.1`**; entrada de combustíveis fluidos em Blaze Burners.
- `emf_compat_create_1.21.1_2.0.0.jar` — **EMF Compat: Create `2.0.0`**; compatibilidade exclusivamente client-side de animação/visual, sem authority de gameplay.
- `kubejs-create-neoforge-2101.3.1-build.18.jar` — **KubeJS Create `2101.3.1-build.18`**; superfície de scripting para Create, não provider autônomo.
- `kubejs_oritech-neoforge-1.21.1-0.4.4.jar` — **KubeJS Oritech `1.21.1-0.4.4`**; integração de scripting KubeJS ↔ Oritech.
- `productive-metalworks-kubejs-addon-1.0.0.jar` — **Productive Metalworks KubeJS Addon `1.0.0`**; helpers de scripting de melting/casting/alloying/fuels.
- `sulfuricresonance-0.4.1.jar` — **Create: Sulfuric Resonance `0.4.1`**; termquímica, calor, química de enxofre/ácido, materiais e automação avançada.

## Deltas de JAR/runtime reconciliados

- Oritech `1.2.10/1.2.11` → **`1.2.12`**.
- Create Aeronautics `1.3.1` → **`1.3.2`**.
- Create Tracks+ `1.0.6b` → **`1.0.6b6`**.
- ExtendedAE `1.21-2.2.35-neoforge` → **`1.21-2.2.36-neoforge`**.
- AE2 Import Export Card `1.5.0` → runtime **`1.21.1-1.6.0`**.
- Petrolpark `1.5.6` → **`1.5.8`**.
- Create: Integrated Farming `1.3.3b` → **`1.4.1b`**.
- Create Cobblestone `1.4.12+neoforge-1.21.1-144` → **`1.5.0+neoforge-1.21.1-153`**.
- Create: Fast Schematic Cannon `1.4.1-neoforge` → **`2.6.1-neoforge`**.
- Create Aeronautics: Transmission & Linkage `0.2.7` → **`0.2.8`**.
- Create Aeronautics: Copycat Wing `1.0.2` → **`1.0.3`**.
- Climbable Ropes `2.1.1` → **`2.1.3`**.
- Aeronautics Camera Sync `1.3.6` → **`1.4.0`**.
- Sound Physics Aeronautics `1.3.0.2` / arquivo antigo → runtime **`1.4.0.1`** em `sound-physics-remastered-neoforge-1.4.0.1.jar`.
- Sophisticated Backpacks `3.25.78` → **`3.26.1`**.
- Tom's Simple Storage `2.4.1` → **`2.4.2`**.
- Create: Enchantment Industry `2.5.3` → **`2.5.3b`**.
- Create: Apokinetics `1.0.5` → **`1.0.6`**.
- Create: Dragons Plus `1.11.7b` → **`1.11.8b`**.
- Create: Connected `1.3.2-mc1.21.1` → **`1.3.3-mc1.21.1`**.
- Create: Bits 'n' Bobs `2.2.7` → **`2.3.0`**.
- Create Stats `1.2.81/1.4.1` → runtime atual **`1.5.1`**.
- Create Cyber Goggles `8.3.14/8.3.15` → **`8.5.1`**.
- Lychee `6.5.4+neoforge` → **`6.6.1+neoforge`**.
- Azimuth `1.4.7` → **`1.4.8`**.
- KilaGraph `21.1.0.11/21.1.0.12` → **`21.1.0.14`**.
- Sophisticated Core `1.4.90` → **`1.5.0`**.
- Cyclops Core `1.29.3` → **`1.29.4`**.
- LowDragLib2 `2.2.37` → **`2.2.39.a`**.

## Tabela canônica exata — 200/200

A tabela foi dividida em quatro páginas de 50 registros para facilitar auditoria e leitura. O conjunto das quatro páginas é a tabela canônica completa de **200/200**.

- [Registros 001–050](CURRENT-MODLIST-001-050.md)
- [Registros 051–100](CURRENT-MODLIST-051-100.md)
- [Registros 101–150](CURRENT-MODLIST-101-150.md)
- [Registros 151–200](CURRENT-MODLIST-151-200.md)

Cada linha preserva exatamente `Arquivo JAR | Mod ID | Runtime name | Runtime version` da `modlist.txt` atual.
