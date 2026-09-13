# Create: Deep Dark

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db814e87d9ecdbba8476bf
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Deep Dark
- **Arquivo JAR:** `create_deep_dark-3.0.2-neoforge-1.21.1.jar`
- **Versão 1.21.1:** 3.0.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, RPG, Exploração
- **Função:** Expansão end-game Create↔Deep Dark centrada em Echo: materiais, equipamento, loot do Warden, Echo Cake/superheating, Sculk processing, trades e recipes Create.
- **Dependências:** Create 6.0.10 físico. Release oficial requer Create; JEI é recomendado, não obrigatório.
- **Sobreposição:** Interage concretamente com Create: Fantasizing Again 1.2.0-b3, que possui copy recipe de Echo Shard, podendo alterar a progressão Echo. Não é duplicata integral; avaliar custo/loops por recipe real.
- **Compatibilidade/Riscos:** Riscos centrais: Warden farm/Looting/trades inflarem a economia Echo; overlap de recipe Netherite double-yield; Echo Cake alterar superheat economy; Sculk→XP formar loop; tags #c capturarem materiais inadequados; buffs full-set duplicados por integração externa.
- **Observações:** JAR físico `create_deep_dark-3.0.2-neoforge-1.21.1.jar`, mod id `create_deep_dark`, runtime 3.0.2. Release oficial NeoForge 1.21.1 de 14/02/2026, Client & Server. 3.0.2 corrige overlap da recipe Netherite e adiciona Warden→Echo Ingot/Template.
- **Procedência:** modlist.txt física atual de 09/09/2026 + metadata runtime + CurseForge oficial Create: Deep Dark 3.0.2.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-deep-dark
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê completo 3.0.2; economia Echo, Warden loot, Echo equipment, Echo Cake/superheat, Netherite double-yield, Sculk Flour/XP, trades, config/data e regressões catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🔊 **Identidade física confirmada:** `create_deep_dark-3.0.2-neoforge-1.21.1.jar`, mod id `create_deep_dark`, runtime `3.0.2`, NeoForge 1.21.1. A build oficial de 14/02/2026 é Client & Server e expande o Deep Dark com uma progressão end-game integrada às máquinas do Create.

## 1. Papel e authority
Create: Deep Dark adiciona uma cadeia própria de conteúdo **Echo/Deep Dark**. O Create continua owner de máquinas, process types, belts, Spout, mixing/crushing e heat states; Minecraft continua owner do Warden, Deep Dark, Sculk e sistema vanilla de loot/trades. O addon owns seus materiais, equipamentos, recipes, loot additions, advancements e regras de progressão.

## 2. Economia Echo
O núcleo da progressão é o material **Echo**. A documentação oficial confirma obtenção a partir de Echo Shards e também via Warden. Echo Shards podem ser processados em **Molten Echo**, seguido por uma assembly line para produzir **Echo Ingots**. Essa cadeia desloca o conteúdo do Deep Dark para automação Create sem tornar Create authority do loot vanilla.

## 3. Warden e loot na 3.0.2
A release 3.0.2 altera explicitamente o Warden: ele passa a ter **50% de chance de dropar Echo Ingot**, com aumento por Looting. A mesma release também permite obter **Echo Smithing Templates** ao derrotar o Warden.
Esse é um gate de balanceamento importante: farms, Looting e qualquer mod que altere Warden loot podem multiplicar a oferta de Echo e reduzir o custo de exploração pretendido.

## 4. Echo equipment
O projeto publica uma armadura Echo completa e uma arma melee própria. A armadura é trimmable e reparável com Echo Ingots. O conjunto completo concede **Resistance II + Strength II**. A Echo Sword aplica **Weakness II + Darkness II por 5 segundos**, sem stacking segundo a documentação oficial.
Esses efeitos pertencem ao addon. Integrações de atributos/perks não devem reaplicar os mesmos buffs/debuffs por inferência.

## 5. Echo Upgrade
O addon adiciona **Echo Upgrade Smithing Template** como parte da progressão de equipamento. A 3.0.2 adiciona uma rota de obtenção via Warden. Template availability deve persistir como data/loot real; recipe viewers ou quests não devem presumir posse apenas porque a receita está visível.

## 6. Echo Cake e superheating
**Echo Cake** é uma alternativa superior ao Blaze Cake. O upstream publica burn time de **19.200 ticks**, aproximadamente três vezes o Blaze Cake regular, e confirma seu uso para **Superheating** ou combustível.
Create permanece authority do heat state. O addon apenas fornece um combustível capaz de alimentar essa superfície; scripts externos não devem conceder superheat paralelamente.

## 7. Netherite recipe e regression gate 3.0.2
O addon fornece uma recipe Create de Netherite Ingot com **double yield**. A 3.0.2 corrige especificamente overlap com uma shapeless recipe do Create. Portanto recipe collision/ambiguity é regression gate obrigatório desta build.
Qualquer KubeJS/datapack que altere Netherite precisa comparar recipe IDs e outputs efetivamente carregados para evitar rota duplicada, barata ou não determinística.

## 8. Sculk Flour e XP
Sculk pode ser crushing-processado em **Sculk Flour**, com geração de XP nuggets conforme a documentação oficial. A cadeia também participa da produção do Echo Cake.
O pack deve testar ganho líquido de XP e renovabilidade de Sculk: uma rota automatizada não pode se tornar um loop de XP infinito não intencional quando combinada com outras farms/recipes.

## 9. Villager trade
A documentação oficial informa que **Master Clerics** podem negociar Echo Shards. Isso cria uma segunda fonte de entrada além da exploração/Warden. Rebalanceamento de villagers, curing discounts ou trade-restock de outros mods pode alterar materialmente a economia Echo.

## 10. Advancements
A linha atual inclui **5 advancements** no módulo Adventure. Eles são sinalização de progressão do addon, não necessariamente eventos seguros para integração externa. Se quests próprias precisarem observar progresso, usar criteria/state server-side comprovados e deduplicáveis, não apenas toast/UI.

## 11. Config e data
O projeto declara opções em arquivo de configuração e usa recipes/loot/tags como superfícies data-driven. A configuração runtime específica do usuário não foi lida nesta ficha; nenhum valor além dos publicados oficialmente é tratado como política ativa.
Reload de datapacks/recipes precisa convergir sem manter recipes, loot rules ou heat acceptance stale.

## 12. Tags e interoperabilidade
A linha 1.9.0 adicionou tags padrão `#c` para Echo Ingot/Block e outras tags de compatibilidade. Isso melhora integração com o pack, mas tags amplas também podem aceitar materiais de outro provider de forma não intencional. Scripts devem resolver o item/tag real antes de assumir equivalência.

## 13. Client/server e multiplayer
Render, tooltips e recipe-viewer são client-facing; loot, trades, effects, recipe consumption/output e heat state são server-authoritative. Em multiplayer, Warden loot deve ser resolvido uma única vez e equipamentos devem aplicar buffs/debuffs de forma consistente entre clientes.

## 14. Riscos técnicos e de balanceamento
1. Warden farm aumenta Echo acima da progressão pretendida.
2. Looting/trade discounts multiplicam a oferta de Echo.
3. Netherite double-yield sobrepõe outra recipe e produz rota duplicada.
4. Echo Cake concede superheat por caminho duplicado ou barato demais.
5. Sculk→XP cria loop positivo com outra automação.
6. Tags `#c` capturam material semanticamente incompatível.
7. Full-set effects são aplicados duas vezes por integração externa.
8. `/reload` deixa recipe/loot/heat state stale.
9. Version drift com Create altera process types ou heat behavior.

## 15. Matriz de testes
- [ ] Dedicated server inicia com Create: Deep Dark 3.0.2 + Create 6.0.10.
- [ ] Warden respeita a nova chance de Echo Ingot sem duplicação de loot.
- [ ] Looting aumenta o resultado sem executar a tabela duas vezes.
- [ ] Echo Smithing Template é obtível pelo fluxo documentado.
- [ ] Molten Echo e assembly line consomem/produzem exatamente uma vez.
- [ ] Echo full set aplica Resistance II + Strength II uma única vez.
- [ ] Echo Sword aplica Weakness II + Darkness II pelo período esperado e sem stacking indevido.
- [ ] Echo Cake fornece superheat e burn time sem conflito com outros fuels.
- [ ] Netherite recipe 3.0.2 não colide com recipe shapeless do Create.
- [ ] Sculk Flour/XP não forma loop de ganho líquido não intencional.
- [ ] Cleric trade não duplica/restocka de forma incompatível com regras do pack.
- [ ] `/reload` mantém recipes/tags/loot coerentes.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 16. Evidências e limites
A modlist física confirma JAR, mod id e runtime 3.0.2. CurseForge oficial confirma Release NeoForge 1.21.1, Client & Server, cadeia Echo, equipamento, Echo Cake, Sculk Flour, Cleric trade, advancements e o changelog exato da 3.0.2. Não foi usado source pin matching do binário; IDs internos, classes, probabilidades além das publicadas e valores de config permanecem fail-closed.

> 🔒 Boundary canônico: **o addon owns conteúdo Echo e suas rotas; Create owns processamento/heat; Minecraft owns Warden/Deep Dark/trades base**. Integrações devem observar o resultado server-side real e não duplicar loot, buffs, XP ou superheat.