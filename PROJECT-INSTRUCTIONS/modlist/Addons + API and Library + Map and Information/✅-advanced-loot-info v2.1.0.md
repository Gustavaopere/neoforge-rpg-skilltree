# Advanced Loot Info

> **Autoridade física atual — 22/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#7**: `AdvancedLootInfo-neoforge-1.21.1-2.1.0.jar`, mod id `ali`, runtime `2.1.0`.
## Propriedades do registro

- **Mod:** Advanced Loot Info
- **Arquivo JAR:** `AdvancedLootInfo-neoforge-1.21.1-2.1.0.jar`
- **Versão 1.21.1:** 2.1.0
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL
- **Função:** Plugin informacional para JEI/EMI/REI que interpreta loot tables e villager trades em estrutura navegável, exibindo loot entries, conditions, functions, number providers e categorias. Suporta LootJS de forma nativa e possui plugin API para tipos customizados. Não altera loot, probabilidades nem trades; é observabilidade. A linha 2.1.0 também adiciona cores configuráveis de tooltip, enum values traduzíveis e loot entries embutidas em tooltips.
- **Dependências:** Advanced Core Info (ACI) é REQUIRED desde a linha 2.0.1 e está instalado em 1.1.0. ALI não é standalone: precisa de um recipe viewer suportado — JEI, EMI ou REI. No pack, a modlist física atual confirma **JEI 19.56.0.440** como viewer efetivo; EMI/REI não aparecem top-level. Suporte built-in a mods externos foi reduzido na linha 1.11.0: LootJS permanece suportado, enquanto outros mods devem fornecer plugins próprios quando usam tipos customizados.
- **Sobreposição:** Sobreposição informacional parcial com Just Enough Resources e outros viewers/tooltips de loot. ALI diferencia-se por representar a estrutura genérica da loot table/trade e por expor plugin API. Lootr/Loot Integrations modificam comportamento/distribuição de loot e não são substitutos. JEI é host de UI, não concorrente.
- **Compatibilidade/Riscos:** A build 2.1.0 é beta. Tabelas vanilla/data-driven são observáveis, mas loot entries/conditions/functions/number providers proprietários de outros mods podem aparecer incompletos ou como erro sem plugin do provider. ACI↔ALI↔JEI forma um pipeline version-sensitive; dedicated server/reload devem validar payloads e cache. Sobreposição visual com JER/outros JEI addons pode duplicar páginas, sem alterar loot real. Não inferir drop chance final se conditions/contexto custom não forem interpretados. Upstream 2.2.0/2.3.0 altera plugin registration, GLM filtering/scanning, trade UI/registration, custom ingredients e detecção de count/chance.
- **Observações:** Mudança arquitetural importante: desde 1.11.0 o projeto deixou de manter built-in support para a maioria dos mods, exceto LootJS; suporte a tipos próprios deve vir do mod owner/plugin. Portanto “ALI instalado” NÃO garante interpretação completa de toda loot table modded. Falhas de exibição não equivalem a falhas de loot. Na 2.2.0, o suporte Farmer's Delight foi movido para o mod separado **ALI Compat**; na 2.3.0, a linha ganhou custom ingredients fora da hierarquia `Ingredient`, informação de spawn natural de entidades e melhorias de memória/scan.
- **Procedência:** modlist.txt física atual + CurseForge oficial Advanced Loot Info 2.1.0/2.2.0/2.3.0 e fontes já auditadas no dossiê. Reconciliação física: JAR/runtime permanecem exatamente `AdvancedLootInfo-neoforge-1.21.1-2.1.0.jar` / `2.1.0`; 2.2.0 e 2.3.0 são apenas updates upstream disponíveis. JEI físico foi revalidado em 19.56.0.440.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/advanced-loot-info
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — runtime físico permanece 2.1.0. CurseForge publicou **2.2.0** em 17/09/2026 e **2.3.0** em 28/09/2026 para 1.21.1; ambos os changelogs foram revisados e registrados abaixo.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

> 🔎 **Escopo canônico.** Runtime físico: `AdvancedLootInfo-neoforge-1.21.1-2.1.0.jar`, release **beta**. Advanced Loot Info (ALI) é uma camada de **observabilidade**: lê/representa loot tables e villager trades em um recipe viewer. Ele não injeta loot, não muda chances e não cria trades.

## 1. Arquitetura do stack
O pipeline correto desta instância é:
1. o servidor/datapack/provider define a loot table ou trade real;
2. **ALI** interpreta a estrutura;
3. **Advanced Core Info (ACI)** fornece plugin discovery, tooltip trees e transporte servidor→cliente;
4. **JEI** apresenta as categorias/tooltips ao usuário.

O pack usa ACI 1.1.0 e JEI 19.56.0.440. EMI/REI são viewers suportados upstream, mas não aparecem top-level no snapshot físico atual.

## 2. Loot tables
ALI expõe loot tables de forma estruturada para responder perguntas como:
- de onde um item pode vir;
- quais pools/entries participam;
- que condições controlam uma entrada;
- quais funções transformam o stack;
- que number providers determinam quantidade/chance/rolls quando o tipo é compreendido.

A informação visual não deve ser confundida com uma simulação perfeita do RNG. Contexto de loot, condições customizadas, luck, entidade, damage source, biome, scoreboard ou código proprietário podem exigir plugin específico para serem representados fielmente.

## 3. Loot Entries
O plugin system permite registrar renderização/interpretação para **custom Loot Entries**. Entries vanilla conhecidas podem ser decompostas na tooltip tree; entries de mods que usam tipo próprio podem precisar de integração fornecida pelo autor desse mod.

A linha **2.1.0** acrescenta exibição de loot entries **dentro de tooltips**, inclusive em contextos como conteúdo de Shulker Box, ampliando a leitura sem obrigar o usuário a sair para outra página.

## 4. Loot Conditions
ALI possui suporte extensível para **Loot Conditions**. A condição deve ser entendida como gate, não como simples texto decorativo: se o plugin não souber interpretar uma condição proprietária, a chance final apresentada não deve ser tratada como authority absoluta.

Exemplos de categorias de condição que outro chat deve procurar quando estiver auditando loot:
- requisitos de entidade/killer/player;
- chance e luck;
- localização/dimensão/bioma;
- ferramenta/encantamento;
- estado de bloco;
- predicates e condições compostas;
- conditions adicionadas por LootJS ou outro provider.

A lista acima é um mapa de auditoria; não significa que ALI 2.1.0 publique renderer dedicado para todo tipo modded existente.

## 5. Loot Functions
Functions alteram o stack depois que uma entry é escolhida. ALI suporta plugins para **custom Loot Functions**, portanto quantidade, encantamentos, NBT/components, damage, nome ou transformações próprias podem ser representados quando existe suporte ao tipo.

Se a UI mostra a entry mas não representa uma function custom, o item real ainda pode sair diferente. Diagnóstico correto: comparar data real da loot table com o renderer/plugin, não concluir que o loot está quebrado.

## 6. Number Providers
O projeto oferece extensão para **custom Number Providers**. Providers são importantes em rolls/counts/chances e podem ser constantes, uniformes ou formas mais complexas dependendo do jogo/mod.

Regra operacional: uma expressão exibida ou valor resumido é informação; para economia/perks que dependam de probabilidade exata, verificar o JSON/provider real da versão instalada.

## 7. Villager trades
ALI também exibe **villager trades**. Isso permite inspecionar ofertas sem atribuir ao mod qualquer alteração da economia.

Para packs com profissões/trades modificados, validar:
- profissão e nível do villager;
- input(s) e output;
- condições/raridade quando aplicáveis;
- trades de mods que usam factories/types customizados;
- refresh depois de datapack reload ou server restart.

ALI não força um trade a existir. Ele apresenta o que a camada de dados/provider disponibiliza e consegue interpretar.

## 8. Recipe viewers suportados
ALI não é standalone. Upstream suporta:
- **JEI**;
- **EMI**;
- **REI**.

Cada viewer é marcado individualmente como integração porque basta **um** deles para a função de apresentação. Nesta instância, o viewer efetivo é JEI.

Não atribuir a ALI funções próprias do JEI, como busca geral de itens/recipes. ALI registra categorias/informação dentro do host.

## 9. LootJS
**LootJS** é o principal suporte externo mantido built-in pelo projeto atual. Isso é relevante porque o pack pode usar scripts para alterar/adicionar loot.

A linha 1.11.0 corrigiu casos de loot removido condicionalmente por LootJS não sendo exibido corretamente. Consequência: ao auditar scripts, comparar estado depois de conditions/reload, não a definição bruta anterior à modificação.

## 10. Mudança arquitetural desde 1.11.0
O changelog upstream registra uma decisão importante: **support para a maioria dos mods foi removido do core, exceto LootJS; os próprios mod owners devem fornecer suporte/plugins**.

Isso muda a interpretação da ficha:
- ALI é genericamente extensível;
- ele não promete conhecer todos os tipos customizados de centenas de mods;
- ausência de detalhe de um mod não é automaticamente bug na loot table;
- antes de criar workaround, procurar plugin/integração do provider.

Essa regra é especialmente importante neste modpack grande.

## 11. Plugin API
Upstream documenta plugin points para:
- custom Loot Entries;
- custom Loot Conditions;
- custom Loot Functions;
- custom Number Providers;
- custom categories, atualmente derivadas da categoria Gameplay conforme a documentação pública.

Para projeto próprio, essa API é a rota correta quando houver tipos de loot custom que ALI precise explicar. Não fazer mixin de renderer genérico se plugin público resolver.

> ⚠️ **Limite de evidência:** a página pública não fornece nesta auditoria nomes/signatures de classes e métodos da API. Qualquer implementação deve confirmar a API exata no source/JAR 2.1.0 antes de codificar.

## 12. ACI como hard dependency
Desde a linha **2.0.1**, Advanced Core Info passou a ser required. O pack usa **ACI 1.1.0**.

ACI fornece a infraestrutura compartilhada; ALI é o consumer de loot. Atualizar um sem o outro pode produzir linkage errors, payload mismatch, plugin discovery incompleto ou tooltips vazias.

## 13. Networking e payload
A linha 1.11.0 otimizou networking ao enviar translation keys por índices, reduzindo o volume de dados informado pelo upstream em aproximadamente 40% nessa mudança.

Isso confirma que ALI não é puramente uma GUI local: existe sincronização de informação entre servidor e cliente. Portanto dedicated-server smoke é obrigatório.

## 14. Linha 2.1.0
O changelog da linha 2.1.0 registra:
- `tooltipColors` configurável para cores de texto, valor, erro e branch;
- enum values traduzíveis nas tooltips;
- remoção de um pattern default de Trial Chambers que também casava indevidamente loot tables de equipamentos modded;
- loot entries exibidas dentro de tooltip, incluindo contêineres como Shulker Box;
- correção de crash raro no startup do servidor.

Essas mudanças são de apresentação/classificação/estabilidade; não representam alteração das loot tables do jogo.

## 15. Beta status
A build física 2.1.0 para 1.21.1 é publicada como **Beta**. Isso não significa que esteja automaticamente quebrada, mas aumenta a necessidade de:
- manter ACI/viewer alinhados;
- validar server start;
- validar reload;
- observar erros de renderer/plugin;
- não promover uma representação incompleta a authority de economia.

## 16. Sobreposição com Just Enough Resources (JER)
JER e ALI podem mostrar informação relacionada a drops/recursos dentro do JEI, porém a arquitetura é diferente.

**ALI:** interpretação de loot table/trades + plugin API.

**JER:** categorias próprias de informação de recursos/drops e outros domínios conforme o mod.

Possível efeito no pack: páginas semelhantes ou informação redundante. Isso é sobreposição de UI. Não é razão automática para remover um dos dois.

## 17. Relação com Lootr e Loot Integrations
Lootr/Loot Integrations atuam na **mecânica/distribuição/integração do loot**. ALI não.

Se um baú gera loot individual por jogador ou uma integration injeta itens numa table, ALI pode eventualmente apresentar a table resultante se o tipo for compreendido, mas não é o responsável pelo comportamento do contêiner.

## 18. Failure modes que outro chat deve reconhecer
### Entrada aparece sem detalhe
Provável tipo custom sem plugin ou renderer incompleto.

### Chance parece errada
Verificar conditions/context/luck/functions antes de culpar RNG.

### Categoria duplicada
Verificar JER/outro JEI addon e registrations do viewer.

### Página vazia após `/reload`
Verificar cache/rebuild/sync ACI↔ALI↔JEI.

### Disconnect/payload error
Verificar versões server/client, ACI e ALI.

### Loot real difere da tooltip
A loot table/provider é authority; ALI é representação. Investigar custom types/functions/scripts.

## 19. Riscos específicos deste pack
1. **Quantidade de mods:** muitos providers usam loot serializers/types próprios.
2. **LootJS/datapacks:** ordem e reload podem mudar o resultado depois do boot.
3. **JER simultâneo:** possível redundância visual.
4. **Beta 2.1.0:** regressões de renderer/network devem ser consideradas.
5. **Villager overhauls:** trades custom podem exigir suporte próprio.
6. **Tooltips profundas:** categorias grandes podem afetar legibilidade/performance de UI.
7. **Falsa authority:** usar uma tooltip incompleta para balancear economia é erro metodológico.

## 20. Matriz de validação
1. iniciar cliente com ACI 1.1.0 + ALI 2.1.0 + JEI;
2. iniciar dedicated server e conectar;
3. loot table vanilla simples;
4. table com pools/entries aninhadas;
5. condition de chance/luck;
6. function que altera quantidade;
7. number provider variável;
8. villager trade vanilla por múltiplos níveis;
9. trade modded simples;
10. LootJS adicionando loot;
11. LootJS removendo loot condicionalmente;
12. custom loot entry sem plugin — confirmar fallback/erro controlado;
13. custom condition/function com plugin quando existir;
14. `/reload` depois de alterar datapack;
15. comparar ALI vs loot real em amostra controlada;
16. abrir conteúdo de Shulker Box/tooltip com nested loot quando aplicável;
17. alterar `tooltipColors` e verificar reload/restart necessário;
18. verificar enums traduzíveis em PT-BR quando translation key existir;
19. comparar páginas com JER para identificar apenas redundância visual;
20. remover ALI em ambiente de teste e confirmar que drops/trades reais permanecem idênticos.

## 21. O que ALI não faz
- não altera loot table;
- não muda drop chance;
- não cria villager trade;
- não cria loot por jogador;
- não é JEI/EMI/REI;
- não garante suporte built-in a todo mod;
- não substitui inspeção do JSON/source quando uma chance exata vira contrato técnico.

## 22. Regras para outros chats
- Tratar a loot table/provider real como authority.
- Quando uma tooltip estiver incompleta, procurar plugin do provider antes de criar compat custom.
- Usar LootJS support quando a origem for LootJS.
- Não transformar “não aparece no ALI” em “não existe no jogo”.
- Para API/plugin, verificar classes/signatures da **2.1.0** antes de implementar.
- Não usar versão 2.0.1 do guia como runtime atual; a física é 2.1.0.

## 23. Fontes e confiança
**Authority física:** modlist 07/09/2026 — `AdvancedLootInfo-neoforge-1.21.1-2.1.0.jar`.

**Upstream:** [CurseForge — Advanced Loot Info](https://www.curseforge.com/minecraft/mc-mods/advanced-loot-info), arquivos/changelogs 1.11.0, 2.0.1 e linha 2.1.0; [Advanced Core Info](https://www.curseforge.com/minecraft/mc-mods/advanced-core-info).

**Fonte interna:** guia Gameplay/Sistemas, preservado como referência conceitual, mas sua versão 2.0.1 foi substituída pela versão física 2.1.0.

**Confiança:** alta para arquitetura, viewers, LootJS, plugin categories, ACI requirement e mudanças publicadas. Para tipos customizados de mods específicos, a confiança só é alta depois de confirmar plugin/runtime correspondente.
## 24. Histórico upstream 2.1.0 → 2.3.0 — não instalado
A autoridade física continua em **Advanced Loot Info 2.1.0**. Para Minecraft 1.21.1, o CurseForge publicou depois **2.2.0** e **2.3.0**, ambas ainda marcadas como Beta e não instaladas neste pack.

### 2.2.0 — 17/09/2026
O changelog oficial registra:
- nova API `IServerRegistry.registerTrades`, permitindo que um plugin liste trades do próprio trader;
- a quantidade de seleções de trade passa a vir do amount do próprio trade set, em vez de ser assumida;
- corrige erros do JEI quando a integração/plugin REI é usada;
- **Global Loot Modifiers** só são exibidos em loot tables nas quais todas as suas conditions podem passar;
- loot tables de entity/gameplay passam a ter ordenação estável;
- job sites de profissões modded passam a ser resolvidos pelo **POI registry**;
- uma loot table referenciada por outra loot table deixa de disparar entity scan indevido;
- plugins de Global Loot Modifier passam a usar `IGlobalLootModifierPlugin` de forma uniforme entre loaders;
- corrige plugins de Global Loot Modifier de outros mods sendo ignorados no **NeoForge**;
- o suporte built-in de **Farmer's Delight** é removido do ALI principal e movido para o mod separado **ALI Compat**.

Impacto para o pack: a mudança de GLM é material para loot modificado por NeoForge/addons, porque reduz falsos positivos e corrige plugins externos ignorados. A migração de Farmer's Delight para ALI Compat significa que atualizar ALI sem revisar esse companion pode reduzir cobertura informacional mesmo sem alterar loot real.

### 2.3.0 — 28/09/2026
O changelog da 2.3.0 adiciona e corrige:
- suporte a **custom ingredients que não são subclasses de `Ingredient`**;
- melhoria na detecção de **count e chance**;
- correção de crash de servidor no Forge quando um GLM não consegue fornecer seu codec — relevante como hardening cross-loader, embora o runtime deste pack seja NeoForge;
- rework da **trades UI**, agora exibindo o spawn egg do trader;
- correção de tooltip ausente em block loot no **JEI e REI**;
- melhoria no registro de trades;
- entity loot passa a mostrar **onde a entidade nasce naturalmente**: dimensões, biomas, estruturas, weight e group size;
- nova config `showEntitiesWithoutLoot` para também listar entidades que spawnam mas não dropam nada;
- menor uso de memória no cliente com **JEI e EMI**;
- scan de loot mais rápido com **Global Loot Modifiers**;
- correção de maximum count aleatório incorreto para **binomial count**;
- atualização de tradução chinesa.

Impacto para este pack: o JEI físico está em **19.56.0.440**, então tooltip/UI/memory paths são diretamente relevantes. Natural-spawn metadata amplia ALI de 'de onde vem o loot' para contexto de spawn da entidade, mas continua sendo observabilidade: biome/structure/weight exibidos não substituem o provider de spawn/worldgen. Count/chance melhorados também não transformam ALI em simulador authoritative quando há conditions ou código custom fora do contrato compreendido.

### Gate de promoção 2.1.0 → 2.3.0
1. client + dedicated server com ACI 1.1.0 e JEI 19.56.0.440;
2. loot vanilla, entity e block loot com tooltips;
3. Global Loot Modifiers condicionais: mostrar apenas onde conditions podem passar;
4. plugin externo de GLM em NeoForge;
5. LootJS e custom ingredient não-`Ingredient`;
6. villager/modded trader UI, seleção de trades e POI job site;
7. entity natural-spawn metadata em dimension/biome/structure;
8. `showEntitiesWithoutLoot` on/off;
9. binomial count e comparação com loot real controlado;
10. Farmer's Delight com/sem ALI Compat para confirmar perda/ganho de cobertura;
11. `/reload`, reconnect e reabertura de JEI sem stale cache;
12. perfil de memória/scan em conjunto representativo de loot tables do pack.

Fontes upstream: CurseForge Advanced Loot Info 2.2.0 e 2.3.0 para Minecraft 1.21.1; changelogs oficiais da linha 2.x.

## 25. Atualização ALI 2.4.0 — 10/10/2026

**Físico:** `AdvancedLootInfo-neoforge-1.21.1-2.1.0.jar` / 2.1.0. A seção 24 preserva as releases **2.2.0 e 2.3.0**, com fixes de Global Loot Modifier NeoForge, compat LootJS, tooltip JEI/REI, natural spawning info, memory e count/binomial. A nova **2.4.0 (06/10/2026)**, arquivo **`AdvancedLootInfo-neoforge-1.21.1-2.4.0.jar`**, é a última **NeoForge Minecraft 1.21.1** verificada. **2.4.1** publicada para Minecraft **26.3** não deve ser confundida com release desta linha.

### Mudanças oficiais 2.4.0 (source `yanny7/AdvancedLootInfo`, branch `1.21.1`, `ali/CHANGELOG.md`)
- Nova config **`spawnInfo`** para ocultar contexto de spawning.
- Exibe **chances e rolls condicionados a Luck** em linhas por valor; a chance de entry agora considera **quality/luck** e pools com alternatives consideram **peso da alternativa sorteada**.
- Number converters e tooltip de count/chance/entry factories recebem as condições do valor; número mais provável, rows por nível, **charts (`showCharts`) e fórmulas avançadas com F3+H**.
- Tooltips maiores que a janela tornam-se **scrollable com wheel**.
- `showInGameNames` traduz biome, dimension, structure, tags e IDs quando tradução está disponível, inclusive no contexto de spawning.
- Fixes de hardening: plugin quebrado de loot functions/conditions ou entry/tooltip/trades não deve ocultar a loot table/trader inteira; componentes não suportados aparecem como `unsupported`.
- `Storage` e enchantment-level number providers passam a exibir valores; enchantment-level values simplificados em uma linha com level rows em vez de tree completa.
- LootJS `replaceLoot` corrigido com **LootJS 3.7.0**; broken loot modifications de LootJS/GLM são ignorados de forma localizada em vez de eliminar whole table.
- Valores inválidos de config geram warning e só o valor inválido é ignorado, sem reset de toda opção.
- Ajustes para names com placeholders (%s, GregTech), blocks que dropam apenas si mesmos, e correção de equipment slot translations no **Fabric** (não afirmar como bug NeoForge).

### Interoperabilidade e QA
ALI é viewer **informacional**; chances exibidas podem depender de Luck, GLM, data components e mods custom, mas **não alteram drop rates reais**. A atualização deve acompanhar requisitos de **ACI** (físico1.1.0; upstream1.3.0) e **JEI** (físico19.56.0.440; upstream19.57.0.451), e registrar o suporte Farmer's Delight removido para **ALI Compat** desde 2.2.0 sem presumir companion instalado. Verificar chances/rolls por luck e alternativas, configs `showCharts`/`spawnInfo`/`showInGameNames`, raw versus translated IDs, tooltip scroll, LootJS replaceLoot e GLMs com errors, modded-trader POI, reload, client+dedicated server e limites de memória/packet payload com grande modlist. Não indicar QA executado.

**Fontes primárias:** https://github.com/yanny7/AdvancedLootInfo/blob/1.21.1/ali/CHANGELOG.md ; https://www.curseforge.com/minecraft/mc-mods/advanced-loot-info/files/all?version=1.21.1

**Estado:** 2.4.0 upstream auditada, 2.1.0 fisicamente comprovada; dependências requerem reconciliação antes de promoção.
