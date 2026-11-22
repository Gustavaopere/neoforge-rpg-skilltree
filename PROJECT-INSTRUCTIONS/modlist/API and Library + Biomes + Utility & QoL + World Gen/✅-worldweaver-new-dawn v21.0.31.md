# WorldWeaver: New Dawn — 21.0.31 (versão instalada declarada em 22/11/2026)

> **Estado vigente — declaração do usuário de 22/11/2026:** `21.0.25` → **`21.0.31`**, NeoForge 1.21.1; JAR oficialmente publicado `worldweaver-21.0.31.jar` ([CurseForge](https://www.curseforge.com/minecraft/mc-mods/worldweaver-neoforge/files/9490293)). Arquivo real local, SHA e metadata não relidos. Posição física histórica #572.

> **Auditoria:** Auditoria anterior individualiza 21.0.26 (biomas BetterEnd ausentes na criação), 21.0.27 (serialização/presets e receitas dinâmicas), 21.0.28 (encantamentos em item data components), 21.0.29 (integração Nether Descent), 21.0.30 (serialização de estruturas sensível a locale) e 21.0.31 (Biolith). Todas as seis publicações foram rastreadas no Modrinth oficial do autor e no CurseForge da continuação New Dawn; não atribuir essas mudanças à linha original Fabric.

> **Dependências:** Biblioteca BetterX/New Dawn com BetterEnd, BetterNether, BCLib e WunderLib; Biolith e Nether Descent são integrações condicionais, não obrigatórias por mera existência da biblioteca.

> **Riscos:** Worldgen, registry reload e compatibilidade de End/Nether, Biolith e Nether Descent. Backup, reprodução de seeds com e sem BetterNether, locale turco, chunks existentes/novos e QA client/server não executados.

> **Contexto histórico:** registros abaixo com “instalado”, “atual” ou “não instalado” anteriores a 22/11/2026 descrevem o snapshot de setembro ou auditorias antigas. Os campos vigentes e esta declaração prevalecem. Não foram realizados testes de runtime, save ou multiplayer.

---


> **Snapshot físico histórico — 28/09/2026.** Ordem física **#572**: JAR `worldweaver-21.0.25.jar`, mod id `wover`, runtime `21.0.25`, SHA-1 `b274b0bb221739d52a0f52bc969bd64d691f8bbc`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** WorldWeaver: New Dawn
- **Arquivo JAR instalado (declarado):** `worldweaver-21.0.31.jar` ([CurseForge](https://www.curseforge.com/minecraft/mc-mods/worldweaver-neoforge/files/9490293)), sem leitura do arquivo local.
- **Versão instalada declarada:** `21.0.31` (anterior `21.0.25`, NeoForge/Minecraft 1.21.1); metadata novo não lido.
- **Estado no pack:** `21.0.31` declarada instalada pelo usuário, como autoridade do inventário documental; JAR local e QA não verificados.
- **Estado da pesquisa:** alvo/JAR e cadeia histórica conferidos conforme seção vigente; lacunas específicas indicadas sem certificação indevida.
- **Decisão:** Manter
- **Categoria:** Biblioteca, Worldgen
- **Função:** Biblioteca de worldgen do stack BetterX/New Dawn, responsável por infraestrutura e compatibilidade de distribuição de biomas/presets consumida por BetterEnd/BetterNether.
- **Dependências:** Biblioteca BetterX/New Dawn com BetterEnd, BetterNether, BCLib e WunderLib; Biolith e Nether Descent são integrações condicionais, não obrigatórias por mera existência da biblioteca.
- **Sobreposição:** Infraestrutura complementar a BCLib/WunderLib/TerraBlender; não é substituto de BetterEnd/BetterNether nem redundância automática por categoria.
- **Compatibilidade/Riscos:** Worldgen, registry reload e compatibilidade de End/Nether, Biolith e Nether Descent. Backup, reprodução de seeds com e sem BetterNether, locale turco, chunks existentes/novos e QA client/server não executados.
- **Observações:** Mod id `wover`, runtime 21.0.25. Decision `Manter` preservada. Worldgen infrastructure não gera Mastery; quests devem usar identity final do biome/provider.
- **Procedência:** modlist.txt física atual consultada em 13/09/2026 + CurseForge/Modrinth oficiais WorldWeaver: New Dawn 21.0.25 revalidados em 13/09/2026 + stack BetterEnd 21.0.34, BetterNether 21.0.26, BCLib 21.0.26 e WunderLib 21.0.10. A 21.0.25 continua a latest release NeoForge 1.21.1 de 07/08/2026. Decisão Manter preservada; nenhum runtime worldgen QA executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/worldweaver-neoforge/files/9490293
- **Atualização/Status:** 22/11/2026 — `21.0.25` → `21.0.31` por declaração de instalação. Auditoria anterior individualiza 21.0.26 (biomas BetterEnd ausentes na criação), 21.0.27 (serialização/presets e receitas dinâmicas), 21.0.28 (encantamentos em item data components), 21.0.29 (integração Nether Descent), 21.0.30 (serialização de estruturas sensível a locale) e 21.0.31 (Biolith). Todas as seis publicações foram rastreadas no Modrinth oficial do autor e no CurseForge da continuação New Dawn; não atribuir essas mudanças à linha original Fabric.
- **Histórico da decisão:** Durante troubleshooting em 22/08/2026, 21.0.25 e 21.0.24 foram testados enquanto uma camada externa de worldgen ainda participava da falha. Após remover essa camada, BetterX/New Dawn voltou a funcionar e WorldWeaver 21.0.25 foi restaurado. Decisão vigente: MANTER; não continuar downgrade.
- **Data da última decisão:** 2026-08-22

# Dossiê operacional — padrão Alex's Mobs

> 🌍 **ESCOPO CANÔNICO.** Runtime físico: `worldweaver-21.0.25.jar`, mod id `wover`, versão `21.0.25`. WorldWeaver: New Dawn é uma **biblioteca de worldgen do stack BetterX/New Dawn**, usada para biome distribution e infraestrutura compartilhada. A decisão vigente é **Manter**; o troubleshooting histórico não demonstrou defeito isolado do WorldWeaver.
## 1. Identidade e maturidade
A build 21.0.25 é Release NeoForge 1.21.1 publicada em 07/08/2026 e permanece a release 1.21.1 atual do projeto.
O projeto se descreve como continuação mantida do WorldWeaver original, com port NeoForge e correções/manutenção do sistema de world generation.
## 2. Stack BetterX atual
A modlist física confirma:
- BetterEnd 21.0.34;
- BetterNether 21.0.26;
- BCLib 21.0.26;
- WorldWeaver 21.0.25;
- WunderLib 21.0.10.
Essas libraries/mods possuem papéis complementares. A presença de BCLib/WunderLib/TerraBlender não torna WorldWeaver redundante por nome ou categoria.
## 3. Função e authority
WorldWeaver participa da distribuição/compatibilidade de biomas e outras primitives de worldgen consumidas pelo BetterX New Dawn.
Authority:
- WorldWeaver: regras/infraestrutura que expõe aos consumers;
- BetterEnd/BetterNether: biomas, features e conteúdo próprio;
- Minecraft/NeoForge/TerraBlender e demais providers: respectivas pipelines/registries.
Não recriar placement/distribution do consumer em mod próprio quando a library já fornece o contrato necessário.
## 4. Mudanças confirmadas da 21.0.25
O changelog oficial da build exata registra:
- fix para biomas TerraBlender e Nether Descent não gerarem com preset BetterX quando BetterNether está ausente;
- compatibilidade de biomas respeitando configuração de biomas desabilitados;
- preservação da distribuição BetterNether existente quando seus biomas estão habilitados;
- prevenção de Warped Forest e Basalt Deltas serem completamente deslocados por biomas BetterNether no preset Default.
Esses pontos tornam 21.0.25 diretamente relevante à composição de Nether/world presets do pack.
## 5. Troubleshooting histórico preservado
Durante troubleshooting anterior, 21.0.25/21.0.24 foram testados enquanto outra camada de worldgen ainda participava da falha `ModelProviderMixin`. Após remover a camada problemática, BetterX/New Dawn voltou a funcionar e 21.0.25 foi restaurado.
Conclusão canônica: **não atribuir aquela falha histórica ao WorldWeaver isoladamente** e não continuar downgrade sem nova evidência.
## 6. Client/server e mixin surface
O JAR atual possui ampla superfície de mixins common/client envolvendo biome, item, core, surface, datagen, POI, block, generator, preset, recipe, feature, tag, UI e structure.
Isso aumenta a importância de dedicated-server/client boot e data-generation/resource reload smoke. A presença de mixins não prova conflito; conflitos devem ser reproduzidos e atribuídos.
## 7. Worldgen lifecycle
Validar especialmente:
- criação de mundo com presets usados pelo pack;
- chunks novos;
- biome config enable/disable;
- datapack reload quando suportado;
- save/restart;
- atualização de versão em mundo existente;
- coexistência BetterEnd/BetterNether;
- ausência de displacement anormal de biomas vanilla.
Worldgen existente em chunks já gerados não deve ser reinterpretado como se a nova distribuição fosse retroativa.
## 8. Boundary para quests/perks
WorldWeaver é infraestrutura e não evento de progressão. Não conceder Mastery por biome placement, chunk generation ou library hooks.
Se uma quest depender de um biome BetterX, usar identity do biome/provider final, não WorldWeaver como proxy causal.
## 9. Riscos
1. **Biome distribution drift:** updates mudam compatibilidade/placement.
2. **Preset interaction:** BetterX/Default/TerraBlender paths divergem.
3. **Consumer coupling:** BetterEnd/BetterNether esperam contratos específicos.
4. **Mixin interaction:** outro worldgen mod altera a mesma superfície.
5. **False blame from historic troubleshooting:** correlação antiga tratada como causa isolada sem reprodução.
6. **World upgrade:** novas regras só afetam chunks futuros de forma normal.
## 10. Matriz de testes
- [ ] Dedicated server/client boot com WorldWeaver 21.0.25 + BetterX atual.
- [ ] Criar mundo com preset efetivamente usado no pack.
- [ ] BetterEnd/BetterNether biomes geram sem registry/data errors.
- [ ] Config de biome disabled é respeitada.
- [ ] Warped Forest/Basalt Deltas não são indevidamente eliminados.
- [ ] TerraBlender/Nether Descent paths relevantes geram corretamente.
- [ ] Save/restart e chunk generation incremental permanecem determinísticos dentro do esperado.
- [ ] Upgrade em cópia de mundo existente não corrompe chunks antigos.
- [ ] Regressão futura é reproduzida antes de culpar/downgradear WorldWeaver isoladamente.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 11. Evidências
- **Modlist física 08/09/2026:** WorldWeaver 21.0.25, BetterEnd 21.0.34, BetterNether 21.0.26, BCLib 21.0.26 e WunderLib 21.0.10.
- CurseForge oficial WorldWeaver: continuation/port de library de worldgen; Release 21.0.25 e changelog de biome compatibility/distribution.
- Histórico do projeto/Notion: troubleshooting anterior e decisão `Manter` após restauração do stack funcional.
## 12. Limitação
Não foram decompilados os algoritmos internos de distribuição nem comparados todos os providers worldgen do pack nesta etapa. Compatibilidade concreta deve ser validada em seed/preset real.
## 13. Revalidação física — 11/09/2026
A modlist física continua contendo exatamente `worldweaver-21.0.25.jar`, mod id `wover`, versão `21.0.25`. A listagem oficial mantém 21.0.25 como a release NeoForge aplicável à linha Minecraft 1.21.1, publicada em 07/08/2026.
O changelog exato continua cobrindo os fixes de TerraBlender/Nether Descent no preset BetterX, respeito à configuração de biomas desabilitados, preservação da distribuição BetterNether e proteção contra deslocamento completo de Warped Forest/Basalt Deltas. A decisão **Manter** e o estado **Integrado ao Github** foram preservados. Nenhum world creation, biome distribution, preset, chunkgen, reload ou dedicated-server test foi executado nesta recatalogação.
## 14. Revalidação — 13/09/2026
WorldWeaver 21.0.25 permanece atual para NeoForge 1.21.1. Decisão Manter preservada. Nenhum teste runtime foi executado.

## 15. Atualização upstream 21.0.26 — 10/10/2026

**Última versão fisicamente documentada:** worldweaver-21.0.25.jar, mod id wover. **Última release CurseForge NeoForge Minecraft 1.21.1:** worldweaver-21.0.26.jar, publicada em 04/10/2026. Não confundir com 21.11.x ou 26.x de Minecraft diferente.

**Limite:** não foi encontrado changelog técnico granular da 21.0.26 nas fontes acessíveis. A simples publicação da atualização não comprova mudança de biome distribution, bugfixes ou API específicos.

WorldWeaver fornece infraestrutura para BetterEnd/BetterNether/BCLib/WunderLib. A promoção deve ser coordenada com versões/requisitos dessas bibliotecas e addons; a análise do BetterEnd 21.0.36 na rodada #38 é upstream, não uma atualização física já executada.

**Gate:** worldgen BetterX/TerraBlender e biomes disabled em seed controlada; chunk boundaries de Nether/End, mistura de biomas vanilla/BetterX, startup dedicado, registry/mixin resolution, save/restart e novos chunks após atualização. Monitorar divergências de bioma e preservar configs. Não declarar testes concluídos.

**Fonte:** https://www.curseforge.com/minecraft/mc-mods/worldweaver-neoforge/files/all

**Estado:** candidato upstream 21.0.26; runtime físico ainda 21.0.25.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** WorldWeaver: New Dawn
- **Último candidato 1.21.1 / NeoForge localizado:** **21.0.29 (NeoForge 1.21.1, 20/10)**
- **Estado:** **CANDIDATO COORDENADO NEW DAWN**
- **Gate de promoção:** Testar junto de BCLib/BetterNether/BetterEnd/WunderLib, worldgen/registries, novos chunks, mundos existentes, datapack reload e restart.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/worldweaver-neoforge

## Lote 21–30 — publicações intermediárias e bloqueio do changelog individual (22/10/2026)

**Instalado historicamente:** WorldWeaver: New Dawn **21.0.25**, NeoForge Minecraft 1.21.1; o port correto é o projeto CurseForge **worldweaver-neoforge**, não o WorldWeaver original de Fabric. **Última publicação válida:** **WorldWeaver-21.0.29.jar**, NeoForge 1.21.1, 20/10/2026.

| Versão | Evidência disponível |
|---|---|
| 21.0.26 | Publicação NeoForge 1.21.1 confirmada na revisão anterior; alteração granular não documentada. |
| 21.0.27 | Parte da sequência NeoForge antes de 21.0.28/29; **changelog individual não pinado**. |
| 21.0.28 | Release NeoForge 1.21.1 publicada 19/10; comportamento próprio específico ainda não isolado. |
| 21.0.29 | Release NeoForge 1.21.1 publicada 20/10; nenhuma lista detalhada de commits associados a **este JAR** foi confirmada na pesquisa. |

**Escopo funcional declarado pelo projeto, sem atribuição arbitrária de versão:** melhorias na compatibilidade entre **TerraBlender e biomas não-Wover** para evitar escala/fragmentação indevida, preservar placement de biomas externos e correções de integração de categorias The End com BetterEnd. A descrição do projeto é cumulativa; ela **não informa qual 21.0.x introduziu cada mudança**. Não transformar isso em changelog específico da 21.0.29.

**QA coordenado:** BCLib: New Dawn, BetterEnd, BetterNether, WunderLib, TerraBlender e worlds com chunks existentes e novos, preservação de biome placement externo, registries, datafixers, End/Nether, datapack reload e seed repetível. **BLOQUEADO para afirmar auditoria completa release-by-release** até localizar changelogs/tags ou diff source de 21.0.26, 21.0.27, 21.0.28 e 21.0.29; nenhuma alteração física foi feita.

Fonte oficial: https://www.curseforge.com/minecraft/mc-mods/worldweaver-neoforge/files/all ; https://www.curseforge.com/minecraft/mc-mods/worldweaver-neoforge .

## Complemento incremental 17/11/2026 — WorldWeaver 21.0.26–21.0.31

**Escopo:** projeto CurseForge correto [WorldWeaver: New Dawn (NeoForge)](https://www.curseforge.com/minecraft/mc-mods/worldweaver-neoforge), distinto do WorldWeaver original Fabric. Categorias oficiais **API and Library + Biomes + Utility & QoL + World Gen**, já refletidas na pasta. O último JAR físico histórico (#572) permanece **`worldweaver-21.0.25.jar`**, `wover` `21.0.25`; nenhum inventário físico novo nem runtime foi obtido. **Release mais recente confirmada para 1.21.1 NeoForge nesta pesquisa: 21.0.31 (07/11/2026)**. Publicações de Minecraft 1.21.11/26.x não são aplicáveis.

| Release | Data registrada e evidência oficial | Impacto funcional verificável / lacuna |
|---|---|---|
| **21.0.26** | 04/10/2026 conforme levantamento documental anterior; página exata do arquivo não recuperada nesta revisão. | **PENDENTE:** changelog individual não confirmado; não atribuir mudanças cumulativas. |
| **21.0.27** | **14/10/2026**, [CurseForge file ID 9172424](https://www.curseforge.com/minecraft/mc-mods/worldweaver-neoforge/files/9172424). | **RELEVANTE:** corrige registro incompleto de biomas de End quando BetterNether está ausente, melhora compatibilidade de fontes/estruturas de End externas e recarrega biome sources após tags aplicadas pelo loader. Supre especificamente lacuna antes registrada na auditoria de 22/10. |
| **21.0.28** | **19/10/2026**, publicação NeoForge 1.21.1 confirmada na [listagem oficial](https://www.curseforge.com/minecraft/mc-mods/worldweaver-neoforge/files/all). | **PENDENTE:** changelog granular associado ao arquivo não obtido; não inventar fix. |
| **21.0.29** | **20/10/2026**, publicação NeoForge 1.21.1 confirmada na [listagem oficial](https://www.curseforge.com/minecraft/mc-mods/worldweaver-neoforge/files/all). | **PENDENTE:** changelog granular não obtido; não atribuir automaticamente as mudanças anunciadas no projeto. |
| **21.0.30** | **24/10/2026**, [CurseForge file ID 9306568](https://www.curseforge.com/minecraft/mc-mods/worldweaver-neoforge/files/9306568). | **RELEVANTE:** corrige placement e serialização de estruturas sensíveis ao locale do sistema (incluindo turco); testar geração em servidor com locales distintos. |
| **21.0.31** | **07/11/2026**, [CurseForge file ID 9490293](https://www.curseforge.com/minecraft/mc-mods/worldweaver-neoforge/files/9490293). | **RELEVANTE:** acrescenta compatibilidade com **Biolith**; não assumir que Biolith esteja instalado ou que substitua integração TerraBlender. |

**Gate de compatibilidade coordenado ainda pendente:** identificar contratos de versão e changelog por build do stack BetterEnd: New Dawn, BetterNether: New Dawn, BCLib: New Dawn, WunderLib e TerraBlender; verificar instâncias com e sem BetterNether, End biome sources externos, Biolith caso presente, seed/presets reais, condições sem Biolith, locale inglês/turco, registries/tags, worldgen em novos chunks e em save existente, restart, reload, dedicado e compat de NeoForge. Estas verificações **não foram executadas**.

**Estado:** **AUDITORIA DOCUMENTAL PENDENTE** por falta de changelogs individuais 21.0.26, 21.0.28 e 21.0.29 (e dados físicos novos). A documentação foi complementada **sem** certificar falsamente a cadeia inteira, sem renomear/mover o dossier e sem atualizar JARs. O dossier já tinha ✅ por auditoria histórica da versão instalada; isso não equivale a homologação de 21.0.31.

## Fechamento do histórico 21.0.26–21.0.31 — 18/11/2026

A [API de versões oficial do Modrinth para New Dawn, filtrada em NeoForge/1.21.1](https://api.modrinth.com/v2/project/R8uGDQpB/version?loaders=%5B%22neoforge%22%5D&game_versions=%5B%221.21.1%22%5D) recuperou 19 registros desse canal, com exatamente seis posteriores à 21.0.25. O projeto CurseForge 1422284 ([catálogo](https://www.curseforge.com/minecraft/mc-mods/worldweaver-neoforge/files/all)) confirma 21.0.31 e categorias API and Library + Biomes + Utility & QoL + World Gen. As três lacunas anteriores foram resolvidas. Datas abaixo são uploads UTC; não extrapolar notas de Fabric ou Minecraft 26.x.

| Release | Data | Changelog primário e delta |
|---|---|---|
| [21.0.26](https://modrinth.com/mod/worldweaver-neoforge/version/x1laXrzF) | 04/10/2026 | Corrige biomas de BetterEnd ausentes na criação do mundo; BetterNether mascarava o defeito, e /reload era contorno temporário. |
| [21.0.27](https://modrinth.com/mod/worldweaver-neoforge/version/lL7lvfic) | 14/10/2026 | Modrinth detalha serialização de presets com contexto de registries ativo, correção da ordem de camadas em presets flat e advancements de desbloqueio para receitas registradas em runtime. O registro anterior do CurseForge cita registro de biomas End e reload de biome sources; preservar como evidência histórica dessa plataforma, sem afirmar equivalência byte a byte entre artefatos. |
| [21.0.28](https://modrinth.com/mod/worldweaver-neoforge/version/YWSPRS64) | 19/10/2026 | Aplicação de encantamentos passa a escrever diretamente em item data components, incluindo encantamentos armazenados em livros. |
| [21.0.29](https://modrinth.com/mod/worldweaver-neoforge/version/1aLkwcmX) | 20/10/2026 | Integração embutida com Nether Descent: preserva distribuição climática/vertical e surface rules, respeita configurações de worldgen e dispensa datapack standalone de compatibilidade WorldWeaver/Nether Descent. Não remover datapacks sem verificar a instalação efetiva. |
| [21.0.30](https://modrinth.com/mod/worldweaver-neoforge/version/21KphEsG) | 24/10/2026 | Corrige serialização de placement e structures dependente de locale. |
| [21.0.31](https://modrinth.com/mod/worldweaver-neoforge/version/KxUUeX9u) | 07/11/2026 | Adiciona compatibilidade com Biolith. |

**Impacto:** testar End sem BetterNether, presets customizados/flat, livros encantados e data components, desbloqueio de recipes dinâmicas, Nether Descent quando presente e datapack de compat, locale, Biolith quando presente, BetterX/BCLib/TerraBlender, novos chunks, saves e reinicialização. Decisão Manter e versão física histórica #572 `worldweaver-21.0.25.jar` preservadas. **Auditoria documental concluída até 21.0.31;** instalação e QA não realizados. O arquivo e a pasta permanecem inalterados.


## Sincronização documental de 22/11/2026 — WorldWeaver: New Dawn 21.0.31

**Estado atual declarado:** `21.0.25` → `21.0.31`; arquivo oficial `worldweaver-21.0.31.jar` (NeoForge 1.21.1), https://www.curseforge.com/minecraft/mc-mods/worldweaver-neoforge/files/9490293. Posição original #572. Nenhum novo JAR, hash ou metadata do PC foi consultado.

**Versões auditadas / lacunas:** Auditoria anterior individualiza 21.0.26 (biomas BetterEnd ausentes na criação), 21.0.27 (serialização/presets e receitas dinâmicas), 21.0.28 (encantamentos em item data components), 21.0.29 (integração Nether Descent), 21.0.30 (serialização de estruturas sensível a locale) e 21.0.31 (Biolith). Todas as seis publicações foram rastreadas no Modrinth oficial do autor e no CurseForge da continuação New Dawn; não atribuir essas mudanças à linha original Fabric.

**Dependências aguardando sincronização e testes:** Biblioteca BetterX/New Dawn com BetterEnd, BetterNether, BCLib e WunderLib; Biolith e Nether Descent são integrações condicionais, não obrigatórias por mera existência da biblioteca. Divergências em outros dossiers ainda não atualizados são pendências documentais, não bloqueio automático nem prova de compatibilidade.

**Riscos e limites:** Worldgen, registry reload e compatibilidade de End/Nether, Biolith e Nether Descent. Backup, reprodução de seeds com e sem BetterNether, locale turco, chunks existentes/novos e QA client/server não executados.
