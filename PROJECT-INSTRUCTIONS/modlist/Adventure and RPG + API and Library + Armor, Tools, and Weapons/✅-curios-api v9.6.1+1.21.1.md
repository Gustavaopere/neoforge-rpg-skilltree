# Curios API — 9.6.1+1.21.1

## Propriedades vigentes — 27/11/2026

- **Mod:** Curios API
- **Arquivo JAR instalado (declarado):** `curios-neoforge-9.6.1-1.21.1.jar`
- **Versão instalada declarada:** `9.6.1+1.21.1` — Release, Minecraft 1.21.1 / NeoForge.
- **Ordem física histórica:** #211 (preservada).
- **Categoria CurseForge / pasta:** Adventure and RPG + API and Library + Armor, Tools, and Weapons.
- **Função:** API de acessórios/equipamento, slots, modifiers, integração de inventário, datapacks e consumers.
- **Atualização/Status:** `9.6.0+1.21.1` → `9.6.1+1.21.1`.
- **Estado no pack:** instalação declarada pelo usuário, sem leitura física nova.
- **Estado da pesquisa:** publicações identificadas e deltas disponíveis auditados/reutilizados; limites de evidência explicitados, sem certificação de runtime.
- **Dependências / compatibilidade / riscos:** 9.6.0 exigia NeoForge >=21.1.265; índice #001 ainda registra 21.1.250. Mínimo exato da 9.6.1 não verificado; não presumir redução. Pendência do loader mantida sem bloquear documentação. Testar datapacks de entidades, equip/unequip, Curios/Ars 'n Spells 3.4.3, consumidores, modifiers e multiplayer; sem QA.
- **Fonte:** [publicação oficial CurseForge](https://www.curseforge.com/minecraft/mc-mods/curios/files/9806453).
- **Procedência:** declaração expressa do usuário e distribuição oficial; não inferir hashes nem metadata do JAR novo.

## Auditoria incremental e reconciliação

9.6.1+1.21.1 — changelog datado 24/11, publicação 25/11/2026: refina parsing de entidades Curios em datapacks; sem descrição de novas regras/schema. A 9.6.0 anterior já auditada inclui prioridade de slots específicos, eventos NeoForge Fortune/Looting e correções de sync. Sem versão intermediária localizada; arquivo principal usa -1.21.1, changelog +1.21.1; não confundir com -api ou -sources adicionais.

## Histórico documental preservado

> O conteúdo abaixo corresponde aos snapshots e auditorias anteriores. Menções a versão física, atual, candidata, não instalada, hashes e dependências são históricas; as propriedades vigentes prevalecem.

> **Estado documental vigente:** `9.5.1+1.21.1` → **`9.6.0+1.21.1`**, JAR `curios-neoforge-9.6.0+1.21.1.jar` ([CurseForge](https://www.curseforge.com/minecraft/mc-mods/curios/files/9694505)), **atualização informada pelo usuário em 22/11/2026**. Não existe verificação independente do JAR na pasta local do usuário.

> **Changelog e requisitos interlotes:** 9.6.0 (19/11/2026): shift-click prioriza slots específicos sobre curio genérico; Fortune/Looting migram aos eventos oficiais NeoForge; fixes de datapack entity parsing, tooltip, transient ItemStack desync/disconnect e localização pt_br. A release declara **NeoForge mínimo 21.1.265**, acima do 21.1.250 do snapshot documental antigo. Registrar atualização do loader como pendência separada de lote/ambiente, sem cancelar a atualização documental declarada de Curios. A diferença de versão de um provider/loader documental ainda não atualizado não impede a sincronização deste item.


> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#211**: JAR `curios-neoforge-9.5.1+1.21.1.jar`, mod id `curios`, runtime `9.5.1+1.21.1`, SHA-1 `418fcd42e3a7844c9bdc71c9b6401fdb3894e0c4`.

## Propriedades do registro

- **Mod:** Curios API
- **Arquivo JAR instalado (declaração do usuário):** `curios-neoforge-9.6.0+1.21.1.jar` ([CurseForge](https://www.curseforge.com/minecraft/mc-mods/curios/files/9694505)).
- **Versão instalada declarada (Minecraft 1.21.1 / NeoForge):** `9.6.0+1.21.1`. A `9.5.1+1.21.1` era registro histórico.
- **Categoria:** Biblioteca, RPG
- **Função:** API flexível de acessórios/equipamentos que permite a mods consumidores definir e usar slots adicionais, consultar itens equipados e integrar regras de equip/unequip sem substituir os slots vanilla por um inventário paralelo próprio do Curios.
- **Dependências:** NeoForge 1.21.1. Curios é infraestrutura Client & Server e sua necessidade no pack é transversal: vários mods e bridges a consomem. Slots concretos, regras e efeitos pertencem aos consumers, não ao Curios por padrão.
- **Estado no pack:** `9.6.0+1.21.1` declarada como instalada pelo usuário; edição documental no GitHub, não leitura direta do JAR no computador.
- **Estado da pesquisa:** série de releases auditada no dossier; sem teste in-game, sem extração de metadata do novo JAR e sem compatibilidade global presumida.
- **Compatibilidade/Riscos:** Dependência transversal e stateful de equipamentos. Riscos: version drift com consumers, slot definitions divergentes após datapack/config reload, equip/unequip hooks duplicados, sync client/server e world-upgrade migrations. A 9.5.1 corrige crash ao carregar mundo após update vindo de 9.4.2+1.21.1 ou inferior.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/curios/files/6529130
- **Procedência:** posição antiga da modlist histórica; upgrade declarado em 22/11/2026 e nome exato do arquivo [CurseForge](https://www.curseforge.com/minecraft/mc-mods/curios/files/9694505); sem hash do novo arquivo local. 
- **Observações vigentes:** `curios-neoforge-9.6.0+1.21.1.jar`, versão `9.6.0+1.21.1` por declaração do usuário. O runtime histórico do NeoForge 21.1.250 não demonstra conformidade com o mínimo da 9.6.0; sincronização do loader em separado.
- **Atualização/Status:** 22/11/2026 — `9.5.1+1.21.1` → `9.6.0+1.21.1`, JAR oficial `curios-neoforge-9.6.0+1.21.1.jar`. 9.6.0 (19/11/2026): shift-click prioriza slots específicos sobre curio genérico; Fortune/Looting migram aos eventos oficiais NeoForge; fixes de datapack entity parsing, tooltip, transient ItemStack desync/disconnect e localização pt_br. A release declara **NeoForge mínimo 21.1.265**, acima do 21.1.250 do snapshot documental antigo. Registrar atualização do loader como pendência separada de lote/ambiente, sem cancelar a atualização documental declarada de Curios.
- **Decisão:** Sem decisão
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Curios API 9.5.1+1.21.1 foi reconfirmado como `Instalado` na modlist física de 595; o dossiê Alex já aplicado foi preservado e necessidade técnica por consumers não foi convertida em decisão curatorial.
- **Sobreposição:** Não substitui os equipamentos dos mods consumidores nem Curios-style systems sem port explícito. Pode coexistir com outros slot APIs; conflito real exige dois providers disputando o mesmo equipamento/state ou bridge duplicando eventos.

# Dossiê operacional — padrão Alex's Mobs
> 💍 Versão física confirmada: `curios-neoforge-9.5.1+1.21.1.jar`, mod id `curios`, runtime `9.5.1+1.21.1`, NeoForge 1.21.1. Curios é uma **API de acessórios/equipamentos**, não um provider de progressão próprio.
## 1. Papel e authority
Curios oferece infraestrutura para mods consumidores criarem/consultarem equipamentos em slots adicionais. O consumer define o item, efeito, regra de equipagem e semântica final; Curios fornece o contract comum de armazenamento/consulta/eventos.
Não atribuir ao Curios stats, cooldowns ou progressão de um anel/charm/back item que pertencem ao provider do item.
## 2. Topologia de slots consumer-defined
A API é flexível/expansível: **não existe uma lista universal obrigatória de slots** válida para todo pack. Consumers e data/config podem expor os tipos necessários.
Integrações próprias devem descobrir a topologia efetiva do runtime em vez de hardcodar `ring`, `necklace`, `back` ou qualquer nome por convenção.
## 3. Equip/unequip
Equipar e remover acessórios é state de inventário/equipamento que precisa settlement uma única vez no servidor/common. Consumers podem reagir a transitions; listeners externos não devem reaplicar o mesmo atributo/efeito após um evento já processado pelo provider.
Retry, reconnect ou slot rebuild não pode duplicar item nem efeito.
## 4. Consulta de acessórios
Mods podem consultar itens equipados via Curios para aplicar comportamento próprio. A consulta é infraestrutura; a decisão de que um item concede voo, mana, armor, spell bonus ou outra feature pertence ao consumer.
Caching externo precisa ser invalidado em equip/unequip, death/clone, dimension transition e mudanças de slot topology.
## 5. Atributos e efeitos
Acessórios frequentemente adicionam modifiers ou effects. Curios organiza o contexto de equipagem, mas o modifier final deve ser registrado/removido pelo consumer exatamente uma vez.
Não contar um cosmetic slot ou cópia renderizada como segundo equipamento funcional.
## 6. Sync client/server
Inventário/slot state que afeta gameplay deve ser coerente com o servidor. UI/render do slot é apresentação.
Em multiplayer, cliente não pode fabricar um accessory state apenas alterando GUI local; server state precisa convergir para todos os observers/consumers relevantes.
## 7. Datapack/config lifecycle
Topologia/regras de slots podem depender da configuração e do stack do pack. Reload deve reconstruir o que a versão/consumer suportar sem perder itens ou manter slots stale.
Qualquer remoção/renomeação de slot em pack existente deve ser testada em cópia do mundo antes de produção.
## 8. Upgrade migration — 9.5.1
O changelog exato da build física corrige **crash no world load após atualizar de `9.4.2+1.21.1` ou inferior**.
Esse é o regression gate principal da 9.5.1: mundos com state Curios anterior precisam carregar sem crash e preservar inventário/equipamentos conforme o contract da migração.
## 9. Consumers concretos do pack
O catálogo já contém integrações/consumers que dependem ou usam Curios, como Artifacts e compatibilidades específicas de outros providers. Essas presenças demonstram a transversalidade da API, mas não autorizam inferir que todo mod RPG use Curios.
Dependency graph e metadata de cada consumer continuam sendo a prova de necessidade.
## 10. Coexistência com outros slot systems
Outro mod pode possuir slots funcionais próprios. Coexistência não é duplicação automática.
Conflito real ocorre quando dois systems tentam armazenar/sincronizar o mesmo item ou disparar os mesmos effects como se fossem o mesmo slot. Bridges devem escolher uma authority e adaptar, não espelhar indefinidamente em ambos.
## 11. Client/server
Curios é Client & Server. Inventory/equip state e effects de gameplay são common/server; screen, icons e render são client-side.
Dedicated server não deve depender de classes gráficas para registrar/consultar slots.
## 12. Lifecycle
Validar player login, equip/unequip, death/respawn, dimension change, disconnect/reconnect, world restart, datapack/config reload e update de versão.
Itens equipados não podem sumir, duplicar ou reaparecer em slot antigo após topology migration.
## 13. Riscos
1. Remover Curios com consumer ativo.
2. Hardcodar slot que não existe no runtime.
3. Double modifier/effect por dois listeners.
4. Item perdido ao remover/renomear slot.
5. Client UI divergir do inventory server-side.
6. Cache externo stale após equip/reload.
7. Update de versão quebrar world migration.
8. Outro slot API ser tratado como substituto binário.
## 14. Matriz de testes
1. Dedicated server boot com consumers atuais.
2. Client join e abertura de interface Curios/consumer.
3. Equip/unequip de acessórios de diferentes providers.
4. Verificar modifiers/effects exatamente uma vez.
5. Death/respawn com accessories equipados.
6. Dimension change/reconnect/restart preservando state.
7. Reload de datapack/config com topology estável.
8. Cópia de mundo de versão Curios \<=9.4.2 → 9.5.1, regression #520.
9. Multiplayer: dois jogadores com loadouts distintos sem state leak.
10. Coexistência com outro slot system/bridge sem dupe.
## 15. Evidência
- modlist física atual: Curios API 9.5.1+1.21.1;
- CurseForge oficial: flexible/expandable accessory/equipment API, Client & Server;
- release/File ID 6529130 para NeoForge 1.21.1;
- changelog 9.5.1: fix de crash no world load ao atualizar de 9.4.2+1.21.1 ou inferior.
> 🔗 Boundary canônico: **Curios organiza slots/equipamento; consumers definem os itens, regras e efeitos**. A topologia deve ser descoberta do runtime, não inventada.


## 16. Revalidação de source, slots e risco upstream #616 — 13/10/2026

**Autoridade do estado instalado:** inventário histórico mais recente de **587 JARs/modloader top-level**: **NeoForge #001 21.1.250**, **Curios API #211 9.5.1+1.21.1**, arquivo `curios-neoforge-9.5.1+1.21.1.jar`. Não foi obtido acesso ao diretório físico do usuário nem executada nova inspeção do JAR em 13/10/2026. A referência antiga a 595 itens corresponde à snapshot de 11/09/2026; não converter essa contagem ou a antiga posição #213 em estado atual.

**Release publicada:** a [listagem oficial CurseForge filtrada Minecraft 1.21.1](https://www.curseforge.com/minecraft/mc-mods/curios/files/all?page=1&version=1.21.1) mantém **`9.5.1+1.21.1`** como última release **NeoForge/1.21.1**, [File ID `6529130`, publicada em 14/05/2025](https://www.curseforge.com/minecraft/mc-mods/curios/files/6529130). O fix oficial dessa release é a migração do mundo após `9.4.2+1.21.1` ou anterior, [issue #520](https://github.com/TheIllusiveC4/Curios/issues/520). O projeto possui releases 2026 para outras versões de Minecraft, mas isso **não cria atualização compatível nova para 1.21.1**.

**Source exato da linha:** [`TheIllusiveC4/Curios` branch 1.21.1, commit `6b122c8a7e2b514fd94197459ade11f19a0bfb09`](https://github.com/TheIllusiveC4/Curios/tree/6b122c8a7e2b514fd94197459ade11f19a0bfb09), cujo `gradle.properties` declara `version=9.5.1+1.21.1`, `mod_id=curios`, `minecraft_version=1.21.1`, `java_version=21` e faixa NeoForge `[21.1.60,)`. Essa metadata fonte **aceita numericamente** o loader histórico `21.1.250`; não é prova de compatibilidade in-game com todas as integrações. Não se demonstrou identidade de bytecode source↔JAR físico.

### 16.1. Contratos observáveis de API 9.5.1 (relevantes para atributos/perks)

A classe pública [`CuriosApi.java`](https://github.com/TheIllusiveC4/Curios/blob/6b122c8a7e2b514fd94197459ade11f19a0bfb09/neoforge/src/main/java/top/theillusivec4/curios/api/CuriosApi.java) define `getCuriosInventory(LivingEntity)` com retorno `Optional<ICuriosItemHandler>`, `getCurio(ItemStack)` com `Optional<ICurio>`, `getSlot(String, Level)`, `getSlots(Level)`, `getItemStackSlots(ItemStack, LivingEntity)` e `getAttributeModifiers(SlotContext, ResourceLocation, ItemStack)`. Muitos desses métodos aparecem como *stubs* `apiError()` na classe API de compilação; o runtime injeta a implementação por [`MixinCuriosApi`](https://github.com/TheIllusiveC4/Curios/blob/6b122c8a7e2b514fd94197459ade11f19a0bfb09/neoforge/src/main/java/top/theillusivec4/curios/mixin/core/MixinCuriosApi.java). Portanto **não interpretar o `Optional.empty()` do source stub como comportamento funcional do runtime** nem contornar esses contratos com mixins próprios sem necessidade.

O [`ICuriosItemHandler`](https://github.com/TheIllusiveC4/Curios/blob/6b122c8a7e2b514fd94197459ade11f19a0bfb09/neoforge/src/main/java/top/theillusivec4/curios/api/type/capability/ICuriosItemHandler.java) da versão 9.5.1 expõe:
- `getCurios()` → `Map<String, ICurioStacksHandler>`; `getStacksHandler(String)` → `Optional<ICurioStacksHandler>` (consultar a topologia real em vez de inventar `ring`/`back`);
- `getEquippedCurios()` → `IItemHandlerModifiable`, **explicitamente excluindo os cosméticos** na documentação de interface;
- `isEquipped(Item)`, `isEquipped(Predicate<ItemStack>)`, `findFirstCurio(...)`, `findCurios(...)` e `isSlotActive(String, int)` para consultas;
- `setEquippedCurio(String, int, ItemStack)`, `addTransientSlotModifier`, `addPermanentSlotModifier` e `removeSlotModifier` para mutações que exigem validação de ownership e lado servidor.

**Implicação de design para perks:** usar consultas provider-native para verificar acessório equipado e estado do slot, com avaliação server-authoritative no momento adequado; um item cosmético não é segundo item funcional; distinguir o efeito do mod consumidor, o modifier de slot e um atributo próprio da árvore, evitando double-application. Nenhuma função aqui permite deduzir cooldowns, buffs ou IDs de terceiros sem ler o JAR exato do consumer.

### 16.2. Issue aberta #616 — congelamentos na geração/carregamento de chunks

O upstream tem a [issue **#616 — Server deadlock on chunk loading caused by EquipCurioTrigger in 9.5.1**](https://github.com/TheIllusiveC4/Curios/issues/616), **aberta**, registrada em **16/06/2026**, com labels `type: bug` e `status: triage` e assignee do projeto. O autor do relato usa **Minecraft 1.21.1 / NeoForge 21.1.232 / Curios 9.5.1** e descreve pausas de **15–50 segundos** ao explorar chunks, sem reprodução publicada para o loader `21.1.250` ou para este modpack.

O [source 9.5.1 de `EquipCurioTrigger`](https://github.com/TheIllusiveC4/Curios/blob/6b122c8a7e2b514fd94197459ade11f19a0bfb09/common/src/main/java/top/theillusivec4/curios/common/util/EquipCurioTrigger.java) efetivamente chama `ServerPlayer.getBlockStateOn()` ao construir `LootParams` com `LootContextParams.BLOCK_STATE`, inclusive na variante `trigger(SlotContext, ServerPlayer, ItemStack)`. O [`CuriosEventHandler` 9.5.1](https://github.com/TheIllusiveC4/Curios/blob/6b122c8a7e2b514fd94197459ade11f19a0bfb09/neoforge/src/main/java/top/theillusivec4/curios/common/event/CuriosEventHandler.java) invoca esse trigger ao processar mudança de stack equipado em `ServerPlayer`. **Esse encadeamento é verificável no source.**

**Limite do diagnóstico:** o stack trace anexado pelo relator mostra `ServerChunkCache.getChunk` / `Entity.move` / `ServerGamePacketListenerImpl.handleMovePlayer`, **sem frames de Curios ou `EquipCurioTrigger` naquele recorte**. Assim, a cadeia causal atribuída ao Curios permanece **hipótese do relatório**, e o tempo de pausa não foi reproduzido nem atribuído causalmente ao mod no pack. Uma espera `TIMED_WAITING` observada nesse recorte **não prova deadlock originado por Curios**.

**Gate de regressão para servidor com modpack grande, NÃO executado:** registrar MSPT/TPS, thread dump com timestamp e servidor/chunkload profiling; medir chunk exploration com Curios e consumers presentes; alternar equip/unequip e eventos de advancements em mundo de teste; verificar se frames `CuriosEventHandler`/`EquipCurioTrigger` aparecem durante travamento; comparar com baseline reproduzível e logs de worldgen, sem fazer downgrade em mundo real. Revalidar compat de dependentes antes de atualizar ou substituir Curios. **Issue #616 não é motivo suficiente para remover a 9.5.1 ou culpar o Curios por qualquer travamento sem evidência local.**

### 16.3. Decisão e estado

- **Instalada historicamente:** `9.5.1+1.21.1`, sem divergência de filename/versão conhecida.
- **Última publicada para MC1.21.1/NeoForge:** `9.5.1+1.21.1`, conferida no CurseForge oficial em 13/10/2026.
- **Decisão curatorial:** `Sem decisão`, inalterada; esta edição documenta evidências e riscos, **não** aprova novo upgrade/removal.
- **Estado:** dossiê atualizado documentalmente; verificação física direta, QA dedicado e atribuição causal da issue permanecem **pendentes/não verificados**.

## Auditoria incremental upstream — Curios API 9.6.0+1.21.1 (22/11/2026; **loader incompatível no baseline histórico**)

**Instalação de referência:** #211 `curios-neoforge-9.5.1+1.21.1.jar` / `9.5.1+1.21.1`. O inventário físico histórico do modpack registra NeoForge `21.1.250` (contrato base anterior 21.1.248). A nova [release oficial **9.6.0+1.21.1**, file 9694505](https://www.curseforge.com/minecraft/mc-mods/curios/files/9694505) foi publicada em **19/11/2026**, com changelog datado pelo desenvolvedor **18/11/2026**. O arquivo está marcado Minecraft 1.21.1 / NeoForge, mas sua **exigência mínima de NeoForge é 21.1.265**: não classificar como instalável no loader físico registrado.

**Changelog oficial por versão (9.6.0):**
- **Quick-move na GUI Curios** passa a priorizar slots específicos (por exemplo, ring) em vez do slot genérico `curio`; isso influencia o resultado de shift-click quando há slots compatíveis concorrentes.
- Hooks de **Fortune e Looting** migram para os **eventos oficiais NeoForge API**, alterando a superfície de integração com loot/atributos.
- **Parsing de entidades Curios de datapacks** corrigido; validar resoluções de IDs e comportamento de reload.
- **Tooltip de itens** na tela Curios corrigido.
- **Transient ItemStack desync** que causava desconexões de clientes corrigido.
- Atualização de localização **pt_br**.
- Aumento do **NeoForge mínimo para 21.1.265**, requisito bloqueante para o ambiente histórico `21.1.250`.

**Bloqueio fail-closed de promoção:** sem atualizar NeoForge (operação não autorizada neste lote) e revalidar todo o modpack, **9.6.0 não é upgrade compatível com a combinação exata atualmente registrada**. Confirmar novo snapshot físico e os intervalos de loaders; só então inspecionar metadata/JAR candidato e testar equip/unequip/shift-click, Fortune/Looting, datapacks, caches, morte/relog, migração de mundo, multiplayer e todos os consumers. Relato upstream #616 da baseline 9.5.1 continua hipótese não reproduzida e não prova que 9.6.0 o corrige.

**Resultado:** publicação 9.6.0 auditada documentalmente, **não elegível para promoção sobre o NeoForge histórico do pack**. JAR, versão do dossier, categorias oficiais e certificação preservados.

## Sincronização declarada da versão — 22/11/2026

**Versão antiga:** `9.5.1+1.21.1`. **Versão agora declarada:** `9.6.0+1.21.1`. **Arquivo de distribuição oficial:** `curios-neoforge-9.6.0+1.21.1.jar`.

**Evolução técnica:** 9.6.0 (19/11/2026): shift-click prioriza slots específicos sobre curio genérico; Fortune/Looting migram aos eventos oficiais NeoForge; fixes de datapack entity parsing, tooltip, transient ItemStack desync/disconnect e localização pt_br. A release declara **NeoForge mínimo 21.1.265**, acima do 21.1.250 do snapshot documental antigo. Registrar atualização do loader como pendência separada de lote/ambiente, sem cancelar a atualização documental declarada de Curios.

**Fonte:** https://www.curseforge.com/minecraft/mc-mods/curios/files/9694505. Informações “não instalada” anteriores são históricas, anteriores a esta declaração. Nenhum teste de jogo ou verificação de JAR local foi realizado.
