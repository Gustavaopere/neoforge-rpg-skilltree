# Just Enough Resources (JER)

## Propriedades do registro

- **Mod:** Just Enough Resources (JER)
- **Arquivo JAR:** JustEnoughResources-NeoForge-1.21.1-1.6.0.17.jar
- **Versão 1.21.1:** 1.6.0.17
- **Categoria:** QoL, Worldgen
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/just-enough-resources-jer
- **Função:** Addon de JEI que apresenta informação de recursos do mundo, drops de mobs, loot e villagers para consulta; não altera o worldgen, loot tables ou trades authoritative.
- **Dependências:** JEI e NeoForge 1.21.1. Pack físico usa JEI 19.56.0.440. Build física JER 1.6.0.17 é a linha oficial NeoForge 1.21.1 instalada.
- **Compatibilidade/Riscos:** 1.6.0.17 é Alpha oficial. Riscos: dados de ore/worldgen/loot stale com datapacks/TFC/worldgen customizado, JEI API drift, informações divergentes de loot dinâmico e sobreposição parcial com Advanced Loot Info. O runtime/provider sempre prevalece sobre a UI.
- **Sobreposição:** Sobreposição parcial com Advanced Loot Info no domínio de loot. JER também cobre recursos/worldgen e villagers, portanto não é duplicata completa. Não substitui JEI nem os providers reais de loot/worldgen.
- **Observações:** Build 1.6.0.17 permanece Alpha; o changelog exato adiciona tradução portuguesa (#518). A antiga 1.6.0.12 Beta é uma build anterior, não um upgrade. Nenhuma troca física foi feita.
- **Procedência:** modlist(1).txt física anexada e reconferida em 25/09/2026 + CurseForge/Modrinth oficiais JER 1.6.0.17 para Minecraft 1.21.1 + changelog exato commit c8341e5 + JEI físico 19.56.0.440. Decisão Manter previamente registrada foi preservada.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 25/09/2026 — JER 1.6.0.17/JAR físico reconfirmado; JEI físico reconciliado para 19.56.0.440. 1.6.0.17 permanece a build NeoForge 1.21.1 mais recente localizada; decisão Manter preservada.
- **Histórico da decisão:** 2026-09-06 — decisão fechada em Manter. A classificação Alpha é preservada como risco de maturidade, mas não há base para downgrade automático: 1.6.0.17 é a linha oficial 1.21.1 posterior e de adoção ampla.
- **Data da última decisão:** 2026-09-06

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #356: JAR `JustEnoughResources-NeoForge-1.21.1-1.6.0.17.jar`, mod id `jeresources`, runtime `1.6.0.17`, SHA-1 `e739d69b2de31ca8c0cb4691ab08e823f153bc44`.

<callout icon="⛏️" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `JustEnoughResources-NeoForge-1.21.1-1.6.0.17.jar`, mod id `jeresources`, versão `1.6.0.17`. A build oficial é **Alpha** para NeoForge 1.21.1. **Decisão ****`Manter`**** preservada.**
</callout>
## 1. Papel e authority
Just Enough Resources adiciona ao JEI informação sobre recursos, mobs, worldgen, loot e villagers. É uma camada de consulta; loot tables, ore placement, spawn e trades continuam pertencendo ao jogo/mod/datapack que os define.
## 2. Canal Alpha
O rótulo Alpha da 1.6.0.17 é mantido como risco de maturidade. A existência de 1.6.0.12 Beta não torna essa build anterior automaticamente superior; não houve downgrade físico nesta auditoria.
## 3. JEI
O pack usa JEI 19.56.0.440. JER depende do viewer para categories/entries. Mudanças na API JEI podem quebrar apresentação mesmo sem alterar loot/worldgen reais.
## 4. Mob drops
JER pode apresentar drops associados a mobs. Loot condicional por difficulty, looting, killer, biome, equipment ou outro mod pode não caber em um resumo estático. O servidor/loot table final é a authority.
## 5. Worldgen e recursos
Informação de geração de minérios/recursos é especialmente sensível a datapacks e worldgen customizado. O pack possui TFC e vários mods de geração; qualquer faixa/distribuição exibida deve ser validada contra o data state real antes de virar regra de quest/progressão.
## 6. Dungeon loot
Entries de loot são documentação probabilística. Uma chest/structure específica pode ter pool alterado por integrações, datapacks ou loot modifiers. Não garantir obtenção de item só porque JER o associa a uma fonte.
## 7. Villagers
O addon também pode apresentar informação ligada a villagers. Profession/trade assignment e preços continuam server-authoritative e podem ser modificados por outros mods.
## 8. Sobreposição com Advanced Loot Info
Há sobreposição parcial no domínio de loot. Isso não torna os mods idênticos: JER também cobre worldgen/resources/villagers. A decisão de manter ambos deve considerar qualidade da UI, cobertura e custo, não apenas nomes de categorias semelhantes.
## 9. Release 1.6.0.17
O changelog exato publicado para essa build adiciona **tradução portuguesa** (#518, commit `c8341e5`). Não atribuir fixes de worldgen/loot à 1.6.0.17 sem evidência adicional.
## 10. Client / server
A build é classificada client-side. JER lê/apresenta dados para o usuário; não deve alterar geração, drops ou trade settlement. Informação client-side divergente resolve a favor do servidor/data authoritative.
## 11. Lifecycle
Validar client boot, JEI plugin registration, world join, data/resource reload, language change e modpack update. Índices devem reconstruir sem duplicar entries ou reter recursos removidos.
## 12. Riscos técnicos
- ore/worldgen info stale sob datapacks/TFC;
- loot display não refletir loot modifiers condicionais;
- mob drop chance interpretada como garantia;
- villager/trade info stale;
- JEI API drift;
- Alpha regression;
- duplicate information com outro plugin;
- cache não invalidado após data change.
## 13. Matriz de testes obrigatória
- [ ] Cliente inicia com JER 1.6.0.17 + JEI 19.56.0.440.
- [ ] Mob drop category abre para amostra vanilla/modded.
- [ ] Worldgen/resource info não causa crash com stack TFC/worldgen.
- [ ] Loot entry é comparada a uma fonte authoritative real.
- [ ] Villager info abre sem registry/reference error.
- [ ] Tradução portuguesa carrega sem missing keys críticas.
- [ ] Data/resource reload não duplica entries.
- [ ] Advanced Loot Info coexistente não produz conflito funcional crítico.
- [ ] JER não altera drops/worldgen/trades.
- [ ] Upgrade futuro de JEI/JER é validado em conjunto.
## 14. Evidências e limites
- **Modlist física:** JAR/mod id/version e JEI atual.
- **CurseForge/Modrinth oficiais:** build 1.6.0.17, canal Alpha e escopo mobs/worldgen/villagers.
- **Changelog:** tradução portuguesa #518 em 1.6.0.17.
- **Limite:** nenhum arquivo de worldgen/loot efetivo do pack foi inferido a partir da UI; dados exibidos precisam de confirmação para decisões de progressão.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.

## Auditoria parcial — 20/11/2026 — novas distribuições 1.6.0

O snapshot #356 permanece `JustEnoughResources-NeoForge-1.21.1-1.6.0.17.jar`, Alpha. Projeto CurseForge 240630, categorias oficiais Addons + Biomes + Map and Information + Mobs + Ores and Resources confirmadas na [ficha oficial 1.6.0.66](https://www.curseforge.com/minecraft/mc-mods/just-enough-resources-jer/files/9452611). O [catálogo de 1.21.1](https://www.curseforge.com/minecraft/mc-mods/just-enough-resources-jer/files/all?page=1&pageSize=20&version=1.21.1) omite alphas; a consulta com alphas explícitos falhou. Não confundir a última release estável .57 com a última build de qualquer canal.

| Publicação confirmada | Delta declarado pelo autor |
|---|---|
| [1.6.0.54 — 23/10/2026, Beta](https://www.curseforge.com/minecraft/mc-mods/just-enough-resources-jer/files/9301525) | Ajuste de infraestrutura de publicação CurseForge/Forge no backport 1.21.1, commit cd328c2; não há mudança funcional NeoForge comprovada pela nota. |
| [1.6.0.57 — 24/10/2026, Release](https://www.curseforge.com/minecraft/mc-mods/just-enough-resources-jer/files/9311693) | Tratamento de slots vazios no RichTooltipCallback (3989533, #580), relevante à apresentação de loot e receitas/itens; não alterar gameplay. |
| [1.6.0.66 — 05/11/2026, Alpha](https://www.curseforge.com/minecraft/mc-mods/just-enough-resources-jer/files/9452611) | Localização das condições de nível de luz (9ed14ea, #606), conteúdo textual de consulta, não alteração de spawn. |

**Bloqueio:** faltam o inventário completo de builds Alpha posteriores a .17 e os deltas das intermediárias entre os arquivos identificados. Não deduzir que todos os números de build foram publicados nem que as notas acima sejam cumulativas. Testar tooltip com slots vazios, localização, JEI e worldgen customizado; nenhuma atualização do JAR ou QA. Manter na fila de auditoria.

## Auditoria incremental parcial — 21/11/2026 — mudanças do source oficial 1.21.1 não atribuídas a builds individuais

**Versão efetivamente instalada no snapshot:** `JustEnoughResources-NeoForge-1.21.1-1.6.0.17.jar` / `1.6.0.17`, posição física #356. O projeto oficial [JER no CurseForge, ID 240630](https://www.curseforge.com/minecraft/mc-mods/just-enough-resources-jer) mantém as categorias **Addons + Biomes + Map and Information + Mobs + Ores and Resources**. A versão [1.6.0.66 Alpha](https://www.curseforge.com/minecraft/mc-mods/just-enough-resources-jer/files/9452611) para NeoForge 1.21.1 permanece o último candidato Alpha identificado em 05/11/2026, diferente da última **Release** 1.6.0.57 de 24/10. Changelogs das builds 1.6.0.54, .57 e .66 já foram individualizados na seção anterior.

**Nova evidência primária do source, branch `1.21.1` do próprio mantenedor**, além da catalogação anterior:

- **23/10 — [commit `ffe1c2dd`](https://github.com/way2muchnoise/JustEnoughResources/commit/ffe1c2dd154f53110a49246b79f73b712fae0b3c):** a configuração padrão `enchantsBlacklist` do **NeoForge** adiciona o encantamento `reactive` de Ars Nouveau às opções já existentes `flimflam` e `soulBound`. Impacto em tooltips/listagem de encantamentos e em modpacks com Ars Nouveau. **Não altera regras reais de encantamento do servidor**; configurações já personalizadas podem não adotar automaticamente defaults novos. Presença do mod Ars no pack não foi inferida só deste commit.
- **24/10 — [commit `398953353`](https://github.com/way2muchnoise/JustEnoughResources/commit/398953353bfa835915b725617feb0194dc93182b):** as classes `DungeonTooltip`, `MobTooltip`, `PlantTooltip` e `WorldGenTooltip` do JER passam a retornar sem renderizar tooltip quando o `IRecipeSlotView` está vazio, evitando acesso a ingrediente inexistente. Esta mudança corresponde ao changelog público da **1.6.0.57** e explica o efeito prático em categorias de loot/worldgen.
- **05/11 — [commit `d3647cf56`](https://github.com/way2muchnoise/JustEnoughResources/commit/d3647cf56405d98cb82c237d424bfee2458eefb3):** a renderização de previews de mobs foi reestruturada, com **ajuste de escala ao espaço disponível e suporte a animações** nas previews da categoria de drops. As mudanças adicionam `EntityPreviewRendering`, `PreviewGeometryBounds` e access widener para estados estritamente visuais de entidades (ex.: Guardian/Wither). Relevância: apresentar corretamente mobs grandes e animados, sem tick de mundo ou efeito de combate. Requer teste com mobs de vários tamanhos, animação, reload e renderizadores de outros mods.
- **05/11 — [commit `9ed14ea70`](https://github.com/way2muchnoise/JustEnoughResources/commit/9ed14ea7046f965d3112227d636b146cb88ca578):** condições de nível de luz passam a ter chaves de localização `jer.lightLevel.below` e `jer.lightLevel.above`, inclusive **`pt_br`** (`Inferior a %s`, `Superior a %s`). Trata-se de texto do viewer, **não de mudança nas condições reais de spawn**, corroborando a nota da **1.6.0.66**.

**Limite de atribuição crucial:** os commits provam mudanças cumulativas **no código-fonte da branch 1.21.1**, mas **não** demonstram, isoladamente, o primeiro arquivo Alpha/Beta/Release contendo as mudanças `ffe1c2dd` e `d3647cf`, nem substituem o inventário integral de arquivos distribuídos. A lista filtrada disponível não permite fechar de maneira inequívoca a cadeia **1.6.0.17 → 1.6.0.66**, inclusive alphas não mostradas na navegação padrão. O fato de faltar um número não prova uma publicação; também não se atribui a versão 1.6.0.66 todo commit recente sem verificar o JAR.

**Status: PENDENTE.** Faltam enumeração completa de artefatos NeoForge 1.21.1 posteriores à instalada, changelog e correspondência source↔JAR por publicação. Não liberar o item para o Bloco de Notas, não alterar versão do dossier/JAR nem alegar QA. Fontes primárias: [GitHub upstream branch 1.21.1](https://github.com/way2muchnoise/JustEnoughResources/tree/1.21.1), commits detalhados acima, CurseForge JER (projeto 240630).

## Auditoria incremental parcial — 21/11/2026 — publicações intermediárias JER 1.6.0.60 e 1.6.0.63

A versão física **1.6.0.17** (JAR `JustEnoughResources-NeoForge-1.21.1-1.6.0.17.jar`, #356) continua inalterada. Após as verificações anteriores das publicações **1.6.0.54, 1.6.0.57 e 1.6.0.66**, foram localizadas também **duas distribuições Alpha intermediárias** de Minecraft 1.21.1 que a auditoria anterior não enumerava:

- **[1.6.0.60 — 30/10/2026, CurseForge file ID 9375674](https://www.curseforge.com/minecraft/mc-mods/just-enough-resources-jer/files/9375674):** distribuição nomeada **`JustEnoughResources-NeoForge-1.21.1-1.6.0.60.jar`**; o changelog do lançamento contém a alteração de **localização russa** (commit upstream [`6815e719`](https://github.com/way2muchnoise/JustEnoughResources/commit/6815e7192)), sem mecânica nova ou fix de worldgen/spawn comprovados. **Somente mudança linguística; não duplicar descrição de gameplay.**
- **[1.6.0.63 — 05/11/2026, publicação primária Modrinth 1.6.0.63](https://modrinth.com/mod/just-enough-resources-jer/version/1.6.0.63):** o changelog do próprio autor aponta **[`d3647cf`, issue #602](https://github.com/way2muchnoise/JustEnoughResources/commit/d3647cf56405d98cb82c237d424bfee2458eefb3)**: corrige dimensionamento e adiciona animações às previews de mobs. A mudança source já está tecnicamente detalhada na seção anterior, com `EntityPreviewRendering` e `PreviewGeometryBounds`. O artefato **`JustEnoughResources-NeoForge-1.21.1-1.6.0.63.jar`** é listado em índices que atribuem a publicação ao CurseForge, mas o **File ID e a página oficial individual para o binário NeoForge não foram recuperados nesta revisão**; a página Modrinth consultada identifica genericamente o loader **Forge**, não sendo por si só prova de equivalência binária NeoForge. **Não certificá-lo como JAR exato auditado até confirmar esse vínculo na fonte primária de distribuição.**
- **[1.6.0.66 — 05/11/2026, CurseForge 9452611](https://www.curseforge.com/minecraft/mc-mods/just-enough-resources-jer/files/9452611):** continua sendo o último Alpha NeoForge 1.21.1 já identificado; seu changelog registra **localização das condições de nível de luz** (commit [`9ed14ea`](https://github.com/way2muchnoise/JustEnoughResources/commit/9ed14ea7046f965d3112227d636b146cb88ca578)), com chaves inclusive para pt-BR. Sem alteração de lógica de spawn.

**Cadeia de distribuições identificadas, ainda com verificação pendente:** `1.6.0.17 → 1.6.0.54 → 1.6.0.57 → 1.6.0.60 → 1.6.0.63 → 1.6.0.66`. Os números omitidos não implicam publicação. A regra do projeto exige identificar e auditar **todas as distribuições**, não apenas commits com numeração. As duas fontes primárias diretas obtidas aqui são o arquivo CurseForge .60 e o changelog do autor no Modrinth .63; o vínculo CurseForge exato da build NeoForge .63 e a ausência de outras publicações compatíveis permanecem **sem comprovação integral**. O source da branch `1.21.1` distingue os commits `6815e719`, `d3647cf` e `9ed14ea`.

**Status documental: PENDENTE (fail-closed).** Acrescentada a atribuição das novas publicações sem atualizar JAR, basename, pasta ou versão física, nem declarar testes. Não remover JER do Bloco de Notas ainda.

