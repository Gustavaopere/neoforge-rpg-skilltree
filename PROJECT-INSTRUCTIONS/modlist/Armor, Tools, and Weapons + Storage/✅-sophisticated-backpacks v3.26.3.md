# Sophisticated Backpacks

> **Autoridade física atual — 27/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física **#516**: JAR `sophisticatedbackpacks-1.21.1-3.26.3.2158.jar`, mod id `sophisticatedbackpacks`, runtime `3.26.3`, SHA-1 `c9cef1451d73c5afef78a637c593634f55e757fc`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** Sophisticated Backpacks
- **Arquivo JAR:** `sophisticatedbackpacks-1.21.1-3.26.3.2158.jar`
- **Versão 1.21.1:** 3.26.3
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Armazenamento, Automação
- **Função:** Storage portátil e colocável modular, com tiers/capacidade e upgrades funcionais de coleta, filtragem, alimentação, crafting/processamento, fluidos/energia e automação, além de settings persistentes.
- **Dependências:** Sophisticated Core 1.5.1 é required e está presente. Integrações físicas: Sophisticated JEI Index 1.2.3, Sophisticated Thirst Upgrade 0.1.8, Sophisticated Backpacks Create Integration 0.2.0 e Create 6.0.10.
- **Sobreposição:** É o storage portátil modular do stack Sophisticated. Cruza em conveniência com outros backpacks e com redes estacionárias, mas não equivale a Tom's/Sophisticated Storage nem aos addons Create.
- **Compatibilidade/Riscos:** Core de storage portátil stateful. Riscos: inventory/upgrades dupe ou loss em place/pickup/death, nested storage recursion, filter/settings drift, automation double-processing, capability sync e Create contraption/linked-storage state. A 3.26.3 corrige stack overflow quando Create Packagers acessam Inception backpacks; linked storage introduzido na linha 3.26.2 continua regression gate.
- **Observações:** JAR físico `sophisticatedbackpacks-1.21.1-3.26.3.2158.jar`, runtime 3.26.3. O delta oficial da build atual corrige stack overflow quando Create Packagers acessam Inception backpacks.
- **Procedência:** modlist(1).txt física atual de 27/09/2026 + SHA-1 físico + CurseForge File ID 8845926 (runtime 3.26.3.2158) + releases oficiais 3.26.4.2162, 3.26.5.2171, 3.26.6.2174 e 3.26.7.2182 + source oficial `P3pp3rF1y/SophisticatedBackpacks` para o delta 3.26.4 + integrações Sophisticated atuais do pack.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/sophisticated-backpacks/files/8845926
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 03/10/2026 — autoridade física permanece Sophisticated Backpacks 3.26.3.2158. A sequência posterior foi revalidada até 3.26.7.2182; a nova 3.26.7 corrige perda de item data durante migração de mundos e foi incorporada abaixo sem alterar a versão instalada.
- **Histórico da decisão:** Mantido como núcleo de armazenamento portátil. Confirmado carregado em 22/08/2026 na versão 3.25.78. A revisão separou o mod-base de addons que apenas facilitam upgrades ou fornecem kits gratuitos.
- **Data da última decisão:** 2026-08-22

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `sophisticatedbackpacks-1.21.1-3.26.3.2158.jar`, mod id `sophisticatedbackpacks`, versão `3.26.3`, NeoForge 1.21.1. É o **storage portátil modular** do stack Sophisticated e a decisão vigente **Manter** é preservada.

## 1. Identidade e papel
- **Mod:** Sophisticated Backpacks.
- **JAR:** `sophisticatedbackpacks-1.21.1-3.26.3.2158.jar`.
- **Mod id:** `sophisticatedbackpacks`.
- **Versão:** `3.26.3`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Required:** Sophisticated Core `1.5.1`.
## 2. Storage portátil e colocável
Backpacks mantêm inventário próprio quando carregados pelo player e podem também operar em formas/contexts colocáveis suportados pelo mod.
A transição item↔placed storage precisa conservar exatamente uma cópia do inventário, upgrades e settings. Esse é um dos principais invariants de anti-dupe do mod.
## 3. Tiers e capacidade
O projeto oferece tiers/capacidade crescentes. Upgrade de tier deve preservar conteúdo e configuração sem recriar o backpack como item vazio nem duplicar stacks.
Slots adicionais não mudam ownership: o backpack continua sendo um único container persistente.
## 4. Upgrade framework
Sophisticated Backpacks usa o framework de upgrades compartilhado pelo Sophisticated Core. Upgrades podem alterar capacidade, coleta, filtragem, uso de itens, crafting/processamento e outros comportamentos.
A ficha trata upgrade state como parte do backpack; não como mod separado salvo quando existe addon físico específico.
## 5. Pickup e magnet-style automation
Upgrades de coleta podem mover drops/items para o backpack automaticamente. Essas ações devem respeitar filters, capacidade e stack rules e liquidar cada entity/item exatamente uma vez.
Pickup concorrente entre player inventory, backpack e outros magnets do pack é regression surface para dupe/loss.
## 6. Feeding e consumables
O ecossistema inclui automação de alimentação/uso de consumíveis. O consumo funcional continua pertencendo ao item/provider correspondente; backpack decide apenas seleção/storage/trigger de seu upgrade.
Sophisticated Thirst Upgrade 0.1.8 amplia esse padrão para hidratação no pack atual.
## 7. Crafting e processing upgrades
Backpacks podem expor workflows de crafting/processamento por upgrades. Inputs/outputs devem ser transacionados sem permitir extração concorrente durante commit.
Recipe reload e item tags podem alterar recipes válidos; caches precisam acompanhar o data state atual.
## 8. Fluid e energy storage
O projeto inclui upgrades capazes de lidar com fluidos/energia em sua arquitetura. Esses recursos devem usar capabilities/contracts dos providers e preservar quantidades após save/restart.
Não presumir interoperabilidade perfeita com qualquer mod de energia/fluidos apenas porque a capability existe; cada integration path precisa de teste.
## 9. Filters e settings
Upgrades possuem settings como filters/modes. Eles são state persistente do backpack/upgrade e precisam sobreviver a:
- relog;
- save/restart;
- tier upgrade;
- placement/pickup;
- transferência legítima do item.
Config visual do cliente não pode substituir filter autoritativo do servidor.
## 10. Inventory persistence
Conteúdo do backpack é state crítico. Validar serialização de:
- item stacks/data components;
- upgrades;
- filter/settings;
- fluid/energy state quando aplicável;
- custom names/colors/metadata quando presentes.
State parcial não pode ser reconstruído a partir apenas do renderer/client cache.
## 11. Nested storage e recursion
Backpacks/storage dentro de outros containers podem criar riscos de nesting/recursion dependendo das regras da build/config. O mod deve aplicar suas próprias restrições e nunca serializar referência cíclica que cause duplication, stack overflow ou save bloat.
A ficha não inventa uma política específica não publicada; testa a política real da build/config.
## 12. Death/drop lifecycle
Morte do player, keepInventory, grave/corpse mods e item drops são boundaries importantes. O backpack deve existir em exatamente um local após a transição.
O conteúdo não pode permanecer simultaneamente em capability do player e no item drop/grave.
## 13. Equip/Curios/inventory boundary
Dependendo do setup, backpack pode ser acessado/equipado por slots/integrações suportadas. Equip/unequip deve atualizar capabilities e hotkeys sem deixar inventory duplicado ou referência stale.
Outros backpacks/equipment mods não ganham ownership do conteúdo apenas por renderizar/equipar o item.
## 14. Sophisticated JEI Index
O pack contém **Sophisticated JEI Index 1.2.3**, que pode usar ingredientes de backpacks equipados em recipe transfer.
Backpacks continua authority do inventory; JEI Index precisa consultar/extrair por APIs autorizadas. Transfer não pode bypassar lock/filter/restrições relevantes.
## 15. Sophisticated Thirst Upgrade
O pack contém **Sophisticated Thirst Upgrade 0.1.8**. Ele adiciona um upgrade funcional ao backpack para auto-hidratação.
Remover/trocar backpack ou upgrade precisa invalidar imediatamente qualquer pending auto-use; o core do backpack não deve consumir item por conta própria sem upgrade ativo.
## 16. Create Integration
**Sophisticated Backpacks Create Integration 0.2.0** está presente e adapta funcionalidades do backpack a moving contraptions Create.
Assembly/disassembly transforma contexto/posição do storage; conteúdo e upgrades devem permanecer únicos e consistentes durante movimento.
## 17. Delta lineage 3.26.2 → 3.26.3
O changelog da build física `3.26.2.2141` registra **linked storage adicionado à integração Create de Sophisticated Backpacks**.
Esse é um regression gate específico da versão. O restante do catálogo de backpack/upgrades é arquitetura da linha e não deve ser atribuído como novidade exclusiva da 3.26.2.
### Delta exato 3.26.3.2158 — runtime atual
Em **09/09/2026**, o upstream publicou `sophisticatedbackpacks-1.21.1-3.26.3.2158.jar` para NeoForge 1.21.1. O changelog oficial informa um fix específico: **stack overflow quando Create Packagers acessam Inception backpacks**.
A modlist física atual de 27/09/2026 contém `3.26.3.2158`; portanto esse fix faz parte do runtime atual. Validar especialmente Inception backpacks acessadas por Create Packagers, além de place/pickup, linked storage, automações e integrações Create já cobertas pela matriz.
## 18. Linked storage boundary
Linked storage implica que uma integração pode referenciar storage de forma lógica além da posição estática original. Identity/link target precisa ser estável e server-authoritative.
Assembly, movement, unlink, break, restart e chunk reload não podem criar duas referências funcionais ao mesmo inventário com commits independentes.
## 19. Client / server authority
Servidor decide:
- inventário e stack counts;
- upgrade operations;
- filtering/automation;
- fluid/energy quantities;
- place/pickup transitions;
- linked-storage identity.
Cliente apresenta GUI/render/input. Slot fantasma/preview não equivale a item real.
## 20. Multiplayer
Dois players acessando o mesmo placed/linked storage precisam de locking/synchronization suficientes para evitar lost update/dupe.
Backpack pessoal não pode ser exposto a outro player sem mecanismo de acesso autorizado do próprio mod/provider.
## 21. Integrações e sobreposição no pack
- **Sophisticated Core 1.5.1:** infraestrutura obrigatória.
- **Sophisticated JEI Index 1.2.3:** recipe transfer/index.
- **Sophisticated Thirst Upgrade 0.1.8:** auto-hidratação.
- **Create Integration 0.2.0:** moving contraptions/linked storage.
- **Sophisticated Storage 1.5.91:** storage estacionário irmão, não substituto direto.
- **Tom's Storage e outras redes:** sobreposição de conveniência, não equivalência de portable inventory.
## 22. Lifecycle
Validar:
- craft/tier upgrade;
- equip/unequip;
- open/close GUI;
- place/pickup;
- add/remove/reconfigure upgrade;
- fill/empty fluid/energy upgrades;
- death/drop/grave flow;
- chunk unload/reload de placed backpack;
- Create assembly/disassembly;
- save/restart;
- multiplayer simultaneous access.
## 23. Riscos técnicos
1. **Place/pickup dupe:** item e block conservam o mesmo conteúdo.
2. **Inventory loss:** serialization incompleta.
3. **Upgrade double-processing:** mesma entity/item é coletada/processada duas vezes.
4. **Filter drift:** settings não persistem/sincronizam.
5. **Nested recursion:** storage dentro de storage cria ciclo/problem state.
6. **Death integration:** grave/drop duplica ou perde backpack.
7. **Capability stale:** equip/contraption continua apontando para inventory antigo.
8. **Linked-storage split-brain:** duas referências divergem.
9. **Concurrent access:** dois players sobrescrevem state.
10. **Core/API drift:** Sophisticated Core incompatível com build consumer.
## 24. Matriz de testes
- [ ] Dedicated server inicia com Backpacks 3.26.3 + Core 1.5.1.
- [ ] Backpack tier upgrade preserva conteúdo/upgrades/settings.
- [ ] Place→pickup preserva exatamente uma cópia do inventário.
- [ ] Pickup/magnet automation não duplica entities/stacks.
- [ ] Feeding/crafting/process upgrades liquidam inputs/outputs uma vez.
- [ ] Filters persistem após relog/restart.
- [ ] Fluid/energy upgrade, se usado, preserva quantidade.
- [ ] Death/grave/drop mantém exatamente um backpack com conteúdo íntegro.
- [ ] JEI Index transfere ingredientes sem dupe/loss.
- [ ] Thirst Upgrade consome item corretamente do backpack.
- [ ] Create assembly/disassembly preserva content/upgrades.
- [ ] Linked storage funciona e mantém identity após movimento/restart — regression 3.26.2.
- [ ] Migrar uma cópia de mundo com backpacks contendo itens com data components/NBT e confirmar preservação integral — regression 3.26.7.
- [ ] Dois players acessando storage compartilhado não causam lost update.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 25. Evidências e limites
- Modlist física atual de 27/09/2026: Backpacks 3.26.3.2158, Core 1.5.1 e addons Sophisticated relacionados.
- CurseForge File ID 8845926: build física 3.26.3.2158 e fix exato de stack overflow quando Create Packagers acessam Inception backpacks; linked-storage integration permanece lineage documentada da 3.26.2.
- Catálogo do pack: JEI Index, Thirst Upgrade e Create Integration presentes.
- **Limite:** configs locais e cada upgrade individual não foram testados; comportamento fino deve seguir a build/config efetivamente carregada.


## 26. Atualizações upstream 3.26.4 → 3.26.7 — não instaladas
A autoridade física continua em **Sophisticated Backpacks 3.26.3.2158** (`sophisticatedbackpacks-1.21.1-3.26.3.2158.jar`). As três releases públicas seguintes para NeoForge 1.21.1 foram verificadas em ordem e não alteram o runtime instalado enquanto o JAR físico não for substituído.

### 3.26.4.2162 — linked backpacks em controller multiblock
O source oficial corrige **duplicate reporting of linked backpack contents in controller multiblock**.

Impacto: o mesmo inventário lógico não deve ser contabilizado/exposto duas vezes quando um linked backpack participa de um controller multiblock. Embora o bug seja de reporting, ele é relevante para integrações que consultam disponibilidade agregada porque double-counting pode induzir decisões incorretas de extração, crafting ou automação.

Gate de promoção:
- [ ] linked backpack conectado ao controller multiblock aparece exatamente uma vez no conteúdo agregado;
- [ ] link/unlink e place/pickup não deixam referência duplicada;
- [ ] chunk unload/reload e restart preservam a identity do link sem double reporting;
- [ ] dois players consultando o mesmo controller recebem o mesmo estado autoritativo.

Evidência upstream: commit oficial `8005657e4ce1caa05ba2feea123d8d036ed1790b` no branch 1.21.x de `P3pp3rF1y/SophisticatedBackpacks`.

### 3.26.5.2171 — persistência após chunk reload
A release corrige **placed backpacks perdendo seus dados após chunk reload**.

Este é um delta de alta relevância para o pack porque afeta diretamente o invariant de persistência do storage colocado. Conteúdo, upgrades, settings, filtros e demais dados serializados precisam sobreviver ao ciclo unload→reload sem reset, perda ou reconstrução parcial.

Gate de promoção:
- [ ] backpack colocado com itens, upgrades e settings preserva todos os dados após unload/reload do chunk;
- [ ] restart do servidor após o unload não produz backpack vazio ou state parcial;
- [ ] place→chunk unload→reload→pickup preserva exatamente uma cópia do inventário;
- [ ] linked storage e Create Integration continuam apontando para a identity correta após reload;
- [ ] acesso multiplayer após reload não reintroduz state stale ou lost update.

Fonte upstream: CurseForge File ID **8992926**, Sophisticated Backpacks 3.26.5.2171 para NeoForge 1.21.1.

### 3.26.6.2174 — render após chunk load
A release corrige **placed backpacks aparecendo brancas após chunk loading**.

O delta é client/rendering, mas cruza metadata visual persistente: cor/modelo/material do backpack deve ser reconstituído corretamente após o chunk voltar ao cliente, sem alterar o inventário autoritativo no servidor.

Gate de promoção:
- [ ] backpack colocado mantém cor/modelo correto após chunk load/reload;
- [ ] relog e mudança de distância de renderização não produzem modelo branco;
- [ ] multiplayer mostra a mesma aparência aos observadores após o chunk ser recarregado;
- [ ] o fix visual não altera conteúdo, upgrades ou settings persistidos.

Fonte upstream: CurseForge File ID **9008234**, Sophisticated Backpacks 3.26.6.2174 para NeoForge 1.21.1.

### 3.26.7.2182 — migração de mundo / preservação de item data

- Release NeoForge 1.21.1 publicada em 02/10/2026, CurseForge file ID `9036628`.
- Corrige **backpacks perdendo item data ao migrar mundos**.
- O delta é de alta relevância para mundos persistentes: itens com data components/NBT dentro do backpack precisam sobreviver à migração sem serem normalizados ou recriados de forma incompleta.
- A versão física do pack continua 3.26.3.2158; esta correção não é atribuída ao runtime instalado.

### Gate consolidado de promoção 3.26.3 → 3.26.7
Antes de substituir o runtime físico, validar conjuntamente:
1. fix já instalado da 3.26.3 para Create Packager + Inception backpack;
2. linked contents reportados uma única vez em controller multiblock;
3. placed backpack preservando conteúdo/upgrades/settings após chunk reload e restart;
4. aparência correta após chunk load;
5. place/pickup sem dupe/loss;
6. linked storage, controller, Create assembly/disassembly e multiplayer sem references stale;
7. dedicated server boot com Sophisticated Core e addons Sophisticated atuais do pack.

Nenhum desses testes upstream foi marcado como executado nesta atualização documental.

## 27. Atualizações upstream 3.26.8 → 3.26.9 — 10/10/2026

**Autoridade física:** `sophisticatedbackpacks-1.21.1-3.26.3.2158.jar` / 3.26.3. O histórico já presente no dossiê cobre **3.26.4.2162 → 3.26.5.2171 → 3.26.6.2174 → 3.26.7.2182**, incluindo a correção de perda de dados ao migrar mundos. Nesta revisão foram acrescentadas todas as releases **posteriores** da linha NeoForge **1.21.1**, até **3.26.9.2195**.

### 3.26.8.2189 — 05/10/2026

**Correção de performance de linked backpack menus:** reduz tráfego de sincronização de menu. Implica menos pacotes/redeshoots sem sacrificar integridade: o inventário client-side deve convergir com a visão server-authoritative sob leitura/edição concorrente e com muitos slots/players. CurseForge file **9074419**.

### 3.26.9.2195 — 06/10/2026

**Correção de sincronização de slots dos backpacks vinculados ao alterar tank upgrades.** Inclui atualização da tradução russa. A correção é funcional, pois altera coerência do inventory state e da UI ao instalar, trocar ou remover upgrade de tank em storage ligado. CurseForge file **9083940**.

**Cadeia completa desde 3.26.3:** 3.26.4 (estado de linked controller e sincronização) → 3.26.5 (correções registradas na seção anterior) → 3.26.6 (modelo/cor pós-chunk load) → 3.26.7 (migração de item data) → 3.26.8 (menor tráfego de menu) → 3.26.9 (slot sync ao modificar tank upgrade). Descrições detalhadas de 3.26.4–3.26.7 permanecem na seção 26; não foram substituídas pelo resumo.

**Gates integrados prioritários:** Core ≥ versão exigida pela 3.26.9 (reconfirmar metadata antes do upgrade); linked storage, inventory persistence após world migration; tank upgrades com múltiplos players; menu de muitos slots sob latência; Create Packaging/Inception sem recursão; place/pickup, chunk unload/reload, world save/restart; integração com Sophisticated Storage/Create. Não afirmar segurança apenas por CI verde do repositório, pois CI não substitui o pack físico.

**Fontes:** https://www.curseforge.com/minecraft/mc-mods/sophisticated-backpacks/files/9074419 ; https://www.curseforge.com/minecraft/mc-mods/sophisticated-backpacks/files/9083940

**Limite de escopo:** build **3.26.10** publicada para outros Minecrafts 26.x em outubro **não** deve ser usada como release de NeoForge 1.21.1. Runtime local segue em 3.26.3 até novo inventário.

