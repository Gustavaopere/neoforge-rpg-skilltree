# Farmer's Delight: Extended — 1.21.1-0.2.2

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81039387d64068ff0074  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-09

## Propriedades do registro

- **Mod:** Farmer's Delight: Extended
- **Arquivo JAR:** `farmersdelight_extended-1.21.1-0.2.2.jar`
- **Versão 1.21.1:** `1.21.1-0.2.2`
- **Categoria:** Comida; Compat
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/farmersdelight-extended
- **Função:** Bridge/expansão de recipes entre Farmer's Delight e Create, focada em compatibilidade culinária sem criar um subsistema paralelo de crops/mobs.
- **Dependências:** Farmer's Delight 1.3.4 + Create 6.0.10 estão fisicamente presentes.
- **Compatibilidade/Riscos:** Riscos: duplicate recipe com Central Kitchen/outros compats, double-processing, broad ingredient tags, container-item dupe/loss, stale recipes após `/reload` e version drift Create/FD.
- **Sobreposição:** Pode coincidir em recipes com Central Kitchen/Create Food/outros compat packs. Só classificar redundância após comparar recipe ID, type, inputs e outputs.
- **Observações:** Preservar a diferença entre metadata `1.21.1-0.2.2` e release pública `0.2.2`. A documentação pública confirma foco em recipes Create↔Farmer's Delight e ausência de novos crops/mobs como requisito do addon.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `farmersdelight_extended-1.21.1-0.2.2.jar`, mod id `farmersdelight_extended`, metadata version `1.21.1-0.2.2` e SHA-1 45200860ea91640de38abb059e950f8abed5f055. Release pública curta permanece 0.2.2.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Farmer's Delight: Extended 0.2.2; recipe-bridge Create↔FD, ownership, reload/automation, multiplayer, overlaps, riscos e testes catalogados.
- **Data da última decisão:** 2026-08-26

## Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `farmersdelight_extended-1.21.1-0.2.2.jar` · mod id `farmersdelight_extended` · metadata version `1.21.1-0.2.2` · NeoForge 1.21.1. Bases físicas: Farmer's Delight 1.3.4 + Create 6.0.10.

## 1. Papel no modpack
Farmer's Delight: Extended é uma bridge/expansão de recipes entre Farmer's Delight e Create. O projeto enfatiza preencher lacunas de integração culinária sem exigir novos crops ou mobs; seu principal patrimônio é o conjunto de recipes/integrações, não um subsistema agrícola paralelo.

## 2. Authority / ownership
- **Farmer's Delight:** ingredients, foods, cooking/cutting/workstation semantics base.
- **Create:** processing/kinetic automation e recipe types Create.
- **Extended:** recipes de ponte e pequenos conteúdos necessários à integração que sua build realmente registra.

O addon não deve liquidar um processamento em duas máquinas ao mesmo tempo nem redefinir o item provider.

## 3. Dependências físicas
Farmer's Delight 1.3.4 e Create 6.0.10 estão presentes no pack. A metadata física do addon inclui o prefixo de Minecraft na versão (`1.21.1-0.2.2`), enquanto a release pública costuma ser apresentada como `0.2.2`; manter ambas sem normalização indevida.

## 4. Recipe bridge
O escopo confirmado publicamente é criar receitas adicionais e compatibilidade alimentar entre Create e Farmer's Delight. Recipe IDs, ingredients e outputs exatos devem ser lidos do JAR/datapack quando uma integração específica for implementada; esta ficha não inventa lista de recipes não auditada.

## 5. Sem crops/mobs próprios como requisito
A descrição pública destaca que a proposta não depende de novos crops ou mobs. Isso distingue Extended de addons como Expanded Delight, que possuem conteúdo agrícola/worldgen mais amplo.

## 6. Automação Create
Quando uma recipe Extended usa machine/recipe type Create, Create permanece authority do processing cycle, stress/speed e machine state; Extended apenas fornece a recipe. Output deve ocorrer uma única vez e container items precisam seguir o contrato da recipe.

## 7. Farmer's Delight side
Ingredients/foods FD continuam com seus tags, food properties e recipe semantics base. O bridge não deve alterar servings, hunger/effects ou Cutting Board/Cooking Pot state fora de recipes explicitamente registradas.

## 8. Configuração e dados
A maior superfície operacional é datapack/recipe data. `/reload` precisa remover/adicionar recipes sem cache stale. Tags amplas compartilhadas por muitos addons culinários são um risco maior que classloading.

## 9. Integrações e sobreposição
O pack também possui Create: Central Kitchen/food integrations e vários addons FD. Sobreposição temática não prova duplicação. Antes de remover ou desativar uma recipe, comparar **recipe ID + recipe type + inputs + output + provider**.

## 10. Client / Server
Recipes e processamento são server-authoritative. O cliente apresenta JEI/recipe viewer, models e animações da máquina. A recipe exibida precisa corresponder ao datapack efetivamente carregado no servidor.

## 11. Lifecycle
Validar server boot, recipe reload, tag reload, Create machine start/cancel/finish, chunk unload durante processamento, restart, container items, automation input/output e updates de Create/FD.

## 12. Multiplayer
Dois jogadores/automations não podem concluir a mesma recipe duas vezes. Inventories e outputs devem convergir no server. Fake players/hoppers/belts precisam respeitar o mesmo recipe contract.

## 13. Riscos
1. recipe duplicate com Central Kitchen/outro compat;
2. double-processing;
3. tag ampla escolher ingredient não intencional;
4. container item dupe/loss;
5. `/reload` deixar recipe cache stale;
6. version drift Create 6.x/FD 1.3.x;
7. recipe viewer mostrar state diferente do servidor;
8. output balanceado para versão anterior das bases;
9. machine recipe type renomeado por update;
10. classificar Extended como content mod amplo e duplicar sua função em mod próprio.

## 14. Matriz de testes
1. Dedicated server boot com Create 6.0.10 + FD 1.3.4.
2. Dump/JEI das recipes `farmersdelight_extended` efetivamente registradas.
3. Executar amostra de cada recipe type Create usado.
4. Recipe manual equivalente, quando existir, sem duplicação.
5. Belt/funnel/hopper automation.
6. Container items.
7. Chunk unload no meio do processing.
8. `/reload` e reteste.
9. Dois jogadores/duas máquinas concorrentes.
10. Comparar recipes contra Central Kitchen e demais compats.

**Esta catalogação não afirma que esses testes foram executados.**

## 15. Evidências
- modlist física canônica: JAR/mod id/metadata version/hash + Create/FD atuais;
- CurseForge oficial Farmer's Delight: Extended 0.2.2 para NeoForge 1.21.1: função de bridge de recipes e foco sem novos crops/mobs;
- estado do pack para identificar overlaps concretos.

> **Boundary canônico:** Extended é authority apenas das **recipes de integração que registra**; Create controla a automação/máquinas e Farmer's Delight controla o conteúdo culinário base.