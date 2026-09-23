# Create: Additional Logistics

> **Autoridade física atual — 23/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#168**: JAR `createadditionallogistics-1.21.1-1.4.5.jar`, mod id `createadditionallogistics`, runtime `1.4.5`, SHA-1 `6acf4d30516b6bf66928aa5d79d6bac6283de44e`.

## Propriedades do registro

- **Mod:** Create: Additional Logistics
- **Arquivo JAR:** createadditionallogistics-1.21.1-1.4.5.jar
- **Versão 1.21.1:** 1.4.5
- **Categoria:** Tecnologia, Automação
- **Função:** Expande a logística do Create com promise limits, additional stock, regex em package addresses, Cash Register/Sales Ledger, Package Accelerator/Editor, monitoramento de trens e blocos lazy orientados a desempenho.
- **Dependências:** Create 6.0.10 no pack físico, sobre NeoForge 21.1.250. Integrações adicionais são condicionais; Create: Factory Logistics não aparece como JAR top-level atual, portanto o fix específico 1.4.5 continua registrado apenas como evidência upstream, não integração ativa confirmada.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Riscos: regex/package matching custoso ou inválido, promises/restock gerando excesso, Package Editor criando loops de endereço, purchase state inconsistente, peripheral remote-write indevido e drift com Create 6.0.10. 1.4.5 corrige exception leakage de regex e incompatibilidade de seat-height patches.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-additional-logistics
- **Procedência:** modlist.txt física atual de 16/09/2026 + runtime `createadditionallogistics` 1.4.5 + CurseForge oficial revalidado em 20/09/2026; changelog exato preservado para seat-height patch compatibility, Factory Logistics purchase fix/limitação de currency conversion e regex validation.
- **Observações:** JAR físico `createadditionallogistics-1.21.1-1.4.5.jar`, mod id `createadditionallogistics`, runtime 1.4.5. Release oficial NeoForge 1.21.1 de 14/01/2026.
- **Atualização/Status:** REVALIDADO EM 20/09/2026 — lote físico #167: createadditionallogistics-1.21.1-1.4.5.jar / 1.4.5 confirmados; regex/package routing, commerce, promise/additional-stock controls e fixes 1.4.5 de seat-height, Factory Logistics e regex validation permanecem atuais.
- **Decisão:** Sem decisão
- **Sobreposição:** Complementa logistics/packages/shops do Create; pode cruzar com outros addons de logística e commerce. Sobreposição deve ser avaliada por block/route/state concreto, não por categoria.

> 📦 **ESCOPO CANÔNICO.** Runtime físico: `createadditionallogistics-1.21.1-1.4.5.jar`, mod id `createadditionallogistics`, versão `1.4.5`. É uma extensão Client & Server da logística/package network do Create; não é um storage network independente.

## 1. Melhorias de logística
O addon adiciona **Promise Limits** aos Factory Gauges para limitar promessas simultâneas e **Additional Stock** aos Restocker Gauges para solicitar lote extra quando o estoque cai abaixo do alvo. Isso altera scheduling/quantidade, não a authority de inventories externas.
## 2. Package addresses e regex
Endereços podem usar regex além de globs. A implementação publica salvaguardas contra catastrophic backtracking, repetition excessiva e backreferences; matchers compilados são cacheados para reduzir custo.
A 1.4.5 corrige situações em que falhas de validação de regex vazavam exceptions e podiam causar crash. Qualquer script/config que gere endereços dinamicamente deve validar compile e limitar complexidade.
## 3. Cash Register e Sales Ledger
Cash Register funciona como alternativa de comércio ao Stock Ticker e registra vendas para consulta via Sales Ledger. O ledger é estado de histórico/comércio do addon; uma integração de quest não deve inferir venda concluída apenas por GUI aberta ou item selecionado.
## 4. Package Accelerator e Editor
Package Accelerator acelera Packager conforme RPM e exige velocidade mínima documentada. Package Editor reescreve endereços de packages existentes com regras/glob/regex e capture groups.
Risco principal: package entrar em ciclo, receber endereço impossível ou ser processado duas vezes em redes concorrentes.
## 5. Train Network Monitor / CC:Tweaked
O projeto expõe um peripheral de monitoramento de rede ferroviária para CC:Tweaked, com dados de trains, stations e packages. Remote read/write é uma superfície sensível e não deve ser presumida ativa se estiver desabilitada por padrão/config.
Scripts externos devem tratar o servidor/Create network como authority e não cachear estado indefinidamente.
## 6. Seats e performance blocks
Há Short/Tall Seats e ajustes para stock keepers permanecerem sentados. Lazy Shafts/Flexible Lazy Shafts/Lazy Cogs existem como variantes orientadas a reduzir custo visual/kinetic em situações específicas e podem ser encased.
A 1.4.5 também corrige incompatibilidade com mods que patchavam a mesma rotina do Create usada para altura da entidade em seats.
## 7. Fix específico Factory Logistics
O changelog 1.4.5 corrige purchases que não enviavam itens quando **Create: Factory Logistics** estava instalado, com limitação de currency conversion nessa combinação. Esse mod-alvo não aparece como top-level na modlist física atual; portanto esta ficha registra o fix como evidência upstream, não como integração runtime ativa.
## 8. Lifecycle e multiplayer
Validar package creation, address parse/cache, stock promise, purchase settlement, train-network polling, chunk unload, station rename, server restart e dois jogadores comprando/requisitando simultaneamente. Mutação econômica precisa ser exactly-once e server-authoritative.
## 9. Riscos
1. Regex patológica ou inválida em package address.
2. Promise/Additional Stock multiplicando pedidos indevidamente.
3. Package Editor criando loops ou destinos inalcançáveis.
4. Sales Ledger divergindo da transação realmente liquidada.
5. Peripheral expondo mutation além da política pretendida.
6. Create update alterando internals de package/seat/kinetic behavior.
## 10. Boundary para quests/perks
Compra, entrega ou restock só devem contar quando existir settlement causal confirmado. Não conceder progresso por package visto, polling de monitor, GUI aberta ou cache de endereço.
## 11. Matriz de testes
- [ ] Dedicated server inicia com Additional Logistics 1.4.5 + Create 6.0.10.
- [ ] Promise Limits respeitam limite sem starvation ou duplicação.
- [ ] Additional Stock solicita quantidade esperada uma única vez.
- [ ] Regex válida/ inválida não causa crash nem backtracking severo.
- [ ] Package Editor preserva payload e altera apenas o endereço pretendido.
- [ ] Cash Register e Sales Ledger convergem em compras simultâneas.
- [ ] Package Accelerator escala com RPM sem duplicar packages.
- [ ] Lazy kinetic blocks mantêm rede funcional após reload/restart.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 12. Evidências e limitação
- Modlist física: `createadditionallogistics-1.21.1-1.4.5.jar`.
- CurseForge/Modrinth oficiais: Release 1.4.5 NeoForge 1.21.1, features e fixes acima.
- Config real e internals de package/peripheral não foram extraídos nesta etapa; integrações programáticas devem verificar API/resources reais.
