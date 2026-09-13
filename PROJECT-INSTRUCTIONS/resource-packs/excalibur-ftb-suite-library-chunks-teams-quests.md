# Excalibur | FTB Suite (Library, Chunks, Teams, Quests)

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d669db9f0db81b4a3b5e6eba2c88b32
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `excalibur_ftbsuite.zip`
- **Versão 1.21.1:** sem versão semântica própria publicada
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `excalibur_ftbsuite.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A modlist física de 08/09/2026 confirma exatamente os quatro alvos do resource pack: FTB Library `2101.1.35`, FTB Chunks `2101.1.22`, FTB Teams `2101.1.11` e FTB Quests `2101.1.34`.
- O arquivo não possui versão semântica pública própria; o campo permanece vazio em vez de inferir versão a partir da data de publicação.
- O nome “FTB Suite” não é usado para inferir módulos FTB adicionais. O escopo desta ficha é somente Library, Chunks, Teams e Quests, conforme a publicação do pack.

## Propriedades do banco

- **Mod:** Excalibur | FTB Suite (Library, Chunks, Teams, Quests)
- **Arquivo JAR:** `excalibur_ftbsuite.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** sem versão semântica própria publicada
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Compat, QoL
- **Função:** Resource pack 16x que retexturiza a suíte FTB — Library, Chunks, Teams e Quests — para combinar com a paleta e estética do Excalibur.
- **Dependências:** Uso visual pretendido: Excalibur + FTB Library 2101.1.35 + FTB Chunks 2101.1.22 + FTB Teams 2101.1.11 + FTB Quests 2101.1.34. Não altera lógica de claims, teams ou quests.
- **Sobreposição:** Pode colidir com Mandala's GUI e outros GUI/resource packs que alterem sprites FTB. Sobreposição deve ser resolvida por asset path e prioridade, sem inferir alteração funcional.
- **Compatibilidade/Riscos:** Sem versão semântica pública no arquivo `excalibur_ftbsuite.zip`. Riscos de drift de GUI/icons com updates FTB, assets faltantes, prioridade incorreta e conflito com outros GUI packs. Claims/teams/quest state continuam nos mods FTB.
- **Observações:** Arquivo instalado `excalibur_ftbsuite.zip`; upstream não publica versão semântica separada, portanto `Versão 1.21.1` permanece vazia. Alvo explícito: Library, Chunks, Teams e Quests.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + modlist física atual + CurseForge oficial do Excalibur FTB Suite publicado em 29/08/2026.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-ftb-suite-library-chunks-teams-quests
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê visual reconstruído; FTB Library/Chunks/Teams/Quests físicos, escopo sem versão semântica, UI authority, load order, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `excalibur_ftbsuite.zip`. O projeto foi publicado para retexturizar **FTB Library, FTB Chunks, FTB Teams e FTB Quests** no estilo Excalibur. Não há versão semântica pública separada para o arquivo instalado.

## 1. Papel e authority
É uma camada de UI/assets. FTB Library continua provider da infraestrutura visual/comum; FTB Chunks controla map/claims/chunkloading; FTB Teams controla teams; FTB Quests controla chapters, tasks, rewards e progression.

## 2. Stack físico atual
- FTB Library `2101.1.35`;
- FTB Chunks `2101.1.22`;
- FTB Teams `2101.1.11`;
- FTB Quests `2101.1.34`.

O resource pack deve ser validado contra exatamente essas builds.

## 3. Cobertura e limite
O upstream confirma retexture da suíte para a paleta/estética Excalibur, mas não publica manifesto completo de sprites, GUIs ou icons. Cobertura integral não é presumida.

## 4. Client e gameplay authority
Map screens, buttons, quest panels, team UI e icons são apresentação. Claim ownership, quest completion, reward state e team membership permanecem server/provider-authoritative.

## 5. Load order e reload
O pack precisa de prioridade suficiente para vencer o Excalibur base nos assets FTB. Outros GUI packs acima podem substituir sprites. Resource reload deve alterar apenas apresentação, sem apagar claims, teams ou progresso de quests.

## 6. Riscos
1. GUI/icon novo das builds FTB atuais não coberto.
2. Outro GUI pack vencer os mesmos assets.
3. Texto perder legibilidade sobre background retexturizado.
4. Map/quest UI ficar visualmente misturada.
5. Resource reload manter sprite cache stale.

## 7. Matriz de testes
- [ ] Abrir FTB Chunks map/claims.
- [ ] Abrir FTB Teams e conferir icons/buttons.
- [ ] Abrir FTB Quests em chapters, tasks e rewards.
- [ ] Verificar tooltips/text contrast.
- [ ] Resource reload sem missing sprites.
- [ ] Confirmar que progresso/claims não mudam com pack on/off.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma `excalibur_ftbsuite.zip`, publicado em 29/08/2026, e o alvo Library/Chunks/Teams/Quests. A modlist física confirma os quatro módulos e versões usados nesta ficha. Sem asset manifest, completude permanece fail-closed.

> Boundary canônico: **FTB controla todos os dados e regras; este resource pack controla somente apresentação visual**.
