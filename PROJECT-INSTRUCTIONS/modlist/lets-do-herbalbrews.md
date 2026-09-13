# [Let's Do] HerbalBrews

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db8195bf4ec2422003dc83
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** [Let's Do] HerbalBrews
- **Arquivo JAR:** `letsdo-herbalbrews-neoforge-1.1.3.jar`
- **Versão 1.21.1:** 1.1.3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Comida
- **Função:** Pipeline de chás/cafés: plantas e coleta, secagem de folhas, Tea/Copper Kettles, Brewing Cauldron, bebidas com efeitos, Jug e acessórios temáticos.
- **Dependências:** NeoForge 1.21.1 + Architectury + Cloth Config. Runtime físico contém Cloth Config 15.0.140; interop culinário com outros Let's Do deve ser tratado por recipes/tags do provider.
- **Sobreposição:** Cruza domínio de bebidas/effects com Farm & Charm/Candlelight e outros food mods; não substitui seus providers. Evitar duplicação de recipes/effects por scripts.
- **Compatibilidade/Riscos:** 1.1.3 corrige duplicate food effects, tooltip/duração divergente e server crash de drying tick sem DRYING. Histórico 1.1.1 também corrige TeaCup client-only crash e potion payload em break.
- **Observações:** Source oficial `Let-s-Do-Collection/HerbalBrews` branch 1.21.1 declara exatamente 1.1.3. State de effect server-side é autoridade; tooltip é representação.
- **Procedência:** modlist.txt física atual + source oficial HerbalBrews branch 1.21.1 + arquivos de build, README e changelog oficiais da mesma linha.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/lets-do-herbalbrews
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — source 1.1.3 pinado; plants/loot, drying ticks, kettles/brewing, potion/effect authority, dedicated-server boundary, lifecycle, risks e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

> 🍵 **ESCOPO CANÔNICO.** Runtime físico: `letsdo-herbalbrews-neoforge-1.1.3.jar`, mod id `herbalbrews`, versão `1.1.3`. É o módulo Let's Do de coleta/secagem de folhas, chás/cafés, Brewing Cauldron, bebidas com efeitos e objetos decorativos/funcionais associados.

## 1. Identidade e source pin
O source oficial `Let-s-Do-Collection/HerbalBrews`, branch `1.21.1`, declara exatamente `mod_version=1.1.3`, Minecraft 1.21.1, Architectury 13.0.8, NeoForge 21.1.x e Cloth Config. O JAR físico converge com essa versão.

## 2. Papel no modpack
O README oficial define o ciclo principal como explorar biomas por tea plants, coletar folhas, secá-las e então preparar tea/coffee. O addon adiciona um pipeline próprio de ingredientes→secagem→brewing, além de efeitos e decoração. Esses stages devem permanecer causalmente separados para quests e automação.

## 3. Plantas e coleta
Wild coffee, rooibos, yerba mate, hibiscus e lavender possuem loot próprio. A versão 1.1.2 corrigiu loot com Shears/Silk Touch, evidenciando que tool context altera o resultado. Scripts não devem normalizar drops por item name sem respeitar loot table/tag/provider.

## 4. Drying lifecycle
Folhas são secas por state de bloco/tick. A versão 1.1.3 corrige um server crash quando o drying tick executava em BlockState sem a propriedade `DRYING`. Isso torna block replacement, chunk reload e scheduler/tick order pontos críticos de QA. Um tick deve sempre revalidar o state atual antes de acessar propriedades esperadas.

## 5. Tea Kettles e brewing
O mod possui Tea Kettle e Copper Tea Kettle para preparar bebidas, além de Brewing Cauldron para combinações de poções. Recipe inputs, fluid/container state e output effects pertencem ao servidor/provider. JEI/tooltip representam a recipe, mas não são authority do resultado.

## 6. Efeitos de bebidas
O README confirma teas/coffees com efeitos benéficos, inclusive efeitos aplicados a jogadores em raio para certas bebidas. A 1.1.3 corrigiu **aplicação duplicada de food-based effects** no consumo e corrigiu tooltip de duração que divergia da duração realmente aplicada. Portanto o state causal é o effect server-side, nunca o tooltip.

## 7. Tea Cup / dedicated server boundary
A 1.1.1 removeu referências client-only de TeaCupBlock que causavam crash em dedicated server. Isso prova um histórico concreto de side leakage já corrigido. Render/tooltip/models ficam cliente; consumo, potion contents, block destruction e effect application permanecem server-authoritative.

## 8. Potion contents e block destruction
A 1.1.1 corrigiu o drop de potion contents ao destruir blocos fora de Creative. Bebida colocada em bloco deve preservar exatamente o payload previsto e liberar/consumir conteúdo uma única vez. Break por jogador, automação ou explosão precisa ser regressado para dupe/loss.

## 9. Jug, hats e efeitos defensivos
O README documenta Jug que pode conter até três chás e também ser usado uma vez como arma, além de Top Hat/Witch Hat que reduzem dano mágico. Inventory/payload do Jug e efeitos defensivos precisam ser aplicados pelo servidor; render do acessório é apresentação.

## 10. Config e interop
Cloth Config está fisicamente presente no pack. O addon compartilha domínio culinário/effects com Farm & Charm, Candlelight e outros Let's Do, além de Farmer's Delight. Compatibilidade deve ser coordenada por tags/recipes/effects, não por duplicação indiscriminada de outputs.

## 11. Lifecycle e persistência
Validar plant/block growth, drying ticks, kettle/cauldron state, drink consumption, potion payload, placed tea destruction, Jug contents e equip/unequip de hats. Reload/restart não deve reiniciar secagem de modo duplicado nem aplicar effect novamente sem nova ação causal.

## 12. Riscos técnicos
1. **Effect duplication** — regressão explicitamente corrigida em 1.1.3.
2. **Tooltip/state divergence** para duração de potion effects.
3. **Invalid drying state crash** após block replacement/unload.
4. **Client class leakage** no dedicated server.
5. **Potion payload dupe/loss** em block destruction.
6. **Recipe/tag overlap** com outros food/drink mods.
7. **Area-effect double application** em multiplayer.

## 13. Matriz de testes
- [ ] Dedicated server inicia com HerbalBrews 1.1.3.
- [ ] Wild plants respeitam loot com ferramenta/contexto esperado.
- [ ] Drying tick sobre state substituído não crasha nem cria item fantasma.
- [ ] Kettle/Copper Kettle consomem inputs e produzem output uma vez.
- [ ] Brewing Cauldron preserva potion contents esperados.
- [ ] Drink aplica cada food/potion effect exatamente uma vez.
- [ ] Tooltip de duração coincide com effect server-side.
- [ ] Bebida colocada e quebrada devolve conteúdo sem dupe/loss.
- [ ] Jug preserva até seu payload válido após relog/restart.
- [ ] Area effects não double-stack no mesmo tick/player.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 14. Evidências e limites
- modlist física: HerbalBrews 1.1.3 e Cloth Config 15.0.140;
- source oficial branch 1.21.1: version pin/dependencies;
- README oficial: plants, drying, kettles, Brewing Cauldron, effects, Jug e hats;
- CHANGELOG 1.1.1–1.1.3: dedicated-server, potion payload, loot, drying state, effect duplication e tooltip duration.
Nenhum efeito/recipe específico não confirmado pelas fontes foi inventado.
