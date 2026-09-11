# Integrated Dungeons and Structures (IDAS)

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81e9a8a9d4594034bbe2
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Integrated Dungeons and Structures (IDAS)
- **Arquivo JAR:** `idas-1.13.7+1.21.1-neoforge.jar`
- **Versão 1.21.1:** 1.13.7+1.21.1-neoforge
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Worldgen, Exploração
- **Função:** Mod de structures/worldgen que adiciona estruturas altamente detalhadas usando conteúdo de Integrated API, Create, Quark, Supplementaries e integrações opcionais de outros mods.
- **Dependências:** Obrigatórias oficiais: Integrated API, Create, Quark e Supplementaries; todas presentes fisicamente. Integrações opcionais presentes incluem Alex's Mobs Continued, Terralith, Waystones, Farmer's Delight, Ice and Fire CE e Ars Nouveau.
- **Sobreposição:** Coexiste com Integrated Dungeons Arise e outros structure packs, mas não é duplicata integral. Auditar por structure_set, biome tags, spacing, terrain fit, loot e performance, não apenas por categoria.
- **Compatibilidade/Riscos:** Riscos: source drift, structure-set/biome placement, colisões com grande stack de worldgen, loot/spawner inflation, referências opcionais condicionais, hybrid chunks após update e remoção de providers usados por estruturas já persistidas.
- **Observações:** Release exata 1.13.7 adiciona Pumpkin Cafe (Howester) e corrige mais missing-texture books em Tinker's Citadel. Source GitHub público acessível não possui linha 1.21.1 correspondente; internals exatos permanecem fail-closed.
- **Procedência:** modlist.txt física atual + CurseForge oficial IDAS 1.13.7 file 8232359 + documentação oficial de dependencies/integrations + changelog 1.13.7; GitHub público craisinlord/IDAS está defasado em 1.18.2 e não é source authority desta build.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/idas/files/8232359
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — IDAS 1.13.7 release-pinned; dependencies, structure/worldgen authority, optional integrations, 1.13.7 changes, lifecycle/save risks e matriz de testes catalogados; source público 1.21.1 exato indisponível.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `idas-1.13.7+1.21.1-neoforge.jar`, mod id `idas`, versão `1.13.7+1.21.1-neoforge`. A release oficial NeoForge 1.21.1 é o file 8232359 de 11/06/2026. O repositório público `craisinlord/IDAS` acessível está na linha antiga 1.18.2; portanto esta ficha é release-pinned, não source-pinned.

## 1. Papel e authority
Integrated Dungeons and Structures (IDAS) adiciona estruturas altamente detalhadas construídas com conteúdo de vários providers. IDAS é authority de seus templates, placement/data, processors, loot e composição estrutural; Create, Quark, Supplementaries e demais mods continuam owners de seus blocos, mobs e itens.

## 2. Dependências obrigatórias
A documentação oficial lista Integrated API, Create, Quark e Supplementaries como requeridos. Todos estão fisicamente presentes: Integrated API 1.8.0, Create 6.0.10, Quark 4.1-483 e Supplementaries 3.9.8. Remover um provider obrigatório pode impedir data load ou deixar blocos ausentes em estruturas já geradas.

## 3. Integrações opcionais
O projeto lista integrações opcionais com Biomes O' Plenty, Alex's Mobs, Guard Villagers, Artifacts, BYG, Regions Unexplored, Terralith, Waystones, Farmer's Delight, Ice and Fire, Ars Nouveau e Better Archeology. No pack atual foram confirmados Alex's Mobs Continued 2.1.11, Terralith 2.6.2, Waystones 21.1.44, Farmer's Delight 1.3.4, Ice and Fire CE 2.1.2 e Ars Nouveau 5.13.1. Ausência de outros opcionais não deve ser tratada como erro sem referência data-driven concreta.

## 4. Integrated API
Integrated API é infraestrutura de compatibilidade/worldgen da série Integrated e deve permanecer separada como provider técnico. Bugs de terrain integration compartilhados podem pertencer à API e não ao template IDAS individual. Atualizações de IDAS e Integrated API precisam ser regression-tested em conjunto.

## 5. Worldgen e placement
IDAS participa diretamente de geração de estruturas. O impacto real deve ser avaliado por structure sets, biome targeting, spacing/separation, terrain fit e processors. Em um pack com muitos structure/worldgen mods, conflito relevante é colisão espacial, densidade, geração cortada/flutuante e custo de chunk generation — não mera presença de duas estruturas tematicamente parecidas.

## 6. Conteúdo modded dentro das estruturas
Os templates podem referenciar blocos/itens/mobs de dependencies e integrações. IDAS não assume authority desses registries. Uma atualização/removal de provider após chunks já gerados deve ser tratada como risco de save, porque os blocos persistidos continuam pertencendo ao provider original.

## 7. Loot, spawners e progressão
Estruturas grandes podem concentrar loot, spawners e encounters. O pack possui quests e outros sistemas de rewards; descobrir uma estrutura não deve conceder automaticamente loot/reward duplicado por listener externo. Balance deve considerar loot total por rota de exploração, não apenas uma chest isolada.

## 8. Release 1.13.7
O changelog exato da build instalada registra dois pontos: **Pumpkin Cafe**, construída por Howester, foi adicionada; e foram corrigidos mais livros com textura ausente em **Tinker's Citadel**. Esses são regression gates específicos da 1.13.7.

## 9. Client / server
A distribuição oficial é Client & Server. O servidor decide worldgen, loot, spawns e state persistido; o cliente precisa dos assets/registries dos providers usados nos templates. Mismatch de modlist/data entre lados pode produzir missing registry ou presentation inconsistente.

## 10. Lifecycle e persistência
Validar criação de mundo, primeira geração de chunks, exploração simultânea, save/restart, `/reload`, update de IDAS/Integrated API e adição/remoção de integração opcional. Chunks já gerados não são redesenhados automaticamente quando templates mudam; updates podem criar fronteiras de conteúdo entre regiões antigas e novas.

## 11. Multiplayer e performance
Com vários jogadores explorando áreas novas, estruturas podem aumentar custo de worldgen, block entities e mob density. Medir spikes de tick/chunk generation e evitar aumentar frequência de structures sem teste por seed/região. Loot contention e spawner activation também devem ser avaliados em multiplayer.

## 12. Relação com Integrated Dungeons Arise
`IDA v2.1.1-1.21.1.jar` também está instalado. Os dois projetos pertencem ao ecossistema Integrated e podem coexistir, mas cada um possui seu próprio catálogo de structures/data. Não deduplicar por nome; comparar placements, loot e dependency sets concretos.

## 13. Source drift
O GitHub público `craisinlord/IDAS` encontrado está em `master` com `mod_version=1.4.3`, Minecraft 1.18.2, sem branch pública 1.21 localizada nesta auditoria. Ele não pode ser usado para afirmar classes/IDs/internals da 1.13.7. Para esta build, release oficial e dados físicos prevalecem.

## 14. Riscos técnicos
- source público incompatível ser usado como se fosse 1.13.7;
- missing provider/reference em template ou processor;
- structure collision e densidade excessiva;
- loot/spawner inflation;
- optional integration não degradar de forma limpa;
- update criar chunks híbridos antigos/novos;
- remover provider após estrutura persistida;
- `/reload` alterar data sem reconstruir estruturas existentes;
- custo elevado de chunk generation em exploração simultânea.

## 15. Matriz de testes obrigatória
- [ ] Dedicated server boot com IDAS 1.13.7 e quatro hard dependencies.
- [ ] Registry/datapack load sem missing references.
- [ ] Seed fixa gera amostra de structures sem terrain corruption crítica.
- [ ] Pumpkin Cafe gera com blocks/loot válidos.
- [ ] Tinker's Citadel não exibe missing-texture books corrigidos na 1.13.7.
- [ ] Integrações opcionais presentes carregam sem duplicate/missing IDs.
- [ ] Ausência dos opcionais não instalados degrada sem hard-fail.
- [ ] Coexistência com Integrated Dungeons Arise não causa colisão/loot inflation crítica.
- [ ] `/reload` conclui sem structure/loot/processor errors.
- [ ] Restart preserva chunks gerados sem missing blocks/entities.
- [ ] Exploração multiplayer de chunks novos não produz regressão severa de tick.

## 16. Evidências e limites
- **Modlist física:** JAR, mod id, versão e mixin `idas-common.mixins.json`.
- **CurseForge oficial:** file 8232359, Release NeoForge 1.21.1, dependencies/integrations e changelog 1.13.7.
- **GitHub público:** linha encontrada é 1.18.2 e serve somente como histórico, não como authority da build atual.
- **Limite:** registry IDs/templates/processors exatos do JAR 1.13.7 não foram extraídos nesta execução.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
