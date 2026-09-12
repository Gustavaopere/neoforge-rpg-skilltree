# (Unofficial) Mandala's GUI - Dark Mode Mod Compatibility

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db816e9b6eec75cc683600
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Mandala's GUI - Dark Mode Compat 0.3.2.zip`
- **Versão 1.21.1:** 0.3.2
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Mandala's GUI - Dark Mode Compat 0.3.2.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- O projeto é explicitamente não oficial e WIP. Suporte publicado a um mod não é tratado como prova de presença física desse mod no pack.

## Propriedades do banco

- **Mod:** (Unofficial) Mandala's GUI - Dark Mode Mod Compatibility
- **Arquivo JAR:** `Mandala's GUI - Dark Mode Compat 0.3.2.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 0.3.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Compat
- **Função:** Resource pack não oficial e WIP que adiciona compatibilidade visual de GUIs de mods ao Mandala's GUI - Dark mode.
- **Dependências:** Mandala's GUI - Dark mode é requisito explícito. Integrações de mods só têm efeito quando o mod correspondente está presente; o projeto lista, entre outros, Apothic Attributes, Curios, Farmer's Delight, Sophisticated Core, Supplementaries e Tom's Simple Storage.
- **Sobreposição:** Sobreposição forte com Mandala Add-Ons, Utopia extension, Mandala base e compats Excalibur de GUI. Prioridade resolve os sprites/layouts coincidentes.
- **Compatibilidade/Riscos:** Projeto é WIP e requer Mandala's GUI - Dark mode. O arquivo 0.3.2 lista 1.21.1 explicitamente entre as versões suportadas. Pode colidir com Utopia/Add-Ons/Excalibur GUI packs nos mesmos assets.
- **Observações:** Arquivo físico `Mandala's GUI - Dark Mode Compat 0.3.2.zip`, versão 0.3.2. Embora a listagem principal o agrupe sob 26.2, a página individual do arquivo declara suporte também a Minecraft 1.21.1.
- **Procedência:** CurseForge oficial do projeto por Gammael, arquivo 0.3.2/File ID 7063749 + captura Resource Packs do perfil em 08/09/2026.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/unofficial-mandalas-gui-dark-mode-mod/files/7063749
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — compat não oficial 0.3.2, suporte explícito 1.21.1, Mandala Dark Mode obrigatório, WIP, mod coverage, load order, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Mandala's GUI - Dark Mode Compat 0.3.2.zip`, versão `0.3.2`. A página individual do arquivo inclui explicitamente Minecraft `1.21.1` entre as versões suportadas.

## 1. Papel e authority
Este projeto é um compatibility resource pack **não oficial e WIP** para Mandala's GUI - Dark mode. Ele adapta GUIs de mods suportados ao tema escuro; os mods continuam authorities de inventories, menus, recipes e dados.

## 2. Dependência obrigatória
O upstream declara **Mandala's GUI - Dark mode** como requisito. O compat não é um tema-base autônomo.

## 3. Cobertura publicada
A lista oficial inclui dezenas de mods, entre eles Apothic Attributes, Cosmetic Armor Reworked, Curios, Farmer's Delight, Polymorph, Sophisticated Core, Supplementaries e Tom's Simple Storage. Suporte publicado não equivale a presença física; cada integração só é relevante quando o alvo está instalado.

## 4. Boundary de versão
Embora a página geral destaque a release 0.3.2 sob a linha 26.2, a página individual do arquivo declara suporte a **1.21.1**. Portanto 0.3.2 é preservado sem downgrade para 0.3.

## 5. Load order
Mandala Add-Ons e Utopia extension podem cobrir GUIs iguais. Compats Excalibur também podem disputar JEI, storage e outros widgets. Nos assets coincidentes, maior prioridade vence.

## 6. Riscos
1. Projeto WIP deixar telas sem cobertura.
2. Mod atualizado alterar layout/path.
3. Duas camadas de compat produzirem mistura visual parcial.
4. Contraste/clipping variar com GUI scale.
5. Resource reload manter sprite stale.

## 7. Matriz de testes
- [ ] Confirmar Mandala Dark Mode ativo.
- [ ] Abrir GUIs de mods fisicamente presentes na lista de suporte.
- [ ] Testar prioridade contra Add-Ons/Utopia/Excalibur.
- [ ] Validar diferentes GUI scales.
- [ ] Resource reload/relog sem missing sprites.
- [ ] Confirmar ausência de alteração funcional nas telas.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma dependência no Mandala Dark Mode, estado WIP, lista de mods e suporte 1.21.1 do arquivo 0.3.2. Não se presume cobertura além dos assets distribuídos.

> Boundary canônico: **o compat controla somente apresentação de GUIs; os mods controlam integralmente a funcionalidade dessas interfaces**.
