# Excalibur | EasyNPC

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81299b8ed222fbd0b960
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Excalibur EasyNPC v1.0.zip`
- **Versão 1.21.1:** 1.0
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Excalibur EasyNPC v1.0.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física acessível de 08/09/2026 confirma a linha funcional Easy NPC `7.11.0`, incluindo `easy_npc-neoforge-1.21.1-7.11.0.jar`, `easy_npc_bundle-neoforge-1.21.1-7.11.0.jar` e `easy_npc_config_ui-neoforge-1.21.1-7.11.0.jar`.

## Propriedades do banco

- **Mod:** Excalibur | EasyNPC
- **Arquivo JAR:** `Excalibur EasyNPC v1.0.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 1.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Visual, Compat, RPG
- **Função:** Compatibility resource pack que redesenha a GUI de diálogo do Easy NPC para a estética Excalibur, sem alterar scripts, diálogos ou lógica dos NPCs.
- **Dependências:** Excalibur base + Easy NPC 7.11.0 no stack físico. O pacote principal/bundle/config UI permanecem authorities funcionais; resource pack é visual/client-side.
- **Sobreposição:** Sobrepõe GUI/assets de diálogo do Easy NPC. Outros GUI packs podem vencer sprites por prioridade; não altera NPC data, commands, quests ou conversation state.
- **Compatibilidade/Riscos:** Riscos de drift da GUI entre Easy NPC 7.11.0 e updates posteriores, load order incorreto e colisão com outros GUI packs. O upstream exige colocar este pack acima do Excalibur.
- **Observações:** Arquivo instalado `Excalibur EasyNPC v1.0.zip`, única release oficial 1.21.1 de 15/06/2026. Upstream confirma especificamente redesign da GUI de diálogo.
- **Procedência:** CurseForge oficial Excalibur | EasyNPC v1.0 para 1.21.1 + captura Resource Packs do perfil em 08/09/2026 + modlist física Easy NPC 7.11.0.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-easynpc
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — v1.0, Easy NPC 7.11.0, dialogue GUI, load order acima do Excalibur, authority, riscos e QA catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Excalibur EasyNPC v1.0.zip`, versão `1.0`, Release oficial para Minecraft 1.21.1. O stack físico usa Easy NPC `7.11.0`.

## 1. Papel e authority
Excalibur | EasyNPC redesenha a **GUI de diálogo** do Easy NPC para a estética Excalibur. Easy NPC continua authority de NPC data, dialogues, actions, commands, scripts, permissions e persistence.

## 2. Cobertura confirmada
O upstream publica como feature específica o **redesign da GUI de diálogo**. Não há base para atribuir retexture integral de todos os NPCs, skins, editor/config UI ou outras telas sem evidência adicional.

## 3. Stack físico
O perfil contém Easy NPC `7.11.0`, além de bundle/config UI da mesma linha. Esses módulos pertencem ao sistema funcional; o resource pack só fornece apresentação onde possui assets.

## 4. Load order obrigatório
A documentação instrui colocar o pack **acima do Excalibur base**. Outro GUI pack acima dele pode substituir sprites, frames ou elementos da tela de diálogo.

## 5. Client e reload
É conteúdo client-side. Resource reload deve atualizar a apresentação da GUI sem modificar texto, branching, commands ou estado dos NPCs.

## 6. Riscos
1. Easy NPC 7.11.0 usar layout/sprite diferente do esperado.
2. GUI pack concorrente sobrescrever o diálogo.
3. Texto perder legibilidade por contraste/font.
4. Escala de GUI revelar clipping/alignment.
5. Resource reload manter sprites antigos.

## 7. Matriz de testes
- [ ] Abrir diálogo simples de Easy NPC.
- [ ] Testar diálogo com múltiplas opções.
- [ ] Conferir textos longos e diferentes GUI scales.
- [ ] Testar junto a outros GUI packs ativos.
- [ ] Confirmar prioridade acima do Excalibur.
- [ ] Resource reload sem missing sprites.
- [ ] Confirmar que pack on/off não altera diálogo ou scripts.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma v1.0 para 1.21.1, redesign da GUI de diálogo e instrução de prioridade acima do Excalibur. Nenhuma função de NPC é atribuída ao resource pack.

> Boundary canônico: **Easy NPC controla NPCs e diálogos; este pack controla apenas a apresentação visual da GUI coberta**.
