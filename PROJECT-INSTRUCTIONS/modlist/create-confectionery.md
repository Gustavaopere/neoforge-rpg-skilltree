# Create Confectionery

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8130ab5dc9915008a2a2
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Confectionery
- **Arquivo JAR:** `create-confectionery1.21.1_v1.1.3b.jar`
- **Versão 1.21.1:** 1.1.3.
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Comida, Automação
- **Função:** Addon culinário/decorativo de Create focado em confeitaria e uma cadeia de produção de chocolates/doces automatizável com as primitives de processamento do Create.
- **Dependências:** Create. Release 1.1.3.b é NeoForge 1.21.1, Client & Server. Central Kitchen pode integrar processos culinários, mas não é dependência base desta ficha.
- **Sobreposição:** Compartilha food/chocolate space com outros addons culinários, mas possui conteúdo e recipes próprios. Create é authority das primitives de processing; Confectionery é authority de seus ingredients, foods, tools e recipes.
- **Compatibilidade/Riscos:** Riscos em food effects, recipes/processamento duplicados por outros addons culinários, tags/ingredients e divergência de identidade: filename/publicação `1.1.3b`/`1.1.3.b`, enquanto metadata runtime declara literalmente `1.1.3.`. Preservar os dois valores sem normalização inventada.
- **Observações:** mod id `create_confectionery`; JAR `create-confectionery1.21.1_v1.1.3b.jar`; runtime literal `1.1.3.`. Release oficial 1.1.3.b corrige effects de candies/glazed berries, atualiza textures, muda Full Chocolate Bar para comportamento de food e adiciona Candy Cane Tools.
- **Procedência:** modlist.txt física atual de 08/09/2026 (595 top-levels) + metadata runtime `create_confectionery` 1.1.3. + CurseForge/Modrinth oficiais da release NeoForge 1.21.1 `1.1.3.b`.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-confectionery
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — Create Confectionery JAR `v1.1.3b`, release `1.1.3.b` e runtime literal `1.1.3.` reconciliados; conteúdo/processing/lifecycle confirmados no QC global #119. Runtime QA não executado.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Create Confectionery foi reconfirmado como `Instalado`; a divergência filename/publicação↔runtime foi preservada e a ficha reconstruída sem inferir decisão curatorial.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🍫 Identidade física: `create-confectionery1.21.1_v1.1.3b.jar`, mod id `create_confectionery`. A publicação oficial é `1.1.3.b`; a metadata runtime da modlist declara literalmente **`1.1.3.`**. A divergência é preservada, não normalizada.

## 1. Papel e authority
Create Confectionery adiciona uma linha própria de chocolates, doces, ingredientes, ferramentas e decoração integrada ao processing do Create. Create decide a execução das primitives mecânicas; Confectionery decide seus recipes, foods, effects e items.

## 2. Cadeia de confeitaria
O projeto se apresenta como uma extensão para montar uma **chocolate factory** usando Create. Ingredients intermediários e foods podem atravessar mixing/filling/processing conforme os recipes registrados pela build.
O catálogo não inventa valores de recipe, duração ou nutrition sem datapack/JAR pin direto.

## 3. Chocolates e candies
A release 1.1.3.b corrige items que não entregavam seus efeitos pretendidos, incluindo `white_chocolate_candy`, `ruby_chocolate_glazed_berries` e `black_chocolate_glazed_berries` citados no changelog.
Food effect deve ser aplicado exatamente uma vez pelo provider; scripts de comida não devem reaplicar o mesmo efeito ao reconhecer o item.

## 4. Full Chocolate Bar
A build muda o **Full Chocolate Bar** para se comportar como alimento normal. Isso torna hunger/saturation/use lifecycle um gate específico da release.
Não transportar comportamento de versões antigas para a 1.1.3.b sem teste.

## 5. Candy Cane Tools
A release adiciona **Candy Cane Tools**. Eles são equipamentos do próprio addon e devem usar seus atributos/durability registrados, sem receber cópia automática de stats de outra família de tools apenas por material temático.

## 6. Texturas e apresentação
1.1.3.b atualiza textures. Resource pack overrides e render mods devem ser avaliados como presentation; a aparência não altera recipe/effect authority.

## 7. Recipes e automação Create
Recipes do addon podem ser automatizados pelas primitives de Create quando registrados para esse fluxo. Integrações externas devem consultar o recipe manager em vez de duplicar receitas em handlers paralelos.
Central Kitchen pode adaptar cooking automation quando houver integração concreta, mas não se torna owner dos foods.

## 8. Tags e integração culinária
Outros addons podem compartilhar chocolate, açúcar, berries ou food tags. Compatibilidade deve ser baseada em tags/recipes reais da build, não em equivalência por nome.
Unificação de ingredientes só deve ocorrer por política explícita do modpack.

## 9. Client/server
Consumption, effects, recipes, inventories e processing são common/server-authoritative. Textures/models/tooltips são client-facing.
Em multiplayer, consumo e output de recipes devem ocorrer exactly once.

## 10. Lifecycle
Validar recipe/datapack reload, resource reload, eating/use completion, equip/unequip de tools, server restart e automação em chunks unload/reload.

## 11. Divergência de versão
Três identificadores precisam continuar separados:
- filename: `v1.1.3b`;
- release upstream: `1.1.3.b`;
- runtime metadata: `1.1.3.`.

O campo do Notion preserva o runtime físico; a ficha registra a release correlata sem fingir que as strings são idênticas.

## 12. Riscos
1. Food effect duplicado por script externo.
2. Recipe duplicado por outro culinary addon.
3. Ingredient tag unificada incorretamente.
4. Runtime/version string normalizada e perda de rastreabilidade.
5. Candy Cane Tool receber attributes duplicados.
6. Resource reload deixar texture/model stale.

## 13. Matriz de testes
1. Dedicated server boot.
2. Recipes principais via Create conforme JEI/recipe manager.
3. Consumir candies/glazed berries e validar efeitos uma vez.
4. Full Chocolate Bar como food normal.
5. Candy Cane Tools: equip/use/durability.
6. Recipe reload e resource reload.
7. Central Kitchen/food addons presentes sem recipe duplication.
8. Multiplayer com produção/consumo simultâneo.

## 14. Evidência
- modlist física 08/09/2026: JAR `v1.1.3b`, runtime `1.1.3.`;
- Modrinth/CurseForge oficiais: release NeoForge 1.21.1 `1.1.3.b`, Client & Server, Create requerido;
- changelog: food-effect fixes, texture update, Full Chocolate Bar e Candy Cane Tools.

> 🔒 Fail-closed: **não corrigir a string runtime para ficar “bonita”**. Filename, publicação e metadata são evidências diferentes e permanecem registradas separadamente.
