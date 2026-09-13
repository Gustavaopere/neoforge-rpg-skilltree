# FarmersStructures — 1.0.6

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81bf8437e074760c198a  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-09

## Propriedades do registro

- **Mod:** FarmersStructures
- **Arquivo JAR:** `FarmersStructures-1.0.6-1.21.1_neoforge.jar`
- **Versão 1.21.1:** `1.0.6`
- **Categoria:** Worldgen; Exploração; Comida
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/farmers-structures
- **Função:** Worldgen/content addon tematizado em Farmer's Delight, adicionando estruturas de exploração com loot e cenários que apresentam/empregam conteúdo FD.
- **Dependências:** Farmer's Delight 1.3.4 é o ecossistema-alvo e está fisicamente presente.
- **Compatibilidade/Riscos:** Riscos: densidade/spacing com muitos structure mods, overlap físico, loot inflation, placement/biome drift, chunks antigos vs novos e block entity/NBT drift após updates. Marker MCreator é implementação, não incompatibilidade automática.
- **Sobreposição:** Sobreposição de densidade/worldgen com outros structure packs; Farmer's Delight permanece provider dos foods/blocks/workstations usados nas structures.
- **Observações:** Changelog oficial 1.0.6 anuncia 40 novas estruturas e cita cat house, flower beds, pond with frogs e blacksmith workshop + mine. Não converter esse delta em total absoluto sem inventário do JAR.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `FarmersStructures-1.0.6-1.21.1_neoforge.jar`, mod id `farmers_structures`, versão 1.0.6, marker MCreator e SHA-1 94262398153d7ca5dc9ffb93e49acdbad85af223.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — FarmersStructures 1.0.6; structure/worldgen authority, 40 new structures in release, loot, chunk lifecycle, multiplayer, density risks e testes catalogados.
- **Data da última decisão:** 2026-08-26

## Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `FarmersStructures-1.0.6-1.21.1_neoforge.jar` · mod id `farmers_structures` · versão `1.0.6` · NeoForge 1.21.1 · marcado fisicamente como MCreator mod. Farmer's Delight 1.3.4 está presente.

## 1. Papel no modpack
FarmersStructures é um content/worldgen addon tematizado em Farmer's Delight. Ele adiciona estruturas de exploração com loot e cenários que apresentam elementos do ecossistema culinário/agropecuário em geração natural.

## 2. Authority / ownership
- **FarmersStructures:** templates/structure placement, loot/blocks próprios que registrar e integração temática das estruturas.
- **Farmer's Delight:** itens, crops, foods e mechanics FD usados nas construções/loot.
- **Minecraft/worldgen stack:** placement final por chunk/biome e coexistência com outras structures.

A estrutura não deve criar uma cópia paralela das mechanics FD.

## 3. Escopo publicado da 1.0.6
A changelog da 1.0.6 anuncia **40 novas estruturas** nesta atualização e cita exemplos como cat house, flower beds, pond with frogs e blacksmith workshop com mina. Materiais públicos também descrevem o projeto como um grande conjunto de estruturas Farmer's Delight.

Para evitar inflação de números, esta ficha não converte automaticamente isso em “total exato” sem inventário do JAR: o dado seguro é que a 1.0.6 adiciona 40 novas estruturas à linha existente.

## 4. Estruturas como onboarding
O propósito do projeto é apresentar/usar mechanics de Farmer's Delight através do mundo. Isso pode fornecer alimentos, crops, workstations ou loot contextual. O item encontrado continua pertencendo ao mod que o registra.

## 5. Worldgen e chunks existentes
Estruturas aparecem em chunks novos conforme placement/biome constraints. Ausência em território já gerado não é falha. Updates do mod podem criar fronteiras de conteúdo entre chunks antigos e novos; não regenere estruturas em área existente sem plano explícito.

## 6. Loot
Loot de estruturas deve ser server-authoritative e data-driven quando tables forem usadas. Com muitos mods de estrutura e loot, verificar inflação de food/resources e duplicação de itens raros. Loot table override deve ser analisado por ID.

## 7. Farmer's Delight 1.3.4
FD é ecossistema-alvo, não substituto. FarmersStructures pode colocar workstations/foods FD, mas o comportamento desses blocos/itens continua regido pelo Farmer's Delight atual.

## 8. MCreator marker
A modlist física marca o JAR como MCreator mod. Isso é característica de implementação, não evidência automática de baixa qualidade ou incompatibilidade. O que importa operacionalmente é validar registries, worldgen, dedicated server e update behavior.

## 9. Client / Server
**Servidor:** structure placement, loot, mobs/blocks colocados e world state.

**Cliente:** render normal dos blocos/entities já sincronizados.

O mod precisa ser tratado como server-required para worldgen consistente.

## 10. Lifecycle
Validar new world, new chunks em world existente, locate/structure search quando suportado, chunk unload/reload, server restart, datapack reload, structure block entities e update/removal do mod.

## 11. Multiplayer
Geração deve ocorrer uma vez por chunk, independente de número de players. Loot containers devem ter comportamento consistente e não duplicar por race. Exploração simultânea não pode gerar duas cópias da mesma placement pass.

## 12. Integrações no pack
Explorer's Compass pode localizar estruturas registradas quando compatíveis com o registry; ele não gera nada. O pack possui muitos worldgen/structure mods, então densidade e spacing são riscos reais. Farmer's Delight fornece conteúdo temático.

## 13. Riscos
1. densidade excessiva de structures;
2. overlap físico entre structures de providers diferentes;
3. loot inflation;
4. placement/biome config drift;
5. estruturas da 1.0.6 aparecerem somente em chunks novos;
6. block entity colocado com NBT incompatível após update FD;
7. structure ID/tag mudar e locator/quest quebrar;
8. server/client pack divergente;
9. MCreator-generated registrations causarem conflito de ID apenas se houver evidência concreta;
10. remover o mod deixando chunks com conteúdo órfão.

## 14. Matriz de testes
1. Dedicated server new world.
2. Gerar grande amostra de chunks e medir densidade.
3. Confirmar exemplos 1.0.6 como cat house/flower beds/pond/blacksmith workshop quando encontrados.
4. Abrir loot em singleplayer e multiplayer.
5. Farmer's Delight blocks dentro das structures.
6. Explorer's Compass em structures registradas, quando aplicável.
7. Chunk unload/reload.
8. Existing world gerando chunks novos.
9. `/reload` e restart.
10. Smoke-test de spacing contra outros grandes structure packs.

**Esta catalogação não afirma que esses testes foram executados.**

## 15. Evidências
- modlist física canônica: JAR/mod id/version/hash e marker MCreator;
- CurseForge oficial 1.0.6: release NeoForge 1.21.1, função e changelog com 40 novas estruturas/exemplos;
- Farmer's Delight 1.3.4 fisicamente presente como provider temático.

> **Boundary canônico:** FarmersStructures controla **worldgen/structures e loot que registra**; Farmer's Delight continua authority dos foods, blocks e workstations usados nelas.