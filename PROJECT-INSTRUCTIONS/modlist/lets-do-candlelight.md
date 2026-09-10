# [Let's Do] Candlelight

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db81efae22eb5529f473cd
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** [Let's Do] Candlelight
- **Arquivo JAR:** `letsdo-candlelight-neoforge-2.1.12.jar`
- **Versão 1.21.1:** 2.1.12
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Comida, QoL
- **Função:** Extensão de Farm & Charm para fine dining: meals multi-stage, potion/effect foods, cooking/tableware, decoração, Typewriter/letters e roupas cosméticas.
- **Dependências:** [Let's Do] Farm & Charm é required. Runtime físico: Farm & Charm 1.1.23; branch 2.1.12 também compila com Architectury/NeoForge e integração Curios.
- **Sobreposição:** Complementa Farm & Charm; não substitui o core agrícola/culinário. Effects e meals podem sobrepor perks/foods de outros mods e exigem deduplicação funcional.
- **Compatibilidade/Riscos:** Riscos: effect/container duplication, food BlockEntity state, written payload persistence, Curios/render drift, Farm & Charm API drift e automation races. 2.1.12 corrige crash ao colocar Small Painting.
- **Observações:** Source oficial `Let-s-Do-Collection/Candlelight` branch 1.21.1 declara exatamente 2.1.12. Changelog 2.1.x mostra correções materiais em food effects, remainder containers, letters e equip/render.
- **Procedência:** modlist.txt física atual + source oficial Candlelight branch 1.21.1 + arquivos de build, README e changelog oficiais da mesma linha.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/lets-do-candlelight
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — source 2.1.12 pinado; meals/effect foods, containers, tableware, letters/typewriter, Curios/render, lifecycle, risks e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

> 🕯️ **ESCOPO CANÔNICO.** Runtime físico: `letsdo-candlelight-neoforge-2.1.12.jar`, mod id `candlelight`, versão `2.1.12`. Candlelight é uma extensão culinária/decorativa de **[Let's Do] Farm & Charm**, com meals multi-stage, effect foods, utensílios/estações, tableware, roupas cosméticas e storytelling props.

## 1. Identidade e source pin
O repositório oficial `Let-s-Do-Collection/Candlelight`, branch `1.21.1`, declara `mod_version=2.1.12`, Minecraft 1.21.1, Architectury 13.0.8 e NeoForge 21.1.x. Essa branch corresponde à versão física instalada e é a referência principal desta ficha.

## 2. Dependência e ownership
O próprio README define Candlelight como extensão sofisticada de Farm & Charm e declara Farm & Charm requerido. Candlelight registra seu conteúdo/recipes/effects, mas o core agrícola/culinário compartilhado de Farm & Charm continua sendo provider quando o addon reutiliza seus blocos, food logic ou stations.

## 3. Meals e consumo por etapas
O addon adiciona refeições grandes preparadas em Frying Pan/Cooking Pot e um sistema de alimentos consumidos em múltiplas porções. Há pratos/placeable dishes que podem ser colocados e consumidos. Hunger, saturation e effects precisam ser calculados a partir do item/block entity real e aplicados uma única vez por porção.

## 4. Effect foods
Candlelight permite infundir refeições com potion effects e transferir efeitos aos pratos. O histórico 2.1.x mostra correções específicas em `LargeCookingPot` e food BlockEntities para garantir que efeitos dos ingredientes sejam gravados no output. Esse é state de gameplay e precisa permanecer server-authoritative.

## 5. Containers e remainders
A versão 2.1.8 corrigiu bottles, bowls e buckets que não eram devolvidos após cozinhar. Recipes/automação devem tratar corretamente container item e remainder para evitar dupe ou perda. Em integração com KubeJS/JEI, a representação visual da recipe não substitui o consumo real do inventory provider.

## 6. Tableware e apresentação
O mod inclui table settings, pratos, copos e wine glasses que podem armazenar/mostrar conteúdo, além de decoração de jantar. Food colocado em table sets já teve regressão de render em versões anteriores. Esse state visual não deve ser usado por quests como prova de consumo ou preparo.

## 7. Typewriter, letters e Heart
Candlelight possui Typewriter, envelopes/letters e Heart block gravável. O histórico registra correções de payload de carta e GUI no NeoForge. Written content/payload precisa persistir em item/block data sem duplicar ou perder texto após abrir, mover ou reiniciar.

## 8. Roupas e Curios/render
A branch compila integração com Curios e o mod adiciona Cooking Hat, Necktie, Rose Crown, Suit/Dress e outros cosméticos. O changelog registra crashes de equip/render corrigidos entre 2.1.1 e 2.1.9. Equip state e atributos/effects devem vir do servidor/Curios; model/texture é apresentação cliente.

## 9. Efeitos Refreshed e Well Served
Desde 2.1.6, Candlelight adiciona dois efeitos alimentares: **Refreshed**, com bonus harvest limitado em determinadas crops, e **Well Served**, que impede hunger de cair abaixo de um mínimo. São effects de gameplay e precisam ser aplicados/deduplicados server-side; não devem double-stack com perks próprios que implementem o mesmo resultado.

## 10. Changelog específico 2.1.12
A release 2.1.12 corrige crash ao colocar **Small Painting** e um problema de renderização de Hat no Fabric. Para o runtime NeoForge, o crash do Small Painting é diretamente relevante; a correção Fabric fica registrada apenas como histórico cross-loader.

## 11. Lifecycle e multiplayer
Validar cooking completion, food block placement/break, effect serialization, letters/typewriter, equip/unequip, chunk unload e resource reload. Em MP, dois jogadores interagindo com o mesmo meal/table/container não podem consumir/gerar output duas vezes.

## 12. Riscos técnicos
1. **Effect duplication** entre ingredient effects, food block e custom perks.
2. **Container duplication/loss** em recipes automatizadas.
3. **BlockEntity mismatch** em food blocks ou cooking stations.
4. **Written payload loss/dupe** em letters/typewriter/Heart.
5. **Curios/render drift** em roupas cosméticas.
6. **Farm & Charm version drift**: addon depende de APIs/semântica do core.
7. **Automation race** quando Create/KubeJS interagem com inventories enquanto jogador usa a estação.

## 13. Matriz de testes
- [ ] Dedicated server inicia com Candlelight 2.1.12 + Farm & Charm 1.1.23.
- [ ] Meals multi-stage consomem uma porção por interação e aplicam hunger/saturation uma vez.
- [ ] Ingredient potion effects chegam ao output exatamente uma vez.
- [ ] Bottles/bowls/buckets retornam somente quando a recipe prevê remainder.
- [ ] Small Painting coloca sem crash.
- [ ] Letters preservam texto/sender/recipient após abrir, relog e restart.
- [ ] Typewriter/Heart persistem conteúdo sem duplicação.
- [ ] Equip/unequip de roupas não acumula modifiers nem quebra render.
- [ ] Dois jogadores usando a mesma food block/station não duplicam output.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 14. Evidências e limites
- modlist física: Candlelight 2.1.12;
- source oficial branch 1.21.1: version pin e dependências de build;
- README oficial: meals, effect foods, decoração, Typewriter/envelopes e roupas;
- CHANGELOG oficial: regressões/fixes 2.1.x e correções específicas de 2.1.12.
Nenhuma receita ou item não localizado nessas fontes foi inventado.
