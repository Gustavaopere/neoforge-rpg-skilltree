# Ratatouille

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81cd8eacd715b6be6eb5
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Ratatouille
- **Arquivo JAR:** `create_ratatouille-1.21.1-1.4.0.jar`
- **Versão 1.21.1:** 1.4.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Comida, Automação, Tecnologia
- **Função:** Bridge culinário-industrial Create↔Farmer's Delight com Squeeze Basin, Mechanical Demolder, processamento/automação, fluidos e intermediários, além de conteúdo de oven, thresher, compostagem e irrigação.
- **Dependências:** Release 1.4.0 requer Farmer's Delight e Create 6.0.10+mc1.21.1. Pack físico: Create 6.0.10 e Farmer's Delight 1.3.4.
- **Sobreposição:** Integra concretamente Create 6.0.10 e Farmer's Delight 1.3.4. Pode criar rotas alternativas de processamento/food/farming; comparar recipes e custos reais, sem classificar o addon inteiro como duplicata.
- **Compatibilidade/Riscos:** Riscos centrais: Squeeze Basin duplicar/perder item/fluid/casing no ciclo do Mechanical Press; output overflow duplicar; Demolder duplicar mold entre belt/storage/right-click; Sequenced Assembly avançar duas vezes; recipe/tag reload stale; overlap de rotas com Create/Farmer's Delight.
- **Observações:** JAR físico `create_ratatouille-1.21.1-1.4.0.jar`, mod id `ratatouille`, runtime 1.4.0. Release oficial NeoForge 1.21.1, Client & Server, 10/07/2026. 1.4.0 corrige vários problemas do Squeeze Basin e melhora o Demolder para retirada manual de molds.
- **Procedência:** modlist.txt física atual de 08/09/2026 + publicação oficial Ratatouille 1.4.0 + repositório TeamStarfruit/Ratatouille, branch matching `1.21.1-create0.6`.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-ratatouille
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê completo 1.4.0; Squeeze Basin, Mechanical Demolder, molds, fluids/intermediários, oven/thresher/compost/irrigation, recipes, lifecycle e regressões catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> 🍲 **Identidade física confirmada:** `create_ratatouille-1.21.1-1.4.0.jar`, mod id `ratatouille`, runtime `1.4.0`, NeoForge 1.21.1. A release oficial 1.4.0 é Client & Server e requer **Farmer's Delight** + **Create 6.0.10+mc1.21.1**.

## 1. Papel e authority
Ratatouille é uma expansão industrial-culinária que conecta **Create** a **Farmer's Delight**. Ratatouille owns suas máquinas, fluidos/intermediários e recipes próprias. Create continua owner da cinética, Mechanical Press, belts e infraestrutura de processamento; Farmer's Delight continua owner de seus alimentos/ingredientes e semântica culinária base.

## 2. Squeeze Basin
O **Squeeze Basin** é uma superfície central da 1.4.0. O source matching da linha 1.21.1 confirma input inventory, output inventory, input fluid tank, item/fluid capabilities e integração direta com **Mechanical Press** colocado acima.
O processamento ocorre no ciclo do Press e consome input de item e/ou fluido antes de aceitar os outputs. Conservação de itens/fluidos é um gate de integridade.

## 3. Persistência do Squeeze Basin
Input e output inventories são serializados em NBT pelo block entity. O tanque usa comportamento de fluido do Create. Break/destroy também lida com drop dos inventários e, quando aplicável, do casing usado pela recipe.
Unload/restart não pode repetir o midpoint do Press, perder conteúdo ou duplicar output/casing.

## 4. Capabilities e automação
O Squeeze Basin registra capabilities NeoForge de **ItemHandler** e **FluidHandler**, aceita alimentação direta por belt e tenta ejetar o output para inventory compatível em posição/direção esperada.
Integrações externas podem inserir/extrair apenas conforme as regras expostas; input/output têm restrições diferentes. Pipes/hoppers/belts não devem obter acesso simétrico por inferência.

## 5. Squeezing recipes
O source confirma recipe type próprio de **Squeezing**. Matching considera o state do basin, inclusive casing quando a recipe exige. O recipe manager server-side é authority.
Como a 1.4.0 declara “many problems on Squeeze Basin” corrigidos, recipe start, midpoint, consumo e output são regression gates obrigatórios.

## 6. Mechanical Demolder
O **Mechanical Demolder** é um bloco cinético que implementa comportamento de pressing sobre belt. O source matching confirma recipe própria de **Demolding**, incluindo suporte a Sequenced Assembly.
Ele separa outputs classificados como mold do fluxo normal e armazena molds num inventário interno próprio.

## 7. Mold storage e retirada manual
A release 1.4.0 melhora explicitamente o Demolder para permitir **right-click e retirada dos molds armazenados**. O inventário de mold é serializado em NBT e expõe capability apenas para extração, não inserção externa.
Isso cria um boundary claro: molds gerados pela recipe pertencem ao storage interno do Demolder até serem extraídos/retirados; automação não deve criar uma segunda cópia no belt e no storage.

## 8. Sequenced Assembly
O Demolder procura primeiro recipe compatível de Sequenced Assembly e depois recipe Demolding comum. Como apenas um item é processado por ciclo (`canProcessInBulk = false` no source), integrações devem preservar essa atomicidade e não tratar um stack inteiro como uma única operação.

## 9. Oven e Oven Fan
A árvore matching da linha 1.21.1 contém **Oven** e **Oven Fan**, formando outra superfície de processamento culinário/industrial. Sem pin binário detalhado para todas as recipes, esta ficha não inventa tempos, heat levels ou outputs; recipes efetivamente carregadas permanecem authority.

## 10. Thresher
O **Thresher** aparece como conteúdo registrado da linha 1.21.1. É uma superfície de processamento de ingredientes/plantas própria do addon. Qualquer overlap com milling/crushing ou recipes de Farmer's Delight deve ser comparado por input/output real, não pelo nome da máquina.

## 11. Compost Tower
O source inclui **Compost Tower** e fluidos/intermediários relacionados a compostagem, incluindo `compost_residue_fluid` e `compost_tea`. Essa cadeia pertence ao Ratatouille; mods agrícolas externos permanecem owners de seus próprios fertilizantes/soil systems.

## 12. Irrigation Tower e Spreader
A linha 1.21.1 contém **Irrigation Tower** e **Spreader**, superfícies que potencialmente afetam agricultura/área. Alcance, targets e consumo efetivos devem ser lidos do runtime/config/recipe quando uma integração precisar deles; não inferir cobertura espacial apenas pelo nome.

## 13. Fluidos e intermediários
A árvore de resources registra conteúdos como `bio_gas`, `cake_batter`, `cocoa_liquor`, `compost_tea`, `egg_yolk`, `melon_juice_fluid`, `mince_meat` e outros intermediários. Eles são resources do addon e podem participar de chains com Create/Farmer's Delight.
Tags amplas e fluid equivalence precisam ser validadas para evitar aceitar provider sem semântica equivalente.

## 14. Frozen Block e demais processing surfaces
O branch matching também contém `frozen_block` e outros conteúdos auxiliares. Eles ampliam o pipeline industrial, mas esta auditoria não transforma nomes de resources em comportamento não comprovado. State/data reais e recipes são a authority.

## 15. Farmer's Delight no pack
O pack físico atual contém `FarmersDelight-1.21.1-1.3.4.jar`. Portanto a dependência/integração Farmer's Delight é concreta, não apenas suporte opcional. Ratatouille deve consumir ingredients/tags sem assumir ownership do conteúdo FD.

## 16. Create no pack
O pack usa Create 6.0.10, exatamente a linha mínima indicada pela release oficial 1.4.0 (`Create 6.0.10+mc1.21.1`). Isso alinha a dependência declarada, mas não substitui smoke-test da combinação completa com os demais addons Create.

## 17. Data, recipes e reload
Recipes/tags são data-driven e podem ser alteradas por datapacks/KubeJS. `/reload` deve invalidar recipe cache e máquinas em progresso de forma consistente. Squeeze Basin e Demolder não podem completar uma recipe removida usando stale reference sem política explícita do provider.

## 18. Client/server e multiplayer
Render/animations/recipe-viewer são client-facing. Inventory/fluid contents, recipe execution, casing consumption, mold storage e kinetic processing são server-authoritative.
Com dois jogadores/automação concorrente, um mesmo item, mold ou volume de fluido não pode ser extraído/processado duas vezes.

## 19. Lifecycle crítico
Testar Squeeze Basin durante Mechanical Press cycle, unload no midpoint, break durante recipe, output bloqueado, tank parcial e casing. No Demolder, testar belt stop/start, mold storage cheio, right-click extraction, capability extraction, unload/restart e Sequenced Assembly interrompida.

## 20. Riscos
1. Squeeze Basin duplica/perde input ou fluido.
2. Reload/restart repete processamento no midpoint do Press.
3. Casing é consumido duas vezes ou não é devolvido corretamente em break.
4. Output overflow é inserido duas vezes no inventory alvo.
5. Demolder duplica mold entre belt e storage interno.
6. Right-click e capability extraem o mesmo mold simultaneamente.
7. Sequenced Assembly avança etapa duas vezes.
8. Recipes Ratatouille sobrepõem rotas Create/FD com custo muito menor.
9. Tags de fluid/item aceitam provider semanticamente incompatível.
10. `/reload` mantém recipe state stale em máquina ativa.
11. Máquinas de área/agricultura produzem carga de tick ou overlap não intencional.

## 21. Matriz de testes
- [ ] Dedicated server inicia com Ratatouille 1.4.0 + Create 6.0.10 + Farmer's Delight 1.3.4.
- [ ] Squeeze Basin recebe item/fluid por capabilities/belt e consome exatamente uma vez.
- [ ] Mechanical Press interrompido/reiniciado não duplica recipe.
- [ ] Casing exigido é consumido/dropado conforme state real.
- [ ] Output bloqueado permanece no basin sem dupe/loss.
- [ ] Demolder processa um item por ciclo e armazena mold uma única vez.
- [ ] Right-click retira mold armazenado sem ghost item.
- [ ] Extração por capability não concorre com retirada manual.
- [ ] Sequenced Assembly progride exatamente uma etapa por operação válida.
- [ ] Restart/chunk unload preserva inventories e molds.
- [ ] `/reload` atualiza recipes/tags sem cache stale.
- [ ] Pipelines com Farmer's Delight não criam loop de recurso/custo indevido.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 22. Evidências e limites
A modlist física confirma JAR/mod id/runtime 1.4.0. A publicação oficial confirma NeoForge 1.21.1, Client & Server, dependências e fixes 1.4.0 de Squeeze Basin/Demolder. O repositório `TeamStarfruit/Ratatouille`, branch `1.21.1-create0.6`, confirma Squeeze Basin com inventories/fluid capability/Mechanical Press e Mechanical Demolder com mold storage, Demolding recipes e Sequenced Assembly. A árvore também confirma as demais famílias de conteúdo citadas; parâmetros não lidos permanecem fail-closed.

> 🔒 **Boundary canônico:** Ratatouille owns suas máquinas/recipes/intermediários; Create owns cinética/Press/belts; Farmer's Delight owns seus ingredientes. Integridade de Squeeze Basin e mold storage do Demolder é o principal gate da 1.4.0.
