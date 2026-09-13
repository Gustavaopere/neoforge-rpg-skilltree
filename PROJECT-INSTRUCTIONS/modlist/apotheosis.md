# Apotheosis

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8192b784c910dff44fb8
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Apotheosis
- **Arquivo JAR:** `Apotheosis-1.21.1-8.8.0.jar`
- **Versão 1.21.1:** 8.8.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** RPG, Exploração
- **Função:** Módulo Adventure/RPG do ecossistema Apotheosis moderno: affix gear/rarities, gems/sockets, World Tiers, Apothic Invaders/Elites/Augmentations, salvaging/reforging/augmenting, loot effects, comparison/linking de equipamento, upgrade templates, gateways/tier progression e conteúdo data-driven. Apothic Attributes, Enchanting e Spawners são módulos separados.
- **Dependências:** Placebo e Apothic Attributes são infraestrutura do ecossistema. CurseForge marca Apothic Enchanting e Apothic Spawners como mandatory para auto-install, mas desde Apotheosis 8.7.0 o runtime pode operar sem um ou ambos com features reduzidas/fallbacks. O pack possui ambos (Enchanting 1.6.2, Spawners 1.4.0) e Placebo 9.9.2. Patchouli é tecnicamente opcional, porém necessário para Chronicle of Shadows/guia; o pack possui Patchouli.
- **Sobreposição:** Sobreposição funcional com outros sistemas de loot affix, gems, rarity, sockets e equipment progression, mas os módulos Apothic próprios não são duplicatas: Attributes fornece stats, Enchanting overhaul de enchanting, Spawners overhaul de spawners. Apothic Compats/Category Compat são bridges de integração.
- **Compatibilidade/Riscos:** Progressão RPG de alto impacto: affixes/gems/World Tiers somam atributos e podem multiplicar bônus com RPG Skill Tree, Additional Attributes e outros gear systems. 8.8.0 adiciona `apotheosis:cannot_be_duplicated`; qualquer sistema externo de clone/loot-copy deve respeitar a tag. Summit/Pinnacle agora dão damage reduction física e mágica a monsters. Invader spawn rules são configuráveis. Data-driven affixes/gems/rarities/tier augments exigem reload e validação de datapacks/compats.
- **Observações:** World Tier e affix/gem data são contratos centrais para perks/economia. Não usar raridade pelo nome de exibição como ID; ler JSON/registry/data. Qualquer clonagem custom deve checar `apotheosis:cannot_be_duplicated`.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge/changelog oficiais Apotheosis 8.8.0 + Placebo 9.9.2/Apothic Attributes 2.10.1/Enchanting 1.6.2/Spawners 1.4.0 + dossiê operacional existente.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/apotheosis
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — Adventure module authority, affixes/gems/sockets, World Tiers, Invaders, cannot_be_duplicated, Placebo 9.9.2 and Apothic modules confirmed in global QC #30.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

> 🔎 **Escopo canônico.** Runtime físico atual: `Apotheosis-1.21.1-8.8.0.jar`. A documentação antiga do projeto citava 8.7.0; para esta instância a authority é **8.8.0**. Na arquitetura moderna, esta entrada representa principalmente o **Adventure module**: affixes, gems, sockets, World Tiers, invaders/elites, loot e progressão de equipamento. Attributes, Enchanting e Spawners são JARs próprios.

## 1. Arquitetura moderna do ecossistema
O Apotheosis histórico foi dividido em módulos top-level:
- **Apothic Attributes** — atributos/efeitos compartilhados do ecossistema;
- **Apothic Enchanting** — overhaul de enchanting;
- **Apothic Spawners** — overhaul/configuração de mob spawners;
- **Apotheosis** — Adventure/RPG: affixes, rarity, gems, sockets, World Tiers, invaders, loot, salvaging/reforging/augmenting e progressão.

Não marcar esses módulos como duplicatas. Eles foram separados justamente para responsabilidades distintas.

## 2. Affix Items
Equipamentos elegíveis podem receber **affixes** que adicionam propriedades especiais e modificadores. A raridade do item determina quantos/que tipos de affixes podem aparecer e qual o power range permitido.

Affix gear surge principalmente por:
- loot;
- mobs especiais;
- sistemas de geração de loot do mod/compats;
- reforging/rewards.

A categoria de loot/arma importa para definir quais affixes são válidos. Por isso **Apothic Category Compat** existe: itens modded mal categorizados podem receber pool incorreto ou nenhum affix adequado.

## 3. Rarities
Rarity no Apotheosis não é apenas cor de nome. Ela governa:
- qualidade/power de affixes;
- quantidade de affixes;
- materiais de salvage/reforge;
- disponibilidade por World Tier;
- efeitos visuais do loot;
- parte da progressão de gems/gear.

Para integração, usar **IDs/data reais de rarity**, não strings de display traduzidas.

## 4. World Tiers
A linha atual usa cinco patamares principais:
1. **Haven** — início/base.
2. **Frontier** — progressão intermediária; começa a ampliar significativamente loot/inimigos especiais.
3. **Ascent** — tier superior intermediário.
4. **Summit** — exige marcos avançados como Wither e abre conteúdo Epic/alto nível conforme data.
5. **Pinnacle** — tier máximo padrão, com Mythic/endgame.

O jogador desbloqueia tiers por critérios/advancements e pode consultar checklist/details na UI. Packs podem alterar critérios e até controlar manualmente a progressão; a UI mostra critérios traduzidos quando o pack fornece language keys correspondentes.

## 5. Tier Augments
World Tiers também aplicam **augments** a mobs/sistemas. Desde a integração das linhas 8.6+, Enchanting e Spawners também podem ser limitados/progredidos pelos tiers quando os módulos estão presentes.

Na 8.8.0 foram adicionados **damage reduction augments**: monsters em **Summit e Pinnacle** recebem redução física e mágica leve. Isso significa que balanceamento de perks/dano deve medir dano real no tier, não apenas atributo bruto do jogador.

## 6. Apothic Invaders
A partir dos tiers apropriados, podem surgir **Invaders**: inimigos anunciados por efeitos/som/beam, com equipamento forte, affixes/enchants e loot garantido/especial.

O sistema possui regras de spawn data/config-driven. Em versões modernas os cooldowns foram melhorados para multiplayer, e 8.8.0 adiciona **opções extras de Invader Spawn Rules**.

Para servers, não presumir cooldown global único entre players; a linha 8.6.1 passou a agrupar cooldowns por jogador/tier/área relevante para evitar deadlock quando players estão distantes.

## 7. Elites e Augmentations
Além de Invaders mais “eventizados”, o ecossistema possui mobs com reforços/augmentations e elites menos teatrais, mas ainda recompensadores. Essas camadas mudam:
- stats;
- dano/resistência;
- loot;
- dificuldade por tier.

Mods que também escalam mobs devem deduplicar/limitar stacking para não multiplicar dificuldade de forma acidental.

## 8. Gems
**Gems** são itens socketáveis com bônus dependentes do item/categoria em que são inseridos. Uma mesma gem pode oferecer efeitos diferentes conforme o tipo de equipamento suportado.

Fontes típicas:
- loot;
- mobs;
- Invaders com chances/qualidade superiores;
- conteúdo dimensional/compats conforme data.

Gems podem ser melhoradas/cortadas conforme o sistema do mod e usam **Gem Dust** e materiais de rarity em várias operações.

## 9. Sockets
Affix gear pode ter sockets. Uma gem pode ser inserida:
- diretamente no inventory, segurando/arrastando a gem e right-click sobre item socketable na linha moderna;
- por rotas de Smithing/GUI quando aplicável.

### Sigil of Withdrawal
Remove gem de um socket preservando o fluxo previsto pelo mod.

### Sigil of Socketing
Adiciona socket conforme limites do item/rarity/data.

Qualquer sistema externo que copie item precisa preservar socket data/gem components corretamente.

## 10. Gem Dust e Salvaging de gems
A progressão inicial de gem dust usa recipes/mechanisms próprios; a linha moderna integrou melhor salvaging e materiais de rarity. Em automation, validar que output aleatório mantém distribuição correta: versões anteriores corrigiram caso em que Salvaging Table automatizada sempre produzia melhores outputs.

## 11. Salvaging Table
A **Salvaging Table** desmonta affix gear e produz materiais ligados à rarity. Esses materiais entram em reforging, upgrade de gems e outros fluxos.

Na linha 8.6 os materiais de salvage foram renomeados para nomes finais reais, mantendo registry aliases antigos para compatibilidade de mundos/datapacks.

Economia do pack precisa distinguir:
- loot para uso;
- loot para salvage;
- materiais de rarity;
- gems;
- gear já refinado.

## 12. Reforging Table
A **Reforging Table** cria/reconfigura affix gear usando item base + materiais/recursos da rarity conforme regras/data. A escolha de rarity e affixes depende de World Tier, tables/pools e config.

Existe também loot function `apotheosis:reforge_item` na linha moderna, útil para datapacks/loot custom sem reproduzir lógica em código externo.

## 13. Augmenting Table
A **Augmenting Table** trabalha sobre affixes individuais, permitindo reroll/upgrade conforme regras, consumindo recursos e XP, incluindo **Sigils of Enhancement**.

Affixes com weight 0 não devem aparecer como candidatos; versões recentes corrigiram essa situação. Affixes level-independent não são upgradáveis pela table.

A table suporta capability/insertion de Sigils por hoppers/automation nas linhas atuais.

## 14. Sigil of Malice — comportamento 8.8.0
A 8.8.0 **reimplementou** o Sigil of Malice.

Comportamento confirmado:
- escolhe um affix aleatório e aumenta seu power para **200%**;
- para comparação, Sigil of Supremacy chega a **150%** no boost correspondente;
- Malice **não remove mais** um affix;
- em vez disso, outro affix é resetado para **0% power**.

Não usar documentação antiga de Malice que descreve remoção de affix. Esta é mudança específica da versão instalada.

## 15. Sigil of Supremacy
Recompensa endgame ligada à progressão/gateways. Permite supercharge de affix além de limites normais, com comportamento diferente de Malice. Não tratar os dois como equivalentes.

## 16. Rune of Debilitation — 8.8.0
A 8.8.0 elevou **Initial Health** de 5% para **16%**, reduzindo a quantidade de runes necessárias para atingir o valor máximo de 16 para **5**. Qualquer guia/quest antiga com “16 runes” está desatualizada para esta build.

## 17. `apotheosis:cannot_be_duplicated`
A 8.8.0 adiciona a item tag:
`apotheosis:cannot_be_duplicated`

Itens nessa tag **não devem ser clonados por abilities que copiam loot items diretamente**. Esta tag é um contrato de segurança/economia e deve ser respeitada por projetos próprios, perks, crafting especial ou qualquer mod de duplicação/cópia.

Se um sistema próprio clona ItemStack sem consultar esta tag, ele pode reintroduzir dupes que Apotheosis explicitamente tenta impedir.

## 18. Loot effects
Affix items dropados no mundo possuem efeitos visuais cuja intensidade cresce com rarity. Custom rarities podem controlar efeitos por JSON; a feature pode ser desativada por config.

Isso é apresentação de loot, não prova de uma entidade “boss”. O mod renomeou antigas referências de Apothic Boss para **Apothic Invader** em linhas recentes.

## 19. Equipment Comparison
Hotkey/overlay permite comparar item affixable com equipamento do mesmo loot category. Não compara categorias diferentes e não se aplica a item não affixable.

Isso significa que Category Compat influencia não só affix selection, mas potencialmente como o item se encaixa em ferramentas de comparação.

## 20. Link Item to Chat
Hotkey padrão documentado na linha moderna permite linkar item no chat com hover data. Possui cooldown server-configurable e pode ser desativado.

Em servidores com chat signing, versões modernas corrigiram problemas relacionados.

## 21. Upgrade Templates
Apotheosis adiciona templates para atualizar materiais vanilla de affix gear (por exemplo iron/gold/diamond) preservando a progressão do item onde o recipe permite. O objetivo é impedir que um bom affix item fique obsoleto apenas porque o material base ficou para trás.

## 22. Chronicle of Shadows
Livro/guia do mod acessível via recipe/progressão apropriada e dependente da camada Patchouli para documentação completa. O projeto também fornece informação via JEI e GUIs.

**Patchouli é tecnicamente opcional**, mas sem ele o Chronicle/experiência de documentação é reduzida. O pack possui Patchouli instalado.

## 23. Gateways e progressão de tiers
Apotheosis integra-se com **Gateways to Eternity** quando presente para fornecer gateways de fim de tier. A partir das linhas 8.4, tiers acima de Haven podem ter gateway correspondente, e Pinnacle dá acesso ao Endless Gateway de Invaders.

Se Gateways não estiver instalado, não inventar que esses encontros existem só porque o código de integração existe. Verificar modlist quando essa feature for usada.

## 24. Dependências e módulos no runtime atual
### Placebo
Infraestrutura do autor/ecossistema. Pack: **9.9.2**.

### Apothic Attributes
Pack: **2.10.1**. Provider de atributos/efeitos compartilhados.

### Apothic Enchanting
Pack: **1.6.2**. CurseForge marca como mandatory para auto-install, mas desde **Apotheosis 8.7.0** o core pode rodar sem ele com features/fallbacks reduzidos.

### Apothic Spawners
Pack: **1.4.0**. Mesma observação: pode ser removido desde 8.7.0 com funcionalidades reduzidas, embora CF mantenha auto-install obrigatório.

### Patchouli
Opcional tecnicamente, usado para Chronicle/guide. Presente no pack.

## 25. Comportamento sem Enchanting/Spawners — mudança 8.7.0 herdada pela 8.8.0
Sem **Apothic Enchanting**:
- unbreakable potion charms e Gem Case of House Fabergé ficam indisponíveis;
- algumas gems/items fortes ficam indisponíveis;
- gateways altos podem perder músicas que dependem de AEnch;
- alguns recipes usam fallbacks.

Sem **Apothic Spawners**:
- Spawner Runes ficam ocultos;
- Rogue Spawners ainda geram, mas sem stats exclusivos do módulo;
- parte de itens preset pode ficar indisponível.

Nesta instância, ambos estão instalados; isso é documentação arquitetural para troubleshooting futuro.

## 26. Data-driven surfaces
O mod é fortemente data-driven. Packs podem alterar:
- rarities;
- affixes;
- affix pools/weights;
- gems e bonuses;
- sockets/applicability;
- World Tier criteria/augments;
- loot rules;
- invader spawn rules;
- structures/gateways/rewards conforme sistemas associados.

A release 8.8.0 oferece também arquivo adicional `stock_data.zip`, útil como referência canônica do datapack default da versão.

Para customização, partir dos stock data da **mesma versão 8.8.0**, não de wiki antiga.

## 27. Apothic Category Compat e Apothic Compats
### Apothic Category Compat
Corrige **loot categories** de itens modded que o auto-detection do Apotheosis não classifica corretamente. Não adiciona novos affixes/gems/loot systems por si só.

### Apothic Compats
É uma camada mais ampla de datapacks/integração capaz de adicionar affixed loot, gear sets, affixes, gems, invaders e categorias para vários mods.

Os dois podem coexistir e não são duplicatas.

## 28. Interação com RPG Skill Tree e outros atributos
O pack possui múltiplas fontes de stats: Apothic Attributes, Additional Attributes, perks próprios, Iron's, Epic Fight e equipamentos de vários mods.

Riscos:
- crit chance/damage empilhados em várias autoridades;
- armor/protection pierce/shred duplicados;
- life steal/heal amplificado por affix + perk;
- World Tier DR mascarando balanceamento;
- gems/affixes multiplicando stats de perks percentuais;
- invaders recebendo scaling de mais de um mod.

Qualquer perk que replique um affix existente precisa justificar identidade/deduplicação.

## 29. Loot cloning e anti-abuso
Além da tag `cannot_be_duplicated`, testar interações com:
- mods que copiam loot;
- machines que duplicam outputs;
- curios/relics que repetem drop;
- perks de extra loot;
- grave/death recovery;
- storage movendo affix items com components complexos.

Affix gear deve manter todos os components ao mover entre inventories/dimensions.

## 30. World Tier balanceamento
Não medir um build apenas em Haven. Testes de dano/defesa devem cobrir pelo menos:
- Haven;
- Frontier/Ascent;
- Summit;
- Pinnacle.

A partir de 8.8.0, Summit/Pinnacle têm DR adicional, portanto o mesmo ataque produz resultado diferente por augment.

## 31. Matriz de validação
1. Startup com Placebo + Attributes + Enchanting + Spawners presentes.
2. Abrir Chronicle/JEI e confirmar docs 8.8.0.
3. Gerar affix loot em múltiplas rarities.
4. Verificar loot category de vanilla e itens modded.
5. Socketar gem no inventory e por rota alternativa suportada.
6. Remover gem com Sigil of Withdrawal.
7. Adicionar socket com Sigil of Socketing.
8. Gem cutting/upgrade e Gem Dust.
9. Salvaging Table manual + automation.
10. Reforging Table em múltiplas rarities.
11. Augmenting Table, hopper com Sigil e weight 0.
12. Sigil of Malice: confirmar 200% + outro affix 0%, sem remoção.
13. Sigil of Supremacy separado.
14. Rune of Debilitation: confirmar 16% e cap em 5 runes para max.
15. `cannot_be_duplicated`: tentar sistema de cópia autorizado e confirmar bloqueio.
16. World Tier unlock checklist de Haven→Pinnacle.
17. Invader spawn/cooldown com dois players separados.
18. Elite/Augmentation loot.
19. Summit/Pinnacle: medir physical/magic DR.
20. Rogue Pillager Spawner: held item preservado, fix 8.8.0.
21. Equipment Comparison com categories corretas.
22. Link to Chat em dedicated server.
23. Affix loot effects por rarity.
24. Datapack reload usando stock data 8.8.0 como baseline.
25. Apothic Category Compat nos itens alvo instalados.
26. Apothic Compats adicionando conteúdo sem duplicar categories.
27. Storage/transport de affix gear sem perder components.
28. RPG Skill Tree: crit/lifesteal/pierce/shred sem double-application.
29. Invader/mob scaling com outros mods de dificuldade.
30. Server restart/relog: World Tier, gear, gems e progression persistem.

## 32. Changelog específico 8.8.0
Confirmado no changelog oficial 1.21:
- Sigil of Malice reimplementado: random affix → 200%; outro → 0%; não remove mais affix.
- Rune of Debilitation Initial Health: 5% → 16%; runes para max: 16 → 5.
- `apotheosis:cannot_be_duplicated`.
- opções adicionais para Invader Spawn Rules.
- fix de rogue spawners de Pillagers sem held items.
- damage reduction augments.
- monsters de Summit/Pinnacle recebem leve redução física e mágica.
- tradução brasileira atualizada.

## 33. Regras para outros chats
- **8.8.0 é authority**, não 8.7.0 do guia antigo.
- Não tratar Enchanting/Spawners/Attributes como “features dentro do mesmo JAR”; são módulos separados.
- Não clonar loot ignorando `cannot_be_duplicated`.
- Não balancear perks só contra Haven.
- Não usar display name de rarity/affix como ID.
- Antes de criar compat, checar se Category Compat ou Apothic Compats já resolve.
- Para datapacks, usar stock data da mesma versão como baseline.

## 34. Fontes e confiança
**Authority física:** modlist de 07/09/2026 — `Apotheosis-1.21.1-8.8.0.jar`.

**Upstream:** [CurseForge — Apotheosis](https://www.curseforge.com/minecraft/mc-mods/apotheosis), release 8.8.0 e changelog oficial 1.21.

**Fonte interna:** guia completo gameplay/sistemas; usado para arquitetura geral, corrigido pela modlist física onde o guia ainda apontava 8.7.0.

**Confiança:** alta para arquitetura, 8.8.0 changelog e systems documentados. Pesos/IDs/recipes/criteria concretos são data-driven e devem ser lidos dos stock data/config/runtime antes de implementação.
