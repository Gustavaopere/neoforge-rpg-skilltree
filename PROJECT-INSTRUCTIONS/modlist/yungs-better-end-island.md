# YUNG's Better End Island

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81a0bff2f15de81ed6bc
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** YUNG's Better End Island
- **Arquivo JAR:** `YungsBetterEndIsland-1.21.1-NeoForge-3.1.2.jar`
- **Versão 1.21.1:** 1.21.1-NeoForge-3.1.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Worldgen, Exploração
- **Função:** Reformula a ilha central do End e a experiência da luta/progressão inicial da dimensão.
- **Dependências:** YUNG's API 5.1.8. BetterEnd 21.0.34 está presente e altera o restante da dimensão; composição da ilha central exige runtime QA.
- **Sobreposição:** Better End Island concentra-se na ilha central/Dragon Fight; BetterEnd altera biomas/features do End. Overlap real deve ser testado, não presumido como substituição integral.
- **Compatibilidade/Riscos:** Riscos: central-island overlap com BetterEnd, dragon fight/portal/gateway state, reset em mundo existente e duplicate quest progression após reset/resummon.
- **Observações:** Mod id `betterendisland`, runtime `1.21.1-NeoForge-3.1.2`. Redesenha pillars/gateways/spawn platform/tower; dragon AI permanece vanilla. `/end_island reset` é operação invasiva e exige backup.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Better End Island NeoForge 3.1.2 + stack BetterEnd atual.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/yungs-better-end-island-neoforge
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — central island, dragon-fight flow, reset command e BetterEnd boundary catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-09

# Dossiê operacional — padrão Alex's Mobs

> 🐉 **ESCOPO CANÔNICO.** Runtime físico: `YungsBetterEndIsland-1.21.1-NeoForge-3.1.2.jar`, mod id `betterendisland`, versão `1.21.1-NeoForge-3.1.2`. O mod redesenha a **ilha central do End e o fluxo da Dragon Fight**; não substitui todo o worldgen do End.

## 1. Superfície confirmada
Obsidian pillars, End gateways, spawn platform e o portal/tower central são redesenhados. O dragão não nasce imediatamente: ele é convocado quando o jogador se aproxima da bell tower central.

## 2. Dragon fight authority
A AI do Ender Dragon permanece vanilla; o mod altera arena/ritual/flow da luta. O resummon usa quatro posições de bedrock dentro da tower, mas as posições vanilla de crystals continuam suportadas para compatibilidade.

## 3. Existing worlds
O projeto fornece `/end_island reset` para reconstruir a fight/island state em mundo existente. O próprio upstream alerta que é operação invasiva e recomenda backup. Excluir apenas os arquivos da dimensão End não substitui esse reset.

## 4. Stack atual
O pack também possui **BetterEnd 21.0.34**, que altera a dimensão de modo amplo. O boundary é: Better End Island = ilha central/dragon-fight; BetterEnd = biomas/features/conteúdo dimensional. A composição real precisa de smoke em seed/mundo do pack.

## 5. Authority e lifecycle
Dragon fight state, portal/gateway state e respawn ritual precisam ser server-authoritative e persistentes. Validar primeiro acesso ao End, dragon death, gateway creation, resummon, restart e reset command.

## 6. Boundary para quests/perks
- “Chegar ao End” e “matar o dragão” devem usar state/evento causal real, não detectar a tower visualmente.
- Reset administrativo não pode conceder novamente milestones.
- Resummon não deve resetar progressão de quest salvo design explícito.

## 7. Riscos
- conflito com BetterEnd na ilha central;
- fight state/portal state corrompido após reset/update;
- gateways/pillars desalinhados;
- crystal positions duplicadas;
- quests premiadas novamente após reset/resummon.

## 8. Matriz de testes
- [ ] Dedicated server cria End central correto com BetterEnd 21.0.34 presente.
- [ ] Dragon é convocado no fluxo esperado e AI permanece funcional.
- [ ] Primeira morte cria portal/gateways corretamente.
- [ ] Resummon funciona nas posições novas e vanilla suportadas.
- [ ] Save/restart preserva fight state.
- [ ] `/end_island reset` é testado somente em cópia/backup e não duplica quest state.
- [ ] Chunks externos BetterEnd permanecem coerentes com a ilha central.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 9. Limitação
Não foram auditadas classes/signatures da dragon-fight state machine da 3.1.2. Qualquer integração própria deve consumir state/eventos reais do Minecraft/provider em vez de reconstruir a luta por blocos/posição.