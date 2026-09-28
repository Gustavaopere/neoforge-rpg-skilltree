# YUNG's Better End Island

> **Autoridade física atual — 28/09/2026.** Ordem física **#580**: JAR `YungsBetterEndIsland-1.21.1-NeoForge-3.1.2.jar`, mod id `betterendisland`, runtime `1.21.1-NeoForge-3.1.2`, SHA-1 `832f2c17425debe74a9f267f4136f1a0f0221d19`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** YUNG's Better End Island
- **Arquivo JAR:** `YungsBetterEndIsland-1.21.1-NeoForge-3.1.2.jar`
- **Versão 1.21.1:** 1.21.1-NeoForge-3.1.2
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Worldgen, Exploração
- **Função:** Reformula a ilha central do End e a experiência da luta/progressão inicial da dimensão.
- **Dependências:** YUNG's API 5.1.8. BetterEnd 21.0.34 está presente e altera o restante da dimensão; composição da ilha central exige runtime QA.
- **Sobreposição:** Better End Island concentra-se na ilha central/Dragon Fight; BetterEnd altera biomas/features do End. Overlap real deve ser testado, não presumido como substituição integral.
- **Compatibilidade/Riscos:** Riscos: central-island overlap com BetterEnd, dragon fight/portal/gateway state, reset/config de central tower em mundo existente e duplicate quest progression após reset/resummon. A 3.1.2 também permite vanilla End Gateways e mute do bell; mudanças em mundo existente exigem backup.
- **Observações:** Mod id `betterendisland`, runtime `1.21.1-NeoForge-3.1.2`. Redesenha pillars/gateways/spawn platform/tower; dragon AI permanece vanilla. `/end_island reset` é operação invasiva e exige backup.
- **Procedência:** modlist.txt física atual consultada em 13/09/2026 + CurseForge/Modrinth oficiais Better End Island 3.1.2 + YUNG's API 5.1.8 e BetterEnd 21.0.34. Nenhum first-entry End, dragon fight, gateway, reset-command ou existing-world config test foi executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/yungs-better-end-island-neoforge
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 13/09/2026 — YUNG's Better End Island 3.1.2 permanece exatamente instalado e continua a latest release NeoForge 1.21.1; central island/dragon flow, reset command, BetterEnd boundary e configs 3.1.2 preservados.
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
## 10. Revalidação física — 11/09/2026
A modlist física mantém exatamente `YungsBetterEndIsland-1.21.1-NeoForge-3.1.2.jar`, mod id `betterendisland`, versão `1.21.1-NeoForge-3.1.2`. A file page oficial continua apontando 3.1.2 como latest release NeoForge 1.21.1.
O changelog exato da 3.1.2 acrescenta configs para controlar a geração da **central tower**, opção para **mutar o som do bell** e opção para gerar **End Gateways vanilla** no lugar dos gateways redesenhados. O upstream alerta que mudanças de central tower funcionam melhor em mundos novos e recomenda backup antes de alterar mundos existentes. BetterEnd `21.0.34` e YUNG's API `5.1.8` continuam presentes. O estado **Integrado ao Github** e a decisão **Sem decisão** foram preservados. Nenhum first-entry End, dragon summon/death, gateway, resummon, reset-command ou existing-world config test foi executado nesta recatalogação.
## 11. Revalidação — 13/09/2026
Better End Island 3.1.2 permanece a latest release NeoForge 1.21.1. Os controles da central tower, mute do bell, opção de End Gateways vanilla e o boundary com BetterEnd permanecem os principais regression gates. Nenhum teste runtime foi executado.
