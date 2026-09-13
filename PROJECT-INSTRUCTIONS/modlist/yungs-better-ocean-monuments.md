# YUNG's Better Ocean Monuments

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81c09cf2ca545617406c
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** YUNG's Better Ocean Monuments
- **Arquivo JAR:** `YungsBetterOceanMonuments-1.21.1-NeoForge-4.1.2.jar`
- **Versão 1.21.1:** 1.21.1-NeoForge-4.1.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Worldgen, Exploração
- **Função:** Reformula ocean monuments em estruturas maiores, variadas e mais complexas, com nova exploração e loot.
- **Dependências:** YUNG's API 5.1.8. Coexiste com Terralith 2.6.2, Oh The Biomes We've Gone 2.6.0 e outros worldgen providers atuais.
- **Sobreposição:** Redesign específico de Ocean Monument; overlap com outros ocean structures é de placement/loot, não authority. Biome/terrain continuam pertencendo aos respectivos providers.
- **Compatibilidade/Riscos:** Riscos: worldgen deadlock regression, density/intersections, randomized-layout accessibility, loot inflation e duplicate quest progress por subpiece/reload.
- **Observações:** Mod id `betteroceanmonuments`, runtime `1.21.1-NeoForge-4.1.2`. 4.1.2 corrige feature-mixin worldgen deadlocks; monuments são maiores/randomizados e podem conter Tridents/Heart of the Sea.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Better Ocean Monuments NeoForge 4.1.2 + stack worldgen atual.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/yungs-better-ocean-monuments-neoforge
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — randomized layouts, loot, worldgen-deadlock fix e quest boundaries catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-09

# Dossiê operacional — padrão Alex's Mobs

> 🌊 **ESCOPO CANÔNICO.** Runtime físico: `YungsBetterOceanMonuments-1.21.1-NeoForge-4.1.2.jar`, mod id `betteroceanmonuments`, versão `1.21.1-NeoForge-4.1.2`. Redesenha Ocean Monuments com layouts maiores e randomizados; não controla ocean biomes/terrain.

## 1. Conteúdo confirmado
O upstream descreve monuments muito maiores que os vanilla, com **layouts completamente randomizados** e loot mais valioso, incluindo possibilidade de Tridents e Heart of the Sea.

## 2. Build 4.1.2
A release exata 4.1.2 corrige um problema em que feature mixins podiam causar **worldgen deadlocks**. Esse fix é especialmente importante em um modpack com muitos worldgen providers.

## 3. Authority
Better Ocean Monuments é authority da structure/layout/pieces/loot que registra. Ocean biome providers e terrain mods permanecem authorities do ambiente onde a structure é posicionada.

## 4. Stack atual
O pack possui Terralith, Oh The Biomes We've Gone e outros worldgen providers. O upstream declara intenção de compatibilidade ampla, inclusive com grandes biome mods, mas isso não substitui smoke real de placement e chunkgen no conjunto atual.

## 5. Boundary para quests/perks
- Discovery deve usar structure identity real.
- Randomized rooms/pieces não são structures independentes para progressão.
- Elder Guardian kill não equivale automaticamente a “monument completed” salvo design explícito.
- Loot encontrado não deve gerar múltiplos milestones ao reabrir containers.

## 6. Lifecycle
Validar chunk generation, structure start/pieces, save/restart, chunk unload/reload, randomized layout determinism por seed e loot container persistence.

## 7. Riscos
- regressão de deadlock/worldgen;
- density/intersection com outras ocean structures;
- loot inflation por Trident/Heart of the Sea;
- randomized layout gerando rooms inacessíveis;
- duplicate quest progress por subpiece/chunk reload.

## 8. Matriz de testes
- [ ] Dedicated server gera Better Ocean Monument 4.1.2 sem deadlock.
- [ ] Layout randomizado é navegável e completo.
- [ ] Seed/restart preserva structure state.
- [ ] Biome/worldgen stack atual não impede placement de forma anormal.
- [ ] Loot especial não duplica em reload.
- [ ] Multiplayer discovery/completion é deduplicado.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 9. Limitação
Não foram enumerados structure-set/pool/loot-table IDs da 4.1.2. Hooks de quest ou integração programática exigem inspeção do registry/resources do JAR exato.