# Sophisticated Backpacks

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81969230c6a657439ec8
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `sophisticatedbackpacks-1.21.1-3.26.2.2141.jar`, mod id `sophisticatedbackpacks`, runtime `3.26.2`, mixin `sophisticatedbackpacks.mixins.json`; Sophisticated Core 1.5.1, Sophisticated JEI Index 1.2.2, Sophisticated Thirst Upgrade 0.1.8, Sophisticated Backpacks Create Integration 0.2.0 e Create 6.0.10 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Sophisticated Backpacks 3.26.2 e os addons/providers citados estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Sophisticated Backpacks
- **Arquivo JAR:** `sophisticatedbackpacks-1.21.1-3.26.2.2141.jar`
- **Versão 1.21.1:** 3.26.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Armazenamento, Automação
- **Função:** Storage portátil e colocável modular, com tiers/capacidade e upgrades funcionais de coleta, filtragem, alimentação, crafting/processamento, fluidos/energia e automação, além de settings persistentes.
- **Dependências:** Sophisticated Core 1.5.1 é required e está presente. Integrações físicas: Sophisticated JEI Index 1.2.2, Sophisticated Thirst Upgrade 0.1.8, Sophisticated Backpacks Create Integration 0.2.0 e Create 6.0.10.
- **Sobreposição:** É o storage portátil modular do stack Sophisticated. Cruza em conveniência com outros backpacks e com redes estacionárias, mas não equivale a Tom's/Sophisticated Storage nem aos addons Create.
- **Compatibilidade/Riscos:** Core de storage portátil stateful. Riscos: inventory/upgrades dupe ou loss em place/pickup/death, nested storage recursion, filter/settings drift, automation double-processing, capability sync e Create contraption/linked-storage state. 3.26.2 adiciona linked storage ao SB Create integration e exige regressão desse path.
- **Observações:** JAR físico `sophisticatedbackpacks-1.21.1-3.26.2.2141.jar`, runtime 3.26.2. Changelog exato da build: adição de linked storage à integração Create de Sophisticated Backpacks; demais features são arquitetura da linha, não delta exclusivo.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Sophisticated Backpacks 3.26.2.2141 + integrações físicas Sophisticated atuais do pack.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/sophisticated-backpacks/files/8833888
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Sophisticated Backpacks 3.26.2 reconstruído: portable/placeable storage, tiers/upgrades/settings, automation, persistence, nested/storage safety, JEI/Thirst/Create integrations, exact 3.26.2 linked-storage delta, lifecycle, riscos e testes.
- **Histórico da decisão:** Mantido como núcleo de armazenamento portátil. Confirmado carregado em 22/08/2026 na versão 3.25.78. A revisão separou o mod-base de addons que apenas facilitam upgrades ou fornecem kits gratuitos.
- **Data da última decisão:** 2026-08-22

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `sophisticatedbackpacks-1.21.1-3.26.2.2141.jar`, mod id `sophisticatedbackpacks`, versão `3.26.2`, NeoForge 1.21.1. É o **storage portátil modular** do stack Sophisticated e a decisão vigente **Manter** é preservada.

## 1. Identidade e papel
- **Mod:** Sophisticated Backpacks.
- **JAR:** `sophisticatedbackpacks-1.21.1-3.26.2.2141.jar`.
- **Mod id:** `sophisticatedbackpacks`.
- **Versão:** `3.26.2`.
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
O pack contém **Sophisticated JEI Index 1.2.2**, que pode usar ingredientes de backpacks equipados em recipe transfer.

Backpacks continua authority do inventory; JEI Index precisa consultar/extrair por APIs autorizadas. Transfer não pode bypassar lock/filter/restrições relevantes.

## 15. Sophisticated Thirst Upgrade
O pack contém **Sophisticated Thirst Upgrade 0.1.8**. Ele adiciona um upgrade funcional ao backpack para auto-hidratação.

Remover/trocar backpack ou upgrade precisa invalidar imediatamente qualquer pending auto-use; o core do backpack não deve consumir item por conta própria sem upgrade ativo.

## 16. Create Integration
**Sophisticated Backpacks Create Integration 0.2.0** está presente e adapta funcionalidades do backpack a moving contraptions Create.

Assembly/disassembly transforma contexto/posição do storage; conteúdo e upgrades devem permanecer únicos e consistentes durante movimento.

## 17. Delta exato 3.26.2
O changelog da build física `3.26.2.2141` registra **linked storage adicionado à integração Create de Sophisticated Backpacks**.

Esse é um regression gate específico da versão. O restante do catálogo de backpack/upgrades é arquitetura da linha e não deve ser atribuído como novidade exclusiva da 3.26.2.

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
- **Sophisticated JEI Index 1.2.2:** recipe transfer/index.
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
- [ ] Dedicated server inicia com Backpacks 3.26.2 + Core 1.5.1.
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
- [ ] Dois players acessando storage compartilhado não causam lost update.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 25. Evidências e limites
- Modlist física atual: Backpacks 3.26.2.2141, Core 1.5.1 e addons Sophisticated relacionados.
- CurseForge oficial: portable/placeable modular storage, upgrade ecosystem e exact changelog 3.26.2 linked-storage integration.
- Catálogo do pack: JEI Index, Thirst Upgrade e Create Integration presentes.
- **Limite:** configs locais e cada upgrade individual não foram testados; comportamento fino deve seguir a build/config efetivamente carregada.
