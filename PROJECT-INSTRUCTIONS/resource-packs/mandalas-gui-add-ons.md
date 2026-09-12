# Mandala's GUI - Add-Ons

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db813ea83ff0c9f209c8cf
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `MandalasGUI_AddOn+DarkModded_V7.1.zip`
- **Versão 1.21.1:** 7.1
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `MandalasGUI_AddOn+DarkModded_V7.1.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A release V7.1 pode aparecer rotulada como linha 1.21.11, mas a página individual do arquivo registra Minecraft 1.21.1 entre as versões suportadas. Esse rótulo não é tratado como incompatibilidade.

## Propriedades do banco

- **Mod:** Mandala's GUI - Add-Ons
- **Arquivo JAR:** `MandalasGUI_AddOn+DarkModded_V7.1.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 7.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Compat
- **Função:** Pacote de add-ons visuais/mod support do Mandala's GUI que fornece assets de interface para uma ampla seleção de mods e complementa o tema dark/modded.
- **Dependências:** Camada de add-ons para o ecossistema Mandala's GUI. O arquivo físico V7.1 suporta explicitamente 1.21.1; integrações específicas só têm efeito quando o mod alvo correspondente está presente.
- **Sobreposição:** Pode substituir GUIs/sprites também cobertos por Mandala Dark Mode, Utopia extension, compat não oficial, Excalibur GUI compats e packs específicos de mods. Maior prioridade vence asset por asset.
- **Compatibilidade/Riscos:** V7.1 é rotulada como linha 1.21.11, porém a página individual do arquivo inclui explicitamente Minecraft 1.21.1 entre as versões suportadas. Forte overlap com Mandala Dark Mode, Utopia, compat não oficial e outros GUI packs; prioridade deve ser validada por tela.
- **Observações:** Arquivo físico `MandalasGUI_AddOn+DarkModded_V7.1.zip`, versão 7.1. Apesar do rótulo principal 1.21.11, o próprio arquivo lista 1.21.1 como suportado; não há drift de compatibilidade por versão de jogo neste ponto.
- **Procedência:** CurseForge oficial Mandala's GUI - Add-Ons V7.1, File ID 8611963 + captura Resource Packs do perfil em 08/09/2026.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/mandalas-gui-add-ons/files/8611963
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Mandala Add-Ons V7.1, suporte oficial 1.21.1–1.21.11, camada modded/dark GUI, prioridade, overlap, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `MandalasGUI_AddOn+DarkModded_V7.1.zip`, versão `7.1`. A página individual do arquivo lista explicitamente Minecraft `1.21.1` entre as versões suportadas.

## 1. Papel e authority
Mandala's GUI - Add-Ons é uma camada de **mod support/interface** para o ecossistema Mandala's GUI. Ela fornece assets de GUI, fontes e elementos visuais para mods suportados; cada mod continua authority de inventories, recipes, dados, ações e networking.

## 2. Boundary de versão
A release aparece na listagem como `Darkmode + Modded V7.1` da linha 1.21.11, mas o arquivo V7.1 declara suporte também a **1.21.1**. Portanto o rótulo da linha principal não deve ser confundido com incompatibilidade do arquivo físico.

## 3. Cobertura publicada
O changelog V7.1 lista vários mods adicionados/atualizados. Isso comprova uma camada ampla e evolutiva de compatibilidade, mas não autoriza registrar cobertura integral de todos os mods do perfil sem inventário dos assets.

## 4. Stack de GUI
Este pack convive com Mandala Dark Mode, Utopia extension e o compat não oficial, além de compats Excalibur de JEI/AppleSkin/Better ModList. Nos paths coincidentes, a prioridade do resource pack define o asset final.

## 5. Client e reload
É conteúdo client-side. Resource reload/relog deve trocar apenas sprites, fontes e GUIs; não pode alterar inventories, recipe logic, storage state ou demais dados funcionais dos mods.

## 6. Riscos
1. Duas camadas Mandala sobrescreverem a mesma GUI parcialmente.
2. Mod atualizado usar sprite/layout posterior ao override.
3. Mistura visual entre Mandala e Excalibur por prioridade parcial.
4. GUI scale revelar clipping ou contraste inadequado.
5. Resource reload manter sprite stale.

## 7. Matriz de testes
- [ ] Abrir GUIs de vários mods realmente presentes e cobertos.
- [ ] Conferir prioridade contra Mandala Dark Mode/Utopia/compat não oficial.
- [ ] Testar conflitos com Excalibur JEI e outros GUI packs.
- [ ] Validar diferentes GUI scales.
- [ ] Resource reload/relog sem missing sprites.
- [ ] Confirmar que pack on/off não altera dados ou funções das telas.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma V7.1 e suporte do arquivo a 1.21.1. A cobertura exata é limitada aos assets distribuídos; não se presume suporte universal a toda a modlist.

> Boundary canônico: **Mandala Add-Ons controla apenas apresentação das GUIs que cobre; cada mod continua controlando sua funcionalidade**.
