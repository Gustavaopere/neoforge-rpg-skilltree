# Excalibur | Lootr Retextured

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d669db9f0db81d39fadfc7b1095529b
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Excalibur_Lootr_v1.1.zip`
- **Versão 1.21.1:** 1.1
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Excalibur_Lootr_v1.1.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A modlist física de 08/09/2026 confirma `lootr-neoforge-1.21.1-1.11.38.125.jar`, mod id `lootr`, runtime `1.21.1-1.11.38.125`.
- Um guia descritivo anterior ainda cita Lootr `1.11.38.124`; essa referência é stale perante a modlist física e não é usada como autoridade de versão nesta exportação.
- O upstream da v1.1 declara todos os containers do Lootr retexturizados e ajuste em `barrel_top`/`opened_barrel_top`; isso é preservado como claim do projeto, sujeito a QA contra o Lootr físico atual.

## Propriedades do banco

- **Mod:** Excalibur | Lootr Retextured
- **Arquivo JAR:** `Excalibur_Lootr_v1.1.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 1.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Compat, Exploração
- **Função:** Support pack 16x que retexturiza todos os containers do Lootr para o estilo Excalibur; não altera o sistema de loot individual por jogador.
- **Dependências:** Uso visual pretendido: Excalibur + Lootr. Stack físico atual: Lootr 1.21.1-1.11.38.125. Conteúdo client-side; loot generation, per-player container state e persistence continuam no Lootr.
- **Sobreposição:** Sobrepõe textures/models de containers Lootr. Outros container/resource packs podem vencer os mesmos paths; não deve interferir no ownership de loot individual do Lootr.
- **Compatibilidade/Riscos:** Riscos de drift entre v1.1 de 2025 e Lootr 1.11.38.125 atual, state-dependent container textures, load order e colisão com outros container retextures. Visual de container aberto/fechado não é authority de loot state.
- **Observações:** Arquivo instalado `Excalibur_Lootr_v1.1.zip`, release 1.1 de 08/09/2025. Upstream declara todos os containers retexturizados; v1.1 altera levemente `barrel_top` e `opened_barrel_top`.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + modlist física atual + CurseForge oficial Excalibur Lootr v1.1.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-lootr-retextured
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê visual reconstruído; Lootr 1.11.38.125, v1.1, all containers, barrel-top fix, load order, per-player loot boundary, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Excalibur_Lootr_v1.1.zip`, versão `1.1`, para Minecraft 1.21.1. O alvo físico atual é Lootr `1.21.1-1.11.38.125`.

## 1. Papel e authority
O support pack altera a aparência dos containers do Lootr. Lootr continua authority de geração de loot, disponibilidade por jogador, opened/unopened state, persistence e sincronização multiplayer.

## 2. Cobertura confirmada
O upstream declara que **todos os containers do Lootr** foram retexturizados para o estilo Excalibur. A v1.1 registra ajuste específico em `barrel_top` e `opened_barrel_top`.

## 3. State visual versus loot state
Textures de container aberto/fechado podem refletir apresentação de state, mas não são a fonte de verdade para disponibilidade de loot. Scripts/quests não devem inferir “saqueado” pela textura exibida.

## 4. Stack físico e drift
O resource pack é de 08/09/2025 e o Lootr físico está em `1.11.38.125`. Novos containers ou mudanças de model/texture path introduzidas depois da v1.1 precisam de QA visual.

## 5. Load order e reload
A instalação oficial exige o support pack acima do Excalibur original. Resource reload deve trocar apenas assets; conteúdo do container e histórico por jogador não podem mudar.

## 6. Riscos
1. Container novo do Lootr atual sem retexture.
2. `opened`/`closed` model apontar para asset incorreto.
3. Outro pack sobrescrever containers.
4. Resource reload deixar visual stale.
5. Usuário confundir aparência com loot availability real.

## 7. Matriz de testes
- [ ] Conferir chest/barrel e demais containers Lootr disponíveis.
- [ ] Verificar estados visuais antes/depois de abrir.
- [ ] Testar dois jogadores no mesmo container e confirmar loot state independente do visual.
- [ ] Confirmar `barrel_top`/`opened_barrel_top` da v1.1.
- [ ] Resource reload sem missing models/textures.
- [ ] Confirmar prioridade acima do Excalibur.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma v1.1, retexture de todos os containers e o ajuste das duas textures de barrel. A modlist física confirma Lootr `1.21.1-1.11.38.125`; a compatibilidade integral com cada container dessa build permanece sujeita a QA.

> Boundary canônico: **Lootr controla loot e state por jogador; o support pack controla somente a aparência dos containers**.
