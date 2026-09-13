# Dynamic Trees Plus

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81f2be6aca8531f1d6e3  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: modlist física mais recente, 595 mods  
> Exportado em: 2026-09-09

## Propriedades do registro

- **Mod:** Dynamic Trees Plus
- **Arquivo JAR:** `DynamicTreesPlus-neoforge-1.21.1-1.3.2.jar`
- **Versão 1.21.1:** `1.3.2`
- **Categoria:** Worldgen; Compat
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/dynamictreesplus
- **Função:** Extensão oficial do Dynamic Trees para vegetação especial além de árvores comuns, incluindo cacti dinâmicos e huge mushrooms que crescem/caem sob contratos do engine.
- **Dependências:** Dynamic Trees. Runtime físico: Dynamic Trees 1.7.2 + Dynamic Trees Plus 1.3.2. Alguns bridges instalados consomem tipos do Plus; sources de BetterEnd/BetterNether 2.2.0 usam DTP 1.5.0 como baseline, acima do runtime atual.
- **Compatibilidade/Riscos:** DTP 1.3.2 corrige crash com Dynamic Trees 1.7.0/1.7.x, mas há drift com bridges BetterEnd/BetterNether cujo source 2.2.0 referencia DTP 1.5.0. Riscos: custom type ausente, cactus canceller falhar, huge-mushroom cap/branch state quebrar, double felling/loot e treepack data incompatível.
- **Sobreposição:** Complementa Dynamic Trees e fornece tipos especiais; não substitui o core nem bridges específicos. Worldgen estático correspondente deve ser cancelado/adaptado pela cadeia Dynamic Trees.
- **Observações:** Runtime 1.3.2. Conteúdo oficialmente documentado inclui quatro espécies distintas de cactus, Saguaro com fruit e huge red/brown mushrooms. O pack usa bridges cujos sources podem esperar DTP mais novo; tratar isso como regression gate, não como falha comprovada.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `DynamicTreesPlus-neoforge-1.21.1-1.3.2.jar`, mod id `dynamictreesplus` e versão 1.3.2. Publicação oficial confirma extensão para cacti/huge mushrooms e fix de compatibilidade com Dynamic Trees 1.7.x.
- **Histórico da decisão:** Mantido como parte do stack Dynamic Trees. Em 22/08/2026 o usuário confirmou que decidiu manter Dynamic Trees + addons após a correção de que ArborFirmaCraft e Dynamic Trees não são substitutos diretos; Dynamic Trees Plus complementa o core com tipos especiais.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — cacti/huge mushrooms, worldgen canceller, DTP↔DT 1.7.x, bridge version-drift, lifecycle/MP, riscos e testes catalogados.
- **Data da última decisão:** 2026-08-26.

## Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `DynamicTreesPlus-neoforge-1.21.1-1.3.2.jar` · mod id `dynamictreesplus` · versão `1.3.2` · NeoForge 1.21.1.

## 1. Papel no modpack
Dynamic Trees Plus é a extensão oficial que leva a engine Dynamic Trees para formas vegetais que não se encaixam no modelo de árvore comum. Na linha instalada, o conteúdo oficialmente documentado inclui cacti dinâmicos e huge mushrooms.

## 2. Authority / ownership
- **Dynamic Trees:** engine base de growth/branches/worldgen/felling.
- **Dynamic Trees Plus:** tipos especiais, comportamento e dados adicionais para cacti/mushrooms.
- **Bridges consumidores:** adapters de biome mods que usam esses tipos.

Plus não cria um segundo engine; executa sobre os contratos do core.

## 3. Build 1.3.2
A build física é 1.3.2. O changelog oficial registra correção de crash com Dynamic Trees 1.7.0/linha 1.7.x, tornando a combinação atual DT 1.7.2 + DTP 1.3.2 uma combinação intencionalmente suportada pela release do Plus.

## 4. Cacti dinâmicos
A documentação oficial descreve **quatro espécies distintas de cactus** distribuídas em desert/badlands. Cacti participam do growth/worldgen dinâmico em vez de permanecer apenas como coluna vanilla estática.

O comportamento de contato/prickling é configurável. Isso é gameplay state server-side; renderer não deve decidir dano.

## 5. Saguaro e fruits
Saguaro participa do conteúdo de cactus e possui fruits comestíveis. Fruit growth/drop deve ocorrer uma vez no servidor, persistir após reload e não ser duplicado por loot scripts paralelos.

## 6. Huge mushrooms
Dynamic Trees Plus adiciona suporte a huge red/brown mushrooms que podem crescer e cair sob a mesma filosofia de estruturas dinâmicas. Caps/stems não devem ser tratados como uma árvore vanilla comum por mods externos de felling.

## 7. Worldgen cancellation
A documentação Dynamic Trees registra o canceller `dynamictreesplus:cactus` fornecido pelo Plus. O objetivo é impedir que geração estática concorrente permaneça quando a versão dinâmica assume o placement.

Falha de cancellation pode aparecer como cactus vanilla + cactus dinâmico no mesmo contexto.

## 8. Integração com bridges
BetterEnd e BetterNether usam infraestrutura especial de mushrooms/caps e possuem source 2.2.0 compilado com **Dynamic Trees Plus 1.5.0** como baseline. O runtime físico do pack é **1.3.2**.

Essa diferença é significativa para API/custom types. Ela já está registrada nas fichas desses bridges e precisa de smoke-test real; não é prova automática de incompatibilidade nem autorização para trocar a versão física sem teste.

## 9. Data layer
Tipos, gen features, leaves/caps, species e worldgen podem ser referenciados por treepacks. Data reload precisa resolver todos os ResourceLocations consumidos pelos bridges atuais.

Boot sem crash não garante que uma species especial tenha carregado corretamente.

## 10. Client / Server
Growth, cactus damage, fruit state, felling, drops e worldgen são server-authoritative. Models/caps/branches são apresentação client-side.

Dedicated server não deve depender de classes de render.

## 11. Lifecycle
Validar:
- world creation e chunk generation;
- cactus growth;
- cactus contact/damage;
- fruit spawn/collect;
- huge mushroom growth/felling;
- chunk unload/reload;
- save/restart;
- datapack reload;
- update de Dynamic Trees;
- loading de bridges BetterEnd/BetterNether.

## 12. Multiplayer
Dois jogadores interagindo com o mesmo cactus/mushroom não podem duplicar fruit/drop/felling. O servidor decide dano e state; cliente apenas renderiza.

## 13. Riscos
1. custom type ausente por version drift;
2. DTP 1.3.2 vs baseline 1.5.0 de bridges;
3. cactus static+dynamic duplication;
4. cactus canceller falhar;
5. fruit double drop;
6. cap/branch network órfã;
7. tree-felling externo duplicar loot;
8. datapack stale;
9. client model não refletir state server;
10. update isolado de DT/DTP quebrar consumers.

## 14. Matriz de testes
1. Dedicated server boot com DT 1.7.2 + DTP 1.3.2.
2. Gerar desert/badlands e localizar cacti dinâmicos.
3. Validar quatro tipos em amostra sem duplicação estática.
4. Testar prickling/dano com configuração atual.
5. Saguaro fruit: growth/drop/save-reload.
6. Red/Brown huge mushroom: growth e felling.
7. Dois jogadores felling/coletando fruit.
8. Datapack reload.
9. BetterEnd: species que dependem de mushroom/cap types.
10. BetterNether: mushrooms/caps e worldgen.
11. Smoke-test após qualquer update de DT/DTP.

**Esta catalogação não afirma que esses testes foram executados.**

## 15. Evidências
- modlist física canônica de 08/09/2026: JAR/mod id/versão 1.3.2;
- publicação oficial Dynamic Trees Plus: cacti, Saguaro fruits, huge mushrooms;
- changelog 1.3.2: correção de compatibilidade com Dynamic Trees 1.7.x;
- wiki oficial Dynamic Trees: cactus feature canceller.

> **Boundary canônico:** Dynamic Trees Plus possui os **tipos vegetais especiais**; Dynamic Trees continua sendo a engine base.
