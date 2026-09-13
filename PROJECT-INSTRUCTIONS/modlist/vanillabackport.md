# VanillaBackport

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c469db9f0db81c386b1f6574259abaf
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `VanillaBackport-neoforge-1.21.1-1.1.7.10.jar`, mod id `vanillabackport`, runtime `1.1.7.10`; Delightful Backport 1.0 e Dynamic Trees for VanillaBackport 1.6.0 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, VanillaBackport 1.1.7.10 e as bridges citadas estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** VanillaBackport
- **Arquivo JAR:** `VanillaBackport-neoforge-1.21.1-1.1.7.10.jar`
- **Versão 1.21.1:** 1.1.7.10
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL, Worldgen, Mobs
- **Função:** Backporta para Minecraft 1.21.1 blocos, itens, mobs e mecânicas vanilla posteriores, procurando preservar comportamento, recipes, tags e apresentação próximos ao vanilla.
- **Dependências:** NeoForge 1.21.1. Bridges fisicamente presentes: Delightful Backport 1.0 e Dynamic Trees for VanillaBackport 1.6.0.
- **Sobreposição:** Pode compartilhar features individuais com outros backports/addons; não há justificativa para remoção integral sem comparação registry-by-registry.
- **Compatibilidade/Riscos:** Risco principal é duplicação pontual com outros backports: comparar por registry/feature. Validar tags/recipes/worldgen e bridges atuais; não hardcodar IDs de features futuras sem inspecionar a build 1.1.7.10.
- **Observações:** Mod id `vanillabackport`, runtime 1.1.7.10. Bridges presentes não substituem o provider; apenas adaptam conteúdo backportado a Farmer's Delight/Dynamic Trees.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge/Modrinth oficiais VanillaBackport 1.1.7.10 + bridges físicos Delightful Backport 1.0 e Dynamic Trees for VanillaBackport 1.6.0. Changelog exato 1.1.7.10 revalidado; nenhum registry/worldgen/runtime QA executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/vanillabackport
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — VanillaBackport 1.1.7.10 permanece exatamente instalado e continua a release NeoForge 1.21.1 pertinente; authority, bridges, riscos e deltas exatos da build preservados.
- **Histórico da decisão:** Primeira ocorrência registrada na auditoria a partir da modlist de 22/08/2026 às 19:31Z. Está instalado, mas não há decisão final nem motivo de adição registrado nesta ficha; não inferir finalidade além do que o próprio mod fornece.
- **Data da última decisão:** 2026-09-09

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `VanillaBackport-neoforge-1.21.1-1.1.7.10.jar`, mod id `vanillabackport`, versão `1.1.7.10`. O mod backporta conteúdo e mecânicas vanilla posteriores para 1.21.1. Ele é provider do conteúdo backportado; bridges específicas devem consumir seus registries/tags reais, sem duplicar esse conteúdo em mod próprio.

## 1. Identidade e função
VanillaBackport porta blocos, itens, mobs e mecânicas de versões vanilla posteriores para Minecraft 1.21.1, procurando manter comportamento, receitas, tags e apresentação próximos ao jogo oficial.

A build `1.1.7.10` é uma Release NeoForge 1.21.1 presente na modlist física atual.

## 2. Authority
VanillaBackport é authority das versões backportadas de seu conteúdo. Para integrações próprias:
- usar registry/tag/item/entity real do backport;
- não criar um segundo Pale Oak/Creaking ou outro conteúdo equivalente apenas para preencher lacuna;
- não inferir IDs de versões vanilla posteriores sem ler o registry do JAR instalado;
- quando o Minecraft base 1.21.1 não possui uma feature, tratar o provider backport como source-of-truth daquela feature no pack.

## 3. Bridges presentes no pack
A modlist física confirma duas integrações diretas:
- `Delightful-Backport-1.0-1.21.1-neoforge.jar` — adapta conteúdo backportado, como ovos azuis/marrons, ao Farmer's Delight;
- `dtvanillabackport-1.21.1-1.6.0.jar` — Dynamic Trees for VanillaBackport, cobrindo árvores/conteúdo vegetal compatível como Pale Oak conforme o addon.

Essas bridges não se tornam authorities dos itens/mobs backportados; apenas adaptam o conteúdo do provider aos respectivos ecossistemas.

## 4. Integração com datapacks e tags
Como o objetivo do projeto é aproximar o comportamento vanilla posterior, recipes/tags/loot/worldgen podem participar de sistemas externos como qualquer conteúdo registrado.

Para mods próprios, preferir tags/registries reais quando disponíveis e evitar hardcode de item IDs até inspeção do JAR/source da build 1.1.7.10.

## 5. Client / server e lifecycle
O mod é Client & Server porque adiciona conteúdo e comportamento real ao jogo.

Validar:
- dedicated server boot;
- registry/data loading;
- worldgen em chunks novos quando houver conteúdo mundial;
- save/reload;
- datapack/resource reload;
- entities/mobs backportados em multiplayer;
- recipes/tags usadas por bridges.

## 6. Riscos de sobreposição
O risco principal é **duplicação pontual** com outros mods que também backportem uma feature específica. A presença de múltiplos backports não significa incompatibilidade integral; a comparação deve ser por registry/feature.

Riscos concretos:
- dois mods registrando versões equivalentes da mesma feature com IDs diferentes;
- recipes/tags de bridges apontando para provider errado;
- loot/worldgen duplicado;
- datapacks assumindo comportamento vanilla posterior não reproduzido exatamente pela versão instalada.

## 7. Boundary para RPG/quests
Conteúdo backportado não vira progressão própria só por ser novo. Perks/quests só devem reagir a mobs/biomas/itens específicos quando houver identidade registry real e causalidade apropriada.

Não conceder Mastery por permanecer próximo de conteúdo backportado ou por reload/rebuild de worldgen.

## 8. Matriz de testes
- [ ] Dedicated server inicia com VanillaBackport 1.1.7.10.
- [ ] Registry/data loading sem duplicate-id ou tag errors.
- [ ] Conteúdo-chave aparece com IDs/tags esperados da build atual.
- [ ] Delightful Backport reconhece seus ingredientes sem duplicar recipes.
- [ ] Dynamic Trees for VanillaBackport reconhece o conteúdo vegetal suportado.
- [ ] Save/reload preserva blocks/entities backportados.
- [ ] Datapack/resource reload não perde recipes/tags.
- [ ] Worldgen smoke em chunks novos para features mundiais usadas pelo pack.
- [ ] Não há duas implementações concorrentes da mesma feature crítica no snapshot atual.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 9. Evidências
- **Modlist física 08/09/2026:** VanillaBackport 1.1.7.10, Delightful Backport 1.0 e Dynamic Trees for VanillaBackport 1.6.0.
- CurseForge/Modrinth oficiais VanillaBackport: backport de features vanilla modernas para 1.21.1, Release NeoForge.
- Guia Gameplay do projeto: conteúdo backportado e bridges atuais.

## 10. Limitação
Esta etapa não enumerou todo o catálogo registry da 1.1.7.10. IDs e semântica de features específicas devem ser extraídos do JAR/source antes de integração programática provider-specific.

## 11. Revalidação física — 11/09/2026
A modlist física mantém exatamente `VanillaBackport-neoforge-1.21.1-1.1.7.10.jar`, mod id `vanillabackport`, versão `1.1.7.10`. Esta continua sendo a release NeoForge pertinente para 1.21.1 no catálogo oficial.

O changelog exato da 1.1.7.10 registra três deltas: remoção da compatibilidade Caverns & Chasms; adição de config para alternar bundle rendering e dyed recipes; e rework do mob cap de Sulfur Cube. Delightful Backport `1.0` e Dynamic Trees for VanillaBackport `1.6.0` permanecem bridges físicas. Nenhum registry audit completo, toggle de config, spawn/worldgen ou teste das bridges foi executado nesta recatalogação.
