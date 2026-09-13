# KubeJSDelight — 1.1.6

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3d369db9f0db8132b306eb5b73bba252  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-10

## Propriedades do registro

- **Mod:** KubeJSDelight
- **Arquivo JAR:** `kubejsdelight-1.1.6.jar`
- **Versão 1.21.1:** `1.1.6`
- **Categoria:** Compat; Automação; Comida
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/kubejs-delight
- **Função:** Integra Farmer's Delight ao KubeJS para registrar knives/pies/feasts custom e criar recipes de Cutting Board/Cooking Pot por scripts.
- **Dependências:** Farmer's Delight 1.3.4 + KubeJS 2101.7.2-build.374 estão fisicamente presentes. NeoForge 1.21.1.
- **Compatibilidade/Riscos:** Riscos: registry migration de items custom, servings/food duplication, recipe conflicts/reload e API drift KubeJS/Farmer's Delight. Filename público e físico diferem; runtime físico é authority.
- **Sobreposição:** Complementa Farmer's Delight/KubeJS; recipes/scripts podem tocar as mesmas rotas que outros addons culinários, mas isso é composição configurável.
- **Observações:** JAR físico `kubejsdelight-1.1.6.jar`, mod id `kubejsdelight`, runtime 1.1.6. A página pública pode exibir `kubejsdelight-1.21.1-1.1.6.jar`; não sobrescrever o filename físico por nomenclatura de distribuição.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge/Modrinth oficiais KubeJS Delight 1.1.6 + KubeJS Wiki Farmer's Delight.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — corpo vazio corrigido; custom knives/pies/feasts, Cutting Board/Cooking Pot recipes, lifecycle e risks catalogados para 1.1.6.
- **Data da última decisão:** 2026-09-06

## Dossiê operacional — padrão Alex's Mobs

> 🍲 **ESCOPO CANÔNICO.** Runtime físico: `kubejsdelight-1.1.6.jar`, mod id `kubejsdelight`, versão `1.1.6`. É a integração Farmer's Delight ↔ KubeJS para conteúdo e recipes customizados; o filename físico curto é autoridade desta instância.

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
Farmer's Delight 1.3.4 e KubeJS build.374 estão fisicamente presentes. A release pública 1.1.6 é NeoForge 1.21.1. CurseForge exibe o projeto como `kubejsdelight-1.21.1-1.1.6.jar`, enquanto a modlist física contém `kubejsdelight-1.1.6.jar`; a diferença nominal não é tratada como incompatibilidade sem evidência runtime.

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
- [ ] Dedicated server inicia com KubeJSDelight 1.1.6 + FD 1.3.4 + KubeJS build.374.
- [ ] Custom knife registra uma vez e preserva tier/comportamento.
- [ ] Pie/feast servings não duplicam itens.
- [ ] Cutting Board recipe consome/produz exatamente o esperado.
- [ ] Cooking Pot recipe mantém inputs/containers corretos.
- [ ] `/reload` não duplica recipes.
- [ ] Conteúdo custom persiste por registry ID após restart.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 9. Evidências e limite
CurseForge/Modrinth e KubeJS Wiki confirmam Release 1.1.6, custom knives/pies/feasts e recipes Cutting Board/Cooking Pot. Scripts reais do pack não foram auditados; nenhum item/recipe custom é assumido ativo sem o script correspondente.
