# Excalibur | Apotheosis Support

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d669db9f0db81bd9188e7f6e4f304fc
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Excalibur_Apotheosis Addon_v2.0.zip`
- **Versão 1.21.1:** 2.0
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Excalibur_Apotheosis Addon_v2.0.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física de 08/09/2026 confirma Apotheosis `8.8.0` e módulos Apothic separados. A cobertura publicada de blocks/items/GUI não é extrapolada para namespaces Apothic sem evidência própria.

## Propriedades do banco

- **Mod:** Excalibur | Apotheosis Support
- **Arquivo JAR:** `Excalibur_Apotheosis Addon_v2.0.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 2.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Compat, RPG
- **Função:** Support pack 16x que retexturiza/remodela blocks, items e GUI do Apotheosis para combinar com o estilo Excalibur.
- **Dependências:** Uso visual pretendido: Excalibur + Apotheosis. Stack físico atual: Apotheosis 8.8.0 e seus módulos físicos associados. Conteúdo client-side; enchanting, affixes, spawners e demais sistemas continuam nos mods Apotheosis/Apothic.
- **Sobreposição:** Sobrepõe assets do Apotheosis base. Não assumir cobertura automática dos módulos separados Apothic Attributes/Enchanting/Spawners ou addons; cada namespace precisa de evidência própria.
- **Compatibilidade/Riscos:** Upstream v2.0 declara cobertura de blocks/items/GUI, mas o alvo físico é Apotheosis 8.8.0 e pode conter assets posteriores. Riscos de GUI/icons stale, assets de módulos Apothic não cobertos automaticamente e colisão com outros GUI/retexture packs.
- **Observações:** Arquivo instalado `Excalibur_Apotheosis Addon_v2.0.zip`, release v2.0 de 17/03/2026. Upstream afirma que todos os blocks, items e GUI do escopo do pack foram retexturizados/remodelados.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + modlist física atual + CurseForge oficial Excalibur Apotheosis Support v2.0 para 1.21.1.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-apotheosis-support
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê visual reconstruído; Apotheosis 8.8.0, v2.0, blocks/items/GUI, load order, módulos físicos, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Excalibur_Apotheosis Addon_v2.0.zip`, versão `2.0`, Release para Minecraft 1.21.1. O alvo físico atual é Apotheosis `8.8.0`.

## 1. Papel e authority
Excalibur | Apotheosis Support é uma camada visual. Apotheosis e os módulos Apothic continuam authorities de enchanting, affixes, spawners, attributes, loot e gameplay.

## 2. Cobertura confirmada
O upstream da v2.0 declara que **blocks, items e GUI** do escopo do pack foram retexturizados/remodelados para o tema Excalibur. A ficha preserva esse claim sem inventar contagem de assets.

## 3. Boundary entre Apotheosis e módulos Apothic
O pack físico possui Apotheosis 8.8.0 e módulos separados como Apothic Attributes, Enchanting e Spawners. O nome do resource pack não prova cobertura automática desses namespaces separados; somente assets realmente incluídos no ZIP devem ser atribuídos ao support pack.

## 4. Load order
O upstream instrui colocar o support pack acima do Excalibur original. Outros GUI/retexture packs acima podem substituir blocks/items/GUI individualmente.

## 5. Client e resource reload
É conteúdo client-side. Resource reload deve alterar apenas models/textures/GUI e nunca affixes, enchant values, spawner state, loot ou player attributes.

## 6. Riscos
1. Apotheosis 8.8.0 possuir asset posterior à v2.0.
2. Módulo Apothic separado permanecer sem cobertura e gerar mistura visual.
3. GUI sprite/layout divergir após update do mod.
4. Outro GUI pack sobrescrever telas/icons.
5. Resource reload deixar cache stale ou missing model.

## 7. Matriz de testes
- [ ] Conferir blocks principais do Apotheosis.
- [ ] Conferir items e icons.
- [ ] Abrir GUIs relevantes e validar legibilidade.
- [ ] Identificar quais módulos Apothic mantêm assets próprios fora do escopo.
- [ ] Confirmar prioridade acima do Excalibur.
- [ ] Resource reload sem missing texture/model.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma v2.0 para 1.21.1 e declara retexture/remodel de blocks, items e GUI. O ZIP não foi inventariado asset por asset e a cobertura de namespaces Apothic separados permanece não confirmada.

> Boundary canônico: **Apotheosis/Apothic controlam gameplay; este pack controla apenas os assets visuais efetivamente cobertos**.
