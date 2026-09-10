# [Let's Do] Meadow

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db81a1a6bef35cdceaff5a
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** [Let's Do] Meadow
- **Arquivo JAR:** `letsdo-meadow-neoforge-1.4.8.jar`
- **Versão 1.21.1:** 1.4.8
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Comida, Worldgen, Exploração
- **Função:** Conteúdo alpino com animals/milk, cheese processing, Fondue/Cooking Cauldron, Watering Can/Woodcutter, Pine/Felt furniture, flora e worldgen temático.
- **Dependências:** NeoForge 1.21.1 + Architectury. Furniture 1.1.4 fisicamente presente adiciona compatibilidade explícita com wood do Meadow.
- **Sobreposição:** Cruza animal husbandry, cooking e worldgen com outros mods do pack; Meadow permanece authority de seus entities/recipes/BlockEntities. Furniture apenas integra wood variants.
- **Compatibilidade/Riscos:** 1.4.8 corrige Wardrobe drop/armor loss, Fur Armor durability/stacking, Pine furniture tags/models; source lineage também corrige Cooking Frame item drop. Riscos incluem inventory loss/dupe, wood compat, husbandry overlap e worldgen overlap.
- **Observações:** Branch atual já está em 1.4.9; a ficha usa commit histórico `406d286497a32f2a2aeec7b194038aa9c4f3449a`, que ainda declara exatamente 1.4.8. Não se afirma equivalência binária byte-a-byte.
- **Procedência:** modlist.txt física atual + publicação oficial Meadow 1.4.8 + source histórico Let-s-Do-Collection/Meadow commit 406d286497a32f2a2aeec7b194038aa9c4f3449a e commits imediatamente anteriores da release.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/lets-do-meadow
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — source lineage 1.4.8 pinado; animals/milk, cheese/cooking, Wardrobe/Cooking Frame persistence, Fur Armor, Pine/Furniture compat, worldgen, risks e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

> 🧀 **ESCOPO CANÔNICO.** Runtime físico: `letsdo-meadow-neoforge-1.4.8.jar`, mod id `meadow`, versão `1.4.8`. Meadow combina ecossistema alpino, animais/milk, processamento de queijo, cooking, ferramentas QoL e blocos/furniture de Pine/Felt.

## 1. Identidade e source pin histórico
A branch oficial `Let-s-Do-Collection/Meadow` atualmente já avançou para 1.4.9, portanto ela não é usada como source corrente da build física. Para 1.4.8 foi localizado o commit histórico `406d286497a32f2a2aeec7b194038aa9c4f3449a`, de 24/02/2026, cujo `gradle.properties` ainda declara exatamente `mod_version=1.4.8`, Minecraft 1.21.1, NeoForge 21.1.205 e Architectury 13.0.8. Os commits imediatamente anteriores desse mesmo dia correspondem às correções publicadas da release 1.4.8. Isso fornece um source lineage forte; equivalência binária byte-a-byte com o JAR distribuído não foi afirmada.

## 2. Papel no modpack
O README oficial define Meadow como um dos mods mais antigos da coleção Let's Do, focado em um retiro/bioma de meadow alpino e no ciclo de criação de queijo. O mod adiciona world/flora, variantes de animais, milk processing, cooking e vários blocos funcionais/decorativos.

## 3. Animais e milk
Meadow adiciona variedade de animais, incluindo cow variants, e amplia o ciclo de obtenção de milk. Spawn, breeding, milking, loot e entity state pertencem ao servidor/provider Meadow. Integrações com outros sistemas de animal husbandry devem evitar substituir ou duplicar drops/interações do provider.

## 4. Cheese pipeline
O pipeline principal inclui milk→Cheesepress→cheeses e uso culinário, além de Fondue. Recipe completion, inventory/fluid/container handling e output precisam ocorrer exatamente uma vez. Quests devem creditar o produto/processo causal real, não a presença da recipe no viewer.

## 5. Cooking Cauldron e Cooking Frame
Meadow possui Cooking Cauldron e sistemas culinários associados. O histórico source imediatamente anterior à 1.4.8 inclui correção para o **Cooking Frame liberar os itens armazenados ao quebrar**, evidenciando state persistente e risco de perda/duplicação em break/move. Automação Create/KubeJS precisa respeitar esse inventory lifecycle.

## 6. Fondue
O source lineage inclui Fondue block/GUI e advancement. O state do Fondue e o consumo de ingredientes/porções pertencem ao servidor. GUI/advancement presentation não substitui recipe completion ou consumption event como source of truth.

## 7. Woodcutter e ferramentas QoL
O mod inclui Woodcutter com papel análogo ao Stonecutter, Watering Can para acelerar crescimento e felting needle para transformar wool em felted wool. Recipes/actions devem ser validadas contra tags/registries reais do Meadow; aceleração de crop não deve double-stack silenciosamente com sprinklers/perks de outros mods.

## 8. Pine/Felt e furniture
Meadow registra Pine wood/furniture e beds feitos com felted wool, além de leaded glass e decoração temática. A versão Furniture 1.1.4, fisicamente presente no mesmo pack, adiciona compatibilidade explícita para woods do Meadow. Esse interop deve resolver os IDs/tags de Pine da versão instalada sem missing models/recipes.

## 9. Wardrobe e armazenamento de armor
A 1.4.8 corrige dois problemas críticos do Wardrobe: quebrar a metade superior agora gera drops corretamente e armor armazenada não desaparece. Isso confirma BlockEntity/inventory state persistente. Break de qualquer half, chunk unload, piston/contraption interaction e restart precisam preservar conteúdo exatamente uma vez.

## 10. Fur Armor
A 1.4.8 aplica durability correta à Fur Armor e impede stacking indevido. Essa release deve ser tratada como baseline para inventory/equipment semantics. Outros RPG/Curios systems não devem assumir stackability baseada apenas em item metadata antiga.

## 11. Tags e models da 1.4.8
O changelog/source lineage adiciona Pine Dresser e Sofa variants ao tag de wooden blocks e corrige models de sofa/Pine cabinet. Esses fixes são relevantes para breakability/tag interop e render/resource packs. Tag de wooden blocks é uma superfície de interoperabilidade, não proof de recipe equivalence com outros wood families.

## 12. Worldgen e flora
Meadow adiciona flora e um ambiente/bioma temático. A distribuição exata de features da build 1.4.8 não foi enumerada nesta ficha, porque o objetivo é catalogação por subsistemas. Worldgen permanece server/datapack authority e precisa coexistir com o stack amplo de biomas/estruturas do pack.

## 13. Client / server
Entity spawn, milking, recipes, inventories, armor state e worldgen pertencem ao servidor. Models, GUI, decorative rendering e visual furniture são client-facing. Dedicated server não deve carregar renderer-specific classes para lógica de BlockEntity/entity.

## 14. Lifecycle e persistência
Validar world/chunk load, animal state, cheese/cooking stations, Cooking Frame break, Wardrobe half break, armor storage, furniture BlockEntities e resource/data reload. Move/break por jogadores ou contraptions não deve dupar nem apagar inventory.

## 15. Riscos técnicos
1. **Wardrobe item loss/duplication** — regressão diretamente corrigida em 1.4.8.
2. **Cooking Frame stored-item loss** em break/move.
3. **Furniture wood/tag drift** com Furniture 1.1.4.
4. **Animal interaction overlap** com outros husbandry mods.
5. **Crop acceleration stacking** entre Watering Can e outros growth systems.
6. **Worldgen overlap** com outros biome mods.
7. **Source lineage drift:** branch atual já é 1.4.9; usar source atual sem pin poderia atribuir features futuras a 1.4.8.

## 16. Matriz de testes
- [ ] Dedicated server inicia com Meadow 1.4.8.
- [ ] Cow variants/spawns e milking persistem após chunk reload.
- [ ] Cheesepress/Cooking Cauldron produzem output uma vez e preservam remainders.
- [ ] Cooking Frame libera itens armazenados exatamente uma vez ao quebrar.
- [ ] Wardrobe quebrado por qualquer half preserva armor sem dupe/loss.
- [ ] Fur Armor tem durability correta e não stacka.
- [ ] Pine Dresser/Sofa estão nos tags/recipes esperados e renderizam sem missing model.
- [ ] Furniture 1.1.4 resolve compat de wood Meadow sem missing IDs.
- [ ] Watering Can não cria crescimento duplicado com outros sistemas.
- [ ] Worldgen Meadow não produz densidade/aninhamento anormal com o stack atual.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 17. Evidências e limites
- modlist física: Meadow 1.4.8;
- publicação oficial 1.4.8: release NeoForge 1.21.1;
- source histórico commit `406d286…`: `mod_version=1.4.8` e linha NeoForge 1.21.1;
- commits imediatamente anteriores: fixes de Fur Armor, wooden tags, Wardrobe e Cooking Frame correspondentes à release;
- README do mesmo source lineage: animals, cheese, Fondue, Cheesepress, Cooking Cauldron, Watering Can, Woodcutter, Pine/Felt e decoração.
A branch atual 1.4.9 não foi usada para atribuir conteúdo novo à 1.4.8.
