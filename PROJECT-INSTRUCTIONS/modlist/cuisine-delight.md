# Cuisine Delight

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81c490a9e3f237a6383c
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Cuisine Delight
- **Arquivo JAR:** `cuisinedelight-1.2.10.jar`
- **Versão 1.21.1:** 1.2.10
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Comida
- **Função:** Sistema culinário livre sobre Farmer's Delight: permite cozinhar combinações arbitrárias de ingredientes em Cuisine Skillet, mexer com Spatula, montar pratos em plates e obter bônus por ponto de cozimento e diversidade nutricional.
- **Dependências:** Farmer's Delight obrigatório. Compatibilidades opcionais públicas incluem Alex's Mobs, Ocean's Delight, Croptopia, Miner's Delight, Nether/End delights, Diet e Spice of Life variants conforme versão. Create: Arm-made Cuisine é bridge complementar quando presente.
- **Sobreposição:** Compartilha alimentação/nutrição com outros mods, mas seu núcleo é cooking freeform por skillet e composição. Bridges como Create: Arm-made Cuisine automatizam a skillet e não substituem o provider de comida.
- **Compatibilidade/Riscos:** Altera cálculo de comida por composição/doneness e pode cruzar outros sistemas nutricionais. Fire Aspect permite cooking sem stove; Efficiency aumenta limite de ingredientes. 1.2.10 corrige fluid rendering e atualiza idiomas; lógica alimentar deve permanecer server/common apesar do fix visual.
- **Observações:** JAR `cuisinedelight-1.2.10.jar`; runtime 1.2.10; Client & Server; Farmer's Delight required. Cada ingrediente tem doneness e one-side-burn state próprios; pratos bem cozidos recebem saturation/nutrition bonuses e diversidade de carbs/meat/vegetables/seafood concede bônus. 1.2.10: fix fluid rendering + language updates.
- **Procedência:** Modlist física canônica de 08/09/2026, 600 top-levels + runtime Cuisine Delight 1.2.10 + CurseForge/Modrinth oficiais da release 1.2.10 e documentação funcional atual.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/cuisine-delight
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — freeform skillet cooking authority, per-ingredient doneness/burnt state, nutrition/saturation bonuses, enchantment interactions, optional food-mod compatibility e regressão 1.2.10 catalogados.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Cuisine Delight 1.2.10 foi reconfirmado como `Instalado` e reconstruído ao padrão técnico. Presença no stack culinário não foi convertida em decisão curatorial.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🍳 Versão física confirmada: `cuisinedelight-1.2.10.jar`, runtime `1.2.10`, NeoForge 1.21.1. Farmer's Delight é dependência obrigatória; o núcleo é **cozinhar livremente sem receitas fixas**.

## 1. Papel e authority
Cuisine Delight controla sua Cuisine Skillet, estados de cozimento, montagem de pratos e bônus alimentares. Farmer's Delight fornece a base culinária/item ecosystem; outros nutrition mods podem consumir o resultado, mas não devem recalcular o cooking state como se fossem o provider.

## 2. Ingredientes livres
O jogador pode adicionar combinações arbitrárias de ingredientes em vez de seguir exclusivamente recipes pré-definidos. A aceitação/classificação do ingrediente deve vir das regras/tags reais da build; não inferir que todo edible item é suportado.

## 3. Cuisine Skillet
A skillet deriva da skillet de Farmer's Delight. Ela mantém a composição dos ingredientes em cozimento e os estados individuais necessários para determinar o resultado do prato.

## 4. Doneness por ingrediente
Cada ingrediente possui uma escala de cozimento individual. A documentação indica verde para undercooked, amarelo para ponto ideal e vermelho para overcooked. Ingredientes com tempos diferentes devem ser adicionados em momentos apropriados.

## 5. Burnt-on-one-side
Há uma segunda escala por ingrediente para quanto ele queimou de um lado. A Spatula é usada para mexer e reduzir o risco de over-burnt. Doneness e burnt state são dimensões separadas e não devem ser condensadas num único flag externo.

## 6. Plate collection
O prato pronto é coletado usando um plate. Inventory settlement deve consumir/transferir os componentes conforme a implementação exatamente uma vez e preservar o dish state gerado.

## 7. Bonus de qualidade
Comida bem cozida recebe bônus de saturation/nutrition conforme a linha atual. O valor final é função do state do prato e da composição; outro mod de alimentação deve consumir esse resultado sem duplicar o bônus base.

## 8. Diversidade nutricional
Tipos diferentes de ingrediente, como carbs, meat, vegetables e seafood, concedem bônus adicionais pela diversidade. A classificação real é data/runtime-driven; tags de outros mods não devem ser atribuídas a uma categoria sem suporte.

## 9. Fire Aspect
Cuisine Skillet pode receber **Fire Aspect** e cozinhar sem stove. O enchantment muda a fonte de calor, não transforma o cliente em authority do timer/doneness.

## 10. Efficiency
**Efficiency** aumenta o limite superior de itens simultâneos na skillet. A documentação pública informa que **Efficiency V permite 6 itens** por vez. Outros níveis/curva exata não são extrapolados sem runtime/source.

## 11. Farmer's Delight
Farmer's Delight é required. Cuisine Delight reutiliza seu ecossistema culinário, mas possui cooking mechanics próprias. Update da dependency precisa de smoke-test de skillet, food components e containers.

## 12. Compatibilidades opcionais
A linha pública cita compat com Alex's Mobs, Alex's Delight, Ocean's Delight, Miner's Delight, Croptopia, Nether/End Delight, Diet e variantes Spice of Life conforme versão. Essas integrações só contam se os mods correspondentes estiverem presentes e a build realmente registrar o suporte.

## 13. Create: Arm-made Cuisine
Esse addon é bridge de automação para a Cuisine Skillet. Cuisine Delight continua authority do prato/doneness; a bridge apenas opera o processo. Não registrar automação como feature nativa deste JAR.

## 14. Fluid rendering — 1.2.10
O changelog exato da 1.2.10 corrige **fluid rendering** e atualiza language files. É regression gate visual; não implica mudança publicada na semântica de cooking/food.

## 15. Client/server
Cooking timers/state, inventory, food properties e consumption são common/server. Overlay, scales, fluid render e language são client-facing. Visual incorreto não deve mudar doneness server-side.

## 16. Lifecycle
Validar add/stir/collect, stove/fire-aspect cooking, logout/reconnect com skillet em uso quando aplicável, server restart, recipe/tag reload e resource reload.

## 17. Riscos
1. Ingredient state ser perdido ou duplicado.
2. Stir aplicar twice por packet retry.
3. Plate collection duplicar comida/container.
4. Nutrition bonus ser somado por dois systems.
5. Optional compat classificar ingredient errado.
6. Fire Aspect divergir do stove timing.
7. Efficiency permitir stack acima do limite real.
8. Fluid render 1.2.10 regredir sem afetar state lógico.

## 18. Matriz de testes
1. Dedicated server boot com Farmer's Delight.
2. Prato de um ingrediente: under/ideal/overcooked.
3. Stir com Spatula e burnt-on-one-side.
4. Prato misto carbs/meat/vegetable/seafood.
5. Plate collection e conservation de inventory.
6. Fire Aspect cooking sem stove.
7. Efficiency V aceitando até 6 itens conforme documentação.
8. Optional integrations efetivamente instaladas.
9. Create: Arm-made Cuisine, se presente, sem double-processing.
10. Resource reload verificando fluid render da 1.2.10.

## 19. Evidência
- modlist física 08/09/2026: Cuisine Delight 1.2.10;
- CurseForge/Modrinth oficiais: Farmer's Delight required, freeform skillet cooking, per-ingredient doneness/burnt scales, Spatula/Plate;
- documentação oficial: Fire Aspect, Efficiency V=6 e diversity bonus;
- changelog 1.2.10: fluid rendering fix + language updates.

> 🔒 Boundary canônico: **Cuisine Delight decide cooking state e dish result; sistemas nutricionais/automação externos apenas consomem ou operam esse state**. O mesmo prato não deve receber bônus/settlement duplicado.