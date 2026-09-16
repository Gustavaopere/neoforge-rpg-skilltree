# Create: Fluid — 2.1.6

> **Reauditoria física e de migração — 16/09/2026.** A autoridade física atual é `fluid-2.1.6.jar`, mod id `fluid`, runtime `2.1.6`, SHA-1 `4f16ea44499ae31826fc5e3d2d12963c4a356b0d`, em NeoForge 1.21.1. O dossiê migrado do Notion foi preservado e atualizado para a release efetivamente instalada. A 2.1.6 incorpora as correções transacionais/logísticas documentadas e acrescenta comportamento explícito para fontes infinitas, Quicksand, Fluid Atomizer, buckets e interaction targets da Mechanical Pipette.

## Propriedades do registro

- **Mod:** Create: Fluid
- **Arquivo JAR:** `fluid-2.1.6.jar`
- **Versão 1.21.1 / runtime:** `2.1.6`
- **Minecraft / loader:** 1.21.1 / NeoForge
- **Categoria:** Tecnologia; Automação
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-fluid/files/8873260
- **Função:** Addon de Create para logística/processamento de fluidos: Mechanical Pipette, Centrifugal Pump, Copper Tap/Sink, Fluid Interfaces, Can Filler/Copper Can, Communicating Vessel, Gutter Outlet, válvulas, Fluid Atomizer e integração com package logistics/promises.
- **Dependências:** A release 2.1.6 declara Minecraft 1.21.1, NeoForge `21.1.219+` e Create `6.0.8+`; validação upstream foi feita com Create `6.0.11`. O pack instala Create `6.0.10`, dentro do range declarado.
- **Compatibilidade/Riscos:** Risco crítico em atomicidade de transferência/package-unpack, handlers terceiros não transacionais, promise settlement e sync client/server de interaction targets. A 2.1.6 corrige dupes, loops de autocrafting/self-routing, lava fuel timing, renderer, waterlogging, Mechanical Arm/Can Filler e recuperação dos targets da Pipette. Source público master já foi usado apenas para arquitetura quando seu metadata não correspondia exatamente ao artefato; release/JAR físico continuam authority da versão instalada.
- **Sobreposição:** Sobreposição parcial com outros addons Create de transporte, interfaces, bombas e fluid logistics. Não remover por tema: comparar por endpoint/contrato. Este mod possui package logistics, promises, Fluid Atomizer e interaction points próprios.
- **Observações:** Runtime físico confirmado: 2.1.6. Creative Fluid Tanks e Copper Sinks com água infinita agora anunciam supply ilimitado à logística; Can Fillers reconhecem fontes infinitas; Quicksand recebeu loot/tag de shovel; buckets drenados viram bucket vazio; Logistics Junction, Atomizer e Pipette receberam correções/interações específicas.
- **Procedência:** modlist física atual de 16/09/2026 + release oficial 2.1.6 (CurseForge file 8873260) + dossiê técnico migrado do Notion.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** REAUDITADO EM 16/09/2026 — migração preservada e estado físico atualizado de 2.1.5 para 2.1.6.
- **Data da última decisão:** 2026-08-26

## Dossiê operacional — padrão Alex's Mobs

> 🔎 **ESCOPO CANÔNICO.** O runtime físico é `fluid-2.1.6.jar`, mod id `fluid`, em Minecraft 1.21.1 / NeoForge. A release oficial 2.1.6 é a autoridade pública para comportamento desta versão. Qualquer source público sem pin exato continua sendo usado apenas para arquitetura/nomenclatura, não como prova de equivalência binária integral.

## 1. Identidade, versão e authority
- **Mod:** Create: Fluid.
- **JAR físico:** `fluid-2.1.6.jar`.
- **Mod id:** `fluid`.
- **Versão instalada:** `2.1.6`.
- **Minecraft / loader:** 1.21.1 / NeoForge.
- **Release oficial:** CurseForge file ID `8873260`, publicada em 13/09/2026.
- **Compatibilidade declarada:** NeoForge `21.1.219+` e Create `6.0.8+`; validação de desenvolvimento/runtime upstream com Create `6.0.11`.
- **Authority funcional:** Create continua sendo autoridade de rede cinética, stress, Mechanical Arms, fluid handlers/pipes e logística base. Create: Fluid acrescenta endpoints, máquinas e bridges de fluido; integra-se aos contratos do Create em vez de substituí-los.

## 2. Papel no modpack
O addon amplia logística e processamento de fluidos no ecossistema Create. O foco confirmado inclui transferência automatizada entre containers, bombeamento, interfaces/proxies de inventário fluido, embalagem de fluidos, coleta ambiental, válvulas, balanceamento entre vasos, fan processing com catalisadores fluidos e integração com a logística de pacotes/Factory Gauge/Stock Links.

## 3. Conteúdo confirmado da linha 2.1.x
A página oficial e o source público documentam como recursos centrais:
- **Mechanical Pipette / Pipette** — equivalente conceitual a um Mechanical Arm para fluidos, extraindo e injetando entre containers.
- **Conductor's Baton** — ferramenta de configuração de interaction points para Mechanical Arm, Mechanical Pipette, Ejector Depot, Postbox e Frogport.
- **Centrifugal Pump** — bomba de maior alcance/velocidade que Mechanical Pump.
- **Copper Tap** — retira fluido do container atrás e abastece Cauldrons/Basins ou executa spout processing abaixo.
- **Can Filler + Copper Can + Fluid Manifest** — empacotamento/logística de fluidos; Copper Cans são desempacotadas pelo Can Filler.
- **Fluid Atomizer** — usa fluido armazenado como catalisador de fan bulk processing e pode expelir nuvens de poção.
- **Communicating Vessel** — liga containers adjacentes sem energia e equaliza níveis de fluido.
- **Gutter Outlet** — coleta fluido/chuva/neve/lava gotejante acima ou drena para container abaixo; o source também contém `smart_gutter_outlet`.
- **Redstone Valve / Triple Valve** — controle e junção de fluxo por redstone.
- **Fluid Interface / Smart Fluid Interface** — proxy para acesso a container adjacente; a variante Smart adiciona filtragem.
- **Copper Sink** — endpoint de água/fluido; na linha atual pode anunciar fonte infinita à logistics network quando a opção correspondente está ativa.
- **Logistics Junction** e **Smart Repackager** — participam da logística/package handling.
- **Quicksand** — bloco/fluido associado a fan catalyst; na 2.1.6 possui block loot e shovel mining tag explicitamente adicionados.
- **Fan catalysts documentados:** Powder Snow, Quicksand, Slime Fluid, Haunting Fluid e Smoking Fluid.
- **Honeycomb Mold** — participa de receitas de compactação de honeycomb.

O source público possui ainda classes/registries de block entities, mounted fluid storage, packets, JEI, Ponder, recipes e partial models. Detalhes adicionais sem pin binário exato são tratados como arquitetura, não como contagem garantida do JAR.

## 4. Sistemas internos e contratos operacionais
### Transferência e endpoints de fluido
As máquinas expõem ou consomem handlers de fluido do ecossistema NeoForge/Create. Interfaces e sinks funcionam como pontos de acesso; Pipette e Pumps movimentam conteúdo. Em integrações próprias, não duplicar a transferência em tick/evento paralelo: o provider do handler deve permanecer authority sobre `simulate`/`execute` e capacidade.

### Logística empacotada e atomicidade
A 2.1.6 corrige item/fluid duplication quando Logistics Junction desempacota Brass Box, cardboard package ou Copper Can. A operação passa por simulação completa antes do commit; rollback afeta apenas conteúdo inserido pela transação corrente e, se um handler terceiro não transacional não puder ser revertido, o source package é consumido para impedir duplicação.

### Promises/autocrafting
A 2.1.6 corrige Can Fillers que liquidavam somente promises de Factory Gauge e ignoravam network promises de Stock Links. O objetivo é impedir repetição indefinida de autocrafting e self-routing de Copper Cans quando a rede não possui fluido suficiente.

### Fontes infinitas
Creative Fluid Tanks e Copper Sinks com infinite water habilitado agora anunciam **unlimited fluid supply** à fluid logistics network. Can Fillers reconhecem fontes infinitas e planejam requests segundo a capacidade de um pacote individual.

### Mechanical Pipette target sync
A 2.1.6 corrige perda dos interaction targets client-side após sair e reentrar no mundo. A reconstrução do target no cliente preserva as posições escolhidas pelo servidor enquanto chunks ainda sincronizam; mudanças são comparadas por NBT completo, e a validação server-side continua estrita.

### Blaze Burner / lava
Fluid-fed Blaze Burners passam a receber o valor correto para 1.000 mB de lava: **20.000 ticks**, equivalente a 1.000 segundos a 20 TPS, alinhado ao lava bucket do Create. O estado do fuel é explicitamente notificado após inserção para persistência/sync.

### Mounted storage e contraptions
O source público registra mounted fluid storage/movement behaviour para componentes como Copper Sink e Gutter Outlet. Integrações com contraptions devem respeitar montagem/desmontagem, serialização e ownership do storage do Create.

### Data reload / processing
O source público contém `AtomizerCompatReloadListener` e `AtomizerProcessingRegistry`, indicando superfície de reload para compat/processing do Fluid Atomizer. Integrações por datapack/resource reload devem ser idempotentes e limpar/substituir estado derivado em vez de acumular entradas.

## 5. Configuração e dados
- A documentação pública não fornece catálogo completo de todas as config keys; não presumir chaves/defaults não auditados.
- Há recipes, tags e Ponder/JEI no source; são superfícies de dados/cliente e devem ser consideradas em resource/datapack reload.
- A 2.1.6 adiciona block loot e shovel mining tag para Quicksand e mantém pickaxe tags para Can Filler, Logistics Junction e Smart Repackager.
- Powder Snow Bucket e Quicksand Bucket drenados com execução bem-sucedida agora retornam bucket vazio corretamente.

## 6. Client / server
- CurseForge classifica o mod como **Client & Server**.
- Transferência, handlers, block entities, logística e commits de conteúdo são server-authoritative.
- Renderers, partial models, particles, JEI e Ponder são client-facing.
- A 2.1.6 corrige crash de block entity rendering por registro tardio de `fluid_interface_drain`, cobrindo Fluid Interface, Smart Fluid Interface e Communicating Vessel; também melhora a dispersão de partículas do Fluid Atomizer em orientação vertical.
- O source possui packets; comandos/interações de configuração enviados pelo cliente precisam ser processados uma vez e validados no servidor.

## 7. Lifecycle
Validar especialmente:
- carga/descarga de block entities e chunks contendo máquinas com fluido;
- montagem/desmontagem de contraptions com mounted fluid storage;
- save/reload preservando volumes, filtros e state logístico;
- resource/datapack reload do Atomizer e recipes;
- mudança de dimensão/chunk de contraptions móveis;
- remoção/substituição do container adjacente a Fluid Interface/Smart Fluid Interface;
- perda de destino durante package/unpack;
- reconnect enquanto Mechanical Pipette possui targets configurados.

## 8. Multiplayer e concorrência
A correção de dupe torna atomicidade uma preocupação de primeira ordem. Dois jogadores, automações ou redes concorrentes não podem receber o mesmo conteúdo. Testes devem cobrir consumidores simultâneos, handler terceiro não transacional, capacidade parcial/zero e chunk unload entre simulation e commit.

## 9. Integrações concretas no pack
- **Create 6.0.10 instalado:** dentro do range oficial `6.0.8+`; upstream validou 2.1.6 com 6.0.11, então o runtime real do pack continua sendo um smoke-test necessário.
- **JEI / Ponder:** surfaces presentes para recipes/descoberta.
- **Outros addons Create de logística/fluidos:** coexistência não equivale a integração; avaliar sobreposição por endpoint.
- **Create Aeronautics e contraptions:** mounted storage deve ser validado no runtime; não é declarada integração específica sem evidência própria.

## 10. Delta específico da 2.1.6
### Added
- Creative Fluid Tanks e Copper Sinks infinitos anunciam supply ilimitado à logistics network.
- Block loot e shovel mining tags para Quicksand.

### Changed
- Sneak em Logistics Junction pula target selection e permite placement normal.
- Jogador em creative pode quebrar selectable targets normalmente.
- Melhor particle spread de Fluid Atomizers verticais.
- Powder Snow/Quicksand Buckets viram bucket vazio após drain executado.
- Copper Sink toca vanilla bucket-fill sound ao preencher water bucket.
- Can Fillers reconhecem fontes infinitas e dimensionam requests pela capacidade de pacote.

### Fixed
- restauração/sync dos targets da Mechanical Pipette após reconnect;
- timing/persistência de lava em fluid-fed Blaze Burners;
- duplication no package unpack do Logistics Junction;
- settlement de Stock Link promises e loops de autocrafting/self-routing;
- crash de renderer de Fluid Interfaces/Communicating Vessel;
- waterlogging de Fluid/Smart Fluid Interfaces;
- duplicate shaft rendering de Logistics Junction;
- Mechanical Arms reconhecendo Can Filler como packaging interaction point;
- pickaxe tags de Can Filler/Logistics Junction/Smart Repackager.

O upstream adicionou GameTests opt-in para Pipette target restoration, cauldron exchange, Blaze Burner lava timing e cadeia de duas Pipettes com lava; esses testes upstream não equivalem a runtime test executado neste pack.

## 11. Riscos técnicos
- **Dupe/transação parcial:** historicamente real e corrigido; regressão é risco crítico.
- **Promise não liquidada / loop de autocrafting:** corrigido na 2.1.6.
- **Self-routing de Copper Cans:** corrigido na 2.1.6.
- **Handler terceiro não transacional:** rollback pode ser impossível; exige teste com storages/handlers do pack.
- **Target sync client/server da Pipette:** corrigido, mas reconnect/chunk sync continua regression gate.
- **Block entity / partial-model classloading:** houve crash de renderer corrigido.
- **Waterlogging:** Interfaces foram corrigidas para não serem deslocadas por água corrente.
- **Mechanical Arm interaction point:** reconhecimento de Can Filler foi corrigido.
- **Create 6.0.10 vs upstream validation 6.0.11:** compatibilidade está no range declarado, mas não foi runtime-tested nesta auditoria.

## 12. Matriz de testes obrigatória
- [ ] Dedicated server boot com `fluid-2.1.6.jar` + Create 6.0.10.
- [ ] Save/reload com cada block entity contendo fluido.
- [ ] Chunk unload/reload durante bombeamento e package/unpack.
- [ ] Mechanical Pipette entre handlers vanilla/Create/terceiros.
- [ ] Configurar targets da Pipette → sair/entrar no mundo → confirmar reconstrução client-side sem alterar authority server-side.
- [ ] Mechanical Arm reconhecendo Can Filler exatamente uma vez.
- [ ] Copper Sink recebendo input por funnel/belt funnel e enchendo bucket com feedback correto.
- [ ] Infinite water do Copper Sink/Creative Fluid Tank anunciado corretamente à network.
- [ ] Can Filler com Factory Gauge e Stock Link; promises liquidadas sem repetição infinita.
- [ ] Logistics Junction desempacotando Brass Box/cardboard package/Copper Can sem dupe sob capacidade total, parcial e zero.
- [ ] Handler terceiro não transacional: falha de commit sem duplicação.
- [ ] Fluid Interface/Smart Interface/Communicating Vessel renderizando sem crash e waterlogged.
- [ ] Blaze Burner recebendo 1.000 mB lava e mantendo 20.000 ticks/persistência esperada.
- [ ] Powder Snow/Quicksand Bucket drain retorna bucket vazio.
- [ ] Resource/datapack reload do Fluid Atomizer sem duplicar registros derivados.
- [ ] Multiplayer com dois consumidores concorrentes sobre o mesmo source.
- [ ] Contraption assemble/disassemble preservando mounted fluid storage aplicável.

## 13. Evidências e limites
**Primárias/canônicas:** modlist física atual; CurseForge oficial da release 2.1.6 e seu changelog; repositório oficial `adonis-baffin/CreateFluid` usado somente dentro dos limites documentados.

**Runtime local:** nenhum teste desta matriz foi executado durante a auditoria documental. Os GameTests relatados pelo upstream validaram ambiente de desenvolvimento com Create 6.0.11 e não são promovidos a resultado deste pack.

**Não foi executado teste de runtime nesta catalogação.** A matriz acima é plano de validação, não resultado aprovado.