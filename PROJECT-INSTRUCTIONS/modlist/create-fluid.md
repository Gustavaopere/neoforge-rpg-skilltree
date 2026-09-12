# Create: Fluid — 2.1.5

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81cc9951fed3c43f30f1  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-09

## Propriedades do registro

- **Mod:** Create: Fluid
- **Arquivo JAR:** `fluid-2.1.5.jar`
- **Versão 1.21.1:** `2.1.5`
- **Categoria:** Tecnologia; Automação
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-fluid/files/8520817
- **Função:** Addon de Create para logística/processamento de fluidos: Mechanical Pipette, Centrifugal Pump, Copper Tap/Sink, Fluid Interfaces, Can Filler/Copper Can, Communicating Vessel, Gutter Outlet, válvulas, Fluid Atomizer e integração com package logistics/promises.
- **Dependências:** Create 6.0.8+ segundo a release 2.1.5; pack instala Create 6.0.10. NeoForge 21.1.219+ declarado para a release.
- **Compatibilidade/Riscos:** Risco crítico em atomicidade de transferência/package-unpack, handlers terceiros não transacionais e promise settlement; 2.1.5 corrigiu dupe, loops de autocrafting/self-routing, renderer e interação Mechanical Arm. Source público master tem version drift (Gradle 2.1.0), portanto não é tratado como pin binário exato.
- **Sobreposição:** Sobreposição parcial com outros addons Create de transporte, interfaces, bombas e fluid logistics. Não remover por tema: comparar por endpoint/contrato. Este mod possui package logistics, promises, Fluid Atomizer e interaction points próprios.
- **Observações:** Runtime físico confirmado: fluid 2.1.5. Release suporta Create 6.0.8+ e foi validada upstream com 6.0.11; pack usa Create 6.0.10. Source master inspecionado declara mod_version 2.1.0, então nomes/arquitetura do source foram usados com fail-closed.
- **Procedência:** modlist.txt física atual de 09/09/2026 + release oficial CurseForge Create: Fluid 2.1.5 (file 8520817) + source oficial adonis-baffin/CreateFluid inspecionado com divergência de versionamento explicitada.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Create: Fluid 2.1.5; conteúdo, handlers/transações, package logistics/promises, mounted storage, lifecycle, riscos de dupe e matriz de testes catalogados; source drift registrado.
- **Data da última decisão:** 2026-08-26

## Dossiê operacional — padrão Alex's Mobs

> 🔎 **ESCOPO CANÔNICO.** O runtime físico é `fluid-2.1.5.jar`, mod id `fluid`, em Minecraft 1.21.1 / NeoForge. A release oficial 2.1.5 é a autoridade pública para comportamento desta versão. O branch público `master` foi inspecionado, mas seu `gradle.properties` ainda declara `mod_version = 2.1.0`; portanto ele é usado para arquitetura e nomes concretos, não como prova de equivalência binária integral com o JAR 2.1.5.

## 1. Identidade, versão e authority
- **Mod:** Create: Fluid.
- **JAR físico:** `fluid-2.1.5.jar`.
- **Mod id:** `fluid`.
- **Versão instalada:** `2.1.5`.
- **Minecraft / loader:** 1.21.1 / NeoForge.
- **Release oficial:** CurseForge file ID `8520817`, publicada em 27/07/2026 para NeoForge 1.21.1.
- **Compatibilidade declarada da 2.1.5:** NeoForge `21.1.219+` e Create `6.0.8+`; a validação de desenvolvimento da release foi feita com Create `6.0.11`.
- **Authority funcional:** Create continua sendo autoridade de rede cinética, stress, Mechanical Arms, fluid handlers/pipes e logística base. Create: Fluid acrescenta endpoints, máquinas e bridges de fluido; integra-se aos contratos do Create em vez de substituí-los.

## 2. Papel no modpack
O addon amplia logística e processamento de fluidos no ecossistema Create. O foco confirmado inclui transferência automatizada entre containers, bombeamento, interfaces/proxies de inventário fluido, embalagem de fluidos, coleta ambiental, válvulas, balanceamento entre vasos, fan processing com catalisadores fluidos e integração com a logística de pacotes/Factory Gauge/Stock Links.

## 3. Conteúdo confirmado da release 2.1.x
A página oficial documenta como recursos centrais:
- **Mechanical Pipette / Pipette** — equivalente conceitual a um Mechanical Arm para fluidos, extraindo e injetando entre containers. O source público registra `pipette` como bloco cinético e aplica impacto de stress `2.0` nesse estado do source.
- **Conductor's Baton** — ferramenta de configuração de interaction points para Mechanical Arm, Mechanical Pipette, Ejector Depot, Postbox e Frogport.
- **Centrifugal Pump** — bomba de maior alcance/velocidade que Mechanical Pump; source público registra `centrifugal_pump`, com possibilidade de encasing e impacto de stress `8.0` no estado inspecionado.
- **Copper Tap** — retira fluido do container atrás e abastece Cauldrons/Basins ou executa spout processing abaixo.
- **Can Filler + Copper Can + Fluid Manifest** — empacotamento/logística de fluidos; Copper Cans são desempacotadas pelo Can Filler.
- **Fluid Atomizer** — usa fluido armazenado como catalisador de fan bulk processing e pode expelir nuvens de poção.
- **Communicating Vessel** — liga containers adjacentes sem energia e equaliza níveis de fluido.
- **Gutter Outlet** — coleta fluido/chuva/neve/lava gotejante acima ou drena para container abaixo. O source também contém `smart_gutter_outlet`.
- **Redstone Valve / Triple Valve** — controle e junção de fluxo por redstone.
- **Fluid Interface / Smart Fluid Interface** — proxy para acesso a container adjacente; a variante Smart adiciona filtragem.
- **Copper Sink** — endpoint de água/fluido confirmado no source e na release 2.1.5; a 2.1.5 acrescentou entrada direta por funnels/belt funnels.
- **Logistics Junction** e **Smart Repackager** — aparecem como registros concretos no source público e participam das correções transacionais da 2.1.5.
- **Quicksand** — bloco/fluido associado a fan catalyst; a 2.1.5 adicionou loot de bloco e mining tag de shovel.
- **Fan catalysts documentados:** Powder Snow, Quicksand, Slime Fluid, Haunting Fluid e Smoking Fluid.
- **Honeycomb Mold** — participa de receitas de compactação de honeycomb.

O source público possui ainda classes/registries de block entities, mounted fluid storage, packets, JEI, Ponder, recipes e partial models. Como o source público não está version-pinado em 2.1.5 pelo metadata Gradle, a existência de cada detalhe adicional deve ser tratada como estruturalmente confirmada no source atual, não como contagem exata do binário instalado.

## 4. Sistemas internos e contratos operacionais
### Transferência e endpoints de fluido
As máquinas expõem ou consomem handlers de fluido do ecossistema NeoForge/Create. Interfaces e sinks funcionam como pontos de acesso; Pipette e Pumps movimentam conteúdo. Em integrações próprias, não duplicar a transferência em tick/evento paralelo: o provider do handler deve permanecer autoridade sobre `simulate`/`execute` e capacidade.

### Logística empacotada
Can Filler/Copper Can/Fluid Manifest convertem fluido em unidade transportável para a malha logística. A release 2.1.5 corrigiu um dupe ao desempacotar Brass Box, cardboard package e Copper Can: agora simula toda a operação antes de commit; rollback deve afetar apenas o conteúdo inserido pela transação corrente; quando um handler terceiro não transacional não pode ser revertido, a source package é consumida para impedir duplicação.

### Promises/autocrafting
A 2.1.5 corrigiu o settlement de promises: Can Fillers passaram a resolver não só Factory Gauge promises, mas também network promises de Stock Links. O bug anterior podia repetir pedidos de autocrafting indefinidamente e fazer Can Fillers enviarem Copper Cans para si mesmos quando faltava fluido.

### Mounted storage e contraptions
O source público registra mounted fluid storage/movement behaviour para componentes como Copper Sink e Gutter Outlet. Qualquer integração com contraptions deve respeitar montagem/desmontagem, serialização e ownership do storage do Create.

### Data reload / processing
O source público contém `AtomizerCompatReloadListener` e `AtomizerProcessingRegistry`, indicando superfície de reload para compat/processing do Fluid Atomizer. Integrações por datapack/resource reload devem ser idempotentes e limpar/substituir estado derivado em vez de acumular entradas.

## 5. Configuração e dados
- A documentação pública da 2.1.5 não fornece catálogo completo de opções configuráveis; não presumir chaves ou defaults não auditados.
- Há recipes, tags e Ponder/JEI no source público; eles são superfícies de dados/cliente e devem ser considerados em resource/datapack reload.
- Tags de mineração foram objeto de correção na 2.1.5 para Quicksand, Can Filler, Logistics Junction e Smart Repackager.

## 6. Client / server
- CurseForge classifica o mod como **Client & Server**.
- Transferência, handlers, block entities, logística e commits de conteúdo são responsabilidades server-authoritative.
- Renderers, partial models, particles, JEI e Ponder são client-facing. A 2.1.5 corrigiu crash de block entity rendering causado por registro tardio de `fluid_interface_drain`; o caminho afetava Fluid Interface, Smart Fluid Interface e Communicating Vessel.
- O source possui packets; validar que comandos/interações de configuração enviados pelo cliente sejam processados uma vez e validados no servidor.

## 7. Lifecycle
Validar especialmente:
- carga/descarga de block entities e chunks contendo máquinas com fluido;
- montagem/desmontagem de contraptions com mounted fluid storage;
- save/reload do mundo preservando volumes, filtros e estado logístico;
- resource/datapack reload do Atomizer e recipes;
- mudança de dimensão/chunk de contraptions móveis;
- remoção/substituição do container adjacente a Fluid Interface/Smart Fluid Interface;
- perda de destino durante uma transação de package/unpack.

## 8. Multiplayer e concorrência
A correção de dupe da 2.1.5 torna atomicidade uma preocupação de primeira ordem. Dois jogadores, automações ou redes concorrentes não podem receber o mesmo conteúdo. Testes devem cobrir dois consumidores simultâneos, handler terceiro não transacional, falta parcial de capacidade e chunk unload entre simulation e commit.

## 9. Integrações concretas no pack
- **Create 6.0.10 instalado:** dependência funcional central; está dentro do range oficial `6.0.8+` da release 2.1.5, embora o changelog cite validação com 6.0.11. Essa diferença deve ser smoke-tested no runtime real do pack.
- **JEI / Ponder:** surfaces presentes no source para visualização/descoberta de recipes.
- **Outros addons Create de logística/fluidos:** coexistência não equivale a integração. Avaliar sobreposição por endpoint e não por tema.
- **Create Aeronautics e contraptions:** qualquer compatibilidade de mounted storage deve ser validada no runtime; não é declarada aqui como integração específica sem evidência própria.

## 10. Riscos técnicos
- **Dupe/transação parcial:** historicamente real e corrigido em 2.1.5; regressão é risco crítico.
- **Promise não liquidada / loop de autocrafting:** historicamente real e corrigido em 2.1.5.
- **Self-routing de Copper Cans:** corrigido em 2.1.5; testar ausência de loop.
- **Handler terceiro não transacional:** rollback pode ser impossível; exige teste com outros storages/handlers do pack.
- **Block entity / partial-model classloading:** houve crash de renderer corrigido na release.
- **Waterlogging:** Fluid Interfaces foram corrigidas para não serem deslocadas por água corrente.
- **Mechanical Arm interaction point:** reconhecimento de Can Filler foi corrigido; testar com braços reais do Create.
- **Version drift do source:** `master` público inspecionado não se identifica como 2.1.5 no Gradle; não derivar garantias binárias de detalhes não sustentados pelo release changelog/JAR.

## 11. Matriz de testes obrigatória
- [ ] Dedicated server boot com `fluid-2.1.5.jar` + Create 6.0.10.
- [ ] Save/reload com cada block entity contendo fluido.
- [ ] Chunk unload/reload durante bombeamento e durante package/unpack.
- [ ] Mechanical Pipette entre handlers vanilla/Create/terceiros.
- [ ] Mechanical Arm reconhecendo Can Filler exatamente uma vez.
- [ ] Copper Sink recebendo input por funnel e belt funnel.
- [ ] Infinite water do Copper Sink/Creative Fluid Tank anunciado corretamente à network.
- [ ] Can Filler com Factory Gauge e Stock Link; promises liquidadas e sem repetição infinita.
- [ ] Logistics Junction desempacotando Brass Box, cardboard package e Copper Can sem dupe sob capacidade total, parcial e zero.
- [ ] Handler terceiro não transacional: falha de commit sem duplicação.
- [ ] Fluid Interface/Smart Interface/Communicating Vessel renderizando sem crash e permanecendo waterlogged.
- [ ] Resource/datapack reload do Fluid Atomizer sem duplicar registros derivados.
- [ ] Multiplayer com dois consumidores concorrentes sobre o mesmo source.
- [ ] Contraption assemble/disassemble preservando mounted fluid storage aplicável.

## 12. Evidências e limites
**Primárias/canônicas:** modlist física atual; CurseForge oficial da release 2.1.5 e seu changelog; repositório oficial `adonis-baffin/CreateFluid` inspecionado no branch `master`.

**Source drift registrado:** o `gradle.properties` público atual declara `mod_version = 2.1.0`, Minecraft 1.21.1, NeoForge 21.1.219 e Create 6.0.11. Por isso o source confirma arquitetura/nomenclatura, enquanto a release oficial e o JAR físico governam a versão instalada.

**Não foi executado teste de runtime nesta catalogação.** A matriz acima é plano de validação, não resultado aprovado.