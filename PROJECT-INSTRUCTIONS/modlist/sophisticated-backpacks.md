# Sophisticated Backpacks

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81969230c6a657439ec8
- **Baseline física pré-update:** `sophisticatedbackpacks-1.21.1-3.26.2.2141.jar`
- **Artefato alvo selecionado no CurseForge:** `sophisticatedbackpacks-1.21.1-3.26.3.2158.jar` — file `8845926`, Release, publicado em 09/09/2026
- **Stack físico baseline:** Sophisticated Core 1.5.1, Sophisticated JEI Index 1.2.2, Sophisticated Thirst Upgrade 0.1.8, Sophisticated Backpacks Create Integration 0.2.0 e Create 6.0.10
- **Data da atualização documental GitHub:** 2026-09-14

> **BOUNDARY FÍSICO.** A modlist física entregue ainda contém 3.26.2.2141. O GitHub registra 3.26.3.2158 como alvo de atualização. O delta público confirmado é específico e relevante para este pack: correção de **stack overflow quando Create Packagers acessam Inception backpacks**.

## Propriedades equivalentes do catálogo

- **Mod:** Sophisticated Backpacks
- **Arquivo JAR alvo:** `sophisticatedbackpacks-1.21.1-3.26.3.2158.jar`
- **Versão 1.21.1 alvo:** 3.26.3
- **Baseline física auditada:** 3.26.2.2141 / runtime 3.26.2
- **Estado da pesquisa:** Verificado documentalmente; validação física/runtime da 3.26.3.2158 pendente
- **Decisão:** Manter
- **Categoria:** Armazenamento, Automação
- **Função:** Storage portátil e colocável modular, com tiers/capacidade e upgrades funcionais de coleta, filtragem, alimentação, crafting/processamento, fluidos/energia e automação, além de settings persistentes.
- **Dependências:** Sophisticated Core 1.5.1 é required e está presente na baseline. Integrações físicas: Sophisticated JEI Index 1.2.2, Sophisticated Thirst Upgrade 0.1.8, Sophisticated Backpacks Create Integration 0.2.0 e Create 6.0.10.
- **Sobreposição:** É o storage portátil modular do stack Sophisticated. Cruza em conveniência com outros backpacks e com redes estacionárias, mas não equivale a Tom's/Sophisticated Storage nem aos addons Create.
- **Compatibilidade/Riscos:** Core de storage portátil stateful. Riscos: inventory/upgrades dupe ou loss em place/pickup/death, nested storage recursion, filter/settings drift, automation double-processing, capability sync, linked-storage state e Create Packager/Inception recursion. A 3.26.3 corrige o stack overflow conhecido nesse último path.
- **Observações:** A baseline 3.26.2 adicionou linked storage à integração Create. A 3.26.3.2158 corrige stack overflow quando Create Packagers acessam Inception backpacks; esse fix passa a ser regression gate do alvo.
- **Procedência:** modlist.txt física baseline + CurseForge oficial Sophisticated Backpacks 3.26.3.2158 + changelogs 3.26.2/3.26.3 + integrações físicas Sophisticated atuais do pack.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/sophisticated-backpacks
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 14/09/2026 — GitHub promovido para alvo 3.26.3.2158. Fix de Create Packager ↔ Inception backpack integrado ao dossier e à matriz de regressão; baseline física 3.26.2 preservada até re-fetch pós-update.
- **Histórico da decisão:** Mantido como núcleo de armazenamento portátil. Confirmado carregado em 22/08/2026 na versão 3.25.78. A revisão separou o mod-base de addons que apenas facilitam upgrades ou fornecem kits gratuitos.
- **Data da última decisão:** 2026-08-22

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico baseline: `sophisticatedbackpacks-1.21.1-3.26.2.2141.jar`, mod id `sophisticatedbackpacks`, versão `3.26.2`. Alvo documental: `sophisticatedbackpacks-1.21.1-3.26.3.2158.jar`. É o **storage portátil modular** do stack Sophisticated e a decisão vigente **Manter** é preservada.

## 1. Identidade e papel
- **Mod:** Sophisticated Backpacks.
- **JAR físico baseline:** `sophisticatedbackpacks-1.21.1-3.26.2.2141.jar`.
- **JAR alvo:** `sophisticatedbackpacks-1.21.1-3.26.3.2158.jar`.
- **Mod id baseline:** `sophisticatedbackpacks`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Required baseline:** Sophisticated Core `1.5.1`.

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

Sophisticated Thirst Upgrade 0.1.8 amplia esse padrão para hidratação no pack baseline.

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

## 11. Nested storage, Inception e recursion
Backpacks/storage dentro de outros containers podem criar riscos de nesting/recursion dependendo das regras da build/config. O mod deve aplicar suas próprias restrições e nunca serializar referência cíclica que cause duplication, stack overflow ou save bloat.

A atualização 3.26.3 é diretamente relevante: corrige **stack overflow quando Create Packagers acessam Inception backpacks**. Portanto Inception + Packager não é apenas risco teórico; é um regression gate release-specific.

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
**Sophisticated Backpacks Create Integration 0.2.0** está presente na baseline e adapta funcionalidades do backpack a moving contraptions Create.

Assembly/disassembly transforma contexto/posição do storage; conteúdo e upgrades devem permanecer únicos e consistentes durante movimento. Create Packagers também são agora uma superfície de regressão explicitamente coberta pelo fix 3.26.3.

## 17. Delta 3.26.2 → 3.26.3
A baseline `3.26.2.2141` adicionou **linked storage à integração Create de Sophisticated Backpacks**.

A release alvo `3.26.3.2158`, publicada em 09/09/2026, corrige especificamente:
- **stack overflow when Create Packagers access Inception backpacks**.

Não atribuir outras mudanças à 3.26.3 sem changelog/source específico.

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
- linked-storage identity;
- nesting/Inception access e Packager settlement.

Cliente apresenta GUI/render/input. Slot fantasma/preview não equivale a item real.

## 20. Multiplayer
Dois players acessando o mesmo placed/linked storage precisam de locking/synchronization suficientes para evitar lost update/dupe.

Backpack pessoal não pode ser exposto a outro player sem mecanismo de acesso autorizado do próprio mod/provider.

## 21. Integrações e sobreposição no pack
- **Sophisticated Core 1.5.1:** infraestrutura obrigatória na baseline.
- **Sophisticated JEI Index 1.2.2:** recipe transfer/index.
- **Sophisticated Thirst Upgrade 0.1.8:** auto-hidratação.
- **Create Integration 0.2.0:** moving contraptions/linked storage.
- **Create 6.0.10:** provider dos Packagers/contraption stack envolvido na regressão.
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
- Create Packager acessando Inception backpack;
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
11. **Packager/Inception recursion:** bug de stack overflow corrigido em 3.26.3 precisa permanecer fechado.

## 24. Matriz de testes
- [ ] Nova modlist física confirma `sophisticatedbackpacks-1.21.1-3.26.3.2158.jar`.
- [ ] Dedicated server inicia com Backpacks 3.26.3 + Core/runtime dependente atual.
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
- [ ] Linked storage funciona e mantém identity após movimento/restart.
- [ ] **Create Packager acessa Inception backpack sem stack overflow, recursion runaway, dupe ou loss — regression gate 3.26.3.**
- [ ] Dois players acessando storage compartilhado não causam lost update.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 25. Evidências e limites
- Modlist física baseline: Backpacks 3.26.2.2141, Core 1.5.1 e addons Sophisticated relacionados.
- CurseForge oficial: `sophisticatedbackpacks-1.21.1-3.26.3.2158.jar`, file 8845926, Release, 09/09/2026.
- Changelog 3.26.3: fix de stack overflow quando Create Packagers acessam Inception backpacks.
- Changelog 3.26.2: linked storage adicionado à integração Create.
- Catálogo do pack: JEI Index, Thirst Upgrade, Create Integration e Create presentes.
- **Limite:** configs locais e cada upgrade individual não foram testados; instalação física 3.26.3 e resultado runtime ainda exigem nova modlist/JAR e QA.
