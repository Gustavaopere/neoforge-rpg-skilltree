# Advanced Loot Info

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81ae8ff0c2668b97e9ec
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Advanced Loot Info
- **Arquivo JAR:** `AdvancedLootInfo-neoforge-1.21.1-2.1.0.jar`
- **Versão 1.21.1:** 2.1.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL
- **Função:** Plugin informacional para JEI/EMI/REI que interpreta loot tables e villager trades em estrutura navegável, exibindo loot entries, conditions, functions, number providers e categorias. Suporta LootJS de forma nativa e possui plugin API para tipos customizados. Não altera loot, probabilidades nem trades; é observabilidade. A linha 2.1.0 também adiciona cores configuráveis de tooltip, enum values traduzíveis e loot entries embutidas em tooltips.
- **Dependências:** Advanced Core Info (ACI) é REQUIRED desde a linha 2.0.1 e está instalado em 1.1.0. ALI não é standalone: precisa de um recipe viewer suportado — JEI, EMI ou REI. No pack, JEI 19.53.0.425 é o viewer efetivo; EMI/REI não aparecem top-level. Suporte built-in a mods externos foi reduzido na linha 1.11.0: LootJS permanece suportado, enquanto outros mods devem fornecer plugins próprios quando usam tipos customizados.
- **Sobreposição:** Sobreposição informacional parcial com Just Enough Resources e outros viewers/tooltips de loot. ALI diferencia-se por representar a estrutura genérica da loot table/trade e por expor plugin API. Lootr/Loot Integrations modificam comportamento/distribuição de loot e não são substitutos. JEI é host de UI, não concorrente.
- **Compatibilidade/Riscos:** A build 2.1.0 é beta. Tabelas vanilla/data-driven são observáveis, mas loot entries/conditions/functions/number providers proprietários de outros mods podem aparecer incompletos ou como erro sem plugin do provider. ACI↔ALI↔JEI forma um pipeline version-sensitive; dedicated server/reload devem validar payloads e cache. Sobreposição visual com JER/outros JEI addons pode duplicar páginas, sem alterar loot real. Não inferir drop chance final se conditions/contexto custom não forem interpretados.
- **Observações:** Mudança arquitetural importante: desde 1.11.0 o projeto deixou de manter built-in support para a maioria dos mods, exceto LootJS; suporte a tipos próprios deve vir do mod owner/plugin. Portanto “ALI instalado” NÃO garante interpretação completa de toda loot table modded. Falhas de exibição não equivalem a falhas de loot.
- **Procedência:** Modlist física 2026-09-07 + CurseForge oficial Advanced Loot Info 2.1.0 + changelogs 1.11.0/2.0.1/2.1.0 + Advanced Core Info 1.1.0 + guia gameplay/sistemas.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/advanced-loot-info
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — dossiê operacional completo de loot/trades observability, ACI→ALI→JEI pipeline, LootJS/plugin API, networking, reload e beta risks confirmado no QC global #7.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

> 🔎 **Escopo canônico.** Runtime físico: `AdvancedLootInfo-neoforge-1.21.1-2.1.0.jar`, release **beta**. Advanced Loot Info (ALI) é uma camada de **observabilidade**: lê/representa loot tables e villager trades em um recipe viewer. Ele não injeta loot, não muda chances e não cria trades.

## 1. Arquitetura do stack
O pipeline correto desta instância é:
1. o servidor/datapack/provider define a loot table ou trade real;
2. **ALI** interpreta a estrutura;
3. **Advanced Core Info (ACI)** fornece plugin discovery, tooltip trees e transporte servidor→cliente;
4. **JEI** apresenta as categorias/tooltips ao usuário.

O pack usa ACI 1.1.0 e JEI 19.53.0.425. EMI/REI são viewers suportados upstream, mas não aparecem top-level no snapshot físico atual.

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
