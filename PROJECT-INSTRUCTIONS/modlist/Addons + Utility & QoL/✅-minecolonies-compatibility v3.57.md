# Compatibility addon for MineColonies

## Propriedades do registro

- **Mod:** Compatibility addon for MineColonies
- **Arquivo JAR:** MineColonies_Compatibility-1.21.1-3.57.jar
- **Versão 1.21.1:** 3.57
- **Categoria:** Compat, RPG, Automação
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/minecolonies-compatibility
- **Função:** Addon não oficial que amplia MineColonies com profissões, ajustes de workers, compatibilidade de alimentos e interoperabilidade com redes/logística de outros mods.
- **Dependências:** Stack físico atual: MineColonies 1.1.1387-1.21.1-snapshot, Structurize 1.0.833-1.21.1-snapshot, Multi-Piston 1.2.58-1.21.1, BlockUI 1.0.212-1.21.1-snapshot, Domum Ornamentum 1.0.236-snapshot e MineColonies Tweaks 3.33. Os target baselines publicados pela 3.57 são menores e servem como referência, não pins.
- **Compatibilidade/Riscos:** Addon amplo sobre APIs de MineColonies. Runtime físico atual usa MineColonies 1.1.1387-snapshot e BlockUI 1.0.212, acima dos target baselines da Compatibility 3.57. Riscos: ABI/behavior drift, recipes/jobs duplicados, request/storage bridge, optional compat ausente, mixin/client UI regressions e referências legadas ao bridge Let's Do removido. Atualizações devem ser testadas como stack.
- **Sobreposição:** Pode compartilhar algumas funções com mods de compat especializados, mas também adiciona jobs/tweaks próprios; não tratar como duplicata simples.
- **Observações:** JAR físico MineColonies_Compatibility-1.21.1-3.57.jar, mod id minecolonies_compatibility, runtime 3.57. O bridge Let's Do addon for MineColonies foi removido da modlist física atual.
- **Procedência:** modlist física atual de 17/09/2026 + CurseForge oficial Compatibility addon for MineColonies 3.57 (Beta, 09/09/2026) + stack físico atual revalidado.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 17/09/2026 — runtime físico atualizado para 3.57; target baselines e knife_dig confirmados; stack MineColonies atual revalidado; referência física ao bridge Let's Do removida por ausência no pack atual.
- **Histórico da decisão:** 2026-09-06 — pesquisa fechada após inspeção do `neoforge.mods.toml` e `gradle.properties` da branch 1.21.1 upstream. Os Target Versions são baselines de compilação; o metadata impõe mínimos, não igualdade exata. Decisão: Manter 3.56.
- **Data da última decisão:** 2026-09-06

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #396: JAR `MineColonies_Compatibility-1.21.1-3.57.jar`, mod id `minecolonies_compatibility`, runtime `3.57`, SHA-1 `03e16cc0377b09361b6935c0f05adf9040d8eb46`.

<callout icon="🏘️" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `MineColonies_Compatibility-1.21.1-3.57.jar`, mod id `minecolonies_compatibility`, versão `3.57`. É um addon **não oficial** de compatibilidade para MineColonies, Client & Server. O próprio projeto orienta não solicitar suporte à equipe MineColonies quando o addon está instalado.
</callout>
## 1. Authority e limites
MineColonies continua owner de colonies, citizens, jobs, requests e buildings. Este addon adiciona jobs/tweaks e bridges para outros mods; não deve manter cópia paralela do colony state.
A página oficial também diz que Farm & Charm e seus compat addons são atendidos pelo projeto separado **Let's Do addon for MineColonies**. Esse bridge não está mais presente na modlist física atual e integra a lista de itens removidos; portanto não atribuir a esta ficha integrações que pertencem ao projeto Let's Do.
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
Neste pack AE2 foi removido do runtime físico atual; portanto qualquer código/config legado de AE2 não deve ser tratado como integração ativa. Tom's Simple Storage está presente, mas o suporte citado pelo histórico upstream para common storage/network precisa ser validado na build física 3.57 antes de automatizar requests próprios.
## 5. Cooking/cutting e Knife
O projeto possui integração histórica com Farmer's Delight Cooking Pot/Cutting Board e tool type Knife. A release 3.57 mantém explicitamente a mudança em que tools performáveis como **`knife_dig`** podem ser usados como tool type Knife.
Isso é relevante no pack culinário, mas não significa que qualquer item chamado knife seja automaticamente aceito; tags/tool behavior runtime são authority.
## 6. Outras compatibilidades
A página do projeto lista ainda:
- Polymorph: melhoria da Teach Recipe window;
- JEI: categoria Research;
- Tinkers Construct tools/levelling support;
- Alex's Mobs Crow afastado do Field;
- Compost utilizável como bonemeal.
Cada módulo depende da presença do alvo; ausências devem resultar em compat inativa, não em crash.
## 7. Versões-alvo 3.57 vs runtime físico
A release 3.57 publica os seguintes target baselines:
- MineColonies 1.1.1368;
- Structurize 1.0.832;
- Multi-Piston 1.2.51;
- BlockUI 1.0.199;
- Domum Ornamentum 1.0.223;
- Tweaks 3.33.
O pack físico atual usa **MineColonies 1.1.1387, Structurize 1.0.833, Multi-Piston 1.2.58, BlockUI 1.0.212, Domum Ornamentum 1.0.236 e Tweaks 3.33**. Os números upstream são target baselines, não pins de igualdade. O drift para builds mais novas exige regressão comportamental, não downgrade automático.
## 8. Canal Beta
A build física 3.57 é **Beta** no CurseForge e, em 17/09/2026, continua sendo a versão 1.21.1 mais recente publicada nesse canal; a Release principal permanece 3.42. A 3.57 mantém os mesmos target baselines da linha anterior e publica o change de `knife_dig` como tool type Knife. A decisão `Manter` é preservada, mas qualquer update futuro continua sujeito a regressão do stack MineColonies.
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
7. Farm & Charm feature é atribuída a este addon apesar de o bridge Let's Do ter sido removido do pack atual.
8. Beta regression após update do stack MineColonies.
## 12. Boundary para quests/perks
Crédito de profissão deve usar work/request concluído e citizen/job identity real. Abrir GUI, detectar recipe ou item aparecer em network storage não prova que o colonist executou o trabalho. Não duplicar XP/progressão nativa MineColonies.
## 13. Matriz de testes
- [ ] Dedicated server inicia com Compatibility 3.57 + MineColonies 1.1.1387 + Tweaks 3.33.
- [ ] Gunner/Orchardist/Fluid Manager/Butcher registram sem missing job/building data.
- [ ] `knife_dig` tool é aceito como Knife e item não compatível é rejeitado.
- [ ] Chef cutting/cooking conclui request exatamente uma vez.
- [ ] Courier network storage não duplica/perde stacks em restart.
- [ ] Optional integrations ausentes são ignoradas sem classloading crash.
- [ ] JEI Research/Teach Recipe UI não altera server state indevidamente.
- [ ] Ausência do bridge Let's Do não deixa referências/classloading obrigatórios para Farm & Charm.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 14. Evidências e limite
CurseForge oficial confirma a Beta 3.57, os target baselines e o change de `knife_dig`. A modlist física de 17/09/2026 confirma Compatibility 3.57 e o stack atual MineColonies 1.1.1387 / Structurize 1.0.833 / Multi-Piston 1.2.58 / BlockUI 1.0.212 / Domum Ornamentum 1.0.236 / Tweaks 3.33. O antigo Let's Do addon for MineColonies não está mais presente. Não foi assumido que todo módulo opcional esteja ativo sem o provider correspondente.
