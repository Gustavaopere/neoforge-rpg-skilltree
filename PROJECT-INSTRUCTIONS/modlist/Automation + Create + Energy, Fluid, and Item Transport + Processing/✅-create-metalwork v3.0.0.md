# Create: Metalwork — versão 3.0.0 (atualização declarada em 22/11/2026)

> **Estado vigente do índice — 22/11/2026:** atualização **declarada pelo usuário** para `3.0.0` (`createmetalwork-neoforge-1.21.1-3.0.0.jar`). Evidência de versão/filename: [CurseForge, file 8995820](https://www.curseforge.com/minecraft/mc-mods/create-metalwork/files/8995820). Posição física original preservada. O JAR atual não foi inspecionado localmente.

> **Dependências e segurança:** 3.0.0: nova implementação documenta registries de metais condicionais, compatibilidade de providers, arquivo createmetalwork-common.toml, slots de fluido expandidos no Basin, processamento metalúrgico e receitas com output condicional. A release oficial diz 'Initial Release'; o diferencial binário completo entre 2.0.0 e 3.0.0 não foi obtido, portanto não marcar a auditoria técnica como comparação binária exaustiva. Este registro não significa que a combinação inteira do pack tenha sido testada. **Seções antigas com 'instalado 1.x' referem-se à baseline histórica anterior ao pedido atual.**


> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#195**: JAR `createmetalwork-2.0.0.jar`, mod id `createmetalwork`, runtime `2.0.0`, SHA-1 `e53cb0ce472f6abc299c7545df5259bdb136de0`.

## Propriedades do registro

- **Mod:** Create: Metalwork
- **Arquivo JAR instalado (declarado pelo usuário):** `createmetalwork-neoforge-1.21.1-3.0.0.jar` — arquivo de distribuição oficial CurseForge 8995820.
- **Versão instalada declarada (Minecraft 1.21.1 / NeoForge):** `3.0.0`; fonte da declaração: pedido de atualização enviado pelo usuário em 22/11/2026.
- **Versão upstream mais recente documentada:** `3.0.0` — **28/09/2026, Release NeoForge 1.21.1, NÃO INSTALADA**. Fonte exata 3.0.0 comparada com as notas públicas da linha 2.0; diff binário 2.0.0→3.0.0 não executado.
- **Conteúdo novo verificado na documentação 3.0.0:** registros condicionais, ampla matriz de integração, `expanded_basin_fluids`, rotas/ratios de processamento; não atribuir automaticamente todos esses itens como *novidades exclusivas* frente à 2.0.0.
- **Categoria:** Metalurgia, Automação, Tecnologia
- **Função:** Adiciona novos fluidos/crushed ores e rotas metalúrgicas do Create voltadas a aumentar rendimento de ingots por minério.
- **Dependências:** Create obrigatório. Pack físico usa NeoForge 21.1.248 + Create 6.0.10; Almost Unified 1.4.2 está presente e continua relevante para variant unification. Big Cannons 5.11.7, Dreams n' Desires 2.3a-BETA e Northstar 0.6.4 permanecem superfícies concretas de compatibilidade.
- **Estado no pack:** atualização para `3.0.0` **declarada pelo usuário e sincronizada neste inventário documental**. O JAR do computador não foi lido diretamente.
- **Estado da pesquisa:** publicações e alterações upstream auditadas; ausência de smoke test no pack não impede o versionamento documental. Não atestar compatibilidade global antes da atualização das dependências de outros lotes.
- **Compatibilidade/Riscos:** Riscos: receitas paralelas com rendimentos incompatíveis; conversion loops; molten fluid amounts semanticamente diferentes; Almost Unified escolher variante inadequada; drift de CBC/DnD/Northstar; KubeJS stale recipes; regressão do crash Lithium se o provider for adicionado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-metalwork
- **Procedência:** o snapshot físico histórico permanece fonte da posição e da versão **anterior**. O pedido explícito de 22/11/2026 é a fonte da versão atual declarada; filename novo obtido do CurseForge 8995820. Não houve leitura direta do computador, hash do JAR novo ou testes de runtime.
- **Observações (estado atual declarado):** `createmetalwork-neoforge-1.21.1-3.0.0.jar`, versão `3.0.0`; arquivo oficial 8995820. Os identificadores de JAR/SHA do snapshot anterior permanecem como histórico, não evidência de que a versão antiga ainda está instalada.
- **Atualização/Status:** 22/11/2026 — `2.0.0` → `3.0.0` (declaração do usuário); versão, JAR oficial, filename do dossier e índice mestre sincronizados. 3.0.0: nova implementação documenta registries de metais condicionais, compatibilidade de providers, arquivo createmetalwork-common.toml, slots de fluido expandidos no Basin, processamento metalúrgico e receitas com output condicional. A release oficial diz 'Initial Release'; o diferencial binário completo entre 2.0.0 e 3.0.0 não foi obtido, portanto não marcar a auditoria técnica como comparação binária exaustiva.
- **Decisão:** Sem decisão
- **Histórico da decisão:** Histórico não operacional: uma auditoria anterior centrada em TFC sugeriu remoção por criar rota metálica concorrente. TFC não está na modlist atual, portanto esse fundamento não é aplicável à decisão presente. O mod permanece instalado e sem decisão final.
- **Sobreposição:** Forte overlap econômico/material com Create Metallurgy 1.0.3 e Productive Metalworks 1.15.1. Não há duplicata automática: comparar crushed ores, molten forms, recipe ratios e máquinas; Almost Unified pode unificar variantes sem resolver rendimento.
- **Data da última decisão:** 2026-08-22

# Dossiê operacional — padrão Alex's Mobs
> ⚙️ **Identidade física confirmada:** `createmetalwork-2.0.0.jar`, mod id `createmetalwork`, runtime `2.0.0`, NeoForge 1.21.1. A release exata é Client & Server e registra como delta específico a correção de crash com Lithium. O repositório público disponível está numa linha antiga 1.20.1 e não é tratado como source binário da 2.0.0.
## 1. Papel e authority
Create: Metalwork adiciona novas rotas de **ore processing**, crushed ores e molten materials com foco em aumentar o rendimento de ingots por minério usando o ecossistema Create. O addon owns seus itens/fluidos/recipes; Create continua authority das máquinas e tipos de processamento base reutilizados.
## 2. Objetivo de rendimento
A documentação oficial declara explicitamente que as rotas do mod produzem rendimentos superiores à fundição vanilla. Isso afeta economia/progressão do pack e precisa ser comparado com outros processadores instalados por input, output e energia/tempo, não apenas por nome de material.
## 3. Crushed ores
O addon adiciona **novos crushed ores** para materiais suportados. Registry/tags reais da build 2.0.0 são authority da lista exata; esta ficha não retroprojeta IDs da antiga branch 1.20.1.
## 4. Molten ores/metals
Também adiciona versões **molten** de ores/metais para permitir cadeias fluid-based. Tipo e amount devem permanecer consistentes entre melting, transporte e solidificação; fluidos homônimos de outro mod só são equivalentes quando tags/recipes os unificam explicitamente.
## 5. Rotas Create de processamento
Metalwork foi projetado para ampliar a transformação de minério em ingots dentro da automação Create. Recipe Manager é authority de cada etapa, quantidade e chance. Viewer não deve ser usado como prova de rendimento se datapacks/KubeJS alterarem recipes.
## 6. Compatibilidade declarada com outros mods
A documentação pública histórica lista integrações com vários providers, incluindo Create, Create: Big Cannons, Create: Dreams & Desires e Create: Northstar. No pack atual, Big Cannons 5.11.7, Dreams n' Desires 2.3a-BETA e Northstar 0.6.4 estão presentes; cada material compartilhado precisa ser testado pela recipe/tag real da build 2.0.0.
## 7. Almost Unified
O próprio projeto recomenda **Almost Unified** para reduzir sobreposição de materiais. O pack contém Almost Unified 1.4.2. A unificação pode escolher uma variante canônica de item/fluid, mas não deve alterar silenciosamente rendimento nem criar ida-e-volta lucrativa entre recipes equivalentes.
## 8. Create Metallurgy
Create Metallurgy 1.0.3 está instalado e possui machinery de melting/casting própria. Há overlap material e econômico, porém não equivalência arquitetural: Metalwork amplia rotas/material forms; Metallurgy adiciona Crucible/Faucet e cadeia própria.
## 9. Productive Metalworks
Productive Metalworks 1.15.1 também está presente. A coexistência cria risco de três providers oferecendo molten materials/crushed intermediates ou routes de alto rendimento. Tags, recipes e conversion ratios precisam ser auditados como sistema único.
## 10. Create Big Cannons
Big Cannons é uma integração potencial concreta porque está fisicamente instalado. Se Metalwork oferecer forms/recipes para materiais CBC, o provider CBC continua authority dos seus itens; Metalwork apenas fornece processamento. Não inferir cobertura de todo material CBC sem recipe/tag observada.
## 11. Dreams & Desires e Northstar
Ambos estão instalados e aparecem na superfície histórica de compatibilidade do projeto. A presença atual justifica smoke-test, mas não autoriza afirmar quais ores/metals da build 2.0.0 estão integrados sem inspeção runtime dos recipes.
## 12. Lithium crash — delta 2.0.0
O changelog exato da 2.0.0 registra **fix de crash com Lithium (#41)**. Lithium não foi encontrado como JAR top-level no pack atual; o fix permanece parte do artefato instalado, mas não é uma integração ativa a ser validada neste pack salvo mudança futura da modlist.
## 13. Datapacks e KubeJS
Em um pack com KubeJS, recipes de ore processing podem ser substituídas ou unificadas. Alterações devem manter conservation e impedir recipes paralelas antigas de sobreviverem junto da rota nova. `/reload` precisa reconstruir Recipe Manager sem stale viewer/cache.
## 14. Client/server
Recipe matching, item/fluid mutation e processing são server-authoritative. JEI, tooltips e fluid rendering são client-facing. Cliente não pode escolher output/rendimento diferente do recipe carregado pelo servidor.
## 15. Lifecycle
Testar recipe reload, chunk unload/restart durante processing, transfer de molten fluids, conversion por tags unificadas e mudança de datapack. Interromper uma operação não pode reaplicar input ou output na retomada.
## 16. Sobreposição e economia
O risco central do mod neste pack não é incompatibilidade binária, mas **multiplicação de rendimento**. Uma sequência que converte ore→crushed→molten→ingot por providers diferentes pode gerar ganho exponencial se ratios forem inconsistentes.
## 17. Riscos
1. Duas recipes equivalentes oferecem rendimentos muito diferentes.
2. Almost Unified escolhe variante canônica incompatível com recipe downstream.
3. Molten fluid de outro provider é aceito com amount semântico diferente.
4. Conversion loop cria ingots infinitos.
5. Chunk/restart duplica output de processing.
6. DnD/Northstar/CBC integration drift deixa recipes quebradas.
7. KubeJS remove uma rota, mas viewer/cache mantém apresentação antiga.
8. Lithium crash reaparece se o provider for adicionado futuramente.
9. Source antigo 1.20.1 é indevidamente tratado como implementação 2.0.0.
## 18. Matriz de testes
- [ ] Dedicated server inicia com Metalwork 2.0.0 + Create 6.0.10.
- [ ] Crushed ores da build aparecem com recipes válidas.
- [ ] Molten materials conservam tipo/amount ao transferir e solidificar.
- [ ] Rendimento por minério corresponde ao Recipe Manager atual.
- [ ] Almost Unified 1.4.2 não cria conversion loop.
- [ ] Coexistência com Metallurgy 1.0.3 e Productive Metalworks 1.15.1 não multiplica material indevidamente.
- [ ] CBC/DnD/Northstar paths presentes resolvem apenas recipes suportadas.
- [ ] `/reload` aplica mudanças de recipe sem stale cache.
- [ ] Restart durante processamento não duplica input/output.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 19. Evidências e limites
A modlist física confirma JAR/mod id/runtime 2.0.0. A release oficial confirma NeoForge 1.21.1 Client & Server e fix de Lithium. A documentação oficial confirma novos fluids/crushed ores, maior rendimento e intenção de coexistência com outros molten-metal mods, incluindo recomendação de Almost Unified. O source público disponível não corresponde à linha 2.0.0 e não foi usado para inventar internals.
> 🔒 **Boundary canônico:** Metalwork amplia forms e recipes de minério; o Recipe Manager do servidor decide rendimento. Unificação de variantes nunca deve criar conversão lucrativa ou alterar conservation.

## 17. Atualização upstream 3.0.0 — não instalada
A versão física do pack continua **2.0.0**. A release **3.0.0** para NeoForge 1.21.1 foi publicada em 28/09/2026.

O changelog oficial da branch 1.21.1 rotula 3.0.0 como **Initial Release** da linha atual e aponta documentação separada para conteúdo, compatibilidade, opções de configuração e processing. Ele **não publica uma lista granular de alterações desde 2.0.0**. Por isso, este dossiê não inventa migrações internas, recipes removidas/adicionadas ou mudanças de IDs sem evidência versionada.

A descrição atual mantém o objetivo de interoperabilidade entre mods de metais fundidos: preencher lacunas para que molten metals possam ser processados em máquinas suportadas, com estágios de processamento que podem aumentar yield. O stack recomendado pelo projeto inclui AllTheOres, Create Metallurgy, Create Big Cannons e TFMG; vários desses domínios se sobrepõem ao pack atual.

**Gate de promoção 2.0.0→3.0.0:** comparar registries/IDs e configs entre os dois JARs; revisar recipes de crushed ores, dusts e molten fluids; verificar deduplicação com Almost Unified; regressar Create Metallurgy, CBC, TFMG, Dreams & Desires/Northstar e qualquer script KubeJS que dependa de IDs da 2.0.0; validar save/reload e recipe viewers.

Fontes upstream: CurseForge Create: Metalwork 3.0.0 para NeoForge 1.21.1; repositório oficial `AverageAnime/create-metalwork-multiloader`, branch `1.21.1`, `CHANGELOG.md`.

> **Atualização/Status — valor histórico preservado do Notion:** PADRÃO ALEX'S MOBS REVALIDADO EM 12/09/2026 — lote físico #196: createmetalwork-2.0.0.jar / runtime 2.0.0 reconfirmados como latest Release NeoForge 1.21.1; crushed/molten ore processing, Almost Unified boundary, CBC/DnD/Northstar overlaps e Lithium crash fix permanecem atuais.

## 20. Complemento verificável da 3.0.0 e delimitação do diff 2.0.0 → 3.0.0 — 22/11/2026

**Identificação e publicação:** [Create: Metalwork 2.0.0](https://www.curseforge.com/minecraft/mc-mods/create-metalwork/files/7287996) (File ID **7287996**, `createmetalwork-2.0.0.jar`) é o artefato top-level instalado **#195**. A [3.0.0 NeoForge 1.21.1](https://www.curseforge.com/minecraft/mc-mods/create-metalwork/files/8995820), File ID **8995820**, publicada em **28/09/2026**, é o candidato Release `createmetalwork-neoforge-1.21.1-3.0.0.jar`, **não instalado**. A release 3.0.0 é marcada pelo autor como **“Initial Release”** da nova implementação, sem changelog diferencial completo. **Não interpretar ausência de lista granular como ausência de mudanças.** Foi usado o source versionado [`AverageAnime/create-metalwork-multiloader`, commit de 28/09/2026 `fd3ba9a`](https://github.com/AverageAnime/create-metalwork-multiloader/tree/fd3ba9aa24a), que declara `version=3.0.0` e Minecraft 1.21.1, em vez do `main` atualizado posteriormente para 3.1.0. Este source **não prova** por si só qual feature já existia no JAR 2.0.0.

### Funcionalidades e contrato documentados para a implementação 3.0.0

- **Registro e interoperabilidade de materiais:** a [lista oficial de conteúdo 3.0.0](https://github.com/AverageAnime/create-metalwork-multiloader/blob/fd3ba9aa24a/documentation/wiki/ContentList.md) enumera `createmetalwork:molten_*`, `crushed_*`, `dirty_*_dust` e `*_dust`. Entre os exemplos: metais de BetterEnd (**aeternium**, **terminite**, **thallasium**), BetterNether (**cincinnasite**), Northstar (**titanium**, **martian_steel**) e metais do ecossistema Create. **Registro condicional:** fluidos-base como `molten_iron`, `molten_copper` e `molten_tin` não devem ser duplicados quando `alltheores` ou `createmetallurgy` registram suas versões; `molten_steel` considera também `tfmg` e `createbigcannons`. A presença física de um provider opcional deve ser verificada antes de afirmar que um item/fluid condicional existe.
- **Matriz de integrações publicada em 3.0.0:** [Compatibility](https://github.com/AverageAnime/create-metalwork-multiloader/blob/fd3ba9aa24a/documentation/wiki/Compatibility.md) lista Create, AllTheOres, BetterEnd, BetterNether, Big Cannons, Dreams & Desires, Ironworks, Metallurgy, Simple Ore Doubling, TFMG, Mekanism, Mythic Metals e Northstar com suportes distintos a fluidos, itens, receitas, tags e *deference*. **Não** pressupor que todos esses projetos estão fisicamente presentes nem que todas as receitas ficam habilitadas.
- **Config comum e migração:** [`createmetalwork-common.toml`](https://github.com/AverageAnime/create-metalwork-multiloader/blob/fd3ba9aa24a/documentation/wiki/ConfigOptions.md) documenta `[addons].addons_enabled`, `[blocks].fluid` e `[items].item`. Entradas aceitam condições `@absent:<modids>`, `@mod:<modids>`, `@any:<modids>` e `@always`, com encadeamento para impedir registries sobrepostos. A configuração persistida **prevalece** sobre os defaults da versão e **novos defaults não aparecem automaticamente** em listas já existentes; é necessário regenerar as listas ou inserir manualmente com backup. Mudanças de ID/registro exigem **reinicialização**, não apenas `/reload`. IDs customizados exigem assets/modelos/texture apropriados.
- **Capacidade do Create Basin:** a opção `[compat.create].expanded_basin_fluids = true` em `createmetalwork-server.toml` permite até **quatro slots de fluidos por lado** no Basin (contra dois), ampliando receitas de liga multimetal; cada slot permanece com **1000 mB**. **Desabilitá-la descarta fluidos excedentes** nos lados expandidos: é uma operação potencialmente destrutiva e um gate de persistência/processamento, sobretudo quando máquinas/receitas já estão em uso.
- **Rotas e rendimento documentados em 3.0.0:** o [Processing Guide](https://github.com/AverageAnime/create-metalwork-multiloader/blob/fd3ba9aa24a/documentation/wiki/ProcessingGuide.md) descreve rotas com TFMG **Industrial Blasting**, **heated mixing**, poeira após milling/washing e vat-smelting, com multiplicadores *teóricos* de **1× a 7,5×** conforme rota/preparo. Esses ratios são o guia do desenvolvedor para a linha 3.0.0, **não rendimentos medidos** no pack nem comparação calculada com a 2.0.0.
- **Solidificação em água:** a 3.0.0 prevê fluidos `molten_<metal>` se converterem em `c:storage_blocks/<metal>` conforme preferência de providers, resolvida no primeiro carregamento de tags; troca de tags em sessão requer restart. A documentação alerta que **1000 mB** de fonte fluida equivalem a bloco de storage que vale **810 mB**, portanto esse caminho perde rendimento material.
- **Receita verificável:** o JSON da [3.0.0 `molten_iron_from_crushed_raw.json`](https://github.com/AverageAnime/create-metalwork-multiloader/blob/fd3ba9aa24a/common/src/generated/resources/data/createmetalwork/recipe/mixing/melting/molten_iron_from_crushed_raw.json) exige `heated`, entrada em `c:crushed_raw_materials/iron` e produz **90 mB** de `createmetalwork:molten_iron`. A recipe fica **desabilitada** quando `createmetallurgy` ou `alltheores` estão instalados e é condicionada à entrada/config. **Não usar 90 mB como regra universal** para ferro no pack, que possui integrações alternativas.
- **Requisitos da implementação 3.0.0:** no [manifest NeoForge do commit versionado](https://github.com/AverageAnime/create-metalwork-multiloader/blob/fd3ba9aa24a/neoforge/src/main/resources/META-INF/neoforge.mods.toml), o range **Create opcional** é `[6.0.0,6.1.0)`, e TFMG opcional é `[1.2.0,)`. O build declara NeoForge **21.1.238**; o inventário do pack registra Create **6.0.10** e NeoForge **21.1.250**, satisfazendo esses ranges estáticos *referenciados na source*. Isso **não** atesta compatibilidade com outros addons, recipes ativas ou desempenho de runtime.

### 3.1.0 identificada somente em source — não atribuir à 3.0.0

O [`CHANGELOG.md` da branch atual](https://github.com/AverageAnime/create-metalwork-multiloader/blob/1.21.1/CHANGELOG.md) contém seção **3.1.0**, cujo commit de **20/11/2026** informa **restaurar** para **180 mB** o output de melted crushed ore que era **90 mB** na implementação anterior, com revisões de slag, novas recipes/integrações e correções. **Esse texto não é changelog da distribuição 3.0.0** e nenhuma release 3.1.0 NeoForge 1.21.1 foi confirmada no catálogo CurseForge consultado nesta auditoria. Usar 3.1.0 apenas como **alerta de potencial desequilíbrio/regressão da 3.0.0**, sem atribuir features/patches futuros ao JAR disponível.

### Limite do que foi efetivamente resolvido

**Resolveu-se a falta de documentação concreta sobre o comportamento da 3.0.0**, incluindo registries condicionais, configuração, fluidos, rotas, ratios e segurança de Basin. **A comparação integral 2.0.0 → 3.0.0 permanece bloqueada**: o changelog “Initial Release” não enumera mudanças relativas à versão antiga, não foram obtidos os dois JARs para confronto de namespaces, registries, configuração, recipe/data, manifest ou bytecode e o source antigo disponível não foi demonstrado como a árvore exata da **2.0.0 NeoForge 1.21.1**. Portanto **não classificar esse salto como completamente auditado ou testado** e não inferir mudanças/remoções específicas do 2.0.0 ausentes de evidência.

**Gate de promoção:** obter JARs 7287996/8995820 e realizar diff de registry IDs, configs, recipes e tags; testar backup/migração de saves, Basin com 2→4 slots e redução posterior, consumo de fluido, liquefação/solidificação, Almost Unified, Create Metallurgy, TFMG, CBC, Dreams & Desires, Northstar, KubeJS, JEI e loops de ganho de material; confirmar instalação física após atualização. Preservar `✅-create-metalwork v2.0.0.md`, versão e localização atuais até prova da mudança de JAR.

## 21. Conciliação da baseline pública da linha 2.0 — 22/11/2026

A comparação anterior já estabeleceu o funcionamento *documentado* da 3.0.0. Esta passagem recuperou **duas fontes primárias da linha antiga 1.21.1 NeoForge** para distinguir conteúdo preexistente e evolução:

1. [**2.0.0-beta**, Modrinth, versão `p4smHMGV`](https://modrinth.com/mod/create-metalwork/version/2.0.0-beta), declara explicitamente o **port para Minecraft 1.21.1**, compatibilidade **naquela Beta** limitada a **Create: Ironworks** e **Create: Simple Ore Doubling**, **solidificação de metais fundidos em água** e atualização de texturas de fluidos/baldes. Consequentemente, **a solidificação em água NÃO é uma novidade comprovadamente introduzida pela 3.0.0**: o mecanismo já era publicamente documentado na linha Beta 2.0.
2. [**2.0.0 Release**, Modrinth, versão `cQiPf5no`](https://modrinth.com/mod/create-metalwork/version/2.0.0), registra como alteração específica **apenas a correção de crash com Lithium (#41)**. A nota **não especifica** se a matriz de integração daquela release mudou em relação à Beta; por isso, não extrapolar que a cobertura final da 2.0.0 permaneceu estritamente em dois addons.
3. [**3.0.0 Release**, Modrinth, versão `jImnSYtO`](https://modrinth.com/mod/create-metalwork/version/3.0.0), declara “Initial Release” da nova linha; seu [source e matrizes 3.0.0 versionados em `fd3ba9a`](https://github.com/AverageAnime/create-metalwork-multiloader/tree/fd3ba9aa24a) ampliam **a cobertura atualmente documentada** para BetterEnd, BetterNether, TFMG, Create Metallurgy, Big Cannons, Northstar, Mekanism, AllTheOres etc., com registro condicionado ao provider. **A expansão em relação ao escopo declarado na 2.0.0-beta é verificável como contraste de documentação; não é prova de que cada integração apareceu somente na 3.0.0.**

**Conclusão operacional para este caso:** a finalidade e o conjunto concreto de funcionalidades *comprovadamente documentadas* da 3.0.0 estão reconciliados, e não há razão para atualizar o filename enquanto a física permanecer 2.0.0. A única pergunta que **fontes publicadas não respondem** é o delta binário exato entre **a Release 2.0.0** e **a Release 3.0.0**: ambas têm downloads oficiais, mas não se obteve o conteúdo dos dois JARs nesta sessão. Não inventar removals, mudanças de registries nem migrações; para afirmar equivalência de saves ou segurança de substituição é indispensável comparar os artefatos e fazer QA em cópia de mundo. **A auditoria do material oficial público está fechada; a compatibilidade de uma futura troca física não foi testada.**


## Sincronização documental do usuário — 22/11/2026

**Mudança declarada e registrada:** `2.0.0` → `3.0.0`; JAR oficial `createmetalwork-neoforge-1.21.1-3.0.0.jar` (CurseForge 8995820). A nomeação anterior e os trechos sobre 'não instalada' no histórico referem-se a auditorias anteriores a este pedido, não ao estado declarativo corrente.

**Conteúdo técnico verificado e riscos:** 3.0.0: nova implementação documenta registries de metais condicionais, compatibilidade de providers, arquivo createmetalwork-common.toml, slots de fluido expandidos no Basin, processamento metalúrgico e receitas com output condicional. A release oficial diz 'Initial Release'; o diferencial binário completo entre 2.0.0 e 3.0.0 não foi obtido, portanto não marcar a auditoria técnica como comparação binária exaustiva.

**Autoridade e limite:** versão instalada **declarada pelo usuário**, não leitura do diretório `mods/`; inventário físico antigo só mantém posições históricas. Requisitos de dependências que aparecem desatualizados em outros dossiers são pendências entre lotes e não impedem o versionamento deste mod. Testes de compatibilidade global não foram executados.

**Fonte da versão-alvo e filename:** https://www.curseforge.com/minecraft/mc-mods/create-metalwork/files/8995820
