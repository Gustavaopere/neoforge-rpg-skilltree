# Reconciliação atual da modlist — Gameplay e Sistemas

> **AUTORIDADE DE PRESENÇA/JAR/VERSÃO — 2026-08-30.** Este arquivo complementa os capítulos descritivos e prevalece quando um capítulo histórico ainda mostra um JAR ou versão anterior. O GitHub é a fonte canônica dos três guias temáticos.

A `modlist.txt` atual contém **573 entradas top-level**, incluindo o NeoForge modloader. A validação cruzada dos três guias cobre **572/572 arquivos `.jar` atuais**; NeoForge é tratado separadamente como modloader. O recorte Gameplay referencia **321 JARs**, com sobreposição intencional para mods cross-domain.

## Entradas incorporadas desde o snapshot temático anterior

| JAR atual | Mod / papel no pack |
|---|---|
| `integrated_simply_swords-1.4.0+1.21.1-neoforge.jar` | Integrated Simply Swords — integração de materiais/mods ao ecossistema Simply Swords. |
| `oracle_index-neoforge-1.3.1.jar` | Oracle Index — documentação/wiki in-game client-side. |
| `simplycataclysm-1.0.2+1.21.1+neoforge.jar` | Simply Swords: Cataclysm — bridge entre Simply Swords e L_Ender's Cataclysm. |
| `simplymore-forge-1.3.0_alpha.jar` | Simply More — expansão/addon de Simply Swords; build alpha instalada. |
| `simplyswords-neoforge-1.70.2-1.21.1.jar` | Simply Swords — arsenal e tipos de armas; a release instalada inclui correção de crash com Epic Fight. |
| `SimplyTooltips-neoforge-0.1.5.jar` | Simply Tooltips — apresentação data-driven de tooltips, client-side. |
| `JustEnoughResources-NeoForge-1.21.1-1.6.0.17.jar` | Just Enough Resources — dados de drops/worldgen integrados à consulta JEI. |
| `Oh-The-Trees-Youll-Grow-neoforge-1.21.1-5.3.2.jar` | Oh The Trees You'll Grow — biblioteca/infraestrutura de árvores usada pelo stack de biomas. |
| `Placebo-1.21.1-9.9.2.jar` | Placebo — biblioteca de infraestrutura. |
| `PresenceFootsteps-1.21.1-1.12.0-beta.1-1.21NeoForge.jar` | Presence Footsteps — áudio de passos client-side; build beta instalada. |
| `TerraBlender-neoforge-1.21.1-4.1.0.8.jar` | TerraBlender — API de worldgen/biomas. |
| `domum-ornamentum-1.0.234-snapshot-main.jar` | Domum Ornamentum — blocos decorativos e infraestrutura do ecossistema MineColonies. |
| `immersive_portals_true_immersion-2.0.4.jar` | Immersive Portals: True Immersion — interações adicionais através de portais. |
| `justenoughbreeding-neoforge-1.21.1-3.2.1.jar` | Just Enough Breeding — consulta de reprodução de entidades em JEI/REI/EMI. |
| `kotlinforforge-5.12.0-all.jar` | Kotlin for Forge — language loader/biblioteca. A linha da modlist não expõe metadata top-level confiável além do JAR; não inventar mod id/runtime. |
| `lambdynamiclights-4.8.10+1.21.1.jar` | LambDynamicLights — iluminação dinâmica client-side. |
| `lithostitched-1.8.0+beta4-neoforge-21.1.jar` | Lithostitched — biblioteca de worldgen/configurabilidade; build beta instalada. |
| `lmft-1.1.1+1.21.9-neoforge.jar` | Load My F***ing Tags — tolerância a entradas inválidas de tags; o JAR multi-versão suporta a linha 1.21.1 apesar do sufixo do filename. |
| `modonomicon-1.21.1-neoforge-1.120.4.jar` | Modonomicon — infraestrutura de documentação/livros in-game. |
| `prometheus-neoforge-1.21-1.2.5.jar` | Prometheus — API/backbone para fogo modded. |
| `rechiseled-1.2.5-neoforge-mc1.21.jar` | Rechiseled — variantes decorativas/chisel. |
| `rhino-2101.2.8-build.91.jar` | Rhino — runtime JavaScript usado por mods de scripting. |
| `shine-2.0.2+1.21.1-neoforge.jar` | Shine — VFX/bloom client-side; build beta instalada. |
| `struts-1.3.0.jar` | Strut Your Stuff — infraestrutura para blocos que atravessam espaços/collisions e integrações físicas. |
| `unchipped-1.21-1.2.jar` | UnChipped — conteúdo/variantes decorativas. |
| `worldweaver-21.0.25.jar` | WorldWeaver: New Dawn — biblioteca/utilitário de worldgen do stack BetterX. |

## Deltas de JAR/versão incorporados

Os seguintes identifiers atuais substituem referências mais antigas do snapshot de 28/08. A presença no pack deve ser lida por esta lista, mesmo se um parágrafo histórico ainda citar a versão anterior.

- `1.21.1-identity2-neoforge-2.2.2.jar` — Identity2 `2.2.2`.
- `AdvancedCoreInfo-neoforge-1.21.1-1.1.0.jar` — Advanced Core Info `1.1.0`.
- `AdvancedLootInfo-neoforge-1.21.1-2.1.0.jar` — Advanced Loot Info `2.1.0`.
- `FarmersDelight-1.21.1-1.3.4.jar` — Farmer's Delight `1.3.4`.
- `ParCool-1.21.1-4.0.0.3.jar` — ParCool `4.0.0.3`.
- `alexsmobs-2.1.9-neoforge+1.21.1.jar` — Alex's Mobs Continued `2.1.9`.
- `dtbetterend-1.21.1-2.0.0h2.jar` — Dynamic Trees - BetterEnd `2.0.0h2`.
- `dtbetternether-1.21.1-2.0.0h2.jar` — Dynamic Trees - BetterNether `2.0.0h2`.
- `dtvanillabackport-1.21.1-1.6.0.jar` — Dynamic Trees–VanillaBackport `1.6.0`.
- `enhancedai-4.2.2.2.jar` — Enhanced AI `4.2.2.2`.
- `iceandfire-2.1.2.jar` — Ice And Fire Community Edition `2.1.2`.
- `insanelib-2.4.30.0.jar` — InsaneLib `2.4.30.0`.
- `jei-1.21.1-neoforge-19.51.0.417.jar` — Just Enough Items `19.51.0.417`.
- `journeymap-neoforge-1.21.1-6.0.7.jar` — JourneyMap `6.0.7`.
- `letsdo-meadow-neoforge-1.4.8.jar` — [Let's Do] Meadow `1.4.8`.
- `minecolonies-1.1.1375-1.21.1-snapshot.jar` — MineColonies `1.1.1375-1.21.1-snapshot`.
- `modernfix-neoforge-5.27.23+mc1.21.1.jar` — ModernFix `5.27.23`.
- `polytone-1.21-4.1.0-neoforge.jar` — Polytone `4.1.0`.
- `sodium-neoforge-0.8.13+mc1.21.1.jar` — Sodium `0.8.13` estável instalado.
- `supplementaries-1.21.1-3.9.6-neoforge.jar` — Supplementaries `3.9.6`.

## Epic Fight × Simply Swords

O pack possui `epicfightcompat-1.1.0-mc1.21.1-neoforge.jar`. Essa camada declara suporte a **Simply Swords** e **Simply More** e atribui presets/capabilities do Epic Fight quando existe mapeamento limpo. Isso não significa que toda arma exótica recebe um moveset bespoke; conteúdo sem mapeamento específico pode cair em comportamento/preset compatível mais genérico. `Simply Swords 1.70.2` também inclui correção específica de crash com Epic Fight.

## Regra operacional

- `CURRENT-MODLIST.md` é a autoridade de **presença, JAR e versão** para este guia.
- Os capítulos continuam sendo a autoridade descritiva de função, interação, riscos e uso em perks, salvo correção posterior explícita.
- Biblioteca, UI, visual, compat ou infraestrutura não deve ser promovida automaticamente a provider mecânico de perk.
- Toda nova modlist deve ser reconciliada aqui antes de um novo lote de perks ser fechado.
