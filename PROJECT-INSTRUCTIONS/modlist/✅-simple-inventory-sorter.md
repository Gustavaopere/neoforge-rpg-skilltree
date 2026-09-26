# Simple Inventory Sorter

## Propriedades do registro

- **Mod:** Simple Inventory Sorter
- **Arquivo JAR:** `inventorysorter-1.21.1-24.0.24.jar`
- **Versão 1.21.1:** `24.0.24`
- **Categoria:** QoL
- **Decisão:** Sem decisão
- **Histórico da decisão:** 2026-09-07 — novo mod incorporado à auditoria; sem decisão curatorial ainda.
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/inventory-sorter/files/7188660
- **Função:** QoL de inventário que ordena containers por comando do cliente processado no servidor e permite transferir itens individualmente com a roda do mouse, com blacklist para containers problemáticos.
- **Dependências:** NeoForge 1.21.1; a release 24.0.24 não lista projeto obrigatório adicional. O mod precisa estar no cliente e servidor para funcionar conforme documentação oficial.
- **Compatibilidade/Riscos:** QoL de inventário com comandos server-side. Riscos: containers com slots especiais mal declarados, dupe se provider viola `canExtract`/`isItemValid`, conflito de gesto/keybind com Mouse Tweaks/Pondus Inventory, sorting de containers modded e client/server version mismatch.
- **Sobreposição:** Sobreposição de UX com Mouse Tweaks 2.26.1 e Pondus Inventory 0.15-Beta presentes no pack. Não há incompatibilidade técnica confirmada; validar gestos/keybinds e evitar duas ações disparadas pelo mesmo input.
- **Observações:** Release oficial 24.0.24. Middle-click ordena; wheel transfere um item por passo. `/inventorysorter` oferece blacklist do último container. Upstream documenta que o sorting não é feito client-side e respeita `canExtract`/`isItemValid` quando o container os implementa corretamente.
- **Procedência:** modlist.txt física anexada e reconferida em 12/09/2026 + CurseForge oficial Inventory Sorter file 7188660. A build `inventorysorter-1.21.1-24.0.24.jar` declara suporte a 1.21.1–1.21.8; a filtragem atual para 1.21.1 mantém 24.0.24 como versão mais recente aplicável. Versões superiores pertencem a outras linhas de Minecraft.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 12/09/2026 — Simple Inventory Sorter 24.0.24/JAR físico reconfirmado. A listagem atual mostra 24.0.24 como a build mais recente que ainda declara suporte a Minecraft 1.21.1; releases numericamente superiores são para linhas 1.21.10/1.21.11/26.x. Server-side sorting authority, wheel transfer, blacklist, slot contracts, lifecycle/multiplayer, riscos e testes preservados.
- **Data da última decisão:** 2026-09-07

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #337: JAR `inventorysorter-1.21.1-24.0.24.jar`, mod id `inventorysorter`, runtime `24.0.24`, SHA-1 `8c842959ecb1927e1c7049d41d024fadf329a584`.

<callout icon="🗂️" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `inventorysorter-1.21.1-24.0.24.jar`, mod id `inventorysorter`, versão `24.0.24`. O arquivo oficial CurseForge 7188660 corresponde à release NeoForge 1.21.1 instalada.
</callout>
## 1. Papel e authority
Simple Inventory Sorter é QoL de inventário. O cliente envia a intenção de ordenar/transferir, mas a documentação do projeto explicita que **o sorting não é feito client-side**: o trabalho é delegado ao servidor. O container/provider continua authority dos slots, validade de item e extração.
## 2. Ordenação por clique do meio
O gesto padrão de middle-click ordena o inventário/container. O algoritmo documentado ordena de maior para menor quantidade e, em empates, agrupa/ordena por registry name do item. A ordem visual resultante não deve alterar ownership, NBT/componentes ou quantidade dos stacks.
## 3. Transferência com mouse wheel
A roda do mouse move itens individualmente entre inventário do jogador e container. O keybinding padrão pode ser restaurado caso tenha sido alterado. Como Mouse Tweaks e outros QoL também observam gestos do mouse, input overlap deve ser testado no stack real.
## 4. Server-side authority
O servidor executa a mutação do inventário. Isso evita tratar a tela local como verdade do inventário, mas exige packet/slot validation. Repetição de input, latency ou screen close não pode aplicar a mesma transferência duas vezes.
## 5. Slot contracts
O projeto procura respeitar `canExtract` e `isItemValid` quando containers os implementam corretamente. Slots especiais que não declaram restrições adequadamente podem ser perigosos: ordenar/mover conteúdo pode quebrar lógica do provider ou, em casos extremos, contribuir para dupes.
## 6. Blacklist de containers
O comando `/inventorysorter <arg>` permite gerenciar uma blacklist baseada no último container em que o usuário tentou ordenar. A documentação cita subcomandos `bladd`, `blremove`, `show` e `list`. A blacklist é mecanismo de segurança/compatibilidade para containers problemáticos, não substituto de correção do provider.
## 7. Containers modded
Máquinas, backpacks, crafting grids e inventories especiais podem possuir slots de output, ghost slots, upgrades ou filtros. Não assumir que todo menu deve ser ordenável. Se um container tem semântica não convencional, validar comportamento e, se necessário, blacklistá-lo.
## 8. Overlap de UX no pack
Mouse Tweaks 2.26.1 e Pondus Inventory 0.15-Beta estão presentes. A coexistência não prova conflito, mas os três podem tocar interação de inventário. O teste precisa confirmar que scroll/middle-click produzem exatamente uma ação e que nenhum mod captura o gesto de modo concorrente.
## 9. Client / server e multiplayer
A distribuição é Client & Server. O cliente captura input/UI; o servidor valida e aplica mudanças nos slots. Em multiplayer, duas interações concorrentes sobre o mesmo container devem respeitar o state atual e nunca duplicar stacks por snapshot stale.
## 10. Lifecycle
Validar abrir/fechar menus, trocar containers rapidamente, reconnect, server restart e uso sob latency. Pacotes recebidos depois que o menu foi fechado ou mudou de container precisam ser rejeitados pelo contexto atual do servidor.
## 11. Riscos técnicos
- container especial não declarar corretamente slot restrictions;
- sorting mover output/ghost/filter slots;
- packet/input duplicado causar duas transferências;
- middle-click/wheel conflitar com outro QoL;
- blacklist não cobrir novo tipo de container problemático;
- client/server em versões diferentes;
- item com componentes/NBT ser agrupado incorretamente por integração externa;
- screen close deixar ação stale.
## 12. Matriz de testes obrigatória
- [ ] Client + dedicated server iniciam com 24.0.24.
- [ ] Middle-click ordena chest vanilla sem alterar quantidades/componentes.
- [ ] Wheel move exatamente um item por ação documentada.
- [ ] Slots de output/upgrade/filter de containers modded não são corrompidos.
- [ ] `bladd`, `blremove`, `show` e `list` funcionam no contexto esperado.
- [ ] Container blacklistado não é ordenado acidentalmente.
- [ ] Mouse Tweaks/Pondus Inventory não duplicam a mesma ação.
- [ ] Latency/repeated input não duplica stacks.
- [ ] Fechar menu durante ação não aplica mutation ao container errado.
- [ ] Multiplayer concorrente mantém state autoritativo.
## 13. Evidências e limites
- **Modlist física:** JAR/mod id/runtime 24.0.24.
- **CurseForge oficial:** file 7188660, NeoForge 1.21.1, Client & Server.
- **Documentação/source oficial:** middle-click, mouse-wheel transfer, server-side sorting, blacklist e slot validity/extraction contracts.
- **Limite:** source branch atual não foi promovido a byte pin da release 24.0.24; detalhes internos não publicados permanecem fail-closed.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
