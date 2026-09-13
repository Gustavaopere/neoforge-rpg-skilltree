# Compatibility addon for MineColonies — 3.56

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81c6957bd70b13a86d44  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-10

## Propriedades do registro

- **Mod:** Compatibility addon for MineColonies
- **Arquivo JAR:** `MineColonies_Compatibility-1.21.1-3.56.jar`
- **Versão 1.21.1:** `3.56`
- **Categoria:** Compat; RPG; Automação
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/minecolonies-compatibility
- **Função:** Addon não oficial que amplia MineColonies com profissões, ajustes de workers, compatibilidade de alimentos e interoperabilidade com redes/logística de outros mods.
- **Dependências:** Runtime físico: MineColonies 1.1.1381 + Structurize 1.0.833 + Multi-Piston 1.2.58 + BlockUI 1.0.211 + Domum Ornamentum 1.0.236 + Tweaks 3.33. Os Target Versions 3.56 (MineColonies 1.1.1368 etc.) são baselines upstream, não pins exatos.
- **Compatibilidade/Riscos:** Beta 3.56. Riscos: snapshot/API/UI drift, network-storage dupe/loss, tool-type misclassification, loot modifier stacking, job AI com provider ausente e confusão de ownership com o Let's Do addon for MineColonies 2.1, fisicamente presente.
- **Sobreposição:** Pode compartilhar algumas funções com mods de compat especializados, mas também adiciona jobs/tweaks próprios; não tratar como duplicata simples.
- **Observações:** JAR físico `MineColonies_Compatibility-1.21.1-3.56.jar`, mod id `minecolonies_compatibility`, runtime 3.56. Build Beta de 13/08/2026; 3.56 adiciona suporte `knife_dig` como tool type Knife. MineColonies físico atual é 1.1.1381, não 1.1.1376/1.1.1368.
- **Procedência:** modlist(4).txt — fonte física canônica atual, 595 mods top-level; JAR `MineColonies_Compatibility-1.21.1-3.56.jar`, mod id `minecolonies_compatibility`, runtime 3.56. Complementado por CurseForge oficial e descrição/features oficiais do projeto.
- **Histórico da decisão:** 2026-09-06 — pesquisa fechada após inspeção do `neoforge.mods.toml` e `gradle.properties` da branch 1.21.1 upstream. Os Target Versions são baselines de compilação; o metadata impõe mínimos, não igualdade exata. Decisão: Manter 3.56.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — corpo vazio corrigido; jobs/tweaks, network storage, cooking/cutting, optional compats, target-version semantics e MineColonies snapshot drift catalogados para 3.56.
- **Data da última decisão:** 2026-09-06

## Dossiê operacional — padrão Alex's Mobs

> 🏘️ **ESCOPO CANÔNICO.** Runtime físico: `MineColonies_Compatibility-1.21.1-3.56.jar`, mod id `minecolonies_compatibility`, versão `3.56`. É um addon **não oficial** de compatibilidade para MineColonies, Client & Server. O próprio projeto orienta não solicitar suporte à equipe MineColonies quando o addon está instalado.

## 1. Authority e limites
MineColonies continua owner de colonies, citizens, jobs, requests e buildings. Este addon adiciona jobs/tweaks e bridges para outros mods; não deve manter cópia paralela do colony state.

A página oficial também diz que Farm & Charm e seus compat addons são atendidos pelo projeto separado **Let's Do addon for MineColonies**. Esse addon 2.1 está fisicamente presente no pack, portanto não atribuir a esta ficha integrações que pertencem ao bridge Let's Do.

## 2. Novos jobs documentados
O projeto lista:
- **Gunner** — usa tool type Gun;
- **Orchardist** — colhe fruits/bushes/crops não suportados pelo Farm padrão;
- **Fluid Manager** — enche Bucket/Large Bottle com fluids solicitados e pode recolher lava de cauldron;
- **Butcher** — desmembra carcasses para meats, hides e by-products.

Cada job continua operando dentro do lifecycle/request system MineColonies.

## 3. Tweaks de jobs existentes
A documentação atual inclui:
- Farmer com suporte mais amplo a crops/plants/farmlands;
- Baker com suporte a blocos Bakery em escopo próprio do addon;
- Chef/Cook Assistant usando Farmer's Delight Cutting Board e Cooking Pot;
- Courier com acesso a network storage;
- Blacksmith com upgrades/Smithing Templates/repair para stacks compatíveis;
- Animal Herders com extra drops via loot modifiers;
- Stonemason processando Stonecutting recipes.

Essas features são condicionais aos providers detectados; não presumir que todos estejam ativos apenas porque o addon carrega.

## 4. Network Storage e Courier
O addon documenta acesso de Courier a redes como Applied Energistics 2, Refined Storage, Simple Storage Network e Create Stock Link, com autocrafting para AE2/RS em linhas suportadas.

Neste pack AE2 foi removido do runtime físico atual; portanto qualquer código/config legado de AE2 não deve ser tratado como integração ativa. Tom's Simple Storage está presente, mas o suporte citado pelo histórico upstream para common storage/network precisa ser validado na build 3.56 antes de automatizar requests próprios.

## 5. Cooking/cutting e Knife
O projeto possui integração histórica com Farmer's Delight Cooking Pot/Cutting Board e tool type Knife. A mudança exata 3.56 diz que tools performáveis como **`knife_dig`** podem ser usados como tool type Knife.

Isso é relevante no pack culinário, mas não significa que qualquer item chamado knife seja automaticamente aceito; tags/tool behavior runtime são authority.

## 6. Outras compatibilidades
A página do projeto lista ainda:
- Polymorph: melhoria da Teach Recipe window;
- JEI: categoria Research;
- Tinkers Construct tools/levelling support;
- Alex's Mobs Crow afastado do Field;
- Compost utilizável como bonemeal.

Cada módulo depende da presença do alvo; ausências devem resultar em compat inativa, não em crash.

## 7. Versões-alvo 3.56 vs runtime físico
O changelog 3.56 publica baselines de compilação:
- MineColonies 1.1.1368;
- Structurize 1.0.832;
- Multi-Piston 1.2.51;
- BlockUI 1.0.199;
- Domum Ornamentum 1.0.223;
- Tweaks 3.33.

O pack físico atual usa **MineColonies 1.1.1381, Structurize 1.0.833, Multi-Piston 1.2.58, BlockUI 1.0.211, Domum 1.0.236 e Tweaks 3.33**. Os números upstream são target baselines, não pins de igualdade. O drift para builds mais novas exige regressão comportamental, não downgrade automático.

## 8. Canal Beta
3.56 é Beta no CurseForge, enquanto uma Release principal mais antiga 3.42 também existe para 1.21.1. O usuário já decidiu `Manter`; esta ficha preserva a decisão e registra o risco Beta em vez de reclassificar por canal.

## 9. Food/loot/economia
Remover saturation nerf, gerar extra drops de herders, processar cutting/cooking e acessar storage networks são superfícies econômicas. Duplicar essas regras com KubeJS/datapacks/perks pode criar inflation ou dupe. Uma única authority deve decidir cada modifier.

## 10. Multiplayer e lifecycle
Jobs, requests e storage operations são server-side. GUIs/JEI/Teach Recipe são apresentação/interação cliente, mas a conclusão de work request, retirada/inserção de items e craft precisam ser validados no servidor e persistir após citizen unload/restart.

## 11. Riscos
1. Snapshot drift MineColonies/BlockUI/Structurize quebra API/GUI.
2. Network storage request duplica ou perde items.
3. Tool type Knife/Gun classifica item incorreto.
4. Extra loot modifier double-stacka com outro addon.
5. Job AI entra em loop por recipe/provider ausente.
6. Compat opcional referencia classe de mod removido.
7. Farm & Charm feature é atribuída a este addon em vez do Let's Do addon 2.1.
8. Beta regression após update do stack MineColonies.

## 12. Boundary para quests/perks
Crédito de profissão deve usar work/request concluído e citizen/job identity real. Abrir GUI, detectar recipe ou item aparecer em network storage não prova que o colonist executou o trabalho. Não duplicar XP/progressão nativa MineColonies.

## 13. Matriz de testes
- [ ] Dedicated server inicia com Compatibility 3.56 + MineColonies 1.1.1381 + Tweaks 3.33.
- [ ] Gunner/Orchardist/Fluid Manager/Butcher registram sem missing job/building data.
- [ ] `knife_dig` tool é aceito como Knife e item não compatível é rejeitado.
- [ ] Chef cutting/cooking conclui request exatamente uma vez.
- [ ] Courier network storage não duplica/perde stacks em restart.
- [ ] Optional integrations ausentes são ignoradas sem classloading crash.
- [ ] JEI Research/Teach Recipe UI não altera server state indevidamente.
- [ ] Let's Do/Farm & Charm behavior fica no bridge correto quando ambos addons coexistem.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 14. Evidências e limite
CurseForge oficial confirma Beta 3.56, target versions, `knife_dig` change e lista de features/jobs/compat. A modlist física confirma o stack MineColonies mais novo e o Let's Do addon 2.1. Não foi assumido que todo módulo opcional esteja ativo sem o provider correspondente.
