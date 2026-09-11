# Excalibur | Create Support

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d669db9f0db81dc834bc2819f4a19e7
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Excalibur_Create-6 Addon_NeoForge_v2.32.zip`
- **Versão 1.21.1:** 2.32
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Excalibur_Create-6 Addon_NeoForge_v2.32.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física acessível de 08/09/2026 confirma o provider-alvo Create `6.0.10`.

## Propriedades do banco

- **Mod:** Excalibur | Create Support
- **Arquivo JAR:** `Excalibur_Create-6 Addon_NeoForge_v2.32.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 2.32
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Compat, Tecnologia
- **Função:** Support pack 16x que retexturiza/remodela Create no estilo Excalibur, cobrindo blocks das abas Main/Decorative, items, GUI e connected textures publicados.
- **Dependências:** Uso visual pretendido: Excalibur base + Create 6.0.10. Build instalada: NeoForge v2.32 para Minecraft 1.21.1. Não é dependência de gameplay/servidor.
- **Sobreposição:** Deve ficar acima do Excalibur base. Outros packs que alterem assets do namespace Create podem prevalecer por prioridade; addons de Create exigem suporte visual próprio quando usam outros namespaces.
- **Compatibilidade/Riscos:** Riscos de conflito com outros retextures/Create addons, CTM/model drift, GUI nova e load order. Addons de Create em namespaces próprios não são automaticamente cobertos.
- **Observações:** Arquivo instalado `Excalibur_Create-6 Addon_NeoForge_v2.32.zip`; upstream declara todos os blocks das abas Main/Decorative, todos os items, GUI e connected textures para stonebricks, naturals e display board.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + modlist física atual + CurseForge oficial Excalibur Create Support v2.32 NeoForge/1.21.1.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-create-support
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê visual reconstruído; Create 6.0.10, v2.32, blocks/items/GUI/CTM, load order, authority, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Excalibur_Create-6 Addon_NeoForge_v2.32.zip`, versão `2.32`, Release para Minecraft 1.21.1. O alvo físico atual é Create `6.0.10`.

## 1. Papel e authority
Excalibur | Create Support é uma camada visual 16x para Create. **Create 6.0.10** continua authority de kinetic networks, stress/speed, contraptions, processing, fluids, trains, logistics, recipes e networking. O resource pack não altera comportamento mecânico.

## 2. Cobertura confirmada
O upstream da v2.32 declara cobertura de **todos os blocks das abas Main e Decorative do Create**, **todos os items**, **GUI** e connected textures para variantes de stonebricks, blocos naturais e display board. O catálogo preserva esse escopo publicado sem transformar a declaração em contagem inventada de assets.

## 3. Stack físico e versão
A build NeoForge `v2.32` foi publicada especificamente para Minecraft 1.21.1 em 04/07/2026. O pack usa Create `6.0.10`; assets adicionados ou renomeados nessa build devem ser validados visualmente, mesmo quando o upstream declara cobertura ampla.

## 4. Load order
A instalação oficial exige o support pack **acima do Excalibur base**. Outros packs que alterem `create` models/textures/GUI podem vencer paths individuais se tiverem prioridade maior.

## 5. Connected textures e resource reload
Connected textures e models devem convergir após resource reload/relog. Falha de CTM/model é problema de apresentação; não muda block state, network topology ou cálculo cinético.

## 6. Sobreposição e riscos
1. Addons de Create podem usar namespaces próprios não cobertos por este pack.
2. Outro Create retexture pode sobrescrever items/blocks/GUI.
3. CTM pode ficar inconsistente entre variantes adjacentes.
4. GUI nova do Create 6.0.10 pode exigir asset não presente.
5. Resource reload pode expor missing model/texture ou cache stale.

## 7. Matriz de testes
- [ ] Conferir blocks das abas Main e Decorative.
- [ ] Conferir items principais e icons.
- [ ] Abrir GUIs representativas de processing/logistics.
- [ ] Testar stonebricks, naturals e display board conectados.
- [ ] Confirmar prioridade acima do Excalibur.
- [ ] Resource reload sem missing textures/models.
- [ ] Confirmar que pack on/off não altera contraptions, recipes ou inventories.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma `v2.32` NeoForge para 1.21.1 e o escopo blocks/items/GUI/connected textures. Não foi feito inventário binário do ZIP asset por asset.

> Boundary canônico: **Create controla gameplay e automação; este support pack controla somente os assets visuais que substitui**.
