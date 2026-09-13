# Create Colony Logistics

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d369db9f0db8157bff3f2f542a2f595
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Colony Logistics
- **Arquivo JAR:** `create_colony_logistics-1.3.2.jar`
- **Versão 1.21.1:** 1.3.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Compat, Automação
- **Função:** Bridge automatizada MineColonies ↔ Create Logistics: traduz requests da colônia em pedidos/packages Create, usa Freight Depot/trem para abastecer o Warehouse e também pode exportar excedentes da colônia.
- **Dependências:** Create 6.0.10 + MineColonies 1.1.1381-1.21.1-snapshot estão fisicamente presentes. A bridge é Client & Server e depende funcionalmente dos dois providers.
- **Sobreposição:** Complementa MineColonies e Create. Não substitui Warehouse/Couriers, Create package network ou trains; ownership permanece nos providers.
- **Compatibilidade/Riscos:** Forte coupling Create↔MineColonies. Riscos: dupe/loss em settlement, replay/infinite export, AI stall, train/chunk timing, pathfinding e API drift. 1.3.2 corrige infinite export e atualiza dependências; runtime físico ainda exige QA com MineColonies snapshot.
- **Observações:** JAR físico `create_colony_logistics-1.3.2.jar`, mod id `cclogistics`, runtime 1.3.2. Release oficial NeoForge 1.21.1 de 04/09/2026; 1.3.2 corrige infinite export bug.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge/GitHub oficiais Create: Colony Logistics 1.3.2 e changelog da release.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-colony-logistics
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — placeholder de reconciliação substituído por dossiê técnico 1.3.2; requests, Freight Depot, Warehouse settlement, exportação, infinite-export regression, lifecycle e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-06

> 📦 **ESCOPO CANÔNICO.** Runtime físico: `create_colony_logistics-1.3.2.jar`, mod id `cclogistics`, versão `1.3.2`. É uma bridge Client & Server entre **MineColonies** e a logística do **Create**; não substitui Warehouse/Couriers nem o sistema de packages do Create.

## 1. Fluxo logístico confirmado
O Logistics Coordinator monitora necessidades da colônia. Quando um worker/building solicita materiais, a integração consulta o storage Create, gera a requisição e usa packages/transporte para enviar os itens à colônia.
O fluxo oficial usa Freight Depot, Stock Ticker/Stock Link, Packager e transporte por trem. Packer Agents descarregam packages e entregam o conteúdo ao Warehouse.

## 2. Exportação da colônia
A integração também pode retirar excedentes do Warehouse e devolvê-los ao ecossistema logístico Create. Essa rota é sensível a exatamente-once: uma mesma stack não pode ser debitada do Warehouse e exportada novamente após reload/retry.
A release 1.3.2 corrige explicitamente um **infinite export bug**, tornando essa regressão obrigatória no QA do pack.

## 3. Freight Depot e segurança ferroviária
A linha 1.2.x introduziu Freight Depot/Train Station e Train Barrier, bloco invisível que permite passagem de jogadores/trens mas bloqueia pathfinding de colonists. A ficha não presume que layouts antigos usem esses blocos; validar construções reais da colônia.

## 4. Authority e ownership
MineColonies continua owner de requests, workers, Warehouse e colony state. Create continua owner de storage network, packages, trains e logística cinética. Colony Logistics traduz pedidos/entregas entre ambos; não deve criar um terceiro inventário autoritativo.

## 5. Runtime atual do pack
A modlist física contém Create 6.0.10 e MineColonies 1.1.1381-1.21.1-snapshot. A 1.3.2 foi publicada após atualização das dependências do projeto para releases recentes. Isso reduz drift conhecido, mas não substitui smoke test com o snapshot físico específico de MineColonies.

## 6. Lifecycle e multiplayer
Validar startup, load de colony data, login/reconnect, chunk unload do Warehouse/Freight Depot, train arrival, worker AI retry e restart durante entrega. Requests devem ter identidade estável para impedir replay de entrega/exportação.

## 7. Riscos
1. **Dupe/loss:** request concluído em um provider mas não no outro.
2. **Infinite export regression:** item exportado repetidamente do Warehouse.
3. **AI stall:** coordinator/packer deixa de solicitar ou processar pacote.
4. **Train/chunk timing:** chegada ocorre com depot/warehouse parcialmente unloaded.
5. **Version coupling:** mudanças de APIs Create/MineColonies quebram bridge.
6. **Pathfinding:** Train Barrier ou layout interfere indevidamente em colonists.

## 8. Boundary para quests/perks
Entrega só deve contar quando houver request/settlement concluído e deduplicável. Package em trânsito, abertura de GUI ou presença do item no Warehouse não são eventos suficientes por si só.

## 9. Matriz de testes
- [ ] Dedicated server inicia com 1.3.2 + Create 6.0.10 + MineColonies 1.1.1381.
- [ ] Pedido de building é atendido exatamente uma vez.
- [ ] Trem descarrega e Warehouse recebe quantidade correta.
- [ ] Exportação de excedente não entra em loop.
- [ ] Restart/unload durante trânsito não duplica nem perde package.
- [ ] Dois requests simultâneos para o mesmo item convergem corretamente.
- [ ] Train Barrier impede colonist sem bloquear trem/jogador.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 10. Evidências e limite
Modlist física confirma JAR/versão e stack atual. CurseForge/GitHub oficiais confirmam o loop Logistics Coordinator→Create storage→package/train→Freight Depot→Warehouse, exportação e fix 1.3.2 de infinite export. Internals de request IDs/packets não foram pinados ao binário; integrações próprias devem usar APIs/state observáveis reais.
