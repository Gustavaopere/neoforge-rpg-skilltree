# Loot Integrations

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81d2ab3dcd93fd122e9d
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Loot Integrations
- **Arquivo JAR:** `lootintegrations-1.21.1-4.7.jar`
- **Versão 1.21.1:** 4.7
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Worldgen, Exploração
- **Função:** Framework server-side/data-driven que integra resultados de uma loot table em outras via datapacks, com controle de quantidade/limite/peso e tag de exclusão; addons ampliam os alvos.
- **Dependências:** Cupboard é obrigatório no metadata, range `[1.21-1.4,)`; runtime físico: `cupboard-1.21.1-4.1.jar`. Minecraft/NeoForge 1.21.1.
- **Sobreposição:** Sobrepõe o domínio de composição/injeção de loot de LootJS, datapacks e outros loot modifiers. Não é duplicata de Lootr: Loot Integrations altera o conteúdo gerado; Lootr controla a instanciação/persistência de loot por jogador. Em conjunto, podem multiplicar o impacto econômico de tabelas enriquecidas.
- **Compatibilidade/Riscos:** Riscos principais: double-processing com LootJS/datapacks/outros modifiers; inflação econômica por múltiplas integrações e peso extra de itens modded; max_result_itemcount podendo deslocar loot original; regressão de skipMapItems causando structure-search lag; state stale após reload; e divergência de source metadata, pois o commit rotulado 4.7 ainda declara mod_version=4.6 no Gradle.
- **Observações:** Release 4.7 oficial para NeoForge 1.21.1. A mudança upstream 4.7 corrige mapas sendo gerados mesmo quando desabilitados. Config auditada: skipMapItems=true, skipExistingItems=false, moddedItemWeight=3, debugOutput=false e showcontainerloottable=false.
- **Procedência:** modlist.txt física atual + CurseForge oficial da release `lootintegrations-1.21.1-4.7.jar` + source upstream someaddons/LootIntegrations, incluindo commit `1a5b83f7c9710025542454b4e7af1e16a0e9acb8` rotulado 4.7 e classes de loot/config; o Gradle desse commit ainda declara 4.6, portanto não foi tratado como pin metadata-perfeito do artefato final.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/loot-integrations
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — release 4.7 reconciliada; datapack schema, recursion guard, weighting, fill limit, tag ignored, Cupboard, map fix, server-side boundary, coexistência com Lootr/LootJS, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

> 🎁 **ESCOPO CANÔNICO.** Runtime físico: `lootintegrations-1.21.1-4.7.jar`, mod id `lootintegrations`, versão `4.7`. Loot Integrations é um framework **server-side/data-driven** que injeta resultados de uma loot table em outras loot tables. Ele altera composição/distribuição de loot; não substitui Lootr e não é authority sobre inventário por jogador.

## 1. Identidade e versionamento
- **Mod:** Loot Integrations.
- **JAR:** `lootintegrations-1.21.1-4.7.jar`.
- **Versão instalada/publicada:** `4.7`.
- **Minecraft/loader:** 1.21.1 / NeoForge.
- **Mod id:** `lootintegrations`.
- **Release oficial 1.21.1:** publicada em 11/06/2025.
- **Ambiente publicado:** only required on server side.
- **Source:** o commit `1a5b83f7c9710025542454b4e7af1e16a0e9acb8` é explicitamente rotulado `4.7: Fix maps generating when disabled` e contém o comportamento da 4.7, porém seu `gradle.properties` ainda declara `mod_version=4.6`. Portanto ele é **evidência de código da mudança 4.7**, não um pin metadata-perfeito do artefato final.

## 2. Papel no modpack
O mod permite que datapacks definam uma loot table fonte e a integrem em uma ou mais loot tables alvo. O projeto oficial resume a função como integração de loot entre loot tables para compatibilidade entre mods e estruturas. Também fornece tabelas internas de categorias que addons podem reutilizar.

## 3. Authority / ownership
- **Loot table fonte/alvo:** continuam pertencendo aos mods/datapacks que as registram.
- **Loot Integrations:** authority sobre a regra de composição/injeção configurada em seus JSONs e sobre a seleção adicional aplicada no momento de geração.
- **Lootr:** authority sobre instanciamento/personalização de containers por jogador; não deve ser confundido com composição de loot.
- **LootJS/outros modifiers:** podem operar sobre o mesmo domínio e exigem teste de ordem/double-processing.

## 4. Loader de dados e resource reload
`LootModifierManager` estende `SimpleJsonResourceReloadListener` e carrega recursos da pasta `loot`. No reload, ele limpa `lootOptionsMap`, valida que o namespace do arquivo seja `lootintegrations`, parseia cada integração e indexa os modifiers pelas loot tables alvo. Isso torna **datapack/resource reload** um lifecycle central do mod.

## 5. Schema JSON confirmado no source 4.7-change
`GlobalLootModifierIntegration.read(...)` reconhece:
- `loot_table` — loot table **fonte** da qual itens adicionais serão gerados;
- `integrated_loot_tables` — objeto que mapeia cada loot table **alvo** para uma quantidade inteira de escolhas;
- `max_result_itemcount` — opcional; limita o tamanho final pretendido do loot, com default interno `27` quando ausente.
Arquivos fora do namespace `lootintegrations` são ignorados com warning.

## 6. Aplicação e recursion guard
No preenchimento de loot, `LootModifierManager.applyTo(...)` resolve o ID da loot table original, procura modifiers registrados e usa um `Set<ResourceLocation> applying` como guard para não reaplicar recursivamente a mesma tabela enquanto ela já está sendo processada. Isso reduz risco de recursão direta, mas cadeias complexas de integrações ainda precisam de validação.

## 7. Geração dos itens adicionais
Para cada modifier aplicável, o mod cria um novo `LootContext` com `queriedLootTableId` apontando para a tabela fonte e executa `getRandomItems(...)` nessa tabela. O resultado é agregado antes da seleção e então itens são escolhidos por peso até o número configurado para a tabela alvo.

## 8. Agregação e limite do container
Antes de inserir itens, o mod agrega stacks por item. Quando a soma entre loot existente e itens adicionais excede `fillSize`, ele agrega o loot original e pode remover entradas aleatórias para respeitar o limite. Isso significa que `max_result_itemcount` não é apenas um teto cosmético: em certos casos pode alterar quais entradas originais permanecem na lista final.

## 9. Tag de exclusão
O source define a tag de item `lootintegrations:ignored`. Itens da loot table fonte marcados com essa tag são descartados durante a agregação dos itens adicionais. Isso é uma superfície importante para datapacks que precisam impedir determinados itens de serem propagados por integrações amplas.

## 10. Configuração comum confirmada
`CommonConfiguration` confirma os seguintes defaults na revisão da mudança 4.7:
- `showcontainerloottable = false` — pode exibir no chat a loot table de containers na primeira abertura;
- `debugOutput = false` — logging detalhado do loot adicionado;
- `skipMapItems = true` — evita gerar mapas como loot adicional para reduzir lag de busca de estruturas; mapas do loot original permanecem;
- `skipExistingItems = false` — quando true, evita adicionar item idêntico já presente no loot;
- `moddedItemWeight = 3` — o código calcula peso `1` para namespace `minecraft` e `moddedItemWeight + 1` para itens modded, portanto o default produz peso efetivo `4` para itens modded versus `1` para vanilla.

## 11. Mudança específica da 4.7
O commit upstream 4.7 corrige **maps generating when disabled**. A mudança passa a aplicar o estado `disabledMaps()` ao `LootContext` novo usado para gerar a loot table fonte, em vez de atuar apenas sobre o contexto original. O risco operacional associado é lag causado por mapas que disparam busca de estruturas quando deveriam estar suprimidos no loot adicional.

## 12. Dependência Cupboard
O metadata NeoForge do source declara `cupboard` como dependência obrigatória com range `[1.21-1.4,)`. A modlist física contém `cupboard-1.21.1-4.1.jar`, portanto a dependência está satisfeita no runtime atual. O projeto público também informa que versões mais novas requerem Cupboard.

## 13. Client / Server
A página oficial diz **Only required on serverside**. O comportamento principal — leitura de datapacks, resolução de loot tables e modificação da lista gerada — é server-side. O cliente não deve ser usado como authority da composição. A opção de mostrar a loot table em chat é uma função de diagnóstico/apresentação disparada a partir de dados do container, não authority de loot.

## 14. Integrações concretas presentes no pack
Imediatamente após o mod base, a modlist contém `lootintegrations_cataclysm-1.2.jar`; também há outros addons Loot Integrations no pack, incluindo variantes para YUNG, vanilla/randomized loot, Integrated Structures e Ice and Fire. Esses addons são **dados/bridges consumidores do framework** e não devem ser fundidos conceitualmente com o mod base.

## 15. Coexistência com Lootr
Loot Integrations muda **quais itens** entram no resultado de uma loot table. Lootr muda a forma como containers/loot são instanciados e persistidos por jogador em multiplayer. Não são duplicatas. O risco combinado é econômico: uma tabela enriquecida por Loot Integrations pode ser instanciada separadamente por Lootr para vários jogadores, multiplicando a quantidade total disponível no servidor.

## 16. Coexistência com LootJS/datapacks/outros modifiers
Qualquer outro sistema que altere a mesma loot table pode atuar antes/depois ou adicionar conteúdo adicional. Devem ser testados:
- double injection;
- ordem de modifiers;
- loops indiretos entre tabelas;
- limites `max_result_itemcount` removendo loot inesperadamente;
- reload deixando mapas/config antigos em memória.
Nenhuma ordem específica entre mods externos é presumida sem teste runtime.

## 17. Multiplayer e economia
A geração ocorre no servidor, mas o impacto é global: mudanças de peso, número de escolhas e addons podem aumentar disponibilidade de equipamento, materiais, alimentos e itens raros. Em um pack com progressão extensa, a validação precisa medir **power scaling e densidade de recompensa**, não apenas ausência de crash.

## 18. Riscos técnicos
1. **Double-processing** com LootJS, datapacks ou outros loot modifiers.
2. **Recursive integration** em cadeias complexas de loot tables; há guard direto, mas não assumir segurança para toda topologia.
3. **Economy inflation** por itens modded com peso elevado e múltiplos addons.
4. **Original-loot displacement** quando `max_result_itemcount` força redução da lista.
5. **Map search lag** se `skipMapItems` falhar/regredir; a 4.7 corrige exatamente esse caso.
6. **Reload stale state** se integrações/configs não forem reconstruídas corretamente.
7. **Component collapsing risk:** agregação é por `Item` e só soma stacks quando `ItemStack.isSameItemSameComponents`; validar itens com componentes distintos.
8. **Server/client deployment mismatch** em servidores onde o mod é instalado somente no lado correto.
9. **Source metadata drift:** commit da mudança 4.7 ainda declara 4.6 no Gradle.

## 19. Matriz de testes
- [ ] Dedicated server inicia com Loot Integrations 4.7 + Cupboard 4.1.
- [ ] Cliente sem o mod conecta quando a configuração do servidor permitir o modelo server-only publicado.
- [ ] Datapack reload limpa e recria `lootOptionsMap` sem duplicar modifiers.
- [ ] JSON com `loot_table` + `integrated_loot_tables` injeta exatamente a quantidade configurada.
- [ ] `max_result_itemcount` respeita o teto sem comportamento econômico inesperado.
- [ ] Item em `lootintegrations:ignored` não é propagado como loot adicional.
- [ ] `skipExistingItems=true` não entra em loop quando todas as opções candidatas já existem.
- [ ] `moddedItemWeight` altera seleção como esperado, inclusive default 3 → peso efetivo 4 no código auditado.
- [ ] `skipMapItems=true` evita geração adicional de mapas/structure-search e não remove mapas do loot original.
- [ ] Lootr + Loot Integrations não duplica o mesmo preenchimento além do modelo intencional por jogador.
- [ ] LootJS/outros modifiers não produzem double injection após `/reload`.
- [ ] Multiplayer/restart preserva apenas state derivado de datapack/config, sem cache stale.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 20. Evidências e limites
Evidências usadas: modlist física atual; CurseForge oficial da release `lootintegrations-1.21.1-4.7.jar`; página oficial do projeto; source upstream `someaddons/LootIntegrations`; commit `1a5b83f7...` rotulado como mudança 4.7; `LootModifierManager`, `GlobalLootModifierIntegration`, `CommonConfiguration` e `neoforge.mods.toml`; presença física de Cupboard 4.1. **Limite:** o Gradle do commit 4.7 ainda declara 4.6, portanto a equivalência byte-for-byte com o JAR publicado não foi afirmada.
