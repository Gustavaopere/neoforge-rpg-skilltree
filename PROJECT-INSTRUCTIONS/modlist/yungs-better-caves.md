# YUNG's Better Caves

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db810e8fb8c0c04b186530
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** YUNG's Better Caves
- **Arquivo JAR:** `YungsBetterCaves-1.21.1-NeoForge-3.1.6.jar`
- **Versão 1.21.1:** 1.21.1-NeoForge-3.1.6
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Worldgen, Exploração
- **Função:** Reformula profundamente a geração de cavernas com novos tipos, escalas e formas subterrâneas.
- **Dependências:** YUNG's API 5.1.8. Coexiste no pack com Terralith 2.6.2, Oh The Biomes We've Gone 2.6.0, TerraBlender 4.1.0.8 e várias structures subterrâneas.
- **Sobreposição:** Cruza com outros sistemas atuais de cave shaping, terreno e hidrologia; função principal é geração de cavernas e não substitui estruturas subterrâneas como mineshafts/dungeons.
- **Compatibilidade/Riscos:** Overhaul amplo de cave carving/hidrologia. Riscos: aquifer/carver interaction, interseções com structures, chunkgen cost e seams após updates. Upstream pretende compatibilidade ampla, mas o stack real exige runtime QA.
- **Observações:** Mod id `bettercaves`, runtime `1.21.1-NeoForge-3.1.6`. Release atual de 04/09/2026; corrige ServiceLoader unsafe call e melhora aquifer error handling.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial YUNG's Better Caves 3.1.6 + stack worldgen atual.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/yungs-better-caves
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — 3.1.6 reconciliada; cave carving, aquifers, dimension config e worldgen lifecycle catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-09

# Dossiê operacional — padrão Alex's Mobs

> 🕳️ **ESCOPO CANÔNICO.** Runtime físico: `YungsBetterCaves-1.21.1-NeoForge-3.1.6.jar`, mod id `bettercaves`, versão `1.21.1-NeoForge-3.1.6`. É overhaul de cave carving/hidrologia subterrânea, não estrutura de dungeon.

## 1. Função
Better Caves reformula geração subterrânea com sistemas de cavernas maiores e mais variados, underground lakes/rivers, flooded caverns e lava oceans. A configuração suporta comportamento por dimensão.

## 2. Build atual
A 3.1.6 é Release NeoForge 1.21.1 publicada em 04/09/2026 e corresponde ao JAR físico. O changelog melhora o tratamento de erro de aquifer e corrige chamada insegura de `ServiceLoader#load`.

## 3. Authority
Better Caves é authority do seu cave-carving/worldgen. Better Mineshafts/Dungeons são structures separadas; TerraBlender/Terralith/BYG e outros terrain/biome providers mantêm suas próprias authorities.

## 4. Stack atual e composição
O pack possui Terralith 2.6.2, Oh The Biomes We've Gone 2.6.0, TerraBlender 4.1.0.8 e várias structures subterrâneas. A compatibilidade pretendida pelo upstream não elimina necessidade de testar interseções, aquifers e custo de chunkgen no conjunto real.

## 5. Lifecycle
Somente chunks novos recebem geração nova normal. Não tratar update de cave generator como retroativo. Validar world creation, chunkgen, save/restart, dimensão, aquifers e seed fixa.

## 6. Boundary para quests/perks
Chunk carving não é evento causal de Mastery. Discovery subterrânea deve usar biome/structure/POI real quando houver; não usar presença de Better Caves como proxy de exploração concluída.

## 7. Riscos
- conflitos de carver/aquifer com terrain mods;
- rivers/lakes subterrâneos cortando structures;
- custo de chunkgen;
- seam entre chunks antigos e novos após update;
- configuração por dimensão divergente.

## 8. Matriz de testes
- [ ] Dedicated server cria mundo com 3.1.6.
- [ ] Overworld gera caves/rivers/lakes sem aquifer spam/failure.
- [ ] Nether/End respeitam config efetiva.
- [ ] Better Mineshafts/Dungeons não apresentam interseções destrutivas recorrentes.
- [ ] Terralith/BYG/TerraBlender coexistem sem crash.
- [ ] Seed smoke e chunkgen stress não revelam regressão severa.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 9. Limitação
Configs efetivas e algoritmo interno não foram decompilados nesta etapa; compatibilidade final permanece dependente de runtime worldgen QA.