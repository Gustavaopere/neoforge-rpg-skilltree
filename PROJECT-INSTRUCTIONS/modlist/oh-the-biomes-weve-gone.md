# Oh The Biomes We've Gone

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81a1bebbc1269d2c57e6
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `Oh-The-Biomes-Weve-Gone-NeoForge-2.6.0.jar`, mod id `biomeswevegone`, runtime `2.6.0`; CorgiLib `5.0.0.9`, GeckoLib `4.9.2`, Oh The Trees You'll Grow `5.3.2`, TerraBlender `4.1.0.8`, Terralith `2.6.2` e Tectonic `3.0.26` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, BWG 2.6.0 e toda a dependency/worldgen stack citada acima estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Oh The Biomes We've Gone
- **Arquivo JAR:** `Oh-The-Biomes-Weve-Gone-NeoForge-2.6.0.jar`
- **Versão 1.21.1:** 2.6.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Worldgen, Exploração
- **Função:** Grande provider de 50+ biomas e conteúdo ambiental/worldgen, com vegetação, materiais e features integradas via TerraBlender e Oh The Trees You'll Grow.
- **Dependências:** Required: CorgiLib, GeckoLib, Oh The Trees You'll Grow e TerraBlender. Optional publicada: WTHIT Forge Edition.
- **Sobreposição:** Sobreposição ampla de paisagens com Terralith; Tectonic atua principalmente na forma do terreno e declara composição com biome mods. Tratar como composição de worldgen, não duplicata simples.
- **Compatibilidade/Riscos:** Grande provider de biomas/worldgen. Riscos: distribuição/densidade com Terralith+Tectonic, config JSON5→JSON, biome/tag drift, tree backend, existing-world borders e addons dependentes. Nenhum conflito estrutural atual foi comprovado.
- **Observações:** Runtime 2.6.0, file ID 8245253, Release 14/06/2026. 2.6.0 migra worldgen config JSON5→JSON, melhora missing keys/comments, permite desligar Fine Grained Vanilla Biome Features e ajusta tags/recipes.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial 2.6.0/relations + source oficial Potion-Studios branch 1.21.1/changelog.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/oh-the-biomes-weve-gone
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — BWG 2.6.0 reconstruído: 50+ biomas, dependency chain, TerraBlender/Trees, config JSON migration, Fine Grained Vanilla Features, tags/recipes, Terralith+Tectonic, lifecycle, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `Oh-The-Biomes-Weve-Gone-NeoForge-2.6.0.jar`, mod id `biomeswevegone`, versão `2.6.0`, NeoForge 1.21.1. BWG é um grande provider de biomas/worldgen com **50+ biomas** publicados. A release 2.6.0 altera o formato/configuração de worldgen e possui dependências obrigatórias CorgiLib, GeckoLib, Oh The Trees You'll Grow e TerraBlender. No pack atual, a análise deve considerar composição com Terralith + Tectonic, não pressupostos antigos de TFC/Nature's Spirit.

## 1. Identidade e papel
- **Mod:** Oh The Biomes We've Gone — BWG.
- **JAR físico:** `Oh-The-Biomes-Weve-Gone-NeoForge-2.6.0.jar`.
- **Mod id:** `biomeswevegone`.
- **Runtime:** `2.6.0`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Projeto:** Potion Studios / sucessor de Oh The Biomes You'll Go.
- **Ambiente:** Client & Server.
- **Papel:** grande expansão de biomas, vegetação, blocos naturais, recursos e features de geração.
- **Escopo publicado:** mais de 50 biomas.
- **Decisão:** Sem decisão.

## 2. Dependências oficiais
A relação oficial da release/projeto lista como Required Dependency:
- **CorgiLib**;
- **GeckoLib**;
- **Oh The Trees You'll Grow**;
- **TerraBlender**.
WTHIT Forge Edition aparece como integração opcional.

Essas dependencies não devem ser confundidas com conteúdo do BWG. TerraBlender fornece infraestrutura de composição de biomas; Trees fornece geração/template de árvores; GeckoLib/CorgiLib oferecem infraestrutura compartilhada.

## 3. Authority de worldgen
BWG é authority dos biomas/features/blocks que registra, mas não é authority exclusiva da forma global do terreno do pack.

No stack atual:
- **BWG** adiciona biomas e conteúdo ambiental;
- **Terralith** adiciona/reestrutura biomas e worldgen por datapack;
- **Tectonic** atua fortemente na forma/escala do terreno e declara compatibilidade com biome mods baseados em TerraBlender/Biolith, incluindo BWG, além de integração com Terralith.

Portanto a coexistência deve ser testada por distribuição/densidade/transições, não tratada como incompatibilidade automática.

## 4. Biomas e distribuição
A página oficial publica mais de 50 biomas. O objetivo do dossiê não é transformar marketing em uma lista inventada de registry IDs: para quests, KubeJS, spawn rules ou mapas, os IDs reais devem ser extraídos da build 2.6.0.

Superfícies críticas:
- frequência relativa;
- adjacency/transições;
- climate/temperature tags;
- biome tags usadas por mobs/outros mods;
- compatibilidade com estruturas e features externas.

## 5. Vegetação, blocos e recursos
BWG adiciona vegetação e materiais associados aos seus biomas. Esses elementos entram em tags, recipes, loot, fuel/building ecosystems e podem ser consumidos por addons externos.

Não atribuir uma árvore inteira ao BWG sem considerar que a geração física pode ser mediada por Oh The Trees You'll Grow. BWG é provider do conteúdo/bioma; Trees fornece parte da infraestrutura de geração arbórea.

## 6. Release 2.6.0 — migração de configuração
O changelog exato da 2.6.0 registra:
- worldgeneration config migrado de **JSON5 para JSON**;
- correção para atualizar chaves ausentes;
- migração automática de arquivos legacy para o novo formato nessa linha;
- mais comentários/documentação de config;
- opção para desabilitar **Fine Grained Vanilla Biome Features** no NeoForge;
- Green Apple adicionado à tag de alimento de cavalos;
- recipes com ovos migrados para `#c:eggs`.

Esses deltas são tratados como específicos da 2.6.0; conteúdo anterior não é rotulado como novidade desta release.

## 7. Fine Grained Vanilla Biome Features
A opção de desabilitar Fine Grained Vanilla Biome Features é relevante porque BWG pode participar da injeção/composição de features em biomas vanilla.

Num pack com vários worldgen providers, esse toggle deve ser avaliado com seed fixa:
- comparar densidade e repetição de features;
- observar se estruturas/vegetação desaparecem ou duplicam;
- registrar o config efetivo antes de alterar qualquer valor.

## 8. Config migration e mundos existentes
A troca JSON5→JSON exige atenção operacional:
- não manter dois arquivos concorrentes e assumir que ambos são lidos;
- guardar backup do config antigo antes de update;
- confirmar quais valores foram migrados;
- checar missing keys adicionadas pela nova versão;
- não importar instruções de 2.7+ para esta build 2.6.0.

Worldgen já gerado não é refeito automaticamente. Alterações de config afetam principalmente chunks novos.

## 9. Oh The Trees You'll Grow
#435 é dependency obrigatória e fornece tree generation por structure templates. Isso cria uma cadeia de ownership clara:
- BWG define biomas/conteúdo e solicita árvores;
- Trees resolve infraestrutura/template de geração;
- remover Trees quebra o provider;
- substituir por Dynamic Trees não satisfaz automaticamente a dependency.

O pack ainda possui **Dynamic Trees - Oh The Biomes We've Gone** como integração separada; esse addon deve ser tratado como bridge, não como substituto automático da library obrigatória.

## 10. TerraBlender e composição regional
TerraBlender é dependency de composição de biomas. Em conjunto com Tectonic/Terralith, os testes precisam verificar se a seleção regional preserva diversidade sem produzir zonas absurdamente raras ou frequências desbalanceadas.

Não existe base para afirmar que TerraBlender “controla todo o terreno”; seu papel aqui é infrastructure de biome placement/composição usada pelo provider.

## 11. Tags, recipes e interoperabilidade
A 2.6.0 altera explicitamente duas superfícies de tags/recipes:
- **Green Apple** passa a integrar horse food tag;
- recipes de ovos passam a usar **`#c:eggs`**.

Isso melhora interoperabilidade, mas amplia a dependência de tags corretas. `/reload` deve manter recipes e tags consistentes com alimentos/ovos de outros mods.

## 12. Estruturas, mobs e addons consumidores
BWG serve de provider para vários addons/compat layers presentes ou catalogados no pack, incluindo Dynamic Trees BWG, resource supports e integrações culinárias/sazonais.

Regra de remoção: antes de remover BWG, mapear todos os consumers, porque o impacto não é apenas “perder biomas”; recipes/tags/worldgen bridges podem depender de seus IDs.

A ficha não atribui estruturas ou mobs específicos sem evidência da linha 2.6.0 para cada item.

## 13. Client/server e lifecycle
Server-authoritative:
- biome selection/worldgen;
- features/structures;
- block/entity state;
- tags/recipes data-driven.

Client-facing:
- textures/models/foliage colors;
- particles/animações quando aplicáveis;
- apresentação em mapas/viewers.

Eventos críticos: criação de mundo, geração de chunk novo, `/reload`, restart e migração de config.

## 14. Riscos no pack
1. **Distribuição composta:** BWG + Terralith + Tectonic podem alterar frequência/variedade percebida.
2. **Feature density:** múltiplos providers podem concentrar vegetação/features em algumas regiões.
3. **Config migration:** JSON5→JSON pode deixar valores antigos esquecidos ou defaults novos inesperados.
4. **Tree backend:** mismatch com Oh The Trees You'll Grow pode quebrar árvores/worldgen.
5. **Biome tags:** mobs, seasons, maps e quests podem depender de tags/IDs.
6. **Existing world:** chunks antigos e novos podem ter fronteiras/geração visualmente diferentes.
7. **Addon dependency graph:** remover BWG pode quebrar bridges e resource/data integrations.
8. **Recipe/tag drift:** `#c:eggs` e outras common tags precisam estar coerentes.
9. **Performance:** 50+ biomas e amplo conteúdo aumentam o espaço de worldgen a validar.

## 15. Matriz de testes
- [ ] Dedicated server e cliente iniciam com BWG 2.6.0 + quatro required dependencies.
- [ ] Mundo novo com seed fixa gera variedade BWG sem crash de biome placement.
- [ ] Terralith + Tectonic + BWG coexistem e produzem transições plausíveis.
- [ ] Comparar Fine Grained Vanilla Biome Features conforme config efetivo.
- [ ] Trees BWG geram com Oh The Trees You'll Grow 5.3.2 sem truncamento/overlap destrutivo.
- [ ] `/reload` mantém biome tags, `#c:eggs` e recipes consistentes.
- [ ] Green Apple funciona como horse food segundo tags da release.
- [ ] Config JSON migrado contém valores esperados e não depende do antigo JSON5.
- [ ] Existing world abre; chunks novos não causam erro/data migration.
- [ ] Addons BWG instalados registram conteúdo sem missing ID.
- [ ] Biome locators/maps reconhecem biomas válidos conforme tags/config.
- [ ] Medir worldgen/tick/loading em exploração rápida de chunks novos.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 16. Evidências e limites
- Modlist física: JAR, mod id/runtime e `biomeswevegone-common.mixins.json`; hash físico `4611dc5bb4240333178e14e81f76168f78167448`.
- CurseForge oficial: project 1070751, file ID 8245253, Release NeoForge 1.21.1 de 14/06/2026, 50+ biomas, Client & Server.
- Relations oficiais: CorgiLib, GeckoLib, Oh The Trees You'll Grow e TerraBlender required; WTHIT optional.
- Source oficial `Potion-Studios/Oh-The-Biomes-Weve-Gone`, branch 1.21.1, com changelog 2.6.0 coerente com os deltas documentados.
- **Limite:** este dossiê não inventa uma enumeração completa de registry IDs, estruturas ou conteúdo por bioma; para scripting, extrair IDs/dados da build física.
