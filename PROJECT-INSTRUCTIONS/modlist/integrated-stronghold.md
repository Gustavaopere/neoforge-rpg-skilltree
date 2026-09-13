# Integrated Stronghold

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81fa8a62c5005eed8c4f
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Integrated Stronghold
- **Arquivo JAR:** `integrated_stronghold-1.1.4+1.21.1-neoforge.jar`
- **Versão 1.21.1:** 1.1.4+1.21.1-neoforge
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Worldgen, Exploração
- **Função:** Substitui o stronghold vanilla por uma megaestrutura de exploração com mais de 50 salas, lore, puzzles, traps, spawners e integração de blocos/conteúdo de vários mods.
- **Dependências:** Obrigatórias oficiais e presentes: Integrated API 1.8.0, Create 6.0.10, Supplementaries 3.9.8 e Quark 4.1-483. Integrações opcionais devem ser avaliadas por presença; Waystones e Farmer's Delight estão presentes no pack.
- **Sobreposição:** É o overhaul de stronghold ativo identificado no pack. Não há YUNG's Better Strongholds top-level na modlist vigente. Outros worldgen mods ainda podem interferir em terrain/biomes/placement, portanto a ausência de concorrente direto não elimina regressões de geração.
- **Compatibilidade/Riscos:** Substituição estrutural de grande escala do stronghold vanilla. Riscos: acesso ao End quebrado, structure placement/terrain collisions, spawner/loot inflation, puzzle state, optional integration drift e chunks híbridos. YUNG's Better Strongholds NÃO está instalado atualmente; não existe esse conflito top-level no pack vigente.
- **Observações:** Release 1.1.4 corrige advancements e music discs. O projeto documenta configuração de spacing/separation, biome tags, custom Integrated Structure Spawners e integration datapacks. A referência antiga a YUNG's Better Strongholds foi removida por ausência física.
- **Procedência:** modlist.txt física atual + CurseForge oficial Integrated Stronghold 1.1.4 NeoForge 1.21.1 + descrição/configuração/changelog oficiais. Source exato não foi pinado como authority nesta auditoria.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/integrated-stronghold
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Integrated Stronghold 1.1.4 release-pinned; megaestrutura >50 rooms, lore/puzzles/traps, structure-set/biome/spawner data, optional integrations, End-progression authority, lifecycle/worldgen risks e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `integrated_stronghold-1.1.4+1.21.1-neoforge.jar`, mod id `integrated_stronghold`, versão `1.1.4+1.21.1-neoforge`. A release oficial NeoForge 1.21.1 é 1.1.4. No pack atual, **YUNG's Better Strongholds não está instalado**.

## 1. Papel e authority
Integrated Stronghold substitui o stronghold vanilla por uma megaestrutura detalhada orientada à exploração. Ele é authority do novo layout, templates, placement, lore, puzzles, traps e spawners que adiciona; Minecraft continua owner do End Portal e regras-base de progressão ao End, enquanto Create/Quark/Supplementaries continuam owners de seus blocos e mecanismos.

## 2. Dependências obrigatórias
A documentação oficial exige Integrated API, Create, Supplementaries e Quark. No pack estão Integrated API 1.8.0, Create 6.0.10, Supplementaries 3.9.8 e Quark 4.1-483. Alterações em qualquer provider podem afetar templates, block entities, recipes ou mecanismos usados nas salas.

## 3. Escala e catálogo estrutural
O projeto documenta **mais de 50 salas únicas** e descreve a estrutura como próxima do maior tamanho suportado pelo sistema de geração. Isso amplia custo de worldgen e número de chunk boundaries envolvidos. Não converter a contagem pública em registry count sem extração da build.

## 4. Lore, puzzles e traps
Há livros de lore em chests, puzzles e traps integrados ao percurso. Quests externas devem observar milestones reais e não conceder recompensas duplicadas por ler um livro, abrir uma chest e completar a sala se todos representam o mesmo avanço narrativo.

## 5. Acesso ao End
Por substituir o stronghold, este mod toca uma rota crítica do jogo. O End Portal precisa permanecer alcançável e funcional; geração cortada, sala inacessível ou portal room ausente é regressão de progressão severa. Testes devem usar seeds novas e localizar múltiplos strongholds.

## 6. Structure sets
A documentação permite ajustar `spacing` e `separation` do structure set. `separation` deve permanecer menor ou igual a `spacing`. Mudanças alteram frequência/distribuição e só afetam geração futura; não reconstroem chunks existentes.

## 7. Biome tags
Biomas elegíveis podem ser controlados por tags em `tags/worldgen/biome/has_structure`. Com muitos biome/worldgen mods, a regra precisa ser validada contra tags reais do datapack carregado. Uma tag ampla demais pode colocar strongholds em terreno inadequado; restrita demais pode tornar progressão rara.

## 8. Integrated Structure Spawners
O projeto documenta arquivos `integrated_structure_spawners` para definir mobs utilizados. Esses spawns pertencem ao sistema estrutural, mas as entidades continuam sob authority de seus providers. Missing entity ID deve falhar de forma diagnosticável, não gerar substituto arbitrário.

## 9. Integrações opcionais
O projeto oferece datapacks de integração opcionais. No pack, Waystones e Farmer's Delight estão presentes e podem participar quando houver data correspondente. Presença de um mod não autoriza inferir que toda integração opcional está ativa; validar arquivos/tags realmente carregados.

## 10. Release 1.1.4
O changelog exato da 1.1.4 corrige **advancements** e **music discs**. Ambos entram como regression gates: progressão associada à estrutura precisa disparar corretamente e discos devem manter loot/obtenção e assets válidos.

## 11. Boundary com YUNG's Better Strongholds
A ficha antiga já corrigiu um falso conflito: **YUNG's Better Strongholds não consta na modlist física atual**. Não reintroduzir esse mod como concorrente hipotético. Outros mods de cavernas/terreno ainda podem alterar o espaço onde o stronghold tenta gerar, mas são outra classe de interação.

## 12. Client / server
Worldgen, loot, spawners, advancements e portal state são server-authoritative. O cliente renderiza blocos, livros, partículas e áudio. Mismatch de data/registry entre lados pode impedir conexão ou gerar apresentação incorreta.

## 13. Lifecycle e persistência
Validar world creation, locate/first generation, exploração parcial, chunk unload/reload, server restart, `/reload`, entrada no End e update do mod/dependencies. Estruturas já geradas permanecem persistidas; updates podem criar strongholds de versões diferentes no mesmo mundo.

## 14. Riscos técnicos
- portal room ausente/inacessível;
- estrutura cortada por terrain/cave generation;
- spacing/separation inviabilizar descoberta;
- spawner ID inválido ou densidade excessiva;
- puzzle/Create state quebrar após unload;
- loot/lore/advancement duplicar milestones;
- optional datapack referenciar provider ausente;
- regressão em music discs/advancements;
- custo alto de geração de megaestrutura;
- confundir outro terrain mod com segundo overhaul de stronghold inexistente.

## 15. Matriz de testes obrigatória
- [ ] Dedicated server inicia com quatro hard dependencies.
- [ ] `/locate`/exploração encontra strongholds válidos em seeds de teste.
- [ ] Mais de um stronghold gera sem peças órfãs/terrain corruption crítica.
- [ ] End Portal room existe, é acessível e ativa corretamente.
- [ ] Create puzzles/traps continuam funcionais após chunk unload/reload.
- [ ] Lore books/chests mantêm conteúdo esperado sem dupe.
- [ ] Integrated Structure Spawners geram entidades válidas.
- [ ] Advancements corrigidos em 1.1.4 disparam uma única vez.
- [ ] Music discs da estrutura não apresentam missing asset/loot regression.
- [ ] Optional integrations degradam corretamente quando um provider não participa.
- [ ] Restart mantém portal/puzzle/spawner state.

## 16. Evidências e limites
- **Modlist física:** JAR/mod id/version e ausência de YUNG's Better Strongholds.
- **CurseForge oficial:** release 1.1.4, >50 rooms, lore/puzzles/traps, dependencies e configuração de worldgen/spawners.
- **Changelog 1.1.4:** fixes de advancements e music discs.
- **Limite:** source code/template registry exatos não foram pinados nesta execução; contagens e IDs além do publicado permanecem fail-closed.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
