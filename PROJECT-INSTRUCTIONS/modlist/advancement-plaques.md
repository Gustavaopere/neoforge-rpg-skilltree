# Advancement Plaques

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81b48a77efba5f3d2b0d
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Advancement Plaques
- **Arquivo JAR:** `AdvancementPlaques-1.21.1-neoforge-1.6.8.jar`
- **Versão 1.21.1:** 1.6.8
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, QoL
- **Função:** Substitui os advancement toasts vanilla por plaques estilizadas/glowing sem alterar critérios, progresso ou rewards. Permite filtrar por frame type Task/Goal/Challenge, escolher topo/baixo e offset, whitelist por advancement ID, customização visual via resource pack e sons próprios. A build 1.6.8 respeita sound overrides de advancements, incluindo Aether.
- **Dependências:** Iceberg é Required Dependency upstream e está presente no pack. Prism é Optional Dependency upstream e não foi localizado top-level. Na linha NeoForge/Forge, custom sounds exigem instalação cliente+servidor devido à limitação documentada; com custom sounds desativados, pode operar client-only.
- **Sobreposição:** Sobreposição exclusivamente de apresentação com outros toast/HUD/advancement notification mods. Não sobrepõe a lógica de advancement, quests, rewards ou progression gates. Resource packs são customização suportada, não concorrência automática.
- **Compatibilidade/Riscos:** Compatibilidade upstream: funciona com advancements modded em geral e é declarada compatível com Toast Control/Toast Manager nas linhas documentadas. Outros mods que substituem advancement popups podem disputar o mesmo toast/HUD. Resource packs podem remodelar plaques. 1.6.8 corrige/respeita advancement sound overrides do Aether. Não tratar duplicação de notificação de FTB Quests como conflito de advancement sem reproduzir.
- **Observações:** A build 1.6.8 é específica para 1.21.1; não importar features 1.7.x de Minecraft 1.21.11/26.x. O mod é apresentação: qualquer trigger/reward continua pertencendo ao advancement original. Se custom sounds permanecerem habilitados, manter cliente e servidor alinhados.
- **Procedência:** Modlist física 2026-09-07 + CurseForge/Modrinth oficiais Advancement Plaques 1.6.8 + guia gameplay/sistemas.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/advancement-plaques
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — dossiê operacional completo de plaque rendering, Task/Goal/Challenge, whitelist, resource-pack theming, sounds, client/server e HUD QA confirmado no QC global #8.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

> 🔎 **Escopo canônico.** Runtime físico: `AdvancementPlaques-1.21.1-neoforge-1.6.8.jar`. O mod substitui a **notificação visual/sonora** de advancements; criteria, progress, parent tree, rewards e completion continuam pertencendo ao advancement vanilla/modded original.

## 1. O que muda na prática
Quando um advancement é concluído e o Minecraft normalmente exibiria um toast retangular, Advancement Plaques renderiza uma plaque mais destacada, com moldura/glow e apresentação diferenciada pelo tipo de advancement.

A mudança está na experiência de feedback. O mod não altera o momento em que o servidor considera o advancement concluído.

## 2. Os três frame types
O sistema de advancement do Minecraft possui três tipos visuais principais, e o mod permite controlar quais recebem plaque:
- **Task**;
- **Goal**;
- **Challenge**.

O usuário pode habilitar qualquer combinação. Isso permite, por exemplo, manter apenas Goal/Challenge como eventos mais chamativos sem transformar Task em plaque.

A aparência tradicional do projeto também diferencia visualmente esses tiers; resource packs podem substituir a estética.

## 3. Posição da plaque
A configuração publicada permite escolher:
- exibição no **topo** da tela;
- exibição na **parte inferior** da tela;
- distância/offset em relação à borda escolhida.

Isso é relevante num pack com HUDs de mana, stamina, temperatura, quests e bosses. A posição deve ser escolhida com base na composição total do HUD, não isoladamente.

## 4. Whitelist por advancement ID
Existe configuração de **whitelist de advancement IDs** para mostrar plaques apenas em advancements selecionados.

Uso correto no modpack:
- restringir plaques a marcos narrativos/importantes;
- evitar spam de addons que registram muitos micro-advancements;
- preservar toast vanilla ou nenhum plaque para conteúdo secundário.

IDs devem ser namespaced (`modid:path`). Não usar nome traduzido como identificador.

## 5. Advancements modded
O upstream declara que o mod funciona com **advancements adicionados por outros mods**. O requisito é que o conteúdo use o sistema normal de advancement/Toast pipeline.

Isso não significa que qualquer sistema de quest vire advancement. FTB Quests, achievements próprios ou popup custom de outro mod podem ter pipeline separado.

## 6. Customização via resource pack
A aparência das plaques pode ser customizada por **resource packs**. O próprio projeto referencia packs temáticos como exemplo de skinning.

Isso torna a auditoria de resource packs parte da experiência final:
- textura/moldura;
- compatibilidade com resolução/UI scale;
- legibilidade de texto;
- possíveis overrides de assets do mod.

Um pack visual quebrado não deve ser diagnosticado como falha de criteria/progress.

## 7. Sons customizados
A versão NeoForge/Forge do mod adiciona **custom advancement sounds**.

O upstream documenta uma limitação da plataforma: com esses sons habilitados, o mod precisa estar presente no **cliente e servidor** para funcionalidade completa.

Se os custom sounds forem **desativados na config**, o mod pode operar **client-only** como substituto visual.

Isso deve ser respeitado em distribuição de modpack/servidor: não presumir que “é visual, então sempre client-only”.

## 8. Aether e sound overrides — mudança 1.6.8
A release física **1.6.8** possui mudança específica: **advancement sound overrides fornecidos pelo Aether passam a ser reproduzidos quando aplicáveis**.

A consequência é importante para a regra de authority sonora:
- se o advancement/provider define override válido, a plaque deve respeitá-lo;
- não forçar som genérico por cima;
- Aether não deve ser classificado como conflito apenas porque também toca o som do advancement.

## 9. Dependências
### Iceberg — obrigatória
CurseForge lista **Iceberg** como Required Dependency. O pack possui Iceberg instalado.

Iceberg fornece infraestrutura compartilhada do ecossistema Grend/Gamemode4-like UI/utilities. Não substituir por outra library genérica.

### Prism — opcional
**Prism** aparece como Optional Dependency upstream. Não foi localizado top-level no snapshot atual.

A ausência de Prism não deve ser tratada como missing dependency fatal.

## 10. Compatibilidade com Toast Control / Toast Manager
A documentação pública declara compatibilidade com **Toast Control/Toast Manager** nas linhas correspondentes. O nome mostrado varia entre páginas/épocas do projeto, mas o ponto operacional é: o autor tratou explicitamente coexistência com um gerenciador de toasts conhecido.

Isso não garante compatibilidade com todo mod que substitui a mesma GUI.

## 11. Outros mods de advancement popup
Upstream alerta que **outros mods que afetam advancement popups podem não funcionar**.

Essa é uma limitação funcional real, embora não exista uma lista formal `Incompatible` na relação CurseForge auditada.

Ao encontrar popup duplicado:
1. identificar se ambos interceptam advancement toast;
2. verificar se um deles pode ser desativado por tipo/config;
3. não culpar criteria/reward;
4. decidir uma única authority visual se necessário.

## 12. Interação com HUD do pack
O pack possui diversas camadas de UI. Pontos de composição:
- boss bars;
- mana/spell HUD;
- temperatura/stamina;
- quest notifications;
- subtitles/chat;
- recipe/info overlays.

Riscos típicos:
- clipping;
- placa cobrindo barra crítica;
- escalas GUI diferentes;
- várias notificações simultâneas;
- texto longo/tradução PT-BR estourando largura.

Nenhum desses riscos implica alteração do advancement em si.

## 13. Texto e tradução
Advancement title/description vêm do conteúdo que registrou o advancement. Portanto comprimento e tradução variam enormemente.

Teste obrigatório em PT-BR:
- título curto;
- título longo;
- description longa;
- caracteres especiais;
- modded advancement com formatação.

A plaque deve manter legibilidade sem truncamento crítico ou sobreposição de ícone.

## 14. Client/server authority
### Cliente + servidor
Modo completo quando custom sounds estão ativos.

### Client-only
Válido quando custom sounds estão desabilitados conforme upstream. Nesse modo, toda função relevante é apresentação local.

### Server-only
Não é um uso útil para jogadores sem o mod visual; não tratar servidor como renderer.

## 15. O que o mod NÃO muda
- advancement criteria;
- triggers;
- progress partial;
- rewards;
- XP/reward functions;
- parent/child tree;
- advancement data packs;
- quests externas.

Se um advancement não completa, investigar o provider/criteria, não Advancement Plaques.

## 16. Sobreposição com FTB Quests e outros quest systems
Quest completion popup pode aparecer perto da plaque, mas trata-se de **pipeline diferente**.

Possível problema: excesso visual quando uma quest também é concluída por um advancement e ambos exibem notification. Isso é UX/duplicação de feedback, não double reward automaticamente.

## 17. Resource-pack authority
Se um resource pack altera os assets da plaque:
- identificar sua prioridade;
- verificar se o pack é para a mesma versão/layout;
- testar GUI scale;
- conferir transparência/glow;
- verificar se não afeta assets Iceberg compartilhados indevidamente.

A ficha de cada resource pack deverá registrar essa relação quando o pack correspondente for auditado.

## 18. Riscos específicos do pack
1. **HUD congestionado** por muitas camadas.
2. **Spam** de micro-advancements — mitigar com whitelist/tipos.
3. **Custom sound mismatch** entre cliente/servidor.
4. **Resource pack desatualizado** alterando assets.
5. **Outro toast replacer** atuando simultaneamente.
6. **Text overflow PT-BR**.
7. **Aether sound override** sendo confundido com erro de áudio.
8. **Quest + advancement simultâneos** duplicando feedback visual.

## 19. Matriz de validação
1. Task vanilla.
2. Goal vanilla.
3. Challenge vanilla.
4. Desabilitar cada tipo individualmente.
5. Plaque no topo.
6. Plaque embaixo.
7. Ajustar offset perto de outros HUDs.
8. Whitelist com advancement vanilla.
9. Whitelist com advancement modded namespaced.
10. Advancement do Aether com sound override.
11. Custom sounds habilitados cliente+servidor.
12. Client-only com custom sounds desabilitados.
13. Advancement modded com título longo PT-BR.
14. Resource pack customizando plaque.
15. UI scale pequena, média e grande.
16. Receber vários advancements em sequência.
17. Coexistência com toast manager/control se presente.
18. Coexistência com FTB Quests notification.
19. Dedicated-server join sem erro de sound registration.
20. Remover Advancement Plaques e confirmar criteria/rewards idênticos.

## 20. Regras para outros chats
- Nunca usar a plaque como trigger de gameplay; usar o advancement real.
- Não atribuir reward ao mod.
- Para spam, preferir config/whitelist antes de remover conteúdo.
- Se custom sounds estiverem ativos, manter instalação dos dois lados.
- Se houver conflito de HUD, resolver posição/authority visual antes de tocar no advancement.
- Não importar mudanças 1.7.x para a build física 1.6.8.

## 21. Fontes e confiança
**Authority física:** modlist 07/09/2026 — `AdvancementPlaques-1.21.1-neoforge-1.6.8.jar`.

**Upstream:** [CurseForge — Advancement Plaques](https://www.curseforge.com/minecraft/mc-mods/advancement-plaques), relations de Iceberg/Prism e release 1.6.8; documentação oficial/Modrinth para configuração, resource packs e client/server.

**Fonte interna:** guia Gameplay/Sistemas.

**Confiança:** alta para escopo, tipos Task/Goal/Challenge, whitelist, posição, custom sounds, dependências e mudança Aether 1.6.8. Qualquer asset concreto de resource pack deve ser validado na etapa de resource packs.
