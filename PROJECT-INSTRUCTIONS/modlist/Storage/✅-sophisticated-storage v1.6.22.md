# Sophisticated Storage — 1.6.22

## Propriedades vigentes — 27/11/2026

- **Mod:** Sophisticated Storage
- **Arquivo JAR instalado (declarado):** `sophisticatedstorage-1.21.1-1.6.22.2244.jar`
- **Versão instalada declarada (Minecraft 1.21.1 / NeoForge):** `1.6.22` — Release.
- **Categoria CurseForge / pasta:** Storage
- **Ordem física histórica:** #519 (preservada).
- **Estado da pesquisa:** atualização documental com fontes oficiais e histórico auditado; metadata interna e runtime não inspecionados.
- **Estado no pack:** instalação declarada pelo usuário; não constitui leitura do JAR local nem aprovação de QA.
- **Fonte:** [arquivo/catálogo oficial CurseForge](https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage/files/all?page=1&pageSize=20&version=1.21.1).
- **Dependências / compatibilidade / riscos:** [Source pinado](https://github.com/P3pp3rF1y/SophisticatedStorage/blob/feb548f2094bc836a37bb20995a3a6e8f07a1982/gradle.properties), [template](https://github.com/P3pp3rF1y/SophisticatedStorage/blob/feb548f2094bc836a37bb20995a3a6e8f07a1982/src/main/templates/META-INF/neoforge.mods.toml) e [build](https://github.com/P3pp3rF1y/SophisticatedStorage/blob/feb548f2094bc836a37bb20995a3a6e8f07a1982/build.gradle): Core >=1.5.35, NeoForge >=21.1.229; JEI >=19.9.0 conforme template, Backpacks 3.26.20 é referência de desenvolvimento, não requisito obrigatório demonstrado. Core aguardando sincronização documental posterior (#518; conferir índice); não impede atualização deste dossier. Storage 1.6.22 supera o mínimo 1.6.3 da integração Create 0.1.23 auditada no lote anterior; pendência documental de Storage satisfeita, sem homologação de runtime. Testar waterlogging/packed double chests, contents/upgrades, uso de wrench, Create contraptions e save.
- **Atualização/Status:** `1.6.20` → `1.6.22`; dependências pendentes serão reconciliadas separadamente.
- **Procedência:** declaração do usuário + publicação oficial; sem inferência de hash ou metadata binária.

## Auditoria incremental e reconciliação

Catálogo NeoForge 1.21.1 lista .20 (18/11) seguida de .22.2244 (25/11); não localizada distribuição separada de .21. O [commit .21](https://github.com/P3pp3rF1y/SophisticatedStorage/commit/628862e0703078020376ad9643e8fa00b04f31a9) eleva mod_version 1.6.20→1.6.21 e corrige waterlogging que apagava baús duplos empacotados e seu conteúdo (ChestBlock). O [commit .22](https://github.com/P3pp3rF1y/SophisticatedStorage/commit/feb548f2094bc836a37bb20995a3a6e8f07a1982) eleva mod_version 1.6.21→1.6.22 e inclui Storage Tool na tag c:tools/wrench (ItemTagProvider e recurso gerado), permitindo uso como chave por integrações que consultam a tag. Não inferir operações concretas de outros mods. Sufixo .2244 identifica build, mod_version 1.6.22 confirmado em source, não metadata binária. Ficha individual CurseForge não recuperada, catálogo oficial confirma basename.

## Histórico documental preservado

> O conteúdo abaixo antecede esta revisão. Menções a versões “atuais”, “físicas”, “não instaladas”, hashes, gates de promoção e dependências se referem ao snapshot respectivo; o cabeçalho vigente prevalece. Não representa nova inspeção binária ou runtime.

> **Versão vigente declarada pelo usuário:** 1.5.91 → **1.6.20**, arquivo oficial `sophisticatedstorage-1.21.1-1.6.20.2239.jar` ([CurseForge](https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage/files/9692522)). O novo JAR local e seu metadata/SHA não foram inspecionados nesta sessão.

> **Deltas da cadeia de publicações:** Histórico audita 1.6.0–1.6.20: linked storage, sync, permissões, tanques empilháveis, dropPacked e correção de perda de itens e upgrades na conversão de baú empacotado em shulker. 1.6.20 é versão-base e .2239 é build do JAR; metadata nova não lida.

> **Dependências pendentes:** Source de Storage 1.6.20 requer Sophisticated Core >=1.5.35. Snapshot documental anterior ainda registra Core 1.5.1: dependência aguardando sincronização documental, NÃO impede esta atualização. Integração Create/Backpacks precisa de QA.

> **Compatibilidade e QA:** Alto risco de item/fluid dupes, perda de upgrades e conversão de saves; linked groups e tanques verticais devem ser testados em mundo copiado.

> **Leitura do histórico:** referências antigas a versão 'física atual', 'instalada' ou 'não instalada' abaixo descrevem o snapshot e a auditoria anterior, não o estado documental declarado agora. A posição física 519 e o JAR antigo `sophisticatedstorage-1.21.1-1.5.91.2127.jar` são mantidos como rastreabilidade.


> **Snapshot físico histórico — 27/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física **#519**: JAR `sophisticatedstorage-1.21.1-1.5.91.2127.jar`, mod id `sophisticatedstorage`, runtime `1.5.91`, SHA-1 `74d5c0a411edfdd521c800fd8813f53c0f9a6f30`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** Sophisticated Storage
- **Arquivo JAR instalado (declarado pelo usuário):** `sophisticatedstorage-1.21.1-1.6.20.2239.jar` — publicado no [CurseForge](https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage/files/9692522); arquivo local não relido.
- **Versão instalada declarada (Minecraft 1.21.1 / NeoForge):** `1.6.20` (antes `1.5.91`; metadata da nova build não extraído).
- **Estado no pack:** `1.6.20` instalado segundo declaração do usuário; documentação sincronizada, sem inspeção local.
- **Estado da pesquisa:** versão-alvo e artefato oficial confirmados; publicações intermediárias documentadas nas seções históricas; binário e QA pendentes.
- **Decisão:** Manter
- **Categoria:** Armazenamento, Automação
- **Função:** Stationary modular storage with tiered barrels/chests/shulker boxes, capacity and functional upgrades, filters/settings, sorting/memorized slots, controller interaction and storage tools.
- **Dependências:** Source de Storage 1.6.20 requer Sophisticated Core >=1.5.35. Snapshot documental anterior ainda registra Core 1.5.1: dependência aguardando sincronização documental, NÃO impede esta atualização. Integração Create/Backpacks precisa de QA.
- **Sobreposição:** Local/upgradable stationary storage. No snapshot físico atual, Tom's Storage 2.4.2 é o provider de rede ativo relevante; AE2 e Refined Storage estão ausentes top-level e permanecem apenas comparações/integrations upstream dormentes. Backpacks continua sobreposição portátil, não equivalente a networked crafting.
- **Compatibilidade/Riscos:** Alto risco de item/fluid dupes, perda de upgrades e conversão de saves; linked groups e tanques verticais devem ser testados em mundo copiado.
- **Observações vigentes:** arquivo publicado `sophisticatedstorage-1.21.1-1.6.20.2239.jar` declarado instalado, sem SHA local; snapshot 1.5.91 apenas histórico.
- **Procedência:** posição histórica #519, declaração de atualização em 22/11/2026 e [publicação oficial](https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage/files/9692522); nenhum arquivo local novo foi lido.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage/files/8762100
- **Atualização/Status:** 22/11/2026 — `1.5.91` → `1.6.20`; Histórico audita 1.6.0–1.6.20: linked storage, sync, permissões, tanques empilháveis, dropPacked e correção de perda de itens e upgrades na conversão de baú empacotado em shulker. 1.6.20 é versão-base e .2239 é build do JAR; metadata nova não lida.
- **Histórico da decisão:** Mantido como armazenamento estacionário do stack Sophisticated. Em 11/09/2026 revalidado na versão física 1.5.91.2127 e documentado no padrão técnico completo.
- **Data da última decisão:** 2026-08-22

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `sophisticatedstorage-1.21.1-1.5.91.2127.jar`, mod id `sophisticatedstorage`, versão `1.5.91`, NeoForge 1.21.1. É o **storage estacionário modular** do ecossistema Sophisticated. A decisão vigente **Manter** é preservada.

## 1. Identidade e papel
- **Mod:** Sophisticated Storage.
- **JAR:** `sophisticatedstorage-1.21.1-1.5.91.2127.jar`.
- **Mod id:** `sophisticatedstorage`.
- **Versão:** `1.5.91`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Required:** Sophisticated Core `1.5.1`.
## 2. Storage types e tiers
O projeto fornece storage blocks como **barrels, chests e shulker boxes** em múltiplos tiers/capacidades.
Tier upgrade deve preservar conteúdo, upgrades e settings. O container continua sendo um único state autoritativo, independentemente do material/tier visual.
## 3. Upgrade framework
Storage blocks usam o framework Sophisticated de upgrades. Capacidade e funcionalidades adicionais são instaladas no próprio storage e compartilham regras/settings com o ecossistema.
Sophisticated Core fornece infraestrutura; Sophisticated Storage continua owner do block/container concreto.
## 4. Filters e memorized slots
O sistema suporta filters/settings e **memorized slots**, permitindo reservar comportamento/identidade de slots mesmo quando a stack muda.
Memory/filter state precisa persistir após save/restart e não pode virar item real por erro de sync.
## 5. Sorting
A linha suporta sorting, inclusive critérios como mod/origem do item em versões documentadas.
Sorting é rearranjo de stacks existentes: não deve alterar contagem, data components ou ownership. Operação interrompida não pode duplicar/perder item.
## 6. Capacity upgrades
Upgrades de capacidade aumentam a densidade do storage. O servidor deve validar limites e stack sizes efetivos.
Downgrade/remoção de upgrade com conteúdo acima da nova capacidade precisa seguir regra segura do mod e nunca descartar silenciosamente itens sem fluxo previsto.
## 7. Compression upgrade
Compression pode manter materiais em formas compactadas/descompactadas conforme recipes suportadas. Recipe reload pode mudar relações válidas; a linha possui histórico de refresh dessas regras.
Como transforma representação do mesmo recurso, qualquer bug de rounding/recipe selection pode causar dupe/loss e é regression surface importante.
## 8. Compacting x Compression
O histórico oficial alerta para conflito entre **compression** e **compacting** em combinações inadequadas, inclusive risco severo em versões da linha. O sistema aplica regras para impedir setups conflitantes.
Não forçar coexistência via config/script sem teste isolado; world hang/loop de conversão é risco de alta severidade.
## 9. Other functional upgrades
O ecossistema oferece upgrades de filtragem, entrada/saída e outras automações. Cada upgrade deve processar um state transition uma vez e respeitar capacidade/filter.
A ficha não enumera como “ativo” todo upgrade existente apenas por estar registrado; uso real depende dos storages do mundo.
## 10. Controller / multi-storage access
Sophisticated Storage possui ferramentas/controladores para facilitar interação com múltiplos storages conforme o sistema do mod.
Deposit/extraction precisa rotear para containers reais sem double-counting. Um controller não deve ser tratado como cópia dos inventories conectados.
## 11. Deposit behavior
Linhas oficiais documentam ajustes no comportamento de depósito via controller. Inserção precisa respeitar memorized slots, filters, capacity e upgrades.
Spam de depósito/múltiplos jogadores não pode criar lost updates ou inserir a mesma stack em dois targets.
## 12. Storage Tool
O **Storage Tool** gerencia propriedades/ações de storages, incluindo estados visuais/lock em fluxos suportados pelo projeto.
A tool deve alterar state server-side do target correto; client highlight não equivale a mudança aplicada.
## 13. Paintbrush / aparência
O ecossistema inclui Paintbrush/tint/material customization em storages suportados. Essas mudanças são principalmente apresentação/metadata e não devem recriar o inventory.
Resource/model reload não pode apagar content state.
## 14. Packed drops / dropPacked
A linha possui configuração relacionada a dropar storage “packed” com conteúdo/state preservado. Esse caminho é crítico de anti-dupe: ao quebrar, conteúdo deve existir ou no packed item ou como drops separados conforme config, nunca em ambos.
Pickup/place do packed storage precisa restaurar exatamente um state.
## 15. Shulker/storage movement
Storages que podem ser movidos como item exigem serialização íntegra de content/upgrades/settings. Interações com outros mods de Carry/contraption/grave podem alterar lifecycle.
Validar sempre o provider que realmente executa a movimentação.
## 16. Create Integration
O pack contém **Sophisticated Storage Create Integration 0.1.21**, que permite storages em contraptions e preserva seus recursos durante movimento.
A bridge é responsável por adaptar contexto móvel; Storage base continua owner dos inventories e settings.
## 17. Ars Sophisticated Compatibility
**Ars Sophisticated Compatibility 0.3.0** está presente e conecta sistemas Ars ao ecossistema Sophisticated.
Qualquer acesso externo deve respeitar capabilities/filters e não bypassar rules do container.
## 18. JEI e recipes
JEI `19.56.0.440` está presente e expõe recipes/upgrades. Recipe viewing não é authority do state; server recipe manager continua decidindo craft/upgrade válido.
Data reload pode mudar recipes de compression/upgrade; caches devem acompanhar.
## 19. Client / server
Servidor decide:
- inventory contents;
- upgrades/settings;
- sorting/memory;
- compression conversions;
- controller routing;
- packing/break drops.
Cliente apresenta GUI/models/indicators. Ghost slots/previews não podem virar item real sem operação server-side.
## 20. Multiplayer
Acesso concorrente a storage/controller deve serializar mutations corretamente. Dois players não podem retirar a mesma stack de snapshots diferentes.
Locks/permissões do próprio sistema ou integrações externas precisam ser respeitados por automation/capabilities.
## 21. Lifecycle
Validar:
- place/open/close;
- tier upgrade;
- add/remove upgrade;
- sort/filter/memorized slot;
- compression recipe reload;
- controller deposit/extract;
- break/packed drop/place;
- chunk unload/reload;
- save/restart;
- Create assembly/disassembly;
- concurrent multiplayer access.
## 22. Sobreposição no pack
No snapshot físico atual, **Tom's Storage 2.4.2** é o provider de rede ativo relevante; AE2 e Refined Storage estão ausentes top-level e permanecem apenas comparações/integrations upstream dormentes. Sophisticated Storage cobre **containers locais modularmente upgradáveis**.
A semelhança “guardar itens” não é suficiente para remoção. Avaliar recipes, progression, network usage e papel de cada sistema.
## 23. Riscos técnicos
1. **Tier/pack dupe:** container original e item packed coexistem.
2. **Serialization loss:** upgrades/settings/content incompletos.
3. **Compression loop/dupe:** recipe conversion incorreta.
4. **Compacting conflict:** combinação incompatível causa loop/hang.
5. **Controller double route:** mesma stack inserida duas vezes.
6. **Memory/filter drift:** slot reservation não persiste.
7. **Concurrent access:** lost update/ghost stack.
8. **Create movement:** capability/reference stale após contraption move.
9. **Recipe reload:** compression cache usa recipe antiga.
## 24. Matriz de testes
- [ ] Dedicated server inicia com Storage 1.5.91 + Core 1.5.1.
- [ ] Tier upgrade preserva inventory/upgrades/settings.
- [ ] Sorting mantém exatamente as mesmas stacks/counts.
- [ ] Memorized slots persistem após relog/restart.
- [ ] Capacity upgrade respeita limites ao instalar/remover.
- [ ] Compression converte recursos sem rounding dupe/loss.
- [ ] Configuração incompatível compression+compacting é rejeitada/segura.
- [ ] Controller deposit/extract respeita filters e quantidade.
- [ ] Packed break/place mantém exatamente uma cópia do content.
- [ ] Chunk unload/reload preserva state.
- [ ] Create contraption preserva content/upgrades/settings.
- [ ] Dois players acessando simultaneamente não duplicam item.
- [ ] Recipe/data reload atualiza compression rules sem cache stale.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 25. Evidências e limites
- Modlist física atual: Storage 1.5.91.2127, Core 1.5.1 e integrations relacionadas.
- CurseForge oficial: storage tiers/types e functional upgrades.
- Documentação/changelog oficial da linha: sorting, compression/compacting constraints, controller/deposit, packed drops e refresh por recipe changes.
- **Limite:** config local e inventories do mundo não foram inspecionados; o dossiê documenta contratos/riscos do runtime, não afirma quais upgrades estão efetivamente instalados em cada storage.


## 26. Atualizações upstream 1.6.0 → 1.6.1 — não instaladas

A autoridade física continua em **Sophisticated Storage 1.5.91.2127** com **Sophisticated Core 1.5.1**.

A 1.6.0 adiciona **Linked Storage**. Barrels, limited barrels, chests e shulker boxes elegíveis podem ser ligados com **Ender Linker** e passam a compartilhar inventory, settings e upgrades. Upgrades orientados por tick executam somente no storage principal; linked storages não podem ser packed; somente o principal pode receber tier upgrade.

O source 1.21.x da 1.6.0 declara Sophisticated Core **>=1.5.2**. Como o pack físico está em Core 1.5.1, a atualização de Storage fica bloqueada até promoção coordenada do Core.

### 1.6.1.2147 — migração de mundo / preservação de item data

- Release NeoForge 1.21.1 publicada em 02/10/2026, CurseForge file ID `9036665`.
- Corrige **storages perdendo item data ao migrar mundos**.
- A correção é material para qualquer mundo persistente: conteúdo com data components/NBT precisa sobreviver à migração sem perda silenciosa.
- O fix não elimina o gate de dependência: a linha 1.6.x continua devendo ser promovida em conjunto com Sophisticated Core compatível.

### Gate de promoção
- [ ] Atualizar Sophisticated Core para >=1.5.2 e revalidar o ecossistema Sophisticated.
- [ ] Migrar cópia de mundo com chests/barrels/shulkers contendo itens com data components/NBT e confirmar preservação integral — regression 1.6.1.
- [ ] Confirmar uma única shared inventory entre storages ligados.
- [ ] Confirmar que upgrades por tick executam somente no storage principal.
- [ ] Confirmar tier upgrade apenas no principal e proibição de packing em linked storage.
- [ ] Validar save/restart, unload/reload e acesso simultâneo.
- [ ] Validar Controller e Create Integration para que storages ligados não sejam tratados como inventários independentes.

Fonte upstream: Sophisticated Storage 1.6.0 e 1.6.1; source oficial 1.21.x, commit de Linked Storage `05d24d4...`. Nenhum teste acima foi executado nesta atualização documental.

## 27. Atualizações upstream 1.6.2.2154 → 1.6.2.2159 — 10/10/2026

**Autoridade física:** `sophisticatedstorage-1.21.1-1.5.91.2127.jar` / 1.5.91, mantendo **Sophisticated Core 1.5.1** como versão instalada conhecida. O dossiê já cobre o salto **1.6.0 → 1.6.1.2147** (linked storage e preservação de dados de itens na migração de mundos). A atualização posterior, ainda na linha NeoForge 1.21.1, tem duas builds da versão semântica 1.6.2:

### 1.6.2.2154 — 05/10/2026

- O upstream **habilitou as funcionalidades de linked storage para containers em movimento e montados**. A mudança aumenta diretamente a interação entre Sophisticated Storage, Create contraptions e estado compartilhado de inventário; não se reduz a atualização de tooltip.
- Os links e a identidade de armazenamento precisam permanecer consistentes durante montagem/desmontagem, movimento, save, chunk unload/reload e troca de dimensão quando suportada.

### 1.6.2.2159 — 06/10/2026

- Publicação posterior de **1.6.2.2159** para Minecraft 1.21.1, com **atualizações de localização espanhola/chinesa**. Não atribuir a este build mecânica inédita sem documentação.
- Este é o **artefato mais recente identificado nessa linha de distribuição**, substituindo a build 2154 como alvo publicado, sem apagar o delta funcional introduzido anteriormente.

### Acoplamento e regressões

**Bloqueio físico:** Core 1.5.1 não satisfaz o mínimo Core **≥1.5.2** exigido pela linha Storage 1.6.x. A atualização precisa ser coordenada com Sophisticated Core (#51), Sophisticated Backpacks (#9), Sophisticated Storage Create Integration (#3) e Sophisticated Backpacks Create Integration (#26). Os dossiês de outros itens ainda não auditados neste ciclo não devem ser presumidos atualizados.

Gate: simular a migração 1.5.91→1.6.2 numa **cópia do mundo** com itens que contêm NBT/data components; linked containers com conteúdos/upgrades/settings; moving/mounted storage durante assembly/disassembly; controller, Ender Linker, breaking/relink, chunks/restart, multiplayer concorrente, tier upgrades e impossibilidade de packing de endpoints linkados. Priorizar detecção de lost/duplicated items e inconsistência de capacidades. Não realizar atualização física isolada.

**Fontes upstream:** https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage/files/9074514 (2154) ; https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage/files/9083992 (2159).

**Estado:** atualização documental; 1.5.91 continua sendo a última versão instalada confirmada.

## Atualização de publicação e gates — 13/10/2026

**Mod:** Sophisticated Storage. **Última release NeoForge 1.21.1 identificada:** `1.6.4.2169`. **Fonte:** https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage/files/all.

Release NeoForge 1.21.1 **`sophisticatedstorage-1.21.1-1.6.4.2169.jar`** em 12/10/2026, posterior à 1.6.2.2159 de 06/10, comparada ao histórico físico 1.5.91. **Versão mudou de linha menor 1.5→1.6:** antes de upgrade, validar serialização, persistent block entities, stack upgrades, sorting, filters, item transfer, Recipe/JEI indexing e remote access. Depende do Core 1.5.11 e bridge Create 0.1.23; coexistência com Sophisticated Backpacks e addon Ars, interações com KubeJS/automation, contraptions, world load/unload, redstone e servidor. Sem changelog granular 1.6.4 específico, não deduzir novos conteúdos.

**Autoridade física:** JAR/metadata e versão efetivamente instalados permanecem os do snapshot histórico `modlist(1).txt`; não há novo dump. Preservar o prefixo ✅ e a versão física no filename do `.md`; não substituir o arquivo com uma versão candidata. **Estado:** reauditoria documental; QA Minecraft (client/server/mundo) não executado nesta ação.

## Release 1.6.5 e contrato de dependências — 13/10/2026

**Autoridade física:** `sophisticatedstorage-1.21.1-1.5.91.2127.jar` / runtime `1.5.91` no snapshot; nenhum novo inventário. **Candidato externo corrigido:** `sophisticatedstorage-1.21.1-1.6.5.2176.jar`, release NeoForge 1.21.1 de 13/10/2026 ([CurseForge file 9154831](https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage/files/9154831)). **Substitui a referência documental a 1.6.4 como candidata**, não o JAR físico.

**Deltas oficiais:** a [1.6.4.2169](https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage/files/9151544) introduziu **grupos linked compartilhados entre Backpacks e Storage** e corrigiu controller deposits que perdiam itens após chunk unload. A **1.6.5.2176** corrige crash quando Sophisticated Backpacks **não está instalado**. Esses são fixes independentes e de relevância para integridade/isolamento de armazenamento; não atribuir outros mecanismos não anunciados.

**Dependências declarativas:** no [source pinado `bba2c29dfa47cacb29c0520d6ccd3736878c24aa`](https://github.com/P3pp3rF1y/SophisticatedStorage/tree/bba2c29dfa47cacb29c0520d6ccd3736878c24aa), `gradle.properties` informa `sc_version=[1.21.1-1.5.9,1.21.2)`. O [template NeoForge](https://github.com/P3pp3rF1y/SophisticatedStorage/blob/bba2c29dfa47cacb29c0520d6ccd3736878c24aa/src/main/templates/META-INF/neoforge.mods.toml) requer **Core**, expandido pelo build para `[1.5.9,)`, e JEI opcional CLIENT. `sb_version=[1.21.1-3.26.11,1.21.2)` é usado como coordenada de dependência no **build** de Storage, mas **não constitui requisito obrigatório de Backpacks no template NeoForge**; o fix 1.6.5 para ausência de Backpacks é consistente com essa distinção.

**QA de mundo PENDENTE:** copiar mundo e capturar contagem/hash lógico de stacks com data components antes/depois da migração 1.5.91→1.6.5; linked Backpacks+Storage e grupos compartilhados; Controller deposit após chunk unload (não perder item); Ender Linker, rebuild/relink, main endpoint-only tick upgrades e restrições de packing/tier; duas sessões concorrentes, chunk reload/restart; contraptions montadas com Storage Create Integration 0.1.23. Testar o cenário sem Backpacks apenas em **perfil descartável**, nunca removendo Backpacks do modpack ativo. Inspecionar artefatos publicados antes de declarar compatibilidade.


## Errata de consumidor Ars — 13/10/2026, após PR #894

**Correção de escopo material:** a entrada física #054 `arssophisticatedcompat-0.3.0.jar` coincide com [Sophisticated Backpacks: Ars Compat 0.3.0 (File ID 8653384)](https://www.curseforge.com/minecraft/mc-mods/sophisticated-backpacks-ars-compat-sophisticated/files/8653384), **não** com [Sophisticated Storage: Ars Compat 0.3.0 (File ID 8655579)](https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage-ars-compat-sophisticated/files/8655579). O segundo publica `arssophisticatedstoragecompat-0.3.0.jar`, não encontrado entre as entradas top-level da última modlist física. A menção histórica a "addon Ars" como integração direta instalada de Storage **não deve ser usada para justificar upgrade ou testes de Storage Source Link/Potion Jar em barrels/chests**. O addon de Backpacks usa a mochila como provider de inventário e é relevante indiretamente aos linked groups **Backpacks↔Storage** introduzidos na linha 1.6.4/1.6.5.

**Gate:** testar a migração do Storage 1.5.91 para candidato 1.6.5 com Backpacks 3.26.12/Core 1.5.11 e **Ars Compat da mochila** 0.3.0, verificando que Source/ticks/upgrades de backpack não são atribuídos a endpoints de Storage em blocos nem processados em duplicidade após linked group, chunk reload, Create contraptions e restart. Não há garantia binária até comparar SHA/manifesto do JAR instalado com File ID 8653384. **Nenhuma alteração física ou QA in-game nesta correção.**

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** Sophisticated Storage
- **Último candidato 1.21.1 / NeoForge localizado:** **1.6.8.2196 (Release NeoForge 1.21.1, 21/10)**
- **Estado:** **CANDIDATO COORDENADO**
- **Gate de promoção:** Não atualizar isoladamente. Exige Core compatível e regressão de linked groups/controllers, anti-dupe, chunk unload, save/restart, Backpacks e Create bridge.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage/files/8762100

## Auditoria integral de releases NeoForge 1.21.1 — 23/10/2026 (1.5.91.2127 → 1.6.9.2199)

**Autoridade física preservada:** posição **#519** da última modlist física, JAR top-level `sophisticatedstorage-1.21.1-1.5.91.2127.jar`, mod ID `sophisticatedstorage`, metadata runtime **1.5.91**; Sophisticated Core físico `sophisticatedcore-1.21.1-1.5.1.2341.jar`, runtime **1.5.1**. **Projeto oficial CurseForge:** *Sophisticated Storage*, ID **619320**, autor **P3pp3rF1y**, categoria temática única **Storage**, coerente com a pasta atual. **Última Release confirmada para exatamente Minecraft 1.21.1 / NeoForge:** **`sophisticatedstorage-1.21.1-1.6.9.2199.jar`**, **23/10/2026**. Retifica a nota de 22/10 que citava a 1.6.8.2196 como última. Fontes: https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage/files/all ; https://github.com/P3pp3rF1y/SophisticatedStorage/tree/1.21.x .

### Sequência de artefatos publicados na plataforma correta

| Release | Data | Delta técnico documentado e evidência |
|---|---|---|
| **1.6.0.2136** | 27/09 | Novo **Linked Storage** via Ender Linker para barris, limited barrels, baús e shulker boxes: inventário/settings/upgrades compartilhados; upgrades por tick aplicados apenas no principal, apenas o principal pode subir de tier e endpoints ligados não podem ser empacotados. Já detalhada na seção 26. |
| **1.6.1.2147** | 02/10 | Corrige **perda de data components/dados de itens durante migração de mundos**, importante para NBT/componentes em inventários existentes (CurseForge **9036665**). |
| **1.6.2.2154** | 05/10 | Permite funcionalidades de linked storage em containers **montados ou móveis**; superfície de Create contraptions e lifecycle de chunks (CurseForge **9074514**). |
| **1.6.2.2159** | 06/10 | Traduções **espanhol/chinês**; não associar feature inédita sem prova adicional (CurseForge **9083992**). |
| **1.6.4.2169** | 12/10 | **Grupos linked compartilhados entre Sophisticated Backpacks e Storage**, e correção de **Controller deposit após chunk unload** (CurseForge **9151544**). |
| **1.6.5.2176** | 13/10 | Corrige **crash quando Sophisticated Backpacks opcional está ausente**; isso não torna seguro desinstalar Backpacks do pack atual (CurseForge **9154831**). |
| **1.6.6.2181** | 13/10 | Corrige Controller deposit com **mod content filters** que podia perder itens. Changelog do JAR exato e código verificam o guard `!stack.isEmpty()` antes de verificar filtros/elegibilidade; adicionados testes de depósitos com slots vazios. **Core mínimo no código da linha passa a 1.5.12**: de `sc_version=[1.21.1-1.5.9,1.21.2)` para `[1.21.1-1.5.12,1.21.2)` (File ID **9166168**; commit `c9e1fa6c31e2`). |
| **1.6.7.2184** | 15/10 | Corrige **faces de barris desaparecendo após alterar display slots**. O código substitui hash reduzido pela chave de cache `BarrelBakedModelCacheKey` que considera estado de renderização, materiais, itens exibidos e faces; adiciona testes de chaves. Changelog exato File ID **9201424**; commit `3f6feef475c4`. |
| **1.6.8.2196** | 21/10 | Corrige **crash ao girar displays de barris usando Sodium**, substituindo mapa de rotações não thread-safe por `ConcurrentHashMap`; o source altera explicitamente `mod_version=1.6.7` → `1.6.8`. Commit `7d01973c3cb1`. |
| **1.6.9.2199** | 23/10 | Corrige **perda de itens ao elevar o tier de baús duplos**, causada por expor/registrar inventário adicional perante Controllers. O source altera explicitamente `mod_version=1.6.8` → `1.6.9`: normaliza o registro das duas metades **após** concluir o upgrade, invalida capabilities e só então adiciona o storage ao Controller. Commit completo **`349ae8f1384db08eef2848bd581d12605706ff9c`**. |

A sequência acima identifica **todos os dez artefatos posteriores presentes na listagem oficial** deste alvo, inclusive as duas builds 1.6.2. Não há publicação 1.6.3 para esse filtro; **não criar entrada fictícia**. Também não confundir 1.6.9.2199 NeoForge 1.21.1 com versões `1.5.x` publicadas em 23/10 para Minecraft 26.x ou Forge 1.20.1.

**Fontes primárias específicas das últimas quatro releases:** https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage/files/9166168 ; https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage/files/9201424 ; https://github.com/P3pp3rF1y/SophisticatedStorage/commit/7d01973c3cb1 ; https://github.com/P3pp3rF1y/SophisticatedStorage/commit/349ae8f1384db08eef2848bd581d12605706ff9c . A correspondência da mudança a **1.6.8/1.6.9** possui evidência reforçada pelo `gradle.properties` alterado **no mesmo commit**. O registro documental não equivale a inspeção de bytecode/manifest dos JARs já compilados.

### Dependência, impacto real e veto a upgrade isolado

- **Bloqueio físico confirmado:** Sophisticated Core **1.5.1** é inferior ao mínimo **1.5.12** que o source 1.21.x da Storage 1.6.9 declara em `sc_version=[1.21.1-1.5.12,1.21.2)`. A versão candidata de Core **1.5.17.2426** continua com sua própria pendência documental (#12); **não atualizar Storage antes de coordenar Core/Backpacks/integrations**.
- **Regressões de integridade:** as correções 1.6.1, 1.6.4, 1.6.6 e **1.6.9** afetam perda de dados ou de itens; migrar mundo existente sem backup pode produzir perda difícil de reverter. Não utilizar a build intermediária 1.6.8 como baseline final se o problema dos baús duplos for aplicável.
- **Integrações físicas:** Sophisticated Backpacks, Storage Create Integration, Backpacks Create Integration, Sophisticated Storage in Motion (quando presentes no snapshot), Sophisticated Core e os controllers compartilham efeitos sobre inventários; é necessário verificar os contratos específicos e seus manifests, inclusive a exigência de NeoForge que o source 1.21.x declara.
- **Render e compat cliente:** duas releases consecutivas (**1.6.7/1.6.8**) corrigem cachê/rotação de quads de barris. Testar Sodium e shader/resource reload sem atribuir à biblioteca influência lógica sobre o inventário.

### Matriz de regressão recomendada — **não executada**

- [ ] Backup frio de mundo e `serverconfig` antes da migração 1.5.91 → 1.6.9, registrando contagem, conteúdo e data components de itens sensíveis.
- [ ] Testar **tier upgrade dos dois lados de baú duplo vanilla** conectado a Controller, com inventários preenchidos, filtro, slots vazios e operação automática; garantir somente um inventário lógico, sem perda/duplicação antes e depois de restart.
- [ ] Testar **Controller deposit** com mod content filter, itens de múltiplos mods, slots vazios e passagem de chunk unload/reload; conferir conservação de itens.
- [ ] Testar Linked Storage entre barris, baús, limited barrels, shulkers e backpacks: Ender Linker, grupos compartilhados, limitação de packing e upgrades por tick apenas no principal.
- [ ] Testar Storage montado/móvel e Create contraption com upgrades, sorting, memória de slots, chunks e save/restart.
- [ ] Testar material/color/display de barris, troca rápida de display slots, rotação de face com/sem Sodium, F3+T, GPU/CPU e GUI.
- [ ] Testar perfil **descartável** sem Backpacks para reproduzir o caso de 1.6.5; não remover um consumer real do modpack ativo.
- [ ] Validar matrizes de dependências e client/dedicated boot com as versões reais das integrações, dois jogadores simultâneos e inventories concorrentes.

**Conclusão documental:** toda a sequência de **releases e changelogs específicos** de **1.5.91.2127 até 1.6.9.2199** foi identificada, confrontada com publicações CurseForge e complementada por commits oficiais que alteram os números de versão. **A pesquisa upstream deste intervalo pode ser retirada da fila (#78)**; a **promoção/QA do JAR 1.6.9 continua BLOQUEADA** pela necessidade de atualizar Sophisticated Core e testar o modpack. Nenhum JAR físico, arquivo de configuração, código, asset, dossier de outro mod ou caminho foi alterado por esta revisão.

## Auditoria incremental completa — 21/11/2026 — NeoForge 1.21.1, 1.6.10.2206 → 1.6.20.2239

**Autoridade física inalterada:** posição #519 do `modlist(1).txt`, JAR `sophisticatedstorage-1.21.1-1.5.91.2127.jar` e versão metadata `1.5.91`. A auditoria anterior cobriu todas as publicações de 1.6.0 até 1.6.9.2199 (23/10). A nova consulta ao [catálogo oficial CurseForge 619320 filtrado para Minecraft 1.21.1](https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage/files/all?page=1&pageSize=20&version=1.21.1) registra **nove novas releases NeoForge 1.21.1**, em ordem cronológica: **1.6.10, 1.6.11, 1.6.13, 1.6.15, 1.6.16, 1.6.17, 1.6.18, 1.6.19 e 1.6.20**. Não há distribuições `1.6.12` nem `1.6.14` no filtro oficial consultado; não criar deltas fictícios a partir dos números ausentes. A categoria temática oficial é exclusivamente **Storage**, igual à pasta atual. As publicações para Minecraft 26.x e Forge 1.20.1 não integram este intervalo.

| Versão / data (2026) | Delta individual verificado e impacto |
|---|---|
| [1.6.10.2206 — 27/10](https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage/files/9341485) | **Persistência de sorting em linked storage:** corrige a perda do modo de ordenação selecionado; verificar relog, chunk unload e troca de dispositivo vinculado. |
| [1.6.11.2211 — 30/10](https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage/files/9373854) | **Segurança do inventário:** corrige requisições forjadas que contornavam restrições de acesso ao inventário. Revalidar ACL/permissões e requests alterados no multiplayer; o changelog não quantifica o escopo de exploração. |
| [1.6.13.2216 — 31/10](https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage/files/9385611) | Corrige transferência de ingredientes da **crafting table via EMI** para upgrade de tiers, receitas de tintura e conversões sintéticas, preservando consulta de receita selecionada; corrige alerta falso de excesso de itens ao quebrar storages vinculados. EMI é integração opcional, não declarar presença física só pelo fix. |
| [1.6.15.2219 — 02/11](https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage/files/9409326) | Corrige barris de compressão que recusavam intermitentemente itens de overflow e Ender Linker que não funcionava em storage vinculado remotamente a Controller. Impacta transporte de itens e consistência do inventário lógico. |
| [1.6.16.2222 — 04/11](https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage/files/9431872) | Corrige perda de configurações de upgrades ao migrar mundos **de Minecraft 1.20.1**; trata-se de cenário de migração entre versões de jogo, não presumir que o mesmo bug afete upgrade 1.21.1→1.21.1. |
| [1.6.17.2224 — 11/11](https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage/files/9555026) | Introduz listas de **mods permitidos e bloqueados** para variantes genéricas de storage de madeira. A seleção permite controlar quais madeiras/mods entram no registro de variantes; confirmar sintaxe/defaults da config específica antes de editar arquivos. |
| [1.6.18.2229 — 12/11](https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage/files/9578283) | **Novo sistema: tanques de armazenamento de fluidos empilháveis**, seis tiers configuráveis, colunas verticais de até **cinco blocos**, upgrade entre tiers; bloco isolado preserva fluido ao ser coletado, enquanto upgrades podem exigir drops próprios ou packing tape. Packing tape também empacota a coluna inteira; item tanque oferece três modos de **colocar, coletar ou dispensar fluido**. Upgrades de pump, magnet, void e stack funcionam nos tanques; integram Controllers e linked storage e podem ser bloqueados (*locked*) para impedir entrada de novo fluido quando vazios. É mudança de gameplay/logística significativa e requer testes completos de conservação, fluid capabilities, stacks verticais, upgrades, filters, persistência, segurança e competição com outros mods de transporte de fluidos. |
| [1.6.19.2234 — 16/11](https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage/files/9651620) | **`dropPacked` passa a empacotar tanques automaticamente** quando habilitado; [commit upstream `4612b512`](https://github.com/P3pp3rF1y/SophisticatedStorage/commit/4612b51277f96bdc3bedead6265df287aa6ebff5) altera o código e `mod_version=1.6.18 → 1.6.19`, incluindo casos de coluna, container de fluido e upgrades. Validar configuração e drops em survival/creative para evitar perda ou duplicação de inventário. |
| **1.6.20.2239 — 18/11** ([catálogo CurseForge](https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage/files/all?page=1&pageSize=20&version=1.21.1), [source exato `9cb877f`](https://github.com/P3pp3rF1y/SophisticatedStorage/commit/9cb877f4145f07c62d08121b5b1558ea18ce1e71)) | **Fix de perda de itens/estado na conversão de baús empacotados em shulker boxes.** O changelog do commit oficial descreve perda de *contents*, upgrades e tiers. O mesmo commit eleva `mod_version=1.6.19 → 1.6.20`, substitui a entrada genérica `c:chests` da receita por **`sophisticatedstorage:non_sophisticated_chest`** e atualiza `ShulkerBoxFromChestRecipe`, evitando caminhos de conversão indevidos. Há testes upstream para preservar metadados; eles não substituem execução no pack. Conferir itens, componentes, tiers, upgrades e receitas da bancada JEI/EMI, inclusive material existente de baús anteriores. |

### Autoridade técnica e compatibilidade da atualização

O [source `gradle.properties` da branch 1.21.x, na revisão 1.6.20](https://github.com/P3pp3rF1y/SophisticatedStorage/blob/1.21.x/gradle.properties) declara `minecraft_version_range=[1.21.1,1.21.2)`, `neo_version_range=[21.1.229,)`, `mod_version=1.6.20` e **`sc_version=[1.21.1-1.5.35,1.21.2)`**. Esta exigência mínima de Sophisticated Core **1.5.35** supera a Core **1.5.1** instalada no snapshot do projeto. Portanto **não é seguro promover apenas o JAR Storage** sem planejar Core e compatibilidades de Backpacks, integração Create, controle de inventários e consumidores. Não inventar compatibilidade apenas porque existe build NeoForge 1.21.1.

Gates prioritários: (1) backup frio de mundo e inventários; (2) conversão packed chest→shulker com tiers, upgrades e contents, e consulta de receitas no JEI/EMI; (3) inventory access restrito e rejeição de request malicioso; (4) tanks 1–5 blocos, seis tiers, tank item em três modos, reordenação/conexão e `dropPacked` com fluidos/linked groups; (5) Controller deposit e linked storage após chunk unload, com allow/block lists por wood mod; (6) Create contraptions, sophisticatedstoragecreateintegration 0.1.21, Sophisticated Backpacks e Core com versões coordenadas; (7) multiplayer, restarts, reloads e contagem exata de recursos. **Nenhum teste de runtime foi executado nem binário instalado nesta auditoria documental.**

**Resultado documental:** publicações **1.6.0 → 1.6.20.2239** da linha NeoForge 1.21.1 analisadas individualmente com changelogs CurseForge e, para .19/.20, correspondência direta dos commits oficiais às versões declaradas no source. **Auditoria incremental documental encerrada até 1.6.20**, sem promoção física; esta última permanece bloqueada por dependências/QA. A categoria Storage e o nome `✅-sophisticated-storage v1.5.91.md` continuam representando a instalação documentada.

