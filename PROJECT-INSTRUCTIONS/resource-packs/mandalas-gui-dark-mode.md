# Mandala's GUI - Dark mode

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db816ea744de38225b973b
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack
- **Arquivo:** `MandalasGUI+Dakmode_1.21.8_v3.1.zip`
- **Versão 1.21.1:** 3.1
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra captura física da pasta Resource Packs do perfil em 08/09/2026 como evidência de instalação de `MandalasGUI+Dakmode_1.21.8_v3.1.zip`.
- A modlist física JAR-centric de 08/09/2026 não constitui prova independente da presença do `.zip`.
- O filename contém `1.21.8`, porém a página oficial da v3.1 declara suporte explícito de Minecraft 1.21.1 a 1.21.8; portanto isso não é tratado como drift de versão.

## Propriedades do banco

- **Mod:** Mandala's GUI - Dark mode
- **Arquivo JAR:** `MandalasGUI+Dakmode_1.21.8_v3.1.zip`
- **Tipo de conteúdo:** Resource Pack
- **Versão 1.21.1:** 3.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual
- **Função:** Resource pack de interface em modo escuro que redefine a apresentação de GUIs na linguagem visual Mandala, servindo de base para extensões e compats do mesmo ecossistema.
- **Dependências:** Resource pack base/tema de GUI; não exige mod funcional para alterar a GUI vanilla. Add-ons e compatibility packs Mandala dependem desta base conforme seus próprios projetos.
- **Sobreposição:** Base visual compartilhada com Mandala Add-Ons, Utopia extension e compat não oficial; também pode disputar GUIs com Excalibur JEI/AppleSkin/Better ModList e outros packs de interface.
- **Compatibilidade/Riscos:** Filename contém `1.21.8`, mas a página individual da v3.1 declara suporte explícito de Minecraft 1.21.1 a 1.21.8. Forte overlap com Mandala Add-Ons/Utopia/compat não oficial e outros GUI packs; prioridade precisa de QA.
- **Observações:** Arquivo físico preservado exatamente como capturado: `MandalasGUI+Dakmode_1.21.8_v3.1.zip`. Apesar do `1.21.8` no nome, o changelog do próprio arquivo diz “Supports 1.21.1 to 1.21.8”.
- **Procedência:** CurseForge oficial Mandala's GUI - Dark mode v3.1, File ID 8605130 + captura Resource Packs do perfil em 08/09/2026.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/mandalas-gui-dark-mode/files/8605130
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Mandala Dark Mode v3.1, suporte explícito 1.21.1–1.21.8, GUI authority, addon stack, overlap, riscos e QA catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** —

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `MandalasGUI+Dakmode_1.21.8_v3.1.zip`, versão `3.1`. O próprio arquivo declara suporte de Minecraft **1.21.1 a 1.21.8**.

## 1. Papel e authority
Mandala's GUI - Dark mode é a camada-base de interface escura do ecossistema Mandala. Ela redefine apresentação de telas, backgrounds, widgets e outros assets de GUI; Minecraft e cada mod continuam authorities da lógica das telas e dos dados exibidos.

## 2. Boundary do filename
O texto `1.21.8` no filename identifica a linha nominal da release, mas não limita sozinho a compatibilidade. A página individual da v3.1 declara explicitamente “Supports 1.21.1 to 1.21.8”. Portanto a instalação no perfil 1.21.1 não é tratada como drift.

## 3. Relação com extensões
Mandala Add-Ons, Utopia extension e o compat não oficial usam/estendem esta linguagem visual. Esses packs podem substituir subconjuntos adicionais de GUIs de mods e devem ser ordenados conscientemente acima da base quando necessário.

## 4. Load order e overlaps
Outros GUI resource packs — incluindo compats Excalibur — podem sobrescrever sprites/backgrounds/widgets iguais. A combinação pode produzir interfaces híbridas quando a prioridade não é coerente.

## 5. Client e reload
É conteúdo client-side. Resource reload/relog deve mudar somente apresentação; containers, inventories, recipe handling, network state e outras mecânicas não podem mudar.

## 6. Riscos
1. Addon/compat acima da base cobrir apenas parte de uma tela.
2. Outro GUI pack vencer assets específicos.
3. GUI scale revelar clipping ou contraste inadequado.
4. Mod atualizado alterar sprite/layout esperado.
5. Resource reload manter asset stale.

## 7. Matriz de testes
- [ ] GUIs vanilla principais em diferentes GUI scales.
- [ ] Telas de mods cobertas pelas extensões Mandala.
- [ ] Prioridade contra Add-Ons, Utopia e compat não oficial.
- [ ] Prioridade contra compats Excalibur de interface.
- [ ] Resource reload/relog sem missing sprites.
- [ ] Confirmar que pack on/off não altera funcionalidade de menus/inventários.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma v3.1 e suporte 1.21.1–1.21.8 no próprio arquivo. O catálogo preserva o filename físico sem transformar seu rótulo em uma restrição inexistente.

> Boundary canônico: **Mandala Dark Mode controla apresentação de GUI; Minecraft e os mods controlam toda lógica e estado das telas**.
