# Mandala's GUI - Dark mode - Utopia extension

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81348277d76fb5dc5404
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Mandala Utopia.zip`
- **Versão 1.21.1:** —
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra captura física da pasta Resource Packs do perfil em 08/09/2026 como evidência de instalação de `Mandala Utopia.zip`.
- O mesmo filename foi reutilizado por múltiplas releases oficiais; sem hash/file-id do ZIP físico, a versão semântica permanece deliberadamente vazia.
- Mandala's GUI - Dark mode é requisito explícito da extensão.

## Propriedades do banco

- **Mod:** Mandala's GUI - Dark mode - Utopia extension
- **Arquivo JAR:** `Mandala Utopia.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** —
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Compat
- **Função:** Extensão de compatibilidade para Mandala's GUI - Dark mode que adapta GUIs de aproximadamente 110 mods ao tema escuro Utopia.
- **Dependências:** Mandala's GUI - Dark mode é requisito explícito. A extensão fornece compatibilidade visual para cerca de 110 mods; cada integração só tem efeito quando o mod correspondente está presente.
- **Sobreposição:** Camada ampla de GUI que pode competir com Excalibur JEI, Excalibur AppleSkin, Better ModList compat e outros packs de interface. Nos sprites/GUI coincidentes, maior prioridade vence.
- **Compatibilidade/Riscos:** O filename `Mandala Utopia.zip` foi reutilizado por releases diferentes; não identifica versão com segurança. Upstream exige Mandala's GUI - Dark mode. Forte overlap com outros GUI packs/compats; prioridade precisa de QA.
- **Observações:** Arquivo instalado `Mandala Utopia.zip`; o mesmo filename aparece em múltiplas releases, incluindo 0.15 e 0.15.1, portanto `Versão 1.21.1` permanece vazia sem hash/file-id. A linha 1.21+ NeoForge foi testada pelo projeto.
- **Procedência:** CurseForge oficial Mandala's GUI - Dark mode - Utopia extension + captura Resource Packs do perfil em 08/09/2026; filename físico preservado sem inferência de versão.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/mandalas-gui-dark-mode-utopia-extension
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Utopia extension, filename sem versão conclusiva, Mandala Dark Mode obrigatório, ~110 mod GUIs, load order, riscos e QA catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** —

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Mandala Utopia.zip`. O filename não identifica com segurança uma versão semântica, pois foi reutilizado em múltiplas releases; `Versão 1.21.1` permanece vazia.

## 1. Papel e authority
Mandala's GUI - Dark mode - Utopia extension é uma extensão visual para **Mandala's GUI - Dark mode**, criada para adaptar GUIs de muitos mods ao mesmo tema escuro. Cada mod continua authority da lógica de seus menus, inventories, recipes e dados.

## 2. Dependência obrigatória
O upstream declara **Mandala's GUI - Dark mode** como requisito. A extensão não deve ser tratada como tema-base autônomo; ela fornece overrides de compatibilidade sobre essa base.

## 3. Cobertura publicada
O projeto anuncia compatibilidade de GUI para aproximadamente **110 mods**. Essa contagem descreve o catálogo do projeto, não confirma que todos esses mods estejam instalados no perfil atual.

## 4. Boundary de versão
O filename `Mandala Utopia.zip` aparece em mais de uma release oficial, incluindo versões como `0.15` e `0.15.1`. Sem file-id/hash do ZIP físico, atribuir um desses números seria especulação. A versão permanece fail-closed.

## 5. Linha 1.21+ e stack de GUI
O projeto registra testes de mods NeoForge na linha 1.21+. Mesmo assim, cada GUI deve ser validada contra a versão física do mod correspondente e contra as demais camadas visuais ativas.

## 6. Sobreposição e load order
A extensão pode competir com **Excalibur JEI Support**, **Excalibur x AppleSkin**, **Excalibur Better ModList compat** e outros GUI packs. Nos sprites/backgrounds/widgets coincidentes, a prioridade de resource pack define a apresentação final.

## 7. Riscos
1. Filename não identificar univocamente a release instalada.
2. GUI de mod atualizado usar paths/layouts posteriores ao override.
3. Outro pack de interface sobrescrever parte do Utopia.
4. Mistura visual entre dark mode e Excalibur por prioridade parcial.
5. GUI scale revelar clipping, contraste ou sprite mismatch.

## 8. Matriz de testes
- [ ] Confirmar Mandala's GUI - Dark mode ativo abaixo/na ordem esperada.
- [ ] Abrir GUIs de vários mods efetivamente instalados e cobertos.
- [ ] Testar JEI junto ao Excalibur JEI Support.
- [ ] Testar AppleSkin/HUD e Better ModList onde houver overlap.
- [ ] Validar diferentes GUI scales.
- [ ] Resource reload/relog sem missing sprites.
- [ ] Confirmar que pack on/off não altera inventories, recipes ou dados.

Nenhum teste foi marcado como aprovado.

## 9. Evidências e limite
CurseForge oficial confirma a dependência no Mandala Dark Mode, a cobertura ampla de mod GUIs e a reutilização do filename em releases distintas. Sem hash/file-id físico, a versão não é inferida.

> Boundary canônico: **Mandala Dark Mode fornece a base visual; Utopia fornece compats de GUI; os mods continuam controlando toda funcionalidade das telas**.
