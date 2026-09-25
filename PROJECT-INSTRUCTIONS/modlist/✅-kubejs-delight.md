# KubeJSDelight

## Propriedades do registro

- **Mod:** KubeJSDelight
- **Arquivo JAR:** kubejsdelight-1.1.6.jar
- **Versão 1.21.1:** 1.1.6
- **Categoria:** Compat, Automação, Comida
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/kubejs-delight
- **Função:** Integra Farmer's Delight ao KubeJS para registrar knives/pies/feasts custom e criar recipes de Cutting Board/Cooking Pot por scripts.
- **Dependências:** Farmer's Delight 1.3.4 + KubeJS 2101.7.2-build.377 estão fisicamente presentes. NeoForge 1.21.1.
- **Compatibilidade/Riscos:** Riscos: registry migration de items custom, servings/food duplication, recipe conflicts/reload e API drift KubeJS/Farmer's Delight. Filename público e físico diferem; runtime físico é authority.
- **Sobreposição:** Complementa Farmer's Delight/KubeJS; recipes/scripts podem tocar as mesmas rotas que outros addons culinários, mas isso é composição configurável.
- **Observações:** Runtime físico KubeJSDelight 1.1.6 permanece atual para NeoForge 1.21.1. O core KubeJS físico está em build.377.
- **Procedência:** modlist(1).txt física anexada e reconferida em 25/09/2026 + CurseForge/Modrinth oficiais KubeJS Delight 1.1.6 + KubeJS Wiki Farmer's Delight.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 25/09/2026 — KubeJSDelight 1.1.6/JAR físico reconfirmado; matriz física reconciliada com KubeJS 2101.7.2-build.377 e Farmer's Delight 1.3.4. Custom content/recipes, lifecycle e riscos preservados.
- **Data da última decisão:** 2026-09-06

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #367: JAR `kubejsdelight-1.1.6.jar`, mod id `kubejsdelight`, runtime `1.1.6`, SHA-1 `66a5de83677d38828242ab3dc96bf1b6f2771198`.

<callout icon="🍲" color="yellow_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `kubejsdelight-1.1.6.jar`, mod id `kubejsdelight`, versão `1.1.6`. É a integração Farmer's Delight ↔ KubeJS para conteúdo e recipes customizados; o filename físico curto é autoridade desta instância.
</callout>
## 1. Conteúdo custom documentado
A integração permite registrar por startup scripts:
- custom knives;
- custom pies;
- custom feasts.
A documentação 1.21.1 usa builders específicos do Farmer's Delight e permite definir propriedades como servings do feast.
## 2. Recipes Farmer's Delight
Server scripts podem criar recipes de **Cutting Board** e **Cooking Pot**. Esses recipes continuam executados pelo provider Farmer's Delight; KubeJSDelight apenas expõe builders/integração ao script.
## 3. Script lifecycle
Items/blocks custom pertencem a `startup_scripts`; recipes pertencem a `server_scripts`. Essa separação evita registrar conteúdo depois do registry freeze e permite `/reload` dos recipes sem recriar item/block IDs.
## 4. Runtime atual
Farmer's Delight 1.3.4 e KubeJS build.377 estão fisicamente presentes. A release pública 1.1.6 é NeoForge 1.21.1. CurseForge exibe o projeto como `kubejsdelight-1.21.1-1.1.6.jar`, enquanto a modlist física contém `kubejsdelight-1.1.6.jar`; a diferença nominal não é tratada como incompatibilidade sem evidência runtime.
## 5. Food/serving authority
Farmer's Delight continua authority de Cutting Board/Cooking Pot e comportamento de food/feast. Scripts podem definir conteúdo custom, mas effects/nutrition/servings precisam ser tratados como dados do item/recipe efetivamente registrado.
## 6. Riscos
1. Registry ID de knife/pie/feast muda em mundo existente.
2. Feast servings configuradas incorretamente causam consumo/dupe.
3. Cutting/Cooking recipes duplicam após reload.
4. Recipe custom conflita com outro datapack/addon culinário.
5. KubeJS/FD update quebra builder API.
6. Quest assume alimento custom que não existe nos scripts atuais.
## 7. Boundary para quests/perks
Comer/servir/cortar só deve contar a partir do evento/state real do provider, não da simples existência da recipe no JEI. Conteúdo custom só entra em quests depois que seu registry ID estiver comprovado nos scripts/runtime.
## 8. Matriz de testes
- [ ] Dedicated server inicia com KubeJSDelight 1.1.6 + FD 1.3.4 + KubeJS build.377.
- [ ] Custom knife registra uma vez e preserva tier/comportamento.
- [ ] Pie/feast servings não duplicam itens.
- [ ] Cutting Board recipe consome/produz exatamente o esperado.
- [ ] Cooking Pot recipe mantém inputs/containers corretos.
- [ ] `/reload` não duplica recipes.
- [ ] Conteúdo custom persiste por registry ID após restart.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 9. Evidências e limite
CurseForge/Modrinth e KubeJS Wiki confirmam Release 1.1.6, custom knives/pies/feasts e recipes Cutting Board/Cooking Pot. Revalidação externa em 13/09/2026 mantém `1.1.6` como a release NeoForge 1.21.1 mais recente localizada. Scripts reais do pack não foram auditados; nenhum item/recipe custom é assumido ativo sem o script correspondente.
