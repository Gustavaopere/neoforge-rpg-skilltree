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

## 15. Atualização MineColonies Compatibility 3.58 — 10/10/2026

**JAR físico:** `MineColonies_Compatibility-1.21.1-3.57.jar` / 3.57 (Beta), segundo último snapshot. **Mais recente NeoForge 1.21.1:** **`MineColonies_Compatibility-1.21.1-3.58.jar`**, Beta de **08/10/2026**, CurseForge **file 9099509**. É a única release pública 1.21.1 entre a versão física 3.57 e a 3.58.

**Changelog oficial:** **“Fixed crash with lastest version of Cultural Delights”**. A correção é específica da integração com **Cultural Delights**; não equivale a nova função de MineColonies ou a mudança garantida de jobs/recipes. Na ausência da dependência opcional, o cenário não está necessariamente ativo. Conferir presença de Cultural Delights no pack antes de marcar o fix como diretamente relevante.

**Baselines de compilação publicadas para 3.58:** MineColonies 1.1.1368-snapshot, Structurize 1.0.832-snapshot, Multi-Piston 1.2.51-snapshot, BlockUI 1.0.199-snapshot, Domum Ornamentum 1.0.223-snapshot, MineColonies Tweaks 3.33. Estes são **target versions**, não pins forçados, salvo se metadata do JAR provar contrário. O físico documentado usa MineColonies 1.1.1387, Structurize 1.0.833, Multi-Piston 1.2.58, BlockUI 1.0.212, Domum 1.0.236 e Tweaks 3.33.

**Regressões:** boot client/dedicated server, optional classloading com e sem Cultural Delights, registries/jobs/recipes, Gunner/Orchardist/Fluid Manager/Butcher, chef/cooking/food culture, knife_dig, courier network requests e exactly-once item delivery, config/reload/restart, addons MineColonies Tweaks/Epic Colonies e presença de novos snapshots MineColonies sem ABI drift. Não ativar bridge Let's Do removido sem instalação comprovada.

**Fonte primária:** https://www.curseforge.com/minecraft/mc-mods/minecolonies-compatibility/files/9099509

**Estado:** versão 3.58 apenas upstream; 3.57 permanece física; sem execução de testes de runtime.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** Compatibility addon for MineColonies
- **Último candidato 1.21.1 / NeoForge localizado:** **3.58 (Beta, 08/10/2026)**
- **Estado:** **BLOQUEADO COMO BETA / CANDIDATO PENDENTE DE QA**
- **Gate de promoção:** Validar especialmente o fix de crash com Cultural Delights, além de boot client/dedicated, optional classloading, jobs/recipes, knife_dig, courier/network requests e compatibilidade com o stack MineColonies físico.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/minecolonies-compatibility

## Revisão de releases 3.59–3.60 — 23/10/2026

**Baseline do inventário:** `MineColonies_Compatibility-1.21.1-3.57.jar` (3.57); nenhum JAR foi substituído. A série NeoForge 1.21.1 publicada no CurseForge avança por **3.58 (08/10/2026) → 3.59 (19/10/2026) → 3.60 (19/10/2026)**, todas **Beta**.

- **3.58 — File ID 9099509:** corrigido crash com versão recente de **Cultural Delights**; source e dependências-alvo registrados na própria página. https://www.curseforge.com/minecraft/mc-mods/minecolonies-compatibility/files/9099509
- **3.59 — 19/10/2026:** publicação NeoForge 1.21.1 confirmada pela lista oficial, mas **não foi isolado o changelog da build exata**. Não transferir automaticamente a correção da 3.60 à 3.59. https://www.curseforge.com/minecraft/mc-mods/minecolonies-compatibility/files/all?version=1.21.1
- **3.60 — File ID 9255068:** corrigido crash com a versão mais recente de **Let's do Vinery**. Metas declaradas na página: MineColonies 1.1.1368 snapshot, Structurize 1.0.832 snapshot, Multi-Piston 1.2.51 snapshot, BlockUI 1.0.199 snapshot, Domum Ornamentum 1.0.223 snapshot e Tweaks addon 3.33. Essas são versões **alvo da release**, não uma afirmação de que todas estejam instaladas. https://www.curseforge.com/minecraft/mc-mods/minecolonies-compatibility/files/9255068

**Risco/gates:** conferir se Cultural Delights e Let's do Vinery integram o pack, compatibilidade de dependências-alvo, classloading opcional, inicialização no dedicated server, jobs/receitas/courier requests, saves e reprodução dos crashes. Não promover versão Beta sem QA.

**Estado:** catalogação de 3.57→3.58→3.59→3.60 atualizada; **auditoria individual da 3.59 e QA físico pendentes**. Não retirar item #51 da lista de acompanhamento.


## Auditoria integral da sequência 3.57 → 3.60 — 23/10/2026

**Esta seção resolve a lacuna técnica anterior da versão 3.59 por consulta direta ao código-fonte upstream 1.21.1.** Baseline: `MineColonies_Compatibility-1.21.1-3.57.jar` (3.57), versão instalada no último dump do pack. Artefatos posteriores publicados para NeoForge 1.21.1: **3.58 (08/10) → 3.59 (19/10) → 3.60 (19/10)**, todos no canal **Beta**.

### 3.58 — correção Cultural Delights
A revisão precedente identifica a correção do crash ao carregar o Cultural Delights recente. No GitHub upstream, o commit **`b520e6fa7`** (`Fixed #203`) precede o bump de versão `cad975d0f` (`mod_version=3.58`). Referência: https://github.com/gisellevonbingen-Minecraft/MineColonies_Compatibility/commit/b520e6fa7b4d758fda80e06e93b64b88c44b5e05 .

### 3.59 — correção técnica individual do Issue #205 (antes não identificada)
Entre os bumps de versão `3.58` (`cad975d0f`) e **`3.59` (`9cd2dd484`)** constam os seguintes commits oficiais, em sequência:
- **`5e4e4ac64`**: ajuste exclusivamente de whitespace em `ScorchedGunsModule.java`, sem alteração de comportamento demonstrada.
- **`c65c0e9ce`** (**`Fixed #205 in 1.21.1`**): substituição do pacote `AddRemoveRecipeMessage` de MineColonies pela mensagem própria **`AddRemoveRecipeMessage2`** na tela de ensinar receitas de crafting. A nova mensagem é registrada como pacote **cliente→servidor** em `ModMessagesRegistrar`, serializa `IRecipeStorage` por `StandardFactoryController`, inclui o sinalizador adicionar/remover, atualiza receitas no `ICraftingBuildingModule`, dá feedback sonoro/mensagem, dispara advancement no sucesso e marca o building como alterado.

**Implicações do patch 3.59:** compatibilidade de protocolo/GUI do teach recipe com as novas APIs de MineColonies; evitar erro ao salvar/remover receitas e assegurar integridade de tokens de `IRecipeStorage`. Testar tela de ensinar crafting, add/remove, receita duplicada, erros, courier/building persistence, client/dedicated server e compatibilidade de versão dos dois lados. Não há evidência de que 3.59 corrigiu Cultural Delights ou Let's do Vinery; são fixes de versões vizinhas. Comparação source pinada: https://github.com/gisellevonbingen-Minecraft/MineColonies_Compatibility/compare/cad975d0f844d55246c7659128f231142174718c...9cd2dd4849f6cd563475871da91f48b827d8e8d7 ; commit de implementação: https://github.com/gisellevonbingen-Minecraft/MineColonies_Compatibility/commit/c65c0e9ce2a84054ba8ac1895cc051cb727fbf29 .

### 3.60 — compatibilidade Let's do Vinery
O commit **`95fd2edf3`** `Fixed #204`, antes do bump `ace57a94a` (`mod_version=3.60`), atualiza dependência de Let's do Vinery (ID CurseForge **7539914 → 9203990**) e referências de API das receitas/plantas: **`GrapeBush → GrapeBushBlock`**, **`RecipeTypesRegistry → RecipeTypeRegistry`**, além de reidentificação do módulo de esmagamento **`lets_do_vinery_apple_press_mashing`**. Testar colher uvas, prensa (mashing/fermenting), recipes teach e migração de módulos identificados por ID. Código: https://github.com/gisellevonbingen-Minecraft/MineColonies_Compatibility/commit/95fd2edf3c3d5f70eb51c13418b1484739445889 . Publicação: https://www.curseforge.com/minecraft/mc-mods/minecolonies-compatibility/files/all?version=1.21.1 .

### Resultado e gates
**Auditoria release-by-release integral das três builds NeoForge 1.21.1 posteriores à instalada concluída**. A alteração 3.59 foi identificada por diff direto e vinculada ao bump de versão do mesmo branch. As mudanças dos três patches afetam addons externos distintos, GUI de receitas e comunicação cliente-servidor. Embora 3.60 seja a candidata mais recente, ainda é **Beta** e sua promoção exige teste de compatibilidade real do pack — especialmente MineColonies/Tweaks/Structurize e versões de Cultural Delights/Let's do Vinery, teach recipes e saves. **Nenhum JAR foi substituído nem QA de runtime realizado.**

A pendência de **pesquisa e documentação** referente ao item **#51** fica solucionada: pode ser removido da lista de auditorias documentais, mantendo QA físico como tarefa diferente caso a atualização da Beta seja instalada.
