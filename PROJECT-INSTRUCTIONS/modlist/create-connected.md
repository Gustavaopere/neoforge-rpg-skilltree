# Create: Connected

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81328e2bea1c4458aa7e
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Connected
- **Arquivo JAR:** `create_connected-1.3.3-mc1.21.1.jar`
- **Versão 1.21.1:** 1.3.3-mc1.21.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, QoL, Automação
- **Função:** Pacote de QoL e automação para Create, com novos blocos, melhorias em filtros/gearshifts, comportamento de contraptions e ajustes configuráveis.
- **Dependências:** Create 6.0.10 físico; a linha 1.3.3 exige Create 6.0.7+. Integrações públicas incluem Dye Depot/Create Dragons Plus para fan catalysts e superfícies de inventory/networking Create.
- **Sobreposição:** Sobrepõe parcialmente QoL/automação com Create Utilities J 0.3.4+1.21.1 e outros addons, mas deve ser comparado feature a feature. Não há base para classificar o pacote inteiro como redundante.
- **Compatibilidade/Riscos:** Addon transversal de alto alcance. Riscos: Inventory Bridge/filter visibility, Kinetic Battery stress/charge dupe, sequenced pulse state, feature-toggle/JEI stale, schematic/contraption state e API/registry drift. 1.3.3 mantém a mudança de package de registrations introduzida em 1.2.0.
- **Observações:** JAR físico `create_connected-1.3.3-mc1.21.1.jar`, mod id `create_connected`, runtime 1.3.3-mc1.21.1. O texto antigo que citava runtime 1.3.2 foi corrigido. Release NeoForge 1.21.1 de 31/08/2026, Client & Server.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Create: Connected 1.3.3 + GitHub/changelog oficial da linha 1.3.x.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-connected
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — corpo vazio corrigido; Inventory Bridges/filters, Kinetic Battery, Sequenced Pulse Generator, feature toggles, contraptions, recipes/lifecycle e regressões catalogados para 1.3.3.
- **Histórico da decisão:**
- **Data da última decisão:**

> 🔗 **ESCOPO CANÔNICO.** Runtime físico `create_connected-1.3.3-mc1.21.1.jar`. Create: Connected é um addon transversal de Create com blocos/itens de QoL, automação, inventory connectivity, redstone/kinetics e feature toggles; por escopo, exige documentação mais ampla que um bridge simples.

## 1. Identidade e authority
Create 6.0.10 é o provider base. Connected owns seus próprios blocks/items, recipes, feature toggles e regras adicionais; stress networks, contraptions, stock networks e core kinetics continuam sendo authority do Create.
A build física 1.3.3 é Release NeoForge 1.21.1 e requer Create 6.0.7+.

## 2. Inventory Bridges e filtros
Na linha 1.3.x, item filters podem controlar extração através de **Inventory Bridges** e também quais items ficam visíveis para Stock Link networks. Isso transforma filter state em parte da logística real, não apenas UI.
Bridge/filter precisa ser server-authoritative: um item escondido do network não pode continuar extraível por cache stale, e alteração de filtro deve refletir nos consumidores sem restart.

## 3. Kinetic Battery
A linha 1.3.x reformulou Kinetic Battery:
- descarrega conforme **stress realmente consumido**;
- não descarrega quando outras sources cobrem a demanda;
- o item da battery preserva charge em NBT/data;
- batteries encadeadas compartilham redstone signal.

Isso exige evitar geração/consumo duplicado de energia cinética, perda de charge em break/place e disagreement entre HUD e state do servidor.

## 4. Sequenced Pulse Generator e redstone
O Sequenced Pulse Generator teve rework e correção de estado preso na linha atual. Pulse sequence, timing e redstone state precisam persistir em unload/restart e não reexecutar etapas ao reconectar cliente.
Centrifugal Clutch e outros controles cinéticos/redstone do addon também devem obedecer ao stress/rotation state do Create em vez de manter contadores paralelos.

## 5. Feature toggles e integração de recipe viewers
O projeto expõe events NeoForge antes/depois de feature toggle para permitir atualização da item list de JEI e integração com progressão/stages. Isso é importante no pack: esconder/desabilitar feature precisa afetar recipe/item visibility sem deixar conteúdo usável apenas porque o cliente ainda o mostra.
Feature visibility no cliente não substitui validação server-side de recipe/interaction.

## 6. Outros blocos e QoL da linha
A linha 1.2/1.3 inclui Dashboard, Brass Chute, catalysts adicionais para fans, placement helpers, Linked Transmitter behavior e melhorias em inventory ports/bridges. O catálogo exato da 1.3.3 deve ser lido do registry/source quando outro projeto precisar de IDs individuais; esta ficha documenta os subsistemas comprovados sem inventar contagem total.

## 7. Fan catalysts e integrações
A linha 1.3 adiciona integração de fan catalysts com Dye Depot e Create Dragons Plus. Esses providers devem permanecer owners dos próprios materiais/conteúdo; Connected apenas registra o comportamento Create correspondente quando presentes.

## 8. Schematics, contraptions e Sable
Changelog histórico da linha registra fixes para schematics, Linked Receivers após restart e até item duplication envolvendo Item Silo em physics contraption. Isso prova que state de BE/contraption é superfície sensível.
No pack atual, Sable/Aeronautics amplia o número de contextos móveis. Inventory Bridges, batteries e redstone blocks precisam ser testados em assemble/disassemble/unload, sem presumir que todo bloco seja automaticamente seguro em sublevels.

## 9. Package/API drift da 1.3.3
A file page 1.3.3 repete a breaking code change introduzida em 1.2.0: registrations foram movidos para o package `registries`. Isso é relevante para KubeJS/addons/código próprio que compile ou importe classes do mod. Não usar imports antigos por conveniência.

## 10. Client/server e multiplayer
GUI, overlays e item visibility são client-facing; filter state, inventory transfer, battery charge, redstone sequence e kinetic application são server-authoritative. Dois players editando filter/battery settings devem convergir para um state único e ordenado.

## 11. Riscos
1. Inventory Bridge mostra uma coisa e extrai outra.
2. Filter cache fica stale após mudança/reload.
3. Kinetic Battery descarrega mesmo com outra source disponível.
4. Break/place perde ou duplica charge.
5. Battery chain duplica redstone/kinetic contribution.
6. Sequenced Pulse Generator repete ou trava step após restart.
7. Feature toggle atualiza JEI mas não bloqueia gameplay real.
8. Schematic/contraption perde BE state ou duplica item.
9. Addon externo usa package antigo após mudança `registries`.
10. Overlap com Create Utilities J/outros QoL implementa a mesma ação duas vezes.

## 12. Boundary para projetos próprios
Para gates/progressão, usar feature state server-side e eventos do provider quando seguros. Não considerar item oculto em JEI como necessariamente bloqueado; não conceder XP por UI/filter edits. Kinetic Battery deve continuar owner de charge/stress accounting.

## 13. Matriz de testes
- [ ] Dedicated server inicia com Connected 1.3.3 + Create 6.0.10.
- [ ] Inventory Bridge respeita filter para extração e Stock Link visibility.
- [ ] Alterar filter atualiza rede sem restart/cache stale.
- [ ] Kinetic Battery descarrega somente pelo stress não coberto por outras sources.
- [ ] Battery item preserva charge após break/place e restart.
- [ ] Chained batteries não double-countam energy/stress.
- [ ] Sequenced Pulse Generator persiste sequência corretamente.
- [ ] Feature toggle atualiza viewer e também bloqueia/permite a feature no servidor conforme esperado.
- [ ] Schematic/contraption preserva state sem dupe/loss.
- [ ] Sable/Aeronautics smoke não reproduz regressões de BE/physics contraption.
- [ ] Dois clientes editando o mesmo state convergem corretamente.
Nenhum teste foi marcado como aprovado.

## 14. Evidências e limites
CurseForge confirma a build 1.3.3 e requirement Create 6.0.7+. Changelog/README oficial sustenta Inventory Bridges, filters, Kinetic Battery, Sequenced Pulse Generator, feature-toggle events, catalysts e regressões de lifecycle. O crawler do source não expôs um delta detalhado exclusivo da 1.3.3 além da package-change reiterada; por isso esta ficha não atribui mudanças não comprovadas especificamente a essa build.