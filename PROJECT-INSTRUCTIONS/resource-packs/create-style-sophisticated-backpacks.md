# Create Style Sophisticated Backpacks

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db815b9a2bc7aba371c1d8
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `CreateSophBackpacks.zip`
- **Versão própria:** não publicada; não usar a versão do jogo como versão do resource pack
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `CreateSophBackpacks.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física de 08/09/2026 confirma Sophisticated Backpacks `3.26.2`, Sophisticated Core `1.5.1` e Create `6.0.10`; esses são os providers/contextos atuais do pack visual.

## Propriedades do banco

- **Mod:** Create Style Sophisticated Backpacks
- **Arquivo JAR:** `CreateSophBackpacks.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** sem versão semântica própria publicada
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Visual, Tecnologia, Armazenamento
- **Função:** Resource pack que reestiliza Sophisticated Backpacks para parecer parte da linguagem visual do Create, sem alterar mecânicas de armazenamento ou upgrades.
- **Dependências:** Sophisticated Backpacks 3.26.2 + Sophisticated Core 1.5.1 como alvo visual; Create 6.0.10 define a estética de referência. Não altera storage slots, upgrades, recipes ou lógica Create.
- **Sobreposição:** Conflito visual potencial com Excalibur Sophisticated Support e qualquer outro pack que altere models/textures de Sophisticated Backpacks. Mods de integração Create continuam funcionais independentemente desta camada visual.
- **Compatibilidade/Riscos:** O arquivo `CreateSophBackpacks.zip` é listado pelo upstream como compatível com 1.21.1 e foi corrigido para updates recentes do Sophisticated Backpacks. Pode colidir com Excalibur Sophisticated Support e outros retextures de backpacks; prioridade decide os assets.
- **Observações:** Arquivo físico `CreateSophBackpacks.zip`; o projeto não publica versão semântica própria, portanto `Versão 1.21.1` permanece vazia. O arquivo atual inclui 1.21.1 entre as versões suportadas.
- **Procedência:** CurseForge oficial Create Style Sophisticated Backpacks, arquivo `CreateSophBackpacks.zip` + captura Resource Packs do perfil em 08/09/2026 + modlist física Sophisticated Backpacks 3.26.2/Core 1.5.1/Create 6.0.10.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/create-sophisticated-backpacks/files/7707479
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — CreateSophBackpacks, suporte oficial 1.21.1, Sophisticated Backpacks 3.26.2 + Create 6.0.10, escopo visual, overlaps, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `CreateSophBackpacks.zip`, sem versão semântica própria publicada. O arquivo atual inclui Minecraft `1.21.1` entre as versões suportadas.

## 1. Papel e authority
Create Style Sophisticated Backpacks adapta a apresentação de **Sophisticated Backpacks** à estética industrial do Create. Sophisticated Backpacks/Core continuam authorities de inventário, slots, upgrades, filtros, persistence e demais mecânicas.

## 2. Compatibilidade de versão
Embora a listagem destaque a linha 1.21.11, a página individual de `CreateSophBackpacks.zip` declara explicitamente suporte a **1.21.1**. O changelog informa correção para atualização recente do Sophisticated Backpacks.

## 3. Stack físico
O perfil contém Sophisticated Backpacks `3.26.2`, Sophisticated Core `1.5.1` e Create `6.0.10`, além de integrações funcionais entre Create e Sophisticated. Este resource pack não substitui essas integrações; ele é somente visual.

## 4. Versão do pack
O upstream não fornece número semântico próprio para `CreateSophBackpacks.zip`. `Versão 1.21.1` permanece vazia em vez de converter a versão do jogo ou data de upload em versão do pack.

## 5. Load order e sobreposição
`Excalibur | Sophisticated Support` também toca o ecossistema Sophisticated. Nos assets coincidentes, o pack de maior prioridade vence. A escolha deve refletir se o visual desejado é Create ou Excalibur para backpacks específicos.

## 6. Client e reload
Resource reload/relog deve afetar apenas textures/models. Conteúdo do inventário, upgrade state, capacidades, recipes e integração Create não podem mudar.

## 7. Riscos
1. Overlap parcial com Excalibur Sophisticated Support.
2. Sophisticated Backpacks atualizar paths/models e deixar fallback.
3. Mistura de tiers com estilos visuais diferentes por prioridade.
4. Item/block model inconsistente em primeira/terceira pessoa.
5. Resource reload manter model stale.

## 8. Matriz de testes
- [ ] Conferir tiers de backpacks no inventário e mundo.
- [ ] Conferir backpacks equipadas/colocadas quando aplicável.
- [ ] Testar GUI e upgrade presentation sem alteração funcional.
- [ ] Comparar prioridade com Excalibur Sophisticated Support.
- [ ] Resource reload/relog sem missing textures/models.
- [ ] Confirmar que pack on/off não altera slots, upgrades ou conteúdo.

Nenhum teste foi marcado como aprovado.

## 9. Evidências e limite
CurseForge oficial confirma o objetivo “Creatified”, o filename atual, suporte a 1.21.1 e correção para atualização do Sophisticated Backpacks. Não se atribui gameplay ao resource pack.

> Boundary canônico: **Sophisticated Backpacks/Core controlam armazenamento e upgrades; este pack controla somente apresentação visual**.
