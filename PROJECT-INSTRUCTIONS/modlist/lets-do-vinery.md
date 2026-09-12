# [Let's Do] Vinery

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db81e19898f84a4f0bb667
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** [Let's Do] Vinery
- **Arquivo JAR:** `letsdo-vinery-neoforge-1.5.3.jar`
- **Versão 1.21.1:** 1.5.3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Comida, Exploração
- **Tipo de conteúdo:** Mod
- **Função:** Viticultura e produção de vinho: uvas/vinhas, Apple Press, Fermenting Barrel, envelhecimento por garrafa, bebidas/efeitos, cherry wood e decoração temática.
- **Dependências:** Architectury API é dependência obrigatória publicada; runtime físico contém Architectury 13.0.11. Cloth Config aparece no ambiente de build/source, mas não foi promovido a hard dependency sem metadata de runtime correspondente.
- **Sobreposição:** Cruza o domínio de bebidas com Brewery/HerbalBrews e outros food mods, mas Vinery permanece authority de seus vinhos, aging, recipes, blocks e payloads próprios.
- **Compatibilidade/Riscos:** Beta 1.5.3. Riscos: aging client/server drift, pre-aged bottles, duplicação/perda em stations/Wine Boxes, payload de vinho após relog, tags/trades inválidos, avanço duplicado e concorrência multiplayer.
- **Observações:** Release 1.5.3 Beta. Source exato converge com o JAR físico. A 1.5.3 move o aging para state por garrafa com valores do servidor e corrige Wine Box com garrafas não-Vinery.
- **Procedência:** modlist.txt física atual + release oficial Vinery 1.5.3 NeoForge 1.21.1 + source oficial Let-s-Do-Collection/Vinery branch 1.21.1, que declara exatamente 1.5.3, + README e changelog da mesma linha.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/lets-do-vinery
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — source 1.5.3 pinado; viticulture/wine pipeline, aging server-authoritative, stations, Wine Boxes, trades/tags, lifecycle, multiplayer, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

> 🍷 **ESCOPO CANÔNICO.** Runtime físico: `letsdo-vinery-neoforge-1.5.3.jar`, mod id `vinery`, versão `1.5.3`. É o módulo de viticultura/vinhos da coleção Let's Do, com cultivo de uvas, prensagem, fermentação, envelhecimento por garrafa, bebidas/efeitos e conteúdo decorativo associado.

## 1. Identidade e source pin
A modlist física confirma Vinery 1.5.3. A branch oficial `Let-s-Do-Collection/Vinery` para 1.21.1 declara exatamente `mod_version=1.5.3`, Minecraft 1.21.1 e NeoForge 21.1.x. A publicação oficial classifica a build como **Beta** para NeoForge 1.21.1. Esse source correspondente é a referência principal desta ficha.

## 2. Papel no modpack
Vinery implementa um pipeline próprio de campo até bebida: obtenção de grapes, cultivo de vines, processamento no Apple Press/Fermenting Barrel, armazenamento e envelhecimento de vinho. O mod também adiciona cherry trees/wood, lattices, furniture, displays e vestimenta temática. Conteúdo visual não deve ser confundido com authority do processo de produção.

## 3. Cultivo e matéria-prima
O README oficial documenta wild grapes, vine stems e cherry trees. Growth, harvest e drops pertencem ao state real de blocos/loot do servidor. A 1.5.3 corrige especificamente Dark Cherry Saplings que faziam crescer Dark Cherry Trees de forma incorreta em build anterior; esse comportamento entra como teste de regressão.

## 4. Apple Press e Fermenting Barrel
Apple Press e Fermenting Barrel compõem o processamento central. Inserção de ingredientes, progressão, consumo de containers e geração de output precisam ocorrer exatamente uma vez. Scripts/KubeJS ou automação Create não devem inferir conclusão pela animação ou pelo recipe viewer; o inventory/output servidor é a fonte causal.

## 5. Aging por garrafa — mudança crítica 1.5.3
A 1.5.3 altera/corrige o envelhecimento para ser armazenado **por garrafa**, usando valores do servidor. O objetivo explícito do fix é impedir vinhos já nascendo envelhecidos e impedir que aging/efeitos dependam do cliente em servidores. Portanto idade/ano/efeitos de vinho são state server-authoritative e devem sobreviver a relog, restart, troca de dimensão e transferência de inventário.

## 6. Wine Boxes e payload
Wine Boxes armazenam garrafas e já tiveram um crash quando recebiam garrafas que não pertenciam ao Vinery, podendo impedir o carregamento do mundo. A 1.5.3 corrige esse caso. Qualquer storage/automação precisa validar item compatível sem corromper BlockEntity/NBT/data components e sem perder ou duplicar o payload individual de cada vinho.

## 7. Bebidas e efeitos
Vinhos podem carregar efeitos ligados ao produto/aging. O servidor deve decidir consumo, efeito e duração; tooltip, cor, modelo ou idade exibida são apresentação. Outros mods de bebida não devem aplicar uma segunda cópia do mesmo efeito apenas por detectar item/tag equivalente.

## 8. Tags, compostagem e trades
A 1.5.3 torna itens Vinery compostáveis e remove IDs depreciados/inválidos da configuração padrão de trades. O histórico 1.5.x também inclui correções de tags. Datapacks e configs do pack devem usar IDs/tags válidos para a build física; entradas ausentes não devem ser silenciosamente transformadas em recompensa alternativa.

## 9. Advancements e comando `/wine`
A 1.5.3 corrige critérios/progresso de advancements relacionados ao mod e define o comando `/wine` com permissão de operador nível 2. Advancements devem ser creditados pelo evento real no servidor. O comando administrativo não deve ser exposto a jogadores sem o nível de permissão previsto.

## 10. Client / server boundary
Cultivo, recipes, inventories, aging, consumo, efeitos, trades e advancements pertencem ao servidor. Occlusion de lattice, modelos, render de armor e demais apresentação pertencem ao cliente. A 1.5.3 também corrige occlusion de lattices transparentes; isso é regressão visual, não state de gameplay.

## 11. Compatibilidade no stack
Architectury API é dependência obrigatória publicada e está fisicamente presente em 13.0.11. Brewery, HerbalBrews e outros food/drink mods compartilham domínio temático, mas não ownership. Recipes/tags podem interoperar quando semanticamente compatíveis; aging e payload de vinho continuam exclusivos de Vinery.

## 12. Lifecycle e multiplayer
Validar cold boot, chunk unload, save/restart, transferência de garrafas entre inventários, Wine Box break/place, uso simultâneo de stations e consumo em multiplayer. Dois jogadores ou automação concorrente não podem produzir, envelhecer, retirar ou consumir a mesma unidade duas vezes.

## 13. Riscos técnicos
1. **Aging drift** entre cliente e servidor ou entre relógios de mundo.
2. **Pre-aged wine** por data inicial incorreta.
3. **Payload loss/dupe** ao armazenar/retirar garrafas.
4. **Wine Box crash/corruption** com item incompatível.
5. **Recipe/output duplication** em automação concorrente.
6. **Trade/tag stale IDs** após reload/update.
7. **Effect stacking** com outros sistemas de bebidas/perks.
8. **Beta drift:** mudanças futuras podem alterar schema de bottle aging.

## 14. Matriz de testes
- [ ] Dedicated server inicia com Vinery 1.5.3 + Architectury 13.0.11.
- [ ] Grapes/vines e Dark Cherry Saplings produzem growth/drop esperado.
- [ ] Apple Press/Fermenting Barrel consomem inputs e geram output uma vez.
- [ ] Garrafa recém-criada não nasce pré-envelhecida.
- [ ] Aging permanece igual após relog/restart e entre clientes diferentes.
- [ ] Wine Box aceita garrafas válidas e rejeita item incompatível sem crash/corruption.
- [ ] Quebrar/recolocar Wine Box preserva apenas o state suportado, sem dupe/loss.
- [ ] Trades não referenciam IDs removidos.
- [ ] Advancements disparam exatamente uma vez por critério real.
- [ ] `/wine` respeita nível de permissão publicado.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 15. Evidências e limites
Foram usados a modlist física, a release oficial 1.5.3, o source oficial 1.21.1 que declara a mesma versão, README e changelog 1.5.x. Config efetiva de balanceamento, recipes modificados por scripts do pack e valores concretos de aging não foram inferidos sem leitura runtime; devem ser validados no ambiente do pack.
