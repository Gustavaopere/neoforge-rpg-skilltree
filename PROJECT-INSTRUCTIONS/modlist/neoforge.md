# neoforge

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c969db9f0db81eabe8bc99185a1f90b
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** neoforge
- **Arquivo JAR:** `neoforge-21.1.248 (modloader)`
- **Versão 1.21.1:** neoforge-21.1.248
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Categoria:** Biblioteca, Compat
- **Função:** Modloader e plataforma de APIs que executa toda a instância Minecraft 1.21.1. NeoForge 21.1.248 é responsável por descoberta/metadata/dependências de mods, lifecycle, registries estáticos e datapack registries, event buses, networking/payloads, configs, capabilities, data attachments, data components, SavedData, tags/data maps/conditions, acesso a recursos/datagen, sides lógico/físico e contratos client/server consumidos por todo o pack.
- **Dependências:** Base: Minecraft 1.21.1 + Java 21 de 64 bits. Todos os JARs NeoForge dependem direta ou indiretamente desta plataforma; mods Fabric executados por camada de compatibilidade continuam sendo hospedados pelo runtime NeoForge. Architectury, Balm, Cloth Config, Moonlight, Puzzles Lib etc. são libraries de consumidores e NÃO substituem o loader.
- **Sobreposição:** Nenhuma sobreposição real com libraries comuns: NeoForge é loader/runtime; Architectury/Balm/Moonlight/etc. são APIs específicas. Sinytra Connector/Forgified Fabric API, quando presentes, são camadas rodando SOBRE NeoForge. Fabric Loader/Forge não devem ser tratados como loaders simultâneos da mesma instância.
- **Compatibilidade/Riscos:** Blast radius global. Qualquer update do loader pode afetar resolução de metadata/faixas de versão, registries, event ordering, networking, configs, capabilities, attachments/components, data reload, mixins/access transformers usados por mods, client/server dist separation e compat layers. JAR Forge/Fabric não é automaticamente válido em NeoForge. Atualizar NeoForge exige bootstrap cliente + dedicated server + mundo existente/novo + reload + smoke dos grandes stacks. Não há uma lista única de 'mods incompatíveis com NeoForge': compatibilidade é artefato/versão-dependente.
- **Observações:** NeoForge é a maior superfície de risco de atualização do pack. Não aceitar conselho genérico do tipo 'atualize o loader' sem conferir faixas de todos os consumidores e executar regressão transversal. Java 21 é requerido para Minecraft 1.20.5+ segundo o guia oficial NeoForge.
- **Procedência:** Modlist física 2026-09-08 + documentação oficial NeoForged 1.21–1.21.1 (registries, capabilities, data components, data maps, SavedData e demais APIs) + NeoForge User Guide para Java/runtime.
- **Fonte:** https://docs.neoforged.net/
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — dossiê operacional completo de loader/runtime, lifecycle, registries, networking, persistence, sides, configs e validation matrix confirmado no QC global #1.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

> 🔎 **Escopo canônico.** Runtime físico do pack: `neoforge-21.1.248 (modloader)` sobre **Minecraft 1.21.1 + Java 21**. NeoForge é a plataforma-base da instância; não é um mod de conteúdo e não deve ser comparado como substituto de libraries como Architectury, Balm ou Moonlight.

## 1. Papel no pack
NeoForge faz o bootstrap da instância, descobre os artefatos, lê metadata/dependências, constrói o ambiente de mods e expõe os contratos de modding usados por praticamente todo o restante do pack. Um problema aqui pode impedir o jogo de chegar ao menu, impedir registry freeze, quebrar payload registration, falhar no carregamento de dados ou provocar crashes muito antes de uma feature específica ser executada.

O impacto de uma atualização de `21.1.248` é portanto **transversal**: não existe “atualizar só o loader” como se fosse um mod isolado.

## 2. Java e ambiente de execução
O guia oficial NeoForge exige **Java 21** para Minecraft 1.20.5 e versões posteriores, portanto Minecraft 1.21.1 deste projeto deve executar em JVM 64-bit Java 21.

Problemas típicos de ambiente incluem:
- launcher apontando para Java antigo;
- múltiplas instalações de Java com `PATH/JAVA_HOME` incorretos;
- JVM 32-bit;
- parâmetros de memória inadequados;
- client e dedicated server usando runtimes Java diferentes.

Em qualquer auditoria de crash de bootstrap, confirmar `java -version` antes de atribuir o problema ao mod culpado pelo último log line.

## 3. Descoberta de mods, metadata e resolução de dependências
Cada mod NeoForge publica metadata que identifica mod id, versão e relações/dependency ranges. O loader usa essas informações para decidir se o conjunto pode ser carregado.

Falhas nesta etapa incluem:
- dependência obrigatória ausente;
- range de Minecraft incompatível;
- range de NeoForge incompatível;
- dois mods registrando o mesmo mod id;
- classes duplicadas/pacotes conflitantes;
- artefato do loader errado;
- dependency jar-in-jar quebrada.

### Regra desta auditoria
Uma library dentro de `/META-INF/jarjar/` pertence ao artefato que a embutiu e **não é entrada top-level da modlist**. Isso é particularmente importante neste pack, que contém diversas APIs jarjar além das versões top-level.

## 4. Lifecycle e inicialização
Mods executam registro e setup em fases/lifecycles definidos pelo ambiente NeoForge. Isso separa tarefas que precisam acontecer antes do registry freeze de tarefas que podem ocorrer depois.

Para manutenção, o ponto essencial é não executar registro arbitrário “quando der”: conteúdo estático deve seguir o pipeline de registries/lifecycle; networking e integrations precisam registrar-se na fase correta; trabalho client-only não deve vazar para dedicated server.

## 5. Event buses
NeoForge oferece uma arquitetura orientada a eventos para que mods observem ou modifiquem comportamento sem substituir classes inteiras sempre que possível.

Há eventos ligados à inicialização/mod lifecycle e eventos ligados à execução do jogo. Para qualquer projeto próprio, preferir hook/event oficial adequado antes de mixin invasivo. Quando um mixin for inevitável, registrar o motivo e o target exato, porque loader/Minecraft updates tornam bytecode patches uma das primeiras superfícies de regressão.

## 6. Registries
Os registries associam **IDs namespaced únicos** a objetos como blocks, items, entity types, effects, attributes e outros conteúdos registráveis.

NeoForge suporta padrões como `DeferredRegister` e eventos de registro. Há também registries data-driven/datapack registries para conteúdo carregável via dados.

### Falhas críticas de registry
- duplicate ID;
- registro tardio;
- referência a objeto ainda não registrado;
- datapack registry ausente;
- ID renomeado sem migration/remap;
- consumidor hardcoded em ID que deixou de existir.

Para perks/bridges do projeto próprio, **registry ID é contrato; display name não é**.

## 7. Tags
Tags agrupam objetos de registry sob IDs data-driven. São a base para muitas compatibilidades genéricas — por exemplo materiais equivalentes, ferramentas, biomas, entidades ou itens aceitos por recipes.

Uma integração robusta deve preferir tag quando a semântica é realmente de conjunto extensível, mas não usar tag para esconder diferença funcional entre dois itens que apenas parecem equivalentes.

## 8. Data Maps
Na linha oficial 1.21–1.21.1, **Data Maps** permitem anexar objetos data-driven a registry entries. Diferentemente de uma tag, que é aproximadamente registry object → boolean, um data map associa registry object → objeto de dados.

Características relevantes:
- carregamento por JSON;
- suportam registry estático e dynamic registry;
- participação no `/reload`;
- mecanismos de merge/conflito;
- possibilidade de sync quando o tipo é definido para isso.

Isso é uma superfície canônica para compatibilidade/configuração antes de escrever mixin ou tabela hardcoded.

## 9. Resource conditions e datapack lifecycle
Conteúdo data-driven pode ser condicionado ao ambiente e recarregado. O pack usa muitos datapacks, compat packs e mods que injetam recipes/tags/loot/worldgen; por isso `/reload` é uma operação de alto risco que precisa ser tratada como evento de integração real.

Após qualquer alteração data-driven, testar:
- startup frio;
- `/reload`;
- segundo `/reload` para idempotência;
- dedicated server;
- cliente entrando depois do reload;
- ausência de data/registry desync.

## 10. Data Components em ItemStack
Minecraft 1.21 usa **Data Components** para estado estruturado de `ItemStack`. NeoForge expõe os contratos necessários para mods criarem/lerem components próprios.

Um `DataComponentType<T>` identifica o componente; components podem ter codecs para disk/network. O stack usa mapas de components e patches para representar diferenças em relação aos defaults.

### Por que isso importa neste pack
Itens avançados carregam sockets, affixes, spell data, energy, custom state e outras informações. Sistemas próprios que copiam/recriam `ItemStack` incorretamente podem perder components mesmo quando preservam item id e count.

**Regra:** ao mover/copiar/serializar equipamento modded, preservar o stack completo e seus components; não reconstruir apenas `new ItemStack(item)` salvo quando isso é intencional.

## 11. Data Attachments
A linha NeoForge 1.21.1 possui sistema de **data attachments** para anexar dados a entidades, chunks e outros holders sem exigir que cada consumidor introduza campos diretamente na classe vanilla.

Attachments são apropriados para estado específico de entidade/chunk/block context, enquanto dados de nível/global podem usar SavedData. A linha 1.21.1 recebeu suporte a attachments sincronizáveis.

Para projetos próprios, attachments são candidatos naturais para estado persistente de jogador/perk quando o lifecycle e a authority forem bem definidos; não duplicar o mesmo estado simultaneamente em attachment, capability e SavedData sem uma única fonte de verdade.

## 12. SavedData
O sistema `SavedData` persiste dados adicionais associados ao level/dimension storage. O contrato exige marcar alterações como dirty para que sejam gravadas.

A documentação oficial recomenda attachments quando a informação pertence especificamente a entidades/chunks/block entities; `SavedData` é mais adequado para estado de nível/global.

Exemplos de uso coerente no projeto próprio:
- índice global de progressão de mundo;
- estado agregado de um sistema dimensional;
- tabelas persistentes não pertencentes a uma única entidade.

## 13. Capabilities
Capabilities expõem comportamento de forma dinâmica, separando **o que** um objeto oferece de **como** ele implementa isso.

NeoForge 1.21.1 oferece capabilities para:
- blocks/block entities;
- entities;
- item stacks.

Casos comuns incluem interfaces de:
- items (`IItemHandler`);
- fluids (`IFluidHandler`);
- energy (`IEnergyStorage`).

### Regra de integração
Antes de hardcodar classe de uma máquina, verificar se a função necessária está corretamente exposta por capability. Isso reduz acoplamento entre mods. Porém capability genérica não substitui API semântica quando a integração precisa distinguir estados internos/progressão/regras específicas.

## 14. Networking
A documentação 1.21–1.21.1 usa registro estruturado de **custom payloads**. Mods registram payload types/handlers para comunicação cliente↔servidor.

Networking precisa respeitar:
- direção correta;
- thread/context do handler;
- validação server-side de dados enviados pelo cliente;
- tamanho/frequência dos payloads;
- compatibilidade de protocolo entre versões;
- ausência de referências client-only em servidor.

### Authority
Cliente nunca deve ser considerado autoridade para concessão de item, perk, dano, mana ou progressão apenas porque enviou um packet. O servidor valida e aplica efeitos autoritativos.

## 15. Physical side vs logical side
NeoForge distingue **physical client/server** de **logical client/server**.

Exemplos:
- renderização, telas, keybinds e shaders pertencem ao physical client;
- regras de gameplay autoritativas pertencem ao logical server;
- em singleplayer, cliente e logical server podem existir no mesmo processo, o que pode esconder side bugs;
- dedicated server é o teste que revela referências a classes client-only em common/server code.

Qualquer mod que “funciona em singleplayer” ainda precisa de smoke test em dedicated server antes de ser considerado server-safe.

## 16. Configuração
NeoForge oferece sistema de configuração TOML baseado em NightConfig/`ModConfigSpec` para configurações client/common/server conforme o mod.

A classificação importa:
- **client:** apresentação/opções locais;
- **server:** regras autoritativas por mundo/server;
- **common:** depende do contrato do mod e não deve ser interpretada como “sempre sincronizada”.

Para automação do projeto, não editar config supondo que qualquer mudança aceita `/reload`; algumas configs exigem restart e o próprio mod define lifecycle.

## 17. Recursos cliente e servidor
O runtime participa do carregamento de:
- datapacks/server resources;
- resource packs/client assets;
- language files;
- models/textures/shaders;
- sounds;
- recipes/tags/loot/worldgen data.

Uma falha visual depois de resource reload pode pertencer ao asset pipeline, enquanto uma falha de recipe/tag pode pertencer ao server-data pipeline. Separar as duas classes de problema evita culpar o loader genericamente.

## 18. Datagen
O ecossistema NeoForge possui geração de dados para produzir JSONs e assets de maneira reprodutível: recipes, loot tables, tags, models e outros recursos podem ser gerados por providers.

Nos projetos próprios, datagen é preferível a centenas de JSONs manualmente duplicados quando o conteúdo segue regras repetíveis. O output gerado ainda precisa ser revisado e testado em runtime.

## 19. Access Transformers e bytecode patching
NeoForge oferece **Access Transformers** para alterar visibilidade/finalidade de elementos quando uma API pública não existe. Muitos mods também usam Mixins como transformação de bytecode.

Essas técnicas são mais frágeis que eventos/APIs estáveis porque dependem de classes/métodos concretos. O risco aumenta em updates de Minecraft/loader/provider.

### Ordem de preferência para projeto próprio
1. API oficial do provider;
2. evento/hook NeoForge;
3. capability/data-driven extension;
4. accessor/AT quando necessário;
5. mixin somente quando não há superfície melhor e com fail-closed/teste dedicado.

## 20. Compatibilidade com Fabric/Forge
Um JAR compilado para outro loader **não é automaticamente compatível**.

### Sinytra Connector / Forgified Fabric API
Quando presentes, essas soluções formam uma camada de compatibilidade dentro do ambiente NeoForge. Elas podem permitir que certos mods Fabric rodem, mas:
- não tornam todo mod Fabric suportado;
- não transformam a instância em Fabric;
- não eliminam diferenças de API/mixin/loader;
- cada mod precisa ser testado.

### Forge
NeoForge e Forge compartilham história/alguns conceitos, mas artefatos modernos não devem ser trocados como binariamente equivalentes sem publicação/compat explícita.

## 21. Relação com libraries do pack
Architectury, Balm, Moonlight/Selene, CreativeCore, GeckoLib, Puzzles Lib, Citadel, Cloth Config, FamiliarsLib etc. são **consumidores/infraestruturas específicas**, não “outros NeoForges”.

Remover uma library porque “NeoForge já tem API” quebra o consumidor compilado contra aquela library.

## 22. Classes de falha no bootstrap
### Dependency resolution
Mensagem típica: mod X requer Y/faixa Z.

### Mixin apply/injection
Pode indicar target alterado, conflito entre patches ou versão incompatível.

### Registry
Duplicate id, missing registry object, freeze/late registration.

### Networking
Payload/codec/registration incompatível.

### Client-only class no server
`NoClassDefFoundError`/dist error ao subir dedicated server.

### Datapack/worldgen
Falhas de codec/registry/data parse ao carregar mundo/reload.

A stacktrace completa e a **primeira causa real** devem ser preservadas; o último mod citado não é necessariamente o culpado.

## 23. Política de atualização para `21.1.248`
Não atualizar loader de produção apenas porque existe build mais nova. Antes:
1. comparar release notes/known issues;
2. procurar minimum/maximum NeoForge range dos mods críticos;
3. atualizar em cópia da instância;
4. executar bootstrap e testes transversais;
5. somente depois promover o novo runtime.

Se algum mod exige build mais nova por correção concreta, essa exigência deve ser registrada como dependência, não como recomendação vaga.

## 24. Matriz transversal de validação após update
1. Resolver 595 entradas top-level sem missing dependency.
2. Cliente até menu principal.
3. Dedicated server até `Done`.
4. Entrar no server e completar handshake.
5. Mundo novo.
6. Mundo existente com backup.
7. `/reload` duas vezes.
8. Resource reload no cliente.
9. Registry de blocks/items/entities/effects/attributes sem duplicates.
10. Networking de mods principais sem disconnect.
11. **Create/Sable/Aeronautics:** contraptions, sublevels e physics.
12. **Tom's Simple Storage:** rede, storage e integração logística/autocrafting conforme os addons presentes.
13. **Iron's/Ars/magia:** cast, mana, spell registries.
14. **RPG:** attributes/perks/damage pipeline.
15. **Worldgen:** Overworld/Nether/End + estruturas.
16. **Entities:** tame/AI/bosses.
17. **Rendering:** shader/resource packs/animations.
18. **Capabilities:** item/fluid/energy transfer.
19. **Persistence:** attachments/components/SavedData após restart.
20. Inspecionar logs por mixin warnings, payload errors e failed data parsing.

## 25. Regras para outros chats
- `21.1.248` é a authority de loader enquanto a modlist física não mudar.
- Não recomendar atualizar NeoForge sem motivo/teste.
- Não tratar library como substituta do loader.
- Não assumir que Forge/Fabric JAR funciona em NeoForge.
- Em integração custom, usar API/event/capability/data-driven contract antes de mixin quando possível.
- Gameplay deve permanecer server-authoritative.
- Singleplayer não substitui dedicated-server validation.

## 26. Fontes e confiança
**Authority física:** modlist do projeto em 08/09/2026.

**Documentação oficial 1.21–1.21.1:**
- [NeoForged Docs](https://docs.neoforged.net/)
- registries;
- capabilities;
- data components;
- data maps;
- SavedData;
- networking e demais contratos versionados.

**NeoForge User Guide:** Java 21 para Minecraft 1.20.5+ e operação client/server.

**Confiança:** muito alta para plataforma/API documentada e versão física. Compatibilidade de cada mod individual continua sendo propriedade do artefato/versão e será documentada em sua própria ficha.
