# Sophisticated Storage Create Integration

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81c3afece3fdd3187b32
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `sophisticatedstoragecreateintegration-1.21.1-0.1.21.209.jar`, mod id `sophisticatedstoragecreateintegration`, runtime `0.1.21`; Create 6.0.10, Sophisticated Core 1.5.1 e Sophisticated Storage 1.5.91 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, a integração 0.1.21 e os providers required citados estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Sophisticated Storage Create Integration
- **Arquivo JAR:** `sophisticatedstoragecreateintegration-1.21.1-0.1.21.209.jar`
- **Versão 1.21.1:** 0.1.21
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Compat, Armazenamento
- **Função:** Allows Sophisticated Storage barrels/chests/shulker boxes to function on Create contraptions while preserving content, upgrades, settings and memorized slots and adapting position-sensitive upgrades to moving storage.
- **Dependências:** Create 6.0.10, Sophisticated Core 1.5.1 and Sophisticated Storage 1.5.91 are required and physically present; JEI 19.53.0.426 is present as an optional integration surface.
- **Sobreposição:** Does not create new storage; adapts existing Sophisticated Storage blocks/features to Create contraptions.
- **Compatibilidade/Riscos:** Create↔Sophisticated Storage bridge. Riscos: content/upgrades loss/dupe on assembly, dynamic-position mistakes for pickup/magnet, stale capability after movement, packed/tier operations in motion, lock/visual-state drift and concurrent access. 0.1.21 is the physical Release.
- **Observações:** JAR físico `sophisticatedstoragecreateintegration-1.21.1-0.1.21.209.jar`, runtime 0.1.21. Official docs explicitly cover dynamic positions for Pickup/Magnet, right-click upgrades, tier upgrades, Storage Tool and Paintbrush on contraption storage.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge official Sophisticated Storage Create Integration 0.1.21 + official project feature documentation.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage-create-integration
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Sophisticated Storage Create Integration 0.1.21 reconstruído: barrels/chests/shulkers on contraptions, persistent content/settings/memorized slots, dynamic-position upgrades, stack/crafting/tier upgrades, Storage Tool/Paintbrush, lifecycle, risks and tests.
- **Histórico da decisão:** Mantido para integração Sophisticated Storage ↔ Create. Em 11/09/2026 revalidado contra runtime físico 0.1.21.209 e providers atuais.
- **Data da última decisão:** 2026-08-22

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `sophisticatedstoragecreateintegration-1.21.1-0.1.21.209.jar`, mod id `sophisticatedstoragecreateintegration`, versão `0.1.21`, NeoForge 1.21.1. A bridge mantém **Sophisticated Storage funcional em contraptions Create**; a decisão vigente **Manter** é preservada.

## 1. Identidade e dependências
- **Mod:** Sophisticated Storage Create Integration.
- **Versão:** `0.1.21`.
- **Providers físicos:** Create `6.0.10`, Sophisticated Core `1.5.1`, Sophisticated Storage `1.5.91`.
- **JEI:** presente como integration surface opcional.

## 2. Storage types suportados
A documentação oficial cobre storages Sophisticated como:
- barrels;
- chests;
- shulker boxes.

Quando montados em contraption, continuam sendo o mesmo storage lógico do provider, não uma cópia genérica de inventory Create.

## 3. Conteúdo persistente
O conteúdo do storage deve sobreviver assembly, movimento e disassembly. A operação precisa manter exatamente um owner state do inventário.

Se o block original deixa de existir no world durante assembly, sua capability não pode continuar disponível em paralelo.

## 4. Upgrades
A integração preserva **upgrades** dos storages montados. Isso inclui upgrades funcionais suportados pelo provider e suas settings.

A bridge não reimplementa upgrade logic; adapta o contexto para que o upgrade existente continue funcionando.

## 5. Settings e memorized slots
Official docs explicitam preservação de **settings** e **memorized slots** em contraptions.

Esse state precisa sobreviver save/restart e retornar intacto no disassembly, sem reset para defaults.

## 6. Position-sensitive upgrades
Upgrades como **Pickup/Magnet** dependem da posição real do storage. Em contraption, a integração usa a posição dinâmica/atual do storage em vez da posição estática de origem.

Esse é um dos maiores riscos técnicos: coordenada stale pode coletar entities/items no lugar errado.

## 7. Pickup/Magnet em movimento
Durante translação/rotação rápida, a área efetiva precisa acompanhar o storage. Um item não pode ser coletado duas vezes ao cruzar frames/posições sucessivas.

Também não deve existir coleta na origem depois que a contraption já se moveu.

## 8. Stack upgrades
A documentação informa que stack upgrades continuam funcionais em contraptions.

Limites efetivos e stack counts continuam sob authority do storage/provider; movimento não pode alterar capacidade nem recalcular stacks de modo destrutivo.

## 9. Crafting Upgrade
Crafting Upgrade também é suportado no contexto móvel. Inputs/outputs precisam operar sobre o inventory da contraption/storage correto.

Fechar GUI, mover contraption ou desmontar durante craft não pode duplicar preview/input/output.

## 10. Right-click upgrade installation
O projeto permite adicionar upgrades por **right-click** no storage montado. Target resolution precisa acertar o storage correto dentro da contraption.

Uma interação client-side com outline errado não pode instalar upgrade em storage diferente.

## 11. Tier upgrades em movimento
Tier upgrades podem ser aplicados ao storage em contraption conforme documentação oficial.

O upgrade deve preservar conteúdo/settings/memorized slots e atualizar block/storage identity sem criar block original paralelo.

## 12. Storage Tool
O **Storage Tool** funciona com storages montados para operações suportadas, incluindo gerenciamento de lock/indicadores visuais.

Targeting deve usar a transformação atual da contraption; lock state precisa sincronizar para todos os clientes.

## 13. Paintbrush
Paintbrush pode alterar tints/barrel materials em storages da contraption.

Isso é alteração visual/metadata e não deve recriar inventory nem resetar upgrades/settings.

## 14. Assembly lifecycle
Na montagem:
1. capturar state válido do storage;
2. transferi-lo ao representation da contraption;
3. invalidar acesso ao block original;
4. preservar upgrades/settings/memory.

Falha parcial deve rollback sem dupe/loss.

## 15. Disassembly lifecycle
Na desmontagem, state deve ser materializado uma única vez no storage colocado. Position-sensitive upgrades precisam voltar ao contexto world normal.

Capability da contraption antiga deve ser invalidada imediatamente.

## 16. Chunk crossing e unload
Contraptions podem cruzar chunks e descarregar/recarregar. Inventory state não deve depender de o chunk de origem permanecer carregado.

Save/restart com contraption carregada é teste crítico de persistence.

## 17. Client / server
Servidor é authority de:
- inventory/upgrades/settings;
- dynamic position usada em operações funcionais;
- tier/upgrade install;
- crafting commits;
- lock state.

Cliente apenas renderiza/interage. Posição visual não pode ser a única fonte para operação funcional.

## 18. Multiplayer
Dois players acessando storage móvel precisam convergir ao mesmo inventory state. Movimento não pode gerar GUI snapshot separado por jogador que permita retirar a mesma stack duas vezes.

Targeting/interação precisa respeitar contraption transform para todos os observadores.

## 19. JEI boundary
JEI pode exibir recipes de upgrades/crafting, mas não controla inventory móvel. Recipe transfer em GUI de contraption, se suportado no path atual, deve continuar validado pelo servidor/storage provider.

Não atribuir feature não documentada apenas pela presença do JEI.

## 20. Riscos técnicos
1. **Assembly dupe/loss:** world block e contraption coexistem.
2. **Dynamic-position stale:** Pickup/Magnet usa coordenada antiga.
3. **Double pickup:** item coletado em dois ticks/frames de movement.
4. **Capability stale:** inventory antigo continua acessível após disassembly.
5. **Settings/memory loss:** state não serializa na contraption.
6. **Tier upgrade corruption:** block identity muda sem transfer completo.
7. **Crafting race:** move/disassembly durante commit duplica inputs/outputs.
8. **Targeting error:** tool/right-click atinge storage errado.
9. **Concurrent multiplayer:** dois clientes retiram mesma stack.

## 21. Matriz de testes
- [ ] Boot com Integration 0.1.21 + Create 6.0.10 + Storage 1.5.91 + Core 1.5.1.
- [ ] Barrel/chest/shulker com conteúdo monta sem dupe/loss.
- [ ] Upgrades/settings/memorized slots persistem durante movimento.
- [ ] Pickup Upgrade usa posição atual da contraption.
- [ ] Magnet Upgrade idem e não coleta duas vezes.
- [ ] Stack Upgrade mantém capacidade correta.
- [ ] Crafting Upgrade não duplica input/output ao mover/desmontar.
- [ ] Right-click instala upgrade no storage correto.
- [ ] Tier upgrade em movimento preserva todo state.
- [ ] Storage Tool altera lock/indicator correto.
- [ ] Paintbrush altera aparência sem resetar inventory.
- [ ] Chunk crossing/save restart preserva state.
- [ ] Disassembly restaura exatamente um storage íntegro.
- [ ] Dois players acessando storage móvel não causam dupe/lost update.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 22. Evidências e limites
- Modlist física atual: Integration 0.1.21.209 e providers atuais.
- CurseForge oficial: barrels/chests/shulkers em contraptions; content/upgrades/settings/memorized slots; dynamic position de Pickup/Magnet; stack/crafting/tier upgrades; Storage Tool/Paintbrush.
- **Limite:** nenhum contraption runtime test foi executado; detalhes não publicados de implementação não foram inventados.
