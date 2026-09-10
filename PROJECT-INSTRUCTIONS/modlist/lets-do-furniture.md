# [Let's Do] Furniture

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81aca62de49316b75c81
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** [Let's Do] Furniture
- **Arquivo JAR:** `letsdo-furniture-neoforge-1.1.4.jar`
- **Versão 1.21.1:** 1.1.4
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, QoL
- **Função:** Mobiliário decorativo e funcional com furniture modular, storage, displays, aquarium, Gramophone, Trashcan, seating e variantes por madeira/cor.
- **Dependências:** NeoForge 1.21.1 + Architectury. Compat layer 1.1.4 inclui BloomingNature, Meadow, AlpineWhispers e Beachparty; Meadow 1.4.8 está fisicamente presente.
- **Sobreposição:** Principalmente decoração/functional furniture. Pode cruzar com outros storage/furniture mods, mas ownership de cada BlockEntity/inventory permanece no provider Furniture.
- **Compatibilidade/Riscos:** Riscos: BlockEntity/NBT loss, save/tick leak do Gramophone, inventory/entity duplication, destructive Trashcan races e wood-compat drift. Regressões Coffer/Gramophone já corrigidas permanecem testes de não-regressão.
- **Observações:** Source oficial `Let-s-Do-Collection/Furniture` branch 1.21.1 declara exatamente 1.1.4. Release adiciona compatibilidade explícita com woods de Meadow e outros três mods.
- **Procedência:** modlist.txt física atual + source oficial Furniture branch 1.21.1 + arquivos de build, README e changelog oficiais da mesma linha.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/lets-do-furniture
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — source 1.1.4 pinado; furniture modular, storage/Coffer, Gramophone, aquarium/displays, wood compat, lifecycle, risks e testes catalogados.
- **Histórico da decisão:** 2026-09-07 — novo mod incorporado à auditoria; sem decisão curatorial ainda.
- **Data da última decisão:** 2026-09-07

> 🪑 **ESCOPO CANÔNICO.** Runtime físico: `letsdo-furniture-neoforge-1.1.4.jar`, mod id `furniture`, versão `1.1.4`. É um mod de mobiliário decorativo e funcional com storage, seating/display, aquarium, Gramophone, trashcan e furniture modular.

## 1. Identidade e source pin
O source oficial `Let-s-Do-Collection/Furniture`, branch `1.21.1`, declara exatamente `mod_version=1.1.4`, Minecraft 1.21.1, Architectury 13.0.8 e NeoForge 21.1.x. A publicação oficial 1.1.4 é para NeoForge 1.21.1 e o JAR físico corresponde à mesma versão.

## 2. Papel no modpack
O README descreve Furniture como uma coleção de móveis com função além da aparência. Há famílias em todos os wood types/cores, elementos modulares e blocos que armazenam, exibem ou reproduzem conteúdo. Portanto parte do mod é puramente render/decor e parte mantém BlockEntity/inventory state persistente.

## 3. Furniture modular
Dressers podem conectar-se a desks de madeira diferente; lamps podem variar de altura; shutters podem ser estendidos. Connectivity/shape deve ser derivada do state real do bloco e atualizada sem perder itens ou produzir blocos duplicados ao quebrar uma composição.

## 4. Storage e Coffer
O mod possui storage funcional, incluindo Coffer. A versão 1.1.1 corrigiu crash ao salvar jogador com Coffer no inventário por ausência do BlockEntity id em NBT. Isso comprova que items/blocks podem carregar BlockEntity data e que save/move/break precisa preservar metadata corretamente.

## 5. Gramophone
O Gramophone reproduz music discs e possui repeat mode. A 1.1.2 corrigiu **world save hang ao sair com Gramophone ativo**; a 1.1.3 corrigiu repeat mode que parava após uma faixa. Playback/ticking deve ser limpo em world unload e não bloquear save thread.

## 6. Aquarium e displays
O README confirma aquarium capaz de conter pufferfish, cod ou salmon, além de displays para items. Essas superfícies têm entity/item presentation state. Captura/release, break/drop e chunk unload devem preservar ownership e não duplicar peixes/items.

## 7. Trashcan e destructive interaction
O Trashcan elimina items. Essa ação precisa ser server-authoritative e deliberada; automação/GUI não deve deletar stacks por race ou client prediction. Não usar a animação visual como confirmação de remoção sem state do inventory servidor.

## 8. Compatibilidade de wood sets
A versão 1.1.4 adiciona compatibility layer para woods de **BloomingNature, Meadow, AlpineWhispers e Beachparty**. Meadow 1.4.8 está fisicamente presente neste pack; portanto recipes/registries de furniture derivados de Pine/wood families precisam ser verificados contra os IDs/tags da versão instalada.

## 9. Client / server
Models, curtains, mirrors, lamps, aquarium rendering e Gramophone presentation são client-facing. Storage, item deletion, disc inventory, fish/item containment e BlockEntity state pertencem ao servidor. Dedicated server não deve carregar classes client-only para lógica de storage/save.

## 10. Lifecycle e persistência
Validar placement/break de blocos modulares, save/restart de storage e aquarium, Gramophone active→quit, chunk unload com áudio ativo, block-to-item conversion de containers e resource reload de models. Nenhum BlockEntity deve ser duplicado ou perder seu payload ao virar item quando essa conversão é suportada.

## 11. Changelog específico 1.1.4
A 1.1.4 remove unused recipes, corrige crash com Turkish language pack e adiciona a camada de compatibilidade de woods. Essas mudanças devem ser consideradas parte da release instalada; problemas históricos de Coffer/Gramophone são regressões já corrigidas, mas entram na matriz de não-regressão.

## 12. Riscos técnicos
1. **BlockEntity/NBT loss** ao mover/quebrar storage.
2. **Save hang/tick leak** com Gramophone ativo.
3. **Inventory duplication** em furniture modular ou automação concorrente.
4. **Entity duplication/loss** em aquarium durante break/unload.
5. **Destructive race** no Trashcan.
6. **Wood compat drift** com Meadow/AlpineWhispers/Beachparty/BloomingNature.
7. **Render/model conflicts** com resource packs e furniture variants.

## 13. Matriz de testes
- [ ] Dedicated server inicia com Furniture 1.1.4.
- [ ] Coffer com conteúdo sobrevive a save/restart e itemização sem crash/dupe.
- [ ] Gramophone ativo não bloqueia world save/quit.
- [ ] Repeat mode mantém sequência conforme esperado e para ao unload.
- [ ] Aquarium preserva peixe/state após chunk unload e não duplica ao quebrar.
- [ ] Displays preservam o item exatamente uma vez.
- [ ] Trashcan remove apenas o stack efetivamente confirmado pelo servidor.
- [ ] Furniture modular conecta/desconecta sem ghost block ou item dupe.
- [ ] Woods de Meadow presentes resolvem recipes/variants sem missing model.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 14. Evidências e limites
- modlist física: Furniture 1.1.4 e Meadow 1.4.8 presentes;
- source oficial branch 1.21.1: version pin;
- README oficial: furniture modular, storage/decor, aquarium, displays, Gramophone e Trashcan;
- CHANGELOG oficial 1.1.1–1.1.4: NBT/Coffer, save hang/repeat e wood compatibility.
Um relato de issue por número que não pôde ser localizado no repositório oficial foi deliberadamente **excluído** desta ficha.
