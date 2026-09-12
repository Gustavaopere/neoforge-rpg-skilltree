# Apotheotic Creation

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81aea99fed44033f090d
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Apotheotic Creation
- **Arquivo JAR:** `apotheoticcreation-2.0.0.jar`
- **Versão 1.21.1:** 2.0.0
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Compat
- **Função:** Bridge Create ↔ Apotheosis que permite definir/usar Create Attribute Filters para propriedades de loot RPG do Apotheosis, especialmente rarities e affixes. A partir da linha 2.0.0 suporta Create 6. Não adiciona novos affixes, gems, sockets, World Tiers ou buffs de máquinas.
- **Dependências:** Create 6.0.10 + Apotheosis 8.8.0/stack Apothic atual. A bridge só traduz rarity/affix para Create Attribute Filters; não substitui providers de affix/gem/máquina.
- **Sobreposição:** Complementar a Apokinetics. Apotheotic Creation trata **classificação/filtragem de gear Apothic**; Apokinetics trata **sockets/Machine Gems e buffs industriais em máquinas Create**. Não são duplicatas.
- **Compatibilidade/Riscos:** Escopo estreito: a fonte oficial diz literalmente que o addon permite definir Create attribute filters para rarities e affixes do Apotheosis. Routing em Brass Tunnels/Smart Observers depende do comportamento normal dos Attribute Filters no Create; não inventar hook extra. Testar itens com vários affixes/rarities e componentes complexos, whitelist/blacklist, reload e automação server-side.
- **Observações:** O guia do projeto cita Smart Observers/Brass Tunnels como rotas que se beneficiam do filtro; upstream define o contrato em torno de Attribute Filters. Tratar Observer/Tunnel como comportamento downstream do Create até teste, não como API separada deste addon.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Apotheotic Creation 2.0.0 e fontes já auditadas no dossiê. Reconciliação final: JAR/runtime permanecem exatamente `apotheoticcreation-2.0.0.jar` / `2.0.0`; sem divergência física.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/apotheotic-creation
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #31: `apotheoticcreation-2.0.0.jar` / `2.0.0` conferidos contra a modlist atual; corpo técnico, decisão e estado preservados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

> 🔎 **Escopo canônico.** Runtime físico: `apotheoticcreation-2.0.0.jar`. O upstream define este addon como uma bridge simples para **Create Attribute Filters reconhecerem rarities e affixes do Apotheosis**. Não atribuir a ele funções de máquina, socket, gem ou progressão que pertencem a outros mods.

## 1. Problema que o addon resolve
Create consegue filtrar itens por propriedades que seu sistema de Attribute Filter entende. Gear do Apotheosis possui metadados/data components de **rarity/affixes** que um filtro Create puro não necessariamente interpreta como categoria útil para automação.

Apotheotic Creation acrescenta a tradução necessária para que o player possa construir filtros baseados nessas propriedades do ecossistema Apotheosis.

## 2. Contrato funcional confirmado upstream
A descrição oficial resume o escopo de forma extremamente estreita: o addon permite **definir Create attribute filters para Apotheosis rarities e affixes**.

Isso é a authority. Consequências logísticas — por exemplo usar esse Attribute Filter em funnel, tunnel, observer ou outra máquina Create que aceite o filtro — vêm do comportamento normal do Create e precisam ser testadas na versão instalada.

## 3. Rarity filtering
O filtro pode distinguir gear por **rarity** do Apotheosis. Isso permite rotas como:
- separar loot comum de loot raro;
- direcionar gear de alta rarity para armazenamento/avaliação;
- enviar gear de baixa rarity para salvaging;
- bloquear determinados tiers de item em linhas de processamento.

A regra concreta deve usar o filtro/data reconhecido, não nome traduzido ou cor visual.

## 4. Affix filtering
Também é possível filtrar por **affixes**, permitindo automações mais semânticas, por exemplo separar gear que contém determinado affix de gear que não contém.

Quando um item possui múltiplos affixes, testar matching e combinação de condições no Attribute Filter; não presumir AND/OR sem verificar a UI/comportamento do Create atual.

## 5. Create 6
A linha **2.0.0+** foi publicada para compatibilidade com **Create 6**. O pack instala Create 6.0.10, portanto esta é a linha arquitetural correta para a instância.

Se Create for atualizado, este addon deve ser revalidado porque filters/data components são superfícies sensíveis a API/version changes.

## 6. O que o addon NÃO faz
- Não cria affixes.
- Não cria rarities.
- Não cria gems.
- Não cria sockets.
- Não altera World Tiers.
- Não cria Reforging/Salvaging/Augmenting.
- Não buffa máquinas Create.
- Não adiciona Machine Gems.
- Não substitui Apokinetics.

Sua identidade é **metadata-aware filtering**.

## 7. Relação com Create logistics
O guia interno do projeto cita **Attribute Filters, Smart Observers, Brass Tunnels e outras rotas de triagem**. A interpretação correta é:
- Apotheotic Creation ensina/estende o **Attribute Filter** para entender Apotheosis;
- qualquer block Create que já consome esse filtro pode então se beneficiar.

Não registrar Smart Observer ou Brass Tunnel como hook independente do addon sem source/runtime que comprove algo além disso.

## 8. Dependências operacionais
### Create
Provider do Attribute Filter e da logística. Runtime do pack: **Create 6.0.10**.

### Apotheosis
Provider de rarities/affixes/gear data. Runtime do pack: **Apotheosis 8.8.0**.

### Apothic modules
Attributes/Enchanting/Spawners existem no pack como módulos separados, mas esta bridge não deve ser descrita como se dependesse de cada feature deles individualmente sem metadata/source específico.

## 9. Relação com Apokinetics
### Apotheotic Creation
Pergunta: “**que tipo de gear Apothic é este item?**” e torna a resposta utilizável por filtros Create.

### Apokinetics
Pergunta: “**como uma gem Apothic modifica esta máquina Create?**” e adiciona sockets/upgrades industriais.

Os dois são complementares.

## 10. Relação com Apotheosis 8.8.0
Apotheosis 8.8.0 possui affixes/rarities data-driven e itens com components complexos. Isso implica que o bridge deve ser testado após:
- rarity custom via datapack;
- affix custom;
- item reforged/augmented;
- gem socketed;
- Sigil of Malice/Supremacy alterando power de affix.

O filtro não deve perder a classificação apenas porque outros components do stack mudaram.

## 11. Riscos de automação
1. **Match incorreto:** item raro enviado para salvage por filtro falhando.
2. **Custom rarity/affix:** datapack novo pode não ser reconhecido como esperado.
3. **Display vs ID:** automação baseada em nome visual é frágil.
4. **Múltiplos affixes:** sem teste, combinação de condições pode produzir seleção ampla demais.
5. **Mutation do item:** reforging/augmenting pode mudar a propriedade depois que ele já entrou numa rota.
6. **Reload:** filtros persistidos precisam continuar válidos após datapack reload/server restart.
7. **Create addon logistics:** máquinas de terceiros que imitam Filter behavior podem não consumir exatamente a mesma API.

## 12. Matriz de validação
1. Criar gear Apotheosis em várias rarities.
2. Criar Attribute Filter por rarity e verificar match positivo/negativo.
3. Repetir para affix específico.
4. Item com 2+ affixes: testar matching.
5. Whitelist e blacklist.
6. Gear com gem/socket.
7. Gear depois de Reforging Table.
8. Gear depois de Augmenting Table.
9. Gear depois de Sigil of Malice 8.8.0.
10. Custom rarity/affix via datapack de teste.
11. Brass Funnel/Tunnel usando o filter.
12. Smart Observer usando o filter quando o comportamento do Create permitir.
13. Belt routing com centenas de gear stacks variados.
14. Server restart/reload: filter salvo continua funcional.
15. Multiplayer/dedicated server: servidor é authority do routing.
16. Comparar com Apokinetics para garantir que nenhum socket de máquina é atribuído a esta bridge.

## 13. Regras para outros chats
- Usar este mod quando a tarefa for **classificar loot Apothic em logística Create**.
- Não usar como provider de stats/affixes/gems.
- Não dizer “Smart Observer support” como feature própria sem explicar que é downstream do Attribute Filter.
- Se um filtro não reconhece um novo affix, verificar data/ID/versão antes de criar workaround em código.

## 14. Fontes e confiança
**Authority física:** modlist 07/09/2026.

**Upstream:** [CurseForge — Apotheotic Creation](https://www.curseforge.com/minecraft/mc-mods/apotheotic-creation), release 2.0.0/Create 6.

**Fonte interna:** guia completo de magia, que descreve a consequência logística da integração em Attribute Filters/Smart Observers/Brass Tunnels.

**Confiança:** muito alta para o escopo do addon porque o upstream o define explicitamente como bridge de Attribute Filters para rarities/affixes. Qualquer comportamento em máquina Create específica deve ser validado como consumidor downstream.
