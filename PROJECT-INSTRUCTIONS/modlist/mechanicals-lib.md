# Mechanicals Lib

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db817d9527f54733d16c81
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Mechanicals Lib
- **Arquivo JAR:** `mechanicals-1.21.1-1.1.6.jar`
- **Versão 1.21.1:** 1.1.6
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** Biblioteca
- **Tipo de conteúdo:** Mod
- **Função:** Framework para addons NeoForge/Create: recipe requirements condicionais e codecs, recipes/builders, block-entity behaviors, energia, GUI/render, data generation, Jade/JEI/KubeJS e utilidades Registrate/Create. Também registra conteúdo próprio feature-gated de lemon wood e um package raro do Create.
- **Dependências:** Source 1.1.6: NeoForge >=21.1.219 e Minecraft 1.21.1; forte acoplamento de build/API a Create 6.0.10 (range [6.0.10,6.1.0)); JEI/KubeJS opcionais no loader. Registrate/Flywheel/Ponder são embarcados via JarJar. Consumer físico confirmado: Create: Mechanical Spawner.
- **Sobreposição:** Não substitui Create nem Mechanical Spawner. É provider de infraestrutura reutilizável; consumers mantêm authority de suas máquinas/recipes. Pode sobrepor funções genéricas de outras libs, mas seus IDs/codecs/API são contrato próprio.
- **Compatibilidade/Riscos:** Biblioteca/ABI para addons Create. Riscos: drift com Create/consumers, codecs/registry IDs, KubeJS bindings, Jade/JEI/render e conteúdo feature-gated. JAR embute Registrate 1.3.0+67, Flywheel 1.0.6 e Ponder 1.0.82 via JarJar; não criar/remover como top-level.
- **Observações:** Runtime físico 1.1.6. Registra 10 recipe requirement types: min/max speed, Y, temperature, downfall, biome e biome_tag. Conteúdo source inclui lemon_log/stripped_lemon_log/planks/leaves/sapling e rare_oierbravo_package. Changelog 1.1.6 é apenas 'Fix publication'.
- **Procedência:** modlist.txt física atual de 10/09/2026 + source oficial oierbravo/mechanicals-lib branch 1.21.1 exatamente 1.1.6 + metadata, registries, tree e changelog oficiais.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/mechanicals-lib
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — source 1.1.6 exato; recipe requirements, codecs, BE behaviors, energia/GUI/render, Jade/JEI/KubeJS, conteúdo lemon/package, JarJar, lifecycle, riscos e testes catalogados.
- **Histórico da decisão:** 2026-08-26 — classificado como Dependência após confirmação de que Create: Mechanical Spawner 1.21.1-2.x requer Mechanicals Lib.
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> ⚙️ **ESCOPO CANÔNICO.** Runtime físico: `mechanicals-1.21.1-1.1.6.jar`, mod id `mechanicals`, versão `1.1.6`. O source oficial `oierbravo/mechanicals-lib`, branch `1.21.1`, declara exatamente 1.1.6, Minecraft 1.21.1 e NeoForge mínimo 21.1.219. O JAR físico também contém Registrate 1.3.0+67, Flywheel 1.0.6 e Ponder 1.0.82 como JarJar internos; eles pertencem ao host e não viram entradas top-level separadas neste catálogo.

## 1. Papel no pack
Mechanicals Lib é uma biblioteca/framework para addons NeoForge, com foco explícito no ecossistema **Create**. Não é apenas um marcador de dependência: a 1.1.6 fornece infraestrutura reutilizável de recipes, recipe requirements, block-entity behaviors, energia, GUI, rendering, conditions, data generation, Jade/JEI/KubeJS compat e utilidades Registrate/Create.
No pack, o consumer confirmado é **Create: Mechanical Spawner**. A remoção da biblioteca deve ser tratada como quebra de ABI/loader para consumers que importam suas classes ou registries.

## 2. Dependências, source-line e JarJar
`gradle.properties` da 1.1.6 declara:
- Minecraft 1.21.1;
- NeoForge `>=21.1.219`;
- Create de desenvolvimento 6.0.10-280, range `[6.0.10,6.1.0)`;
- Ponder 1.0.82, Flywheel 1.0.6 e Registrate MC1.21-1.3.0+67;
- JEI 19.25.0.322;
- Curios 9.2.2;
- KubeJS 2101.7.2-build.348 quando habilitado no build.
O `neoforge.mods.toml` força NeoForge e Minecraft e declara JEI/KubeJS opcionais. Create aparece como forte acoplamento de código/build e APIs importadas, ainda que não esteja declarado como hard dependency nesse template; portanto o catálogo não deve converter automaticamente build dependency em loader pin sem observar o artefato/consumer.

## 3. Registries e recipe requirements
A biblioteca cria um registry próprio para **recipe requirements** e registra dez tipos concretos:
- `min_speed` / `max_speed`;
- `min_y` / `max_y`;
- `biome` / `biome_tag`;
- `min_temperature` / `max_temperature`;
- `min_downfall` / `max_downfall`.
Cada requirement possui codec de dados e stream codec, permitindo que recipes consumers carreguem e sincronizem condições ambientais/cinéticas de forma estruturada.
Isso é uma surface contratual importante: datapacks/KubeJS/consumers que serializam esses requirements dependem dos IDs e codecs desta biblioteca.

## 4. Recipe framework
A árvore 1.1.6 contém:
- `AbstractMechanicalRecipe`;
- builders/params abstratos;
- `IRecipeRequirement` e `IRecipeWithRequirements`;
- `RecipeRequirementType`;
- `StandardMechanicalRecipeProvider`;
- `CountableIngredient`;
- utilitários de `ProcessingOutput`, predicates e geração de recipes Create.
A responsabilidade da biblioteca é fornecer o modelo e a validação; a recipe concreta e seu resultado continuam pertencendo ao addon consumidor.

## 5. Block entity behaviors
`foundation/blockEntity/behaviour` fornece pelo menos:
- `CycleBehavior`;
- `DynamicCycleBehavior`;
- `RecipeRequirementsBehaviour`.
Essas classes são infraestrutura para máquinas/blocks consumers. Persistência, ticking e sincronização efetiva precisam ser auditados no consumer que as instancia; a presença da classe na lib não prova que uma máquina específica do pack use todas elas.

## 6. Energia, GUI e rendering
A 1.1.6 fornece `AbstractEnergyStorage`, widgets de energia/progresso/botões, ícones/texturas de GUI, `FluidRenderer`, `PartialBlockRenderer`, reload listeners e visuals de shaft. Isso cria uma superfície comum de client/render e FE-style storage para addons.
Erros nessa camada podem aparecer como problemas em consumers — GUI quebrada, progresso divergente, fluid render incorreto, shaft visual ausente — mesmo quando o bug não está no addon final.

## 7. Integrações Jade, JEI e KubeJS
### Jade
A lib contém `IHavePercent` e `MechanicalProgressComponentProvider`, uma abstração para consumers exporem progresso no Jade.
### JEI
Há builders/categories e renderer de recipe requirements para apresentar recipes mecânicas/condicionais.
### KubeJS
`MechanicalsJsPlugin` e componentes/bindings expõem primitives como block predicates, processing outputs, countable ingredients e recipe requirements ao scripting.
Essas integrações são extensões da API da lib; o conteúdo efetivo depende dos consumers e scripts existentes.

## 8. Conteúdo próprio da biblioteca
Diferentemente de uma biblioteca totalmente invisível, a source-line possui conteúdo registrado/foundation.
`MechanicalsBlocks` registra uma família de **lemon wood**: `lemon_log`, `stripped_lemon_log`, `lemon_planks`, `lemon_leaves` e `lemon_sapling`. A sapling usa feature flag própria e `MechanicalsTreeGrower`.
`MechanicalsCreateItems` registra `rare_oierbravo_package`, um `PackageItem` compatível com a tag de packages do Create, stack 1.
A existência dessas entradas deve ser preservada no inventário; ainda assim, a função principal do mod permanece biblioteca.

## 9. Worldgen/feature flags
O source contém configured/placed features, biome modifiers e `MechanicalsWorldGenProvider` ligados ao conteúdo lemon, além de `feature_flags.json`. Worldgen só deve ser tratado como ativo quando a feature flag e os dados da build o habilitarem; não inferir geração de árvore apenas pela existência das classes.
Atualizações da biblioteca em mundo já criado podem alterar a disponibilidade desses recursos e a ABI de consumers simultaneamente.

## 10. Client/server e lifecycle
A biblioteca possui inicialização comum e classe client dedicada. Recipe requirements têm stream codecs; rendering/Jade/JEI são client-facing, enquanto recipes, conditions, energia e lógica de block entities pertencem à simulação comum/server conforme o consumer.
Não existe um único lifecycle global que represente todos os módulos. Testes precisam cobrir boot, registry freeze, datapack reload, world load, chunk unload/reload e client reconnect com pelo menos um consumer real.

## 11. Riscos técnicos
1. **ABI drift:** consumer compilado contra outra 1.1.x pode quebrar por assinatura/registry ID.
2. **Create drift:** source 1.1.6 foi construída em torno de Create 6.0.10 e range anterior a 6.1.0; qualquer avanço do Create do pack precisa de regressão.
3. **JarJar duplication:** Registrate/Flywheel/Ponder internos não devem ser catalogados/removidos como top-level só por aparecerem sob META-INF.
4. **Codec/schema drift:** recipes/scripts dependentes podem falhar no reload.
5. **Client compat:** Jade/JEI/render podem falhar sem comprometer a simulação server, exigindo triagem por side.
6. **KubeJS binding drift:** scripts que usam componentes Mechanicals precisam ser revistos após updates.
7. **Worldgen/feature flags:** não presumir conteúdo lemon ativo em todo mundo/instância sem teste.
8. **Consumer ownership:** bugs de Mechanical Spawner não devem ser atribuídos automaticamente à lib e vice-versa.

## 12. Matriz de testes
- [ ] Dedicated server inicia com NeoForge 21.1.248 e consumers atuais.
- [ ] Create: Mechanical Spawner carrega sem `NoClassDefFoundError`, registry ou codec error.
- [ ] Recipe com requirement de speed resolve limites corretamente.
- [ ] Requirement de Y/biome/temperature/downfall serializa, sincroniza e recarrega após `/reload`.
- [ ] KubeJS scripts, se houver consumers da API, compilam sem binding ausente.
- [ ] JEI mostra requirements sem crash.
- [ ] Jade progress provider de consumer apresenta progresso coerente.
- [ ] Chunk unload/reload não perde estado de behavior/energia no consumer testado.
- [ ] Client reconnect não gera registry mismatch.
- [ ] Conteúdo lemon/package, se feature-enabled, possui modelos/tags/recipes/worldgen coerentes.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 13. Evidências
- Modlist física: `mechanicals-1.21.1-1.1.6.jar` e JarJars Registrate/Flywheel/Ponder internos.
- Source oficial: `oierbravo/mechanicals-lib`, branch `1.21.1`, `mod_version=1.1.6`.
- `gradle.properties`, `neoforge.mods.toml`, árvore completa 1.21.1.
- Arquivos auditados: `MechanicalsBlocks`, `MechanicalsCreateItems`, `MechanicalRecipeRequirementTypes` e classes foundation/compat identificadas na árvore.
- Changelog 1.1.6: publicação corrigida; internals descritos vêm do source exato da branch, não do texto curto do changelog.