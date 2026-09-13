# Excalibur | Tom's Simple Storage Support

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d669db9f0db81d48139cc94992cc09a
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Excalibur Tom's Simple Storage 1.3.zip`
- **Versão 1.21.1:** 1.3
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Excalibur Tom's Simple Storage 1.3.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A modlist física acessível de 08/09/2026 confirma `toms_storage-1.21-2.4.2.jar`, mod id `toms_storage`, runtime `2.4.2`.
- O upstream da v1.3 declara redesign de todos os items e blocks e das GUIs do Tom's; essa cobertura é preservada como claim oficial do projeto, sem inventário independente do ZIP.
- O resource pack não altera storage network, terminals, filtering, crafting, inventory transfer ou autocrafting; essas autoridades permanecem integralmente no Tom's Simple Storage.

## Propriedades do banco

- **Mod:** Excalibur | Tom's Simple Storage Support
- **Arquivo JAR:** `Excalibur Tom's Simple Storage 1.3.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 1.3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Compat, Armazenamento
- **Função:** Support pack 16x que redesenha items, blocks e GUIs do Tom's Simple Storage para combinar com o estilo Excalibur.
- **Dependências:** Uso visual pretendido: Excalibur + Tom's Simple Storage. Stack físico atual: Tom's Simple Storage 2.4.2. O resource pack é client-side; storage network, terminals, crafting e inventory transfer permanecem no mod.
- **Sobreposição:** Sobrepõe assets de Tom's Simple Storage, especialmente containers/terminals/GUI. Outros GUI/storage packs podem vencer os mesmos paths por prioridade; gameplay permanece no Tom's.
- **Compatibilidade/Riscos:** Riscos de drift com Tom's 2.4.2, GUI sprite/layout, novos blocks/items sem cobertura, load order e colisão com outros storage/GUI packs. Aparência de terminal/inventory não altera conteúdo ou routing da rede.
- **Observações:** Arquivo instalado `Excalibur Tom's Simple Storage 1.3.zip`, release 1.3 de 14/12/2025. Upstream declara redesign de todos os items e blocks e GUIs em estilo Excalibur.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + modlist física atual + CurseForge oficial Excalibur Tom's Simple Storage Support 1.3.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-toms-simple-storage-support
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê visual reconstruído; Tom's Simple Storage 2.4.2, v1.3, all items/blocks + GUI, load order, storage authority, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Excalibur Tom's Simple Storage 1.3.zip`, versão `1.3`. O alvo físico atual é Tom's Simple Storage `2.4.2`.

## 1. Papel e authority
O support pack altera exclusivamente apresentação de Tom's Simple Storage. O mod continua authority da storage network, inventories, terminals, connectors, crafting, filtering e item transfer.

## 2. Cobertura confirmada
O upstream da v1.3 declara **redesign de todos os items e blocks** e também **GUIs em estilo Excalibur**. A ficha preserva esse claim sem inventar contagem de assets.

## 3. GUI versus storage state
Terminal, slots, search field e demais elementos visuais representam state do Tom's. O resource pack não pode ser tratado como alteração de capacidade, routing, autocrafting ou conteúdo de inventário.

## 4. Stack físico e drift
O alvo é Tom's Simple Storage 2.4.2. A release visual 1.3 é de 14/12/2025; qualquer block/item/GUI adicionado ou reorganizado posteriormente precisa ser validado no runtime atual.

## 5. Load order e reload
O support pack deve ficar acima do Excalibur base para seus assets do Tom's prevalecerem. Resource reload troca apenas textures/models/GUI e não deve desconectar rede nem alterar inventories.

## 6. Sobreposição
Pode colidir com outros GUI packs e retextures de storage/terminals. A prioridade final deve ser avaliada por asset path. Nenhum conflito visual autoriza alterar configuração da storage network.

## 7. Riscos
1. GUI do Tom's 2.4.2 possuir sprite/layout posterior à v1.3.
2. Block/item novo cair no visual padrão.
3. Outro GUI pack reduzir legibilidade ou sobrescrever widgets.
4. Model de terminal/container ficar incoerente com estado real.
5. Resource reload manter sprite/model cache stale.

## 8. Matriz de testes
- [ ] Conferir todos os blocks/items principais do Tom's.
- [ ] Abrir Storage Terminal e Crafting Terminal.
- [ ] Conferir slots, search e buttons em múltiplas resoluções de GUI.
- [ ] Confirmar que pack on/off não altera inventories/network state.
- [ ] Testar prioridade com outros GUI packs ativos.
- [ ] Resource reload sem missing sprites/models.

Nenhum teste foi marcado como aprovado.

## 9. Evidências e limite
CurseForge oficial confirma v1.3 e declara redesign de todos os items/blocks e GUIs. A modlist física confirma Tom's Simple Storage `2.4.2`. A compatibilidade integral com cada asset dessa build permanece sujeita a QA runtime.

> Boundary canônico: **Tom's controla armazenamento e automação; o support pack controla apenas a apresentação visual**.
