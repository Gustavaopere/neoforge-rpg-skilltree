# Excalibur | Better ModList & Mod Menu Compat

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81fdb405f17869a901ae
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Excalibur - Better ModList Mod Menu Compat v3.0.zip`
- **Versão 1.21.1:** 3.0
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Excalibur - Better ModList Mod Menu Compat v3.0.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão física é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física de 08/09/2026 confirma Better ModList `21.1.1`, mod id `mod_menu`. Não há Mod Menu Fabric top-level separado no snapshot.
- **Drift crítico:** o upstream associa v3.0 às linhas 26.1/26.2, enquanto o arquivo explicitamente 1.21.1 é v1.0. A compatibilidade do v3.0 físico com 1.21.1 permanece fail-closed até QA.

## Propriedades do banco

- **Mod:** Excalibur | Better ModList & Mod Menu Compat
- **Arquivo JAR:** `Excalibur - Better ModList Mod Menu Compat v3.0.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 3.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Visual, Compat, QoL
- **Função:** Compatibility resource pack que redesenha botões/GUI de Better ModList e Mod Menu para integrar essas interfaces ao estilo Excalibur.
- **Dependências:** Excalibur base + Better ModList 21.1.1 fisicamente presente. Não há Mod Menu Fabric top-level no snapshot; Better ModList usa mod id `mod_menu`. Conteúdo é visual/client-side.
- **Sobreposição:** Sobrepõe botões/assets de Better ModList/Mod Menu e pode colidir com GUI packs. Mandala's GUI e outras camadas visuais devem ser testadas por prioridade.
- **Compatibilidade/Riscos:** DRIFT CRÍTICO: ZIP físico é `v3.0`, mas a listagem oficial associa v3.0 às linhas 26.1/26.2; o arquivo explicitamente 1.21.1 é `Excalibur Better ModList v1.0.zip`. Compatibilidade do v3.0 físico com 1.21.1 permanece fail-closed até QA.
- **Observações:** Arquivo instalado `Excalibur - Better ModList Mod Menu Compat v3.0.zip`. Não rebaixar automaticamente: o físico é autoridade de presença/versão instalada, mas a matriz oficial atual não valida v3.0 como build 1.21.1.
- **Procedência:** Captura Resource Packs do perfil em 08/09/2026 + modlist física Better ModList 21.1.1 + CurseForge oficial Excalibur Better ModList & Mod Menu Compat.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-better-modlist-and-mod-menu-compat
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — v3.0 físico, Better ModList 21.1.1, drift crítico contra build oficial 1.21.1 v1.0, GUI/load order, riscos e QA catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Excalibur - Better ModList Mod Menu Compat v3.0.zip`, versão física `3.0`. Há drift de distribuição: o upstream associa v3.0 às linhas 26.1/26.2, enquanto o arquivo explicitamente 1.21.1 é v1.0.

## 1. Papel e authority
Este pack redesenha assets/botões de Better ModList e Mod Menu para combinar com Excalibur. Better ModList continua owner da funcionalidade de lista de mods; o resource pack não altera descoberta de mods, config ou loader state.

## 2. Stack físico
O perfil contém Better ModList `21.1.1`, cujo mod id físico é `mod_menu`. Não há um Mod Menu Fabric top-level separado no snapshot atual; portanto o nome do compat não autoriza inferir outro mod instalado.

## 3. Boundary crítico de versão
O ZIP instalado declara `v3.0`. Na matriz oficial atual, v3.0 aparece ligado às linhas `26.1/26.2`; o arquivo explicitamente listado para Minecraft `1.21.1` é `Excalibur Better ModList v1.0.zip`. O catálogo preserva v3.0 como autoridade física, mas **não declara compatibilidade 1.21.1 validada** sem QA.

## 4. Cobertura confirmada
O upstream descreve redesign dos botões de Better ModList e Mod Menu. Não extrapolar para toda a UI, todos os mod screens ou comportamento do menu.

## 5. Load order e GUI stack
Deve prevalecer sobre Excalibur base nos assets específicos. Outros GUI packs, especialmente Mandala's GUI e compats, podem disputar os mesmos sprites/buttons por prioridade.

## 6. Riscos
1. v3.0 usar paths/formats das linhas 26.x.
2. Botões ausentes ou deslocados no Better ModList 21.1.1.
3. GUI pack concorrente sobrescrever assets.
4. Resource reload manter sprite stale.
5. Nome “Mod Menu” induzir inferência incorreta de mod Fabric top-level.

## 7. Matriz de testes
- [ ] Abrir Better ModList 21.1.1.
- [ ] Verificar todos os botões redesenhados presentes.
- [ ] Comparar v3.0 com GUI sem o pack.
- [ ] Testar junto a Mandala's GUI e seus addons.
- [ ] Resource reload sem missing sprites.
- [ ] Confirmar que ativação do pack não altera lista/config de mods.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma o objetivo visual e mostra v3.0 nas linhas modernas, além de v1.0 como arquivo 1.21.1. A instalação física permanece v3.0; essa divergência é mantida fail-closed.

> Boundary canônico: **v3.0 está instalado, mas não deve ser declarado compatível com 1.21.1 apenas pelo filename; QA visual é obrigatória**.
