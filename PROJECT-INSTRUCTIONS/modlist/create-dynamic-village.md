# Create: Dynamic Village — 0.9

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db8157a2d1e8e54f233bbf  
> Estado no momento da exportação: `Integrado ao Github`  
> Autoridade física no momento da exportação: `modlist(4).txt`, 595 mods top-level  
> Exportado/reconciliado em: 2026-09-09

## Propriedades do registro

- **Mod:** Create: Dynamic Village
- **Arquivo JAR:** `dynamicvillage-0.9-1.21.1.jar`
- **Versão 1.21.1:** `0.9`
- **Categoria:** Worldgen; Tecnologia; Mobs
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/dynamic-village
- **Função:** Integra Create ao sistema de villages com profissões, job sites, trades, loot e 20 buildings temáticos distribuídos por biomas, além de controles de tamanho/densidade de vilas.
- **Dependências:** Create. Runtime físico: Create 6.0.10 + Dynamic Village 0.9. O pack também contém Integrated Villages 1.3.3+1.21.1-neoforge, que atua na mesma superfície de settlements/worldgen e exige regressão conjunta.
- **Compatibilidade/Riscos:** Riscos em profissões/job-site collision, trades duplicados, buildings concorrendo com outros village mods, config de density/size produzindo vilas excessivas, air-carving/estrutura substituindo terreno e data-defined profession/trade drift. Integrated Villages está fisicamente presente e é sobreposição concreta a testar.
- **Sobreposição:** Sobreposição parcial e intencional com outros mods de villages/settlements. Não substitui Create nem villagers vanilla; injeta conteúdo Create no ecossistema de aldeias. Integrated Villages 1.3.3 está presente e deve ser testado em conjunto.
- **Observações:** 0.9 refaz interiores dos 20 buildings, corrige salas sem iluminação/chests ausentes, remove o anel de ar ao redor das construções e ajusta geração por bioma. A linha atual também expõe extensão data-defined para trades/buildings/professions; validar o schema efetivo da 0.9 antes de customização pesada.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `dynamicvillage-0.9-1.21.1.jar`, mod id `dynamicvillage`, versão 0.9 e Create 6.0.10. Publicação/source oficial confirmam quatro profissões, job sites, 20 buildings e mudanças/configs da 0.9.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — 0.9; 4 profissões/job sites, 20 buildings, trades/loot, village generation config, data-defined extensions, lifecycle, riscos e testes catalogados.
- **Data da última decisão:** 2026-08-26

## Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `dynamicvillage-0.9-1.21.1.jar` · mod id `dynamicvillage` · versão `0.9` · NeoForge 1.21.1. O pack usa Create 6.0.10.

## 1. Papel no modpack
Create: Dynamic Village leva o ecossistema Create para villages vanilla/modded por meio de profissões, job sites, trades, loot e buildings temáticos. O mod não substitui Create nem o sistema base de villagers; ele registra conteúdo de settlement que referencia itens/blocos Create.

## 2. Authority / ownership
- **Minecraft/village system:** villager lifecycle, POI/profession framework, village pieces e trading base.
- **Create:** itens, blocos e máquinas usados como mercadorias/job sites.
- **Dynamic Village:** profissões, trades, buildings, loot e configurações de distribuição que adiciona.
- **Outros village mods:** suas próprias pools/estruturas/profissões.

Worldgen final precisa ser uma composição coerente, não múltiplos providers escrevendo a mesma estrutura sem precedence.

## 3. Quatro profissões confirmadas
A documentação oficial da linha atual confirma quatro profissões temáticas:
1. **Mechanical Engineer** — contraption parts e componentes Create; job site ligado à **Schematic Table**.
2. **Hydraulic Engineer** — fluid parts, pipes, pumps/diving-related content; job site ligado ao **Item Drain**.
3. **Train Mechanic** — componentes do sistema ferroviário; job site ligado à **Train Station**.
4. **Miner** — mineração, equipamentos e minerais/metais; job site ligado ao **Mechanical Drill**.

Os trades concretos devem ser lidos do data/source da build antes de automações econômicas; esta ficha não inventa preços/levels não verificados.

## 4. Buildings
A linha 0.9 possui **20 buildings** distribuídos entre villages de plains, savanna, desert, taiga e snowy. Eles são parte do village/worldgen provider do addon, não estruturas independentes que devam ser colocadas em paralelo por scripts externos.

## 5. Mudanças da 0.9
O changelog oficial da build física registra:
- interiores refeitos para os 20 buildings;
- correções de salas sem iluminação;
- correções de chests ausentes;
- remoção do anel de ar gerado ao redor dos buildings;
- tuning da geração por bioma;
- controles novos/ajustados de tamanho e densidade de village.

Esses pontos formam regressões específicas ao atualizar de 0.8 para 0.9.

## 6. Configuração de village
A 0.9 expõe controles de **Biome Density** e **Village Size**. A documentação descreve Biome Density como controle do número de buildings customizados e Village Size com sliders para extensão/distância e densidade.

Valores físicos atuais do pack não foram auditados neste ciclo; portanto não declarar número de casas/village efetivo sem ler a config runtime.

## 7. Trades e economia
Villager trades adicionados pelo mod movimentam itens Create e podem alterar disponibilidade/progressão econômica. Isso precisa ser analisado em conjunto com recipes, loot e outros mods que vendem os mesmos componentes.

O servidor é authority de offer generation, restock, trade execution e inventory settlement. GUI não pode duplicar compra/venda.

## 8. Extensão data-defined
A documentação/source da linha 0.7+ introduziu definitions para trades/buildings/professions por dados, com condições como `mod_loaded`, `item_exists` e `block_exists`, além de merge determinístico e opção de replacement em determinadas definições.

Profissões data-defined são carregadas cedo e a documentação do projeto usa `config/dynamicvillage/professions/<name>.json`; job sites precisam participar do tag de POI/job site adequado. Antes de customizar a build 0.9, validar o schema atual para não projetar campos removidos/alterados.

## 9. Loot e containers
Buildings podem conter loot/containers definidos pelo mod. A 0.9 corrige chests ausentes em interiores, portanto gerar village + abrir containers é regression gate específico.

Loot deve liquidar uma vez no servidor e respeitar loot tables; scripts externos não devem preencher o mesmo chest novamente sem intenção explícita.

## 10. Create 6.0.10
O runtime físico usa Create 6.0.10. Job sites/trades que apontam para Schematic Table, Item Drain, Train Station e Mechanical Drill dependem desses IDs/contratos permanecerem compatíveis.

Update de Create deve disparar smoke-test de profession POIs, trades e structures, mesmo que o jogo faça boot.

## 11. Integrated Villages no pack
`integrated_villages-1.3.3+1.21.1-neoforge.jar` está fisicamente presente. Ele toca a mesma superfície macro de villages/settlements, então coexistência precisa ser validada em worldgen real.

Isso não prova duplicação: cada projeto pode adicionar pools/estruturas diferentes. O risco é densidade excessiva, pool competition, terrain carving ou buildings sobrepostos.

## 12. Client / Server
Worldgen, POIs, professions, trades, loot e villager state são server-authoritative. Models/textures/GUI são client-facing.

Dedicated server não deve carregar assets client-only para registrar profissão/estrutura.

## 13. Lifecycle
Validar:
- bootstrap/registries;
- world creation;
- village generation por cada um dos cinco biomas-base suportados;
- villager claim/release de job site;
- profession change;
- trade generation/restock;
- chunk unload/reload;
- server restart;
- datapack/config reload quando aplicável;
- update de Create;
- update de outro village/worldgen mod.

## 14. Multiplayer
Dois jogadores negociando com o mesmo villager não podem duplicar item/emerald. Job-site ownership deve convergir no servidor. Village structures geradas são parte do save e não devem depender da configuração visual do cliente.

## 15. Riscos
1. profession/job-site collision;
2. trade duplicado com outro addon;
3. economia de Create trivializada por villager trade;
4. POI não encontrado após update Create;
5. building pool collision;
6. Integrated Villages gerar densidade excessiva;
7. terrain/air carving incorreto;
8. chest/loot ausente ou duplicado;
9. config de density/size excessiva;
10. data-defined schema drift;
11. chunks antigos/novos com villages diferentes;
12. villager restock/profession state stale após restart.

## 16. Matriz de testes
1. Dedicated server boot com Create 6.0.10.
2. Gerar villages plains/savanna/desert/taiga/snowy.
3. Contabilizar buildings Dynamic Village e procurar overlaps.
4. Validar os 20 building templates em amostra/world de teste.
5. Confirmar iluminação/interiores/chests corrigidos na 0.9.
6. Testar as quatro profissões e seus job sites.
7. Trade/restock/relogin com dois jogadores.
8. Testar config Biome Density e Village Size em cópia de mundo.
9. Gerar com Integrated Villages habilitado.
10. Chunk unload/reload e restart.
11. Smoke-test após update Create.
12. Se custom data for usado, validar schema/conditions/merge em datapack de teste.

**Esta catalogação não afirma que esses testes foram executados.**

## 17. Evidências
- modlist física canônica de 08/09/2026: JAR/mod id/versão 0.9, Create 6.0.10 e Integrated Villages presente;
- publicação/source oficial Dynamic Village: quatro profissões/job sites e 20 buildings;
- changelog oficial 0.9: interiores, iluminação/chests, air ring e tuning/config de village;
- documentação da linha data-driven do projeto para trades/buildings/professions.

> **Boundary canônico:** Dynamic Village é authority apenas do **conteúdo de village que adiciona**. Minecraft/village framework e Create continuam donos de seus respectivos sistemas.
