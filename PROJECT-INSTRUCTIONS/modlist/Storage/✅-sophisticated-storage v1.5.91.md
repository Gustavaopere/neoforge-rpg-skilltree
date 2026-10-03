# Sophisticated Storage

> **Autoridade física atual — 27/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física **#519**: JAR `sophisticatedstorage-1.21.1-1.5.91.2127.jar`, mod id `sophisticatedstorage`, runtime `1.5.91`, SHA-1 `74d5c0a411edfdd521c800fd8813f53c0f9a6f30`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** Sophisticated Storage
- **Arquivo JAR:** `sophisticatedstorage-1.21.1-1.5.91.2127.jar`
- **Versão 1.21.1:** 1.5.91
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Armazenamento, Automação
- **Função:** Stationary modular storage with tiered barrels/chests/shulker boxes, capacity and functional upgrades, filters/settings, sorting/memorized slots, controller interaction and storage tools.
- **Dependências:** Sophisticated Core 1.5.1 está fisicamente presente. Sophisticated Storage Create Integration 0.1.21 e Ars Sophisticated Compatibility 0.3.0 permanecem integrações físicas. A upstream Storage 1.6.0+ exige Sophisticated Core >=1.5.2, portanto a promoção não é compatível com o Core físico atual.
- **Sobreposição:** Local/upgradable stationary storage. No snapshot físico atual, Tom's Storage 2.4.2 é o provider de rede ativo relevante; AE2 e Refined Storage estão ausentes top-level e permanecem apenas comparações/integrations upstream dormentes. Backpacks continua sobreposição portátil, não equivalente a networked crafting.
- **Compatibilidade/Riscos:** Stateful stationary storage. Risks: inventory/upgrades loss/dupe, compression/compacting recipe conflicts, controller routing, packed-drop duplication, filter/memory drift, network/capability sync and Create contraption serialization. Compression and compacting are mutually dangerous when combined in conflicting configurations.
- **Observações:** Runtime físico permanece 1.5.91. Upstream 1.6.0 adiciona Linked Storage por Ender Linker, com inventário/settings/upgrades compartilhados; 1.6.1 corrige perda de item data durante migração de mundos. Somente o storage principal executa upgrades por tick e recebe tier upgrade; linked storages não podem ser packed.
- **Procedência:** modlist física atual confirma Storage 1.5.91.2127 + Core 1.5.1. CurseForge oficial e source `P3pp3rF1y/SophisticatedStorage` branch `1.21.x` revalidados em 03/10/2026 confirmam a linha Storage 1.6.0→1.6.1 e o requisito Core >=1.5.2.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage/files/8762100
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 03/10/2026 — Storage físico permanece 1.5.91. A sequência upstream agora chega a 1.6.1.2147; 1.6.1 corrige perda de item data durante migração de mundos e continua exigindo promoção coordenada do Sophisticated Core.
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
