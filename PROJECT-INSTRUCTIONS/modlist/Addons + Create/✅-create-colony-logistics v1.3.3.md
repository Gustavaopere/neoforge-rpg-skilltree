# Create Colony Logistics

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Arquivo JAR:** `create_colony_logistics-1.3.3.jar`
- **Versão 1.21.1:** 1.3.3
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Compat, Automação
- **Função:** Bridge automatizada MineColonies ↔ Create Logistics: traduz requests da colônia em pedidos/packages Create, usa Freight Depot/transporte para abastecer Warehouses, exporta excedentes e, desde 1.3.3, suporta logística entre colônias aliadas.
- **Dependências:** Create `6.0.10` + MineColonies `1.1.1387-1.21.1-snapshot` estão fisicamente presentes na modlist de 16/09/2026. A bridge é Client & Server e depende funcionalmente dos dois providers.
- **Sobreposição:** Complementa MineColonies e Create. Não substitui Warehouse/Couriers, colony state, Create package/storage network ou trains; ownership permanece nos providers.
- **Compatibilidade/Riscos:** Forte coupling Create↔MineColonies. Riscos: dupe/loss em settlement, replay/infinite export, AI stall, train/chunk timing, pathfinding, API drift e, em 1.3.3, autorização/contabilidade cross-colony. A correção 1.3.2 de infinite export continua regression gate.
- **Observações:** JAR físico `create_colony_logistics-1.3.3.jar`, mod id `cclogistics`, runtime 1.3.3. Release oficial NeoForge 1.21.1 de 14/09/2026, file ID 8878545. A 1.3.3 introduz Cross-Colony Logistics, Alliance system no Foremen's Hut, acesso opcional ao Create Storage por aliados e limites de déficit comercial.
- **Procedência:** modlist física de 16/09/2026 + CurseForge oficial Create: Colony Logistics 1.3.3. Dossiê migrado do Notion preservado e reconciliado à versão instalada; nenhum teste runtime foi executado nesta reauditoria.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-colony-logistics
- **Atualização/Status:** REAUDITADO EM 16/09/2026 — runtime físico atualizado de 1.3.2 para 1.3.3; conteúdo técnico reconciliado com o Cross-Colony Update. Certificação permanece pendente até QC/re-fetch final.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-06

> 📦 **ESCOPO CANÔNICO.** Runtime físico: `create_colony_logistics-1.3.3.jar`, mod id `cclogistics`, versão `1.3.3`. É uma bridge Client & Server entre **MineColonies** e a logística do **Create**; desde 1.3.3 também coordena fornecimento entre colônias aliadas. Não cria um terceiro inventário autoritativo.

## 1. Fluxo logístico base
O Logistics Coordinator monitora necessidades da colônia. Quando um worker/building solicita materiais, a integração consulta a infraestrutura disponível, gera a requisição e usa packages/transporte para enviar itens à colônia. O fluxo publicado usa Freight Depot, Stock Ticker/Stock Link, Packager e transporte; Packer Agents descarregam packages e entregam o conteúdo ao Warehouse.

## 2. Exportação da colônia
A integração pode retirar excedentes do Warehouse e devolvê-los ao ecossistema logístico Create. Essa rota exige comportamento exactly-once: uma stack não pode ser debitada e exportada de novo após reload/retry. A 1.3.2 corrigiu explicitamente um **infinite export bug**; o gate permanece válido na 1.3.3.

## 3. Freight Depot e segurança ferroviária
A linha 1.2.x introduziu Freight Depot/Train Station e Train Barrier, bloco invisível que permite passagem de jogadores/trens mas bloqueia pathfinding de colonists. Isso não prova que layouts existentes usem esses blocos; construções reais precisam de validação.

## 4. Authority e ownership
- **MineColonies:** requests, workers, Warehouses e colony state.
- **Create:** storage network, packages, trains e logística cinética.
- **Colony Logistics:** tradução/orquestração entre ambos e, em 1.3.3, entre colônias aliadas.

Nenhuma integração externa deve criar inventário ou settlement paralelo.

## 5. Runtime físico do pack
A modlist de 16/09/2026 contém Create `6.0.10`, MineColonies `1.1.1387-1.21.1-snapshot` e Colony Logistics `1.3.3`. O snapshot MineColonies específico continua sendo regression surface; compatibilidade publicada não substitui smoke test.

## 6. Atualização instalada — 1.3.3 Cross-Colony
A release 1.3.3 adiciona suporte oficial para múltiplas colônias trabalharem em conjunto. O **Foremen's Hut** foi refatorado para um sistema de **Alliances**:
- convite por nome do jogador ou Colony ID e aceite no Foremen's Hut;
- opção para permitir que o Freight Inspector exporte diretamente do Create Storage ligado ao Freight Depot;
- limite configurável de déficit comercial, inclusive opção ilimitada em cenários apropriados;
- quando a colônia solicitante não possui estoque, o Freight Inspector consulta aliados online e com chunks carregados;
- um aliado capaz de fornecer/craftar recebe a ordem, prepara o package e o coloca no Export Vault para transporte.

O upstream afirma uso da API oficial MineColonies para requests cross-colony, mas isso não equivale a prova runtime no pack.

## 7. Lifecycle e multiplayer
Validar startup, load de colony data, login/reconnect, chunk unload de Warehouse/Freight Depot/Export Vault, chegada de transporte, worker AI retry, restart durante entrega e mudança de disponibilidade de aliados. Requests e trades precisam de identidade estável para impedir replay.

## 8. Riscos
1. **Dupe/loss:** um provider conclui settlement e o outro não.
2. **Infinite export regression:** excedente volta a ser exportado repetidamente.
3. **AI stall:** coordinator/packer/freight inspector para de processar.
4. **Train/chunk timing:** destino parcialmente unloaded.
5. **Version coupling:** API Create/MineColonies diverge.
6. **Pathfinding:** Train Barrier/layout interfere em colonists.
7. **Alliance authorization:** aliado acessa Create Storage sem permissão pretendida.
8. **Trade accounting:** deficit threshold fica stale, duplica ou bloqueia indevidamente.
9. **Cross-colony replay:** pedido é atendido mais de uma vez por retry/reconnect.

## 9. Boundary para quests/perks
Entrega só deve contar quando request/settlement estiver concluído e deduplicável. Package em trânsito, GUI aberta, item no Warehouse/Export Vault ou alliance request isolado não são eventos suficientes.

## 10. Matriz de testes
- [ ] Dedicated server inicia com 1.3.3 + Create 6.0.10 + MineColonies 1.1.1387 snapshot.
- [ ] Pedido local é atendido exatamente uma vez.
- [ ] Warehouse recebe quantidade correta.
- [ ] Exportação de excedente não entra em loop.
- [ ] Restart/unload durante trânsito não duplica nem perde package.
- [ ] Dois requests simultâneos convergem corretamente.
- [ ] Train Barrier preserva comportamento esperado.
- [ ] Alliance invite/accept/revoke mantém state correto.
- [ ] Permissão de acesso ao Create Storage é respeitada.
- [ ] Deficit limit bloqueia/libera fornecimento sem corrupção de contabilidade.
- [ ] Cross-colony order via Export Vault é concluída uma única vez.

**Nenhum desses testes foi executado nesta reauditoria documental.**

## 11. Evidências e limites
A modlist física confirma JAR/runtime e providers atuais. CurseForge oficial confirma 1.3.3 em NeoForge 1.21.1 e o fluxo Cross-Colony/Alliance/Foremen's Hut. O conteúdo anterior do Notion sobre Coordinator, Freight Depot, Warehouse, exportação e fix 1.3.2 foi preservado. Internals de request IDs/packets e garantias transacionais não foram pinados ao binário; integrações próprias devem usar APIs/state observáveis reais.

## 12. Reauditoria física — 16/09/2026
O runtime físico mudou de `1.3.2` para `1.3.3`. A documentação foi reconciliada à versão instalada e mantém como histórico técnico os gates introduzidos em 1.2.x/1.3.2. Não houve teste runtime, multiplayer, alliance, transport, dupe/loss ou restart nesta passagem.